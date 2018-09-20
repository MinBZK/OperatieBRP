/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.centrale.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import nl.bzk.brp.blobifier.service.BlobifierService;
import nl.bzk.brp.dataaccess.repository.ToegangLeveringsautorisatieRepository;
import nl.bzk.brp.gba.centrale.berichten.AfnemerindicatieOnderhoudAntwoord;
import nl.bzk.brp.gba.centrale.berichten.AfnemerindicatieOnderhoudOpdracht;
import nl.bzk.brp.gba.dataaccess.Lo3FilterRubriekRepository;
import nl.bzk.brp.levering.afnemerindicaties.model.BewerkAfnemerindicatieResultaat;
import nl.bzk.brp.levering.afnemerindicaties.service.AfnemerindicatiesMetRegelsService;
import nl.bzk.brp.levering.algemeen.LeveringConstanten;
import nl.bzk.brp.levering.lo3.bericht.Bericht;
import nl.bzk.brp.levering.lo3.bericht.BerichtFactory;
import nl.bzk.brp.levering.lo3.conversie.ConversieCache;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.SysteemNaamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ReferentienummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienstbundel;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Stelsel;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.internbericht.RegelMelding;
import nl.bzk.brp.model.internbericht.SynchronisatieBerichtGegevens;
import nl.bzk.brp.model.operationeel.ber.BerichtStuurgegevensGroepModel;
import nl.bzk.brp.serialisatie.JsonStringSerializer;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory.ConversietabelFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.transaction.annotation.Transactional;

/**
 * Verwerk afnemerindicatie onderhoud opdracht.
 */
public final class AfnemerindicatiesService implements GbaService {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    @Autowired
    private AfnemerindicatiesMetRegelsService afnemerindicatiesService;
    @Autowired
    private ToegangLeveringsautorisatieRepository toegangLeveringsautorisatieRepository;
    @Autowired
    private BlobifierService blobifierService;
    @Autowired
    private BerichtFactory berichtFactory;
    @Autowired
    private Lo3FilterRubriekRepository lo3FilterRubriekRepository;
    @Autowired
    private ConversietabelFactory conversietabelFactory;

    private JmsTemplate verzendingTemplate;

    @Required
    public void setVerzendingTemplate(final JmsTemplate verzendingTemplate) {
        this.verzendingTemplate = verzendingTemplate;
    }

    @Override
    @Transactional(transactionManager = "lezenSchrijvenTransactionManager")
    public String verwerk(final String verzoek, final String berichtReferentie) {
        // Unmarshal JSON
        AfnemerindicatieOnderhoudOpdracht opdracht;
        try {
            opdracht = JSON_MAPPER.readValue(verzoek, AfnemerindicatieOnderhoudOpdracht.class);
        } catch (final IOException e) {
            throw new JmsException("Kan bericht niet deserialiseren naar AfnemerindicatieOnderhoudOpdracht.", e) {
                private static final long serialVersionUID = 1L;
            };
        }

        // Antwoord bericht
        final AfnemerindicatieOnderhoudAntwoord antwoord = new AfnemerindicatieOnderhoudAntwoord();
        antwoord.setReferentienummer(opdracht.getReferentienummer());

        // Bepaal toegang leveringsautorisatie
        final ToegangLeveringsautorisatie toegang =
                toegangLeveringsautorisatieRepository.haalToegangLeveringsautorisatieOp(opdracht.getToegangLeveringsautorisatieId());
        if (toegang == null) {
            // ToegangLeveringsautorisatie niet gevonden
            antwoord.setFoutcode('X');
        } else {
            // Verwerk opdracht
            final BewerkAfnemerindicatieResultaat resultaat;
            switch (opdracht.getEffectAfnemerindicatie()) {
                case PLAATSING:
                    final Dienst dienstSpontaan = toegang.getLeveringsautorisatie().geefDienst(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE);
                    if (dienstSpontaan == null) {
                        // Geen spontaan bij plaatsen
                        antwoord.setFoutcode('R');
                    } else {
                        resultaat =
                                afnemerindicatiesService.gbaPlaatsAfnemerindicatie(toegang, opdracht.getPersoonId(), opdracht.getDienstId(), null, null);
                        antwoord.setFoutcode(bepaalFoutcode(resultaat.getMeldingen()));

                        if (antwoord.getFoutcode() == null) {
                            // Versturen Ag01 bij succesvol verwerken plaatsen afnemersindicatie
                            maakEnVerstuurAg01Bericht(opdracht, toegang, dienstSpontaan.getDienstbundel());
                        }
                    }
                    break;
                case VERWIJDERING:
                    resultaat = afnemerindicatiesService.gbaVerwijderAfnemerindicatie(toegang, opdracht.getPersoonId(), opdracht.getDienstId());
                    antwoord.setFoutcode(bepaalFoutcode(resultaat.getMeldingen()));
                    break;
                default:
                    throw new IllegalArgumentException("Effect afnemerindicatie heeft ongeldige waarde: " + opdracht.getEffectAfnemerindicatie());
            }
        }

        // Marshall JSON
        final String antwoordTekst;
        try {
            antwoordTekst = JSON_MAPPER.writeValueAsString(antwoord);
        } catch (final JsonProcessingException e) {
            throw new JmsException("Kan bericht niet serialiseren naar AfnemerindicatieOnderhoudAntwoord.", e) {
                private static final long serialVersionUID = 1L;
            };
        }
        return antwoordTekst;
    }

    private Character bepaalFoutcode(final List<RegelMelding> meldingen) {
        for (final RegelMelding melding : meldingen) {
            if (SoortMelding.FOUT != melding.getSoort().getWaarde()) {
                continue;
            }

            final String regelCode = melding.getRegel().getWaarde().getCode();
            final Character foutcode = conversietabelFactory.createRegelConversietabel().converteerNaarLo3(regelCode);
            if (foutcode == null) {
                throw new IllegalArgumentException("Regelcode heeft ongeldige waarde (in conversie van regel brp naar foutcode lo3): " + regelCode);
            }
            return foutcode;
        }

        return null;
    }

    /**
     * Maak en verstuur bericht.
     *
     * @param opdracht
     *            opdracht
     */
    private void maakEnVerstuurAg01Bericht(
        final AfnemerindicatieOnderhoudOpdracht opdracht,
        final ToegangLeveringsautorisatie toegang,
        final Dienstbundel dienstbundelSpontaan)
    {
        // Lees persoon
        final PersoonHisVolledig persoon = blobifierService.leesBlob(opdracht.getPersoonId());

        // Volg de bericht strategy (ook voor ontkoppeling van migratie code)
        final Bericht bericht = berichtFactory.maakAg01Bericht(persoon);

        // Converteer naar LO3
        bericht.converteerNaarLo3(new ConversieCache());

        // Bepaal filter rubrieken obv de dienstbundel voor spontaan
        final List<String> rubrieken = lo3FilterRubriekRepository.haalLo3FilterRubriekenVoorDienstbundel(dienstbundelSpontaan);
        bericht.filterRubrieken(rubrieken);

        // Maak bericht
        final String uitgaandBericht = bericht.maakUitgaandBericht();

        // Message creator
        final MessageCreator jmsBerichtMetAfnemerXml = new Ag01Bericht(uitgaandBericht, toegang, opdracht);

        // Verzenden
        final Partij partij = toegang.getGeautoriseerde().getPartij();
        verzendingTemplate.send(partij.getQueueNaam(), jmsBerichtMetAfnemerXml);
    }

    /**
     * Message creator voor het versturen van een Ag01 bericht.
     */
    public static final class Ag01Bericht implements MessageCreator {

        private static final JsonStringSerializer<SynchronisatieBerichtGegevens> BERICHT_GEGEVENS_SERIALIZER =
                new JsonStringSerializer<>(SynchronisatieBerichtGegevens.class);

        private final String uitgaandBericht;
        private final ToegangLeveringsautorisatie toegangLeveringsautorisatie;
        private final AfnemerindicatieOnderhoudOpdracht opdracht;

        /**
         * Instantieert een nieuw Ag01 bericht.
         *
         * @param uitgaandBericht
         *            uitgaand bericht
         * @param toegangLeveringsautorisatie
         *            toegang leveringsautorisatie
         * @param opdracht
         *            de opdracht
         */
        public Ag01Bericht(
            final String uitgaandBericht,
            final ToegangLeveringsautorisatie toegangLeveringsautorisatie,
            final AfnemerindicatieOnderhoudOpdracht opdracht)
        {
            this.uitgaandBericht = uitgaandBericht;
            this.toegangLeveringsautorisatie = toegangLeveringsautorisatie;
            this.opdracht = opdracht;
        }

        @Override
        public Message createMessage(final Session session) throws JMSException {
            final Message message = session.createMessage();

            message.setJMSType(Stelsel.GBA.toString());
            message.setStringProperty(LeveringConstanten.JMS_PROPERTY_KEY_XML_BERICHT, uitgaandBericht);
            message.setStringProperty(LeveringConstanten.JMS_PROPERTY_KEY_BERICHT_GEGEVENS, maakBerichtgegevens());

            message.setStringProperty(
                LeveringConstanten.JMS_PROPERTY_PARTIJ_CODE,
                toegangLeveringsautorisatie.getGeautoriseerde().getPartij().getCode().getWaarde().toString());
            message.setStringProperty(
                LeveringConstanten.JMS_PROPERTY_LEVERINGS_AUTORISATIE_ID,
                toegangLeveringsautorisatie.getLeveringsautorisatie().getID().toString());

            if (toegangLeveringsautorisatie.getLeveringsautorisatie().getProtocolleringsniveau() != null) {
                message.setStringProperty(
                    LeveringConstanten.JMS_PROPERTY_PROTOCOLLERINGNIVEAU,
                    toegangLeveringsautorisatie.getLeveringsautorisatie().getProtocolleringsniveau().name());
            }
            return message;
        }

        /**
         * Maak berichtgegevens.
         */
        private String maakBerichtgegevens() {
            final DatumTijdAttribuut nu = new DatumTijdAttribuut(new Date());
            final Partij afnemer = toegangLeveringsautorisatie.getGeautoriseerde().getPartij();

            // Stuurgegevens
            final SynchronisatieBerichtGegevens synchronisatieBerichtGegevens =
                    new SynchronisatieBerichtGegevens(null, toegangLeveringsautorisatie.getID());
            synchronisatieBerichtGegevens.setStelsel(Stelsel.GBA);

            final ReferentienummerAttribuut referentienummer = new ReferentienummerAttribuut(opdracht.getReferentienummer());
            final BerichtStuurgegevensGroepModel stuurgegevens =
                    new BerichtStuurgegevensGroepModel(
                        null,
                        null,
                        afnemer.getID(),
                        new SysteemNaamAttribuut("GBA"),
                        referentienummer,
                        referentienummer,
                        nu,
                        null);

            synchronisatieBerichtGegevens.setStuurgegevens(stuurgegevens);

            // Geleverde formele periode
            synchronisatieBerichtGegevens.setDatumTijdAanvangFormelePeriodeResultaat(nu);
            synchronisatieBerichtGegevens.setDatumTijdEindeFormelePeriodeResultaat(nu);

            // Geleverde personen
            synchronisatieBerichtGegevens.setGeleverdePersoonsIds(Collections.singletonList(opdracht.getPersoonId()));

            // Soort levering
            synchronisatieBerichtGegevens.setDienstId(opdracht.getDienstId());
            synchronisatieBerichtGegevens.setSoortDienst(SoortDienst.PLAATSEN_AFNEMERINDICATIE);
            synchronisatieBerichtGegevens.setSoortSynchronisatie(new SoortSynchronisatieAttribuut(SoortSynchronisatie.VOLLEDIGBERICHT));

            return BERICHT_GEGEVENS_SERIALIZER.serialiseerNaarString(synchronisatieBerichtGegevens);
        }
    }

}

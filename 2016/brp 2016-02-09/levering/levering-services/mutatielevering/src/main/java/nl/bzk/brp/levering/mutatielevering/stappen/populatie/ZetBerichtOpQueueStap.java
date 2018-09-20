/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen.populatie;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import nl.bzk.brp.levering.algemeen.LeveringConstanten;
import nl.bzk.brp.levering.business.stappen.populatie.AbstractAfnemerVerwerkingStap;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieStappenOnderwerp;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieVerwerkingResultaat;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringsautorisatieVerwerkingContext;
import nl.bzk.brp.levering.mutatielevering.jmx.MutatieLeveringInfoBean;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Stelsel;
import nl.bzk.brp.model.bericht.ber.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.hisvolledig.autaut.PersoonAfnemerindicatieHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaat.HistorieVanafPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.internbericht.SynchronisatieBerichtGegevens;
import nl.bzk.brp.model.levering.SynchronisatieBericht;
import nl.bzk.brp.model.operationeel.ber.BerichtStuurgegevensGroepModel;
import nl.bzk.brp.serialisatie.JsonStringSerializer;
import org.perf4j.aop.Profiled;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;


/**
 * Deze klasse creeert het uitgaande (max) bericht dat naar de afnemers gaat.
 *
 * @brp.bedrijfsregel R1268
 * @brp.bedrijfsregel R1614
 * @brp.bedrijfsregel R1993
 */
@Regels({ Regel.R1993, Regel.R1268, Regel.R1614 })
public class ZetBerichtOpQueueStap extends AbstractAfnemerVerwerkingStap {

    /**
     * De Constante LOGGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    @Named("afnemersJmsTemplate")
    private JmsTemplate afnemersJmsTemplate;

    @Inject
    private MutatieLeveringInfoBean mutatieLeveringInfoBean;

    private final JsonStringSerializer<SynchronisatieBerichtGegevens> jsonStringSerialiseerder =
        new JsonStringSerializer<>(SynchronisatieBerichtGegevens.class);

    @Override
    @Profiled(tag = "ZetBerichtOpQueueStap", logFailuresSeparately = true, level = "DEBUG")
    public final boolean voerStapUit(final LeveringautorisatieStappenOnderwerp onderwerp,
        final LeveringsautorisatieVerwerkingContext context,
        final LeveringautorisatieVerwerkingResultaat resultaat)
    {
        zetBerichtenOpQueue(onderwerp, context);

        return DOORGAAN;
    }

    /**
     * Zet de levering berichten van de context op de queue.
     *
     * @param onderwerp Het onderwerp.
     * @param context   De context.
     * @brp.bedrijfsregel VR00045
     * @brp.bedrijfsregel VR00046
     * @brp.bedrijfsregel VR00047
     * @brp.bedrijfsregel VR00048
     */
    @Regels({ Regel.VR00045, Regel.VR00046, Regel.VR00047, Regel.VR00048, Regel.R1993 })
    private void zetBerichtenOpQueue(final LeveringautorisatieStappenOnderwerp onderwerp,
        final LeveringsautorisatieVerwerkingContext context)
    {
        final Stelsel stelsel = onderwerp.getLeveringinformatie().getToegangLeveringsautorisatie().getLeveringsautorisatie().getStelsel();
        final Long administratieveHandelingId = context.getAdministratieveHandeling().getID();

        for (int i = 0; i < context.getUitgaandePlatteTekstBerichten().size(); i++) {
            final String uitgaandeXmlLeveringBericht = context.getUitgaandePlatteTekstBerichten().get(i);
            final SynchronisatieBericht leveringBericht = context.getLeveringBerichten().get(i);
            final BerichtStuurgegevensGroepBericht stuurgegevensGroepBericht = leveringBericht.getStuurgegevens();

            final Partij afnemer = onderwerp.getLeveringinformatie().getToegangLeveringsautorisatie().getGeautoriseerde().getPartij();
            final String afnemerNaam = afnemer.getNaam().getWaarde();

            final List<Integer> persoonIdsInBericht = new ArrayList<>();
            for (final PersoonHisVolledigView persoonInBericht : leveringBericht.getAdministratieveHandeling().getBijgehoudenPersonen()) {
                persoonIdsInBericht.add(persoonInBericht.getID());
            }

            final String jsonString = maakBerichtGegevensJson(onderwerp, context, administratieveHandelingId, leveringBericht, stuurgegevensGroepBericht,
                persoonIdsInBericht);

            final MessageCreator jmsBerichtMetAfnemerXml = new MessageCreator() {

                @Override
                public final Message createMessage(final Session session) throws JMSException {
                    final Message message = session.createMessage();

                    message.setStringProperty(LeveringConstanten.JMS_PROPERTY_KEY_XML_BERICHT, uitgaandeXmlLeveringBericht);
                    message.setStringProperty(LeveringConstanten.JMS_PROPERTY_KEY_BERICHT_GEGEVENS, jsonString);

                    final ToegangLeveringsautorisatie toegangLeveringsautorisatie = onderwerp.getLeveringinformatie().getToegangLeveringsautorisatie();
                    final Leveringsautorisatie leveringsautorisatie = toegangLeveringsautorisatie.getLeveringsautorisatie();
                    message.setStringProperty(LeveringConstanten.JMS_PROPERTY_PARTIJ_CODE,
                        toegangLeveringsautorisatie.getGeautoriseerde().getPartij().getCode().getWaarde().toString());
                    message.setStringProperty(LeveringConstanten.JMS_PROPERTY_LEVERINGS_AUTORISATIE_ID, leveringsautorisatie.getID().toString());
                    if (leveringsautorisatie.getProtocolleringsniveau() != null) {
                        message.setStringProperty(LeveringConstanten.JMS_PROPERTY_PROTOCOLLERINGNIVEAU,
                            leveringsautorisatie.getProtocolleringsniveau().name());
                    }
                    if (stelsel == Stelsel.BRP) {
                        message.setStringProperty("brpAfleverURI", toegangLeveringsautorisatie.getAfleverpunt().getWaarde());
                    }

                    message.setJMSType(stelsel.getNaam());
                    LOGGER.info("Zet bericht op de queue voor afnemer {} en kanaal {}", afnemerNaam, stelsel);
                    return message;
                }

            };
            afnemersJmsTemplate.send(afnemer.getQueueNaam(), jmsBerichtMetAfnemerXml);
            mutatieLeveringInfoBean.incrementAfnemerBericht();
            LOGGER.info("Bericht op queue gezet met naam {}.", afnemer.getQueueNaam());
        }
    }

    /**
     * Maakt de berichtgegevens-json. Dit zijn de 'metagegevens' bij het bericht.
     *
     * @param onderwerp                  Het onderwerp van de stappen.
     * @param context                    De context van de stappen.
     * @param administratieveHandelingId Het id van de administratieve handeling.
     * @param leveringBericht            Het levering bericht.
     * @param stuurgegevensGroepBericht  De stuurgegevens.
     * @param persoonIdsInBericht        De persoon id's in het bericht.
     * @return De berichtgegevens als json string.
     */
    @Regels(Regel.R1268)
    private String maakBerichtGegevensJson(final LeveringautorisatieStappenOnderwerp onderwerp, final LeveringsautorisatieVerwerkingContext context,
        final Long administratieveHandelingId, final SynchronisatieBericht leveringBericht,
        final BerichtStuurgegevensGroepBericht stuurgegevensGroepBericht,
        final List<Integer> persoonIdsInBericht)
    {
        final ToegangLeveringsautorisatie toegangLeveringsautorisatie = onderwerp.getLeveringinformatie().getToegangLeveringsautorisatie();
        final Dienst dienst = onderwerp.getLeveringinformatie().getDienst();
        final DatumTijdAttribuut tsReg = context.getAdministratieveHandeling().getTijdstipRegistratie();

        final SynchronisatieBerichtGegevens synchronisatieBerichtGegevens = new SynchronisatieBerichtGegevens(administratieveHandelingId,
            toegangLeveringsautorisatie.getID());
        final BerichtStuurgegevensGroepModel berichtStuurgegevensGroepModel = new BerichtStuurgegevensGroepModel(stuurgegevensGroepBericht);
        if (stuurgegevensGroepBericht.getZendendePartij() != null) {
            berichtStuurgegevensGroepModel.setZendendePartijId(stuurgegevensGroepBericht.getZendendePartij().getWaarde().getID());
        }
        if (stuurgegevensGroepBericht.getOntvangendePartij() != null) {
            berichtStuurgegevensGroepModel.setOntvangendePartijId(stuurgegevensGroepBericht.getOntvangendePartij().getWaarde().getID());
        }
        synchronisatieBerichtGegevens.setStuurgegevens(berichtStuurgegevensGroepModel);
        synchronisatieBerichtGegevens.setGeleverdePersoonsIds(new ArrayList<>(persoonIdsInBericht));
        synchronisatieBerichtGegevens
            .setStelsel(onderwerp.getLeveringinformatie().getToegangLeveringsautorisatie().getLeveringsautorisatie().getStelsel());
        synchronisatieBerichtGegevens.setDienstId(dienst.getID());
        synchronisatieBerichtGegevens.setSoortSynchronisatie(new SoortSynchronisatieAttribuut(leveringBericht.geefSoortSynchronisatie()));
        final SoortDienst soortDienst = dienst.getSoort();
        synchronisatieBerichtGegevens.setAdministratieveHandelingTijdstipRegistratie(tsReg);
        synchronisatieBerichtGegevens.setSoortDienst(soortDienst);
        synchronisatieBerichtGegevens.setDatumTijdAanvangFormelePeriodeResultaat(tsReg);
        synchronisatieBerichtGegevens.setDatumTijdEindeFormelePeriodeResultaat(tsReg);

        final DatumEvtDeelsOnbekendAttribuut materieleResultaat = zoekMaterieelVanafMoment(leveringBericht, soortDienst, context,
            toegangLeveringsautorisatie);
        synchronisatieBerichtGegevens.setDatumAanvangMaterielePeriodeResultaat(materieleResultaat);

        return jsonStringSerialiseerder.serialiseerNaarString(synchronisatieBerichtGegevens);
    }

    /**
     * Voor volledigberichten zoek naar peilmomentMaterieelResultaat. Indien een levering n.a.v. plaatsing_afnemerindicatie, dan is er 1 persoon, anders
     * een 'regulier' volledigbericht met mogelijk meerdere personen. Indien meer personen dan zullen allen hetzelfde momentVanaf hebben.
     *
     * @param leveringBericht een leveringbericht
     * @param soortDienst     de soort dienst
     * @return materieel vanaf moment indien beschikbaar
     */
    private DatumEvtDeelsOnbekendAttribuut zoekMaterieelVanafMoment(final SynchronisatieBericht leveringBericht, final SoortDienst soortDienst,
        final LeveringsautorisatieVerwerkingContext context, final ToegangLeveringsautorisatie toegangLeveringsautorisatie)
    {
        DatumEvtDeelsOnbekendAttribuut materieelVanafMoment = null;
        if (SoortSynchronisatie.VOLLEDIGBERICHT.equals(leveringBericht.geefSoortSynchronisatie())) {
            final PersoonHisVolledigView view = context.getBijgehoudenPersoonViews().get(0);
            final HistorieVanafPredikaat historieVanafPredikaat = view.getPredikaatVanType(HistorieVanafPredikaat.class);
            if (historieVanafPredikaat != null && historieVanafPredikaat.getLeverenVanafMoment() != null) {
                materieelVanafMoment = new DatumEvtDeelsOnbekendAttribuut(historieVanafPredikaat.getLeverenVanafMoment());
            }
        } else if (SoortSynchronisatie.MUTATIEBERICHT.equals(leveringBericht.geefSoortSynchronisatie())
            && SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE.equals(soortDienst))
        {
            final PersoonHisVolledigView view = context.getBijgehoudenPersoonViews().get(0);

            for (final PersoonAfnemerindicatieHisVolledig persoonAfnemerindicatie : view.getAfnemerindicaties()) {
                final Integer leveringsautorisatieId = toegangLeveringsautorisatie.getLeveringsautorisatie().getID();
                if (leveringsautorisatieId.equals(persoonAfnemerindicatie.getLeveringsautorisatie().getWaarde().getID())
                    && persoonAfnemerindicatie.getPersoonAfnemerindicatieHistorie().getActueleRecord() != null
                    && persoonAfnemerindicatie.getPersoonAfnemerindicatieHistorie().getActueleRecord().getDatumAanvangMaterielePeriode()
                    != null)
                {
                    materieelVanafMoment =
                        persoonAfnemerindicatie.getPersoonAfnemerindicatieHistorie().getActueleRecord().getDatumAanvangMaterielePeriode();
                    break;
                }
            }
        }
        return materieelVanafMoment;
    }
}

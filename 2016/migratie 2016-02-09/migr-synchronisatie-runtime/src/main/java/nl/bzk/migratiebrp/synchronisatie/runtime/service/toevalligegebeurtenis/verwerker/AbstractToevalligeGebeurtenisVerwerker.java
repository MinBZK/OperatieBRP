/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.verwerker;

import java.util.Calendar;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import nl.bzk.migratiebrp.bericht.model.JMSConstants;
import nl.bzk.migratiebrp.bericht.model.brp.generated.AdellijkeTitelCode;
import nl.bzk.migratiebrp.bericht.model.brp.generated.Administratienummer;
import nl.bzk.migratiebrp.bericht.model.brp.generated.Aktenummer;
import nl.bzk.migratiebrp.bericht.model.brp.generated.BuitenlandsePlaats;
import nl.bzk.migratiebrp.bericht.model.brp.generated.Burgerservicenummer;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ContainerActieBronnen;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ContainerAdministratieveHandelingBronnen;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ContainerRelatieBetrokkenheden;
import nl.bzk.migratiebrp.bericht.model.brp.generated.DatumMetOnzekerheid;
import nl.bzk.migratiebrp.bericht.model.brp.generated.DatumTijd;
import nl.bzk.migratiebrp.bericht.model.brp.generated.GemeenteCode;
import nl.bzk.migratiebrp.bericht.model.brp.generated.GeslachtsaanduidingCode;
import nl.bzk.migratiebrp.bericht.model.brp.generated.GeslachtsaanduidingCodeS;
import nl.bzk.migratiebrp.bericht.model.brp.generated.Geslachtsnaamstam;
import nl.bzk.migratiebrp.bericht.model.brp.generated.GroepBerichtParameters;
import nl.bzk.migratiebrp.bericht.model.brp.generated.GroepBerichtStuurgegevens;
import nl.bzk.migratiebrp.bericht.model.brp.generated.GroepPersoonGeboorte;
import nl.bzk.migratiebrp.bericht.model.brp.generated.GroepPersoonGeslachtsaanduiding;
import nl.bzk.migratiebrp.bericht.model.brp.generated.GroepPersoonIdentificatienummers;
import nl.bzk.migratiebrp.bericht.model.brp.generated.GroepPersoonSamengesteldeNaam;
import nl.bzk.migratiebrp.bericht.model.brp.generated.GroepRelatieRelatie;
import nl.bzk.migratiebrp.bericht.model.brp.generated.JaNee;
import nl.bzk.migratiebrp.bericht.model.brp.generated.JaNeeS;
import nl.bzk.migratiebrp.bericht.model.brp.generated.LandGebiedCode;
import nl.bzk.migratiebrp.bericht.model.brp.generated.MigratievoorzieningRegistreerHuwelijkGeregistreerdPartnerschapBijhouding;
import nl.bzk.migratiebrp.bericht.model.brp.generated.MigratievoorzieningRegistreerNaamGeslachtBijhouding;
import nl.bzk.migratiebrp.bericht.model.brp.generated.MigratievoorzieningRegistreerOverlijdenBijhouding;
import nl.bzk.migratiebrp.bericht.model.brp.generated.NaamEnumeratiewaarde;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjectFactory;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypeActie;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypeActieBron;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypeAdministratieveHandelingBron;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypeBerichtBijhouding;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypeDocument;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypeFamilierechtelijkeBetrekking;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypeGeregistreerdPartnerschap;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypeHuwelijk;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypePersoon;
import nl.bzk.migratiebrp.bericht.model.brp.generated.PartijCode;
import nl.bzk.migratiebrp.bericht.model.brp.generated.PredicaatCode;
import nl.bzk.migratiebrp.bericht.model.brp.generated.Referentienummer;
import nl.bzk.migratiebrp.bericht.model.brp.generated.Sleutelwaardetekst;
import nl.bzk.migratiebrp.bericht.model.brp.generated.SysteemNaam;
import nl.bzk.migratiebrp.bericht.model.brp.generated.VerwerkingswijzeNaam;
import nl.bzk.migratiebrp.bericht.model.brp.generated.VerwerkingswijzeNaamS;
import nl.bzk.migratiebrp.bericht.model.brp.generated.Voornamen;
import nl.bzk.migratiebrp.bericht.model.brp.generated.Voorvoegsel;
import nl.bzk.migratiebrp.bericht.model.brp.xml.BrpXml;
import nl.bzk.migratiebrp.bericht.model.sync.generated.NaamGroepType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.PersoonType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkToevalligeGebeurtenisVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAdellijkeTitelCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPredicaatCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.Lo3AttribuutConverteerder;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.ToevalligeGebeurtenisVerwerker;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * Klasse met overkoepelende functionaliteit voor het verwerken van toevallige gebeurtenis berichten.
 */
public abstract class AbstractToevalligeGebeurtenisVerwerker implements ToevalligeGebeurtenisVerwerker {

    /**
     *
     * Constantes die ook in de specifieke implementatieklassen worden gebruikt.
     *
     */

    /**
     * Lengte van de gemeente code.
     */
    protected static final int LENGTE_GEMEENTE_CODE = 4;

    /**
     * Identifier voor het persoon technisch Id.
     */
    protected static final String OBJECT_TYPE_PERSOON_TECHNISCH_ID = "PersoonTechnischId";

    /**
     * Objectfactory voor het aanmaken van JAXB elementen voor de BRP opdracht.
     */
    protected static final ObjectFactory OBJECT_FACTORY = new ObjectFactory();

    /**
     * Identifier voor actie.
     */
    protected static final String OBJECT_TYPE_ADMINISTRATIEVE_HANDELING = "AdministratieveHandeling";

    /**
     * Identifier voor relatie.
     */
    protected static final String OBJECT_TYPE_PERSOON = "Persoon";

    /**
     * Identifier voor relatie.
     */
    protected static final String OBJECT_TYPE_RELATIE = "Relatie";

    /**
     * Identifier voor relatie.
     */
    protected static final String OBJECT_TYPE_BETROKKENHEID = "Betrokkenheid";

    /**
     * Identifier voor actie.
     */
    protected static final String OBJECT_TYPE_ACTIE = "Actie";

    /**
     * Identifier voor relatie.
     */
    protected static final String OBJECT_TYPE_DOCUMENT = "Document";

    /**
     * Identifier voor actie bron.
     */
    protected static final String OBJECT_TYPE_ACTIE_BRON = "ActieBron";

    /**
     * Communicatie identifier voor bron.
     */
    protected static final String COMMUNICATIE_TYPE_BRON = "Bron";

    /**
     * Communicatie identifier voor identificatie.
     */
    protected static final String COMMUNICATIE_TYPE_IDENTIFICATIE = "Identificatie";

    private static final String ZENDENDE_PARTIJ_ISC = "199902";
    private static final String ZENDENDE_SYSTEEM_ISC = "ISC";
    private static final Logger LOG = LoggerFactory.getLogger();
    private static final String DATUM_FORMAT = "yyyy-MM-dd'T'hh:mm:ss.SSSZ";
    private static final FastDateFormat DATUM_FORMAAT = FastDateFormat.getInstance(DATUM_FORMAT);

    /**
     * Volgnummer voor bron communicatieId.
     */
    private int bronIdVolgnummer;

    /**
     * Volgnummer voor bron identificatieId.
     */
    private int identificatieIdVolgnummer;

    @Inject
    @Named(value = "brpQueueJmsTemplate")
    private JmsTemplate jmsTemplate;

    @Inject
    @Named(value = "gbaToevalligeGebeurtenissen")
    private Destination destination;

    @Inject
    private Lo3AttribuutConverteerder converteerder;

    @Override
    public final boolean verwerk(final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek, final Persoon rootPersoon) {

        // Zet de initiele waarden voor de volgnummers.
        bronIdVolgnummer = 1;
        identificatieIdVolgnummer = 1;

        // Maak opdracht o.b.v. verzoek.
        final ObjecttypeBerichtBijhouding opdracht = maakBrpToevalligeGebeurtenisOpdracht(verzoek, rootPersoon);

        // Verzend de bijhouding opdracht naar BRP.
        verstuurBrpToevalligeGebeurtenisOpdracht(opdracht, verzoek.getMessageId());

        return true;
    }

    /**
     * Hook methode voor specifieke generatie van de BRP toevallige gebeurtenis opdracht.
     *
     * @param verzoek
     *            Het verzoek op basis waarvan de opdracht wordt opgesteld.
     * @param rootPersoon
     *            De persoon waar vanuit de bijhouding plaatsvindt.
     * @return De BRP opdracht.
     */
    protected abstract ObjecttypeBerichtBijhouding maakBrpToevalligeGebeurtenisOpdracht(
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek,
        final Persoon rootPersoon);

    /**
     * Verstuurt een BRP toevallige gebeurtenis opdracht naar de betreffende destination.
     *
     * @param opdracht
     *            De te versturen opdracht.
     * @param verzoekBerichtId
     *            Het messageId van het binnenkomende verzoek.
     */
    private void verstuurBrpToevalligeGebeurtenisOpdracht(final ObjecttypeBerichtBijhouding opdracht, final String verzoekBerichtId) {
        LOG.debug("Toevallige gebeurtenis opdracht wordt verstuurd.");
        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(final Session session) throws JMSException {
                LOG.info("Code niet gevuld? " + (opdracht.getGBASluitingHuwelijkGeregistreerdPartnerschap().getValue().getCode() == null));
                String berichtTekst;
                if (opdracht instanceof MigratievoorzieningRegistreerNaamGeslachtBijhouding) {
                    berichtTekst =
                            BrpXml.SINGLETON.elementToString(OBJECT_FACTORY.createIscMigRegistreerNaamGeslacht((MigratievoorzieningRegistreerNaamGeslachtBijhouding) opdracht));
                } else if (opdracht instanceof MigratievoorzieningRegistreerHuwelijkGeregistreerdPartnerschapBijhouding) {
                    berichtTekst =
                            BrpXml.SINGLETON.elementToString(OBJECT_FACTORY.createIscMigRegistreerHuwelijkGeregistreerdPartnerschap((MigratievoorzieningRegistreerHuwelijkGeregistreerdPartnerschapBijhouding) opdracht));
                } else if (opdracht instanceof MigratievoorzieningRegistreerOverlijdenBijhouding) {
                    berichtTekst =
                            BrpXml.SINGLETON.elementToString(OBJECT_FACTORY.createIscMigRegistreerOverlijden((MigratievoorzieningRegistreerOverlijdenBijhouding) opdracht));
                } else {
                    throw new IllegalStateException("Bericht wordt nog niet ondersteund " + (opdracht.getClass().getName()));
                }

                LOG.info("Bericht: " + berichtTekst);
                final Message message = session.createTextMessage(berichtTekst);
                LOG.info("Message: " + message);
                message.setStringProperty(JMSConstants.BERICHT_REFERENTIE, verzoekBerichtId);
                return message;
            }
        });
    }

    /**
     * Maak de bericht stuurgegevens voor de opdracht o.b.v. het verzoek. Deze zijn gelijk voor alle berichten.
     *
     * @param verzoek
     *            Het verzoek waarop de stuurgegevens worden gebaseerd.
     * @return De bericht stuurgegevens.
     */
    protected final GroepBerichtStuurgegevens maakBerichtStuurgegevens(final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek) {
        final GroepBerichtStuurgegevens stuurgegevens = new GroepBerichtStuurgegevens();
        stuurgegevens.setCommunicatieID(COMMUNICATIE_TYPE_IDENTIFICATIE + getIdentificatieIdVolgnummerEnVerhoogMetEen());

        final Referentienummer referentienummer = new Referentienummer();
        referentienummer.setValue(UUID.randomUUID().toString());
        stuurgegevens.setReferentienummer(OBJECT_FACTORY.createGroepBerichtStuurgegevensReferentienummer(referentienummer));

        final PartijCode zendendePartij = new PartijCode();
        zendendePartij.setValue(ZENDENDE_PARTIJ_ISC);
        stuurgegevens.setZendendePartij(OBJECT_FACTORY.createGroepBerichtStuurgegevensZendendePartij(zendendePartij));

        final SysteemNaam zendendeSysteem = new SysteemNaam();
        zendendeSysteem.setValue(ZENDENDE_SYSTEEM_ISC);
        stuurgegevens.setZendendeSysteem(OBJECT_FACTORY.createGroepBerichtStuurgegevensZendendeSysteem(zendendeSysteem));

        final DatumTijd tijdstipVerzending = new DatumTijd();
        tijdstipVerzending.setValue(DATUM_FORMAAT.format(Calendar.getInstance().getTime()));
        stuurgegevens.setTijdstipVerzending(OBJECT_FACTORY.createGroepBerichtStuurgegevensTijdstipVerzending(tijdstipVerzending));
        return stuurgegevens;
    }

    /**
     * Maak de bericht parameters voor de opdracht o.b.v. het verzoek. Deze zijn gelijk voor alle berichten.
     *
     * @param rootPersoon
     *            De persoon uit de BRP database.
     * @return De bericht parameters.
     */
    protected final GroepBerichtParameters maakBerichtParameters(final Persoon rootPersoon) {
        final GroepBerichtParameters groepBerichtParameters = new GroepBerichtParameters();
        final VerwerkingswijzeNaam verwerkingswijzeNaam = new VerwerkingswijzeNaam();
        verwerkingswijzeNaam.setValue(VerwerkingswijzeNaamS.BIJHOUDING);
        groepBerichtParameters.setVerwerkingswijze(OBJECT_FACTORY.createGroepBerichtParametersVerwerkingswijze(verwerkingswijzeNaam));
        groepBerichtParameters.setCommunicatieID(COMMUNICATIE_TYPE_IDENTIFICATIE + getIdentificatieIdVolgnummerEnVerhoogMetEen());

        if (rootPersoon != null) {
            final Sleutelwaardetekst sleutelwaardeTekst = new Sleutelwaardetekst();
            sleutelwaardeTekst.setValue(String.valueOf(rootPersoon.getId()));
            groepBerichtParameters.setBezienVanuitPersoon(OBJECT_FACTORY.createGroepBerichtParametersBezienVanuitPersoon(sleutelwaardeTekst));
        }

        return groepBerichtParameters;
    }

    /**
     * Maak de bericht administratieve handeling bronnen voor de opdracht o.b.v. het verzoek.
     *
     * @param verzoek
     *            Het verzoek waarop de administratieve handeling bronnen worden gebaseerd.
     * @return De bericht administratieve handeling bronnen.
     */
    protected final ContainerAdministratieveHandelingBronnen maakAdministratieveHandelingBronnen(
            final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek)
    {
        final ContainerAdministratieveHandelingBronnen administratievehandelingBronnen = new ContainerAdministratieveHandelingBronnen();
        final ObjecttypeAdministratieveHandelingBron administratievehandelingBron = new ObjecttypeAdministratieveHandelingBron();
        administratievehandelingBron.setObjecttype(OBJECT_TYPE_ACTIE_BRON);
        administratievehandelingBron.setCommunicatieID(COMMUNICATIE_TYPE_BRON + getBronIdVolgnummerEnVerhoogMetEen());

        final ObjecttypeDocument document = new ObjecttypeDocument();
        final Aktenummer aktenummer = new Aktenummer();
        aktenummer.setValue(verzoek.getAkte().getAktenummer());
        document.setAktenummer(OBJECT_FACTORY.createObjecttypeDocumentAktenummer(aktenummer));
        final NaamEnumeratiewaarde naamEnumeratiewaarde = new NaamEnumeratiewaarde();
        naamEnumeratiewaarde.setValue("Historie conversie");
        document.setSoortNaam(OBJECT_FACTORY.createObjecttypeDocumentSoortNaam(naamEnumeratiewaarde));
        document.setObjecttype(OBJECT_TYPE_DOCUMENT);
        document.setCommunicatieID(COMMUNICATIE_TYPE_IDENTIFICATIE + getIdentificatieIdVolgnummerEnVerhoogMetEen());
        document.setPartijCode(OBJECT_FACTORY.createObjecttypeDocumentPartijCode(maakHandelingRegisterGemeente(verzoek)));
        administratievehandelingBron.setDocument(OBJECT_FACTORY.createObjecttypeAdministratieveHandelingBronDocument(document));
        administratievehandelingBronnen.getBron().add(administratievehandelingBron);
        return administratievehandelingBronnen;
    }

    /**
     * Maakt een actie aan op basis van de gegevens uit het verzoek en het soort relatie.
     *
     * @param actie
     *            De actie die gevuld dient te worden.
     * @param datumAanvangGeldigheidVerzoek
     *            De aanvangsdatum waarop de actie geldig wordt.
     * @param actieBron
     *            De bron van de actie.
     * @param relatie
     *            De relatie waarop de actie betrekking heeft.
     * @param betrokkenheden
     *            De betrokkenheden binnen de actie.
     * @param soortVerbintenis
     *            Het soort verbintenis.
     */
    protected final void vulActie(
        final ObjecttypeActie actie,
        final int datumAanvangGeldigheidVerzoek,
        final ObjecttypeActieBron actieBron,
        final GroepRelatieRelatie relatie,
        final ContainerRelatieBetrokkenheden betrokkenheden,
        final BrpSoortRelatieCode soortVerbintenis)
    {
        actie.setObjecttype(OBJECT_TYPE_ACTIE);
        actieBron.setObjecttype(OBJECT_TYPE_ACTIE_BRON);

        final BrpDatum brpDatumAanvangGeldigheid = converteerder.converteerDatum(new Lo3Datum(datumAanvangGeldigheidVerzoek));
        final DatumMetOnzekerheid datumAanvangGeldigheid = new DatumMetOnzekerheid();
        datumAanvangGeldigheid.setValue(String.valueOf(brpDatumAanvangGeldigheid.getWaarde()));
        actie.setDatumAanvangGeldigheid(OBJECT_FACTORY.createObjecttypeActieDatumAanvangGeldigheid(datumAanvangGeldigheid));

        final ContainerActieBronnen actieBronnen = new ContainerActieBronnen();
        actieBronnen.getBron().add(actieBron);
        actie.setBronnen(OBJECT_FACTORY.createObjecttypeActieBronnen(actieBronnen));

        if (BrpSoortRelatieCode.HUWELIJK.equals(soortVerbintenis)) {
            // Huwelijk
            final ObjecttypeHuwelijk huwelijk = new ObjecttypeHuwelijk();
            huwelijk.setObjecttype(OBJECT_TYPE_RELATIE);
            huwelijk.setCommunicatieID(COMMUNICATIE_TYPE_IDENTIFICATIE + getIdentificatieIdVolgnummerEnVerhoogMetEen());
            huwelijk.getRelatie().add(relatie);
            huwelijk.setBetrokkenheden(OBJECT_FACTORY.createObjecttypeRelatieBetrokkenheden(betrokkenheden));
            actie.setHuwelijk(OBJECT_FACTORY.createObjecttypeActieHuwelijk(huwelijk));

        } else if (BrpSoortRelatieCode.GEREGISTREERD_PARTNERSCHAP.equals(soortVerbintenis)) {
            // Geregistreerd partnerschap
            final ObjecttypeGeregistreerdPartnerschap geregistreerdPartnerschap = new ObjecttypeGeregistreerdPartnerschap();
            geregistreerdPartnerschap.setObjecttype(OBJECT_TYPE_RELATIE);
            geregistreerdPartnerschap.setCommunicatieID(COMMUNICATIE_TYPE_IDENTIFICATIE + getIdentificatieIdVolgnummerEnVerhoogMetEen());
            geregistreerdPartnerschap.getRelatie().add(relatie);
            geregistreerdPartnerschap.setBetrokkenheden(OBJECT_FACTORY.createObjecttypeRelatieBetrokkenheden(betrokkenheden));
            actie.setGeregistreerdPartnerschap(OBJECT_FACTORY.createObjecttypeActieGeregistreerdPartnerschap(geregistreerdPartnerschap));
        } else if (BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING.equals(soortVerbintenis)) {
            // Familierechtelijke betrekking
            final ObjecttypeFamilierechtelijkeBetrekking familieRechtelijkeBetrekking = new ObjecttypeFamilierechtelijkeBetrekking();
            familieRechtelijkeBetrekking.setObjecttype(OBJECT_TYPE_RELATIE);
            familieRechtelijkeBetrekking.setCommunicatieID(COMMUNICATIE_TYPE_IDENTIFICATIE + getIdentificatieIdVolgnummerEnVerhoogMetEen());
            familieRechtelijkeBetrekking.getRelatie().add(relatie);
            familieRechtelijkeBetrekking.setBetrokkenheden(OBJECT_FACTORY.createObjecttypeRelatieBetrokkenheden(betrokkenheden));
            actie.setFamilierechtelijkeBetrekking(OBJECT_FACTORY.createObjecttypeActieFamilierechtelijkeBetrekking(familieRechtelijkeBetrekking));
        }

    }

    /**
     * Maak op basis van de gevonden BRP persoon een persoon voor de opdracht.
     *
     * @param gevondenPersoon
     *            De in BRP gevonden persoon.
     * @return De persoon voor de opdracht.
     */
    protected final ObjecttypePersoon maakPersoon(final Persoon gevondenPersoon) {
        final ObjecttypePersoon persoon = new ObjecttypePersoon();
        persoon.setObjecttype(OBJECT_TYPE_PERSOON);
        persoon.setCommunicatieID(COMMUNICATIE_TYPE_IDENTIFICATIE + getIdentificatieIdVolgnummerEnVerhoogMetEen());

        // Groep identificatienummers
        persoon.getIdentificatienummers().add(
            maakPersoonIdentificatienummers(
                String.valueOf(gevondenPersoon.getAdministratienummer()),
                String.valueOf(gevondenPersoon.getBurgerservicenummer())));

        // Groep samengestelde naam
        persoon.getSamengesteldeNaam().add(
            maakPersoonSamengesteldeNaam(
                gevondenPersoon.getVoornamen(),
                gevondenPersoon.getAdellijkeTitel() != null ? gevondenPersoon.getAdellijkeTitel().getCode() : null,
                gevondenPersoon.getPredicaat() != null ? gevondenPersoon.getPredicaat().getCode() : null,
                gevondenPersoon.getVoorvoegsel(),
                gevondenPersoon.getGeslachtsnaamstam()));

        // Groep geboorte
        persoon.getGeboorte().add(
            maakPersoonGeboorte(
                String.valueOf(gevondenPersoon.getDatumGeboorte()),
                gevondenPersoon.getBuitenlandsePlaatsGeboorte(),
                String.valueOf(gevondenPersoon.getGemeenteGeboorte() != null ? gevondenPersoon.getGemeenteGeboorte().getCode() : null),
                String.valueOf(gevondenPersoon.getLandOfGebiedGeboorte() != null ? gevondenPersoon.getLandOfGebiedGeboorte().getCode() : null)));

        // Groep geslachtsaanduiding
        if (gevondenPersoon.getGeslachtsaanduiding() != null) {
            persoon.getGeslachtsaanduiding().add(maakPersoonGeslachtsaanduiding(gevondenPersoon.getGeslachtsaanduiding().getCode()));
        }

        return persoon;
    }

    /**
     * Maak op basis van de persoon uit het verzoek een persoon voor de opdracht.
     *
     * @param persoonType
     *            De in persoon uit het verzoek.
     * @return De persoon voor de opdracht.
     */
    protected final ObjecttypePersoon maakPersoon(final PersoonType persoonType) {
        final ObjecttypePersoon persoon = new ObjecttypePersoon();
        persoon.setObjecttype(OBJECT_TYPE_PERSOON);
        persoon.setCommunicatieID(COMMUNICATIE_TYPE_IDENTIFICATIE + getIdentificatieIdVolgnummerEnVerhoogMetEen());

        // Groep identificatienummers
        persoon.getIdentificatienummers().add(
            maakPersoonIdentificatienummers(persoonType.getIdentificatienummers().getANummer(), persoonType.getIdentificatienummers()
                                                                                                           .getBurgerservicenummer()));

        // Groep samengestelde naam
        BrpAdellijkeTitelCode adellijkeTitel = null;
        BrpPredicaatCode predicaat = null;
        if (persoonType.getNaam().getAdellijkeTitelPredicaat() != null) {
            adellijkeTitel =
                    converteerder.converteerLo3AdellijkeTitelPredikaatCodeNaarBrpAdellijkeTitelCode(new Lo3AdellijkeTitelPredikaatCode(
                        persoonType.getNaam().getAdellijkeTitelPredicaat().name()));
            predicaat =
                    converteerder.converteerLo3AdellijkeTitelPredikaatCodeNaarBrpPredicaatCode(new Lo3AdellijkeTitelPredikaatCode(
                        persoonType.getNaam().getAdellijkeTitelPredicaat().name()));
        }
        persoon.getSamengesteldeNaam().add(
            maakPersoonSamengesteldeNaam(
                persoonType.getNaam().getVoornamen(),
                adellijkeTitel == null ? null : adellijkeTitel.getWaarde(),
                predicaat == null ? null : predicaat.getWaarde(),
                persoonType.getNaam().getVoorvoegsel(),
                persoonType.getNaam().getGeslachtsnaam()));

        // Groep geboorte
        if (persoonType.getGeboorte().getPlaats().length() == LENGTE_GEMEENTE_CODE && StringUtils.isNumeric(persoonType.getGeboorte().getPlaats())) {
            persoon.getGeboorte().add(
                maakPersoonGeboorte(
                    String.valueOf(persoonType.getGeboorte().getDatum()),
                    null,
                    persoonType.getGeboorte().getPlaats(),
                    persoonType.getGeboorte().getLand()));
        } else {
            persoon.getGeboorte().add(
                maakPersoonGeboorte(
                    String.valueOf(persoonType.getGeboorte().getDatum()),
                    persoonType.getGeboorte().getPlaats(),
                    null,
                    persoonType.getGeboorte().getLand()));
        }

        // Groep geslachtsaanduiding
        final BrpGeslachtsaanduidingCode geslachtsaanduidingCode =
                converteerder.converteerLo3Geslachtsaanduiding(new Lo3Geslachtsaanduiding(persoonType.getGeslacht().getGeslachtsaanduiding().value()));
        persoon.getGeslachtsaanduiding().add(maakPersoonGeslachtsaanduiding(geslachtsaanduidingCode.getWaarde()));

        return persoon;
    }

    /**
     * Maakt een persoon samengestelde naam groep voor het BRP bericht.
     *
     * @param naam
     *            De naam groep uit het verzoek.
     * @return de samengestelde naam groep voor het BRP bericht.
     */
    protected final GroepPersoonSamengesteldeNaam maakPersoonSamengesteldeNaam(final NaamGroepType naam) {

        final BrpAdellijkeTitelCode adellijkeTitelVerzoek;
        final BrpPredicaatCode predicaatVerzoek;
        if (naam.getAdellijkeTitelPredicaat() != null) {

            adellijkeTitelVerzoek =
                    converteerder.converteerLo3AdellijkeTitelPredikaatCodeNaarBrpAdellijkeTitelCode(new Lo3AdellijkeTitelPredikaatCode(
                        naam.getAdellijkeTitelPredicaat().value()));
            predicaatVerzoek =
                    converteerder.converteerLo3AdellijkeTitelPredikaatCodeNaarBrpPredicaatCode(new Lo3AdellijkeTitelPredikaatCode(
                        naam.getAdellijkeTitelPredicaat().value()));

        } else {
            adellijkeTitelVerzoek = null;
            predicaatVerzoek = null;
        }

        return maakPersoonSamengesteldeNaam(
            naam.getVoornamen(),
            adellijkeTitelVerzoek != null ? adellijkeTitelVerzoek.getWaarde() : null,
            predicaatVerzoek != null ? predicaatVerzoek.getWaarde() : null,
            naam.getVoorvoegsel(),
            naam.getGeslachtsnaam());
    }

    /**
     * Maakt een persoon samengestelde naam groep voor het BRP bericht.
     *
     * @param voornamenVerzoek
     *            De voornamen uit het verzoek.
     * @param adellijkeTitelVerzoek
     *            De adellijke titel uit het verzoek.
     * @param predicaatVerzoek
     *            Het predicaat uit het verzoek.
     * @param voorvoegselVerzoek
     *            De voorvoegsels uit het verzoek.
     * @param geslachtsnaamstamVerzoek
     *            De geslachtsnaamstam uit het verzoek.
     * @return de samengestelde naam groep voor het BRP bericht.
     */
    private GroepPersoonSamengesteldeNaam maakPersoonSamengesteldeNaam(
        final String voornamenVerzoek,
        final String adellijkeTitelVerzoek,
        final String predicaatVerzoek,
        final String voorvoegselVerzoek,
        final String geslachtsnaamstamVerzoek)
    {

        final GroepPersoonSamengesteldeNaam samengesteldeNaam = new GroepPersoonSamengesteldeNaam();
        samengesteldeNaam.setCommunicatieID(COMMUNICATIE_TYPE_IDENTIFICATIE + getIdentificatieIdVolgnummerEnVerhoogMetEen());

        final JaNee indicatieNamenReeks = new JaNee();
        indicatieNamenReeks.setValue(JaNeeS.N);
        samengesteldeNaam.setIndicatieNamenreeks(OBJECT_FACTORY.createGroepPersoonSamengesteldeNaamIndicatieNamenreeks(indicatieNamenReeks));

        if (voornamenVerzoek != null) {
            final Voornamen voornamen = new Voornamen();
            voornamen.setValue(voornamenVerzoek);
            samengesteldeNaam.setVoornamen(OBJECT_FACTORY.createGroepPersoonSamengesteldeNaamVoornamen(voornamen));
        }

        if (adellijkeTitelVerzoek != null) {
            final AdellijkeTitelCode adellijkeTitel = new AdellijkeTitelCode();
            adellijkeTitel.setValue(adellijkeTitelVerzoek);
            samengesteldeNaam.setAdellijkeTitelCode(OBJECT_FACTORY.createGroepPersoonSamengesteldeNaamAdellijkeTitelCode(adellijkeTitel));
        }

        if (predicaatVerzoek != null) {
            final PredicaatCode predicaat = new PredicaatCode();
            predicaat.setValue(predicaatVerzoek);
            samengesteldeNaam.setPredicaatCode(OBJECT_FACTORY.createGroepPersoonSamengesteldeNaamPredicaatCode(predicaat));
        }

        if (voorvoegselVerzoek != null) {
            final Voorvoegsel voorvoegsel = new Voorvoegsel();
            voorvoegsel.setValue(voorvoegselVerzoek);
            samengesteldeNaam.setVoorvoegsel(OBJECT_FACTORY.createGroepPersoonSamengesteldeNaamVoorvoegsel(voorvoegsel));
        }

        final Geslachtsnaamstam geslachtsnaamstam = new Geslachtsnaamstam();
        geslachtsnaamstam.setValue(geslachtsnaamstamVerzoek);
        samengesteldeNaam.setGeslachtsnaamstam(OBJECT_FACTORY.createGroepPersoonSamengesteldeNaamGeslachtsnaamstam(geslachtsnaamstam));
        return samengesteldeNaam;
    }

    private GroepPersoonGeboorte maakPersoonGeboorte(
        final String geboortedatum,
        final String geboorteplaatsBuitenland,
        final String geboorteplaats,
        final String landOfGebied)
    {
        final GroepPersoonGeboorte geboorte = new GroepPersoonGeboorte();
        geboorte.setCommunicatieID(COMMUNICATIE_TYPE_IDENTIFICATIE + getIdentificatieIdVolgnummerEnVerhoogMetEen());

        final DatumMetOnzekerheid datumGeboorte = new DatumMetOnzekerheid();
        datumGeboorte.setValue(geboortedatum);
        geboorte.setDatum(OBJECT_FACTORY.createGroepPersoonGeboorteDatum(datumGeboorte));

        if (geboorteplaatsBuitenland != null) {
            final BuitenlandsePlaats buitenlandsePlaats = new BuitenlandsePlaats();
            buitenlandsePlaats.setValue(geboorteplaatsBuitenland);
            geboorte.setBuitenlandsePlaats(OBJECT_FACTORY.createGroepPersoonGeboorteBuitenlandsePlaats(buitenlandsePlaats));
        }

        if (geboorteplaats != null) {
            final GemeenteCode gemeente = new GemeenteCode();
            gemeente.setValue(geboorteplaats);
            geboorte.setGemeenteCode(OBJECT_FACTORY.createGroepPersoonGeboorteGemeenteCode(gemeente));
        }

        final LandGebiedCode land = new LandGebiedCode();
        land.setValue(landOfGebied);
        geboorte.setLandGebiedCode(OBJECT_FACTORY.createGroepPersoonGeboorteLandGebiedCode(land));

        return geboorte;
    }

    private GroepPersoonIdentificatienummers maakPersoonIdentificatienummers(final String anr, final String bsn) {
        final GroepPersoonIdentificatienummers identificatienummers = new GroepPersoonIdentificatienummers();
        identificatienummers.setCommunicatieID(COMMUNICATIE_TYPE_IDENTIFICATIE + getIdentificatieIdVolgnummerEnVerhoogMetEen());
        final Administratienummer aNummer = new Administratienummer();
        aNummer.setValue(anr);
        identificatienummers.setAdministratienummer(OBJECT_FACTORY.createGroepPersoonIdentificatienummersAdministratienummer(aNummer));

        if (bsn != null) {
            final Burgerservicenummer burgerServiceNummer = new Burgerservicenummer();
            burgerServiceNummer.setValue(bsn);
            identificatienummers.setBurgerservicenummer(OBJECT_FACTORY.createGroepPersoonIdentificatienummersBurgerservicenummer(burgerServiceNummer));
        }

        return identificatienummers;
    }

    /**
     * Maakt een persoon geslachtsaanduiding groep voor het BRP bericht.
     *
     * @param geslachtsaanduidingVerzoek
     *            De geslachtsaanduiding uit het verzoek.
     * @return de geslachtsaanduiding groep voor het BRP bericht.
     */
    protected final GroepPersoonGeslachtsaanduiding maakPersoonGeslachtsaanduiding(final String geslachtsaanduidingVerzoek) {

        final GroepPersoonGeslachtsaanduiding geslachtsaanduiding = new GroepPersoonGeslachtsaanduiding();
        geslachtsaanduiding.setCommunicatieID(COMMUNICATIE_TYPE_IDENTIFICATIE + getIdentificatieIdVolgnummerEnVerhoogMetEen());

        final GeslachtsaanduidingCode geslachtsaanduidingCode = new GeslachtsaanduidingCode();
        geslachtsaanduidingCode.setValue(GeslachtsaanduidingCodeS.valueOf(geslachtsaanduidingVerzoek));
        geslachtsaanduiding.setCode(OBJECT_FACTORY.createGroepPersoonGeslachtsaanduidingCode(geslachtsaanduidingCode));

        return geslachtsaanduiding;
    }

    /**
     * Maakt een partijCode aan voor de register gemeente in het BRP bericht.
     *
     * @param verzoek
     *            Het binnenkomende verzoek.
     * @return De partij code.
     */
    protected final PartijCode maakHandelingRegisterGemeente(final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek) {
        final BrpPartijCode brpPartijCode =
                getConverteerder().converteerLo3GemeenteCodeNaarBrpPartijCode(new Lo3GemeenteCode(verzoek.getAkte().getRegistergemeente()));
        final PartijCode partijCode = new PartijCode();
        partijCode.setValue(String.valueOf(brpPartijCode.getWaarde()));
        return partijCode;
    }

    /**
     * Geef het bronId volgnummer en verhoog deze met 1.
     *
     * @return Het huidige volgnummer
     */
    public final int getBronIdVolgnummerEnVerhoogMetEen() {
        return bronIdVolgnummer++;
    }

    /**
     * Geef het identificatieId volgnummer en verhoog deze met 1.
     *
     * @return Het huidige volgnummer
     */
    public final int getIdentificatieIdVolgnummerEnVerhoogMetEen() {
        return identificatieIdVolgnummer++;
    }

    protected final Lo3AttribuutConverteerder getConverteerder() {
        return converteerder;
    }

}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.verwerker.utils;

import java.util.GregorianCalendar;
import java.util.UUID;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import nl.bzk.algemeenbrp.services.objectsleutel.ObjectSleutelService;
import nl.bzk.migratiebrp.bericht.model.brp.generated.Aktenummer;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ContainerActieBronnen;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ContainerAdministratieveHandelingBronnen;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ContainerRelatieBetrokkenheden;
import nl.bzk.migratiebrp.bericht.model.brp.generated.DatumMetOnzekerheid;
import nl.bzk.migratiebrp.bericht.model.brp.generated.DatumTijd;
import nl.bzk.migratiebrp.bericht.model.brp.generated.GroepBerichtParameters;
import nl.bzk.migratiebrp.bericht.model.brp.generated.GroepBerichtStuurgegevens;
import nl.bzk.migratiebrp.bericht.model.brp.generated.GroepRelatieRelatie;
import nl.bzk.migratiebrp.bericht.model.brp.generated.NaamEnumeratiewaarde;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjectFactory;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypeActie;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypeActieBron;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypeAdministratieveHandeling;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypeAdministratieveHandelingBron;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypeDocument;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypeFamilierechtelijkeBetrekking;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypeGeregistreerdPartnerschap;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypeHuwelijk;
import nl.bzk.migratiebrp.bericht.model.brp.generated.PartijCode;
import nl.bzk.migratiebrp.bericht.model.brp.generated.Referentienummer;
import nl.bzk.migratiebrp.bericht.model.brp.generated.SysteemNaam;
import nl.bzk.migratiebrp.bericht.model.brp.generated.VerwerkingswijzeNaam;
import nl.bzk.migratiebrp.bericht.model.brp.generated.VerwerkingswijzeNaamS;
import nl.bzk.migratiebrp.conversie.model.brp.BrpRelatie;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenis;
import org.springframework.stereotype.Component;

/**
 * Utility voor maken van berichten.
 */
@Component
public class BerichtMaker {

    private static final String ZENDENDE_SYSTEEM_ISC = "ISC";
    private static final ObjectFactory OBJECT_FACTORY = new ObjectFactory();
    private static final DatatypeFactory DATATYPE_FACTORY;
    private static final String OBJECT_TYPE_ACTIE = "Actie";
    private static final String OBJECT_TYPE_ACTIE_BRON = "ActieBron";
    private static final String OBJECT_TYPE_ADMINISTRATIEVE_HANDELING_BRON = "AdministratieveHandelingBron";
    private static final String OBJECT_TYPE_DOCUMENT = "Document";
    private static final String OBJECT_TYPE_RELATIE = "Relatie";
    private static final String OBJECT_TYPE_ADMINISTRATIEVE_HANDELING = "AdministratieveHandeling";

    static {
        try {
            DATATYPE_FACTORY = DatatypeFactory.newInstance();
        } catch (final DatatypeConfigurationException e) {
            throw new IllegalArgumentException("Kan geen datatype factory instantieren", e);
        }
    }

    private final ObjectSleutelService objectSleutelService;
    private final AttribuutMaker attribuutMaker;
    private final VerbintenisMaker verbintenisMaker;
    private final RelatieMaker relatieMaker;
    private final PersoonMaker persoonMaker;

    /**
     * Constructor.
     * @param objectSleutelService service voor objectsleutel
     * @param attribuutMaker utility voor attributen
     * @param persoonMaker utility voor maken personen
     * @param relatieMaker utility voor maken relaties
     * @param verbintenisMaker utility voor maken verbintenissen
     */
    public BerichtMaker(final ObjectSleutelService objectSleutelService,
                        final AttribuutMaker attribuutMaker,
                        final VerbintenisMaker verbintenisMaker,
                        final RelatieMaker relatieMaker,
                        final PersoonMaker persoonMaker) {
        this.objectSleutelService = objectSleutelService;
        this.attribuutMaker = attribuutMaker;
        this.verbintenisMaker = verbintenisMaker;
        this.relatieMaker = relatieMaker;
        this.persoonMaker = persoonMaker;
    }

    /**
     * Maak de bericht stuurgegevens voor de opdracht o.b.v. het verzoek. Deze zijn gelijk voor alle berichten.
     * @param idMaker identificatie maker
     * @param verzoek Het verzoek waarop de stuurgegevens worden gebaseerd.
     * @return De bericht stuurgegevens.
     */
    public final GroepBerichtStuurgegevens maakBerichtStuurgegevens(final BerichtIdentificatieMaker idMaker, final BrpToevalligeGebeurtenis verzoek) {
        final GroepBerichtStuurgegevens stuurgegevens = new GroepBerichtStuurgegevens();
        stuurgegevens.setCommunicatieID(idMaker.volgendIdentificatieId());

        final Referentienummer referentienummer = new Referentienummer();
        referentienummer.setValue(UUID.randomUUID().toString());
        stuurgegevens.setReferentienummer(OBJECT_FACTORY.createGroepBerichtStuurgegevensReferentienummer(referentienummer));

        final PartijCode zendendePartij = new PartijCode();
        zendendePartij.setValue(verzoek.getRegisterGemeente().getWaarde());
        stuurgegevens.setZendendePartij(OBJECT_FACTORY.createGroepBerichtStuurgegevensZendendePartij(zendendePartij));

        final SysteemNaam zendendeSysteem = new SysteemNaam();
        zendendeSysteem.setValue(ZENDENDE_SYSTEEM_ISC);
        stuurgegevens.setZendendeSysteem(OBJECT_FACTORY.createGroepBerichtStuurgegevensZendendeSysteem(zendendeSysteem));

        final DatumTijd tijdstipVerzending = new DatumTijd();
        tijdstipVerzending.setValue(DATATYPE_FACTORY.newXMLGregorianCalendar(new GregorianCalendar()));
        tijdstipVerzending.getValue().setTimezone(0);
        stuurgegevens.setTijdstipVerzending(OBJECT_FACTORY.createGroepBerichtStuurgegevensTijdstipVerzending(tijdstipVerzending));
        return stuurgegevens;
    }

    /**
     * Maak de bericht parameters voor de opdracht o.b.v. het verzoek. Deze zijn gelijk voor alle berichten.
     * @param idMaker bericht identificatie maker
     * @return De bericht parameters.
     */
    public final GroepBerichtParameters maakBerichtParameters(final BerichtIdentificatieMaker idMaker) {
        final GroepBerichtParameters groepBerichtParameters = new GroepBerichtParameters();
        final VerwerkingswijzeNaam verwerkingswijzeNaam = new VerwerkingswijzeNaam();
        verwerkingswijzeNaam.setValue(VerwerkingswijzeNaamS.BIJHOUDING);
        groepBerichtParameters.setVerwerkingswijze(OBJECT_FACTORY.createGroepBerichtParametersVerwerkingswijze(verwerkingswijzeNaam));
        groepBerichtParameters.setCommunicatieID(idMaker.volgendIdentificatieId());

        return groepBerichtParameters;
    }

    /**
     * Maak de bericht administratieve handeling bronnen voor de opdracht o.b.v. het verzoek.
     * @param idMaker bericht identificatie maker
     * @param verzoek Het verzoek waarop de administratieve handeling bronnen worden gebaseerd.
     * @return De bericht administratieve handeling bronnen.
     */
    public final ContainerAdministratieveHandelingBronnen maakAdministratieveHandelingBronnen(
            final BerichtIdentificatieMaker idMaker,
            final BrpToevalligeGebeurtenis verzoek) {
        final ContainerAdministratieveHandelingBronnen administratievehandelingBronnen = new ContainerAdministratieveHandelingBronnen();
        final ObjecttypeAdministratieveHandelingBron administratievehandelingBron = new ObjecttypeAdministratieveHandelingBron();
        administratievehandelingBron.setObjecttype(OBJECT_TYPE_ADMINISTRATIEVE_HANDELING_BRON);
        administratievehandelingBron.setCommunicatieID(idMaker.volgendBronId());

        final ObjecttypeDocument document = new ObjecttypeDocument();
        final Aktenummer aktenummer = new Aktenummer();
        aktenummer.setValue(verzoek.getNummerAkte().getWaarde());
        document.setAktenummer(OBJECT_FACTORY.createObjecttypeDocumentAktenummer(aktenummer));
        final NaamEnumeratiewaarde naamEnumeratiewaarde = new NaamEnumeratiewaarde();
        naamEnumeratiewaarde.setValue("Historie conversie");
        document.setSoortNaam(OBJECT_FACTORY.createObjecttypeDocumentSoortNaam(naamEnumeratiewaarde));
        document.setObjecttype(OBJECT_TYPE_DOCUMENT);
        document.setCommunicatieID(idMaker.volgendIdentificatieId());
        document.setPartijCode(OBJECT_FACTORY.createObjecttypeDocumentPartijCode(maakHandelingRegisterGemeente(verzoek)));
        administratievehandelingBron.setDocument(OBJECT_FACTORY.createObjecttypeAdministratieveHandelingBronDocument(document));
        administratievehandelingBronnen.getBron().add(administratievehandelingBron);
        return administratievehandelingBronnen;
    }

    /**
     * Maakt een actie aan op basis van de gegevens uit het verzoek en het soort relatie.
     * @param idMaker bericht identificatie maker
     * @param actie De actie die gevuld dient te worden.
     * @param brpDatumAanvangGeldigheid De aanvangsdatum waarop de actie geldig wordt.
     * @param actieBron De bron van de actie.
     * @param brpRelatie De gevonden brp relatie waarop de actie betrekking heeft
     * @param relatie De relatie waarop de actie betrekking heeft.
     * @param betrokkenheden De betrokkenheden binnen de actie.
     * @param soortVerbintenis Het soort verbintenis.
     */
    public final void vulActie(
            final BerichtIdentificatieMaker idMaker,
            final ObjecttypeActie actie,
            final BrpDatum brpDatumAanvangGeldigheid,
            final ObjecttypeActieBron actieBron,
            final BrpRelatie brpRelatie,
            final GroepRelatieRelatie relatie,
            final ContainerRelatieBetrokkenheden betrokkenheden,
            final BrpSoortRelatieCode soortVerbintenis) {
        actie.setCommunicatieID(idMaker.volgendIdentificatieId());
        actie.setObjecttype(OBJECT_TYPE_ACTIE);

        actieBron.setCommunicatieID(idMaker.volgendIdentificatieId());
        actieBron.setObjecttype(OBJECT_TYPE_ACTIE_BRON);

        if (brpDatumAanvangGeldigheid != null) {
            // Datum aanvang geldigheid alleen voor acties die betrekking hebben op groepen met materiele historie.
            final DatumMetOnzekerheid datumAanvangGeldigheid = attribuutMaker.maakDatumMetOnzekerheid(brpDatumAanvangGeldigheid);
            actie.setDatumAanvangGeldigheid(OBJECT_FACTORY.createObjecttypeActieDatumAanvangGeldigheid(datumAanvangGeldigheid));
        }

        final ContainerActieBronnen actieBronnen = new ContainerActieBronnen();
        actieBronnen.getBron().add(actieBron);
        actie.setBronnen(OBJECT_FACTORY.createObjecttypeActieBronnen(actieBronnen));

        if (BrpSoortRelatieCode.HUWELIJK.equals(soortVerbintenis)) {
            // Huwelijk
            final ObjecttypeHuwelijk huwelijk = new ObjecttypeHuwelijk();
            huwelijk.setObjecttype(OBJECT_TYPE_RELATIE);
            huwelijk.setCommunicatieID(idMaker.volgendIdentificatieId());
            if (brpRelatie != null) {
                huwelijk.setObjectSleutel(objectSleutelService.maakRelatieObjectSleutel(brpRelatie.getRelatieId()).maskeren());
            }
            huwelijk.getRelatie().add(relatie);
            if (betrokkenheden != null) {
                huwelijk.setBetrokkenheden(OBJECT_FACTORY.createObjecttypeRelatieBetrokkenheden(betrokkenheden));
            }
            actie.setHuwelijk(OBJECT_FACTORY.createObjecttypeActieHuwelijk(huwelijk));

        } else if (BrpSoortRelatieCode.GEREGISTREERD_PARTNERSCHAP.equals(soortVerbintenis)) {
            // Geregistreerd partnerschap
            final ObjecttypeGeregistreerdPartnerschap geregistreerdPartnerschap = new ObjecttypeGeregistreerdPartnerschap();
            geregistreerdPartnerschap.setObjecttype(OBJECT_TYPE_RELATIE);
            geregistreerdPartnerschap.setCommunicatieID(idMaker.volgendIdentificatieId());
            if (brpRelatie != null) {
                geregistreerdPartnerschap.setObjectSleutel(objectSleutelService.maakRelatieObjectSleutel(brpRelatie.getRelatieId()).maskeren());
            }
            geregistreerdPartnerschap.getRelatie().add(relatie);
            if (betrokkenheden != null) {
                geregistreerdPartnerschap.setBetrokkenheden(OBJECT_FACTORY.createObjecttypeRelatieBetrokkenheden(betrokkenheden));
            }
            actie.setGeregistreerdPartnerschap(OBJECT_FACTORY.createObjecttypeActieGeregistreerdPartnerschap(geregistreerdPartnerschap));
        } else if (BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING.equals(soortVerbintenis)) {
            // Familierechtelijke betrekking
            final ObjecttypeFamilierechtelijkeBetrekking familieRechtelijkeBetrekking = new ObjecttypeFamilierechtelijkeBetrekking();
            familieRechtelijkeBetrekking.setObjecttype(OBJECT_TYPE_RELATIE);
            familieRechtelijkeBetrekking.setCommunicatieID(idMaker.volgendIdentificatieId());
            if (brpRelatie != null) {
                familieRechtelijkeBetrekking.setObjectSleutel(objectSleutelService.maakRelatieObjectSleutel(brpRelatie.getRelatieId()).maskeren());
            }
            familieRechtelijkeBetrekking.getRelatie().add(relatie);
            familieRechtelijkeBetrekking.setBetrokkenheden(OBJECT_FACTORY.createObjecttypeRelatieBetrokkenheden(betrokkenheden));
            actie.setFamilierechtelijkeBetrekking(OBJECT_FACTORY.createObjecttypeActieFamilierechtelijkeBetrekking(familieRechtelijkeBetrekking));
        }

    }

    /**
     * Vult een handeling.
     * @param idMaker berichtIdentificatiemaker
     * @param verzoek het orginele verzoek
     * @param administratieveHandelingBronnen de bronnen van de administratieve handeling
     * @param handeling de handeling welke gevuld moeten worden
     */
    public final void vulHandeling(
            final BerichtIdentificatieMaker idMaker,
            final BrpToevalligeGebeurtenis verzoek,
            final ContainerAdministratieveHandelingBronnen administratieveHandelingBronnen,
            final ObjecttypeAdministratieveHandeling handeling) {

        handeling.setCommunicatieID(idMaker.volgendIdentificatieId());
        handeling.setObjecttype(OBJECT_TYPE_ADMINISTRATIEVE_HANDELING);
        handeling.setPartijCode(OBJECT_FACTORY.createObjecttypeAdministratieveHandelingPartijCode(maakHandelingRegisterGemeente(verzoek)));
        handeling.setBronnen(OBJECT_FACTORY.createObjecttypeAdministratieveHandelingBronnen(administratieveHandelingBronnen));
    }

    /**
     * Maakt een partijCode aan voor de register gemeente in het BRP bericht.
     * @param verzoek Het binnenkomende verzoek.
     * @return De partij code.
     */
    public final PartijCode maakHandelingRegisterGemeente(final BrpToevalligeGebeurtenis verzoek) {
        final PartijCode partijCode = new PartijCode();
        partijCode.setValue(verzoek.getRegisterGemeente().getWaarde());
        return partijCode;
    }

    /**
     * geef de objectsleutel service.
     * @return ObjectSleutelService
     */
    public ObjectSleutelService getObjectSleutelService() {
        return objectSleutelService;
    }

    /**
     * Geef de attribuutmaker.
     * @return AttribuutMaker
     */
    public AttribuutMaker getAttribuutMaker() {
        return attribuutMaker;
    }

    /**
     * Geef de verbintenismaker.
     * @return verbintenismaker
     */
    public VerbintenisMaker getVerbintenisMaker() {
        return verbintenisMaker;
    }

    /**
     * Geef de relatieMaker.
     * @return relatiemaker
     */
    public RelatieMaker getRelatieMaker() {
        return relatieMaker;
    }

    /**
     * geef de persoon maker
     * @return persoonmaker
     */
    public PersoonMaker getPersoonMaker() {
        return persoonMaker;
    }
}

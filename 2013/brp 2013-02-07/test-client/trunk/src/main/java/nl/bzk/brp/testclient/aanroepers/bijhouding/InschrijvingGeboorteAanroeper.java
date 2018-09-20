/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.aanroepers.bijhouding;

import java.math.BigInteger;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.xml.bind.JAXBElement;

import nl.bprbzk.brp._0001.ANummer;
import nl.bprbzk.brp._0001.AfstammingInschrijvingAangifteGeboorteBijhouding;
import nl.bprbzk.brp._0001.Applicatienaam;
import nl.bprbzk.brp._0001.Burgerservicenummer;
import nl.bprbzk.brp._0001.ContainerBerichtActies;
import nl.bprbzk.brp._0001.ContainerBerichtOverruleMeldingen;
import nl.bprbzk.brp._0001.ContainerPersoonGeslachtsnaamcomponenten;
import nl.bprbzk.brp._0001.ContainerPersoonNationaliteiten;
import nl.bprbzk.brp._0001.ContainerPersoonVoornamen;
import nl.bprbzk.brp._0001.ContainerRelatieBetrokkenheden;
import nl.bprbzk.brp._0001.Datum;
import nl.bprbzk.brp._0001.DatumTijd;
import nl.bprbzk.brp._0001.Gemeentecode;
import nl.bprbzk.brp._0001.GeslachtsaanduidingCode;
import nl.bprbzk.brp._0001.GeslachtsaanduidingCodeS;
import nl.bprbzk.brp._0001.Geslachtsnaamcomponent;
import nl.bprbzk.brp._0001.GroepBerichtStuurgegevens;
import nl.bprbzk.brp._0001.GroepBetrokkenheidOuderschap;
import nl.bprbzk.brp._0001.GroepPersoonAfgeleidAdministratief;
import nl.bprbzk.brp._0001.GroepPersoonGeboorte;
import nl.bprbzk.brp._0001.GroepPersoonGeslachtsaanduiding;
import nl.bprbzk.brp._0001.GroepPersoonIdentificatienummers;
import nl.bprbzk.brp._0001.Ja;
import nl.bprbzk.brp._0001.JaS;
import nl.bprbzk.brp._0001.Landcode;
import nl.bprbzk.brp._0001.NaamEnumeratiewaarde;
import nl.bprbzk.brp._0001.ObjectFactory;
import nl.bprbzk.brp._0001.ObjecttypeOverrule;
import nl.bprbzk.brp._0001.ObjecttypePersoon;
import nl.bprbzk.brp._0001.ObjecttypePersoonGeslachtsnaamcomponent;
import nl.bprbzk.brp._0001.ObjecttypePersoonNationaliteit;
import nl.bprbzk.brp._0001.ObjecttypePersoonVoornaam;
import nl.bprbzk.brp._0001.Organisatienaam;
import nl.bprbzk.brp._0001.RegelCode;
import nl.bprbzk.brp._0001.Sleutelwaardetekst;
import nl.bprbzk.brp._0001.ViewFamilierechtelijkeBetrekking;
import nl.bprbzk.brp._0001.ViewInschrijvingGeboorte;
import nl.bprbzk.brp._0001.ViewKind;
import nl.bprbzk.brp._0001.ViewOuder;
import nl.bprbzk.brp._0001.ViewRegistratieNationaliteit;
import nl.bprbzk.brp._0001.Volgnummer;
import nl.bprbzk.brp._0001.Voornaam;
import nl.bprbzk.brp._0001.Voorvoegsel;
import nl.bzk.brp.testclient.TestClient;
import nl.bzk.brp.testclient.misc.Bericht;
import nl.bzk.brp.testclient.misc.Eigenschappen;
import nl.bzk.brp.testclient.soapcommands.bijhouding.GeboorteCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * De Class InschrijvingGeboorteAanroeper.
 */
public class InschrijvingGeboorteAanroeper extends BijhoudingAanroeper {

    private static final Logger     log                        = LoggerFactory
                                                                       .getLogger(InschrijvingGeboorteAanroeper.class);

    // NB: Let op dat je bij het toevoegen van parameters, deze ook toevoegt in getParameterList()

    public static final String      PARAMETER_BSN              = "BSN";
    public static final String      PARAMETER_NAAM             = "NAAM";
    public static final String      PARAMETER_VOORVOEGSEL      = "VOORVOEGSEL";
    public static final String      PARAMETER_BSN_OUDER1       = "BSN_OUDER1";
    public static final String      PARAMETER_BSN_OUDER2       = "BSN_OUDER2";
    public static final String      PARAMETER_ANUMMER          = "ANUMMER";
    public static final String      PARAMETER_REFERENTIENUMMER = "REFERENTIENUMMER";
    private static final String     BSN                        = "100000011";
    private static final String     BSN_OUDER1                 = "100000010";
    private static final String     BSN_OUDER2                 = "100000009";
    private static final String     ANUMMER                    = "1111111";
    private static final String     ORGANISATIE                = "364";
    private static final String     APPLICATIE                 = "Testclient";
    private static final String     LANDCODE                   = "6030";
    private static final String     GEMEENTECODE               = "0347";
    private static final String     NAAM                       = "Elytis";
    private static final String     VOORVOEGSEL                = null;
    private static final String     VOORNAAM                   = "Testertje";
    private static final String     NATIONALITEIT_NAAM         = "0001";
    private static final String     OVERRULE_REGELCODE         = "BRBY0106";
    private static final String     REDEN_VERKRIJGING_NAAM     = "1";
    private static final BigInteger TIJDSTIP_ONTLENING         = BigInteger.valueOf(20120920);
    private static final BigInteger TIJDSTIP_LAATSTE_WIJZIGING = BigInteger.valueOf(20120920);
    private static final BigInteger VOLGNUMMER                 = BigInteger.valueOf(1);


    /**
     * Instantieert een nieuwe inschrijving geboorte aanroeper.
     *
     * @param eigenschappen de eigenschappen
     * @param parameterMap de parameter map
     * @throws Exception de exception
     */
    public InschrijvingGeboorteAanroeper(final Eigenschappen eigenschappen, final Map<String, String> parameterMap)
            throws Exception
    {
        super(eigenschappen, parameterMap);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.brp.testclient.aanroepers.AbstractAanroeper#fire()
     */
    /**
     * Fire.
     */
    @Override
    public void fire() {
        AfstammingInschrijvingAangifteGeboorteBijhouding bijhouding =
            creeerAfstammingInschrijvingAangifteGeboorteBijhouding();
        GeboorteCommand command = new GeboorteCommand(bijhouding);
        Long duur = roepAan(command);
        TestClient.statistieken.bijwerkenNaBericht(Bericht.BERICHT.BIJHOUDING_GEBOORTE, getStatus(), duur);
    }

    /**
     * Creeer afstamming inschrijving aangifte geboorte bijhouding.
     *
     * @return de afstamming inschrijving aangifte geboorte bijhouding
     * @throws SQLException
     */
    private AfstammingInschrijvingAangifteGeboorteBijhouding creeerAfstammingInschrijvingAangifteGeboorteBijhouding() {
        SimpleDateFormat sdfTijd = new SimpleDateFormat("yyyyMMddhhmmssSS");
        String datumTijdNu = sdfTijd.format(new Date());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String datumNu = sdf.format(new Date());

        ObjectFactory objectFactory = new ObjectFactory();

        AfstammingInschrijvingAangifteGeboorteBijhouding afstammingInschrijvingAangifteGeboorteBijhouding =
            objectFactory.createAfstammingInschrijvingAangifteGeboorteBijhouding();

        // Stuurgegevens-element
        List<GroepBerichtStuurgegevens> groepBerichtStuurgegevensList =
            afstammingInschrijvingAangifteGeboorteBijhouding.getStuurgegevens();
        GroepBerichtStuurgegevens groepBerichtStuurgegevens = creeerGroepBerichtStuurgegevens();
        groepBerichtStuurgegevensList.add(groepBerichtStuurgegevens);

        // Overrule
        ContainerBerichtOverruleMeldingen overruleBerichten = new ContainerBerichtOverruleMeldingen();
        JAXBElement<ContainerBerichtOverruleMeldingen> jaxbOverruleMeldingen =
            objectFactory.createObjecttypeBerichtOverruleMeldingen(overruleBerichten);
        afstammingInschrijvingAangifteGeboorteBijhouding.setOverruleMeldingen(jaxbOverruleMeldingen);
        List<ObjecttypeOverrule> overruleList = overruleBerichten.getOverrule();
        ObjecttypeOverrule overrule = new ObjecttypeOverrule();

        // Regelcode
        RegelCode regelcode = new RegelCode();
        regelcode.setValue(OVERRULE_REGELCODE);
        overrule.setRegelCode(regelcode);
        overruleList.add(overrule);

        // Acties-element
        ContainerBerichtActies containerBerichtActies = objectFactory.createContainerBerichtActies();
        JAXBElement<ContainerBerichtActies> jaxbContainerBerichtActies =
            objectFactory.createObjecttypeBerichtActies(containerBerichtActies);
        afstammingInschrijvingAangifteGeboorteBijhouding.setActies(jaxbContainerBerichtActies);

        // Inschrijving geboorte
        List<ViewInschrijvingGeboorte> viewInschrijvingGeboorteList = containerBerichtActies.getInschrijvingGeboorte();
        ViewInschrijvingGeboorte viewInschrijvingGeboorte = objectFactory.createViewInschrijvingGeboorte();
        viewInschrijvingGeboorteList.add(viewInschrijvingGeboorte);

        // Aanvang geldigheid
        JAXBElement<Datum> jaxbAanvangGeldigheid = creeerJaxbAanvangGeldigheid();
        viewInschrijvingGeboorte.setDatumAanvangGeldigheid(jaxbAanvangGeldigheid);

        // Tijdstip ontlening
        JAXBElement<DatumTijd> jaxbTijdstipOntlening = creeerJaxbTijdstipOntlening();
        viewInschrijvingGeboorte.setTijdstipOntlening(jaxbTijdstipOntlening);

        // Familierechtelijke Betrekking
        List<ViewFamilierechtelijkeBetrekking> viewFamilierechtelijkeBetrekkingList =
            viewInschrijvingGeboorte.getFamilierechtelijkeBetrekking();
        ViewFamilierechtelijkeBetrekking viewFamilierechtelijkeBetrekking =
            objectFactory.createViewFamilierechtelijkeBetrekking();
        viewFamilierechtelijkeBetrekkingList.add(viewFamilierechtelijkeBetrekking);

        // Betrokkenheden
        ContainerRelatieBetrokkenheden containerRelatieBetrokkenheden =
            objectFactory.createContainerRelatieBetrokkenheden();
        JAXBElement<ContainerRelatieBetrokkenheden> jaxbContainerRelatieBetrokkenheden =
            objectFactory.createObjecttypeRelatieBetrokkenheden(containerRelatieBetrokkenheden);
        viewFamilierechtelijkeBetrekking.setBetrokkenheden(jaxbContainerRelatieBetrokkenheden);

        // Kind
        List<ViewKind> viewKindList = containerRelatieBetrokkenheden.getKind();
        ViewKind viewKind = objectFactory.createViewKind();
        viewKindList.add(viewKind);

        // Persoon
        ObjecttypePersoon objecttypePersoon = objectFactory.createObjecttypePersoon();
        JAXBElement<ObjecttypePersoon> jaxbObjecttypePersoon =
            objectFactory.createObjecttypeBetrokkenheidPersoon(objecttypePersoon);
        viewKind.setPersoon(jaxbObjecttypePersoon);

        // Identificatienummers
        GroepPersoonIdentificatienummers groepPersoonIdentificatienummers =
            objectFactory.createGroepPersoonIdentificatienummers();
        List<GroepPersoonIdentificatienummers> groepPersoonIdentificatienummersList =
            objecttypePersoon.getIdentificatienummers();
        groepPersoonIdentificatienummersList.add(groepPersoonIdentificatienummers);

        // BSN Kind met random bsn
        Burgerservicenummer burgerservicenummerKind = objectFactory.createBurgerservicenummer();
        burgerservicenummerKind.setValue(getValueFromValueMap(PARAMETER_BSN, BSN));
        JAXBElement<Burgerservicenummer> jaxbBurgerservicenummerKind =
            objectFactory.createGroepPersoonIdentificatienummersBurgerservicenummer(burgerservicenummerKind);
        groepPersoonIdentificatienummers.setBurgerservicenummer(jaxbBurgerservicenummerKind);

        // A-nummer Kind
        ANummer aNummerKind = objectFactory.createANummer();
        aNummerKind.setValue(getValueFromValueMap(PARAMETER_ANUMMER, ANUMMER));
        JAXBElement<ANummer> jaxbANummerKind =
            objectFactory.createGroepPersoonIdentificatienummersAdministratienummer(aNummerKind);
        groepPersoonIdentificatienummers.setAdministratienummer(jaxbANummerKind);

        // Geslachtsaanduiding
        List<GroepPersoonGeslachtsaanduiding> groepPersoonGeslachtsaanduidingList =
            objecttypePersoon.getGeslachtsaanduiding();
        GroepPersoonGeslachtsaanduiding groepPersoonGeslachtsaanduiding =
            objectFactory.createGroepPersoonGeslachtsaanduiding();
        GeslachtsaanduidingCode geslachtsaanduidingCode = objectFactory.createGeslachtsaanduidingCode();
        geslachtsaanduidingCode.setValue(GeslachtsaanduidingCodeS.M);
        groepPersoonGeslachtsaanduiding.setCode(geslachtsaanduidingCode);
        groepPersoonGeslachtsaanduidingList.add(groepPersoonGeslachtsaanduiding);

        // Geboorte
        List<GroepPersoonGeboorte> groepPersoonGeboorteList = objecttypePersoon.getGeboorte();
        GroepPersoonGeboorte groepPersoonGeboorte = objectFactory.createGroepPersoonGeboorte();
        groepPersoonGeboorteList.add(groepPersoonGeboorte);

        // Geboortedatum
        Datum datum = objectFactory.createDatum();
        datum.setValue(BigInteger.valueOf(Long.valueOf(datumNu)));
        groepPersoonGeboorte.setDatum(datum);

        // Gemeentecode
        Gemeentecode gemeentecodeGeboorte = objectFactory.createGemeentecode();
        gemeentecodeGeboorte.setValue(GEMEENTECODE);
        JAXBElement<Gemeentecode> jaxbGemeentecodeGeboorte =
            objectFactory.createGroepPersoonGeboorteGemeenteCode(gemeentecodeGeboorte);
        groepPersoonGeboorte.setGemeenteCode(jaxbGemeentecodeGeboorte);

        // Landcode
        Landcode landcode = objectFactory.createLandcode();
        landcode.setValue(LANDCODE);
        groepPersoonGeboorte.setLandCode(landcode);

        // Voornamen
        ContainerPersoonVoornamen voornamen = new ContainerPersoonVoornamen();
        JAXBElement<ContainerPersoonVoornamen> jaxbVoornamen =
            objectFactory.createObjecttypePersoonVoornamen(voornamen);
        objecttypePersoon.setVoornamen(jaxbVoornamen);

        List<ObjecttypePersoonVoornaam> voornaamList = voornamen.getVoornaam();
        ObjecttypePersoonVoornaam voornaam = new ObjecttypePersoonVoornaam();

        // Volgnummer
        Volgnummer volgnummer = new Volgnummer();
        volgnummer.setValue(VOLGNUMMER);
        voornaam.setVolgnummer(volgnummer);

        // Voornaam
        Voornaam voornaamElement = new Voornaam();
        voornaamElement.setValue(VOORNAAM);
        voornaam.setNaam(voornaamElement);
        voornaamList.add(voornaam);

        // Geslachtsnaamcomponenten
        ContainerPersoonGeslachtsnaamcomponenten containerPersoonGeslachtsnaamcomponenten =
            objectFactory.createContainerPersoonGeslachtsnaamcomponenten();
        JAXBElement<ContainerPersoonGeslachtsnaamcomponenten> jaxbContainerPersoonGeslachtsnaamcomponenten =
            objectFactory.createObjecttypePersoonGeslachtsnaamcomponenten(containerPersoonGeslachtsnaamcomponenten);
        objecttypePersoon.setGeslachtsnaamcomponenten(jaxbContainerPersoonGeslachtsnaamcomponenten);

        // Geslachtsnaamcomponent
        ObjecttypePersoonGeslachtsnaamcomponent objecttypePersoonGeslachtsnaamcomponentGeboorte =
            objectFactory.createObjecttypePersoonGeslachtsnaamcomponent();
        List<ObjecttypePersoonGeslachtsnaamcomponent> objecttypePersoonGeslachtsnaamcomponentGeboorteList =
            containerPersoonGeslachtsnaamcomponenten.getGeslachtsnaamcomponent();
        objecttypePersoonGeslachtsnaamcomponentGeboorteList.add(objecttypePersoonGeslachtsnaamcomponentGeboorte);

        // Volgnummer
        Volgnummer volgnummerGeslachtsnaam = new Volgnummer();
        volgnummerGeslachtsnaam.setValue(VOLGNUMMER);
        objecttypePersoonGeslachtsnaamcomponentGeboorte.setVolgnummer(volgnummerGeslachtsnaam);

        // Voorvoegsel
        if (getValueFromValueMap(PARAMETER_VOORVOEGSEL, VOORVOEGSEL) != null) {
            Voorvoegsel voorvoegsel = new Voorvoegsel();
            voorvoegsel.setValue(getValueFromValueMap(PARAMETER_VOORVOEGSEL, VOORVOEGSEL));
            JAXBElement<Voorvoegsel> jaxbVoorvoegsel =
                objectFactory.createObjecttypePersoonGeslachtsnaamcomponentVoorvoegsel(voorvoegsel);
            objecttypePersoonGeslachtsnaamcomponentGeboorte.setVoorvoegsel(jaxbVoorvoegsel);
        }

        // Naam
        Geslachtsnaamcomponent naam = objectFactory.createGeslachtsnaamcomponent();
        naam.setValue(getValueFromValueMap(PARAMETER_NAAM, NAAM));
        objecttypePersoonGeslachtsnaamcomponentGeboorte.setNaam(naam);

        // Ouders
        List<ViewOuder> viewOuderList = containerRelatieBetrokkenheden.getOuder();

        // Ouder 1
        ViewOuder viewOuder = objectFactory.createViewOuder();
        viewOuderList.add(viewOuder);

        // Persoon
        ObjecttypePersoon objecttypePersoonOuder = objectFactory.createObjecttypePersoon();
        JAXBElement<ObjecttypePersoon> jaxbObjecttypePersoonOuder =
            objectFactory.createObjecttypeBetrokkenheidPersoon(objecttypePersoonOuder);
        viewOuder.setPersoon(jaxbObjecttypePersoonOuder);

        // Identificatienummers
        List<GroepPersoonIdentificatienummers> groepPersoonIdentificatienummersOuderList =
            objecttypePersoonOuder.getIdentificatienummers();
        GroepPersoonIdentificatienummers groepPersoonIdentificatienummersOuder =
            objectFactory.createGroepPersoonIdentificatienummers();
        groepPersoonIdentificatienummersOuderList.add(groepPersoonIdentificatienummersOuder);

        // Burgerservicenummer
        Burgerservicenummer burgerservicenummer = objectFactory.createBurgerservicenummer();
        burgerservicenummer.setValue(getValueFromValueMap(PARAMETER_BSN_OUDER1, BSN_OUDER1));
        JAXBElement<Burgerservicenummer> jaxbBurgerservicenummer =
            objectFactory.createGroepPersoonIdentificatienummersBurgerservicenummer(burgerservicenummer);
        groepPersoonIdentificatienummersOuder.setBurgerservicenummer(jaxbBurgerservicenummer);

        // Afgeleid administratief
        List<GroepPersoonAfgeleidAdministratief> afgeleidAdministratiefList =
            objecttypePersoonOuder.getAfgeleidAdministratief();
        GroepPersoonAfgeleidAdministratief afgeleidAdministratief = new GroepPersoonAfgeleidAdministratief();
        afgeleidAdministratiefList.add(afgeleidAdministratief);

        // Tijdstip laatste wijziging
        DatumTijd laatsteWijziging = new DatumTijd();
        laatsteWijziging.setValue(TIJDSTIP_LAATSTE_WIJZIGING);
        JAXBElement<DatumTijd> jaxbLaatsteWijziging =
            objectFactory.createGroepPersoonAfgeleidAdministratiefTijdstipLaatsteWijziging(laatsteWijziging);
        afgeleidAdministratief.setTijdstipLaatsteWijziging(jaxbLaatsteWijziging);

        // Ouderschap
        List<GroepBetrokkenheidOuderschap> groepBetrokkenheidOuderschapList = viewOuder.getOuderschap();
        GroepBetrokkenheidOuderschap groepBetrokkenheidOuderschap = objectFactory.createGroepBetrokkenheidOuderschap();
        groepBetrokkenheidOuderschapList.add(groepBetrokkenheidOuderschap);

        // Datum Aanvang
        Datum aanvang = objectFactory.createDatum();
        aanvang.setValue(BigInteger.valueOf(Long.valueOf(datumNu)));
        JAXBElement<Datum> jaxbAanvangOuderschap =
            objectFactory.createGroepBetrokkenheidOuderschapDatumAanvang(aanvang);
        groepBetrokkenheidOuderschap.setDatumAanvang(jaxbAanvangOuderschap);

        // Indicatie Adresgevend
        Ja ja = objectFactory.createJa();
        ja.setValue(JaS.J);
        JAXBElement<Ja> jaxbJa = objectFactory.createGroepBetrokkenheidOuderschapIndicatieAdresgevend(ja);
        groepBetrokkenheidOuderschap.setIndicatieAdresgevend(jaxbJa);

        // Ouder 2
        ViewOuder viewOuder2 = objectFactory.createViewOuder();
        viewOuderList.add(viewOuder2);

        // Persoon
        ObjecttypePersoon objecttypePersoonOuder2 = objectFactory.createObjecttypePersoon();
        JAXBElement<ObjecttypePersoon> jaxbObjecttypePersoonOuder2 =
            objectFactory.createObjecttypeBetrokkenheidPersoon(objecttypePersoonOuder2);
        viewOuder2.setPersoon(jaxbObjecttypePersoonOuder2);

        // Identificatienummers
        List<GroepPersoonIdentificatienummers> groepPersoonIdentificatienummersOuderList2 =
            objecttypePersoonOuder2.getIdentificatienummers();
        GroepPersoonIdentificatienummers groepPersoonIdentificatienummersOuder2 =
            objectFactory.createGroepPersoonIdentificatienummers();
        groepPersoonIdentificatienummersOuderList2.add(groepPersoonIdentificatienummersOuder2);

        // Burgerservicenummer
        Burgerservicenummer burgerservicenummer2 = objectFactory.createBurgerservicenummer();
        burgerservicenummer2.setValue(getValueFromValueMap(PARAMETER_BSN_OUDER2, BSN_OUDER2));
        JAXBElement<Burgerservicenummer> jaxbBurgerservicenummer2 =
            objectFactory.createGroepPersoonIdentificatienummersBurgerservicenummer(burgerservicenummer2);
        groepPersoonIdentificatienummersOuder2.setBurgerservicenummer(jaxbBurgerservicenummer2);

        // Afgeleid administratief
        List<GroepPersoonAfgeleidAdministratief> afgeleidAdministratiefList2 =
            objecttypePersoonOuder2.getAfgeleidAdministratief();
        GroepPersoonAfgeleidAdministratief afgeleidAdministratief2 = new GroepPersoonAfgeleidAdministratief();
        afgeleidAdministratiefList2.add(afgeleidAdministratief2);

        // Tijdstip laatste wijziging
        DatumTijd laatsteWijziging2 = new DatumTijd();
        laatsteWijziging2.setValue(TIJDSTIP_LAATSTE_WIJZIGING);
        JAXBElement<DatumTijd> jaxbLaatsteWijziging2 =
            objectFactory.createGroepPersoonAfgeleidAdministratiefTijdstipLaatsteWijziging(laatsteWijziging2);
        afgeleidAdministratief2.setTijdstipLaatsteWijziging(jaxbLaatsteWijziging2);

        // Ouderschap
        List<GroepBetrokkenheidOuderschap> groepBetrokkenheidOuderschapList2 = viewOuder2.getOuderschap();
        GroepBetrokkenheidOuderschap groepBetrokkenheidOuderschap2 = objectFactory.createGroepBetrokkenheidOuderschap();
        groepBetrokkenheidOuderschapList2.add(groepBetrokkenheidOuderschap2);

        // Datum Aanvang
        Datum aanvang2 = objectFactory.createDatum();
        aanvang2.setValue(BigInteger.valueOf(Long.valueOf(datumNu)));
        JAXBElement<Datum> jaxbAanvangOuderschap2 =
            objectFactory.createGroepBetrokkenheidOuderschapDatumAanvang(aanvang2);
        groepBetrokkenheidOuderschap2.setDatumAanvang(jaxbAanvangOuderschap2);

        // Registratie Nationaliteit
        List<ViewRegistratieNationaliteit> viewRegistratieNationaliteitList =
            containerBerichtActies.getRegistratieNationaliteit();
        ViewRegistratieNationaliteit viewRegistratieNationaliteit = objectFactory.createViewRegistratieNationaliteit();
        viewRegistratieNationaliteitList.add(viewRegistratieNationaliteit);

        // Datum Aanvang Geldigheid
        Datum aanvangGeldigheidNationaliteit = objectFactory.createDatum();
        aanvangGeldigheidNationaliteit.setValue(BigInteger.valueOf(Long.valueOf(datumNu)));
        JAXBElement<Datum> jaxbAanvangGeldigheidNationaliteit =
            objectFactory.createObjecttypeActieDatumAanvangGeldigheid(aanvangGeldigheidNationaliteit);
        viewRegistratieNationaliteit.setDatumAanvangGeldigheid(jaxbAanvangGeldigheidNationaliteit);

        // Tijdstip ontlening
        DatumTijd tijdstipOntleningNationaliteit = objectFactory.createDatumTijd();
        tijdstipOntleningNationaliteit.setValue(TIJDSTIP_ONTLENING);
        JAXBElement<DatumTijd> jaxbTijdstipOntleningNationaliteit =
            objectFactory.createObjecttypeActieTijdstipOntlening(tijdstipOntleningNationaliteit);
        viewRegistratieNationaliteit.setTijdstipOntlening(jaxbTijdstipOntleningNationaliteit);

        // Persoon nationaliteit
        ObjecttypePersoon objecttypePersoonNationaliteit = objectFactory.createObjecttypePersoon();
        viewRegistratieNationaliteit.getPersoon().add(objecttypePersoonNationaliteit);

        // Identificatienummers nationaliteit
        GroepPersoonIdentificatienummers groepPersoonIdentificatienummersNationaliteit =
            objectFactory.createGroepPersoonIdentificatienummers();
        objecttypePersoonNationaliteit.getIdentificatienummers().add(groepPersoonIdentificatienummersNationaliteit);

        // Bsn nationaliteit
        Burgerservicenummer burgerservicenummerNationaliteit = objectFactory.createBurgerservicenummer();
        burgerservicenummerNationaliteit.setValue(getValueFromValueMap(PARAMETER_BSN, BSN));
        JAXBElement<Burgerservicenummer> bsnNationaliteit =
            objectFactory.createGroepPersoonIdentificatienummersBurgerservicenummer(burgerservicenummerNationaliteit);
        groepPersoonIdentificatienummersNationaliteit.setBurgerservicenummer(bsnNationaliteit);

        // Afgeleid administratief nationaliteit
        GroepPersoonAfgeleidAdministratief groepPersoonAfgeleidAdministratief =
            objectFactory.createGroepPersoonAfgeleidAdministratief();
        objecttypePersoonNationaliteit.getAfgeleidAdministratief().add(groepPersoonAfgeleidAdministratief);

        // Tijdstip Laatste Wijziging
        DatumTijd tijdstipLaatsteWijziging = objectFactory.createDatumTijd();
        tijdstipLaatsteWijziging.setValue(TIJDSTIP_LAATSTE_WIJZIGING);
        JAXBElement<DatumTijd> jaxbTijdstipLaatsteWijziging =
            objectFactory.createGroepPersoonAfgeleidAdministratiefTijdstipLaatsteWijziging(tijdstipLaatsteWijziging);
        groepPersoonAfgeleidAdministratief.setTijdstipLaatsteWijziging(jaxbTijdstipLaatsteWijziging);

        // Nationaliteiten
        ContainerPersoonNationaliteiten nationaliteiten = new ContainerPersoonNationaliteiten();
        JAXBElement<ContainerPersoonNationaliteiten> jaxbNationaliteiten =
            objectFactory.createObjecttypePersoonNationaliteiten(nationaliteiten);
        objecttypePersoonNationaliteit.setNationaliteiten(jaxbNationaliteiten);
        List<ObjecttypePersoonNationaliteit> nationaliteitList = nationaliteiten.getNationaliteit();

        // Nationaliteit
        ObjecttypePersoonNationaliteit nationaliteit = new ObjecttypePersoonNationaliteit();
        NaamEnumeratiewaarde nationaliteitNaam = new NaamEnumeratiewaarde();
        nationaliteitNaam.setValue(NATIONALITEIT_NAAM);
        nationaliteit.setNationaliteitNaam(nationaliteitNaam);
        nationaliteitList.add(nationaliteit);

        // Reden Verkrijging Naam
        NaamEnumeratiewaarde redenVerkrijgingNaam = new NaamEnumeratiewaarde();
        redenVerkrijgingNaam.setValue(REDEN_VERKRIJGING_NAAM);
        JAXBElement<NaamEnumeratiewaarde> jaxbRedenVerkrijgingNaam =
            objectFactory.createObjecttypePersoonNationaliteitRedenVerkrijgingNaam(redenVerkrijgingNaam);
        nationaliteit.setRedenVerkrijgingNaam(jaxbRedenVerkrijgingNaam);

        return afstammingInschrijvingAangifteGeboorteBijhouding;
    }

    /**
     * Creeer jaxb tijdstip ontlening.
     *
     * @return de jAXB element
     */
    private JAXBElement<DatumTijd> creeerJaxbTijdstipOntlening() {
        ObjectFactory objectFactory = new ObjectFactory();
        DatumTijd tijdstipOntlening = objectFactory.createDatumTijd();
        tijdstipOntlening.setValue(TIJDSTIP_ONTLENING);
        JAXBElement<DatumTijd> jaxbTijdstipOntlening =
            objectFactory.createObjecttypeActieTijdstipOntlening(tijdstipOntlening);
        return jaxbTijdstipOntlening;
    }

    /**
     * Creeer jaxb aanvang geldigheid.
     *
     * @return de jAXB element
     */
    private JAXBElement<Datum> creeerJaxbAanvangGeldigheid() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String datumNu = sdf.format(new Date());
        ObjectFactory objectFactory = new ObjectFactory();
        Datum aanvangGeldigheid = objectFactory.createDatum();
        aanvangGeldigheid.setValue(BigInteger.valueOf(Long.valueOf(datumNu)));
        JAXBElement<Datum> jaxbAanvangGeldigheid =
            objectFactory.createObjecttypeActieDatumAanvangGeldigheid(aanvangGeldigheid);
        return jaxbAanvangGeldigheid;
    }

    /**
     * Creeer groep bericht stuurgegevens.
     *
     * @return de groep bericht stuurgegevens
     */
    private GroepBerichtStuurgegevens creeerGroepBerichtStuurgegevens() {
        ObjectFactory objectFactory = new ObjectFactory();

        GroepBerichtStuurgegevens groepBerichtStuurgegevens = new GroepBerichtStuurgegevens();

        Organisatienaam organisatienaam = new Organisatienaam();
        organisatienaam.setValue(ORGANISATIE);
        groepBerichtStuurgegevens.setOrganisatie(organisatienaam);

        Applicatienaam applicatienaam = new Applicatienaam();
        applicatienaam.setValue(APPLICATIE);
        groepBerichtStuurgegevens.setApplicatie(applicatienaam);

        Sleutelwaardetekst sleutelwaardetekst = new Sleutelwaardetekst();
        sleutelwaardetekst.setValue(getValueFromValueMap(PARAMETER_REFERENTIENUMMER, "" + new Random().nextInt(1000)));
        groepBerichtStuurgegevens.setReferentienummer(sleutelwaardetekst);

        if (valueMap.containsKey(PARAMETER_PREVALIDATIE)) {
            Ja ja = new Ja();
            ja.setValue(JaS.J);
            JAXBElement<Ja> jaxbJa = objectFactory.createGroepBerichtStuurgegevensIndicatiePrevalidatie(ja);
            groepBerichtStuurgegevens.setIndicatiePrevalidatie(jaxbJa);
        }

        return groepBerichtStuurgegevens;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.brp.testclient.aanroepers.AbstractAanroeper#getParameterList()
     */
    @Override
    protected List<String> getParameterList() {
        return Arrays.asList(new String[] { PARAMETER_REFERENTIENUMMER, PARAMETER_PREVALIDATIE, PARAMETER_BSN,
            PARAMETER_ANUMMER, PARAMETER_NAAM, PARAMETER_BSN_OUDER1, PARAMETER_BSN_OUDER2, PARAMETER_VOORVOEGSEL });
    }
}

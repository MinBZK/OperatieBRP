/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.aanroepers.bijhouding;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBElement;

import nl.bprbzk.brp._0001.Aktenummer;
import nl.bprbzk.brp._0001.Applicatienaam;
import nl.bprbzk.brp._0001.Burgerservicenummer;
import nl.bprbzk.brp._0001.ContainerActieBronnen;
import nl.bprbzk.brp._0001.ContainerBerichtActies;
import nl.bprbzk.brp._0001.ContainerRelatieBetrokkenheden;
import nl.bprbzk.brp._0001.Datum;
import nl.bprbzk.brp._0001.DatumTijd;
import nl.bprbzk.brp._0001.DocumentIdentificatie;
import nl.bprbzk.brp._0001.Gemeentecode;
import nl.bprbzk.brp._0001.GroepBerichtStuurgegevens;
import nl.bprbzk.brp._0001.GroepPersoonAanschrijving;
import nl.bprbzk.brp._0001.GroepPersoonAfgeleidAdministratief;
import nl.bprbzk.brp._0001.GroepPersoonIdentificatienummers;
import nl.bprbzk.brp._0001.HuwelijkPartnerschapRegistratieHuwelijkBijhouding;
import nl.bprbzk.brp._0001.Ja;
import nl.bprbzk.brp._0001.JaNee;
import nl.bprbzk.brp._0001.JaNeeS;
import nl.bprbzk.brp._0001.JaS;
import nl.bprbzk.brp._0001.NaamEnumeratiewaarde;
import nl.bprbzk.brp._0001.ObjectFactory;
import nl.bprbzk.brp._0001.ObjecttypeBron;
import nl.bprbzk.brp._0001.ObjecttypeDocument;
import nl.bprbzk.brp._0001.ObjecttypePersoon;
import nl.bprbzk.brp._0001.Ontleningstoelichting;
import nl.bprbzk.brp._0001.Organisatienaam;
import nl.bprbzk.brp._0001.Sleutelwaardetekst;
import nl.bprbzk.brp._0001.ViewHuwelijk;
import nl.bprbzk.brp._0001.ViewPartner;
import nl.bprbzk.brp._0001.ViewRegistratieHuwelijk;
import nl.bprbzk.brp._0001.ViewWijzigingNaamgebruik;
import nl.bprbzk.brp._0001.WijzeGebruikGeslachtsnaamCode;
import nl.bprbzk.brp._0001.WijzeGebruikGeslachtsnaamCodeS;
import nl.bzk.brp.testclient.TestClient;
import nl.bzk.brp.testclient.misc.Bericht;
import nl.bzk.brp.testclient.misc.Eigenschappen;
import nl.bzk.brp.testclient.soapcommands.bijhouding.HuwelijkCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * De Class RegistreerHuwelijkEnPartnerschapAanroeper.
 */
public class RegistreerHuwelijkEnPartnerschapAanroeper extends BijhoudingAanroeper {

    /** De Constante log. */
    private static final Logger     log                                 =
                                                                            LoggerFactory
                                                                                    .getLogger(RegistreerHuwelijkEnPartnerschapAanroeper.class);

    // NB: Let op dat je bij het toevoegen van parameters, deze ook toevoegt in getParameterList()

    public static final String      PARAMETER_BSN1                      = "BSN1";
    public static final String      PARAMETER_BSN2                      = "BSN2";
    public static final String      PARAMETER_BSN_NAAMGEBRUIK           = "BSN_NAAMGEBRUIK";
    public static final String      PARAMETER_REFERENTIENUMMER          = "REFERENTIENUMMER";
    private static final String     BSN1                                = "100075290";
    private static final String     BSN2                                = "100027933";
    private static final String     ORGANISATIE                         = "364";
    private static final String     APPLICATIE                          = "Testclient";
    private static final String     REFERENTIENUMMER                    = "12345";
    private static final String     GEMEENTECODE                        = "0530";
    private static final String     ONTLENINGSTOELICHTING               = "Test test test";
    private static final String     BSN_NAAMGEBRUIK                     = "100027933";
    private static final BigInteger AANVANG_GELDIGHEID                  = BigInteger.valueOf(20120919);
    private static final BigInteger TIJDSTIP_ONTLENING                  = BigInteger.valueOf(2012092014145900L);
    private static final BigInteger DATUM_AANVANG_HUWELIJK              = BigInteger.valueOf(20120821);
    private static final BigInteger DATUM_AANVANG_WIJZIGING_NAAMGEBRUIK = BigInteger.valueOf(20120921);

    /**
     * Instantieert een nieuwe registreer huwelijk en partnerschap aanroeper.
     *
     * @param eigenschappen de eigenschappen
     * @param parameterMap de parameter map
     * @throws Exception de exception
     */
    public RegistreerHuwelijkEnPartnerschapAanroeper(final Eigenschappen eigenschappen,
            final Map<String, String> parameterMap) throws Exception
    {
        super(eigenschappen, parameterMap);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.brp.testclient.aanroepers.AbstractAanroeper#fire()
     */
    @Override
    public void fire() {
        HuwelijkPartnerschapRegistratieHuwelijkBijhouding bijhouding =
            creeerHuwelijkPartnerschapRegistratieHuwelijkBijhouding();
        HuwelijkCommand command = new HuwelijkCommand(bijhouding);
        Long duur = roepAan(command);
        TestClient.statistieken.bijwerkenNaBericht(Bericht.BERICHT.BIJHOUDING_HUWELIJK,
                getStatus(), duur);
    }

    /**
     * Creeer huwelijk partnerschap registratie huwelijk bijhouding.
     *
     * @return de huwelijk partnerschap registratie huwelijk bijhouding
     */
    private HuwelijkPartnerschapRegistratieHuwelijkBijhouding creeerHuwelijkPartnerschapRegistratieHuwelijkBijhouding()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssSS");
        String datumTijdNu = sdf.format(new Date());

        ObjectFactory objectFactory = new ObjectFactory();
        HuwelijkPartnerschapRegistratieHuwelijkBijhouding huwelijkPartnerschap =
            objectFactory.createHuwelijkPartnerschapRegistratieHuwelijkBijhouding();

        // Stuurgegevens-element
        List<GroepBerichtStuurgegevens> groepBerichtStuurgegevensList = huwelijkPartnerschap.getStuurgegevens();
        GroepBerichtStuurgegevens groepBerichtStuurgegevens = creeerGroepBerichtStuurgegevens();
        groepBerichtStuurgegevensList.add(groepBerichtStuurgegevens);

        // Acties-element
        ContainerBerichtActies containerBerichtActies = objectFactory.createContainerBerichtActies();
        JAXBElement<ContainerBerichtActies> jaxbContainerBerichtActies =
            objectFactory.createObjecttypeBerichtActies(containerBerichtActies);
        huwelijkPartnerschap.setActies(jaxbContainerBerichtActies);

        // Registratie huwelijk
        ViewRegistratieHuwelijk registratieHuwelijk = new ViewRegistratieHuwelijk();
        List<ViewRegistratieHuwelijk> registratieHuwelijkList = containerBerichtActies.getRegistratieHuwelijk();
        registratieHuwelijkList.add(registratieHuwelijk);

        // Datum Aanvang Geldigheid
        Datum datumAanvangGeldigheid = new Datum();
        datumAanvangGeldigheid.setValue(AANVANG_GELDIGHEID);
        JAXBElement<Datum> jaxbDatumAanvangGeldigheid =
            objectFactory.createObjecttypeActieDatumAanvangGeldigheid(datumAanvangGeldigheid);
        registratieHuwelijk.setDatumAanvangGeldigheid(jaxbDatumAanvangGeldigheid);

        // Tijdstip Ontlening
        DatumTijd tijdstipOntlening = new DatumTijd();
        tijdstipOntlening.setValue(TIJDSTIP_ONTLENING);
        JAXBElement<DatumTijd> jaxbTijdstipOntlening =
            objectFactory.createObjecttypeActieTijdstipOntlening(tijdstipOntlening);
        registratieHuwelijk.setTijdstipOntlening(jaxbTijdstipOntlening);

        // Toelichting Ontlening
        Ontleningstoelichting ontleningstoelichting = new Ontleningstoelichting();
        ontleningstoelichting.setValue(ONTLENINGSTOELICHTING);
        JAXBElement<Ontleningstoelichting> jaxbOntleningstoelichting =
            objectFactory.createObjecttypeActieToelichtingOntlening(ontleningstoelichting);
        registratieHuwelijk.setToelichtingOntlening(jaxbOntleningstoelichting);

        // Bronnen
        ContainerActieBronnen bronnen = new ContainerActieBronnen();
        JAXBElement<ContainerActieBronnen> jaxbBronnen = objectFactory.createObjecttypeActieBronnen(bronnen);
        registratieHuwelijk.setBronnen(jaxbBronnen);
        List<ObjecttypeBron> bronList = bronnen.getBron();
        ObjecttypeBron bron = new ObjecttypeBron();
        ObjecttypeDocument document = new ObjecttypeDocument();
        bron.setDocument(document);
        bronList.add(bron);

        // Soort naam
        NaamEnumeratiewaarde soortNaam = new NaamEnumeratiewaarde();
        soortNaam.setValue("Huwelijksakte");
        document.setSoortNaam(soortNaam);

        // Identificatie
        DocumentIdentificatie documentIdentificatie = new DocumentIdentificatie();
        documentIdentificatie.setValue("HA0530-3AA0001");
        JAXBElement<DocumentIdentificatie> jaxbDocumentIdentificatie =
            objectFactory.createObjecttypeDocumentIdentificatie(documentIdentificatie);
        document.setIdentificatie(jaxbDocumentIdentificatie);

        // Aktenummer
        Aktenummer aktenummer = new Aktenummer();
        aktenummer.setValue("3AA0001");
        JAXBElement<Aktenummer> jaxbAktenummer = objectFactory.createObjecttypeDocumentAktenummer(aktenummer);
        document.setAktenummer(jaxbAktenummer);

        // Partijcode
        Gemeentecode gemeentecode = new Gemeentecode();
        gemeentecode.setValue(GEMEENTECODE);
        document.setPartijCode(gemeentecode);

        // Huwelijk
        List<ViewHuwelijk> huwelijkList = registratieHuwelijk.getHuwelijk();
        ViewHuwelijk huwelijk = new ViewHuwelijk();
        huwelijkList.add(huwelijk);

        // Datum aanvang
        Datum datumAanvang = new Datum();
        datumAanvang.setValue(DATUM_AANVANG_HUWELIJK);
        JAXBElement<Datum> jaxbDatumAanvang = objectFactory.createObjecttypeRelatieDatumAanvang(datumAanvang);
        huwelijk.setDatumAanvang(jaxbDatumAanvang);

        // Gemeente Aanvang Code
        Gemeentecode gemeenteAanvangCode = new Gemeentecode();
        gemeenteAanvangCode.setValue(GEMEENTECODE);
        JAXBElement<Gemeentecode> jaxbGemeentecode =
            objectFactory.createObjecttypeRelatieGemeenteAanvangCode(gemeenteAanvangCode);
        huwelijk.setGemeenteAanvangCode(jaxbGemeentecode);

        // Betrokkenheden
        ContainerRelatieBetrokkenheden betrokkenheden = new ContainerRelatieBetrokkenheden();
        JAXBElement<ContainerRelatieBetrokkenheden> jaxbBetrokkenheden =
            objectFactory.createObjecttypeRelatieBetrokkenheden(betrokkenheden);
        huwelijk.setBetrokkenheden(jaxbBetrokkenheden);

        // Partner 1
        List<ViewPartner> partnerList = betrokkenheden.getPartner();
        ViewPartner partner = new ViewPartner();
        ObjecttypePersoon persoonPartner = new ObjecttypePersoon();
        JAXBElement<ObjecttypePersoon> jaxbPersoon = objectFactory.createObjecttypeBetrokkenheidPersoon(persoonPartner);
        partner.setPersoon(jaxbPersoon);
        partnerList.add(partner);

        // Identificatienummers
        List<GroepPersoonIdentificatienummers> identificatienummersList = persoonPartner.getIdentificatienummers();
        GroepPersoonIdentificatienummers identificatienummers = new GroepPersoonIdentificatienummers();
        Burgerservicenummer burgerservicenummerPartner = new Burgerservicenummer();
        burgerservicenummerPartner.setValue(getValueFromValueMap(PARAMETER_BSN1, BSN1));

        JAXBElement<Burgerservicenummer> jaxbBurgerservicenummerPartner =
            objectFactory.createGroepPersoonIdentificatienummersBurgerservicenummer(burgerservicenummerPartner);
        identificatienummers.setBurgerservicenummer(jaxbBurgerservicenummerPartner);
        identificatienummersList.add(identificatienummers);

        // Afgeleid administratief
        List<GroepPersoonAfgeleidAdministratief> afgeleidAdministratiefList =
            persoonPartner.getAfgeleidAdministratief();
        GroepPersoonAfgeleidAdministratief afgeleidAdministratief = new GroepPersoonAfgeleidAdministratief();
        DatumTijd tijdstipLaatsteWijziging = new DatumTijd();
        tijdstipLaatsteWijziging.setValue(BigInteger.valueOf(Long.valueOf(datumTijdNu)));
        JAXBElement<DatumTijd> jaxbTijdstipLaatsteWijziging =
            objectFactory.createGroepPersoonAfgeleidAdministratiefTijdstipLaatsteWijziging(tijdstipLaatsteWijziging);
        afgeleidAdministratief.setTijdstipLaatsteWijziging(jaxbTijdstipLaatsteWijziging);
        afgeleidAdministratiefList.add(afgeleidAdministratief);

        // Partner 2
        ViewPartner partner2 = new ViewPartner();
        ObjecttypePersoon persoonPartner2 = new ObjecttypePersoon();
        JAXBElement<ObjecttypePersoon> jaxbPersoon2 =
            objectFactory.createObjecttypeBetrokkenheidPersoon(persoonPartner2);
        partner2.setPersoon(jaxbPersoon2);
        partnerList.add(partner2);

        // Identificatienummers
        List<GroepPersoonIdentificatienummers> identificatienummersList2 = persoonPartner2.getIdentificatienummers();
        GroepPersoonIdentificatienummers identificatienummers2 = new GroepPersoonIdentificatienummers();
        Burgerservicenummer burgerservicenummerPartner2 = new Burgerservicenummer();
        burgerservicenummerPartner2.setValue(getValueFromValueMap(PARAMETER_BSN2, BSN2));
        JAXBElement<Burgerservicenummer> jaxbBurgerservicenummerPartner2 =
            objectFactory.createGroepPersoonIdentificatienummersBurgerservicenummer(burgerservicenummerPartner2);
        identificatienummers2.setBurgerservicenummer(jaxbBurgerservicenummerPartner2);
        identificatienummersList2.add(identificatienummers2);

        // Afgeleid administratief
        List<GroepPersoonAfgeleidAdministratief> afgeleidAdministratiefList2 =
            persoonPartner2.getAfgeleidAdministratief();
        GroepPersoonAfgeleidAdministratief afgeleidAdministratief2 = new GroepPersoonAfgeleidAdministratief();
        DatumTijd tijdstipLaatsteWijziging2 = new DatumTijd();
        tijdstipLaatsteWijziging2.setValue(BigInteger.valueOf(Long.valueOf(datumTijdNu)));
        JAXBElement<DatumTijd> jaxbTijdstipLaatsteWijziging2 =
            objectFactory.createGroepPersoonAfgeleidAdministratiefTijdstipLaatsteWijziging(tijdstipLaatsteWijziging2);
        afgeleidAdministratief2.setTijdstipLaatsteWijziging(jaxbTijdstipLaatsteWijziging2);
        afgeleidAdministratiefList2.add(afgeleidAdministratief2);

        // Wijziging naamgebruik
        List<ViewWijzigingNaamgebruik> wijzigingNaamgebruikList = containerBerichtActies.getWijzigingNaamgebruik();
        ViewWijzigingNaamgebruik wijzigingNaamgebruik = new ViewWijzigingNaamgebruik();
        wijzigingNaamgebruikList.add(wijzigingNaamgebruik);

        // Datum Aanvang Geldigheid
        Datum datumAanvangNaamgebruik = new Datum();
        datumAanvangNaamgebruik.setValue(DATUM_AANVANG_WIJZIGING_NAAMGEBRUIK);
        JAXBElement<Datum> jaxbDatumAanvangNaamgebruik =
            objectFactory.createObjecttypeActieDatumAanvangGeldigheid(datumAanvangNaamgebruik);
        wijzigingNaamgebruik.setDatumAanvangGeldigheid(jaxbDatumAanvangNaamgebruik);

        // Tijdstip Ontlening
        DatumTijd tijdstipOntleningNaamgebruik = new DatumTijd();
        tijdstipOntleningNaamgebruik.setValue(TIJDSTIP_ONTLENING);
        JAXBElement<DatumTijd> jaxbTijdstipOntleningNaamgebruik =
            objectFactory.createObjecttypeActieTijdstipOntlening(tijdstipOntleningNaamgebruik);
        wijzigingNaamgebruik.setTijdstipOntlening(jaxbTijdstipOntleningNaamgebruik);

        // Persoon
        List<ObjecttypePersoon> persoonList = wijzigingNaamgebruik.getPersoon();
        ObjecttypePersoon persoonNaamgebruik = new ObjecttypePersoon();
        persoonList.add(persoonNaamgebruik);

        // Identificatienummers
        List<GroepPersoonIdentificatienummers> identificatienummersNaamgebruikList =
            persoonNaamgebruik.getIdentificatienummers();
        GroepPersoonIdentificatienummers identificatienummersNaamgebruik = new GroepPersoonIdentificatienummers();
        identificatienummersNaamgebruikList.add(identificatienummersNaamgebruik);

        // Burgerservicenummer
        Burgerservicenummer burgerservicenummerNaamgebruik = new Burgerservicenummer();
        burgerservicenummerNaamgebruik.setValue(getValueFromValueMap(PARAMETER_BSN_NAAMGEBRUIK, BSN_NAAMGEBRUIK));
        JAXBElement<Burgerservicenummer> jaxbBurgerservicenummerNaamgebruik =
            objectFactory.createGroepPersoonIdentificatienummersBurgerservicenummer(burgerservicenummerNaamgebruik);
        identificatienummersNaamgebruik.setBurgerservicenummer(jaxbBurgerservicenummerNaamgebruik);

        // Aanschrijving
        List<GroepPersoonAanschrijving> aanschrijvingList = persoonNaamgebruik.getAanschrijving();
        GroepPersoonAanschrijving aanschrijving = new GroepPersoonAanschrijving();
        aanschrijvingList.add(aanschrijving);

        // Wijze van gebruik
        WijzeGebruikGeslachtsnaamCode wijzeGebruikGeslachtsnaamCode = new WijzeGebruikGeslachtsnaamCode();
        wijzeGebruikGeslachtsnaamCode.setValue(WijzeGebruikGeslachtsnaamCodeS.P);
        JAXBElement<WijzeGebruikGeslachtsnaamCode> jaxbWijzeGebruikGeslachtsnaamCode =
            objectFactory
                    .createGroepPersoonAanschrijvingWijzeVanGebruikGeslachtsnaamEchtgenootGeregistreerdPartnerCode(wijzeGebruikGeslachtsnaamCode);
        aanschrijving
                .setWijzeVanGebruikGeslachtsnaamEchtgenootGeregistreerdPartnerCode(jaxbWijzeGebruikGeslachtsnaamCode);

        // Indicatie algoritmisch afgeleid
        JaNee indicatieAlgoritmischAfgeleid = new JaNee();
        indicatieAlgoritmischAfgeleid.setValue(JaNeeS.J);
        aanschrijving.setIndicatieAlgoritmischAfgeleid(indicatieAlgoritmischAfgeleid);

        // Afgeleid Administratief
        List<GroepPersoonAfgeleidAdministratief> afgeleidAdministratiefNaamgebruikList =
            persoonNaamgebruik.getAfgeleidAdministratief();
        GroepPersoonAfgeleidAdministratief afgeleidAdministratiefNaamgebruik = new GroepPersoonAfgeleidAdministratief();
        afgeleidAdministratiefNaamgebruikList.add(afgeleidAdministratiefNaamgebruik);

        // Tijdstip laatste wijziging
        DatumTijd tijdstipLaatsteWijzigingNaamgebruik = new DatumTijd();
        tijdstipLaatsteWijzigingNaamgebruik.setValue(BigInteger.valueOf(Long.valueOf(datumTijdNu)));
        JAXBElement<DatumTijd> jaxbTijdstipLaatsteWijzigingNaamgebruik =
            objectFactory
                    .createGroepPersoonAfgeleidAdministratiefTijdstipLaatsteWijziging(tijdstipLaatsteWijzigingNaamgebruik);
        afgeleidAdministratiefNaamgebruik.setTijdstipLaatsteWijziging(jaxbTijdstipLaatsteWijzigingNaamgebruik);

        return huwelijkPartnerschap;
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
        sleutelwaardetekst.setValue(getValueFromValueMap(PARAMETER_REFERENTIENUMMER, REFERENTIENUMMER));
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
        return Arrays.asList(new String[] { BijhoudingAanroeper.PARAMETER_PREVALIDATIE, PARAMETER_BSN1, PARAMETER_BSN2,
            PARAMETER_BSN_NAAMGEBRUIK, PARAMETER_REFERENTIENUMMER });
    }
}

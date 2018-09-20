/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.aanroepers.bijhouding;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.xml.bind.JAXBElement;

import nl.bprbzk.brp._0001.Applicatienaam;
import nl.bprbzk.brp._0001.Burgerservicenummer;
import nl.bprbzk.brp._0001.ContainerBerichtActies;
import nl.bprbzk.brp._0001.Datum;
import nl.bprbzk.brp._0001.DatumTijd;
import nl.bprbzk.brp._0001.Gemeentecode;
import nl.bprbzk.brp._0001.GroepBerichtStuurgegevens;
import nl.bprbzk.brp._0001.GroepPersoonAfgeleidAdministratief;
import nl.bprbzk.brp._0001.GroepPersoonIdentificatienummers;
import nl.bprbzk.brp._0001.GroepPersoonOverlijden;
import nl.bprbzk.brp._0001.Ja;
import nl.bprbzk.brp._0001.JaS;
import nl.bprbzk.brp._0001.Landcode;
import nl.bprbzk.brp._0001.ObjectFactory;
import nl.bprbzk.brp._0001.ObjecttypePersoon;
import nl.bprbzk.brp._0001.Organisatienaam;
import nl.bprbzk.brp._0001.OverlijdenRegistratieOverlijdenBijhouding;
import nl.bprbzk.brp._0001.Sleutelwaardetekst;
import nl.bprbzk.brp._0001.ViewRegistratieOverlijden;
import nl.bprbzk.brp._0001.Woonplaatscode;
import nl.bzk.brp.testclient.TestClient;
import nl.bzk.brp.testclient.misc.Bericht;
import nl.bzk.brp.testclient.misc.Eigenschappen;
import nl.bzk.brp.testclient.soapcommands.bijhouding.OverlijdenCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class OverlijdenAanroeper extends BijhoudingAanroeper {

    private static final Logger     log                                = LoggerFactory
                                                                               .getLogger(OverlijdenAanroeper.class);

    private static final String     BSN                                = "100000009";
    private static final String     ORGANISATIE                        = "364";
    private static final String     APPLICATIE                         = "Testclient";
    private static final String     GEMEENTECODE                       = "0599";
    private static final String     WOONPLAATSCODE                     = "3086";
    private static final String     LANDCODE                           = "6030";

    private static final BigInteger AANVANG_GELDIGHEID                 = BigInteger.valueOf(20100719);
    private static final BigInteger TIJDSTIP_ONTLENING                 = BigInteger.valueOf(20120820);
    private static final BigInteger TIJDSTIP_LAATSTE_WIJZIGING         = BigInteger.valueOf(20120812);

    // NB: Let op dat je bij het toevoegen van parameters, deze ook toevoegt in getParameterList()

    public static final String      PARAMETER_REFERENTIENUMMER         = "REFERENTIENUMMER";
    public static final String      PARAMETER_DATUM_AANVANG_GELDIGHEID = "DATUM_AANVANG_GELDIGHEID";
    public static final String      PARAMETER_BSN                      = "BSN";
    public static final String      PARAMETER_DATUM_OVERLIJDEN         = "DATUM_OVERLIJDEN";
    public static final String      PARAMETER_GEMEENTECODE             = "GEMEENTE";
    public static final String      PARAMETER_WOONPLAATSCODE           = "WOONPLAATSCODE";
    public static final String      PARAMETER_LANDCODE                 = "LANDCODE";

    @Override
    protected List<String> getParameterList() {
        return Arrays.asList(new String[] { PARAMETER_REFERENTIENUMMER, PARAMETER_PREVALIDATIE, PARAMETER_BSN,
            PARAMETER_DATUM_AANVANG_GELDIGHEID, PARAMETER_DATUM_OVERLIJDEN, PARAMETER_GEMEENTECODE,
            PARAMETER_WOONPLAATSCODE, PARAMETER_LANDCODE });
    }

    public OverlijdenAanroeper(final Eigenschappen eigenschappen, final Map<String, String> parameterMap)
            throws Exception
    {
        super(eigenschappen, parameterMap);
    }

    @Override
    public void fire() {
        OverlijdenRegistratieOverlijdenBijhouding bijhouding = creeerOverlijdenRegistratieOverlijdenBijhouding();
        OverlijdenCommand command = new OverlijdenCommand(bijhouding);
        Long duur = roepAan(command);
        TestClient.statistieken.bijwerkenNaBericht(Bericht.BERICHT.BIJHOUDING_OVERLIJDEN, getStatus(), duur);
    }

    private OverlijdenRegistratieOverlijdenBijhouding creeerOverlijdenRegistratieOverlijdenBijhouding() {
        ObjectFactory objectFactory = new ObjectFactory();
        OverlijdenRegistratieOverlijdenBijhouding overlijden =
            objectFactory.createOverlijdenRegistratieOverlijdenBijhouding();

        // Stuurgegevens-element
        List<GroepBerichtStuurgegevens> groepBerichtStuurgegevensList = overlijden.getStuurgegevens();
        GroepBerichtStuurgegevens groepBerichtStuurgegevens = creeerGroepBerichtStuurgegevens();
        groepBerichtStuurgegevensList.add(groepBerichtStuurgegevens);

        // Acties-element
        ContainerBerichtActies containerBerichtActies = objectFactory.createContainerBerichtActies();
        JAXBElement<ContainerBerichtActies> jaxbContainerBerichtActies =
            objectFactory.createObjecttypeBerichtActies(containerBerichtActies);
        overlijden.setActies(jaxbContainerBerichtActies);

        List<ViewRegistratieOverlijden> overlijdenActieList = containerBerichtActies.getRegistratieOverlijden();
        ViewRegistratieOverlijden viewRegistratieOverlijden = objectFactory.createViewRegistratieOverlijden();
        overlijdenActieList.add(viewRegistratieOverlijden);

        // Aanvang geldigheid
        JAXBElement<Datum> jaxbAanvangGeldigheid = creeerJaxbAanvangGeldigheid();
        viewRegistratieOverlijden.setDatumAanvangGeldigheid(jaxbAanvangGeldigheid);

        // Tijdstip ontlening
        JAXBElement<DatumTijd> jaxbTijdstipOntlening = creeerJaxbTijdstipOntlening();
        viewRegistratieOverlijden.setTijdstipOntlening(jaxbTijdstipOntlening);

        // Persoon
        List<ObjecttypePersoon> objecttypePersoonList = viewRegistratieOverlijden.getPersoon();
        ObjecttypePersoon objecttypePersoon = objectFactory.createObjecttypePersoon();
        objecttypePersoonList.add(objecttypePersoon);

        // Identificatienummers
        List<GroepPersoonIdentificatienummers> groepPersoonIdentificatienummersList =
            objecttypePersoon.getIdentificatienummers();
        GroepPersoonIdentificatienummers groepPersoonIdentificatienummers =
            objectFactory.createGroepPersoonIdentificatienummers();
        groepPersoonIdentificatienummersList.add(groepPersoonIdentificatienummers);

        // BSN
        Burgerservicenummer burgerservicenummer = new Burgerservicenummer();
        burgerservicenummer.setValue(getValueFromValueMap(PARAMETER_BSN, BSN));
        JAXBElement<Burgerservicenummer> jaxbBurgerservicenummer =
            objectFactory.createGroepPersoonIdentificatienummersBurgerservicenummer(burgerservicenummer);
        groepPersoonIdentificatienummers.setBurgerservicenummer(jaxbBurgerservicenummer);

        // Overlijden
        List<GroepPersoonOverlijden> groepPersoonOverlijdenList = objecttypePersoon.getOverlijden();
        GroepPersoonOverlijden groepPersoonOverlijden = objectFactory.createGroepPersoonOverlijden();
        groepPersoonOverlijdenList.add(groepPersoonOverlijden);

        // Datum
        Datum datumOverlijden = objectFactory.createDatum();
        datumOverlijden.setValue(BigInteger.valueOf(Long.parseLong(getValueFromValueMap(PARAMETER_DATUM_OVERLIJDEN, ""
            + AANVANG_GELDIGHEID))));
        groepPersoonOverlijden.setDatum(datumOverlijden);

        // Gemeentecode
        Gemeentecode gemeentecode = objectFactory.createGemeentecode();
        gemeentecode.setValue(getValueFromValueMap(PARAMETER_GEMEENTECODE, GEMEENTECODE));
        JAXBElement<Gemeentecode> jaxbGemeentecode =
            objectFactory.createObjecttypePersoonAdresGemeenteCode(gemeentecode);
        groepPersoonOverlijden.setGemeenteCode(jaxbGemeentecode);

        // Woonplaatscode
        Woonplaatscode woonplaatsCode = new Woonplaatscode();
        woonplaatsCode.setValue(getValueFromValueMap(PARAMETER_WOONPLAATSCODE, WOONPLAATSCODE));
        JAXBElement<Woonplaatscode> jaxbWoonplaatsCode =
            objectFactory.createObjecttypePersoonAdresWoonplaatsCode(woonplaatsCode);
        groepPersoonOverlijden.setWoonplaatsCode(jaxbWoonplaatsCode);

        // Landcode
        Landcode landcode = new Landcode();
        landcode.setValue(getValueFromValueMap(PARAMETER_LANDCODE, LANDCODE));
        JAXBElement<Landcode> jaxbLandcode = objectFactory.createGroepPersoonOverlijdenLandCode(landcode);
        groepPersoonOverlijden.setLandCode(jaxbLandcode);

        // Afgeleid administratief
        List<GroepPersoonAfgeleidAdministratief> groepPersoonAfgeleidAdministratiefList =
            objecttypePersoon.getAfgeleidAdministratief();
        GroepPersoonAfgeleidAdministratief groepPersoonAfgeleidAdministratief =
            objectFactory.createGroepPersoonAfgeleidAdministratief();

        // Tijdstip laatste wijziging
        JAXBElement<DatumTijd> jaxbDatumTijd = creeerJaxbDatumTijd();
        groepPersoonAfgeleidAdministratief.setTijdstipLaatsteWijziging(jaxbDatumTijd);
        groepPersoonAfgeleidAdministratiefList.add(groepPersoonAfgeleidAdministratief);

        return overlijden;
    }

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

    private JAXBElement<Datum> creeerJaxbAanvangGeldigheid() {
        ObjectFactory objectFactory = new ObjectFactory();
        Datum aanvangGeldigheid = objectFactory.createDatum();
        aanvangGeldigheid.setValue(BigInteger.valueOf(Long.parseLong(getValueFromValueMap(
                PARAMETER_DATUM_AANVANG_GELDIGHEID, "" + AANVANG_GELDIGHEID))));
        JAXBElement<Datum> jaxbAanvangGeldigheid =
            objectFactory.createObjecttypeActieDatumAanvangGeldigheid(aanvangGeldigheid);
        return jaxbAanvangGeldigheid;
    }

    private JAXBElement<DatumTijd> creeerJaxbTijdstipOntlening() {
        ObjectFactory objectFactory = new ObjectFactory();
        DatumTijd tijdstipOntlening = objectFactory.createDatumTijd();
        tijdstipOntlening.setValue(BigInteger.valueOf(Long.parseLong(getValueFromValueMap(
                PARAMETER_DATUM_AANVANG_GELDIGHEID, "" + TIJDSTIP_ONTLENING))));
        JAXBElement<DatumTijd> jaxbTijdstipOntlening =
            objectFactory.createObjecttypeActieTijdstipOntlening(tijdstipOntlening);
        return jaxbTijdstipOntlening;
    }

    private JAXBElement<DatumTijd> creeerJaxbDatumTijd() {
        ObjectFactory objectFactory = new ObjectFactory();
        DatumTijd datumTijd = objectFactory.createDatumTijd();
        datumTijd.setValue(BigInteger.valueOf(Long.parseLong(getValueFromValueMap(PARAMETER_DATUM_AANVANG_GELDIGHEID,
                "" + TIJDSTIP_LAATSTE_WIJZIGING))));
        JAXBElement<DatumTijd> jaxbDatumTijd =
            objectFactory.createGroepPersoonAfgeleidAdministratiefTijdstipLaatsteWijziging(datumTijd);
        return jaxbDatumTijd;
    }
}

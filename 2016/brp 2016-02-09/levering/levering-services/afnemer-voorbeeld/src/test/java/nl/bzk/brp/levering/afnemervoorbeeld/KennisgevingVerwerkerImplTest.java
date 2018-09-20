/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.afnemervoorbeeld;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.List;
import javax.xml.bind.JAXBElement;
import nl.bzk.brp.brp0200.AdministratieveHandelingCode;
import nl.bzk.brp.brp0200.Burgerservicenummer;
import nl.bzk.brp.brp0200.ContainerAdministratieveHandelingBijgehoudenPersonen;
import nl.bzk.brp.brp0200.ContainerPersoonGeslachtsnaamcomponenten;
import nl.bzk.brp.brp0200.ContainerPersoonVoornamen;
import nl.bzk.brp.brp0200.DienstID;
import nl.bzk.brp.brp0200.GeslachtsaanduidingCode;
import nl.bzk.brp.brp0200.GeslachtsaanduidingCodeS;
import nl.bzk.brp.brp0200.Geslachtsnaamstam;
import nl.bzk.brp.brp0200.GroepBerichtParameters;
import nl.bzk.brp.brp0200.GroepBerichtStuurgegevens;
import nl.bzk.brp.brp0200.GroepPersoonGeslachtsaanduiding;
import nl.bzk.brp.brp0200.GroepPersoonIdentificatienummers;
import nl.bzk.brp.brp0200.LeveringsautorisatieID;
import nl.bzk.brp.brp0200.NaamEnumeratiewaarde;
import nl.bzk.brp.brp0200.ObjectFactory;
import nl.bzk.brp.brp0200.ObjecttypeAdministratieveHandeling;
import nl.bzk.brp.brp0200.ObjecttypePersoon;
import nl.bzk.brp.brp0200.ObjecttypePersoonGeslachtsnaamcomponent;
import nl.bzk.brp.brp0200.ObjecttypePersoonVoornaam;
import nl.bzk.brp.brp0200.PartijCode;
import nl.bzk.brp.brp0200.Referentienummer;
import nl.bzk.brp.brp0200.Scheidingsteken;
import nl.bzk.brp.brp0200.SynchronisatieVerwerkPersoon;
import nl.bzk.brp.brp0200.SysteemNaam;
import nl.bzk.brp.brp0200.VerwerkingssoortS;
import nl.bzk.brp.brp0200.Voornaam;
import nl.bzk.brp.brp0200.Voorvoegsel;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.ReflectionUtils;

@RunWith(MockitoJUnitRunner.class)
public class KennisgevingVerwerkerImplTest {

    public static final String ZENDENDE_PARTIJ = "67890";

    @InjectMocks
    private final KennisgevingVerwerker kennisgevingVerwerker = new KennisgevingVerwerkerImpl();

    @Mock
    private ApplicationContext applicationContext;

    @Mock
    private JdbcTemplate jdbcTemplate;

    private final SynchronisatieVerwerkPersoon synVerwerkMutatiePersoon = new SynchronisatieVerwerkPersoon();

    private final ObjectFactory objectFactory = new ObjectFactory();

    @Before
    public void setUp() throws Exception {
        final Field field = ReflectionUtils.findField(PhaseInterceptorChain.class, "CURRENT_MESSAGE");
        Assert.assertNotNull(field);
        ReflectionUtils.makeAccessible(field);
        final ThreadLocal<Message> threadLocal = (ThreadLocal<Message>) field.get(null);
        final Message messageMock = Mockito.mock(Message.class);
        threadLocal.set(messageMock);

        maakStuurgegevens();
        maakParameters();
        maakSynchronisatie();
    }

    @Test
    public void testVerwerkKennisgeving() {
        kennisgevingVerwerker.verwerkKennisgeving(synVerwerkMutatiePersoon);
    }

    @Test
    public void testVerwerkKennisgevingPersistent() {
        ReflectionTestUtils.setField(kennisgevingVerwerker, "isPersistent", true);
        Mockito.when(applicationContext.getBean("jdbcTemplate")).thenReturn(jdbcTemplate);
        kennisgevingVerwerker.verwerkKennisgeving(synVerwerkMutatiePersoon);
    }

    /**
     * Maakt parameters.
     */
    private void maakParameters() {
        final GroepBerichtParameters parameters = new GroepBerichtParameters();
        final NaamEnumeratiewaarde leveringsautorisatieNaam = new NaamEnumeratiewaarde();
        leveringsautorisatieNaam.setValue("Test leverings autorisatie");

        final LeveringsautorisatieID leveringsautorisatieID = objectFactory.createLeveringsautorisatieID();
        final DienstID dienstID = objectFactory.createDienstID();
        leveringsautorisatieID.setValue(new BigInteger("123"));
        final JAXBElement<LeveringsautorisatieID> jaxbElement = objectFactory
            .createGroepBerichtParametersLeveringsautorisatieIdentificatie(leveringsautorisatieID);
        parameters.setLeveringsautorisatieIdentificatie(jaxbElement);
        final JAXBElement<DienstID> jaxbElementDienst = objectFactory
            .createGroepBerichtParametersDienstIdentificatie(dienstID);
        parameters.setLeveringsautorisatieIdentificatie(jaxbElement);
        parameters.setDienstIdentificatie(jaxbElementDienst);
        synVerwerkMutatiePersoon.setParameters(objectFactory.createObjecttypeBerichtParameters(parameters));
    }

    private void maakSynchronisatie() {
        final PartijCode zendendePartij = new PartijCode();
        zendendePartij.setValue(ZENDENDE_PARTIJ);
        final ObjecttypeAdministratieveHandeling objecttypeAdministratieveHandeling = new ObjecttypeAdministratieveHandeling();
        final NaamEnumeratiewaarde naam = new NaamEnumeratiewaarde();
        naam.setValue("Test Administratieve Handeling");
        objecttypeAdministratieveHandeling.setNaam(objectFactory.createObjecttypeAdministratieveHandelingNaam(naam));
        final AdministratieveHandelingCode code = new AdministratieveHandelingCode();
        code.setValue("code");
        objecttypeAdministratieveHandeling.setCode(objectFactory.createObjecttypeAdministratieveHandelingCode(code));
        objecttypeAdministratieveHandeling.setPartijCode(objectFactory.createObjecttypePartijCode(zendendePartij));
        final JAXBElement<ObjecttypeAdministratieveHandeling> synchronisatie =
            objectFactory.createObjecttypeBerichtSynchronisatie(objecttypeAdministratieveHandeling);
        synVerwerkMutatiePersoon.setSynchronisatie(synchronisatie);

        final ContainerAdministratieveHandelingBijgehoudenPersonen containerAdministratieveHandelingBijgehoudenPersonen =
            new ContainerAdministratieveHandelingBijgehoudenPersonen();
        objecttypeAdministratieveHandeling.setBijgehoudenPersonen(
            objectFactory.createObjecttypeAdministratieveHandelingBijgehoudenPersonen(containerAdministratieveHandelingBijgehoudenPersonen));
        final ObjecttypePersoon persoon = new ObjecttypePersoon();
        containerAdministratieveHandelingBijgehoudenPersonen.getPersoon().add(persoon);

        final List<GroepPersoonIdentificatienummers> identificatienummers = persoon.getIdentificatienummers();
        final GroepPersoonIdentificatienummers groepIdentificatieNummers = new GroepPersoonIdentificatienummers();
        final Burgerservicenummer bsn = new Burgerservicenummer();
        bsn.setValue("111222333");
        groepIdentificatieNummers.setBurgerservicenummer(objectFactory.createGroepPersoonIdentificatienummersBurgerservicenummer(bsn));
        identificatienummers.add(groepIdentificatieNummers);

        persoon.setVerwerkingssoort(VerwerkingssoortS.IDENTIFICATIE);

        final List<GroepPersoonGeslachtsaanduiding> groepGeslachtsaanduiding = persoon.getGeslachtsaanduiding();
        final GroepPersoonGeslachtsaanduiding geslachtsaanduiding = new GroepPersoonGeslachtsaanduiding();
        final GeslachtsaanduidingCode geslachtsaanduidingCode = new GeslachtsaanduidingCode();
        geslachtsaanduidingCode.setValue(GeslachtsaanduidingCodeS.M);
        geslachtsaanduiding.setCode(objectFactory.createObjecttypeGeslachtsaanduidingCode(geslachtsaanduidingCode));
        groepGeslachtsaanduiding.add(geslachtsaanduiding);

        maakNaam(persoon);
    }

    private void maakNaam(final ObjecttypePersoon persoon) {
        final ContainerPersoonGeslachtsnaamcomponenten containerPersoonGeslachtsnaamcomponenten = new ContainerPersoonGeslachtsnaamcomponenten();
        final JAXBElement<ContainerPersoonGeslachtsnaamcomponenten> geslachtsnaamcomponenten =
            objectFactory.createObjecttypePersoonGeslachtsnaamcomponenten(containerPersoonGeslachtsnaamcomponenten);

        final ObjecttypePersoonGeslachtsnaamcomponent geslachtsnaamcomponent = new ObjecttypePersoonGeslachtsnaamcomponent();
        final Voorvoegsel voorvoegsel = new Voorvoegsel();
        voorvoegsel.setValue("de");
        geslachtsnaamcomponent.setVoorvoegsel(objectFactory.createObjecttypePersoonGeslachtsnaamcomponentVoorvoegsel(voorvoegsel));
        final Scheidingsteken scheidingsteken = new Scheidingsteken();
        scheidingsteken.setValue("");
        geslachtsnaamcomponent.setScheidingsteken(objectFactory.createObjecttypePersoonGeslachtsnaamcomponentScheidingsteken(scheidingsteken));

        final Geslachtsnaamstam stam = new Geslachtsnaamstam();
        stam.setValue("Testparel");
        geslachtsnaamcomponent.setStam(objectFactory.createObjecttypePersoonGeslachtsnaamcomponentStam(stam));
        containerPersoonGeslachtsnaamcomponenten.getGeslachtsnaamcomponent().add(geslachtsnaamcomponent);
        persoon.setGeslachtsnaamcomponenten(geslachtsnaamcomponenten);

        final ContainerPersoonVoornamen containerPersoonVoornamen = new ContainerPersoonVoornamen();
        final JAXBElement<ContainerPersoonVoornamen> voornamen = objectFactory.createObjecttypePersoonVoornamen(containerPersoonVoornamen);
        final Voornaam voornaam = new Voornaam();
        voornaam.setValue("Karel");
        final ObjecttypePersoonVoornaam voornaamObject = new ObjecttypePersoonVoornaam();
        voornaamObject.setNaam(objectFactory.createObjecttypePersoonVoornaamNaam(voornaam));
        containerPersoonVoornamen.getVoornaam().add(voornaamObject);
        voornamen.setValue(containerPersoonVoornamen);

        persoon.setVoornamen(voornamen);
    }

    /**
     * Maakt stuurgegevens.
     */
    private void maakStuurgegevens() {
        final GroepBerichtStuurgegevens groepBerichtStuurgegevens = new GroepBerichtStuurgegevens();
        final PartijCode ontvangendePartij = new PartijCode();
        ontvangendePartij.setValue("12345");
        groepBerichtStuurgegevens.setOntvangendePartij(objectFactory.createObjecttypePartijCode(ontvangendePartij));

        final PartijCode zendendePartij = new PartijCode();
        zendendePartij.setValue(ZENDENDE_PARTIJ);
        groepBerichtStuurgegevens.setZendendePartij(objectFactory.createObjecttypePartijCode(zendendePartij));

        final SysteemNaam zendendeSysteem = new SysteemNaam();
        zendendeSysteem.setValue("TESTSYSTEEM");
        groepBerichtStuurgegevens.setZendendeSysteem(objectFactory.createGroepBerichtStuurgegevensZendendeSysteem(zendendeSysteem));

        final Referentienummer referentieNummer = new Referentienummer();
        referentieNummer.setValue("d09b63f0-1a78-4ff9-8ad1-2b57529052cd");
        groepBerichtStuurgegevens.setReferentienummer(objectFactory.createGroepBerichtStuurgegevensReferentienummer(referentieNummer));

        final JAXBElement<GroepBerichtStuurgegevens> stuurgegevens = objectFactory.createObjecttypeBerichtStuurgegevens(groepBerichtStuurgegevens);
        synVerwerkMutatiePersoon.setStuurgegevens(stuurgegevens);
    }
}

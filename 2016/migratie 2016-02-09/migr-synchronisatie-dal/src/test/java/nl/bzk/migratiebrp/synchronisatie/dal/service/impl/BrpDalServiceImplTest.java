/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.blokkering.entity.Blokkering;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.blokkering.entity.RedenBlokkering;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Gemeente;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3Bericht;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3BerichtenBron;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Nationaliteit;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Partij;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.BlokkeringRepository;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.Lo3BerichtRepository;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.PersoonRepository;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.StamtabelRepository;
import nl.bzk.migratiebrp.synchronisatie.dal.service.SyncParameters;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BrpDalServiceImplTest {

    private static final String GEMEENTE_0599 = "0599";
    private static final String GEMEENTE_0600 = "0600";
    private static final RedenBlokkering VERHUIZEND_VAN_LO3_NAAR_BRP = RedenBlokkering.VERHUIZEND_VAN_LO3_NAAR_BRP;

    @Mock
    private PersoonRepository repoMock;
    @Mock
    private DynamischeStamtabelRepository dynamischeStamtabelRepositoryMock;
    @Mock
    private Lo3BerichtRepository berichtLogRepositoryMock;
    @Mock
    private StamtabelRepository stamtabelRepositoryMock;
    @Mock
    private BlokkeringRepository blokkeringRepository;
    //TODO BRM44
//    @Mock
//    private AbonnementRepository abonnementRepositoryMock;
    @Spy
    private final SyncParameters syncParameters = new SyncParameters();
    @InjectMocks
    private BrpDalServiceImpl service;

    private Lo3Bericht lo3Bericht;

    @Before
    public void setup() {
        syncParameters.setInitieleVulling(false);
        lo3Bericht =
                new Lo3Bericht("BrpDalServiceImplTest", Lo3BerichtenBron.INITIELE_VULLING, new Timestamp(System.currentTimeMillis()), "TEST_DATA", true);
    }

    @Test
    @Ignore("Test uitgezet ivm aanpassingen 3.1 situatie. Aanzetten als 3.1 ontwikkeleing klaar is")
    public void testPersisteerPersoon() {
        Mockito.when(dynamischeStamtabelRepositoryMock.getPartijByCode(BrpPartijCode.MIGRATIEVOORZIENING.getWaarde())).thenReturn(
            new Partij("Migratievoorziening", BrpPartijCode.MIGRATIEVOORZIENING.getWaarde()));
        Mockito.when(dynamischeStamtabelRepositoryMock.getNationaliteitByNationaliteitcode((short) 1)).thenReturn(new Nationaliteit("One",
                                                                                                                                    (short) 1));

        final BrpPersoonslijst brpPersoonslijst = maakBrpPersoonslijst();
        service.persisteerPersoonslijst(brpPersoonslijst, lo3Bericht);

        final ArgumentCaptor<Persoon> persoonArgument = ArgumentCaptor.forClass(Persoon.class);
        Mockito.verify(repoMock).save(persoonArgument.capture());
        controleerPersoonEntiteit(persoonArgument.getValue(), null);
    }

    @Test
    @Ignore("Test uitgezet ivm aanpassingen 3.1 situatie. Aanzetten als 3.1 ontwikkeleing klaar is")
    public void persisteerPersoonslijstAnummerVervangenIllegalArgument() {
        Mockito.when(dynamischeStamtabelRepositoryMock.getPartijByCode(BrpPartijCode.MIGRATIEVOORZIENING.getWaarde())).thenReturn(
            new Partij("Migratievoorziening", BrpPartijCode.MIGRATIEVOORZIENING.getWaarde()));
        Mockito.when(dynamischeStamtabelRepositoryMock.getNationaliteitByNationaliteitcode((short) 1)).thenReturn(new Nationaliteit("One", (short) 1));
        final long anummer = 1234567890L;
        try {
            service.persisteerPersoonslijst(maakBrpPersoonslijst(), anummer, false, lo3Bericht);
        } catch (final IllegalArgumentException iae) {
            Assert.assertNull(iae.getMessage());
        }
    }

    @Test
    @Ignore("Test uitgezet ivm aanpassingen 3.1 situatie. Aanzetten als 3.1 ontwikkeleing klaar is")
    public void persisteerPersoonslijstAnummerVervangen() {
        Mockito.when(dynamischeStamtabelRepositoryMock.getPartijByCode(BrpPartijCode.MIGRATIEVOORZIENING.getWaarde())).thenReturn(
            new Partij("Migratievoorziening", BrpPartijCode.MIGRATIEVOORZIENING.getWaarde()));
        Mockito.when(dynamischeStamtabelRepositoryMock.getNationaliteitByNationaliteitcode((short) 1)).thenReturn(new Nationaliteit("One", (short) 1));
        final long anummer = 1234567890L;
        try {

            service.persisteerPersoonslijst(maakBrpPersoonslijst(), anummer, false, lo3Bericht);
            final ArgumentCaptor<Persoon> persoonArgument = ArgumentCaptor.forClass(Persoon.class);
            Mockito.verify(repoMock).save(persoonArgument.capture());
            controleerPersoonEntiteit(persoonArgument.getValue(), anummer);
        } catch (final IllegalArgumentException iae) {
            Assert.assertNull(iae.getMessage());
        }
    }

    @Test(expected = NullPointerException.class)
    public void persisteerPersoonslijstAnummerNull() {
        service.persisteerPersoonslijst(maakBrpPersoonslijst(), null);
        Assert.fail("Er wordt een NullPointerException verwacht");
    }

    @Test
    public void testPersisteerBerichtLog() {
        final Lo3Bericht bericht = Mockito.mock(Lo3Bericht.class);
        service.persisteerLo3Bericht(bericht);

        final ArgumentCaptor<Lo3Bericht> lo3BerichtArgument = ArgumentCaptor.forClass(Lo3Bericht.class);
        Mockito.verify(berichtLogRepositoryMock).save(lo3BerichtArgument.capture());
    }

    @Test
    public void testZoekBerichtLogAnrs() {
        try {
            final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            dateFormat.setLenient(false);
            final Date vanaf = dateFormat.parse("20020708");
            final Date tot = dateFormat.parse("20120709");
            final Set<Long> anummers = new HashSet<>();
            anummers.add((long) 123456789);
            Mockito.when(berichtLogRepositoryMock.findLaatsteBerichtLogAnrs(vanaf, tot)).thenReturn(anummers);
            final Set<Long> gevondenAnrs = service.zoekBerichtLogAnrs(vanaf, tot);
            Assert.assertNotNull(gevondenAnrs);
            Assert.assertEquals(1, gevondenAnrs.size());
            Assert.assertTrue(gevondenAnrs.contains(Long.valueOf(123456789L)));
        } catch (final ParseException e) {
            Assert.fail("Er zou geen fout op moeten treden.");
        }
    }

    @Test
    public void testZoekBerichtLogOpAnummer() {
        final long anummer = 123456789L;
        Mockito.when(berichtLogRepositoryMock.findLaatsteLo3PersoonslijstBerichtVoorANummer(anummer))
               .thenReturn(
            new Lo3Bericht("Referentie", Lo3BerichtenBron.INITIELE_VULLING, new Timestamp(System.currentTimeMillis()), " ", true));
        final Lo3Bericht gevondenLog = service.zoekLo3PeroonslijstBerichtOpAnummer(anummer);
        Assert.assertNotNull(gevondenLog);
        Assert.assertEquals(gevondenLog.getReferentie(), "Referentie");
    }

    @Test(expected = NullPointerException.class)
    public void testPersisteerBerichtLogNull() {
        service.persisteerLo3Bericht(null);
        Assert.fail("Er wordt een NullPointerException verwacht");
    }

    @Test
    public void testPersisteerBlokkeringAlGeblokkeerd() {

        final Long aNummerTePersisterenBlokkering = 1234567890L;
        final Blokkering tePersisterenBlokkering =
                new Blokkering(aNummerTePersisterenBlokkering, 1L, GEMEENTE_0599, GEMEENTE_0600, VERHUIZEND_VAN_LO3_NAAR_BRP, new Timestamp(
                    System.currentTimeMillis()));

        final Blokkering gepersisteerdeBlokkering =
                new Blokkering(aNummerTePersisterenBlokkering, 1L, GEMEENTE_0599, GEMEENTE_0600, VERHUIZEND_VAN_LO3_NAAR_BRP, new Timestamp(
                    System.currentTimeMillis()));

        Mockito.when(blokkeringRepository.statusBlokkering(aNummerTePersisterenBlokkering)).thenReturn(gepersisteerdeBlokkering);

        try {
            service.persisteerBlokkering(tePersisterenBlokkering);
        } catch (final IllegalStateException exception) {
            Assert.assertEquals("De persoonlijst met het opgegeven aNummer is al geblokkeerd.", exception.getMessage());
        }
    }

    @Test
    public void testVraagOpBlokkering() {

        final Long aNummerTePersisterenBlokkering = 1234567890L;
        final Blokkering gepersisteerdeBlokkering =
                new Blokkering(aNummerTePersisterenBlokkering, 1L, GEMEENTE_0599, GEMEENTE_0600, VERHUIZEND_VAN_LO3_NAAR_BRP, new Timestamp(
                    System.currentTimeMillis()));

        Mockito.when(blokkeringRepository.statusBlokkering(aNummerTePersisterenBlokkering)).thenReturn(gepersisteerdeBlokkering);

        Assert.assertNotNull(service.vraagOpBlokkering(aNummerTePersisterenBlokkering));

    }

    @Test
    public void testVraagOpBlokkeringGeenANummer() {

        try {
            service.vraagOpBlokkering(null);
        } catch (final IllegalStateException exception) {
            Assert.assertEquals("Er is geen aNummer opgegeven.", exception.getMessage());
        }
    }

    @Test
    public void testVerwijderBlokkering() {

        final Long aNummerTeVerwijderenBlokkering = 1234567890L;
        final Blokkering teVerwijderenBlokkering =
                new Blokkering(aNummerTeVerwijderenBlokkering, 1L, GEMEENTE_0599, GEMEENTE_0600, VERHUIZEND_VAN_LO3_NAAR_BRP, new Timestamp(
                    System.currentTimeMillis()));

        final Blokkering controleBlokkering =
                new Blokkering(aNummerTeVerwijderenBlokkering, 1L, GEMEENTE_0599, GEMEENTE_0600, VERHUIZEND_VAN_LO3_NAAR_BRP, new Timestamp(
                    System.currentTimeMillis()));

        Mockito.when(blokkeringRepository.statusBlokkering(aNummerTeVerwijderenBlokkering)).thenReturn(controleBlokkering);

        service.verwijderBlokkering(teVerwijderenBlokkering);
    }

    @Test
    public void testVerwijderBlokkeringGeenBlokkering() {
        final Long aNummerTeVerwijderenBlokkering = 1234567890L;
        final Blokkering teVerwijderenBlokkering =
                new Blokkering(aNummerTeVerwijderenBlokkering, 1L, GEMEENTE_0599, GEMEENTE_0600, VERHUIZEND_VAN_LO3_NAAR_BRP, new Timestamp(
                    System.currentTimeMillis()));

        Mockito.when(blokkeringRepository.statusBlokkering(aNummerTeVerwijderenBlokkering)).thenReturn(null);

        try {
            service.verwijderBlokkering(teVerwijderenBlokkering);
        } catch (final IllegalStateException exception) {
            Assert.assertEquals("De persoonlijst met het opgegeven aNummer is niet geblokkeerd.", exception.getMessage());
        }
    }

    @Test
    public void testVerwijderBlokkeringOngeldigProcesId() {
        final Long aNummerTeVerwijderenBlokkering = 1234567890L;
        final Blokkering teVerwijderenBlokkering =
                new Blokkering(aNummerTeVerwijderenBlokkering, 2L, GEMEENTE_0599, GEMEENTE_0600, VERHUIZEND_VAN_LO3_NAAR_BRP, new Timestamp(
                    System.currentTimeMillis()));

        final Blokkering controleBlokkering =
                new Blokkering(aNummerTeVerwijderenBlokkering, 1L, GEMEENTE_0599, GEMEENTE_0600, VERHUIZEND_VAN_LO3_NAAR_BRP, new Timestamp(
                    System.currentTimeMillis()));

        Mockito.when(blokkeringRepository.statusBlokkering(aNummerTeVerwijderenBlokkering)).thenReturn(controleBlokkering);

        try {
            service.verwijderBlokkering(teVerwijderenBlokkering);
        } catch (final IllegalStateException exception) {
            Assert.assertEquals("Het process ID komt niet overeen met het process ID waarmee de persoonslijst is geblokkeerd.", exception.getMessage());
        }
    }

    @Test
    public void testVerwijderBlokkeringOngeldigeRegistratiegemeente() {
        final Long aNummerTeVerwijderenBlokkering = 1234567890L;
        final Blokkering teVerwijderenBlokkering =
                new Blokkering(aNummerTeVerwijderenBlokkering, 1L, GEMEENTE_0599, GEMEENTE_0600, VERHUIZEND_VAN_LO3_NAAR_BRP, new Timestamp(
                    System.currentTimeMillis()));

        final Blokkering controleBlokkering =
                new Blokkering(aNummerTeVerwijderenBlokkering, 1L, GEMEENTE_0599, "1906", VERHUIZEND_VAN_LO3_NAAR_BRP, new Timestamp(
                    System.currentTimeMillis()));

        Mockito.when(blokkeringRepository.statusBlokkering(aNummerTeVerwijderenBlokkering)).thenReturn(controleBlokkering);

        try {
            service.verwijderBlokkering(teVerwijderenBlokkering);
        } catch (final IllegalStateException exception) {
            Assert.assertEquals(
                "De registratiegemeente komt niet overeen met de registratiegemeent die de persoonslijst " + "heeft geblokkeerd.",
                exception.getMessage());
        }
    }

    private BrpPersoonslijst maakBrpPersoonslijst() {
        return new BrpPersoonslijstTestDataBuilder().addDefaultTestStapels()
                                                    .addGroepMetHistorieA(
                                                        new BrpNationaliteitInhoud(
                                                            new BrpNationaliteitCode(Short.parseShort("1")),
                                                            null,
                                                            null,
                                                            null,
                                                            null,
                                                            null,
                                                            null))
                                                    .build();
    }

    private void controleerPersoonEntiteit(final Persoon persoon, final Long anummer) {
        Assert.assertNotNull(persoon);
        Long expected = BrpPersoonslijstTestDataBuilder.DEFAULT_ADMINISTRATIENUMMER.getWaarde();
        if (anummer != null) {
            expected = anummer;
        }
        Assert.assertEquals(expected, persoon.getAdministratienummer());
    }

    @Test
    public void testGeefAlleGemeenten() {
        final List<Gemeente> gemeenten = new ArrayList<>();
        gemeenten.add(new Gemeente((short) 1, "Gemeente", (short) 599, new Partij("Partij", 599010)));

        Mockito.when(stamtabelRepositoryMock.findAllGemeentes()).thenReturn(gemeenten);

        final Collection<Gemeente> result = service.geefAlleGemeenten();

        Assert.assertSame(gemeenten, result);

    }

    @Test
    @Ignore
    public void testGeefAlleAbonnementen() {
        //TODO BRM44
//        final List<ToegangAbonnement> abonnementen = new ArrayList<>();
//        abonnementen.add(new ToegangAbonnement(new Partij("Partij", 599010), new Abonnement("Test Abonnement")));
//
//        Mockito.when(abonnementRepositoryMock.findAllActieveLo3Abonnementen()).thenReturn(abonnementen);
//
//        final Collection<ToegangAbonnement> result = service.geefAlleAbonnementen();
//
//        Assert.assertSame(abonnementen, result);

    }

}

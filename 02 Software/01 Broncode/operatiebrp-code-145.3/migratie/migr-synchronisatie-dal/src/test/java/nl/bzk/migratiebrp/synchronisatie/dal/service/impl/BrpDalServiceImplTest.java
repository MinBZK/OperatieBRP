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
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Blokkering;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Bericht;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Lo3BerichtenBron;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.RedenBlokkering;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.BlokkeringRepository;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.Lo3BerichtRepository;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.StamtabelRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BrpDalServiceImplTest {

    private static final String GEMEENTE_0599 = "059901";
    private static final String GEMEENTE_0600 = "060001";

    @Mock
    private Lo3BerichtRepository berichtLogRepositoryMock;
    @Mock
    private StamtabelRepository stamtabelRepositoryMock;
    @Mock
    private BlokkeringRepository blokkeringRepository;

    @InjectMocks
    private BrpDalServiceImpl service;

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
            final Set<String> anummers = new HashSet<>();
            anummers.add("123456789");
            Mockito.when(berichtLogRepositoryMock.findLaatsteBerichtLogAnrs(vanaf, tot)).thenReturn(anummers);
            final Set<String> gevondenAnrs = service.zoekBerichtLogAnrs(vanaf, tot);
            Assert.assertNotNull(gevondenAnrs);
            Assert.assertEquals(1, gevondenAnrs.size());
            Assert.assertTrue(gevondenAnrs.contains("123456789"));
        } catch (final ParseException e) {
            Assert.fail("Er zou geen fout op moeten treden.");
        }
    }

    @Test
    public void testZoekBerichtLogOpAnummer() {
        final String anummer = "123456789";
        Mockito.when(berichtLogRepositoryMock.findLaatsteLo3PersoonslijstBerichtVoorANummer(anummer))
                .thenReturn(new Lo3Bericht("Referentie", Lo3BerichtenBron.INITIELE_VULLING, new Timestamp(System.currentTimeMillis()), " ", true));
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

        final String aNummerTePersisterenBlokkering = "1234567890";
        final Blokkering tePersisterenBlokkering = new Blokkering(aNummerTePersisterenBlokkering, new Timestamp(System.currentTimeMillis()));
        tePersisterenBlokkering.setProcessId(1L);
        tePersisterenBlokkering.setGemeenteCodeNaar(GEMEENTE_0599);
        tePersisterenBlokkering.setRegistratieGemeente(GEMEENTE_0600);
        tePersisterenBlokkering.setRedenBlokkering(RedenBlokkering.VERHUIZEND_VAN_LO3_NAAR_BRP);

        final Blokkering gepersisteerdeBlokkering = new Blokkering(aNummerTePersisterenBlokkering, new Timestamp(System.currentTimeMillis()));
        gepersisteerdeBlokkering.setProcessId(1L);
        gepersisteerdeBlokkering.setGemeenteCodeNaar(GEMEENTE_0599);
        gepersisteerdeBlokkering.setRegistratieGemeente(GEMEENTE_0600);
        gepersisteerdeBlokkering.setRedenBlokkering(RedenBlokkering.VERHUIZEND_VAN_LO3_NAAR_BRP);

        Mockito.when(blokkeringRepository.statusBlokkering(aNummerTePersisterenBlokkering)).thenReturn(gepersisteerdeBlokkering);

        try {
            service.persisteerBlokkering(tePersisterenBlokkering);
        } catch (final IllegalStateException exception) {
            Assert.assertEquals("De persoonlijst met het opgegeven aNummer is al geblokkeerd.", exception.getMessage());
        }
    }

    @Test
    public void testVraagOpBlokkering() {

        final String aNummerTePersisterenBlokkering = "1234567890";
        final Blokkering gepersisteerdeBlokkering = new Blokkering(aNummerTePersisterenBlokkering, new Timestamp(System.currentTimeMillis()));
        gepersisteerdeBlokkering.setProcessId(1L);
        gepersisteerdeBlokkering.setGemeenteCodeNaar(GEMEENTE_0599);
        gepersisteerdeBlokkering.setRegistratieGemeente(GEMEENTE_0600);
        gepersisteerdeBlokkering.setRedenBlokkering(RedenBlokkering.VERHUIZEND_VAN_LO3_NAAR_BRP);
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
        final String aNummerTeVerwijderenBlokkering = "1234567890";
        final Blokkering teVerwijderenBlokkering = new Blokkering(aNummerTeVerwijderenBlokkering, new Timestamp(System.currentTimeMillis()));
        teVerwijderenBlokkering.setProcessId(1L);
        teVerwijderenBlokkering.setGemeenteCodeNaar(GEMEENTE_0599);
        teVerwijderenBlokkering.setRegistratieGemeente(GEMEENTE_0600);
        teVerwijderenBlokkering.setRedenBlokkering(RedenBlokkering.VERHUIZEND_VAN_LO3_NAAR_BRP);

        final Blokkering controleBlokkering = new Blokkering(aNummerTeVerwijderenBlokkering, new Timestamp(System.currentTimeMillis()));
        controleBlokkering.setProcessId(1L);
        controleBlokkering.setGemeenteCodeNaar(GEMEENTE_0599);
        controleBlokkering.setRegistratieGemeente(GEMEENTE_0600);
        controleBlokkering.setRedenBlokkering(RedenBlokkering.VERHUIZEND_VAN_LO3_NAAR_BRP);

        Mockito.when(blokkeringRepository.statusBlokkering(aNummerTeVerwijderenBlokkering)).thenReturn(controleBlokkering);

        service.verwijderBlokkering(teVerwijderenBlokkering);
    }

    @Test
    public void testVerwijderBlokkeringGeenBlokkering() {
        final String aNummerTeVerwijderenBlokkering = "1234567890";
        final Blokkering teVerwijderenBlokkering = new Blokkering(aNummerTeVerwijderenBlokkering, new Timestamp(System.currentTimeMillis()));
        teVerwijderenBlokkering.setProcessId(1L);
        teVerwijderenBlokkering.setGemeenteCodeNaar(GEMEENTE_0599);
        teVerwijderenBlokkering.setRegistratieGemeente(GEMEENTE_0600);
        teVerwijderenBlokkering.setRedenBlokkering(RedenBlokkering.VERHUIZEND_VAN_LO3_NAAR_BRP);

        Mockito.when(blokkeringRepository.statusBlokkering(aNummerTeVerwijderenBlokkering)).thenReturn(null);

        try {
            service.verwijderBlokkering(teVerwijderenBlokkering);
        } catch (final IllegalStateException exception) {
            Assert.assertEquals("De persoonlijst met het opgegeven aNummer is niet geblokkeerd.", exception.getMessage());
        }
    }

    @Test
    public void testVerwijderBlokkeringOngeldigProcesId() {
        final String aNummerTeVerwijderenBlokkering = "1234567890";
        final Blokkering teVerwijderenBlokkering = new Blokkering(aNummerTeVerwijderenBlokkering, new Timestamp(System.currentTimeMillis()));
        teVerwijderenBlokkering.setProcessId(2L);
        teVerwijderenBlokkering.setGemeenteCodeNaar(GEMEENTE_0599);
        teVerwijderenBlokkering.setRegistratieGemeente(GEMEENTE_0600);
        teVerwijderenBlokkering.setRedenBlokkering(RedenBlokkering.VERHUIZEND_VAN_LO3_NAAR_BRP);

        final Blokkering controleBlokkering = new Blokkering(aNummerTeVerwijderenBlokkering, new Timestamp(System.currentTimeMillis()));
        controleBlokkering.setProcessId(1L);
        controleBlokkering.setGemeenteCodeNaar(GEMEENTE_0599);
        controleBlokkering.setRegistratieGemeente(GEMEENTE_0600);
        controleBlokkering.setRedenBlokkering(RedenBlokkering.VERHUIZEND_VAN_LO3_NAAR_BRP);

        Mockito.when(blokkeringRepository.statusBlokkering(aNummerTeVerwijderenBlokkering)).thenReturn(controleBlokkering);

        try {
            service.verwijderBlokkering(teVerwijderenBlokkering);
        } catch (final IllegalStateException exception) {
            Assert.assertEquals("Het process ID komt niet overeen met het process ID waarmee de persoonslijst is geblokkeerd.", exception.getMessage());
        }
    }

    @Test
    public void testVerwijderBlokkeringOngeldigeRegistratiegemeente() {
        final String aNummerTeVerwijderenBlokkering = "1234567890";
        final Blokkering teVerwijderenBlokkering = new Blokkering(aNummerTeVerwijderenBlokkering, new Timestamp(System.currentTimeMillis()));
        teVerwijderenBlokkering.setProcessId(1L);
        teVerwijderenBlokkering.setGemeenteCodeNaar(GEMEENTE_0599);
        teVerwijderenBlokkering.setRegistratieGemeente(GEMEENTE_0600);
        teVerwijderenBlokkering.setRedenBlokkering(RedenBlokkering.VERHUIZEND_VAN_LO3_NAAR_BRP);

        final Blokkering controleBlokkering = new Blokkering(aNummerTeVerwijderenBlokkering, new Timestamp(System.currentTimeMillis()));
        controleBlokkering.setProcessId(1L);
        controleBlokkering.setGemeenteCodeNaar(GEMEENTE_0599);
        controleBlokkering.setRegistratieGemeente("1906");
        controleBlokkering.setRedenBlokkering(RedenBlokkering.VERHUIZEND_VAN_LO3_NAAR_BRP);

        Mockito.when(blokkeringRepository.statusBlokkering(aNummerTeVerwijderenBlokkering)).thenReturn(controleBlokkering);

        try {
            service.verwijderBlokkering(teVerwijderenBlokkering);
        } catch (final IllegalStateException exception) {
            Assert.assertEquals(
                    "De registratiegemeente komt niet overeen met de registratiegemeent die de persoonslijst " + "heeft geblokkeerd.", exception.getMessage());
        }
    }

    @Test
    public void testGeefAlleGemeenten() {
        final List<Gemeente> gemeenten = new ArrayList<>();
        gemeenten.add(new Gemeente((short) 1, "Gemeente", "0599", new Partij("Partij", "599010")));

        Mockito.when(stamtabelRepositoryMock.findAllGemeentes()).thenReturn(gemeenten);

        final Collection<Gemeente> result = service.geefAlleGemeenten();

        Assert.assertSame(gemeenten, result);

    }
}

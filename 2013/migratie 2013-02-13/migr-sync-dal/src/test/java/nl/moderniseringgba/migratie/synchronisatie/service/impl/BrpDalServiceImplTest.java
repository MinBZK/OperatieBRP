/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpNationaliteitCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.moderniseringgba.migratie.conversie.validatie.InputValidationException;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Persoon;
import nl.moderniseringgba.migratie.synchronisatie.domein.logging.entity.BerichtLog;
import nl.moderniseringgba.migratie.synchronisatie.repository.BerichtLogRepository;
import nl.moderniseringgba.migratie.synchronisatie.repository.DynamischeStamtabelRepository;
import nl.moderniseringgba.migratie.synchronisatie.repository.PersoonRepository;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.relateren.PersoonRelateerder;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

@RunWith(MockitoJUnitRunner.class)
public class BrpDalServiceImplTest {

    @Mock
    private PersoonRepository repoMock;
    @Mock
    private DynamischeStamtabelRepository dynamischeStamtabelRepositoryMock;
    @Mock
    private BerichtLogRepository berichtLogRepositoryMock;
    @Mock
    private PersoonRelateerder persoonRelateerderMock;
    @InjectMocks
    private BrpDalServiceImpl service;

    @Test
    public void testPersisteerPersoon() throws InputValidationException {
        final BrpPersoonslijst brpPersoonslijst = maakBrpPersoonslijst();
        service.persisteerPersoonslijst(brpPersoonslijst);

        final ArgumentCaptor<Persoon> persoonArgument = ArgumentCaptor.forClass(Persoon.class);
        verify(repoMock).save(persoonArgument.capture());
        controleerPersoonEntiteit(persoonArgument.getValue(), null);
    }

    @Test
    public void persisteerPersoonslijstAnummerVervangenIllegalArgument() {
        final BigDecimal anummer = new BigDecimal(1234567890);
        try {
            when(persoonRelateerderMock.updateRelatiesVanPersoon(any(Persoon.class), any(BigDecimal.class)))
                    .thenThrow(new IllegalArgumentException());
            service.persisteerPersoonslijst(maakBrpPersoonslijst(), anummer);
        } catch (final InputValidationException ive) {
            fail("Geen InputValidationException verwacht");
        } catch (final IllegalArgumentException iae) {
            assertNull(iae.getMessage());
        }
    }

    @Test
    public void persisteerPersoonslijstAnummerVervangen() {
        final BigDecimal anummer = new BigDecimal(1234567890);
        try {
            when(persoonRelateerderMock.updateRelatiesVanPersoon(any(Persoon.class), any(BigDecimal.class)))
                    .thenAnswer(new Answer<Persoon>() {

                        @Override
                        public Persoon answer(final InvocationOnMock invocation) {
                            final Persoon persoon = (Persoon) invocation.getArguments()[0];
                            persoon.setAdministratienummer(anummer);
                            return persoon;
                        }
                    });
            service.persisteerPersoonslijst(maakBrpPersoonslijst(), anummer);
            final ArgumentCaptor<Persoon> persoonArgument = ArgumentCaptor.forClass(Persoon.class);
            verify(repoMock).save(persoonArgument.capture());
            controleerPersoonEntiteit(persoonArgument.getValue(), anummer);
        } catch (final InputValidationException ive) {
            fail("Geen InputValidationException verwacht");
        } catch (final IllegalArgumentException iae) {
            assertNull(iae.getMessage());
        }
    }

    @Test
    public void persisteerPersoonslijstAnummerNull() {
        try {
            service.persisteerPersoonslijst(maakBrpPersoonslijst(), null);
            fail("Er wordt een NullPointerException verwacht");
        } catch (final InputValidationException ive) {
            Assert.fail("Geen InputValidationException verwacht");
        } catch (final NullPointerException npe) {
            assertTrue(npe.getMessage() != null);
        }

    }

    @Test
    public void testPersisteerBerichtLog() throws InputValidationException {
        final BerichtLog berichtLog = mock(BerichtLog.class);
        service.persisteerBerichtLog(berichtLog);

        final ArgumentCaptor<BerichtLog> berichtLogArgument = ArgumentCaptor.forClass(BerichtLog.class);
        verify(berichtLogRepositoryMock).save(berichtLogArgument.capture());
    }

    @Test
    public void testZoekBerichtLogAnrs() throws InputValidationException {
        try {
            final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            dateFormat.setLenient(false);
            final Date vanaf = dateFormat.parse("20020708");
            final Date tot = dateFormat.parse("20120709");
            final List<Long> anummers = new ArrayList<Long>();
            anummers.add(Long.valueOf(123456789));
            Mockito.when(berichtLogRepositoryMock.findLaatsteBerichtLogAnrs(vanaf, tot, null)).thenReturn(anummers);
            final List<Long> gevondenAnrs = service.zoekBerichtLogAnrs(vanaf, tot, null);
            assertNotNull(gevondenAnrs);
            assertEquals(gevondenAnrs.get(0), Long.valueOf(123456789l));
        } catch (final ParseException e) {
            fail("Er zou geen fout op moeten treden.");
        }
    }

    @Test
    public void testZoekBerichtLogOpAnummer() throws InputValidationException {
        final long anummer = 123456789l;
        Mockito.when(berichtLogRepositoryMock.findLaatsteBerichtLogVoorANummer(BigDecimal.valueOf(anummer)))
                .thenReturn(new BerichtLog("Referentie", "GBA", new Timestamp(System.currentTimeMillis())));
        final BerichtLog gevondenLog = service.zoekBerichtLogOpAnummer(anummer);
        assertNotNull(gevondenLog);
        assertEquals(gevondenLog.getReferentie(), "Referentie");
    }

    @Test
    public void testPersisteerBerichtLogNull() {
        try {
            service.persisteerBerichtLog(null);
            fail("Er wordt een NullPointerException verwacht");
        } catch (final NullPointerException npe) {
            assertTrue(npe.getMessage() != null);
        }
    }

    private BrpPersoonslijst maakBrpPersoonslijst() throws InputValidationException {
        return new BrpPersoonslijstTestDataBuilder()
                .addDefaultTestStapels()
                .addGroepMetHistorieA(
                        new BrpNationaliteitInhoud(new BrpNationaliteitCode(Integer.valueOf("1")), null, null))
                .build();
    }

    private void controleerPersoonEntiteit(final Persoon persoon, final BigDecimal anummer) {
        assertNotNull(persoon);
        BigDecimal expected = new BigDecimal(BrpPersoonslijstTestDataBuilder.DEFAULT_ADMINISTRATIENUMMER);
        if (anummer != null) {
            expected = anummer;
        }
        assertEquals(expected, persoon.getAdministratienummer());
    }
}

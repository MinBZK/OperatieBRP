/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.stappen;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.brp.bijhouding.business.stappen.context.BijhoudingBerichtContext;
import nl.bzk.brp.bijhouding.business.stappen.resultaat.Resultaat;
import nl.bzk.brp.bijhouding.business.stappen.resultaat.ResultaatMelding;
import nl.bzk.brp.bijhouding.business.testconfig.BrpLockerTestConfig;
import nl.bzk.brp.configuratie.BrpBusinessConfiguratie;
import nl.bzk.brp.locking.BrpLocker;
import nl.bzk.brp.locking.LockingElement;
import nl.bzk.brp.locking.LockingMode;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.utils.junit.OverslaanBijInMemoryDatabase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { BrpLockerTestConfig.class })
@Category(OverslaanBijInMemoryDatabase.class)
public class LockStapMeldingTest extends AbstractStapTest {

    @Mock
    private BrpBusinessConfiguratie brpBusinessConfiguratie;

    @Mock
    private BijhoudingBerichtContext context;

    @Inject
    private BrpLocker brpLocker;

    private LockStap           lockStap;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        lockStap = new LockStap();

        ReflectionTestUtils.setField(lockStap, "brpLocker", brpLocker);
        ReflectionTestUtils.setField(lockStap, "brpBusinessConfiguratie", brpBusinessConfiguratie);

        when(brpBusinessConfiguratie.getLockingTimeOutProperty()).thenReturn(3);

        when(context.getIngaandBerichtId()).thenReturn(2L);
        when(context.getLockingElement()).thenReturn(LockingElement.PERSOON);
        when(context.getLockingMode()).thenReturn(LockingMode.EXCLUSIVE);
        when(context.getLockingIds()).thenReturn(Arrays.asList(1435, 3242312, 4523));
    }

    @Test
    public void testReadWriteMetAfwijking() {
        final List<ResultaatMelding> meldingen = new ArrayList<>();

        try {
            try {
                // Eerst vergrendelen
                lockStap.vergrendel(context);
            } catch (Exception e) {
                throw new IllegalStateException("Fout in zetten lock ter voorbereiding van test.", e);
            }

            Thread myThread = new Thread() {

                @Override
                public void run() {
                    try {
                        // Nog een keer vergrendelen (in een aparte thread) moet tot een foutmelding leiden
                        Resultaat resultaat = lockStap.vergrendel(context);
                        // Deze melding voegen we toe aan een lijst, om later te controleren
                        meldingen.addAll(resultaat.getMeldingen());
                    } finally {
                        lockStap.ontgrendel();
                    }
                }

            };

            myThread.start();
            myThread.join();

            assertEquals(1, meldingen.size());
            assertEquals(ResultaatMelding.builder()
                    .withSoort(SoortMelding.FOUT)
                    .withRegel(Regel.ALG0001)
                    .withReferentieID("onbekend")
                    .withMeldingTekst("De personen in bericht met ID 2 worden gelijktijdig door een ander bericht geraadpleegd of gemuteerd").build(),
                meldingen.get(0));

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lockStap.ontgrendel();
        }
    }

    @After
    public void cleanup() {
        brpLocker.unLock();
    }
}

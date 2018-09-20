/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatieverwerker.stap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import nl.bzk.brp.business.stappen.StappenResultaat;
import nl.bzk.brp.business.stappen.locking.BsnLocker;
import nl.bzk.brp.levering.mutatieverwerker.model.AdministratieveHandelingMutatie;
import nl.bzk.brp.levering.mutatieverwerker.service.AdministratieveHandelingVerwerkingContext;
import nl.bzk.brp.levering.mutatieverwerker.service.AdministratieveHandelingVerwerkingResultaat;
import nl.bzk.brp.model.validatie.Melding;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;


public class LockStapTest extends AbstractStappenTest {

    @Mock
    private AdministratieveHandelingMutatie mutatie;

    @Mock
    private BsnLocker bsnLocker;

    private BetrokkenBsnLockStap lockStap;

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test
    public void testVoerStapUitVoorMutatie() {
        List<Melding> meldingen = new ArrayList<Melding>();
        AdministratieveHandelingVerwerkingResultaat resultaat =
                new AdministratieveHandelingVerwerkingResultaat(meldingen);
        ArgumentCaptor<Collection> readLocks = ArgumentCaptor.forClass(Collection.class);
        ArgumentCaptor<Collection> writeLocks = ArgumentCaptor.forClass(Collection.class);
        ArgumentCaptor<Long> berichtContext = ArgumentCaptor.forClass(Long.class);

        lockStap.voerStapUit(mutatie, new AdministratieveHandelingVerwerkingContext(), resultaat);

        Mockito.verify(bsnLocker).getLocks(readLocks.capture(), writeLocks.capture(), berichtContext.capture());

        Assert.assertEquals(mutatie.getReadBsnLocks(), readLocks.getValue());
        Assert.assertEquals(mutatie.getWriteBsnLocks(), writeLocks.getValue());

    }

    @Test
    public void testNaVerwerkingsStap() {
        List<Melding> meldingen = new ArrayList<Melding>();
        StappenResultaat resultaat = new AdministratieveHandelingVerwerkingResultaat(meldingen);
        lockStap.voerNabewerkingStapUit(mutatie, new AdministratieveHandelingVerwerkingContext(),
                                              (AdministratieveHandelingVerwerkingResultaat) resultaat);
        Mockito.verify(bsnLocker).unLock();
    }

    @Before
    public final void initMocksEnService() {
        MockitoAnnotations.initMocks(this);
        lockStap = new BetrokkenBsnLockStap();

        mutatie = new AdministratieveHandelingMutatie(1L, null);


        ReflectionTestUtils.setField(lockStap, "bsnLocker", bsnLocker);
    }

}

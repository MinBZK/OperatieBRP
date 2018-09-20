/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.handlers;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;

import nl.bzk.brp.bevraging.business.dto.BerichtContext;
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtAntwoord;
import nl.bzk.brp.bevraging.business.dto.verzoek.BerichtVerzoek;
import nl.bzk.brp.bevraging.business.service.BsnLocker;
import nl.bzk.brp.bevraging.domein.ber.SoortBericht;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;


public class LockStapTest {

    @Mock
    private BerichtAntwoord                 antwoord;

    @Mock
    private BsnLocker                       bsnLocker;

    private LockStap                        lockStap;

    private BerichtContext                  context;

    private BerichtVerzoek<BerichtAntwoord> verzoek;

    @Test
    public void testVoerVerwerkingsStapUitVoorBericht() {

        ArgumentCaptor<Long> berichtId = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Collection> readLocks = ArgumentCaptor.forClass(Collection.class);
        ArgumentCaptor<Collection> writeLocks = ArgumentCaptor.forClass(Collection.class);

        lockStap.voerVerwerkingsStapUitVoorBericht(verzoek, context, antwoord);

        Mockito.verify(bsnLocker).getLocks(berichtId.capture(), readLocks.capture(), writeLocks.capture());

        assertEquals(context.getIngaandBerichtId(), berichtId.getValue());

        assertEquals(verzoek.getReadBSNLocks(), readLocks.getValue());
        assertEquals(verzoek.getWriteBSNLocks(), writeLocks.getValue());

    }

    @Test
    public void testNaVerwerkingsStap() {
        lockStap.naVerwerkingsStapVoorBericht(verzoek, context);
        Mockito.verify(bsnLocker).unLock();
    }

    @Before
    public final void initMocksEnService() {
        MockitoAnnotations.initMocks(this);

        context = new BerichtContext();

        context.setIngaandBerichtId(42L);

        lockStap = new LockStap();

        verzoek = getBerichtVerzoek();

        ReflectionTestUtils.setField(lockStap, "bsnLocker", bsnLocker);
    }

    private BerichtVerzoek<BerichtAntwoord> getBerichtVerzoek() {
        return new BerichtVerzoek<BerichtAntwoord>() {

            @Override
            public Calendar getBeschouwing() {
                return null;
            }

            @Override
            public Collection<Long> getReadBSNLocks() {
                return Arrays.asList(1435L, 3242312L, 4523L);
            }

            @Override
            public Collection<Long> getWriteBSNLocks() {
                return Arrays.asList(43524L, 324133L, 1234L);
            }

            @Override
            public SoortBericht getSoortBericht() {
                return SoortBericht.OPVRAGEN_PERSOON_VRAAG;
            }

            @Override
            public Class<BerichtAntwoord> getAntwoordClass() {
                return null;
            }

        };

    }

}

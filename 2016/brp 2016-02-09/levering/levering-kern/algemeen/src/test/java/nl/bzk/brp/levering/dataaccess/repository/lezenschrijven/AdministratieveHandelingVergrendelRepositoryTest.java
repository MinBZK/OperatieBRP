/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.dataaccess.repository.lezenschrijven;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceException;

import nl.bzk.brp.levering.dataaccess.repository.lezenenschrijven.impl.AdministratieveHandelingVergrendelRepositoryImpl;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingLeveringGroepModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class AdministratieveHandelingVergrendelRepositoryTest {

    private static final long ADMINISTRATIEVE_HANDELING_ID = 5;

    @Mock
    private EntityManager em;

    @InjectMocks
    private AdministratieveHandelingVergrendelRepositoryImpl administratieveHandelingLockRepository;

    private AdministratieveHandelingModel administratieveHandelingModel;

    private final Map<String, Object> emProperties = new HashMap<>();

    @Before
    public final void setup() {
        emProperties.put("javax.persistence.lock.timeout", 0);
        administratieveHandelingModel = mock(AdministratieveHandelingModel.class);
        when(em.find(AdministratieveHandelingModel.class, ADMINISTRATIEVE_HANDELING_ID, LockModeType.PESSIMISTIC_WRITE,
                emProperties)).thenReturn(administratieveHandelingModel);
    }

    @Test
    public final void testHandelingIsNietVerwerktEnLockKanWordenGeplaatst() {
        final boolean resultaat =
                administratieveHandelingLockRepository.vergrendelAlsNogNietIsVerwerkt(ADMINISTRATIEVE_HANDELING_ID);

        assertTrue(resultaat);
    }

    @Test
    public final void testHandelingIsAlVerwerkt() {
        final AdministratieveHandelingLeveringGroepModel reedsVerwerkteLevering =
                new AdministratieveHandelingLeveringGroepModel(new DatumTijdAttribuut(new Date()));
        when(administratieveHandelingModel.getLevering()).thenReturn(reedsVerwerkteLevering);

        final boolean resultaat =
                administratieveHandelingLockRepository.vergrendelAlsNogNietIsVerwerkt(ADMINISTRATIEVE_HANDELING_ID);

        assertFalse(resultaat);
    }

    @Test
    public final void testHandelingIsAlGelockt() {
        when(em.find(AdministratieveHandelingModel.class, ADMINISTRATIEVE_HANDELING_ID, LockModeType.PESSIMISTIC_WRITE,
                emProperties)).thenThrow(PersistenceException.class);

        final boolean resultaat =
                administratieveHandelingLockRepository.vergrendelAlsNogNietIsVerwerkt(ADMINISTRATIEVE_HANDELING_ID);

        assertFalse(resultaat);
    }

    @Test
    public final void testHandelingIsNull() {
        when(em.find(AdministratieveHandelingModel.class, ADMINISTRATIEVE_HANDELING_ID, LockModeType.PESSIMISTIC_WRITE,
                emProperties)).thenReturn(null);

        final boolean resultaat =
                administratieveHandelingLockRepository.vergrendelAlsNogNietIsVerwerkt(ADMINISTRATIEVE_HANDELING_ID);

        assertFalse(resultaat);
    }
}

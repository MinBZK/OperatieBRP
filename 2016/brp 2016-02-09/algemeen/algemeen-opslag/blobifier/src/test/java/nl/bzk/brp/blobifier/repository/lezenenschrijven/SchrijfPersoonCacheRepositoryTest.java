/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.blobifier.repository.lezenenschrijven;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import nl.bzk.brp.model.operationeel.kern.PersoonCacheModel;
import nl.bzk.brp.model.operationeel.kern.PersoonCacheStandaardGroepModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SchrijfPersoonCacheRepositoryTest {
    private static final int PERSOON_CACHE_ID = 123;

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private SchrijfPersoonCacheJpaRepository schrijfPersoonCacheJpaRepository;

    private PersoonCacheModel persoonCache = mock(PersoonCacheModel.class);

    @Before
    public void setup() {
        when(persoonCache.getPersoonId()).thenReturn(PERSOON_CACHE_ID);
        final PersoonCacheStandaardGroepModel mock = mock(PersoonCacheStandaardGroepModel.class);
        when(persoonCache.getStandaard()).thenReturn(mock);
    }

    @Test
    public void testOpslaanNieuwePersoonCache() {
        schrijfPersoonCacheJpaRepository.opslaanNieuwePersoonCache(persoonCache);

        verify(entityManager).persist(persoonCache);
        verifyZeroInteractions(persoonCache);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOpslaanNieuwePersoonCacheGooitInvalidArgumentException() {
        schrijfPersoonCacheJpaRepository.opslaanNieuwePersoonCache(null);
    }


    @Test
    public void testHaalPersoonOpUitMasterDb() {
        final TypedQuery typedQueryMock = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(PersoonCacheModel.class))).thenReturn(typedQueryMock);
        when(typedQueryMock.setParameter(anyString(), any(Integer.class))).thenReturn(typedQueryMock);
        when(typedQueryMock.getResultList()).thenReturn(Arrays.asList(persoonCache));

        final PersoonCacheModel resultaat = schrijfPersoonCacheJpaRepository.haalPersoonCacheOpUitMasterDatabase(PERSOON_CACHE_ID);

        assertNotNull(resultaat);
        verify(entityManager).createQuery(anyString(), eq(PersoonCacheModel.class));
    }

    @Test
    public void testHaalPersoonUitMasterDbOpVindtGeenPersoon() {
        final TypedQuery typedQueryMock = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(PersoonCacheModel.class))).thenReturn(typedQueryMock);
        when(typedQueryMock.setParameter(anyString(), any(Integer.class))).thenReturn(typedQueryMock);
        when(typedQueryMock.getResultList()).thenReturn(Collections.emptyList());

        final PersoonCacheModel resultaat = schrijfPersoonCacheJpaRepository.haalPersoonCacheOpUitMasterDatabase(PERSOON_CACHE_ID);

        assertNull(resultaat);
        verify(entityManager).createQuery(anyString(), eq(PersoonCacheModel.class));
    }

}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.blobifier.repository.alleenlezen;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Map;
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
public class LeesPersoonCacheRepositoryTest {
    private static final int PERSOON_CACHE_ID = 123;
    private static final String SELECT_PC_FROM_PERSOON_CACHE_MODEL_PC_WHERE_PC_PERSOON_ID_IN_IDS =
        "SELECT pc FROM PersoonCacheModel pc WHERE pc.persoonId IN :ids";

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private LeesPersoonCacheJpaRepository leesPersoonCacheJpaRepository;

    private final PersoonCacheModel persoonCache = mock(PersoonCacheModel.class);

    @Before
    public void setup() {
        when(persoonCache.getPersoonId()).thenReturn(PERSOON_CACHE_ID);
        final PersoonCacheStandaardGroepModel mock = mock(PersoonCacheStandaardGroepModel.class);
        when(persoonCache.getStandaard()).thenReturn(mock);
    }

    @Test
    public void testHaalPersoonOp() {
        final TypedQuery typedQueryMock = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(PersoonCacheModel.class))).thenReturn(typedQueryMock);
        when(typedQueryMock.setParameter(anyString(), any(Integer.class))).thenReturn(typedQueryMock);
        when(typedQueryMock.getResultList()).thenReturn(Collections.singletonList(persoonCache));

        final PersoonCacheModel resultaat = leesPersoonCacheJpaRepository.haalPersoonCacheOp(PERSOON_CACHE_ID);

        assertNotNull(resultaat);
    }

    @Test
    public void testHaalPersoonOpVindtGeenPersoon() {
        final TypedQuery typedQueryMock = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(PersoonCacheModel.class))).thenReturn(typedQueryMock);
        when(typedQueryMock.setParameter(anyString(), any(Integer.class))).thenReturn(typedQueryMock);
        when(typedQueryMock.getResultList()).thenReturn(Collections.emptyList());

        final PersoonCacheModel resultaat = leesPersoonCacheJpaRepository.haalPersoonCacheOp(PERSOON_CACHE_ID);

        assertNull(resultaat);
    }


    @Test
    public void testHaalPersoonCacheOpMetPersoonHisVolledigGegevens() {

        final TypedQuery typedQueryMock = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(PersoonCacheModel.class))).thenReturn(typedQueryMock);
        when(typedQueryMock.setParameter(anyString(), any(Integer.class))).thenReturn(typedQueryMock);
        when(typedQueryMock.getResultList()).thenReturn(Collections.singletonList(persoonCache));

        final PersoonCacheModel resultaat =
                leesPersoonCacheJpaRepository.haalPersoonCacheOpMetPersoonHisVolledigGegevens(PERSOON_CACHE_ID);

        assertNotNull(resultaat);
        verify(persoonCache.getStandaard()).getPersoonHistorieVolledigGegevens();
    }

    @Test
    public void testHaalPersoonCachesOpMetPersoonHisVolledigGegevens() {
        final TypedQuery typedQuery = mock(TypedQuery.class);
        when(typedQuery.getResultList()).thenReturn(Collections.singletonList(persoonCache));
        when(entityManager.createQuery(anyString(), eq(PersoonCacheModel.class))).thenReturn(typedQuery);

        final Map<Integer, PersoonCacheModel> resultaat =
                leesPersoonCacheJpaRepository.haalPersoonCachesOpMetPersoonHisVolledigGegevens(Collections.singletonList(PERSOON_CACHE_ID));

        assertNotNull(resultaat);
        assertEquals(1, resultaat.size());
        assertEquals(persoonCache, resultaat.get(PERSOON_CACHE_ID));
        verify(persoonCache.getStandaard()).getPersoonHistorieVolledigGegevens();
        verify(entityManager).createQuery(SELECT_PC_FROM_PERSOON_CACHE_MODEL_PC_WHERE_PC_PERSOON_ID_IN_IDS, PersoonCacheModel.class);
    }

    @Test
    public void testHaalPersoonCacheOpMetAfnemerindicatieGegevens() {
        final TypedQuery typedQueryMock = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(PersoonCacheModel.class))).thenReturn(typedQueryMock);
        when(typedQueryMock.setParameter(anyString(), any(Integer.class))).thenReturn(typedQueryMock);
        when(typedQueryMock.getResultList()).thenReturn(Collections.singletonList(persoonCache));

        final PersoonCacheModel resultaat =
                leesPersoonCacheJpaRepository.haalPersoonCacheOpMetAdministratieveHandelingenGegevens(PERSOON_CACHE_ID);

        assertNotNull(resultaat);
        verify(persoonCache.getStandaard()).getAfnemerindicatieGegevens();
    }

    @Test
    public void testHaalPersoonCachesOpMetAfnemerindicatieGegevens() {
        final TypedQuery typedQuery = mock(TypedQuery.class);
        when(typedQuery.getResultList()).thenReturn(Collections.singletonList(persoonCache));
        when(entityManager.createQuery(anyString(), eq(PersoonCacheModel.class))).thenReturn(typedQuery);

        final Map<Integer, PersoonCacheModel> resultaat =
                leesPersoonCacheJpaRepository.haalPersoonCachesOpMetAdministratieveHandelingenGegevens(Collections.singletonList(PERSOON_CACHE_ID));

        assertNotNull(resultaat);
        assertEquals(1, resultaat.size());
        verify(persoonCache.getStandaard()).getAfnemerindicatieGegevens();
        verify(entityManager)
                .createQuery(SELECT_PC_FROM_PERSOON_CACHE_MODEL_PC_WHERE_PC_PERSOON_ID_IN_IDS, PersoonCacheModel.class);
    }

}

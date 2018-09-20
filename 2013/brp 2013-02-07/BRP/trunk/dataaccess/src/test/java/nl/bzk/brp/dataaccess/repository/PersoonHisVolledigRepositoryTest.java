/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import nl.bzk.brp.dataaccess.repository.jpa.PersoonHisVolledigJpaRepository;
import nl.bzk.brp.model.hisvolledig.PersoonHisVolledigCache;
import nl.bzk.brp.model.hisvolledig.PersoonHisVolledigSerializer;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PersoonHisVolledigRepositoryTest {

    @Mock private PersoonHisVolledigSerializer serializer;
    @Mock private EntityManager entityManager;
    @Mock private PersoonRepository persoonRepository;

    @InjectMocks
    private PersoonHisVolledigJpaRepository repository;

    @Before
    public void setupMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void opslaanAlsCacheNietBestaat() throws IOException {
        // given
        PersoonHisVolledig persoonHisVolledig = new PersoonHisVolledig();
        when(entityManager.find(any(Class.class), anyInt())).thenReturn(null);
        when(serializer.serializeer(any(PersoonHisVolledig.class))).thenReturn(new byte[]{});

        // when
        repository.opslaanPersoon(persoonHisVolledig);

        //then
        verify(serializer).serializeer(any(PersoonHisVolledig.class));
        verify(entityManager).persist(any(PersoonHisVolledigCache.class));
    }

    @Test
    public void opslaanMisluktDoorIOException() throws IOException {
        // given
        PersoonHisVolledig persoonHisVolledig = new PersoonHisVolledig();
        PersoonHisVolledigCache cache = new PersoonHisVolledigCache();

        when(entityManager.find(any(Class.class), anyInt())).thenReturn(cache);
        when(serializer.serializeer(any(PersoonHisVolledig.class))).thenThrow(new IOException("dummy"));

        // when
        repository.opslaanPersoon(persoonHisVolledig);

        //then
        verify(serializer).serializeer(any(PersoonHisVolledig.class));
        verify(entityManager, never()).persist(any(PersoonHisVolledigCache.class));

        Assert.assertEquals(null, cache.getData());

    }


    @Test
    public void opslaanAlsCacheReedsBestaat() throws IOException {
        // given
        PersoonHisVolledig persoonHisVolledig = new PersoonHisVolledig();

        when(entityManager.find(any(Class.class), anyInt())).thenReturn(new PersoonHisVolledigCache());
        when(serializer.serializeer(any(PersoonHisVolledig.class))).thenReturn(new byte[]{});

        // when
        repository.opslaanPersoon(persoonHisVolledig);

        //then
        verify(serializer).serializeer(any(PersoonHisVolledig.class));
        verify(entityManager, never()).persist(any(PersoonHisVolledigCache.class));
    }

    @Test
    public void leesAlsCacheReedsBestaat() throws IOException {
        // given
        PersoonHisVolledig persoonHisVolledig = new PersoonHisVolledig();

        // when
        when(entityManager.find(eq(PersoonHisVolledigCache.class), anyInt())).thenReturn(new PersoonHisVolledigCache());
        when(serializer.deserializeer(any(byte[].class))).thenReturn(persoonHisVolledig);

        PersoonHisVolledig resultaat = repository.haalPersoonOp(1);

        //then
        verify(entityManager).find(eq(PersoonHisVolledigCache.class), anyInt());
        verify(serializer).deserializeer(any(byte[].class));

        Assert.assertNotNull(resultaat);
    }

    @Test
    public void lezenMisluktDoorIOException() throws IOException {
        // given
        when(entityManager.find(eq(PersoonHisVolledigCache.class), anyInt())).thenReturn(new PersoonHisVolledigCache());
        when(serializer.deserializeer(any(byte[].class))).thenThrow(new IOException("dummy"));

        // when
        PersoonHisVolledig resultaat = repository.haalPersoonOp(1);

        //then
        verify(entityManager).find(eq(PersoonHisVolledigCache.class), anyInt());
        verify(serializer).deserializeer(any(byte[].class));

        Assert.assertNull(resultaat);
    }

    @Test
    public void leesAlsCacheWelEnNietBestaat() throws IOException {
        // given
        PersoonHisVolledig persoonHisVolledig1 = new PersoonHisVolledig();
        PersoonHisVolledig persoonHisVolledig2 = new PersoonHisVolledig();

        //Cache aanwezig voor persoon 1, niet voor 2
        PersoonHisVolledigCache persoonHisVolledigCache1 = mock(PersoonHisVolledigCache.class);
        when(persoonHisVolledigCache1.getId()).thenReturn(1);

        final TypedQuery typedQuery = mock(TypedQuery.class);
        when(typedQuery.getResultList()).thenReturn(Arrays.asList(new PersoonHisVolledigCache[]{persoonHisVolledigCache1}));
        when(entityManager.createQuery("SELECT pvc FROM PersoonHisVolledigCache pvc WHERE pvc.persoon.id IN :ids",
                PersoonHisVolledigCache.class)).thenReturn(typedQuery);
        when(serializer.deserializeer(any(byte[].class))).thenReturn(persoonHisVolledig1);

        //Cache voor persoon 2 niet aanwezig, dus zal database bevragen
        when(entityManager.find(PersoonHisVolledig.class, 2)).thenReturn(persoonHisVolledig2);

        // when
        List<PersoonHisVolledig> resultaat = repository.haalPersonenOp(Arrays.asList(new Integer[]{1, 2}));

        //then
        //Persoon 1
        verify(entityManager).find(eq(PersoonHisVolledigCache.class), anyInt());
        verify(serializer).deserializeer(any(byte[].class));
        verify(entityManager, times(0)).find(PersoonHisVolledig.class, 1);

        //Persoon 2
        verify(entityManager).find(PersoonHisVolledig.class, 2);

        //Uiteindelijk resultaat
        Assert.assertEquals(2, resultaat.size());
    }
}

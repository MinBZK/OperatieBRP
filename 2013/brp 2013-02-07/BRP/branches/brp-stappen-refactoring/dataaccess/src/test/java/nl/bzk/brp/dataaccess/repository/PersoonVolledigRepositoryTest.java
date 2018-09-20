/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.persistence.EntityManager;

import junit.framework.Assert;
import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.dataaccess.repository.jpa.PersoonVolledigJpaRepository;
import nl.bzk.brp.dataaccess.serializatie.PersoonVolledigSerializer;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.operationeel.kern.PersoonVolledig;
import nl.bzk.brp.model.operationeel.kern.PersoonVolledigCache;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PersoonVolledigRepositoryTest extends AbstractRepositoryTestCase {

    @Mock private PersoonVolledigSerializer serializer;
    @Mock private EntityManager entityManager;
    @Mock private PersoonRepository persoonRepository;

    @InjectMocks
    private PersoonVolledigJpaRepository repository;

    @Before
    public void setupMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void opslaanAlsCacheNietBestaat() throws IOException {
        // given
        PersoonVolledig persoonVolledig = new PersoonVolledig(-1);
        when(entityManager.find(any(Class.class), anyInt())).thenReturn(null);
        when(serializer.serializeer(any(PersoonVolledig.class))).thenReturn(new byte[]{});

        // when
        repository.opslaanPersoon(persoonVolledig);

        //then
        verify(serializer).serializeer(any(PersoonVolledig.class));
        verify(entityManager).persist(any(PersoonVolledigCache.class));
    }

    @Test
    public void opslaanMisluktDoorIOException() throws IOException {
        // given
        PersoonVolledig persoonVolledig = new PersoonVolledig(-1);
        PersoonVolledigCache cache = new PersoonVolledigCache();

        when(entityManager.find(any(Class.class), anyInt())).thenReturn(cache);
        when(serializer.serializeer(any(PersoonVolledig.class))).thenThrow(new IOException("dummy"));

        // when
        repository.opslaanPersoon(persoonVolledig);

        //then
        verify(serializer).serializeer(any(PersoonVolledig.class));
        verify(entityManager, never()).persist(any(PersoonVolledigCache.class));

        Assert.assertEquals(null, cache.getData());

    }


    @Test
    public void opslaanAlsCacheReedsBestaat() throws IOException {
        // given
        PersoonVolledig persoonVolledig = new PersoonVolledig(-1);

        when(entityManager.find(any(Class.class), anyInt())).thenReturn(new PersoonVolledigCache());
        when(serializer.serializeer(any(PersoonVolledig.class))).thenReturn(new byte[]{});

        // when
        repository.opslaanPersoon(persoonVolledig);

        //then
        verify(serializer).serializeer(any(PersoonVolledig.class));
        verify(entityManager, never()).persist(any(PersoonVolledigCache.class));
    }

    @Ignore @Test
    public void leesAlsCacheNietBestaatEnSchrijfMeteenWeg() throws IOException {
        // given
        given(entityManager.find(eq(PersoonVolledigCache.class), anyInt())).willReturn(null);
        given(persoonRepository.haalPersoonMetAdres(anyInt())).willReturn(new PersoonModel(SoortPersoon.DUMMY));

        // when
        PersoonVolledig resultaat = repository.haalPersoonOp(1);

        //then
        verify(serializer, never()).deserializeer(any(byte[].class));
        verify(entityManager).persist(any(PersoonVolledigCache.class));

        Assert.assertNotNull(resultaat);
    }

    @Ignore @Test
    public void leesAlsCacheNietBestaatEnSchrijfMeteenWegMaarBlobIsErToch() throws IOException {
        // given
        PersoonModel persoon = mock(PersoonModel.class);

        when(entityManager.find(eq(PersoonVolledigCache.class), anyInt())).thenReturn(null).thenReturn(new PersoonVolledigCache());
        when(persoonRepository.haalPersoonMetAdres(anyInt())).thenReturn(persoon);

        // when
        PersoonVolledig resultaat = repository.haalPersoonOp(1);

        //then
        verify(serializer, never()).deserializeer(any(byte[].class));
        verify(serializer).serializeer(any(PersoonVolledig.class));
        verify(entityManager, never()).persist(any(PersoonVolledigCache.class));

        Assert.assertNotNull(resultaat);
    }

    @Test
    public void leesAlsCacheReedsBestaat() throws IOException {
        // given
        PersoonVolledig persoonVolledig = new PersoonVolledig(1);

        // when
        when(entityManager.find(eq(PersoonVolledigCache.class), anyInt())).thenReturn(new PersoonVolledigCache());
        when(serializer.deserializeer(any(byte[].class))).thenReturn(persoonVolledig);

        PersoonVolledig resultaat = repository.haalPersoonOp(1);

        //then
        verify(entityManager).find(eq(PersoonVolledigCache.class), anyInt());
        verify(serializer).deserializeer(any(byte[].class));

        Assert.assertNotNull(resultaat);
    }

    @Test
    public void lezenMisluktDoorIOException() throws IOException {
        // given
        PersoonVolledig persoonVolledig = new PersoonVolledig(1);

        // when
        when(entityManager.find(eq(PersoonVolledigCache.class), anyInt())).thenReturn(new PersoonVolledigCache());
        when(serializer.deserializeer(any(byte[].class))).thenThrow(new IOException("dummy"));

        PersoonVolledig resultaat = repository.haalPersoonOp(1);

        //then
        verify(entityManager).find(eq(PersoonVolledigCache.class), anyInt());
        verify(serializer).deserializeer(any(byte[].class));

        Assert.assertNull(resultaat);
    }
}

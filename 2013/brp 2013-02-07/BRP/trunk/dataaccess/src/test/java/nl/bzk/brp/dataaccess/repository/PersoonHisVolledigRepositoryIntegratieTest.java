/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.model.hisvolledig.PersoonHisVolledigCache;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import org.junit.Assert;
import org.junit.Test;

public class PersoonHisVolledigRepositoryIntegratieTest extends AbstractRepositoryTestCase {

    @PersistenceContext(unitName = "nl.bzk.brp")
    private EntityManager em;

    @Inject
    private PersoonHisVolledigRepository persoonHisVolledigRepository;

    @Test
    public void haalPersoonOpTest() throws IOException {
        int persoonId = 1;

        PersoonHisVolledig resultaat = persoonHisVolledigRepository.haalPersoonOp(persoonId);

        Assert.assertNotNull(resultaat);
    }

    @Test
    public void leesAlsCacheNietBestaatEnSchrijfMeteenWeg() throws IOException {
        int persoonId = 1;

        PersoonHisVolledigCache cacheOrigineel = haalCacheVoorPersoonId(persoonId);
        if(cacheOrigineel != null){
            verwijderCache(cacheOrigineel);
        }

        persoonHisVolledigRepository.haalPersoonOp(persoonId);

        PersoonHisVolledigCache cacheNieuw = haalCacheVoorPersoonId(persoonId);
        Assert.assertNotNull(cacheNieuw);
    }

    @Test
    public void leesAlsCacheBestaatEnVerwijderEnHaalEnControleerCacheIsGelijk() throws IOException {
        int persoonId = 1;

        //Zorg dat cache bestaat
        persoonHisVolledigRepository.haalPersoonOp(persoonId);

        PersoonHisVolledigCache cacheOrigineel = haalCacheVoorPersoonId(persoonId);
        verwijderCache(cacheOrigineel);

        //Maak cache opnieuw
        persoonHisVolledigRepository.haalPersoonOp(persoonId);

        PersoonHisVolledigCache cacheNieuw = haalCacheVoorPersoonId(persoonId);

        assertThat(cacheOrigineel.getData(), equalTo(cacheNieuw.getData()));
        assertThat(cacheOrigineel.getChecksum(), equalTo("ba0fbcad559c4a545482d1b6e954c52dc43862a5"));
    }

    @Test
    public void haalPersonenOp() throws IOException {
        final List<Integer> personenIds = Arrays.asList(1, 3);

        final List<PersoonHisVolledig> personenVolledig = persoonHisVolledigRepository.haalPersonenOp(personenIds);

        Assert.assertEquals(personenIds.size(), personenVolledig.size());
    }

    @Test
    public void haalPersonenOpAlsCacheNietBestaatEnSchrijfMeteenWeg() throws IOException {
        final List<Integer> personenIds = Arrays.asList(1, 3, 501);

        for(Integer persoonId : personenIds){
            final PersoonHisVolledigCache cacheOrigineel = haalCacheVoorPersoonId(persoonId);
            if(cacheOrigineel != null){
                verwijderCache(cacheOrigineel);
            }
        }

        final List<PersoonHisVolledig> personenVolledig = persoonHisVolledigRepository.haalPersonenOp(personenIds);

        for(Integer persoonId : personenIds){
            final PersoonHisVolledigCache cacheNieuw = haalCacheVoorPersoonId(persoonId);
            Assert.assertNotNull(cacheNieuw);
        }

        Assert.assertEquals(personenIds.size(), personenVolledig.size());
    }

    private void verwijderCache(final PersoonHisVolledigCache cacheOrigineel) {
        em.remove(cacheOrigineel);
    }

    private PersoonHisVolledigCache haalCacheVoorPersoonId(final int persoonId) {
        return em.find(PersoonHisVolledigCache.class, persoonId);
    }

}

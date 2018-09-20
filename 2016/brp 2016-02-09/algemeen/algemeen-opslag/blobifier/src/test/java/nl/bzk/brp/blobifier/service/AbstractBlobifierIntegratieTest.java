/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.blobifier.service;

import static org.junit.Assert.assertNotNull;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nl.bzk.brp.blobifier.repository.alleenlezen.HisPersTabelRepository;
import nl.bzk.brp.dataaccess.test.AbstractDBUnitIntegratieTest;
import nl.bzk.brp.model.operationeel.kern.PersoonCacheModel;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public abstract class AbstractBlobifierIntegratieTest extends AbstractDBUnitIntegratieTest {
    private static final String EEN_TRANSACTIE = "EenTransactie";

    @Inject
    @Named("lezenSchrijvenTransactionManager")
    protected PlatformTransactionManager transactionManager;

    @Inject
    protected BlobifierService blobifierService;

    @Inject
    protected AfnemerIndicatieBlobifierService afnemerIndicatieBlobifierService;

    @Inject
    protected HisPersTabelRepository hisPersTabelRepository;

    @PersistenceContext(unitName = "nl.bzk.brp.lezenschrijven")
    private EntityManager em;

    protected void verwijderCache(final PersoonCacheModel cacheOrigineel) {
        em.remove(cacheOrigineel);
    }

    protected PersoonCacheModel haalCacheVoorPersoonId(final int persoonId) {
        final List<PersoonCacheModel> persoonCaches =
            em.createQuery("select persoonCache from PersoonCacheModel persoonCache where persoonCache.persoonId = :persoonId", PersoonCacheModel.class)
                .setParameter("persoonId", persoonId).getResultList();
        if (!persoonCaches.isEmpty()) {
            return persoonCaches.get(0);
        }
        return null;
    }

    protected void verwijderPersoonCache(final int persoonId) {
        final PersoonCacheModel persoonCache = haalCacheVoorPersoonId(persoonId);
        if (persoonCache != null) {
            verwijderCache(persoonCache);
        }
    }

    protected void assertPersoonCacheBestaat(final int persoonId) {
        final PersoonCacheModel persoonCache;
        persoonCache = haalCacheVoorPersoonId(persoonId);
        assertNotNull(persoonCache);
    }

    protected TransactionStatus zorgDatBlobBestaatInTransactie(final Integer persoonId) {
        final TransactionStatus transactionStatus = geefTransactionStatus();

        blobifierService.blobify(persoonId, false);

        return transactionStatus;
    }

    protected TransactionStatus geefTransactionStatus() {
        final DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName(EEN_TRANSACTIE);
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        return transactionManager.getTransaction(def);
    }

    protected TransactionStatus zorgDatAfnemerindicatieBlobBestaatInTransactie(final Integer technischId) {
        final TransactionStatus transactionStatus = geefTransactionStatus();

        blobifierService.blobify(technischId, false);
        afnemerIndicatieBlobifierService.blobify(technischId);

        return transactionStatus;
    }

}

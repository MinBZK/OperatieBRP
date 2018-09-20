/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.blobifier.service;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import org.junit.Test;
import org.springframework.transaction.TransactionStatus;

/**
 * Test of afnemerindicaties lazy worden opgehaald.
 */
public class BlobifierServiceAfnemerindicatiesLazyIntegratieTest extends AbstractBlobifierIntegratieTest {

    @Test
    public void leesAlsPersoonCacheWelEnAfnemerindicatiesCacheNietBestaat() {
        final Integer persoonId = 2000001;

        final TransactionStatus transactieStatus = zorgDatBlobBestaatInTransactie(persoonId);
        transactionManager.commit(transactieStatus);

        final PersoonHisVolledig persoonUitCache = blobifierService.leesBlob(persoonId);

        assertThat(persoonUitCache.getAfnemerindicaties().size(), is(2));
    }

    @Test
    public void leesAlsPersoonEnAfnemerindicatiesCacheNietBestaat() {
        final Integer persoonId = 2000001;
        verwijderPersoonCache(persoonId);

        final PersoonHisVolledig persoonUitCache = blobifierService.leesBlob(persoonId);

        assertThat(persoonUitCache.getAfnemerindicaties().size(), is(2));
    }

    @Test
    public void leesAlsPersoonEnAfnemerindicatieCacheBestaat() {
        final Integer persoonId = 2000001;

        TransactionStatus transactieStatus = zorgDatBlobBestaatInTransactie(persoonId);
        transactionManager.commit(transactieStatus);

        transactieStatus = zorgDatAfnemerindicatieBlobBestaatInTransactie(persoonId);
        transactionManager.commit(transactieStatus);

        final PersoonHisVolledig persoonUitCache = blobifierService.leesBlob(persoonId);

        assertThat(persoonUitCache.getAfnemerindicaties().size(), is(2));
    }

    @Test
    public void leesAlsPersoonCacheNietEnAfnemerindicatiesCacheWelBestaat() {
        final Integer persoonId = 2000001;
        verwijderPersoonCache(persoonId);

        final TransactionStatus transactieStatus = zorgDatAfnemerindicatieBlobBestaatInTransactie(persoonId);
        transactionManager.commit(transactieStatus);

        final PersoonHisVolledig persoonUitCache = blobifierService.leesBlob(persoonId);

        assertThat(persoonUitCache.getAfnemerindicaties().size(), is(2));
    }
}

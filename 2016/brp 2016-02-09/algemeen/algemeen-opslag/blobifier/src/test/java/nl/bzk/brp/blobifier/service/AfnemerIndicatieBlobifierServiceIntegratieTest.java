/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.blobifier.service;

import java.io.IOException;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.brp.blobifier.exceptie.AfnemerindicatiesNietAanwezigExceptie;
import nl.bzk.brp.blobifier.repository.alleenlezen.HisAfnemerindicatieBlobRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ChecksumAttribuut;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.PersoonCacheModel;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.transaction.TransactionStatus;

/**
 *
 */
public class AfnemerIndicatieBlobifierServiceIntegratieTest extends AbstractBlobifierIntegratieTest {

    @Inject
    private HisAfnemerindicatieBlobRepository hisAfnemerindicatieBlobRepository;

    @Test
    public void testOpslaanInCacheViaId() {
        final Integer persoonId = 2000001;

        afnemerIndicatieBlobifierService.blobify(persoonId);

        assertPersoonCacheBestaat(persoonId);
    }

    @Test
    public void testOpslaanInCacheViaPersoon() {
        final Integer persoonId = 2000001;
        final PersoonHisVolledigImpl persoon = blobifierService.leesBlob(persoonId);
        verwijderPersoonCache(persoonId);

        afnemerIndicatieBlobifierService.blobify(persoonId, persoon.getAfnemerindicaties());

        assertPersoonCacheBestaat(persoonId);
    }

    @Test
    public void testOpslaanNietBestaandId() {
        // Voorheen werd er een exceptie gegooid als er een onbestaande persoonid werd geblobified, dit is niet meer
        // het geval. Als er een persoonId bekend is, gaan we er vanuit dat de persoon bestaat.

        final Integer persoonId = 123;
        afnemerIndicatieBlobifierService.blobify(persoonId);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOpslaanVoorNullPersoonHisVolledig() {
        afnemerIndicatieBlobifierService.blobify((PersoonHisVolledigImpl) null);
    }

    @Test
    public void testLezenBlobBestaandeBlob() {
        final Integer persoonId = 2000001;

        final TransactionStatus transactieStatus = zorgDatAfnemerindicatieBlobBestaatInTransactie(persoonId);
        transactionManager.commit(transactieStatus);

        // act
        final Set<?> indicaties = afnemerIndicatieBlobifierService.leesBlob(persoonId);

        Assert.assertThat(indicaties.size(), Matchers.is(2));
    }

    @Test
    public void testLezenLegeSetAfnemerindicaties() {
        final Integer persoonId = 2000004;

        final TransactionStatus transactieStatus = zorgDatAfnemerindicatieBlobBestaatInTransactie(persoonId);
        transactionManager.commit(transactieStatus);

        // act
        final Set<?> indicaties = afnemerIndicatieBlobifierService.leesBlob(persoonId);

        // assert
        Assert.assertThat(indicaties.size(), Matchers.is(0));

        final PersoonCacheModel model = haalCacheVoorPersoonId(persoonId);
        final String checksum = model.getStandaard().getAfnemerindicatieChecksum().getWaarde();
        Assert.assertThat(checksum, Matchers.equalTo("97d170e1550eee4afc0af065b78cda302a97674c"));
    }

    @Test
    public void testLeesBlobBestaandeBlobMetIncorrecteChecksumBlijftGewoonWerken() {
        final Integer persoonId = 2000002;

        final TransactionStatus transactieStatus = zorgDatAfnemerindicatieBlobBestaatInTransactie(persoonId);
        final PersoonCacheModel inMemoryCache = haalCacheVoorPersoonId(persoonId);
        inMemoryCache.getStandaard().setPersoonHistorieVolledigChecksum(new ChecksumAttribuut("fouteChecksum"));
        transactionManager.commit(transactieStatus);

        final Set<?> indicaties = afnemerIndicatieBlobifierService.leesBlob(persoonId);

        Assert.assertThat(indicaties.size(), Matchers.is(1));
    }

    @Test
    public void haalAfnemerindicatiesOpTest() throws IOException {
        final int persoonId = 1;

        final PersoonHisVolledig jpaEntity = hisPersTabelRepository.leesGenormalizeerdModelVoorInMemoryBlob(persoonId);
        final PersoonHisVolledig blobEntity = blobifierService.leesBlob(persoonId);

        Assert.assertNotNull(jpaEntity);
        Assert.assertNotNull(blobEntity);
        Assert.assertThat(jpaEntity.getID(), CoreMatchers.is(blobEntity.getID()));
    }

    @Test
    public void haalPersoonOpTestVoorNieuweBlob() throws IOException {
        final int persoonId = 2000003;

        final Set<?> jpaEntity = hisAfnemerindicatieBlobRepository.leesGenormaliseerdModelVoorNieuweBlob(persoonId);
        final Set<?> blobEntity = afnemerIndicatieBlobifierService.leesBlob(persoonId);

        Assert.assertNotNull(jpaEntity);
        Assert.assertNotNull(blobEntity);
        Assert.assertThat(jpaEntity.size(), Matchers.is(blobEntity.size()));
    }

    @Test(expected = AfnemerindicatiesNietAanwezigExceptie.class)
    public void leesBlobNietBestaandePersoon() {
        final int persoonId = 453452314;

        afnemerIndicatieBlobifierService.leesBlob(persoonId);
    }

    /**
     * Test dat het ophalen van afnemerindicaties zonder blob slaagt (er wordt een geserializeerde versie van de indicaties terug gegeven). De database
     * wordt niet aangepast.
     */
    @Test
    public void leesAlsCacheNietBestaatEnSchrijfNietWeg() throws IOException {
        final int persoonId = 2000001;

        verwijderPersoonCache(persoonId);

        afnemerIndicatieBlobifierService.leesBlob(persoonId);

        final PersoonCacheModel cacheNieuw = haalCacheVoorPersoonId(persoonId);
        Assert.assertNull(cacheNieuw);
    }
}

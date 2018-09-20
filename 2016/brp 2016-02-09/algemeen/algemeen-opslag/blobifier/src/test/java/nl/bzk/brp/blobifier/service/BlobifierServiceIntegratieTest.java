/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.blobifier.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import nl.bzk.brp.blobifier.exceptie.NietUniekeAnummerExceptie;
import nl.bzk.brp.blobifier.exceptie.NietUniekeBsnExceptie;
import nl.bzk.brp.blobifier.exceptie.PersoonNietAanwezigExceptie;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdministratienummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ChecksumAttribuut;
import nl.bzk.brp.model.hisvolledig.impl.kern.BetrokkenheidHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.PersoonCacheModel;
import org.hamcrest.CoreMatchers;
import org.hibernate.proxy.HibernateProxy;
import org.junit.Test;
import org.springframework.transaction.TransactionStatus;

public class BlobifierServiceIntegratieTest extends AbstractBlobifierIntegratieTest {

    private static final String BSN_NIET_BESTAAND = "111111111";

    @Test
    public void testOpslaanInPersoonCache() {
        final int persoonId = 2;
        verwijderPersoonCache(persoonId);
        final PersoonHisVolledig persoon = blobifierService.leesBlob(persoonId);

        blobifierService.blobify((PersoonHisVolledigImpl) persoon, false);

        assertPersoonCacheBestaat(persoonId);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOpslaanInPersoonCacheNullPersoon() {
        blobifierService.blobify((PersoonHisVolledigImpl) null, false);
    }

    @Test
    public void testBlobifyBestaandeBlob() {
        final int persoonId = 2;
        blobifierService.blobify(persoonId, false);
        final PersoonCacheModel bestaandeCache = haalCacheVoorPersoonId(persoonId);

        blobifierService.blobify(persoonId, false);

        final PersoonCacheModel nieuweCache = haalCacheVoorPersoonId(persoonId);
        assertEquals(bestaandeCache, nieuweCache);
    }

    @Test
    public void testOpslaanInPersoonCacheViaId() {
        final int persoonId = 2;
        verwijderPersoonCache(persoonId);

        blobifierService.blobify(persoonId, false);

        assertPersoonCacheBestaat(persoonId);
    }

    @Test(expected = PersoonNietAanwezigExceptie.class)
    public void testOpslaanInPersoonCacheViaIdNietBestaandId() {
        blobifierService.blobify(123, false);
    }

    @Test
    public void testOpslaanInPersoonCacheViaBsn() throws NietUniekeBsnExceptie {
        final int persoonId = 2;
        verwijderPersoonCache(persoonId);

        blobifierService.blobify(new BurgerservicenummerAttribuut(303937828), false);

        assertPersoonCacheBestaat(persoonId);
    }

    @Test(expected = PersoonNietAanwezigExceptie.class)
    public void testOpslaanInPersoonCacheViaBsnNietBestaandeBsn() throws NietUniekeAnummerExceptie, NietUniekeBsnExceptie
    {
        blobifierService.blobify(new BurgerservicenummerAttribuut(123), false);
    }

    @Test
    public void testOpslaanInPersoonCacheViaAnr() throws NietUniekeAnummerExceptie {
        final int persoonId = 2;
        verwijderPersoonCache(persoonId);

        blobifierService.blobify(new AdministratienummerAttribuut(1285742609L), false);

        assertPersoonCacheBestaat(persoonId);
    }

    @Test(expected = PersoonNietAanwezigExceptie.class)
    public void testOpslaanInPersoonCacheViaAnrNietBestaandAnr() throws NietUniekeAnummerExceptie
    {
        blobifierService.blobify(new AdministratienummerAttribuut(123L), false);
    }

    @Test
    public void testLeesBlobBestaandeBlob() {
        final Integer persoonId = 2;

        final TransactionStatus transactieStatus = zorgDatBlobBestaatInTransactie(persoonId);
        transactionManager.commit(transactieStatus);

        final PersoonHisVolledig persoon = blobifierService.leesBlob(persoonId);

        assertEquals(persoonId, persoon.getID());
    }

    @Test
    public void testLeesBlobBestaandeBlobMetIncorrecteChecksumBlijftGewoonWerken() {
        final Integer persoonId = 2;

        final TransactionStatus transactieStatus = zorgDatBlobBestaatInTransactie(persoonId);
        final PersoonCacheModel inMemoryCache = haalCacheVoorPersoonId(persoonId);
        inMemoryCache.getStandaard().setPersoonHistorieVolledigChecksum(new ChecksumAttribuut("fouteChecksum"));
        transactionManager.commit(transactieStatus);

        final PersoonHisVolledig persoon = blobifierService.leesBlob(persoonId);

        assertEquals(persoonId, persoon.getID());
    }

    @Test
    public void testLeesBlobViaAnr() throws NietUniekeAnummerExceptie {
        final PersoonHisVolledig persoon = blobifierService.leesBlob(new AdministratienummerAttribuut(1285742609L));

        assertNotNull(persoon);
    }

    @Test
    public void testLeesBlobViaBsn() throws NietUniekeBsnExceptie {
        final PersoonHisVolledig persoon = blobifierService.leesBlob(new BurgerservicenummerAttribuut(303937828));

        assertNotNull(persoon);
    }

    @Test
    public void haalPersoonOpTest() throws IOException {
        final int persoonId = 1;

        final PersoonHisVolledig jpaEntity = hisPersTabelRepository.leesGenormalizeerdModelVoorInMemoryBlob(persoonId);
        final PersoonHisVolledig blobEntity = blobifierService.leesBlob(persoonId);

        assertNotNull(jpaEntity);
        assertNotNull(blobEntity);
        assertThat(jpaEntity.getID(), CoreMatchers.is(blobEntity.getID()));
    }

    @Test
    public void haalPersoonOpMetAdministratieveHandelingenTest() throws IOException {
        final int persoonId = 2;

        final PersoonHisVolledigImpl blobEntity = blobifierService.leesBlob(persoonId);

        assertNotNull(blobEntity);
        assertEquals(5, blobEntity.getAdministratieveHandelingen().size());
        assertNotNull(blobEntity.getPersoonIdentificatienummersHistorie().getActueleRecord().getVerantwoordingInhoud());
        assertNotNull(blobEntity.getPersoonIdentificatienummersHistorie().getActueleRecord().getVerantwoordingInhoud()
            .getAdministratieveHandeling());
    }

    @Test
    public void haalPersoonOpTestVoorNieuweBlob() throws IOException {
        final int persoonId = 1;

        final PersoonHisVolledig jpaEntity = hisPersTabelRepository.leesGenormalizeerdModelVoorNieuweBlob(persoonId);
        final PersoonHisVolledig blobEntity = blobifierService.leesBlob(persoonId);

        assertNotNull(jpaEntity);
        assertNotNull(blobEntity);
        assertThat(jpaEntity.getID(), CoreMatchers.is(blobEntity.getID()));
    }

    @Test(expected = PersoonNietAanwezigExceptie.class)
    public void leesBlobNietBestaandePersoon() {
        final int persoonId = 453452314;

        blobifierService.leesBlob(persoonId);
    }

    /**
     * Test dat het ophalen van een persoon zonder blob slaagt (er wordt een geserializeerde versie van de persoon terug gegeven). De database is wordt
     * niet aangepast.
     *
     * @throws IOException iO exception
     */
    @Test
    public void leesAlsCacheNietBestaatEnSchrijfNietWeg() throws IOException {
        final int persoonId = 1;

        verwijderPersoonCache(persoonId);

        blobifierService.leesBlob(persoonId);

        final PersoonCacheModel cacheNieuw = haalCacheVoorPersoonId(persoonId);
        assertNull(cacheNieuw);
    }

    @Test
    public void haalPersonenOp() throws IOException {
        final List<Integer> personenIds = Arrays.asList(1, 3);

        final List<PersoonHisVolledigImpl> personenVolledig = blobifierService.leesBlobs(personenIds);

        assertEquals(personenIds.size(), personenVolledig.size());
    }

    /**
     * Test dat het ophalen van personen zonder blob slaagt (er wordt een geserializeerde versie van de persoon terug gegeven). De database is wordt niet
     * aangepast.
     *
     * @throws IOException iO exception
     */
    @Test
    public void haalPersonenOpAlsCacheNietBestaatEnSchrijfNietWeg() throws IOException {
        // Arrange
        final List<Integer> personenIds = Arrays.asList(1, 3, 5);

        for (final Integer persoonId : personenIds) {
            final PersoonCacheModel cacheOrigineel = haalCacheVoorPersoonId(persoonId);
            if (cacheOrigineel != null) {
                verwijderCache(cacheOrigineel);
            }
        }

        // Act
        final List<PersoonHisVolledigImpl> personenVolledig = blobifierService.leesBlobs(personenIds);

        // Assert
        for (final Integer persoonId : personenIds) {
            final PersoonCacheModel cacheNieuw = haalCacheVoorPersoonId(persoonId);
            assertNull(cacheNieuw);
        }

        assertEquals(personenIds.size(), personenVolledig.size());
    }

    @Test
    public void zoekIdBijBsn() throws NietUniekeBsnExceptie {
        assertEquals(Integer.valueOf(1),
            hisPersTabelRepository.zoekIdBijBSN(new BurgerservicenummerAttribuut("302533928")));
    }

    @Test
    public void zoekIdBijBsnActiefPersoon() throws NietUniekeBsnExceptie {
        assertEquals(Integer.valueOf(1),
            hisPersTabelRepository.zoekIdBijBSNVoorActievePersoon(new BurgerservicenummerAttribuut("302533928")));
    }

    @Test
    public void zoekIdBijBsnActiefPersoonMetInactiefPersoonMetIdentiekBsn() throws NietUniekeBsnExceptie {
        assertEquals(Integer.valueOf(21),
            hisPersTabelRepository.zoekIdBijBSNVoorActievePersoon(new BurgerservicenummerAttribuut("999999911")));
    }

    @Test(expected = PersoonNietAanwezigExceptie.class)
    public void zoekIdBijBsnInactiefPersoon() throws NietUniekeBsnExceptie {
        assertEquals(Integer.valueOf(23),
            hisPersTabelRepository.zoekIdBijBSNVoorActievePersoon(new BurgerservicenummerAttribuut("999999912")));
    }

    @Test(expected = PersoonNietAanwezigExceptie.class)
    public void zoekIdBijNietBestaandeBsn() throws NietUniekeBsnExceptie {
        hisPersTabelRepository.zoekIdBijBSN(new BurgerservicenummerAttribuut(BSN_NIET_BESTAAND));
    }

    @Test(expected = PersoonNietAanwezigExceptie.class)
    public void zoekIdBijBsnNullAttribuut() throws NietUniekeBsnExceptie {
        assertNull(hisPersTabelRepository.zoekIdBijBSN(null));
    }

    @Test
    public void zoekIdBijAnummer() throws NietUniekeAnummerExceptie {
        assertEquals(Integer.valueOf(1),
            hisPersTabelRepository.zoekIdBijAnummer(new AdministratienummerAttribuut("1268046023")));
    }

    @Test(expected = PersoonNietAanwezigExceptie.class)
    public void zoekIdBijNietBestaandAnummer() throws NietUniekeAnummerExceptie {
        hisPersTabelRepository.zoekIdBijAnummer(new AdministratienummerAttribuut(BSN_NIET_BESTAAND));
    }

    @Test(expected = PersoonNietAanwezigExceptie.class)
    public void zoekIdBijAnummerNullAttribuut() throws NietUniekeAnummerExceptie {
        assertNull(hisPersTabelRepository.zoekIdBijAnummer(null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void opslaanInPersoonCacheMetNull() {
        blobifierService.blobify((Integer) null, false);
    }

    @Test
    public void blobifyVanEenHibernateProxyGaatGoed() throws IOException {
        final int persoonId = 2;

        final PersoonHisVolledigImpl jpaEntiteit = hisPersTabelRepository.leesGenormalizeerdModelVoorInMemoryBlob(persoonId);

        final PersoonHisVolledigImpl luieEntiteitPartner = haalPartnerOp(jpaEntiteit);

        //Check uitgangssituatie: de ouder moet een hibernate-proxy zijn
        assertTrue(HibernateProxy.class.isInstance(luieEntiteitPartner));

        //Maak nu een blob van de luie ouder
        blobifierService.blobify(luieEntiteitPartner, true);

        final TransactionStatus transactieStatus = geefTransactionStatus();
        transactionManager.commit(transactieStatus);

        //Check of de verantwoording in de blob zit
        final PersoonHisVolledigImpl partnerBlob = blobifierService.leesBlob(luieEntiteitPartner.getID());
        assertTrue(partnerBlob.getAdministratieveHandelingen().size() > 0);
        assertEquals(luieEntiteitPartner.getPersoonAfgeleidAdministratiefHistorie().getAantal(), partnerBlob.getAdministratieveHandelingen().size());
    }

    private PersoonHisVolledigImpl haalPartnerOp(final PersoonHisVolledigImpl persoon) {
        final Set<BetrokkenheidHisVolledigImpl>
            eventuelePartners = persoon.getPartnerBetrokkenheden().iterator().next().getRelatie().getBetrokkenheden();

        for (final BetrokkenheidHisVolledigImpl eventuelePartner : eventuelePartners) {
            if (!eventuelePartner.getPersoon().getID().equals(persoon.getID())) {
                return eventuelePartner.getPersoon();
            }
        }
        return null;
    }

}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie.persoon;

import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.brp.blobifier.repository.alleenlezen.LeesPersoonCacheRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ChecksumAttribuut;
import nl.bzk.brp.model.logisch.kern.PersoonCache;
import nl.bzk.brp.serialisatie.AbstractIntegratieTest;
import nl.bzk.brp.utils.junit.OverslaanBijInMemoryDatabase;
import nl.bzk.brp.vergrendeling.VergrendelFout;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 *
 */
@Category(OverslaanBijInMemoryDatabase.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/config/integratieTest-context.xml")
public class PersoonSerialiseerderIntegratieTest extends AbstractIntegratieTest {

    @Inject
    private PersoonSerialiseerder serialiseerder;

    @Inject
    private LeesPersoonCacheRepository leesPersoonCacheRepository;

    @Inject
    @Named("lezenSchrijvenTransactionManager")
    private PlatformTransactionManager transactionManager;

    @Test(expected = IllegalArgumentException.class)
    public void serialisatieFout() throws VergrendelFout {
        serialiseerder.serialiseerPersoon(null);
        Assert.fail("Serialiseren moet een technische sleutel krijgen om te kunnen serialiseren");
    }

    @Test
    public void serialiseerNieuwPersoon() throws VergrendelFout {
        Assert.assertNull(leesPersoonCacheRepository.haalPersoonCacheOp(1));

        final TransactionStatus trasactieStatus = startTransactie();
        serialiseerder.serialiseerPersoon(1);
        transactionManager.commit(trasactieStatus);

        Assert.assertNotNull(leesPersoonCacheRepository.haalPersoonCacheOp(1));
    }

    @Test
    public void serialiseerPersoonNogmaalsZonderWijzigingLevertZelfdeChecksum() throws VergrendelFout {
        PersoonCache cache = leesPersoonCacheRepository.haalPersoonCacheOp(1);
        Assert.assertNull(cache);

        final TransactionStatus trasactieStatus = startTransactie();
        serialiseerder.serialiseerPersoon(1);
        transactionManager.commit(trasactieStatus);

        cache = leesPersoonCacheRepository.haalPersoonCacheOp(1);
        Assert.assertNotNull(cache);
        final ChecksumAttribuut checksum = cache.getStandaard().getPersoonHistorieVolledigChecksum();
        //En sla hem nu nogmaals op, zonder wijziging zal dit dezelfde checksum opleveren
        final TransactionStatus trasactieStatus2 = startTransactie();
        serialiseerder.serialiseerPersoon(1);
        transactionManager.commit(trasactieStatus2);
        cache = leesPersoonCacheRepository.haalPersoonCacheOp(1);
        Assert.assertEquals(checksum, cache.getStandaard().getPersoonHistorieVolledigChecksum());
    }

    /**
     * Aangezien we met 2 transacties werken (lezen staat los van schrijven), moeten we binnen de test een commit doen
     * voordat we de data op kunnen halen/verifieren.
     *
     * @return De transactiestatus.
     */
    private TransactionStatus startTransactie() {
        final DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("EenTransactie");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

        return transactionManager.getTransaction(def);
    }

}

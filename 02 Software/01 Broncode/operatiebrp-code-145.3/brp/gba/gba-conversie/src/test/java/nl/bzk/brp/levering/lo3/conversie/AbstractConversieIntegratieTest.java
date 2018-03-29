/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie;

import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.cache.DalCacheController;
import nl.bzk.algemeenbrp.test.dal.data.DBUnitLoaderTestExecutionListener;
import nl.bzk.algemeenbrp.test.dal.data.Data;
import nl.bzk.brp.service.algemeen.blob.PersoonslijstService;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


@Transactional(transactionManager = "masterTransactionManager")
@Rollback
@TestExecutionListeners(listeners = DBUnitLoaderTestExecutionListener.class, mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
@Data(resources = {
        "classpath:/data/dataset.xml",
        "classpath:/data/aut-lev.xml",
        "classpath:/data/ist.xml"
})
@ContextConfiguration(locations = {"classpath:config/mutatie-test-datasource.xml", "classpath:config/mutatie-test-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class AbstractConversieIntegratieTest {

    @Inject
    protected PersoonslijstService persoonslijstService;

    @Inject
    private DalCacheController cacheController;

    @After
    public void clean() {
        cacheController.maakCachesLeeg();
    }
}

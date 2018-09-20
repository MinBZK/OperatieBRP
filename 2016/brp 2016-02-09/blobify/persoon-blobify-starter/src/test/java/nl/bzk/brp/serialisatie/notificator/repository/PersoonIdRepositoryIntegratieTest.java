/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie.notificator.repository;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import nl.bzk.brp.serialisatie.AbstractIntegratieTest;
import nl.bzk.brp.utils.junit.OverslaanBijInMemoryDatabase;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/config/integratieTest-context.xml")
@TransactionConfiguration(transactionManager = "alleenLezenTransactionManager", defaultRollback = true)
public class PersoonIdRepositoryIntegratieTest extends AbstractIntegratieTest {

    protected DataSource dataSrc;

    @Inject
    private PersoonIdRepository persoonIdRepository;

    @Override
    @Inject
    @Named("alleenLezenDataSource")
    public void setDataSource(final DataSource dataSource) {
        super.setDataSource(dataSource);

        this.dataSrc = dataSource;
    }

    @Test
    public void testVindAllePersoonIds() {
        final List<Integer> persoonIds = persoonIdRepository.vindAllePersoonIds();

        assertAlleIdsAanwezig(persoonIds);
    }

    @Test
    public void testVindAllePersoonIdsInBatchVan100() {
        final List<Integer> persoonIds = persoonIdRepository.vindPersoonIds(1, 100);

        assertAlleIdsAanwezig(persoonIds);
    }

    @Test
    public void testVindAllePersoonIdsInBatchVan10() {
        final List<Integer> verwachteIds = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        final List<Integer> persoonIds = persoonIdRepository.vindPersoonIds(1, 10);

        assertThat(persoonIds, (Matcher) hasItems(verwachteIds.toArray(new Integer[verwachteIds.size()])));
    }

    @Test
    public void testVindAllePersoonIdsInBatchVan10Batch2() {
        final List<Integer> verwachteIds = Arrays.asList(11, 14, 28, 42, 43, 44, 45, 46, 47, 48);

        final List<Integer> persoonIds = persoonIdRepository.vindPersoonIds(11, 10);

        assertThat(persoonIds, (Matcher) hasItems(verwachteIds.toArray(new Integer[verwachteIds.size()])));
    }

    private void assertAlleIdsAanwezig(final List<Integer> persoonIds) {
        final List<Integer> verwachteIds = Arrays.asList(1, 4, 6, 10, 14, 28, 42);

        assertThat(persoonIds, (Matcher) hasItems(verwachteIds.toArray(new Integer[verwachteIds.size()])));
    }

    /**
     * Overslaan bij inmemory database vanwege timestamp syntax in query
     */
    @Category(OverslaanBijInMemoryDatabase.class)
    @Test
    public void testVindBijgehoudenPersoonIds() {
        final List<Integer> verwachteIds = Arrays.asList(1, 2, 3, 4);

        final List<Integer> persoonIds = persoonIdRepository.vindBijgehoudenPersoonIds(1000000);

        assertThat(persoonIds, (Matcher) hasItems(verwachteIds.toArray(new Integer[verwachteIds.size()])));
    }

}

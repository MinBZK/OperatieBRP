/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie.notificator.app;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/config/integratieTest-context.xml")
@TransactionConfiguration(transactionManager = "alleenLezenTransactionManager", defaultRollback = true)
public class CliApplicatieTest extends nl.bzk.brp.serialisatie.AbstractIntegratieTest {

    protected DataSource dataSrc;

    @Override
    @Inject
    @Named("alleenLezenDataSource")
    public void setDataSource(final DataSource dataSource) {
        super.setDataSource(dataSource);

        this.dataSrc = dataSource;
    }

    @Test
    public void testCliApplicatie() throws Exception {
        final String[] args = new String[]{"-s", "specifieke-personen", "-p", "12345", "-p", "23456", "-p", "34567"};

        CliApplicatie.main(args);
    }

    @Test
    public void testCliApplicatieMetFoutiefScenario() throws Exception {
        final String[] args = new String[]{"-s", "niet-bestaand-scenario", "-p", "12345"};

        CliApplicatie.main(args);
    }

    @Test
    public void testCliApplicatieMetFoutieveArgumenten() throws Exception {
        final String[] args = new String[]{"-s", "specifieke-personen", "-p", "12345", "-n ep"};

        CliApplicatie.main(args);
    }

    @Test
    public void testCliApplicatieZonderArgumenten() throws Exception {
        final String[] args = new String[]{};

        CliApplicatie.main(args);
    }

    @Test
    public void testCliApplicatieAllePersonen() throws Exception {
        final String[] args = new String[]{"-s", "alle-personen"};

        CliApplicatie.main(args);
    }

    @Test
    public void testCliApplicatieBijgehoudenPersonen() throws Exception {
        final String[] args = new String[]{"-s", "bijgehouden-personen", "-u", "999999999"};

        CliApplicatie.main(args);
    }

    @Test
    public void testCliApplicatieBijgehoudenPersonenMetVerkeerdeUren() throws Exception {
        final String[] args = new String[]{"-s", "bijgehouden-personen", "-u", "aa"};

        CliApplicatie.main(args);
    }

}

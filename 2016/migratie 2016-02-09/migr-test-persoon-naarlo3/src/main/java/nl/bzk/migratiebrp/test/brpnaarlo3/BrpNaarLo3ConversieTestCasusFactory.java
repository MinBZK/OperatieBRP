/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.brpnaarlo3;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.BrpExcelAdapter;
import nl.bzk.migratiebrp.test.dal.AbstractTestCasusFactory;
import nl.bzk.migratiebrp.test.dal.TestCasus;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * Conversie test casus factory.
 */
public final class BrpNaarLo3ConversieTestCasusFactory extends AbstractTestCasusFactory {
    private static final Logger LOG = LoggerFactory.getLogger();
    private static final String INPUT_EXTENSIE_BRP_EXCEL = ".brp.xls";
    private final AutowireCapableBeanFactory autowireBeanFactory;

    /**
     * Constructor.
     *
     * @param context
     *            de Spring Application context
     */
    protected BrpNaarLo3ConversieTestCasusFactory(final GenericXmlApplicationContext context) {
        super(context);
        autowireBeanFactory = context.getAutowireCapableBeanFactory();
        resetDB(LOG);
    }

    @Override
    public List<TestCasus> leesTestCasussen(final File input) throws Exception {
        final String inputName = input.getName();
        LOG.info("leesTestCasussen(thema={}, input={})", getThema(), inputName);
        if (!input.isFile()) {
            LOG.error("Bestand '{}' is geen bestand.", inputName);
        }

        final List<TestCasus> result;
        if (input.getName().endsWith(INPUT_EXTENSIE_BRP_EXCEL)) {
            result = leesBrpExcel(input);
        } else {
            LOG.error("Bestand '{}' is van een onbekend type (extensie).", inputName);
            result = Collections.emptyList();
        }
        return result;
    }

    private List<TestCasus> leesBrpExcel(final File file) {
        final PlatformTransactionManager transactionManager = autowireBeanFactory.getBean(PlatformTransactionManager.class);

        final TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        final TransactionCallback<List<TestCasus>> execution = new ConversieTransactionCallback(file);
        autowireBeanFactory.autowireBean(execution);

        return transactionTemplate.execute(execution);
    }

    /**
     * implementatie van de {@link TransactionCallback}.
     */
    private final class ConversieTransactionCallback implements TransactionCallback<List<TestCasus>> {
        @PersistenceContext
        private EntityManager em;

        @Inject
        private BrpExcelAdapter excelAdapter;

        private final File file;

        private ConversieTransactionCallback(final File file) {
            this.file = file;
        }

        @Override
        public List<TestCasus> doInTransaction(final TransactionStatus status) {
            final List<TestCasus> result = new ArrayList<>();

            try (final FileInputStream fileInputStream = new FileInputStream(file)) {
                final List<Persoon> personen = excelAdapter.leesExcel(fileInputStream);
                for (int i = 0; i < personen.size(); i++) {
                    final Persoon persoon = personen.get(i);
                    em.persist(persoon);

                    if (persoon.getAdministratienummer() != null) {
                        final TestCasus testCasus =
                                new BrpNaarLo3ConversieTestCasus(
                                    getThema(),
                                    maakNaam(file.getName(), i),
                                    getOutputFolder(),
                                    getExpectedFolder(),
                                    persoon.getAdministratienummer());

                        autowireBeanFactory.autowireBean(testCasus);
                        result.add(testCasus);
                    }
                }
            } catch (final Exception e) {
                final TestCasus testCasus =
                        new BrpNaarLo3ConversieTestCasus(getThema(), maakNaam(file.getName(), 0), getOutputFolder(), getExpectedFolder(), e);
                autowireBeanFactory.autowireBean(testCasus);
                result.add(testCasus);
            }
            return result;
        }
    }
}

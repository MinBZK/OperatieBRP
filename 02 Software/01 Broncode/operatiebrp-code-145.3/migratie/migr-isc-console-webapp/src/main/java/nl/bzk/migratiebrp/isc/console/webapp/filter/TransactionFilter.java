/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.webapp.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.transaction.Transaction;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.springframework.transaction.jta.TransactionFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Transaction filter.
 */
public final class TransactionFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private TransactionFactory transactionFactory;

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {

        Transaction transaction = null;
        try {
            LOGGER.debug("Starting a transaction");
            transaction = transactionFactory.createTransaction(null, -1);

            // Call the next filter (continue request processing)
            chain.doFilter(request, response);

            // Commit and cleanup
            LOGGER.debug("Committing the database transaction");
            transaction.commit();
        } catch (final Exception ex /* Catch exception vanwege rollback. Exception wordt gerethrowed. */) {
            // Rollback
            try {
                if (transaction != null) {
                    LOGGER.debug("Trying to rollback transaction after exception");
                    transaction.rollback();
                }
            } catch (final Exception rbEx /* Catch exception van rollback. Bovenliggende excepion wordt gerethrowed. */) {
                LOGGER.error("Could not rollback after exception!", rbEx);
            }

            // Let others handle it... maybe another interceptor for exceptions?
            throw new ServletException(ex);
        }
    }

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        LOGGER.debug("Initializing filter...");
        final WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext());
        transactionFactory = wac.getBean("iscTransactionManager", TransactionFactory.class);
    }

    @Override
    public void destroy() {
        // No-op
    }

}

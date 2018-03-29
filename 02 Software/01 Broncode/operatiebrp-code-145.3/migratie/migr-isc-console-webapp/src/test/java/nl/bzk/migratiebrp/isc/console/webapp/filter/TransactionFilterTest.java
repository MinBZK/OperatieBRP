/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.webapp.filter;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.transaction.Transaction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.springframework.transaction.jta.TransactionFactory;
import org.springframework.web.context.WebApplicationContext;

public class TransactionFilterTest {

    private TransactionFilter subject;
    private TransactionFactory transactionFactory;

    @Before
    public void setup() throws ServletException {
        transactionFactory = Mockito.mock(TransactionFactory.class);
        final WebApplicationContext wac = Mockito.mock(WebApplicationContext.class);
        final ServletContext servletContext = Mockito.mock(ServletContext.class);
        final FilterConfig filterConfig = Mockito.mock(FilterConfig.class);

        Mockito.when(filterConfig.getServletContext()).thenReturn(servletContext);
        Mockito.when(servletContext.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE)).thenReturn(wac);
        Mockito.when(wac.getBean("iscTransactionManager", TransactionFactory.class)).thenReturn(transactionFactory);

        subject = new TransactionFilter();
        subject.init(filterConfig);
    }

    @Test
    public void testCommit() throws Exception {
        final ServletRequest request = Mockito.mock(ServletRequest.class);
        final ServletResponse response = Mockito.mock(ServletResponse.class);
        final FilterChain chain = Mockito.mock(FilterChain.class);

        final Transaction transaction = Mockito.mock(Transaction.class);
        Mockito.when(transactionFactory.createTransaction(null, -1)).thenReturn(transaction);

        // Execute
        subject.doFilter(request, response, chain);

        // Verify
        final InOrder order = Mockito.inOrder(transactionFactory, chain, transaction);
        order.verify(transactionFactory).createTransaction(null, -1);
        order.verify(chain).doFilter(request, response);
        order.verify(transaction).commit();

        Mockito.verifyNoMoreInteractions(transactionFactory, transaction);

        subject.destroy();
    }

    @Test
    public void testRollback() throws Exception {
        final ServletRequest request = Mockito.mock(ServletRequest.class);
        final ServletResponse response = Mockito.mock(ServletResponse.class);
        final FilterChain chain = Mockito.mock(FilterChain.class);

        final Transaction transaction = Mockito.mock(Transaction.class);
        Mockito.when(transactionFactory.createTransaction(null, -1)).thenReturn(transaction);

        Mockito.doThrow(IllegalArgumentException.class).when(chain).doFilter(request, response);

        // Execute
        try {
            subject.doFilter(request, response, chain);
            Assert.fail("ServletException verwacht");
        } catch (final ServletException e) {
            Assert.assertEquals(IllegalArgumentException.class, e.getCause().getClass());
        }

        // Verify
        final InOrder order = Mockito.inOrder(transactionFactory, chain, transaction);
        order.verify(transactionFactory).createTransaction(null, -1);
        order.verify(chain).doFilter(request, response);
        order.verify(transaction).rollback();

        Mockito.verifyNoMoreInteractions(transactionFactory, transaction);
    }

    @Test
    public void testRollbackFailed() throws Exception {
        final ServletRequest request = Mockito.mock(ServletRequest.class);
        final ServletResponse response = Mockito.mock(ServletResponse.class);
        final FilterChain chain = Mockito.mock(FilterChain.class);

        final Transaction transaction = Mockito.mock(Transaction.class);
        Mockito.when(transactionFactory.createTransaction(null, -1)).thenReturn(transaction);

        Mockito.doThrow(IllegalArgumentException.class).when(chain).doFilter(request, response);
        Mockito.doThrow(IllegalStateException.class).when(transaction).rollback();

        // Execute
        try {
            subject.doFilter(request, response, chain);
            Assert.fail("ServletException verwacht");
        } catch (final ServletException e) {
            Assert.assertEquals(IllegalArgumentException.class, e.getCause().getClass());
        }

        // Verify
        final InOrder order = Mockito.inOrder(transactionFactory, chain, transaction);
        order.verify(transactionFactory).createTransaction(null, -1);
        order.verify(chain).doFilter(request, response);
        order.verify(transaction).rollback();

        Mockito.verifyNoMoreInteractions(transactionFactory, transaction);
    }
}

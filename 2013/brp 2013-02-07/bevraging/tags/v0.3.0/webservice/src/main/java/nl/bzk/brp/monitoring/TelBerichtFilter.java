/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.monitoring;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


/**
 * Servlet filter voor het registereren van vraag en antwoorden.
 *
 */
public class TelBerichtFilter implements Filter {

    private BerichtenMBean berichtenMBean;

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub

    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
        throws IOException, ServletException
    {
        berichtenMBean.telOpInkomendeBericht();
        long startTime = System.currentTimeMillis();
        chain.doFilter(request, response);
        long endTime = System.currentTimeMillis();
        berichtenMBean.telOpUitgaandeBericht();

        berichtenMBean.telOpBerekenGemiddeldeResponseTijd((int) (endTime - startTime));
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

    public void setBerichtenMBean(final BerichtenMBean berichtenMBean) {
        this.berichtenMBean = berichtenMBean;
    }
}

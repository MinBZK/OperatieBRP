/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.viewer;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

/**
 * Filter voor de login. Hier kunnen we loggen dat er een login gedaan is.
 */
public class ViewerFormAuthenticationFilter extends FormAuthenticationFilter {

    private static final Logger LOG = LoggerFactory.getLogger();

    /**
     * Constructor.
     */
    public ViewerFormAuthenticationFilter() {
        super();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.shiro.web.filter.authc.FormAuthenticationFilter#onLoginSuccess(
     * org.apache.shiro.authc.AuthenticationToken, org.apache.shiro.subject.Subject, javax.servlet.ServletRequest,
     * javax.servlet.ServletResponse)
     */
    @Override
    protected final boolean onLoginSuccess(
            final AuthenticationToken token,
            final Subject subject,
            final ServletRequest request,
            final ServletResponse response) throws Exception {
        LOG.info("Login succesvol: " + token.getPrincipal());
        WebUtils.issueRedirect(request, response, "/", null, true);
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.shiro.web.filter.authc.FormAuthenticationFilter#onLoginFailure(
     * org.apache.shiro.authc.AuthenticationToken , org.apache.shiro.authc.AuthenticationException,
     * javax.servlet.ServletRequest, javax.servlet.ServletResponse)
     */
    @Override
    protected final boolean onLoginFailure(
            final AuthenticationToken token,
            final AuthenticationException e,
            final ServletRequest request,
            final ServletResponse response) {
        LOG.info("Login NIET succesvol: " + token.getPrincipal());
        return super.onLoginFailure(token, e, request, response);
    }

}

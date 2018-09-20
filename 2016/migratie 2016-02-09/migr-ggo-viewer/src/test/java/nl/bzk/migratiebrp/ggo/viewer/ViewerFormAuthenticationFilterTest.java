/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.junit.Test;

public class ViewerFormAuthenticationFilterTest {
    @Test
    public void testLogin() throws Exception {
        final ViewerFormAuthenticationFilter filter = new ViewerFormAuthenticationFilter();

        final UsernamePasswordToken token = new UsernamePasswordToken();
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final AuthenticationException e = new AuthenticationException();

        assertEquals(false, filter.onLoginSuccess(token, null, request, response));
        verify(response).sendRedirect(anyString());
        assertEquals(true, filter.onLoginFailure(token, e, request, response));
    }
}

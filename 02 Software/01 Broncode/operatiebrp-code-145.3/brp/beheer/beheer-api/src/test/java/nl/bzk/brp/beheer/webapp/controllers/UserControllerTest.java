/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * UserController test.
 */
public class UserControllerTest {

    private UserController subject;

    @Test
    public void testAuthenticatedUser() {
        final SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        final Authentication authentication = Mockito.mock(Authentication.class);
        final UserDetails userDetails = Mockito.mock(UserDetails.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
        final SecurityContextHolderStrategy securityContextHolderStrategy = Mockito.mock(SecurityContextHolderStrategy.class);
        Mockito.when(securityContextHolderStrategy.getContext()).thenReturn(securityContext);
        final SecurityContextHolder securityContextHolder = new SecurityContextHolder();
        ReflectionTestUtils.setField(securityContextHolder, "strategy", securityContextHolderStrategy);

        subject = new UserController();
        Assert.assertNotNull("Er moeten userdetails zijn", subject.authenticatedUser());
    }

    @Test
    public void testGeenAuthenticationAanwezig() {
        final SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(null);
        final SecurityContextHolderStrategy securityContextHolderStrategy = Mockito.mock(SecurityContextHolderStrategy.class);
        Mockito.when(securityContextHolderStrategy.getContext()).thenReturn(securityContext);
        final SecurityContextHolder securityContextHolder = new SecurityContextHolder();
        ReflectionTestUtils.setField(securityContextHolder, "strategy", securityContextHolderStrategy);

        subject = new UserController();
        Assert.assertNull("Er kunnen geen userdetails zijn", subject.authenticatedUser());
    }

    @Test
    public void testPrincipleIsGeenUserDetail() {
        final SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        final Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getPrincipal()).thenReturn("fout antwoord");
        final SecurityContextHolderStrategy securityContextHolderStrategy = Mockito.mock(SecurityContextHolderStrategy.class);
        Mockito.when(securityContextHolderStrategy.getContext()).thenReturn(securityContext);
        final SecurityContextHolder securityContextHolder = new SecurityContextHolder();
        ReflectionTestUtils.setField(securityContextHolder, "strategy", securityContextHolderStrategy);

        subject = new UserController();
        Assert.assertNull("Er kunnen geen userdetails zijn", subject.authenticatedUser());
    }
}

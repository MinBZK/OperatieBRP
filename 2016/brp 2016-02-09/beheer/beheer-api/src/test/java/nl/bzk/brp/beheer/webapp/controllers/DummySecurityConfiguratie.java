/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers;

import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Dummy implementatie voor UserDetailsService voor controller testen.
 */
@Configuration
public class DummySecurityConfiguratie {

    @Bean
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return new UserDetailsService() {

            @Override
            public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
                return new User(username, "password", Collections.<GrantedAuthority>emptyList());
            }
        };
    }

}

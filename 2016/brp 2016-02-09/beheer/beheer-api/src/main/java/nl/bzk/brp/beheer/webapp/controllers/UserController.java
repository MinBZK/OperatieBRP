/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers;

import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * User controller.
 */
@RestController
@RequestMapping(value = ControllerConstants.USER_URI)
public final class UserController {

    //@Autowired
    //private UserDetailsService userService;

    /**
     * Geef de user details van de ingelogde gebruiker.
     *
     * @return user details
     */
    @RequestMapping(value = "/authenticated", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public UserDetails authenticatedUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails)) {
            return null;
        }

        return (UserDetails) authentication.getPrincipal();
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.spring;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Mix-in voor {@link org.springframework.security.core.userdetails.UserDetails}.
 */
public interface UserDetailsMixIn extends UserDetails {

    @Override
    @JsonProperty
    Collection<? extends GrantedAuthority> getAuthorities();

    @Override
    @JsonProperty
    String getUsername();
}

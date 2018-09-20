/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Configuratie controller.
 */
@RestController
@RequestMapping(value = ControllerConstants.CONFIGURATIE_URI)
public final class ConfiguratieController implements InitializingBean {

    private static final String SLASH = "/";

    private final Map<String, String> configuratie = new HashMap<>();

    @Inject
    private Environment environment;

    @Override
    public void afterPropertiesSet() {
        String iscUrl = environment.getRequiredProperty("isc.url");
        if (!iscUrl.endsWith(SLASH)) {
            iscUrl = iscUrl + SLASH;
        }

        configuratie.put("isc-processenOpAh", iscUrl + environment.getRequiredProperty("isc.processenOpAh"));
    }

    /**
     * Geef de configuratie gegevens.
     *
     * @return configuratie gegevens
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> configuratie() {
        return configuratie;
    }

}

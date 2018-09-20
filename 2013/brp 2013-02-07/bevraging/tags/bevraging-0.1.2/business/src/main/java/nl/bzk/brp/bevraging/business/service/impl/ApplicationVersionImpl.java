/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.service.impl;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import nl.bzk.brp.bevraging.business.service.SvnVersionService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * Logt de versie van de applicatie bij het starten.
 */
@Service
public class ApplicationVersionImpl {

    /**
     * Een Service welke bij het opstarten een regel met de versie van de BRP logt.
     */

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationVersionImpl.class);

    @Inject
    private SvnVersionService   svnVersionService;

    /**
     * Init method welke de versie logt.
     */

    @SuppressWarnings("unused")
    @PostConstruct
    public void init() {
        LOGGER.info("Staring " + svnVersionService.getAppString() + "...");
    }

}

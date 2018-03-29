/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime.start;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.register.client.RegisterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Start de regsiters door refresh() aan te roepen.
 */
@Component
public final class RegisterStarter implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Autowired
    private RegisterService<?>[] registerServices;

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        // Starten
        for (final RegisterService<?> registerService : registerServices) {
            LOG.info("Starting register: {}", registerService.getClass().getName());
            registerService.refreshRegister();
        }
    }
}

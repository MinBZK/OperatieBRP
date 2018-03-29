/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.cache;

import javax.inject.Inject;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

/**
 * Jmx support.
 */
@Component
@ManagedResource(objectName = "caches:name=Caches",
        description = "Het herladen van partij-cache, stamtabel-cache en leveringsautorisatiecache.")
public class JmxSupport {


    private CacheController cacheController;

    /**
     * Constructor.
     * @param cacheController cache controller
     */
    @Inject
    public JmxSupport(final CacheController cacheController) {
        this.cacheController = cacheController;
    }

    /**
     * Herlaad stamtabel,partij- en autorisatiecache.
     * NB: methode kan niet final zijn ivm cglib
     */
    @ManagedOperation(description = "herlaadCaches")
    public void herlaadCaches() {
        cacheController.herlaadCaches();
    }

}

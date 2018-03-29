/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.cache;

import java.util.Collection;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.repositories.CacheClearable;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

/**
 * Deze class beheert de caches van bijhouding DAL. Via de controller kunnen de caches leeg gemaakt
 * worden.
 */
@Component
@ManagedResource(objectName = "caches:name=AlgemeenDALCaches", description = "Het beheren van de AlgemeenDALCaches.")
public final class DalCacheController {

    private final Collection<CacheClearable> objects;

    /**
     * Constructor.
     * @param objects clearable caches
     */
    @Inject
    public DalCacheController(final Collection<CacheClearable> objects) {
        this.objects = objects;
    }

    /**
     * Maakt alle caches leeg.
     */
    @ManagedOperation(description = "maakt de caches leeg")
    public void maakCachesLeeg() {
        for (final CacheClearable object : objects) {
            object.clear();
        }
    }
}

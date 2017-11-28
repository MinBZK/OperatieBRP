/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.domain.leveringmodel.MetaObject;

/**
 * Helper voor object sleutels.
 */
public final class ObjectSleutelsHelper {

    private ObjectSleutelsHelper() {
        // Niet instantieerbaar
    }

    /**
     * Bepaal object sleutels voor object.
     * @param object object
     * @return object sleutels
     */
    public static List<Long> bepaalObjectSleutels(final MetaObject object) {
        final List<Long> objectSleutels = new ArrayList<>();

        objectSleutels.add(object.getObjectsleutel());

        return objectSleutels;
    }
}

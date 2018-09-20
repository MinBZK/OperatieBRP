/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.hisvolledig.kern.PersoonIndicatieHisVolledig;

/**
 * Helper voor object sleutels.
 */
public final class ObjectSleutelsHelper {

    private ObjectSleutelsHelper() {
        // Niet instantieerbaar
    }

    /**
     * Bepaal object sleutels voor object.
     *
     * @param object
     *            object
     * @return object sleutels
     */
    public static List<Long> bepaalObjectSleutels(final ModelIdentificeerbaar<? extends Number> object) {
        final List<Long> objectSleutels = new ArrayList<>();

        final Number objectSleutel = object.getID();
        if (objectSleutel != null) {
            objectSleutels.add(objectSleutel.longValue());
        }

        return objectSleutels;
    }

    /**
     * Bepaal object sleutels voor indicatie.
     *
     * @param indicatie
     *            indicatie
     * @return object sleutels
     */
    public static List<Long> bepaalObjectSleutels(final PersoonIndicatieHisVolledig<?> indicatie) {
        final List<Long> objectSleutels = new ArrayList<>();

        final Integer objectSleutel = indicatie.getID();
        if (objectSleutel != null) {
            objectSleutels.add(objectSleutel.longValue());
        }

        return objectSleutels;
    }
}

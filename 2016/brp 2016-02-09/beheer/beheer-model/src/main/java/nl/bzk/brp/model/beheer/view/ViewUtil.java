/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.beheer.view;

import java.util.List;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;

/**
 * View utilities.
 */
public final class ViewUtil {

    private ViewUtil() {
        // Niet instantieerbaar
    }

    /**
     * Bepaal uit een lijst de view voor een bepaald basis object.
     *
     * @param lijst lijst
     * @param object basis object
     * @param <T> basis object type
     * @return de view voor het gegeven basis object
     *
     */
    public static <T extends ModelIdentificeerbaar<?>> ObjectView<T> geefViewVoor(final List<ObjectView<T>> lijst, final T object) {
        for (final ObjectView<T> view : lijst) {
            if (view.isViewVoor(object)) {
                return view;
            }
        }

        final ObjectView<T> view = new ObjectView<T>(object);
        lijst.add(view);
        return view;
    }

}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Utility class gebruikt door Jibx databinding.
 */
public final class BindingUtil {

    /**
     * Constructor. Private toegang omdat dit een utility class is.
     */
    private BindingUtil() {
    }

    /**
     * Retourneert een List implementatie die gebruikt wordt door Jibx databinding.
     *
     * @param <T> type van de list
     * @return ArrayList implementatie
     */
    public static <T> List<T> newListInstance() {
        return new ArrayList<T>();
    }

    /**
     * Retourneert een Set implementatie die gebruikt wordt door Jibx databinding.
     *
     * @param <T> type van de set
     * @return HashSet implementatie
     */
    public static <T> Set<T> newSetInstance() {
        return new HashSet<T>();
    }
}

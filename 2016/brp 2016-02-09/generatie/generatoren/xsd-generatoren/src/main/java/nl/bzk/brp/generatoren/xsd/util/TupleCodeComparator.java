/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.xsd.util;

import java.io.Serializable;
import java.util.Comparator;

import nl.bzk.brp.metaregister.model.Tuple;

/**
 * Comparator voor tuples die op de code van een tuple sorteert.
 */
public class TupleCodeComparator implements Comparator<Tuple>, Serializable {

    @Override
    public int compare(final Tuple o1, final Tuple o2) {
        return o1.getCode().compareTo(o2.getCode());
    }

}

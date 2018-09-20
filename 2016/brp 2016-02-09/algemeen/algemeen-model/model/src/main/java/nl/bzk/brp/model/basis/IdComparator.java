/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

import java.io.Serializable;
import java.util.Comparator;


/**
 * Standaard comparator voor Collecties in het model. Altijd sorteren op Id. We willen altijd een vaste voorspelbare volgorde hebben van entiteiten. Zodat
 * bijvoorbeeld de volgorde in uitgaande xml voorspelbaar is.
 */
public final class IdComparator extends AbstractIdComparator implements Comparator<ModelIdentificeerbaar>, Serializable {

    @Override
    public int compare(final ModelIdentificeerbaar obj1, final ModelIdentificeerbaar obj2) {
        return vergelijkModelIdentificeerbaar(obj1, obj2);
    }

}

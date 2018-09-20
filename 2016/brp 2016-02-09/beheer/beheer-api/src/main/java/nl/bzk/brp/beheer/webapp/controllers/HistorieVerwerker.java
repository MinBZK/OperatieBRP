/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers;

import nl.bzk.brp.model.basis.FormeelHistorisch;

/**
 * Historie verwerker.
 *
 * @param <T> entiteit type
 * @param <H> historie type
 */
public interface HistorieVerwerker<T, H extends FormeelHistorisch> {

    /**
     * Verwerk historie.
     *
     * @param item item (input)
     * @param managedItem item (entity)
     */
    void verwerkHistorie(final T item, final T managedItem);

}

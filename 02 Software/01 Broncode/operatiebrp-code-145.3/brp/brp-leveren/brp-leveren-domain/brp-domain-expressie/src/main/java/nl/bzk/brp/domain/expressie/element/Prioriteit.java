/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.element;

import nl.bzk.brp.domain.leveringmodel.MetaGroep;
import nl.bzk.brp.domain.leveringmodel.MetaObject;

/**
 * Definieert de prioritieit van de resolver. Voor een gegeven expressie kunnen meerdere
 * resolvers gedefinieerd zijn, bijv voor x.DatumAanvangGeldigheid zijn dat er meer dan 100.
 * Na het filteren mbt {{@link Resolver#matchContext(Object)}} kunnen er twee overblijven, een resolver
 * op {@link MetaGroep} en een resolver op {@link MetaObject} als parent.
 * De resolver op groep is sprecifieker dan de resolver op object en heeft daarom
 * een hogere prioriteit.
 */
enum Prioriteit {

    /**
     * Prioriteit waarde hoog.
     */
    HOOG(100),
    /**
     * Prioriteit waarde middel.
     */
    MIDDEL(50),
    /**
     * Prioriteit waarde laag.
     */
    LAAG(0);

    private int prioriteitWaarde;

    /**
     * @param prioriteitWaarde prioriteitWaarde
     */
    Prioriteit(final int prioriteitWaarde) {
        this.prioriteitWaarde = prioriteitWaarde;
    }

    /**
     * @return prioriteitWaarde
     */
    public int getPrioriteitWaarde() {
        return prioriteitWaarde;
    }
}

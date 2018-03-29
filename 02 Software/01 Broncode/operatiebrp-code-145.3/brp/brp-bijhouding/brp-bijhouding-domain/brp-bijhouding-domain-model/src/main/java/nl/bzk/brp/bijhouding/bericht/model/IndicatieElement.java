/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import nl.bzk.brp.bijhouding.bericht.annotation.XmlElementInterface;

/**
 * Een indicatie die deel uitmaakt van een persoon in een bijhoudingsbericht.
 *
 */
@XmlElementInterface(IndicatieRegister.class)
public interface IndicatieElement extends BmrObjecttype {

    /**
     * Geeft de waarde van heeftIndiciatie.
     *
     * @return heeftIndicatie
     */
    BooleanElement getHeeftIndicatie();
}

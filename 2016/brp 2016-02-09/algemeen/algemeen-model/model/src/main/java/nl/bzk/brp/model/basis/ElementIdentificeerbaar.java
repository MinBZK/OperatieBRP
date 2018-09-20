/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;


/**
 * Interface voor groepen en objecttypen uit het Kern schema die gekoppeld zijn aan een instantie van de Element {@link
 * nl.bzk.brp.model.algemeen.stamgegeven.kern.Element} enumeratie. Zo kun je aan een klasse die deze interface implementeert 'vragen' tot welk Element het
 * behoort.
 */
public interface ElementIdentificeerbaar {

    /**
     * Geeft het Element dat dit object identificeert.
     *
     * @return element
     */
    ElementEnum getElementIdentificatie();
}

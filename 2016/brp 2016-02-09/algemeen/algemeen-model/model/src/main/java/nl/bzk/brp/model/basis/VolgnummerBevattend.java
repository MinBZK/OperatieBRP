/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;


/**
 * Interface voor objectend die een {@link Volgnummer} bevatten en dus conform het model sorteerbaar en eventueel indexeerbaar op volgnummer zijn.
 */
public interface VolgnummerBevattend {

    /**
     * Retourneert het volgnummer van dit object.
     *
     * @return het volgnummer.
     */
    VolgnummerAttribuut getVolgnummer();

}

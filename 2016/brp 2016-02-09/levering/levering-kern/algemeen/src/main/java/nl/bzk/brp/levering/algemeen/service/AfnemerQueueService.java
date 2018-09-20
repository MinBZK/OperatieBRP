/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.algemeen.service;

import java.util.List;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;


/**
 * De Interface AfnemerQueueService waarin de methoden staan gedefinieerd die te maken hebben met Afnemers.
 */
public interface AfnemerQueueService {

    /**
     * Haalt een lijst van partijcodes op van afnemers die een endpoint ingesteld hebben.
     *
     * @return De lijst van partijcodes van de afnemers.
     */
    List<PartijCodeAttribuut> haalPartijCodesWaarvoorGeleverdMoetWorden();

}

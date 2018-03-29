/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.autorisatie;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;


/**
 * PartijService. Service voor partij tabel interactie.
 */
public interface PartijService {

    /**
     * @param code de partij code
     * @return een partij id of null
     */
    Short vindPartijIdOpCode(String code);

    /**
     * @param code de partij code
     * @return een partij
     */
    Partij vindPartijOpCode(String code);

    /**
     * @param id de partij id
     * @return een partij
     */
    Partij vindPartijOpId(short id);


    /**
     * @param oin de oin van de partij
     * @return een partij
     */
    Partij vindPartijOpOin(String oin);

    /**
     * @return de BRP partij
     */
    Partij geefBrpPartij();
}

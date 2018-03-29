/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.register;

import java.io.Serializable;
import java.util.List;

/**
 * Partij register voor opzoeken partijen.
 */
public interface PartijRegister extends Serializable {

    /**
     * Geef alle partijen.
     * @return lijst met alle partijen.
     */
    List<Partij> geefAllePartijen();

    /**
     * Geef een partij terug op basis van gegeven partij code.
     * @param partijCode partij code van de gezochte partij
     * @return de partij
     */
    Partij zoekPartijOpPartijCode(String partijCode);


    /**
     * Geef een partij terug op basis van gegeven gemeente code.
     * @param gemeenteCode gemeente code van de gezochte partij
     * @return de partij
     */
    Partij zoekPartijOpGemeenteCode(String gemeenteCode);
}

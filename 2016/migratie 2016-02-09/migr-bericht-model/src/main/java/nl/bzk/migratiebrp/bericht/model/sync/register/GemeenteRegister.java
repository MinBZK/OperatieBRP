/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.register;

import java.util.List;

/**
 * Definitie van de functionaliteit van het gemeente register.
 */
public interface GemeenteRegister {

    /**
     * Geef de volledige lijst van alle gemeenten.
     * 
     * @return lijst van alle gemeenten
     */
    List<Gemeente> geefAlleGemeenten();

    /**
     * Zoek een gemeente op gemeente code.
     * 
     * @param gemeenteCode
     *            gemeente code (4-cijferig)
     * @return gemeente, of null als de gemeente niet gevonden kan worden.
     */
    Gemeente zoekGemeenteOpGemeenteCode(String gemeenteCode);

    /**
     * Zoek een gemeente op partij coe.
     * 
     * @param partijCode
     *            partij code (6-cijferig)
     * @return gemeente, of null als de gemeente niet gevonden kan worden.
     */
    Gemeente zoekGemeenteOpPartijCode(String partijCode);
}

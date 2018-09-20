/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.register;

import java.util.List;

/**
 * Definitie van de functionaliteit van het autorisatie register.
 */
public interface AutorisatieRegister {

    /**
     * Geef de volledige lijst van alle autorisaties.
     *
     * @return lijst van autorisaties
     */
    List<Autorisatie> geefAlleAutorisaties();

    /**
     * Zoek een autorisatie op partij code.
     *
     * @param partijCode
     *            partij code (6-cijferig)
     * @return autorisatie, of null als de code niet gevonden kan worden.
     */
    Autorisatie zoekAutorisatieOpPartijCode(String partijCode);
}

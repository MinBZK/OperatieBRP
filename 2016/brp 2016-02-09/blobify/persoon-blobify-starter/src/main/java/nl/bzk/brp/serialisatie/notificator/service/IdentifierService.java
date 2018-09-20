/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie.notificator.service;


import java.util.List;

/**
 * Interface voor converteren van identifiers van "command line interface" (String) naar juiste formaat (Integer).
 */
public interface IdentifierService {

    /**
     * Converteer lijst van String naar Integer.
     *
     * @param cliLijst de lijst met Strings uit command line interface
     * @return de lijst met Integers
     */
    List<Integer> converteerLijstString(List<String> cliLijst);
}

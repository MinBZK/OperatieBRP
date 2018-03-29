/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.verzoek;

import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpVerzoekBericht;

/**
 * Controle obv van het verzoek.
 */
public interface VerzoekControle {

    /**
     * Controle.
     * @param verzoek verzoek
     * @return true, als de controle voldoet, anders false
     */
    boolean controleer(SynchroniseerNaarBrpVerzoekBericht verzoek);
}

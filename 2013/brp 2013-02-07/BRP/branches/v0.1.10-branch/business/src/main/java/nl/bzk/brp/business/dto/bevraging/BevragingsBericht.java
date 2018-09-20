/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.dto.bevraging;

import nl.bzk.brp.business.dto.BRPBericht;

/**
 * Generieke interface voor alle bevragingsberichten. Deze interface specificeert generieke methodes op de
 * bevragings berichten.
 */
public interface BevragingsBericht extends BRPBericht {

    /**
     * Retourneert de vraag die gesteld wordt in het BRP bevragingsbericht.
     * @return De vraag.
     */
    AbstractVraag getVraag();

}

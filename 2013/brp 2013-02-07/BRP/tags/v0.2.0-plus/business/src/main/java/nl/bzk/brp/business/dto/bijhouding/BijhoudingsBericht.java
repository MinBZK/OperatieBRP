/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.dto.bijhouding;

import java.util.List;

import nl.bzk.brp.business.dto.BRPBericht;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.validatie.OverruleMelding;

/**
 * Generieke interface voor alle bijhoudingsberichten. Deze interface specificeert generieke methodes op de
 * bijhoudings berichten.
 */
public interface BijhoudingsBericht extends BRPBericht {

    /**
     * Retourneert de actie(s) die dienen te worden uitgevoerd en de bijhouding bevatten.
     * @return de actie(s) die dienen te worden uitgevoerd en de bijhouding bevatten.
     */
    List<Actie> getBrpActies();

    /**
     * Retourneert de meldingen die tijdens de verwerking van de bijhouding genegeerd mogen/dienen te worden.
     * @return de meldingen die genegeerd dienen te worden.
     */
    List<OverruleMelding> getOverruledMeldingen();

}

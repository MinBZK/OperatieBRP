/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis;

import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkToevalligeGebeurtenisVerzoekBericht;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;

/**
 * Interface voor het verwerken van toevallige gebeurtenis verzoeken.
 */
public interface ToevalligeGebeurtenisVerwerker {

    /**
     * Maakt op basis van een toevallige gebeurtenis verzoek een BRP equivalent opdracht bericht aan.
     *
     * @param verzoek
     *            Het binnenkomende verzoek.
     * @param rootPersoon
     *            De persoon waar vanuit de bijhouding plaatsvindt.
     * @return True indien de verwerking is gelukt en de inhoudelijke controle is geslaagd, false in andere gevallen.
     * @throws IllegalArgumentException
     *             in het geval er geen opdracht o.b.v. het verzoek kan worden opgesteld.
     */
    boolean verwerk(VerwerkToevalligeGebeurtenisVerzoekBericht verzoek, final Persoon rootPersoon);

}

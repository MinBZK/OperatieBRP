/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer;

import java.util.List;
import nl.bzk.migratiebrp.ggo.viewer.log.FoutMelder;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoPersoonslijstGroep;

/**
 * Verwerkt de zoek anummer actie van de front end.
 */
public interface ZoekAnummerVerwerker {

    /**
     * Zoekt in de Database naar pl en conversie gegevens op basis van Anummer.
     * @param aNummer Long
     * @param foutMelder De foutMelder waar de errors gelogt worden.
     * @return String met de persoonslijst.
     */
    List<GgoPersoonslijstGroep> zoekOpAnummer(String aNummer, FoutMelder foutMelder);

}

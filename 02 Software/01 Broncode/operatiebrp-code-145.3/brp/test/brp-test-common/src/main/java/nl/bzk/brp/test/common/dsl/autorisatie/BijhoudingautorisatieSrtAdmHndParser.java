/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.test.common.dsl.autorisatie;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Bijhoudingsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BijhoudingsautorisatieSoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.brp.test.common.dsl.DslSectie;

/**
 *
 */
final class BijhoudingautorisatieSrtAdmHndParser {
    static final String SECTIE_BIJHAUT_SRTADMHND = "Bijhoudingsautorisatie Soort Administratieve Handeling";

    private BijhoudingautorisatieSrtAdmHndParser() {
    }

    static BijhoudingsautorisatieSoortAdministratieveHandeling parse(final DslSectie sectie,
                                                                     final Bijhoudingsautorisatie bijhoudingsautorisatie) {

        final SoortAdministratieveHandeling srtadmhnd = sectie.geefInteger("srtadmhnd").map(SoortAdministratieveHandeling::parseId).orElse(null);
        return new BijhoudingsautorisatieSoortAdministratieveHandeling(bijhoudingsautorisatie, srtadmhnd);
    }
}

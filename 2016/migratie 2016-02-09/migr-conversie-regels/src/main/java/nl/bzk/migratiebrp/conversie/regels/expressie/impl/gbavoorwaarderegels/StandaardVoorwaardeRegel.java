/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl.gbavoorwaarderegels;

import org.springframework.stereotype.Component;

/**
 * Standaard GBA voorwaarde regel vertaling. Volgens LO3 hoeft de StandaardVoorwaardeRegel alleen de operatoren: - GA1 -
 * GAA - OGA1 - OGAA te verwerken.
 *
 */
@Component
public class StandaardVoorwaardeRegel extends AbstractStandaardVoorwaardeRegel {

    private static final String REGEX_PATROON = ".*";
    private static final int VOLGORDE = 999;

    /**
     * Maakt nieuwe voorwaarderegel aan.
     */
    public StandaardVoorwaardeRegel() {
        super(VOLGORDE, REGEX_PATROON);
    }

    @Override
    public final String vertaalWaardeVanRubriek(final String ruweWaarde) {
        return ruweWaarde.replaceAll("^0+", "").replaceAll("\\/\\*", "\\*");
    }
}

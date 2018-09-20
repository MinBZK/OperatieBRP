/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl.gbavoorwaarderegels;

import nl.bzk.migratiebrp.conversie.regels.expressie.impl.BrpType;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaRubriekOnbekendExceptie;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaVoorwaardeOnvertaalbaarExceptie;
import org.springframework.stereotype.Component;

/**
 * Vertaald de KNV 07.67.10 en KNV 07.67.20 opgeschort regel.
 * 
 */
@Component
public class OpgeschortVoorwaardeRegel extends AbstractGbaVoorwaardeRegel {

    /**
     * Regex expressie voor selectie van voorwaarderegels die door deze class
     * worden behandeld.
     */
    private static final String REGEX_PATROON = "^(KV|KNV)\\ 07\\.67\\.(1|2)0";
    private static final int VOLGORDE = 100;

    /**
     * Maakt nieuwe voorwaarderegel aan.
     */
    public OpgeschortVoorwaardeRegel() {
        super(VOLGORDE, REGEX_PATROON);
    }

    @Override
    public final String getBrpExpressie(final String voorwaardeRegel) throws GbaVoorwaardeOnvertaalbaarExceptie {
        final String[] delen = voorwaardeRegel.split(" ");
        final StringBuilder result = new StringBuilder();
        try {
            final BrpType brpType = getGbaRubriekNaarBrpTypeVertaler().vertaalGbaRubriekNaarBrpType("07.67.20")[0];
            switch (delen[0]) {
                case "KV":
                    result.append(String.format("%s <> \"A\"", brpType.getType()));
                    break;
                case "KNV":
                    result.append(String.format("%s = \"A\"", brpType.getType()));
                    break;
                default:
                    throw new GbaVoorwaardeOnvertaalbaarExceptie(voorwaardeRegel);
            }
        } catch (GbaRubriekOnbekendExceptie groe) {
            throw new GbaVoorwaardeOnvertaalbaarExceptie(voorwaardeRegel, groe);
        }

        return result.toString();
    }
}

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
 * Voorwaarde regel conversie voor aanduiding bijzonder nederlanderschap.
 */
@Component
public class AanduidingBijzonderNederlanderschapVoorwaardeRegel extends AbstractGbaVoorwaardeRegel {

    private static final String REGEX = "^04\\.65\\.10.*";
    private static final int VOLGORDE = 400;

    /**
     * Constructor.
     */
    public AanduidingBijzonderNederlanderschapVoorwaardeRegel() {
        super(VOLGORDE, REGEX);
    }

    @Override
    public String getBrpExpressie(final String voorwaardeRegel) throws GbaVoorwaardeOnvertaalbaarExceptie {
        final String[] delen = voorwaardeRegel.split(SPLIT_CHARACTER);

        final BrpType brpType;
        try {
            if ("B".equals(delen[DEEL_REST].replaceAll("\"", ""))) {
                brpType = getGbaRubriekNaarBrpTypeVertaler().vertaalGbaRubriekNaarBrpType("04.65.10.b")[0];
            } else if ("V".equals(delen[DEEL_REST].replaceAll("\"", ""))) {
                brpType = getGbaRubriekNaarBrpTypeVertaler().vertaalGbaRubriekNaarBrpType("04.65.10.v")[0];
            } else {
                throw new GbaVoorwaardeOnvertaalbaarExceptie(voorwaardeRegel);
            }
        } catch (final GbaRubriekOnbekendExceptie e) {
            throw new GbaVoorwaardeOnvertaalbaarExceptie(voorwaardeRegel, e);
        }

        final String result;
        switch (delen[DEEL_OPERATOR]) {
            case "GA1":
            case "GAA":
                result = String.format("NIET IS_NULL(%s)", brpType.getType());
                break;
            case "OGA1":
            case "OGAA":
                result = String.format("IS_NULL(%s)", brpType.getType());
                break;
            default:
                throw new GbaVoorwaardeOnvertaalbaarExceptie(voorwaardeRegel);
        }

        return result;
    }
}

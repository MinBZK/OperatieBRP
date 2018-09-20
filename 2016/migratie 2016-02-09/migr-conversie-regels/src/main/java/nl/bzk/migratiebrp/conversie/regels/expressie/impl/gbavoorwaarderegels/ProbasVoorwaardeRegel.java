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
 * Voorwaarde regel om een voorwaarde op gepriviligeerde te doen.
 */
@Component
public final class ProbasVoorwaardeRegel extends AbstractGbaVoorwaardeRegel {

    private static final String REGEX = "^04\\.82\\.30\\s*(O)?GA(1|A).*(?i:proba).*";
    private static final int VOLGORDE = 400;

    /**
     * Constructor.
     */
    public ProbasVoorwaardeRegel() {
        super(VOLGORDE, REGEX);
    }

    @Override
    public String getBrpExpressie(final String voorwaardeRegel) throws GbaVoorwaardeOnvertaalbaarExceptie {
        final BrpType[] brpType;
        try {
            brpType = getGbaRubriekNaarBrpTypeVertaler().vertaalGbaRubriekNaarBrpType("probas");
        } catch (final GbaRubriekOnbekendExceptie e) {
            throw new GbaVoorwaardeOnvertaalbaarExceptie(voorwaardeRegel, e);
        }

        final String[] delen = voorwaardeRegel.split(SPLIT_CHARACTER, DEEL_AANTAL);
        final String operator = delen[DEEL_OPERATOR];

        final String result;
        switch (operator.toUpperCase()) {
            case "OGA1":
            case "OGAA":
                result = String.format("IS_NULL(%s)", brpType[0].getType());
                break;
            case "GA1":
            case "GAA":
                result = String.format("NIET IS_NULL(%s)", brpType[0].getType());
                break;
            default:
                throw new GbaVoorwaardeOnvertaalbaarExceptie(voorwaardeRegel);
        }

        return result;
    }
}

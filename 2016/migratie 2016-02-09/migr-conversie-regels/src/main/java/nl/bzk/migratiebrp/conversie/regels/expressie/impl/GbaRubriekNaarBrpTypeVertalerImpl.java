/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl;

import java.util.List;
import nl.bzk.migratiebrp.conversie.regels.expressie.mapping.RubriekMapping;
import nl.bzk.migratiebrp.conversie.regels.expressie.mapping.RubriekMapping.Expressie;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class GbaRubriekNaarBrpTypeVertalerImpl implements GbaRubriekNaarBrpTypeVertaler {

    @Override
    public final BrpType[] vertaalGbaRubriekNaarBrpType(final String rubriek) throws GbaRubriekOnbekendExceptie {
        final List<Expressie> expressies = RubriekMapping.getExpressiesVoorRubriek(rubriek);
        if (expressies == null) {
            throw new GbaRubriekOnbekendExceptie(rubriek);
        }
        final BrpType[] result = new BrpType[expressies.size()];
        for (int x = 0; x < expressies.size(); x++) {
            final Expressie expressie = expressies.get(x);
            if (expressie.getParent() == null) {
                result[x] = new BrpType(expressie.getExpressie());
            } else {
                result[x] = new BrpType(String.format("RMAP(%1$s, x, x.%2$s)", expressie.getParent(), expressie.getExpressie()));
            }
        }
        return result;
    }

}

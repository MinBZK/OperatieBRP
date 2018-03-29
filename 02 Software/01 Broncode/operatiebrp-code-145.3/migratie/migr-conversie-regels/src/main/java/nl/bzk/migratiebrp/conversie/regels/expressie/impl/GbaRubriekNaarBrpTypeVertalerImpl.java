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
 * Implementatie van GbaRubriekNaarBrpTypeVertaler.
 */
@Component
public class GbaRubriekNaarBrpTypeVertalerImpl implements GbaRubriekNaarBrpTypeVertaler {

    @Override
    public final BrpType[] vertaalGbaRubriekNaarBrpType(final String rubriek) throws GbaRubriekOnbekendExceptie {
        final List<Expressie> expressies;
        if (rubriek.endsWith(".vragen") && !RubriekMapping.isErEenExpressieVoorRubriek(rubriek)) {
            expressies = RubriekMapping.getExpressiesVoorRubriek(rubriek.replaceAll(".vragen", ""));
        } else {
            expressies = RubriekMapping.getExpressiesVoorRubriek(rubriek);
        }

        if (expressies == null) {
            throw new GbaRubriekOnbekendExceptie(rubriek);
        }
        final BrpType[] result = new BrpType[expressies.size()];
        for (int x = 0; x < expressies.size(); x++) {
            final Expressie expressie = expressies.get(x);
            if (expressie.getParent() == null) {
                final List<Expressie> lijstExpressies = RubriekMapping.getExpressiesVoorRubriek(rubriek + ".lijst");
                boolean lijstExpressie = expressie.getExpressie().contains("HIS");
                lijstExpressie = lijstExpressie || (lijstExpressies != null && "true".equals(lijstExpressies.get(x).getExpressie()));
                result[x] =
                        new BrpType(expressie.getExpressie(), lijstExpressie, expressie.getKvInverse());
            } else {
                result[x] = new BrpType(String.format("%1$s.%2$s", expressie.getParent(), expressie.getExpressie()), true, expressie.getKvInverse());
            }
        }
        return result;
    }

}

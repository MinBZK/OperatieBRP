/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Expressies welke met EN moeten worden gekoppeld.
 */
public class EnWaarde implements Expressie {

    private static final String EN = " EN ";

    private final List<Expressie> expressies;

    /**
     * Constructor.
     * @param expressie expressies
     */
    public EnWaarde(final Expressie... expressie) {
        expressies = new ArrayList<>();
        Arrays.stream(expressie).forEach(expressies::add);
    }

    @Override
    public final String getBrpExpressie() {
        if (!expressies.isEmpty()) {
            final StringBuilder builder = new StringBuilder("(");
            expressies.forEach(expressie -> builder.append(expressie.getBrpExpressie()).append(EN));
            final int vervangingStart = builder.lastIndexOf(EN);
            builder.replace(vervangingStart, vervangingStart + EN.length(), ")");
            return builder.toString();
        } else {
            throw new IllegalStateException("En zonder expressies kan niet");
        }

    }

    @Override
    public final List<Criterium> getCriteria() {
        final List<Criterium> resultaat = new ArrayList<>();
        expressies.forEach(criterium -> resultaat.addAll(criterium.getCriteria()));
        return resultaat;
    }
}

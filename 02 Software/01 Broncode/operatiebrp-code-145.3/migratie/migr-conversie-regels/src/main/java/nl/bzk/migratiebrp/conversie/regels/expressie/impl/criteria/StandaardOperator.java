/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria;

/**
 * Een functie operator bijv. KV(element).
 */
public class StandaardOperator implements Operator {

    private final String operator;

    /**
     * Constructor.
     * @param operator de functie operator
     */
    public StandaardOperator(final String operator) {
        this.operator = operator;
    }

    @Override
    public final String apply(final String element, final String waarde) {
        return String.format(wildcardZoeken(waarde), element, waarde);
    }

    private String wildcardZoeken(final String waarde) {
        String aangepasteOperator = operator;
        if (waarde != null && waarde.contains("*")) {
            aangepasteOperator = operator.replace("=", "=%%").replace("IN", "IN%%");
        }
        return aangepasteOperator;
    }

}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.common.vergelijk.vergelijking;

/**
 * Vergelijking waarbij een reguliere expressie obv naam in de context steeds hetzelfde moet zijn.
 */
public final class ConstantVariableVergelijking implements Vergelijking {

    private final String name;
    private final String regex;

    /**
     * Constructor.
     * @param name naam
     * @param regex reguliere expressie
     */
    public ConstantVariableVergelijking(final String name, final String regex) {
        this.name = name;
        this.regex = regex;
    }

    /* (non-Javadoc)
     * @see nl.bzk.migratiebrp.test.common.vergelijk.vergelijking.Vergelijking#getRegex()
     */
    @Override
    public String getRegex() {
        return regex;
    }

    @Override
    public boolean check(final VergelijkingContext context, final String value) {
        return context.checkConstantVariable(name, value);
    }

}

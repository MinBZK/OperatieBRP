/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.test.vergelijk;

/**
 * Vergelijking obv exacte string.
 */
public final class ExactVergelijking implements Vergelijking {

    private final String value;

    /**
     * Constructor.
     * 
     * @param value
     *            extracte string
     */
    public ExactVergelijking(final String value) {
        this.value = value;
    }

    @Override
    public String getRegex() {
        // Escape \ [ ] . { } ^$ ? * + ( ) |
        return value.replaceAll("([\\\\\\[\\]\\.\\{\\}\\^\\$\\?\\*\\+\\(\\)\\|])", "\\\\$1");
    }

    @Override
    public boolean check(final VergelijkingContext context, final String value) {
        return true;
    }

}

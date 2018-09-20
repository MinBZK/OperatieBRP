/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.test.vergelijk;

/**
 * Vergelijking obv reguliere expressie.
 */
public final class RegexVergelijking implements Vergelijking {

    private final String regex;

    /**
     * Constructor.
     * 
     * @param regex
     *            te gebruiken reguliere expressie
     */
    public RegexVergelijking(final String regex) {
        this.regex = regex;
    }

    @Override
    public String getRegex() {
        return regex;
    }

    @Override
    public boolean check(final VergelijkingContext context, final String value) {
        return true;
    }

}

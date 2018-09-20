/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.util.common;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

/**
 * Alle encoding constanten die binnen migratie code worden gebruikt dienen hier te zijn gedefinieerd.
 */
public final class EncodingConstants {

    /** De UTF-8 Charset. */
    public static final Charset CHARSET = StandardCharsets.UTF_8;
    /** De naam van de UTF-8 Charset. */
    public static final String CHARSET_NAAM = CHARSET.name();
    /** De locale voor Nederland en de nederlandse taal. */
    public static final Locale LOCALE = Locale.forLanguageTag("nl-NL");

    /*
     * Explicit private constructor.
     */
    private EncodingConstants() {
        throw new AssertionError("Deze classe mag niet geinstantieerd worden.");
    }
}

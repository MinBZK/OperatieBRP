/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.injection;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.Bericht;

/**
 * Selenium variabele injector.
 */
public class InjectorSelenese implements Injector {

    /**
     * Zoek in de bericht inhoud naar {@code <td>store</td><td>(.*)</td><td>key</td>} en
     * vervang de {@code (.*)} met de value.
     * @param context context
     * @param bericht bericht
     * @param key variabele naam in het selenium script
     * @param value waarde
     */
    @Override
    public final void inject(final Context context, final Bericht bericht, final String key, final String value) {
        final StringBuilder inhoud = new StringBuilder(bericht.getInhoud());

        final Pattern pattern = Pattern.compile("<td>store</td>\\s*<td>(.*?)</td>\\s*<td>" + key + "</td>");
        final Matcher matcher = pattern.matcher(inhoud);
        if (matcher.find()) {
            final int start = matcher.start(1);
            final int end = matcher.end(1);

            if (end != -1) {
                inhoud.delete(start, end);
            }

            inhoud.insert(start, value);
        }

        bericht.setInhoud(inhoud.toString());

    }
}

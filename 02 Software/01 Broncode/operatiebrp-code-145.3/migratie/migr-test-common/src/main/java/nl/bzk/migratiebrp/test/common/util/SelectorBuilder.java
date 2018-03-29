/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.common.util;

/**
 * Selector class om specifieke berichten van een JMS Queue te ontvangen.
 */
public final class SelectorBuilder {
    private final StringBuilder buffer = new StringBuilder();

    /**
     * Voegt een criteria toe aan de selector waarbij het meegegven veld exact de waarde moet bevatten.
     * @param key veld naam waarop geselecteerd moet worden
     * @param value waarde van het veld waarop geselecteerd moet worden
     */
    public void addEqualsCriteria(final String key, final String value) {
        addCriteria(key, "=", value);
    }

    /**
     * Voegt een criteria toen aan de selector. Hier kan de worden meegeven hoe het opgegeven veld en de waarde
     * vergeleken dient te worden.
     * @param key veld naam waarop geselecteerd moet worden
     * @param comparator de manier waarop de waarde van het opgegeven veld moet overeenkomen met de opgegeven waarde
     * @param value waarde van het veld waarop geselecteerd moet worden
     */
    public void addCriteria(final String key, final String comparator, final String value) {
        if (buffer.length() != 0) {
            buffer.append(" AND ");
        }

        buffer.append(key).append(' ').append(comparator).append(" '").append(value).append('\'');
    }

    /**
     * Geef de empty.
     * @return empty
     */
    public boolean isEmpty() {
        return buffer.length() == 0;
    }

    @Override
    public String toString() {
        return buffer.length() == 0 ? "" : buffer.toString();
    }
}

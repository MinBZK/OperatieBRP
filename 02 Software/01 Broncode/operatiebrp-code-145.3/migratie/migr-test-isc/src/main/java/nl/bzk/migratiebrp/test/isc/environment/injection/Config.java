/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.injection;

/**
 * Configuratie regel.
 */
public final class Config {

    private final String name;
    private final String type;
    private final String key;

    /**
     * Constructor.
     * @param name naam
     * @param type type
     * @param key sleutel
     */
    public Config(final String name, final String type, final String key) {
        this.name = name;
        this.type = type;
        this.key = key;
    }

    /**
     * Geef de waarde van name.
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Geef de waarde van type.
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * Geef de waarde van key.
     * @return key
     */
    public String getKey() {
        return key;
    }

}

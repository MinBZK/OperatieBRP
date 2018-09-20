/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.datataal.leveringautorisatie;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 *
 */
final class DslSectie {

    private static final Logger LOGGER = LoggerFactory.getLogger(DslSectie.class);

    /**
     * statische lijst van aliassen voor de keys
     */
    private static final Properties KEY_ALIAS_MAP = new Properties() {

        {
            try {
                load(DslSectie.class.getResourceAsStream("/autaut/kolomalias.properties"));
            } catch (IOException e) {
                LOGGER.error("autaut/kolomalias.properties niet gevonden");
            }
        }

        @Override
        public String getProperty(final String key) {

            String returnKey;
            if (!containsKey(key)) {
                returnKey = key;
            } else {
                returnKey = super.getProperty(key);
            }
            return StringUtils.lowerCase(returnKey);
        }
    };

    private String sectieNaam;
    private Map<String, String> regels = new HashMap<>();

    /**
     * Maakt een nieuwe sectie
     * @param sectieNaam naam van de sectie
     */
    DslSectie(final String sectieNaam) {
        Assert.notNull(StringUtils.trimToNull(sectieNaam));
        this.sectieNaam = sectieNaam;
    }

    /**
     * Voegt een nieuwe regel toe aan de sectie
     * @param key de key
     * @param value de value
     */
    public void addRegel(final String key, final String value) {
        regels.put(key.toLowerCase(), value);
    }

    /**
     * @return indicatie of de sectie regels bevat
     */
    public boolean heeftRegels() {
        return !regels.isEmpty();
    }

    public String getSectieNaam() {
        return sectieNaam;
    }

    /**
     * Doet een assertion dat deze sectie de opgegeven naam heeft
     * @param sectieNaam naam van de sectie
     */
    public void assertMetNaam(final String sectieNaam) {
        if (!sectieNaam.equals(getSectieNaam())) {
            throw new IllegalStateException(String.format("Sectie '%s' verwacht, '%s' gevonden",
                sectieNaam, getSectieNaam()));
        }
    }

    /**
     * @param key de key van de value
     * @return de waarde als integer
     */
    public Integer geefInteger(final String key) {
        final String value = geefStringValue(key);
        if (value == null) {
            return null;
        }
        return Integer.parseInt(value);
    }

    /**
     * @param key de key van de value
     * @param defaultInteger default value
     * @return de waarde als integer
     */
    public Integer geefIntegerOfDefault(final String key, final Integer defaultInteger) {
        final String value = geefStringValue(key);
        if (value == null) {
            return defaultInteger;
        }
        return Integer.parseInt(value);
    }

    /**
     * @param key de key van de value
     * @return de integer waarde
     */
    public Integer geefDatumInt(final String key) {
        return geefInteger(key);
    }


    /**
     * @param key de key van de value
     * @param defaultDatum default datumattribuut
     * @return de value datum integer, of de default datum als fallback indien opgegeven
     */
    public Integer geefDatumIntOfDefault(final String key, final DatumAttribuut defaultDatum) {
        final Integer integer = geefDatumInt(key);
        if (integer != null && integer > 0) {
            return integer;
        }
        return defaultDatum.getWaarde();

    }

    /**
     * @param key de key van de value
     * @return de value als String
     */
    public String geefStringValue(final String key) {
        final String lowerKey = key.toLowerCase();
        String keyToUse = null;
        if (regels.containsKey(lowerKey)) {
            keyToUse = lowerKey;
        } else if (KEY_ALIAS_MAP.containsKey(lowerKey) && regels.containsKey(KEY_ALIAS_MAP.getProperty(lowerKey))) {
            keyToUse = KEY_ALIAS_MAP.getProperty(lowerKey);
        }
        return StringUtils.trimToNull(regels.get(keyToUse));
    }

    /**
     * @param key de key voor de value
     * @return de value als boolean
     */
    public Boolean geefBooleanValue(final String key) {
        final String value = geefStringValue(key);
        if (value == null) {
            return null;
        }
        return Boolean.parseBoolean(value);
    }

    /**
     * @param value de key voor de value
     * @return de value met quotes
     */
    public static String quote(final String value) {
        if (value == null) {
            return null;
        }
        return String.format("'%s'", value);
    }

    @Override
    public String toString() {
        return "DslSectie{" +
                "sectieNaam='" + sectieNaam + '\'' +
                ", regels=" + regels +
                '}';
    }

    /**
     * Test of de regel met de gegeven key bestaat.
     * @param key de key
     * @return indicatie of de regel bestaat
     */
    public boolean heeftRegel(final String key) {
        return regels.containsKey(key.toLowerCase());
    }
}

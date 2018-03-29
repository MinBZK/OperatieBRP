/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.test.common.dsl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

/**
 *
 */
public final class DslSectie {

    /**
     * statische lijst van aliassen voor de keys
     */
    private static final Properties KEY_ALIAS_MAP = new Properties() {
        {
            put("srt", "Soort");
            put("datingang", "Datum ingang");
            put("dateinde", "Datum Einde");
            put("naderepopulatiebeperking", "Nadere Populatie Beperking");
            put("populatiebeperking", "Populatie Beperking");
            put("effectafnemerindicaties", "Effect afnemer indicatie");
            put("attenderingscriterium", "Attenderingscriterium");
            put("indblok", "Geblokkeeerd");
            put("naam", "Naam");
            put("indaliassrtadmhndleveren", "Ind alias srtadmhnd leveren");
        }

        @Override
        public String getProperty(final String key) {
            final String returnKey;
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
     * Maakt een nieuwe sectie.
     * @param sectieNaam naam van de sectie
     */
    public DslSectie(final String sectieNaam) {
        Assert.notNull(StringUtils.trimToNull(sectieNaam));
        this.sectieNaam = sectieNaam;
    }

    /**
     * Voegt een nieuwe regel toe aan de sectie.
     * @param key de key
     * @param value de value
     */
    public void addRegel(final String key, final String value) {
        regels.put(key.toLowerCase(), value);
    }

    public String getSectieNaam() {
        return sectieNaam;
    }

    /**
     * Doet een assertion dat deze sectie de opgegeven naam heeft.
     * @param sectieNaamParam naam van de sectie
     */
    public void assertMetNaam(final String sectieNaamParam) {
        if (!sectieNaamParam.equals(this.sectieNaam)) {
            throw new IllegalStateException(String.format("Sectie '%s' verwacht, '%s' gevonden",
                    sectieNaamParam, this.sectieNaam));
        }
    }

    /**
     * @param key de key van de value
     * @return de waarde als integer
     */
    public Optional<Integer> geefInteger(final String key) {
        return geefStringValue(key).map(Integer::parseInt);
    }

    /**
     * @param key de key van de value
     * @return de integer waarde
     */
    public Optional<Integer> geefDatumInt(final String key) {
        return geefStringValue(key).map(DatumConstanten::getBasicIsoDateInt);
    }

    /**
     * @param key de key van de value
     * @return de value als String
     */
    public Optional<String> geefStringValue(final String key) {
        final String lowerKey = key.toLowerCase();
        String keyToUse = null;
        if (regels.containsKey(lowerKey)) {
            keyToUse = lowerKey;
        } else if (KEY_ALIAS_MAP.containsKey(lowerKey) && regels.containsKey(KEY_ALIAS_MAP.getProperty(lowerKey))) {
            keyToUse = KEY_ALIAS_MAP.getProperty(lowerKey);
        }
        final String value = StringUtils.trimToNull(regels.get(keyToUse));
        if (value == null || "NULL".equals(value)) {
            return Optional.empty();
        }
        return Optional.of(value);
    }

    /**
     * @param key de key voor de value
     * @return de value als boolean
     */
    public Optional<Boolean> geefBooleanValue(final String key) {
        return geefStringValue(key).map(Boolean::parseBoolean);
    }

    /**
     * Test of de regel met de gegeven key bestaat.
     * @param key de key
     * @return indicatie of de regel bestaat
     */
    boolean heeftRegel(final String key) {
        return regels.containsKey(key.toLowerCase());
    }

    @Override
    public String toString() {
        return "DslSectie{"
                + "sectieNaam='" + sectieNaam + '\''
                + ", regels=" + regels
                + '}';
    }

}

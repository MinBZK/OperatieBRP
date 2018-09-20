/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.mapping;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Rubriek naar expressie mapping.
 */
public final class RubriekMapping {

    /** Mapping. */
    private static final Map<String, List<Expressie>> MAPPING = readMapping();

    /** Lees mapping uit '/vertaling/rubrieken.properties'. */
    private static Map<String, List<Expressie>> readMapping() {
        final Map<String, List<Expressie>> result = new HashMap<>();
        final Properties properties = new Properties();
        try (InputStream is = RubriekMapping.class.getResourceAsStream("/vertaling/rubrieken.properties")) {
            properties.load(is);
        } catch (final IOException e) {
            throw new IllegalArgumentException("Kan /vertaling/rubrieken.properties niet laden.", e);
        }

        for (final String rubriek : properties.stringPropertyNames()) {
            final String mapping = properties.getProperty(rubriek);
            if (mapping == null || "".equals(mapping.trim())) {
                continue;
            }
            final String[] elementen = mapping.split("#");
            final List<Expressie> expressies = new ArrayList<>();
            for (final String element : elementen) {
                final String[] onderdelen = element.split("\\|", 2);
                final String parent;
                final String expressie;

                if (onderdelen.length == 1) {
                    parent = null;
                    expressie = onderdelen[0];
                } else {
                    parent = onderdelen[0];
                    expressie = onderdelen[1];
                }
                expressies.add(new Expressie(parent, expressie));
            }
            result.put(rubriek, Collections.unmodifiableList(expressies));
        }
        return Collections.unmodifiableMap(result);
    }

    /**
     * Geef de expressies voor een rubiek.
     *
     * @param rubriek
     *            rubriek
     * @return lijst van expressies, null als niet gevonden
     */
    public static List<Expressie> getExpressiesVoorRubriek(final String rubriek) {
        return MAPPING.get(rubriek);
    }

    /**
     * Expressie.
     */
    public static final class Expressie {
        private final String parent;
        private final String expressie;

        /**
         * Maakt nieuwe Expressie object.
         * @param parent expressie van de ouder van een element
         * @param expressie de expressie voor een element
         */
        Expressie(final String parent, final String expressie) {
            super();
            this.parent = parent;
            this.expressie = expressie;
        }

        /**
         * Parent (null, als dit direct een waarde op 'persoon' is).
         *
         * @return parent
         */
        public String getParent() {
            return parent;
        }

        /**
         * Basis expressie.
         *
         * @return basis expressie
         */
        public String getExpressie() {
            return expressie;
        }

    }
}

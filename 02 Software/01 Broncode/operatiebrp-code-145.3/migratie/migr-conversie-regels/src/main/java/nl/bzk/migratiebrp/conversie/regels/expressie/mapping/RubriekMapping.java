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
 * Rubriek naar brpExpressie mapping.
 */
public final class RubriekMapping {

    /**
     * Mapping.
     */
    private static final Map<String, List<Expressie>> MAPPING = readMapping();

    private RubriekMapping() {
        // prevent instantiation
    }

    /**
     * Lees mapping uit '/vertaling/rubrieken.properties'.
     */
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
                final String brpElement;
                boolean inverse;
                if (element.startsWith("!")) {
                    brpElement = element.substring(1);
                    inverse = true;
                } else {
                    brpElement = element;
                    inverse = false;
                }
                final String[] onderdelen = brpElement.split("\\|", 2);
                final String parent;
                final String expressie;

                if (onderdelen.length == 1) {
                    parent = null;
                    expressie = onderdelen[0];
                } else {
                    parent = onderdelen[0];
                    expressie = onderdelen[1];
                }
                expressies.add(new Expressie(parent, expressie, inverse));
            }
            result.put(rubriek, Collections.unmodifiableList(expressies));
        }
        return Collections.unmodifiableMap(result);
    }

    /**
     * Geef de expressies voor een rubiek.
     * @param rubriek rubriek
     * @return lijst van expressies, null als niet gevonden
     */
    public static List<Expressie> getExpressiesVoorRubriek(final String rubriek) {
        return MAPPING.get(rubriek);
    }

    /**
     * Geef aan of de rubriek aanwezig is in de mapping.
     * @param rubriek rubriek
     * @return indicatie of er een mapping aanwezig is
     */
    public static boolean isErEenExpressieVoorRubriek(final String rubriek) {
        return MAPPING.containsKey(rubriek);
    }

    /**
     * Expressie.
     */
    public static final class Expressie {
        private final String parent;
        private final String brpExpressie;
        private final boolean kvInverse;

        /**
         * Maakt nieuwe Expressie object.
         * @param parent brpExpressie van de ouder van een element
         * @param expressie de brpExpressie voor een element
         * @param kvInverse of bij KV element KNV moet worden
         */
        Expressie(final String parent, final String expressie, final boolean kvInverse) {
            super();
            this.parent = parent;
            this.brpExpressie = expressie;
            this.kvInverse = kvInverse;
        }

        /**
         * Parent (null, als dit direct een waarde op 'persoon' is).
         * @return parent
         */
        public String getParent() {
            return parent;
        }

        /**
         * Basis brpExpressie.
         * @return basis brpExpressie
         */
        public String getExpressie() {
            return brpExpressie;
        }

        /**
         * Indicatie of element bij KV inverse moet worden meegenomen.
         * @return indicatie
         */
        public boolean getKvInverse() {
            return kvInverse;
        }
    }
}

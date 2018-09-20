/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.misc;

import java.util.ArrayList;
import java.util.List;

/**
 * De Class Bericht.
 */
public final class Bericht {

    /**
     * Instantieert een nieuwe Bericht.
     */
    private Bericht() {
    }

    /**
     * De Enum TYPE.
     */
    public static enum TYPE {

        /** Alle berichten. */
        ALLES("Alles"),

        /** Bevraging berichten. */
        BEVRAGING("Bevraging"),

        /** Bijhouding berichten. */
        BIJHOUDING("Bijhouding");

        /** De naam. */
        private final String naam;

        /**
         * Instantieert een nieuwe type.
         *
         * @param naam de naam
         */
        TYPE(final String naam) {
            this.naam = naam;
        }

        /**
         * Haalt een naam op.
         *
         * @return naam
         */
        public String getNaam() {
            return naam;
        }
    }

    // TODO: BIJHOUDING_GEBOORTE, BIJHOUDING_ADOPTIE en BIJHOUDING_GEBOORTE_MET_ERKENNING geven nog teveel fouten met
    // huidige dataset. Deze hebben tijdelijkt type ALLES om zo eenvoudig alle werkende bijhoudingen via het type te
    // kunnen draaien. Zie DELTA-1643
    /**
     * De Enum BERICHT.
     */
    public static enum BERICHT {

        /** Vraag details persoon. */
        VRAAG_DETAILS(TYPE.BEVRAGING, "DetailsPersoon", "\t"),
        /** Kandidaat vader. */
        VRAAG_KANDIDAAT_VADER(TYPE.BEVRAGING, "KandidaatVader", "\t"),
        /** Personen op adres. */
        VRAAG_PERSONEN_ADRES(TYPE.BEVRAGING, "PersAdres", "\t\t"),
        /** Personen op adres via BSN. */
        VRAAG_PERSONEN_ADRES_VIA_BSN(TYPE.BEVRAGING, "PersAdresBsn", "\t"),

        /** Correctie adres. */
        BIJHOUDING_CORRECTIE_ADRES(TYPE.BIJHOUDING, "CorrectieAdres", "\t"),
        /** Geboorte. */
        BIJHOUDING_GEBOORTE(TYPE.BIJHOUDING, "Geboorte", "\t\t"),
        /** Huwelijk. */
        BIJHOUDING_HUWELIJK(TYPE.BIJHOUDING, "Huwelijk", "\t\t"),
        /** Overlijden. */
        BIJHOUDING_OVERLIJDEN(TYPE.BIJHOUDING, "Overlijden", "\t\t"),
        /** Verhuizing. */
        BIJHOUDING_VERHUIZING(TYPE.BIJHOUDING, "Verhuizing", "\t\t"),
        /** Adoptie. */
        BIJHOUDING_ADOPTIE(TYPE.BIJHOUDING, "Adoptie", "\t\t\t"),
        /** Geboorte met erkenning. */
        BIJHOUDING_GEBOORTE_MET_ERKENNING(TYPE.BIJHOUDING, "GeboorteErk", "\t\t");

        private final TYPE   type;
        private final String naam;
        private final String witruimte;

        /**
         * Instantieert een nieuwe bericht.
         *
         * @param type de type
         * @param naam de naam
         * @param witruimte de witruimte
         */
        BERICHT(final TYPE type, final String naam, final String witruimte) {
            this.type = type;
            this.naam = naam;
            this.witruimte = witruimte;
        }

        /**
         * Haalt een type op.
         *
         * @return type
         */
        public TYPE getType() {
            return type;
        }

        /**
         * Haalt een naam op.
         *
         * @return naam
         */
        public String getNaam() {
            return naam;
        }

        /**
         * Haalt een witruimte op.
         *
         * @return witruimte
         */
        public String getWitruimte() {
            return witruimte;
        }
    }

    /**
     * De Enum STATUS.
     */
    public static enum STATUS {
        /** GOED. */
        GOED,
        /** FOUT. */
        FOUT
    }

    /**
     * Verkrijgt berichten van type.
     *
     * @param type type
     * @return berichten van type
     */
    public static List<BERICHT> getBerichtenVanType(final TYPE type) {
        final List<BERICHT> berichten = new ArrayList<>();

        for (BERICHT bericht : BERICHT.values()) {
            if (type.equals(TYPE.ALLES) || bericht.getType().equals(type)) {
                berichten.add(bericht);
            }
        }

        return berichten;
    }

    /**
     * Verkrijgt bericht.
     *
     * @param naam naam
     * @return bericht
     */
    public static BERICHT getBericht(final String naam) {
        for (BERICHT bericht : BERICHT.values()) {
            if (naam.equalsIgnoreCase(bericht.getNaam())) {
                return bericht;
            }
        }
        return null;
    }

    /**
     * Verkrijgt type.
     *
     * @param naam naam
     * @return type
     */
    public static TYPE getType(final String naam) {
        for (TYPE type: TYPE.values()) {
            if (naam.equalsIgnoreCase(type.getNaam())) {
                return type;
            }
        }
        return null;
    }

}

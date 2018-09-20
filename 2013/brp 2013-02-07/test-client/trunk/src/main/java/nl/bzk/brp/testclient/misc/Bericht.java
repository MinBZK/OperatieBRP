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
public class Bericht {

    /**
     * De Enum TYPE.
     */
    public static enum TYPE {

        ALLES("Alles"),
        BEVRAGING("Bevraging"),
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
        };

        /**
         * Haalt een naam op.
         *
         * @return naam
         */
        public String getNaam() {
            return naam;
        }
    };

    /**
     * De Enum BERICHT.
     */
    public static enum BERICHT {

        VRAAG_DETAILS(TYPE.BEVRAGING, "DetailsPersoon", "\t"),
        VRAAG_KANDIDAAT_VADER(TYPE.BEVRAGING, "KandidaatVader", "\t"),
        VRAAG_PERSONEN_ADRES(TYPE.BEVRAGING, "PersAdres", "\t\t"),
        VRAAG_PERSONEN_ADRES_VIA_BSN(TYPE.BEVRAGING, "PersAdresBsn", "\t"),

        BIJHOUDING_CORRECTIE_ADRES(TYPE.BIJHOUDING, "CorrectieAdres", "\t"),
        BIJHOUDING_GEBOORTE(TYPE.BIJHOUDING, "Geboorte", "\t\t"),
        BIJHOUDING_HUWELIJK(TYPE.BIJHOUDING, "Huwelijk", "\t\t"),
        BIJHOUDING_OVERLIJDEN(TYPE.BIJHOUDING, "Overlijden", "\t\t"),
        BIJHOUDING_VERHUIZING(TYPE.BIJHOUDING, "Verhuizing", "\t\t");

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
        };

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
    };

    /**
     * De Enum STATUS.
     */
    public static enum STATUS {
        GOED,
        FOUT
    };

    public static List<BERICHT> getBerichtenVanType(final TYPE type) {
        List<BERICHT> berichten = new ArrayList<BERICHT>();

        for (BERICHT bericht : BERICHT.values()) {
            if (type.equals(TYPE.ALLES) || bericht.getType().equals(type)) {
                berichten.add(bericht);
            }
        }

        return berichten;
    }
}

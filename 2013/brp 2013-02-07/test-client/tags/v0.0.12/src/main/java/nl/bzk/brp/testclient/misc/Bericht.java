/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.misc;


/**
 * De Class Bericht.
 */
public class Bericht {

    /**
     * De Enum TYPE.
     */
    public static enum TYPE {

        ALLES("Alles"),

        /** De bevraging. */
        BEVRAGING("Bevraging"),

        /** De bijhouding. */
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

        /** De vraag details. */
        VRAAG_DETAILS(TYPE.BEVRAGING, "DetailsPersoon", "\t"),

        /** De vraag kandidaat vader. */
        VRAAG_KANDIDAAT_VADER(TYPE.BEVRAGING, "KandidaatVader", "\t"),

        /** De vraag personen op adres. */
        VRAAG_PERSONEN_OP_ADRES(TYPE.BEVRAGING, "PersOpAdres", "\t\t"),

        /** De vraag personen op adres via bsn. */
        VRAAG_PERSONEN_OP_ADRES_VIA_BSN(TYPE.BEVRAGING, "PersOpAdresBsn", "\t"),

        /** De bijhouding correctie adres. */
        BIJHOUDING_CORRECTIE_ADRES(TYPE.BIJHOUDING, "CorrectieAdres", "\t"),

        /** De bijhouding inschrijving geboorte. */
        BIJHOUDING_INSCHRIJVING_GEBOORTE(TYPE.BIJHOUDING, "InschrGeboorte", "\t"),

        /** De bijhouding registreer huwelijk. */
        BIJHOUDING_REGISTREER_HUWELIJK(TYPE.BIJHOUDING, "RegistreerHuwelijk", "\t"),

        /** De bijhouding verhuizing. */
        BIJHOUDING_VERHUIZING(TYPE.BIJHOUDING, "Verhuizing", "\t\t");

        /** De type. */
        private final TYPE type;

        /** De naam. */
        private final String naam;

        /** De witruimte. */
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

        /** De goed. */
        GOED,

        /** De fout. */
        FOUT

    };

}

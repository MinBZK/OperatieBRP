/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.attributen;

import nl.moderniseringgba.migratie.Definitie;
import nl.moderniseringgba.migratie.Definities;
import nl.moderniseringgba.migratie.Requirement;
import nl.moderniseringgba.migratie.Requirements;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpLandCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPlaatsCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;

/**
 * Deze class converteert de LO3 plaats velden naar de BRP plaats velden.
 * 
 * 
 * 
 */
@Requirement({ Requirements.CAP001, Requirements.CAP001_LB01, Requirements.CAP001_LB02 })
public final class Lo3PlaatsLandConversieHelper {

    private final Lo3AttribuutConverteerder converteerder;
    private final Lo3GemeenteCode lo3GemeenteCode;
    private final Lo3LandCode lo3LandCode;

    /**
     * Maakt een Lo3PlaatsLandConversieHelper object.
     * 
     * @param lo3GemeenteCode
     *            de lo3 gemeente die geconverteerd moet worden
     * @param lo3LandCode
     *            de lo3 land code die geconverteerd moet worden
     * @param converteerder
     *            converteerder service
     */
    public Lo3PlaatsLandConversieHelper(
            final Lo3GemeenteCode lo3GemeenteCode,
            final Lo3LandCode lo3LandCode,
            final Lo3AttribuutConverteerder converteerder) {
        this.lo3GemeenteCode = lo3GemeenteCode;
        this.lo3LandCode = lo3LandCode;
        this.converteerder = converteerder;
    }

    /**
     * Converteert de LO3 gemeente en land naar de corresponderende velden in het BRP model.
     * 
     * @return corresponderende velden in het BRP model
     */
    public BrpPlaatsLand converteerNaarBrp() {
        /*
         * De BRP velden plaats en buitenlandse regio worden niet gevuld tijdens de conversie van LO3 naar BRP.
         */

        BrpGemeenteCode brpGemeenteCode = null;
        String brpBuitenlandsePlaats = null;
        String brpOmschrijvingLocatie = null;

        if (lo3GemeenteCode != null && lo3LandCode != null) {
            if (isWoonachtigInNederlandseGemeente()) {
                brpGemeenteCode = converteerder.converteerLo3GemeenteCode(lo3GemeenteCode);
            } else if (isWoonachtigInBuitenlandsePlaats()) {
                brpBuitenlandsePlaats = lo3GemeenteCode.getCode();
            } else if (isLocatieOmschrijvingIngevuldInGemeenteCode()) {
                brpOmschrijvingLocatie = lo3GemeenteCode.getCode();
            }
        }

        return new BrpPlaatsLand(null, null, brpGemeenteCode, brpBuitenlandsePlaats, brpOmschrijvingLocatie,
                converteerder.converteerLo3LandCode(lo3LandCode));
    }

    /**
     * @return true als deze persoon woonachtig is in een nederlandse gemeente, anders false
     */
    @Definitie(Definities.DEF001)
    private boolean isWoonachtigInNederlandseGemeente() {
        return lo3GemeenteCode.isValideNederlandseGemeenteCode() && lo3LandCode.isNederlandCode();
    }

    /**
     * @return true als deze persoon woonachtig is in een bekende buitenlandse plaats, anders false
     */
    @Definitie(Definities.DEF002)
    private boolean isWoonachtigInBuitenlandsePlaats() {
        return lo3LandCode.isLandCodeBuitenland();
    }

    /**
     * @return true als in de waarde van {@link #getGeboorteGemeenteCode()} de omschrijving van de locatie bevat, anders
     *         false
     */
    @Definitie(Definities.DEF003)
    private boolean isLocatieOmschrijvingIngevuldInGemeenteCode() {
        return lo3LandCode.isOnbekend();
    }

    /**
     * Deze class bevat de BRP velden die corresponderen met de LO3 elementen gemeente en land.
     * 
     */
    public static final class BrpPlaatsLand {
        private final BrpPlaatsCode brpPlaatsCode;
        private final String brpBuitenlandseRegio;
        private final BrpGemeenteCode brpGemeenteCode;
        private final String brpBuitenlandsePlaats;
        private final String brpOmschrijvingLocatie;
        private final BrpLandCode brpLandCode;

        /**
         * Maak een BrpPlaatsLand.
         * 
         * @param brpPlaatsCode
         *            plaats
         * @param brpBuitenlandseRegio
         *            buitenlandse regio
         * @param brpGemeenteCode
         *            gemeente
         * @param brpBuitenlandsePlaats
         *            buitenlandse plaats
         * @param brpOmschrijvingLocatie
         *            omschrijving locatie
         * @param brpLandCode
         *            land
         */
        public BrpPlaatsLand(
                final BrpPlaatsCode brpPlaatsCode,
                final String brpBuitenlandseRegio,
                final BrpGemeenteCode brpGemeenteCode,
                final String brpBuitenlandsePlaats,
                final String brpOmschrijvingLocatie,
                final BrpLandCode brpLandCode) {
            this.brpPlaatsCode = brpPlaatsCode;
            this.brpBuitenlandseRegio = brpBuitenlandseRegio;
            this.brpGemeenteCode = brpGemeenteCode;
            this.brpBuitenlandsePlaats = brpBuitenlandsePlaats;
            this.brpOmschrijvingLocatie = brpOmschrijvingLocatie;
            this.brpLandCode = brpLandCode;
        }

        /**
         * @return the brpPlaatsCode
         */
        public BrpPlaatsCode getBrpPlaatsCode() {
            return brpPlaatsCode;
        }

        /**
         * @return the brpBuitenlandseRegio
         */
        public String getBrpBuitenlandseRegio() {
            return brpBuitenlandseRegio;
        }

        /**
         * @return the brpGemeenteCode
         */
        public BrpGemeenteCode getBrpGemeenteCode() {
            return brpGemeenteCode;
        }

        /**
         * @return the brpBuitenlandsePlaats
         */
        public String getBrpBuitenlandsePlaats() {
            return brpBuitenlandsePlaats;
        }

        /**
         * @return the brpOmschrijvingLocatie
         */
        public String getBrpOmschrijvingLocatie() {
            return brpOmschrijvingLocatie;
        }

        /**
         * @return the brpLandCode
         */
        public BrpLandCode getBrpLandCode() {
            return brpLandCode;
        }
    }
}

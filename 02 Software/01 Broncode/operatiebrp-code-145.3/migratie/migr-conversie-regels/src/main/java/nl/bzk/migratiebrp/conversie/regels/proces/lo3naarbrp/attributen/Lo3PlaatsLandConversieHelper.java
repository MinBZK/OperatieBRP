/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen;

import nl.bzk.migratiebrp.conversie.model.Definitie;
import nl.bzk.migratiebrp.conversie.model.Definities;
import nl.bzk.migratiebrp.conversie.model.Requirement;
import nl.bzk.migratiebrp.conversie.model.Requirements;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Validatie;

/**
 * Deze class converteert de LO3 plaats velden naar de BRP plaats velden.
 */
@Requirement({Requirements.CAP001, Requirements.CAP001_LB01, Requirements.CAP001_LB02})
public final class Lo3PlaatsLandConversieHelper {

    private final Lo3AttribuutConverteerder converteerder;
    private final Lo3GemeenteCode lo3GemeenteCode;
    private final Lo3LandCode lo3LandCode;

    /**
     * Maakt een Lo3PlaatsLandConversieHelper object.
     * @param lo3GemeenteCode de lo3 gemeente die geconverteerd moet worden
     * @param lo3LandCode de lo3 land code die geconverteerd moet worden
     * @param converteerder converteerder service
     */
    public Lo3PlaatsLandConversieHelper(final Lo3GemeenteCode lo3GemeenteCode, final Lo3LandCode lo3LandCode, final Lo3AttribuutConverteerder converteerder) {
        this.lo3GemeenteCode = lo3GemeenteCode;
        this.lo3LandCode = lo3LandCode;
        this.converteerder = converteerder;
    }

    /**
     * Converteert de LO3 gemeente en land naar de corresponderende velden in het BRP model.
     * @return corresponderende velden in het BRP model
     */
    public BrpPlaatsLand converteerNaarBrp() {
        /*
         * De BRP velden woonplaatsnaam en buitenlandse regio worden niet gevuld tijdens de conversie van LO3 naar BRP.
         */
        final BrpPlaatsLand resultaat;
        final BrpLandOfGebiedCode brpLandOfGebiedCode = converteerder.converteerLo3LandCode(lo3LandCode);
        if (Lo3Validatie.isElementGevuld(lo3GemeenteCode)) {
            BrpGemeenteCode brpGemeenteCode = null;
            BrpString brpBuitenlandsePlaats = null;
            BrpString brpOmschrijvingLocatie = null;

            if (isWoonachtigInNederlandseGemeente()) {
                // DEF001
                brpGemeenteCode = converteerder.converteerLo3GemeenteCode(lo3GemeenteCode);
            } else if (isWoonachtigInBuitenlandsePlaats()) {
                // DEF002
                brpBuitenlandsePlaats = new BrpString(lo3GemeenteCode.getWaarde(), lo3GemeenteCode.getOnderzoek());
            } else {
                // DEF003
                brpOmschrijvingLocatie = new BrpString(lo3GemeenteCode.getWaarde(), lo3GemeenteCode.getOnderzoek());
            }

            resultaat = new BrpPlaatsLand(null, null, brpGemeenteCode, brpBuitenlandsePlaats, brpOmschrijvingLocatie, brpLandOfGebiedCode);
        } else {
            resultaat = new BrpPlaatsLand(null, null, null, null, null, brpLandOfGebiedCode);
        }

        return resultaat;
    }

    /**
     * Geef de woonachtig in nederlandse gemeente.
     * @return true als deze persoon woonachtig is in een nederlandse gemeente, anders false
     */
    @Definitie(Definities.DEF001)
    private boolean isWoonachtigInNederlandseGemeente() {
        return lo3GemeenteCode.isValideNederlandseGemeenteCode() && lo3LandCode.isNederlandCode();
    }

    /**
     * Geef de woonachtig in buitenlandse plaats.
     * @return true als deze persoon woonachtig is in een bekende buitenlandse plaats, anders false
     */
    @Definitie(Definities.DEF002)
    private boolean isWoonachtigInBuitenlandsePlaats() {
        return lo3LandCode.isLandCodeBuitenland();
    }

    /**
     * Deze class bevat de BRP velden die corresponderen met de LO3 elementen gemeente en land.
     */
    public static final class BrpPlaatsLand {
        private final BrpString brpWoonplaatsnaam;
        private final BrpString brpBuitenlandseRegio;
        private final BrpGemeenteCode brpGemeenteCode;
        private final BrpString brpBuitenlandsePlaats;
        private final BrpString brpOmschrijvingLocatie;
        private final BrpLandOfGebiedCode brpLandOfGebiedCode;

        /**
         * Maak een BrpPlaatsLand.
         * @param brpWoonplaatsnaam plaats
         * @param brpBuitenlandseRegio buitenlandse regio
         * @param brpGemeenteCode gemeente
         * @param brpBuitenlandsePlaats buitenlandse plaats
         * @param brpOmschrijvingLocatie omschrijving locatie
         * @param brpLandOfGebiedCode land
         */
        public BrpPlaatsLand(
                final BrpString brpWoonplaatsnaam,
                final BrpString brpBuitenlandseRegio,
                final BrpGemeenteCode brpGemeenteCode,
                final BrpString brpBuitenlandsePlaats,
                final BrpString brpOmschrijvingLocatie,
                final BrpLandOfGebiedCode brpLandOfGebiedCode) {
            this.brpWoonplaatsnaam = brpWoonplaatsnaam;
            this.brpBuitenlandseRegio = brpBuitenlandseRegio;
            this.brpGemeenteCode = brpGemeenteCode;
            this.brpBuitenlandsePlaats = brpBuitenlandsePlaats;
            this.brpOmschrijvingLocatie = brpOmschrijvingLocatie;
            this.brpLandOfGebiedCode = brpLandOfGebiedCode;
        }

        /**
         * Geef de waarde van brp woonplaatsnaam.
         * @return the brpWoonplaatsnaam
         */
        public BrpString getBrpWoonplaatsnaam() {
            return brpWoonplaatsnaam;
        }

        /**
         * Geef de waarde van brp buitenlandse regio.
         * @return the brpBuitenlandseRegio
         */
        public BrpString getBrpBuitenlandseRegio() {
            return brpBuitenlandseRegio;
        }

        /**
         * Geef de waarde van brp gemeente code.
         * @return the brpGemeenteCode
         */
        public BrpGemeenteCode getBrpGemeenteCode() {
            return brpGemeenteCode;
        }

        /**
         * Geef de waarde van brp buitenlandse plaats.
         * @return the brpBuitenlandsePlaats
         */
        public BrpString getBrpBuitenlandsePlaats() {
            return brpBuitenlandsePlaats;
        }

        /**
         * Geef de waarde van brp omschrijving locatie.
         * @return the brpOmschrijvingLocatie
         */
        public BrpString getBrpOmschrijvingLocatie() {
            return brpOmschrijvingLocatie;
        }

        /**
         * Geef de waarde van brp land of gebied code.
         * @return the brpLandOfGebiedCode
         */
        public BrpLandOfGebiedCode getBrpLandOfGebiedCode() {
            return brpLandOfGebiedCode;
        }
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser.gba;

import java.util.Dictionary;
import java.util.Hashtable;
import nl.bzk.brp.expressietaal.symbols.ExpressieAttribuut;

/**
 * Utility class voor GBA-rubrieken.
 */
public final class GbaRubrieken {

    private static final Dictionary<String, ExpressieAttribuut> ALFANUMRUBRIEKEN = getAlfanumRubrieken();
    private static final Dictionary<String, ExpressieAttribuut> NUMRUBRIEKEN = getNumRubrieken();
    private static final Dictionary<String, ExpressieAttribuut> DATRUBRIEKEN = getDatRubrieken();

    /**
     * Constructor. Private voor utility class.
     */
    private GbaRubrieken() {
    }


    /**
     * Geeft een dictionary met alle alfanumerieke rubrieken uit LO3 die in voorwaarderegels voorkomen.
     *
     * @return Dictionary met alfanumerieke rubrieken.
     */
    private static Dictionary<String, ExpressieAttribuut> getAlfanumRubrieken() {
        final Dictionary<String, ExpressieAttribuut> result = new Hashtable<>();
        result.put("01.02.10", ExpressieAttribuut.PERSOON_SAMENGESTELDE_NAAM_VOORNAMEN);
        //result.put("01.02.20",""); adellijke titel
        result.put("01.02.30", ExpressieAttribuut.PERSOON_SAMENGESTELDE_NAAM_VOORVOEGSEL);
        result.put("01.02.40", ExpressieAttribuut.PERSOON_SAMENGESTELDE_NAAM_GESLACHTSNAAMSTAM);
        result.put("01.04.10", ExpressieAttribuut.PERSOON_GESLACHTSAANDUIDING_GESLACHTSAANDUIDING);
        //result.put("04.65.10",""); aanduiding bijzonder nederlanderschap
        //        result.put("04.82.30","");
        //result.put("07.67.20", "");
        result.put("08.10.10", ExpressieAttribuut.PERSOONADRES_SOORT);
        result.put("08.11.60", ExpressieAttribuut.PERSOONADRES_POSTCODE);
        //        result.put("12.35.10","");
        //        result.put("12.35.20","");
        //        result.put("12.35.70","");
        return result;
    }

    /**
     * Geeft een dictionary met alle numerieke rubrieken uit LO3 die in voorwaarderegels voorkomen.
     *
     * @return Dictionary met numerieke rubrieken.
     */
    private static Dictionary<String, ExpressieAttribuut> getNumRubrieken() {
        final Dictionary<String, ExpressieAttribuut> result = new Hashtable<>();
        result.put("01.01.10", ExpressieAttribuut.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER);
        result.put("04.05.10", ExpressieAttribuut.PERSOONNATIONALITEIT_NATIONALITEIT);
        result.put("04.63.10", ExpressieAttribuut.PERSOONNATIONALITEIT_REDEN_VERKRIJGING);
        result.put("04.64.10", ExpressieAttribuut.PERSOONNATIONALITEIT_REDEN_VERLIES);
        result.put("05.06.30", ExpressieAttribuut.HUWELIJK_LAND_GEBIED_AANVANG);
        result.put("08.09.10", ExpressieAttribuut.PERSOON_BIJHOUDING_BIJHOUDINGSPARTIJ);
        result.put("08.13.10", ExpressieAttribuut.PERSOONADRES_LAND_GEBIED);
        result.put("08.14.10", ExpressieAttribuut.PERSOON_MIGRATIE_LAND_GEBIED);
        //result.put("14.40.10","");
        return result;
    }

    /**
     * Geeft een dictionary met alle datumgeorienteerde rubrieken uit LO3 die in voorwaarderegels voorkomen.
     *
     * @return Dictionary met datumgeorienteerde rubrieken.
     */
    private static Dictionary<String, ExpressieAttribuut> getDatRubrieken() {
        final Dictionary<String, ExpressieAttribuut> result = new Hashtable<>();
        result.put("01.03.10", ExpressieAttribuut.PERSOON_GEBOORTE_DATUM);
        //        result.put("01.85.10","");
        //        result.put("01.86.10","");
        //        result.put("02.62.10","");
        //        result.put("03.62.10","");
        //        result.put("04.85.10",);
        //        result.put("04.86.10","");
        //        result.put("05.06.10","");
        result.put("05.85.10", ExpressieAttribuut.HUWELIJK_DATUM_AANVANG);
        //        result.put("05.86.10","");
        result.put("06.08.10", ExpressieAttribuut.PERSOON_OVERLIJDEN_DATUM);
        //        result.put("06.85.10","");
        //        result.put("07.66.20","");
        result.put("07.67.10", ExpressieAttribuut.PERSOON_BIJHOUDING_DATUM_EINDE_GELDIGHEID);
        //        result.put("07.68.10","");
        result.put("08.09.20", ExpressieAttribuut.PERSOON_BIJHOUDING_DATUM_AANVANG_GELDIGHEID);
        result.put("08.10.30", ExpressieAttribuut.PERSOONADRES_DATUM_AANVANG_ADRESHOUDING);
        result.put("08.13.20", ExpressieAttribuut.PERSOON_MIGRATIE_DATUM_AANVANG_GELDIGHEID);
        result.put("08.14.20", ExpressieAttribuut.PERSOON_MIGRATIE_DATUM_AANVANG_GELDIGHEID);
        //        result.put("09.85.10","");
        result.put("12.35.50", ExpressieAttribuut.PERSOONREISDOCUMENT_DATUM_EINDE_DOCUMENT);
        return result;
    }

    /**
     * Zoekt het BRP-attribuut dat overeenstemt met de GBA-rubriek.
     *
     * @param rubriek De zoeken rubriek.
     * @return Gevonden BRP-attribuut of NULL.
     */
    public static ExpressieAttribuut getAttribute(final String rubriek) {
        ExpressieAttribuut result = NUMRUBRIEKEN.get(rubriek);
        if (result == null) {
            result = ALFANUMRUBRIEKEN.get(rubriek);
        }
        if (result == null) {
            result = DATRUBRIEKEN.get(rubriek);
        }
        return result;
    }

    /**
     * Geeft het BRP-attribuut voor een numerieke GBA-rubriek.
     *
     * @param rubriek GBA-rubriek.
     * @return BRP-attribuut of NULL indien niet gevonden.
     */
    public static ExpressieAttribuut getNumRubriek(final String rubriek) {
        return NUMRUBRIEKEN.get(rubriek);
    }

    /**
     * Geeft het BRP-attribuut voor een alfanumerieke GBA-rubriek.
     *
     * @param rubriek GBA-rubriek.
     * @return BRP-attribuut of NULL indien niet gevonden.
     */
    public static ExpressieAttribuut getAlfanumRubriek(final String rubriek) {
        return ALFANUMRUBRIEKEN.get(rubriek);
    }

    /**
     * Geeft het BRP-attribuut voor een GBA-datumrubriek.
     *
     * @param rubriek GBA-rubriek.
     * @return BRP-attribuut of NULL indien niet gevonden.
     */
    public static ExpressieAttribuut getDatRubriek(final String rubriek) {
        return DATRUBRIEKEN.get(rubriek);
    }

    /**
     * Geeft TRUE als de rubriek (rubrieknummer) een numerieke rubriek is.
     *
     * @param rubriek Te zoeken rubrieknummer.
     * @return TRUE als het een numerieke rubriek is, anders FALSE.
     */
    public static boolean isNumRubriek(final String rubriek) {
        return NUMRUBRIEKEN.get(rubriek) != null;
    }

    /**
     * Geeft TRUE als de rubriek (rubrieknummer) een datumrubriek is.
     *
     * @param rubriek Te zoeken rubrieknummer.
     * @return TRUE als het een datumrubriek is, anders FALSE.
     */
    public static boolean isDatRubriek(final String rubriek) {
        return DATRUBRIEKEN.get(rubriek) != null;
    }

    /**
     * Geeft TRUE als de rubriek (rubrieknummer) een alfanumerieke rubriek is.
     *
     * @param rubriek Te zoeken rubrieknummer.
     * @return TRUE als het een alfanumerieke rubriek is, anders FALSE.
     */
    public static boolean isAlfanumRubriek(final String rubriek) {
        return ALFANUMRUBRIEKEN.get(rubriek) != null;
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp;

import nl.moderniseringgba.migratie.Preconditie;
import nl.moderniseringgba.migratie.Precondities;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpLandCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.groep.FoutmeldingUtil;

/**
 * Valideer de verschillende groepen.
 * 
 */
public final class BrpGroepValidator {

    private BrpGroepValidator() {
        throw new AssertionError("BrpGroepValidator mag niet worden geïnstantieerd");
    }

    /**
     * Valideer dat het land Nederland is in het geval gemeente is gevuld.
     * 
     * @param gemeente
     *            de te valideren gemeentecode
     * @param land
     *            de te valideren landcode
     * @param groep
     *            de groep waarop de validatie wordt uitgevoerd
     */
    @Preconditie(Precondities.PRE003)
    public static void valideerGemeenteInNederland(
            final BrpGemeenteCode gemeente,
            final BrpLandCode land,
            final String groep) {
        if (gemeente != null && !BrpLandCode.NEDERLAND.equals(land)) {
            FoutmeldingUtil.gooiValidatieExceptie(groep + ": Gemeente is gevuld, maar land is niet Nederland",
                    Precondities.PRE003);
        }
    }

    /**
     * Valideer dat wanneer Buitenlandse plaats is gevuld, de landcode ongelijk is aan Nederland.
     * 
     * @param buitenlandsePlaats
     *            de te valideren buitenlandse plaats
     * @param land
     *            de te valideren landcode
     * @param groep
     *            de groep waarop de validatie wordt uitgevoerd
     */
    @Preconditie(Precondities.PRE004)
    public static void valideerBuitenlandsePlaatsNietNederland(
            final String buitenlandsePlaats,
            final BrpLandCode land,
            final String groep) {
        if (buitenlandsePlaats != null && BrpLandCode.NEDERLAND.equals(land)) {
            FoutmeldingUtil.gooiValidatieExceptie(groep + ": Buitenlandse plaats is gevuld, maar land is Nederland",
                    Precondities.PRE004);
        }
    }

    /**
     * Valideer dat voorvoegsel en scheidingsteken of allebei gevuld, of allebei leeg zijn.
     * 
     * @param voorvoegsel
     *            het te valideren voorvoegsel
     * @param scheidingsteken
     *            het te valideren scheidingsteken
     * @param groep
     *            de groep waarop de validatie wordt uitgevoerd
     */
    @Preconditie(Precondities.PRE022)
    public static void valideerVoorvoegselScheidingsteken(
            final String voorvoegsel,
            final Character scheidingsteken,
            final String groep) {
        if (isGevuldEnGeenSpatie(voorvoegsel) && scheidingsteken == null) {
            FoutmeldingUtil.gooiValidatieExceptie(groep + ": Voorvoegsel gevuld maar scheidingsteken leeg",
                    Precondities.PRE022);
        }
        if (!isGevuldEnGeenSpatie(voorvoegsel) && scheidingsteken != null) {
            FoutmeldingUtil.gooiValidatieExceptie(groep + ": Voorvoegsel leeg maar scheidingsteken gevuld",
                    Precondities.PRE022);
        }
    }

    private static boolean isGevuldEnGeenSpatie(final String string) {
        return string != null && string.trim().length() > 0;
    }

}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

import nl.bzk.migratiebrp.conversie.model.Preconditie;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.AbstractBrpAttribuutMetOnderzoek;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpValidatie;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;

/**
 * Valideer de verschillende groepen.
 */
public final class BrpGroepValidator {

    private BrpGroepValidator() {
        throw new AssertionError("BrpGroepValidator mag niet worden geïnstantieerd");
    }

    /**
     * Valideer dat het landOfGebiedCode Nederland is in het geval gemeente is gevuld.
     * @param gemeente de te valideren gemeentecode
     * @param landOfGebiedCode de te valideren landcode
     * @param groep de groep waarop de validatie wordt uitgevoerd
     */
    @Preconditie(SoortMeldingCode.PRE003)
    public static void valideerGemeenteInNederland(final BrpGemeenteCode gemeente, final BrpLandOfGebiedCode landOfGebiedCode, final BrpGroepInhoud groep) {
        if (BrpValidatie.isAttribuutGevuld(gemeente)
                && (!BrpValidatie.isAttribuutGevuld(landOfGebiedCode) || !AbstractBrpAttribuutMetOnderzoek.equalsWaarde(
                BrpLandOfGebiedCode.NEDERLAND,
                landOfGebiedCode))) {
            FoutmeldingUtil.gooiValidatieExceptie(SoortMeldingCode.PRE003, groep);
        }
    }

    /**
     * Valideer dat wanneer Buitenlandse plaats is gevuld, de landcode ongelijk is aan Nederland.
     * @param buitenlandsePlaats de te valideren buitenlandse plaats
     * @param landOfGebiedCode de te valideren landcode
     * @param groep de groep waarop de validatie wordt uitgevoerd
     */
    @Preconditie(SoortMeldingCode.PRE004)
    public static void valideerBuitenlandsePlaatsNietNederland(
            final BrpString buitenlandsePlaats,
            final BrpLandOfGebiedCode landOfGebiedCode,
            final BrpGroepInhoud groep) {
        if (BrpValidatie.isAttribuutGevuld(buitenlandsePlaats)
                && AbstractBrpAttribuutMetOnderzoek.equalsWaarde(BrpLandOfGebiedCode.NEDERLAND, landOfGebiedCode)) {
            FoutmeldingUtil.gooiValidatieExceptie(SoortMeldingCode.PRE004, groep);
        }
    }

    /**
     * Valideer dat voorvoegsel en scheidingsteken of allebei gevuld, of allebei leeg zijn.
     * @param voorvoegsel het te valideren voorvoegsel
     * @param scheidingsteken het te valideren scheidingsteken
     * @param groep de groep waarop de validatie wordt uitgevoerd
     */
    @Preconditie(SoortMeldingCode.PRE022)
    public static void valideerVoorvoegselScheidingsteken(final String voorvoegsel, final Character scheidingsteken, final BrpGroepInhoud groep) {
        if (isGevuldEnGeenSpatie(voorvoegsel) && scheidingsteken == null) {
            FoutmeldingUtil.gooiValidatieExceptie(SoortMeldingCode.PRE022, groep);
        }
        if (!isGevuldEnGeenSpatie(voorvoegsel) && scheidingsteken != null) {
            FoutmeldingUtil.gooiValidatieExceptie(SoortMeldingCode.PRE022, groep);
        }
    }

    private static boolean isGevuldEnGeenSpatie(final String string) {
        return string != null && string.trim().length() > 0;
    }
}

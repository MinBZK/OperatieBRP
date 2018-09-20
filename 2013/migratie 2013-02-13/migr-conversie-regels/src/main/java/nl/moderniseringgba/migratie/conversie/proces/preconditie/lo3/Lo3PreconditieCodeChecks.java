/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.preconditie.lo3;

import java.util.regex.Pattern;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.conversie.domein.conversietabel.ConversietabelFactory;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingVerblijfstitelCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AutoriteitVanAfgifteNederlandsReisdocument;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3NationaliteitCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3RedenNederlandschapCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3RedenOntbindingHuwelijkOfGpCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3SoortNederlandsReisdocument;

/**
 * Bevat checks for het controleren van Lo3 codes op basis van logische regels en op basis van de conversie tabellen.
 */
public abstract class Lo3PreconditieCodeChecks {
    private static final Pattern GEMEENTE_CODE_IND_NED_GEMEENTE_PATTERN = Pattern.compile("^[0-9].*$");
    private static final Pattern GEMEENTE_CODE_NED_PATTERN = Pattern.compile("^[0-9]{4}$");

    @Inject
    private ConversietabelFactory conversieTabelFactory;

    /**
     * Controleer of gemeentecode een valide nederlandse gemeentecode is en log een foutmelding indien niet.
     * 
     * @param landCode
     *            De landcode. Als dit null is, of een landcode voor een ander land dan Nederland, dan wordt geen fout
     *            gelogd.
     * @param gemeenteCode
     *            de gemeentecode om te controleren. null is geen valide gemeentecode.
     * @param foutmelding
     *            de foutmelding om te loggen als gemeentecode geen valide nederlandse gemeentecode is.
     */
    protected final void controleerNederlandseGemeente(
            final Lo3LandCode landCode,
            final Lo3GemeenteCode gemeenteCode,
            final Foutmelding foutmelding) {
        if (landCode != null && landCode.isNederlandCode()) {
            if (gemeenteCode == null || !gemeenteCode.isValideNederlandseGemeenteCode()
                    || !conversieTabelFactory.createGemeenteConversietabel().valideerLo3(gemeenteCode)) {
                foutmelding.log();
            }
        }
    }

    /**
     * Controleer of de gegeven huisletter een valide letter is, en log een foutmelding indien niet.
     * 
     * @param huisletter
     *            de huisletter om te controleren.
     * @param foutmelding
     *            de foutmelding om te loggen als de huisletter niet valide is.
     */
    protected static void controleerCode(final Character huisletter, final Foutmelding foutmelding) {
        if (huisletter == null) {
            return;
        }

        final char lower = Character.toLowerCase(huisletter);

        if (lower < 'a' || lower > 'z') {
            foutmelding.log();
        }

    }

    /**
     * Controleer of de gegeven code een geldige code is, en log een foutmelding indien niet.
     * 
     * @param aanduidingVerblijfstitelCode
     *            de code om te controleren.
     * @param foutmelding
     *            de foutmelding om te loggen als de code niet geldig is.
     */
    protected final void controleerCode(
            final Lo3AanduidingVerblijfstitelCode aanduidingVerblijfstitelCode,
            final Foutmelding foutmelding) {
        if (!conversieTabelFactory.createVerblijfsrechtConversietabel().valideerLo3(aanduidingVerblijfstitelCode)) {
            foutmelding.log();
        }
    }

    /**
     * Controleer of de gegeven code een geldige code is, en log een foutmelding indien niet.
     * 
     * @param adellijkeTitelPredikaatCode
     *            de code om te controleren.
     * @param foutmelding
     *            de foutmelding om te loggen als de code niet geldig is.
     */
    protected final void controleerCode(
            final Lo3AdellijkeTitelPredikaatCode adellijkeTitelPredikaatCode,
            final Foutmelding foutmelding) {
        if (!conversieTabelFactory.createAdellijkeTitelPredikaatConversietabel().valideerLo3(
                adellijkeTitelPredikaatCode)) {
            foutmelding.log();
        }
    }

    /**
     * Controleer of de gegeven code een geldige code is, en log een foutmelding indien niet.
     * 
     * @param autoriteitVanAfgifteNederlandsReisdocument
     *            de code om te controleren.
     * @param foutmelding
     *            de foutmelding om te loggen als de code niet geldig is.
     */
    protected final void controleerCode(
            final Lo3AutoriteitVanAfgifteNederlandsReisdocument autoriteitVanAfgifteNederlandsReisdocument,
            final Foutmelding foutmelding) {
        if (!conversieTabelFactory.createReisdocumentAutoriteitVanAfgifteConversietabel().valideerLo3(
                autoriteitVanAfgifteNederlandsReisdocument)) {
            foutmelding.log();
        }
    }

    /**
     * Controleer of de gegeven code een geldige code is, en log een foutmelding indien niet.
     * 
     * @param gemeenteCode
     *            de code om te controleren.
     * @param buitenlandsToegestaaan
     *            Indien true dan wordt een buitenlandge gemeentecode als geldig gezien.
     * @param foutmelding
     *            de foutmelding om te loggen als de code niet geldig is.
     */
    protected final void controleerCode(
            final Lo3GemeenteCode gemeenteCode,
            final boolean buitenlandsToegestaaan,
            final Foutmelding foutmelding) {
        if (gemeenteCode == null) {
            return;
        }

        if (GEMEENTE_CODE_IND_NED_GEMEENTE_PATTERN.matcher(gemeenteCode.getCode()).matches()) {
            if (!GEMEENTE_CODE_NED_PATTERN.matcher(gemeenteCode.getCode()).matches()) {
                foutmelding.log();
            } else {
                if (!conversieTabelFactory.createGemeenteConversietabel().valideerLo3(gemeenteCode)) {
                    foutmelding.log();
                }
            }
        } else {
            // Als de gemeente niet begint met een cijfer dan geeft het een buitenlandse plaats aan.
            if (!buitenlandsToegestaaan) {
                foutmelding.log();
            }
        }
    }

    /**
     * Controleer of de gegeven code een geldige code is, en log een foutmelding indien niet.
     * 
     * @param landCode
     *            de code om te controleren.
     * @param foutmelding
     *            de foutmelding om te loggen als de code niet geldig is.
     */
    protected final void controleerCode(final Lo3LandCode landCode, final Foutmelding foutmelding) {
        if (!conversieTabelFactory.createLandConversietabel().valideerLo3(landCode)) {
            foutmelding.log();
        }
    }

    /**
     * Controleer of de gegeven code een geldige code is, en log een foutmelding indien niet.
     * 
     * @param nationaliteitCode
     *            de code om te controleren.
     * @param foutmelding
     *            de foutmelding om te loggen als de code niet geldig is.
     */
    protected final void controleerCode(final Lo3NationaliteitCode nationaliteitCode, final Foutmelding foutmelding) {
        if (!conversieTabelFactory.createNationaliteitConversietabel().valideerLo3(nationaliteitCode)) {
            foutmelding.log();
        }
    }

    /**
     * Controleer of de gegeven code een geldige code is, en log een foutmelding indien niet.
     * 
     * @param redenNederlanderschapCode
     *            de code om te controleren.
     * @param verkrijging
     *            bij true wordt gecontroleerd voor Verkrijging, bij false voor Verlies.
     * @param foutmelding
     *            de foutmelding om te loggen als de code niet geldig is.
     */
    protected final void controleerCode(
            final Lo3RedenNederlandschapCode redenNederlanderschapCode,
            final Foutmelding foutmelding,
            final boolean verkrijging) {
        if (verkrijging) {
            if (!conversieTabelFactory.createRedenVerkrijgingNlNationaliteitConversietabel().valideerLo3(
                    redenNederlanderschapCode)) {
                foutmelding.log();
            }
        } else {
            if (!conversieTabelFactory.createRedenVerliesNlNationaliteitConversietabel().valideerLo3(
                    redenNederlanderschapCode)) {
                foutmelding.log();
            }
        }
    }

    /**
     * Controleer of de gegeven code een geldige code is, en log een foutmelding indien niet.
     * 
     * @param redenOntbindingHuwelijkOfGpCode
     *            de code om te controleren.
     * @param foutmelding
     *            de foutmelding om te loggen als de code niet geldig is.
     */
    protected final void controleerCode(
            final Lo3RedenOntbindingHuwelijkOfGpCode redenOntbindingHuwelijkOfGpCode,
            final Foutmelding foutmelding) {
        if (!conversieTabelFactory.createRedenEindeRelatieConversietabel().valideerLo3(
                redenOntbindingHuwelijkOfGpCode)) {
            foutmelding.log();
        }
    }

    /**
     * Controleer of de gegeven code een geldige code is, en log een foutmelding indien niet.
     * 
     * @param soortNederlandsReisdocument
     *            de code om te controleren.
     * @param foutmelding
     *            de foutmelding om te loggen als de code niet geldig is.
     */
    protected final void controleerCode(
            final Lo3SoortNederlandsReisdocument soortNederlandsReisdocument,
            final Foutmelding foutmelding) {
        if (!conversieTabelFactory.createSoortReisdocumentConversietabel().valideerLo3(soortNederlandsReisdocument)) {
            foutmelding.log();
        }
    }

    /**
     * Controleer of de gegeven code een geldig voorvoegsel is, en log een foutmelding indien niet.
     * 
     * @param voorvoegsel
     *            de code om te controleren.
     * @param foutmelding
     *            de foutmelding om te loggen als de code niet geldig is.
     */
    protected final void controleerCode(final String voorvoegsel, final Foutmelding foutmelding) {
        if (!conversieTabelFactory.createVoorvoegselScheidingstekenConversietabel().valideerLo3(voorvoegsel)) {
            foutmelding.log();
        }
    }

    /**
     * Controleer of de gegeven woonplaats een geldige woonplaats is, en log een foutmelding indien niet.
     * 
     * @param woonplaatsnaam
     *            de woonplaats om te controleren.
     * @param foutmelding
     *            de foutmelding om te loggen als de woonplaats niet geldig is.
     */
    protected final void controleerWoonplaats(final String woonplaatsnaam, final Foutmelding foutmelding) {
        if (!conversieTabelFactory.createPlaatsConversietabel().valideerLo3(woonplaatsnaam)) {
            foutmelding.log();
        }
    }

}

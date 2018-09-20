/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.preconditie.lo3;

import java.util.regex.Pattern;

import javax.inject.Inject;

import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.dynamisch.AbstractAdellijkeTitelPredikaatConversietabel;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory.ConversietabelFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingHuisnummer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingVerblijfstitelCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3NationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RNIDeelnemerCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOntbindingHuwelijkOfGpCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Validatie;

/**
 * Bevat checks for het controleren van Lo3 codes op basis van logische regels en op basis van de conversie tabellen.
 */
public abstract class AbstractLo3PreconditieCodeChecks {
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
    protected final void controleerNederlandseGemeente(final Lo3LandCode landCode, final Lo3GemeenteCode gemeenteCode, final Foutmelding foutmelding) {
        if (isNederland(landCode) && !isNederlandseGemeente(gemeenteCode)) {
            foutmelding.log();
        }
    }

    private boolean isNederland(final Lo3LandCode landCode) {
        return Validatie.isElementGevuld(landCode) && landCode.isNederlandCode();
    }

    private boolean isNederlandseGemeente(final Lo3GemeenteCode gemeenteCode) {
        return Validatie.isElementGevuld(gemeenteCode)
               && gemeenteCode.isValideNederlandseGemeenteCode()
               && conversieTabelFactory.createGemeenteConversietabel().valideerLo3(gemeenteCode);
    }

    /**
     * Controleer of de gegeven code een geldige code is, en log een foutmelding indien niet.
     * 
     * @param aanduidingVerblijfstitelCode
     *            de code om te controleren.
     * @param foutmelding
     *            de foutmelding om te loggen als de code niet geldig is.
     */
    protected final void controleerCode(final Lo3AanduidingVerblijfstitelCode aanduidingVerblijfstitelCode, final Foutmelding foutmelding) {
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
    protected final void controleerCode(final Lo3AdellijkeTitelPredikaatCode adellijkeTitelPredikaatCode, final Foutmelding foutmelding) {
        if (!conversieTabelFactory.createAdellijkeTitelPredikaatConversietabel().valideerLo3(adellijkeTitelPredikaatCode)) {
            foutmelding.log();
        }
    }

    /**
     * Controleer of de opgegeven adellijketitel/predikaat in combinatie met geslacht een geldige combinatie is.
     * 
     * @param adellijkeTitelPredikaatCode
     *            de adellijketitel of predikaat
     * @param geslachtsaanduiding
     *            geslacht code
     * @param foutmelding
     *            de foutmelding die gelogd wordt als bijzondere situatie van toepassing is
     */
    protected final void controleerBijzondereSituatieLB035(
        final Lo3AdellijkeTitelPredikaatCode adellijkeTitelPredikaatCode,
        final Lo3Geslachtsaanduiding geslachtsaanduiding,
        final Foutmelding foutmelding)
    {
        if (!Validatie.isElementGevuld(adellijkeTitelPredikaatCode)) {
            return;
        }

        final Conversietabel tabel = conversieTabelFactory.createAdellijkeTitelPredikaatConversietabel();
        if (((AbstractAdellijkeTitelPredikaatConversietabel) tabel).isBijzondereSituatieLB035VanToepassing(
            adellijkeTitelPredikaatCode,
            geslachtsaanduiding))
        {
            foutmelding.log();
        }
    }

    /**
     * Controleer of de gegeven code een geldige code is, en log een foutmelding indien niet.
     * 
     * @param gemeenteCode
     *            de code om te controleren.
     * @param buitenlandsToegestaan
     *            Indien true dan wordt een buitenlandge gemeentecode als geldig gezien.
     * @param foutmelding
     *            de foutmelding om te loggen als de code niet geldig is.
     */
    protected final void controleerCode(final Lo3GemeenteCode gemeenteCode, final boolean buitenlandsToegestaan, final Foutmelding foutmelding) {
        controleerCode(gemeenteCode, buitenlandsToegestaan, false, foutmelding);
    }

    /**
     * Controleer of de gegeven code een geldige code is, en log een foutmelding indien niet.
     * 
     * @param gemeenteCode
     *            de code om te controleren.
     * @param buitenlandsToegestaan
     *            Indien true dan wordt een buitenlandge gemeentecode als geldig gezien.
     * @param isNederlandsAdres
     *            Indien true dan wordt gemeentecode ook tegen gemeenteconversietabel gehouden ipv alleen
     *            partijconversietabel
     * @param foutmelding
     *            de foutmelding om te loggen als de code niet geldig is.
     */
    protected final void controleerCode(
        final Lo3GemeenteCode gemeenteCode,
        final boolean buitenlandsToegestaan,
        final boolean isNederlandsAdres,
        final Foutmelding foutmelding)
    {
        if (!Validatie.isElementGevuld(gemeenteCode)) {
            return;
        }

        if (GEMEENTE_CODE_IND_NED_GEMEENTE_PATTERN.matcher(gemeenteCode.getWaarde()).matches()) {
            if (!GEMEENTE_CODE_NED_PATTERN.matcher(gemeenteCode.getWaarde()).matches()) {
                foutmelding.log();
            } else {
                if (!conversieTabelFactory.createPartijConversietabel().valideerLo3(gemeenteCode)) {
                    foutmelding.log();
                }
                if (isNederlandsAdres && !conversieTabelFactory.createGemeenteConversietabel().valideerLo3(gemeenteCode)) {
                    foutmelding.log();
                }
            }
        } else {
            // Als de gemeente niet begint met een cijfer dan geeft het een buitenlandse plaats aan.
            if (!buitenlandsToegestaan) {
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
        if (nationaliteitCode != null
            && !nationaliteitCode.getWaarde().equals(Lo3NationaliteitCode.NATIONALITEIT_CODE_STAATLOOS)
            && !conversieTabelFactory.createNationaliteitConversietabel().valideerLo3(nationaliteitCode))
        {
            foutmelding.log();
        }
    }

    /**
     * Controleer of de gegeven code een geldige code is. Als dit niet het geval is, log dan de foutmelding.
     * 
     * @param rniDeelnemerCode
     *            de code om te controleren
     * @param foutmelding
     *            de foutmelding die gelogd wordt als de code niet geldig is
     */
    protected final void controleerCode(final Lo3RNIDeelnemerCode rniDeelnemerCode, final Foutmelding foutmelding) {
        if (!conversieTabelFactory.createRNIDeelnemerConversietabel().valideerLo3(rniDeelnemerCode)) {
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
        final boolean verkrijging)
    {
        if (verkrijging) {
            if (!conversieTabelFactory.createRedenOpnameNationaliteitConversietabel().valideerLo3(redenNederlanderschapCode)) {
                foutmelding.log();
            }
        } else {
            if (!conversieTabelFactory.createRedenBeeindigingNationaliteitConversietabel().valideerLo3(redenNederlanderschapCode)) {
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
    protected final void controleerCode(final Lo3RedenOntbindingHuwelijkOfGpCode redenOntbindingHuwelijkOfGpCode, final Foutmelding foutmelding) {
        Lo3RedenOntbindingHuwelijkOfGpCode redenOntbindingHuwelijkOfGpCodeZonderOnderzoek = null;
        if (redenOntbindingHuwelijkOfGpCode != null) {
            redenOntbindingHuwelijkOfGpCodeZonderOnderzoek = new Lo3RedenOntbindingHuwelijkOfGpCode(redenOntbindingHuwelijkOfGpCode.getWaarde(), null);
        }

        if (!conversieTabelFactory.createRedenEindeRelatieConversietabel().valideerLo3(redenOntbindingHuwelijkOfGpCodeZonderOnderzoek)) {
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
    protected final void controleerCode(final Lo3SoortNederlandsReisdocument soortNederlandsReisdocument, final Foutmelding foutmelding) {
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
    protected final void controleerVoorvoegsel(final Lo3String voorvoegsel, final Foutmelding foutmelding) {
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
    protected final void controleerWoonplaatsnaam(final Lo3String woonplaatsnaam, final Foutmelding foutmelding) {
        final String naam = Lo3String.unwrap(woonplaatsnaam);
        if (naam == null || ".".equals(naam) || conversieTabelFactory.createWoonplaatsnaamConversietabel().valideerLo3(naam)) {
            return;
        }
        foutmelding.log();
    }

    /**
     * Controleer of de gegeven code een geldige aanduiding huisnummer is, en log een foutmelding indien niet.
     * 
     * @param aanduiding
     *            de code om te controleren.
     * @param foutmelding
     *            de foutmelding om te loggen als de code niet geldig is.
     */
    protected final void controleerAanduidingHuisnummer(final Lo3AanduidingHuisnummer aanduiding, final Foutmelding foutmelding) {
        if (!conversieTabelFactory.createAanduidingHuisnummerConversietabel().valideerLo3(aanduiding)) {
            foutmelding.log();
        }
    }

    /**
     * Controleer de akteNummerString, log foutmelding indien geen valide akteNummer.
     * 
     * @param akteNummer
     *            akteNummerString de akteNummerString om te controleren.
     * @param foutmelding
     *            de melding om te loggen als de akteNummerString niet geldig is.
     */
    protected final void controleerNummerAkte(final Lo3String akteNummer, final Foutmelding foutmelding) {
        final String unwrappedAkteNummer = Lo3String.unwrap(akteNummer);
        if (unwrappedAkteNummer == null || unwrappedAkteNummer.length() < 1) {
            foutmelding.log();
        } else if (!conversieTabelFactory.createSoortRegisterSoortDocumentConversietabel().valideerLo3(unwrappedAkteNummer.charAt(0))) {
            foutmelding.log();
        }
    }
}

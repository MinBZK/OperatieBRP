/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.preconditie.lo3;

import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3AanduidingBijzonderNederlandschapEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3AanduidingEuropeesKiesrechtEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3AanduidingInhoudingVermissingNederlandsReisdocumentEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3AanduidingNaamgebruikCodeEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3AanduidingUitgeslotenKiesrechtEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3AangifteAdreshoudingEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3FunctieAdresEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3GeslachtsaanduidingEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3IndicatieCurateleregisterEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3IndicatieDocumentEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3IndicatieGeheimCodeEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3IndicatieGezagMinderjarigeEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3IndicatieOnjuistEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3IndicatiePKVolledigGeconverteerdCodeEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3RedenOpschortingBijhoudingCodeEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3SignaleringEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingBijzonderNederlandschap;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingEuropeesKiesrecht;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingInhoudingVermissingNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingNaamgebruikCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingUitgeslotenKiesrecht;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AangifteAdreshouding;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3FunctieAdres;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieCurateleregister;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieDocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieGeheimCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieGezagMinderjarige;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieOnjuist;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatiePKVolledigGeconverteerdCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOpschortingBijhoudingCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Signalering;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Validatie;

/**
 * Bevat de statische checks van Lo3 codes aan de hand van statisch gedefinieerde Enum classes.
 */
public final class Lo3PreconditieEnumCodeChecks {

    /**
     * Private constructor om te voorkomen dat de class geinstancieert wordt.
     */
    private Lo3PreconditieEnumCodeChecks() {
    }

    /**
     * Controleer of de gegeven code een geldige code is, en log een foutmelding indien niet.
     *
     * @param aanduidingBijzonderNederlandschap
     *            de code om te controleren.
     * @param foutmelding
     *            de foutmelding om te loggen als de code niet geldig is.
     */
    protected static void controleerCode(final Lo3AanduidingBijzonderNederlandschap aanduidingBijzonderNederlandschap, final Foutmelding foutmelding) {
        if (Validatie.isElementGevuld(aanduidingBijzonderNederlandschap)
            && !Lo3AanduidingBijzonderNederlandschapEnum.containsCode(aanduidingBijzonderNederlandschap.getWaarde()))
        {
            foutmelding.log();
        }
    }

    /**
     * Controleer of de gegeven code een geldige code is, en log een foutmelding indien niet.
     *
     * @param aanduidingEuropeesKiesrecht
     *            de code om te controleren.
     * @param foutmelding
     *            de foutmelding om te loggen als de code niet geldig is.
     */
    protected static void controleerCode(final Lo3AanduidingEuropeesKiesrecht aanduidingEuropeesKiesrecht, final Foutmelding foutmelding) {
        if (Validatie.isElementGevuld(aanduidingEuropeesKiesrecht)
            && !Lo3AanduidingEuropeesKiesrechtEnum.containsCode(aanduidingEuropeesKiesrecht.getIntegerWaarde()))
        {
            foutmelding.log();
        }
    }

    /**
     * Controleer of de gegeven code een geldige code is, en log een foutmelding indien niet.
     *
     * @param aanduidingInhouding
     *            de code om te controleren.
     * @param foutmelding
     *            de foutmelding om te loggen als de code niet geldig is.
     */
    protected static void controleerCode(final Lo3AanduidingInhoudingVermissingNederlandsReisdocument aanduidingInhouding, final Foutmelding foutmelding) {
        if (Validatie.isElementGevuld(aanduidingInhouding)
            && !Lo3AanduidingInhoudingVermissingNederlandsReisdocumentEnum.containsCode(aanduidingInhouding.getWaarde()))
        {
            foutmelding.log();
        }
    }

    /**
     * Controleer of de gegeven code een geldige code is, en log een foutmelding indien niet.
     *
     * @param aanduidingNaamgebruikCode
     *            de code om te controleren.
     * @param foutmelding
     *            de foutmelding om te loggen als de code niet geldig is.
     */
    protected static void controleerCode(final Lo3AanduidingNaamgebruikCode aanduidingNaamgebruikCode, final Foutmelding foutmelding) {
        if (Validatie.isElementGevuld(aanduidingNaamgebruikCode)
            && !Lo3AanduidingNaamgebruikCodeEnum.containsCode(aanduidingNaamgebruikCode.getWaarde()))
        {
            foutmelding.log();
        }
    }

    /**
     * Controleer of de gegeven code een geldige code is, en log een foutmelding indien niet.
     *
     * @param aanduidingUitgeslotenKiesrecht
     *            de code om te controleren.
     * @param foutmelding
     *            de foutmelding om te loggen als de code niet geldig is.
     */
    protected static void controleerCode(final Lo3AanduidingUitgeslotenKiesrecht aanduidingUitgeslotenKiesrecht, final Foutmelding foutmelding) {
        if (Validatie.isElementGevuld(aanduidingUitgeslotenKiesrecht)
            && !Lo3AanduidingUitgeslotenKiesrechtEnum.containsCode(aanduidingUitgeslotenKiesrecht.getWaarde()))
        {
            foutmelding.log();
        }
    }

    /**
     * Controleer of de gegeven code een geldige code is, en log een foutmelding indien niet.
     *
     * @param aangifteAdreshouding
     *            de code om te controleren.
     * @param foutmelding
     *            de foutmelding om te loggen als de code niet geldig is.
     */
    protected static void controleerCode(final Lo3AangifteAdreshouding aangifteAdreshouding, final Foutmelding foutmelding) {
        if (Validatie.isElementGevuld(aangifteAdreshouding) && !Lo3AangifteAdreshoudingEnum.containsCode(aangifteAdreshouding.getWaarde())) {
            foutmelding.log();
        }
    }

    /**
     * Controleer of de gegeven code een geldige code is, en log een foutmelding indien niet.
     *
     * @param functieAdres
     *            de code om te controleren.
     * @param foutmelding
     *            de foutmelding om te loggen als de code niet geldig is.
     */
    protected static void controleerCode(final Lo3FunctieAdres functieAdres, final Foutmelding foutmelding) {
        if (Validatie.isElementGevuld(functieAdres) && !Lo3FunctieAdresEnum.containsCode(functieAdres.getWaarde())) {
            foutmelding.log();
        }
    }

    /**
     * Controleer of de gegeven code een geldige code is, en log een foutmelding indien niet.
     *
     * @param geslachtsaanduiding
     *            de code om te controleren.
     * @param foutmelding
     *            de foutmelding om te loggen als de code niet geldig is.
     */
    protected static void controleerCode(final Lo3Geslachtsaanduiding geslachtsaanduiding, final Foutmelding foutmelding) {
        if (Validatie.isElementGevuld(geslachtsaanduiding) && !Lo3GeslachtsaanduidingEnum.containsCode(geslachtsaanduiding.getWaarde())) {
            foutmelding.log();
        }
    }

    /**
     * Controleer of de gegeven code een geldige code is, en log een foutmelding indien niet.
     *
     * @param indicatieCurateleregister
     *            de code om te controleren.
     * @param foutmelding
     *            de foutmelding om te loggen als de code niet geldig is.
     */
    protected static void controleerCode(final Lo3IndicatieCurateleregister indicatieCurateleregister, final Foutmelding foutmelding) {
        if (Validatie.isElementGevuld(indicatieCurateleregister)
            && !Lo3IndicatieCurateleregisterEnum.containsCode(indicatieCurateleregister.getIntegerWaarde()))
        {
            foutmelding.log();
        }
    }

    /**
     * Controleer of de gegeven code een geldige code is, en log een foutmelding indien niet.
     *
     * @param indicatieDocument
     *            de code om te controleren.
     * @param foutmelding
     *            de foutmelding om te loggen als de code niet geldig is.
     */
    protected static void controleerCode(final Lo3IndicatieDocument indicatieDocument, final Foutmelding foutmelding) {
        if (Validatie.isElementGevuld(indicatieDocument) && !Lo3IndicatieDocumentEnum.containsCode(indicatieDocument.getIntegerWaarde())) {
            foutmelding.log();
        }
    }

    /**
     * Controleer of de gegeven code een geldige code is, en log een foutmelding indien niet.
     *
     * @param indicatieGeheimCode
     *            de code om te controleren.
     * @param foutmelding
     *            de foutmelding om te loggen als de code niet geldig is.
     */
    protected static void controleerCode(final Lo3IndicatieGeheimCode indicatieGeheimCode, final Foutmelding foutmelding) {
        if (Validatie.isElementGevuld(indicatieGeheimCode) && !Lo3IndicatieGeheimCodeEnum.containsCode(indicatieGeheimCode.getIntegerWaarde())) {
            foutmelding.log();
        }
    }

    /**
     * Controleer of de gegeven code een geldige code is, en log een foutmelding indien niet.
     *
     * @param indicatieGezagMinderjarige
     *            de code om te controleren.
     * @param foutmelding
     *            de foutmelding om te loggen als de code niet geldig is.
     */
    protected static void controleerCode(final Lo3IndicatieGezagMinderjarige indicatieGezagMinderjarige, final Foutmelding foutmelding) {
        if (Validatie.isElementGevuld(indicatieGezagMinderjarige)
            && !Lo3IndicatieGezagMinderjarigeEnum.containsCode(indicatieGezagMinderjarige.getWaarde()))
        {
            foutmelding.log();
        }
    }

    /**
     * Controleer of de gegeven code een geldige code is, en log een foutmelding indien niet.
     *
     * @param indicatiePKVolledigGeconverteerdCode
     *            de code om te controleren.
     * @param foutmelding
     *            de foutmelding om te loggen als de code niet geldig is.
     */
    protected static void controleerCode(
        final Lo3IndicatiePKVolledigGeconverteerdCode indicatiePKVolledigGeconverteerdCode,
        final Foutmelding foutmelding)
    {
        if (Validatie.isElementGevuld(indicatiePKVolledigGeconverteerdCode)
            && !Lo3IndicatiePKVolledigGeconverteerdCodeEnum.containsCode(indicatiePKVolledigGeconverteerdCode.getWaarde()))
        {
            foutmelding.log();
        }
    }

    /**
     * Controleer of de gegeven code een geldige code is, en log een foutmelding indien niet.
     *
     * @param onjuist
     *            de code om te controleren.
     * @param foutmelding
     *            de foutmelding om te loggen als de code niet geldig is.
     */
    protected static void controleerCode(final Lo3IndicatieOnjuist onjuist, final Foutmelding foutmelding) {
        if (Validatie.isElementGevuld(onjuist) && !Lo3IndicatieOnjuistEnum.containsCode(onjuist.getWaarde())) {
            foutmelding.log();
        }
    }

    /**
     * Controleer of de gegeven code een geldige code is, en log een foutmelding indien niet.
     *
     * @param redenOpschortingBijhoudingCode
     *            de code om te controleren.
     * @param foutmelding
     *            de foutmelding om te loggen als de code niet geldig is.
     */
    protected static void controleerCode(final Lo3RedenOpschortingBijhoudingCode redenOpschortingBijhoudingCode, final Foutmelding foutmelding) {
        if (Validatie.isElementGevuld(redenOpschortingBijhoudingCode)
            && !Lo3RedenOpschortingBijhoudingCodeEnum.containsCode(redenOpschortingBijhoudingCode.getWaarde()))
        {
            foutmelding.log();
        }
    }

    /**
     * Controleer of de gegeven code een geldige code is, en log een foutmelding indien niet.
     *
     * @param signalering
     *            de code om te controleren.
     * @param foutmelding
     *            de foutmelding om te loggen als de code niet geldig is.
     */
    protected static void controleerCode(final Lo3Signalering signalering, final Foutmelding foutmelding) {
        if (Validatie.isElementGevuld(signalering) && !Lo3SignaleringEnum.containsCode(signalering.getIntegerWaarde())) {
            foutmelding.log();
        }
    }

}

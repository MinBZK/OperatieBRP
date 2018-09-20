/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.preconditie.lo3;

import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3AanduidingBezitBuitenlandsReisdocumentEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3AanduidingBijzonderNederlandschapEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3AanduidingEuropeesKiesrechtEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3AanduidingHuisnummerEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3AanduidingInhoudingVermissingNederlandsReisdocumentEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3AanduidingNaamgebruikCodeEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3AanduidingUitgeslotenKiesrechtEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3AangifteAdreshoudingEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3FunctieAdresEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3GeslachtsaanduidingEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3IndicatieCurateleregisterEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3IndicatieDocumentEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3IndicatieGeheimCodeEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3IndicatieGezagMinderjarigeEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3IndicatieOnjuistEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3IndicatiePKVolledigGeconverteerdCodeEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3RedenOpschortingBijhoudingCodeEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3SignaleringEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3SoortVerbintenisEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingBezitBuitenlandsReisdocument;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingBijzonderNederlandschap;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingEuropeesKiesrecht;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingHuisnummer;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingInhoudingVermissingNederlandsReisdocument;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingNaamgebruikCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingUitgeslotenKiesrecht;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AangifteAdreshouding;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3FunctieAdres;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieCurateleregister;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieDocument;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieGeheimCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieGezagMinderjarige;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieOnjuist;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatiePKVolledigGeconverteerdCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3RedenOpschortingBijhoudingCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Signalering;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3SoortVerbintenis;

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
     * @param aanduidingBezitBuitenlandsReisdocument
     *            de code om te controleren.
     * @param foutmelding
     *            de foutmelding om te loggen als de code niet geldig is.
     */
    protected static void controleerCode(
            final Lo3AanduidingBezitBuitenlandsReisdocument aanduidingBezitBuitenlandsReisdocument,
            final Foutmelding foutmelding) {
        if (aanduidingBezitBuitenlandsReisdocument != null) {
            if (!Lo3AanduidingBezitBuitenlandsReisdocumentEnum.containsCode(aanduidingBezitBuitenlandsReisdocument
                    .getCode())) {
                foutmelding.log();
            }
        }
    }

    /**
     * Controleer of de gegeven code een geldige code is, en log een foutmelding indien niet.
     * 
     * @param aanduidingBijzonderNederlandschap
     *            de code om te controleren.
     * @param foutmelding
     *            de foutmelding om te loggen als de code niet geldig is.
     */
    protected static void controleerCode(
            final Lo3AanduidingBijzonderNederlandschap aanduidingBijzonderNederlandschap,
            final Foutmelding foutmelding) {
        if (aanduidingBijzonderNederlandschap != null) {
            if (!Lo3AanduidingBijzonderNederlandschapEnum.containsCode(aanduidingBijzonderNederlandschap.getCode())) {
                foutmelding.log();
            }
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
    protected static void controleerCode(
            final Lo3AanduidingEuropeesKiesrecht aanduidingEuropeesKiesrecht,
            final Foutmelding foutmelding) {
        if (aanduidingEuropeesKiesrecht != null) {
            if (!Lo3AanduidingEuropeesKiesrechtEnum.containsCode(aanduidingEuropeesKiesrecht.getCode())) {
                foutmelding.log();
            }
        }
    }

    /**
     * Controleer of de gegeven code een geldige code is, en log een foutmelding indien niet.
     * 
     * @param aanduidingHuisnummer
     *            de code om te controleren.
     * @param foutmelding
     *            de foutmelding om te loggen als de code niet geldig is.
     */
    protected static void controleerCode(
            final Lo3AanduidingHuisnummer aanduidingHuisnummer,
            final Foutmelding foutmelding) {
        if (aanduidingHuisnummer != null) {
            if (!Lo3AanduidingHuisnummerEnum.containsCode(aanduidingHuisnummer.getCode())) {
                foutmelding.log();
            }
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
    protected static void controleerCode(
            final Lo3AanduidingInhoudingVermissingNederlandsReisdocument aanduidingInhouding,
            final Foutmelding foutmelding) {
        if (aanduidingInhouding != null) {
            if (!Lo3AanduidingInhoudingVermissingNederlandsReisdocumentEnum.containsCode(aanduidingInhouding
                    .getCode())) {
                foutmelding.log();
            }
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
    protected static void controleerCode(
            final Lo3AanduidingNaamgebruikCode aanduidingNaamgebruikCode,
            final Foutmelding foutmelding) {
        if (aanduidingNaamgebruikCode != null) {
            if (!Lo3AanduidingNaamgebruikCodeEnum.containsCode(aanduidingNaamgebruikCode.getCode())) {
                foutmelding.log();
            }
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
    protected static void controleerCode(
            final Lo3AanduidingUitgeslotenKiesrecht aanduidingUitgeslotenKiesrecht,
            final Foutmelding foutmelding) {
        if (aanduidingUitgeslotenKiesrecht != null) {
            if (!Lo3AanduidingUitgeslotenKiesrechtEnum.containsCode(aanduidingUitgeslotenKiesrecht.getCode())) {
                foutmelding.log();
            }
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
    protected static void controleerCode(
            final Lo3AangifteAdreshouding aangifteAdreshouding,
            final Foutmelding foutmelding) {
        if (aangifteAdreshouding != null) {
            if (!Lo3AangifteAdreshoudingEnum.containsCode(aangifteAdreshouding.getCode())) {
                foutmelding.log();
            }
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
        if (functieAdres != null) {
            if (!Lo3FunctieAdresEnum.containsCode(functieAdres.getCode())) {
                foutmelding.log();
            }
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
    protected static void controleerCode(
            final Lo3Geslachtsaanduiding geslachtsaanduiding,
            final Foutmelding foutmelding) {
        if (geslachtsaanduiding != null) {
            if (!Lo3GeslachtsaanduidingEnum.containsCode(geslachtsaanduiding.getCode())) {
                foutmelding.log();
            }
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
    protected static void controleerCode(
            final Lo3IndicatieCurateleregister indicatieCurateleregister,
            final Foutmelding foutmelding) {
        if (indicatieCurateleregister != null) {
            if (!Lo3IndicatieCurateleregisterEnum.containsCode(indicatieCurateleregister.getCode())) {
                foutmelding.log();
            }
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
        if (indicatieDocument != null) {
            if (!Lo3IndicatieDocumentEnum.containsCode(indicatieDocument.getCode())) {
                foutmelding.log();
            }
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
    protected static void controleerCode(
            final Lo3IndicatieGeheimCode indicatieGeheimCode,
            final Foutmelding foutmelding) {
        if (indicatieGeheimCode != null) {
            if (!Lo3IndicatieGeheimCodeEnum.containsCode(indicatieGeheimCode.getCode())) {
                foutmelding.log();
            }
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
    protected static void controleerCode(
            final Lo3IndicatieGezagMinderjarige indicatieGezagMinderjarige,
            final Foutmelding foutmelding) {
        if (indicatieGezagMinderjarige != null) {
            if (!Lo3IndicatieGezagMinderjarigeEnum.containsCode(indicatieGezagMinderjarige.getCode())) {
                foutmelding.log();
            }
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
            final Foutmelding foutmelding) {
        if (indicatiePKVolledigGeconverteerdCode != null) {
            if (!Lo3IndicatiePKVolledigGeconverteerdCodeEnum.containsCode(indicatiePKVolledigGeconverteerdCode
                    .getCode())) {
                foutmelding.log();
            }
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
        if (onjuist != null) {
            if (!Lo3IndicatieOnjuistEnum.containsCode(onjuist.getCode())) {
                foutmelding.log();
            }
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
    protected static void controleerCode(
            final Lo3RedenOpschortingBijhoudingCode redenOpschortingBijhoudingCode,
            final Foutmelding foutmelding) {
        if (redenOpschortingBijhoudingCode != null) {
            if (!Lo3RedenOpschortingBijhoudingCodeEnum.containsCode(redenOpschortingBijhoudingCode.getCode())) {
                foutmelding.log();
            }
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
        if (signalering != null) {
            if (!Lo3SignaleringEnum.containsCode(signalering.getCode())) {
                foutmelding.log();
            }
        }
    }

    /**
     * Controleer of de gegeven code een geldige code is, en log een foutmelding indien niet.
     * 
     * @param soortVerbintenis
     *            de code om te controleren.
     * @param foutmelding
     *            de foutmelding om te loggen als de code niet geldig is.
     */
    protected static void controleerCode(final Lo3SoortVerbintenis soortVerbintenis, final Foutmelding foutmelding) {
        if (soortVerbintenis != null) {
            if (!Lo3SoortVerbintenisEnum.containsCode(soortVerbintenis.getCode())) {
                foutmelding.log();
            }
        }
    }
}

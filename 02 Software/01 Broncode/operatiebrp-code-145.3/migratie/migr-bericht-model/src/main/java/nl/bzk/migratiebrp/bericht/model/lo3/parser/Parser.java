/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.parser;

import java.util.Map;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingBijzonderNederlandschap;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingEuropeesKiesrecht;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingHuisnummer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingInhoudingVermissingNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingNaamgebruikCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingUitgeslotenKiesrecht;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingVerblijfstitelCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AangifteAdreshouding;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AutoriteitVanAfgifteNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Character;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datumtijdstempel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3FunctieAdres;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Huisnummer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieCurateleregister;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieDocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieGeheimCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieGezagMinderjarige;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieOnjuist;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatiePKVolledigGeconverteerdCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Long;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3NationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RNIDeelnemerCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOntbindingHuwelijkOfGpCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOpschortingBijhoudingCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Signalering;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortVerbintenis;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;

/**
 * Parser utility methoden voor persoonslijst elementen. Deze parsers verwerken ook onderzoek.
 */
public final class Parser {

    private Parser() {
        throw new IllegalStateException("Niet instantieerbaar");
    }

    private static Lo3Onderzoek bepaalOnderzoek(final Lo3Onderzoek onderzoek, final Lo3CategorieEnum categorie, final Lo3ElementEnum element) {
        final Lo3Onderzoek result;
        if (onderzoek != null && onderzoek.omvatElementInCategorie(element, categorie)) {
            result = onderzoek;
        } else {
            result = null;
        }
        return result;
    }

    /**
     * Parse een Lo3Integer.
     * @param elementen De Map van waarden.
     * @param element Het element dat uit de elementen gelezen en verwijderd moet worden.
     * @param categorie De categorie waar dit element bij hoort.
     * @param onderzoek Het onderzoek op de huidige categorie, of null.
     * @return Lo3Integer of null als de waarde null is
     * @throws NumberFormatException als de waarde niet alleen cijfers bevat
     */
    public static Lo3Integer parseLo3Integer(
            final Map<Lo3ElementEnum, String> elementen,
            final Lo3ElementEnum element,
            final Lo3CategorieEnum categorie,
            final Lo3Onderzoek onderzoek) {
        final Lo3Integer resultaat;
        final String elementWaarde = elementen.remove(element);
        final String waarde = !isElementGevuld(elementWaarde) ? null : elementWaarde;
        final Lo3Onderzoek elementOnderzoek = bepaalOnderzoek(onderzoek, categorie, element);

        if (waarde != null || elementOnderzoek != null) {
            resultaat = new Lo3Integer(waarde, elementOnderzoek);
        } else {
            resultaat = null;
        }
        return resultaat;
    }

    /**
     * Parse een Lo3Long.
     * @param elementen De Map van waarden.
     * @param element Het element dat uit de elementen gelezen en verwijderd moet worden.
     * @param categorie De categorie waar dit element bij hoort.
     * @param onderzoek Het onderzoek op de huidige categorie, of null.
     * @return Lo3Long of null als de waarde null is
     * @throws NumberFormatException als de waarde niet alleen cijfers bevat
     */
    static Lo3Long parseLo3Long(
            final Map<Lo3ElementEnum, String> elementen,
            final Lo3ElementEnum element,
            final Lo3CategorieEnum categorie,
            final Lo3Onderzoek onderzoek) {
        final Lo3Long resultaat;
        final String elementWaarde = elementen.remove(element);
        final String waarde = !isElementGevuld(elementWaarde) ? null : elementWaarde;
        final Lo3Onderzoek elementOnderzoek = bepaalOnderzoek(onderzoek, categorie, element);

        if (waarde != null || elementOnderzoek != null) {
            resultaat = new Lo3Long(waarde, elementOnderzoek);
        } else {
            resultaat = null;
        }
        return resultaat;
    }

    /**
     * Parse een Lo3String.
     * @param elementen De Map van waarden.
     * @param element Het element dat uit de elementen gelezen en verwijderd moet worden.
     * @param categorie De categorie waar dit element bij hoort.
     * @param onderzoek Het onderzoek op de huidige categorie, of null.
     * @return Lo3String of null als de waarde null is
     * @throws NumberFormatException als de waarde niet alleen cijfers bevat
     */
    public static Lo3String parseLo3String(
            final Map<Lo3ElementEnum, String> elementen,
            final Lo3ElementEnum element,
            final Lo3CategorieEnum categorie,
            final Lo3Onderzoek onderzoek) {
        final Lo3String resultaat;
        final String elementWaarde = elementen.remove(element);
        final String waarde = !isElementGevuld(elementWaarde) ? null : elementWaarde;
        final Lo3Onderzoek elementOnderzoek = bepaalOnderzoek(onderzoek, categorie, element);

        if (waarde != null || elementOnderzoek != null) {
            resultaat = new Lo3String(waarde, elementOnderzoek);
        } else {
            resultaat = null;
        }

        return resultaat;
    }

    /**
     * Parse een Lo3Character.
     * @param elementen De Map van waarden.
     * @param element Het element dat uit de elementen gelezen en verwijderd moet worden.
     * @param categorie De categorie waar dit element bij hoort.
     * @param onderzoek Het onderzoek op de huidige categorie, of null.
     * @return Lo3Character of null als de waarde null is
     */
    static Lo3Character parseLo3Character(
            final Map<Lo3ElementEnum, String> elementen,
            final Lo3ElementEnum element,
            final Lo3CategorieEnum categorie,
            final Lo3Onderzoek onderzoek) {
        final Lo3Character resultaat;
        final String elementWaarde = elementen.remove(element);
        final String waarde = !isElementGevuld(elementWaarde) ? null : elementWaarde;
        final Lo3Onderzoek elementOnderzoek = bepaalOnderzoek(onderzoek, categorie, element);

        if (waarde != null || elementOnderzoek != null) {
            resultaat = new Lo3Character(waarde, elementOnderzoek);
        } else {
            resultaat = null;
        }
        return resultaat;
    }

    /**
     * Parse een Lo3AdellijkeTitelPredikaatCode.
     * @param elementen De Map van waarden.
     * @param element Het element dat uit de elementen gelezen en verwijderd moet worden.
     * @param categorie De categorie waar dit element bij hoort.
     * @param onderzoek Het onderzoek op de huidige categorie, of null.
     * @return Lo3AdellijkeTitelPredikaatCode of null als de waarde null is
     * @throws IllegalArgumentException als de waarde niet voldoet aan de inhoudelijke eisen van
     * {@link Lo3AdellijkeTitelPredikaatCode#Lo3AdellijkeTitelPredikaatCode}
     */
    public static Lo3AdellijkeTitelPredikaatCode parseLo3AdellijkeTitelPredikaatCode(
            final Map<Lo3ElementEnum, String> elementen,
            final Lo3ElementEnum element,
            final Lo3CategorieEnum categorie,
            final Lo3Onderzoek onderzoek) {
        final Lo3AdellijkeTitelPredikaatCode resultaat;
        final String elementWaarde = elementen.remove(element);
        final String waarde = !isElementGevuld(elementWaarde) ? null : elementWaarde;
        final Lo3Onderzoek elementOnderzoek = bepaalOnderzoek(onderzoek, categorie, element);

        if (waarde != null || elementOnderzoek != null) {
            resultaat = new Lo3AdellijkeTitelPredikaatCode(waarde, elementOnderzoek);
        } else {
            resultaat = null;
        }
        return resultaat;
    }

    /**
     * Parse een Lo3Datum.
     * @param elementen De Map van waarden.
     * @param element Het element dat uit de elementen gelezen en verwijderd moet worden.
     * @param categorie De categorie waar dit element bij hoort.
     * @param onderzoek Het onderzoek op de huidige categorie, of null.
     * @return Lo3Datum of null als de waarde null is
     * @throws NumberFormatException als de waarde niet alleen cijfers bevat
     */
    public static Lo3Datum parseLo3Datum(
            final Map<Lo3ElementEnum, String> elementen,
            final Lo3ElementEnum element,
            final Lo3CategorieEnum categorie,
            final Lo3Onderzoek onderzoek) {
        final Lo3Datum resultaat;
        final String elementWaarde = elementen.remove(element);
        final String waarde = !isElementGevuld(elementWaarde) ? null : elementWaarde;
        final Lo3Onderzoek elementOnderzoek = bepaalOnderzoek(onderzoek, categorie, element);

        if (waarde != null || elementOnderzoek != null) {
            resultaat = new Lo3Datum(waarde, elementOnderzoek);
        } else {
            resultaat = null;
        }

        return resultaat;
    }

    /**
     * Parse een Lo3GemeenteCode.
     * @param elementen De Map van waarden.
     * @param element Het element dat uit de elementen gelezen en verwijderd moet worden.
     * @param categorie De categorie waar dit element bij hoort.
     * @param onderzoek Het onderzoek op de huidige categorie, of null.
     * @return Lo3GemeenteCode of null als de waarde null is
     * @throws IllegalArgumentException als de waarde niet voldoet aan de inhoudelijke eisen van {@link Lo3GemeenteCode#Lo3GemeenteCode}
     */
    public static Lo3GemeenteCode parseLo3GemeenteCode(
            final Map<Lo3ElementEnum, String> elementen,
            final Lo3ElementEnum element,
            final Lo3CategorieEnum categorie,
            final Lo3Onderzoek onderzoek) {
        final Lo3GemeenteCode resultaat;
        final String elementWaarde = elementen.remove(element);
        final String waarde = !isElementGevuld(elementWaarde) ? null : elementWaarde;
        final Lo3Onderzoek elementOnderzoek = bepaalOnderzoek(onderzoek, categorie, element);

        if (waarde != null || elementOnderzoek != null) {
            resultaat = new Lo3GemeenteCode(waarde, elementOnderzoek);
        } else {
            resultaat = null;
        }

        return resultaat;
    }

    /**
     * Parse een Lo3LandCode.
     * @param elementen De Map van waarden.
     * @param element Het element dat uit de elementen gelezen en verwijderd moet worden.
     * @param categorie De categorie waar dit element bij hoort.
     * @param onderzoek Het onderzoek op de huidige categorie, of null.
     * @return Lo3GemeenteCode of null als de waarde null is
     * @throws IllegalArgumentException als de waarde niet voldoet aan de inhoudelijke eisen van {@link Lo3GemeenteCode#Lo3GemeenteCode}
     */
    public static Lo3LandCode parseLo3LandCode(
            final Map<Lo3ElementEnum, String> elementen,
            final Lo3ElementEnum element,
            final Lo3CategorieEnum categorie,
            final Lo3Onderzoek onderzoek) {
        final Lo3LandCode resultaat;
        final String elementWaarde = elementen.remove(element);
        final String waarde = !isElementGevuld(elementWaarde) ? null : elementWaarde;
        final Lo3Onderzoek elementOnderzoek = bepaalOnderzoek(onderzoek, categorie, element);

        if (waarde != null || elementOnderzoek != null) {
            resultaat = new Lo3LandCode(waarde, elementOnderzoek);
        } else {
            resultaat = null;
        }

        return resultaat;
    }

    /**
     * Parse een Lo3Geslachtsaanduiding.
     * @param elementen De Map van waarden.
     * @param element Het element dat uit de elementen gelezen en verwijderd moet worden.
     * @param categorie De categorie waar dit element bij hoort.
     * @param onderzoek Het onderzoek op de huidige categorie, of null.
     * @return Lo3Geslachtsaanduiding of null als de waarde null is
     */
    static Lo3Geslachtsaanduiding parseLo3Geslachtsaanduiding(
            final Map<Lo3ElementEnum, String> elementen,
            final Lo3ElementEnum element,
            final Lo3CategorieEnum categorie,
            final Lo3Onderzoek onderzoek) {
        final Lo3Geslachtsaanduiding resultaat;
        final String elementWaarde = elementen.remove(element);
        final String waarde = !isElementGevuld(elementWaarde) ? null : elementWaarde;
        final Lo3Onderzoek elementOnderzoek = bepaalOnderzoek(onderzoek, categorie, element);

        if (waarde != null || elementOnderzoek != null) {
            resultaat = new Lo3Geslachtsaanduiding(waarde, elementOnderzoek);
        } else {
            resultaat = null;
        }

        return resultaat;
    }

    /**
     * Parse een Lo3AanduidingNaamgebruikCode.
     * @param elementen De Map van waarden.
     * @param element Het element dat uit de elementen gelezen en verwijderd moet worden.
     * @param categorie De categorie waar dit element bij hoort.
     * @param onderzoek Het onderzoek op de huidige categorie, of null.
     * @return Lo3AanduidingNaamgebruikCode of null als de waarde null is
     */
    static Lo3AanduidingNaamgebruikCode parseLo3AanduidingNaamgebruikCode(
            final Map<Lo3ElementEnum, String> elementen,
            final Lo3ElementEnum element,
            final Lo3CategorieEnum categorie,
            final Lo3Onderzoek onderzoek) {
        final Lo3AanduidingNaamgebruikCode resultaat;
        final String elementWaarde = elementen.remove(element);
        final String waarde = !isElementGevuld(elementWaarde) ? null : elementWaarde;
        final Lo3Onderzoek elementOnderzoek = bepaalOnderzoek(onderzoek, categorie, element);

        if (waarde != null || elementOnderzoek != null) {
            resultaat = new Lo3AanduidingNaamgebruikCode(waarde, elementOnderzoek);
        } else {
            resultaat = null;
        }

        return resultaat;
    }

    /**
     * Parse een Lo3RNIDeelnemerCode.
     * @param elementen De Map van waarden.
     * @param element Het element dat uit de elementen gelezen en verwijderd moet worden.
     * @param categorie De categorie waar dit element bij hoort.
     * @param onderzoek Het onderzoek op de huidige categorie, of null.
     * @return Lo3RNIDeelnemerCode of null als de waarde null is
     */
    static Lo3RNIDeelnemerCode parseLo3RniDeelnemerCode(final Map<Lo3ElementEnum, String> elementen, final Lo3ElementEnum element,
                                                        final Lo3CategorieEnum categorie,
                                                        final Lo3Onderzoek onderzoek) {
        final Lo3RNIDeelnemerCode resultaat;
        final String elementWaarde = elementen.remove(element);
        final String waarde = !isElementGevuld(elementWaarde) ? null : elementWaarde;
        final Lo3Onderzoek elementOnderzoek = bepaalOnderzoek(onderzoek, categorie, element);

        if (waarde != null || elementOnderzoek != null) {
            resultaat = new Lo3RNIDeelnemerCode(waarde, elementOnderzoek);
        } else {
            resultaat = null;
        }
        return resultaat;
    }

    /**
     * Parse een Lo3IndicatieOnjuist.
     * @param elementen De Map van waarden.
     * @param element Het element dat uit de elementen gelezen en verwijderd moet worden.
     * @param categorie De categorie waar dit element bij hoort.
     * @param onderzoek Het onderzoek op de huidige categorie, of null.
     * @return Lo3IndicatieOnjuist of null als de waarde null is
     */
    static Lo3IndicatieOnjuist parseLo3IndicatieOnjuist(
            final Map<Lo3ElementEnum, String> elementen,
            final Lo3ElementEnum element,
            final Lo3CategorieEnum categorie,
            final Lo3Onderzoek onderzoek) {
        final Lo3IndicatieOnjuist resultaat;
        final String elementWaarde = elementen.remove(element);
        final String waarde = !isElementGevuld(elementWaarde) ? null : elementWaarde;
        final Lo3Onderzoek elementOnderzoek = bepaalOnderzoek(onderzoek, categorie, element);

        if (waarde != null || elementOnderzoek != null) {
            resultaat = new Lo3IndicatieOnjuist(waarde, elementOnderzoek);
        } else {
            resultaat = null;
        }
        return resultaat;
    }

    /**
     * Parse een Lo3NationaliteitCode.
     * @param elementen De Map van waarden.
     * @param element Het element dat uit de elementen gelezen en verwijderd moet worden.
     * @param categorie De categorie waar dit element bij hoort.
     * @param onderzoek Het onderzoek op de huidige categorie, of null.
     * @return Lo3NationaliteitCode of null als de waarde null is
     */
    static Lo3NationaliteitCode parseLo3NationaliteitCode(
            final Map<Lo3ElementEnum, String> elementen,
            final Lo3ElementEnum element,
            final Lo3CategorieEnum categorie,
            final Lo3Onderzoek onderzoek) {
        final Lo3NationaliteitCode resultaat;
        final String elementWaarde = elementen.remove(element);
        final String waarde = !isElementGevuld(elementWaarde) ? null : elementWaarde;
        final Lo3Onderzoek elementOnderzoek = bepaalOnderzoek(onderzoek, categorie, element);

        if (waarde != null || elementOnderzoek != null) {
            resultaat = new Lo3NationaliteitCode(waarde, elementOnderzoek);
        } else {
            resultaat = null;
        }
        return resultaat;
    }

    /**
     * Parse een Lo3RedenNederlandschapCode.
     * @param elementen De Map van waarden.
     * @param element Het element dat uit de elementen gelezen en verwijderd moet worden.
     * @param categorie De categorie waar dit element bij hoort.
     * @param onderzoek Het onderzoek op de huidige categorie, of null.
     * @return Lo3RedenNederlandschapCode of null als de waarde null is
     */
    static Lo3RedenNederlandschapCode parseLo3RedenNederlandschapCode(
            final Map<Lo3ElementEnum, String> elementen,
            final Lo3ElementEnum element,
            final Lo3CategorieEnum categorie,
            final Lo3Onderzoek onderzoek) {
        final Lo3RedenNederlandschapCode resultaat;
        final String elementWaarde = elementen.remove(element);
        final String waarde = !isElementGevuld(elementWaarde) ? null : elementWaarde;
        final Lo3Onderzoek elementOnderzoek = bepaalOnderzoek(onderzoek, categorie, element);

        if (waarde != null || elementOnderzoek != null) {
            resultaat = new Lo3RedenNederlandschapCode(waarde, elementOnderzoek);
        } else {
            resultaat = null;
        }
        return resultaat;
    }

    /**
     * Parse een Lo3AanduidingBijzonderNederlandschap.
     * @param elementen De Map van waarden.
     * @param element Het element dat uit de elementen gelezen en verwijderd moet worden.
     * @param categorie De categorie waar dit element bij hoort.
     * @param onderzoek Het onderzoek op de huidige categorie, of null.
     * @return Lo3AanduidingBijzonderNederlandschap of null als de waarde null is
     */
    static Lo3AanduidingBijzonderNederlandschap parseLo3AanduidingBijzonderNederlandschap(
            final Map<Lo3ElementEnum, String> elementen,
            final Lo3ElementEnum element,
            final Lo3CategorieEnum categorie,
            final Lo3Onderzoek onderzoek) {
        final Lo3AanduidingBijzonderNederlandschap resultaat;
        final String elementWaarde = elementen.remove(element);
        final String waarde = !isElementGevuld(elementWaarde) ? null : elementWaarde;
        final Lo3Onderzoek elementOnderzoek = bepaalOnderzoek(onderzoek, categorie, element);

        if (waarde != null || elementOnderzoek != null) {
            resultaat = new Lo3AanduidingBijzonderNederlandschap(waarde, elementOnderzoek);
        } else {
            resultaat = null;
        }
        return resultaat;
    }

    /**
     * Parse een Lo3RedenOpschortingBijhoudingCode.
     * @param elementen De Map van waarden.
     * @param element Het element dat uit de elementen gelezen en verwijderd moet worden.
     * @param categorie De categorie waar dit element bij hoort.
     * @param onderzoek Het onderzoek op de huidige categorie, of null.
     * @return Lo3RedenOpschortingBijhoudingCode of null als de waarde null is
     */
    static Lo3RedenOpschortingBijhoudingCode parseLo3RedenOpschortingBijhoudingCode(
            final Map<Lo3ElementEnum, String> elementen,
            final Lo3ElementEnum element,
            final Lo3CategorieEnum categorie,
            final Lo3Onderzoek onderzoek) {
        final Lo3RedenOpschortingBijhoudingCode resultaat;
        final String elementWaarde = elementen.remove(element);
        final String waarde = !isElementGevuld(elementWaarde) ? null : elementWaarde;
        final Lo3Onderzoek elementOnderzoek = bepaalOnderzoek(onderzoek, categorie, element);

        if (waarde != null || elementOnderzoek != null) {
            resultaat = new Lo3RedenOpschortingBijhoudingCode(waarde, elementOnderzoek);
        } else {
            resultaat = null;
        }
        return resultaat;
    }

    /**
     * Parse een Lo3IndicatieGeheimCode.
     * @param elementen De Map van waarden.
     * @param element Het element dat uit de elementen gelezen en verwijderd moet worden.
     * @param categorie De categorie waar dit element bij hoort.
     * @param onderzoek Het onderzoek op de huidige categorie, of null.
     * @return Lo3IndicatieGeheimCode of null als de waarde null is
     */
    static Lo3IndicatieGeheimCode parseLo3IndicatieGeheimCode(
            final Map<Lo3ElementEnum, String> elementen,
            final Lo3ElementEnum element,
            final Lo3CategorieEnum categorie,
            final Lo3Onderzoek onderzoek) {
        final Lo3IndicatieGeheimCode resultaat;
        final String elementWaarde = elementen.remove(element);
        final String waarde = !isElementGevuld(elementWaarde) ? null : elementWaarde;
        final Lo3Onderzoek elementOnderzoek = bepaalOnderzoek(onderzoek, categorie, element);

        if (waarde != null || elementOnderzoek != null) {
            resultaat = new Lo3IndicatieGeheimCode(waarde, elementOnderzoek);
        } else {
            resultaat = null;
        }
        return resultaat;
    }

    /**
     * Parse een Lo3Datumtijdstempel.
     * @param elementen De Map van waarden.
     * @param element Het element dat uit de elementen gelezen en verwijderd moet worden.
     * @return Lo3Datumtijdstempel of null als de waarde null is
     */
    public static Lo3Datumtijdstempel parseLo3Datumtijdstempel(
            final Map<Lo3ElementEnum, String> elementen, final Lo3ElementEnum element) {
        final Lo3Datumtijdstempel resultaat;
        final String elementWaarde = elementen.remove(element);
        final String waarde = !isElementGevuld(elementWaarde) ? null : elementWaarde;
        final Lo3Onderzoek elementOnderzoek = bepaalOnderzoek(null, null, element);

        if (waarde != null || elementOnderzoek != null) {
            resultaat = new Lo3Datumtijdstempel(waarde, elementOnderzoek);
        } else {
            resultaat = null;
        }
        return resultaat;
    }

    /**
     * Parse een Lo3IndicatiePKVolledigGeconverteerdCode.
     * @param elementen De Map van waarden.
     * @param element Het element dat uit de elementen gelezen en verwijderd moet worden.
     * @param categorie De categorie waar dit element bij hoort.
     * @param onderzoek Het onderzoek op de huidige categorie, of null.
     * @return Lo3IndicatiePKVolledigGeconverteerdCode of null als de waarde null is
     */
    static Lo3IndicatiePKVolledigGeconverteerdCode parseLo3IndicatiePKVolledigGeconverteerdCode(
            final Map<Lo3ElementEnum, String> elementen,
            final Lo3ElementEnum element,
            final Lo3CategorieEnum categorie,
            final Lo3Onderzoek onderzoek) {
        final Lo3IndicatiePKVolledigGeconverteerdCode resultaat;
        final String elementWaarde = elementen.remove(element);
        final String waarde = !isElementGevuld(elementWaarde) ? null : elementWaarde;
        final Lo3Onderzoek elementOnderzoek = bepaalOnderzoek(onderzoek, categorie, element);

        if (waarde != null || elementOnderzoek != null) {
            resultaat = new Lo3IndicatiePKVolledigGeconverteerdCode(waarde, elementOnderzoek);
        } else {
            resultaat = null;
        }
        return resultaat;
    }

    /**
     * Parse een Lo3RedenOntbindingHuwelijkOfGpCode.
     * @param elementen De Map van waarden.
     * @param element Het element dat uit de elementen gelezen en verwijderd moet worden.
     * @param categorie De categorie waar dit element bij hoort.
     * @param onderzoek Het onderzoek op de huidige categorie, of null.
     * @return Lo3RedenOntbindingHuwelijkOfGpCode of null als de waarde null is
     * @throws IllegalArgumentException als de waarde niet voldoet aan de inhoudelijke eisen van
     * {@link Lo3RedenOntbindingHuwelijkOfGpCode#Lo3RedenOntbindingHuwelijkOfGpCode}
     */
    static Lo3RedenOntbindingHuwelijkOfGpCode parseLo3RedenOntbindingHuwelijkOfGpCode(
            final Map<Lo3ElementEnum, String> elementen,
            final Lo3ElementEnum element,
            final Lo3CategorieEnum categorie,
            final Lo3Onderzoek onderzoek) {
        final Lo3RedenOntbindingHuwelijkOfGpCode resultaat;
        final String elementWaarde = elementen.remove(element);
        final String waarde = !isElementGevuld(elementWaarde) ? null : elementWaarde;
        final Lo3Onderzoek elementOnderzoek = bepaalOnderzoek(onderzoek, categorie, element);

        if (waarde != null || elementOnderzoek != null) {
            resultaat = new Lo3RedenOntbindingHuwelijkOfGpCode(waarde, elementOnderzoek);
        } else {
            resultaat = null;
        }
        return resultaat;
    }

    /**
     * Parse een Lo3SoortVerbintenis.
     * @param elementen De Map van waarden.
     * @param element Het element dat uit de elementen gelezen en verwijderd moet worden.
     * @param categorie De categorie waar dit element bij hoort.
     * @param onderzoek Het onderzoek op de huidige categorie, of null.
     * @return Lo3SoortVerbintenis of null als de waarde null is
     * @throws IllegalArgumentException als de waarde niet voorkomt in {@link Lo3SoortVerbintenis}
     */
    static Lo3SoortVerbintenis parseLo3SoortVerbintenis(
            final Map<Lo3ElementEnum, String> elementen,
            final Lo3ElementEnum element,
            final Lo3CategorieEnum categorie,
            final Lo3Onderzoek onderzoek) {
        final Lo3SoortVerbintenis resultaat;
        final String elementWaarde = elementen.remove(element);
        final String waarde = !isElementGevuld(elementWaarde) ? null : elementWaarde;
        final Lo3Onderzoek elementOnderzoek = bepaalOnderzoek(onderzoek, categorie, element);

        if (waarde != null || elementOnderzoek != null) {
            resultaat = new Lo3SoortVerbintenis(waarde, elementOnderzoek);
        } else {
            resultaat = null;
        }
        return resultaat;
    }

    /**
     * Parse een Lo3AanduidingVerblijfstitelCode.
     * @param elementen De Map van waarden.
     * @param element Het element dat uit de elementen gelezen en verwijderd moet worden.
     * @param categorie De categorie waar dit element bij hoort.
     * @param onderzoek Het onderzoek op de huidige categorie, of null.
     * @return Lo3AanduidingVerblijfstitelCode of null als de waarde null is
     */
    static Lo3AanduidingVerblijfstitelCode parseLo3AanduidingVerblijfstitelCode(
            final Map<Lo3ElementEnum, String> elementen,
            final Lo3ElementEnum element,
            final Lo3CategorieEnum categorie,
            final Lo3Onderzoek onderzoek) {
        final Lo3AanduidingVerblijfstitelCode resultaat;
        final String elementWaarde = elementen.remove(element);
        final String waarde = !isElementGevuld(elementWaarde) ? null : elementWaarde;
        final Lo3Onderzoek elementOnderzoek = bepaalOnderzoek(onderzoek, categorie, element);

        if (waarde != null || elementOnderzoek != null) {
            resultaat = new Lo3AanduidingVerblijfstitelCode(waarde, elementOnderzoek);
        } else {
            resultaat = null;
        }
        return resultaat;
    }

    /**
     * Parse een Lo3IndicatieGezagMinderjarige.
     * @param elementen De Map van waarden.
     * @param element Het element dat uit de elementen gelezen en verwijderd moet worden.
     * @param categorie De categorie waar dit element bij hoort.
     * @param onderzoek Het onderzoek op de huidige categorie, of null.
     * @return Lo3IndicatieGezagMinderjarige of null als de waarde null is
     */
    static Lo3IndicatieGezagMinderjarige parseLo3IndicatieGezagMinderjarige(
            final Map<Lo3ElementEnum, String> elementen,
            final Lo3ElementEnum element,
            final Lo3CategorieEnum categorie,
            final Lo3Onderzoek onderzoek) {
        final Lo3IndicatieGezagMinderjarige resultaat;
        final String elementWaarde = elementen.remove(element);
        final String waarde = !isElementGevuld(elementWaarde) ? null : elementWaarde;
        final Lo3Onderzoek elementOnderzoek = bepaalOnderzoek(onderzoek, categorie, element);

        if (waarde != null || elementOnderzoek != null) {
            resultaat = new Lo3IndicatieGezagMinderjarige(waarde, elementOnderzoek);
        } else {
            resultaat = null;
        }
        return resultaat;
    }

    /**
     * Parse een Lo3IndicatieCurateleregister.
     * @param elementen De Map van waarden.
     * @param element Het element dat uit de elementen gelezen en verwijderd moet worden.
     * @param categorie De categorie waar dit element bij hoort.
     * @param onderzoek Het onderzoek op de huidige categorie, of null.
     * @return Lo3IndicatieCurateleregister of null als de waarde null is
     */
    static Lo3IndicatieCurateleregister parseLo3IndicatieCurateleregister(
            final Map<Lo3ElementEnum, String> elementen,
            final Lo3ElementEnum element,
            final Lo3CategorieEnum categorie,
            final Lo3Onderzoek onderzoek) {
        final Lo3IndicatieCurateleregister resultaat;
        final String elementWaarde = elementen.remove(element);
        final String waarde = !isElementGevuld(elementWaarde) ? null : elementWaarde;
        final Lo3Onderzoek elementOnderzoek = bepaalOnderzoek(onderzoek, categorie, element);

        if (waarde != null || elementOnderzoek != null) {
            resultaat = new Lo3IndicatieCurateleregister(waarde, elementOnderzoek);
        } else {
            resultaat = null;
        }
        return resultaat;
    }

    /**
     * Parse een Lo3SoortNederlandsReisdocument.
     * @param elementen De Map van waarden.
     * @param element Het element dat uit de elementen gelezen en verwijderd moet worden.
     * @param categorie De categorie waar dit element bij hoort.
     * @param onderzoek Het onderzoek op de huidige categorie, of null.
     * @return Lo3SoortNederlandsReisdocument of null als de waarde null is
     */
    static Lo3SoortNederlandsReisdocument parseLo3SoortNederlandsReisdocument(
            final Map<Lo3ElementEnum, String> elementen,
            final Lo3ElementEnum element,
            final Lo3CategorieEnum categorie,
            final Lo3Onderzoek onderzoek) {
        final Lo3SoortNederlandsReisdocument resultaat;
        final String elementWaarde = elementen.remove(element);
        final String waarde = !isElementGevuld(elementWaarde) ? null : elementWaarde;
        final Lo3Onderzoek elementOnderzoek = bepaalOnderzoek(onderzoek, categorie, element);

        if (waarde != null || elementOnderzoek != null) {
            resultaat = new Lo3SoortNederlandsReisdocument(waarde, elementOnderzoek);
        } else {
            resultaat = null;
        }
        return resultaat;
    }

    /**
     * Parse een Lo3AutoriteitVanAfgifteNederlandsReisdocument.
     * @param elementen De Map van waarden.
     * @param element Het element dat uit de elementen gelezen en verwijderd moet worden.
     * @param categorie De categorie waar dit element bij hoort.
     * @param onderzoek Het onderzoek op de huidige categorie, of null.
     * @return Lo3AutoriteitVanAfgifteNederlandsReisdocument of null als de waarde null is
     */
    static Lo3AutoriteitVanAfgifteNederlandsReisdocument parseLo3AutoriteitVanAfgifteNederlandsReisdocument(
            final Map<Lo3ElementEnum, String> elementen,
            final Lo3ElementEnum element,
            final Lo3CategorieEnum categorie,
            final Lo3Onderzoek onderzoek) {
        final Lo3AutoriteitVanAfgifteNederlandsReisdocument resultaat;
        final String elementWaarde = elementen.remove(element);
        final String waarde = !isElementGevuld(elementWaarde) ? null : elementWaarde;
        final Lo3Onderzoek elementOnderzoek = bepaalOnderzoek(onderzoek, categorie, element);

        if (waarde != null || elementOnderzoek != null) {
            resultaat = new Lo3AutoriteitVanAfgifteNederlandsReisdocument(waarde, elementOnderzoek);
        } else {
            resultaat = null;
        }
        return resultaat;
    }

    /**
     * Parse een Lo3AanduidingInhoudingVermissingNederlandsReisdocument.
     * @param elementen De Map van waarden.
     * @param element Het element dat uit de elementen gelezen en verwijderd moet worden.
     * @param categorie De categorie waar dit element bij hoort.
     * @param onderzoek Het onderzoek op de huidige categorie, of null.
     * @return Lo3AanduidingInhoudingVermissingNederlandsReisdocument of null als de waarde null is
     */
    static Lo3AanduidingInhoudingVermissingNederlandsReisdocument parseLo3AanduidingInhoudingVermissingNederlandsReisdocument(
            final Map<Lo3ElementEnum, String> elementen,
            final Lo3ElementEnum element,
            final Lo3CategorieEnum categorie,
            final Lo3Onderzoek onderzoek) {
        final Lo3AanduidingInhoudingVermissingNederlandsReisdocument resultaat;
        final String elementWaarde = elementen.remove(element);
        final String waarde = !isElementGevuld(elementWaarde) ? null : elementWaarde;
        final Lo3Onderzoek elementOnderzoek = bepaalOnderzoek(onderzoek, categorie, element);

        if (waarde != null || elementOnderzoek != null) {
            resultaat = new Lo3AanduidingInhoudingVermissingNederlandsReisdocument(waarde, elementOnderzoek);
        } else {
            resultaat = null;
        }
        return resultaat;
    }

    /**
     * Parse een Lo3Signalering.
     * @param elementen De Map van waarden.
     * @param element Het element dat uit de elementen gelezen en verwijderd moet worden.
     * @param categorie De categorie waar dit element bij hoort.
     * @param onderzoek Het onderzoek op de huidige categorie, of null.
     * @return Lo3Signalering of null als de waarde null is
     */
    static Lo3Signalering parseLo3Signalering(
            final Map<Lo3ElementEnum, String> elementen,
            final Lo3ElementEnum element,
            final Lo3CategorieEnum categorie,
            final Lo3Onderzoek onderzoek) {
        final Lo3Signalering resultaat;
        final String elementWaarde = elementen.remove(element);
        final String waarde = !isElementGevuld(elementWaarde) ? null : elementWaarde;
        final Lo3Onderzoek elementOnderzoek = bepaalOnderzoek(onderzoek, categorie, element);

        if (waarde != null || elementOnderzoek != null) {
            resultaat = new Lo3Signalering(waarde, elementOnderzoek);
        } else {
            resultaat = null;
        }
        return resultaat;
    }

    /**
     * Controleert of Lo3AanduidingBezitBuitenlandsReisdocument is gevuld.
     * @param elementen De Map van waarden.
     * @param element Het element dat uit de elementen gelezen en verwijderd moet worden.
     * @return Lo3AanduidingBezitBuitenlandsReisdocument of null als de waarde null is
     */
    static boolean bevatLo3AanduidingBezitBuitenlandsReisdocument(final Map<Lo3ElementEnum, String> elementen, final Lo3ElementEnum element) {
        return isElementGevuld(elementen.get(element));
    }

    /**
     * Parse een Lo3AanduidingEuropeesKiesrecht.
     * @param elementen De Map van waarden.
     * @param element Het element dat uit de elementen gelezen en verwijderd moet worden.
     * @param categorie De categorie waar dit element bij hoort.
     * @param onderzoek Het onderzoek op de huidige categorie, of null.
     * @return Lo3AanduidingEuropeesKiesrecht of null als de waarde null is
     */
    static Lo3AanduidingEuropeesKiesrecht parseLo3AanduidingEuropeesKiesrecht(
            final Map<Lo3ElementEnum, String> elementen,
            final Lo3ElementEnum element,
            final Lo3CategorieEnum categorie,
            final Lo3Onderzoek onderzoek) {
        final Lo3AanduidingEuropeesKiesrecht resultaat;
        final String elementWaarde = elementen.remove(element);
        final String waarde = !isElementGevuld(elementWaarde) ? null : elementWaarde;
        final Lo3Onderzoek elementOnderzoek = bepaalOnderzoek(onderzoek, categorie, element);

        if (waarde != null || elementOnderzoek != null) {
            resultaat = new Lo3AanduidingEuropeesKiesrecht(waarde, elementOnderzoek);
        } else {
            resultaat = null;
        }
        return resultaat;
    }

    /**
     * Parse een Lo3AanduidingUitgeslotenKiesrecht.
     * @param elementen De Map van waarden.
     * @param element Het element dat uit de elementen gelezen en verwijderd moet worden.
     * @param categorie De categorie waar dit element bij hoort.
     * @param onderzoek Het onderzoek op de huidige categorie, of null.
     * @return Lo3AanduidingUitgeslotenKiesrecht of null als de waarde null is
     */
    static Lo3AanduidingUitgeslotenKiesrecht parseLo3AanduidingUitgeslotenKiesrecht(
            final Map<Lo3ElementEnum, String> elementen,
            final Lo3ElementEnum element,
            final Lo3CategorieEnum categorie,
            final Lo3Onderzoek onderzoek) {
        final Lo3AanduidingUitgeslotenKiesrecht resultaat;
        final String elementWaarde = elementen.remove(element);
        final String waarde = !isElementGevuld(elementWaarde) ? null : elementWaarde;
        final Lo3Onderzoek elementOnderzoek = bepaalOnderzoek(onderzoek, categorie, element);

        if (waarde != null || elementOnderzoek != null) {
            resultaat = new Lo3AanduidingUitgeslotenKiesrecht(waarde, elementOnderzoek);
        } else {
            resultaat = null;
        }
        return resultaat;
    }

    /**
     * Parse een Lo3Huisnummer.
     * @param elementen De Map van waarden.
     * @param element Het element dat uit de elementen gelezen en verwijderd moet worden.
     * @param categorie De categorie waar dit element bij hoort.
     * @param onderzoek Het onderzoek op de huidige categorie, of null.
     * @return Lo3Huisnummer of null als de waarde null is
     */
    static Lo3Huisnummer parseLo3Huisnummer(
            final Map<Lo3ElementEnum, String> elementen,
            final Lo3ElementEnum element,
            final Lo3CategorieEnum categorie,
            final Lo3Onderzoek onderzoek) {
        final Lo3Huisnummer resultaat;
        final String elementWaarde = elementen.remove(element);
        final String waarde = !isElementGevuld(elementWaarde) ? null : elementWaarde;
        final Lo3Onderzoek elementOnderzoek = bepaalOnderzoek(onderzoek, categorie, element);

        if (waarde != null || elementOnderzoek != null) {
            resultaat = new Lo3Huisnummer(waarde, elementOnderzoek);
        } else {
            resultaat = null;
        }
        return resultaat;
    }

    /**
     * Parse een Lo3AanduidingHuisnummer.
     * @param elementen De Map van waarden.
     * @param element Het element dat uit de elementen gelezen en verwijderd moet worden.
     * @param categorie De categorie waar dit element bij hoort.
     * @param onderzoek Het onderzoek op de huidige categorie, of null.
     * @return Lo3AanduidingHuisnummer of null als de waarde null is
     */
    static Lo3AanduidingHuisnummer parseLo3AanduidingHuisnummer(
            final Map<Lo3ElementEnum, String> elementen,
            final Lo3ElementEnum element,
            final Lo3CategorieEnum categorie,
            final Lo3Onderzoek onderzoek) {
        final Lo3AanduidingHuisnummer resultaat;
        final String elementWaarde = elementen.remove(element);
        final String waarde = !isElementGevuld(elementWaarde) ? null : elementWaarde;
        final Lo3Onderzoek elementOnderzoek = bepaalOnderzoek(onderzoek, categorie, element);

        if (waarde != null || elementOnderzoek != null) {
            resultaat = new Lo3AanduidingHuisnummer(waarde, elementOnderzoek);
        } else {
            resultaat = null;
        }
        return resultaat;
    }

    /**
     * Parse een Lo3FunctieAdres.
     * @param elementen De Map van waarden.
     * @param element Het element dat uit de elementen gelezen en verwijderd moet worden.
     * @param categorie De categorie waar dit element bij hoort.
     * @param onderzoek Het onderzoek op de huidige categorie, of null.
     * @return Lo3FunctieAdres of null als de waarde null is
     * @throws IllegalArgumentException als de waarde niet voorkomt in {@link Lo3FunctieAdres}
     */
    static Lo3FunctieAdres parseLo3FunctieAdres(
            final Map<Lo3ElementEnum, String> elementen,
            final Lo3ElementEnum element,
            final Lo3CategorieEnum categorie,
            final Lo3Onderzoek onderzoek) {
        final Lo3FunctieAdres resultaat;
        final String elementWaarde = elementen.remove(element);
        final String waarde = !isElementGevuld(elementWaarde) ? null : elementWaarde;
        final Lo3Onderzoek elementOnderzoek = bepaalOnderzoek(onderzoek, categorie, element);

        if (waarde != null || elementOnderzoek != null) {
            resultaat = new Lo3FunctieAdres(waarde, elementOnderzoek);
        } else {
            resultaat = null;
        }
        return resultaat;
    }

    /**
     * Parse een Lo3AangifteAdreshouding.
     * @param elementen De Map van waarden.
     * @param element Het element dat uit de elementen gelezen en verwijderd moet worden.
     * @param categorie De categorie waar dit element bij hoort.
     * @param onderzoek Het onderzoek op de huidige categorie, of null.
     * @return Lo3AangifteAdreshouding of null als de waarde null is
     * @throws IllegalArgumentException als de waarde niet voorkomt in {@link Lo3AangifteAdreshouding}
     */
    static Lo3AangifteAdreshouding parseLo3AangifteAdreshouding(
            final Map<Lo3ElementEnum, String> elementen,
            final Lo3ElementEnum element,
            final Lo3CategorieEnum categorie,
            final Lo3Onderzoek onderzoek) {
        final Lo3AangifteAdreshouding resultaat;
        final String elementWaarde = elementen.remove(element);
        final String waarde = !isElementGevuld(elementWaarde) ? null : elementWaarde;
        final Lo3Onderzoek elementOnderzoek = bepaalOnderzoek(onderzoek, categorie, element);

        if (waarde != null || elementOnderzoek != null) {
            resultaat = new Lo3AangifteAdreshouding(waarde, elementOnderzoek);
        } else {
            resultaat = null;
        }
        return resultaat;
    }

    /**
     * Parse een Lo3IndicatieDocument.
     * @param elementen De Map van waarden.
     * @param element Het element dat uit de elementen gelezen en verwijderd moet worden.
     * @param categorie De categorie waar dit element bij hoort.
     * @param onderzoek Het onderzoek op de huidige categorie, of null.
     * @return Lo3IndicatieDocument of null als de waarde null is
     * @throws IllegalArgumentException als de waarde niet voorkomt in {@link Lo3IndicatieDocument}
     * @throws NumberFormatException als de waarde niet alleen cijfers bevat
     */
    static Lo3IndicatieDocument parseLo3IndicatieDocument(
            final Map<Lo3ElementEnum, String> elementen,
            final Lo3ElementEnum element,
            final Lo3CategorieEnum categorie,
            final Lo3Onderzoek onderzoek) {
        final Lo3IndicatieDocument resultaat;
        final String elementWaarde = elementen.remove(element);
        final String waarde = !isElementGevuld(elementWaarde) ? null : elementWaarde;
        final Lo3Onderzoek elementOnderzoek = bepaalOnderzoek(onderzoek, categorie, element);

        if (waarde != null || elementOnderzoek != null) {
            resultaat = new Lo3IndicatieDocument(waarde, elementOnderzoek);
        } else {
            resultaat = null;
        }
        return resultaat;
    }

    private static boolean isElementGevuld(final String elementWaarde) {
        return elementWaarde != null && !elementWaarde.isEmpty();
    }
}

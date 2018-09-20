/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.parser;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3CategorieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieOnjuist;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RNIDeelnemerCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Interface voor LO3 categorie parsers.
 * 
 * 
 * 
 * @param <T>
 *            de LO3 categorie die geparsed wordt.
 */
public abstract class AbstractLo3CategorieParser<T extends Lo3CategorieInhoud> {

    private static final Set<Lo3CategorieEnum> CATS_ZONDER_GROEP_81 = new HashSet<>(Arrays.asList(
        Lo3CategorieEnum.CATEGORIE_04,
        Lo3CategorieEnum.CATEGORIE_07,
        Lo3CategorieEnum.CATEGORIE_08,
        Lo3CategorieEnum.CATEGORIE_10,
        Lo3CategorieEnum.CATEGORIE_11,
        Lo3CategorieEnum.CATEGORIE_12,
        Lo3CategorieEnum.CATEGORIE_13,
        Lo3CategorieEnum.CATEGORIE_21));
    private static final Set<Lo3CategorieEnum> CATS_ZONDER_GROEP_82 = new HashSet<>(Arrays.asList(
        Lo3CategorieEnum.CATEGORIE_07,
        Lo3CategorieEnum.CATEGORIE_08,
        Lo3CategorieEnum.CATEGORIE_10,
        Lo3CategorieEnum.CATEGORIE_21));
    private static final Set<Lo3CategorieEnum> CATS_ZONDER_GROEP_84 = new HashSet<>(Arrays.asList(
        Lo3CategorieEnum.CATEGORIE_07,
        Lo3CategorieEnum.CATEGORIE_12,
        Lo3CategorieEnum.CATEGORIE_13));
    private static final Set<Lo3CategorieEnum> CATS_ZONDER_GROEP_85 = new HashSet<>(Arrays.asList(
        Lo3CategorieEnum.CATEGORIE_07,
        Lo3CategorieEnum.CATEGORIE_13));
    private static final Set<Lo3CategorieEnum> CATS_ZONDER_GROEP_86 = new HashSet<>(Arrays.asList(
        Lo3CategorieEnum.CATEGORIE_07,
        Lo3CategorieEnum.CATEGORIE_13));
    private static final Set<Lo3CategorieEnum> CATS_ZONDER_GROEP_88 = new HashSet<>(Arrays.asList(
        Lo3CategorieEnum.CATEGORIE_02,
        Lo3CategorieEnum.CATEGORIE_03,
        Lo3CategorieEnum.CATEGORIE_05,
        Lo3CategorieEnum.CATEGORIE_09,
        Lo3CategorieEnum.CATEGORIE_10,
        Lo3CategorieEnum.CATEGORIE_11,
        Lo3CategorieEnum.CATEGORIE_12,
        Lo3CategorieEnum.CATEGORIE_13,
        Lo3CategorieEnum.CATEGORIE_21));

    /**
     * Parse een categorie.
     * 
     * @param categorieen
     *            de lijst met categorie waarden uit de kolom van de persoonslijst
     * @return een lo3 stapel
     */
    public abstract Lo3Stapel<T> parse(List<Lo3CategorieWaarde> categorieen);

    /**
     * Parsed de LO3 Onderzoek elementen voor het onderzoek en verwijderd deze uit de meegegeven lijst van elementen.
     * Onderzoek kan zelf nooit in onderzoek staan.
     * 
     * @param elementen
     *            de elementen
     * @param lo3Herkomst
     *            de herkomst
     * @return het LO3 onderzoek object
     */
    protected final Lo3Onderzoek parseLo3Onderzoek(final Map<Lo3ElementEnum, String> elementen, final Lo3Herkomst lo3Herkomst) {
        final Lo3Integer aanduidingGegevensInOnderzoek = Parser.parseLo3Integer(elementen, Lo3ElementEnum.ELEMENT_8310, null, null);
        final Lo3Datum datumIngangOnderzoek = Parser.parseLo3Datum(elementen, Lo3ElementEnum.ELEMENT_8320, null, null);
        final Lo3Datum datumEindeOnderzoek = Parser.parseLo3Datum(elementen, Lo3ElementEnum.ELEMENT_8330, null, null);

        return Lo3Onderzoek.build(aanduidingGegevensInOnderzoek, datumIngangOnderzoek, datumEindeOnderzoek, lo3Herkomst);
    }

    /**
     * Parsed de LO3 Documentatie elementen en de LO3 RNI Deelnemer elementen voor de document/akte en verwijderd deze
     * uit de meegegeven lijst van elementen.
     * 
     * @param elementen
     *            de elementen
     * @param categorie
     *            De categorie waar dit element bij hoort.
     * @param lo3Onderzoek
     *            onderzoek
     * @return het LO3 documentatie object
     */
    protected final Lo3Documentatie parseLo3Documentatie(
        final Map<Lo3ElementEnum, String> elementen,
        final Lo3CategorieEnum categorie,
        final Lo3Onderzoek lo3Onderzoek)
    {

        Lo3GemeenteCode registergemeenteAkte = null;
        Lo3String aktenummer = null;
        Lo3GemeenteCode gemeenteDocument = null;
        Lo3Datum datumDocument = null;
        Lo3String beschrijvingDocument = null;

        if (!CATS_ZONDER_GROEP_81.contains(categorie)) {
            registergemeenteAkte = Parser.parseLo3GemeenteCode(elementen, Lo3ElementEnum.ELEMENT_8110, categorie, lo3Onderzoek);
            aktenummer = Parser.parseLo3String(elementen, Lo3ElementEnum.ELEMENT_8120, categorie, lo3Onderzoek);
        }
        if (!CATS_ZONDER_GROEP_82.contains(categorie)) {
            gemeenteDocument = Parser.parseLo3GemeenteCode(elementen, Lo3ElementEnum.ELEMENT_8210, categorie, lo3Onderzoek);
            datumDocument = Parser.parseLo3Datum(elementen, Lo3ElementEnum.ELEMENT_8220, categorie, lo3Onderzoek);
            beschrijvingDocument = Parser.parseLo3String(elementen, Lo3ElementEnum.ELEMENT_8230, categorie, lo3Onderzoek);
        }

        final Pair<Lo3RNIDeelnemerCode, Lo3String> rniDeelnemer = parseRNIDeelnemer(elementen, categorie, lo3Onderzoek);
        return Lo3Documentatie.build(
            registergemeenteAkte,
            aktenummer,
            gemeenteDocument,
            datumDocument,
            beschrijvingDocument,
            rniDeelnemer.getLeft(),
            rniDeelnemer.getRight());
    }

    /**
     * Parsed de LO3 RNI Deelnemer elementen voor de document/akte en verwijderd deze uit de meegegeven lijst van
     * elementen.
     * 
     * @param elementen
     *            de elementen
     * @param categorie
     *            De categorie waar dit element bij hoort.
     * @param lo3Onderzoek
     *            onderzoek
     * @return het LO3 documentatie object
     */
    protected final Lo3Documentatie parseLo3RNIDeelnemer(
        final Map<Lo3ElementEnum, String> elementen,
        final Lo3CategorieEnum categorie,
        final Lo3Onderzoek lo3Onderzoek)
    {
        final Pair<Lo3RNIDeelnemerCode, Lo3String> rniDeelnemer = parseRNIDeelnemer(elementen, categorie, lo3Onderzoek);
        return Lo3Documentatie.build(null, null, null, null, null, rniDeelnemer.getLeft(), rniDeelnemer.getRight());
    }

    private Pair<Lo3RNIDeelnemerCode, Lo3String> parseRNIDeelnemer(
        final Map<Lo3ElementEnum, String> elementen,
        final Lo3CategorieEnum categorie,
        final Lo3Onderzoek lo3Onderzoek)
    {
        Lo3RNIDeelnemerCode rniDeelnemerCode = null;
        Lo3String omschrijvingVerdrag = null;
        if (!CATS_ZONDER_GROEP_88.contains(categorie)) {
            rniDeelnemerCode = Parser.parseLo3RniDeelnemerCode(elementen, Lo3ElementEnum.ELEMENT_8810, categorie, lo3Onderzoek);
            omschrijvingVerdrag = Parser.parseLo3String(elementen, Lo3ElementEnum.ELEMENT_8820, categorie, lo3Onderzoek);
        }
        return new ImmutablePair<>(rniDeelnemerCode, omschrijvingVerdrag);
    }

    /**
     * Parsed de LO3 historie elementen voor de historie en verwijderd deze uit de meegegeven lijst van elementen.
     * 
     * @param elementen
     *            de elementen
     * @param categorie
     *            De categorie waar dit element bij hoort.
     * @param lo3Onderzoek
     *            onderzoek
     * @return het LO3 historie object
     */
    protected final Lo3Historie parseLo3Historie(
        final Map<Lo3ElementEnum, String> elementen,
        final Lo3CategorieEnum categorie,
        final Lo3Onderzoek lo3Onderzoek)
    {
        final Lo3IndicatieOnjuist indicatieOnjuist =
                CATS_ZONDER_GROEP_84.contains(categorie) ? null : Parser.parseLo3IndicatieOnjuist(
                    elementen,
                    Lo3ElementEnum.ELEMENT_8410,
                    categorie,
                    lo3Onderzoek);
        final Lo3Datum ingangsdatumGeldigheid =
                CATS_ZONDER_GROEP_85.contains(categorie) ? null : Parser.parseLo3Datum(elementen, Lo3ElementEnum.ELEMENT_8510, categorie, lo3Onderzoek);
        final Lo3Datum datumVanOpneming =
                CATS_ZONDER_GROEP_86.contains(categorie) ? null : Parser.parseLo3Datum(elementen, Lo3ElementEnum.ELEMENT_8610, categorie, lo3Onderzoek);
        return Lo3Historie.build(indicatieOnjuist, ingangsdatumGeldigheid, datumVanOpneming);
    }
}

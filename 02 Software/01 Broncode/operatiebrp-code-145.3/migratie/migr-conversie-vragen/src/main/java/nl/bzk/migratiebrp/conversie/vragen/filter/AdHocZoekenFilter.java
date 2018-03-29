/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.vragen.filter;

import com.google.common.collect.ImmutableMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3GroepEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Filter voor adhoc zoeken persoon voor uitfilteren van vragen met
 * niet toegestane rubrieken.
 *
 * Niet toegestane categorieën:
 * - 02 Ouder1
 * - 03 Ouder2
 * - 05 Huwelijk
 * - 09 Kind
 * - 11 Gezagsverhouding
 * - 50+ historische categorieën
 *
 * Niet toegestane groepen:
 * - 81 Akte
 * - 82 Document
 */
public final class AdHocZoekenFilter {
    /**
     * Niet toegestane categorieën (categorie 02, categorie 03, categorie 05, categorie 09, categorie 11).
     */
    private static final List<Lo3CategorieEnum> NIET_TOEGESTANE_CATEGORIEEN = Arrays.asList(
            Lo3CategorieEnum.CATEGORIE_02, Lo3CategorieEnum.CATEGORIE_03, Lo3CategorieEnum.CATEGORIE_05,
            Lo3CategorieEnum.CATEGORIE_09, Lo3CategorieEnum.CATEGORIE_11);

    /**
     * Niet toegestane groepen (groep 13, groep 14, groep 81, groep 82,groep 83,groep 84,groep 85,groep 86,groep 87,groep 88).
     */
    private static final List<Lo3GroepEnum> NIET_TOEGESTANE_GROEPEN = Arrays.asList(
            Lo3GroepEnum.GROEP13, Lo3GroepEnum.GROEP14, Lo3GroepEnum.GROEP80,
            Lo3GroepEnum.GROEP81, Lo3GroepEnum.GROEP82, Lo3GroepEnum.GROEP83,
            Lo3GroepEnum.GROEP84, Lo3GroepEnum.GROEP85, Lo3GroepEnum.GROEP86,
            Lo3GroepEnum.GROEP87, Lo3GroepEnum.GROEP88);

    /**
     * Niet toegestane rubrieken (04.64.10, 07.67.10, 07.70.10, 08.09.20).
     */
    private static final Map<Lo3CategorieEnum, List<Lo3ElementEnum>> NIET_TOEGESTANE_RUBRIEKEN = ImmutableMap.of(
            Lo3CategorieEnum.CATEGORIE_04, Arrays.asList(Lo3ElementEnum.ELEMENT_6310, Lo3ElementEnum.ELEMENT_6410),
            Lo3CategorieEnum.CATEGORIE_07, Arrays.asList(Lo3ElementEnum.ELEMENT_6710, Lo3ElementEnum.ELEMENT_7010),
            Lo3CategorieEnum.CATEGORIE_08, Arrays.asList(Lo3ElementEnum.ELEMENT_0920)
    );

    /**
     * uitzonderingscategorieën. Gebruiken indien slechts één of twee rubrieken zijn toegestaan uit een categorie (05.02.40).
     */
    private static final Map<Lo3CategorieEnum, List<Lo3ElementEnum>> WEL_TOEGESTANE_RUBRIEKEN = ImmutableMap.of(
            Lo3CategorieEnum.CATEGORIE_05, Arrays.asList(Lo3ElementEnum.ELEMENT_0240)
    );

    /**
     * Controleert de lijst van LO3 Categoriewaarden op niet toegestane rubrieken.
     * @param categorieen de lijst van alle LO3 categoriewaarden
     * @return de lijst met niet toegestane rubrieken die voorkomen in de lijst met alle LO3 categoriewaarden
     */
    public List<Integer> nietToegestaneRubrieken(final List<Lo3CategorieWaarde> categorieen) {
        return categorieen.stream()
                .map(this::flattenElementen)
                .flatMap(Collection::stream)
                .filter(this::isNietToegestaan)
                .filter(this::isNietRubriekExplicietToegestaan)
                .map(this::format)
                .sorted()
                .collect(Collectors.toList());
    }

    private boolean isNietToegestaan(final Pair<Lo3CategorieWaarde, Lo3ElementEnum> pair) {
        return isNietActueel(pair)
                || isCategorieNietToegestaan(pair)
                || isGroepNietToegestaan(pair)
                || isRubriekNietToegestaan(pair);
    }

    private boolean isCategorieNietToegestaan(final Pair<Lo3CategorieWaarde, Lo3ElementEnum> pair) {
        return NIET_TOEGESTANE_CATEGORIEEN.contains(pair.getLeft().getCategorie());
    }

    private boolean isGroepNietToegestaan(final Pair<Lo3CategorieWaarde, Lo3ElementEnum> pair) {
        return NIET_TOEGESTANE_GROEPEN.contains(pair.getRight().getGroep());
    }

    private boolean isRubriekNietToegestaan(final Pair<Lo3CategorieWaarde, Lo3ElementEnum> pair) {
        return NIET_TOEGESTANE_RUBRIEKEN.getOrDefault(pair.getLeft().getCategorie(), Collections.emptyList()).contains(pair.getRight());
    }

    private boolean isNietRubriekExplicietToegestaan(final Pair<Lo3CategorieWaarde, Lo3ElementEnum> pair) {
        return !WEL_TOEGESTANE_RUBRIEKEN.getOrDefault(pair.getLeft().getCategorie(), Collections.emptyList()).contains(pair.getRight());
    }

    private boolean isNietActueel(final Pair<Lo3CategorieWaarde, Lo3ElementEnum> pair) {
        return !pair.getLeft().getCategorie().isActueel();
    }

    private Integer format(final Pair<Lo3CategorieWaarde, Lo3ElementEnum> pair) {
        return Integer.valueOf(pair.getLeft().getCategorie().getCategorie() + pair.getRight().getElementNummer());
    }

    private List<Pair<Lo3CategorieWaarde, Lo3ElementEnum>> flattenElementen(final Lo3CategorieWaarde categorieWaarde) {
        return categorieWaarde.getElementen().entrySet().stream()
                .map(e -> Pair.of(categorieWaarde, e.getKey()))
                .collect(Collectors.toList());
    }
}

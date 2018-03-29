/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.impl;

import static nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum.CATEGORIE_01;
import static nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum.CATEGORIE_08;
import static nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum.CATEGORIE_51;
import static nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum.ELEMENT_0110;
import static nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum.ELEMENT_0120;
import static nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum.ELEMENT_0240;
import static nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum.ELEMENT_1160;
import static nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum.ELEMENT_8410;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

final class PersoonsidentificatieValidator {
    private static final ImmutableMap<Lo3CategorieEnum, List<Lo3ElementEnum>> actueleRubrieken = ImmutableMap.of(
            CATEGORIE_01, Arrays.asList(ELEMENT_0110, ELEMENT_0120, ELEMENT_0240),
            CATEGORIE_08, Collections.singletonList(ELEMENT_1160)
    );

    private static final ImmutableMap<Lo3CategorieEnum, List<Lo3ElementEnum>> historischeRubrieken = ImmutableMap.of(
            CATEGORIE_51, Arrays.asList(ELEMENT_0110, ELEMENT_0120, ELEMENT_0240)
    );

    private static final ImmutableList<Lo3ElementEnum> nietToegestaneRubrieken = ImmutableList.of(ELEMENT_8410);

    private PersoonsidentificatieValidator() {
        // private
    }

    static boolean valideerActueel(final List<Lo3CategorieWaarde> categorieen) {
        return categorieen.stream().anyMatch(PersoonsidentificatieValidator::containsActueel) &&
                categorieen.stream().noneMatch(PersoonsidentificatieValidator::containsNotAllowed);
    }

    static boolean valideerHistorisch(final List<Lo3CategorieWaarde> categorieen) {
        return categorieen.stream().anyMatch(PersoonsidentificatieValidator::containsHistorisch) &&
                categorieen.stream().noneMatch(PersoonsidentificatieValidator::containsNotAllowed);
    }

    private static boolean containsActueel(final Lo3CategorieWaarde categorie) {
        return contains(actueleRubrieken, categorie);
    }

    private static boolean containsHistorisch(final Lo3CategorieWaarde categorie) {
        return contains(historischeRubrieken, categorie);
    }

    private static boolean containsNotAllowed(final Lo3CategorieWaarde categorie) {
        return categorie.getElementen().keySet().stream().anyMatch(nietToegestaneRubrieken::contains);
    }

    private static boolean contains(final Map<Lo3CategorieEnum, List<Lo3ElementEnum>> rubrieken, final Lo3CategorieWaarde categorie) {
        return rubrieken.getOrDefault(categorie.getCategorie(), Collections.emptyList()).stream()
                .anyMatch(element -> !Strings.isNullOrEmpty(categorie.getElement(element)));
    }
}

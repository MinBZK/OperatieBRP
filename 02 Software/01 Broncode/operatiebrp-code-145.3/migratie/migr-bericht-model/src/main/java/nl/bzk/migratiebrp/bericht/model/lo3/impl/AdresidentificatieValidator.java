/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.impl;

import static nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum.CATEGORIE_08;
import static nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum.ELEMENT_1020;
import static nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum.ELEMENT_1110;
import static nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum.ELEMENT_1115;
import static nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum.ELEMENT_1120;
import static nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum.ELEMENT_1130;
import static nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum.ELEMENT_1140;
import static nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum.ELEMENT_1150;
import static nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum.ELEMENT_1160;
import static nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum.ELEMENT_1170;
import static nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum.ELEMENT_1180;
import static nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum.ELEMENT_1190;
import static nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum.ELEMENT_1210;

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

final class AdresidentificatieValidator {
    private static final Map<Lo3CategorieEnum, List<Lo3ElementEnum>> verplichteRubrieken = ImmutableMap.of(
            CATEGORIE_08, Arrays.asList(ELEMENT_1110, ELEMENT_1115, ELEMENT_1160, ELEMENT_1180, ELEMENT_1190, ELEMENT_1210)
    );

    private static final Map<Lo3CategorieEnum, List<Lo3ElementEnum>> optioneleRubrieken = ImmutableMap.of(
            CATEGORIE_08, Arrays.asList(ELEMENT_1020, ELEMENT_1120, ELEMENT_1130, ELEMENT_1140, ELEMENT_1150, ELEMENT_1170)
    );

    private static final Map<Lo3CategorieEnum, List<Lo3ElementEnum>> alleRubrieken = ImmutableMap.of(
            CATEGORIE_08,
            ImmutableList.<Lo3ElementEnum>builder()
                    .addAll(verplichteRubrieken.get(CATEGORIE_08))
                    .addAll(optioneleRubrieken.get(CATEGORIE_08))
                    .build());

    private AdresidentificatieValidator() {
        // private
    }

    static boolean valideer(final List<Lo3CategorieWaarde> categorieen) {
        return categorieen.stream().anyMatch(AdresidentificatieValidator::containsVerplicht)
                && categorieen.stream().allMatch(AdresidentificatieValidator::containsAll);
    }

    private static boolean containsVerplicht(final Lo3CategorieWaarde categorie) {
        return contains(verplichteRubrieken, categorie);
    }

    private static boolean containsAll(final Lo3CategorieWaarde categorie) {
        return contains(alleRubrieken, categorie);
    }

    private static boolean contains(final Map<Lo3CategorieEnum, List<Lo3ElementEnum>> rubrieken, final Lo3CategorieWaarde categorie) {
        return rubrieken.getOrDefault(categorie.getCategorie(), Collections.emptyList()).stream()
                .anyMatch(element -> !Strings.isNullOrEmpty(categorie.getElement(element)));
    }
}

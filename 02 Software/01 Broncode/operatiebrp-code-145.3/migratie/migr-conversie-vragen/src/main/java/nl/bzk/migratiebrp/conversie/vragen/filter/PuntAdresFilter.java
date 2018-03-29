/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.vragen.filter;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Klasse voor het filteren van een 'punt adres'.
 */
public final class PuntAdresFilter {
    private static final String PUNT_ADRES = ".";
    private static final List<Lo3ElementEnum> PUNT_ADRES_RUBRIEKEN_LEEG = Arrays.asList(
            Lo3ElementEnum.ELEMENT_1115, Lo3ElementEnum.ELEMENT_1120,
            Lo3ElementEnum.ELEMENT_1130, Lo3ElementEnum.ELEMENT_1140,
            Lo3ElementEnum.ELEMENT_1150, Lo3ElementEnum.ELEMENT_1160,
            Lo3ElementEnum.ELEMENT_1170, Lo3ElementEnum.ELEMENT_1180,
            Lo3ElementEnum.ELEMENT_1190, Lo3ElementEnum.ELEMENT_1020);

    private PuntAdresFilter() {
        // Do not instantiate.
    }

    /**
     * Vervangt indien nodig de inhoud van de LO3 categoriewaarde.
     * @param waarde de LO3 categoriewaarde waarvan de inhoud wordt vervangen indien nodig
     * @return de LO3 categoriewaarde met de vervangen inhoud of de originele LO3 catgoriewaarde indien er niets is vervangen
     */
    public static Lo3CategorieWaarde replaceInCategorieWaarde(final Lo3CategorieWaarde waarde) {
        // waarde 11.10 = . en er zijn een of meer rubrieken 10.20 11.15 11.20 11.30 11.40 11.50 11.60 11.70 11.70 11.80 11.90
        // aanwezig die niet leeg is dan moet 11.10 letterlijk worden opgevat
        //
        // als waarde 11.10 een . is en geen van de bovengenoemde rubrieken zijn aanwezig of
        // ,indien wel aanwezig, een lege waarde hebben dan moet 11.10 als een onbekend adres worden opgevat
        return Stream.of(waarde)
                .filter(PuntAdresFilter::isVerblijfplaatsCategorie)
                .filter(PuntAdresFilter::isPuntAdres)
                .filter(PuntAdresFilter::heeftGeenOfLegeAdresRubrieken)
                .findFirst()
                .map(PuntAdresFilter::leegStraatnaam)
                .orElse(waarde);
    }

    private static boolean isVerblijfplaatsCategorie(final Lo3CategorieWaarde waarde) {
        return waarde.getCategorie() == Lo3CategorieEnum.CATEGORIE_08 || waarde.getCategorie() == Lo3CategorieEnum.CATEGORIE_58;
    }

    private static boolean isPuntAdres(final Lo3CategorieWaarde waarde) {
        return PUNT_ADRES.equals(waarde.getElement(Lo3ElementEnum.ELEMENT_1110));
    }

    private static boolean heeftGeenOfLegeAdresRubrieken(final Lo3CategorieWaarde waarde) {
        return PUNT_ADRES_RUBRIEKEN_LEEG.stream()
                .map(waarde::getElement)
                .filter(Objects::nonNull)
                .noneMatch(elementWaarde -> elementWaarde.length() > 0);
    }

    private static Lo3CategorieWaarde leegStraatnaam(final Lo3CategorieWaarde waarde) {
        waarde.addElement(Lo3ElementEnum.ELEMENT_1110, "");
        return waarde;
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.vragen.filter;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.junit.Test;

public class PuntAdresFilterTest {
    @Test
    public void moetNietFilteren() {
        Lo3CategorieWaarde waarde = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0);
        waarde.addElement(Lo3ElementEnum.ELEMENT_0110, "1234567890");
        Lo3CategorieWaarde result = PuntAdresFilter.replaceInCategorieWaarde(waarde);
        assertEquals("Lo3CategorieWaarde[categorie=01,elementen={ELEMENT_0110=1234567890}]", result.toString());
    }

    @Test
    public void moetNormaalAdresNietLeegmaken() {
        Map<Lo3ElementEnum, String> elementenMap = new HashMap<>();
        elementenMap.put(Lo3ElementEnum.ELEMENT_1110, "Dorpsstraat");
        elementenMap.put(Lo3ElementEnum.ELEMENT_1120, "14");
        elementenMap.put(Lo3ElementEnum.ELEMENT_1140, "a");
        Lo3CategorieWaarde waarde = maakVerblijfplaatsCategorie(false, elementenMap);
        Lo3CategorieWaarde result = PuntAdresFilter.replaceInCategorieWaarde(waarde);
        assertEquals("Lo3CategorieWaarde[categorie=08,elementen={ELEMENT_1110=Dorpsstraat, ELEMENT_1120=14, ELEMENT_1140=a}]", result.toString());
    }

    @Test
    public void moetPuntAdresLeegmaken() {
        Map<Lo3ElementEnum, String> elementenMap = new HashMap<>();
        elementenMap.put(Lo3ElementEnum.ELEMENT_1110, ".");
        Lo3CategorieWaarde waarde = maakVerblijfplaatsCategorie(false, elementenMap);
        Lo3CategorieWaarde result = PuntAdresFilter.replaceInCategorieWaarde(waarde);
        assertEquals("Lo3CategorieWaarde[categorie=08,elementen={ELEMENT_1110=}]", result.toString());
    }

    @Test
    public void moetPuntAdresNietLeegmakenAlsAndereAdresGroepMetWaarde() {
        Map<Lo3ElementEnum, String> elementenMap = new HashMap<>();
        elementenMap.put(Lo3ElementEnum.ELEMENT_1110, ".");
        elementenMap.put(Lo3ElementEnum.ELEMENT_1120, "13");
        Lo3CategorieWaarde waarde = maakVerblijfplaatsCategorie(false, elementenMap);
        Lo3CategorieWaarde result = PuntAdresFilter.replaceInCategorieWaarde(waarde);
        assertEquals("Lo3CategorieWaarde[categorie=08,elementen={ELEMENT_1110=., ELEMENT_1120=13}]", result.toString());
    }

    @Test
    public void moetPuntAdresNietLeegmakenAlsAndereAdresGroepMetLegeWaarde() {
        Map<Lo3ElementEnum, String> elementenMap = new HashMap<>();
        elementenMap.put(Lo3ElementEnum.ELEMENT_1110, ".");
        elementenMap.put(Lo3ElementEnum.ELEMENT_1120, "");
        Lo3CategorieWaarde waarde = maakVerblijfplaatsCategorie(false, elementenMap);
        Lo3CategorieWaarde result = PuntAdresFilter.replaceInCategorieWaarde(waarde);
        assertEquals("Lo3CategorieWaarde[categorie=08,elementen={ELEMENT_1110=, ELEMENT_1120=}]", result.toString());
    }

    @Test
    public void moetPuntAdresNietLeegmakenAlsAndereAdresGroepZonderWaarde() {
        Map<Lo3ElementEnum, String> elementenMap = new HashMap<>();
        elementenMap.put(Lo3ElementEnum.ELEMENT_1110, ".");
        elementenMap.put(Lo3ElementEnum.ELEMENT_1120, null);
        Lo3CategorieWaarde waarde = maakVerblijfplaatsCategorie(false, elementenMap);
        Lo3CategorieWaarde result = PuntAdresFilter.replaceInCategorieWaarde(waarde);
        assertEquals("Lo3CategorieWaarde[categorie=08,elementen={ELEMENT_1110=}]", result.toString());
    }

    @Test
    public void moetNormaalAdresNietLeegmakenHistorisch() {
        Map<Lo3ElementEnum, String> elementenMap = new HashMap<>();
        elementenMap.put(Lo3ElementEnum.ELEMENT_1110, "Dorpsstraat");
        elementenMap.put(Lo3ElementEnum.ELEMENT_1120, "14");
        elementenMap.put(Lo3ElementEnum.ELEMENT_1140, "a");
        Lo3CategorieWaarde waarde = maakVerblijfplaatsCategorie(true, elementenMap);
        Lo3CategorieWaarde result = PuntAdresFilter.replaceInCategorieWaarde(waarde);
        assertEquals("Lo3CategorieWaarde[categorie=58,elementen={ELEMENT_1110=Dorpsstraat, ELEMENT_1120=14, ELEMENT_1140=a}]", result.toString());
    }

    @Test
    public void moetPuntAdresLeegmakenHistorisch() {
        Map<Lo3ElementEnum, String> elementenMap = new HashMap<>();
        elementenMap.put(Lo3ElementEnum.ELEMENT_1110, ".");
        Lo3CategorieWaarde waarde = maakVerblijfplaatsCategorie(true, elementenMap);
        Lo3CategorieWaarde result = PuntAdresFilter.replaceInCategorieWaarde(waarde);
        assertEquals("Lo3CategorieWaarde[categorie=58,elementen={ELEMENT_1110=}]", result.toString());
    }

    @Test
    public void moetPuntAdresNietLeegmakenAlsAndereAdresGroepMetWaardeHistorisch() {
        Map<Lo3ElementEnum, String> elementenMap = new HashMap<>();
        elementenMap.put(Lo3ElementEnum.ELEMENT_1110, ".");
        elementenMap.put(Lo3ElementEnum.ELEMENT_1120, "13");
        Lo3CategorieWaarde waarde = maakVerblijfplaatsCategorie(true, elementenMap);
        Lo3CategorieWaarde result = PuntAdresFilter.replaceInCategorieWaarde(waarde);
        assertEquals("Lo3CategorieWaarde[categorie=58,elementen={ELEMENT_1110=., ELEMENT_1120=13}]", result.toString());
    }

    @Test
    public void moetPuntAdresNietLeegmakenAlsAndereAdresGroepMetLegeWaardeHistorisch() {
        Map<Lo3ElementEnum, String> elementenMap = new HashMap<>();
        elementenMap.put(Lo3ElementEnum.ELEMENT_1110, ".");
        elementenMap.put(Lo3ElementEnum.ELEMENT_1120, "");
        Lo3CategorieWaarde waarde = maakVerblijfplaatsCategorie(true, elementenMap);
        Lo3CategorieWaarde result = PuntAdresFilter.replaceInCategorieWaarde(waarde);
        assertEquals("Lo3CategorieWaarde[categorie=58,elementen={ELEMENT_1110=, ELEMENT_1120=}]", result.toString());
    }

    @Test
    public void moetPuntAdresNietLeegmakenAlsAndereAdresGroepZonderWaardeHistorisch() {
        Map<Lo3ElementEnum, String> elementenMap = new HashMap<>();
        elementenMap.put(Lo3ElementEnum.ELEMENT_1110, ".");
        elementenMap.put(Lo3ElementEnum.ELEMENT_1120, null);
        Lo3CategorieWaarde waarde = maakVerblijfplaatsCategorie(true, elementenMap);
        Lo3CategorieWaarde result = PuntAdresFilter.replaceInCategorieWaarde(waarde);
        assertEquals("Lo3CategorieWaarde[categorie=58,elementen={ELEMENT_1110=}]", result.toString());
    }

    private Lo3CategorieWaarde maakVerblijfplaatsCategorie(final boolean historischeCategorie, final Map<Lo3ElementEnum, String> elementenMap) {
        Lo3CategorieWaarde waarde = new Lo3CategorieWaarde(historischeCategorie ? Lo3CategorieEnum.CATEGORIE_58 : Lo3CategorieEnum.CATEGORIE_08, 0, 0);
        if (!elementenMap.isEmpty()) {
            elementenMap.entrySet().forEach(elementwaarde -> waarde.addElement(elementwaarde.getKey(), elementwaarde.getValue()));
        }
        return waarde;
    }
}

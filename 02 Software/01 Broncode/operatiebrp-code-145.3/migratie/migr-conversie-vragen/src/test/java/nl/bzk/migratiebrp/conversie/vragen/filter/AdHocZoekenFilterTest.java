/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.vragen.filter;

import static org.junit.Assert.assertEquals;

import com.google.common.collect.ImmutableList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.junit.Test;

/**
 * Test voor filter.
 */
public class AdHocZoekenFilterTest {

    @Test
    public void testGoedFilter() throws Exception {
        Lo3CategorieWaarde categorie01 =
                maakLo3CategorieWaarde(
                        Lo3CategorieEnum.CATEGORIE_01,
                        Lo3ElementEnum.ELEMENT_0110, Lo3ElementEnum.ELEMENT_0120, Lo3ElementEnum.ELEMENT_0210,
                        Lo3ElementEnum.ELEMENT_0220, Lo3ElementEnum.ELEMENT_0230, Lo3ElementEnum.ELEMENT_0240);
        final AdHocZoekenFilter filter = new AdHocZoekenFilter();
        Collection<Integer> result = filter.nietToegestaneRubrieken(Collections.singletonList(categorie01));
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testCategorie02() throws Exception {
        Lo3CategorieWaarde categorie02 = maakLo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_02, Lo3ElementEnum.ELEMENT_0110, Lo3ElementEnum.ELEMENT_0120);
        final AdHocZoekenFilter filter = new AdHocZoekenFilter();
        Collection<Integer> result = filter.nietToegestaneRubrieken(Collections.singletonList(categorie02));
        assertEquals(ImmutableList.of(20110, 20120), result);
    }

    @Test
    public void testCategorie03() throws Exception {
        Lo3CategorieWaarde categorie03 = maakLo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_03, Lo3ElementEnum.ELEMENT_0110, Lo3ElementEnum.ELEMENT_0120);
        final AdHocZoekenFilter filter = new AdHocZoekenFilter();
        Collection<Integer> result = filter.nietToegestaneRubrieken(Collections.singletonList(categorie03));
        assertEquals(ImmutableList.of(30110, 30120), result);
    }

    @Test
    public void testCategorie05() throws Exception {
        Lo3CategorieWaarde categorie05 = maakLo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_05, Lo3ElementEnum.ELEMENT_0110, Lo3ElementEnum.ELEMENT_0120);
        final AdHocZoekenFilter filter = new AdHocZoekenFilter();
        Collection<Integer> result = filter.nietToegestaneRubrieken(Collections.singletonList(categorie05));
        assertEquals(ImmutableList.of(50110, 50120), result);
    }

    @Test
    public void testRubriek050240() throws Exception {
        Lo3CategorieWaarde categorie05 = maakLo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_05, Lo3ElementEnum.ELEMENT_0240);
        final AdHocZoekenFilter filter = new AdHocZoekenFilter();
        Collection<Integer> result = filter.nietToegestaneRubrieken(Collections.singletonList(categorie05));
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testCategorie09() throws Exception {
        Lo3CategorieWaarde categorie09 =
                maakLo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_09, Lo3ElementEnum.ELEMENT_0110, Lo3ElementEnum.ELEMENT_0120);
        final AdHocZoekenFilter filter = new AdHocZoekenFilter();
        Collection<Integer> result = filter.nietToegestaneRubrieken(Collections.singletonList(categorie09));
        assertEquals(ImmutableList.of(90110, 90120), result);
    }

    @Test
    public void testCategorie11() throws Exception {
        Lo3CategorieWaarde categorie11 =
                maakLo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_11, Lo3ElementEnum.ELEMENT_0110, Lo3ElementEnum.ELEMENT_0120);
        final AdHocZoekenFilter filter = new AdHocZoekenFilter();
        Collection<Integer> result = filter.nietToegestaneRubrieken(Collections.singletonList(categorie11));
        assertEquals(ImmutableList.of(110110, 110120), result);
    }

    @Test
    public void testCategorieHistorisch() throws Exception {
        Lo3CategorieWaarde categorie52 =
                maakLo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_52, Lo3ElementEnum.ELEMENT_0110, Lo3ElementEnum.ELEMENT_0120);
        final AdHocZoekenFilter filter = new AdHocZoekenFilter();
        Collection<Integer> result = filter.nietToegestaneRubrieken(Collections.singletonList(categorie52));
        assertEquals(ImmutableList.of(520110, 520120), result);
    }

    @Test
    public void testGroep80() throws Exception {
        Lo3CategorieWaarde categorie01 =
                maakLo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, Lo3ElementEnum.ELEMENT_8010, Lo3ElementEnum.ELEMENT_0120);
        final AdHocZoekenFilter filter = new AdHocZoekenFilter();
        Collection<Integer> result = filter.nietToegestaneRubrieken(Collections.singletonList(categorie01));
        assertEquals(ImmutableList.of(18010), result);
    }

    @Test
    public void testGroep81() throws Exception {
        Lo3CategorieWaarde categorie01 =
                maakLo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, Lo3ElementEnum.ELEMENT_8110, Lo3ElementEnum.ELEMENT_0120);
        final AdHocZoekenFilter filter = new AdHocZoekenFilter();
        Collection<Integer> result = filter.nietToegestaneRubrieken(Collections.singletonList((categorie01)));
        assertEquals(ImmutableList.of(18110), result);
    }

    @Test
    public void testGroep82() throws Exception {
        Lo3CategorieWaarde categorie01 =
                maakLo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, Lo3ElementEnum.ELEMENT_8210, Lo3ElementEnum.ELEMENT_0120);
        final AdHocZoekenFilter filter = new AdHocZoekenFilter();
        Collection<Integer> result = filter.nietToegestaneRubrieken(Collections.singletonList(categorie01));
        assertEquals(ImmutableList.of(18210), result);
    }

    @Test
    public void testGroep83() throws Exception {
        Lo3CategorieWaarde categorie01 =
                maakLo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, Lo3ElementEnum.ELEMENT_8310, Lo3ElementEnum.ELEMENT_0120);
        final AdHocZoekenFilter filter = new AdHocZoekenFilter();
        Collection<Integer> result = filter.nietToegestaneRubrieken(Collections.singletonList(categorie01));
        assertEquals(ImmutableList.of(18310), result);
    }

    @Test
    public void testGroep84() throws Exception {
        Lo3CategorieWaarde categorie01 =
                maakLo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, Lo3ElementEnum.ELEMENT_8410, Lo3ElementEnum.ELEMENT_0120);
        final AdHocZoekenFilter filter = new AdHocZoekenFilter();
        Collection<Integer> result = filter.nietToegestaneRubrieken(Collections.singletonList(categorie01));
        assertEquals(ImmutableList.of(18410), result);
    }

    @Test
    public void testGroep85() throws Exception {
        Lo3CategorieWaarde categorie01 =
                maakLo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, Lo3ElementEnum.ELEMENT_8510, Lo3ElementEnum.ELEMENT_0120);
        final AdHocZoekenFilter filter = new AdHocZoekenFilter();
        Collection<Integer> result = filter.nietToegestaneRubrieken(Collections.singletonList(categorie01));
        assertEquals(ImmutableList.of(18510), result);
    }

    @Test
    public void testGroep86() throws Exception {
        Lo3CategorieWaarde categorie01 =
                maakLo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, Lo3ElementEnum.ELEMENT_8610, Lo3ElementEnum.ELEMENT_0120);
        final AdHocZoekenFilter filter = new AdHocZoekenFilter();
        Collection<Integer> result = filter.nietToegestaneRubrieken(Collections.singletonList(categorie01));
        assertEquals(ImmutableList.of(18610), result);
    }

    @Test
    public void testGroep87() throws Exception {
        Lo3CategorieWaarde categorie01 =
                maakLo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, Lo3ElementEnum.ELEMENT_8710, Lo3ElementEnum.ELEMENT_0120);
        final AdHocZoekenFilter filter = new AdHocZoekenFilter();
        Collection<Integer> result = filter.nietToegestaneRubrieken(Collections.singletonList(categorie01));
        assertEquals(ImmutableList.of(18710), result);
    }

    @Test
    public void testGroep88() throws Exception {
        Lo3CategorieWaarde categorie01 = maakLo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, Lo3ElementEnum.ELEMENT_8810, Lo3ElementEnum.ELEMENT_0120);
        final AdHocZoekenFilter filter = new AdHocZoekenFilter();
        Collection<Integer> result = filter.nietToegestaneRubrieken(Collections.singletonList(categorie01));
        assertEquals(ImmutableList.of(18810), result);
    }

    @Test
    public void testNietToegestaneRubriek077010() throws Exception {
        Lo3CategorieWaarde categorie01 = maakLo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, Lo3ElementEnum.ELEMENT_0110);
        Lo3CategorieWaarde categorie07 = maakLo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_07, Lo3ElementEnum.ELEMENT_7010);
        final AdHocZoekenFilter filter = new AdHocZoekenFilter();
        Collection<Integer> result = filter.nietToegestaneRubrieken(Arrays.asList(categorie01, categorie07));
        assertEquals(ImmutableList.of(77010), result);
    }

    @Test
    public void testNietToegestaneRubriek076710() throws Exception {
        Lo3CategorieWaarde categorie01 = maakLo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, Lo3ElementEnum.ELEMENT_0110);
        Lo3CategorieWaarde categorie07 = maakLo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_07, Lo3ElementEnum.ELEMENT_6710);
        final AdHocZoekenFilter filter = new AdHocZoekenFilter();
        Collection<Integer> result = filter.nietToegestaneRubrieken(Arrays.asList(categorie01, categorie07));
        assertEquals(ImmutableList.of(76710), result);
    }

    @Test
    public void testNietToegestaneRubriek046410() throws Exception {
        Lo3CategorieWaarde categorie01 = maakLo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, Lo3ElementEnum.ELEMENT_0110);
        Lo3CategorieWaarde categorie04 = maakLo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_04, Lo3ElementEnum.ELEMENT_6410);
        final AdHocZoekenFilter filter = new AdHocZoekenFilter();
        Collection<Integer> result = filter.nietToegestaneRubrieken(Arrays.asList(categorie01, categorie04));
        assertEquals(ImmutableList.of(46410), result);
    }

    @Test
    public void testNietToegestaneRubriek080920() throws Exception {
        Lo3CategorieWaarde categorie01 = maakLo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, Lo3ElementEnum.ELEMENT_0110);
        Lo3CategorieWaarde categorie08 = maakLo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_08, Lo3ElementEnum.ELEMENT_0920);
        final AdHocZoekenFilter filter = new AdHocZoekenFilter();
        Collection<Integer> result = filter.nietToegestaneRubrieken(Arrays.asList(categorie01, categorie08));
        assertEquals(ImmutableList.of(80920), result);
    }

    @Test
    public void testSortering() {
        Lo3CategorieWaarde categorie04 = maakLo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_04, Lo3ElementEnum.ELEMENT_6410);
        Lo3CategorieWaarde categorie07 = maakLo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_07, Lo3ElementEnum.ELEMENT_6710);
        Lo3CategorieWaarde categorie08 = maakLo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_08, Lo3ElementEnum.ELEMENT_0920);
        final AdHocZoekenFilter filter = new AdHocZoekenFilter();
        Collection<Integer> result = filter.nietToegestaneRubrieken(Arrays.asList(categorie08, categorie04, categorie07));
        assertEquals(ImmutableList.of(46410, 76710, 80920), result);
    }

    private Lo3CategorieWaarde maakLo3CategorieWaarde(final Lo3CategorieEnum categorie, final Lo3ElementEnum... elementen) {
        return new Lo3CategorieWaarde(categorie, 0, 0, Stream.of(elementen).collect(Collectors.toMap(Function.identity(), element -> "")));
    }
}

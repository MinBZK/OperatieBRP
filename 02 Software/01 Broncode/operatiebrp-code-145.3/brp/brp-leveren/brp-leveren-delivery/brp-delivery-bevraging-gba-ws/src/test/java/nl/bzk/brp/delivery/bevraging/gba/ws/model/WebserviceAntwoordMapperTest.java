/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging.gba.ws.model;

import static org.junit.Assert.assertEquals;

import com.google.common.collect.ImmutableMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import nl.bzk.brp.delivery.bevraging.gba.ws.lo3.Categoriestapel;
import nl.bzk.brp.delivery.bevraging.gba.ws.lo3.Categorievoorkomen;
import nl.bzk.brp.delivery.bevraging.gba.ws.lo3.Element;
import nl.bzk.brp.delivery.bevraging.gba.ws.lo3.PL;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.junit.Test;

public class WebserviceAntwoordMapperTest {

    @Test
    public void mapLeeg() {
        List<PL> list = WebserviceAntwoordMapper.map(Collections.emptyList());
        assertEquals(true, list.isEmpty());
    }

    @Test
    public void mapLegeCategorieen() {
        List<PL> list = WebserviceAntwoordMapper.map(Collections.singletonList(Collections.emptyList()));
        assertEquals(1, list.size());
        assertEquals(true, list.get(0).getCategoriestapels().isEmpty());
    }

    @Test
    public void mapEnkeleCategorie() {
        List<PL> list = WebserviceAntwoordMapper.map(Collections.singletonList(Collections.singletonList(example1())));
        assertEquals(1, list.size());
        assertEquals((byte) 1, list.get(0).getCategoriestapels().get(0).getCategorievoorkomens().get(0).getCategorienummer());
        assertEquals("[110: 1234567890, 120: 9876543210]",
                formatElementen(list.get(0).getCategoriestapels().get(0).getCategorievoorkomens().get(0).getElementen()));
    }

    @Test
    public void mapMeerdereCategorieen() {
        List<PL> list = WebserviceAntwoordMapper.map(Collections.singletonList(Arrays.asList(example1(), example2())));
        assertEquals(1, list.size());
        assertEquals(Arrays.asList((byte) 1, (byte) 8),
                list.stream()
                        .map(PL::getCategoriestapels)
                        .flatMap(List::stream)
                        .map(Categoriestapel::getCategorievoorkomens)
                        .flatMap(List::stream)
                        .map(Categorievoorkomen::getCategorienummer)
                        .collect(Collectors.toList()));
        assertEquals("[[110: 1234567890, 120: 9876543210], [1110: Dorpsstraat, 1120: 13]]",
                formatElementenLijst(list.stream()
                        .map(PL::getCategoriestapels)
                        .flatMap(List::stream)
                        .map(Categoriestapel::getCategorievoorkomens)
                        .flatMap(List::stream)
                        .map(Categorievoorkomen::getElementen)
                        .collect(Collectors.toList())));
    }

    private Lo3CategorieWaarde example1() {
        return new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0, ImmutableMap.of(
                Lo3ElementEnum.ELEMENT_0110, "1234567890",
                Lo3ElementEnum.ELEMENT_0120, "9876543210"
        ));
    }

    private Lo3CategorieWaarde example2() {
        return new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_08, 0, 0, ImmutableMap.of(
                Lo3ElementEnum.ELEMENT_1110, "Dorpsstraat",
                Lo3ElementEnum.ELEMENT_1120, "13"
        ));
    }

    private String formatElementen(final List<Element> elementen) {
        return elementen.stream().map(e -> e.getNummer() + ": " + e.getWaarde()).collect(Collectors.toList()).toString();
    }

    private String formatElementenLijst(final List<List<Element>> listOfElementen) {
        return listOfElementen.stream().map(this::formatElementen).collect(Collectors.toList()).toString();
    }
}

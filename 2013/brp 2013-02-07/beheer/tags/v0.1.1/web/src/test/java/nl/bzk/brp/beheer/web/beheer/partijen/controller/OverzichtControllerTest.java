/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.web.beheer.partijen.controller;

import java.util.HashMap;

import junit.framework.Assert;
import nl.bzk.brp.beheer.model.Partij;
import nl.bzk.brp.beheer.web.beheer.controller.partijen.OverzichtController;
import nl.bzk.brp.beheer.web.beheer.partijen.service.PartijService;
import org.displaytag.pagination.PaginatedListImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;


@RunWith(MockitoJUnitRunner.class)
public class OverzichtControllerTest {

    @Mock
    private PartijService       partijService;

    private OverzichtController overzichtController;

    private ModelMap            modelMap;
    private BindingResult       result;
    private Partij              partij;


    @SuppressWarnings("unchecked")
    @Before
    public void setUp() {
        overzichtController = new OverzichtController();
        overzichtController.setPartijService(partijService);

        PaginatedListImpl<Partij> zoekResultaat = new PaginatedListImpl<Partij>();

        Mockito.when(partijService.zoekPartij(Mockito.anyString(), (PaginatedListImpl<Partij>) Mockito.anyObject()))
                .thenReturn(zoekResultaat);

        modelMap = new ModelMap();
        result = new MapBindingResult(new HashMap<Object, Object>(), "");
        partij = new Partij(null);
        partij.setNaam("abc");
    }

    /**
     * Test het openen van de overzicht pagina.
     */
    @Test
    public void testOpenOverzichtPagina() {

        String view = overzichtController.openOverzichtPagina(modelMap);

        Assert.assertEquals("beheren/partijen/overzicht", view);
        Assert.assertNotNull(modelMap.get("command"));
        Assert.assertNotNull(modelMap.get("partijen"));
    }

    /**
     * Test zoeken.
     */
    @Test
    public void testZoek() {
        String view = overzichtController.zoek(partij, result, modelMap, "3");

        assertZoek(view, 3);
    }

    /**
     * Test zoeken zonder pagina aanduiding.
     */
    @Test
    public void testZoekZonderPaginaAanduiding() {
        String view = overzichtController.zoek(partij, result, modelMap, "");

        assertZoek(view, 1);
    }

    /**
     * Test ophalen van volgende pagina.
     */
    @Test
    public void testVolgendePagina() {
        String view = overzichtController.volgendePagina(partij, result, modelMap, "3");

        assertZoek(view, 4);
    }

    /**
     * Test ophalen van vorige pagina.
     */
    @Test
    public void testVorigePagina() {
        String view = overzichtController.vorigePagina(partij, result, modelMap, "3");

        assertZoek(view, 2);
    }


    /**
     * Asserts.
     *
     * @param view verwachte view
     * @param pagina verwachte pagina
     */
    private void assertZoek(final String view, final int pagina) {
        ArgumentCaptor<String> param1 = ArgumentCaptor.forClass(String.class);
        @SuppressWarnings("unchecked")
        ArgumentCaptor<PaginatedListImpl<Partij>> param2 =
            (ArgumentCaptor<PaginatedListImpl<Partij>>) (Object) ArgumentCaptor.forClass(PaginatedListImpl.class);
        Mockito.verify(partijService, Mockito.times(1)).zoekPartij(param1.capture(), param2.capture());
        Assert.assertEquals("abc", param1.getValue());
        Assert.assertEquals(pagina, param2.getValue().getPageNumber());

        Assert.assertNull("Moet in de huidige view blijven.", view);
        Assert.assertNotNull("Zoek resultaat mag niet null zijn.", modelMap.get("partijen"));
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.web.controller;

import junit.framework.Assert;
import nl.bzk.brp.beheer.web.service.GenericDomeinService;
import nl.bzk.brp.domein.kern.Partij;
import org.displaytag.pagination.PaginatedListImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.ModelMap;


// CHECKSTYLE:OFF
@RunWith(MockitoJUnitRunner.class)
public class AbstractOverzichtControllerTest {

    // CHECKSTYLE:ON
    @Mock
    private GenericDomeinService<Partij>        partijService;

    private AbstractOverzichtController<Partij> overzichtController;

    private ModelMap                            modelMap;
    private String                              partij;

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() {
        overzichtController = new AbstractOverzichtController<Partij>("beheren/partij/overzicht") {
        };
        overzichtController.setDomeinService(partijService);

        PaginatedListImpl<Partij> zoekResultaat = new PaginatedListImpl<Partij>();

        Mockito.when(partijService.zoekObject(Matchers.anyString(), (PaginatedListImpl<Partij>) Matchers.anyObject()))
                .thenReturn(zoekResultaat);

        modelMap = new ModelMap();

        partij = "abc";
    }

    /**
     * Test het openen van de overzicht pagina.
     *
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    @Test
    public void testOpenOverzichtPagina() throws InstantiationException, IllegalAccessException {
        String view = overzichtController.openOverzichtPagina(modelMap);

        Assert.assertEquals("beheren/partij/overzicht", view);
        Assert.assertNotNull(modelMap.get("resultaat"));
    }

    /**
     * Test zoeken.
     */
    @Test
    public void testZoek() {
        String view = overzichtController.zoek(partij, modelMap, "3");

        assertZoek(view, 3);
    }

    /**
     * Test zoeken zonder pagina aanduiding.
     */
    @Test
    public void testZoekZonderPaginaAanduiding() {
        String view = overzichtController.zoek(partij, modelMap, "");

        assertZoek(view, 1);
    }

    /**
     * Test ophalen van volgende pagina.
     */
    @Test
    public void testVolgendePagina() {
        String view = overzichtController.volgendePagina(partij, modelMap, "3");

        assertZoek(view, 4);
    }

    /**
     * Test ophalen van vorige pagina.
     */
    @Test
    public void testVorigePagina() {
        String view = overzichtController.vorigePagina(partij, modelMap, "3");

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
        Mockito.verify(partijService, Mockito.times(1)).zoekObject(param1.capture(), param2.capture());
        Assert.assertEquals("abc", param1.getValue());
        Assert.assertEquals(pagina, param2.getValue().getPageNumber());

        Assert.assertNull("Moet in de huidige view blijven.", view);
        Assert.assertNotNull("Zoek resultaat mag niet null zijn.", modelMap.get("resultaat"));
    }
}

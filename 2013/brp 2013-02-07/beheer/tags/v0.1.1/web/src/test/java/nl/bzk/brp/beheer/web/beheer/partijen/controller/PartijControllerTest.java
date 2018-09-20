/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.web.beheer.partijen.controller;

import java.util.ArrayList;
import java.util.HashMap;

import junit.framework.Assert;
import nl.bzk.brp.beheer.model.Partij;
import nl.bzk.brp.beheer.model.Sector;
import nl.bzk.brp.beheer.model.SoortPartij;
import nl.bzk.brp.beheer.web.beheer.controller.partijen.PartijController;
import nl.bzk.brp.beheer.web.beheer.controller.partijen.PartijFormBean;
import nl.bzk.brp.beheer.web.beheer.partijen.service.PartijService;
import nl.bzk.brp.beheer.web.beheer.stamgegevens.service.StamgegevensService;
import nl.bzk.brp.beheer.web.controller.AbstractController;
import nl.bzk.brp.beheer.web.messages.MessageSeverity;
import nl.bzk.brp.beheer.web.messages.MessageUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;


@RunWith(MockitoJUnitRunner.class)
public class PartijControllerTest {

    private PartijController    partijController;

    @Mock
    private PartijService       partijService;

    @Mock
    private StamgegevensService stamgegevensService;

    @Mock
    private MessageUtil         messageUtil;

    @Before
    public void setUp() {
        partijController = new PartijController();
        partijController.setPartijService(partijService);
        partijController.setStamgegevensService(stamgegevensService);
        partijController.setMessageUtil(messageUtil);

        Mockito.when(stamgegevensService.getSoortPartij()).thenReturn(new ArrayList<SoortPartij>());
        Mockito.when(stamgegevensService.getSector()).thenReturn(new ArrayList<Sector>());

        Partij partij = new Partij(null);
        partij.setNaam("Amsterdam");
        partij.setId(1L);
        Mockito.when(partijService.ophalenPartij(1)).thenReturn(partij);
    }

    /**
     * Open toevoegen pagina via request parameter _nieuw.
     */
    @Test
    public void testOpenNieuwPartijPagina() {
        ModelMap modelMap = new ModelMap();
        String view = partijController.openNieuwPartijPagina(modelMap);

        Mockito.verify(partijService, Mockito.times(0)).ophalenPartij(Mockito.anyInt());

        assertOpenToevoegenPagina(view, modelMap);
    }

    /**
     * Open toevoegen pagina zonder request parameter. En wanneer de gebruiker nog niet bezig is om een partij toe te
     * voegen.
     * Dit kan gebeuren wanneer de gebruiker de link naar deze pagina openend zonder request parameter _nieuw
     */
    @Test
    public void testOpenToevoegenPaginaViaUrl() {
        ModelMap modelMap = new ModelMap();
        String view = partijController.openToevoegenPagina(modelMap);

        Mockito.verify(partijService, Mockito.times(0)).ophalenPartij(Mockito.anyInt());

        assertOpenToevoegenPagina(view, modelMap);
    }

    /**
     * Gebruiker opent de bewerk scherm, de partij moet dan opgehaald worden uit de database.
     */
    @Test
    public void testOpenBewerkPartijPagina() {
        ModelMap modelMap = new ModelMap();
        String view = partijController.openBewerkPartijPagina(modelMap, "1");

        Mockito.verify(partijService, Mockito.times(1)).ophalenPartij(1);

        Assert.assertTrue("lees modus moet aan staan.",
                (Boolean) modelMap.get(AbstractController.SESSION_ATTRIBUTE_LEESMODUS));

        assertBewerkToevoegenPagina(view, modelMap);
    }

    /**
     * Gebruiker navigeert terug naar de partij toevoegen pagina via de tabs, verwacht wordt dat de id bekend is in de
     * sessie.
     */
    @Test
    public void testOpenToevoegenPaginaViaTabs() {
        ModelMap modelMap = new ModelMap();
        modelMap.put(AbstractController.SESSION_ATTRIBUTE_ID, "1");
        // Dit kan true of false zijn afhankelijk van hoe de gebruiker de binnenkomt via nieuw of bewerk.
        modelMap.put(AbstractController.SESSION_ATTRIBUTE_LEESMODUS, false);
        String view = partijController.openToevoegenPagina(modelMap);

        Mockito.verify(partijService, Mockito.times(1)).ophalenPartij(1);

        Assert.assertFalse("lees modus moet niet aan staan.",
                (Boolean) modelMap.get(AbstractController.SESSION_ATTRIBUTE_LEESMODUS));
        assertBewerkToevoegenPagina(view, modelMap);
    }

    /**
     * Omschakelen van lees modus naar bewerk modus.
     */
    @Test
    public void testWijzigen() {
        PartijFormBean formBean = new PartijFormBean();
        formBean.setLeesModus(true);

        String view = partijController.wijzigen(formBean);

        Assert.assertNull("Moet terug gaan naar dezelfde view.", view);
        Assert.assertFalse("Scherm moet nu in bewerk modus zitten.", formBean.isLeesModus());
    }

    /**
     * Opslaan van nieuwe partij zonder fouten.
     */
    @Test
    public void testOpslaanNieuwZonderFormFouten() {
        ModelMap modelMap = new ModelMap();
        // Lees modus moet al in de sessie zitten in deze stap, omdat de gebruiker via een bepaald flow binnenkomt,
        // lees, bewerk of toevoegen.
        modelMap.put(AbstractController.SESSION_ATTRIBUTE_LEESMODUS, false);
        PartijFormBean formBean = new PartijFormBean();
        Partij partij = new Partij(null);
        partij.setNaam("abcd");
        partij.setId(1L);
        formBean.setPartij(partij);

        Mockito.when(partijService.opslaan(partij)).thenReturn(partij);

        Assert.assertNull("Voordat de partij opgeslagen zou de attribute geen id moeten bevatten.",
                modelMap.get(AbstractController.SESSION_ATTRIBUTE_ID));

        BindingResult result = new MapBindingResult(new HashMap<Object, Object>(), "");
        String view = partijController.opslaan(formBean, result, modelMap);

        Mockito.verify(messageUtil, Mockito.times(1)).addMessage(MessageSeverity.SUCCESS, "bericht.isopgeslagen",
                new String[] { "entity.Partij" }, false);

        Assert.assertEquals("Na het opslaan zou de attribute een nieuw id moeten hebben", "1",
                modelMap.get(AbstractController.SESSION_ATTRIBUTE_ID));
        Assert.assertNull("Moet terug naar huidige view", view);
    }

    @Test
    public void testOpslaanMetFormFouten() {
        ModelMap modelMap = new ModelMap();
        // Lees modus moet al in de sessie zitten in deze stap, omdat de gebruiker via een bepaald flow binnenkomt,
        // lees, bewerk of toevoegen.
        modelMap.put(AbstractController.SESSION_ATTRIBUTE_LEESMODUS, false);
        PartijFormBean formBean = new PartijFormBean();
        Partij partij = new Partij(null);
        partij.setNaam("");
        partij.setId(1L);
        formBean.setPartij(partij);

        Mockito.verify(partijService, Mockito.times(0)).opslaan((Partij) Mockito.anyObject());

        BindingResult result = new MapBindingResult(new HashMap<Object, Object>(), "");
        String view = partijController.opslaan(formBean, result, modelMap);

        Mockito.verify(messageUtil, Mockito.times(0)).addMessage((MessageSeverity) Mockito.any(), Mockito.anyString(),
                (String[]) Mockito.anyObject(), Mockito.anyBoolean());

        Assert.assertNull("Moet terug naar huidige view.", view);
        Assert.assertEquals(1, result.getErrorCount());
    }

    /**
     * Test opslaan in van bewerk modus. Na het bewerken moet de view weer terug naar leesmodus.
     */
    @Test
    public void testOpslaanBewerken() {
        ModelMap modelMap = new ModelMap();
        // Lees modus moet al in de sessie zitten in deze stap, omdat de gebruiker via een bepaald flow binnenkomt,
        // lees, bewerk of toevoegen.
        modelMap.put(AbstractController.SESSION_ATTRIBUTE_LEESMODUS, true);
        PartijFormBean formBean = new PartijFormBean();
        formBean.setLeesModus(false);
        Partij partij = new Partij(null);
        partij.setNaam("abcd");
        partij.setId(1L);
        formBean.setPartij(partij);

        Mockito.when(partijService.opslaan(partij)).thenReturn(partij);

        BindingResult result = new MapBindingResult(new HashMap<Object, Object>(), "");
        String view = partijController.opslaan(formBean, result, modelMap);

        Assert.assertTrue("Lees modus moet weer aangeschakeld zijn na het opslaan.", formBean.isLeesModus());

        Assert.assertNull("Moet terug naar huidige view.", view);
    }

    private void assertOpenToevoegenPagina(final String view, final ModelMap modelMap) {
        Assert.assertEquals("Partij view moet weergegeven worden.", "beheren/partijen/partij", view);
        Assert.assertEquals("id moet gereset worden.", "", modelMap.get(AbstractController.SESSION_ATTRIBUTE_ID));
        Assert.assertFalse("lees modus moet niet aan staan.",
                (Boolean) modelMap.get(AbstractController.SESSION_ATTRIBUTE_LEESMODUS));
        Assert.assertNotNull("srtPartijen moet gevuld zijn om de soort partijen op het scherm te kunnen laten zien.",
                modelMap.get("srtPartijen"));
        Assert.assertNotNull("partijSector moet gevuld zijn om de sectoren op het scherm te kunnen laten zien.",
                modelMap.get("partijSector"));
        Assert.assertNull("Naam moet leeg zijn bij een nieuw toevoegen pagina.",
                ((PartijFormBean) modelMap.get(AbstractController.SESSION_ATTRIBUTE_COMMAND)).getPartij().getNaam());
    }

    private void assertBewerkToevoegenPagina(final String view, final ModelMap modelMap) {
        Assert.assertEquals("Partij view moet weergegeven worden.", "beheren/partijen/partij", view);
        Assert.assertEquals("id moet gezet worden.", "1", modelMap.get(AbstractController.SESSION_ATTRIBUTE_ID));
        Assert.assertNotNull("srtPartijen moet gevuld zijn om de soort partijen op het scherm te kunnen laten zien.",
                modelMap.get("srtPartijen"));
        Assert.assertNotNull("partijSector moet gevuld zijn om de sectoren op het scherm te kunnen laten zien.",
                modelMap.get("partijSector"));
        Assert.assertEquals("Naam moet gevuld zijn.", "Amsterdam",
                ((PartijFormBean) modelMap.get(AbstractController.SESSION_ATTRIBUTE_COMMAND)).getPartij().getNaam());
    }
}

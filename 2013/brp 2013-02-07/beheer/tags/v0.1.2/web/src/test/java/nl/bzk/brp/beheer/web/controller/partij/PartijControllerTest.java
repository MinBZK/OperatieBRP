/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.web.controller.partij;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import junit.framework.Assert;
import nl.bzk.brp.beheer.web.controller.AbstractController;
import nl.bzk.brp.beheer.web.controller.AbstractDetailController;
import nl.bzk.brp.beheer.web.messages.MessageSeverity;
import nl.bzk.brp.beheer.web.messages.MessageUtil;
import nl.bzk.brp.beheer.web.service.GenericDomeinService;
import nl.bzk.brp.beheer.web.service.stamgegevens.StamgegevensService;
import nl.bzk.brp.domein.DomeinObjectFactory;
import nl.bzk.brp.domein.PersistentDomeinObjectFactory;
import nl.bzk.brp.domein.kern.Partij;
import nl.bzk.brp.domein.kern.SoortPartij;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;


@RunWith(MockitoJUnitRunner.class)
public class PartijControllerTest {

    private PartijController             partijController;

    private final DomeinObjectFactory    domeinObjectFactory = new PersistentDomeinObjectFactory();

    @Mock
    private GenericDomeinService<Partij> partijService;

    @Mock
    private StamgegevensService          stamgegevensService;

    @Mock
    private MessageUtil                  messageUtil;

    @Before
    public void setUp() {
        partijController = new PartijController();
        partijController.setDomeinService(partijService);
        partijController.setStamgegevensService(stamgegevensService);
        partijController.setMessageUtil(messageUtil);
        partijController.setDomeinObjectFactory(domeinObjectFactory);

        Map<SoortPartij, String> vertaaldeEnumWaarde = new LinkedHashMap<SoortPartij, String>();

        Mockito.when(stamgegevensService.getSoortPartij()).thenReturn(vertaaldeEnumWaarde);

        Partij partij = domeinObjectFactory.createPartij();
        partij.setNaam("Amsterdam");
        partij.setID(1L);
        Mockito.when(partijService.ophalenObject(1)).thenReturn(partij);
    }

    /**
     * Open toevoegen pagina via request parameter _nieuw.
     */
    @Test
    public void testOpenNieuwPartijPagina() {
        ModelMap modelMap = new ModelMap();
        String view = partijController.openNieuwPagina(modelMap);

        Mockito.verify(partijService, Mockito.times(0)).ophalenObject(Matchers.anyInt());

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
        String view = partijController.openPagina(modelMap);

        Mockito.verify(partijService, Mockito.times(0)).ophalenObject(Matchers.anyInt());

        assertOpenToevoegenPagina(view, modelMap);
    }

    /**
     * Gebruiker opent de bewerk scherm, de partij moet dan opgehaald worden uit de database.
     */
    @Test
    public void testOpenBewerkPartijPagina() {
        ModelMap modelMap = new ModelMap();
        String view = partijController.openBewerkPagina(modelMap, "1");

        Mockito.verify(partijService, Mockito.times(1)).ophalenObject(1);

        Assert.assertTrue("lees modus moet aan staan.",
                (Boolean) modelMap.get(AbstractDetailController.SESSION_ATTRIBUTE_LEESMODUS));

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
        modelMap.put(AbstractDetailController.SESSION_ATTRIBUTE_LEESMODUS, false);
        String view = partijController.openPagina(modelMap);

        Mockito.verify(partijService, Mockito.times(1)).ophalenObject(1);

        Assert.assertFalse("lees modus moet niet aan staan.",
                (Boolean) modelMap.get(AbstractDetailController.SESSION_ATTRIBUTE_LEESMODUS));
        assertBewerkToevoegenPagina(view, modelMap);
    }

    /**
     * Omschakelen van lees modus naar bewerk modus.
     */
    @Test
    public void testWijzigen() {
        PartijFormBean formBean = new PartijFormBean(domeinObjectFactory.createPartij());
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
        modelMap.put(AbstractDetailController.SESSION_ATTRIBUTE_LEESMODUS, false);
        Partij partij = domeinObjectFactory.createPartij();
        partij.setNaam("abcd");
        partij.setID(1L);
        PartijFormBean formBean = new PartijFormBean(partij);

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
        modelMap.put(AbstractDetailController.SESSION_ATTRIBUTE_LEESMODUS, false);
        Partij partij = domeinObjectFactory.createPartij();

        //TODO Hosing: naam is niet goed geannoteerd nog, enige restrictie op dit moment is max 40
        partij.setNaam("12345678901234567890123456789012345678901");
        partij.setID(1L);
        PartijFormBean formBean = new PartijFormBean(partij);

        Mockito.verify(partijService, Mockito.times(0)).opslaan((Partij) Matchers.anyObject());

        BindingResult result = new MapBindingResult(new HashMap<Object, Object>(), "");
        String view = partijController.opslaan(formBean, result, modelMap);

        Mockito.verify(messageUtil, Mockito.times(0)).addMessage((MessageSeverity) Matchers.any(),
                Matchers.anyString(), (String[]) Matchers.anyObject(), Matchers.anyBoolean());

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
        modelMap.put(AbstractDetailController.SESSION_ATTRIBUTE_LEESMODUS, true);
        Partij partij = domeinObjectFactory.createPartij();
        partij.setNaam("abcd");
        partij.setID(1L);
        PartijFormBean formBean = new PartijFormBean(partij);
        formBean.setLeesModus(false);

        Mockito.when(partijService.opslaan(partij)).thenReturn(partij);

        BindingResult result = new MapBindingResult(new HashMap<Object, Object>(), "");
        String view = partijController.opslaan(formBean, result, modelMap);

        Assert.assertTrue("Lees modus moet weer aangeschakeld zijn na het opslaan.", formBean.isLeesModus());

        Assert.assertNull("Moet terug naar huidige view.", view);
    }

    private void assertOpenToevoegenPagina(final String view, final ModelMap modelMap) {
        Assert.assertEquals("Partij view moet weergegeven worden.", "beheren/partij/partij", view);
        Assert.assertEquals("id moet gereset worden.", "", modelMap.get(AbstractController.SESSION_ATTRIBUTE_ID));
        Assert.assertFalse("lees modus moet niet aan staan.",
                (Boolean) modelMap.get(AbstractDetailController.SESSION_ATTRIBUTE_LEESMODUS));
        Assert.assertNotNull("SoortPartij moet gevuld zijn om de soort partijen op het scherm te kunnen laten zien.",
                modelMap.get("SoortPartij"));
        Assert.assertNull("Naam moet leeg zijn bij een nieuw toevoegen pagina.",
                ((PartijFormBean) modelMap.get(AbstractController.SESSION_ATTRIBUTE_COMMAND)).getPartij().getNaam());
    }

    private void assertBewerkToevoegenPagina(final String view, final ModelMap modelMap) {
        Assert.assertEquals("Partij view moet weergegeven worden.", "beheren/partij/partij", view);
        Assert.assertEquals("id moet gezet worden.", "1", modelMap.get(AbstractController.SESSION_ATTRIBUTE_ID));
        Assert.assertNotNull("SoortPartij moet gevuld zijn om de soort partijen op het scherm te kunnen laten zien.",
                modelMap.get("SoortPartij"));
        Assert.assertEquals("Naam moet gevuld zijn.", "Amsterdam",
                ((PartijFormBean) modelMap.get(AbstractController.SESSION_ATTRIBUTE_COMMAND)).getPartij().getNaam());
    }
}

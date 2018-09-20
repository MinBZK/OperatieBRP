/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.web.beheer.partijen.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;
import nl.bzk.brp.beheer.model.Partij;
import nl.bzk.brp.beheer.model.Partijrol;
import nl.bzk.brp.beheer.model.Rol;
import nl.bzk.brp.beheer.web.beheer.controller.partijen.RolController;
import nl.bzk.brp.beheer.web.beheer.controller.partijen.RolFormBean;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;


@RunWith(MockitoJUnitRunner.class)
public class RolControllerTest {

    @Mock
    private StamgegevensService stamgegevensService;

    @Mock
    private PartijService       partijService;

    @Mock
    private MessageUtil         messageUtil;

    private RolController       rolController;

    @Before
    public void setUp() {
        rolController = new RolController();
        rolController.setPartijService(partijService);
        rolController.setStamgegevensService(stamgegevensService);
        rolController.setMessageUtil(messageUtil);

        List<Rol> rollen = new ArrayList<Rol>();
        Rol rol1 = new Rol(1, "rol1");
        Rol rol2 = new Rol(2, "rol2");
        Rol rol3 = new Rol(3, "rol3");
        Rol rol4 = new Rol(4, "rol4");

        rollen.add(rol1);
        rollen.add(rol2);
        rollen.add(rol3);
        rollen.add(rol4);

        Mockito.when(stamgegevensService.getRollen()).thenReturn(rollen);

        Partij partij = new Partij(null);
        partij.setNaam("Amsterdam");
        partij.setId(1L);
        Mockito.when(partijService.ophalenPartij(1)).thenReturn(partij);
    }

    /**
     * Test het openen van deze pagina wanneer partij nog niet is opgeslagen.
     */
    @Test
    public void testOpenToevoegenPaginaZonderPartij() {
        ModelMap modelMap = new ModelMap();
        ModelAndView view = rolController.openToevoegenPagina("", false, modelMap);

        Mockito.verify(messageUtil, Mockito.times(1)).addMessage(MessageSeverity.INFO, "bericht.parentnietopgeslagen",
                new String[] { "entity.Partijrol", "entity.Partij" }, true);

        Assert.assertEquals(RedirectView.class, view.getView().getClass());
        Assert.assertEquals("partij.html", ((RedirectView) view.getView()).getUrl());
    }

    @Test
    public void testOpenToevoegenPagina() {
        ModelMap modelMap = new ModelMap();
        ModelAndView view = rolController.openToevoegenPagina("1", false, modelMap);

        RolFormBean formBean = (RolFormBean) modelMap.get(AbstractController.SESSION_ATTRIBUTE_COMMAND);
        Assert.assertFalse("Pagina is geopened in toevoegen modus.", formBean.isLeesModus());
        Assert.assertEquals("Partij met id 1 zou opgehaald moeten zijn.", "Amsterdam", formBean.getPartij().getNaam());

        Assert.assertNotNull("rollen moet gevuld zijn om de rollen op het scherm te kunnen laten zien.",
                modelMap.get("rollen"));
        Assert.assertEquals(ModelAndView.class, view.getClass());
        Assert.assertEquals("beheren/partijen/rol", view.getViewName());

    }

    /**
     * Omschakelen van lees modus naar bewerk modus.
     */
    @Test
    public void testWijzigen() {
        RolFormBean formBean = new RolFormBean();
        formBean.setLeesModus(true);

        String view = rolController.wijzigen(formBean);

        Assert.assertNull("Moet terug gaan naar dezelfde view.", view);
        Assert.assertFalse("Scherm moet nu in bewerk modus zitten.", formBean.isLeesModus());
    }

    /**
     * Toevoegen van rollen.
     */
    @Test
    public void testVoegRolToe() {
        RolFormBean formBean = new RolFormBean();
        ModelMap modelMap = new ModelMap();

        Partij partij = new Partij(null);

        List<Rol> toeTeVoegenRollen = new ArrayList<Rol>();
        Rol rol1 = new Rol(1, "rol1");
        Rol rol2 = new Rol(2, "rol2");

        toeTeVoegenRollen.add(rol1);
        toeTeVoegenRollen.add(rol2);

        formBean.setPartij(partij);
        formBean.setToeTeVoegenRollen(toeTeVoegenRollen);

        BindingResult result = new MapBindingResult(new HashMap<Object, Object>(), "");
        String view = rolController.voegRolToe(formBean, result, modelMap);

        @SuppressWarnings("unchecked")
        List<Rol> rollen = (List<Rol>) modelMap.get("rollen");

        Assert.assertNull("View moet terug gaan naar huidige view.", view);
        Assert.assertEquals("Twee rollen moeten zijn toegevoegd.", 2, formBean.getPartij().getPartijrols().size());

        // Moet op deze manier getest worden omdat de volgorde van de Set niet altijd hetzelfde is.
        for (Rol rol : toeTeVoegenRollen) {
            boolean aanwezig = false;

            for (Partijrol pr : formBean.getPartij().getPartijrols()) {
                if (rol.getNaam().equals(pr.getRol().getNaam())) {
                    aanwezig = true;
                    break;
                }
            }

            Assert.assertTrue("Rol moet nu aan de partij zijn toegevoegd", aanwezig);
        }

        Assert.assertEquals("Toegevoegde rollen mogen niet meer beschikbaar zijn voor toevoeging.", 2, rollen.size());
        Assert.assertEquals("rol3", rollen.get(0).getNaam());
        Assert.assertEquals("rol4", rollen.get(1).getNaam());
    }

    @Test
    public void testVoegRolToeGeenRolGeselecteerd() {
        RolFormBean formBean = new RolFormBean();
        ModelMap modelMap = new ModelMap();
        BindingResult result = new MapBindingResult(new HashMap<Object, Object>(), "");

        String view = rolController.voegRolToe(formBean, result, modelMap);

        Mockito.verify(messageUtil, Mockito.times(1)).addMessage(MessageSeverity.INFO, "bericht.nietgeselecteerd",
                new String[] { "entity.Partijrol" }, false);

        Assert.assertNull("Moet in de huidige view blijven.", view);
    }

    @Test
    public void testVerwijderenRol() {
        Partij partij = new Partij(null);
        Set<Partijrol> partijrollen = new HashSet<Partijrol>();
        partij.setPartijrols(partijrollen);
        partijrollen.add(new Partijrol(1, partij, new Rol(1, "rol1"), null));
        partijrollen.add(new Partijrol(2, partij, new Rol(2, "rol2"), null));
        partijrollen.add(new Partijrol(3, partij, new Rol(3, "rol3"), null));

        List<String> teVerwijderenRollen = new ArrayList<String>();
        teVerwijderenRollen.add("2");

        RolFormBean formBean = new RolFormBean();
        formBean.setPartij(partij);
        formBean.setTeVerwijderenRollen(teVerwijderenRollen);

        ModelMap modelMap = new ModelMap();
        BindingResult result = new MapBindingResult(new HashMap<Object, Object>(), "");

        String view = rolController.verwijderenRol(formBean, result, modelMap);

        Assert.assertEquals("1 rol moet verwijderd zijn van partijrol", 2, formBean.getPartij().getPartijrols().size());

        // Moet op deze manier getest worden omdat de volgorde van de Set niet altijd hetzelfde is.
        for (String rol : teVerwijderenRollen) {
            for (Partijrol pr : formBean.getPartij().getPartijrols()) {
                if (Integer.parseInt(rol) == pr.getRol().getId()) {
                    Assert.assertFalse("Rol mag niet meer in partij zitten.", true);
                }
            }
        }

        Assert.assertNull("Moet in de huidige view blijven.", view);
    }

    @Test
    public void testVerwijderenRolGeenRolGeselecteerd() {
        RolFormBean formBean = new RolFormBean();
        ModelMap modelMap = new ModelMap();
        BindingResult result = new MapBindingResult(new HashMap<Object, Object>(), "");

        String view = rolController.verwijderenRol(formBean, result, modelMap);

        Mockito.verify(messageUtil, Mockito.times(1)).addMessage(MessageSeverity.INFO, "bericht.nietgeselecteerd",
                new String[] { "entity.Partijrol" }, false);

        Assert.assertNull("Moet in de huidige view blijven.", view);
    }

    @Test
    public void testOpslaan() {
        RolFormBean formBean = new RolFormBean();
        Partij partij = new Partij(null);
        formBean.setPartij(partij);
        BindingResult result = new MapBindingResult(new HashMap<Object, Object>(), "");

        String view = rolController.opslaan(formBean, result, true);

        Mockito.verify(partijService, Mockito.times(1)).opslaan(partij);

        Mockito.verify(messageUtil, Mockito.times(1)).addMessage(MessageSeverity.SUCCESS, "bericht.zijnopgeslagen",
                new String[] { "entity.Partijrol" }, false);

        Assert.assertNull("Moet in de huidige view blijven.", view);
        Assert.assertTrue("Huidige leesmodus moet gezet zijn.", formBean.isLeesModus());
    }
}

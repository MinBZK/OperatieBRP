/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.web.beheer.partijen.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;
import nl.bzk.brp.beheer.model.AuthenticatieMiddel;
import nl.bzk.brp.beheer.model.Certificaat;
import nl.bzk.brp.beheer.model.Partij;
import nl.bzk.brp.beheer.web.beheer.controller.partijen.AuthenticatiemiddelController;
import nl.bzk.brp.beheer.web.beheer.controller.partijen.AuthenticatiemiddelFormBean;
import nl.bzk.brp.beheer.web.beheer.partijen.service.PartijService;
import nl.bzk.brp.beheer.web.controller.AbstractController;
import nl.bzk.brp.beheer.web.messages.MessageSeverity;
import nl.bzk.brp.beheer.web.messages.MessageUtil;
import org.junit.Before;
import org.junit.Ignore;
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
public class AuthenticatiemiddelControllerTest {
    //TODO functieel werkt beheren van authenticatiemiddel nog niet correct. Test moet aangepast worden zodra het volgens de specificaties werkt.

    @Mock
    private PartijService                 partijService;

    @Mock
    private MessageUtil                   messageUtil;

    private AuthenticatiemiddelController controller;

    private BindingResult                 bindingResult;

    @Before
    public void setUp() {
        controller = new AuthenticatiemiddelController();
        controller.setMessageUtil(messageUtil);
        controller.setPartijService(partijService);
        bindingResult = new MapBindingResult(new HashMap<Object, Object>(), "partijFormBean");

        Partij partij = new Partij(null);
        partij.setNaam("Amsterdam");
        partij.setId(1L);
        Mockito.when(partijService.ophalenPartij(1)).thenReturn(partij);
    }

    @Test
    public void testOpenToevoegenPaginaZonderPartij() {
        ModelMap modelMap = new ModelMap();
        ModelAndView view = controller.openToevoegenPagina("", false, modelMap);

        Mockito.verify(messageUtil, Mockito.times(1)).addMessage(MessageSeverity.INFO, "bericht.parentnietopgeslagen",
                new String[] { "entity.Authenticatiemiddel", "entity.Partij" }, true);

        Assert.assertEquals(RedirectView.class, view.getView().getClass());
        Assert.assertEquals("partij.html", ((RedirectView) view.getView()).getUrl());
    }

    @Test
    public void testOpenToevoegenPagina() {
        ModelMap modelMap = new ModelMap();
        ModelAndView view = controller.openToevoegenPagina("1", false, modelMap);

        AuthenticatiemiddelFormBean formBean =
            (AuthenticatiemiddelFormBean) modelMap.get(AbstractController.SESSION_ATTRIBUTE_COMMAND);
        Assert.assertFalse("Pagina is geopened in toevoegen modus.", formBean.isLeesModus());
        Assert.assertEquals("Partij met id 1 zou opgehaald moeten zijn.", "Amsterdam", formBean.getPartij().getNaam());

        Assert.assertEquals(ModelAndView.class, view.getClass());
        Assert.assertEquals("beheren/partijen/authenticatiemiddel", view.getViewName());
    }

    /**
     * Omschakelen van lees modus naar bewerk modus.
     */
    @Test
    public void testWijzigen() {
        AuthenticatiemiddelFormBean formBean = new AuthenticatiemiddelFormBean(new Partij(null));
        formBean.setLeesModus(true);

        String view = controller.wijzigen(formBean);

        Assert.assertNull("Moet terug gaan naar dezelfde view.", view);
        Assert.assertFalse("Scherm moet nu in bewerk modus zitten.", formBean.isLeesModus());
    }

    /**
     * Toevoegen van rollen.
     */
    @Test
    public void voegAuthenticatiemiddelToe() {
        Partij partij = new Partij(null);
        AuthenticatiemiddelFormBean formBean = new AuthenticatiemiddelFormBean(partij);

        Certificaat certificaatTbvOndertekening = new Certificaat();
        certificaatTbvOndertekening.setSubject("a1");
        Certificaat certificaatTbvSsl = new Certificaat();
        certificaatTbvSsl.setSubject("b1");

        AuthenticatieMiddel authenticatiemiddel = new AuthenticatieMiddel(partij, certificaatTbvOndertekening);
        authenticatiemiddel.setCertificaatTbvSsl(certificaatTbvSsl);

        formBean.setPartij(partij);
        formBean.setAuthenticatiemiddel(authenticatiemiddel);

        BindingResult result = new MapBindingResult(new HashMap<Object, Object>(), "");
        String view = controller.voegAuthenticatiemiddelToe(formBean, result);

        Assert.assertNull("View moet terug gaan naar huidige view.", view);
        Assert.assertEquals("Een authenticatiemiddel moet zijn toegevoegd.", 1, formBean.getPartij().getAuthenticatieMiddels().size());
        Assert.assertTrue("Twee rollen moeten zijn toegevoegd.", formBean.getPartij().getAuthenticatieMiddels().contains(authenticatiemiddel));
    }



    @Test
    @Ignore
    public void testVerwijderthenticatiemiddel() {
        // TODO fix delete met hashset

        Partij partij = new Partij(null);
        AuthenticatiemiddelFormBean authenticatieMiddelFormBean = new AuthenticatiemiddelFormBean(partij);
        Set<AuthenticatieMiddel> authMiddel = new HashSet<AuthenticatieMiddel>();
        AuthenticatieMiddel auth2 = new AuthenticatieMiddel(partij, new Certificaat());

        authMiddel.add(new AuthenticatieMiddel(partij, new Certificaat()));
        authMiddel.add(auth2);
        authMiddel.add(new AuthenticatieMiddel(partij, new Certificaat()));

        authenticatieMiddelFormBean.getPartij().setAuthenticatieMiddels(authMiddel);

        List<String> teVerwijderenAuthmiddelen = Arrays.asList("0", "2");

        authenticatieMiddelFormBean.setTeVerwijderenAuthmiddelen(teVerwijderenAuthmiddelen);

        String result = controller.verwijderAuthenticatiemiddel(authenticatieMiddelFormBean, bindingResult);

        Assert.assertEquals(1, authenticatieMiddelFormBean.getPartij().getAuthenticatieMiddels().size());
        Assert.assertTrue(authenticatieMiddelFormBean.getPartij().getAuthenticatieMiddels().contains(auth2));
        Assert.assertEquals("beheren/partijen/toevoegen/algemeen", result);
    }

    @Test
    public void testOpslaan() {
        Partij partij = new Partij(null);
        AuthenticatiemiddelFormBean formBean = new AuthenticatiemiddelFormBean(partij);
        BindingResult result = new MapBindingResult(new HashMap<Object, Object>(), "");

        String view = controller.opslaan(formBean, result, true);

        Mockito.verify(partijService, Mockito.times(1)).opslaan(partij);

        Mockito.verify(messageUtil, Mockito.times(1)).addMessage(MessageSeverity.SUCCESS, "bericht.zijnopgeslagen",
                new String[] { "entity.Authenticatiemiddel" }, false);

        Assert.assertNull("Moet in de huidige view blijven.", view);
        Assert.assertTrue("Huidige leesmodus moet gezet zijn.", formBean.isLeesModus());
    }
}

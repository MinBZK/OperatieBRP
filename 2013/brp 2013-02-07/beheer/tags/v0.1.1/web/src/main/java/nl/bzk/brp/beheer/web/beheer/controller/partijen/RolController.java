/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.web.beheer.controller.partijen;

import java.util.List;
import java.util.Set;

import nl.bzk.brp.beheer.model.Partijrol;
import nl.bzk.brp.beheer.model.Rol;
import nl.bzk.brp.beheer.web.beheer.partijen.service.PartijService;
import nl.bzk.brp.beheer.web.beheer.stamgegevens.service.StamgegevensService;
import nl.bzk.brp.beheer.web.controller.AbstractController;
import nl.bzk.brp.beheer.web.messages.MessageSeverity;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller voo de rol scherm.
 *
 */
@Controller
@RequestMapping("/beheren/partijen/")
@SessionAttributes({ AbstractController.SESSION_ATTRIBUTE_ID, AbstractController.SESSION_ATTRIBUTE_LEESMODUS,
    AbstractController.SESSION_ATTRIBUTE_COMMAND, "rollen" })
public class RolController extends AbstractController {

    private static final String TOEVOEGEN_PAGINA = "beheren/partijen/rol";

    @Autowired
    private StamgegevensService stamgegevensService;

    @Autowired
    private PartijService       partijService;

    /**
     * Openen van de rol pagina.
     *
     * @param id id van de parent entity
     * @param leesModus de lees modus
     * @param modelMap ModelMap
     * @return view
     */
    @RequestMapping(value = "/rol.html", method = RequestMethod.GET)
    public ModelAndView openToevoegenPagina(@ModelAttribute(SESSION_ATTRIBUTE_ID) final String id,
            @ModelAttribute(SESSION_ATTRIBUTE_LEESMODUS) final boolean leesModus, final ModelMap modelMap)
    {
        if (StringUtils.isBlank(id)) {
            getMessageUtil().addMessage(MessageSeverity.INFO, "bericht.parentnietopgeslagen",
                    new String[] { "entity.Partijrol", "entity.Partij" }, true);

            return redirect("partij.html");
        }

        RolFormBean rolFormBean = new RolFormBean();
        rolFormBean.setLeesModus(leesModus);
        rolFormBean.setPartij(partijService.ophalenPartij(Integer.parseInt(id)));

        modelMap.addAttribute(SESSION_ATTRIBUTE_COMMAND, rolFormBean);

        // Stampgegevens
        modelMap.addAttribute("rollen", stamgegevensService.getRollen());

        return new ModelAndView(TOEVOEGEN_PAGINA);
    }

    /**
     * Zet de raadpleeg modus in bewerk modus.
     *
     * @param rolFormBean FormBean
     * @return view
     */
    @RequestMapping(value = "/rol.html", params = "_wijzigen")
    public String wijzigen(@ModelAttribute(SESSION_ATTRIBUTE_COMMAND) final RolFormBean rolFormBean) {
        rolFormBean.setLeesModus(false);

        return null;
    }

    /**
     * Rollen toevoegen.
     *
     * @param rolFormBean FormBean
     * @param result BindingResult
     * @param modelMap ModelMap
     * @return view
     */
    @RequestMapping(value = "/rol.html", method = RequestMethod.POST, params = "_rolToevoegen")
    public String voegRolToe(@ModelAttribute(SESSION_ATTRIBUTE_COMMAND) final RolFormBean rolFormBean,
            final BindingResult result, final ModelMap modelMap)
    {

        if (rolFormBean.getToeTeVoegenRollen() == null) {
            getMessageUtil().addMessage(MessageSeverity.INFO, "bericht.nietgeselecteerd",
                    new String[] { "entity.Partijrol" }, false);
            return null;
        }

        for (Rol rol : rolFormBean.getToeTeVoegenRollen()) {
            // Maak new partij rol
            Partijrol partijRol = new Partijrol();
            //TODO voorlopig hard-coded
            partijRol.setPartijRolStatusHis("A");
            partijRol.setPartij(rolFormBean.getPartij());
            partijRol.setRol(rol);
            // partijFormBean.getPartijrols().add(partijRol);
            rolFormBean.getPartij().getPartijrols().add(partijRol);
        }

        // Maak new beschikbare rollen lijst
        modelMap.addAttribute("rollen", maakLijstBeschikbareRollen(rolFormBean.getPartij().getPartijrols()));

        return null;
    }

    /**
     * Verwijder rollen.
     *
     * @param rolFormBean FormBean
     * @param result BindingResult
     * @param modelMap ModelMap0
     * @return view
     */
    @RequestMapping(value = "/rol.html", method = RequestMethod.POST, params = "_rolVerwijderen")
    public String verwijderenRol(@ModelAttribute(SESSION_ATTRIBUTE_COMMAND) final RolFormBean rolFormBean,
            final BindingResult result, final ModelMap modelMap)
    {

        if (rolFormBean.getTeVerwijderenRollen() == null) {
            getMessageUtil().addMessage(MessageSeverity.INFO, "bericht.nietgeselecteerd",
                    new String[] { "entity.Partijrol" }, false);
            return null;
        }

        for (String rolId : rolFormBean.getTeVerwijderenRollen()) {
            Partijrol teVerwijderenPr = null;
            for (Partijrol pr : rolFormBean.getPartij().getPartijrols()) {
                if (pr.getRol().getId() == Integer.parseInt(rolId)) {
                    teVerwijderenPr = pr;
                    break;
                }
            }
            rolFormBean.getPartij().getPartijrols().remove(teVerwijderenPr);
        }

        // Maak new beschikbare rollen lijst
        modelMap.addAttribute("rollen", maakLijstBeschikbareRollen(rolFormBean.getPartij().getPartijrols()));

        return null;
    }

    /**
     * Opslaan van rollen.
     *
     * @param rolFormBean FormBean
     * @param result BindingResult
     * @param leesModus de leesModus
     * @return view
     */
    @RequestMapping(value = "/rol.html", method = RequestMethod.POST, params = "_opslaan")
    public String opslaan(@ModelAttribute(SESSION_ATTRIBUTE_COMMAND) final RolFormBean rolFormBean,
            final BindingResult result, @ModelAttribute(SESSION_ATTRIBUTE_LEESMODUS) final boolean leesModus)
    {

        rolFormBean.setPartij(partijService.opslaan(rolFormBean.getPartij()));

        getMessageUtil().addMessage(MessageSeverity.SUCCESS, "bericht.zijnopgeslagen",
                new String[] { "entity.Partijrol" }, false);

        rolFormBean.setLeesModus(leesModus);

        return null;
    }

    /**
     * Maak een lijst van de beschikbare rollen.
     *
     * @param filter de rollen die verwijderd moeten worden
     * @return lijst met uitgefilteerde rollen
     */
    private List<Rol> maakLijstBeschikbareRollen(final Set<Partijrol> filter) {
        List<Rol> rollen = stamgegevensService.getRollen();
        for (Partijrol toegewezenRol : filter) {
            rollen.remove(toegewezenRol.getRol());
        }

        return rollen;
    }

    public void setStamgegevensService(final StamgegevensService stamgegevensService) {
        this.stamgegevensService = stamgegevensService;
    }

    public void setPartijService(final PartijService partijService) {
        this.partijService = partijService;
    }
}

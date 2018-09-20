/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.web.beheer.controller.partijen;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.beheer.model.AuthenticatieMiddel;
import nl.bzk.brp.beheer.web.beheer.partijen.service.PartijService;
import nl.bzk.brp.beheer.web.controller.AbstractController;
import nl.bzk.brp.beheer.web.messages.MessageSeverity;
import nl.bzk.brp.beheer.web.validator.ValidatorUtil;
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
 * Controller voor het beheren van Authenticatiemiddelen.
 *
 */
@Controller
@RequestMapping("/beheren/partijen/")
@SessionAttributes({ AbstractController.SESSION_ATTRIBUTE_ID, AbstractController.SESSION_ATTRIBUTE_LEESMODUS,
    AbstractController.SESSION_ATTRIBUTE_COMMAND })
public class AuthenticatiemiddelController extends AbstractController {

    private static final String TOEVOEGEN_PAGINA = "beheren/partijen/authenticatiemiddel";

    @Autowired
    private PartijService       partijService;

    /**
     * Open van de authenticatiemiddel pagina.
     *
     * @param id van de parent
     * @param leesModus read-only mode
     * @param modelMap ModelMap
     * @return ModelAndView
     */
    @RequestMapping(value = "/authenticatiemiddel.html", method = RequestMethod.GET)
    public ModelAndView openToevoegenPagina(@ModelAttribute(SESSION_ATTRIBUTE_ID) final String id,
            @ModelAttribute(SESSION_ATTRIBUTE_LEESMODUS) final boolean leesModus, final ModelMap modelMap)
    {
        if (StringUtils.isBlank(id)) {
            getMessageUtil().addMessage(MessageSeverity.INFO, "bericht.parentnietopgeslagen",
                    new String[] { "entity.Authenticatiemiddel", "entity.Partij" }, true);
            return redirect("partij.html");
        }

        AuthenticatiemiddelFormBean authenticatiemiddelFormBean =
            new AuthenticatiemiddelFormBean(partijService.ophalenPartij(Integer.parseInt(id)));
        authenticatiemiddelFormBean.setLeesModus(leesModus);

        modelMap.addAttribute("command", authenticatiemiddelFormBean);

        return new ModelAndView(TOEVOEGEN_PAGINA);
    }

    /**
     * Van lees modus omzetten naar wijzig modus.
     *
     * @param authenticatiemiddelFormBean de FormBean
     * @return de view
     */
    @RequestMapping(value = "/authenticatiemiddel.html", params = "_wijzigen")
    public String wijzigen(
            @ModelAttribute(SESSION_ATTRIBUTE_COMMAND) final AuthenticatiemiddelFormBean authenticatiemiddelFormBean)
    {
        authenticatiemiddelFormBean.setLeesModus(false);

        return null;
    }

    /**
     * Voeg authenticatmiddel toe.
     *
     * @param authenticatiemiddelFormBean de FormBean
     * @param result BindingResult
     * @return de view
     */
    @RequestMapping(value = "/authenticatiemiddel.html", method = RequestMethod.POST, params = "_addAuth")
    public String voegAuthenticatiemiddelToe(
            @ModelAttribute(SESSION_ATTRIBUTE_COMMAND) final AuthenticatiemiddelFormBean authenticatiemiddelFormBean,
            final BindingResult result)
    {
        String[] velden =
        { "authenticatiemiddel.certificaatTbvSsl.subject",
          "authenticatiemiddel.certificaatTbvOndertekening.subject" };

        if (ValidatorUtil.isValid(result, authenticatiemiddelFormBean, velden)) {

            // Voeg toe aan de lijst
            authenticatiemiddelFormBean.getPartij().getAuthenticatieMiddels()
                    .add(authenticatiemiddelFormBean.getAuthenticatiemiddel());

            // Maak een nieuwe Authenticatiemiddel object voor nieuwe invoer
            authenticatiemiddelFormBean.maakNieuwAuthenticatieMiddel();
        }

        return null;
    }

    /**
     * Verwijder authenticatiemiddel.
     *
     * @param authenticatiemiddelFormBean de FormBean
     * @param result BindingResult
     * @return de view
     */
    @RequestMapping(value = "/authenticatiemiddel.html", method = RequestMethod.POST, params = "_deleteAuth")
    public String verwijderAuthenticatiemiddel(
            @ModelAttribute(SESSION_ATTRIBUTE_COMMAND) final AuthenticatiemiddelFormBean authenticatiemiddelFormBean,
            final BindingResult result)
    {

        // TODO: deze logica werkt niet gegarandeerd 100% omdat hier een hashset
        // gebruikt wordt, pas dit aan als de uiteindelijk interaction design
        // klaar is
        if (authenticatiemiddelFormBean.getTeVerwijderenAuthmiddelen() == null) {
            getMessageUtil().addMessage(MessageSeverity.INFO, "bericht.nietgeselecteerd",
                    new String[] { "entity.Authenticatiemiddel" }, false);
        } else {
            List<AuthenticatieMiddel> teVerwijderenAuthMiddelen = new ArrayList<AuthenticatieMiddel>();
            if (authenticatiemiddelFormBean.getTeVerwijderenAuthmiddelen().size() > 0) {
                for (String index : authenticatiemiddelFormBean.getTeVerwijderenAuthmiddelen()) {
                    teVerwijderenAuthMiddelen.add((AuthenticatieMiddel) authenticatiemiddelFormBean.getPartij()
                            .getAuthenticatieMiddels().toArray()[Integer.parseInt(index)]);
                }

                authenticatiemiddelFormBean.getPartij().getAuthenticatieMiddels().removeAll(teVerwijderenAuthMiddelen);
            }
        }

        return null;
    }

    /**
     * Opslaan van de invoer.
     *
     * @param authenticatiemiddelFormBean de FormBean
     * @param result BindingResult
     * @param leesModus read-only mode
     * @return de view
     */
    @RequestMapping(value = "/authenticatiemiddel.html", method = RequestMethod.POST, params = "_opslaan")
    public String opslaan(
            @ModelAttribute(SESSION_ATTRIBUTE_COMMAND) final AuthenticatiemiddelFormBean authenticatiemiddelFormBean,
            final BindingResult result, @ModelAttribute(SESSION_ATTRIBUTE_LEESMODUS) final boolean leesModus)
    {

        authenticatiemiddelFormBean.setPartij(partijService.opslaan(authenticatiemiddelFormBean.getPartij()));

        getMessageUtil().addMessage(MessageSeverity.SUCCESS, "bericht.zijnopgeslagen",
                new String[] { "entity.Authenticatiemiddel" }, false);

        authenticatiemiddelFormBean.setLeesModus(leesModus);

        return null;
    }

    public void setPartijService(final PartijService partijService) {
        this.partijService = partijService;
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.web.beheer.controller.partijen;

import nl.bzk.brp.beheer.web.beheer.partijen.service.PartijService;
import nl.bzk.brp.beheer.web.beheer.stamgegevens.service.StamgegevensService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;


/**
 * Controller voo de partij scherm.
 *
 */
@Controller
@RequestMapping("/beheren/partijen/")
@SessionAttributes({ AbstractController.SESSION_ATTRIBUTE_ID, AbstractController.SESSION_ATTRIBUTE_LEESMODUS,
    AbstractController.SESSION_ATTRIBUTE_COMMAND, "srtPartijen", "partijSector" })
public class PartijController extends AbstractController {

    private static final String TOEVOEGEN_PAGINA = "beheren/partijen/partij";

    @Autowired
    private PartijService       partijService;

    @Autowired
    private StamgegevensService stamgegevensService;

    /**
     * Deze method reset de id en zorgt ervoor dat de gebruiker in de toevoegen
     * flow terecht komt.
     *
     * @param modelMap ModelMap
     * @return de view
     */
    @RequestMapping(value = "/partij.html", method = RequestMethod.GET, params = "_nieuw")
    public String openNieuwPartijPagina(final ModelMap modelMap) {
        initialiseer(modelMap);

        return openToevoegenPagina(modelMap);
    }

    /**
     * Deze methode zorgt ervoor dat de gebruiker in de raadpleeg en bewerk flow terecht komt.
     *
     * @param modelMap ModelMap
     * @param id Id van de partij.
     * @return view
     */
    @RequestMapping(value = "/partij.html", method = RequestMethod.GET, params = SESSION_ATTRIBUTE_ID)
    public String openBewerkPartijPagina(final ModelMap modelMap, @RequestParam(value = SESSION_ATTRIBUTE_ID,
                                                                                required = false) final String id)
    {
        modelMap.addAttribute(SESSION_ATTRIBUTE_ID, id);
        modelMap.addAttribute(SESSION_ATTRIBUTE_LEESMODUS, true);

        return openToevoegenPagina(modelMap);
    }

    /**
     * Deze methode wordt aangeroepen wanneer de gebruiker op de toevoegen knop
     * drukt of wanneer de gebruiker via de tabs navigeert.
     *
     * @param modelMap ModelMap
     * @return view
     */
    @RequestMapping(value = "/partij.html", method = RequestMethod.GET)
    public String openToevoegenPagina(final ModelMap modelMap) {
        PartijFormBean partijFormBean = new PartijFormBean();

        // Haal de partij op
        String id = (String) modelMap.get(SESSION_ATTRIBUTE_ID);
        if (StringUtils.isNotBlank(id)) {
            partijFormBean.setPartij(partijService.ophalenPartij(Integer.parseInt(id)));
        } else if (id == null) {
            initialiseer(modelMap);
        }

        partijFormBean.setLeesModus((Boolean) modelMap.get(SESSION_ATTRIBUTE_LEESMODUS));

        modelMap.addAttribute(SESSION_ATTRIBUTE_COMMAND, partijFormBean);

        // Haal stamgegevens op
        modelMap.addAttribute("srtPartijen", stamgegevensService.getSoortPartij());
        modelMap.addAttribute("partijSector", stamgegevensService.getSector());

        return TOEVOEGEN_PAGINA;
    }

    /**
     * Zet de raadpleeg modus in bewerk modus.
     *
     * @param partijFormBean FormBean
     * @return view
     */
    @RequestMapping(value = "/partij.html", params = "_wijzigen")
    public String wijzigen(@ModelAttribute(SESSION_ATTRIBUTE_COMMAND) final PartijFormBean partijFormBean) {
        partijFormBean.setLeesModus(false);

        return null;
    }

    /**
     * Opslaan van de Partij.
     *
     * @param partijFormBean FormBean
     * @param result BindingResult
     * @param modelMap ModelMap
     * @return view
     */
    @RequestMapping(value = "/partij.html", method = RequestMethod.POST, params = "_opslaan")
    public String opslaan(@ModelAttribute(SESSION_ATTRIBUTE_COMMAND) final PartijFormBean partijFormBean,
            final BindingResult result, final ModelMap modelMap)
    {
        // Velden die op de scherm zitten
        String[] velden = { "partij.naam", "partij.dataanv", "partij.dateinde" };

        if (!ValidatorUtil.isValid(result, partijFormBean, velden)) {
            // Terug naar de pagina van submit
            return null;
        }

        // Sla op in database
        partijFormBean.setPartij(partijService.opslaan(partijFormBean.getPartij()));

        // Sla op in sessie
        modelMap.addAttribute(SESSION_ATTRIBUTE_ID, partijFormBean.getPartij().getId().toString());

        getMessageUtil().addMessage(MessageSeverity.SUCCESS, "bericht.isopgeslagen", new String[] { "entity.Partij" },
                false);

        partijFormBean.setLeesModus((Boolean) modelMap.get(SESSION_ATTRIBUTE_LEESMODUS));

        return null;
    }

    /**
     * Initialiseerd the flow.
     *
     * @param modelMap ModelMap
     */
    private void initialiseer(final ModelMap modelMap) {
        modelMap.addAttribute(SESSION_ATTRIBUTE_ID, "");
        modelMap.addAttribute(SESSION_ATTRIBUTE_LEESMODUS, false);
    }

    public void setPartijService(final PartijService partijService) {
        this.partijService = partijService;
    }

    public void setStamgegevensService(final StamgegevensService stamgegevensService) {
        this.stamgegevensService = stamgegevensService;
    }
}

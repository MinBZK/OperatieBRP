/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.web.bevraging;

import java.util.List;

import nl.bzk.brp.model.data.kern.Pers;
import nl.bzk.brp.preview.dataaccess.BerichtenDao;
import nl.bzk.brp.preview.model.Bericht;
import nl.bzk.brp.preview.service.BevragingViaBsnService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


/**
 * De controller die het bevragingscherm aanstuurt.
 *
 *
 */
@Controller
public class BsnOpvraagController {

    private static Logger          logger = LoggerFactory.getLogger(BsnOpvraagController.class);
    @Autowired
    private BerichtenDao           berichtenDao;

    @Autowired
    private BevragingViaBsnService bevragingService;

    /**
     * startpagina voor het zoekscherm.
     *
     * @param model de model
     * @return de string
     */
    @RequestMapping(value = "/bevraging", method = RequestMethod.GET)
    public String search(@RequestParam(value = "bsn", required=false) final String bsn, final ModelMap model) {

        if(bsn != null) {
            Pers persoon = bevragingService.findPersonByBsn(bsn);
            model.addAttribute("bsn", bsn);
            model.addAttribute("persoon", persoon);
            model.addAttribute("nationaliteiten", bevragingService.zoekNationaliteitenOpPersoon(persoon));
            List<Bericht> berichten = berichtenDao.getBerichtenOpBsn(Integer.valueOf(bsn));
            if (berichten.size() > 0) {
                model.addAttribute("berichten", berichten);
            }
        }
        return "toonPersoon";
    }

    /**
     * Haal de persoon op via de BSN in de URL en stop alle gegevens in het model
     *
     * @param bsn de bsn
     * @param model de model
     * @return the persoon
     */
    @RequestMapping(method = RequestMethod.GET, value = "/bevraging/{bsn}")
    public ModelAndView getPersoon(@PathVariable("bsn") final String bsn, final Model model) {

        if (bsn == null) {
            throw new IllegalArgumentException("BSN is verplicht.");
        }

        zoekGegevensVoorBsn(bsn, model);
        return new ModelAndView("toonPersoon", "data", model);
    }

    private void zoekGegevensVoorBsn(final String bsn, final Model model) {
        Pers persoon = bevragingService.findPersonByBsn(bsn);
        model.addAttribute("bsn", bsn);
        model.addAttribute("persoon", persoon);
        List<Bericht> berichten = berichtenDao.getBerichtenOpBsn(Integer.valueOf(bsn));
        if (berichten.size() > 0) {
            model.addAttribute("berichten", berichten);
            logger.debug("Voor BSN " + bsn + " " + berichten.size() + " berichten gevonden");
        } else {
            logger.debug("Geen berichten voor deze BSN gevonden " + bsn);
        }
    }
}

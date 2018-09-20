/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.web.bevraging;

import java.util.HashMap;
import java.util.Map;

import nl.bzk.brp.model.data.kern.Pers;
import nl.bzk.brp.model.data.kern.Persadres;
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
    private BevragingViaBsnService bevragingService;

    /**
     * startpagina voor het zoekscherm.
     *
     * @param model de model
     * @return de string
     */
    @RequestMapping(value = "/bevraging", method = RequestMethod.GET)
    public String search(@RequestParam(value = "bsn", required = false) final String bsn, final ModelMap model) {

        if (bsn != null && isValidBsn(bsn)) {
            Map<String, Object> persoonData = null;
            persoonData = zoekGegevensVoorBsn(bsn);
            model.addAllAttributes(persoonData);
        }
        return "toonPersoon";
    }

    private boolean isValidBsn(final String bsn) {
        try {
            Integer.valueOf(bsn);
        } catch (NumberFormatException ex) {
            logger.debug("Iemand probeert te testen of je iets anders kan intypen dan nummertjes...");
            return false;
        }
        return true;
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

        Map<String, Object> persoonData = null;
        persoonData = zoekGegevensVoorBsn(bsn);
        model.addAllAttributes(persoonData);
        return new ModelAndView("toonPersoon", "data", model);
    }

    private Map<String, Object> zoekGegevensVoorBsn(final String bsn) {
        Map<String, Object> model = new HashMap<String, Object>();
        Pers persoon = bevragingService.findPersonByBsn(bsn);
        model.put("bsn", bsn);
        model.put("persoon", persoon);
        Persadres huidigAdres = bevragingService.findAdresByPers(persoon);
        model.put("adres", huidigAdres);
        model.put("adreshistorie", bevragingService.findHistorischeAdressenByPers(huidigAdres));
        model.put("ouders", bevragingService.findOuders(persoon));
        model.put("kinderen", bevragingService.findKinderen(persoon));
        model.put("partner", bevragingService.findPartner(persoon));
        model.put("verstrekkingsbeperking", bevragingService.findIndicatie(persoon, 3));
        model.put("onderCuratele", bevragingService.findIndicatie(persoon, 2));
        model.put("predikaat", getPredikaat(persoon));
        model.put("adellijkeTitelAanschr", getAdellijkeTitelAanschr(persoon));

        model.put("nationaliteiten", bevragingService.zoekNationaliteitenOpPersoon(persoon));
        // TODO friso: berichten niet hier ophalen maar vanuit browser
        // List<Bericht> berichten = berichtenDao.getBerichtenOpBsn(Integer.valueOf(bsn));
        // if (berichten.size() > 0) {
        // model.put("berichten", berichten);
        // logger.debug("Voor BSN " + bsn + " " + berichten.size() + " berichten gevonden");
        // } else {
        // logger.debug("Geen berichten voor deze BSN gevonden " + bsn);
        // }
        return model;
    }

    private String getPredikaat(final Pers persoon) {
        String resultaat;
        if (persoon == null || persoon.getPredikaataanschr() == null) {
            resultaat = null;
        } else if ("V".equals(persoon.getGeslachtsaand().getCode())) {
            resultaat = persoon.getPredikaataanschr().getNaamvrouwelijk();
        } else {
            resultaat = persoon.getPredikaataanschr().getNaammannelijk();
        }
        return resultaat;
    }

    private String getAdellijkeTitelAanschr(final Pers persoon) {
        String resultaat;
        if (persoon == null || persoon.getAdellijketitelaanschr() == null) {
            resultaat = null;
        } else if ("V".equals(persoon.getGeslachtsaand().getCode())) {
            resultaat = persoon.getAdellijketitelaanschr().getNaamvrouwelijk();
        } else {
            resultaat = persoon.getAdellijketitelaanschr().getNaammannelijk();
        }
        return resultaat;
    }

}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.web.statistieken;

import nl.bzk.brp.preview.model.BerichtStatistiek;
import nl.bzk.brp.preview.service.StatistiekenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 */
@Controller
public class StatistiekenController {

    /** De LOGGER. */
    private static Logger logger = LoggerFactory.getLogger(StatistiekenController.class);

    @Autowired
    private StatistiekenService statistiekenService;


    /**
     * startpagina voor het statistiekscherm.
     *
     * @return de string
     */
    @RequestMapping(value = "/statistieken", method = RequestMethod.GET)
    public String search(final ModelMap model) {

        return "statistieken";
    }

    /**
     * @return de meest recente statistieken
     */
    @RequestMapping(value = "/statistieken/data", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public StatistiekResponse getData() {
        StatistiekResponse resultaat = new StatistiekResponse();
        BerichtStatistiek[] statistieken = statistiekenService.getStatistiek();
        //TODO: hmm, nummertjes als identificatie? Dan in ieder geval enums, kan je die ook in de JSP gebruiken...
        resultaat.setGeboorteStatistiek(statistieken[0]);
        resultaat.setHuwelijkStatistiek(statistieken[1]);
        resultaat.setVerhuizingStatistiek(statistieken[2]);
        resultaat.setAdrescorrectieStatistiek(statistieken[3]);
        logger.info("Statistieken :" + resultaat);
        return resultaat;
    }

}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.web.statistiek;

import nl.bzk.brp.preview.model.BerichtStatistiek;
import nl.bzk.brp.preview.service.StatistiekenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * De controller die het statistiekscherm aanstuurt.
 *
 *
 */
@Controller
public class StatistiekController {

    @Autowired
    private StatistiekenService statistiekenService;

    /**
     * startpagina voor het statistiekscherm.
     *
     * @return de string
     */
    @RequestMapping(value = "/statistiek", method = RequestMethod.GET)
    public String search(final ModelMap model) {

        return "toonStatistiek";
    }

    /**
     * @return de meest recente statistieken
     */
    @RequestMapping(value = "/statistiek/data", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public StatistiekResponse getData() {
        StatistiekResponse resultaat = new StatistiekResponse();
        BerichtStatistiek[] statistieken = statistiekenService.getStatistiek();
        resultaat.setGeboorteStatistiek(statistieken[0]);
        resultaat.setHuwelijkStatistiek(statistieken[1]);
        resultaat.setVerhuizingStatistiek(statistieken[2]);
        resultaat.setAdrescorrectieStatistiek(statistieken[3]);
        return resultaat;
    }

}

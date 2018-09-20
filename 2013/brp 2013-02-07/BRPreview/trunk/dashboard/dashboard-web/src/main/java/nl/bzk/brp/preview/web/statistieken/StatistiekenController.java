/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.web.statistieken;

import java.util.Calendar;
import java.util.Map;

import nl.bzk.brp.preview.model.BerichtStatistiek;
import nl.bzk.brp.preview.model.Berichtsoort;
import nl.bzk.brp.preview.service.StatistiekenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * De Class StatistiekenController, beheert de opvraag van schermen mbt statistieken..
 */
@Controller
public class StatistiekenController {

    /** De LOGGER. */
    private static Logger       logger = LoggerFactory.getLogger(StatistiekenController.class);

    /** De statistieken service. */
    @Autowired
    private StatistiekenService statistiekenService;

    /**
     * startpagina voor het statistiekscherm.
     *
     * @param model de model
     * @return de string
     */
    @RequestMapping(value = "/statistieken", method = RequestMethod.GET)
    public String search(final ModelMap model) {
        return "statistieken";
    }

    /**
     * Startpagina voor het statistiekscherm.
     *
     * @param model de model
     * @param dag de dag
     * @param maand de maand
     * @param jaar de jaar
     * @param uur de uur
     * @param minuut de minuut
     * @param seconde de seconde
     * @return de string
     */
    @RequestMapping(value = "/{dag}/{maand}/{jaar}/{uur}/{minuut}/{seconde}", method = RequestMethod.GET)
    public String search(final ModelMap model, @PathVariable("dag") final int dag,
            @PathVariable("maand") final int maand, @PathVariable("jaar") final int jaar,
            @PathVariable("uur") final int uur, @PathVariable("minuut") final int minuut,
            @PathVariable("seconde") final int seconde)
    {

        model.addAttribute("dag", dag);
        model.addAttribute("maand", maand);
        model.addAttribute("jaar", jaar);
        model.addAttribute("uur", uur);
        model.addAttribute("minuut", minuut);
        model.addAttribute("seconde", seconde);
        return "statistieken";
    }

    /**
     * Haalt data op met tijdstip onbekend.
     *
     * @return de meest recente statistieken
     */
    @RequestMapping(value = "/data", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public StatistiekResponse getData() {
        return getdata(null);
    }

    /**
     * Haalt een data op.
     *
     * @param dag de dag
     * @param maand de maand
     * @param jaar de jaar
     * @param uur de uur
     * @param minuut de minuut
     * @param seconde de seconde
     * @return de meest recente statistieken vanaf een bepaald moment
     */
    @RequestMapping(value = "/data/{dag}/{maand}/{jaar}/{uur}/{minuut}/{seconde}",
                    method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public StatistiekResponse getData(@PathVariable("dag") final int dag,
            @PathVariable("maand") final int maand, @PathVariable("jaar") final int jaar,
            @PathVariable("uur") final int uur, @PathVariable("minuut") final int minuut,
            @PathVariable("seconde") final int seconde)
    {
        logger.debug(String.format("vanaf %s-%s-%s %s:%s:%s", dag, maand, jaar, uur, minuut, seconde));
        Calendar calendar = Calendar.getInstance();
        calendar.set(jaar, maand - 1, dag, uur, minuut, seconde);
        return getdata(calendar);
    }

    /**
     * Haalt een data op.
     *
     * @param calendar de calendar
     * @return data
     */
    private StatistiekResponse getdata(final Calendar calendar) {
        StatistiekResponse resultaat = new StatistiekResponse();
        Map<Berichtsoort, BerichtStatistiek> statistieken = statistiekenService.getStatistiek(calendar);
        resultaat.setGeboorteStatistiek(statistieken.get(Berichtsoort.GEBOORTE));
        resultaat.setHuwelijkStatistiek(statistieken.get(Berichtsoort.HUWELIJK));
        resultaat.setVerhuizingStatistiek(statistieken.get(Berichtsoort.VERHUIZING));
        resultaat.setAdrescorrectieStatistiek(statistieken.get(Berichtsoort.ADRESCORRECTIE));
        return resultaat;
    }

}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.web.restapi;

import java.text.DateFormat;
import java.util.Calendar;

import nl.bzk.brp.preview.model.BerichtenResponse;
import nl.bzk.brp.preview.model.DashboardSettings;
import nl.bzk.brp.preview.service.BerichtenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * REST service om berichten die vanuit de BRP zijn opgeslagen te tonen.
 */
@Controller
public class BerichtenOpvraagController {

    /** De LOGGER. */
    private static Logger     logger = LoggerFactory.getLogger(BerichtenOpvraagController.class);

    /** De settings. */
    @Autowired
    private DashboardSettings settings;

    @Autowired
    private BerichtenService  berichtenService;

    /**
     * @return de meest recente tekstberichten
     */
    @RequestMapping(value = "/berichten", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public BerichtenResponse getBerichten() {
        logger.info("Alle berichten opvragen");
        logger.debug("Gebruik settings object in getBerichten:" + settings.toString());
        BerichtenResponse response = berichtenService.getBerichten();

        return response;

    }

    @RequestMapping(value = "/berichten/{timestamp}", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public BerichtenResponse getBerichten(@PathVariable("timestamp") final Long timestamp) {

        Calendar vanaf = Calendar.getInstance();
        vanaf.setTimeInMillis(timestamp);

        logger.debug("Vraag berichten op sinds:" + DateFormat.getDateTimeInstance().format(vanaf.getTime()));
        BerichtenResponse response = berichtenService.getBerichtenResponseVanaf(vanaf);
        return response;

    }

    @RequestMapping(value = "/berichten/partij/{partij}", method = RequestMethod.GET,
                    headers = "Accept=application/json")
    @ResponseBody
    public BerichtenResponse getBerichten(@PathVariable("partij") String partij) {
        if (partij.contains("Gravenhage") || partij.equalsIgnoreCase("DenHaag")) {
            partij = "'s-Gravenhage";
        }
        BerichtenResponse response = berichtenService.getBerichtenVoorPartij(partij);
        logger.debug("Settings object in getBerichten:" + settings.toString());
        return response;
    }

    @RequestMapping(value = "/berichten/partij/{partij}/{timestamp}", method = RequestMethod.GET,
                    headers = "Accept=application/json")
    @ResponseBody
    public BerichtenResponse getBerichten(@PathVariable("partij") String partij,
            @PathVariable("timestamp") final Long timestamp)
    {
        if (partij.contains("Gravenhage") || partij.equalsIgnoreCase("DenHaag")) {
            partij = "'s-Gravenhage";
        }
        Calendar vanaf = Calendar.getInstance();
        vanaf.setTimeInMillis(timestamp);

        logger.debug("Vraag berichten op sinds:" + DateFormat.getDateTimeInstance().format(vanaf.getTime()));
        BerichtenResponse response = berichtenService.getBerichtenVoorPartijVanaf(partij, vanaf);
        return response;

    }

    @RequestMapping(value = "/berichten/settings/aantalberichten/{berichtenPerPagina}", method = RequestMethod.GET,
                    headers = "Accept=application/json")
    @ResponseBody
    public int changeSettings(@PathVariable("berichtenPerPagina") final Long berichtenPerPagina) {

        logger.debug("Verander maximum aantal berichten per pagina naar:" + berichtenPerPagina + ", was "
            + settings.getMaximaalAantalBerichtenTonen());
        if (berichtenPerPagina != null) {
            settings.setMaximumAantalBerichtenTonen(berichtenPerPagina.intValue());
            berichtenService.setMaximumAantalBerichtenTonen(berichtenPerPagina.intValue());
        }
        return settings.getMaximaalAantalBerichtenTonen();

    }

}

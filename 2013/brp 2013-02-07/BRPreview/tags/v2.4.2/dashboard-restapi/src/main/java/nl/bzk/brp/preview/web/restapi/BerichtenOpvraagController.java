/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.web.restapi;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Set;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * REST service om berichten die vanuit de BRP zijn opgeslagen te tonen.
 */
@Controller
public class BerichtenOpvraagController {

    private static final String GRAVENHAGE   = "Gravenhage";

    private static final String DEN_HAAG     = "DenHaag";

    private static final String S_GRAVENHAGE = "'s-Gravenhage";

    /** De LOGGER. */
    private static Logger       logger       = LoggerFactory.getLogger(BerichtenOpvraagController.class);

    /** De settings. */
    @Autowired
    private DashboardSettings   settings;

    @Autowired
    private BerichtenService    berichtenService;

    /**
     * @return de meest recente tekstberichten
     */
    @RequestMapping(value = "/berichten", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public BerichtenResponse getBerichten() {
        logger.info("Alle berichten opvragen");
        logger.debug("Gebruik settings object in getBerichten:" + settings.toString());
        return berichtenService.getBerichten();
    }

    @RequestMapping(value = "/berichten/bsn/{bsn}", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public BerichtenResponse getBerichtenVoorBsn(@PathVariable("bsn") final String bsn) {

        logger.debug("Vraag berichten van bsn: {}", bsn);
        return berichtenService.getBerichtenVoorBsn(bsn);

    }

    @RequestMapping(value = "/berichten/{timestamp}", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public BerichtenResponse getBerichten(@PathVariable("timestamp") final Long timestamp) {

        Calendar vanaf = Calendar.getInstance();
        vanaf.setTimeInMillis(timestamp);

        logger.debug("Vraag berichten op sinds:" + DateFormat.getDateTimeInstance().format(vanaf.getTime()));
        return berichtenService.getBerichtenResponseVanaf(vanaf);

    }

    @RequestMapping(value = "/berichten/partij/{partij}", method = RequestMethod.GET,
                    headers = "Accept=application/json")
    @ResponseBody
    public BerichtenResponse getBerichten(@PathVariable("partij") final String partij) {
        String gemeente = partij;
        if (gemeente.contains(GRAVENHAGE) || gemeente.equalsIgnoreCase(DEN_HAAG)) {
            gemeente = S_GRAVENHAGE;
        }
        return berichtenService.getBerichtenVoorPartij(gemeente);
    }

    @RequestMapping(value = "/berichten/partij/{partij}/{timestamp}", method = RequestMethod.GET,
                    headers = "Accept=application/json")
    @ResponseBody
    public BerichtenResponse getBerichten(@PathVariable("partij") final String partij,
            @PathVariable("timestamp") final Long timestamp)
    {
        String gemeente = partij;
        if (gemeente.contains(GRAVENHAGE) || gemeente.equalsIgnoreCase(DEN_HAAG)) {
            gemeente = S_GRAVENHAGE;
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

    @RequestMapping(value = "/berichten/bsns", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public List<String> getBsns(@RequestParam("term") final String term) {

        Set<Integer> bsns = berichtenService.getBsns();
        List<String> bsnStrings = new ArrayList<String>();
        for (Integer bsn : bsns) {
            String bsnString = bsn.toString();
            if (bsnString.startsWith(term)) {
                bsnStrings.add(bsnString);
            }
        }
        Collections.sort(bsnStrings);
        return bsnStrings;

    }

}

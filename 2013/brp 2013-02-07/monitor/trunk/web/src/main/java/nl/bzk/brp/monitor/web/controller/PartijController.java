/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.monitor.web.controller;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.domein.ber.Bericht;
import nl.bzk.brp.monitor.domein.BerichtenPerPartij;
import nl.bzk.brp.monitor.domein.repository.BerichtRepository;
import nl.bzk.brp.monitor.domein.repository.PartijRepository;
import nl.bzk.brp.monitor.schedular.model.DataTable;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.displaytag.pagination.PaginatedListImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;


/**
 * Controller voor de berichten per partij schermen.
 *
 */
@Controller
@RequestMapping("/monitor/partij/")
@SessionAttributes("partij")
public class PartijController {

    @Inject
    private PartijRepository  partijRepository;

    @Inject
    private BerichtRepository berichtRepository;

    /**
     * Open de overzicht pagina.
     *
     * @param modelMap ModelMap
     * @return de overzicht scherm
     * @throws IOException problemen met omzetten naar json string
     */
    @RequestMapping(value = "overzicht.html", method = RequestMethod.GET)
    public String openOverzicht(final ModelMap modelMap) throws IOException {
        List<BerichtenPerPartij> berichtenPerPartij = partijRepository.zoekAantalBerichtenPerPartij();

        modelMap.addAttribute("berichtenPerPartij", berichtenPerPartij);

        ObjectMapper mapper = new ObjectMapper();

        DataTable dataTable = new DataTable();
        dataTable.addColumn("partij", "string", "Partij");
        dataTable.addColumn("aantal", "number", "Aantal");

        for (BerichtenPerPartij bp : berichtenPerPartij) {
            dataTable.addRow(bp.getPartijNaam(), bp.getAantal());
        }

        modelMap.addAttribute("jsonData", mapper.writeValueAsString(dataTable));

        return "monitor/partij/overzicht";
    }

    /**
     * Haalt op de berichten van de de partij.
     *
     * @param modelMap ModelMap
     * @param partijId id van de partij
     * @param pagina pagina nummer
     * @return de scherm met de overzicht van de partij
     */
    @RequestMapping(value = "partij.html", method = RequestMethod.GET)
    public String openPartijOverzicht(final ModelMap modelMap, @RequestParam(value = "partijId") final String partijId,
            @RequestParam(value = "page", required = false) final String pagina)
    {
        PaginatedListImpl<Bericht> paginatedList = new PaginatedListImpl<Bericht>();
        if (StringUtils.isBlank(pagina)) {
            paginatedList.setPageNumber(1);
        } else {
            paginatedList.setPageNumber(Integer.valueOf(pagina));
        }

        Integer id = Integer.parseInt(partijId);
        paginatedList.setList(partijRepository.haalOpBerichtenVoorPartij(new PageRequest(
                paginatedList.getPageNumber() - 1, paginatedList.getObjectsPerPage()), id));
        paginatedList.setFullListSize(partijRepository.haalOpAantalBerichtenVoorPartij(id).intValue());

        modelMap.addAttribute("berichten", paginatedList);
        modelMap.addAttribute("partij", partijRepository.findOne(id));

        return "monitor/partij/partij";
    }

    /**
     * Haalt de bericht inhoud op.
     *
     * @param modelMap ModelMap
     * @param berichtId id van bericht
     * @return de scherm met de inhoud van de bericht
     */
    @RequestMapping(value = "bericht.html", method = RequestMethod.GET)
    public String openBericht(final ModelMap modelMap, @RequestParam(value = "berichtId") final String berichtId) {

        modelMap.addAttribute("inkomendeBericht", berichtRepository.findOne(Long.parseLong(berichtId)));
        modelMap.addAttribute("uitgaandeBericht", berichtRepository.zoekUitgaandBericht(Long.parseLong(berichtId)));

        return "monitor/partij/bericht";
    }
}

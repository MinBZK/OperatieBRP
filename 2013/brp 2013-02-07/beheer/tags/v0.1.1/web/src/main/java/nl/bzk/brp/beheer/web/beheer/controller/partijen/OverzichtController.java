/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.web.beheer.controller.partijen;

import nl.bzk.brp.beheer.model.Partij;
import nl.bzk.brp.beheer.web.beheer.partijen.service.PartijService;
import org.apache.commons.lang.StringUtils;
import org.displaytag.pagination.PaginatedListImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * Deze class verzorgt het weergeven van de overzicht scherm.
 *
 */
@Controller
@RequestMapping("/beheren/partijen")
public class OverzichtController {

    private static final String OVERZICHT_PAGINA = "beheren/partijen/overzicht";

    @Autowired
    private PartijService       partijService;

    /**
     * Open de overzicht pagina.
     *
     * @param modelMap ModelMap
     * @return view
     */
    @RequestMapping(value = "/overzicht.html")
    public String openOverzichtPagina(final ModelMap modelMap) {
        PaginatedListImpl<Partij> paginatedList = new PaginatedListImpl<Partij>();
        Partij partij = new Partij(null);

        modelMap.addAttribute("partijen", paginatedList);
        modelMap.addAttribute("command", partij);

        return OVERZICHT_PAGINA;
    }

    /**
     * Zoeken naar partijen.
     *
     * @param partij Partij
     * @param result BindingResult
     * @param modelMap ModelMap
     * @param pagina pagina nummer
     * @return view
     */
    @RequestMapping(value = "/overzicht.html", params = "_zoek")
    public String zoek(@ModelAttribute("command") final Partij partij, final BindingResult result,
            final ModelMap modelMap, @RequestParam("pagina") final String pagina)
    {
        PaginatedListImpl<Partij> paginatedList = new PaginatedListImpl<Partij>();
        if (StringUtils.isBlank(pagina)) {
            paginatedList.setPageNumber(1);
        } else {
            paginatedList.setPageNumber(Integer.valueOf(pagina));
        }

        // TODO refactor, verwijder Partij als zoek object
        modelMap.addAttribute("partijen", partijService.zoekPartij(partij.getNaam(), paginatedList));

        return null;
    }

    /**
     * Naar de volgende pagina gaan.
     *
     * @param partij Partij
     * @param result BindingResult
     * @param modelMap modelMap
     * @param pagina pagina nummer
     * @return view
     */
    @RequestMapping(value = "/overzicht.html", params = "_volgende")
    public String volgendePagina(@ModelAttribute("command") final Partij partij, final BindingResult result,
            final ModelMap modelMap, @RequestParam("pagina") final String pagina)
    {
        int volgendePagina = Integer.valueOf(pagina);
        volgendePagina++;

        return zoek(partij, result, modelMap, Integer.toString(volgendePagina));
    }

    /**
     * Naar de vorige pagina gaan.
     *
     * @param partij Partij
     * @param result BindingResult
     * @param modelMap modelMap
     * @param pagina pagina nummer
     * @return view
     */
    @RequestMapping(value = "/overzicht.html", params = "_vorige")
    public String vorigePagina(@ModelAttribute("command") final Partij partij, final BindingResult result,
            final ModelMap modelMap, @RequestParam("pagina") final String pagina)
    {
        int vorigePagina = Integer.valueOf(pagina);
        vorigePagina--;

        return zoek(partij, result, modelMap, Integer.toString(vorigePagina));
    }

    public void setPartijService(final PartijService partijService) {
        this.partijService = partijService;
    }
}

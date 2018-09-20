/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.web.controller;

import nl.bzk.brp.beheer.web.service.GenericDomeinService;
import org.apache.commons.lang.StringUtils;
import org.displaytag.pagination.PaginatedListImpl;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * Dit is een Abstract controller voor de overzicht pagina, het bevat de basis functionaliteit zoek, volgende pagina en
 * vorige pagina.
 *
 * @param <T> de Basis type
 */
public abstract class AbstractOverzichtController<T> extends AbstractController {

    private final String            overzichtPagina;

    private GenericDomeinService<T> domeinService;

    /**
     * Constructor.
     *
     * @param overzichtViewNaam de view die getoond moet worden
     */
    protected AbstractOverzichtController(final String overzichtViewNaam) {
        overzichtPagina = overzichtViewNaam;
    }

    /**
     * Open de overzicht pagina.
     *
     * @param modelMap ModelMap
     * @return view
     * @throws IllegalAccessException constructor van PersistentType is niet toegankelijk
     * @throws InstantiationException wanneer PersistentType niet geinstantieerd kan worden
     */
    @RequestMapping(value = "/overzicht.html")
    public String openOverzichtPagina(final ModelMap modelMap) throws InstantiationException, IllegalAccessException {
        PaginatedListImpl<T> paginatedList = new PaginatedListImpl<T>();

        modelMap.addAttribute("resultaat", paginatedList);

        return overzichtPagina;
    }

    /**
     * Zoeken naar Bericht.
     *
     * @param zoekterm String
     * @param modelMap ModelMap
     * @param pagina pagina nummer
     * @return view
     */
    @RequestMapping(value = "/overzicht.html", params = "_zoek")
    public String zoek(@RequestParam("zoekterm") final String zoekterm, final ModelMap modelMap,
            @RequestParam("pagina") final String pagina)
    {
        PaginatedListImpl<T> paginatedList = new PaginatedListImpl<T>();
        if (StringUtils.isBlank(pagina)) {
            paginatedList.setPageNumber(1);
        } else {
            paginatedList.setPageNumber(Integer.valueOf(pagina));
        }

        modelMap.addAttribute("resultaat", domeinService.zoekObject(zoekterm, paginatedList));

        return null;
    }

    /**
     * Naar de volgende pagina gaan.
     *
     * @param zoekterm String
     * @param modelMap modelMap
     * @param pagina pagina nummer
     * @return view
     */
    @RequestMapping(value = "/overzicht.html", params = "_volgende")
    public String volgendePagina(@RequestParam("zoekterm") final String zoekterm, final ModelMap modelMap,
            @RequestParam("pagina") final String pagina)
    {
        int volgendePagina = Integer.valueOf(pagina);
        volgendePagina++;

        return zoek(zoekterm, modelMap, Integer.toString(volgendePagina));
    }

    /**
     * Naar de vorige pagina gaan.
     *
     * @param zoekterm String
     * @param modelMap modelMap
     * @param pagina pagina nummer
     * @return view
     */
    @RequestMapping(value = "/overzicht.html", params = "_vorige")
    public String vorigePagina(@RequestParam("zoekterm") final String zoekterm, final ModelMap modelMap,
            @RequestParam("pagina") final String pagina)
    {
        int vorigePagina = Integer.valueOf(pagina);
        vorigePagina--;

        return zoek(zoekterm, modelMap, Integer.toString(vorigePagina));
    }

    public void setDomeinService(final GenericDomeinService<T> domeinService) {
        this.domeinService = domeinService;
    }
}

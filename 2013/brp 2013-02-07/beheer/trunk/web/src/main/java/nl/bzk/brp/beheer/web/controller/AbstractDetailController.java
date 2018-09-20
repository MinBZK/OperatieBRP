/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.web.controller;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;


/**
 * Abstract controller voor de detail pagina's, deze class bevat alleen methode die van toepassing zijn voor de
 * detailController.
 *
 */
public abstract class AbstractDetailController extends AbstractController {

    /** leesModus attribute ten behoeve voor het aangeven of de schermen read only view bevinden. */
    public static final String SESSION_ATTRIBUTE_LEESMODUS = "leesModus";

    /**
     * Deze methode zorgt ervoor dat alle emtpy string waarden van de form submit omgezet worden naar null.
     *
     * @param binder WebDataBinder
     */
    @InitBinder
    public void initBinder(final WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    /**
     * Helper methode om een redirect uit te voeren zonder request parameters.
     *
     * @param url
     *            locatie waarnaar ge-redirect moet worden
     * @return ModelAndView met een RedirectView
     */
    protected ModelAndView redirect(final String url) {
        RedirectView redirectView = new RedirectView(url);
        redirectView.setExposeModelAttributes(false);
        return new ModelAndView(redirectView);
    }
}

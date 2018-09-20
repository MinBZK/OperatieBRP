/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers;

import javax.inject.Inject;
import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import nl.bzk.brp.beheer.webapp.controllers.ErrorHandler.NotFoundException;
import nl.bzk.brp.blobifier.service.BlobifierService;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Persoon controller.
 */
@RestController
@RequestMapping(value = ControllerConstants.PERSOON_VOLLEDIG_URI)
public final class PersoonVolledigController {

    @Inject
    private BlobifierService blobifierService;

    /**
     * Haal een specifiek item op.
     *
     * @param id id
     * @return item
     * @throws NotFoundException wanneer het item niet gevonden kan worden
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public PersoonHisVolledig get(@PathVariable("id") final Integer id) throws NotFoundException {
        final PersoonHisVolledig item = blobifierService.leesBlob(id);
        if (item == null) {
            throw new NotFoundException();
        }
        return item;
    }
}

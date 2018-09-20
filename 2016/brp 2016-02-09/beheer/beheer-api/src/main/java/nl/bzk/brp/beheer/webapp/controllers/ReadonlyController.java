/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers;

import java.io.Serializable;
import java.util.Map;
import nl.bzk.brp.beheer.webapp.controllers.ErrorHandler.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Readonly controller.
 *
 * @param <V> view type
 * @param <I> id type
 */
public interface ReadonlyController<V, I extends Serializable> {

    /**
     * Haal een specifiek item op.
     *
     * @param id id
     * @return item
     * @throws NotFoundException wanneer het item niet gevonden kan worden
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    V get(@PathVariable("id") final I id) throws NotFoundException;

    /**
     * Haal een lijst van items op.
     *
     * @param parameters request parameters
     * @param pageable paginering
     * @return lijst van item (inclusief paginering en sortering)
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    Page<V> list(@RequestParam final Map<String, String> parameters, @PageableDefault(size = 10) final Pageable pageable);
}

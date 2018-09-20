/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers;

import java.io.Serializable;
import nl.bzk.brp.beheer.webapp.controllers.ErrorHandler.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Read-write controller.
 *
 * @param <V> view type
 * @param <I> id type
 */
public interface ReadWriteController<V, I extends Serializable> extends ReadonlyController<V, I> {

    /**
     * Slaat een gewijzigd item op.
     *
     * @param item item
     * @return item
     * @throws NotFoundException wanneer het item niet gevonden kan worden om te updaten
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    V save(@RequestBody final V item) throws NotFoundException;

}

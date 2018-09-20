/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.kern;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.inject.Named;

import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import nl.bzk.brp.beheer.webapp.controllers.AbstractReadWriteController;
import nl.bzk.brp.beheer.webapp.controllers.query.Filter;
import nl.bzk.brp.beheer.webapp.repository.ReadWriteRepository;
import nl.bzk.brp.model.beheer.kern.Aangever;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Aangever controller.
 */
@RestController
@RequestMapping(value = ControllerConstants.AANGEVER_URI)
public final class AangeverController extends AbstractReadWriteController<Aangever, Short> {

    private static final List<String> SORTERINGEN = Arrays.asList("code", "naam", "omschrijving");

    /**
     * Constructor.
     *
     * @param repository repository
     */
    @Autowired
    protected AangeverController(@Named("aangeverRepository") final ReadWriteRepository<Aangever, Short> repository) {
        super(repository, Collections.<Filter<?>>emptyList(), null, SORTERINGEN);
    }

}

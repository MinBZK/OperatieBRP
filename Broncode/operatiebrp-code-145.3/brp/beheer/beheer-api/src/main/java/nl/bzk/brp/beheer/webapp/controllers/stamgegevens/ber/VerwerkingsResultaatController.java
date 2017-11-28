/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.ber;

import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.VerwerkingsResultaat;
import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import nl.bzk.brp.beheer.webapp.controllers.AbstractReadonlyController;
import nl.bzk.brp.beheer.webapp.repository.ReadonlyRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Verwerkingsresultaat controller.
 */
@RestController
@RequestMapping(value = ControllerConstants.VERWERKINGSRESULTAAT_URI)
public final class VerwerkingsResultaatController extends AbstractReadonlyController<VerwerkingsResultaat, Integer> {

    /**
     * Constructor.
     * @param repository repository
     */
    @Inject
    protected VerwerkingsResultaatController(@Named("verwerkingsResultaatRepository") final ReadonlyRepository<VerwerkingsResultaat, Integer> repository) {
        super(repository);
    }

}

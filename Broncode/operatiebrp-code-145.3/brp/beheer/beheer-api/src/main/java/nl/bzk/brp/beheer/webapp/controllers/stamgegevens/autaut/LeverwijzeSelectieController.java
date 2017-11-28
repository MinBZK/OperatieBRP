/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.autaut;

import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.LeverwijzeSelectie;
import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import nl.bzk.brp.beheer.webapp.controllers.AbstractReadonlyController;
import nl.bzk.brp.beheer.webapp.repository.ReadonlyRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Leverwijze Selectie controller.
 */
@RestController
@RequestMapping(value = ControllerConstants.LEVERWIJZE_SELECTIE_URI)
public final class LeverwijzeSelectieController extends AbstractReadonlyController<LeverwijzeSelectie, Integer> {

    /**
     * Constructor.
     * @param repository Leverwijze Selectie repository
     */
    @Inject
    protected LeverwijzeSelectieController(
            @Named("leverwijzeSelectieRepository") final ReadonlyRepository<LeverwijzeSelectie, Integer> repository) {
        super(repository);
    }
}

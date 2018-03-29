/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.verconv;

import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.AanduidingOuder;
import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import nl.bzk.brp.beheer.webapp.controllers.AbstractReadonlyController;
import nl.bzk.brp.beheer.webapp.repository.ReadonlyRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * LO3SoortMelding controller.
 */
@RestController
@RequestMapping(value = ControllerConstants.LO3_SOORT_AANDUIDING_OUDER_URI)
public final class Lo3SoortAanduidingOuderController extends AbstractReadonlyController<AanduidingOuder, Integer> {

    /**
     * Constructor.
     * @param repository repository
     */
    @Inject
    protected Lo3SoortAanduidingOuderController(
        @Named("lo3SoortAanduidingOuderRepository") final ReadonlyRepository<AanduidingOuder, Integer> repository) {
        super(repository);
    }

}

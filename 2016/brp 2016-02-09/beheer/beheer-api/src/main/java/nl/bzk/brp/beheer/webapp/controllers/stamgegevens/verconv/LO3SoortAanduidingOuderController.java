/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.verconv;

import javax.inject.Named;
import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import nl.bzk.brp.beheer.webapp.controllers.AbstractReadonlyController;
import nl.bzk.brp.beheer.webapp.repository.ReadonlyRepository;
import nl.bzk.brp.model.algemeen.stamgegeven.verconv.LO3SoortAanduidingOuder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * LO3SoortMelding controller.
 */
@RestController
@RequestMapping(value = ControllerConstants.LO3_SOORT_AANDUIDING_OUDER_URI)
public final class LO3SoortAanduidingOuderController extends AbstractReadonlyController<LO3SoortAanduidingOuder, Integer> {

    /**
     * Constructor.
     *
     * @param repository repository
     */
    @Autowired
    protected LO3SoortAanduidingOuderController(
        @Named("LO3SoortAanduidingOuderRepository") final ReadonlyRepository<LO3SoortAanduidingOuder, Integer> repository)
    {
        super(repository);
    }

}

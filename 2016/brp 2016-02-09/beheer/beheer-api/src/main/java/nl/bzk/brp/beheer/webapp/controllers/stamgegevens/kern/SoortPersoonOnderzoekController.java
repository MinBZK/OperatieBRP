/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.kern;

import javax.inject.Named;
import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import nl.bzk.brp.beheer.webapp.controllers.AbstractReadonlyController;
import nl.bzk.brp.beheer.webapp.repository.ReadonlyRepository;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonOnderzoek;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SoortPersoonOnderzoek controller.
 */
@RestController
@RequestMapping(value = ControllerConstants.SOORT_PERSOON_ONDERZOEK_URI)
public final class SoortPersoonOnderzoekController extends AbstractReadonlyController<SoortPersoonOnderzoek, Integer> {

    /**
     * Constructor.
     *
     * @param repository repository
     */
    @Autowired
    protected SoortPersoonOnderzoekController(@Named("soortPersoonOnderzoekRepository") final ReadonlyRepository<SoortPersoonOnderzoek, Integer> repository) {
        super(repository);
    }

}

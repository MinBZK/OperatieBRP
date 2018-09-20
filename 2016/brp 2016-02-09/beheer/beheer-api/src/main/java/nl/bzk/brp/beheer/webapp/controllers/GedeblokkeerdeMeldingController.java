/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers;

import java.util.Arrays;
import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import nl.bzk.brp.beheer.webapp.controllers.query.EqualPredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.Filter;
import nl.bzk.brp.beheer.webapp.controllers.query.LongValueAdapter;
import nl.bzk.brp.beheer.webapp.repository.kern.GedeblokkeerdeMeldingRepository;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingGedeblokkeerdeMeldingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller voor {@link AdministratieveHandelingGedeblokkeerdeMeldingModel}.
 */
@RestController
@RequestMapping(value = ControllerConstants.GEDEBLOKKEERDE_MELDING_URI)
public class GedeblokkeerdeMeldingController extends AbstractReadonlyController<AdministratieveHandelingGedeblokkeerdeMeldingModel, Long> {

    private static final String ADMINISTRATIEVE_HANDELING = "administratieveHandeling";
    private static final Filter<?> FILTER_ADMINISTRATIEVE_HANDELING = new Filter<>(
            ADMINISTRATIEVE_HANDELING,
            new LongValueAdapter(),
            new EqualPredicateBuilderFactory(ADMINISTRATIEVE_HANDELING));

    /**
     * Contructor for {@link GedeblokkeerdeMeldingController}.
     *
     * @param repository the repository to use
     */
    @Autowired
    public GedeblokkeerdeMeldingController(final GedeblokkeerdeMeldingRepository repository) {
        super(repository, Arrays.<Filter<?>>asList(FILTER_ADMINISTRATIEVE_HANDELING));
    }
}

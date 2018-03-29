/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.kern;

import java.util.Arrays;
import java.util.Collections;
import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRolHistorie;
import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import nl.bzk.brp.beheer.webapp.controllers.AbstractHistorieVerwerker;
import nl.bzk.brp.beheer.webapp.controllers.AbstractReadWriteController;
import nl.bzk.brp.beheer.webapp.controllers.query.EqualPredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.Filter;
import nl.bzk.brp.beheer.webapp.controllers.query.ShortValueAdapter;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.kern.PartijRolRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller voor PartijRol.
 */
@RestController
@RequestMapping(value = ControllerConstants.PARTIJROL_URI)
public class PartijRolController extends AbstractReadWriteController<PartijRol, Integer> {

    private static final String PARAMETER_PARTIJ = "partij";
    private static final String PARAMETER_PARTIJ_FIELD = "partij.id";
    private static final Filter<?> FILTER_PARTIJ =
            new Filter<>(PARAMETER_PARTIJ, new ShortValueAdapter(), new EqualPredicateBuilderFactory(PARAMETER_PARTIJ_FIELD));
    private static final String PARAMETER_ROL = "rol";
    private static final String PARAMETER_ROL_FIELD = "rol.id";
    private static final Filter<?> FILTER_ROL =
            new Filter<>(PARAMETER_ROL, new ShortValueAdapter(), new EqualPredicateBuilderFactory(PARAMETER_ROL_FIELD));

    /**
     * Constructor.
     * @param repository Partijrol repository
     */
    @Inject
    public PartijRolController(@Named("partijRolRepository") final PartijRolRepository repository) {
        super(repository,
                Arrays.asList(FILTER_PARTIJ, FILTER_ROL),
                Collections.singletonList(new PartijRolHistorieVerwerker()),
                Collections.singletonList("datumIngang"));
    }

    /**
     * Partijrol historie verwerker.
     */
    static final class PartijRolHistorieVerwerker extends AbstractHistorieVerwerker<PartijRol, PartijRolHistorie> {

        @Override
        public Class<PartijRolHistorie> historieClass() {
            return PartijRolHistorie.class;
        }

        @Override
        public Class<PartijRol> entiteitClass() {
            return PartijRol.class;
        }
    }
}

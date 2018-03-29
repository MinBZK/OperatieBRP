/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.kern;

import java.util.Arrays;
import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import nl.bzk.brp.beheer.webapp.controllers.AbstractReadWriteController;
import nl.bzk.brp.beheer.webapp.controllers.ErrorHandler.NotFoundException;
import nl.bzk.brp.beheer.webapp.controllers.query.EqualPredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.Filter;
import nl.bzk.brp.beheer.webapp.controllers.query.LikePredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.ShortValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.StringValueAdapter;
import nl.bzk.brp.beheer.webapp.repository.ReadWriteRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Gemeente controller.
 */
@RestController
@RequestMapping(value = ControllerConstants.GEMEENTE_URI)
public final class GemeenteController extends AbstractReadWriteController<Gemeente, Short> {

    private static final String NAAM = "naam";
    private static final String CODE = "code";
    private static final String PARTIJ = "partij.naam";
    private static final String PARTIJ_ID = "partij.id";
    private static final String VOORTZETTENDE_GEMEENTE = "voortzettendeGemeente.naam";
    private static final String VOORTZETTENDE_GEMEENTE_ID = "voortzettendeGemeente.id";

    private final PartijController partijController;

    /**
     * Constructor.
     * @param repository repository
     * @param partijController controller voor partijen
     */
    @Inject
    protected GemeenteController(@Named("gemeenteRepository") final ReadWriteRepository<Gemeente, Short> repository, final PartijController partijController) {
        super(repository,
                Arrays.asList(
                        new Filter<>(NAAM, new StringValueAdapter(), new LikePredicateBuilderFactory(NAAM)),
                        new Filter<>(CODE, new StringValueAdapter(), new EqualPredicateBuilderFactory(CODE)),
                        new Filter<>(PARTIJ_ID, new ShortValueAdapter(), new EqualPredicateBuilderFactory(PARTIJ_ID)),
                        new Filter<>(VOORTZETTENDE_GEMEENTE_ID, new ShortValueAdapter(), new EqualPredicateBuilderFactory(VOORTZETTENDE_GEMEENTE_ID))),
                null,
                Arrays.asList(NAAM, CODE, PARTIJ, VOORTZETTENDE_GEMEENTE));
        this.partijController = partijController;
    }

    @Override
    protected void wijzigObjectVoorOpslag(final Gemeente item) throws NotFoundException {
        item.setPartij(partijController.get(item.getPartij().getId()));
        if (item.getVoortzettendeGemeente() != null && item.getVoortzettendeGemeente().getId() != null) {
            item.setVoortzettendeGemeente(get(item.getVoortzettendeGemeente().getId()));
        }
    }
}

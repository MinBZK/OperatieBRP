/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.conv;

import java.util.Arrays;
import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RNIDeelnemer;
import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import nl.bzk.brp.beheer.webapp.controllers.AbstractReadWriteController;
import nl.bzk.brp.beheer.webapp.controllers.query.EqualPredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.Filter;
import nl.bzk.brp.beheer.webapp.controllers.query.ShortValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.StringValueAdapter;
import nl.bzk.brp.beheer.webapp.repository.ReadWriteRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ConversieAanduidingInhoudingVermissingReisdocument controller.
 */
@RestController
@RequestMapping(value = ControllerConstants.CONVERSIE_RNI_DEELNEMER_URI)
public final class ConversieRNIDeelnemerController extends AbstractReadWriteController<RNIDeelnemer, Integer> {

    private static final String LO3_OMSCHRIJVING = "lo3CodeRNIDeelnemer";
    private static final String PARTIJ = "partij";
    private static final String PARTIJ_ID = "partij.id";

    /**
     * Constructor.
     *
     * @param repository
     *            repository
     */
    @Inject
    protected ConversieRNIDeelnemerController(@Named("conversieRNIDeelnemerRepository") final ReadWriteRepository<RNIDeelnemer, Integer> repository) {
        super(repository,
              Arrays.asList(
                  new Filter<>(LO3_OMSCHRIJVING, new ShortValueAdapter(), new EqualPredicateBuilderFactory(LO3_OMSCHRIJVING)),
                  new Filter<>(PARTIJ, new StringValueAdapter(), new EqualPredicateBuilderFactory(PARTIJ)),
                  new Filter<>(PARTIJ_ID, new ShortValueAdapter(), new EqualPredicateBuilderFactory(PARTIJ_ID))),
              null,
              Arrays.asList(LO3_OMSCHRIJVING, PARTIJ));
    }

}

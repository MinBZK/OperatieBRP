/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.conv;

import java.util.Arrays;
import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AanduidingInhoudingVermissingReisdocument;
import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import nl.bzk.brp.beheer.webapp.controllers.AbstractReadWriteController;
import nl.bzk.brp.beheer.webapp.controllers.query.EqualPredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.Filter;
import nl.bzk.brp.beheer.webapp.controllers.query.LikePredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.ShortValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.StringValueAdapter;
import nl.bzk.brp.beheer.webapp.repository.ReadWriteRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ConversieAanduidingInhoudingVermissingReisdocument controller.
 */
@RestController
@RequestMapping(value = ControllerConstants.CONVERSIE_AANDUIDING_INHOUDING_VERMISSING_REISDOCUMENT_URI)
public final class ConversieAanduidingInhoudingVermissingReisdocumentController
        extends AbstractReadWriteController<AanduidingInhoudingVermissingReisdocument, Integer> {

    private static final String LO3_AANDUIDING_INHOUDING_OF_VERMISSING_REISDOCUMENT = "lo3AanduidingInhoudingOfVermissingReisdocument";
    private static final String AANDUIDING_INHOUDING_OF_VERMISSING_REISDOCUMENT = "aanduidingInhoudingOfVermissingReisdocument.naam";
    private static final String AANDUIDING_INHOUDING_OF_VERMISSING_REISDOCUMENT_ID = "aanduidingInhoudingOfVermissingReisdocument.id";

    /**
     * Constructor.
     * @param repository repository
     */
    @Inject
    protected ConversieAanduidingInhoudingVermissingReisdocumentController(
        @Named("conversieAanduidingInhoudingVermissingReisdocumentRepository")
        final ReadWriteRepository<AanduidingInhoudingVermissingReisdocument, Integer> repository) {
        super(repository,
                Arrays.asList(
                        new Filter<>(
                                LO3_AANDUIDING_INHOUDING_OF_VERMISSING_REISDOCUMENT,
                                new StringValueAdapter(),
                                new LikePredicateBuilderFactory(LO3_AANDUIDING_INHOUDING_OF_VERMISSING_REISDOCUMENT)),
                        new Filter<>(
                                AANDUIDING_INHOUDING_OF_VERMISSING_REISDOCUMENT_ID,
                                new ShortValueAdapter(),
                                new EqualPredicateBuilderFactory(AANDUIDING_INHOUDING_OF_VERMISSING_REISDOCUMENT_ID))),
                null,
                Arrays.asList(LO3_AANDUIDING_INHOUDING_OF_VERMISSING_REISDOCUMENT, AANDUIDING_INHOUDING_OF_VERMISSING_REISDOCUMENT));
    }
}

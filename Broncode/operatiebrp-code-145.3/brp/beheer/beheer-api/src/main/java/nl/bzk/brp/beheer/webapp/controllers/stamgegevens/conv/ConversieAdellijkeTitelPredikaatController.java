/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.conv;

import java.util.Arrays;
import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdellijkeTitelPredikaat;
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
 * ConversieAdellijkeTitelPredikaat controller.
 */
@RestController
@RequestMapping(value = ControllerConstants.CONVERSIE_ADELLIJKE_TITEL_PREDIKAAT_URI)
public final class ConversieAdellijkeTitelPredikaatController extends AbstractReadWriteController<AdellijkeTitelPredikaat, Integer> {

    private static final String LO3_OMSCHRIJVING = "lo3AdellijkeTitelPredikaat";
    private static final String GESLACHTSAANDUIDING = "geslachtsaanduidingId";
    private static final String ADELLIJKE_TITEL = "adellijkeTitelId";
    private static final String PREDIKAAT = "predikaatId";

    /**
     * Constructor.
     * @param repository repository
     */
    @Inject
    protected ConversieAdellijkeTitelPredikaatController(
        @Named("conversieAdellijkeTitelPredikaatRepository") final ReadWriteRepository<AdellijkeTitelPredikaat, Integer> repository) {
        super(repository,
                Arrays.asList(
                        new Filter<>(LO3_OMSCHRIJVING, new StringValueAdapter(), new LikePredicateBuilderFactory(LO3_OMSCHRIJVING)),
                        new Filter<>(GESLACHTSAANDUIDING, new ShortValueAdapter(), new EqualPredicateBuilderFactory(GESLACHTSAANDUIDING)),
                        new Filter<>(ADELLIJKE_TITEL, new ShortValueAdapter(), new EqualPredicateBuilderFactory(ADELLIJKE_TITEL)),
                        new Filter<>(PREDIKAAT, new ShortValueAdapter(), new EqualPredicateBuilderFactory(PREDIKAAT))),
                null,
                Arrays.asList(LO3_OMSCHRIJVING, GESLACHTSAANDUIDING, ADELLIJKE_TITEL, PREDIKAAT));
    }

}

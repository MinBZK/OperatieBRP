/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.kern;

import java.util.Arrays;
import java.util.List;

import javax.inject.Named;

import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import nl.bzk.brp.beheer.webapp.controllers.AbstractReadWriteController;
import nl.bzk.brp.beheer.webapp.controllers.query.AttribuutValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.EqualPredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.Filter;
import nl.bzk.brp.beheer.webapp.controllers.query.ShortValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.StringValueAdapter;
import nl.bzk.brp.beheer.webapp.repository.ReadWriteRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PredicaatCodeAttribuut;
import nl.bzk.brp.model.beheer.kern.Predicaat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Predicaat controller.
 */
@RestController
@RequestMapping(value = ControllerConstants.PREDICAAT_URI)
public final class PredicaatController extends AbstractReadWriteController<Predicaat, Short> {

    private static final String CODE = "code";
    private static final Filter<?> FILTER_ID = new Filter<>("filterId", new ShortValueAdapter(), new EqualPredicateBuilderFactory("iD"));
    private static final Filter<?> FILTER_CODE = new Filter<>("filterCode", new AttribuutValueAdapter<>(
        new StringValueAdapter(),
        PredicaatCodeAttribuut.class), new EqualPredicateBuilderFactory(CODE));

    private static final List<String> SORTERINGEN = Arrays.asList(CODE, "naamMannelijk", "naamVrouwelijk");

    /**
     * Constructor.
     *
     * @param repository predicaat repository
     */
    @Autowired
    protected PredicaatController(@Named("predicaatRepository") final ReadWriteRepository<Predicaat, Short> repository) {
        super(repository, Arrays.<Filter<?>>asList(FILTER_ID, FILTER_CODE), null, SORTERINGEN);
    }
}

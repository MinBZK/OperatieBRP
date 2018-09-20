/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.conv;

import java.util.Arrays;
import java.util.List;

import javax.inject.Named;

import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import nl.bzk.brp.beheer.webapp.controllers.AbstractReadonlyController;
import nl.bzk.brp.beheer.webapp.controllers.query.AttribuutValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.EqualPredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.Filter;
import nl.bzk.brp.beheer.webapp.controllers.query.StringValueAdapter;
import nl.bzk.brp.beheer.webapp.repository.ReadonlyRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.conv.ConversieLO3RubriekNaamAttribuut;
import nl.bzk.brp.model.beheer.conv.ConversieLO3Rubriek;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Conversie LO3 Rubriek controller.
 */
@RestController
@RequestMapping(value = ControllerConstants.CONVERSIE_LO3_RUBRIEK_URI)
public class ConversieLO3RubriekController extends AbstractReadonlyController<ConversieLO3Rubriek, Integer> {

    private static final String NAAM = "naam";
    private static final List<String> SORTERINGEN = Arrays.asList(NAAM);

    private static final Filter<?> FILTER_NAAM = new Filter<>(NAAM, new AttribuutValueAdapter<>(
        new StringValueAdapter(),
        ConversieLO3RubriekNaamAttribuut.class), new EqualPredicateBuilderFactory(NAAM));

    /**
     * Constructor.
     *
     * @param repository repository
     */
    @Autowired
    protected ConversieLO3RubriekController(@Named("conversieLO3RubriekRepository") final ReadonlyRepository<ConversieLO3Rubriek, Integer> repository) {
        super(repository, Arrays.<Filter<?>>asList(FILTER_NAAM), SORTERINGEN);
    }
}

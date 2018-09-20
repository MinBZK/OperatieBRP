/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.conv;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.inject.Named;
import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import nl.bzk.brp.beheer.webapp.controllers.AbstractReadWriteController;
import nl.bzk.brp.beheer.webapp.controllers.HistorieVerwerker;
import nl.bzk.brp.beheer.webapp.controllers.query.Filter;
import nl.bzk.brp.beheer.webapp.repository.ReadWriteRepository;
import nl.bzk.brp.model.beheer.conv.ConversieVoorvoegsel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ConversieVoorvoegsel controller.
 */
@RestController
@RequestMapping(value = ControllerConstants.CONVERSIE_VOORVOEGSEL_URI)
public final class ConversieVoorvoegselController extends AbstractReadWriteController<ConversieVoorvoegsel, Integer> {

    private static final List<String> SORTERINGEN = Arrays.asList("rubriek0231Voorvoegsel", "voorvoegsel", "scheidingsteken");
    /**
     * Constructor.
     *
     * @param repository repository
     */
    @Autowired
    protected ConversieVoorvoegselController(@Named("conversieVoorvoegselRepository") final ReadWriteRepository<ConversieVoorvoegsel, Integer> repository) {
        super(repository, Collections.<Filter<?>>emptyList(), Collections.<HistorieVerwerker<ConversieVoorvoegsel, ?>>emptyList(), SORTERINGEN);
    }

}

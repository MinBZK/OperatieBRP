/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.conv;

import java.util.Collections;
import javax.inject.Named;
import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import nl.bzk.brp.beheer.webapp.controllers.AbstractReadWriteController;
import nl.bzk.brp.beheer.webapp.controllers.query.Filter;
import nl.bzk.brp.beheer.webapp.repository.ReadWriteRepository;
import nl.bzk.brp.model.beheer.conv.ConversieAanduidingInhoudingVermissingReisdocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ConversieAanduidingInhoudingVermissingReisdocument controller.
 */
@RestController
@RequestMapping(value = ControllerConstants.CONVERSIE_AANDUIDING_INHOUDING_VERMISSING_REISDOCUMENT_URI)
public final class ConversieAanduidingInhoudingVermissingReisdocumentController extends
    AbstractReadWriteController<ConversieAanduidingInhoudingVermissingReisdocument, Integer>
{

    /**
     * Constructor.
     *
     * @param repository repository
     */
    @Autowired
    protected ConversieAanduidingInhoudingVermissingReisdocumentController(
        @Named("conversieAanduidingInhoudingVermissingReisdocumentRepository")
        final ReadWriteRepository<ConversieAanduidingInhoudingVermissingReisdocument, Integer> repository)
    {
        super(repository, Collections.<Filter<?>>emptyList());
    }
}

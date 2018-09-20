/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers;

import java.util.Arrays;
import java.util.List;

import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import nl.bzk.brp.beheer.webapp.controllers.query.AttribuutValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.EnumValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.EqualPredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.Filter;
import nl.bzk.brp.beheer.webapp.controllers.query.IntegerValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.LongValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.ShortValueAdapter;
import nl.bzk.brp.beheer.webapp.repository.kern.ActieRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.operationeel.kern.ActieModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ActieController voor inzage acties.
 *
 **/
@RestController
@RequestMapping(value = ControllerConstants.ACTIE_URI)
public class ActieController extends AbstractReadonlyController<ActieModel, Long> {

    /** Parameter om te filteren op: administratieve handeling. */
    public static final String PARAMETER_ADMINISTRATIEVE_HANDELING = "administratieveHandeling";

    private static final String DATUM_ONTLENING = "datumOntlening";
    private static final String ADMINISTRATIEVE_HANDELING = PARAMETER_ADMINISTRATIEVE_HANDELING;
    private static final String SOORT = "soort";
    private static final String TIJDSTIP_REGISTRATIE = "tijdstipRegistratie";

    private static final Filter<?> FILTER_ADMINISTRATIEVE_HANDELING = new Filter<>(
        ADMINISTRATIEVE_HANDELING,
        new LongValueAdapter(),
        new EqualPredicateBuilderFactory(ADMINISTRATIEVE_HANDELING));
    private static final Filter<?> FILTER_DATUM_ONTLENING = new Filter<>(DATUM_ONTLENING, new AttribuutValueAdapter<>(
        new IntegerValueAdapter(),
        DatumEvtDeelsOnbekendAttribuut.class), new EqualPredicateBuilderFactory(DATUM_ONTLENING));

    private static final Filter<?> FILTER_PARTIJ = new Filter<>("partij", new ShortValueAdapter(), new EqualPredicateBuilderFactory("partij.waarde.iD"));
    private static final Filter<?> FILTER_SOORT = new Filter<>(SOORT, new AttribuutValueAdapter<>(
        new EnumValueAdapter<>(SoortActie.class),
        SoortActieAttribuut.class), new EqualPredicateBuilderFactory(SOORT));
    private static final List<String> SORTERINGEN = Arrays.asList(SOORT, TIJDSTIP_REGISTRATIE);

    /**
     * Constructor voor ActieController.
     *
     * @param repository
     *            actie repository
     */
    @Autowired
    public ActieController(final ActieRepository repository) {
        super(repository, Arrays.<Filter<?>>asList(FILTER_ADMINISTRATIEVE_HANDELING, FILTER_DATUM_ONTLENING, FILTER_PARTIJ, FILTER_SOORT), SORTERINGEN);
    }

}

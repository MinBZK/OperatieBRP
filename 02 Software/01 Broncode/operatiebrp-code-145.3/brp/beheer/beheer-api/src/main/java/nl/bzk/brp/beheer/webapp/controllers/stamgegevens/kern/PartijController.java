/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.kern;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijBijhoudingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijVrijBerichtHistorie;
import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import nl.bzk.brp.beheer.webapp.controllers.AbstractHistorieVerwerker;
import nl.bzk.brp.beheer.webapp.controllers.AbstractReadWriteController;
import nl.bzk.brp.beheer.webapp.controllers.ErrorHandler;
import nl.bzk.brp.beheer.webapp.controllers.ErrorHandler.NotFoundException;
import nl.bzk.brp.beheer.webapp.controllers.ReadWriteController;
import nl.bzk.brp.beheer.webapp.controllers.query.BooleanValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.EqualPredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.Filter;
import nl.bzk.brp.beheer.webapp.controllers.query.IntegerValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.LessOrEqualPredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.LikePredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.ShortValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.StringValueAdapter;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.kern.PartijRepository;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.kern.SoortPartijRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Partij controller.
 */
@RestController
@RequestMapping(value = ControllerConstants.PARTIJ_URI)
public class PartijController extends AbstractReadWriteController<Partij, Short> {

    private static final String NAAM = "naam";
    private static final String CODE = "code";
    private static final String DATUM_INGANG = "datumIngang";
    private static final String DATUM_EINDE = "datumEinde";
    private static final String AUTOMATISCH_FIATTEREN = "indicatieAutomatischFiatteren";
    private static final String DATUM_OVERGANG_BRP = "datumOvergangNaarBrp";
    private static final String PARTIJ_ROL = "partijRolSet.rolId";

    private static final Filter<?> FILTER_CODE = new Filter<>("filterCode", new StringValueAdapter(), new EqualPredicateBuilderFactory(CODE));
    private static final Filter<?> FILTER_NAAM = new Filter<>("filterNaam", new StringValueAdapter(), new LikePredicateBuilderFactory(NAAM));
    private static final Filter<?> FILTER_OIN = new Filter<>("filterOin", new StringValueAdapter(), new LikePredicateBuilderFactory("oin"));
    private static final Filter<?> FILTER_SOORT = new Filter<>("filterSoort", new ShortValueAdapter(), new EqualPredicateBuilderFactory("soortPartij.id"));
    private static final Filter<?> FILTER_ROL = new Filter<>("filterPartijRol", new ShortValueAdapter(), new EqualPredicateBuilderFactory(PARTIJ_ROL));
    private static final Filter<?> FILTER_DATUM_INGANG =
            new Filter<>("filterDatumIngang", new IntegerValueAdapter(), new EqualPredicateBuilderFactory(DATUM_INGANG));
    private static final Filter<?> FILTER_DATUM_EINDE =
            new Filter<>("filterDatumEinde", new IntegerValueAdapter(), new EqualPredicateBuilderFactory(DATUM_EINDE));
    private static final Filter<?> FILTER_DATUM_OVERGANG_BRP =
            new Filter<>("filterDatumOvergangNaarBrp", new IntegerValueAdapter(), new LessOrEqualPredicateBuilderFactory<>(DATUM_OVERGANG_BRP));
    private static final Filter<?> FILTER_INDICATIE =
            new Filter<>("filterIndicatie", new BooleanValueAdapter(), new EqualPredicateBuilderFactory("indicatieVerstrekkingsbeperkingMogelijk"));
    private static final Filter<?> FILTER_AUTOMATISCH_FIATTEREN =
            new Filter<>("filterIndicatieAutomatischFiatteren", new BooleanValueAdapter(), new EqualPredicateBuilderFactory(AUTOMATISCH_FIATTEREN));

    private static final List<String> SORTERINGEN = Arrays.asList(CODE, NAAM, DATUM_INGANG, DATUM_EINDE);

    private final SoortPartijRepository soortPartijRepository;

    private final ReadWriteController<PartijRol, Integer> partijRolController;

    /**
     * Constructor.
     * @param repository repository
     * @param soortPartijRepository repository voor soort partij
     * @param partijRolController controller voor partijrol
     */
    @Inject
    protected PartijController(final PartijRepository repository, final SoortPartijRepository soortPartijRepository,
                               final ReadWriteController<PartijRol, Integer> partijRolController) {
        super(repository,
                Arrays.asList(
                        FILTER_CODE,
                        FILTER_NAAM,
                        FILTER_OIN,
                        FILTER_SOORT,
                        FILTER_DATUM_INGANG,
                        FILTER_DATUM_EINDE,
                        FILTER_INDICATIE,
                        FILTER_DATUM_OVERGANG_BRP,
                        FILTER_ROL,
                        FILTER_AUTOMATISCH_FIATTEREN
                ),
                Arrays.asList(new PartijHistorieVerwerker(), new PartijBijhoudingHistorieVerwerker(), new PartijVrijBerichtHistorieVerwerker()),
                SORTERINGEN);
        this.soortPartijRepository = soortPartijRepository;
        this.partijRolController = partijRolController;
    }

    @Override
    protected final void wijzigObjectVoorOpslag(final Partij partij) throws ErrorHandler.NotFoundException {
        if (partij.getSoortPartij() != null && partij.getSoortPartij().getId() != null) {
            partij.setSoortPartij(soortPartijRepository.getOne(partij.getSoortPartij().getId()));
        }

        // Lege OIN's mogen niet, sla deze als null op.
        if ("".equals(partij.getOin())) {
            partij.setOin(null);
        }

        // Indicatieverstrekkingsbeperkingmogelijk mag niet leeg zijn, zet deze op FALSE indien leeg.
        if (partij.isIndicatieVerstrekkingsbeperkingMogelijk() == null) {
            partij.setIndicatieVerstrekkingsbeperkingMogelijk(Boolean.FALSE);
        }

    }

    /**
     * Haal een specifiek partijrol op.
     * @param id id
     * @param partijRolId partijRolId
     * @return item
     * @throws ErrorHandler.NotFoundException wanneer het item niet gevonden kan worden
     */
    @RequestMapping(value = "/{id}/partijrollen/{pid}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public final PartijRol getPartijRol(@PathVariable("id") final Long id, @PathVariable("pid") final Integer partijRolId)
            throws ErrorHandler.NotFoundException {
        return partijRolController.get(partijRolId);
    }

    /**
     * Haal een lijst van partijrollen op.
     * @param id id van actie
     * @param parameters request parameters
     * @param pageable paginering
     * @return lijst van partijrol (inclusief paginering en sortering)
     */
    @RequestMapping(value = "/{id}/partijrollen", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public final Page<PartijRol> listPartijRol(
            @PathVariable(value = "id") final String id,
            @RequestParam final Map<String, String> parameters,
            @PageableDefault(size = 10) @SortDefault(sort = "datumIngang", direction = Sort.Direction.ASC) final Pageable pageable) {
        parameters.put("partij", id);
        return partijRolController.list(parameters, pageable);
    }

    /**
     * Bewaar een partijrol.
     * @param partijId Id van de partij
     * @param item request body
     * @return item
     * @throws ErrorHandler.NotFoundException indien Partij niet gevonden wordt.
     */
    @RequestMapping(value = "/{id}/partijrollen", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public final PartijRol savePartijRol(@PathVariable(value = "id") final Integer partijId, @Validated @RequestBody final PartijRol item)
            throws NotFoundException {
        item.setPartij(get(partijId.shortValue()));
        return partijRolController.save(item);
    }

    /**
     * Partij historie verwerker.
     */
    static final class PartijHistorieVerwerker extends AbstractHistorieVerwerker<Partij, PartijHistorie> {

        @Override
        public Class<PartijHistorie> historieClass() {
            return PartijHistorie.class;
        }

        @Override
        public Class<Partij> entiteitClass() {
            return Partij.class;
        }
    }

    /**
     * PartijBijhouding historie verwerker.
     */
    static final class PartijBijhoudingHistorieVerwerker extends AbstractHistorieVerwerker<Partij, PartijBijhoudingHistorie> {

        @Override
        public Class<PartijBijhoudingHistorie> historieClass() {
            return PartijBijhoudingHistorie.class;
        }

        @Override
        public Class<Partij> entiteitClass() {
            return Partij.class;
        }
    }

    /**
     * PartijVrijBericht historie verwerker.
     */
    static final class PartijVrijBerichtHistorieVerwerker extends AbstractHistorieVerwerker<Partij, PartijVrijBerichtHistorie> {

        @Override
        public Class<PartijVrijBerichtHistorie> historieClass() {
            return PartijVrijBerichtHistorie.class;
        }

        @Override
        public Class<Partij> entiteitClass() {
            return Partij.class;
        }
    }
}

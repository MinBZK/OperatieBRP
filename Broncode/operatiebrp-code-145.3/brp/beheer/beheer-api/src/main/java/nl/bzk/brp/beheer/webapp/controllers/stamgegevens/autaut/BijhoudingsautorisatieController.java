/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.autaut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.alaag.ALaagAfleidingsUtil;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Bijhoudingsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BijhoudingsautorisatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BijhoudingsautorisatieSoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangBijhoudingsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import nl.bzk.brp.beheer.webapp.controllers.AbstractHistorieVerwerker;
import nl.bzk.brp.beheer.webapp.controllers.AbstractReadWriteController;
import nl.bzk.brp.beheer.webapp.controllers.ErrorHandler;
import nl.bzk.brp.beheer.webapp.controllers.ErrorHandler.NotFoundException;
import nl.bzk.brp.beheer.webapp.controllers.ReadWriteController;
import nl.bzk.brp.beheer.webapp.controllers.SaveTransaction;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.autaut.BijhoudingsautorisatieRepository;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.autaut.BijhoudingsautorisatieSoortAdministratieveHandelingRepository;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.kern.SoortAdministratieveHandelingRepository;
import nl.bzk.brp.beheer.webapp.view.BijhoudingsautorisatieSoortAdministratieveHandelingView;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
 * BijhoudingsautorisatieController voor beheer.
 */
@RestController
@RequestMapping(value = ControllerConstants.BIJHOUDINGSAUTORISATIE_URI)
public class BijhoudingsautorisatieController extends AbstractReadWriteController<Bijhoudingsautorisatie, Integer> {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final String INDICATIE_GEBLOKKEERD = "indicatieGeblokkeerd";
    private static final String NAAM = "naam";
    private static final String DATUM_INGANG = "datumIngang";
    private static final String DATUM_EINDE = "datumEinde";

    private static final List<String> SORTERINGEN = Arrays.asList(NAAM, INDICATIE_GEBLOKKEERD, DATUM_INGANG, DATUM_EINDE);
    private final BijhoudingsautorisatieSoortAdministratieveHandelingRepository bijhoudingsautorisatieSoortAdministratieveHandelingRepository;
    private final ReadWriteController<ToegangBijhoudingsautorisatie, Integer> toegangBijhoudingsautorisatieController;
    private final BijhoudingsautorisatieSoortAdministratieveHandelingController bijhoudingsautorisatieSoortAdministratieveHandelingController;
    private SoortAdministratieveHandelingRepository soortAdministratieveHandelingRepository = new SoortAdministratieveHandelingRepository();

    /**
     * Constructor voor BijhoudingsautorisatieController.
     * @param repository repo
     * @param bijhoudingsautorisatieSoortAdministratieveHandelingRepository repo bijhoudingsautorisatie
     * @param toegangBijhoudingsautorisatieController repo toegang bijhoudingsautorisatie
     * @param bijhoudingsautorisatieSoortAdministratieveHandelingController repo soort administratieve handeling
     */
    @Inject
    public BijhoudingsautorisatieController(final BijhoudingsautorisatieRepository repository,
                                            final BijhoudingsautorisatieSoortAdministratieveHandelingRepository
                                                    bijhoudingsautorisatieSoortAdministratieveHandelingRepository,
                                            final ReadWriteController<ToegangBijhoudingsautorisatie, Integer> toegangBijhoudingsautorisatieController,
                                            final BijhoudingsautorisatieSoortAdministratieveHandelingController
                                                    bijhoudingsautorisatieSoortAdministratieveHandelingController) {
        super(repository,
                Arrays.asList(
                        AutorisatieFilterFactory.getFilterNaam(),
                        AutorisatieFilterFactory.getFilterDatumIngang(),
                        AutorisatieFilterFactory.getFilterDatumEinde(),
                        AutorisatieFilterFactory.getFilterIndicatieModelAutorisatie(),
                        BijhoudingsautorisatieFilterFactory.getFilterGeautoriseerdePartij(),
                        BijhoudingsautorisatieFilterFactory.getFilterTransporteurPartij(),
                        BijhoudingsautorisatieFilterFactory.getFilterOndertekenaarPartij(),
                        AutorisatieFilterFactory.getFilterGeblokkeerd()),
                Collections.singletonList(new BijhoudingsautorisatieHistorieVerwerker()),
                SORTERINGEN);
        this.bijhoudingsautorisatieSoortAdministratieveHandelingRepository = bijhoudingsautorisatieSoortAdministratieveHandelingRepository;
        this.toegangBijhoudingsautorisatieController = toegangBijhoudingsautorisatieController;
        this.bijhoudingsautorisatieSoortAdministratieveHandelingController = bijhoudingsautorisatieSoortAdministratieveHandelingController;
    }

    /**
     * Haal een specifiek item op.
     * @param id id
     * @param toegangLeveringsautorisatieId id van toegang
     * @return item
     * @throws NotFoundException wanneer het item niet gevonden kan worden
     */
    @RequestMapping(value = "/{id}/toegangbijhoudingsautorisaties/{tid}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public final ToegangBijhoudingsautorisatie getToegangBijhoudingsautorisatie(
            @PathVariable("id") final Integer id,
            @PathVariable("tid") final Integer toegangLeveringsautorisatieId) throws ErrorHandler.NotFoundException {
        return toegangBijhoudingsautorisatieController.get(toegangLeveringsautorisatieId);
    }

    /**
     * Haal een lijst van items op.
     * @param id id van Leveringsautorisatie
     * @param parameters request parameters
     * @param pageable paginering
     * @return lijst van item (inclusief paginering en sortering)
     */
    @RequestMapping(value = "/{id}/toegangbijhoudingsautorisaties", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public final Page<ToegangBijhoudingsautorisatie> listToegangLeveringsautorisatie(
            @PathVariable(value = "id") final String id,
            @RequestParam final Map<String, String> parameters,
            @PageableDefault(size = 10) @SortDefault(sort = {DATUM_INGANG, DATUM_EINDE}, direction = Sort.Direction.ASC) final Pageable pageable) {
        parameters.put(ToegangBijhoudingsautorisatieController.PARAMETER_FILTER_BIJHOUDINGSAUTORISATIE, id);
        return toegangBijhoudingsautorisatieController.list(parameters, pageable);
    }

    /**
     * Slaat een gewijzigd item op.
     * @param id id
     * @param item item
     * @return item
     * @throws NotFoundException wanneer het item niet gevonden kan worden om te updaten
     */
    @RequestMapping(value = "/{id}/toegangbijhoudingsautorisaties", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public final ToegangBijhoudingsautorisatie saveToegangBijhoudingsautorisatie(
            @PathVariable("id") final Integer id,
            @Validated @RequestBody final ToegangBijhoudingsautorisatie item) throws NotFoundException {
        item.setBijhoudingsautorisatie(get(id));
        return toegangBijhoudingsautorisatieController.save(item);
    }

    /**
     * Slaat een gewijzigd item op.
     * @param bijhoudingsautorisatieId Id van de bijhoudingsautorisatie
     * @param item item
     * @return item
     * @throws NotFoundException wanneer het item niet gevonden kan worden om te updaten
     */
    @RequestMapping(value = "/{id}/bijhoudingsautorisatieSoortAdministratieveHandelingen", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public final Page<BijhoudingsautorisatieSoortAdministratieveHandelingView> saveBijhoudingsautorisatieSoortAdministratieveHandeling(
            @PathVariable("id") final Integer bijhoudingsautorisatieId,
            @RequestBody final BijhoudingsautorisatieSoortAdministratieveHandelingView item) throws NotFoundException {

        return new SaveTransaction<Page<BijhoudingsautorisatieSoortAdministratieveHandelingView>>(getTransactionTemplate())
                .execute(() -> saveSoortAdministratieveHandeling(bijhoudingsautorisatieId, item));
    }

    private Page<BijhoudingsautorisatieSoortAdministratieveHandelingView> saveSoortAdministratieveHandeling(
            final Integer bijhoudingsautorisatieId,
            final BijhoudingsautorisatieSoortAdministratieveHandelingView item) throws NotFoundException {
        final Bijhoudingsautorisatie bijhoudingsautorisatie = get(bijhoudingsautorisatieId);
        final BijhoudingsautorisatieSoortAdministratieveHandeling handeling;
        if (item.getId() != null) {
            handeling = bijhoudingsautorisatieSoortAdministratieveHandelingController.get(item.getId());
            if (!item.isActief()) {
                FormeleHistorieZonderVerantwoording.sluit(handeling.getBijhoudingsautorisatieSoortAdministratieveHandelingHistorieSet());
                ALaagAfleidingsUtil.leegALaag(handeling);
                bijhoudingsautorisatieSoortAdministratieveHandelingRepository.save(handeling);
            } else {
                bijhoudingsautorisatieSoortAdministratieveHandelingController.save(handeling);
            }
        } else if (item.getId() == null && item.isActief()) {
            handeling = new BijhoudingsautorisatieSoortAdministratieveHandeling(bijhoudingsautorisatie, item.getSoortAdministratievehandeling());
            handeling.setBijhoudingsautorisatie(bijhoudingsautorisatie);
            bijhoudingsautorisatie.addBijhoudingsautorisatieSoortAdministratieveHandeling(handeling);
            bijhoudingsautorisatieSoortAdministratieveHandelingController.save(handeling);
        } else {
            throw new NotFoundException();
        }

        return new PageImpl<>(bepaalActiefStatusSoortAdministratieveHandelingen(bijhoudingsautorisatie));
    }

    /**
     * Haal een lijst van items op.
     * @param bijhoudingsautorisatieId bijhoudingsautorisatie ID
     * @param parameters request parameters
     * @param pageable paginering
     * @return lijst van item (inclusief paginering en sortering)
     */
    @RequestMapping(value = "/{id}/bijhoudingsautorisatieSoortAdministratieveHandelingen", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public final Page<BijhoudingsautorisatieSoortAdministratieveHandelingView> listSoortAdministratieveHandelingen(
            @PathVariable("id") final Integer bijhoudingsautorisatieId,
            @RequestParam final Map<String, String> parameters,
            @PageableDefault(size = 1000) final Pageable pageable) {
        return getReadonlyTransactionTemplate().execute(status -> {
            try {
                final Bijhoudingsautorisatie bijhoudingsautorisatie = get(bijhoudingsautorisatieId);
                // Aangezien de page die we terugkrijgen uit de repository immutable is, maken we een nieuwe lijst aan om
                // vervolgens een nieuw page object aan te maken met de betreffende subset van de lijst.
                final List<BijhoudingsautorisatieSoortAdministratieveHandelingView> schermSoorten =
                        bepaalActiefStatusSoortAdministratieveHandelingen(bijhoudingsautorisatie);
                final int fromIndex = pageable.getOffset();
                final int toIndex = (fromIndex + pageable.getPageSize()) > schermSoorten.size() ? schermSoorten.size() : fromIndex + pageable.getPageSize();
                return new PageImpl<>(schermSoorten.subList(fromIndex, toIndex), pageable, schermSoorten.size());
            } catch (NotFoundException exception) {
                LOG.error(ExceptionUtils.getFullStackTrace(exception));
                return null;
            }
        });
    }

    private List<BijhoudingsautorisatieSoortAdministratieveHandelingView> bepaalActiefStatusSoortAdministratieveHandelingen(
            final Bijhoudingsautorisatie bijhoudingsautorisatie) {

        final List<SoortAdministratieveHandeling> alleSoortenAdministratieveHandelingen = soortAdministratieveHandelingRepository.getValues();

        final List<BijhoudingsautorisatieSoortAdministratieveHandelingView> schermSoortAdministratieveHandelingen = new ArrayList<>();

        for (final SoortAdministratieveHandeling huidigeSoort : alleSoortenAdministratieveHandelingen) {
            final BijhoudingsautorisatieSoortAdministratieveHandeling bijhoudingsautorisatieSoortAdministratieveHandeling =
                    zoekBijhoudinsautorisatieSoortAdministratieveHandeling(bijhoudingsautorisatie, huidigeSoort);

            schermSoortAdministratieveHandelingen.add(new BijhoudingsautorisatieSoortAdministratieveHandelingView(
                    bijhoudingsautorisatieSoortAdministratieveHandeling,
                    bijhoudingsautorisatieSoortAdministratieveHandeling.getId() != null && bijhoudingsautorisatieSoortAdministratieveHandeling
                            .isActueelEnGeldig()));
        }

        return schermSoortAdministratieveHandelingen;
    }

    private BijhoudingsautorisatieSoortAdministratieveHandeling zoekBijhoudinsautorisatieSoortAdministratieveHandeling(
            final Bijhoudingsautorisatie bijhoudingsautorisatie,
            final SoortAdministratieveHandeling soortAdministratieveHandeling) {
        for (final BijhoudingsautorisatieSoortAdministratieveHandeling opgeslagenBijhoudingsautorisatieSoortAdministratieveHandeling :
                bijhoudingsautorisatieSoortAdministratieveHandelingRepository.findByBijhoudingsautorisatie(bijhoudingsautorisatie)) {
            if (opgeslagenBijhoudingsautorisatieSoortAdministratieveHandeling.getSoortAdministratievehandeling().getId() ==
                    soortAdministratieveHandeling.getId()) {
                return opgeslagenBijhoudingsautorisatieSoortAdministratieveHandeling;
            }
        }
        return new BijhoudingsautorisatieSoortAdministratieveHandeling(bijhoudingsautorisatie, soortAdministratieveHandeling);
    }

    /**
     * Bijhoudingsautorisatie historie verwerker.
     */
    static class BijhoudingsautorisatieHistorieVerwerker extends AbstractHistorieVerwerker<Bijhoudingsautorisatie, BijhoudingsautorisatieHistorie> {

        @Override
        public Class<BijhoudingsautorisatieHistorie> historieClass() {
            return BijhoudingsautorisatieHistorie.class;
        }

        @Override
        public Class<Bijhoudingsautorisatie> entiteitClass() {
            return Bijhoudingsautorisatie.class;
        }
    }

}

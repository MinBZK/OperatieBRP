/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.autaut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroep;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroepAttribuut;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelLo3Rubriek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LeveringsautorisatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Rubriek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import nl.bzk.brp.beheer.webapp.controllers.AbstractHistorieVerwerker;
import nl.bzk.brp.beheer.webapp.controllers.AbstractReadWriteController;
import nl.bzk.brp.beheer.webapp.controllers.ErrorHandler;
import nl.bzk.brp.beheer.webapp.controllers.ErrorHandler.NotFoundException;
import nl.bzk.brp.beheer.webapp.controllers.ReadWriteController;
import nl.bzk.brp.beheer.webapp.controllers.SaveTransaction;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.autaut.DienstbundelGroepAttribuutRepository;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.autaut.DienstbundelGroepRepository;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.autaut.DienstbundelLo3RubriekRepository;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.autaut.DienstbundelRepository;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.autaut.LeveringsautorisatieRepository;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.autaut.SoortDienstRepository;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.conv.Lo3RubriekRepository;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.kern.ElementRepository;
import nl.bzk.brp.beheer.webapp.view.DienstbundelGroepAttribuutView;
import nl.bzk.brp.beheer.webapp.view.DienstbundelLo3RubriekView;
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
 * LeveringsautorisatieController voor beheer abonnement.
 */

@RestController
@RequestMapping(value = ControllerConstants.LEVERINGSAUTORISATIE_URI)
public class LeveringsautorisatieController extends AbstractReadWriteController<Leveringsautorisatie, Integer> {

    private static final String DATUM_INGANG = "datumIngang";
    private static final String DATUM_EINDE = "datumEinde";

    private static final List<String> SORTERINGEN =
            Arrays.asList("naam", "stelsel", "indicatieModelautorisatie", "indicatieGeblokkeerd", DATUM_INGANG, DATUM_EINDE);

    private final ReadWriteController<ToegangLeveringsAutorisatie, Integer> toegangLeveringsautorisatieController;
    private final ReadWriteController<Dienst, Integer> dienstController;
    private final ReadWriteController<Dienstbundel, Integer> dienstbundelController;
    private final DienstbundelRepository dienstbundelRepository;
    private final ReadWriteController<DienstbundelLo3Rubriek, Integer> dienstbundelLo3RubriekController;
    private final DienstbundelLo3RubriekRepository dienstbundelLo3RubriekRepository;
    private final Lo3RubriekRepository conversieLO3RubriekRepository;
    private final ReadWriteController<DienstbundelGroep, Integer> dienstbundelGroepController;
    private final DienstbundelGroepRepository dienstbundelGroepRepository;
    private final DienstbundelGroepAttribuutRepository dienstbundelGroepAttribuutRepository;

    private final ElementRepository elementRepository;

    /**
     * Constructor voor LeveringsautorisatieController.
     * @param repository repo
     * @param toegangLeveringsautorisatieController controller
     * @param dienstController controller
     * @param dienstbundelController controller
     * @param dienstbundelRepository repo
     * @param dienstbundelLo3RubriekController controller
     * @param dienstbundelLo3RubriekRepository repo
     * @param conversieLO3RubriekRepository repo
     * @param dienstbundelGroepController controller
     * @param dienstbundelGroepRepository repo
     * @param dienstbundelGroepAttribuutRepository repo
     */
    @Inject
    public LeveringsautorisatieController(final LeveringsautorisatieRepository repository,
                                          final ReadWriteController<ToegangLeveringsAutorisatie, Integer> toegangLeveringsautorisatieController,
                                          final ReadWriteController<Dienst, Integer> dienstController,
                                          final ReadWriteController<Dienstbundel, Integer> dienstbundelController,
                                          final DienstbundelRepository dienstbundelRepository,
                                          final ReadWriteController<DienstbundelLo3Rubriek, Integer> dienstbundelLo3RubriekController,
                                          final DienstbundelLo3RubriekRepository dienstbundelLo3RubriekRepository,
                                          final Lo3RubriekRepository conversieLO3RubriekRepository,
                                          final ReadWriteController<DienstbundelGroep, Integer> dienstbundelGroepController,
                                          final DienstbundelGroepRepository dienstbundelGroepRepository,
                                          final DienstbundelGroepAttribuutRepository dienstbundelGroepAttribuutRepository) {
        super(repository,
                Arrays.asList(
                        AutorisatieFilterFactory.getFilterNaam(),
                        LeveringsautorisatieFilterFactory.getFilterStelsel(),
                        AutorisatieFilterFactory.getFilterDatumIngang(),
                        AutorisatieFilterFactory.getFilterDatumEinde(),
                        AutorisatieFilterFactory.getFilterIndicatieModelAutorisatie(),
                        LeveringsautorisatieFilterFactory.getFilterGeautoriseerdePartij(),
                        LeveringsautorisatieFilterFactory.getFilterTransporteurPartij(),
                        LeveringsautorisatieFilterFactory.getFilterOndertekenaarPartij(),
                        LeveringsautorisatieFilterFactory.getFilterDienstbundelNaam(),
                        LeveringsautorisatieFilterFactory.getFilterSoortDienst(),
                        AutorisatieFilterFactory.getFilterGeblokkeerd()),
                Collections.singletonList(new LeveringsautorisatieHistorieVerwerker()),
                SORTERINGEN);
        this.toegangLeveringsautorisatieController = toegangLeveringsautorisatieController;
        this.dienstController = dienstController;
        this.dienstbundelController = dienstbundelController;
        this.dienstbundelRepository = dienstbundelRepository;
        this.dienstbundelLo3RubriekController = dienstbundelLo3RubriekController;
        this.dienstbundelLo3RubriekRepository = dienstbundelLo3RubriekRepository;
        this.conversieLO3RubriekRepository = conversieLO3RubriekRepository;
        this.dienstbundelGroepController = dienstbundelGroepController;
        this.dienstbundelGroepRepository = dienstbundelGroepRepository;
        this.dienstbundelGroepAttribuutRepository = dienstbundelGroepAttribuutRepository;
        this.elementRepository = new ElementRepository();
    }

    private List<DienstbundelGroepAttribuutView> bepaalActiefStatusAttributenVoorGroep(
            final DienstbundelGroep dienstbundelGroep) {

        final List<Element> alleAttributen = elementRepository.filterEnumOpGroepEnAutorisatie(dienstbundelGroep.getGroep().getId());

        final List<DienstbundelGroepAttribuutView> schermAttributen = new ArrayList<>();

        for (final Element huidigAttribuut : alleAttributen) {
            final DienstbundelGroepAttribuut dienstbundelGroepAttribuut = zoekDienstbundelGroepAttribuut(dienstbundelGroep, huidigAttribuut);
            final DienstbundelGroepAttribuutView schermAttribuut =
                    new DienstbundelGroepAttribuutView(
                            dienstbundelGroepAttribuut,
                            dienstbundelGroepAttribuut.getId() != null);
            schermAttributen.add(schermAttribuut);
        }

        return schermAttributen;
    }

    private DienstbundelGroepAttribuut zoekDienstbundelGroepAttribuut(final DienstbundelGroep dienstbundelGroep, final Element attribuut) {
        for (final DienstbundelGroepAttribuut opgeslagenGroepAttribuut : dienstbundelGroepAttribuutRepository.findByDienstbundelGroep(dienstbundelGroep)) {
            if (opgeslagenGroepAttribuut.getAttribuut().getId() == attribuut.getId()) {
                return opgeslagenGroepAttribuut;
            }
        }
        return new DienstbundelGroepAttribuut(dienstbundelGroep, attribuut);
    }

    /**
     * Haal een specifiek item op.
     * @param id id
     * @param toegangLeveringsautorisatieId id van toegang
     * @return item
     * @throws NotFoundException wanneer het item niet gevonden kan worden
     */
    @RequestMapping(value = "/{id}/toegangen/{tid}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public final ToegangLeveringsAutorisatie getToegangLeveringsautorisatie(
            @PathVariable("id") final Integer id,
            @PathVariable("tid") final Integer toegangLeveringsautorisatieId) throws ErrorHandler.NotFoundException {
        return toegangLeveringsautorisatieController.get(toegangLeveringsautorisatieId);
    }

    /**
     * Haal een lijst van items op.
     * @param id id van Leveringsautorisatie
     * @param parameters request parameters
     * @param pageable paginering
     * @return lijst van item (inclusief paginering en sortering)
     */
    @RequestMapping(value = "/{id}/toegangen", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public final Page<ToegangLeveringsAutorisatie> listToegangLeveringsautorisatie(
            @PathVariable(value = "id") final String id,
            @RequestParam final Map<String, String> parameters,
            @PageableDefault(size = 10) @SortDefault(sort = {DATUM_INGANG, DATUM_EINDE}, direction = Sort.Direction.ASC) final Pageable pageable) {
        parameters.put(ToegangLeveringsautorisatieController.PARAMETER_FILTER_LEVERINGSAUTORISATIE, id);
        return toegangLeveringsautorisatieController.list(parameters, pageable);
    }

    /**
     * Slaat een gewijzigd item op.
     * @param id id
     * @param item item
     * @return item
     * @throws NotFoundException wanneer het item niet gevonden kan worden om te updaten
     */
    @RequestMapping(value = "/{id}/toegangen", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public final ToegangLeveringsAutorisatie saveToegangAbonnement(
            @PathVariable("id") final Integer id,
            @Validated @RequestBody final ToegangLeveringsAutorisatie item) throws NotFoundException {
        item.setLeveringsautorisatie(get(id));
        return toegangLeveringsautorisatieController.save(item);
    }

    /**
     * Haal een specifiek item op.
     * @param id id
     * @param dienstbundelId id van dienstbundel
     * @return item
     * @throws NotFoundException wanneer het item niet gevonden kan worden
     */
    @RequestMapping(value = "/{id}/dienstbundels/{did1}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public final Dienstbundel getDienstbundel(@PathVariable("id") final Integer id, @PathVariable("did1") final Integer dienstbundelId)
            throws ErrorHandler.NotFoundException {
        return dienstbundelController.get(dienstbundelId);
    }

    /**
     * Haal een lijst van items op.
     * @param id id van dienstbundel
     * @param parameters request parameters
     * @param pageable paginering
     * @return lijst van item (inclusief paginering en sortering)
     */
    @RequestMapping(value = "/{id}/dienstbundels", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public final Page<Dienstbundel> listDienstbundel(
            @PathVariable(value = "id") final String id,
            @RequestParam final Map<String, String> parameters,
            @PageableDefault(size = 10) final Pageable pageable) {
        parameters.put(DienstbundelController.PARAMETER_FILTER_LEVERINGSAUTORISATIE, id);
        return dienstbundelController.list(parameters, pageable);
    }

    /**
     * Slaat een gewijzigd item op.
     * @param id id
     * @param item item
     * @return item
     * @throws NotFoundException wanneer het item niet gevonden kan worden om te updaten
     */
    @RequestMapping(value = "/{id}/dienstbundels", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public final Dienstbundel saveDienstbundel(@PathVariable("id") final Integer id, @Validated @RequestBody final Dienstbundel item)
            throws NotFoundException {
        item.setLeveringsautorisatie(get(id));
        return dienstbundelController.save(item);
    }

    /**
     * Haal een specifiek item op.
     * @param id id
     * @param dienstId id van toegang
     * @return item
     * @throws NotFoundException wanneer het item niet gevonden kan worden
     */
    @RequestMapping(value = "/{id}/dienstbundels/{did}/diensten/{rid}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public final Dienst getDienst(@PathVariable("id") final Integer id, @PathVariable("rid") final Integer dienstId)
            throws ErrorHandler.NotFoundException {
        return dienstController.get(dienstId);
    }

    /**
     * Haal een lijst van items op.
     * @param dienstbundelId id van rubriek
     * @param parameters request parameters
     * @param pageable paginering
     * @return lijst van item (inclusief paginering en sortering)
     * @throws NotFoundException not found exception
     */
    @RequestMapping(value = "/{id}/dienstbundels/{did2}/diensten", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public final Page<Dienst> listDiensten(
            @PathVariable(value = "did2") final String dienstbundelId,
            @RequestParam final Map<String, String> parameters,
            @PageableDefault(size = 10) final Pageable pageable) {
        parameters.put(DienstController.PARAMETER_FILTER_DIENSTBUNDEL, dienstbundelId);
        return dienstController.list(parameters, pageable);
    }

    /**
     * Slaat een gewijzigd item op.
     * @param item item
     * @param leveringsautorisatieId id van de leveringsautorisatie
     * @param dienstbundelId id van de dienstbundel
     * @return item
     * @throws NotFoundException wanneer het item niet gevonden kan worden om te updaten
     */
    @RequestMapping(value = "/{id}/dienstbundels/{did3}/diensten", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public final Dienst saveDienst(
            @PathVariable(value = "id") final Integer leveringsautorisatieId,
            @PathVariable(value = "did3") final Integer dienstbundelId,
            @Validated @RequestBody final Dienst item) throws NotFoundException {
        return new SaveTransaction<Dienst>(getTransactionTemplate()).execute(() -> {
            final Leveringsautorisatie leveringsautorisatie = get(leveringsautorisatieId);
            final SoortDienstRepository soortDienstRepository = new SoortDienstRepository();
            if (!soortDienstRepository.filterEnumOpStelsel(Stelsel.GBA.equals(leveringsautorisatie.getStelsel())).contains(item.getSoortDienst())) {
                throw new IllegalArgumentException("Ongeldige soort dienst voor deze GBA autorisatie.");
            }

            item.setDienstbundel(dienstbundelRepository.findOne(dienstbundelId));

            return dienstController.save(item);
        });
    }

    /**
     * Haal een specifiek item op.
     * @param id id
     * @param rubriekId id van toegang
     * @return item
     * @throws NotFoundException wanneer het item niet gevonden kan worden
     */
    @RequestMapping(value = "/{id}/dienstbundels/{did}/lo3rubrieken/{rid}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public final DienstbundelLo3Rubriek getDienstbundelLO3Rubriek(@PathVariable("id") final Integer id, @PathVariable("rid") final Integer rubriekId)
            throws ErrorHandler.NotFoundException {
        return dienstbundelLo3RubriekController.get(rubriekId);
    }

    /**
     * Haal een lijst van items op.
     * @param dienstbundelId id van rubriek
     * @param parameters request parameters
     * @param pageable paginering
     * @return lijst van item (inclusief paginering en sortering)
     * @throws NotFoundException Indien de dienstbundel niet kan worden opgehaald
     */
    @RequestMapping(value = "/{id}/dienstbundels/{did4}/lo3rubrieken", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public final Page<DienstbundelLo3RubriekView> listDienstbundelLo3Rubrieken(
            @PathVariable(value = "did4") final Integer dienstbundelId,
            @RequestParam final Map<String, String> parameters,
            @PageableDefault(size = 10) final Pageable pageable) throws NotFoundException {
        final List<DienstbundelLo3RubriekView> viewRubrieken =
                haalDienstbundelLo3RubriekenOpBasisVanDienstbundelOp(dienstbundelId);
        return new PageImpl<>(viewRubrieken, pageable, viewRubrieken.size());
    }

    private List<DienstbundelLo3RubriekView> haalDienstbundelLo3RubriekenOpBasisVanDienstbundelOp(final Integer dienstbundelId) throws NotFoundException {
        return new SaveTransaction<List<DienstbundelLo3RubriekView>>(getTransactionTemplate()).execute(() -> {
            final List<DienstbundelLo3Rubriek> rubrieken = dienstbundelLo3RubriekRepository.findByDienstbundel(dienstbundelController.get(dienstbundelId));
            final List<DienstbundelLo3RubriekView> viewRubrieken = new ArrayList<>();
            rubrieken.forEach(rubriek -> viewRubrieken.add(new DienstbundelLo3RubriekView(rubriek, Boolean.TRUE)));
            return viewRubrieken;
        });
    }

    /**
     * Slaat een gewijzigd item op.
     * @param item item
     * @param leveringsautorisatieId Id van de leveringsautorisatie
     * @param dienstbundelId id van de dienstbundel
     * @return item
     * @throws NotFoundException wanneer het item niet gevonden kan worden om te updaten
     */
    @RequestMapping(value = "/{id}/dienstbundels/{did5}/lo3rubrieken", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public final DienstbundelLo3RubriekView saveDienstbundelLO3Rubriek(
            @PathVariable(value = "id") final Integer leveringsautorisatieId,
            @PathVariable(value = "did5") final Integer dienstbundelId,
            @RequestBody final DienstbundelLo3RubriekView item) throws NotFoundException {
        return new SaveTransaction<DienstbundelLo3RubriekView>(getTransactionTemplate())
                .execute(() -> saveLO3Rubriek(leveringsautorisatieId, dienstbundelId, item));
    }

    private DienstbundelLo3RubriekView saveLO3Rubriek(final Integer leveringsautorisatieId, final Integer dienstbundelId,
                                                      final DienstbundelLo3RubriekView item) throws NotFoundException {
        final Leveringsautorisatie leveringsautorisatie = get(leveringsautorisatieId);
        if (!Stelsel.GBA.equals(leveringsautorisatie.getStelsel())) {
            throw new IllegalArgumentException("Lo3 rubrieken kunnen niet worden toegevoegd op een BRP autorisatie.");
        }

        final Dienstbundel dienstbundel = dienstbundelRepository.getOne(dienstbundelId);
        final Lo3Rubriek rubriek = conversieLO3RubriekRepository.getOne(item.getLo3Rubriek().getId());
        final DienstbundelLo3Rubriek gevondenDienstbundelLo3Rubriek =
                dienstbundelLo3RubriekRepository.findByDienstbundelAndLo3Rubriek(dienstbundel, rubriek);

        final DienstbundelLo3RubriekView resultaat;
        if (item.isActief()) {
            // LO3 rubriek toevoegen.
            if (gevondenDienstbundelLo3Rubriek == null) {
                item.setDienstbundel(dienstbundel);
                item.setLo3Rubriek(rubriek);
                resultaat =
                        new DienstbundelLo3RubriekView(
                                dienstbundelLo3RubriekController.save(item.getEntityObject()),
                                Boolean.TRUE);
            } else {
                resultaat = new DienstbundelLo3RubriekView(gevondenDienstbundelLo3Rubriek, Boolean.TRUE);
            }
        } else {
            // LO3 rubriek verwijderen.
            dienstbundel.getDienstbundelLo3RubriekSet().removeIf(lo3Rubriek -> lo3Rubriek.getId().equals(gevondenDienstbundelLo3Rubriek.getId()));
            dienstbundelController.save(dienstbundel);
            resultaat = new DienstbundelLo3RubriekView(gevondenDienstbundelLo3Rubriek, Boolean.FALSE);
        }
        return resultaat;
    }

    /**
     * Haal een specifiek item op.
     * @param groepId id van dienstbundelgroep
     * @return item
     * @throws NotFoundException wanneer het item niet gevonden kan worden
     */
    @RequestMapping(value = "/{id}/dienstbundels/{did}/dienstbundelgroepen/{gid}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public final DienstbundelGroep getGroep(@PathVariable("gid") final Integer groepId) {
        return getReadonlyTransactionTemplate().execute(status -> dienstbundelGroepRepository.getOne(groepId));
    }

    /**
     * Slaat een gewijzigd item op.
     * @param item item
     * @param leveringsautorisatieId Id van de leveringsautorisatie
     * @param dienstbundelId Id van de dienstbundel
     * @return item
     * @throws NotFoundException wanneer het item niet gevonden kan worden om te updaten
     */
    @RequestMapping(value = "/{id}/dienstbundels/{did}/dienstbundelgroepen", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public final DienstbundelGroep saveDienstbundelGroep(
            @PathVariable("id") final Integer leveringsautorisatieId,
            @PathVariable("did") final Integer dienstbundelId,
            @RequestBody final DienstbundelGroep item) throws NotFoundException {
        return new SaveTransaction<DienstbundelGroep>(getTransactionTemplate()).execute(() -> {
            final Leveringsautorisatie leveringsautorisatie = get(leveringsautorisatieId);
            if (!Stelsel.BRP.equals(leveringsautorisatie.getStelsel())) {
                throw new IllegalArgumentException("Groepen kunnen niet worden toegevoegd op een GBA autorisatie.");
            }

            if (item.getId() != null) {
                item.setDienstbundelGroepAttribuutSet(new HashSet<>(dienstbundelGroepAttribuutRepository.findByDienstbundelGroep(item)));
            }
            item.setDienstbundel(dienstbundelRepository.findOne(dienstbundelId));
            item.setGroep(elementRepository.findOne(item.getGroep().getId()));
            return dienstbundelGroepController.save(item);
        });
    }

    /**
     * Haal een lijst van items op.
     * @param dienstbundelId Dienstbundel ID
     * @param parameters request parameters
     * @param pageable paginering
     * @return lijst van item (inclusief paginering en sortering)
     */
    @RequestMapping(value = "/{id}/dienstbundels/{did6}/dienstbundelgroepen", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public final Page<DienstbundelGroep> listGroep(
            @PathVariable("did6") final Integer dienstbundelId,
            @RequestParam final Map<String, String> parameters,
            @PageableDefault(size = 10) final Pageable pageable) {
        return getReadonlyTransactionTemplate().execute(status -> {
            final Dienstbundel dienstbundel = dienstbundelRepository.findOne(dienstbundelId);
            return dienstbundelGroepRepository.findByDienstbundel(dienstbundel, pageable);
        });
    }

    /**
     * Verwijdert een groep.
     * @param leveringsautorisatieId Leveringsautorisatie ID
     * @param dienstbundelId Dienstbundel ID
     * @param groepId id van dienstbundel
     * @param pageable paginering
     * @return item
     * @throws NotFoundException wanneer het item niet gevonden kan worden
     */
    @RequestMapping(value = "/{id}/dienstbundels/{did7}/dienstbundelgroepen/{gid1}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public final Page<DienstbundelGroep> deleteGroep(
            @PathVariable("id") final Integer leveringsautorisatieId,
            @PathVariable("did7") final Integer dienstbundelId,
            @PathVariable("gid1") final Integer groepId,
            @PageableDefault(size = 10) final Pageable pageable) throws ErrorHandler.NotFoundException {
        return new SaveTransaction<Page<DienstbundelGroep>>(getTransactionTemplate()).execute(() -> {
            final Dienstbundel dienstbundel = dienstbundelController.get(dienstbundelId);

            dienstbundel.getDienstbundelGroepSet().removeIf(groep -> groep.getId().equals(groepId));

            final Dienstbundel opgeslagenDienstBundel = dienstbundelController.save(dienstbundel);

            // Herlaad de overige dienstbundel groepen.
            return dienstbundelGroepRepository.findByDienstbundel(opgeslagenDienstBundel, pageable);
        });
    }

    /**
     * Slaat een gewijzigd item op.
     * @param leveringsautorisatieId Id van de leveringsautorisatie
     * @param dienstbundelGroepId groep ID
     * @param item item
     * @return item
     * @throws NotFoundException wanneer het item niet gevonden kan worden om te updaten
     */
    @RequestMapping(value = "/{id2}/dienstbundels/{did8}/dienstbundelgroepen/{gid2}/attributen", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public final Page<DienstbundelGroepAttribuutView> saveDienstbundelGroepAttribuut(
            @PathVariable("id2") final Integer leveringsautorisatieId,
            @PathVariable("gid2") final Integer dienstbundelGroepId,
            @RequestBody final DienstbundelGroepAttribuutView item) throws NotFoundException {
        return new SaveTransaction<Page<DienstbundelGroepAttribuutView>>(getTransactionTemplate()).execute(() -> {
            final Leveringsautorisatie leveringsautorisatie = get(leveringsautorisatieId);
            if (!Stelsel.BRP.equals(leveringsautorisatie.getStelsel())) {
                throw new IllegalArgumentException("Groepen kunnen niet worden toegevoegd op een GBA autorisatie.");
            }

            final DienstbundelGroep dienstbundelGroep = dienstbundelGroepController.get(dienstbundelGroepId);

            if (item.getId() != null && !item.isActief()) {
                // Verwijderen attribuut uit dienstbundelgroep.
                dienstbundelGroep.getDienstbundelGroepAttribuutSet().removeIf(attribuut -> attribuut.getId().equals(item.getId()));
            } else if (item.isActief()) {
                // Toevoegen attribuut aan dienstbundelgroep.
                dienstbundelGroep.getDienstbundelGroepAttribuutSet().add(new DienstbundelGroepAttribuut(dienstbundelGroep, item.getAttribuut()));
            }

            dienstbundelGroepController.save(dienstbundelGroep);
            return new PageImpl<>(bepaalActiefStatusAttributenVoorGroep(dienstbundelGroep));
        });
    }

    /**
     * Haal een lijst van items op.
     * @param groepId groep ID
     * @param parameters request parameters
     * @param pageable paginering
     * @return lijst van item (inclusief paginering en sortering)
     */
    @RequestMapping(value = "/{id}/dienstbundels/{did}/dienstbundelgroepen/{gid3}/attributen", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public final Page<DienstbundelGroepAttribuutView> listAttribuut(
            @PathVariable("gid3") final Integer groepId,
            @RequestParam final Map<String, String> parameters,
            @PageableDefault(size = 1000) final Pageable pageable) {
        return getReadonlyTransactionTemplate().execute(status -> {
            final DienstbundelGroep dienstbundelGroep = dienstbundelGroepRepository.findOne(groepId);
            // Aangezien de page die we terugkrijgen uit de repository immutable is, maken we een nieuwe lijst aan om
            // vervolgens een nieuw page object aan te maken met de betreffende subset van de lijst.
            final List<DienstbundelGroepAttribuutView> schermAttributen =
                    bepaalActiefStatusAttributenVoorGroep(dienstbundelGroep);
            final int fromIndex = pageable.getOffset();
            final int toIndex =
                    (fromIndex + pageable.getPageSize()) > schermAttributen.size() ? schermAttributen.size() : fromIndex + pageable.getPageSize();
            return new PageImpl<>(schermAttributen.subList(fromIndex, toIndex), pageable, schermAttributen.size());
        });
    }

    /**
     * Leveringsautorisatie historie verwerker.
     */
    static class LeveringsautorisatieHistorieVerwerker extends AbstractHistorieVerwerker<Leveringsautorisatie, LeveringsautorisatieHistorie> {

        @Override
        public Class<LeveringsautorisatieHistorie> historieClass() {
            return LeveringsautorisatieHistorie.class;
        }

        @Override
        public Class<Leveringsautorisatie> entiteitClass() {
            return Leveringsautorisatie.class;
        }
    }

}

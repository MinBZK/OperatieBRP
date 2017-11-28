/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortVrijBericht;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.VrijBericht;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.VrijBerichtPartij;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import nl.bzk.brp.beheer.webapp.controllers.query.BooleanValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.DateValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.EqualPredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.Filter;
import nl.bzk.brp.beheer.webapp.controllers.query.GreaterOrEqualPredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.IntegerValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.InverseBooleanPredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.LessOrEqualPredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.ShortValueAdapter;
import nl.bzk.brp.beheer.webapp.repository.beh.VrijBerichtRepository;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.kern.PartijRepository;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.kern.SoortVrijBerichtRepository;
import nl.bzk.brp.beheer.webapp.service.VrijBerichtClientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller voor vrije berichten.
 */
@RestController
@RequestMapping(ControllerConstants.VRIJ_BERICHT)
public class VrijBerichtController extends AbstractReadWriteController<VrijBericht, Integer> {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final String TIJDSTIP_REGISTRATIE = "tijdstipRegistratie";

    private static final Filter<?>
            FILTER_RICHTING =
            new Filter<>("soortBericht", new ShortValueAdapter(), new EqualPredicateBuilderFactory("soortBerichtVrijBericht"));
    private static final Filter<?>
            FILTER_ONGELEZEN =
            new Filter<>("ongelezen", new BooleanValueAdapter(), new InverseBooleanPredicateBuilderFactory("indicatieGelezen"));
    private static final Filter<?>
            FILTER_PARTIJID =
            new Filter<>("partijId", new IntegerValueAdapter(), new EqualPredicateBuilderFactory("vrijBerichtPartijen.partij.id"));
    private static final Filter<?>
            FILTER_SOORT_PARTIJ =
            new Filter<>("soortPartij", new ShortValueAdapter(), new EqualPredicateBuilderFactory("vrijBerichtPartijen.partij.soortPartij"));
    private static final Filter<?>
            FILTER_SOORT_BEGINDATUM =
            new Filter<>("beginDatum", new DateValueAdapter(), new GreaterOrEqualPredicateBuilderFactory<>(TIJDSTIP_REGISTRATIE));
    private static final Filter<?>
            FILTER_SOORT_EINDDATUM =
            new Filter<>("eindDatum", new DateValueAdapter(), new LessOrEqualPredicateBuilderFactory<>(TIJDSTIP_REGISTRATIE));
    private static final Filter<?>
            FILTER_SOORT =
            new Filter<>("soortVrijBericht", new ShortValueAdapter(), new EqualPredicateBuilderFactory("soortVrijBericht"));

    private static final List<String> SORTERINGEN = Collections.singletonList(TIJDSTIP_REGISTRATIE);
    private final VrijBerichtClientService vrijBerichtClientService;
    private final PartijRepository partijRepository;
    private final SoortVrijBerichtRepository soortVrijBerichtRepository;

    @Inject
    protected VrijBerichtController(final VrijBerichtRepository repository, final VrijBerichtClientService vrijBerichtClientService,
                                    final PartijRepository partijRepository, final SoortVrijBerichtRepository soortVrijBerichtRepository) {
        super(repository, Arrays.asList(
                FILTER_RICHTING,
                FILTER_ONGELEZEN,
                FILTER_PARTIJID,
                FILTER_SOORT_PARTIJ,
                FILTER_SOORT_BEGINDATUM,
                FILTER_SOORT_EINDDATUM,
                FILTER_SOORT
        ), Collections.emptyList(), SORTERINGEN, true);
        this.vrijBerichtClientService = vrijBerichtClientService;
        this.partijRepository = partijRepository;
        this.soortVrijBerichtRepository = soortVrijBerichtRepository;
    }

    /**
     * Haal de lijst van vrij bericht partijen behorend bij de opgegeven partij op.
     * @param id id van vrij bericht
     * @param pageable pagable instantie
     * @return pagina vrijberichtpartij pagina
     * @throws ErrorHandler.NotFoundException wanneer het item niet gevonden kan worden
     */
    @RequestMapping(value = "/{id}/vrijberichtpartij", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public final Page<VrijBerichtPartij> getVrijBerichtPartijen(@PathVariable("id") final Integer id,
                                                                @PageableDefault(size = Short.MAX_VALUE) final Pageable pageable)
            throws ErrorHandler.NotFoundException {
        final List<VrijBerichtPartij> vrijBerichtPartijen = get(id).getVrijBerichtPartijen();
        return new PageImpl<>(vrijBerichtPartijen, pageable, vrijBerichtPartijen.size());
    }

    /**
     * Haal de lijst van partijen die geldig zijn voor vrij bericht op.
     * @return de lijst met partijen
     * @throws ErrorHandler.NotFoundException wanneer het item niet gevonden kan worden
     */
    @RequestMapping(value = "/geldigepartijen", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public final List<Partij> getGeldigePartijen()
            throws ErrorHandler.NotFoundException {
        return partijRepository.geldigeVrijBerichtPartijen(DatumUtil.vandaag());
    }

    /**
     * Haal de lijst van soort vrij bericht die geldig zijn op.
     * @return de lijst met soort vrij bericht
     * @throws ErrorHandler.NotFoundException wanneer het item niet gevonden kan worden
     */
    @RequestMapping(value = "/geldigesoortvrijber", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public final List<SoortVrijBericht> getGeldigeSoortBerichtVrijBericht()
            throws ErrorHandler.NotFoundException {
        return soortVrijBerichtRepository.getGeldigeSoortVrijBericht(DatumUtil.vandaag());
    }

    @Override
    public VrijBericht save(@Validated @RequestBody final VrijBericht item) throws ErrorHandler.NotFoundException {
        boolean nieuwItem = false;
        if (item.getId() == null) {
            // Bepaling nieuw item via EntityManager.contains
            nieuwItem = true;
        }
        VrijBericht vrijBericht = item;
        if (nieuwItem) {
            final List<VrijBerichtPartij> teVerwijderenPartijen = Lists.newArrayList();
            for (VrijBerichtPartij vrijBerichtPartij : vrijBericht.getVrijBerichtPartijen()) {
                final String partijCode = vrijBerichtPartij.getPartij().getCode();
                try {
                    vrijBerichtClientService.verstuurVrijBericht(vrijBericht, partijCode);
                } catch (VrijBerichtClientService.VrijBerichtClientException e) {
                    LOG.error("Kon vrij bericht niet versturen. Bericht voor partij met code {} is niet verstuurd: {}", partijCode, e);
                    teVerwijderenPartijen.add(vrijBerichtPartij);
                }
            }
            if (!teVerwijderenPartijen.isEmpty()) {
                vrijBericht.getVrijBerichtPartijen().removeAll(teVerwijderenPartijen);
            }
        }
        vrijBericht = super.save(item);
        return vrijBericht;
    }
}

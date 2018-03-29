/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.autaut;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelLo3Rubriek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Rubriek;
import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import nl.bzk.brp.beheer.webapp.controllers.AbstractReadWriteController;
import nl.bzk.brp.beheer.webapp.controllers.ErrorHandler.NotFoundException;
import nl.bzk.brp.beheer.webapp.controllers.query.EqualPredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.Filter;
import nl.bzk.brp.beheer.webapp.controllers.query.IntegerValueAdapter;
import nl.bzk.brp.beheer.webapp.repository.ReadonlyRepository;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.autaut.DienstbundelLo3RubriekRepository;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AbonnementLO3Rubriek controller voor beheer AbonnementLO3Rubrieken.
 */
@RestController
@RequestMapping(value = ControllerConstants.LO3_FILTER_RUBRIEK)
public class DienstbundelLo3RubriekController extends AbstractReadWriteController<DienstbundelLo3Rubriek, Integer> {

    /**
     * Parameter abonnement.
     */
    public static final String PARAMETER_FILTER_DIENSTBUNDEL = "dienstbundel";

    private static final String DIENSTBUNDEL = PARAMETER_FILTER_DIENSTBUNDEL;

    private static final List<String> SORTERINGEN = Arrays.asList(DIENSTBUNDEL);

    private final ReadonlyRepository<Dienstbundel, Integer> dienstbundelRepository;
    private final ReadonlyRepository<Lo3Rubriek, Integer> conversieLo3Repository;

    /**
     * Constructor voor AbonnementLO3Rubriekcontroller.
     * @param repository AbonnementLO3Rubriek repository
     * @param conversieLo3Repository conversie lo3 repo
     * @param dienstbundelRepository dienstbundel repo
     */
    @Inject
    public DienstbundelLo3RubriekController(final DienstbundelLo3RubriekRepository repository,
                                            final ReadonlyRepository<Dienstbundel, Integer> dienstbundelRepository,
                                            final ReadonlyRepository<Lo3Rubriek, Integer> conversieLo3Repository) {
        super(repository,
                Collections.singletonList(new Filter<>(DIENSTBUNDEL, new IntegerValueAdapter(), new EqualPredicateBuilderFactory(DIENSTBUNDEL))),
                Collections.emptyList(),
                SORTERINGEN);
        this.dienstbundelRepository = dienstbundelRepository;
        this.conversieLo3Repository = conversieLo3Repository;
    }

    @Override
    protected final void wijzigObjectVoorOpslag(final DienstbundelLo3Rubriek item) throws NotFoundException {
        getReadonlyTransactionTemplate().execute(status -> {
            // Dienstbundel en lo3rubriek niet updaten via de dienstbundelLo3Rubriek (laden via id)
            item.setDienstbundel(dienstbundelRepository.findOne(item.getDienstbundel().getId()));
            item.setLo3Rubriek(conversieLo3Repository.findOne(item.getLo3Rubriek().getId()));
            return null;
        });
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.autaut;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstAttenderingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstSelectieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import nl.bzk.brp.beheer.webapp.controllers.AbstractHistorieVerwerker;
import nl.bzk.brp.beheer.webapp.controllers.AbstractReadWriteController;
import nl.bzk.brp.beheer.webapp.controllers.ErrorHandler.NotFoundException;
import nl.bzk.brp.beheer.webapp.controllers.query.EqualPredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.Filter;
import nl.bzk.brp.beheer.webapp.controllers.query.IntegerValueAdapter;
import nl.bzk.brp.beheer.webapp.repository.ReadWriteRepository;
import nl.bzk.brp.beheer.webapp.repository.ReadonlyRepository;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Dienst controller voor beheer diensten.
 */
@RestController
@RequestMapping(value = ControllerConstants.DIENST_URI)
public final class DienstController extends AbstractReadWriteController<Dienst, Integer> {

    /**
     * Parameter om te sorteren op: dienst.
     */
    public static final String PARAMETER_FILTER_DIENSTBUNDEL = "dienstbundel";
    private static final String DATUM_INGANG = "datumIngang";
    private static final String DIENSTBUNDEL = PARAMETER_FILTER_DIENSTBUNDEL;
    private static final String INDICATIE_GEBLOKKEERD = "indicatieGeblokkeerd";
    private static final String SOORT = "soort";

    private static final List<String> SORTERINGEN = Arrays.asList(INDICATIE_GEBLOKKEERD, SOORT, DATUM_INGANG, PARAMETER_FILTER_DIENSTBUNDEL);

    private final ReadonlyRepository<Dienstbundel, Integer> dienstbundelRepository;

    /**
     * Constructor voor Dienstcontroller.
     * @param repository dienst repository
     * @param dienstbundelRepository dienstbundel repository
     */
    @Inject
    public DienstController(final ReadWriteRepository<Dienst, Integer> repository, final ReadonlyRepository<Dienstbundel, Integer> dienstbundelRepository) {
        super(repository,
                Arrays.asList(
                        new Filter<>(DIENSTBUNDEL, new IntegerValueAdapter(), new EqualPredicateBuilderFactory(DIENSTBUNDEL)),
                        new Filter<>(INDICATIE_GEBLOKKEERD, new IntegerValueAdapter(), new EqualPredicateBuilderFactory(INDICATIE_GEBLOKKEERD)),
                        new Filter<>(SOORT, new IntegerValueAdapter(), new EqualPredicateBuilderFactory(SOORT)),
                        new Filter<>(DATUM_INGANG, new IntegerValueAdapter(), new EqualPredicateBuilderFactory(DATUM_INGANG))),
                Arrays.asList(new DienstHistorieVerwerker(), new DienstAttenderingHistorieVerwerker(), new DienstSelectieHistorieVerwerker()),
                SORTERINGEN);
        this.dienstbundelRepository = dienstbundelRepository;
    }

    @Override
    protected void wijzigObjectVoorOpslag(final Dienst item) throws NotFoundException {
        // Dienstbundel niet updaten via de dienst (laden via id)
        item.setDienstbundel(getReadonlyTransactionTemplate().execute(status -> dienstbundelRepository.findOne(item.getDienstbundel().getId())));
    }

    /**
     * Dienst historie verwerker.
     */
    static final class DienstHistorieVerwerker extends AbstractHistorieVerwerker<Dienst, DienstHistorie> {

        @Override
        public Class<DienstHistorie> historieClass() {
            return DienstHistorie.class;
        }

        @Override
        public Class<Dienst> entiteitClass() {
            return Dienst.class;
        }

    }

    /**
     * DienstAttendering historie verwerker.
     */
    static final class DienstAttenderingHistorieVerwerker extends AbstractHistorieVerwerker<Dienst, DienstAttenderingHistorie> {

        @Override
        public Class<DienstAttenderingHistorie> historieClass() {
            return DienstAttenderingHistorie.class;
        }

        @Override
        public Class<Dienst> entiteitClass() {
            return Dienst.class;
        }
    }

    /**
     * DienstSelectie historie verwerker.
     */
    static final class DienstSelectieHistorieVerwerker extends AbstractHistorieVerwerker<Dienst, DienstSelectieHistorie> {

        @Override
        public Class<DienstSelectieHistorie> historieClass() {
            return DienstSelectieHistorie.class;
        }

        @Override
        public Class<Dienst> entiteitClass() {
            return Dienst.class;
        }
    }
}

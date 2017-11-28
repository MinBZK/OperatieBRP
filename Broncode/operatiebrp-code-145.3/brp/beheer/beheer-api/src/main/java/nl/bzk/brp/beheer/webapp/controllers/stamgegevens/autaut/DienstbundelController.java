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
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import nl.bzk.brp.beheer.webapp.controllers.AbstractHistorieVerwerker;
import nl.bzk.brp.beheer.webapp.controllers.AbstractReadWriteController;
import nl.bzk.brp.beheer.webapp.controllers.query.BooleanValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.EqualPredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.Filter;
import nl.bzk.brp.beheer.webapp.controllers.query.IntegerValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.ReferenceValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.StringValueAdapter;
import nl.bzk.brp.beheer.webapp.repository.ReadWriteRepository;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.autaut.DienstbundelRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * DienstbundelController voor beheer dienstbundel.
 */
@RestController
@RequestMapping(value = ControllerConstants.DIENSTBUNDEL_URI)
public class DienstbundelController extends AbstractReadWriteController<Dienstbundel, Integer> {

    /**
     * Parameter: filter op abonnement.
     */
    public static final String PARAMETER_FILTER_LEVERINGSAUTORISATIE = "leveringsautorisatie";
    private static final String LEVERINGSAUTORISATIE = PARAMETER_FILTER_LEVERINGSAUTORISATIE;
    private static final String INDICATIE_GEBLOKKEERD = "indicatieGeblokkeerd";
    private static final String NAAM = "naam";
    private static final List<String> SORTERINGEN = Arrays.asList(INDICATIE_GEBLOKKEERD, NAAM);

    /**
     * Constructor voor DienstbundelController.
     * @param repository repo
     * @param leveringsautorisatieRepository repo voor leveringsautorisatie
     */
    @Inject
    public DienstbundelController(
            final DienstbundelRepository repository,
            final ReadWriteRepository<Leveringsautorisatie, Integer> leveringsautorisatieRepository) {
        super(repository,
                Arrays.asList(
                        new Filter<>(
                                LEVERINGSAUTORISATIE,
                                new ReferenceValueAdapter<>(new IntegerValueAdapter(), leveringsautorisatieRepository),
                                new EqualPredicateBuilderFactory(LEVERINGSAUTORISATIE)),
                        new Filter<>(NAAM, new StringValueAdapter(), new EqualPredicateBuilderFactory(NAAM)),
                        new Filter<>(INDICATIE_GEBLOKKEERD, new BooleanValueAdapter(), new EqualPredicateBuilderFactory(INDICATIE_GEBLOKKEERD))),
                Collections.singletonList(new DienstbundelHistorieVerwerker()),
                SORTERINGEN);
    }

    /**
     * Dienstbundel historie verwerker.
     */
    static final class DienstbundelHistorieVerwerker extends AbstractHistorieVerwerker<Dienstbundel, DienstbundelHistorie> {

        @Override
        public Class<DienstbundelHistorie> historieClass() {
            return DienstbundelHistorie.class;
        }

        @Override
        public Class<Dienstbundel> entiteitClass() {
            return Dienstbundel.class;
        }
    }
}

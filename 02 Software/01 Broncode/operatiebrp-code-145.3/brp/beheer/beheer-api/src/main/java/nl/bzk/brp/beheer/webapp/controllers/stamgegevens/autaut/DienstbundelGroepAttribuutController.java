/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.autaut;

import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroep;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroepAttribuut;
import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import nl.bzk.brp.beheer.webapp.controllers.AbstractReadWriteController;
import nl.bzk.brp.beheer.webapp.controllers.ErrorHandler.NotFoundException;
import nl.bzk.brp.beheer.webapp.repository.ReadWriteRepository;
import nl.bzk.brp.beheer.webapp.repository.ReadonlyRepository;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * DienstbundelGroepAttribuut controller voor beheer dienstbundelgroepattributen.
 */
@RestController
@RequestMapping(value = ControllerConstants.DIENSTBUNDEL_GROEP_ATTRIBUUT_URI)
public final class DienstbundelGroepAttribuutController extends AbstractReadWriteController<DienstbundelGroepAttribuut, Integer> {

    private final ReadonlyRepository<DienstbundelGroep, Integer> dienstbundelGroepRepository;

    /**
     * Constructor voor Dienstcontroller.
     * @param repository dienst repository
     * @param dienstbundelGroepRepository dienstbundel groep repository
     */
    @Inject
    public DienstbundelGroepAttribuutController(final ReadWriteRepository<DienstbundelGroepAttribuut, Integer> repository,
                                                final ReadonlyRepository<DienstbundelGroep, Integer> dienstbundelGroepRepository) {
        super(repository);
        this.dienstbundelGroepRepository = dienstbundelGroepRepository;
    }

    @Override
    protected void wijzigObjectVoorOpslag(final DienstbundelGroepAttribuut item) throws NotFoundException {
        // Dienstbundelgroep niet updaten via de dienst (laden via id)
        item.setDienstbundelGroep(
                getReadonlyTransactionTemplate().execute(status -> dienstbundelGroepRepository.findOne(item.getDienstbundelGroep().getId())));
    }
}

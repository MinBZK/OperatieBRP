/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.autaut;

import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroep;
import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import nl.bzk.brp.beheer.webapp.controllers.AbstractReadWriteController;
import nl.bzk.brp.beheer.webapp.controllers.ErrorHandler.NotFoundException;
import nl.bzk.brp.beheer.webapp.repository.ReadWriteRepository;
import nl.bzk.brp.beheer.webapp.repository.ReadonlyRepository;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * DienstbundelGroep controller voor beheer dienstbundelgroepen.
 */
@RestController
@RequestMapping(value = ControllerConstants.DIENSTBUNDEL_GROEP_URI)
public final class DienstbundelGroepController extends AbstractReadWriteController<DienstbundelGroep, Integer> {

    private final ReadonlyRepository<Dienstbundel, Integer> dienstbundelRepository;

    /**
     * Constructor voor Dienstcontroller.
     * @param repository dienst repository
     * @param dienstbundelRepository dienstbundel repository
     */
    @Inject
    public DienstbundelGroepController(final ReadWriteRepository<DienstbundelGroep, Integer> repository,
                                       final ReadonlyRepository<Dienstbundel, Integer> dienstbundelRepository) {
        super(repository);
        this.dienstbundelRepository = dienstbundelRepository;
    }

    @Override
    protected void wijzigObjectVoorOpslag(final DienstbundelGroep item) throws NotFoundException {
        // Dienstbundel niet updaten via de dienstbundelgroep (laden via id)
        if (item.getDienstbundel() != null) {
            item.setDienstbundel(getReadonlyTransactionTemplate().execute(status -> dienstbundelRepository.findOne(item.getDienstbundel().getId())));
        }
    }

}

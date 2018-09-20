/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.dataaccess;

import javax.inject.Inject;

import nl.bzk.brp.bevraging.app.support.PersoonsLijst;
import nl.bzk.brp.dataaccess.repository.PersoonHisVolledigRepository;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 */
@Service
public class PersoonsLijstJpaService implements PersoonsLijstService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersoonsLijstJpaService.class);

    @Inject
    private PersoonHisVolledigRepository persoonVolledigRepository;

    @Override
    @Transactional(readOnly = true)
    public PersoonsLijst findPersoonVolledigLijst(Integer id) {
        try {
            PersoonHisVolledig persoon = persoonVolledigRepository.leesGenormalizeerdModel(id);

            PersoonView view = new PersoonView(persoon);
            PersoonsLijst pl = new PersoonsLijst(view);

            String lijst = pl.toString();
            LOGGER.debug("Persoonslijst: {}", lijst);

            return pl;
        } catch (Throwable e) {
            LOGGER.error("BSN '" + id + "': " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public PersoonHisVolledig haalPersoonOp(final Integer id) {
        try {
            return persoonVolledigRepository.leesGeserializeerdModel(id);
        } catch (Throwable e) {
            LOGGER.error("{}", e);
        }

        return null;
    }
}

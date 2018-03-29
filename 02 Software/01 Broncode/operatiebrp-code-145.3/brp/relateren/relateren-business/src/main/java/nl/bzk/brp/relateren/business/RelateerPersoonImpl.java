/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.relateren.business;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.springframework.stereotype.Component;

//import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;

/**
 * Implementatie van busniess logica voor het relateren.
 */
@Component
public final class RelateerPersoonImpl implements RelateerPersoon {
    private static final Logger LOGGER = LoggerFactory.getLogger();

    // @Inject
    // private PersoonHisVolledigRepository persoonRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean relateerOpBasisVanID(final Integer id) {
        LOGGER.info("relateer persoon met id " + id);
        //
        // final PersoonHisVolledig persoonHisVolledig = persoonRepository.leesGenormalizeerdModel(id);
        // persoonHisVolledig.getBetrokkenheden();
        return false;
    }
}

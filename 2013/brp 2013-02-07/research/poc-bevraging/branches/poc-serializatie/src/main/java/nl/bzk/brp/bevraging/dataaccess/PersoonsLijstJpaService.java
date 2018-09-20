/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.dataaccess;

import javax.inject.Inject;

import nl.bzk.brp.bevraging.app.support.PersoonsLijst;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
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
    private PersoonRepository persoonRepository;

    @Override
    @Transactional
    public PersoonsLijst findPersoonsLijst(final Integer bsn) {

        try {
            PersoonModel persoon = persoonRepository.findByBurgerservicenummer(
                    new Burgerservicenummer(String.valueOf(bsn)));
            PersoonsLijst pl = new PersoonsLijst(persoon);
            LOGGER.info("Persoonslijst: {}", pl.toString());

            return pl;
        } catch (Exception e) {
            LOGGER.error("BSN '" + bsn + "': " + e.getMessage(), e);
        }
        return null;
    }
}

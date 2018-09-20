/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.dataaccess;

import nl.bzk.brp.bevraging.app.support.PersoonsLijst;
import org.springframework.transaction.annotation.Transactional;

/**
 */
public interface PersoonsLijstJpaService {
    PersoonsLijst getPersoonsLijstJpa(Integer bsn);

    PersoonsLijst getPersoonsLijstMetHistorieJpa(Integer bsn);

    PersoonsLijst getPersoonsLijstJoinedJpa(Integer bsn);
}

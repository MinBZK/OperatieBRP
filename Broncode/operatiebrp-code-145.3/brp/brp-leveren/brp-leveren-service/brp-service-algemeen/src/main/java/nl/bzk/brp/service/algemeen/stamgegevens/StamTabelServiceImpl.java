/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.stamgegevens;

import javax.inject.Inject;
import nl.bzk.brp.domain.algemeen.StamtabelGegevens;
import nl.bzk.brp.service.cache.StamTabelCache;
import org.springframework.stereotype.Service;

/**
 * De implementatie van de stam tabel service {@link StamTabelService}.
 */
@Service
final class StamTabelServiceImpl implements StamTabelService {

    @Inject
    private StamTabelCache stamTabelCache;

    private StamTabelServiceImpl() {

    }

    @Override
    public StamtabelGegevens geefStamgegevens(final String stamgegeven) {
        final StamtabelGegevens stamtabelGegevens = stamTabelCache.geefSynchronisatieStamgegevensUitRepository(stamgegeven);
        if (stamtabelGegevens == null || !stamtabelGegevens.getStamgegevenTabel().getObjectElement().inBericht() || stamtabelGegevens.getStamgegevenTabel()
                .getStamgegevenAttributenInBericht().isEmpty()) {
            return null;
        }
        return stamtabelGegevens;
    }
}

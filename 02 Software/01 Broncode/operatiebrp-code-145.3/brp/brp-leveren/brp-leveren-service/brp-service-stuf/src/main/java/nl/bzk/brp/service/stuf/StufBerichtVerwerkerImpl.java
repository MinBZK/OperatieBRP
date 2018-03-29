/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.stuf;

import javax.inject.Inject;
import nl.bzk.brp.domain.berichtmodel.StufAntwoordBericht;
import nl.bzk.brp.service.algemeen.ServiceCallback;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * StufBerichtVerwerkerImpl.
 */
@Service
final class StufBerichtVerwerkerImpl implements StufBerichtVerwerker {

    @Inject
    private StufBerichtBerichtFactory stufBerichtBerichtFactory;
    @Inject
    private StufBerichtArchiveringService archiveringService;
    @Inject
    private StufBerichtService stufBerichtService;

    private StufBerichtVerwerkerImpl() {
    }

    @Override
    @Transactional(transactionManager = "masterTransactionManager")
    public void verwerkVerzoek(final StufBerichtVerzoek verzoek, final ServiceCallback<StufAntwoordBericht, String> callback) {
        final StufBerichtResultaat resultaat = stufBerichtService.verwerkVerzoek(verzoek);
        final StufAntwoordBericht antwoordBericht = stufBerichtBerichtFactory.maakAntwoordBericht(resultaat);
        callback.verwerkBericht(antwoordBericht);
        archiveringService.archiveer(resultaat, antwoordBericht, callback.getBerichtResultaat());
    }
}

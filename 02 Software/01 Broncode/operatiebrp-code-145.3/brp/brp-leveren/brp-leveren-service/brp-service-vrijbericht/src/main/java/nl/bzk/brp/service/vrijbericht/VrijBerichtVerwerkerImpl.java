/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.vrijbericht;

import javax.inject.Inject;
import nl.bzk.brp.domain.berichtmodel.VrijBerichtAntwoordBericht;
import nl.bzk.brp.service.algemeen.ServiceCallback;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * UCS VB.0.AV implementatie.
 */
@Service
final class VrijBerichtVerwerkerImpl implements VrijBerichtVerwerker {

    @Inject
    private VrijBerichtBerichtFactory vrijBerichtBerichtFactory;
    @Inject
    private VrijBerichtArchiveringService archiveringService;
    @Inject
    private VrijBerichtService vrijBerichtService;

    private VrijBerichtVerwerkerImpl() {
    }

    @Override
    @Transactional(transactionManager = "masterTransactionManager")
    public void stuurVrijBericht(final VrijBerichtVerzoek verzoek, final ServiceCallback<VrijBerichtAntwoordBericht, String> callback) {
        final VrijBerichtResultaat resultaat = vrijBerichtService.verwerkVerzoek(verzoek);
        final VrijBerichtAntwoordBericht antwoordBericht = vrijBerichtBerichtFactory.maakAntwoordBericht(resultaat);
        callback.verwerkBericht(antwoordBericht);
        archiveringService.archiveer(resultaat, antwoordBericht, callback.getBerichtResultaat());
    }
}

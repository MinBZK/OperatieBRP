/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.afnemerindicatie;

import java.util.Collection;
import java.util.List;
import nl.bzk.algemeenbrp.services.blobber.BlobException;
import nl.bzk.brp.domain.internbericht.selectie.SelectieAfnemerindicatieTaak;
import org.springframework.transaction.annotation.Transactional;

/**
 * OnderhoudAfnemerindicatieService.
 */
interface OnderhoudAfnemerindicatieSelectieService {
    /**
     * @param verzoeken verzoeken
     * @return resultaten
     * @throws BlobException blob fout
     */
    @Transactional(transactionManager = "masterTransactionManager")
    List<OnderhoudAfnemerindicatieResultaat> verwerk(Collection<SelectieAfnemerindicatieTaak> verzoeken) throws BlobException;
}

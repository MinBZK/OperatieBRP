/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.blobifier.service;

import javax.inject.Inject;

import nl.bzk.brp.blobifier.repository.alleenlezen.HisPersTabelRepository;
import nl.bzk.brp.blobifier.repository.alleenlezen.LeesPersoonCacheRepository;
import nl.bzk.brp.blobifier.repository.lezenenschrijven.SchrijfPersoonCacheRepository;
import nl.bzk.brp.blobifier.util.ChecksumBerekenaar;
import nl.bzk.brp.blobifier.util.SHA1ChecksumBerekenaar;

/**
 * Basis service voor blobifiers.
 */
public abstract class AbstractBlobifierService {

    /**
     * De repository voor het lezen van PersoonCaches.
     */
    @Inject
    protected LeesPersoonCacheRepository leesPersoonCacheRepository;

    /**
     * De repository voor het schrijven van PersoonCaches.
     */
    @Inject
    protected SchrijfPersoonCacheRepository schrijfPersoonCacheRepository;

    /**
     * De Berekening van de checksums van byte[] s.
     */
    protected ChecksumBerekenaar checksumBerekenaar = new SHA1ChecksumBerekenaar();

    /**
     * Repository voor PersoonHisVolledig.
     */
    @Inject
    protected HisPersTabelRepository hisPersTabelRepository;
}

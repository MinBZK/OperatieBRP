/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.blobconversie.service;

import java.util.List;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAfnemerindicatie;
import nl.bzk.algemeenbrp.services.blobber.BlobException;
import nl.bzk.algemeenbrp.services.blobber.Blobber;
import nl.bzk.brp.service.dalapi.AfnemerindicatieRepository;
import nl.bzk.brp.service.dalapi.PersoonCacheRepository;
import nl.bzk.brp.service.dalapi.PersoonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 */
@Service
@Transactional(transactionManager = "masterTransactionManager")
final class PersoonBlobServiceImpl implements PersoonBlobService {

    @Inject
    private PersoonCacheRepository persoonCacheRepository;

    @Inject
    private AfnemerindicatieRepository afnemerindicatieRepository;

    @Inject
    private Excel2DatabaseService excel2DatabaseService;

    @Inject
    private PersoonRepository persoonRepository;

    private PersoonBlobServiceImpl() {

    }

    @Override
    public void blobify(final Integer persId) throws BlobException {
        final Persoon persoon = persoonRepository.haalPersoonOp(persId);
        Assert.notNull(persoon, "Persoon niet gevonden met id: " + persId);
        final List<PersoonAfnemerindicatie> persoonAfnemerindicaties = afnemerindicatieRepository.haalAfnemerindicatiesOp(persId);
        final byte[] persoonBlob = Blobber.toJsonBytes(Blobber.maakBlob(persoon));
        final byte[] afnemerindicatiesBlob = Blobber.toJsonBytes(Blobber.maakBlob(persoonAfnemerindicaties));
        excel2DatabaseService.insertPersoonCache(persId, persoonBlob, afnemerindicatiesBlob);
    }
}

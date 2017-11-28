/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.blob;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonCache;
import nl.bzk.algemeenbrp.services.blobber.BlobException;
import nl.bzk.algemeenbrp.services.blobber.Blobber;
import nl.bzk.algemeenbrp.services.blobber.json.AfnemerindicatiesBlob;
import nl.bzk.algemeenbrp.services.blobber.json.PersoonBlob;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.BrpServiceRuntimeException;
import nl.bzk.brp.service.algemeen.StapException;
import nl.bzk.brp.service.dalapi.PersoonCacheRepository;
import org.springframework.stereotype.Service;

/**
 * Service voor het maken van Persoonsgegevens obv de Blob.
 */
@Service
final class PersoonslijstServiceImpl implements PersoonslijstService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private PersoonCacheRepository persoonCacheRepository;

    private PersoonslijstServiceImpl() {
    }

    private static PersoonData maakPersData(final PersoonCache cache) {
        return maakPersData(cache.getPersoonHistorieVolledigGegevens(), cache.getAfnemerindicatieGegevens(), cache.getLockversieAfnemerindicatie());
    }

    private static PersoonData maakPersData(final byte[] persoonHistorieVolledigGegevens, final byte[] afnemerindicatieGegevens,
                                            final Long lockversieAfnemerindicatie) {
        try {
            PersoonBlob persoonBlob = null;
            AfnemerindicatiesBlob afnemerindicatiesBlob = null;
            if (persoonHistorieVolledigGegevens.length > 0) {
                persoonBlob = Blobber.deserializeNaarPersoonBlob(persoonHistorieVolledigGegevens);
            }
            if (afnemerindicatieGegevens.length > 0) {
                afnemerindicatiesBlob = Blobber.deserializeNaarAfnemerindicatiesBlob(afnemerindicatieGegevens);
            }
            return new PersoonData(persoonBlob, afnemerindicatiesBlob, lockversieAfnemerindicatie);
        } catch (BlobException e) {
            LOGGER.error("Fout bij deserialiseren van persoon", e);
            throw new BrpServiceRuntimeException(e);
        }
    }

    @Override
    public Persoonslijst getById(final long persId) {
        LOGGER.debug("Persoonsgegevens ophalen voor persoon {}", persId);
        final PersoonCache cache = persoonCacheRepository.haalPersoonCacheOp(persId);
        if (cache == null) {
            return null;
        }
        return PersoonslijstFactory.maak(maakPersData(cache));
    }

    @Override
    public Persoonslijst maak(final byte[] persoonData, final byte[] afnemerindicatieData, final long afnemerindicatieLockVersie) {
        return PersoonslijstFactory.maak(maakPersData(persoonData, afnemerindicatieData, afnemerindicatieLockVersie));
    }

    @Override
    public List<Persoonslijst> getByIdsVoorZoeken(final Set<Long> persoonIds) throws StapException {
        final List<PersoonCache> caches = persoonCacheRepository.haalPersoonCachesOp(persoonIds);
        if (caches.size() != persoonIds.size()) {
            throw new StapException("minder blobs gevonden dan gevraagd, mag niet voorkomen");
        }
        final List<Persoonslijst> persoonslijstLijst = new ArrayList<>();
        for (PersoonCache cache : caches) {
            if (cache.getPersoonHistorieVolledigGegevens().length == 0) {
                throw new StapException("cache bevat geen persoonsgegevens blob");
            }
            persoonslijstLijst.add(PersoonslijstFactory.maak(maakPersData(cache)));
        }
        return persoonslijstLijst;
    }
}

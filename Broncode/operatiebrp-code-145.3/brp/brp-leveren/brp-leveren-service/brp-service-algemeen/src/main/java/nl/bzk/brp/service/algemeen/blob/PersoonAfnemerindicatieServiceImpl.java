/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.blob;

import java.time.ZonedDateTime;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.services.blobber.BlobException;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.service.dalapi.AfnemerindicatieRepository;
import nl.bzk.brp.service.dalapi.PersoonCacheRepository;
import org.springframework.stereotype.Service;

/**
 * PersoonAfnemerindicatieServiceImpl.
 */
@Service
@Bedrijfsregel(Regel.R1335)
@Bedrijfsregel(Regel.R1408)
final class PersoonAfnemerindicatieServiceImpl implements PersoonAfnemerindicatieService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private PersoonCacheRepository persoonCacheRepository;
    @Inject
    private AfnemerindicatieRepository afnemerindicatieRepository;
    @Inject
    private PersoonslijstService persoonslijstService;

    private PersoonAfnemerindicatieServiceImpl() {

    }

    @Override
    public void verwijderAfnemerindicatie(final AfnemerindicatieParameters afnemerindicatieParameters, final Partij partij,
                                          final int dienstId,
                                          final int leveringsautorisatieId) {
        afnemerindicatieRepository.verwijderAfnemerindicatie(afnemerindicatieParameters.getPersoonId(), partij.getId(), leveringsautorisatieId, dienstId);
        LOGGER.info("Afnemerindicatie verwijderd. Leveringautorisatie {}, persoonId {}, dienstId {}, partijCode {}", leveringsautorisatieId,
                afnemerindicatieParameters.getPersoonId(),
                dienstId,
                partij.getCode());
    }

    @Override
    @Bedrijfsregel(Regel.R1408)
    public void plaatsAfnemerindicatie(final AfnemerindicatieParameters afnemerindicatieParameters, final Partij partij,
                                       final int leveringsautorisatieId,
                                       final int dienstId, final Integer datumAanvangMaterielePeriode,
                                       final Integer datumEindeVolgen, final ZonedDateTime tsReg) {
        afnemerindicatieRepository
                .plaatsAfnemerindicatie(afnemerindicatieParameters.getPersoonId(), partij.getId(), leveringsautorisatieId, dienstId, datumEindeVolgen,
                        datumAanvangMaterielePeriode, tsReg);
        LOGGER.info("Afnemerindicatie geplaatst. Leveringsautorisatie {}, persoonId {}, dienstId {}, partijCode {}", leveringsautorisatieId,
                afnemerindicatieParameters.getPersoonId(), dienstId, partij.getCode());
    }

    @Override
    public void updateAfnemerindicatieBlob(final AfnemerindicatieParameters afnemerindicatieParameters) throws BlobException {
        persoonCacheRepository.updateAfnemerindicatieBlob(afnemerindicatieParameters.getPersoonId(), afnemerindicatieParameters.getPersoonLockVersie(),
                afnemerindicatieParameters.getAfnemerindicatieLockVersie());
    }

}

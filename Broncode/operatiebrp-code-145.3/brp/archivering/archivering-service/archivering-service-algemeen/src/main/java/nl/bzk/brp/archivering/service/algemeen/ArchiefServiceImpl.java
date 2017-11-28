/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.archivering.service.algemeen;

import javax.inject.Inject;
import nl.bzk.brp.archivering.domain.algemeen.ArchiveringOpdracht;
import nl.bzk.brp.archivering.service.dal.ArchiefBerichtRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Verwerk archivering verzoek.
 */
@Service
public final class ArchiefServiceImpl implements ArchiefService {

    private ArchiefBerichtRepository berichtRepository;

    /**
     * @param archiefBerichtRepository archiefBerichtRepository
     */
    @Inject
    public ArchiefServiceImpl(final ArchiefBerichtRepository archiefBerichtRepository) {
        this.berichtRepository = archiefBerichtRepository;
    }


    @Override
    @Transactional(transactionManager = "archiveringTransactionManager")
    public void archiveer(final ArchiveringOpdracht opdracht) {
        berichtRepository.accept(ArchiveringOpdrachtConverteerder.converteer(opdracht));
    }
}

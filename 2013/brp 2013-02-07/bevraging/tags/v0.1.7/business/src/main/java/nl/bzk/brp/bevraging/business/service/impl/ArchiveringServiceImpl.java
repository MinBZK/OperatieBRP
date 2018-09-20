/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.service.impl;

import javax.inject.Inject;

import nl.bzk.brp.bevraging.business.service.ArchiveringService;
import nl.bzk.brp.bevraging.domein.ber.Bericht;
import nl.bzk.brp.bevraging.domein.ber.Richting;
import nl.bzk.brp.bevraging.domein.repository.BerichtRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * Standaard implementatie van de {@link ArchiveringService} die archiveert naar de database.
 */
@Service
public class ArchiveringServiceImpl implements ArchiveringService {

    @Inject
    private BerichtRepository berichtRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Long archiveer(final String data, final Richting richting) {
        Bericht bericht = new Bericht(data, richting);
        berichtRepository.save(bericht);
        return bericht.getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void werkDataBij(final Long berichtId, final String data) {
        Bericht bericht = berichtRepository.findOne(berichtId);
        bericht.setData(data);
        berichtRepository.save(bericht);
    }

}

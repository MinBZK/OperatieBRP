/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.service;

import java.util.Calendar;

import javax.inject.Inject;

import nl.bzk.brp.business.dto.BerichtenIds;
import nl.bzk.brp.dataaccess.repository.BerichtRepository;
import nl.bzk.brp.model.operationeel.ber.PersistentBericht;
import nl.bzk.brp.model.operationeel.ber.Richting;
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
    public BerichtenIds archiveer(final String ingaandBerichtData) {
        PersistentBericht ingaandBericht = new PersistentBericht();
        ingaandBericht.setDatumTijdOntvangst(Calendar.getInstance());
        ingaandBericht.setRichting(Richting.INGAAND);
        ingaandBericht.setData(ingaandBerichtData);
        berichtRepository.save(ingaandBericht);
        PersistentBericht uitgaandBericht = new PersistentBericht();
        uitgaandBericht.setRichting(Richting.UITGAAND);
        uitgaandBericht.setData("<Wordt nader bepaald>");
        uitgaandBericht.setAntwoordOp(ingaandBericht);
        berichtRepository.save(uitgaandBericht);
        return new BerichtenIds(ingaandBericht.getId(), uitgaandBericht.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void werkDataBij(final Long uitgaandBerichtId, final String data) {
        PersistentBericht uitgaandBericht = berichtRepository.findOne(uitgaandBerichtId);
        if (uitgaandBericht == null) {
            throw new RuntimeException(String.format("uitgaand bericht %s ontbreekt in database", uitgaandBerichtId));
        }
        uitgaandBericht.setData(data);
        uitgaandBericht.setDatumTijdVerzenden(Calendar.getInstance());
        berichtRepository.save(uitgaandBericht);
    }

}

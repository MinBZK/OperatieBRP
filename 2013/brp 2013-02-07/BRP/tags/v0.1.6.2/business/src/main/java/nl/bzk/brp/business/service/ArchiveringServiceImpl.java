/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.service;

import java.sql.Timestamp;
import java.util.Calendar;

import javax.inject.Inject;

import nl.bzk.brp.business.dto.BerichtenIds;
import nl.bzk.brp.dataaccess.repository.BerichtMdlRepository;
import nl.bzk.brp.model.attribuuttype.BerichtData;
import nl.bzk.brp.model.attribuuttype.DatumTijd;
import nl.bzk.brp.model.objecttype.operationeel.BerichtModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Richting;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * Standaard implementatie van de {@link ArchiveringService} die archiveert naar de database.
 */
@Service
public class ArchiveringServiceImpl implements ArchiveringService {

    @Inject
    private BerichtMdlRepository berichtRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public BerichtenIds archiveer(final String ingaandBerichtData) {
        BerichtModel ingaandBericht = new BerichtModel(Richting.INGAAND, new BerichtData(ingaandBerichtData));
        ingaandBericht.setDatumTijdOntvangst(new DatumTijd(new Timestamp(Calendar.getInstance().getTimeInMillis())));
        berichtRepository.save(ingaandBericht);

        BerichtModel uitgaandBericht = new BerichtModel(Richting.INGAAND, new BerichtData("<Wordt nader bepaald>"));
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
        BerichtModel uitgaandBericht = berichtRepository.findOne(uitgaandBerichtId);
        if (uitgaandBericht == null) {
            throw new RuntimeException(String.format("uitgaand bericht %s ontbreekt in database", uitgaandBerichtId));
        }
        uitgaandBericht.setData(new BerichtData(data));
        uitgaandBericht.setDatumTijdVerzenden(new DatumTijd(new Timestamp(Calendar.getInstance().getTimeInMillis())));
        berichtRepository.save(uitgaandBericht);
    }

}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.service.stamdata;

import java.util.Collection;
import javax.inject.Inject;
import nl.bzk.brp.beheer.service.dal.StamdataRepository;
import org.springframework.stereotype.Service;

/**
 * Implementatie van {@link StatischeStamdataService}.
 */
@Service
final class StatischeStamdataServiceImpl implements StatischeStamdataService {

    private final StamdataRepository stamdataRepository;

    @Inject
    StatischeStamdataServiceImpl(StamdataRepository stamdataRepository) {
        this.stamdataRepository = stamdataRepository;
    }

    /**
     * select naam, identdbschema  from kern.element where id in (select distinct element.tabel from kern.element) order by naam;
     * TODO haal schema uit elementtabel. wordt nu nog meegegeven in verzoek.
     */
    @Override
    public Collection<StatischeStamdataDTO> getStatischeStamdata(final String tabel) {
        return stamdataRepository.getStatischeStamdata(tabel);
    }

}

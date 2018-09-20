/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.algemeen.service.impl;

import javax.inject.Inject;
import nl.bzk.brp.levering.algemeen.cache.PartijCache;
import nl.bzk.brp.levering.algemeen.service.PartijService;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

/**
 * De implementatie van de stam tabel service {@link nl.bzk.brp.levering.algemeen.service.PartijService}.
 */
@Service
public class PartijServiceImpl implements PartijService {

    @Inject
    private PartijCache partijCache;

    @Override
    public final Partij vindPartijOpCode(final int code) {
        final Partij partij = partijCache.geefPartij(code);
        if (partij == null) {
            throw new EmptyResultDataAccessException(1);
        }
        return partij;
    }

    @Override
    public final boolean bestaatPartij(final int code) {
        return partijCache.geefPartij(code) != null;
    }

}

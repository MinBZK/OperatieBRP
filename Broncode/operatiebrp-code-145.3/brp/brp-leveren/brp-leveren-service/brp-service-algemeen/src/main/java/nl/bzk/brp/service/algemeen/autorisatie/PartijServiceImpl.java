/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.autorisatie;

import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.service.cache.PartijCache;
import org.springframework.stereotype.Service;

/**
 * De implementatie van de stam tabel service {@link PartijService}.
 */
@Service
final class PartijServiceImpl implements PartijService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private PartijCache partijCache;

    private PartijServiceImpl() {

    }


    @Override
    public Short vindPartijIdOpCode(final String code) {
        final Partij partij = partijCache.geefPartij(code);
        if (partij == null) {
            LOGGER.info("Partij niet gevonden of niet actueel en geldig voor code " + code);
        }
        return partij == null ? null : partij.getId();
    }

    @Override
    public Partij vindPartijOpCode(final String code) {
        final Partij partij = partijCache.geefPartij(code);
        if (partij == null) {
            LOGGER.info("Partij niet gevonden of niet actueel en geldig voor code " + code);
        }
        return partij;
    }

    @Override
    public Partij vindPartijOpId(final short id) {
        final Partij partij = partijCache.geefPartijMetId(id);
        if (partij == null) {
            LOGGER.info("Partij niet gevonden of niet actueel en geldig voor id " + id);
        }
        return partij;
    }

    @Override
    public Partij vindPartijOpOin(final String oin) {
        return partijCache.geefPartijMetOin(oin);
    }

    @Override
    public Partij geefBrpPartij() {
        return vindPartijOpCode(Partij.PARTIJ_CODE_BRP);
    }

}

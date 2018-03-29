/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.autconv.lo3naarbrp;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Converteert een GBA PartijRol naar een BRP PartijRol.
 */
@Component
final class PartijRolConversie {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private final Map<Integer, Integer> partijrolConversieMap = Maps.newHashMap();

    @PersistenceContext(unitName = "nl.bzk.brp.master")
    private EntityManager entityManager;

    @Inject
    private PartijConversie partijConversie;

    private PartijRolConversie() {
    }

    void converteerPartijRol(final PartijRol partijRolOud) {
        partijConversie.converteerPartij(partijRolOud.getPartij());
        if (partijrolConversieMap.containsKey(partijRolOud.getId())) {
            LOGGER.info("PartijRol reeds geconverteerd, id: {}", partijRolOud.getId());
            return;
        }
        LOGGER.info("Start omzetten PartijRol met id: {}", partijRolOud.getId());
        final Partij partijRef = entityManager.getReference(Partij.class,
                partijConversie.getPartijConversieMap().get(partijRolOud.getPartij().getId()));
        final PartijRol partijrolNieuw = new PartijRol(partijRef, partijRolOud.getRol());
        partijrolNieuw.setDatumIngang(partijRolOud.getDatumIngang());
        partijrolNieuw.setDatumEinde(partijRolOud.getDatumEinde());
        partijrolNieuw.setActueelEnGeldig(partijRolOud.isActueelEnGeldig());
        final PartijRol mergePartijRol = entityManager.merge(partijrolNieuw);
        partijrolConversieMap.put(partijRolOud.getId(), mergePartijRol.getId());
    }

    Map<Integer, Integer> getPartijrolConversieMap() {
        return partijrolConversieMap;
    }
}

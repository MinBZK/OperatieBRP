/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.autconv.lo3naarbrp;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Converteert een GBA dienst naar een BRP dienst.
 */
@Component
final class DienstConversie {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private final Map<Integer, Integer> dienstConversieMap = Maps.newHashMap();

    @PersistenceContext(unitName = "nl.bzk.brp.master")
    private EntityManager entityManager;

    private DienstConversie() {
    }

    void converteerDienst(final Dienst dienst, final Dienstbundel mergedDienstbundel) {
        LOGGER.info("Start omzetten Dienst met id: {}", dienst.getId());
        final Dienst dienstNieuw = new Dienst(mergedDienstbundel, dienst.getSoortDienst());
        dienstNieuw.setEffectAfnemerindicaties(dienst.getEffectAfnemerindicaties());
        dienstNieuw.setDatumIngang(dienst.getDatumIngang());
        dienstNieuw.setDatumEinde(dienst.getDatumEinde());
        dienstNieuw.setIndicatieGeblokkeerd(dienst.getIndicatieGeblokkeerd());
        dienstNieuw.setAttenderingscriterium(dienst.getAttenderingscriterium());
        dienstNieuw.setEersteSelectieDatum(dienst.getEersteSelectieDatum());
        dienstNieuw.setActueelEnGeldig(dienst.isActueelEnGeldig());
        dienstNieuw.setActueelEnGeldigVoorAttendering(dienst.isActueelEnGeldigVoorAttendering());
        dienstNieuw.setActueelEnGeldigVoorSelectie(dienst.isActueelEnGeldigVoorSelectie());
        dienstNieuw.setActueelEnGeldigVoorZoeken(dienst.isActueelEnGeldigVoorZoeken());
        dienstNieuw.setMaximumAantalZoekresultaten(dienst.getMaximumAantalZoekresultaten());
        final Dienst mergedDienst = entityManager.merge(dienstNieuw);
        dienstConversieMap.put(dienst.getId(), mergedDienst.getId());
    }

    public Map<Integer, Integer> getDienstConversieMap() {
        return dienstConversieMap;
    }
}

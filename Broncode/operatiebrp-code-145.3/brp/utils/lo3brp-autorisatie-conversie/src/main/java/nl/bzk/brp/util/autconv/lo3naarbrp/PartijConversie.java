/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.autconv.lo3naarbrp;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Converteert een GBA partij naar een BRP partij.
 */
@Component
final class PartijConversie {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @PersistenceContext(unitName = "nl.bzk.brp.master")
    private EntityManager entityManager;

    private final AtomicInteger partijCodeRange = new AtomicInteger();
    private final Map<Short, Short> partijConversieMap = Maps.newHashMap();

    private PartijConversie() {
    }

    @PostConstruct
    void postConstruct() {
        final String maxPartijCode = (String) entityManager.
                createNativeQuery("select max(code) || '' from kern.partij where code <= '900000'").getSingleResult();
        LOGGER.info("max partijCode = {}", maxPartijCode);
        partijCodeRange.set(Integer.parseInt(maxPartijCode));
    }

    void converteerPartij(final Partij partijOud) {
        if (partijConversieMap.containsKey(partijOud.getId())) {
            LOGGER.info("Partij reeds geconverteerd, id: {}", partijOud.getId());
            return;
        }
        LOGGER.info("Start omzetten Partij met id: {}", partijOud.getId());
        final String naam = BrpPostfix.appendTo(partijOud.getNaam(), partijOud.getId());
        final String code = StringUtils.leftPad(String.valueOf(partijCodeRange.incrementAndGet()), 6, "0");
        final Partij partijNieuw = new Partij(naam, code);
        partijNieuw.setDatumIngang(partijOud.getDatumIngang());
        partijNieuw.setDatumEinde(partijOud.getDatumEinde());
        partijNieuw.setOin(partijOud.getOin());
        partijNieuw.setSoortPartij(partijOud.getSoortPartij());
        partijNieuw.setIndicatieVerstrekkingsbeperkingMogelijk(partijOud.isIndicatieVerstrekkingsbeperkingMogelijk());
        partijNieuw.setIndicatieAutomatischFiatteren(partijOud.getIndicatieAutomatischFiatteren());
        partijNieuw.setDatumOvergangNaarBrp(DatumUtil.vandaag());
        partijNieuw.setActueelEnGeldig(partijOud.isActueelEnGeldig());
        final Partij mergePartij = entityManager.merge(partijNieuw);
        partijConversieMap.put(partijOud.getId(), mergePartij.getId());
    }

    Map<Short, Short> getPartijConversieMap() {
        return partijConversieMap;
    }
}

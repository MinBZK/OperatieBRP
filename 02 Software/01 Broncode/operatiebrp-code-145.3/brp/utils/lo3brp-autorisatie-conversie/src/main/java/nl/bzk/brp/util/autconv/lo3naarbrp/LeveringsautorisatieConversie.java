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
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

/**
 * Converteert een GBA leveringsautorisatie naar een BRP leveringsautorisatie.
 */
@Component
final class LeveringsautorisatieConversie {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private final Map<Integer, Integer> leveringsautorisatieConversieMap = Maps.newHashMap();

    @Inject
    private DienstbundelConversie dienstbundelConversie;

    @PersistenceContext(unitName = "nl.bzk.brp.master")
    private EntityManager entityManager;

    private LeveringsautorisatieConversie() {
    }

    void converteerLeveringsautorisaties() {
        final Session session = (Session) entityManager.getDelegate();
        final ScrollableResults scResults = session.createCriteria(Leveringsautorisatie.class).scroll(ScrollMode.FORWARD_ONLY);
        while (scResults.next()) {
            final Leveringsautorisatie leveringsautorisatie = (Leveringsautorisatie) scResults.get(0);
            if (Stelsel.GBA == leveringsautorisatie.getStelsel()) {
                converteerLeveringautorisatie(leveringsautorisatie);
                entityManager.flush();
                entityManager.clear();
            }
        }
    }

    private void converteerLeveringautorisatie(final Leveringsautorisatie leveringsautorisatie) {
        LOGGER.info("Start omzetten LO3Leveringsautorisatie met naam: {}", leveringsautorisatie.getNaam());
        final Leveringsautorisatie leveringsautorisatieNieuw = new Leveringsautorisatie(Stelsel.BRP, leveringsautorisatie.getIndicatieModelautorisatie());
        leveringsautorisatieNieuw.setNaam(BrpPostfix.appendTo(leveringsautorisatie.getNaam(), leveringsautorisatie.getId()));
        leveringsautorisatieNieuw.setProtocolleringsniveau(leveringsautorisatie.getProtocolleringsniveau());
        leveringsautorisatieNieuw.setIndicatieAliasSoortAdministratieveHandelingLeveren(leveringsautorisatie
                .getIndicatieAliasSoortAdministratieveHandelingLeveren());
        leveringsautorisatieNieuw.setDatumIngang(leveringsautorisatie.getDatumIngang());
        leveringsautorisatieNieuw.setDatumEinde(leveringsautorisatie.getDatumEinde());
        leveringsautorisatieNieuw.setPopulatiebeperking(leveringsautorisatie.getPopulatiebeperking());
        leveringsautorisatieNieuw.setToelichting(leveringsautorisatie.getToelichting());
        leveringsautorisatieNieuw.setIndicatieGeblokkeerd(leveringsautorisatie.getIndicatieGeblokkeerd());
        leveringsautorisatieNieuw.setActueelEnGeldig(leveringsautorisatie.isActueelEnGeldig());
        final Leveringsautorisatie mergedLeveringsautorisatie = entityManager.merge(leveringsautorisatieNieuw);
        leveringsautorisatieConversieMap.put(leveringsautorisatie.getId(), mergedLeveringsautorisatie.getId());

        leveringsautorisatie.getDienstbundelSet().forEach(d ->
                dienstbundelConversie.converteerDienstbundel(d, mergedLeveringsautorisatie));
    }

    Map<Integer, Integer> getLeveringsautorisatieConversieMap() {
        return leveringsautorisatieConversieMap;
    }
}

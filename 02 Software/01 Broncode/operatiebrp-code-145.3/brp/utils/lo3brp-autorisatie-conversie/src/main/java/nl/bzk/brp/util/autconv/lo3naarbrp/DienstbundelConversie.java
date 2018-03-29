/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.autconv.lo3naarbrp;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Converteert een GBA dienstbundel naar een BRP dienstbundel.
 */
@Component
final class DienstbundelConversie {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private DienstConversie dienstConversie;
    @Inject
    private RubriekConversie rubriekConversie;
    @PersistenceContext(unitName = "nl.bzk.brp.master")
    private EntityManager entityManager;

    private DienstbundelConversie() {
    }

    void converteerDienstbundel(final Dienstbundel lo3dienstbundel, final Leveringsautorisatie brpLeveringsautorisatie) {
        LOGGER.info("Start omzetten Dienstbundel met id: {}", lo3dienstbundel.getId());
        final Dienstbundel dienstbundelNieuw = new Dienstbundel(brpLeveringsautorisatie);
        dienstbundelNieuw.setNaam(BrpPostfix.appendTo(lo3dienstbundel.getNaam(), lo3dienstbundel.getId()));
        dienstbundelNieuw.setDatumIngang(lo3dienstbundel.getDatumIngang());
        dienstbundelNieuw.setDatumEinde(lo3dienstbundel.getDatumEinde());
        dienstbundelNieuw.setNaderePopulatiebeperking(lo3dienstbundel.getNaderePopulatiebeperking());
        dienstbundelNieuw.setIndicatieNaderePopulatiebeperkingVolledigGeconverteerd(lo3dienstbundel
                .getIndicatieNaderePopulatiebeperkingVolledigGeconverteerd());
        dienstbundelNieuw.setToelichting(lo3dienstbundel.getToelichting());
        dienstbundelNieuw.setIndicatieGeblokkeerd(lo3dienstbundel.getIndicatieGeblokkeerd());
        dienstbundelNieuw.setActueelEnGeldig(lo3dienstbundel.isActueelEnGeldig());
        final Dienstbundel mergedDienstbundel = entityManager.merge(dienstbundelNieuw);
        for (Dienst dienst : lo3dienstbundel.getDienstSet()) {
            dienstConversie.converteerDienst(dienst, mergedDienstbundel);
        }
        rubriekConversie.converteerRubrieken(mergedDienstbundel,
                lo3dienstbundel.getDienstbundelLo3RubriekSet());
    }
}

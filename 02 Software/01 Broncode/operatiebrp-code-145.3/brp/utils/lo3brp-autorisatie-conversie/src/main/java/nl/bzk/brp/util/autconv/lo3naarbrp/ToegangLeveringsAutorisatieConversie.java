/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.autconv.lo3naarbrp;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Converteert een GBA toegang naar een BRP toegang.
 */
@Component
final class ToegangLeveringsAutorisatieConversie {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private String brpAfleverpunt;
    @Inject
    private PartijRolConversie partijRolConversie;
    @Inject
    private LeveringsautorisatieConversie leveringsautorisatieConversie;
    @PersistenceContext(unitName = "nl.bzk.brp.master")
    private EntityManager entityManager;

    private ToegangLeveringsAutorisatieConversie() {
    }

    @PostConstruct
    void postConstruct() {
        this.brpAfleverpunt = System.getProperty("brp.afleverpunt");
        Assert.notNull(brpAfleverpunt, "brp.afleverpunt property niet gevuld");
    }

    void converteerToegangLeveringsautorisaties() {
        final Session session = (Session) entityManager.getDelegate();
        final ScrollableResults scResults = session.createCriteria(ToegangLeveringsAutorisatie.class).scroll(ScrollMode.FORWARD_ONLY);
        while (scResults.next()) {
            final ToegangLeveringsAutorisatie toegang = (ToegangLeveringsAutorisatie) scResults.get(0);
            if (Stelsel.GBA == toegang.getLeveringsautorisatie().getStelsel()) {

                if (toegang.getLeveringsautorisatie().getDatumEinde() != null) {
                    LOGGER.warn("ToegangLeveringsAutorisatie met id {} wordt niet geconverteerd, "
                                    + "want leveringsautorisatie '{}' met id {} is niet geldig",
                            toegang.getId(),
                            toegang.getLeveringsautorisatie().getNaam(),
                            toegang.getLeveringsautorisatie().getId());
                    continue;
                }
                converteerToegangLeveringsAutorisatie(toegang);
                entityManager.flush();
                entityManager.clear();
            }
        }
    }

    private void converteerToegangLeveringsAutorisatie(final ToegangLeveringsAutorisatie toegangOud) {
        LOGGER.info("Start omzetten ToegangLeveringsAutorisatie met id: {}", toegangOud.getId());
        partijRolConversie.converteerPartijRol(toegangOud.getGeautoriseerde());
        final PartijRol partijRolRef = entityManager
                .find(PartijRol.class, partijRolConversie.getPartijrolConversieMap().get(toegangOud.getGeautoriseerde().getId()));
        final Leveringsautorisatie leveringsautorisatieRef = entityManager.getReference(Leveringsautorisatie.class,
                leveringsautorisatieConversie.getLeveringsautorisatieConversieMap().get(toegangOud.getLeveringsautorisatie().getId()));
        final ToegangLeveringsAutorisatie toegangNieuw = new ToegangLeveringsAutorisatie(partijRolRef, leveringsautorisatieRef);
        toegangNieuw.setDatumIngang(toegangOud.getDatumIngang());
        toegangNieuw.setDatumEinde(toegangOud.getDatumEinde());
        toegangNieuw.setNaderePopulatiebeperking(toegangOud.getNaderePopulatiebeperking());
        toegangNieuw.setIndicatieGeblokkeerd(toegangOud.getIndicatieGeblokkeerd());
        toegangNieuw.setActueelEnGeldig(toegangOud.isActueelEnGeldig());

        //altijd leeg in GBA
        toegangNieuw.setTransporteur(null);
        toegangNieuw.setOndertekenaar(null);
        toegangNieuw.setAfleverpunt(brpAfleverpunt);
        entityManager.merge(toegangNieuw);
    }
}

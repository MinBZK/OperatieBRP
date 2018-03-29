/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.dataaccess;

import com.google.common.collect.Lists;
import java.time.ZonedDateTime;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAfnemerindicatie;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.service.dalapi.AfnemerindicatieRepository;
import nl.bzk.brp.tooling.apitest.service.basis.Stateful;
import org.springframework.util.Assert;

/**
 * Stub voor {@link AfnemerindicatieRepository}.
 */
class AfnemerindicatieRepositoryStub implements AfnemerindicatieRepository, AfnemerindicatieStubService, Stateful {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private final List<AfnemerindicatieEvent> verwijderdeAfnemerindicaties = Lists.newLinkedList();
    private final List<AfnemerindicatieEvent> geplaatsteAfnemerindicaties = Lists.newLinkedList();

    @Inject
    private PersoonDataStubService persoonDataStubService;

    @Override
    public List<PersoonAfnemerindicatie> haalAfnemerindicatiesOp(final long persoonId) {
        throw new Error("Afnemerindicaties moeten uit de BLOB komen");
    }

    @Override
    public void plaatsAfnemerindicatie(final long persoonId, final short partijId, final int leveringsautorisatieId, final int dienstId,
                                       final Integer datumEindeVolgen,
                                       final Integer datumAanvangMaterielePeriode, final ZonedDateTime tijdstipRegistratie) {
        geplaatsteAfnemerindicaties.add(new AfnemerindicatieEvent(persoonId, partijId, leveringsautorisatieId, dienstId));
    }

    @Override
    public void verwijderAfnemerindicatie(final long persoonId, final short partijId, final int leveringsautorisatieId, final int dienstId) {
        verwijderdeAfnemerindicaties.add(new AfnemerindicatieEvent(persoonId, partijId, leveringsautorisatieId, dienstId));
    }

    @Override
    public void reset() {
        verwijderdeAfnemerindicaties.clear();
        geplaatsteAfnemerindicaties.clear();
    }


    @Override
    public void assertAfnemerindicatieGeplaatst(final String bsn, final Integer leveringsautorisatieId, final Short partijId) {
        LOGGER.debug("Assert afnemerindicatie geplaatst voor bsn: {} en leveringautorisatie: {}", bsn, leveringsautorisatieId);
        Assert.isTrue(persoonDataStubService.getBsnIdMap().containsKey(bsn), String.format("Bsn %s bestaat niet", bsn));
        final Long persId = persoonDataStubService.getBsnIdMap().get(bsn).iterator().next();
        for (final AfnemerindicatieEvent geplaatsteAfnemerindicatie : geplaatsteAfnemerindicaties) {
            if (geplaatsteAfnemerindicatie.getPersoonId() == persId
                    && leveringsautorisatieId == geplaatsteAfnemerindicatie.getLeveringsautorisatieId()
                    && partijId == geplaatsteAfnemerindicatie.getPartijId()) {
                return;
            }
        }
        throw new AssertionError(String.format("Er is geen afnemerindicatie "
                + "geplaatst voor bsn: %s en leveringautorisatie: %s", bsn, leveringsautorisatieId));
    }

    @Override
    public void assertAfnemerindicatieNietGeplaatst(final String bsn, final Integer leveringsautorisatieId, final Short partijId) {
        LOGGER.debug("Assert afnemerindicatie NIET geplaatst voor bsn: {} en leveringautorisatie: {}", bsn, leveringsautorisatieId);
        final Long persId = persoonDataStubService.getBsnIdMap().get(bsn).iterator().next();
        for (final AfnemerindicatieEvent geplaatsteAfnemerindicatie : geplaatsteAfnemerindicaties) {
            if (geplaatsteAfnemerindicatie.getPersoonId() == persId
                    && leveringsautorisatieId == geplaatsteAfnemerindicatie.getLeveringsautorisatieId()
                    && partijId == geplaatsteAfnemerindicatie.getPartijId()) {
                throw new AssertionError(String.format("Afnemerindicatie "
                        + "wel geplaatst voor bsn: %s en leveringautorisatie: %s", bsn, leveringsautorisatieId));
            }
        }
    }

    @Override
    public void assertAfnemerindicatieVerwijderd(final String bsn, final Integer leveringsautorisatieId, final Short partijId) {
        LOGGER.debug("Assert afnemerindicatie verwijderd voor bsn: {} en leveringautorisatie: {}", bsn, leveringsautorisatieId);
        if (!isAfnemerIndicatieVerwijderd(bsn, leveringsautorisatieId, partijId)) {
            throw new AssertionError(String.format("Er is geen afnemerindicatie verwijderd voor bsn: %s "
                    + "en leveringautorisatie: %s", bsn, leveringsautorisatieId));
        }
    }

    @Override
    public void assertAfnemerindicatieNietVerwijderd(final String bsn, final Integer leveringsautorisatieId, final Short partijId) {
        LOGGER.debug("Assert afnemerindicatie niet verwijderd voor bsn: {} en leveringautorisatie: {}", bsn, leveringsautorisatieId);
        if (isAfnemerIndicatieVerwijderd(bsn, leveringsautorisatieId, partijId)) {
            throw new AssertionError(String.format("Er is een afnemerindicatie verwijderd voor bsn: %s "
                    + "en leveringautorisatie: %s", bsn, leveringsautorisatieId));
        }
    }

    private boolean isAfnemerIndicatieVerwijderd(final String bsn, final Integer leveringsautorisatieId, final Short partijId) {
        final Long persId = persoonDataStubService.getBsnIdMap().get(bsn).iterator().next();
        for (final AfnemerindicatieEvent event : verwijderdeAfnemerindicaties) {
            if (event.getPersoonId() == persId
                    && leveringsautorisatieId == event.getLeveringsautorisatieId()
                    && partijId == event.getPartijId()) {
                return true;
            }
        }
        return false;
    }


    /**
     * Afnemerindicatie event
     */
    private static final class AfnemerindicatieEvent {

        private final long persoonId;
        private final short partijId;
        private final int leveringsautorisatieId;
        private final int dienstId;

        AfnemerindicatieEvent(final long persoonId, final short partijId, final int leveringsautorisatieId, final int dienstId) {
            this.persoonId = persoonId;
            this.partijId = partijId;
            this.leveringsautorisatieId = leveringsautorisatieId;
            this.dienstId = dienstId;
        }

        public long getPersoonId() {
            return persoonId;
        }

        public short getPartijId() {
            return partijId;
        }

        public int getLeveringsautorisatieId() {
            return leveringsautorisatieId;
        }

        public int getDienstId() {
            return dienstId;
        }
    }
}

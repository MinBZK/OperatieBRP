/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.mutatielevering.leveringbepaling;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.logging.MDC;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Populatie;
import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.logging.LeveringVeld;
import nl.bzk.brp.service.cache.LeveringsAutorisatieCache;
import nl.bzk.brp.service.mutatielevering.dto.Mutatiehandeling;
import nl.bzk.brp.service.mutatielevering.dto.Mutatielevering;
import nl.bzk.brp.service.mutatielevering.leveringbepaling.filter.LeveringFilterService;
import nl.bzk.brp.service.mutatielevering.leveringbepaling.populatiebepaling.PopulatieBepalingService;
import org.springframework.stereotype.Component;


/**
 * Deze stap bepaalt de leveringsautorisatie populatie voor de te controleren leveringsautorisaties.
 */
@Component
final class MutatieleveringServiceImpl implements MutatieleveringService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private LeveringsAutorisatieCache leveringsAutorisatieCache;

    @Inject
    private PopulatieBepalingService populatieBepalingService;

    @Inject
    private LeveringFilterService leveringFilterService;

    private MutatieleveringServiceImpl() {
    }

    @Override
    public List<Mutatielevering> bepaalLeveringen(final Mutatiehandeling mutatiehandeling) {
        final List<Mutatielevering> mutatieleveringList = Lists.newLinkedList();
        final List<Autorisatiebundel> leveringAutorisaties = leveringsAutorisatieCache.geefAutorisatieBundelsVoorMutatielevering();
        LOGGER.debug("Aantal te evalueren autorisaties is {}", leveringAutorisaties.size());
        for (final Autorisatiebundel autorisatiebundel : leveringAutorisaties) {
            final Map<String, String> mdcMap = Maps.newHashMap();
            mdcMap.put(LeveringVeld.MDC_LEVERINGAUTORISATIEID.getVeld(), String.valueOf(autorisatiebundel.getLeveringsautorisatieId()));
            mdcMap.put(LeveringVeld.MDC_PARTIJ_ID.getVeld(), String.valueOf(autorisatiebundel.getPartij().getCode()));
            mdcMap.put(LeveringVeld.MDC_AANGEROEPEN_DIENST.getVeld(), String.valueOf(autorisatiebundel.getDienst().getId()));
            try (MDC.MDCCloser closer = MDC.putData(mdcMap)) {
                bepaalTeLeverenPersonen(mutatiehandeling, autorisatiebundel, mutatieleveringList);
            } catch (ExpressieException expressieExceptie) {
                LOGGER.error("Er is een fout opgetreden tijdens het bepalen van de leveringsautorisatie populatie map.",
                        expressieExceptie);
            }
        }
        LOGGER.debug("Aantal leveringen is {}", mutatieleveringList.size());
        return ImmutableList.copyOf(mutatieleveringList);
    }


    /**
     * bepaalTeLeverenPersonen.
     */
    private void bepaalTeLeverenPersonen(final Mutatiehandeling mutatiehandeling,
                                         final Autorisatiebundel autorisatiebundel,
                                         final List<Mutatielevering> mutatieleveringList) throws ExpressieException {
        final Map<Persoonslijst, Populatie> persoonsgegevensPopulatieMap = populatieBepalingService
                .bepaalPersoonPopulatieCorrelatie(mutatiehandeling, autorisatiebundel);
        LOGGER.debug("Resultaat populatiebepaling: {}", persoonsgegevensPopulatieMap);
        final Set<Persoonslijst> personen = leveringFilterService
                .bepaalTeLeverenPersonen(autorisatiebundel, persoonsgegevensPopulatieMap);
        persoonsgegevensPopulatieMap.keySet().retainAll(personen);
        LOGGER.debug("Resultaat te leveren personen voor autorisatie [{}] na filtering is: {}",
                autorisatiebundel.getLeveringsautorisatie().getNaam(),
                persoonsgegevensPopulatieMap.keySet());
        LOGGER.debug("Aantal te leveren personen voor autorisatie [{}] na filtering is: {}",
                autorisatiebundel.getLeveringsautorisatie().getNaam(),
                persoonsgegevensPopulatieMap.keySet().size());

        if (!persoonsgegevensPopulatieMap.isEmpty()) {
            mutatieleveringList.add(new Mutatielevering(autorisatiebundel, ImmutableMap.copyOf(persoonsgegevensPopulatieMap)));
        }
    }
}

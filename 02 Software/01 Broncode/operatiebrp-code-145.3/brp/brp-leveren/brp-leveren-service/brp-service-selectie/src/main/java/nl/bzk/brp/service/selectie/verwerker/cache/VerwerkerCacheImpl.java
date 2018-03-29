/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.verwerker.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.expressie.SelectieLijst;
import nl.bzk.brp.domain.internbericht.selectie.SelectieAutorisatieBericht;
import nl.bzk.brp.service.algemeen.autorisatie.LeveringsautorisatieService;
import org.springframework.stereotype.Component;

/**
 * VerwerkerAutorisatieCache. Lazy init cache die tijdens een run autorisatie data en selectie lijsten opbouwt.
 */
@Component
public final class VerwerkerCacheImpl implements VerwerkerCache {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private AtomicInteger cacheVersion = new AtomicInteger(0);

    @Inject
    private LeveringsautorisatieService leveringsautorisatieService;

    @Inject
    private SelectieLijstMakerService selectieLijstMakerService;


    private final ConcurrentHashMap<SelectieAutorisatieBericht, Autorisatiebundel> autorisatiebundelMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Integer, SelectieLijst> selectieLijstMap = new ConcurrentHashMap<>();

    private VerwerkerCacheImpl() {
    }

    @Override
    public Autorisatiebundel getAutorisatiebundel(final SelectieAutorisatieBericht selectieAutorisatieBericht, final Integer selectierunId) {
        cacheVersion.updateAndGet(value -> value != selectierunId ? clearUpdate(selectierunId) : value);
        return autorisatiebundelMap.computeIfAbsent(selectieAutorisatieBericht, this::maakBundel);
    }


    @Override
    public SelectieLijst getSelectieLijst(final Integer dienstId, final Integer selectieTaakId, final Integer selectierunId) {
        cacheVersion.updateAndGet(value -> value != selectierunId ? clearUpdate(selectierunId) : value);
        return selectieLijstMap.computeIfAbsent(selectieTaakId, s -> selectieLijstMakerService.maak(dienstId, s));
    }


    private Autorisatiebundel maakBundel(SelectieAutorisatieBericht selectieAutorisatieBericht) {
        final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie =
                leveringsautorisatieService.geefToegangLeveringsAutorisatie(selectieAutorisatieBericht.getToegangLeveringsAutorisatieId());
        final Dienst dienst = AutAutUtil.zoekDienst(toegangLeveringsAutorisatie.getLeveringsautorisatie(), selectieAutorisatieBericht.getDienstId());
        return new Autorisatiebundel(toegangLeveringsAutorisatie, dienst);
    }

    @Override
    public void clear() {
        LOGGER.info("clear autorisatie verwerker cache");
        autorisatiebundelMap.clear();
        selectieLijstMap.clear();
    }

    private int clearUpdate(Integer selectierunId) {
        clear();
        return selectierunId;
    }
}

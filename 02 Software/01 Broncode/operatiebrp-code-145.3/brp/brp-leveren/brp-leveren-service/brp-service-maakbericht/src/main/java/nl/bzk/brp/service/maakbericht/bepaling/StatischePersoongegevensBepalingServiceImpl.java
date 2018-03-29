/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.bepaling;

import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Verwerkingssoort;
import nl.bzk.brp.domain.leveringmodel.MetaModel;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import org.springframework.stereotype.Service;

/**
 * StatischePersoonGegevensBepalingServiceImpl.
 */
@Service
final class StatischePersoongegevensBepalingServiceImpl implements StatischePersoongegevensBepalingService {

    @Inject
    private MutatieleveringDeltabepalingService mutatieleveringDeltabepalingService;

    @Inject
    private MutatieleveringVerwerkingssoortService mutatieleveringVerwerkingssoortService;

    @Inject
    private BepaalPreRelatieGegevensService bepaalPreRelatieGegevensService;

    @Inject
    private BepaalVolgnummerService bepaalVolgnummerService;

    private StatischePersoongegevensBepalingServiceImpl() {
    }

    @Override
    public StatischePersoongegevens bepaal(final Persoonslijst persoonslijst, final boolean isMutatielevering) {
        final StatischePersoongegevens statischePersoongegevens = new StatischePersoongegevens();
        if (isMutatielevering) {
            final Set<MetaRecord> deltaRecords = mutatieleveringDeltabepalingService.execute(persoonslijst);
            statischePersoongegevens.setDeltaRecords(deltaRecords);
            final Map<MetaModel, Verwerkingssoort> verwerkingssoortMap = mutatieleveringVerwerkingssoortService.execute(persoonslijst);
            statischePersoongegevens.setVerwerkingssoortMap(verwerkingssoortMap);
        }
        final Set<MetaRecord> preRelatieRecords = bepaalPreRelatieGegevensService.bepaal(persoonslijst);
        final Map<MetaObject, Integer> volgnummerMap = bepaalVolgnummerService.bepaalVolgnummers(persoonslijst);
        statischePersoongegevens.setVolgnummerMap(volgnummerMap);
        statischePersoongegevens.setPreRelatieRecords(preRelatieRecords);
        return statischePersoongegevens;
    }

}

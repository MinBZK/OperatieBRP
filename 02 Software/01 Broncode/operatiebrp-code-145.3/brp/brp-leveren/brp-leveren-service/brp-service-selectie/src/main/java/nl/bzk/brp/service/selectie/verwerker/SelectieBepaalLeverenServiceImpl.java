/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.verwerker;

import com.google.common.collect.Lists;
import java.util.function.BooleanSupplier;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSelectie;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.expressie.SelectieLijst;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.VerstrekkingsbeperkingService;
import nl.bzk.brp.service.algemeen.expressie.ExpressieService;
import nl.bzk.brp.service.selectie.verwerker.cache.VerwerkerCache;
import org.springframework.stereotype.Service;

/**
 * Bepaalt of de gegeven persoon met de gegeven autorisatie in de selectie opgenomen wordt.
 */
@Service
@Bedrijfsregel(Regel.R2059)
@Bedrijfsregel(Regel.R2540)
@Bedrijfsregel(Regel.R1983)
final class SelectieBepaalLeverenServiceImpl implements SelectieBepaalLeverenService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private ExpressieService expressieService;
    @Inject
    private VerstrekkingsbeperkingService verstrekkingsbeperkingService;
    @Inject
    private VerwerkerCache verwerkerCache;

    private SelectieBepaalLeverenServiceImpl() {
    }

    @Override
    public boolean inSelectie(final Persoonslijst persoonslijst, final Autorisatiebundel autorisatiebundel, final int datumStartSelectie,
                              final Integer selectierunId, final boolean lijstGebruiken, final Integer selectieTaakId) {
        final Persoonslijst persoonNu = persoonslijst.getNuNuBeeld();
        final Dienst dienst = autorisatiebundel.getDienst();
        if (dienst == null) {
            throw new NullPointerException("dienst kan niet null zijn");
        }
        //voor soort selectie verwijder afnemerindicatie zijn personen met verstrekkingsbeperking in selectie
        final BooleanSupplier persoonHeeftGeenVerstrekkingsbeperking = () ->
                (dienst.getSoortSelectie() == SoortSelectie.SELECTIE_MET_VERWIJDERING_AFNEMERINDICATIE.getId() ||
                        !verstrekkingsbeperkingService.heeftGeldigeVerstrekkingsbeperking(persoonNu, autorisatiebundel.getPartij()));
        final BooleanSupplier heeftGeldigeExpressie = () -> {
            try {
                final Expressie expressieParsed = expressieService.geefPopulatiebeperking(autorisatiebundel);
                final SelectieLijst selectiebestandLijst;
                if (lijstGebruiken) {
                    selectiebestandLijst = verwerkerCache.getSelectieLijst(dienst.getId(), selectieTaakId, selectierunId);
                } else {
                    selectiebestandLijst = SelectieLijst.GEEN_LIJST;
                }
                final Boolean evaluatieResultaat = expressieService.evalueerMetSelectieDatumEnSelectielijst(expressieParsed, persoonslijst,
                        datumStartSelectie, selectiebestandLijst);

                LOGGER.debug("persoon {} voldoet {} aan selectieexpressie", persoonslijst.getId(),
                        evaluatieResultaat == Boolean.TRUE ? "WEL" : "NIET");
                return evaluatieResultaat;
            } catch (ExpressieException e) {
                LOGGER.error("Er is een fout opgetreden tijdens de populatiebepaling van de persoon.", e);
                return false;
            }
        };
        final boolean inSelectie = Lists.newArrayList(persoonHeeftGeenVerstrekkingsbeperking, heeftGeldigeExpressie).stream()
                .allMatch(BooleanSupplier::getAsBoolean);
        LOGGER.debug("persoon {} zit {} in selectie", persoonslijst.getId(), inSelectie ? "WEL" : "NIET");
        return inSelectie;
    }
}

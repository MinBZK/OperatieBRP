/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.expressie;

import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.expressie.BooleanLiteral;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.expressie.SelectieLijst;
import nl.bzk.brp.domain.expressie.BRPExpressies;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.cache.LeveringsAutorisatieCache;
import org.springframework.stereotype.Service;

/**
 * De implementatie van de expressieservice. Deze service biedt methoden die te maken hebben met expressies, zoals het evalueren van een expressie en het
 * opvragen van expressies bij een leveringsautorisatie en het combineren van populatiebperkingen voor specifieke diensten.
 */
@Service
final class ExpressieServiceImpl implements ExpressieService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private LeveringsAutorisatieCache leveringAutorisatieCache;

    private ExpressieServiceImpl() {

    }

    @Override
    @Bedrijfsregel(Regel.R1334)
    public boolean bepaalPersoonGewijzigd(final Persoonslijst oud, Persoonslijst nieuw, final Expressie expressie) {
        final Expressie result = BRPExpressies.evalueerAttenderingsCriterium(expressie, oud, nieuw);
        return result.alsBoolean();
    }

    @Override
    public Expressie geefPopulatiebeperking(final Autorisatiebundel autorisatiebundel) throws ExpressieException {
        return leveringAutorisatieCache.geefPopulatiebeperking(autorisatiebundel.getToegangLeveringsautorisatie(), autorisatiebundel.getDienst());
    }

    @Override
    public Boolean evalueerMetSelectieDatumEnSelectielijst(final Expressie expressieParsed, final Persoonslijst persoonslijst, final int datumStartSelectie,
                                                           final SelectieLijst selectieLijst) {
        try {
            final Expressie
                    evaluatieResultaat =
                    BRPExpressies.evalueerMetSelectieDatumEnSelectielijst(expressieParsed, persoonslijst, datumStartSelectie, selectieLijst);
            return evaluatieResultaat == BooleanLiteral.WAAR;
        } catch (ExpressieException e) {
            LOGGER.error("Er is een fout opgetreden tijdens de populatiebepaling van de persoon.", e);
            return false;
        }
    }

    /**
     * Geef het attenderings criterium op basis van de levering autorisatie.
     * @param autorisatiebundel de levering autorisatie
     * @return de expressie
     */
    @Override
    public Expressie geefAttenderingsCriterium(final Autorisatiebundel autorisatiebundel) {
        return leveringAutorisatieCache.geefAttenderingExpressie(autorisatiebundel.getDienst());
    }

    /**
     * Evalueert een expressie.
     * @param expressie expressie
     * @param persoon persoon die geevalueerd wordt
     * @return true als aan expressie voldaan wordt, anders false
     */
    @Override
    public Boolean evalueer(final Expressie expressie, final Persoonslijst persoon) {
        final Expressie resultaat;
        try {
            resultaat = BRPExpressies.evalueer(expressie, persoon);
            return resultaat.alsBoolean();
        } catch (ExpressieException e) {
            LOGGER.error(
                    "Fout bij het uitvoeren van expressie: '{}' op persoon met id {}.",
                    expressie.alsString(),
                    persoon.getMetaObject().getObjectsleutel(),
                    e);
        }
        return false;
    }
}

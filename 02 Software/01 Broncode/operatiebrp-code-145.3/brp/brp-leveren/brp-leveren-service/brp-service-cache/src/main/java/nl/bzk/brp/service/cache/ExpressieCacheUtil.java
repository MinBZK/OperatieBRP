/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.cache;

import com.google.common.collect.Lists;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSelectie;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.expressie.BooleanLiteral;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.expressie.functie.SelectieLijstFunctie;
import nl.bzk.brp.domain.expressie.parser.ExpressieParser;
import org.apache.commons.lang3.StringUtils;

/**
 * ExpressieCacheUtil.
 */
final class ExpressieCacheUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final String EN_FUNCTIE = " EN ";

    private ExpressieCacheUtil() {

    }

    /**
     * @param toegangLeveringsAutorisatie toegangLeveringsAutorisatie
     * @param dienstbundel dienstbundel
     * @return expressie
     */
    @Bedrijfsregel(Regel.R2059)
    static Expressie maakTotalePopulatieBeperkingExpressie(final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie, final Dienstbundel
            dienstbundel) {
        //maak pop bepaling expr voor dienstbundel
        final String enClause = wrap(StringUtils.join(new String[]{
                getExpressieString(toegangLeveringsAutorisatie.getLeveringsautorisatie().getPopulatiebeperking()),
                getExpressieString(dienstbundel.getNaderePopulatiebeperking()),
                getExpressieString(toegangLeveringsAutorisatie.getNaderePopulatiebeperking())
        }, EN_FUNCTIE));

        return parseExpressie(enClause, toegangLeveringsAutorisatie.getId());
    }

    /**
     * Maakt de totale selectie expressie.
     * @param toegangLeveringsAutorisatie toegangLeveringsAutorisatie
     * @param dienst dienstbundel
     * @return expressie
     */
    @Bedrijfsregel(Regel.R2540)
    static Expressie maakSelectieExpressie(final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie, final Dienst dienst) {
        final List<String> expressieLijst = Lists.newArrayList(
                getExpressieString(dienst.getNadereSelectieCriterium()), SelectieLijstFunctie.EXPRESSIE);
        if (SoortSelectie.SELECTIE_MET_VERWIJDERING_AFNEMERINDICATIE != SoortSelectie.parseId(dienst.getSoortSelectie())) {
            expressieLijst.addAll(0, Lists.newArrayList(
                    getExpressieString(toegangLeveringsAutorisatie.getLeveringsautorisatie().getPopulatiebeperking()),
                    getExpressieString(dienst.getDienstbundel().getNaderePopulatiebeperking()),
                    getExpressieString(toegangLeveringsAutorisatie.getNaderePopulatiebeperking())));
        }
        final String enClause = wrap(StringUtils.join(expressieLijst, EN_FUNCTIE));
        return parseExpressie(enClause, toegangLeveringsAutorisatie.getId());
    }

    /**
     * @param expressieString expressieString
     * @param toegangleveringsautorisatieId toegangleveringsautorisatieId
     * @return valide
     */
    static boolean valideerExpressie(final String expressieString, final int toegangleveringsautorisatieId) {
        boolean valide = true;
        if (StringUtils.isNotEmpty(expressieString)) {
            try {
                ExpressieParser.parse(expressieString);
            } catch (ExpressieException e) {
                LOGGER.warn("Validatie van expressie {} voor toegangleveringsautorisatie {} is niet succesvol: {}", expressieString,
                        toegangleveringsautorisatieId, e);
                valide = false;
            }
        }
        return valide;
    }

    /**
     * @param expressieString expressieString
     * @param toegangleveringsautorisatieId toegangleveringsautorisatieId
     * @return expressie
     */
    static Expressie parseExpressie(final String expressieString, final int toegangleveringsautorisatieId) {
        try {
            return ExpressieParser.parse(expressieString);
        } catch (ExpressieException e) {
            LOGGER.error("Parsing van expressie {} voor toegangleveringsautorisatie {} is niet succesvol: {}",
                    expressieString, toegangleveringsautorisatieId, e);
            return null;
        }
    }

    /**
     * @param expressieString expressieString
     * @return expressie string
     */
    private static String getExpressieString(final String expressieString) {
        String expressieWaarde = BooleanLiteral.WAAR.toString();
        if (expressieString != null && !StringUtils.isBlank(expressieString)) {
            expressieWaarde = expressieString;
        }
        return wrap(expressieWaarde);
    }

    private static String wrap(String value) {
        return "(" + value + ")";
    }
}

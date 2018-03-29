/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.autorisatie;

import java.util.List;

/**
 */
final class DslParser {

    private List<ToegangLeveringautorisatieParser> toegangLeveringautorisatyParseerResultaats;
    private LeveringautorisatieParser leveringautorisatieParser;

    /**
     * Maakt een nieuw parseer resultaat object
     *
     * @param tlaDSL toegangleveringautorisatie parser
     * @param laDSL  laDSL parser
     */
    DslParser(final List<ToegangLeveringautorisatieParser> tlaDSL,
              final LeveringautorisatieParser laDSL)
    {
        this.toegangLeveringautorisatyParseerResultaats = tlaDSL;
        this.leveringautorisatieParser = laDSL;
    }

    /**
     * Schrijft de toegangleveringautorisatie naar de gegeven writer
     *
     * @param autorisatieData een Writer om naar te schrijven.
     */
    public void collect(final AutorisatieData autorisatieData) {
        leveringautorisatieParser.collect(autorisatieData);
        for (ToegangLeveringautorisatieParser toegangLeveringautorisatieParser : toegangLeveringautorisatyParseerResultaats) {
            toegangLeveringautorisatieParser.collect(autorisatieData);
        }
    }
}

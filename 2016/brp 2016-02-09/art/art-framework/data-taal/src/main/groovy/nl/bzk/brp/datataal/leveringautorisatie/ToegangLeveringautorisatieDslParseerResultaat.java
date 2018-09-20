/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.datataal.leveringautorisatie;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

/**
 */
public final class ToegangLeveringautorisatieDslParseerResultaat {

    private List<ToegangLeveringautorisatieDsl> toegangLeveringautorisatieDsls;
    private LeveringautorisatieDsl              leveringautorisatieDsl;

    /**
     * Maakt een nieuw parseer resultaat object
     * @param tlaDSL toegangleveringautorisatie parser
     * @param laDSL laDSL parser
     */
    public ToegangLeveringautorisatieDslParseerResultaat(final List<ToegangLeveringautorisatieDsl> tlaDSL,
                                                         final LeveringautorisatieDsl laDSL) {
        this.toegangLeveringautorisatieDsls = tlaDSL;
        this.leveringautorisatieDsl = laDSL;
    }

    public List<ToegangLeveringautorisatieDsl> getToegangLeveringautorisatieDsls() {
        return toegangLeveringautorisatieDsls;
    }

    public LeveringautorisatieDsl getLeveringautorisatieDsl() {
        return leveringautorisatieDsl;
    }


    /**
     * @return de toegangleveringautorisatie als SQL String
     */
    public String toSql() {
        final StringWriter sw = new StringWriter();
        try {
            toSQL(sw);
        } catch (IOException e) {
            throw new RuntimeException("Kan dsl niet omzetten naar SQL", e);
        }
        return sw.toString();
    }

    /**
     * Schrijft de toegangleveringautorisatie naar de gegeven writer
     *
     * @param writer een Writer om naar te schrijven.
     * @throws IOException als het schrijven mislukt
     */
    public void toSQL(final Writer writer) throws IOException {
        leveringautorisatieDsl.toSQL(writer);
        for (ToegangLeveringautorisatieDsl toegangLeveringautorisatieDsl : toegangLeveringautorisatieDsls) {
            toegangLeveringautorisatieDsl.toSQL(writer);
        }
    }
}

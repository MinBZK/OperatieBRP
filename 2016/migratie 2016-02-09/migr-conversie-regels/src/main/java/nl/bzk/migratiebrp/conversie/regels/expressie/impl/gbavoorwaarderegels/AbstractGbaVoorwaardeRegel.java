/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl.gbavoorwaarderegels;

import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaRubriekNaarBrpTypeVertaler;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaVoorwaardeRegel;

/**
 * Een GBA voorwaarde regel vertaald een voorwaarde regel naar een BRP expressie.
 *
 */
public abstract class AbstractGbaVoorwaardeRegel implements GbaVoorwaardeRegel {

    /** Scheidings character gba voorwaarde regel. */
    protected static final String SPLIT_CHARACTER = " ";
    /** Rubriek deel van de voorwaarderegel. */
    protected static final int DEEL_RUBRIEK = 0;
    /** Operator deel van de voorwaarderegel. */
    protected static final int DEEL_OPERATOR = 1;
    /** Restdeel van de voorwaarderegel. */
    protected static final int DEEL_REST = 2;
    /** Aantal delen van een gba voorwaarde regel. */
    protected static final int DEEL_AANTAL = 3;

    /** kleiner dan of gelijk teken. */
    protected static final String KLEINER_GELIJK = "<=";
    /** groter dan of gelijk teken. */
    protected static final String GROTER_GELIJK = ">=";
    /** kleiner dan teken. */
    protected static final String KLEINER = "<";
    /** groter dan teken. */
    protected static final String GROTER = ">";
    /** gelijk teken. */
    protected static final String GELIJK = "=";
    /** ongelijk teken. */
    protected static final String ONGELIJK = "<>";

    /** Operator enum voor gebruik binnen de voorwaarde subclassen. */
    protected enum Operator {
        /** Gelijk aan 1 operator. */
        GA1(GELIJK),
        /** Ongelijka aan 1 operator. */
        OGA1(ONGELIJK),
        /** Ongelijk aan alle operator. */
        GAA(GELIJK),
        /** Ongelijk aan alle operator. */
        OGAA(ONGELIJK),
        /** Groter dan 1 operator. */
        GD1(GROTER),
        /** kleiner dan 1 operator. */
        KD1(KLEINER),
        /** Groter dan of gelijk aan 1 operator. */
        GDOG1(GROTER_GELIJK),
        /** Kleiner dan of gelijk aan 1 operator. */
        KDOG1(KLEINER_GELIJK),
        /** Groter dan alle operator. */
        GDA(GROTER),
        /** kleiner dan all operator. */
        KDA(KLEINER),
        /** Groter dan of gelijk aan alle operator. */
        GDOGA(GROTER_GELIJK),
        /** Kleiner dan of gelijk aan alle operator. */
        KDOGA(KLEINER_GELIJK);


        private final String operatorString;

        private Operator(final String operatorString) {
            this.operatorString = operatorString;
        }

        /**
         * Geeft de waarde van de operator.
         * @return de operator voor de BRP expressie
         */
        public String getOperatorString() {
            return operatorString;
        }
    }

    @Inject
    private GbaRubriekNaarBrpTypeVertaler gbaRubriekNaarBrpTypeVertaler;

    private final int volgorde;
    private final String regexPatroon;

    /**
     * Maakt een nieuw voorwaarderegel aan.
     * @param volgorde volgorde waarin voorwaarderegel moet worden uitgevoerd
     * @param regexPatroon reguliere expressie waaraan voorwaarde regel moet voldoen
     */
    public AbstractGbaVoorwaardeRegel(final int volgorde, final String regexPatroon) {
        this.volgorde = volgorde;
        this.regexPatroon = regexPatroon;
    }

    /**
     *
     * Haalt de huidige GbaRubriekNaarBrpVertaler op.
     * @return GbaRubriekNaarBrpVertaler
     */
    public final GbaRubriekNaarBrpTypeVertaler getGbaRubriekNaarBrpTypeVertaler() {
        return gbaRubriekNaarBrpTypeVertaler;
    }

    @Override
    public final int volgorde() {
        return volgorde;
    }

    @Override
    public final boolean filter(final String voorwaarde) {
        return voorwaarde.matches(regexPatroon);
    }
}

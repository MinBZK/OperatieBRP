/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie;

/**
 * Representeert constante periode-expressies.
 */
public final class PeriodeLiteral implements Literal {

    private final Datumdeel jaar;
    private final Datumdeel maand;
    private final Datumdeel dag;

    /**
     * Constructor voor periode-expressies.
     *
     * @param aJaar  Aantal jaar (mag negatief zijn).
     * @param aMaand Aantal maanden (mag negatief zijn).
     * @param aDag   Aantal dagen (mag negatief zijn).
     */
    public PeriodeLiteral(final Datumdeel aJaar, final Datumdeel aMaand, final Datumdeel aDag) {
        this.jaar = aJaar;
        this.maand = aMaand;
        this.dag = aDag;
    }

    /**
     * @return het jaar van de  periode
     */
    public Datumdeel getJaar() {
        return jaar;
    }

    /**
     * @return de maand van de periode
     */
    public Datumdeel getMaand() {
        return maand;
    }

    /**
     * @return de dag van de periode
     */
    public Datumdeel getDag() {
        return dag;
    }

    @Override
    public ExpressieType getType(final Context context) {
        return ExpressieType.PERIODE;
    }

    @Override
    public String toString() {
        return String.format("%c%s%c%s%c%s", '^', jaar, '/', maand, '/', dag);
    }
}

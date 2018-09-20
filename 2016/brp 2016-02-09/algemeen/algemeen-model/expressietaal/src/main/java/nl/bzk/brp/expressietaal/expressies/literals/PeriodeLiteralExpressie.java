/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.expressies.literals;

import nl.bzk.brp.expressietaal.Characters;
import nl.bzk.brp.expressietaal.Context;
import nl.bzk.brp.expressietaal.ExpressieType;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;

/**
 * Representeert constante periode-expressies.
 */
public final class PeriodeLiteralExpressie extends AbstractNietNumeriekRepresenteerbareLiteralExpressie {

    private final int jaar;
    private final int maand;
    private final int dag;

    /**
     * Constructor voor periode-expressies.
     *
     * @param aJaar  Aantal jaar (mag negatief zijn).
     * @param aMaand Aantal maanden (mag negatief zijn).
     * @param aDag   Aantal dagen (mag negatief zijn).
     */
    public PeriodeLiteralExpressie(final int aJaar, final int aMaand, final int aDag) {
        super();
        this.jaar = aJaar;
        this.maand = aMaand;
        this.dag = aDag;
    }

    /**
     * Constructor voor periode-expressie.
     *
     * @param aPeriode Gecodeerde periode in een enkele integer (vergelijkbaar met datum).
     */
    public PeriodeLiteralExpressie(final int aPeriode) {
        super();
        jaar = aPeriode / DatumLiteralExpressie.JAARFACTOR;
        maand = (aPeriode - jaar * DatumLiteralExpressie.JAARFACTOR) / DatumLiteralExpressie.MAANDFACTOR;
        dag = aPeriode - jaar * DatumLiteralExpressie.JAARFACTOR - maand * DatumLiteralExpressie.MAANDFACTOR;
    }

    public int getJaar() {
        return jaar;
    }

    public int getMaand() {
        return maand;
    }

    public int getDag() {
        return dag;
    }

    /**
     * Geeft TRUE als de periode leeg is, dat wil zeggen dat alle onderdelen (jaar, maand, dag) nul zijn.
     *
     * @return TRUE als de periode leeg.
     */
    public boolean isLeeg() {
        return jaar == 0 && maand == 0 && dag == 0;
    }

    @Override
    public ExpressieType getType(final Context context) {
        return ExpressieType.PERIODE;
    }

    @Override
    public boolean alsBoolean() {
        // Default boolean-waarde voor een expressie is FALSE.
        return false;
    }

    @Override
    public String alsString() {
        return stringRepresentatie();
    }

    @Override
    public Attribuut getAttribuut() {
        return null;
    }

    @Override
    public Groep getGroep() {
        return null;
    }

    @Override
    protected String stringRepresentatie() {
        return String.format("%c%d%c%d%c%d", Characters.PERIODE_START, jaar, Characters.DATUM_SCHEIDINGSTEKEN, maand,
            Characters.DATUM_SCHEIDINGSTEKEN, dag);
    }
}

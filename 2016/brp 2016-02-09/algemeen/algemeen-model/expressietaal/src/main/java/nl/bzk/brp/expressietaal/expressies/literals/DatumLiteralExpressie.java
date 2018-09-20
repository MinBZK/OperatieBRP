/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.expressies.literals;

import nl.bzk.brp.expressietaal.Characters;
import nl.bzk.brp.expressietaal.Context;
import nl.bzk.brp.expressietaal.ExpressieType;
import nl.bzk.brp.expressietaal.util.DatumUtils;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import org.joda.time.DateTime;

/**
 * Representeert constante datumexpressies.
 */
public class DatumLiteralExpressie extends AbstractLiteralExpressie implements Comparable<DatumLiteralExpressie> {

    /**
     * Factor voor het jaartal in een gecodeerde datum.
     */
    public static final int JAARFACTOR  = 10000;
    /**
     * Factor voor de maand in een gecodeerde datum.
     */
    public static final int MAANDFACTOR = 100;

    /**
     * Minimum jaartal dat nog als correct beschouwd wordt.
     */
    public static final int MINIMUM_JAARTAL = 1800;
    /**
     * Maximum jaartal dat nog als correct beschouwd wordt.
     */
    public static final int MAXIMUM_JAARTAL = 3000;

    /**
     * Code voor onbekend jaartal in een datum.
     */
    private static final int JAAR_ONBEKEND  = 0;
    /**
     * Code voor onbekende maand in een datum.
     */
    private static final int MAAND_ONBEKEND = 0;
    /**
     * Code voor een onbekende dag in een datum.
     */
    private static final int DAG_ONBEKEND   = 0;

    private static final String ONBEKEND_DATUMDEEL_STRING = "?";
    private static final String VARIABELE_TWEE_CIJFERS = "%02d";
    private static final int EENENDERTIG = 31;

    private Datumdeel jaar;
    private Datumdeel maand;
    private Datumdeel dag;

    /**
     * Constructor.
     *
     * @param aJaar  Jaartal.
     * @param aMaand Maandnummer.
     * @param aDag   Dagnummer.
     */
    public DatumLiteralExpressie(final Datumdeel aJaar, final Datumdeel aMaand, final Datumdeel aDag)
    {
        super();
        this.jaar = aJaar;
        this.maand = aMaand;
        this.dag = aDag;
    }

    /**
     * Constructor.
     *
     * @param datum Datum als integer, zoals gebruikt wordt in de Java mapping van het BMR.
     */
    public DatumLiteralExpressie(final int datum) {
        super();
        setDatum(datum);
    }

    /**
     * Constructor.
     *
     * @param object Datumwaarde.
     */
    public DatumLiteralExpressie(final Object object) {
        super();
        if (object instanceof Integer) {
            setDatum((Integer) object);
        } else if (object instanceof DateTime) {
            final DateTime dt = (DateTime) object;
            this.jaar = new Datumdeel(dt.getYear());
            this.maand = new Datumdeel(dt.getMonthOfYear());
            this.dag = new Datumdeel(dt.getDayOfMonth());
        } else {
            setDatum(0);
        }
    }

    /**
     * Constructor.
     *
     * @param dt DateTime-waarde voor datum literal.
     */
    public DatumLiteralExpressie(final DateTime dt) {
        this(new Datumdeel(dt.getYear()), new Datumdeel(dt.getMonthOfYear()), new Datumdeel(dt.getDayOfMonth()));
    }

    /**
     * Geeft jaartal van de datum.
     *
     * @return Jaartal.
     */
    public Datumdeel getJaar() {
        return jaar;
    }

    /**
     * Geeft maandnummer van de datum.
     *
     * @return Maandnummer.
     */
    public Datumdeel getMaand() {
        return maand;
    }

    /**
     * Geeft dagnummer van de datum.
     *
     * @return Dagnummer.
     */
    public Datumdeel getDag() {
        return dag;
    }

    /**
     * Zet de datumdelen op basis van een datum gerepresenteerd door een integer.
     *
     * @param datum Integerrepresentatie van de datum.
     */
    private void setDatum(final int datum) {
        final int dJaar = datum / JAARFACTOR;
        final int dMaand = (datum - dJaar * JAARFACTOR) / MAANDFACTOR;
        final int dDag = datum - dJaar * JAARFACTOR - dMaand * MAANDFACTOR;

        if (dJaar != JAAR_ONBEKEND) {
            jaar = new Datumdeel(dJaar);
        } else {
            jaar = Datumdeel.ONBEKEND_DATUMDEEL;
        }
        if (dMaand != MAAND_ONBEKEND) {
            maand = new Datumdeel(dMaand);
        } else {
            maand = Datumdeel.ONBEKEND_DATUMDEEL;
        }
        if (dDag != DAG_ONBEKEND) {
            dag = new Datumdeel(dDag);
        } else {
            dag = Datumdeel.ONBEKEND_DATUMDEEL;
        }
    }

    /**
     * Geeft datum als een DateTime-object, mits alle datumdelen bekend zijn; anders NULL.
     *
     * @return Datum als DateTime-object.
     */
    public DateTime alsDateTime() {
        DateTime dateTime = null;
        if (isVolledigBekend()) {
            dateTime =
                new DateTime(jaar.getWaarde(), maand.getWaarde(), dag.getWaarde(), DatumUtils.MIDDAGUUR, 0, 0, 0);
        }
        return dateTime;
    }

    /**
     * Geeft TRUE als de datum volledig bekend is.
     *
     * @return TRUE als de datum volledig bekend is.
     */
    public final boolean isVolledigBekend() {
        return !jaar.isOnbekend() && !maand.isOnbekend() && !dag.isOnbekend();
    }

    public final boolean isVolledigOnbekend() {
        return jaar.isOnbekend() && maand.isOnbekend() && dag.isOnbekend();
    }

    public final boolean isDagOnbekend() {
        return dag.isOnbekend();
    }

    public final boolean isMaandDagOnbekend() {
        return dag.isOnbekend() && maand.isOnbekend();
    }

    @Override
    public ExpressieType getType(final Context context) {
        return ExpressieType.DATUM;
    }

    @Override
    public boolean alsBoolean() {
        // Default boolean-waarde voor een expressie is FALSE.
        return false;
    }

    @Override
    public int alsInteger() {
        return jaar.getWaarde() * JAARFACTOR + maand.getWaarde() * MAANDFACTOR + dag.getWaarde();
    }

    @Override
    public long alsLong() {
        return (long) alsInteger();
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
        final String dagStr;
        final String maandStr;
        final String jaarStr;

        if (dag.isOnbekend()) {
            dagStr = ONBEKEND_DATUMDEEL_STRING;
        } else {
            dagStr = String.format(VARIABELE_TWEE_CIJFERS, dag.getWaarde());
        }
        if (maand.isOnbekend()) {
            maandStr = ONBEKEND_DATUMDEEL_STRING;
        } else {
            maandStr = String.format(VARIABELE_TWEE_CIJFERS, maand.getWaarde());
        }
        if (jaar.isOnbekend()) {
            jaarStr = ONBEKEND_DATUMDEEL_STRING;
        } else {
            jaarStr = String.format("%04d", jaar.getWaarde());
        }

        return String.format("%s%c%s%c%s", jaarStr, Characters.DATUM_SCHEIDINGSTEKEN, maandStr,
            Characters.DATUM_SCHEIDINGSTEKEN, dagStr);
    }

    @Override
    public int compareTo(final DatumLiteralExpressie o) {
        return Integer.valueOf(this.alsInteger()).compareTo(o.alsInteger());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final DatumLiteralExpressie that = (DatumLiteralExpressie) o;

        if (!getDag().equals(that.getDag())) {
            return false;
        }
        if (!getJaar().equals(that.getJaar())) {
            return false;
        }
        if (!getMaand().equals(that.getMaand())) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = jaar.hashCode();
        result = EENENDERTIG * result + maand.hashCode();
        result = EENENDERTIG * result + dag.hashCode();
        return result;
    }
}


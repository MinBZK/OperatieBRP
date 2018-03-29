/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie;

import com.google.common.hash.HashCode;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import org.apache.commons.lang3.builder.EqualsBuilder;

/**
 * Representeert constante datumexpressies.
 */
public class DatumLiteral implements Literal {

    /**
     * Aantal maanden in jaar.
     */
    public static final String ONBEKEND_DATUMDEEL_STRING = "?";
    /**
     * Factor voor het jaartal in een gecodeerde datum.
     */
    private static final int JAARFACTOR = 10_000;
    /**
     * Factor voor de maand in een gecodeerde datum.
     */
    private static final int MAANDFACTOR = 100;

    private static final String VARIABELE_TWEE_CIJFERS = "%02d";

    private Datumdeel jaar = Datumdeel.ONBEKEND_DATUMDEEL;
    private Datumdeel maand = Datumdeel.ONBEKEND_DATUMDEEL;
    private Datumdeel dag = Datumdeel.ONBEKEND_DATUMDEEL;

    /**
     * Constructor.
     *
     * @param aJaar  Jaartal.
     * @param aMaand Maandnummer.
     * @param aDag   Dagnummer.
     */
    public DatumLiteral(final Datumdeel aJaar, final Datumdeel aMaand, final Datumdeel aDag) {
        this.jaar = aJaar;
        this.maand = aMaand;
        this.dag = aDag;
    }

    /**
     * Constructor.
     * Zet de datumdelen op basis van een datum gerepresenteerd door een integer.
     *
     * @param datum Datum als integer, zoals gebruikt wordt in de Java mapping van het BMR.
     */
    public DatumLiteral(final int datum) {
        final int dJaar = datum / JAARFACTOR;
        final int dMaand = (datum - dJaar * JAARFACTOR) / MAANDFACTOR;
        final int dDag = datum - dJaar * JAARFACTOR - dMaand * MAANDFACTOR;
        jaar = Datumdeel.valueOf(dJaar);
        maand = Datumdeel.valueOf(dMaand);
        dag = Datumdeel.valueOf(dDag);
    }

    /**
     * Constructor.
     *
     * @param dt DateTime-waarde voor datum literal.
     */
    public DatumLiteral(final ZonedDateTime dt) {
        final ZonedDateTime inNederland = dt.withZoneSameInstant(DatumUtil.NL_ZONE_ID);
        this.jaar = Datumdeel.valueOf(inNederland.getYear());
        this.maand = Datumdeel.valueOf(inNederland.getMonthValue());
        this.dag = Datumdeel.valueOf(inNederland.getDayOfMonth());
    }

    /**
     * Berekent het aantal dagen dat in een maand zit, gegeven een jaartal en een maand en rekening houdend met
     * schrikkeljaren.
     *
     * @param jaar  Jaartal.
     * @param maand Maandnummer [1..12].
     * @return Aantal dagen in een bepaalde maand.
     */
    public static int dagenInMaand(final int jaar, final int maand) {
        final ZonedDateTime dateTime = ZonedDateTime.of(jaar, maand, 1, 0, 0, 0, 0, DatumUtil.NL_ZONE_ID);
        return (int) dateTime.range(ChronoField.DAY_OF_MONTH).getMaximum();
    }

    /**
     * Geeft jaartal van de datum.
     *
     * @return Jaartal.
     */
    public final Datumdeel getJaar() {
        return jaar;
    }

    /**
     * Geeft maandnummer van de datum.
     *
     * @return Maandnummer.
     */
    public final Datumdeel getMaand() {
        return maand;
    }

    /**
     * Geeft dagnummer van de datum.
     *
     * @return Dagnummer.
     */
    public final Datumdeel getDag() {
        return dag;
    }

    /**
     * Geeft datum als een DateTime-object, mits alle datumdelen bekend zijn; anders NULL.
     *
     * @return Datum als DateTime-object.
     */
    public final ZonedDateTime alsDateTime() {
        ZonedDateTime dateTime = null;
        if (isVolledigBekend()) {
            dateTime = ZonedDateTime.of(jaar.getWaarde(), maand.getWaarde(), dag.getWaarde(), 0, 0, 0, 0, DatumUtil.NL_ZONE_ID);
        }
        return dateTime;
    }

    /**
     * @return indicatie of alle datumdelen bekend zijn
     */
    public final boolean isVolledigBekend2() {
        return jaar.getWaarde() != 0 && maand.getWaarde() != 0 && dag.getWaarde() != 0;
    }

    /**
     * @return indicatie of alle datumdelen bekend zijn
     */
    public final boolean isVolledigBekend() {
        return !jaar.isOnbekend() && !maand.isOnbekend() && !dag.isOnbekend();
    }

    @Override
    public ExpressieType getType(final Context context) {
        return ExpressieType.DATUM;
    }

    /**
     * @return geeft de integer representatie
     */
    public final int alsInteger() {
        return jaar.getWaarde() * JAARFACTOR + maand.getWaarde() * MAANDFACTOR + dag.getWaarde();
    }

    @Override
    public String toString() {
        final String dagStr = dag.isVraagteken()
                ? ONBEKEND_DATUMDEEL_STRING
                : String.format(VARIABELE_TWEE_CIJFERS, dag.getWaarde());
        final String maandStr = maand.isVraagteken()
                ? ONBEKEND_DATUMDEEL_STRING
                : String.format(VARIABELE_TWEE_CIJFERS, maand.getWaarde());
        final String jaarStr = jaar.isVraagteken()
                ? ONBEKEND_DATUMDEEL_STRING
                : String.format("%04d", jaar.getWaarde());
        return String.format("%s%c%s%c%s", jaarStr, '/', maandStr, '/', dagStr);
    }

    @Override
    public int hashCode() {
        return HashCode.fromInt(alsInteger()).hashCode();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final DatumLiteral that = (DatumLiteral) o;

        return new EqualsBuilder()
                .append(jaar, that.jaar)
                .append(maand, that.maand)
                .append(dag, that.dag)
                .isEquals();
    }
}

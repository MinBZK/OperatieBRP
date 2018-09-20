/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.test.utils.bijhouding.datumutil;




/**
 * Attribuut wrapper klasse voor Datum (evt. deels onbekend).
 *
 */
public class DatumAttribuut {

    private Integer waarde;

    /** Aantal seconden per minuut. */
    public static final int AANTAL_SECONDEN_PER_MINUUT = 60;
    /** Aantal minuten per uur. */
    public static final int AANTAL_MINUTEN_PER_UUR = 60;
    /** Aantal uren per dag. */
    public static final int AANTAL_UREN_PER_DAG = 24;

    /** Aantal milliseconden per seconde. */
    public static final int AANTAL_MILLIS_PER_SECONDE = 1000;
    /** Aantal milliseconden per miuut. */
    public static final int AANTAL_MILLIS_PER_MINUUT = AANTAL_MILLIS_PER_SECONDE * AANTAL_SECONDEN_PER_MINUUT;
    /** Aantal milliseconden per uur. */
    public static final int AANTAL_MILLIS_PER_UUR = AANTAL_MILLIS_PER_MINUUT * AANTAL_MINUTEN_PER_UUR;
    /** Aantal milliseconden per dag. */
    public static final int AANTAL_MILLIS_PER_DAG = AANTAL_MILLIS_PER_UUR * AANTAL_UREN_PER_DAG;

    /**
     * Lege private constructor voor DatumAttribuut, om te voorkomen dat wrappers zonder waarde worden geïnstantieerd.
     *
     */
    private DatumAttribuut() {
        super();
    }

    public Integer getWaarde() {
        return waarde;
    }

    /**
     * Constructor voor DatumAttribuut die de waarde toekent.
     *
     * @param waarde De waarde van het attribuut.
     */
    public DatumAttribuut(final Integer waarde) {
        this.waarde = waarde;
    }

    /**
     * Test of deze datum na de opgegeven datum ligt.
     *
     * @param vergelijkingsDatum de datum waarmee vergeleken wordt.
     * @return of deze datum na de opgegeven datum ligt.
     */
    public boolean na(final DatumAttribuut vergelijkingsDatum) {
        if (vergelijkingsDatum == null || vergelijkingsDatum.getWaarde() == null) {
            throw new IllegalArgumentException("Datum moet aanwezig zijn.");
        }
        return getWaarde() > vergelijkingsDatum.getWaarde();
    }

    /**
     * Test of deze datum na of op vergelijkingsDatum ligt.
     * @param vergelijkingsDatum vergelijkingsDatum.
     * @return of deze datum na of op vergelijkingsDatum ligt.
     */
    public boolean naOfOp(final DatumAttribuut vergelijkingsDatum) {
        return na(vergelijkingsDatum) || op(vergelijkingsDatum);
    }

    /**
     * Test of deze datum voor of op vergelijkingsDatum ligt.
     * @param vergelijkingsDatum vergelijkingsDatum.
     * @return of deze datum voor of op vergelijkingsDatum ligt.
     */
    public boolean voorOfOp(final DatumAttribuut vergelijkingsDatum) {
        return voor(vergelijkingsDatum) || op(vergelijkingsDatum);
    }

    /**
     * Test of deze datum voor de opgegeven datum ligt.
     *
     * @param vergelijkingsDatum de datum waarmee vergeleken wordt.
     * @return of deze datum voor de opgegeven datum ligt.
     */
    public boolean voor(final DatumAttribuut vergelijkingsDatum) {
        if (vergelijkingsDatum == null || vergelijkingsDatum.getWaarde() == null) {
            throw new IllegalArgumentException("Datum moet aanwezig zijn.");
        }
        return getWaarde() < vergelijkingsDatum.getWaarde();
    }

    /**
     * Test of deze datum dezelfde datum is als de opgegeven datum.
     *
     * @param vergelijkingsDatum de datum
     * @return true of false
     */
    public boolean op(final DatumAttribuut vergelijkingsDatum) {
        if (vergelijkingsDatum == null || vergelijkingsDatum.getWaarde() == null) {
            throw new IllegalArgumentException("Datum moet aanwezig zijn.");
        }
        return getWaarde().equals(vergelijkingsDatum.getWaarde());
    }

    /**
     * Geeft het aantal dagen verschil aan tussen 2 datums.
     * Dit aantal is altijd een positief (niet-negatief) getal!
     *
     * @param vergelijkingsDatum de datum
     * @return het verschil in dagen
     */
    public int aantalDagenVerschil(final DatumAttribuut vergelijkingsDatum) {
        if (vergelijkingsDatum == null || vergelijkingsDatum.getWaarde() == null) {
            throw new IllegalArgumentException("Datum moet aanwezig zijn.");
        }
        long dezeMillis = DatumUtil.datumToDate(this).getTime();
        long andereMillis = DatumUtil.datumToDate(vergelijkingsDatum).getTime();
        long millisVerschil = Math.abs(dezeMillis - andereMillis);
        // Voor de zekerheid netjes afronden ivm schrikkelseconden en dergelijke grappen.
        return (int) Math.round(((double) millisVerschil) / AANTAL_MILLIS_PER_DAG);
    }

    @Override
    public String toString() {
        if (getWaarde() == null) {
            return null;
        } else {
            return String.format("%04d-%02d-%02d", DatumUtil.getJaar(this), DatumUtil.getMaand(this), DatumUtil.getDag(this));
        }
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.attribuut;

import nl.bzk.migratiebrp.conversie.model.Requirement;
import nl.bzk.migratiebrp.conversie.model.Requirements;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.validatie.Periode;
import org.simpleframework.xml.Element;

/**
 * Deze class representeert het BRP datum type.
 * 
 * Deze class is immutable en threadsafe.
 */
@Requirement(Requirements.CAP002)
public final class BrpDatum extends AbstractBrpAttribuutMetOnderzoek implements Comparable<BrpDatum> {

    /** De onbekende datum (0). */
    public static final BrpDatum ONBEKEND = new BrpDatum(0, null);
    private static final long serialVersionUID = 1L;
    private static final int MINIMAL_LENGTH_WITH_YEAR = 5;
    private static final int START_POSITION_MAAND = 4;
    private static final int EIND_POSITIE_MAAND = 6;
    private static final String INDICATIE_ONBEKEND = "00";
    private static final String DATE_FORMAT = "%08d";
    private static final int DATE_LENGTH = 8;

    /**
     * Maakt een BrpDatum object met onderzoek.
     * 
     * @param waarde
     *            de datum als integer in de vorm van jjjjmmdd.
     * @param onderzoek
     *            het onderzoek waar deze datum onder valt. Mag NULL zijn.
     */
    public BrpDatum(
        @Element(name = "waarde", required = false) final Integer waarde,
        @Element(name = "onderzoek", required = false) final Lo3Onderzoek onderzoek)
    {
        super(waarde, onderzoek);
    }

    /**
     * Wrap de waarde en onderzoek naar een BrpDatum.
     * 
     * @param waarde
     *            de Integer waarde
     * @param onderzoek
     *            het Lo3 onderzoek
     * @return BrpDatum object met daarin waarde en onderzoek
     */
    public static BrpDatum wrap(final Integer waarde, final Lo3Onderzoek onderzoek) {
        if (waarde == null && onderzoek == null) {
            return null;
        }
        return new BrpDatum(waarde, onderzoek);
    }

    /**
     * Unwrap een BrpDatum object om de Integer waarde terug te krijgen.
     * 
     * @param attribuut
     *            De BrpDatum, mag null zijn.
     * @return Een Integer object, of null als de BrpDatum null was.
     */
    public static Integer unwrap(final BrpDatum attribuut) {
        return (Integer) AbstractBrpAttribuutMetOnderzoek.unwrapImpl(attribuut);
    }

    /**
     * Maak een periode adhv BrpDatum object.
     *
     * @param beginDatum
     *            De begin datum. Als deze null is wordt Long.MIN_VALUE ingevuld.
     * @param eindDatum
     *            De eind datum. Als deze null is wordt Long.MAX_VALUE ingevuld.
     * @return de nieuwe Periode
     */
    public static Periode createPeriode(final BrpDatum beginDatum, final BrpDatum eindDatum) {
        return new Periode(beginDatum == null ? null : beginDatum.getWaarde().longValue(), eindDatum == null ? null : eindDatum.getWaarde().longValue());
    }

    /**
     * @return het BRP equivalent van deze LO3 datum
     * @param lo3Datum
     *            de LO3 datum
     */
    public static BrpDatum fromLo3Datum(final Lo3Datum lo3Datum) {
        final Integer datum = lo3Datum.getWaarde() == null ? null : Integer.valueOf(lo3Datum.getWaarde());
        return new BrpDatum(datum, lo3Datum.getOnderzoek());
    }

    /**
     * @return het BRP equivalent van deze LO3 datum zonder onderzoek
     * @param lo3Datum
     *            de LO3 datum
     */
    public static BrpDatum fromLo3DatumZonderOnderzoek(final Lo3Datum lo3Datum) {
        final Integer datum = lo3Datum.getWaarde() == null ? null : Integer.valueOf(lo3Datum.getWaarde());
        return new BrpDatum(datum, null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.conversie.model.brp.BrpAttribuutMetOnderzoek#getWaarde()
     */
    @Override
    @Element(name = "waarde", required = false)
    public Integer getWaarde() {
        return unwrap(this);
    }

    @Override
    public int compareTo(final BrpDatum andereDatum) {
        if (!Validatie.isAttribuutGevuld(andereDatum)) {
            throw new NullPointerException("Andere datum is null");
        }

        return getWaarde() - andereDatum.getWaarde();
    }

    /**
     * Converteer naar lo3 datum.
     * 
     * @return lo3 datum
     */
    public Lo3Datum converteerNaarLo3Datum() {
        return converteerNaarLo3Datum(false);
    }

    /**
     * Converteer naar lo3 datum.
     * 
     * @param stripOnderzoek
     *            true als de datum naar Lo3Datum geconverteerd moet worden zonder onderzoek
     * @return lo3 datum
     */
    public Lo3Datum converteerNaarLo3Datum(final boolean stripOnderzoek) {
        final String waarde = getWaarde() == null ? null : String.format(DATE_FORMAT, getWaarde());
        final Lo3Onderzoek onderzoek = stripOnderzoek ? null : getOnderzoek();

        return new Lo3Datum(waarde, onderzoek);
    }

    /**
     * Is een gedeelte van deze datum onbekend? Een gedeelte (jaar, maand of dag) van een datum is onbekend als er 0000
     * (jaar) of 00 (maand of dag) als waarde is ingevuld.
     * 
     * Bijvoorbeeld:
     * <ul>
     * <li>19000101 -> false</li>
     * <li>19000100 -> true</li>
     * <li>19000001 -> true</li>
     * <li>00000101 -> true</li>
     * <li>00000100 -> true</li>
     * <li>00000001 -> true</li>
     * <li>19000000 -> true</li>
     * <li>00000000 -> true</li>
     * </ul>
     *
     * @return true als een gedeelte van de datum onbekend is, anders false
     */
    public boolean isOnbekend() {
        if (getWaarde() == null) {
            throw new NullPointerException("Waarde is niet gevuld");
        }
        String waarde = getWaarde().toString();
        if (waarde.length() >= MINIMAL_LENGTH_WITH_YEAR && waarde.length() < DATE_LENGTH) {
            waarde = String.format(DATE_FORMAT, getWaarde());
        }
        return waarde.length() < MINIMAL_LENGTH_WITH_YEAR
               || waarde.substring(START_POSITION_MAAND, EIND_POSITIE_MAAND).equals(INDICATIE_ONBEKEND)
               || waarde.endsWith(INDICATIE_ONBEKEND);

    }
}

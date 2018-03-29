/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.element;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.migratiebrp.conversie.model.Requirement;
import nl.bzk.migratiebrp.conversie.model.Requirements;
import nl.bzk.migratiebrp.conversie.model.validatie.Periode;

/**
 * Deze class representeert de LO3 waarde datum (jjjjmmdd); er wordt geen inhoudelijke controle uitgevoerd of een datum
 * daadwerkelijk geldig is. LET OP: voordat er een berekening gedaan kan worden op/met deze class moet de methode
 * isInhoudelijkGevuld bevraagd worden.
 *
 * De class is immutable en threadsafe.
 */
@Requirement(Requirements.CAP002)
public final class Lo3Datum extends AbstractLo3Element implements Comparable<Lo3Datum> {

    private static final int NIEUW_JAAR_VOOR_NIEUWE_DATUM = 99_990_000;
    private static final int NIEUWE_MAAND_VOOR_NIEUWE_DATUM = 9900;
    private static final int NIEUWE_DAG_VOOR_NIEUWE_DATUM = 99;
    private static final int HONDERD = 100;
    private static final int TIEN_DUIZEND = 10_000;
    private static final long serialVersionUID = 1L;

    /**
     * Maakt een Lo3Datum object; er wordt geen inhoudelijke controle uitgevoerd of een datum daadwerkelijk geldig is.
     * @param waarde de datum als integer in de vorm van jjjjmmdd.
     */
    public Lo3Datum(final Integer waarde) {
        this(String.format("%08d", waarde), null);
    }

    /**
     * Maakt een Lo3Datum object met onderzoek; er wordt geen inhoudelijke controle uitgevoerd of een datum
     * daadwerkelijk geldig is.
     * @param waarde de datum als String in de vorm van jjjjmmdd.
     * @param onderzoek het onderzoek waar deze datum onder valt. Mag NULL zijn.
     */
    public Lo3Datum(
            @Element(name = "waarde") final String waarde,
            @Element(name = "onderzoek") final Lo3Onderzoek onderzoek) {
        super(waarde, onderzoek);
    }

    /**
     * Maak een periode adhv Lo3Datum object.
     * @param beginDatum De begin datum. Als deze null is wordt Long.MIN_VALUE ingevuld.
     * @param eindDatum De eind datum. Als deze null is wordt Long.MAX_VALUE ingevuld.
     * @return de nieuwe Periode
     */
    static Periode createPeriode(final Lo3Datum beginDatum, final Lo3Datum eindDatum) {
        return new Periode(
                !Lo3Validatie.isElementGevuld(beginDatum) ? null : beginDatum.getLongWaarde(),
                !Lo3Validatie.isElementGevuld(eindDatum) ? null : eindDatum.getLongWaarde());
    }

    /**
     * Is een gedeelte van deze datum onbekend? Een gedeelte (jaar, maand of dag) van een datum is onbekend als er 0000
     * (jaar) of 00 (maand of dag) als waarde is ingevuld.
     *
     * Bijvoorbeeld: {@literal
     *
     * <ul>
     *
     * <li>19000101 -> false</li>
     *
     * <li>19000100 -> true</li>
     *
     * <li>19000001 -> true</li>
     *
     * <li>00000101 -> true</li>
     *
     * <li>00000100 -> true</li>
     *
     * <li>00000001 -> true</li>
     *
     * <li>19000000 -> true</li>
     *
     * <li>00000000 -> true</li>
     *
     * </ul>
     * }
     * @return true als een gedeelte van de datum onbekend is, anders false
     */
    public boolean isOnbekend() {
        final int datum = convertWaardeNaarDatum(getWaarde());
        /* TIEN_DUIZEND(10000) en HONDERD(100) zijn hier selectors voor jaar, maand en dag */
        return datum == 0 || datum / TIEN_DUIZEND == 0 || datum % TIEN_DUIZEND / HONDERD == 0 || datum % HONDERD == 0;
    }

    private int convertWaardeNaarDatum(final String waarde) {
        if (waarde == null) {
            throw new NullPointerException("Waarde is niet gevuld");
        }
        return Integer.parseInt(waarde);
    }

    /**
     * Als de datum (gedeeltelijk) onbekend is, maak een nieuwe gemaximaliseerde datum, waarbij alle onbekende gedeelten
     * zijn vervangen door 99.
     * @return De gemaximaleseerde datum, of de oorspronkelijke Lo3Datum als die niet (gedeeltelijk) onbekend is.
     * @throws java.lang.NullPointerException als de inhoudelijk waarde niet gevuld is
     */
    public int maximaliseerOnbekendeDatum() {
        if (isOnbekend()) {
            int nieuweDatum = convertWaardeNaarDatum(getWaarde());

            /* TIEN_DUIZEND(10000) en HONDERD(100) zijn hier selectors voor jaar, maand en dag */
            if (nieuweDatum % HONDERD == 0) {
                nieuweDatum += NIEUWE_DAG_VOOR_NIEUWE_DATUM;
            }

            if (nieuweDatum % TIEN_DUIZEND / HONDERD == 0) {
                nieuweDatum += NIEUWE_MAAND_VOOR_NIEUWE_DATUM;
            }

            if (nieuweDatum / TIEN_DUIZEND == 0) {
                nieuweDatum += NIEUW_JAAR_VOOR_NIEUWE_DATUM;
            }

            return nieuweDatum;
        } else {
            return convertWaardeNaarDatum(getWaarde());
        }
    }

    @Override
    public int compareTo(final Lo3Datum andereDatum) {
        if (!Lo3Validatie.isElementGevuld(andereDatum)) {
            throw new NullPointerException("Andere datum is null");
        }
        final Integer eigenDatumWaarde = convertWaardeNaarDatum(getWaarde());
        final Integer andereDatumWaarde = convertWaardeNaarDatum(andereDatum.getWaarde());
        return eigenDatumWaarde - andereDatumWaarde;
    }

    private long getLongWaarde() {
        return super.getWaarde() == null ? null : Long.valueOf(super.getWaarde());
    }

    /**
     * Geef de waarde van integer waarde.
     * @return integer waarde
     */
    public Integer getIntegerWaarde() {
        return super.getWaarde() == null ? null : Integer.valueOf(super.getWaarde());
    }
}

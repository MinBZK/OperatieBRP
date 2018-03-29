/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.element;

import java.io.Serializable;
import nl.bzk.algemeenbrp.util.xml.annotation.Element;

/**
 * Deze class representeert de LO3 waarde datum (jjjjmmddhhmmssμμμ). Er wordt geen controle uitgevoerd op de invoer.
 * LETOP: Eerst de methode isInhoudelijkGevuld aanroepen, anders kans op een NumberFormatException. De class is
 * immutable en threadsafe.
 */
public final class Lo3Datumtijdstempel extends AbstractLo3Element implements Comparable<Lo3Datumtijdstempel>, Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Maakt een Lo3Datum object .
     * @param waarde de datum als long in de vorm van jjjjmmddhhmmssuuu.
     */
    public Lo3Datumtijdstempel(final Long waarde) {
        this(String.valueOf(waarde), null);
    }

    /**
     * Maakt een Lo3Datumtijdstempel object met onderzoek.
     * @param waarde de datum als string in de vorm van jjjjmmddhhmmssuuu.
     * @param onderzoek het onderzoek waar deze code onder valt. Mag NULL zijn.
     */
    public Lo3Datumtijdstempel(
            @Element(name = "waarde", required = false) final String waarde,
            @Element(name = "onderzoek", required = false) final Lo3Onderzoek onderzoek) {
        super(waarde, onderzoek);
    }

    @Override
    public int compareTo(final Lo3Datumtijdstempel andereDatum) {
        if (!Lo3Validatie.isElementGevuld(andereDatum)) {
            throw new NullPointerException("Andere datum is null");
        }
        final long eigenDatumWaarde = convertWaardeNaarDatum(getWaarde());
        final long andereDatumWaarde = convertWaardeNaarDatum(andereDatum.getWaarde());
        final long result = eigenDatumWaarde - andereDatumWaarde;

        return result > 0 ? 1 : result < 0 ? -1 : 0;
    }

    private long convertWaardeNaarDatum(final String waarde) {
        if (waarde == null) {
            throw new NullPointerException("Waarde is niet gevuld");
        }
        return Long.parseLong(waarde);
    }

    /**
     * Geef de waarde van long waarde.
     * @return long waarde
     */
    public Long getLongWaarde() {
        return super.getWaarde() == null ? null : Long.valueOf(super.getWaarde());
    }
}

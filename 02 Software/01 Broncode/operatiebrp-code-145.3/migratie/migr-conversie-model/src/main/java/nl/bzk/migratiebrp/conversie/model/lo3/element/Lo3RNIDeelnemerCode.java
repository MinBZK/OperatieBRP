/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.element;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;

/**
 * Deze class representeert de LO3 RNI Deelnemer code. Deze identificeert een entry in de LO3 RNI-deelnemer tabel (Tabel
 * 60).
 *
 * Deze class is immutable en threadsafe.
 */
public final class Lo3RNIDeelnemerCode extends AbstractLo3Element {

    /**
     * De rni deelnemer code voor 'Standaard'.
     */
    public static final String CODE_STANDAARD = "0000";

    /**
     * Standaard.
     */
    public static final Lo3RNIDeelnemerCode STANDAARD = new Lo3RNIDeelnemerCode(CODE_STANDAARD);

    private static final long serialVersionUID = 1L;

    /**
     * Maakt een Lo3RNIDeelnemerCode object.
     * @param waarde de LO3 code die een rni deelnemer in LO3 uniek identificeert, mag niet null zijn
     * @throws NullPointerException als code null is
     */
    public Lo3RNIDeelnemerCode(final String waarde) {
        this(waarde, null);
    }

    /**
     * Maakt een Lo3RNIDeelnemerCode object met onderzoek.
     * @param waarde de LO3 code die een rni deelnemer in LO3 uniek identificeert, mag niet null zijn
     * @param onderzoek het onderzoek waar deze code onder valt. Mag NULL zijn.
     */
    public Lo3RNIDeelnemerCode(@Element(name = "waarde", required = false) final String waarde, @Element(name = "onderzoek",
            required = false) final Lo3Onderzoek onderzoek) {
        super(waarde, onderzoek);
    }
}

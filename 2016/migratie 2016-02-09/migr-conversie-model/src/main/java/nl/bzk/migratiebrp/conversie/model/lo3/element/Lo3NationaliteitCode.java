/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.element;

import org.simpleframework.xml.Element;

/**
 * Deze class representeert de LO3 nationaliteit code. Deze code verwijst binnen LO3 naar een nationaliteit in Tabel 32
 * (zie LO3.7).
 * 
 * Deze class is immutable en threadsafe.
 * 
 * 
 * 
 */
public final class Lo3NationaliteitCode extends AbstractLo3Element {

    /** Nationaliteit code voor de Nederlandse nationaliteit. */
    public static final Lo3NationaliteitCode NATIONALITEIT_CODE_NEDERLANDSE = new Lo3NationaliteitCode("0001");
    /**
     * Nationaliteit code voor de indicatie Staatloos (was Statenloos).
     */
    public static final String NATIONALITEIT_CODE_STAATLOOS = "0499";

    private static final long serialVersionUID = 1L;

    /**
     * Maakt een Lo3NationaliteitCode object.
     * 
     * @param waarde
     *            de LO3 code
     */
    public Lo3NationaliteitCode(final String waarde) {
        this(waarde, null);
    }

    /**
     * Maakt een Lo3NationaliteitCode object met onderzoek.
     * 
     * @param waarde
     *            de LO3 code
     * @param onderzoek
     *            het onderzoek waar deze code onder valt. Mag NULL zijn.
     */
    public Lo3NationaliteitCode(@Element(name = "waarde", required = false) final String waarde, @Element(name = "onderzoek",
            required = false) final Lo3Onderzoek onderzoek)
    {
        super(waarde, onderzoek);
    }

    /**
     * Geeft de nationaliteit code terug zonder onderzoek.
     * 
     * @param nationaliteitCode
     *            de nationaliteitcode waarbij het onderzoek verwijderd wordt.
     * @return nationaliteitcode zonder onderzoek
     */
    public static Lo3NationaliteitCode zonderOnderzoek(final Lo3NationaliteitCode nationaliteitCode) {
        if (nationaliteitCode == null) {
            return null;
        }

        return new Lo3NationaliteitCode(nationaliteitCode.getWaarde(), null);
    }

}

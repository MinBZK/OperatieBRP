/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.element;

import nl.bzk.migratiebrp.conversie.model.Definitie;
import nl.bzk.migratiebrp.conversie.model.Definities;
import org.simpleframework.xml.Element;

/**
 * Deze class representeert de LO3 land code. Deze identificeert een entry in de LO3 landen tabel (Tabel 34).
 * 
 * Deze class is immutable en threadsafe.
 * 
 */
public final class Lo3LandCode extends AbstractLo3Element {

    /** De land code voor 'Onbekend'. */
    public static final String CODE_ONBEKEND = "9999";
    /** De land code voor 'Standaard'. */
    public static final String CODE_STANDAARD = "0000";
    /** De land code voor 'Nederland'. */
    public static final String CODE_NEDERLAND = "6030";

    /** Nederland. */
    public static final Lo3LandCode NEDERLAND = new Lo3LandCode(CODE_NEDERLAND);
    /** Standaard. */
    public static final Lo3LandCode STANDAARD = new Lo3LandCode(CODE_STANDAARD);

    private static final long serialVersionUID = 1L;

    /**
     * Maakt een Lo3LandCode object.
     * 
     * @param waarde
     *            de LO3 code die een land in LO3 uniek identificeert
     */
    public Lo3LandCode(final String waarde) {
        this(waarde, null);
    }

    /**
     * Maakt een Lo3LandCode object met onderzoek.
     * 
     * @param waarde
     *            de LO3 code die een land in LO3 uniek identificeert
     * @param onderzoek
     *            het onderzoek waar deze landcode onder valt. Mag NULL zijn.
     */
    public Lo3LandCode(
        @Element(name = "waarde", required = false) final String waarde,
        @Element(name = "onderzoek", required = false) final Lo3Onderzoek onderzoek)
    {
        super(waarde, onderzoek);
    }

    /**
     * Geef de nederland code.
     *
     * @return true als deze land code gelijk is aan de land code voor Nederland, anders false
     */
    @Definitie(Definities.DEF001)
    public boolean isNederlandCode() {
        return CODE_NEDERLAND.equals(getWaarde());
    }

    /**
     * Geef de land code buitenland.
     *
     * @return true als code <> 0000 en code <> 9999 en code <> 6030, anders false
     */
    @Definitie(Definities.DEF002)
    public boolean isLandCodeBuitenland() {
        return !(CODE_NEDERLAND.equals(getWaarde()) || CODE_STANDAARD.equals(getWaarde()) || CODE_ONBEKEND.equals(getWaarde()));
    }

    /**
     * Geef de onbekend.
     *
     * @return true als code gelijk is aan 0000 of 9999
     */
    @Definitie(Definities.DEF003)
    public boolean isOnbekend() {
        return CODE_STANDAARD.equals(getWaarde()) || CODE_ONBEKEND.equals(getWaarde());
    }
}

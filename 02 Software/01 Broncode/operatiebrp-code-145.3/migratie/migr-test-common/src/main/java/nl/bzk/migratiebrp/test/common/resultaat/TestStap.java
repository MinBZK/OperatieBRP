/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.common.resultaat;

import nl.bzk.algemeenbrp.util.xml.annotation.Attribute;
import nl.bzk.algemeenbrp.util.xml.annotation.Element;

/**
 * Test stap resultaat.
 */
public final class TestStap {

    private final TestStatus status;
    private final String omschrijving;
    private final String bestand;
    private final String verschillenBestand;

    /**
     * Constructor.
     * @param status status
     */
    public TestStap(final TestStatus status) {
        this(status, null, null, null);
    }

    /**
     * Constructor.
     * @param status status
     * @param omschrijving omschrijving
     * @param bestand filename
     * @param verschillenBestand filename2
     */
    public TestStap(
            @Attribute(name = "status", required = false) final TestStatus status,
            @Attribute(name = "omschrijving", required = false) final String omschrijving,
            @Element(name = "bestand", required = false) final String bestand,
            @Element(name = "verschillenBestand", required = false) final String verschillenBestand) {
        super();
        this.status = status;
        this.omschrijving = omschrijving;
        this.bestand = bestand;
        this.verschillenBestand = verschillenBestand;

    }

    /**
     * Geef de waarde van status.
     * @return status
     */
    @Attribute(name = "status", required = false)
    public TestStatus getStatus() {
        return status;
    }

    /**
     * Geef de waarde van omschrijving.
     * @return omschrijving
     */
    @Attribute(name = "omschrijving", required = false)
    public String getOmschrijving() {
        return omschrijving;
    }

    /**
     * Geef de waarde van bestand.
     * @return bestand
     */
    @Element(name = "bestand", required = false)
    public String getBestand() {
        return bestand;
    }

    /**
     * Geef de waarde van verschillen bestand.
     * @return verschillen bestand
     */
    @Element(name = "verschillenBestand", required = false)
    public String getVerschillenBestand() {
        return verschillenBestand;
    }

}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.element;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;

/**
 * Indicatie document.
 */
public final class Lo3IndicatieDocument extends AbstractLo3Element {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     * @param waarde code
     */
    public Lo3IndicatieDocument(final Integer waarde) {
        this(String.valueOf(waarde), null);
    }

    /**
     * Constructor met onderzoek.
     * @param waarde code
     * @param onderzoek het onderzoek waar deze code onder valt. Mag NULL zijn.
     */
    public Lo3IndicatieDocument(@Element(name = "waarde", required = false) final String waarde, @Element(name = "onderzoek",
            required = false) final Lo3Onderzoek onderzoek) {
        super(waarde, onderzoek);
    }

    /**
     * Geef de waarde van integer waarde.
     * @return integer waarde
     */
    public Integer getIntegerWaarde() {
        return super.getWaarde() == null ? null : Integer.valueOf(super.getWaarde());
    }
}

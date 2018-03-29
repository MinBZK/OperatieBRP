/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.element;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;

/**
 * 15.10 Soort verbintenis gebruikt in element 15.10.
 */
public final class Lo3SoortVerbintenis extends AbstractLo3Element {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     * @param waarde code
     */
    public Lo3SoortVerbintenis(final String waarde) {
        this(waarde, null);
    }

    /**
     * Constructor met onderzoek.
     * @param waarde code
     * @param onderzoek het onderzoek waar deze code onder valt. Mag NULL zijn.
     */
    public Lo3SoortVerbintenis(
            @Element(name = "waarde", required = false) final String waarde,
            @Element(name = "onderzoek", required = false) final Lo3Onderzoek onderzoek) {
        super(waarde, onderzoek);
    }

    /**
     * Bepaalt of de {@link Lo3SoortVerbintenis} eem geldige waarde bevat.
     * @return true als waarde leeg, "H" of een "P" is.
     */
    public boolean bevatGeldigeWaarde() {
        return getWaarde().isEmpty() || "H".equals(getWaarde()) || "P".equals(getWaarde());
    }
}

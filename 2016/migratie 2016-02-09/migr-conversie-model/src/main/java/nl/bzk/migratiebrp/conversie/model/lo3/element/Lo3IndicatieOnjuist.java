/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.element;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.simpleframework.xml.Element;

/**
 * Indicatie onjuist.
 * 
 */
public final class Lo3IndicatieOnjuist extends AbstractLo3Element implements Comparable<Lo3IndicatieOnjuist> {

    /**
     * Indicatie onjuist.
     */
    public static final Lo3IndicatieOnjuist O = new Lo3IndicatieOnjuist("O");

    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     * 
     * @param waarde
     *            code
     */
    public Lo3IndicatieOnjuist(final String waarde) {
        this(waarde, null);
    }

    /**
     * Constructor met onderzoek.
     * 
     * @param waarde
     *            code
     * @param onderzoek
     *            het onderzoek waar deze code onder valt. Mag NULL zijn.
     */
    public Lo3IndicatieOnjuist(@Element(name = "waarde", required = false) final String waarde, @Element(name = "onderzoek",
            required = false) final Lo3Onderzoek onderzoek)
    {
        super(waarde, onderzoek);
    }

    /**
     * Geef de waarde van code as character.
     *
     * @return de code als character
     */
    public Character getCodeAsCharacter() {
        return getWaarde() == null ? null : getWaarde().charAt(0);
    }

    @Override
    public int compareTo(final Lo3IndicatieOnjuist other) {
        if (other == null) {
            throw new NullPointerException("other is null");
        }
        return new CompareToBuilder().append(getWaarde(), other.getWaarde()).toComparison();
    }
}

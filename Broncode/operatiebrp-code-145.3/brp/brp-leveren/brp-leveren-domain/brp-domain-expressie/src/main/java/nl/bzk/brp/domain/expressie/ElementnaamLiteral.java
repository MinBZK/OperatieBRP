/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie;

/**
 * Representeert attribuutcodes, zoals '[geboorte.datum]', in expressies.
 */
public final class ElementnaamLiteral implements Literal {

    private String elementnaam;

    /**
     * Constructor.
     *
     * @param elementnaam Code van het attribuut.
     */
    public ElementnaamLiteral(final String elementnaam) {
        this.elementnaam = elementnaam;
    }

    @Override
    public ExpressieType getType(final Context context) {
        return ExpressieType.ELEMENT;
    }

    @Override
    public String alsString() {
        return elementnaam;
    }

    @Override
    public String toString() {
        return '[' + alsString() + ']';
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.element;

/**
 * Deze class levert helper functionaliteit voor het doen van validaties op LO3 gegevens.
 */
public final class Lo3Validatie {
    /**
     * Explicit private constructor.
     */
    private Lo3Validatie() {
        throw new AssertionError("Er mag geen instantie van Lo3Validatie gemaakt worden.");
    }

    /**
     * Controleert de lijst met parameters op null waarden. Als één van de parameters NIET null is wordt true
     * geretourneerd, anders false.
     * @param parameters de lijst met te controleren parameters
     * @return true als één van de parameters NIET null is, anders false
     */
    public static boolean isEenParameterNietNull(final Object... parameters) {
        boolean result = false;
        for (final Object parameter : parameters) {
            if (parameter != null) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Controleert of het opgegeven BRP element een waarde bevat.
     * @param element LO3 element dat getest wordt
     * @return true als element niet null is en een waarde bevat.
     */
    public static boolean isElementGevuld(final Lo3Element element) {
        return element != null && element.isInhoudelijkGevuld();
    }

    /**
     * Controleert de lijst met parameters op null waarden. Als één van de parameters NIET null is wordt true
     * geretourneerd, anders false.
     * @param parameters de lijst met te controleren parameters
     * @return true als één van de parameters NIET null is, anders false
     */
    public static boolean isEenParameterGevuld(final Lo3Element... parameters) {
        boolean result = false;
        for (final Lo3Element parameter : parameters) {
            if (parameter != null && isElementGevuld(parameter)) {
                result = true;
                break;
            }
        }
        return result;
    }
}

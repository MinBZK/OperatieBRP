/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.attribuut;

/**
 * Deze class levert helper functionaliteit voor het doen van validaties.
 *
 */
public final class Validatie {
    private static final int MAX_DATUM_WAARDE = 99999999;
    private static final long MAX_DATUM_TIJD_WAARDE = 99999999999999L;

    /**
     * Explicit private constructor.
     */
    private Validatie() {
        throw new AssertionError("Er mag geen instantie van Validatie gemaakt worden.");
    }

    /**
     * Controleert of het opgegeven BRP Attribuut een waarde bevat.
     * 
     * @param attribuut
     *            BRP attribuut dat getest wordt
     * @return true als attribuut niet null is en een waarde bevat.
     */
    public static boolean isAttribuutGevuld(final BrpAttribuutMetOnderzoek attribuut) {
        return attribuut != null && attribuut.isInhoudelijkGevuld();
    }

    /**
     * Controleert of de invoer een geldige datum is. Omdat gedeeltelijke datum zijn toegestaan in LO3 en BRP en deze
     * worden weergegeven als 0 zijn de volgende waaarde mogelijk:
     * <p>
     * <table border="1">
     * <tr>
     * <th>YYYYMMDD</th>
     * <th>Integer waarde</th>
     * </tr>
     * <tr>
     * <td>00000000</td>
     * <td>0</td>
     * </tr>
     * <tr>
     * <td>19810000</td>
     * <td>19810000</td>
     * </tr>
     * <tr>
     * <td>19811200</td>
     * <td>19811200</td>
     * </tr>
     * <tr>
     * <td>19811201</td>
     * <td>19811201</td>
     * </tr>
     * <tr>
     * <td>00000001</td>
     * <td>1</td>
     * </tr>
     * <tr>
     * <td>00000100</td>
     * <td>100</td>
     * </tr>
     * </table>
     * </p>
     * Momenteel worden alleen de volgende condities gecontroleerd:
     * <ul>
     * <li>datum &gt;= 0</li>
     * <li>datum &lt;= 99999999</li>
     * </ul>
     * 
     * @param datum
     *            de datum invoer die gecontroleerd moet worden
     * @return true als de datum in het format YYYYMMDD is anders, false
     */
    public static boolean isGeldigDatumFormaatYYYYMMDD(final Integer datum) {
        return datum != null && datum >= 0 && datum <= MAX_DATUM_WAARDE;
    }

    /**
     * Controleert of de invoer een geldige datum/tijd is.
     * <p>
     * Momenteel worden alleen de volgende condities gecontroleerd:
     * <ul>
     * <li>datum &gt;= 0</li>
     * <li>datum &lt;= 99999999999999</li>
     * </ul>
     * 
     * @param datumTijd
     *            de datum invoer die gecontroleerd moet worden
     * @return true als de datum in het format YYYYMMDDHHMMSS is anders, false
     */
    public static boolean isGeldigDatumFormaatYYYYMMDDHHMMSS(final long datumTijd) {
        return datumTijd >= 0 && datumTijd <= MAX_DATUM_TIJD_WAARDE;
    }

    /**
     * Controleert de lijst met parameters en gooit een illegalArgument exception als één van de parameters leeg is.
     * 
     * @param message
     *            een string conform de String.format definitie die wordt aangeroepen met de 'message' en 'parameters'
     *            parmaters.
     * @param parameters
     *            de lijst met string parameters die op lege waarden wordt gecontroleerd.
     * @see String#format(String, Object...)
     */
    public static void controleerOpLegeWaarden(final String message, final String... parameters) {
        for (final String parameter : parameters) {
            if (parameter != null && parameter.length() == 0) {
                throw new IllegalArgumentException(String.format(message, (Object) parameters));
            }
        }
    }

    /**
     * Controleert de lijst met parameters op null waarden. Als één van de parameters NIET null is wordt true
     * geretourneerd, anders false.
     *
     * @param parameters
     *            de lijst met te controleren parameters
     * @return true als één van de parameters NIET null is, anders false
     */
    public static boolean isEenParameterGevuld(final BrpAttribuutMetOnderzoek... parameters) {
        boolean result = false;
        for (final BrpAttribuutMetOnderzoek parameter : parameters) {
            if (parameter != null && isAttribuutGevuld(parameter)) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Controleert de lijst met parameters en gooit een nullpointer exception als één van de parameters null is.
     *
     * @param message
     *            een string conform de String.format definitie die wordt aangeroepen met de 'message' en 'parameters'
     *            parmaters.
     * @param parameters
     *            de lijst met parameters die op null waarden wordt gecontroleerd.
     * @see String#format(String, Object...)
     */
    public static void controleerOpNullWaarden(final String message, final BrpAttribuutMetOnderzoek... parameters) {
        if (!isEenParameterGevuld(parameters)) {
            throw new NullPointerException(String.format(message, (Object) parameters));
        }
    }
}

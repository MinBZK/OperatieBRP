/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.util;

/**
 * Util class met daarin validatie methoden..
 */
public final class ValidationUtils {
    /**
     * Explicit private constructor.
     */
    private ValidationUtils() {
        throw new AssertionError("Er mag geen instantie van ValidationUtils gemaakt worden.");
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
    public static void controleerOpNullWaarden(final String message, final Object... parameters) {
        if (!ValidationUtils.isEenParameterGevuld(parameters)) {
            throw new NullPointerException(String.format(message, parameters));
        }
    }

    private static boolean isEenParameterGevuld(final Object... parameters) {
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
     * Controleert de lijst met parameters en gooit een {@link IllegalArgumentException} als één van de parameters leeg
     * is.
     *
     * @param message
     *            een string conform de String.format definitie die wordt aangeroepen met de 'message' en 'parameters'
     *            parameters.
     * @param parameters
     *            de lijst met string parameters die op lege waarden wordt gecontroleerd.
     * @throws IllegalArgumentException
     *             als 1 van de parameters een leeg is
     * @see String#format(String, Object...)
     */
    public static void controleerOpLegeWaarden(final String message, final String... parameters) {
        for (final String parameter : parameters) {
            if (parameter != null && parameter.length() == 0) {
                throw new IllegalArgumentException(message);
            }
        }
    }

    /**
     * Controleert de parameter en gooit een {@link IllegalArgumentException} als lengte niet goed is.
     *
     * @param message
     *            een string conform de String.format definitie die wordt aangeroepen met de 'message' en 'parameters'
     *            parameters.
     * @param parameter
     *            de string parameter die op lengte wordt gecontroleerd.
     *            @param verwachteLengte de verwachte lengte van de gegeven parameter
     * @throws IllegalArgumentException
     *             als de parameter lengte niet overeenkomt met de verwachte lengte
     * @see String#format(String, Object...)
     */
    public static void controleerOpLengte(final String message, final String parameter, final int verwachteLengte) {
        if (parameter == null || parameter.length() != verwachteLengte) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Controleert of de lijst van meegegeven parameters allemaal null zijn. Als er een parameter is die niet null is,
     * dan zal deze methode false terug geven.
     * 
     * @param parameters
     *            de lijst van parameters die gecontroleerd moet worden
     * @return true als alle parameters null zijn, anders false.
     */
    public static boolean zijnParametersAllemaalNull(final Object... parameters) {
        for (final Object parameter : parameters) {
            if (parameter != null) {
                return false;
            }
        }
        return true;
    }
}

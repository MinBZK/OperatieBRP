/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie;

/**
 * Representeert NULL-waarden. Dit zijn waarden van expressies die niet bekend zijn (bijvoorbeeld een attribuut dat
 * ontbreekt).
 */
public final class NullLiteral implements Literal {

    /**
     * Geeft de representatie van de null-waarde voor expressies (singleton).
     */
    public static final NullLiteral INSTANCE = new NullLiteral();

    /**
     * Private constructor voor singleton NullExpressie.
     */
    private NullLiteral() {
    }

    @Override
    public ExpressieType getType(final Context context) {
        return ExpressieType.NULL;
    }

    @Override
    public String toString() {
        return "NULL";
    }


    /**
     * Geeft een Expressie-object terug, ook als het argument null is. Als het argument null (pointer) is, is het resultaat een NULL-expressie
     * (Expressie).
     *
     * @param expressie Expressie of null.
     * @return Expressie-object.
     */
    public static Expressie veiligeExpressie(final Expressie expressie) {
        final Expressie veilig;
        if (expressie == null) {
            veilig = NullLiteral.INSTANCE;
        } else {
            veilig = expressie;
        }
        return veilig;
    }
}

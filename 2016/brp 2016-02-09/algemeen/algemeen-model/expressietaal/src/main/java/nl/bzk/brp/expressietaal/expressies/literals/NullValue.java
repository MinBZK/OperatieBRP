/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.expressies.literals;

import nl.bzk.brp.expressietaal.Context;
import nl.bzk.brp.expressietaal.DefaultKeywordMapping;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.ExpressieType;
import nl.bzk.brp.expressietaal.Keyword;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;

/**
 * Representeert NULL-waarden. Dit zijn waarden van expressies die niet bekend zijn (bijvoorbeeld een attribuut dat
 * ontbreekt).
 */
public final class NullValue extends AbstractNietNumeriekRepresenteerbareLiteralExpressie {

    private static final Expressie INSTANCE = new NullValue();

    /**
     * Private constructor voor singleton NullValue.
     */
    private NullValue() {
        super();
    }

    /**
     * Geeft de representatie van de null-waarde voor expressies (singleton).
     *
     * @return Null-waarde.
     */
    public static Expressie getInstance() {
        return INSTANCE;
    }

    @Override
    public ExpressieType getType(final Context context) {
        return ExpressieType.NULL;
    }

    @Override
    public boolean alsBoolean() {
        // Default boolean-waarde voor een expressie is FALSE.
        return false;
    }

    @Override
    public String alsString() {
        return stringRepresentatie();
    }

    @Override
    public Attribuut getAttribuut() {
        return null;
    }

    @Override
    public Groep getGroep() {
        return null;
    }

    @Override
    public String stringRepresentatie() {
        return DefaultKeywordMapping.getSyntax(Keyword.NULL);
    }
}

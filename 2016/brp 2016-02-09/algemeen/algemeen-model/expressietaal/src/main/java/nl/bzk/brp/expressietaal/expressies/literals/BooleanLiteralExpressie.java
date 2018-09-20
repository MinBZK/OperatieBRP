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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;

/**
 * Representeert een boolean constante.
 */
public final class BooleanLiteralExpressie extends AbstractNietNumeriekRepresenteerbareLiteralExpressie {

    /**
     * Constante waarde WAAR (true).
     */
    public static final BooleanLiteralExpressie WAAR   = new BooleanLiteralExpressie(true);
    /**
     * Constante waarde ONWAAR (false).
     */
    public static final BooleanLiteralExpressie ONWAAR = new BooleanLiteralExpressie(false);

    /**
     * De waarde van de boolean expressie.
     */
    private final boolean value;

    /**
     * Constructor.
     *
     * @param aValue Waarde van de constante.
     */
    private BooleanLiteralExpressie(final boolean aValue) {
        super();
        this.value = aValue;
    }

    /**
     * Geeft de (constante) boolean expressie die hoort bij de gegeven boolean waarde.
     *
     * @param value Boolean.
     * @return De constante expressie die hoort bij de gegeven boolean waarde.
     */
    public static BooleanLiteralExpressie getExpressie(final boolean value) {
        final BooleanLiteralExpressie booleanWaarde;
        if (value) {
            booleanWaarde = WAAR;
        } else {
            booleanWaarde = ONWAAR;
        }
        return booleanWaarde;
    }

    /**
     * Geeft de (constante) boolean expressie die hoort bij de gegeven boolean waarde.
     *
     * @param object Waarde.
     * @return De constante expressie die hoort bij de gegeven boolean waarde.
     */
    public static BooleanLiteralExpressie getExpressie(final Object object) {
        final BooleanLiteralExpressie result;
        if (object instanceof Boolean) {
            result = getExpressie(((Boolean) object).booleanValue());
        } else if (object instanceof Expressie) {
            result = getExpressie(((Expressie) object).alsBoolean());
        } else if (object instanceof Ja) {
            result = WAAR;
        } else {
            result = ONWAAR;
        }
        return result;
    }

    @Override
    public ExpressieType getType(final Context context) {
        return ExpressieType.BOOLEAN;
    }

    @Override
    public boolean alsBoolean() {
        return value;
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
        final String string;
        if (value) {
            string = DefaultKeywordMapping.getSyntax(Keyword.TRUE);
        } else {
            string = DefaultKeywordMapping.getSyntax(Keyword.FALSE);
        }
        return string;
    }
}

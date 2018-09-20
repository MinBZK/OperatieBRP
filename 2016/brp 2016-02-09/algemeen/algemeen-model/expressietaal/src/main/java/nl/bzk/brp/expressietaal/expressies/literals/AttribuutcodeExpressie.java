/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.expressies.literals;

import nl.bzk.brp.expressietaal.Context;
import nl.bzk.brp.expressietaal.ExpressieType;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;

/**
 * Representeert attribuutcodes, zoals '[geboorte.datum]', in expressies.
 */
public final class AttribuutcodeExpressie extends AbstractNietNumeriekRepresenteerbareLiteralExpressie {

    private String attribuutcode;

    /**
     * Constructor.
     *
     * @param attribuutcode Code van het attribuut.
     */
    public AttribuutcodeExpressie(final String attribuutcode) {
        super();
        this.attribuutcode = attribuutcode;
    }

    @Override
    public ExpressieType getType(final Context context) {
        return ExpressieType.ATTRIBUUTCODE;
    }

    @Override
    public boolean alsBoolean() {
        return false;
    }

    @Override
    public String alsString() {
        return attribuutcode;
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
    protected String stringRepresentatie() {
        return '[' + alsString() + ']';
    }
}

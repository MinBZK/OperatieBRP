/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.expressies.literals;

import nl.bzk.brp.expressietaal.Context;
import nl.bzk.brp.expressietaal.ExpressieType;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.basis.Groep;

/**
 * Representeert een expressie die verwijst naar een (root)object. Dit is meestal een Persoon, maar kan ook een ander rootobject zijn.
 */
public final class BrpObjectExpressie extends AbstractNietNumeriekRepresenteerbareLiteralExpressie {

    private final ExpressieType brpObjectType;
    private final BrpObject     brpObject;

    /**
     * Constructor.
     *
     * @param aBrpObject     Rootobject waar de expressie naar verwijst.
     * @param aBrpObjectType Type van het rootobject.
     */
    public BrpObjectExpressie(final BrpObject aBrpObject, final ExpressieType aBrpObjectType)
    {
        super();
        this.brpObject = aBrpObject;
        this.brpObjectType = aBrpObjectType;
    }

    public BrpObject getBrpObject() {
        return brpObject;
    }

    @Override
    public ExpressieType getType(final Context context) {
        return brpObjectType;
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
    protected String stringRepresentatie() {
        return "@" + brpObjectType.getNaam();
    }

}

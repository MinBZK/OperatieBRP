/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser.syntaxtree;

import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.logisch.kern.Persoon;

/**
 * Representeert een expressie die verwijst naar een (root)object. Dit is meestal een Persoon, maar kan ook een ander
 * rootobject zijn.
 */
public class RootObjectExpressie extends AbstractLiteralExpressie {

    private final ExpressieType rootObjectType;
    private final RootObject rootObject;

    /**
     * Constructor.
     *
     * @param rootObject     Rootobject waar de expressie naar verwijst.
     * @param rootObjectType Type van het rootobject.
     */
    public RootObjectExpressie(final RootObject rootObject, final ExpressieType rootObjectType) {
        this.rootObject = rootObject;
        this.rootObjectType = rootObjectType;
    }

    public RootObject getRootObject() {
        return rootObject;
    }

    @Override
    public ExpressieType getType() {
        return rootObjectType;
    }

    @Override
    public boolean isRootObject() {
        return true;
    }

    @Override
    public String alsLeesbareString() {
        return rootObjectType.getNaam();
    }

    @Override
    public String alsFormeleString() {
        return String.format("@%s(%s)@", rootObjectType.getNaam(), getIdentifierString());
    }

    /**
     * Geeft een stringrepresentatie van het rootobject. Dit is afhankelijk van het soort rootobject.
     *
     * @return Stringrepresentatie van het rootobject.
     */
    private String getIdentifierString() {
        if (rootObjectType == ExpressieType.PERSOON) {
            return ((Persoon) rootObject).getIdentificatienummers().getBurgerservicenummer().toString();
        } else {
            return "?";
        }
    }
}

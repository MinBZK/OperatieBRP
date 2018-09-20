/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.expressies.literals;

import nl.bzk.brp.expressietaal.Characters;
import nl.bzk.brp.expressietaal.Context;
import nl.bzk.brp.expressietaal.DefaultKeywordMapping;
import nl.bzk.brp.expressietaal.ExpressieType;
import nl.bzk.brp.expressietaal.Keyword;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;

/**
 * Representeert expressies die verwijzen naar een (concreet) attribuut in het logisch model.
 */
public final class BrpGroepReferentieExpressie extends AbstractNietNumeriekRepresenteerbareLiteralExpressie {

    private final Groep dataGroep;

    /**
     * Constructor.
     *
     * @param aDataGroep De concrete gegevensgroep.
     */
    public BrpGroepReferentieExpressie(final Groep aDataGroep) {
        super();
        this.dataGroep = aDataGroep;
    }

    @Override
    public ExpressieType getType(final Context context) {
        final ExpressieType type;
        if (dataGroep != null) {
            type = ExpressieType.GROEP;
        } else {
            type = ExpressieType.NULL;
        }
        return type;
    }

    @Override
    public boolean alsBoolean() {
        return false;
    }

    @Override
    public String alsString() {
        return null;
    }

    @Override
    public Attribuut getAttribuut() {
        return null;
    }

    @Override
    public Groep getGroep() {
        return dataGroep;
    }

    @Override
    protected String stringRepresentatie() {
        final String referentie;
        if (dataGroep != null) {
            referentie = dataGroep.getClass().getName();
        } else {
            referentie = DefaultKeywordMapping.getSyntax(Keyword.NULL);
        }
        return String.format("%c%s", Characters.REFERENCE, referentie);
    }
}

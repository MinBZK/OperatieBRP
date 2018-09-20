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
public final class BrpAttribuutReferentieExpressie extends AbstractNietNumeriekRepresenteerbareLiteralExpressie {

    private final Attribuut dataAttribuut;

    /**
     * Constructor.
     *
     * @param aDataAttribuut Het concrete gegevensattribuut.
     */
    public BrpAttribuutReferentieExpressie(final Attribuut aDataAttribuut) {
        super();
        this.dataAttribuut = aDataAttribuut;
    }

    @Override
    public ExpressieType getType(final Context context) {
        final ExpressieType type;
        if (dataAttribuut != null) {
            type = ExpressieType.ATTRIBUUT;
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
        return dataAttribuut;
    }

    @Override
    public Groep getGroep() {
        return null;
    }

    @Override
    protected String stringRepresentatie() {
        final String referentie;
        if (dataAttribuut != null) {
            referentie = dataAttribuut.getClass().getName();
        } else {
            referentie = DefaultKeywordMapping.getSyntax(Keyword.NULL);
        }
        return String.format("%c%s", Characters.REFERENCE, referentie);
    }
}

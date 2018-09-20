/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.objecttype.impl.gen;

import nl.bzk.brp.model.groep.interfaces.usr.PersoonNationaliteitStandaardGroep;
import nl.bzk.brp.model.objecttype.interfaces.gen.PersoonNationaliteitBasis;
import nl.bzk.brp.model.objecttype.interfaces.usr.Persoon;
import nl.bzk.brp.model.objecttype.statisch.Nationaliteit;
import nl.bzk.brp.web.AbstractObjectTypeWeb;

/**
 * Implementatie voor objecttype Persoon Nationaliteit.
 */
public abstract class AbstractPersoonNationaliteitWeb extends AbstractObjectTypeWeb
        implements PersoonNationaliteitBasis
{

    private Persoon persoon;
    private Nationaliteit nationaliteit;
    private PersoonNationaliteitStandaardGroep gegevens;

    @Override
    public Persoon getPersoon() {
        return persoon;
    }

    @Override
    public Nationaliteit getNationaliteit() {
        return nationaliteit;
    }

    @Override
    public PersoonNationaliteitStandaardGroep getGegevens() {
        return gegevens;
    }
}

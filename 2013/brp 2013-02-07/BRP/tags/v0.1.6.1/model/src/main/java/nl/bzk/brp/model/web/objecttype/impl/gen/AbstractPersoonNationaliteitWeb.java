/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.web.objecttype.impl.gen;

import nl.bzk.brp.model.objecttype.interfaces.gen.PersoonNationaliteitBasis;
import nl.bzk.brp.model.objecttype.statisch.Nationaliteit;
import nl.bzk.brp.model.web.AbstractObjectTypeWeb;
import nl.bzk.brp.model.web.groep.impl.usr.PersoonNationaliteitStandaardGroepWeb;
import nl.bzk.brp.model.web.objecttype.impl.usr.PersoonWeb;

/**
 * Implementatie voor objecttype Persoon Nationaliteit.
 */
@SuppressWarnings("serial")
public abstract class AbstractPersoonNationaliteitWeb extends AbstractObjectTypeWeb
        implements PersoonNationaliteitBasis
{

    private PersoonWeb persoon;
    private Nationaliteit nationaliteit;
    private PersoonNationaliteitStandaardGroepWeb gegevens;

    @Override
    public PersoonWeb getPersoon() {
        return persoon;
    }

    @Override
    public Nationaliteit getNationaliteit() {
        return nationaliteit;
    }

    @Override
    public PersoonNationaliteitStandaardGroepWeb getGegevens() {
        return gegevens;
    }
}

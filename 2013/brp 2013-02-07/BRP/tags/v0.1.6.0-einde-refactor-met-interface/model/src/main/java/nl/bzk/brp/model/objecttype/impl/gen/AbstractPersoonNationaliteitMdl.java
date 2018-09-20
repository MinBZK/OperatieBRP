/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.impl.gen;

import nl.bzk.brp.model.attribuuttype.TechnischIdMiddel;
import nl.bzk.brp.model.basis.AbstractDynamischObjectType;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonNationaliteitStandaardGroep;
import nl.bzk.brp.model.objecttype.interfaces.gen.PersoonNationaliteitBasis;
import nl.bzk.brp.model.objecttype.interfaces.usr.Persoon;
import nl.bzk.brp.model.objecttype.statisch.Nationaliteit;

/**
 * Implementatie voor objecttype Persoon Nationaliteit.
 */
public abstract class AbstractPersoonNationaliteitMdl extends AbstractDynamischObjectType
        implements PersoonNationaliteitBasis
{

    private TechnischIdMiddel iD;
    private Persoon persoon;
    private Nationaliteit nationaliteit;
    private PersoonNationaliteitStandaardGroep gegevens;

    public TechnischIdMiddel getID() {
        return iD;
    }

    public Persoon getPersoon() {
        return persoon;
    }

    public Nationaliteit getNationaliteit() {
        return nationaliteit;
    }

    public PersoonNationaliteitStandaardGroep getGegevens() {
        return gegevens;
    }
}

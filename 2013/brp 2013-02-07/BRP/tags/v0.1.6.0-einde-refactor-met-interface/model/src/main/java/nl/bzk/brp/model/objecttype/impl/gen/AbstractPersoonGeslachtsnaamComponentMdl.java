/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.impl.gen;

import nl.bzk.brp.model.attribuuttype.TechnischIdMiddel;
import nl.bzk.brp.model.attribuuttype.Volgnummer;
import nl.bzk.brp.model.basis.AbstractDynamischObjectType;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonGeslachtsnaamCompStandaardGroep;
import nl.bzk.brp.model.objecttype.interfaces.gen.PersoonGeslachtsnaamComponentBasis;
import nl.bzk.brp.model.objecttype.interfaces.usr.Persoon;

/**
 * Implementatie object type Persoon Geslachtsnaam component.
 */
public abstract class AbstractPersoonGeslachtsnaamComponentMdl extends AbstractDynamischObjectType
        implements PersoonGeslachtsnaamComponentBasis
{

    private TechnischIdMiddel iD;
    private Persoon persoon;
    private PersoonGeslachtsnaamCompStandaardGroep persoonGeslachtsnaamCompStandaardGroep;
    private Volgnummer volgnummer;

    public TechnischIdMiddel getID() {
        return iD;
    }

    @Override
    public Persoon getPersoon() {
        return persoon;
    }

    @Override
    public Volgnummer getVolgnummer() {
        return volgnummer;
    }

    @Override
    public PersoonGeslachtsnaamCompStandaardGroep getGegevens() {
        return persoonGeslachtsnaamCompStandaardGroep;
    }
}

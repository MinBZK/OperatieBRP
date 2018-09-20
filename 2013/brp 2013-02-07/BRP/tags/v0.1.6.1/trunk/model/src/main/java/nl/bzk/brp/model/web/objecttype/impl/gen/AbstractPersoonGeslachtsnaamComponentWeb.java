/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.web.objecttype.impl.gen;

import nl.bzk.brp.model.attribuuttype.Volgnummer;
import nl.bzk.brp.model.objecttype.interfaces.gen.PersoonGeslachtsnaamComponentBasis;
import nl.bzk.brp.model.web.AbstractObjectTypeWeb;
import nl.bzk.brp.model.web.groep.impl.usr.PersoonGeslachtsnaamCompStandaardGroepWeb;
import nl.bzk.brp.model.web.objecttype.impl.usr.PersoonWeb;

/**
 * Implementatie object type Persoon Geslachtsnaam component.
 */
@SuppressWarnings("serial")
public abstract class AbstractPersoonGeslachtsnaamComponentWeb extends AbstractObjectTypeWeb
        implements PersoonGeslachtsnaamComponentBasis
{

    private PersoonWeb persoon;
    private PersoonGeslachtsnaamCompStandaardGroepWeb persoonGeslachtsnaamCompStandaardGroep;
    private Volgnummer volgnummer;


    @Override
    public PersoonWeb getPersoon() {
        return persoon;
    }

    @Override
    public Volgnummer getVolgnummer() {
        return volgnummer;
    }

    @Override
    public PersoonGeslachtsnaamCompStandaardGroepWeb getGegevens() {
        return persoonGeslachtsnaamCompStandaardGroep;
    }
}

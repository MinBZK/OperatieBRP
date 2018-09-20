/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.bericht.basis;

import nl.bzk.brp.model.attribuuttype.Volgnummer;
import nl.bzk.brp.model.basis.AbstractObjectTypeBericht;
import nl.bzk.brp.model.groep.bericht.PersoonGeslachtsnaamCompStandaardGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.logisch.basis.PersoonGeslachtsnaamComponentBasis;

/**
 * Implementatie object type Persoon Geslachtsnaam component.
 */
@SuppressWarnings("serial")
public abstract class AbstractPersoonGeslachtsnaamComponentBericht extends AbstractObjectTypeBericht
        implements PersoonGeslachtsnaamComponentBasis
{

    private PersoonBericht persoon;
    private PersoonGeslachtsnaamCompStandaardGroepBericht persoonGeslachtsnaamCompStandaardGroep;
    private Volgnummer volgnummer;


    @Override
    public PersoonBericht getPersoon() {
        return persoon;
    }

    @Override
    public Volgnummer getVolgnummer() {
        return volgnummer;
    }

    @Override
    public PersoonGeslachtsnaamCompStandaardGroepBericht getGegevens() {
        return persoonGeslachtsnaamCompStandaardGroep;
    }

    public void setPersoonGeslachtsnaamCompStandaardGroep(final PersoonGeslachtsnaamCompStandaardGroepBericht persoonGeslachtsnaamCompStandaardGroep) {
        this.persoonGeslachtsnaamCompStandaardGroep = persoonGeslachtsnaamCompStandaardGroep;
    }

    public void setVolgnummer(final Volgnummer volgnummer) {
        this.volgnummer = volgnummer;
    }
}

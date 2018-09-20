/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.objecttype.bericht.basis;

import nl.bzk.copy.model.attribuuttype.Volgnummer;
import nl.bzk.copy.model.basis.AbstractObjectTypeBericht;
import nl.bzk.copy.model.groep.bericht.PersoonGeslachtsnaamcomponentStandaardGroepBericht;
import nl.bzk.copy.model.objecttype.bericht.PersoonBericht;
import nl.bzk.copy.model.objecttype.logisch.basis.PersoonGeslachtsnaamcomponentBasis;

/**
 * Implementatie object type Persoon Geslachtsnaam component.
 */
@SuppressWarnings("serial")
public abstract class AbstractPersoonGeslachtsnaamcomponentBericht extends AbstractObjectTypeBericht
        implements PersoonGeslachtsnaamcomponentBasis
{

    private PersoonBericht persoon;
    private PersoonGeslachtsnaamcomponentStandaardGroepBericht gegevens;
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
    public PersoonGeslachtsnaamcomponentStandaardGroepBericht getGegevens() {
        return gegevens;
    }

    public void setGegevens(final PersoonGeslachtsnaamcomponentStandaardGroepBericht gegevens) {
        this.gegevens = gegevens;
    }

    public void setVolgnummer(final Volgnummer volgnummer) {
        this.volgnummer = volgnummer;
    }
}

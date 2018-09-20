/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.objecttype.bericht.basis;

import javax.validation.Valid;

import nl.bzk.copy.model.basis.AbstractObjectTypeBericht;
import nl.bzk.copy.model.groep.bericht.PersoonAdresStandaardGroepBericht;
import nl.bzk.copy.model.objecttype.bericht.PersoonBericht;
import nl.bzk.copy.model.objecttype.logisch.basis.PersoonAdresBasis;


/**
 * Implementatie voor objecttype Persoon adres.
 */
@SuppressWarnings("serial")
public abstract class AbstractPersoonAdresBericht extends AbstractObjectTypeBericht implements PersoonAdresBasis {

    private PersoonBericht persoon;
    @Valid
    private PersoonAdresStandaardGroepBericht gegevens;

    @Override
    public PersoonBericht getPersoon() {
        return persoon;
    }

    @Override
    public PersoonAdresStandaardGroepBericht getGegevens() {
        return gegevens;
    }

    public void setPersoon(final PersoonBericht persoon) {
        this.persoon = persoon;
    }

    public void setGegevens(final PersoonAdresStandaardGroepBericht gegevens) {
        this.gegevens = gegevens;
    }
}

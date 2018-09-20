/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.bericht.basis;

import nl.bzk.brp.model.attribuuttype.Volgnummer;
import nl.bzk.brp.model.basis.AbstractObjectTypeBericht;
import nl.bzk.brp.model.groep.bericht.PersoonVoornaamStandaardGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.logisch.basis.PersoonVoornaamBasis;


/**
 * Implementatie voor objecttype Persoon voornaam.
 */
@SuppressWarnings("serial")
public abstract class AbstractPersoonVoornaamBericht extends AbstractObjectTypeBericht implements PersoonVoornaamBasis {

    private PersoonBericht                       persoon;
    private Volgnummer                           volgnummer;
    private PersoonVoornaamStandaardGroepBericht gegevens;

    @Override
    public PersoonBericht getPersoon() {
        return persoon;
    }

    @Override
    public Volgnummer getVolgnummer() {
        return volgnummer;
    }

    @Override
    public PersoonVoornaamStandaardGroepBericht getGegevens() {
        return gegevens;
    }

    public void setVolgnummer(final Volgnummer volgnummer) {
        this.volgnummer = volgnummer;
    }

    public void setGegevens(final PersoonVoornaamStandaardGroepBericht gegevens) {
        this.gegevens = gegevens;
    }
}

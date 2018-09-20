/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.groep.bericht.basis;

import nl.bzk.copy.model.attribuuttype.Voornaam;
import nl.bzk.copy.model.basis.AbstractGroepBericht;
import nl.bzk.copy.model.groep.logisch.basis.PersoonVoornaamStandaardGroepBasis;


/**
 * Implementatie voor standaard groep van persoon voornaam.
 */
@SuppressWarnings("serial")
public abstract class AbstractPersoonVoornaamStandaardGroepBericht extends AbstractGroepBericht implements
        PersoonVoornaamStandaardGroepBasis
{

    private Voornaam voornaam;

    @Override
    public Voornaam getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(final Voornaam voornaam) {
        this.voornaam = voornaam;
    }

}

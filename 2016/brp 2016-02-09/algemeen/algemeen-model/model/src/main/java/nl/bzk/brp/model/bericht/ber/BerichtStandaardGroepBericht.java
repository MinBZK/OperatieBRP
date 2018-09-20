/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.ber;

import javax.validation.Valid;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.MetaIdentificeerbaar;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.logisch.ber.BerichtStandaardGroep;


/**
 *
 *
 */
public final class BerichtStandaardGroepBericht extends AbstractBerichtStandaardGroepBericht implements Groep,
    BerichtStandaardGroep, MetaIdentificeerbaar
{

    @Override
    @Valid
    public AdministratieveHandelingBericht getAdministratieveHandeling() {
        return super.getAdministratieveHandeling();
    }

}

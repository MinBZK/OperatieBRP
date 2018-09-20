/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.ber;

import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.basis.MetaIdentificeerbaar;
import nl.bzk.brp.model.logisch.ber.Bijhoudingsplan;

/**
 * Het bijhoudingsplan welke opgesteld is naar aanleiding van een bijhoudingsvoorstel.
 *
 * Het bijhoudingsplan wordt niet gepersisteerd in de database. Uit de Bijhoudings POCs kwam geen businesscase om dit te
 * doen.
 *
 *
 *
 */
public class BijhoudingsplanBericht extends AbstractBijhoudingsplanBericht implements BrpObject, BerichtEntiteit, MetaIdentificeerbaar, Bijhoudingsplan {

    //handmatige wijziging
    @Override
    public Short getPartijBijhoudingsvoorstelId() {
        return null;
    }
    //handmatige wijziging
    @Override
    public Long getAdministratieveHandelingId() {
        return null;
    }
}

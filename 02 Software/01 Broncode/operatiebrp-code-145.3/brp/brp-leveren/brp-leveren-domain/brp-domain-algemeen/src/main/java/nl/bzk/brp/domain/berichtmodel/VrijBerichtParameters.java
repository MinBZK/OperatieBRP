/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.berichtmodel;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;

/**
 * Parameters voor een vrij bericht.
 */
public final class VrijBerichtParameters {

    private Partij zenderVrijBericht;
    private Partij ontvangerVrijBericht;

    /**
     * Constructor.
     *
     * @param zenderVrijBericht de zendende partij
     * @param ontvangerVrijBericht de ontvangende partij
     */
    public VrijBerichtParameters(final Partij zenderVrijBericht, final Partij ontvangerVrijBericht) {
        this.zenderVrijBericht = zenderVrijBericht;
        this.ontvangerVrijBericht = ontvangerVrijBericht;
    }

    public Partij getZenderVrijBericht() {
        return zenderVrijBericht;
    }

    public Partij getOntvangerVrijBericht() {
        return ontvangerVrijBericht;
    }

}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.web.beheer.controller.partijen;

import java.util.List;

import nl.bzk.brp.beheer.model.Partij;
import nl.bzk.brp.beheer.model.Rol;

/**
 * Form bean voor rollen scherm.
 *
 */
public class RolFormBean {

    private Partij       partij;

    private boolean      leesModus;

    /** Invoerveld geselecteerde items binnen rollen veld. */
    private List<Rol>    toeTeVoegenRollen;

    /** Invoerveld geselecteerde items binnen toevoegde rollen veld. */
    private List<String> teVerwijderenRollen;

    public Partij getPartij() {
        return partij;
    }

    public void setPartij(final Partij partij) {
        this.partij = partij;
    }

    public List<String> getTeVerwijderenRollen() {
        return teVerwijderenRollen;
    }

    public void setTeVerwijderenRollen(final List<String> teVerwijderenRollen) {
        this.teVerwijderenRollen = teVerwijderenRollen;
    }

    public List<Rol> getToeTeVoegenRollen() {
        return toeTeVoegenRollen;
    }

    public void setToeTeVoegenRollen(final List<Rol> toeTeVoegenRollen) {
        this.toeTeVoegenRollen = toeTeVoegenRollen;
    }

    public boolean isLeesModus() {
        return leesModus;
    }

    public void setLeesModus(final boolean leesModus) {
        this.leesModus = leesModus;
    }
}

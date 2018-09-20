/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.afnemerindicaties.model;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.model.internbericht.RegelMelding;


/**
 * Resultaatobject voor het plaatsen en verwijderen van afnemerindicaties. Deze bevat de administratieve handeling id
 * en eventuele meldingen.
 */
public final class BewerkAfnemerindicatieResultaat {

    private Long administratieveHandelingId;

    private List<RegelMelding> meldingen = new ArrayList<>();

    public Long getAdministratieveHandelingId() {
        return administratieveHandelingId;
    }

    public void setAdministratieveHandelingId(final Long administratieveHandelingId) {
        this.administratieveHandelingId = administratieveHandelingId;
    }

    public List<RegelMelding> getMeldingen() {
        return meldingen;
    }

    public void setMeldingen(final List<RegelMelding> meldingen) {
        this.meldingen = meldingen;
    }

}

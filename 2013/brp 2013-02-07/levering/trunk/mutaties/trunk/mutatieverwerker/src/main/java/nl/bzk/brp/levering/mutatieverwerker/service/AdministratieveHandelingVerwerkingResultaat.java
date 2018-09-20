/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatieverwerker.service;

import java.util.List;

import nl.bzk.brp.business.stappen.AbstractStappenResultaat;
import nl.bzk.brp.model.validatie.Melding;

/**
 * Deze klasse bevat het resultaat voor de administratieve handeling stappen verwerking.
 */
public class AdministratieveHandelingVerwerkingResultaat extends AbstractStappenResultaat {

    private boolean verwerkingIsSuccesvol = true;

    /**
     * Default contructor voor deze klasse.
     */
    public AdministratieveHandelingVerwerkingResultaat() {

    }

    /**
     * Construeerd een AdministratieveHandelingVerwerkingResultaat met een lijst van meldingen.
     * @param meldingen De lijst van meldingen.
     */
    public AdministratieveHandelingVerwerkingResultaat(final List<Melding> meldingen) {
        super(meldingen);
    }

    @Override
    public boolean isFoutief() {
        return !verwerkingIsSuccesvol;
    }

    @Override
    public boolean isSuccesvol() {
        return verwerkingIsSuccesvol;
    }

    @Override
    public boolean bevatStoppendeFouten() {
        return !verwerkingIsSuccesvol;
    }

    public void setVerwerkingIsSuccesvol(final boolean verwerkingIsSuccesvol) {
        this.verwerkingIsSuccesvol = verwerkingIsSuccesvol;
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.stappen.populatie;

import java.util.List;

import nl.bzk.brp.business.stappen.AbstractStappenResultaat;
import nl.bzk.brp.model.validatie.Melding;


/**
 * Deze klasse bevat het resultaat voor de afnemer stappen verwerking.
 */
public class LeveringautorisatieVerwerkingResultaat extends AbstractStappenResultaat {

    /**
     * Default contructor voor deze klasse.
     */
    public LeveringautorisatieVerwerkingResultaat() {

    }

    /**
     * Construeert een LeveringautorisatieVerwerkingResultaat met een lijst van meldingen.
     *
     * @param meldingen De lijst van meldingen.
     */
    public LeveringautorisatieVerwerkingResultaat(final List<Melding> meldingen) {
        super(meldingen);
    }

}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.bijhouding.business.stappen.resultaat.ResultaatMelding;

/**
 * Resultaat van een afleiding. Bevat een lijst met vervolg afleidingen en een lijst met opgetreden meldingen.
 */
public class AfleidingResultaat {

    private List<Afleidingsregel>  vervolgAfleidingen;
    private List<ResultaatMelding> meldingen;

    /**
     * Constructor.
     */
    public AfleidingResultaat() {
        this.vervolgAfleidingen = new ArrayList<>();
        this.meldingen = new ArrayList<>();
    }

    public List<Afleidingsregel> getVervolgAfleidingen() {
        return vervolgAfleidingen;
    }

    public List<ResultaatMelding> getMeldingen() {
        return meldingen;
    }

    /**
     * Voeg een afleidingsregel toe.
     *
     * @param afleidingsregel afleidingsregel
     */
    public void voegVervolgAfleidingToe(final Afleidingsregel afleidingsregel) {
        this.vervolgAfleidingen.add(afleidingsregel);
    }

    /**
     * Voeg een melding toe.
     *
     * @param melding melding
     */
    public void voegMeldingToe(final ResultaatMelding melding) {
        this.meldingen.add(melding);
    }

}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.viewer;

import java.util.List;

import nl.moderniseringgba.migratie.conversie.viewer.log.FoutRegel;


/**
 * Het JSON response object, met daarin de PersoonsijstGroepen (met daarin de PLs en controles). Bevat ook de
 * foutmeldingen van de verwerking (als foutRegels)
 */
public class ViewerResponse {
    private final List<PersoonslijstGroep> persoonslijstGroepen;
    private final List<FoutRegel> foutRegels;

    /**
     * Constructor.
     * 
     * @param persoonslijstGroepen
     *            De lijst met persoonslijstgroepen.
     * @param foutRegels
     *            De lijst met foutRegels.
     */
    public ViewerResponse(final List<PersoonslijstGroep> persoonslijstGroepen, final List<FoutRegel> foutRegels) {
        this.persoonslijstGroepen = persoonslijstGroepen;
        this.foutRegels = foutRegels;
    }

    /**
     * Dit heeft de Upload JS nodig om te bevestigen dat de upload gelukt is.
     * 
     * @return String met daarin true.
     */
    public final String getSuccess() {
        return "true";
    }

    /**
     * @return De persoonslijstGroepen lijst.
     */
    public final List<PersoonslijstGroep> getPersoonslijstGroepen() {
        return persoonslijstGroepen;
    }

    /**
     * @return Een lijst met fouten die hebben plaatsgevonden tijdens het inlezen.
     */
    public final List<FoutRegel> getFoutRegels() {
        return foutRegels;
    }
}

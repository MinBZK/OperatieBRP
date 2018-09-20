/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer;

import java.util.List;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoFoutRegel;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoPersoonslijstGroep;

/**
 * Het JSON response object, met daarin de PersoonsijstGroepen (met daarin de PLs en controles). Bevat ook de
 * foutmeldingen van de verwerking (als foutRegels)
 */
public class ViewerResponse {
    private final List<GgoPersoonslijstGroep> persoonslijstGroepen;
    private final List<GgoFoutRegel> foutRegels;

    /**
     * Constructor.
     * 
     * @param persoonslijstGroepen
     *            De lijst met persoonslijstgroepen.
     * @param list
     *            De lijst met foutRegels.
     */
    public ViewerResponse(final List<GgoPersoonslijstGroep> persoonslijstGroepen, final List<GgoFoutRegel> list) {
        this.persoonslijstGroepen = persoonslijstGroepen;
        foutRegels = list;
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
     * Geef de waarde van persoonslijst groepen.
     *
     * @return De persoonslijstGroepen lijst.
     */
    public final List<GgoPersoonslijstGroep> getPersoonslijstGroepen() {
        return persoonslijstGroepen;
    }

    /**
     * Geef de waarde van fout regels.
     *
     * @return Een lijst met fouten die hebben plaatsgevonden tijdens het inlezen.
     */
    public final List<GgoFoutRegel> getFoutRegels() {
        return foutRegels;
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.builder;

import java.util.List;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.levering.lo3.bericht.WebserviceAntwoord;

/**
 * Webservice antwoord builder.
 */
public interface WebserviceAntwoordBuilder {

    /**
     * Maak een antwoord.
     * @param persoonslijsten persoonslijsten
     * @return antwoord
     */
    WebserviceAntwoord build(List<Persoonslijst> persoonslijsten);
}

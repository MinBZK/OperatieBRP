/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.builder;

import java.util.List;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.levering.lo3.bericht.BerichtFactory;
import nl.bzk.brp.levering.lo3.bericht.OpvragenPLWebserviceAntwoord;

/**
 * Builder voor een AdhocWebserviceAntwoord.
 */
public class OpvragenPLWebserviceAntwoordBuilder implements WebserviceAntwoordBuilder {

    private final BerichtFactory berichtFactory;

    /**
     * Constructor.
     * @param berichtFactory bericht factory
     */
    public OpvragenPLWebserviceAntwoordBuilder(final BerichtFactory berichtFactory) {
        this.berichtFactory = berichtFactory;
    }

    /**
     * Maakt een AdhocWebserviceAntwoord.
     * @param persoonslijsten persoonslijsten
     * @return AdhocWebserviceAntwoord
     */
    @Override
    public OpvragenPLWebserviceAntwoord build(final List<Persoonslijst> persoonslijsten) {
        return berichtFactory.maakVraagPLAntwoord(persoonslijsten);
    }
}

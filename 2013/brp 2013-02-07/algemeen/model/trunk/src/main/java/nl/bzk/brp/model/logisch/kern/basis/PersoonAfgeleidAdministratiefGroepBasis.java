/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern.basis;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijd;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNee;
import nl.bzk.brp.model.basis.Groep;


/**
 *
 *
 */
public interface PersoonAfgeleidAdministratiefGroepBasis extends Groep {

    /**
     * Retourneert Tijdstip laatste wijziging van Afgeleid administratief.
     *
     * @return Tijdstip laatste wijziging.
     */
    DatumTijd getTijdstipLaatsteWijziging();

    /**
     * Retourneert Gegevens in onderzoek? van Afgeleid administratief.
     *
     * @return Gegevens in onderzoek?.
     */
    JaNee getIndicatieGegevensInOnderzoek();

}

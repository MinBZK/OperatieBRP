/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern.basis;

import java.util.Collection;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijd;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.basis.ObjectType;
import nl.bzk.brp.model.logisch.kern.ActieBron;
import nl.bzk.brp.model.logisch.kern.AdministratieveHandeling;


/**
 * Eenheid van gegevensbewerking in de BRP.
 *
 * Het bijhouden van de BRP geschiedt door het uitwerken van gegevensbewerkingen op de inhoud van de BRP, c.q. het doen
 * van bijhoudingsacties. De kleinste eenheid van gegevensbewerking is de "BRP actie".
 *
 *
 *
 */
public interface ActieBasis extends ObjectType {

    /**
     * Retourneert Soort van Actie.
     *
     * @return Soort.
     */
    SoortActie getSoort();

    /**
     * Retourneert Administratieve handeling van Actie.
     *
     * @return Administratieve handeling.
     */
    AdministratieveHandeling getAdministratieveHandeling();

    /**
     * Retourneert Partij van Actie.
     *
     * @return Partij.
     */
    Partij getPartij();

    /**
     * Retourneert Datum aanvang geldigheid van Actie.
     *
     * @return Datum aanvang geldigheid.
     */
    Datum getDatumAanvangGeldigheid();

    /**
     * Retourneert Datum einde geldigheid van Actie.
     *
     * @return Datum einde geldigheid.
     */
    Datum getDatumEindeGeldigheid();

    /**
     * Retourneert Tijdstip registratie van Actie.
     *
     * @return Tijdstip registratie.
     */
    DatumTijd getTijdstipRegistratie();

    /**
     * Retourneert Actie/Bronnen van Actie.
     *
     * @return Actie/Bronnen van Actie.
     */
    Collection<? extends ActieBron> getBronnen();

}

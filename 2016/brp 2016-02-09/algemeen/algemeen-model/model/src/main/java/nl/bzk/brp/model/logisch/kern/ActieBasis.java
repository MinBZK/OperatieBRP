/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern;

import java.util.Collection;
import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.basis.BrpObject;

/**
 * Kleinste eenheid van gegevensbewerking in de BRP.
 *
 * Het bijhouden van de BRP geschiedt door het verwerken van administratieve handelingen. Deze administratieve
 * handelingen vallen uiteen in één of meer 'eenheden' van gegevensbewerkingen.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.LogischModelGenerator")
public interface ActieBasis extends BrpObject {

    /**
     * Retourneert Soort van Actie.
     *
     * @return Soort.
     */
    SoortActieAttribuut getSoort();

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
    PartijAttribuut getPartij();

    /**
     * Retourneert Datum aanvang geldigheid van Actie.
     *
     * @return Datum aanvang geldigheid.
     */
    DatumEvtDeelsOnbekendAttribuut getDatumAanvangGeldigheid();

    /**
     * Retourneert Datum einde geldigheid van Actie.
     *
     * @return Datum einde geldigheid.
     */
    DatumEvtDeelsOnbekendAttribuut getDatumEindeGeldigheid();

    /**
     * Retourneert Tijdstip registratie van Actie.
     *
     * @return Tijdstip registratie.
     */
    DatumTijdAttribuut getTijdstipRegistratie();

    /**
     * Retourneert Datum ontlening van Actie.
     *
     * @return Datum ontlening.
     */
    DatumEvtDeelsOnbekendAttribuut getDatumOntlening();

    /**
     * Retourneert Actie \ Bronnen van Actie.
     *
     * @return Actie \ Bronnen van Actie.
     */
    Collection<? extends ActieBron> getBronnen();

}

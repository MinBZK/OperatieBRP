/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.autaut;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortDocumentAttribuut;
import nl.bzk.brp.model.basis.Groep;

/**
 * De uitzondering wordt gedefinieerd door middel van een aantal attributen te weten; A:"Partij \
 * Fiatteringsuitzondering.Partij bijhoudingsvoorstel", A:"Partij \ Fiatteringsuitzondering.Soort document" en A:"Partij
 * \ Fiatteringsuitzondering.Soort administratieve handeling". Minimaal ��n van deze attributen moet gevuld zijn. Als er
 * meerdere attributen gevuld zijn, dan moet het bijhoudinsvoorstel voldoen aan alle criteria.
 *
 * Evaluatie van akten gaan simpel en bewust niet subtiel. We verzamelen alle akten die voorkomen bij een bijhouding en
 * kijken of in die set van akten het soort document (dat is opgegeven als uitzondering) voorkomt. We gaan dus niet per
 * persoon kijken etc.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.LogischModelGenerator")
public interface PartijFiatteringsuitzonderingStandaardGroepBasis extends Groep {

    /**
     * Retourneert Datum ingang van Standaard.
     *
     * @return Datum ingang.
     */
    DatumAttribuut getDatumIngang();

    /**
     * Retourneert Datum einde van Standaard.
     *
     * @return Datum einde.
     */
    DatumAttribuut getDatumEinde();

    /**
     * Retourneert Partij bijhoudingsvoorstel van Standaard.
     *
     * @return Partij bijhoudingsvoorstel.
     */
    PartijAttribuut getPartijBijhoudingsvoorstel();

    /**
     * Retourneert Soort document van Standaard.
     *
     * @return Soort document.
     */
    SoortDocumentAttribuut getSoortDocument();

    /**
     * Retourneert Soort administratieve handeling van Standaard.
     *
     * @return Soort administratieve handeling.
     */
    SoortAdministratieveHandelingAttribuut getSoortAdministratieveHandeling();

    /**
     * Retourneert Geblokkeerd? van Standaard.
     *
     * @return Geblokkeerd?.
     */
    JaAttribuut getIndicatieGeblokkeerd();

}

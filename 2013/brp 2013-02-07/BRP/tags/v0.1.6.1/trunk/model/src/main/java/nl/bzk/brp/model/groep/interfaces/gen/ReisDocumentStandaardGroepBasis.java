/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.interfaces.gen;


import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.LengteInCm;
import nl.bzk.brp.model.attribuuttype.ReisDocumentNummer;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.objecttype.statisch.AutoriteitVanAfgifteReisdocument;
import nl.bzk.brp.model.objecttype.statisch.RedenVervallenReisdocument;

/**
 * .
 *
 */
public interface ReisDocumentStandaardGroepBasis extends Groep {
    /**
     * .
     * @return .
     */
    ReisDocumentNummer  getNummer();
    /**
     * .
     * @return .
     */
    Datum               getDatumUitgifte();
    /**
     * .
     * @return .
     */
    Datum  getDatumIngangDocument();
    /**
     * .
     * @return .
     */
    AutoriteitVanAfgifteReisdocument        getAutoriteitVanAfgifte();
    /**
     * .
     * @return .
     */
    Datum               getDatumVoorzieneEindeGeldigheid();
    /**
     * .
     * @return .
     */
    Datum               getDatumInhoudingVermissing();
    /**
     * .
     * @return .
     */
    RedenVervallenReisdocument  getRedenVervallen();
    /**
     * .
     * @return .
     */
    LengteInCm          getLengteHouder();

}

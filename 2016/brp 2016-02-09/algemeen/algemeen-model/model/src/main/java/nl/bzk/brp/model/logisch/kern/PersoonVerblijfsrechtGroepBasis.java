/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AanduidingVerblijfsrechtAttribuut;
import nl.bzk.brp.model.basis.Groep;

/**
 * Deze groep bevat geen materiele historie (meer). De IND stuurt namelijk alleen de actuele status. Daarmee is het
 * verleden niet meer betrouwbaar (er zijn geen correcties doorgevoerd op de materiele tijdlijn). Daarom wordt er
 * uitsluitend 1 actueel voorkomen geregistreerd die aanvangt op een bepaalde datum.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.LogischModelGenerator")
public interface PersoonVerblijfsrechtGroepBasis extends Groep {

    /**
     * Retourneert Aanduiding verblijfsrecht van Verblijfsrecht.
     *
     * @return Aanduiding verblijfsrecht.
     */
    AanduidingVerblijfsrechtAttribuut getAanduidingVerblijfsrecht();

    /**
     * Retourneert Datum aanvang verblijfsrecht van Verblijfsrecht.
     *
     * @return Datum aanvang verblijfsrecht.
     */
    DatumEvtDeelsOnbekendAttribuut getDatumAanvangVerblijfsrecht();

    /**
     * Retourneert Datum mededeling verblijfsrecht van Verblijfsrecht.
     *
     * @return Datum mededeling verblijfsrecht.
     */
    DatumEvtDeelsOnbekendAttribuut getDatumMededelingVerblijfsrecht();

    /**
     * Retourneert Datum voorzien einde verblijfsrecht van Verblijfsrecht.
     *
     * @return Datum voorzien einde verblijfsrecht.
     */
    DatumEvtDeelsOnbekendAttribuut getDatumVoorzienEindeVerblijfsrecht();

}

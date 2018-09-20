/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AutoriteitVanAfgifteReisdocumentCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ReisdocumentNummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AanduidingInhoudingVermissingReisdocumentAttribuut;
import nl.bzk.brp.model.basis.Groep;

/**
 * 1. Vorm van historie: alleen formeel. Motivatie: de gegevens van een reisdocument zijn enerzijds de gegevens die in
 * het document staan, zoals lengte van de houder. Anderzijds zijn het gegevens die normaliter éénmalig wijzigen, zoals
 * reden vervallen. Omdat hetzelfde reisdocument niet tweemaal wordt uitgegeven, is formele historie voldoende.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.LogischModelGenerator")
public interface PersoonReisdocumentStandaardGroepBasis extends Groep {

    /**
     * Retourneert Nummer van Standaard.
     *
     * @return Nummer.
     */
    ReisdocumentNummerAttribuut getNummer();

    /**
     * Retourneert Autoriteit van afgifte van Standaard.
     *
     * @return Autoriteit van afgifte.
     */
    AutoriteitVanAfgifteReisdocumentCodeAttribuut getAutoriteitVanAfgifte();

    /**
     * Retourneert Datum ingang document van Standaard.
     *
     * @return Datum ingang document.
     */
    DatumEvtDeelsOnbekendAttribuut getDatumIngangDocument();

    /**
     * Retourneert Datum einde document van Standaard.
     *
     * @return Datum einde document.
     */
    DatumEvtDeelsOnbekendAttribuut getDatumEindeDocument();

    /**
     * Retourneert Datum uitgifte van Standaard.
     *
     * @return Datum uitgifte.
     */
    DatumEvtDeelsOnbekendAttribuut getDatumUitgifte();

    /**
     * Retourneert Datum inhouding/vermissing van Standaard.
     *
     * @return Datum inhouding/vermissing.
     */
    DatumEvtDeelsOnbekendAttribuut getDatumInhoudingVermissing();

    /**
     * Retourneert Aanduiding inhouding/vermissing van Standaard.
     *
     * @return Aanduiding inhouding/vermissing.
     */
    AanduidingInhoudingVermissingReisdocumentAttribuut getAanduidingInhoudingVermissing();

}

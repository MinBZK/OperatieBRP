/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern.basis;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LengteInCm;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ReisdocumentNummer;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AutoriteitVanAfgifteReisdocument;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVervallenReisdocument;
import nl.bzk.brp.model.basis.Groep;


/**
 * 1. Vorm van historie: alleen formeel. Motivatie: de gegevens van een reisdocument zijn enerzijds de gegevens die in
 * het document staan, zoals lengte van de houder. Anderzijds zijn het gegevens die normaliter ��nmalig wijzigen, zoals
 * reden vervallen.
 * Omdat hetzelfde reisdocument niet tweemaal wordt uitgegeven, is formele historie voldoende.
 * RvdP 26 jan 2012.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.LogischModelGenerator.
 * Generator versie: 1.0-SNAPSHOT.
 * Metaregister versie: 1.6.0.
 * Gegenereerd op: Tue Jan 15 12:53:52 CET 2013.
 */
public interface PersoonReisdocumentStandaardGroepBasis extends Groep {

    /**
     * Retourneert Nummer van Standaard.
     *
     * @return Nummer.
     */
    ReisdocumentNummer getNummer();

    /**
     * Retourneert Lengte houder van Standaard.
     *
     * @return Lengte houder.
     */
    LengteInCm getLengteHouder();

    /**
     * Retourneert Autoriteit van afgifte van Standaard.
     *
     * @return Autoriteit van afgifte.
     */
    AutoriteitVanAfgifteReisdocument getAutoriteitVanAfgifte();

    /**
     * Retourneert Datum ingang document van Standaard.
     *
     * @return Datum ingang document.
     */
    Datum getDatumIngangDocument();

    /**
     * Retourneert Datum uitgifte van Standaard.
     *
     * @return Datum uitgifte.
     */
    Datum getDatumUitgifte();

    /**
     * Retourneert Datum voorziene einde geldigheid van Standaard.
     *
     * @return Datum voorziene einde geldigheid.
     */
    Datum getDatumVoorzieneEindeGeldigheid();

    /**
     * Retourneert Datum inhouding/vermissing van Standaard.
     *
     * @return Datum inhouding/vermissing.
     */
    Datum getDatumInhoudingVermissing();

    /**
     * Retourneert Reden vervallen van Standaard.
     *
     * @return Reden vervallen.
     */
    RedenVervallenReisdocument getRedenVervallen();

}

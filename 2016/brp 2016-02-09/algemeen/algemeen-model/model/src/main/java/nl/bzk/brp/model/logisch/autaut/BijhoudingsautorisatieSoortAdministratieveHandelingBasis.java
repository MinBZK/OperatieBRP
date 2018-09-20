/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.autaut;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.basis.BrpObject;

/**
 * Soorten administratieve handelingen waarvoor de A:"Toegang bijhoudingsautorisatie.Geautoriseerde" toegang geeft aan
 * de bewerker.
 *
 * Het vastleggen van Soorten administratieve handelingen geeft aan dat alleen die Soorten administratieve handelingen
 * zijn toegestaan. Het niet opnemen van enige Soort administratieve handeling betekent dat alle Soorten zijn toegestaan
 * voor de Bijhoudingsautorisatie.
 *
 * Feitelijk bestaat er nog een Bijhoudingsautorisatie en is dit de koppeltabel tussen de Bijhoudingsautorisatie van de
 * Soorten administratieve handelingen. Omdat de Bijhoudingsautorisatie 1-op-1 loopt met de Toegang
 * bijhoudingsautorisatie, is de Bijhoudingsautorisatie vervallen en verwijzen we hier direct naar de Toegang.
 *
 * De bewerkersconstructie (samenwerken) ligt dicht tegen de OT:"Partij \ Fiatteringsuitzondering" ("de knop"). Er is
 * bewust gekozen om dit niet samen te voegen in 1 construct; De knop ondersteunt: Nee / Ja / Ja, maar niet indien
 * (uitzonderingen) De knop ondersteunt maar een beperkte set handelingen: Alleen die handelingen die betrekking hebben
 * op relaties. Alle overige handelingen mogen niet omdat plaatsonafhankelijke bijhouding niet is toegestaan. De knop is
 * ook gericht op het filteren van soorten documenten. Dit lijkt niet relevant voor samenwerken. De beide constructen:
 * fiatteren en samenwerken/uitbesteden moeten samen kunnen voorkomen. (De partij gemeente besteed het werk uit en
 * fiatteert bijhoudingen). Samenvoegen is wellicht mogelijk maar gaat een generalisatie opleveren die de duidelijkheid
 * niet te goede komt. Expliciet uitmodelleren is dan, zeker in het kader van autorisaties, de veilige keuze.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.LogischModelGenerator")
public interface BijhoudingsautorisatieSoortAdministratieveHandelingBasis extends BrpObject {

    /**
     * Retourneert Toegang bijhoudingsautorisatie van Bijhoudingsautorisatie \ Soort administratieve handeling.
     *
     * @return Toegang bijhoudingsautorisatie.
     */
    ToegangBijhoudingsautorisatie getToegangBijhoudingsautorisatie();

    /**
     * Retourneert Soort administratieve handeling van Bijhoudingsautorisatie \ Soort administratieve handeling.
     *
     * @return Soort administratieve handeling.
     */
    SoortAdministratieveHandelingAttribuut getSoortAdministratieveHandeling();

}

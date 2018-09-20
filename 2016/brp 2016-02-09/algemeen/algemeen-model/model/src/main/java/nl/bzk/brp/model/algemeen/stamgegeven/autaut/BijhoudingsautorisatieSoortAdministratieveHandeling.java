/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.autaut;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

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
@Table(schema = "AutAut", name = "BijhautorisatieSrtAdmHnd")
@Access(value = AccessType.FIELD)
@Entity
@Immutable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class BijhoudingsautorisatieSoortAdministratieveHandeling extends AbstractBijhoudingsautorisatieSoortAdministratieveHandeling {

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected BijhoudingsautorisatieSoortAdministratieveHandeling() {
        super();
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param toegangBijhoudingsautorisatie toegangBijhoudingsautorisatie van
     *            BijhoudingsautorisatieSoortAdministratieveHandeling.
     * @param soortAdministratieveHandeling soortAdministratieveHandeling van
     *            BijhoudingsautorisatieSoortAdministratieveHandeling.
     */
    protected BijhoudingsautorisatieSoortAdministratieveHandeling(
        final ToegangBijhoudingsautorisatie toegangBijhoudingsautorisatie,
        final SoortAdministratieveHandeling soortAdministratieveHandeling)
    {
        super(toegangBijhoudingsautorisatie, soortAdministratieveHandeling);
    }

}

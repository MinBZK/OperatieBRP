/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.autaut;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
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
@Access(value = AccessType.FIELD)
@EntityListeners(value = StamgegevenEntityListener.class)
@Immutable
@Generated(value = "nl.bzk.brp.generatoren.java.DynamischeStamgegevensGenerator")
@MappedSuperclass
public abstract class AbstractBijhoudingsautorisatieSoortAdministratieveHandeling {

    @Id
    @JsonProperty
    private Integer iD;

    @ManyToOne
    @JoinColumn(name = "ToegangBijhautorisatie")
    @Fetch(value = FetchMode.JOIN)
    private ToegangBijhoudingsautorisatie toegangBijhoudingsautorisatie;

    @Column(name = "SrtAdmHnd")
    private SoortAdministratieveHandeling soortAdministratieveHandeling;

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected AbstractBijhoudingsautorisatieSoortAdministratieveHandeling() {
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param toegangBijhoudingsautorisatie toegangBijhoudingsautorisatie van
     *            BijhoudingsautorisatieSoortAdministratieveHandeling.
     * @param soortAdministratieveHandeling soortAdministratieveHandeling van
     *            BijhoudingsautorisatieSoortAdministratieveHandeling.
     */
    protected AbstractBijhoudingsautorisatieSoortAdministratieveHandeling(
        final ToegangBijhoudingsautorisatie toegangBijhoudingsautorisatie,
        final SoortAdministratieveHandeling soortAdministratieveHandeling)
    {
        this.toegangBijhoudingsautorisatie = toegangBijhoudingsautorisatie;
        this.soortAdministratieveHandeling = soortAdministratieveHandeling;

    }

    /**
     * Retourneert ID van Bijhoudingsautorisatie \ Soort administratieve handeling.
     *
     * @return ID.
     */
    protected Integer getID() {
        return iD;
    }

    /**
     * Retourneert Toegang bijhoudingsautorisatie van Bijhoudingsautorisatie \ Soort administratieve handeling.
     *
     * @return Toegang bijhoudingsautorisatie.
     */
    public final ToegangBijhoudingsautorisatie getToegangBijhoudingsautorisatie() {
        return toegangBijhoudingsautorisatie;
    }

    /**
     * Retourneert Soort administratieve handeling van Bijhoudingsautorisatie \ Soort administratieve handeling.
     *
     * @return Soort administratieve handeling.
     */
    public final SoortAdministratieveHandeling getSoortAdministratieveHandeling() {
        return soortAdministratieveHandeling;
    }

}

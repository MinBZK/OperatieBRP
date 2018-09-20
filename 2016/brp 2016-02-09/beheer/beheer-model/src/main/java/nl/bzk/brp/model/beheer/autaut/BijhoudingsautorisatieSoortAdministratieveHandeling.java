/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.beheer.autaut;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;

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
@Entity(name = "beheer.BijhoudingsautorisatieSoortAdministratieveHandeling")
@Table(schema = "AutAut", name = "BijhautorisatieSrtAdmHnd")
@Generated(value = "nl.bzk.brp.generatoren.java.BeheerGenerator")
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class BijhoudingsautorisatieSoortAdministratieveHandeling {

    @Id
    @SequenceGenerator(name = "BIJHOUDINGSAUTORISATIESOORTADMINISTRATIEVEHANDELING", sequenceName = "AutAut.seq_BijhautorisatieSrtAdmHnd")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "BIJHOUDINGSAUTORISATIESOORTADMINISTRATIEVEHANDELING")
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ToegangBijhautorisatie")
    private ToegangBijhoudingsautorisatie toegangBijhoudingsautorisatie;

    @Column(name = "SrtAdmHnd")
    @Enumerated
    private SoortAdministratieveHandeling soortAdministratieveHandeling;

    /**
     * Retourneert ID van Bijhoudingsautorisatie \ Soort administratieve handeling.
     *
     * @return ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Toegang bijhoudingsautorisatie van Bijhoudingsautorisatie \ Soort administratieve handeling.
     *
     * @return Toegang bijhoudingsautorisatie.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public ToegangBijhoudingsautorisatie getToegangBijhoudingsautorisatie() {
        return toegangBijhoudingsautorisatie;
    }

    /**
     * Retourneert Soort administratieve handeling van Bijhoudingsautorisatie \ Soort administratieve handeling.
     *
     * @return Soort administratieve handeling.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public SoortAdministratieveHandeling getSoortAdministratieveHandeling() {
        return soortAdministratieveHandeling;
    }

    /**
     * Zet ID van Bijhoudingsautorisatie \ Soort administratieve handeling.
     *
     * @param pID ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setID(final Integer pID) {
        this.iD = pID;
    }

    /**
     * Zet Toegang bijhoudingsautorisatie van Bijhoudingsautorisatie \ Soort administratieve handeling.
     *
     * @param pToegangBijhoudingsautorisatie Toegang bijhoudingsautorisatie.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setToegangBijhoudingsautorisatie(final ToegangBijhoudingsautorisatie pToegangBijhoudingsautorisatie) {
        this.toegangBijhoudingsautorisatie = pToegangBijhoudingsautorisatie;
    }

    /**
     * Zet Soort administratieve handeling van Bijhoudingsautorisatie \ Soort administratieve handeling.
     *
     * @param pSoortAdministratieveHandeling Soort administratieve handeling.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setSoortAdministratieveHandeling(final SoortAdministratieveHandeling pSoortAdministratieveHandeling) {
        this.soortAdministratieveHandeling = pSoortAdministratieveHandeling;
    }

}

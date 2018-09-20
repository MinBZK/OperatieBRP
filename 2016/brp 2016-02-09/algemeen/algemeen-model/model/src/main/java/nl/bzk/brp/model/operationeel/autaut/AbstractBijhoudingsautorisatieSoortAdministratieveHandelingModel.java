/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.autaut;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.basis.AbstractDynamischObject;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.logisch.autaut.BijhoudingsautorisatieSoortAdministratieveHandelingBasis;

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
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractBijhoudingsautorisatieSoortAdministratieveHandelingModel extends AbstractDynamischObject implements
        BijhoudingsautorisatieSoortAdministratieveHandelingBasis, ModelIdentificeerbaar<Integer>
{

    @Transient
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ToegangBijhautorisatie")
    @JsonProperty
    private ToegangBijhoudingsautorisatieModel toegangBijhoudingsautorisatie;

    @Embedded
    @AttributeOverride(name = SoortAdministratieveHandelingAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "SrtAdmHnd"))
    @JsonProperty
    private SoortAdministratieveHandelingAttribuut soortAdministratieveHandeling;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractBijhoudingsautorisatieSoortAdministratieveHandelingModel() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param toegangBijhoudingsautorisatie toegangBijhoudingsautorisatie van Bijhoudingsautorisatie \ Soort
     *            administratieve handeling.
     * @param soortAdministratieveHandeling soortAdministratieveHandeling van Bijhoudingsautorisatie \ Soort
     *            administratieve handeling.
     */
    public AbstractBijhoudingsautorisatieSoortAdministratieveHandelingModel(
        final ToegangBijhoudingsautorisatieModel toegangBijhoudingsautorisatie,
        final SoortAdministratieveHandelingAttribuut soortAdministratieveHandeling)
    {
        this();
        this.toegangBijhoudingsautorisatie = toegangBijhoudingsautorisatie;
        this.soortAdministratieveHandeling = soortAdministratieveHandeling;

    }

    /**
     * Retourneert ID van Bijhoudingsautorisatie \ Soort administratieve handeling.
     *
     * @return ID.
     */
    @Id
    @SequenceGenerator(name = "BIJHOUDINGSAUTORISATIESOORTADMINISTRATIEVEHANDELING", sequenceName = "AutAut.seq_BijhautorisatieSrtAdmHnd")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "BIJHOUDINGSAUTORISATIESOORTADMINISTRATIEVEHANDELING")
    @Access(value = AccessType.PROPERTY)
    public Integer getID() {
        return iD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ToegangBijhoudingsautorisatieModel getToegangBijhoudingsautorisatie() {
        return toegangBijhoudingsautorisatie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SoortAdministratieveHandelingAttribuut getSoortAdministratieveHandeling() {
        return soortAdministratieveHandeling;
    }

    /**
     * Setter is verplicht voor JPA, omdat de Id annotatie op de getter zit. We maken de functie private voor de
     * zekerheid.
     *
     * @param id Id
     */
    private void setID(final Integer id) {
        this.iD = id;
    }

    /**
     * Geeft alle groepen van de object met uitzondering van groepen die null zijn.
     *
     * @return Lijst met groepen.
     */
    public List<Groep> getGroepen() {
        final List<Groep> groepen = new ArrayList<>();
        return groepen;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van het object.
     */
    public List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (soortAdministratieveHandeling != null) {
            attributen.add(soortAdministratieveHandeling);
        }
        return attributen;
    }

}

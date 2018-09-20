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
import javax.persistence.AssociationOverride;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijRolAttribuut;
import nl.bzk.brp.model.basis.AbstractDynamischObject;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.logisch.autaut.ToegangBijhoudingsautorisatieBasis;

/**
 * Vastlegging van autorisaties welke Partijen gerechtigd zijn bijhoudingen in te dienen voor andere Partijen.
 *
 * De Toegang bijhoudingsautorisatie geeft invulling aan de bewerkersconstructie voor bijhouders.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractToegangBijhoudingsautorisatieModel extends AbstractDynamischObject implements ToegangBijhoudingsautorisatieBasis,
        ModelIdentificeerbaar<Integer>
{

    @Transient
    @JsonProperty
    private Integer iD;

    @Embedded
    @AssociationOverride(name = PartijRolAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "Geautoriseerde"))
    @JsonProperty
    private PartijRolAttribuut geautoriseerde;

    @Embedded
    @AssociationOverride(name = PartijAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "Ondertekenaar"))
    @JsonProperty
    private PartijAttribuut ondertekenaar;

    @Embedded
    @AssociationOverride(name = PartijAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "Transporteur"))
    @JsonProperty
    private PartijAttribuut transporteur;

    @Embedded
    @JsonProperty
    private ToegangBijhoudingsautorisatieStandaardGroepModel standaard;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractToegangBijhoudingsautorisatieModel() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param geautoriseerde geautoriseerde van Toegang bijhoudingsautorisatie.
     * @param ondertekenaar ondertekenaar van Toegang bijhoudingsautorisatie.
     * @param transporteur transporteur van Toegang bijhoudingsautorisatie.
     */
    public AbstractToegangBijhoudingsautorisatieModel(
        final PartijRolAttribuut geautoriseerde,
        final PartijAttribuut ondertekenaar,
        final PartijAttribuut transporteur)
    {
        this();
        this.geautoriseerde = geautoriseerde;
        this.ondertekenaar = ondertekenaar;
        this.transporteur = transporteur;

    }

    /**
     * Retourneert ID van Toegang bijhoudingsautorisatie.
     *
     * @return ID.
     */
    @Id
    @SequenceGenerator(name = "TOEGANGBIJHOUDINGSAUTORISATIE", sequenceName = "AutAut.seq_ToegangBijhautorisatie")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "TOEGANGBIJHOUDINGSAUTORISATIE")
    @Access(value = AccessType.PROPERTY)
    public Integer getID() {
        return iD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PartijRolAttribuut getGeautoriseerde() {
        return geautoriseerde;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PartijAttribuut getOndertekenaar() {
        return ondertekenaar;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PartijAttribuut getTransporteur() {
        return transporteur;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ToegangBijhoudingsautorisatieStandaardGroepModel getStandaard() {
        return standaard;
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
     * Zet Standaard van Toegang bijhoudingsautorisatie.
     *
     * @param standaard Standaard.
     */
    public void setStandaard(final ToegangBijhoudingsautorisatieStandaardGroepModel standaard) {
        this.standaard = standaard;
    }

    /**
     * Geeft alle groepen van de object met uitzondering van groepen die null zijn.
     *
     * @return Lijst met groepen.
     */
    public List<Groep> getGroepen() {
        final List<Groep> groepen = new ArrayList<>();
        if (standaard != null) {
            groepen.add(standaard);
        }
        return groepen;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van het object.
     */
    public List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (geautoriseerde != null) {
            attributen.add(geautoriseerde);
        }
        if (ondertekenaar != null) {
            attributen.add(ondertekenaar);
        }
        if (transporteur != null) {
            attributen.add(transporteur);
        }
        return attributen;
    }

}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AssociationOverride;
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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GegevenswaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementAttribuut;
import nl.bzk.brp.model.basis.AbstractDynamischObject;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.GegevenInTerugmeldingBasis;

/**
 * Een door de terugmelder aangeduid gegeven waarover (gerede) twijfel is.
 *
 * Bij onderzoek is het linken naar gegevens 'schaars': verreweg de meeste gegevens staan niet in onderzoek en hebben
 * ook niet in onderzoek gestaan. Daarom wordt er hier verwezen naar het gegeven vanuit het onderzoek en niet andersom
 * (vanuit het gegeven naar het onderzoek zoals bij verantwoording).
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractGegevenInTerugmeldingModel extends AbstractDynamischObject implements GegevenInTerugmeldingBasis,
        ModelIdentificeerbaar<Integer>
{

    @Transient
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Terugmelding")
    private TerugmeldingModel terugmelding;

    @Embedded
    @AssociationOverride(name = ElementAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "Element"))
    @JsonProperty
    private ElementAttribuut element;

    @Embedded
    @AttributeOverride(name = GegevenswaardeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "BetwijfeldeWaarde"))
    @JsonProperty
    private GegevenswaardeAttribuut betwijfeldeWaarde;

    @Embedded
    @AttributeOverride(name = GegevenswaardeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "VerwachteWaarde"))
    @JsonProperty
    private GegevenswaardeAttribuut verwachteWaarde;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractGegevenInTerugmeldingModel() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param terugmelding terugmelding van Gegeven in terugmelding.
     * @param element element van Gegeven in terugmelding.
     * @param betwijfeldeWaarde betwijfeldeWaarde van Gegeven in terugmelding.
     * @param verwachteWaarde verwachteWaarde van Gegeven in terugmelding.
     */
    public AbstractGegevenInTerugmeldingModel(
        final TerugmeldingModel terugmelding,
        final ElementAttribuut element,
        final GegevenswaardeAttribuut betwijfeldeWaarde,
        final GegevenswaardeAttribuut verwachteWaarde)
    {
        this();
        this.terugmelding = terugmelding;
        this.element = element;
        this.betwijfeldeWaarde = betwijfeldeWaarde;
        this.verwachteWaarde = verwachteWaarde;

    }

    /**
     * Retourneert ID van Gegeven in terugmelding.
     *
     * @return ID.
     */
    @Id
    @SequenceGenerator(name = "GEGEVENINTERUGMELDING", sequenceName = "Kern.seq_GegevenInTerugmelding")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "GEGEVENINTERUGMELDING")
    @Access(value = AccessType.PROPERTY)
    public Integer getID() {
        return iD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TerugmeldingModel getTerugmelding() {
        return terugmelding;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ElementAttribuut getElement() {
        return element;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GegevenswaardeAttribuut getBetwijfeldeWaarde() {
        return betwijfeldeWaarde;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GegevenswaardeAttribuut getVerwachteWaarde() {
        return verwachteWaarde;
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
        if (element != null) {
            attributen.add(element);
        }
        if (betwijfeldeWaarde != null) {
            attributen.add(betwijfeldeWaarde);
        }
        if (verwachteWaarde != null) {
            attributen.add(verwachteWaarde);
        }
        return attributen;
    }

}

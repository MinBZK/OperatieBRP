/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.kern;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
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
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.GegevenInTerugmeldingHisVolledigBasis;

/**
 * HisVolledig klasse voor Gegeven in terugmelding.
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigModelGenerator")
@MappedSuperclass
public abstract class AbstractGegevenInTerugmeldingHisVolledigImpl implements HisVolledigImpl, GegevenInTerugmeldingHisVolledigBasis,
        ElementIdentificeerbaar
{

    @Transient
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Terugmelding")
    @JsonBackReference
    private TerugmeldingHisVolledigImpl terugmelding;

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
     * Default contructor voor JPA.
     *
     */
    protected AbstractGegevenInTerugmeldingHisVolledigImpl() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param terugmelding terugmelding van Gegeven in terugmelding.
     * @param element element van Gegeven in terugmelding.
     * @param betwijfeldeWaarde betwijfeldeWaarde van Gegeven in terugmelding.
     * @param verwachteWaarde verwachteWaarde van Gegeven in terugmelding.
     */
    public AbstractGegevenInTerugmeldingHisVolledigImpl(
        final TerugmeldingHisVolledigImpl terugmelding,
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
     * Retourneert Terugmelding van Gegeven in terugmelding.
     *
     * @return Terugmelding.
     */
    public TerugmeldingHisVolledigImpl getTerugmelding() {
        return terugmelding;
    }

    /**
     * Retourneert Element van Gegeven in terugmelding.
     *
     * @return Element.
     */
    public ElementAttribuut getElement() {
        return element;
    }

    /**
     * Retourneert Betwijfelde waarde van Gegeven in terugmelding.
     *
     * @return Betwijfelde waarde.
     */
    public GegevenswaardeAttribuut getBetwijfeldeWaarde() {
        return betwijfeldeWaarde;
    }

    /**
     * Retourneert Verwachte waarde van Gegeven in terugmelding.
     *
     * @return Verwachte waarde.
     */
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
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.GEGEVENINTERUGMELDING;
    }

}

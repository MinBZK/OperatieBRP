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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.SleutelwaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.GegevenInOnderzoekHisVolledigBasis;

/**
 * HisVolledig klasse voor Gegeven in onderzoek.
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigModelGenerator")
@MappedSuperclass
public abstract class AbstractGegevenInOnderzoekHisVolledigImpl implements HisVolledigImpl, GegevenInOnderzoekHisVolledigBasis, ElementIdentificeerbaar {

    @Transient
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Onderzoek")
    @JsonBackReference
    private OnderzoekHisVolledigImpl onderzoek;

    @Embedded
    @AssociationOverride(name = ElementAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "Element"))
    @JsonProperty
    private ElementAttribuut element;

    @Embedded
    @AttributeOverride(name = SleutelwaardeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "ObjectSleutelGegeven"))
    @JsonProperty
    private SleutelwaardeAttribuut objectSleutelGegeven;

    @Embedded
    @AttributeOverride(name = SleutelwaardeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "VoorkomenSleutelGegeven"))
    @JsonProperty
    private SleutelwaardeAttribuut voorkomenSleutelGegeven;

    /**
     * Default contructor voor JPA.
     *
     */
    protected AbstractGegevenInOnderzoekHisVolledigImpl() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param onderzoek onderzoek van Gegeven in onderzoek.
     * @param element element van Gegeven in onderzoek.
     * @param objectSleutelGegeven objectSleutelGegeven van Gegeven in onderzoek.
     * @param voorkomenSleutelGegeven voorkomenSleutelGegeven van Gegeven in onderzoek.
     */
    public AbstractGegevenInOnderzoekHisVolledigImpl(
        final OnderzoekHisVolledigImpl onderzoek,
        final ElementAttribuut element,
        final SleutelwaardeAttribuut objectSleutelGegeven,
        final SleutelwaardeAttribuut voorkomenSleutelGegeven)
    {
        this();
        this.onderzoek = onderzoek;
        this.element = element;
        this.objectSleutelGegeven = objectSleutelGegeven;
        this.voorkomenSleutelGegeven = voorkomenSleutelGegeven;

    }

    /**
     * Retourneert ID van Gegeven in onderzoek.
     *
     * @return ID.
     */
    @Id
    @SequenceGenerator(name = "GEGEVENINONDERZOEK", sequenceName = "Kern.seq_GegevenInOnderzoek")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "GEGEVENINONDERZOEK")
    @Access(value = AccessType.PROPERTY)
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Onderzoek van Gegeven in onderzoek.
     *
     * @return Onderzoek.
     */
    public OnderzoekHisVolledigImpl getOnderzoek() {
        return onderzoek;
    }

    /**
     * Retourneert Element van Gegeven in onderzoek.
     *
     * @return Element.
     */
    public ElementAttribuut getElement() {
        return element;
    }

    /**
     * Retourneert Object sleutel gegeven van Gegeven in onderzoek.
     *
     * @return Object sleutel gegeven.
     */
    public SleutelwaardeAttribuut getObjectSleutelGegeven() {
        return objectSleutelGegeven;
    }

    /**
     * Retourneert Voorkomen sleutel gegeven van Gegeven in onderzoek.
     *
     * @return Voorkomen sleutel gegeven.
     */
    public SleutelwaardeAttribuut getVoorkomenSleutelGegeven() {
        return voorkomenSleutelGegeven;
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
        return ElementEnum.GEGEVENINONDERZOEK;
    }

}

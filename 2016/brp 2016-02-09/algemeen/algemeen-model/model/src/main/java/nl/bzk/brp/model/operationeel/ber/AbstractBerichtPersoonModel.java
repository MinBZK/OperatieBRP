/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.ber;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import nl.bzk.brp.model.basis.AbstractDynamischObject;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.logisch.ber.BerichtPersoonBasis;

/**
 * De opsomming van in een antwoord betrokken personen.
 *
 * Het betreft een constructie met als doel het genereren van de gewenste structuren in de XSD's.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractBerichtPersoonModel extends AbstractDynamischObject implements BerichtPersoonBasis, ModelIdentificeerbaar<Long> {

    @Transient
    @JsonProperty
    private Long iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Ber")
    private BerichtModel bericht;

    @JsonProperty
    @Column(name = "Pers")
    private Integer persoonId;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractBerichtPersoonModel() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param bericht bericht van Bericht \ Persoon.
     * @param persoonId persoonId van Bericht \ Persoon.
     */
    public AbstractBerichtPersoonModel(final BerichtModel bericht, final Integer persoonId) {
        this();
        this.bericht = bericht;
        this.persoonId = persoonId;

    }

    /**
     * Retourneert ID van Bericht \ Persoon.
     *
     * @return ID.
     */
    @Id
    @SequenceGenerator(name = "BERICHTPERSOON", sequenceName = "Ber.seq_BerPers")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "BERICHTPERSOON")
    @Access(value = AccessType.PROPERTY)
    public Long getID() {
        return iD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BerichtModel getBericht() {
        return bericht;
    }

    /**
     * Retourneert Persoon van Bericht \ Persoon.
     *
     * @return Persoon.
     */
    public Integer getPersoonId() {
        return persoonId;
    }

    /**
     * Setter is verplicht voor JPA, omdat de Id annotatie op de getter zit. We maken de functie private voor de
     * zekerheid.
     *
     * @param id Id
     */
    private void setID(final Long id) {
        this.iD = id;
    }

    /**
     * Zet Persoon van Bericht \ Persoon.
     *
     * @param persoonId Persoon.
     */
    public void setPersoonId(final Integer persoonId) {
        this.persoonId = persoonId;
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
        return attributen;
    }

}

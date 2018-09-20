/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.ber;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
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
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.ber.BerichtPersoonHisVolledigBasis;

/**
 * HisVolledig klasse voor Bericht \ Persoon.
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigModelGenerator")
@MappedSuperclass
public abstract class AbstractBerichtPersoonHisVolledigImpl implements HisVolledigImpl, BerichtPersoonHisVolledigBasis {

    @Transient
    @JsonProperty
    private Long iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Ber")
    @JsonBackReference
    private BerichtHisVolledigImpl bericht;

    @Column(name = "Pers")
    @JsonProperty
    private Integer persoonId;

    /**
     * Default contructor voor JPA.
     *
     */
    protected AbstractBerichtPersoonHisVolledigImpl() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param bericht bericht van Bericht \ Persoon.
     * @param persoonId persoonId van Bericht \ Persoon.
     */
    public AbstractBerichtPersoonHisVolledigImpl(final BerichtHisVolledigImpl bericht, final Integer persoonId) {
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
     * Retourneert Bericht van Bericht \ Persoon.
     *
     * @return Bericht.
     */
    public BerichtHisVolledigImpl getBericht() {
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

}

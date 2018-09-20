/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.kern;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;
import javax.persistence.Access;
import javax.persistence.AccessType;
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
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonCacheHisVolledigBasis;
import nl.bzk.brp.model.operationeel.kern.PersoonCacheStandaardGroepModel;

/**
 * HisVolledig klasse voor Persoon cache.
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigModelGenerator")
@MappedSuperclass
public abstract class AbstractPersoonCacheHisVolledigImpl implements HisVolledigImpl, PersoonCacheHisVolledigBasis, ElementIdentificeerbaar {

    @Transient
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Pers")
    @JsonProperty
    private PersoonHisVolledigImpl persoon;

    @Embedded
    private PersoonCacheStandaardGroepModel standaard;

    /**
     * Default contructor voor JPA.
     *
     */
    protected AbstractPersoonCacheHisVolledigImpl() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param persoon persoon van Persoon cache.
     */
    public AbstractPersoonCacheHisVolledigImpl(final PersoonHisVolledigImpl persoon) {
        this();
        this.persoon = persoon;

    }

    /**
     * Retourneert ID van Persoon cache.
     *
     * @return ID.
     */
    @Id
    @SequenceGenerator(name = "PERSOONCACHE", sequenceName = "Kern.seq_PersCache")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PERSOONCACHE")
    @Access(value = AccessType.PROPERTY)
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Persoon van Persoon cache.
     *
     * @return Persoon.
     */
    public PersoonHisVolledigImpl getPersoon() {
        return persoon;
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
     * Zet Standaard van Persoon cache.
     *
     * @param standaard Standaard.
     */
    public void setStandaard(final PersoonCacheStandaardGroepModel standaard) {
        this.standaard = standaard;
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.PERSOON_CACHE;
    }

}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.ist;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;
import javax.persistence.Access;
import javax.persistence.AccessType;
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
import nl.bzk.brp.model.hisvolledig.impl.kern.RelatieHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.ist.StapelRelatieHisVolledigBasis;

/**
 * HisVolledig klasse voor Stapel \ Relatie.
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigModelGenerator")
@MappedSuperclass
public abstract class AbstractStapelRelatieHisVolledigImpl implements HisVolledigImpl, StapelRelatieHisVolledigBasis {

    @Transient
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Stapel")
    @JsonBackReference
    private StapelHisVolledigImpl stapel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Relatie")
    @JsonProperty
    private RelatieHisVolledigImpl relatie;

    /**
     * Default contructor voor JPA.
     *
     */
    protected AbstractStapelRelatieHisVolledigImpl() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param stapel stapel van Stapel \ Relatie.
     * @param relatie relatie van Stapel \ Relatie.
     */
    public AbstractStapelRelatieHisVolledigImpl(final StapelHisVolledigImpl stapel, final RelatieHisVolledigImpl relatie) {
        this();
        this.stapel = stapel;
        this.relatie = relatie;

    }

    /**
     * Retourneert ID van Stapel \ Relatie.
     *
     * @return ID.
     */
    @Id
    @SequenceGenerator(name = "STAPELRELATIE", sequenceName = "IST.seq_StapelRelatie")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "STAPELRELATIE")
    @Access(value = AccessType.PROPERTY)
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Stapel van Stapel \ Relatie.
     *
     * @return Stapel.
     */
    public StapelHisVolledigImpl getStapel() {
        return stapel;
    }

    /**
     * Retourneert Relatie van Stapel \ Relatie.
     *
     * @return Relatie.
     */
    public RelatieHisVolledigImpl getRelatie() {
        return relatie;
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

}

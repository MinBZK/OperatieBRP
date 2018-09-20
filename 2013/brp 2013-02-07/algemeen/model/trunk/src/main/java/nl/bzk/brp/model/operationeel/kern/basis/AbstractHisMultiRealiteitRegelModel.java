/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern.basis;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import nl.bzk.brp.model.basis.AbstractFormeleHistorieEntiteit;
import nl.bzk.brp.model.operationeel.kern.MultiRealiteitRegelModel;


/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@MappedSuperclass
public abstract class AbstractHisMultiRealiteitRegelModel extends AbstractFormeleHistorieEntiteit {

    @Id
    @SequenceGenerator(name = "HIS_MULTIREALITEITREGEL", sequenceName = "Kern.seq_His_MultiRealiteitRegel")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_MULTIREALITEITREGEL")
    @JsonProperty
    private Integer                  iD;

    @ManyToOne
    @JoinColumn(name = "MultiRealiteitRegel")
    private MultiRealiteitRegelModel multiRealiteitRegel;

    /**
     * Default constructor t.b.v. JPA
     *
     */
    protected AbstractHisMultiRealiteitRegelModel() {
    }

    /**
     * Copy Constructor die op basis van een A-laag klasse en groep een C/D laag klasse construeert.
     *
     * @param multiRealiteitRegelModel instantie van A-laag klasse.
     */
    public AbstractHisMultiRealiteitRegelModel(final MultiRealiteitRegelModel multiRealiteitRegelModel) {
        this.multiRealiteitRegel = multiRealiteitRegelModel;

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie instantie van A-laag klasse.
     */
    public AbstractHisMultiRealiteitRegelModel(final AbstractHisMultiRealiteitRegelModel kopie) {
        super(kopie);
        multiRealiteitRegel = kopie.getMultiRealiteitRegel();

    }

    /**
     * Retourneert ID van His Multi-realiteit regel.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Multi-realiteit regel van His Multi-realiteit regel.
     *
     * @return Multi-realiteit regel.
     */
    public MultiRealiteitRegelModel getMultiRealiteitRegel() {
        return multiRealiteitRegel;
    }

}

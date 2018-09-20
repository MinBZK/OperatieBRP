/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.autaut.basis;

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

import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Uitgeslotene;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@MappedSuperclass
public abstract class AbstractHisUitgeslotene {

    @Id
    @SequenceGenerator(name = "HIS_UITGESLOTENE", sequenceName = "AutAut.seq_His_Uitgeslotene")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_UITGESLOTENE")
    @JsonProperty
    private Integer      iD;

    @ManyToOne
    @JoinColumn(name = "Uitgeslotene")
    @Fetch(value = FetchMode.JOIN)
    private Uitgeslotene uitgeslotene;

    /**
     * Default constructor t.b.v. JPA
     *
     */
    protected AbstractHisUitgeslotene() {
    }

    /**
     * Retourneert ID van His Uitgeslotene.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Uitgeslotene van His Uitgeslotene.
     *
     * @return Uitgeslotene.
     */
    public Uitgeslotene getUitgeslotene() {
        return uitgeslotene;
    }

}

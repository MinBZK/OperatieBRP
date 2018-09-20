/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.lev.basis;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import nl.bzk.brp.model.algemeen.attribuuttype.autaut.Populatiecriterium;
import nl.bzk.brp.model.algemeen.stamgegeven.lev.Abonnement;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@MappedSuperclass
public abstract class AbstractHisAbonnement {

    @Id
    @SequenceGenerator(name = "HIS_ABONNEMENT", sequenceName = "Lev.seq_His_Abonnement")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_ABONNEMENT")
    @JsonProperty
    private Integer            iD;

    @ManyToOne
    @JoinColumn(name = "Abonnement")
    @Fetch(value = FetchMode.JOIN)
    private Abonnement         abonnement;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Populatiecriterium"))
    @JsonProperty
    private Populatiecriterium populatiecriterium;

    /**
     * Default constructor t.b.v. JPA
     *
     */
    protected AbstractHisAbonnement() {
    }

    /**
     * Retourneert ID van His Abonnement.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Abonnement van His Abonnement.
     *
     * @return Abonnement.
     */
    public Abonnement getAbonnement() {
        return abonnement;
    }

    /**
     * Retourneert Populatiecriterium van His Abonnement.
     *
     * @return Populatiecriterium.
     */
    public Populatiecriterium getPopulatiecriterium() {
        return populatiecriterium;
    }

}

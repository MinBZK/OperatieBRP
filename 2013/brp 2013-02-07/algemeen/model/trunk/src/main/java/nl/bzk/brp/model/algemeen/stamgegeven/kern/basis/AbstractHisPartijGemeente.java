/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern.basis;

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

import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@MappedSuperclass
public abstract class AbstractHisPartijGemeente {

    @Id
    @SequenceGenerator(name = "HIS_PARTIJGEMEENTE", sequenceName = "Kern.seq_His_PartijGem")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_PARTIJGEMEENTE")
    @JsonProperty
    private Integer iD;

    @ManyToOne
    @JoinColumn(name = "Partij")
    @Fetch(value = FetchMode.JOIN)
    private Partij  partij;

    @ManyToOne
    @JoinColumn(name = "VoortzettendeGem")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Partij  voortzettendeGemeente;

    @ManyToOne
    @JoinColumn(name = "OnderdeelVan")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Partij  onderdeelVan;

    /**
     * Default constructor t.b.v. JPA
     *
     */
    protected AbstractHisPartijGemeente() {
    }

    /**
     * Retourneert ID van His Partij Gemeente.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Partij van His Partij Gemeente.
     *
     * @return Partij.
     */
    public Partij getPartij() {
        return partij;
    }

    /**
     * Retourneert Voortzettende gemeente van His Partij Gemeente.
     *
     * @return Voortzettende gemeente.
     */
    public Partij getVoortzettendeGemeente() {
        return voortzettendeGemeente;
    }

    /**
     * Retourneert Onderdeel van van His Partij Gemeente.
     *
     * @return Onderdeel van.
     */
    public Partij getOnderdeelVan() {
        return onderdeelVan;
    }

}

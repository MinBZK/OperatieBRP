/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern.basis;

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

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Sector;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@MappedSuperclass
public abstract class AbstractHisPartij {

    @Id
    @SequenceGenerator(name = "HIS_PARTIJ", sequenceName = "Kern.seq_His_Partij")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_PARTIJ")
    @JsonProperty
    private Integer iD;

    @ManyToOne
    @JoinColumn(name = "Partij")
    @Fetch(value = FetchMode.JOIN)
    private Partij  partij;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatEinde"))
    @JsonProperty
    private Datum   datumEinde;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatAanv"))
    @JsonProperty
    private Datum   datumAanvang;

    @ManyToOne
    @JoinColumn(name = "Sector")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Sector  sector;

    /**
     * Default constructor t.b.v. JPA
     *
     */
    protected AbstractHisPartij() {
    }

    /**
     * Retourneert ID van His Partij.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Partij van His Partij.
     *
     * @return Partij.
     */
    public Partij getPartij() {
        return partij;
    }

    /**
     * Retourneert Datum einde van His Partij.
     *
     * @return Datum einde.
     */
    public Datum getDatumEinde() {
        return datumEinde;
    }

    /**
     * Retourneert Datum aanvang van His Partij.
     *
     * @return Datum aanvang.
     */
    public Datum getDatumAanvang() {
        return datumAanvang;
    }

    /**
     * Retourneert Sector van His Partij.
     *
     * @return Sector.
     */
    public Sector getSector() {
        return sector;
    }

}

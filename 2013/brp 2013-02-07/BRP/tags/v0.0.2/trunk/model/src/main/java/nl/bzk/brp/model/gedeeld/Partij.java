/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.gedeeld;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Een partij in het operationeel model.
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = "Partij", schema = "Kern")
public class Partij {

    @Id
    @SequenceGenerator(name = "PARTIJ", sequenceName = "Kern.seq_Partij")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PARTIJ")
    private Integer id;

    @Column(name = "Gemcode")
    private String gemeentecode;

    /**
     * No-args constructor, vereist voor JPA.
     */
    public Partij() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }


    public String getGemeentecode() {
        return gemeentecode;
    }


    public void setGemeentecode(final String gemeentecode) {
        this.gemeentecode = gemeentecode;
    }

}

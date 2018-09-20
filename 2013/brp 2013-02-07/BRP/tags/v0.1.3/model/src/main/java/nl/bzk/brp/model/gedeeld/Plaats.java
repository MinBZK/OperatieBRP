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
import javax.validation.constraints.NotNull;

/**
 * Een stad of dorp in het operationeel model.
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = "Plaats", schema = "Kern")
public class Plaats  {

    @Id
    @SequenceGenerator(name = "PLAATS", sequenceName = "Kern.seq_Plaats")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PLAATS")
    private Integer id;

    @NotNull
    private String naam;

    @Column(name = "Wplcode")
    @NotNull
    private String woonplaatscode;

    /**
     * No-args constructor, vereist voor JPA.
     */
    public Plaats() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }


    public String getNaam() {
        return naam;
    }


    public void setNaam(final String naam) {
        this.naam = naam;
    }


    public String getWoonplaatscode() {
        return woonplaatscode;
    }


    public void setWoonplaatscode(final String woonplaatscode) {
        this.woonplaatscode = woonplaatscode;
    }

}

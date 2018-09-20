/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.gedeeld;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Een land.
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = "Land", schema = "Kern")
public class Land {

    @Id
    private Integer id;

    @NotNull
    private String naam;

    private String iso31661Alpha2;

    @NotNull
    private String landcode;

    /**
     * No-args constructor, vereist voor JPA.
     */
    public Land() {
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


    public String getIso31661Alpha2() {
        return iso31661Alpha2;
    }


    public void setIso31661Alpha2(final String iso31661Alpha2) {
        this.iso31661Alpha2 = iso31661Alpha2;
    }


    public String getLandcode() {
        return landcode;
    }


    public void setLandcode(final String landcode) {
        this.landcode = landcode;
    }

}

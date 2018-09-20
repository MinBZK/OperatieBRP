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
import javax.persistence.Id;
import javax.persistence.Table;

import nl.bzk.brp.model.basis.AbstractBestaansperiodeEntiteit;

/**
 * Database entiteit voor Kern.Nationaliteit.
 */
@Entity
@Table(schema = "kern", name = "nation")
@Access(AccessType.FIELD)
public class Nationaliteit extends AbstractBestaansperiodeEntiteit {

    @Id
    private Integer id;
    @Column(name = "naam")
    private String naam;
    @Column(name = "nationcode")
    private String code;

    public Integer getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public String getCode() {
        return code;
    }

    public void setNaam(final String naam) {
        this.naam = naam;
    }

    public void setCode(final String code) {
        this.code = code;
    }
}

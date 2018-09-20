/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.domein.brm;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Regel.
 */
@Entity
@Table(name = "Regel", schema = "BRM")
@Access(AccessType.FIELD)
public class Regel {

    @Id
    private Long       id;

    @Column(name = "Srt")
    private SoortRegel soort;

    @Column(name = "Code")
    private String     code;

    /**
     * No-arg constructor voor JPA.
     */
    protected Regel() {
    }

    /**
     * @return primaire sleutel
     */
    public Long getId() {
        return id;
    }

    public SoortRegel getSoort() {
        return soort;
    }

    public String getCode() {
        return code;
    }

}

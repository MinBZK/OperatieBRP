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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import nl.bzk.brp.bevraging.domein.ber.SoortBericht;


/**
 * RegelImplementatie.
 */
@Entity
@Table(name = "Regelimplementatie", schema = "BRM")
@Access(AccessType.FIELD)
public class RegelImplementatie {

    @Id
    private Long         id;

    @ManyToOne
    @JoinColumn(name = "Regel")
    private Regel        regel;

    @Column(name = "SrtBer")
    private SoortBericht soortBericht;

    /**
     * No-arg constructor voor JPA.
     */
    protected RegelImplementatie() {
    }

    /**
     * @return primaire sleutel
     */
    public Long getId() {
        return id;
    }

    public Regel getRegel() {
        return regel;
    }

    public SoortBericht getSoortBericht() {
        return soortBericht;
    }

}

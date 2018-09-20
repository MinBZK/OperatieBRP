/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein.ber;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import nl.bzk.brp.bevraging.domein.lev.Levering;


/**
 * Representeert een bericht van een levering.
 */
@Entity
@Table(name = "levber", schema = "lev")
@Access(AccessType.FIELD)
public class LeveringBericht implements Serializable {

    @SequenceGenerator(name = "LEVBER_SEQUENCE_GENERATOR", sequenceName = "lev.seq_levber")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LEVBER_SEQUENCE_GENERATOR")
    private Long id;

    public Long getId() {
        return id;
    }

    @Column(name = "GebaseerdOp")
    private Long     bericht;

    @ManyToOne
    @JoinColumn(name = "lev")
    private Levering levering;

    public Long getBericht() {
        return bericht;
    }

    public void setBericht(final Long bericht) {
        this.bericht = bericht;
    }

    public Levering getLevering() {
        return levering;
    }

    public void setLevering(final Levering levering) {
        this.levering = levering;
    }

}

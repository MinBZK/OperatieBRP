/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * The persistent class for the perscache database table.
 *
 */
@Entity
@Table(name = "vrijberpartij", schema = "beh", uniqueConstraints = @UniqueConstraint(columnNames = {"id"}))
public class VrijBerichtPartij implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "vrijberpartij_id_generator", sequenceName = "beh.seq_vrijberpartij", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vrijberpartij_id_generator")
    @Column(nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "vrijber", nullable = false)
    private VrijBericht vrijBericht;

    @OneToOne
    @JoinColumn(name = "partij", nullable = false)
    private Partij partij;

    /**
     * JPA default constructor.
     */
    protected VrijBerichtPartij() {}

    /**
     * Constructor.
     * @param vrijBericht het vrij bericht
     * @param partij de partij voor/van wie het vrije bericht bedoeld/afkomstig is
     */
    public VrijBerichtPartij(final VrijBericht vrijBericht, final Partij partij) {
        this.vrijBericht = vrijBericht;
        this.partij = partij;
    }

    public Integer getId() {
        return id;
    }

    public VrijBericht getVrijBericht() {
        return vrijBericht;
    }

    public Partij getPartij() {
        return partij;
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;

/**
 * The persistent class for the Lo3 Aanduiding Ouder database table.
 * 
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "lo3aandouder", schema = "verconv", uniqueConstraints = @UniqueConstraint(columnNames = {"ouder", "srt" }))
@SuppressWarnings("checkstyle:designforextension")
public class Lo3AanduidingOuder implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "lo3aandouder_id_generator", sequenceName = "verconv.seq_lo3aandouder", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lo3aandouder_id_generator")
    @Column(nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "ouder", nullable = false)
    private Betrokkenheid ouder;

    @Column(name = "srt", nullable = false)
    private short aanduidingOuder;

    /**
     * JPA default constructor.
     */
    protected Lo3AanduidingOuder() {
    }

    /**
     * Maak een nieuwe aanduiding ouder.
     *
     * @param aanduidingOuder
     *            {@link AanduidingOuder}
     * @param betrokkenOuder
     *            {@link Betrokkenheid}
     */
    public Lo3AanduidingOuder(final AanduidingOuder aanduidingOuder, final Betrokkenheid betrokkenOuder) {
        setOuderAanduiding(aanduidingOuder);
        setOuder(betrokkenOuder);
    }

    /**
     * Geef de waarde van id.
     *
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Zet de waarde van id.
     *
     * @param id
     *            id
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van AanduidingOuder.
     *
     * @return AanduidingOuder
     */
    public AanduidingOuder getOuderAanduiding() {
        return AanduidingOuder.parseId(aanduidingOuder);
    }

    /**
     * Zet de waarde van OuderAanduiding.
     * 
     * @param aanduiding
     *            {@link AanduidingOuder}
     */
    public void setOuderAanduiding(final AanduidingOuder aanduiding) {
        ValidationUtils.controleerOpNullWaarden("Aanduiding ouder mag niet null zijn", aanduiding);
        this.aanduidingOuder = aanduiding.getId();
    }

    /**
     * return de betrokkenheid ouder.
     * 
     * @return Betrokkenheid
     */
    public Betrokkenheid getOuder() {
        return ouder;
    }

    /**
     * Zet de betrokkenheid van de ouder.
     * 
     * @param ouder
     *            de betrokkenheid van de ouder waar deze aanduiding voor is
     */
    public void setOuder(final Betrokkenheid ouder) {
        ValidationUtils.controleerOpNullWaarden("Betrokkenheid mag niet null zijn", ouder);
        this.ouder = ouder;
    }
}

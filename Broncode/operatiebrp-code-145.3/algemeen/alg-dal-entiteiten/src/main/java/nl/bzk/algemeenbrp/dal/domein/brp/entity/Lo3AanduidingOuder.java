/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

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
import nl.bzk.algemeenbrp.dal.domein.brp.enums.AanduidingOuder;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;

/**
 * The persistent class for the Lo3 Aanduiding Ouder database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "lo3aandouder", schema = "verconv", uniqueConstraints = @UniqueConstraint(columnNames = {"ouder", "srt"}))
public class Lo3AanduidingOuder implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "lo3aandouder_id_generator", sequenceName = "verconv.seq_lo3aandouder", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lo3aandouder_id_generator")
    @Column(nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "ouder", nullable = false)
    private Betrokkenheid ouder;

    @Column(name = "srt", nullable = false)
    private int aanduidingOuder;

    /**
     * JPA default constructor.
     */
    protected Lo3AanduidingOuder() {}

    /**
     * Maak een nieuwe aanduiding ouder.
     *
     * @param aanduidingOuder {@link AanduidingOuder}
     * @param betrokkenOuder {@link Betrokkenheid}
     */
    public Lo3AanduidingOuder(final AanduidingOuder aanduidingOuder, final Betrokkenheid betrokkenOuder) {
        setOuderAanduiding(aanduidingOuder);
        setOuder(betrokkenOuder);
    }

    /**
     * Geef de waarde van id van Lo3AanduidingOuder.
     *
     * @return de waarde van id van Lo3AanduidingOuder
     */
    public Long getId() {
        return id;
    }

    /**
     * Zet de waarden voor id van Lo3AanduidingOuder.
     *
     * @param id de nieuwe waarde voor id van Lo3AanduidingOuder
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van ouder aanduiding van Lo3AanduidingOuder.
     *
     * @return de waarde van ouder aanduiding van Lo3AanduidingOuder
     */
    public AanduidingOuder getOuderAanduiding() {
        return AanduidingOuder.parseId(aanduidingOuder);
    }

    /**
     * Zet de waarden voor ouder aanduiding van Lo3AanduidingOuder.
     *
     * @param aanduiding de nieuwe waarde voor ouder aanduiding van Lo3AanduidingOuder
     */
    public void setOuderAanduiding(final AanduidingOuder aanduiding) {
        ValidationUtils.controleerOpNullWaarden("Aanduiding ouder mag niet null zijn", aanduiding);
        aanduidingOuder = aanduiding.getId();
    }

    /**
     * Geef de waarde van ouder van Lo3AanduidingOuder.
     *
     * @return de waarde van ouder van Lo3AanduidingOuder
     */
    public Betrokkenheid getOuder() {
        return ouder;
    }

    /**
     * Zet de waarden voor ouder van Lo3AanduidingOuder.
     *
     * @param ouder de nieuwe waarde voor ouder van Lo3AanduidingOuder
     */
    public void setOuder(final Betrokkenheid ouder) {
        ValidationUtils.controleerOpNullWaarden("Betrokkenheid mag niet null zijn", ouder);
        this.ouder = ouder;
    }
}

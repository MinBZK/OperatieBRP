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
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;

/**
 * The persistent class for the his_persverificatie database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "his_persverificatie", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"persverificatie", "tsreg" }))
@SuppressWarnings("checkstyle:designforextension")
public class PersoonVerificatieHistorie extends AbstractFormeleHistorie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_persverificatie_id_generator", sequenceName = "kern.seq_his_persverificatie", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_persverificatie_id_generator")
    @Column(nullable = false)
    private Long id;

    @Column(name = "dat", nullable = false)
    private int datum;

    // bi-directional many-to-one association to PersoonVerificatie
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "persverificatie", nullable = false)
    private PersoonVerificatie persoonVerificatie;

    /**
     * JPA default constructor.
     */
    protected PersoonVerificatieHistorie() {
    }

    /**
     * Maak een nieuwe persoon verificatie historie.
     *
     * @param persoonVerificatie
     *            persoon verificatie
     * @param datum
     *            datum
     */
    public PersoonVerificatieHistorie(final PersoonVerificatie persoonVerificatie, final int datum) {
        setPersoonVerificatie(persoonVerificatie);
        this.datum = datum;
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
     * Geef de waarde van datum.
     *
     * @return datum
     */
    public Integer getDatum() {
        return datum;
    }

    /**
     * Zet de waarde van datum.
     *
     * @param datum
     *            datum
     */
    public void setDatum(final int datum) {
        this.datum = datum;
    }

    /**
     * Geef de waarde van persoon verificatie.
     *
     * @return persoon verificatie
     */
    public PersoonVerificatie getPersoonVerificatie() {
        return persoonVerificatie;
    }

    /**
     * Zet de waarde van persoon verificatie.
     *
     * @param persoonVerificatie
     *            persoon verificatie
     */
    public void setPersoonVerificatie(final PersoonVerificatie persoonVerificatie) {
        ValidationUtils.controleerOpNullWaarden("persoonVerificatie mag niet null zijn", persoonVerificatie);
        this.persoonVerificatie = persoonVerificatie;
    }
}

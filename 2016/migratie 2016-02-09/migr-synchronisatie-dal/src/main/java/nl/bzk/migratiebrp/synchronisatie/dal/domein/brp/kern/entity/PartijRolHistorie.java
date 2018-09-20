/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

import java.io.Serializable;
import java.sql.Timestamp;
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
 * The persistent class for the his_partijrol database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "his_partijrol", schema = "kern", uniqueConstraints = {@UniqueConstraint(columnNames = {"partijrol", "tsreg" }) })
@SuppressWarnings("checkstyle:designforextension")
public class PartijRolHistorie extends AbstractFormeleHistorie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_partijrol_id_generator", sequenceName = "kern.seq_his_partijrol", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_partijrol_id_generator")
    @Column(nullable = false)
    /**
     * Het veld zou Integer moeten zijn maar is Long i.v.m. de link naar GegevenInOnderzoek.
     */
    private Long id;

    @Column(name = "datingang", nullable = false)
    private int datumIngang;

    @Column(name = "dateinde")
    private Integer datumEinde;

    // bi-directional many-to-one association to Partij
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "partijrol", nullable = false)
    private PartijRol partijRol;

    /**
     * JPA default constructor.
     */
    protected PartijRolHistorie() {
    }

    /**
     * Maak een nieuwe partij historie.
     *
     * @param partijRol
     *            partij rol
     * @param datumTijdRegistratie
     *            datumTijdRegistratie
     * @param datumIngang
     *            datum ingang
     */
    public PartijRolHistorie(
        final PartijRol partijRol,
        final Timestamp datumTijdRegistratie,
        final int datumIngang)
    {
        setPartijRol(partijRol);
        setDatumTijdRegistratie(datumTijdRegistratie);
        setDatumIngang(datumIngang);
    }

    /**
     * Geef de Id waarde voor de entiteit. Intern is de Id een Long voor integratie met GegevenInOnderzoek. De waarde
     * wordt geconverteerd naar een Integer.
     *
     * @return de Id waarde.
     */
    public Integer getId() {
        return convertLongNaarInteger(id);
    }

    /**
     * Zet de Id waarde voor de entiteit. Intern wordt de Id waarde geconverteert naar een Long voor integratie met
     * GegevenInOnderzoek.
     *
     * @param id
     *            de Id waarde.
     */
    public void setId(final Integer id) {
        this.id = convertIntegerNaarLong(id);
    }

    /**
     * Geef de waarde van datum ingang.
     *
     * @return datum ingang
     */
    public int getDatumIngang() {
        return datumIngang;
    }

    /**
     * Zet de waarde van datum ingang.
     *
     * @param datumIngang
     *            datum ingang
     */
    public void setDatumIngang(final int datumIngang) {
        this.datumIngang = datumIngang;
    }

    /**
     * Geef de waarde van datum einde.
     *
     * @return datum einde
     */
    public Integer getDatumEinde() {
        return datumEinde;
    }

    /**
     * Zet de waarde van datum einde.
     *
     * @param datumEinde
     *            datum einde
     */
    public void setDatumEinde(final Integer datumEinde) {
        this.datumEinde = datumEinde;
    }

    /**
     * Geef de waarde van partijRol.
     *
     * @return partijRol
     */
    public PartijRol getPartijRol() {
        return partijRol;
    }

    /**
     * Zet de waarde van partij.
     *
     * @param partijRol
     *            partijRol
     */
    public void setPartijRol(final PartijRol partijRol) {
        ValidationUtils.controleerOpNullWaarden("partijrol mag niet null zijn", partijRol);
        this.partijRol = partijRol;
    }
}

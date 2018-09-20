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
 * The persistent class for the his_partijbijhouding database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "his_partijbijhouding", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"partij", "tsreg" }))
@SuppressWarnings("checkstyle:designforextension")
public class PartijBijhoudingHistorie extends AbstractFormeleHistorie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_partijbijhouding_id_generator", sequenceName = "kern.seq_his_partijbijhouding", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_partijbijhouding_id_generator")
    @Column(nullable = false)
    /**
     * Het veld zou Integer moeten zijn maar is Long i.v.m. de link naar GegevenInOnderzoek.
     */
    private Long id;

    // bi-directional many-to-one association to Partij
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "partij", nullable = false, updatable = false)
    private Partij partij;

    @Column(name = "indautofiat", nullable = false)
    private boolean indicatieAutomatischFiatteren;

    @Column(name = "datovergangnaarbrp", nullable = false)
    private int datumOvergangNaarBrp;

    /**
     * JPA default constructor.
     */
    protected PartijBijhoudingHistorie() {
    }

    /**
     * Maak een nieuwe partij bijhouding historie.
     *
     * @param partij
     *            partij, mag niet null zijn
     * @param datumTijdRegistratie
     *            datum tijd registratie, mag niet null zijn
     * @param indicatieAutomatischFiatteren
     *            indicatieAutomatischFiatteren
     * @param datumOvergangNaarBrp
     *            datumOvergangNaarBrp
     */
    public PartijBijhoudingHistorie(
        final Partij partij,
        final Timestamp datumTijdRegistratie,
        final boolean indicatieAutomatischFiatteren,
        final int datumOvergangNaarBrp)
    {
        setPartij(partij);
        setDatumTijdRegistratie(datumTijdRegistratie);
        setIndicatieAutomatischFiatteren(indicatieAutomatischFiatteren);
        setDatumOvergangNaarBrp(datumOvergangNaarBrp);
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
     * Geef de waarde van partij.
     *
     * @return partij
     */
    public Partij getPartij() {
        return partij;
    }

    /**
     * Zet de waarde van partij.
     *
     * @param partij
     *            partij
     */
    public void setPartij(final Partij partij) {
        ValidationUtils.controleerOpNullWaarden("partij mag niet null zijn", partij);
        this.partij = partij;
    }

    /**
     * Geef de waarde van indicatieAutomatischFiatteren.
     * 
     * @return indicatieAutomatischFiatteren
     */
    public boolean getIndicatieAutomatischFiatteren() {
        return indicatieAutomatischFiatteren;
    }

    /**
     * Zet de waarde van indicatieAutomatischFiatteren.
     *
     * @param indicatieAutomatischFiatteren
     *            indicatieAutomatischFiatteren
     */
    public void setIndicatieAutomatischFiatteren(final boolean indicatieAutomatischFiatteren) {
        this.indicatieAutomatischFiatteren = indicatieAutomatischFiatteren;
    }

    /**
     * Geef de waarde van datumOvergangNaarBrp.
     *
     * @return datumOvergangNaarBrp
     */
    public int getDatumOvergangNaarBrp() {
        return datumOvergangNaarBrp;
    }

    /**
     * Zet de waarde van datumOvergangNaarBrp.
     *
     * @param datumOvergangNaarBrp
     *            datumOvergangNaarBrp
     */
    public void setDatumOvergangNaarBrp(final int datumOvergangNaarBrp) {
        this.datumOvergangNaarBrp = datumOvergangNaarBrp;
    }
}

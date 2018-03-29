/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.io.Serializable;
import java.sql.Timestamp;
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
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;

/**
 * The persistent class for the his_partijbijhouding database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "his_partijbijhouding", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"partij", "tsreg"}))
public class PartijBijhoudingHistorie extends AbstractFormeleHistorieZonderVerantwoording implements Serializable {
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partij", nullable = false, updatable = false)
    private Partij partij;

    @Column(name = "indautofiat", nullable = false)
    private boolean indicatieAutomatischFiatteren;

    /**
     * JPA default constructor.
     */
    protected PartijBijhoudingHistorie() {}

    /**
     * Maak een nieuwe partij bijhouding historie.
     *
     * @param partij partij, mag niet null zijn
     * @param datumTijdRegistratie datum tijd registratie, mag niet null zijn
     * @param indicatieAutomatischFiatteren indicatieAutomatischFiatteren
     */
    public PartijBijhoudingHistorie(final Partij partij, final Timestamp datumTijdRegistratie, final boolean indicatieAutomatischFiatteren) {
        setPartij(partij);
        setDatumTijdRegistratie(datumTijdRegistratie);
        setIndicatieAutomatischFiatteren(indicatieAutomatischFiatteren);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.algemeen.dal.domein.brp.kern.entity.DeltaEntiteit#getId()
     */
    @Override
    public Integer getId() {
        return convertLongNaarInteger(id);
    }

    /**
     * Zet de waarden voor id van PartijBijhoudingHistorie.
     *
     * @param id de nieuwe waarde voor id van PartijBijhoudingHistorie
     */
    public void setId(final Integer id) {
        this.id = convertIntegerNaarLong(id);
    }

    /**
     * Geef de waarde van partij van PartijBijhoudingHistorie.
     *
     * @return de waarde van partij van PartijBijhoudingHistorie
     */
    public Partij getPartij() {
        return partij;
    }

    /**
     * Zet de waarden voor partij van PartijBijhoudingHistorie.
     *
     * @param partij de nieuwe waarde voor partij van PartijBijhoudingHistorie
     */
    public void setPartij(final Partij partij) {
        ValidationUtils.controleerOpNullWaarden("partij mag niet null zijn", partij);
        this.partij = partij;
    }

    /**
     * Geef de waarde van indicatie automatisch fiatteren van PartijBijhoudingHistorie.
     *
     * @return de waarde van indicatie automatisch fiatteren van PartijBijhoudingHistorie
     */
    public boolean getIndicatieAutomatischFiatteren() {
        return indicatieAutomatischFiatteren;
    }

    /**
     * Zet de waarden voor indicatie automatisch fiatteren van PartijBijhoudingHistorie.
     *
     * @param indicatieAutomatischFiatteren de nieuwe waarde voor indicatie automatisch fiatteren
     *        van PartijBijhoudingHistorie
     */
    public void setIndicatieAutomatischFiatteren(final boolean indicatieAutomatischFiatteren) {
        this.indicatieAutomatischFiatteren = indicatieAutomatischFiatteren;
    }

}

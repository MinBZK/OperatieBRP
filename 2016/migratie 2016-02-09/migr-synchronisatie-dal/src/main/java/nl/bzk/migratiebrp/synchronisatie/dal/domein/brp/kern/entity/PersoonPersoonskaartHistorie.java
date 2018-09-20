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
 * The persistent class for the his_perspk database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "his_perspk", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"pers", "tsreg" }))
@SuppressWarnings("checkstyle:designforextension")
public class PersoonPersoonskaartHistorie extends AbstractFormeleHistorie implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String PERSOON_MAG_NIET_NULL_ZIJN = "persoon mag niet null zijn";

    @Id
    @SequenceGenerator(name = "his_perspk_id_generator", sequenceName = "kern.seq_his_perspk", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_perspk_id_generator")
    @Column(nullable = false)
    /**
     * Het veld zou Integer moeten zijn maar is Long i.v.m. de link naar GegevenInOnderzoek.
     */
    private Long id;

    @Column(name = "indpkvollediggeconv", nullable = false)
    private boolean indicatiePersoonskaartVolledigGeconverteerd;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "gempk")
    private Partij partij;

    // bi-directional many-to-one association to Persoon
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "pers", nullable = false)
    private Persoon persoon;

    /**
     * JPA default constructor.
     */
    protected PersoonPersoonskaartHistorie() {
    }

    /**
     * Maak een nieuwe persoon persoonskaart historie.
     *
     * @param persoon
     *            persoon
     * @param indicatiePersoonskaartVolledigGeconverteerd
     *            indicatie persoonskaart volledig geconverteerd
     */
    public PersoonPersoonskaartHistorie(final Persoon persoon, final boolean indicatiePersoonskaartVolledigGeconverteerd) {
        ValidationUtils.controleerOpNullWaarden(PERSOON_MAG_NIET_NULL_ZIJN, persoon);
        this.persoon = persoon;
        this.indicatiePersoonskaartVolledigGeconverteerd = indicatiePersoonskaartVolledigGeconverteerd;
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
     * Geef de waarde van indicatie persoonskaart volledig geconverteerd.
     *
     * @return indicatie persoonskaart volledig geconverteerd
     */
    public boolean getIndicatiePersoonskaartVolledigGeconverteerd() {
        return indicatiePersoonskaartVolledigGeconverteerd;
    }

    /**
     * Zet de waarde van indicatie persoonskaart volledig geconverteerd.
     *
     * @param indicatiePersoonskaartVolledigGeconverteerd
     *            indicatie persoonskaart volledig geconverteerd
     */
    public void setIndicatiePersoonskaartVolledigGeconverteerd(final boolean indicatiePersoonskaartVolledigGeconverteerd) {
        this.indicatiePersoonskaartVolledigGeconverteerd = indicatiePersoonskaartVolledigGeconverteerd;
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
        this.partij = partij;
    }

    /**
     * Geef de waarde van persoon.
     *
     * @return persoon
     */
    public Persoon getPersoon() {
        return persoon;
    }

    /**
     * Zet de waarde van persoon.
     *
     * @param persoon
     *            persoon
     */
    public void setPersoon(final Persoon persoon) {
        ValidationUtils.controleerOpNullWaarden(PERSOON_MAG_NIET_NULL_ZIJN, persoon);
        this.persoon = persoon;
    }
}

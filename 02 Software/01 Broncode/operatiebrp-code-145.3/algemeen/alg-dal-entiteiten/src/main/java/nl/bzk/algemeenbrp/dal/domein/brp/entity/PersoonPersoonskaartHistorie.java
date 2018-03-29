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
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;

/**
 * The persistent class for the his_perspk database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "his_perspk", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"pers", "tsreg"}))
public class PersoonPersoonskaartHistorie extends AbstractFormeleHistorie implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String PERSOON_MAG_NIET_NULL_ZIJN = "persoon mag niet null zijn";

    @Id
    @SequenceGenerator(name = "his_perspk_id_generator", sequenceName = "kern.seq_his_perspk", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_perspk_id_generator")
    @Column(nullable = false)
    private Long id;

    @Column(name = "indpkvollediggeconv", nullable = false)
    private boolean indicatiePersoonskaartVolledigGeconverteerd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gempk")
    private Partij gemeentePersoonskaart;

    // bi-directional many-to-one association to Persoon
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "pers", nullable = false)
    private Persoon persoon;

    /**
     * JPA default constructor.
     */
    protected PersoonPersoonskaartHistorie() {}

    /**
     * Maak een nieuwe persoon persoonskaart historie.
     *
     * @param persoon persoon
     * @param indicatiePersoonskaartVolledigGeconverteerd indicatie persoonskaart volledig
     *        geconverteerd
     */
    public PersoonPersoonskaartHistorie(final Persoon persoon, final boolean indicatiePersoonskaartVolledigGeconverteerd) {
        ValidationUtils.controleerOpNullWaarden(PERSOON_MAG_NIET_NULL_ZIJN, persoon);
        this.persoon = persoon;
        this.indicatiePersoonskaartVolledigGeconverteerd = indicatiePersoonskaartVolledigGeconverteerd;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.algemeen.dal.domein.brp.kern.entity.DeltaEntiteit#getId()
     */
    @Override
    public Long getId() {
        return id;
    }

    /**
     * Zet de waarden voor id van PersoonPersoonskaartHistorie.
     *
     * @param id de nieuwe waarde voor id van PersoonPersoonskaartHistorie
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van indicatie persoonskaart volledig geconverteerd van
     * PersoonPersoonskaartHistorie.
     *
     * @return de waarde van indicatie persoonskaart volledig geconverteerd van
     *         PersoonPersoonskaartHistorie
     */
    public boolean getIndicatiePersoonskaartVolledigGeconverteerd() {
        return indicatiePersoonskaartVolledigGeconverteerd;
    }

    /**
     * Zet de waarden voor indicatie persoonskaart volledig geconverteerd van
     * PersoonPersoonskaartHistorie.
     *
     * @param indicatiePersoonskaartVolledigGeconverteerd de nieuwe waarde voor indicatie
     *        persoonskaart volledig geconverteerd van PersoonPersoonskaartHistorie
     */
    public void setIndicatiePersoonskaartVolledigGeconverteerd(final boolean indicatiePersoonskaartVolledigGeconverteerd) {
        this.indicatiePersoonskaartVolledigGeconverteerd = indicatiePersoonskaartVolledigGeconverteerd;
    }

    /**
     * Geef de waarde van partij van PersoonPersoonskaartHistorie.
     *
     * @return de waarde van partij van PersoonPersoonskaartHistorie
     */
    public Partij getPartij() {
        return gemeentePersoonskaart;
    }

    /**
     * Zet de waarden voor partij van PersoonPersoonskaartHistorie.
     *
     * @param partij de nieuwe waarde voor partij van PersoonPersoonskaartHistorie
     */
    public void setPartij(final Partij partij) {
        gemeentePersoonskaart = partij;
    }

    /**
     * Geef de waarde van persoon van PersoonPersoonskaartHistorie.
     *
     * @return de waarde van persoon van PersoonPersoonskaartHistorie
     */
    public Persoon getPersoon() {
        return persoon;
    }

    /**
     * Zet de waarden voor persoon van PersoonPersoonskaartHistorie.
     *
     * @param persoon de nieuwe waarde voor persoon van PersoonPersoonskaartHistorie
     */
    public void setPersoon(final Persoon persoon) {
        ValidationUtils.controleerOpNullWaarden(PERSOON_MAG_NIET_NULL_ZIJN, persoon);
        this.persoon = persoon;
    }
}

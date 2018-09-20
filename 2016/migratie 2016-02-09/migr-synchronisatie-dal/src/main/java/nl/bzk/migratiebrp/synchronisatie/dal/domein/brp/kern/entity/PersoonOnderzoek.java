/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;

/**
 * The persistent class for the personderzoek database table.
 * 
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "personderzoek", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"onderzoek", "pers" }))
@SuppressWarnings("checkstyle:designforextension")
public class PersoonOnderzoek extends AbstractDeltaEntiteit implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "personderzoek_id_generator", sequenceName = "kern.seq_personderzoek", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "personderzoek_id_generator")
    @Column(nullable = false)
    /**
     * Het veld zou Integer moeten zijn maar is Long i.v.m. de link naar GegevenInOnderzoek.
     */
    private Long id;

    // bi-directional many-to-one association to PersoonOnderzoekHistorie
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "persoonOnderzoek", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE },
            orphanRemoval = true)
    private Set<PersoonOnderzoekHistorie> persoonOnderzoekHistorieSet = new LinkedHashSet<>(0);

    /**
     * Onderzoeken moeten EAGER worden geladen ivm uni-directionele koppeling van {@link GegevenInOnderzoek} naar
     * {@link DeltaEntiteit} door {@link GegevenInOnderzoekListener}. Dit wordt gedaan ivm @Any in de
     * {@link GegevenInOnderzoek}.
     */
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "onderzoek", nullable = false)
    private Onderzoek onderzoek;

    // bi-directional many-to-one association to Persoon
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "pers", nullable = false)
    private Persoon persoon;

    @Column(name = "rol")
    private Short soortPersoonOnderzoekId;

    /**
     * JPA default constructor.
     */
    protected PersoonOnderzoek() {
    }

    /**
     * Maak een nieuwe persoon onderzoek.
     *
     * @param persoon
     *            persoon
     * @param onderzoek
     *            onderzoek
     */
    public PersoonOnderzoek(final Persoon persoon, final Onderzoek onderzoek) {
        setPersoon(persoon);
        setOnderzoek(onderzoek);
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
     * Geef de waarde van persoon onderzoek historie set.
     *
     * @return persoon onderzoek historie set
     */
    public Set<PersoonOnderzoekHistorie> getPersoonOnderzoekHistorieSet() {
        return persoonOnderzoekHistorieSet;
    }

    /**
     * Toevoegen van een persoon onderzoek historie.
     *
     * @param persoonOnderzoekHistorie
     *            persoon onderzoek historie
     */
    public void addPersoonOnderzoekHistorie(final PersoonOnderzoekHistorie persoonOnderzoekHistorie) {
        persoonOnderzoekHistorie.setPersoonOnderzoek(this);
        persoonOnderzoekHistorieSet.add(persoonOnderzoekHistorie);
    }

    /**
     * Geef de waarde van onderzoek.
     *
     * @return onderzoek
     */
    public Onderzoek getOnderzoek() {
        return onderzoek;
    }

    /**
     * Zet de waarde van onderzoek.
     *
     * @param onderzoek
     *            onderzoek
     */
    public void setOnderzoek(final Onderzoek onderzoek) {
        ValidationUtils.controleerOpNullWaarden("onderzoek mag niet null zijn", onderzoek);
        this.onderzoek = onderzoek;
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
        ValidationUtils.controleerOpNullWaarden("persoon mag niet null zijn", persoon);
        this.persoon = persoon;
    }

    /**
     * Geef de waarde van soort persoon onderzoek.
     *
     * @return soort persoon onderzoek
     */
    public SoortPersoonOnderzoek getSoortPersoonOnderzoek() {
        return SoortPersoonOnderzoek.parseId(soortPersoonOnderzoekId);
    }

    /**
     * Zet de waarde van soort persoon onderzoek.
     *
     * @param soortPersoonOnderzoek
     *            soort persoon onderzoek
     */
    public void setSoortPersoonOnderzoek(final SoortPersoonOnderzoek soortPersoonOnderzoek) {
        soortPersoonOnderzoekId = soortPersoonOnderzoek == null ? null : soortPersoonOnderzoek.getId();
    }

}

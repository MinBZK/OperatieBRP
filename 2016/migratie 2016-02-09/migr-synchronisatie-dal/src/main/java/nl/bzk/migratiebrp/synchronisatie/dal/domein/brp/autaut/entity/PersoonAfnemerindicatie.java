/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Partij;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;

/**
 * The persistent class for the persafnemerindicatie database table.
 *
 */
@Entity
@Table(name = "persafnemerindicatie", schema = "autaut")
@NamedQuery(name = "PersoonAfnemerindicatie.findAll", query = "SELECT p FROM PersoonAfnemerindicatie p")
@SuppressWarnings("checkstyle:designforextension")
public class PersoonAfnemerindicatie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "persafnemerindicatie_id_generator", sequenceName = "autaut.seq_persafnemerindicatie", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "persafnemerindicatie_id_generator")
    @Column(updatable = false)
    private Long id;

    // uni-directional many-to-one association to Partij
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "afnemer", nullable = false)
    private Partij afnemer;

    @Column(name = "dataanvmaterieleperiode")
    private Integer datumAanvangMaterielePeriode;

    @Column(name = "dateindevolgen")
    private Integer datumEindeVolgen;

    // uni-directional many-to-one association to Persoon
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pers", nullable = false)
    private Persoon persoon;

    // bi-directional many-to-one association to PersoonAfnemerindicatieHistorie
    @OneToMany(mappedBy = "persoonAfnemerindicatie", cascade = CascadeType.ALL)
    private Set<PersoonAfnemerindicatieHistorie> persoonAfnemerindicatieHistorieSet = new LinkedHashSet<>(0);

    // uni-directional many-to-one association to Leveringsautorisatie
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "levsautorisatie", nullable = false)
    private Leveringsautorisatie leveringsautorisatie;

    /**
     * JPA no-args constructor.
     */
    PersoonAfnemerindicatie() {
    }

    /**
     * Maakt een nieuw PersoonAfnemerindicatie object aan.
     *
     * @param persoon
     *            persoon
     * @param afnemer
     *            afnemer
     * @param leveringsautorisatie
     *            leveringsautorisatie
     */
    public PersoonAfnemerindicatie(final Persoon persoon, final Partij afnemer, final Leveringsautorisatie leveringsautorisatie) {
        setPersoon(persoon);
        setAfnemer(afnemer);
        setLeveringsautorisatie(leveringsautorisatie);
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
     * Geef de waarde van afnemer.
     *
     * @return afnemer
     */
    public Partij getAfnemer() {
        return afnemer;
    }

    /**
     * Zet de waarde van afnemer.
     *
     * @param afnemer
     *            afnemer
     */
    public void setAfnemer(final Partij afnemer) {
        ValidationUtils.controleerOpNullWaarden("afnemer mag niet null zijn", afnemer);
        this.afnemer = afnemer;
    }

    /**
     * Geef de waarde van datumAanvangMaterielePeriode.
     *
     * @return datumAanvangMaterielePeriode
     */
    public Integer getDatumAanvangMaterielePeriode() {
        return datumAanvangMaterielePeriode;
    }

    /**
     * Zet de waarde van datumAanvangMaterielePeriode.
     *
     * @param datumAanvangMaterielePeriode
     *            datumAanvangMaterielePeriode
     */
    public void setDatumAanvangMaterielePeriode(final Integer datumAanvangMaterielePeriode) {
        this.datumAanvangMaterielePeriode = datumAanvangMaterielePeriode;
    }

    /**
     * Geef de waarde van datumEindeVolgen.
     *
     * @return datumEindeVolgen
     */
    public Integer getDatumEindeVolgen() {
        return datumEindeVolgen;
    }

    /**
     * Zet de waarde van datumEindeVolgen.
     *
     * @param datumEindeVolgen
     *            datumEindeVolgen
     */
    public void setDatumEindeVolgen(final Integer datumEindeVolgen) {
        this.datumEindeVolgen = datumEindeVolgen;
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
     * Geef de waarde van persoonAfnemerindicatieHistorieSet.
     *
     * @return persoonAfnemerindicatieHistorieSet
     */
    public Set<PersoonAfnemerindicatieHistorie> getPersoonAfnemerindicatieHistorieSet() {
        return persoonAfnemerindicatieHistorieSet;
    }

    /**
     * Zet de waarde van persoonAfnemerindicatieHistorieSet.
     *
     * @param persoonAfnemerindicatieHistorieSet
     *            persoonAfnemerindicatieHistorieSet
     */
    public void setPersoonAfnemerindicatieHistorieSet(final Set<PersoonAfnemerindicatieHistorie> persoonAfnemerindicatieHistorieSet) {
        this.persoonAfnemerindicatieHistorieSet = persoonAfnemerindicatieHistorieSet;
    }

    /**
     * Voeg persoonAfnemerindicatieHistorie toe aan persoonAfnemerindicatieHistorieSet.
     *
     * @param persoonAfnemerindicatieHistorie
     *            persoonAfnemerindicatieHistorie
     */
    public void addPersoonAfnemerindicatieHistorieSet(final PersoonAfnemerindicatieHistorie persoonAfnemerindicatieHistorie) {
        getPersoonAfnemerindicatieHistorieSet().add(persoonAfnemerindicatieHistorie);
        persoonAfnemerindicatieHistorie.setPersoonAfnemerindicatie(this);
    }

    /**
     * Verwijder persoonAfnemerindicatieHistorie uit persoonAfnemerindicatieHistorieSet.
     *
     * @param persoonAfnemerindicatieHistorie
     *            persoonAfnemerindicatieHistorie
     * @return true als persoonAfnemerindicatieHistorie verwijderd is, anders false
     */
    public boolean removePersoonAfnemerindicatieHistorieSet(final PersoonAfnemerindicatieHistorie persoonAfnemerindicatieHistorie) {
        final boolean result = getPersoonAfnemerindicatieHistorieSet().remove(persoonAfnemerindicatieHistorie);
        persoonAfnemerindicatieHistorie.setPersoonAfnemerindicatie(null);
        return result;
    }

    /**
     * Geef de waarde van leveringsautorisatie.
     *
     * @return leveringsautorisatie
     */
    public Leveringsautorisatie getLeveringsautorisatie() {
        return leveringsautorisatie;
    }

    /**
     * Zet de waarde van leveringsautorisatie.
     *
     * @param leveringsautorisatie
     *            leveringsautorisatie
     */
    public void setLeveringsautorisatie(final Leveringsautorisatie leveringsautorisatie) {
        ValidationUtils.controleerOpNullWaarden("leveringsautorisatie mag niet null zijn", leveringsautorisatie);
        this.leveringsautorisatie = leveringsautorisatie;
    }
}

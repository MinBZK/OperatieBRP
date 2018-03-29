/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import nl.bzk.algemeenbrp.dal.domein.brp.annotation.IndicatieActueelEnGeldig;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;

/**
 * The persistent class for the persafnemerindicatie database table.
 */
@Entity
@Table(name = "persafnemerindicatie", schema = "autaut")
@NamedQuery(name = "PersoonAfnemerindicatie" + Constanten.ZOEK_ALLES_VOOR_CACHE, query = "SELECT p FROM PersoonAfnemerindicatie p")
public class PersoonAfnemerindicatie implements Afleidbaar, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "persafnemerindicatie_id_generator", sequenceName = "autaut.seq_persafnemerindicatie", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "persafnemerindicatie_id_generator")
    @Column(updatable = false)
    private Long id;

    // uni-directional many-to-one association to Partij
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partij", nullable = false)
    private Partij partij;

    @Column(name = "dataanvmaterieleperiode")
    private Integer datumAanvangMaterielePeriode;

    @Column(name = "dateindevolgen")
    private Integer datumEindeVolgen;

    @Column(name = "indag", nullable = false)
    private boolean isActueelEnGeldig;

    // uni-directional many-to-one association to Persoon
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pers", nullable = false)
    private Persoon persoon;

    // bi-directional many-to-one association to PersoonAfnemerindicatieHistorie
    @IndicatieActueelEnGeldig(naam = "isActueelEnGeldig")
    @OneToMany(mappedBy = "persoonAfnemerindicatie", cascade = CascadeType.ALL)
    private Set<PersoonAfnemerindicatieHistorie> persoonAfnemerindicatieHistorieSet = new LinkedHashSet<>(0);

    // uni-directional many-to-one association to Leveringsautorisatie
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "levsautorisatie", nullable = false)
    private Leveringsautorisatie leveringsautorisatie;

    /**
     * JPA no-args constructor.
     */
    PersoonAfnemerindicatie() {}

    /**
     * Maakt een nieuw PersoonAfnemerindicatie object aan.
     *
     * @param persoon persoon
     * @param partij partij
     * @param leveringsautorisatie leveringsautorisatie
     */
    public PersoonAfnemerindicatie(final Persoon persoon, final Partij partij, final Leveringsautorisatie leveringsautorisatie) {
        setPersoon(persoon);
        setPartij(partij);
        setLeveringsautorisatie(leveringsautorisatie);
    }

    /**
     * Geef de waarde van id van PersoonAfnemerindicatie.
     *
     * @return de waarde van id van PersoonAfnemerindicatie
     */
    public Long getId() {
        return id;
    }

    /**
     * Zet de waarden voor id van PersoonAfnemerindicatie.
     *
     * @param id de nieuwe waarde voor id van PersoonAfnemerindicatie
     */
    public void setId(final Long id) {
        this.id = id;
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
     * Zet de waarde voor partij.
     *
     * @param partij partij
     */
    public void setPartij(final Partij partij) {
        ValidationUtils.controleerOpNullWaarden("partij mag niet null zijn", partij);
        this.partij = partij;
    }

    /**
     * Geef de waarde van datum aanvang materiele periode van PersoonAfnemerindicatie.
     *
     * @return de waarde van datum aanvang materiele periode van PersoonAfnemerindicatie
     */
    public Integer getDatumAanvangMaterielePeriode() {
        return datumAanvangMaterielePeriode;
    }

    /**
     * Zet de waarden voor datum aanvang materiele periode van PersoonAfnemerindicatie.
     *
     * @param datumAanvangMaterielePeriode de nieuwe waarde voor datum aanvang materiele periode van
     *        PersoonAfnemerindicatie
     */
    public void setDatumAanvangMaterielePeriode(final Integer datumAanvangMaterielePeriode) {
        this.datumAanvangMaterielePeriode = datumAanvangMaterielePeriode;
    }

    /**
     * Geef de waarde van datum einde volgen van PersoonAfnemerindicatie.
     *
     * @return de waarde van datum einde volgen van PersoonAfnemerindicatie
     */
    public Integer getDatumEindeVolgen() {
        return datumEindeVolgen;
    }

    /**
     * Zet de waarden voor datum einde volgen van PersoonAfnemerindicatie.
     *
     * @param datumEindeVolgen de nieuwe waarde voor datum einde volgen van PersoonAfnemerindicatie
     */
    public void setDatumEindeVolgen(final Integer datumEindeVolgen) {
        this.datumEindeVolgen = datumEindeVolgen;
    }

    /**
     * Geef de waarde van isActueelEnGeldig.
     *
     * @return isActueelEnGeldig
     */
    public boolean isActueelEnGeldig() {
        return isActueelEnGeldig;
    }

    /**
     * Zet de waarde van isActueelEnGeldig.
     *
     * @param actueelEnGeldig isActueelEnGeldig
     */
    public void setActueelEnGeldig(final boolean actueelEnGeldig) {
        isActueelEnGeldig = actueelEnGeldig;
    }

    /**
     * Geef de waarde van persoon van PersoonAfnemerindicatie.
     *
     * @return de waarde van persoon van PersoonAfnemerindicatie
     */
    public Persoon getPersoon() {
        return persoon;
    }

    /**
     * Zet de waarden voor persoon van PersoonAfnemerindicatie.
     *
     * @param persoon de nieuwe waarde voor persoon van PersoonAfnemerindicatie
     */
    public void setPersoon(final Persoon persoon) {
        ValidationUtils.controleerOpNullWaarden("persoon mag niet null zijn", persoon);
        this.persoon = persoon;
    }

    /**
     * Geef de waarde van persoon afnemerindicatie historie set van PersoonAfnemerindicatie.
     *
     * @return de waarde van persoon afnemerindicatie historie set van PersoonAfnemerindicatie
     */
    public Set<PersoonAfnemerindicatieHistorie> getPersoonAfnemerindicatieHistorieSet() {
        return persoonAfnemerindicatieHistorieSet;
    }

    /**
     * Zet de waarden voor persoon afnemerindicatie historie set van PersoonAfnemerindicatie.
     *
     * @param persoonAfnemerindicatieHistorieSet de nieuwe waarde voor persoon afnemerindicatie
     *        historie set van PersoonAfnemerindicatie
     */
    public void setPersoonAfnemerindicatieHistorieSet(final Set<PersoonAfnemerindicatieHistorie> persoonAfnemerindicatieHistorieSet) {
        this.persoonAfnemerindicatieHistorieSet = persoonAfnemerindicatieHistorieSet;
    }

    /**
     * Voeg persoonAfnemerindicatieHistorie toe aan persoonAfnemerindicatieHistorieSet.
     *
     * @param persoonAfnemerindicatieHistorie persoonAfnemerindicatieHistorie
     */
    public void addPersoonAfnemerindicatieHistorieSet(final PersoonAfnemerindicatieHistorie persoonAfnemerindicatieHistorie) {
        getPersoonAfnemerindicatieHistorieSet().add(persoonAfnemerindicatieHistorie);
        persoonAfnemerindicatieHistorie.setPersoonAfnemerindicatie(this);
    }

    /**
     * Verwijder persoonAfnemerindicatieHistorie uit persoonAfnemerindicatieHistorieSet.
     *
     * @param persoonAfnemerindicatieHistorie persoonAfnemerindicatieHistorie
     * @return true als persoonAfnemerindicatieHistorie verwijderd is, anders false
     */
    public boolean removePersoonAfnemerindicatieHistorieSet(final PersoonAfnemerindicatieHistorie persoonAfnemerindicatieHistorie) {
        final boolean result = getPersoonAfnemerindicatieHistorieSet().remove(persoonAfnemerindicatieHistorie);
        persoonAfnemerindicatieHistorie.setPersoonAfnemerindicatie(null);
        return result;
    }

    /**
     * Geef de waarde van leveringsautorisatie van PersoonAfnemerindicatie.
     *
     * @return de waarde van leveringsautorisatie van PersoonAfnemerindicatie
     */
    public Leveringsautorisatie getLeveringsautorisatie() {
        return leveringsautorisatie;
    }

    /**
     * Zet de waarden voor leveringsautorisatie van PersoonAfnemerindicatie.
     *
     * @param leveringsautorisatie de nieuwe waarde voor leveringsautorisatie van
     *        PersoonAfnemerindicatie
     */
    public void setLeveringsautorisatie(final Leveringsautorisatie leveringsautorisatie) {
        ValidationUtils.controleerOpNullWaarden("leveringsautorisatie mag niet null zijn", leveringsautorisatie);
        this.leveringsautorisatie = leveringsautorisatie;
    }
}

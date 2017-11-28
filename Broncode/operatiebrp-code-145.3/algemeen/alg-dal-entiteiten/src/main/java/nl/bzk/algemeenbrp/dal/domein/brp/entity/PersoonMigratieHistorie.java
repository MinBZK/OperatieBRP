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
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMigratie;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;

/**
 * The persistent class for the his_persimmigratie database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "his_persmigratie", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"pers", "tsreg", "dataanvgel"}))
public class PersoonMigratieHistorie extends AbstractMaterieleHistorie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_persimmigratie_id_generator", sequenceName = "kern.seq_his_persmigratie", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_persimmigratie_id_generator")
    @Column(nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "landgebiedmigratie")
    private LandOfGebied landOfGebiedMigratie;

    @Column(name = "srtmigratie", nullable = false)
    private int soortMigratieId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rdnwijzmigratie")
    private RedenWijzigingVerblijf redenWijzigingMigratie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aangmigratie")
    private Aangever aangeverMigratie;

    @Column(name = "bladresregel1migratie")
    private String buitenlandsAdresRegel1Migratie;
    @Column(name = "bladresregel2migratie")
    private String buitenlandsAdresRegel2Migratie;
    @Column(name = "bladresregel3migratie")
    private String buitenlandsAdresRegel3Migratie;
    @Column(name = "bladresregel4migratie")
    private String buitenlandsAdresRegel4Migratie;
    @Column(name = "bladresregel5migratie")
    private String buitenlandsAdresRegel5Migratie;
    @Column(name = "bladresregel6migratie")
    private String buitenlandsAdresRegel6Migratie;

    // bi-directional many-to-one association to Persoon
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "pers", nullable = false)
    private Persoon persoon;

    /**
     * JPA default constructor.
     */
    protected PersoonMigratieHistorie() {}

    /**
     * Maak een nieuwe persoon migratie historie.
     *
     * @param persoon persoon
     * @param soortMigratie soort migratie
     */
    public PersoonMigratieHistorie(final Persoon persoon, final SoortMigratie soortMigratie) {
        setPersoon(persoon);
        setSoortMigratie(soortMigratie);
    }

    /**
     * Kopie constructor. Maakt een nieuw object op basis van het gegeven bron object.
     *
     * @param ander het te kopieren object
     */
    public PersoonMigratieHistorie(final PersoonMigratieHistorie ander) {
        super(ander);
        landOfGebiedMigratie = ander.getLandOfGebied();
        soortMigratieId = ander.getSoortMigratie().getId();
        redenWijzigingMigratie = ander.getRedenWijzigingMigratie();
        aangeverMigratie = ander.getAangeverMigratie();
        buitenlandsAdresRegel1Migratie = ander.getBuitenlandsAdresRegel1();
        buitenlandsAdresRegel2Migratie = ander.getBuitenlandsAdresRegel2();
        buitenlandsAdresRegel3Migratie = ander.getBuitenlandsAdresRegel3();
        buitenlandsAdresRegel4Migratie = ander.getBuitenlandsAdresRegel4();
        buitenlandsAdresRegel5Migratie = ander.getBuitenlandsAdresRegel5();
        buitenlandsAdresRegel6Migratie = ander.getBuitenlandsAdresRegel6();
        persoon = ander.getPersoon();
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
     * Zet de waarden voor id van PersoonMigratieHistorie.
     *
     * @param id de nieuwe waarde voor id van PersoonMigratieHistorie
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van land of gebied van PersoonMigratieHistorie.
     *
     * @return de waarde van land of gebied van PersoonMigratieHistorie
     */
    public LandOfGebied getLandOfGebied() {
        return landOfGebiedMigratie;
    }

    /**
     * Zet de waarden voor land of gebied van PersoonMigratieHistorie.
     *
     * @param landOfGebied de nieuwe waarde voor land of gebied van PersoonMigratieHistorie
     */
    public void setLandOfGebied(final LandOfGebied landOfGebied) {
        landOfGebiedMigratie = landOfGebied;
    }

    /**
     * Geef de waarde van soort migratie van PersoonMigratieHistorie.
     *
     * @return de waarde van soort migratie van PersoonMigratieHistorie
     */
    public SoortMigratie getSoortMigratie() {
        return SoortMigratie.parseId(soortMigratieId);
    }

    /**
     * Zet de waarden voor soort migratie van PersoonMigratieHistorie.
     *
     * @param soortMigratie de nieuwe waarde voor soort migratie van PersoonMigratieHistorie
     */
    public void setSoortMigratie(final SoortMigratie soortMigratie) {
        ValidationUtils.controleerOpNullWaarden("soortMigratie mag niet null zijn", soortMigratie);
        soortMigratieId = soortMigratie.getId();
    }

    /**
     * Geef de waarde van reden wijziging migratie van PersoonMigratieHistorie.
     *
     * @return de waarde van reden wijziging migratie van PersoonMigratieHistorie
     */
    public RedenWijzigingVerblijf getRedenWijzigingMigratie() {
        return redenWijzigingMigratie;
    }

    /**
     * Zet de waarden voor reden wijziging migratie van PersoonMigratieHistorie.
     *
     * @param redenWijzigingMigratie de nieuwe waarde voor reden wijziging migratie van
     *        PersoonMigratieHistorie
     */
    public void setRedenWijzigingMigratie(final RedenWijzigingVerblijf redenWijzigingMigratie) {
        this.redenWijzigingMigratie = redenWijzigingMigratie;
    }

    /**
     * Geef de waarde van aangever migratie van PersoonMigratieHistorie.
     *
     * @return de waarde van aangever migratie van PersoonMigratieHistorie
     */
    public Aangever getAangeverMigratie() {
        return aangeverMigratie;
    }

    /**
     * Zet de waarden voor aangever migratie van PersoonMigratieHistorie.
     *
     * @param aangeverMigratie de nieuwe waarde voor aangever migratie van PersoonMigratieHistorie
     */
    public void setAangeverMigratie(final Aangever aangeverMigratie) {
        this.aangeverMigratie = aangeverMigratie;
    }

    /**
     * Geef de waarde van buitenlands adres regel1 van PersoonMigratieHistorie.
     *
     * @return de waarde van buitenlands adres regel1 van PersoonMigratieHistorie
     */
    public String getBuitenlandsAdresRegel1() {
        return buitenlandsAdresRegel1Migratie;
    }

    /**
     * Zet de waarden voor buitenlands adres regel1 van PersoonMigratieHistorie.
     *
     * @param buitenlandsAdresRegel1 de nieuwe waarde voor buitenlands adres regel1 van
     *        PersoonMigratieHistorie
     */
    public void setBuitenlandsAdresRegel1(final String buitenlandsAdresRegel1) {
        buitenlandsAdresRegel1Migratie = buitenlandsAdresRegel1;
    }

    /**
     * Geef de waarde van buitenlands adres regel2 van PersoonMigratieHistorie.
     *
     * @return de waarde van buitenlands adres regel2 van PersoonMigratieHistorie
     */
    public String getBuitenlandsAdresRegel2() {
        return buitenlandsAdresRegel2Migratie;
    }

    /**
     * Zet de waarden voor buitenlands adres regel2 van PersoonMigratieHistorie.
     *
     * @param buitenlandsAdresRegel2 de nieuwe waarde voor buitenlands adres regel2 van
     *        PersoonMigratieHistorie
     */
    public void setBuitenlandsAdresRegel2(final String buitenlandsAdresRegel2) {
        buitenlandsAdresRegel2Migratie = buitenlandsAdresRegel2;
    }

    /**
     * Geef de waarde van buitenlands adres regel3 van PersoonMigratieHistorie.
     *
     * @return de waarde van buitenlands adres regel3 van PersoonMigratieHistorie
     */
    public String getBuitenlandsAdresRegel3() {
        return buitenlandsAdresRegel3Migratie;
    }

    /**
     * Zet de waarden voor buitenlands adres regel3 van PersoonMigratieHistorie.
     *
     * @param buitenlandsAdresRegel3 de nieuwe waarde voor buitenlands adres regel3 van
     *        PersoonMigratieHistorie
     */
    public void setBuitenlandsAdresRegel3(final String buitenlandsAdresRegel3) {
        buitenlandsAdresRegel3Migratie = buitenlandsAdresRegel3;
    }

    /**
     * Geef de waarde van buitenlands adres regel4 van PersoonMigratieHistorie.
     *
     * @return de waarde van buitenlands adres regel4 van PersoonMigratieHistorie
     */
    public String getBuitenlandsAdresRegel4() {
        return buitenlandsAdresRegel4Migratie;
    }

    /**
     * Zet de waarden voor buitenlands adres regel4 van PersoonMigratieHistorie.
     *
     * @param buitenlandsAdresRegel4 de nieuwe waarde voor buitenlands adres regel4 van
     *        PersoonMigratieHistorie
     */
    public void setBuitenlandsAdresRegel4(final String buitenlandsAdresRegel4) {
        buitenlandsAdresRegel4Migratie = buitenlandsAdresRegel4;
    }

    /**
     * Geef de waarde van buitenlands adres regel5 van PersoonMigratieHistorie.
     *
     * @return de waarde van buitenlands adres regel5 van PersoonMigratieHistorie
     */
    public String getBuitenlandsAdresRegel5() {
        return buitenlandsAdresRegel5Migratie;
    }

    /**
     * Zet de waarden voor buitenlands adres regel5 van PersoonMigratieHistorie.
     *
     * @param buitenlandsAdresRegel5 de nieuwe waarde voor buitenlands adres regel5 van
     *        PersoonMigratieHistorie
     */
    public void setBuitenlandsAdresRegel5(final String buitenlandsAdresRegel5) {
        buitenlandsAdresRegel5Migratie = buitenlandsAdresRegel5;
    }

    /**
     * Geef de waarde van buitenlands adres regel6 van PersoonMigratieHistorie.
     *
     * @return de waarde van buitenlands adres regel6 van PersoonMigratieHistorie
     */
    public String getBuitenlandsAdresRegel6() {
        return buitenlandsAdresRegel6Migratie;
    }

    /**
     * Zet de waarden voor buitenlands adres regel6 van PersoonMigratieHistorie.
     *
     * @param buitenlandsAdresRegel6 de nieuwe waarde voor buitenlands adres regel6 van
     *        PersoonMigratieHistorie
     */
    public void setBuitenlandsAdresRegel6(final String buitenlandsAdresRegel6) {
        buitenlandsAdresRegel6Migratie = buitenlandsAdresRegel6;
    }

    @Override
    public Persoon getPersoon() {
        return persoon;
    }

    /**
     * Zet de waarden voor persoon van PersoonMigratieHistorie.
     *
     * @param persoon de nieuwe waarde voor persoon van PersoonMigratieHistorie
     */
    public void setPersoon(final Persoon persoon) {
        ValidationUtils.controleerOpNullWaarden("persoon mag niet null zijn", persoon);
        this.persoon = persoon;
    }

    @Override
    public final PersoonMigratieHistorie kopieer() {
        return new PersoonMigratieHistorie(this);
    }

    @Override
    public boolean moetHistorieAaneengeslotenZijn() {
        return true;
    }
}

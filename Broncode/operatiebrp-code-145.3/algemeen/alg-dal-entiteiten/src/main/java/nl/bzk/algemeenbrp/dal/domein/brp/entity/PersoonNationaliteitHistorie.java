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
 * The persistent class for the his_persnation database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "his_persnation", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"persnation", "tsreg", "dataanvgel"}))
public class PersoonNationaliteitHistorie extends AbstractMaterieleHistorie implements Serializable {
    /**
     * Veldnaam van einde bijhouding.
     */
    public static final String EINDE_BIJHOUDING = "indicatieBijhoudingBeeindigd";
    /**
     * Veldnaam van migratie datum.
     */
    public static final String MIGRATIE_DATUM = "migratieDatumEindeBijhouding";
    /**
     * Veldnaam van migratie reden beeindiging nationaliteit.
     */
    public static final String MIGRATIE_REDEN_BEEINDIGING_NATIONALITEIT = "migratieRedenBeeindigenNationaliteit";
    /**
     * Veldnaam van reden verlies NL nationaliteit.
     */
    public static final String VELD_REDEN_VERLIES_NL_NATIONALITEIT = "redenVerliesNLNationaliteit";
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "his_persnation_id_generator", sequenceName = "kern.seq_his_persnation", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_persnation_id_generator")
    @Column(nullable = false)
    private Long id;

    // bi-directional many-to-one association to PersoonNationaliteit
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "persnation", nullable = false)
    private PersoonNationaliteit persoonNationaliteit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rdnverk")
    private RedenVerkrijgingNLNationaliteit redenVerkrijgingNLNationaliteit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rdnverlies")
    private RedenVerliesNLNationaliteit redenVerliesNLNationaliteit;

    @Column(name = "indbijhoudingbeeindigd")
    private Boolean indicatieBijhoudingBeeindigd;

    @Column(name = "migrdateindebijhouding")
    private Integer migratieDatumEindeBijhouding;

    @Column(name = "migrrdnopnamenation")
    private String migratieRedenOpnameNationaliteit;

    @Column(name = "migrrdnbeeindigennation")
    private String migratieRedenBeeindigenNationaliteit;

    /**
     * JPA default constructor.
     */
    protected PersoonNationaliteitHistorie() {}

    /**
     * Maak een nieuwe persoon nationaliteit historie.
     *
     * @param persoonNationaliteit persoon nationaliteit
     */
    public PersoonNationaliteitHistorie(final PersoonNationaliteit persoonNationaliteit) {
        setPersoonNationaliteit(persoonNationaliteit);
    }

    /**
     * Kopie constructor. Maakt een nieuw object op basis van het gegeven bron object.
     *
     * @param ander het te kopieren object
     */
    public PersoonNationaliteitHistorie(final PersoonNationaliteitHistorie ander) {
        super(ander);
        persoonNationaliteit = ander.getPersoonNationaliteit();
        redenVerkrijgingNLNationaliteit = ander.getRedenVerkrijgingNLNationaliteit();
        redenVerliesNLNationaliteit = ander.getRedenVerliesNLNationaliteit();
        indicatieBijhoudingBeeindigd = ander.getIndicatieBijhoudingBeeindigd();
        migratieDatumEindeBijhouding = ander.getMigratieDatumEindeBijhouding();
        migratieRedenOpnameNationaliteit = ander.getMigratieRedenOpnameNationaliteit();
        migratieRedenBeeindigenNationaliteit = ander.getMigratieRedenBeeindigenNationaliteit();
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
     * Zet de waarden voor id van PersoonNationaliteitHistorie.
     *
     * @param id de nieuwe waarde voor id van PersoonNationaliteitHistorie
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van persoon nationaliteit van PersoonNationaliteitHistorie.
     *
     * @return de waarde van persoon nationaliteit van PersoonNationaliteitHistorie
     */
    public PersoonNationaliteit getPersoonNationaliteit() {
        return persoonNationaliteit;
    }

    /**
     * Zet de waarden voor persoon nationaliteit van PersoonNationaliteitHistorie.
     *
     * @param persoonNationaliteit de nieuwe waarde voor persoon nationaliteit van
     *        PersoonNationaliteitHistorie
     */
    public void setPersoonNationaliteit(final PersoonNationaliteit persoonNationaliteit) {
        ValidationUtils.controleerOpNullWaarden("persoonNationaliteit mag niet null zijn", persoonNationaliteit);
        this.persoonNationaliteit = persoonNationaliteit;
    }

    /**
     * Geef de waarde van reden verkrijging nl nationaliteit van PersoonNationaliteitHistorie.
     *
     * @return de waarde van reden verkrijging nl nationaliteit van PersoonNationaliteitHistorie
     */
    public RedenVerkrijgingNLNationaliteit getRedenVerkrijgingNLNationaliteit() {
        return redenVerkrijgingNLNationaliteit;
    }

    /**
     * Zet de waarden voor reden verkrijging nl nationaliteit van PersoonNationaliteitHistorie.
     *
     * @param redenVerkrijgingNLNationaliteit de nieuwe waarde voor reden verkrijging nl
     *        nationaliteit van PersoonNationaliteitHistorie
     */
    public void setRedenVerkrijgingNLNationaliteit(final RedenVerkrijgingNLNationaliteit redenVerkrijgingNLNationaliteit) {
        this.redenVerkrijgingNLNationaliteit = redenVerkrijgingNLNationaliteit;
    }

    /**
     * Geef de waarde van reden verlies nl nationaliteit van PersoonNationaliteitHistorie.
     *
     * @return de waarde van reden verlies nl nationaliteit van PersoonNationaliteitHistorie
     */
    public RedenVerliesNLNationaliteit getRedenVerliesNLNationaliteit() {
        return redenVerliesNLNationaliteit;
    }

    /**
     * Zet de waarden voor reden verlies nl nationaliteit van PersoonNationaliteitHistorie.
     *
     * @param redenVerliesNLNationaliteit de nieuwe waarde voor reden verlies nl nationaliteit van
     *        PersoonNationaliteitHistorie
     */
    public void setRedenVerliesNLNationaliteit(final RedenVerliesNLNationaliteit redenVerliesNLNationaliteit) {
        this.redenVerliesNLNationaliteit = redenVerliesNLNationaliteit;
    }

    /**
     * Geef de waarde van indicatie bijhouding beeindigd van PersoonNationaliteitHistorie.
     *
     * @return de waarde van indicatie bijhouding beeindigd van PersoonNationaliteitHistorie
     */
    public Boolean getIndicatieBijhoudingBeeindigd() {
        return indicatieBijhoudingBeeindigd;
    }

    /**
     * Zet de waarden voor indicatie bijhouding beeindigd van PersoonNationaliteitHistorie.
     *
     * @param indicatieBijhoudingBeeindigd de nieuwe waarde voor indicatie bijhouding beeindigd van
     *        PersoonNationaliteitHistorie
     */
    public void setIndicatieBijhoudingBeeindigd(final Boolean indicatieBijhoudingBeeindigd) {
        this.indicatieBijhoudingBeeindigd = indicatieBijhoudingBeeindigd;
    }

    /**
     * Geef de waarde van migratie datum einde bijhouding van PersoonNationaliteitHistorie.
     *
     * @return de waarde van migratie datum einde bijhouding van PersoonNationaliteitHistorie
     */
    public Integer getMigratieDatumEindeBijhouding() {
        return migratieDatumEindeBijhouding;
    }

    /**
     * Zet de waarden voor migratie datum einde bijhouding van PersoonNationaliteitHistorie.
     *
     * @param migratieDatumEindeBijhouding de nieuwe waarde voor migratie datum einde bijhouding van
     *        PersoonNationaliteitHistorie
     */
    public void setMigratieDatumEindeBijhouding(final Integer migratieDatumEindeBijhouding) {
        this.migratieDatumEindeBijhouding = migratieDatumEindeBijhouding;
    }

    /**
     * Geef de waarde van migratie reden opname nationaliteit van PersoonNationaliteitHistorie.
     *
     * @return de waarde van migratie reden opname nationaliteit van PersoonNationaliteitHistorie
     */
    public String getMigratieRedenOpnameNationaliteit() {
        return migratieRedenOpnameNationaliteit;
    }

    /**
     * Zet de waarden voor migratie reden opname nationaliteit van PersoonNationaliteitHistorie.
     *
     * @param migratieRedenOpnameNationaliteit de nieuwe waarde voor migratie reden opname
     *        nationaliteit van PersoonNationaliteitHistorie
     */
    public void setMigratieRedenOpnameNationaliteit(final String migratieRedenOpnameNationaliteit) {
        this.migratieRedenOpnameNationaliteit = migratieRedenOpnameNationaliteit;
    }

    /**
     * Geef de waarde van migratie reden beeindigen nationaliteit van PersoonNationaliteitHistorie.
     *
     * @return de waarde van migratie reden beeindigen nationaliteit van
     *         PersoonNationaliteitHistorie
     */
    public String getMigratieRedenBeeindigenNationaliteit() {
        return migratieRedenBeeindigenNationaliteit;
    }

    /**
     * Zet de waarden voor migratie reden beeindigen nationaliteit van PersoonNationaliteitHistorie.
     *
     * @param migratieRedenBeeindigenNationaliteit de nieuwe waarde voor migratie reden beeindigen
     *        nationaliteit van PersoonNationaliteitHistorie
     */
    public void setMigratieRedenBeeindigenNationaliteit(final String migratieRedenBeeindigenNationaliteit) {
        this.migratieRedenBeeindigenNationaliteit = migratieRedenBeeindigenNationaliteit;
    }

    @Override
    public final PersoonNationaliteitHistorie kopieer() {
        return new PersoonNationaliteitHistorie(this);
    }

    @Override
    public Persoon getPersoon() {
        return getPersoonNationaliteit().getPersoon();
    }

    @Override
    public boolean isDegGelijkAanDagToegestaan() {
        return true;
    }
}

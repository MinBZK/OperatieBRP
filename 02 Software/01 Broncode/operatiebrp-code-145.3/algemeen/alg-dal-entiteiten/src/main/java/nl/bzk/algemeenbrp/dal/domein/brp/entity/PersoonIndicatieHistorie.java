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
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;


/**
 * The persistent class for the his_persindicatie database table.
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "his_persindicatie", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"persindicatie", "tsreg", "dataanvgel"}))
public class PersoonIndicatieHistorie extends AbstractMaterieleHistorie implements Serializable {
    /**
     * Veldnaam van migratie reden beeindiging nationaliteit.
     */
    public static final String MIGRATIE_REDEN_BEEINDIGING_NATIONALITEIT = "migratieRedenBeeindigenNationaliteit";
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "his_persindicatie_id_generator", sequenceName = "kern.seq_his_persindicatie", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_persindicatie_id_generator")
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private boolean waarde;

    // bi-directional many-to-one association to PersoonIndicatie
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "persindicatie", nullable = false)
    private PersoonIndicatie persoonIndicatie;

    @Column(name = "migrRdnOpnameNation")
    private String migratieRedenOpnameNationaliteit;

    @Column(name = "migrRdnBeeindigenNation")
    private String migratieRedenBeeindigenNationaliteit;

    /**
     * JPA default constructor.
     */
    protected PersoonIndicatieHistorie() {
    }

    /**
     * Maak een nieuwe persoon indicatie historie.
     * @param persoonIndicatie persoon indicatie
     * @param waarde waarde
     */
    public PersoonIndicatieHistorie(final PersoonIndicatie persoonIndicatie, final boolean waarde) {
        setPersoonIndicatie(persoonIndicatie);
        setWaarde(waarde);
    }

    /**
     * Kopie constructor. Maakt een nieuw object op basis van het gegeven bron object.
     * @param ander het te kopieren object
     */
    public PersoonIndicatieHistorie(final PersoonIndicatieHistorie ander) {
        super(ander);
        waarde = ander.getWaarde();
        persoonIndicatie = ander.getPersoonIndicatie();
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
     * Zet de waarden voor id van PersoonIndicatieHistorie.
     * @param id de nieuwe waarde voor id van PersoonIndicatieHistorie
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van waarde van PersoonIndicatieHistorie.
     * @return de waarde van waarde van PersoonIndicatieHistorie
     */
    public boolean getWaarde() {
        return waarde;
    }

    /**
     * Zet de waarden voor waarde van PersoonIndicatieHistorie.
     * @param waarde de nieuwe waarde voor waarde van PersoonIndicatieHistorie
     */
    public void setWaarde(final boolean waarde) {
        this.waarde = waarde;
    }

    /**
     * Geef de waarde van persoon indicatie van PersoonIndicatieHistorie.
     * @return de waarde van persoon indicatie van PersoonIndicatieHistorie
     */
    public PersoonIndicatie getPersoonIndicatie() {
        return persoonIndicatie;
    }

    /**
     * Zet de waarden voor persoon indicatie van PersoonIndicatieHistorie.
     * @param persoonIndicatie de nieuwe waarde voor persoon indicatie van PersoonIndicatieHistorie
     */
    public void setPersoonIndicatie(final PersoonIndicatie persoonIndicatie) {
        ValidationUtils.controleerOpNullWaarden("persoonIndicatie mag niet null zijn", persoonIndicatie);
        this.persoonIndicatie = persoonIndicatie;
    }

    /**
     * Geef de waarde van migratie reden opname nationaliteit van PersoonIndicatieHistorie.
     * @return de waarde van migratie reden opname nationaliteit van PersoonIndicatieHistorie
     */
    public String getMigratieRedenOpnameNationaliteit() {
        return migratieRedenOpnameNationaliteit;
    }

    /**
     * Zet de waarden voor migratie reden opname nationaliteit van PersoonIndicatieHistorie.
     * @param migratieRedenOpnameNationaliteit de nieuwe waarde voor migratie reden opname nationaliteit van PersoonIndicatieHistorie
     */
    public void setMigratieRedenOpnameNationaliteit(final String migratieRedenOpnameNationaliteit) {
        this.migratieRedenOpnameNationaliteit = migratieRedenOpnameNationaliteit;
    }

    /**
     * Geef de waarde van migratie reden beeindigen nationaliteit van PersoonIndicatieHistorie.
     * @return de waarde van migratie reden beeindigen nationaliteit van PersoonIndicatieHistorie
     */
    public String getMigratieRedenBeeindigenNationaliteit() {
        return migratieRedenBeeindigenNationaliteit;
    }

    /**
     * Zet de waarden voor migratie reden beeindigen nationaliteit van PersoonIndicatieHistorie.
     * @param migratieRedenBeeindigenNationaliteit de nieuwe waarde voor migratie reden beeindigen nationaliteit van PersoonIndicatieHistorie
     */
    public void setMigratieRedenBeeindigenNationaliteit(final String migratieRedenBeeindigenNationaliteit) {
        this.migratieRedenBeeindigenNationaliteit = migratieRedenBeeindigenNationaliteit;
    }

    @Override
    public final PersoonIndicatieHistorie kopieer() {
        return new PersoonIndicatieHistorie(this);
    }

    @Override
    public Persoon getPersoon() {
        return getPersoonIndicatie().getPersoon();
    }

    @Override
    public boolean isDegGelijkAanDagToegestaan() {
        switch (getPersoonIndicatie().getSoortIndicatie()) {
            case BEHANDELD_ALS_NEDERLANDER:
            case ONDER_CURATELE:
            case DERDE_HEEFT_GEZAG:
            case VASTGESTELD_NIET_NEDERLANDER:
                return false;
            default:
                return true;
        }
    }


    @Override
    public boolean moetHistorieAaneengeslotenZijn() {
        final SoortIndicatie soortIndicatie = getPersoonIndicatie().getSoortIndicatie();
        return soortIndicatie == SoortIndicatie.BEHANDELD_ALS_NEDERLANDER || soortIndicatie == SoortIndicatie.VASTGESTELD_NIET_NEDERLANDER;
    }
}

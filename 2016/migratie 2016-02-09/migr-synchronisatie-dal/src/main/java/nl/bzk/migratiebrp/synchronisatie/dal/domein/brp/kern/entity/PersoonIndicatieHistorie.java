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
 * The persistent class for the his_persindicatie database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "his_persindicatie", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"persindicatie", "tsreg", "dataanvgel" }))
@SuppressWarnings("checkstyle:designforextension")
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
    /**
     * Het veld zou Integer moeten zijn maar is Long i.v.m. de link naar GegevenInOnderzoek.
     */
    private Long id;

    @Column(nullable = false)
    private boolean waarde;

    // bi-directional many-to-one association to PersoonIndicatie
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
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
     *
     * @param persoonIndicatie
     *            persoon indicatie
     * @param waarde
     *            waarde
     */
    public PersoonIndicatieHistorie(final PersoonIndicatie persoonIndicatie, final boolean waarde) {
        setPersoonIndicatie(persoonIndicatie);
        setWaarde(waarde);
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
     * Geef de waarde van waarde.
     *
     * @return waarde
     */
    public boolean getWaarde() {
        return waarde;
    }

    /**
     * Zet de waarde van waarde.
     *
     * @param waarde
     *            waarde
     */
    public void setWaarde(final boolean waarde) {
        this.waarde = waarde;
    }

    /**
     * Geef de waarde van persoon indicatie.
     *
     * @return persoon indicatie
     */
    public PersoonIndicatie getPersoonIndicatie() {
        return persoonIndicatie;
    }

    /**
     * Zet de waarde van persoon indicatie.
     *
     * @param persoonIndicatie
     *            persoon indicatie
     */
    public void setPersoonIndicatie(final PersoonIndicatie persoonIndicatie) {
        ValidationUtils.controleerOpNullWaarden("persoonIndicatie mag niet null zijn", persoonIndicatie);
        this.persoonIndicatie = persoonIndicatie;
    }

    /**
     * @return de reden opname nationaliteit tbv migratie/conversie
     */
    public String getMigratieRedenOpnameNationaliteit() {
        return migratieRedenOpnameNationaliteit;
    }

    /**
     * Zet de migratie reden opname nationaliteit tbv migratie/conversie.
     * 
     * @param migratieRedenOpnameNationaliteit
     *            de reden opname nationaliteit
     */
    public void setMigratieRedenOpnameNationaliteit(final String migratieRedenOpnameNationaliteit) {
        this.migratieRedenOpnameNationaliteit = migratieRedenOpnameNationaliteit;
    }

    /**
     * @return de reden beeindiging nationaliteit tbv migratie/conversie
     */
    public String getMigratieRedenBeeindigenNationaliteit() {
        return migratieRedenBeeindigenNationaliteit;
    }

    /**
     * Zet de migratie reden beeindiging nationaliteit tbv migratie/conversie.
     *
     * @param migratieRedenBeeindigenNationaliteit
     *            de reden beeindiging nationaliteit
     */
    public void setMigratieRedenBeeindigenNationaliteit(final String migratieRedenBeeindigenNationaliteit) {
        this.migratieRedenBeeindigenNationaliteit = migratieRedenBeeindigenNationaliteit;
    }
}

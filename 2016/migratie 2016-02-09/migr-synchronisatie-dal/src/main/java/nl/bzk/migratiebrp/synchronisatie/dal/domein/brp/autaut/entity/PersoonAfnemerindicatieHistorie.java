/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AbstractDeltaEntiteit;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BRPActie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.FormeleHistorie;
import nl.bzk.migratiebrp.util.common.Kopieer;

/**
 * The persistent class for the his_persafnemerindicatie database table.
 *
 */
@Entity
@Table(name = "his_persafnemerindicatie", schema = "autaut")
@NamedQuery(name = "PersoonAfnemerindicatieHistorie.findAll", query = "SELECT p FROM PersoonAfnemerindicatieHistorie p")
@SuppressWarnings("checkstyle:designforextension")
public class PersoonAfnemerindicatieHistorie extends AbstractDeltaEntiteit implements FormeleHistorie, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_persafnemerindicatie_id_generator", sequenceName = "autaut.seq_his_persafnemerindicatie", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_persafnemerindicatie_id_generator")
    @Column(updatable = false)
    private Long id;

    @Column(name = "dataanvmaterieleperiode")
    private Integer datumAanvangMaterielePeriode;

    @Column(name = "dateindevolgen")
    private Integer datumEindeVolgen;

    @Column(name = "tsreg", nullable = false)
    private Timestamp datumTijdRegistratie;

    @Column(name = "tsverval")
    private Timestamp datumTijdVerval;

    @Column(name = "nadereaandverval", length = 1)
    private Character nadereAanduidingVerval;

    // uni-directional many-to-one association to Dienst
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dienstinh")
    private Dienst dienstInhoud;

    // uni-directional many-to-one association to Dienst
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dienstverval")
    private Dienst dienstVerval;

    // bi-directional many-to-one association to PersoonAfnemerindicatie
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "persafnemerindicatie", nullable = false)
    private PersoonAfnemerindicatie persoonAfnemerindicatie;

    /**
     * JPA no-args constructor.
     */
    PersoonAfnemerindicatieHistorie() {
    }

    /**
     * Maakt een nieuw PersoonAfnemerindicatieHistorie object.
     *
     * @param persoonAfnemerindicatie
     *            persoonAfnemerindicatie
     */
    public PersoonAfnemerindicatieHistorie(final PersoonAfnemerindicatie persoonAfnemerindicatie) {
        setPersoonAfnemerindicatie(persoonAfnemerindicatie);
    }

    /**
     * Geef de waarde van id.
     *
     * @return id
     */
    @Override
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
     * Geef de waarde van datumTijdRegistratie.
     *
     * @return datumTijdRegistratie
     */
    @Override
    public Timestamp getDatumTijdRegistratie() {
        return Kopieer.timestamp(datumTijdRegistratie);
    }

    /**
     * Zet de waarde van datumTijdRegistratie.
     *
     * @param datumTijdRegistratie
     *            datumTijdRegistratie
     */
    @Override
    public void setDatumTijdRegistratie(final Timestamp datumTijdRegistratie) {
        ValidationUtils.controleerOpNullWaarden("datumTijdRegistratie mag niet null zijn", datumTijdRegistratie);
        this.datumTijdRegistratie = Kopieer.timestamp(datumTijdRegistratie);
    }

    /**
     * Geef de waarde van datumTijdVerval.
     *
     * @return datumTijdVerval
     */
    @Override
    public Timestamp getDatumTijdVerval() {
        return Kopieer.timestamp(datumTijdVerval);
    }

    /**
     * Zet de waarde van datumTijdVerval.
     *
     * @param datumTijdVerval
     *            datumTijdVerval
     */
    @Override
    public void setDatumTijdVerval(final Timestamp datumTijdVerval) {
        this.datumTijdVerval = Kopieer.timestamp(datumTijdVerval);
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
     * Geef de waarde van nadereAanduidingVerval.
     *
     * @return nadereAanduidingVerval
     */
    @Override
    public Character getNadereAanduidingVerval() {
        return nadereAanduidingVerval;
    }

    /**
     * Zet de waarde van nadereAanduidingVerval.
     *
     * @param nadereAanduidingVerval
     *            nadereAanduidingVerval
     */
    @Override
    public void setNadereAanduidingVerval(final Character nadereAanduidingVerval) {
        this.nadereAanduidingVerval = nadereAanduidingVerval;
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
     * Geef de waarde van dienstInhoud.
     *
     * @return dienstInhoud
     */
    public Dienst getDienstInhoud() {
        return dienstInhoud;
    }

    /**
     * Zet de waarde van dienstInhoud.
     *
     * @param dienstInhoud
     *            dienstInhoud
     */
    public void setDienstInhoud(final Dienst dienstInhoud) {
        this.dienstInhoud = dienstInhoud;
    }

    /**
     * Geef de waarde van dienstVerval.
     *
     * @return dienstVerval
     */
    public Dienst getDienstVerval() {
        return dienstVerval;
    }

    /**
     * Zet de waarde van dienstVerval.
     *
     * @param dienstVerval
     *            dienstVerval
     */
    public void setDienstVerval(final Dienst dienstVerval) {
        this.dienstVerval = dienstVerval;
    }

    /**
     * Geef de waarde van persoonAfnemerindicatie.
     *
     * @return persoonAfnemerindicatie
     */
    public PersoonAfnemerindicatie getPersoonAfnemerindicatie() {
        return persoonAfnemerindicatie;
    }

    /**
     * Zet de waarde van persoonAfnemerindicatie.
     *
     * @param persoonAfnemerindicatie
     *            persoonAfnemerindicatie
     */
    public void setPersoonAfnemerindicatie(final PersoonAfnemerindicatie persoonAfnemerindicatie) {
        ValidationUtils.controleerOpNullWaarden("persoonAfnemerindicatie mag niet null zijn", persoonAfnemerindicatie);
        this.persoonAfnemerindicatie = persoonAfnemerindicatie;
    }

    // Hieronder volgen de methodes van Formele Historie waar deze implementie afwijkt van het standaard patroon

    @Override
    public boolean isActueel() {
        return getDatumTijdVerval() == null;
    }

    @Override
    public final boolean isVervallen() {
        return getDatumTijdVerval() != null;
    }

    @Override
    public BRPActie getActieVervalTbvLeveringMutaties() {
        return null;
    }

    @Override
    public void setActieVervalTbvLeveringMutaties(final BRPActie actieVervalTbvLeveringMutaties) {
        throw new IllegalArgumentException("PersoonAfnemerindicatieHistorie bevat geen actieVervalTbvLeveringMutaties.");
    }

    @Override
    public Boolean getIndicatieVoorkomenTbvLeveringMutaties() {
        return Boolean.FALSE;
    }

    @Override
    public void setIndicatieVoorkomenTbvLeveringMutaties(final Boolean indicatieVoorkomenTbvLeveringMutaties) {
        throw new IllegalArgumentException("PersoonAfnemerindicatieHistorie bevat geen indicatieVoorkomenTbvLeveringMutaties.");
    }

    @Override
    public final boolean isVoorkomenTbvLeveringMutaties() {
        return Boolean.TRUE.equals(getIndicatieVoorkomenTbvLeveringMutaties());
    }

    @Override
    public BRPActie getActieInhoud() {
        return null;
    }

    @Override
    public void setActieInhoud(final BRPActie actieInhoud) {
        throw new IllegalArgumentException("PersoonAfnemerindicatieHistorie bevat geen actieInhoud.");
    }

    @Override
    public BRPActie getActieVerval() {
        return null;
    }

    @Override
    public void setActieVerval(final BRPActie actieVerval) {
        throw new IllegalArgumentException("PersoonAfnemerindicatieHistorie bevat geen actieVerval.");
    }
}

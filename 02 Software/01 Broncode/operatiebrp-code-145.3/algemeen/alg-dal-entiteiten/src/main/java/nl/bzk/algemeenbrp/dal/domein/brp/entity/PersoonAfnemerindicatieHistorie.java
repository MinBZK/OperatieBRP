/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

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
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;

/**
 * The persistent class for the his_persafnemerindicatie database table.
 *
 */
@Entity
@Table(name = "his_persafnemerindicatie", schema = "autaut")
@NamedQuery(name = "PersoonAfnemerindicatieHistorie" + Constanten.ZOEK_ALLES_VOOR_CACHE, query = "SELECT p FROM PersoonAfnemerindicatieHistorie p")
public class PersoonAfnemerindicatieHistorie extends AbstractEntiteit implements FormeleHistorie, Serializable {
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
    PersoonAfnemerindicatieHistorie() {}

    /**
     * Maakt een nieuw PersoonAfnemerindicatieHistorie object.
     *
     * @param persoonAfnemerindicatie persoonAfnemerindicatie
     */
    public PersoonAfnemerindicatieHistorie(final PersoonAfnemerindicatie persoonAfnemerindicatie) {
        setPersoonAfnemerindicatie(persoonAfnemerindicatie);
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
     * Zet de waarden voor id van PersoonAfnemerindicatieHistorie.
     *
     * @param id de nieuwe waarde voor id van PersoonAfnemerindicatieHistorie
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.algemeen.dal.domein.brp.FormeleHistorieZonderVerantwoording#
     * getDatumTijdRegistratie()
     */
    @Override
    public Timestamp getDatumTijdRegistratie() {
        return Entiteit.timestamp(datumTijdRegistratie);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.algemeen.dal.domein.brp.FormeleHistorieZonderVerantwoording#
     * setDatumTijdRegistratie(java.sql.Timestamp)
     */
    @Override
    public void setDatumTijdRegistratie(final Timestamp datumTijdRegistratie) {
        ValidationUtils.controleerOpNullWaarden("datumTijdRegistratie mag niet null zijn", datumTijdRegistratie);
        this.datumTijdRegistratie = Entiteit.timestamp(datumTijdRegistratie);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.algemeen.dal.domein.brp.FormeleHistorieZonderVerantwoording# getDatumTijdVerval()
     */
    @Override
    public Timestamp getDatumTijdVerval() {
        return Entiteit.timestamp(datumTijdVerval);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.algemeen.dal.domein.brp.FormeleHistorieZonderVerantwoording#
     * setDatumTijdVerval(java.sql.Timestamp)
     */
    @Override
    public void setDatumTijdVerval(final Timestamp datumTijdVerval) {
        this.datumTijdVerval = Entiteit.timestamp(datumTijdVerval);
    }

    /**
     * Geef de waarde van datum aanvang materiele periode van PersoonAfnemerindicatieHistorie.
     *
     * @return de waarde van datum aanvang materiele periode van PersoonAfnemerindicatieHistorie
     */
    public Integer getDatumAanvangMaterielePeriode() {
        return datumAanvangMaterielePeriode;
    }

    /**
     * Zet de waarden voor datum aanvang materiele periode van PersoonAfnemerindicatieHistorie.
     *
     * @param datumAanvangMaterielePeriode de nieuwe waarde voor datum aanvang materiele periode van
     *        PersoonAfnemerindicatieHistorie
     */
    public void setDatumAanvangMaterielePeriode(final Integer datumAanvangMaterielePeriode) {
        this.datumAanvangMaterielePeriode = datumAanvangMaterielePeriode;
    }

    /**
     * Geef de waarde van datum einde volgen van PersoonAfnemerindicatieHistorie.
     *
     * @return de waarde van datum einde volgen van PersoonAfnemerindicatieHistorie
     */
    public Integer getDatumEindeVolgen() {
        return datumEindeVolgen;
    }

    /**
     * Zet de waarden voor datum einde volgen van PersoonAfnemerindicatieHistorie.
     *
     * @param datumEindeVolgen de nieuwe waarde voor datum einde volgen van
     *        PersoonAfnemerindicatieHistorie
     */
    public void setDatumEindeVolgen(final Integer datumEindeVolgen) {
        this.datumEindeVolgen = datumEindeVolgen;
    }

    /**
     * Geef de waarde van dienst inhoud van PersoonAfnemerindicatieHistorie.
     *
     * @return de waarde van dienst inhoud van PersoonAfnemerindicatieHistorie
     */
    public Dienst getDienstInhoud() {
        return dienstInhoud;
    }

    /**
     * Zet de waarden voor dienst inhoud van PersoonAfnemerindicatieHistorie.
     *
     * @param dienstInhoud de nieuwe waarde voor dienst inhoud van PersoonAfnemerindicatieHistorie
     */
    public void setDienstInhoud(final Dienst dienstInhoud) {
        this.dienstInhoud = dienstInhoud;
    }

    /**
     * Geef de waarde van dienst verval van PersoonAfnemerindicatieHistorie.
     *
     * @return de waarde van dienst verval van PersoonAfnemerindicatieHistorie
     */
    public Dienst getDienstVerval() {
        return dienstVerval;
    }

    /**
     * Zet de waarden voor dienst verval van PersoonAfnemerindicatieHistorie.
     *
     * @param dienstVerval de nieuwe waarde voor dienst verval van PersoonAfnemerindicatieHistorie
     */
    public void setDienstVerval(final Dienst dienstVerval) {
        this.dienstVerval = dienstVerval;
    }

    /**
     * Geef de waarde van persoon afnemerindicatie van PersoonAfnemerindicatieHistorie.
     *
     * @return de waarde van persoon afnemerindicatie van PersoonAfnemerindicatieHistorie
     */
    public PersoonAfnemerindicatie getPersoonAfnemerindicatie() {
        return persoonAfnemerindicatie;
    }

    /**
     * Zet de waarden voor persoon afnemerindicatie van PersoonAfnemerindicatieHistorie.
     *
     * @param persoonAfnemerindicatie de nieuwe waarde voor persoon afnemerindicatie van
     *        PersoonAfnemerindicatieHistorie
     */
    public void setPersoonAfnemerindicatie(final PersoonAfnemerindicatie persoonAfnemerindicatie) {
        ValidationUtils.controleerOpNullWaarden("persoonAfnemerindicatie mag niet null zijn", persoonAfnemerindicatie);
        this.persoonAfnemerindicatie = persoonAfnemerindicatie;
    }

    // Hieronder volgen de methodes van Formele Historie waar deze implementie afwijkt van het
    // standaard patroon

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.algemeen.dal.domein.brp.FormeleHistorieZonderVerantwoording#isActueel()
     */
    @Override
    public boolean isActueel() {
        return getDatumTijdVerval() == null;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.algemeen.dal.domein.brp.FormeleHistorieZonderVerantwoording#isVervallen()
     */
    @Override
    public final boolean isVervallen() {
        return getDatumTijdVerval() != null;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.algemeen.dal.domein.brp.FormeleHistorie#getActieVervalTbvLeveringMutaties()
     */
    @Override
    public BRPActie getActieVervalTbvLeveringMutaties() {
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.algemeen.dal.domein.brp.FormeleHistorie#setActieVervalTbvLeveringMutaties(nl
     * .bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BRPActie)
     */
    @Override
    public void setActieVervalTbvLeveringMutaties(final BRPActie actieVervalTbvLeveringMutaties) {
        throw new IllegalArgumentException("PersoonAfnemerindicatieHistorie bevat geen actieVervalTbvLeveringMutaties.");
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.algemeen.dal.domein.brp.FormeleHistorie#
     * getIndicatieVoorkomenTbvLeveringMutaties()
     */
    @Override
    public Boolean getIndicatieVoorkomenTbvLeveringMutaties() {
        return Boolean.FALSE;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.algemeen.dal.domein.brp.FormeleHistorie#
     * setIndicatieVoorkomenTbvLeveringMutaties(java.lang.Boolean)
     */
    @Override
    public void setIndicatieVoorkomenTbvLeveringMutaties(final Boolean indicatieVoorkomenTbvLeveringMutaties) {
        throw new IllegalArgumentException("PersoonAfnemerindicatieHistorie bevat geen indicatieVoorkomenTbvLeveringMutaties.");
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.algemeen.dal.domein.brp.FormeleHistorieZonderVerantwoording#
     * getNadereAanduidingVerval()
     */
    @Override
    public Character getNadereAanduidingVerval() {
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.algemeen.dal.domein.brp.FormeleHistorieZonderVerantwoording#
     * setNadereAanduidingVerval(java.lang.Character)
     */
    @Override
    public void setNadereAanduidingVerval(final Character nadereAanduidingVerval) {
        throw new IllegalArgumentException("PersoonAfnemerindicatieHistorie bevat geen nadereAanduidingVerval.");
    }

    @Override
    public void laatVervallen(final BRPActie actie, final Character nadereAanduidingVerval) {
        throw new IllegalArgumentException("PersoonAfnemerindicatieHistorie bevat geen actie verval en nadereAanduidingVerval.");
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.algemeen.dal.domein.brp.FormeleHistorie#isVoorkomenTbvLeveringMutaties()
     */
    @Override
    public final boolean isVoorkomenTbvLeveringMutaties() {
        return Boolean.TRUE.equals(getIndicatieVoorkomenTbvLeveringMutaties());
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.algemeen.dal.domein.brp.FormeleHistorie#getActieInhoud()
     */
    @Override
    public BRPActie getActieInhoud() {
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.algemeen.dal.domein.brp.FormeleHistorie#setActieInhoud(nl.bzk.migratiebrp.
     * synchronisatie.dal.domein.brp.kern.entity.BRPActie)
     */
    @Override
    public void setActieInhoud(final BRPActie actieInhoud) {
        throw new IllegalArgumentException("PersoonAfnemerindicatieHistorie bevat geen actieInhoud.");
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.algemeen.dal.domein.brp.FormeleHistorie#getActieVerval()
     */
    @Override
    public BRPActie getActieVerval() {
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.algemeen.dal.domein.brp.FormeleHistorie#setActieVerval(nl.bzk.migratiebrp.
     * synchronisatie.dal.domein.brp.kern.entity.BRPActie)
     */
    @Override
    public void setActieVerval(final BRPActie actieVerval) {
        throw new IllegalArgumentException("PersoonAfnemerindicatieHistorie bevat geen actieVerval.");
    }
}

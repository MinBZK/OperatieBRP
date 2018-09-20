/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;
import nl.bzk.migratiebrp.util.common.Kopieer;

/**
 * Deze class bevat de velden voor het opslaan van formele historie.
 */
@MappedSuperclass
@SuppressWarnings("checkstyle:designforextension")
public abstract class AbstractFormeleHistorie extends AbstractDeltaEntiteit implements FormeleHistorie {

    /** Veldnaam van actieInhoud tbv verschil verwerking. */
    public static final String ACTIE_INHOUD = "actieInhoud";
    /** Veldnaam van actieVerval tbv verschil verwerking. */
    public static final String ACTIE_VERVAL = "actieVerval";
    /** Veldnaam van actieVervalTbvLeveringMutaties tbv verschil bepaling. */
    public static final String ACTIE_VERVAL_TBV_LEVERING_MUTATIES = "actieVervalTbvLeveringMutaties";
    /** Veldnaam van indicatieVoorkomenTbvLeveringMutaties tbv verschil bepaling. */
    public static final String INDICATIE_VOORKOMEN_TBV_LEVERING_MUTATIES = "indicatieVoorkomenTbvLeveringMutaties";
    /** Veldnaam van datumTijdRegistratie tbv verschil verwerking. */
    public static final String DATUM_TIJD_REGISTRATIE = "datumTijdRegistratie";
    /** Veldnaam van datumTijdVerval tbv verschil verwerking. */
    public static final String DATUM_TIJD_VERVAL = "datumTijdVerval";
    /** Veldnaam van nadereAanduidingVerval tbv verschil verwerking. */
    public static final String NADERE_AANDUIDING_VERVAL = "nadereAanduidingVerval";

    @Column(name = "tsreg", nullable = false)
    /* Bij wijziging veld-naam ook de string constante daarvoor aanpassen */
    private Timestamp datumTijdRegistratie;

    @Column(name = "tsverval")
    /* Bij wijziging veld-naam ook de string constante daarvoor aanpassen */
    private Timestamp datumTijdVerval;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "actieinh")
    /* Bij wijziging veld-naam ook de string constante daarvoor aanpassen */
    private BRPActie actieInhoud;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "actieverval")
    /* Bij wijziging veld-naam ook de string constante daarvoor aanpassen */
    private BRPActie actieVerval;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "actievervaltbvlevmuts")
    /* Bij wijziging veld-naam ook de string constante daarvoor aanpassen */
    private BRPActie actieVervalTbvLeveringMutaties;

    @Column(name = "nadereaandverval", length = 1)
    /* Bij wijziging veld-naam ook de string constante daarvoor aanpassen */
    private Character nadereAanduidingVerval;

    @Column(name = "indvoorkomentbvlevmuts")
    /* Bij wijziging veld-naam ook de string constante daarvoor aanpassen */
    private Boolean indicatieVoorkomenTbvLeveringMutaties;

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.FormeleHistorie#getDatumTijdRegistratie()
     */
    @Override
    public Timestamp getDatumTijdRegistratie() {

        return Kopieer.timestamp(datumTijdRegistratie);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.FormeleHistorie#setDatumTijdRegistratie(java.sql
     * .Timestamp)
     */
    @Override
    public void setDatumTijdRegistratie(final Timestamp datumTijdRegistratie) {
        ValidationUtils.controleerOpNullWaarden("datumTijdRegistratie mag niet null zijn", datumTijdRegistratie);
        this.datumTijdRegistratie = Kopieer.timestamp(datumTijdRegistratie);
        controleerFormeleHistorieInterval();
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.FormeleHistorie#getDatumTijdVerval()
     */
    @Override
    public Timestamp getDatumTijdVerval() {

        return Kopieer.timestamp(datumTijdVerval);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.FormeleHistorie#setDatumTijdVerval(java.sql.
     * Timestamp )
     */
    @Override
    public void setDatumTijdVerval(final Timestamp datumTijdVerval) {

        this.datumTijdVerval = Kopieer.timestamp(datumTijdVerval);
        controleerFormeleHistorieInterval();
    }

    private void controleerFormeleHistorieInterval() {
        // TODO Controle uitgeschakeld totdat ORANJE-3018 wordt opgepakt
        // if (datumTijdRegistratie != null && datumTijdVerval != null && datumTijdRegistratie.after(datumTijdVerval)) {
        // throw new IllegalStateException("dtRegistratie ligt na dtVerval");
        // }
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.FormeleHistorie#getActieInhoud()
     */
    @Override
    public BRPActie getActieInhoud() {

        return actieInhoud;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.FormeleHistorie#setActieInhoud(nl.bzk.migratiebrp
     * .synchronisatie.dal.domein.brp.kern.entity.BRPActie)
     */
    @Override
    public void setActieInhoud(final BRPActie actieInhoud) {

        this.actieInhoud = actieInhoud;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.FormeleHistorie#getActieVerval()
     */
    @Override
    public BRPActie getActieVerval() {

        return actieVerval;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.FormeleHistorie#setActieVerval(nl.bzk.migratiebrp
     * .synchronisatie.dal.domein.brp.kern.entity.BRPActie)
     */
    @Override
    public void setActieVerval(final BRPActie actieVerval) {

        this.actieVerval = actieVerval;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.FormeleHistorie#getNadereAanduidingVerval()
     */
    @Override
    public Character getNadereAanduidingVerval() {

        return nadereAanduidingVerval;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.FormeleHistorie#setNadereAanduidingVerval(java.lang
     * .Character)
     */
    @Override
    public void setNadereAanduidingVerval(final Character nadereAanduidingVerval) {

        this.nadereAanduidingVerval = nadereAanduidingVerval;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.FormeleHistorie#isActueel()
     */
    @Override
    public boolean isActueel() {

        return getDatumTijdVerval() == null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.FormeleHistorie#isVervallen()
     */
    @Override
    public final boolean isVervallen() {
        return getDatumTijdVerval() != null;
    }

    @Override
    public BRPActie getActieVervalTbvLeveringMutaties() {

        return actieVervalTbvLeveringMutaties;
    }

    @Override
    public void setActieVervalTbvLeveringMutaties(final BRPActie actieVervalTbvLeveringMutaties) {

        this.actieVervalTbvLeveringMutaties = actieVervalTbvLeveringMutaties;
    }

    @Override
    public Boolean getIndicatieVoorkomenTbvLeveringMutaties() {

        return indicatieVoorkomenTbvLeveringMutaties;
    }

    @Override
    public void setIndicatieVoorkomenTbvLeveringMutaties(final Boolean indicatieVoorkomenTbvLeveringMutaties) {

        if (Boolean.FALSE.equals(indicatieVoorkomenTbvLeveringMutaties)) {
            throw new IllegalStateException("indicatieVoorkomenTbvLeveringMutaties mag alleen null of true zijn.");
        }
        this.indicatieVoorkomenTbvLeveringMutaties = indicatieVoorkomenTbvLeveringMutaties;
    }

    @Override
    public final boolean isVoorkomenTbvLeveringMutaties() {
        return Boolean.TRUE.equals(getIndicatieVoorkomenTbvLeveringMutaties());
    }

    /**
     * Maakt van de historische rij een M-rij. Deze rij is vervallen (zowel actie verval als tijdstip verval wordt
     * gevuld) en er wordt aangegeven dat dit gebeurd is tbv mutatie levering is (actie verval tbv levering mutatie en
     * indicatie voorkomen tbv levering mutaties worden gezet).
     * 
     * @param actieVervalTbvLeveringMuts
     *            de actie die gebruikt om aan te geven dat deze rij tbv levering mutaties is aangepast.
     */
    public void converteerNaarMRij(final BRPActie actieVervalTbvLeveringMuts) {
        setActieVerval(getActieInhoud());
        setActieVervalTbvLeveringMutaties(actieVervalTbvLeveringMuts);
        setIndicatieVoorkomenTbvLeveringMutaties(true);
        setDatumTijdVerval(getDatumTijdRegistratie());
    }
}

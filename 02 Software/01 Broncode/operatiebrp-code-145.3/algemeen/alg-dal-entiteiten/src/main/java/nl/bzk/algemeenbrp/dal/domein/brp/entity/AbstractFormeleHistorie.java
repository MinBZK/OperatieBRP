/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 * Deze class bevat de velden voor het opslaan van formele historie inclusief
 * verantwoordingsattributen voor Acties.
 */
@MappedSuperclass
public abstract class AbstractFormeleHistorie extends AbstractFormeleHistorieZonderVerantwoording implements FormeleHistorie {

    /** Veldnaam van actieInhoud tbv verschil verwerking. */
    public static final String ACTIE_INHOUD = "actieInhoud";
    /** Veldnaam van actieVerval tbv verschil verwerking. */
    public static final String ACTIE_VERVAL = "actieVerval";
    /** Veldnaam van actieVervalTbvLeveringMutaties tbv verschil bepaling. */
    public static final String ACTIE_VERVAL_TBV_LEVERING_MUTATIES = "actieVervalTbvLeveringMutaties";
    /** Veldnaam van indicatieVoorkomenTbvLeveringMutaties tbv verschil bepaling. */
    public static final String INDICATIE_VOORKOMEN_TBV_LEVERING_MUTATIES = "indicatieVoorkomenTbvLeveringMutaties";
    /** Veldnaam van nadereAanduidingVerval tbv verschil verwerking. */
    public static final String NADERE_AANDUIDING_VERVAL = "nadereAanduidingVerval";

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "actieinh")
    /* Bij wijziging veld-naam ook de string constante daarvoor aanpassen */
    private BRPActie actieInhoud;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "actieverval")
    /* Bij wijziging veld-naam ook de string constante daarvoor aanpassen */
    private BRPActie actieVerval;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "actievervaltbvlevmuts")
    /* Bij wijziging veld-naam ook de string constante daarvoor aanpassen */
    private BRPActie actieVervalTbvLeveringMutaties;

    @Column(name = "indvoorkomentbvlevmuts")
    /* Bij wijziging veld-naam ook de string constante daarvoor aanpassen */
    private Boolean indicatieVoorkomenTbvLeveringMutaties;

    @Column(name = "nadereaandverval", length = 1)
    /* Bij wijziging veld-naam ook de string constante daarvoor aanpassen */
    private Character nadereAanduidingVerval;

    /**
     * Maakt een nieuw abstracte formele historie.
     */
    public AbstractFormeleHistorie() {
        super();
    }

    /**
     * Kopie constructor voor AbstractFormeleHistorie.
     *
     * @param ander de andere historie waaruit gekopieerd moet worden
     */
    public AbstractFormeleHistorie(final AbstractFormeleHistorie ander) {
        super(ander);
        actieInhoud = ander.getActieInhoud();
        actieVerval = ander.getActieVerval();
        actieVervalTbvLeveringMutaties = ander.getActieVervalTbvLeveringMutaties();
        indicatieVoorkomenTbvLeveringMutaties = ander.getIndicatieVoorkomenTbvLeveringMutaties();
        nadereAanduidingVerval = ander.getNadereAanduidingVerval();
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.algemeen.dal.domein.brp.FormeleHistorie#getActieInhoud()
     */
    @Override
    public BRPActie getActieInhoud() {

        return actieInhoud;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.algemeen.dal.domein.brp.FormeleHistorie#setActieInhoud(nl.bzk.migratiebrp
     * .synchronisatie.dal.domein.brp.kern.entity.BRPActie)
     */
    @Override
    public void setActieInhoud(final BRPActie actieInhoud) {

        this.actieInhoud = actieInhoud;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.algemeen.dal.domein.brp.FormeleHistorie#getActieVerval()
     */
    @Override
    public BRPActie getActieVerval() {

        return actieVerval;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.algemeen.dal.domein.brp.FormeleHistorie#setActieVerval(nl.bzk.migratiebrp
     * .synchronisatie.dal.domein.brp.kern.entity.BRPActie)
     */
    @Override
    public void setActieVerval(final BRPActie actieVerval) {

        this.actieVerval = actieVerval;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.algemeen.dal.domein.brp.FormeleHistorie#isActueel()
     */
    @Override
    public boolean isActueel() {

        return getDatumTijdVerval() == null;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.algemeen.dal.domein.brp.FormeleHistorie#getActieVervalTbvLeveringMutaties()
     */
    @Override
    public BRPActie getActieVervalTbvLeveringMutaties() {

        return actieVervalTbvLeveringMutaties;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.algemeen.dal.domein.brp.FormeleHistorie#setActieVervalTbvLeveringMutaties(nl
     * .bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BRPActie)
     */
    @Override
    public void setActieVervalTbvLeveringMutaties(final BRPActie actieVervalTbvLeveringMutaties) {

        this.actieVervalTbvLeveringMutaties = actieVervalTbvLeveringMutaties;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.algemeen.dal.domein.brp.FormeleHistorie#
     * getIndicatieVoorkomenTbvLeveringMutaties()
     */
    @Override
    public Boolean getIndicatieVoorkomenTbvLeveringMutaties() {

        return indicatieVoorkomenTbvLeveringMutaties;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.algemeen.dal.domein.brp.FormeleHistorie#
     * setIndicatieVoorkomenTbvLeveringMutaties(java.lang.Boolean)
     */
    @Override
    public void setIndicatieVoorkomenTbvLeveringMutaties(final Boolean indicatieVoorkomenTbvLeveringMutaties) {

        if (Boolean.FALSE.equals(indicatieVoorkomenTbvLeveringMutaties)) {
            throw new IllegalStateException("indicatieVoorkomenTbvLeveringMutaties mag alleen null of true zijn.");
        }
        this.indicatieVoorkomenTbvLeveringMutaties = indicatieVoorkomenTbvLeveringMutaties;
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
     * @see nl.bzk.algemeen.dal.domein.brp.FormeleHistorie#getNadereAanduidingVerval()
     */
    @Override
    public Character getNadereAanduidingVerval() {

        return nadereAanduidingVerval;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.algemeen.dal.domein.brp.FormeleHistorie#setNadereAanduidingVerval(java.lang
     * .Character)
     */
    @Override
    public void setNadereAanduidingVerval(final Character nadereAanduidingVerval) {

        this.nadereAanduidingVerval = nadereAanduidingVerval;
    }

    @Override
    public void laatVervallen(final BRPActie actie, final Character nadereAanduidingVerval) {
        setDatumTijdVerval(actie.getDatumTijdRegistratie());
        setActieVerval(actie);
        setNadereAanduidingVerval(nadereAanduidingVerval);
    }

    /**
     * Maakt van de historische rij een M-rij. Deze rij is vervallen (zowel actie verval als
     * tijdstip verval wordt gevuld) en er wordt aangegeven dat dit gebeurd is tbv mutatie levering
     * is (actie verval tbv levering mutatie en indicatie voorkomen tbv levering mutaties worden
     * gezet).
     *
     * @param actieVervalTbvLeveringMuts de actie die gebruikt om aan te geven dat deze rij tbv
     *        levering mutaties is aangepast.
     */
    public void converteerNaarMRij(final BRPActie actieVervalTbvLeveringMuts) {
        setActieVerval(getActieInhoud());
        setActieVervalTbvLeveringMutaties(actieVervalTbvLeveringMuts);
        setIndicatieVoorkomenTbvLeveringMutaties(true);
        setDatumTijdVerval(getDatumTijdRegistratie());
    }
}

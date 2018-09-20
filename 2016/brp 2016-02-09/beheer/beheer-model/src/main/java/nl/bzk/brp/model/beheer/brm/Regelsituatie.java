/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.beheer.brm;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Generated;
import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.brm.RegelSoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.brm.Regeleffect;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Bijhoudingsaard;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NadereBijhoudingsaard;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * De situaties waarin de Regelimplementatie van toepassing is.
 *
 * 1. De runtime beheerde gegevens zijn in een aparte entiteit ondergebracht: Deze. Hierdoor is regel zelf release time
 * beheerd geworden.
 *
 * 2. Bij de naamgeving van deze entiteit is aangesloten bij het patroon bij bijhoudingssituaties: deze entiteit
 * beschrijft namelijk de situaties waarin een bepaalde regel/soort bericht 'van toepassing' is. In tegenstelling tot de
 * situatie bij 'bijhoudingsautorisatie', echter, is het 'effect' nu vastgelegd bij de "...situatie" entiteit, in plaats
 * van bij de "ouder" entiteit. Reden is dat, in tegenstelling tot bijhoudingssituatie, het effect (weigeren,
 * waarschuwen, ...) op het niveau van een individuele situatie is, en niet voor het 'geheel'. Het geheel doortrekken
 * van de naam zou 'regel/soortbericht situatie' hebben opgeleverd; deze term is echter minder begrijpelijk dan
 * 'regelsituatie'. Om die reden is voor regelsituatie gekozen.
 *
 *
 *
 */
@Entity(name = "beheer.Regelsituatie")
@Table(schema = "BRM", name = "Regelsituatie")
@Generated(value = "nl.bzk.brp.generatoren.java.BeheerGenerator")
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class Regelsituatie {

    @Id
    @SequenceGenerator(name = "REGELSITUATIE", sequenceName = "BRM.seq_Regelsituatie")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "REGELSITUATIE")
    @JsonProperty
    private Integer iD;

    @Column(name = "RegelSrtBer")
    @Enumerated
    private RegelSoortBericht regelSoortBericht;

    @Column(name = "Bijhaard")
    @Enumerated
    private Bijhoudingsaard bijhoudingsaard;

    @Column(name = "NadereBijhaard")
    @Enumerated
    private NadereBijhoudingsaard nadereBijhoudingsaard;

    @Column(name = "Effect")
    @Enumerated
    private Regeleffect effect;

    @Embedded
    @AttributeOverride(name = JaNeeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndActief"))
    private JaNeeAttribuut indicatieActief;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "regelsituatie", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    private Set<HisRegelsituatie> hisRegelsituatieLijst = new HashSet<>();

    /**
     * Retourneert ID van Regelsituatie.
     *
     * @return ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Regel \ Soort bericht van Regelsituatie.
     *
     * @return Regel \ Soort bericht.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public RegelSoortBericht getRegelSoortBericht() {
        return regelSoortBericht;
    }

    /**
     * Retourneert Bijhoudingsaard van Regelsituatie.
     *
     * @return Bijhoudingsaard.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Bijhoudingsaard getBijhoudingsaard() {
        return bijhoudingsaard;
    }

    /**
     * Retourneert Nadere bijhoudingsaard van Regelsituatie.
     *
     * @return Nadere bijhoudingsaard.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public NadereBijhoudingsaard getNadereBijhoudingsaard() {
        return nadereBijhoudingsaard;
    }

    /**
     * Retourneert Effect van Regelsituatie.
     *
     * @return Effect.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Regeleffect getEffect() {
        return effect;
    }

    /**
     * Retourneert Actief? van Regelsituatie.
     *
     * @return Actief?.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public JaNeeAttribuut getIndicatieActief() {
        return indicatieActief;
    }

    /**
     * Retourneert Standaard van Regelsituatie.
     *
     * @return Standaard.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Set<HisRegelsituatie> getHisRegelsituatieLijst() {
        return hisRegelsituatieLijst;
    }

    /**
     * Zet ID van Regelsituatie.
     *
     * @param pID ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setID(final Integer pID) {
        this.iD = pID;
    }

    /**
     * Zet Regel \ Soort bericht van Regelsituatie.
     *
     * @param pRegelSoortBericht Regel \ Soort bericht.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setRegelSoortBericht(final RegelSoortBericht pRegelSoortBericht) {
        this.regelSoortBericht = pRegelSoortBericht;
    }

    /**
     * Zet Bijhoudingsaard van Regelsituatie.
     *
     * @param pBijhoudingsaard Bijhoudingsaard.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setBijhoudingsaard(final Bijhoudingsaard pBijhoudingsaard) {
        this.bijhoudingsaard = pBijhoudingsaard;
    }

    /**
     * Zet Nadere bijhoudingsaard van Regelsituatie.
     *
     * @param pNadereBijhoudingsaard Nadere bijhoudingsaard.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setNadereBijhoudingsaard(final NadereBijhoudingsaard pNadereBijhoudingsaard) {
        this.nadereBijhoudingsaard = pNadereBijhoudingsaard;
    }

    /**
     * Zet Effect van Regelsituatie.
     *
     * @param pEffect Effect.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setEffect(final Regeleffect pEffect) {
        this.effect = pEffect;
    }

    /**
     * Zet Actief? van Regelsituatie.
     *
     * @param pIndicatieActief Actief?.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setIndicatieActief(final JaNeeAttribuut pIndicatieActief) {
        this.indicatieActief = pIndicatieActief;
    }

}

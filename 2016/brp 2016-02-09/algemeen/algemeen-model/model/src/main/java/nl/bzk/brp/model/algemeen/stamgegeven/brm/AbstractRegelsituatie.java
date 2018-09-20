/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.brm;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Bijhoudingsaard;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NadereBijhoudingsaard;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;
import org.hibernate.annotations.Immutable;

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
@Access(value = AccessType.FIELD)
@EntityListeners(value = StamgegevenEntityListener.class)
@Immutable
@Generated(value = "nl.bzk.brp.generatoren.java.DynamischeStamgegevensGenerator")
@MappedSuperclass
public abstract class AbstractRegelsituatie {

    @Id
    @JsonProperty
    private Integer iD;

    @Column(name = "RegelSrtBer")
    private RegelSoortBericht regelSoortBericht;

    @Column(name = "Bijhaard")
    private Bijhoudingsaard bijhoudingsaard;

    @Column(name = "NadereBijhaard")
    private NadereBijhoudingsaard nadereBijhoudingsaard;

    @Column(name = "Effect")
    private Regeleffect effect;

    @Embedded
    @AttributeOverride(name = JaNeeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndActief"))
    private JaNeeAttribuut indicatieActief;

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected AbstractRegelsituatie() {
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param regelSoortBericht regelSoortBericht van Regelsituatie.
     * @param bijhoudingsaard bijhoudingsaard van Regelsituatie.
     * @param nadereBijhoudingsaard nadereBijhoudingsaard van Regelsituatie.
     * @param effect effect van Regelsituatie.
     * @param indicatieActief indicatieActief van Regelsituatie.
     */
    protected AbstractRegelsituatie(
        final RegelSoortBericht regelSoortBericht,
        final Bijhoudingsaard bijhoudingsaard,
        final NadereBijhoudingsaard nadereBijhoudingsaard,
        final Regeleffect effect,
        final JaNeeAttribuut indicatieActief)
    {
        this.regelSoortBericht = regelSoortBericht;
        this.bijhoudingsaard = bijhoudingsaard;
        this.nadereBijhoudingsaard = nadereBijhoudingsaard;
        this.effect = effect;
        this.indicatieActief = indicatieActief;

    }

    /**
     * Retourneert ID van Regelsituatie.
     *
     * @return ID.
     */
    protected Integer getID() {
        return iD;
    }

    /**
     * Retourneert Regel \ Soort bericht van Regelsituatie.
     *
     * @return Regel \ Soort bericht.
     */
    public final RegelSoortBericht getRegelSoortBericht() {
        return regelSoortBericht;
    }

    /**
     * Retourneert Bijhoudingsaard van Regelsituatie.
     *
     * @return Bijhoudingsaard.
     */
    public final Bijhoudingsaard getBijhoudingsaard() {
        return bijhoudingsaard;
    }

    /**
     * Retourneert Nadere bijhoudingsaard van Regelsituatie.
     *
     * @return Nadere bijhoudingsaard.
     */
    public final NadereBijhoudingsaard getNadereBijhoudingsaard() {
        return nadereBijhoudingsaard;
    }

    /**
     * Retourneert Effect van Regelsituatie.
     *
     * @return Effect.
     */
    public final Regeleffect getEffect() {
        return effect;
    }

    /**
     * Retourneert Actief? van Regelsituatie.
     *
     * @return Actief?.
     */
    public final JaNeeAttribuut getIndicatieActief() {
        return indicatieActief;
    }

}

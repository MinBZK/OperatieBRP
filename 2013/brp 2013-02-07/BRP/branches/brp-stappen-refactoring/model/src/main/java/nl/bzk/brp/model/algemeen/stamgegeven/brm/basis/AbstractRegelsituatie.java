/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.brm.basis;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNee;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.StatusHistorie;
import nl.bzk.brp.model.algemeen.stamgegeven.brm.RegelSoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.brm.Regeleffect;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Bijhoudingsaard;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenOpschorting;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;
import org.hibernate.annotations.Type;


/**
 * De situaties waarin de Regelimplementatie van toepassing is.
 *
 * 1. De runtime beheerde gegevens zijn in een aparte entiteit ondergebracht: Deze. Hierdoor is regel zelf release time
 * beheerd geworden. RvdP 20 december 2011, aangepast 24 januari 2012 en 16 april 2012.
 *
 * 2. Bij de naamgeving van deze entiteit is aangesloten bij het patroon bij bijhoudingssituaties: deze entiteit
 * beschrijft namelijk de situaties waarin een bepaalde regel/soort bericht 'van toepassing' is. In tegenstelling tot de
 * situatie bij 'bijhoudingsautorisatie', echter, is het 'effect' nu vastgelegd bij de "...situatie" entiteit, in plaats
 * van bij de "ouder" entiteit. Reden is dat, in tegenstelling tot bijhoudingssituatie, het effect (weigeren,
 * waarschuwen, ...) op het niveau van een individuele situatie is, en niet voor het 'geheel'.
 * Het geheel doortrekken van de naam zou 'regel/soortbericht situatie' hebben opgeleverd; deze term is echter minder
 * begrijpelijk dan 'regelsituatie'. Om die reden is voor regelsituatie gekozen.
 * RvdP 24 januari 2012, aangepast 16 april 2012.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.DynamischeStamgegevensGenerator.
 * Metaregister versie: 1.6.0.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2013-01-15 12:43:16.
 * Gegenereerd op: Tue Jan 15 12:53:51 CET 2013.
 */
@Access(value = AccessType.FIELD)
@EntityListeners(value = StamgegevenEntityListener.class)
@MappedSuperclass
public abstract class AbstractRegelsituatie extends AbstractStatischObjectType {

    @Id
    private Integer           iD;

    @Column(name = "Regelimplementatie")
    private RegelSoortBericht regelimplementatie;

    @Column(name = "Bijhverantwoordelijkheid")
    private Bijhoudingsaard   bijhoudingsverantwoordelijkheid;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "IndOpgeschort"))
    private JaNee             indicatieOpgeschort;

    @Column(name = "RdnOpschorting")
    private RedenOpschorting  redenOpschorting;

    @Column(name = "Effect")
    private Regeleffect       effect;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "IndActief"))
    private JaNee             indicatieActief;

    @Type(type = "StatusHistorie")
    @Column(name = "RegelsituatieStatusHis")
    private StatusHistorie    regelsituatieStatusHis;

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected AbstractRegelsituatie() {
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param regelimplementatie regelimplementatie van Regelsituatie.
     * @param bijhoudingsverantwoordelijkheid bijhoudingsverantwoordelijkheid van Regelsituatie.
     * @param indicatieOpgeschort indicatieOpgeschort van Regelsituatie.
     * @param redenOpschorting redenOpschorting van Regelsituatie.
     * @param effect effect van Regelsituatie.
     * @param indicatieActief indicatieActief van Regelsituatie.
     * @param regelsituatieStatusHis regelsituatieStatusHis van Regelsituatie.
     */
    protected AbstractRegelsituatie(final RegelSoortBericht regelimplementatie,
            final Bijhoudingsaard bijhoudingsverantwoordelijkheid, final JaNee indicatieOpgeschort,
            final RedenOpschorting redenOpschorting, final Regeleffect effect, final JaNee indicatieActief,
            final StatusHistorie regelsituatieStatusHis)
    {
        this.regelimplementatie = regelimplementatie;
        this.bijhoudingsverantwoordelijkheid = bijhoudingsverantwoordelijkheid;
        this.indicatieOpgeschort = indicatieOpgeschort;
        this.redenOpschorting = redenOpschorting;
        this.effect = effect;
        this.indicatieActief = indicatieActief;
        this.regelsituatieStatusHis = regelsituatieStatusHis;

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
     * Retourneert Regelimplementatie van Regelsituatie.
     *
     * @return Regelimplementatie.
     */
    public RegelSoortBericht getRegelimplementatie() {
        return regelimplementatie;
    }

    /**
     * Retourneert Bijhoudingsverantwoordelijkheid van Regelsituatie.
     *
     * @return Bijhoudingsverantwoordelijkheid.
     */
    public Bijhoudingsaard getBijhoudingsverantwoordelijkheid() {
        return bijhoudingsverantwoordelijkheid;
    }

    /**
     * Retourneert Opgeschort? van Regelsituatie.
     *
     * @return Opgeschort?.
     */
    public JaNee getIndicatieOpgeschort() {
        return indicatieOpgeschort;
    }

    /**
     * Retourneert Reden opschorting van Regelsituatie.
     *
     * @return Reden opschorting.
     */
    public RedenOpschorting getRedenOpschorting() {
        return redenOpschorting;
    }

    /**
     * Retourneert Effect van Regelsituatie.
     *
     * @return Effect.
     */
    public Regeleffect getEffect() {
        return effect;
    }

    /**
     * Retourneert Actief? van Regelsituatie.
     *
     * @return Actief?.
     */
    public JaNee getIndicatieActief() {
        return indicatieActief;
    }

    /**
     * Retourneert Regelsituatie StatusHis van Regelsituatie.
     *
     * @return Regelsituatie StatusHis.
     */
    public StatusHistorie getRegelsituatieStatusHis() {
        return regelsituatieStatusHis;
    }

}

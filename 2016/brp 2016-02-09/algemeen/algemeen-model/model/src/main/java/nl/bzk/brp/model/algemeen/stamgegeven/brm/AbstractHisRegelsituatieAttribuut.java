/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.brm;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.BijhoudingsaardAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenOpschortingAttribuut;


/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@MappedSuperclass
public abstract class AbstractHisRegelsituatieAttribuut {

    @Id
    @SequenceGenerator(name = "HIS_REGELSITUATIE", sequenceName = "BRM.seq_His_Regelsituatie")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_REGELSITUATIE")
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Regelsituatie")
    private Regelsituatie regelsituatie;

    @Embedded
    @AttributeOverride(name = BijhoudingsaardAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Bijhverantwoordelijkheid"))
    @JsonProperty
    private BijhoudingsaardAttribuut bijhoudingsverantwoordelijkheid;

    @Embedded
    @AttributeOverride(name = JaNeeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndOpgeschort"))
    @JsonProperty
    private JaNeeAttribuut indicatieOpgeschort;

    @Embedded
    @AttributeOverride(name = RedenOpschortingAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "RdnOpschorting"))
    @JsonProperty
    private RedenOpschortingAttribuut redenOpschorting;

    @Embedded
    @AttributeOverride(name = RegeleffectAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Effect"))
    @JsonProperty
    private RegeleffectAttribuut effect;

    @Embedded
    @AttributeOverride(name = JaNeeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndActief"))
    @JsonProperty
    private JaNeeAttribuut indicatieActief;

    /**
     * Default constructor t.b.v. JPA
     */
    protected AbstractHisRegelsituatieAttribuut() {
    }

    /**
     * Retourneert ID van His Regelsituatie.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Regelsituatie van His Regelsituatie.
     *
     * @return Regelsituatie.
     */
    public Regelsituatie getRegelsituatie() {
        return regelsituatie;
    }

    /**
     * Retourneert Bijhoudingsverantwoordelijkheid van His Regelsituatie.
     *
     * @return Bijhoudingsverantwoordelijkheid.
     */
    public BijhoudingsaardAttribuut getBijhoudingsverantwoordelijkheid() {
        return bijhoudingsverantwoordelijkheid;
    }

    /**
     * Retourneert Opgeschort? van His Regelsituatie.
     *
     * @return Opgeschort?.
     */
    public JaNeeAttribuut getIndicatieOpgeschort() {
        return indicatieOpgeschort;
    }

    /**
     * Retourneert Reden opschorting van His Regelsituatie.
     *
     * @return Reden opschorting.
     */
    public RedenOpschortingAttribuut getRedenOpschorting() {
        return redenOpschorting;
    }

    /**
     * Retourneert Effect van His Regelsituatie.
     *
     * @return Effect.
     */
    public RegeleffectAttribuut getEffect() {
        return effect;
    }

    /**
     * Retourneert Actief? van His Regelsituatie.
     *
     * @return Actief?.
     */
    public JaNeeAttribuut getIndicatieActief() {
        return indicatieActief;
    }

}

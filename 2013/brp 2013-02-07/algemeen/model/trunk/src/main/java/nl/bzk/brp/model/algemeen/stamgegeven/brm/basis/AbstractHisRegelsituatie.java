/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.brm.basis;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNee;
import nl.bzk.brp.model.algemeen.stamgegeven.brm.Regeleffect;
import nl.bzk.brp.model.algemeen.stamgegeven.brm.Regelsituatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Bijhoudingsaard;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenOpschorting;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@MappedSuperclass
public abstract class AbstractHisRegelsituatie {

    @Id
    @SequenceGenerator(name = "HIS_REGELSITUATIE", sequenceName = "BRM.seq_His_Regelsituatie")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_REGELSITUATIE")
    @JsonProperty
    private Integer          iD;

    @ManyToOne
    @JoinColumn(name = "Regelsituatie")
    @Fetch(value = FetchMode.JOIN)
    private Regelsituatie    regelsituatie;

    @Enumerated
    @Column(name = "Bijhverantwoordelijkheid")
    @JsonProperty
    private Bijhoudingsaard  bijhoudingsverantwoordelijkheid;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "IndOpgeschort"))
    @JsonProperty
    private JaNee            indicatieOpgeschort;

    @Enumerated
    @Column(name = "RdnOpschorting")
    @JsonProperty
    private RedenOpschorting redenOpschorting;

    @Enumerated
    @Column(name = "Effect")
    @JsonProperty
    private Regeleffect      effect;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "IndActief"))
    @JsonProperty
    private JaNee            indicatieActief;

    /**
     * Default constructor t.b.v. JPA
     *
     */
    protected AbstractHisRegelsituatie() {
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
    public Bijhoudingsaard getBijhoudingsverantwoordelijkheid() {
        return bijhoudingsverantwoordelijkheid;
    }

    /**
     * Retourneert Opgeschort? van His Regelsituatie.
     *
     * @return Opgeschort?.
     */
    public JaNee getIndicatieOpgeschort() {
        return indicatieOpgeschort;
    }

    /**
     * Retourneert Reden opschorting van His Regelsituatie.
     *
     * @return Reden opschorting.
     */
    public RedenOpschorting getRedenOpschorting() {
        return redenOpschorting;
    }

    /**
     * Retourneert Effect van His Regelsituatie.
     *
     * @return Effect.
     */
    public Regeleffect getEffect() {
        return effect;
    }

    /**
     * Retourneert Actief? van His Regelsituatie.
     *
     * @return Actief?.
     */
    public JaNee getIndicatieActief() {
        return indicatieActief;
    }

}

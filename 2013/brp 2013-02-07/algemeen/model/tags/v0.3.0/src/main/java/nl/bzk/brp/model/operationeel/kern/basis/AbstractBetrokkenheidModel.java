/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern.basis;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.basis.AbstractDynamischObjectType;
import nl.bzk.brp.model.logisch.kern.Betrokkenheid;
import nl.bzk.brp.model.logisch.kern.basis.BetrokkenheidBasis;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.operationeel.kern.RelatieModel;


/**
 * De wijze waarop een Persoon betrokken is bij een Relatie.
 *
 * Er wordt expliciet onderscheid gemaakt tussen de Relatie enerzijds, en de Persoon die in de Relatie betrokken is
 * anderzijds. De koppeling van een Persoon en een Relatie gebeurt via Betrokkenheid. Zie ook overkoepelend verhaal.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@MappedSuperclass
public abstract class AbstractBetrokkenheidModel extends AbstractDynamischObjectType implements BetrokkenheidBasis {

    @Id
    @SequenceGenerator(name = "BETROKKENHEID", sequenceName = "Kern.seq_Betr")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "BETROKKENHEID")
    @JsonProperty
    private Integer            iD;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Relatie")
    @JsonProperty
    private RelatieModel       relatie;

    @Enumerated
    @Column(name = "Rol", updatable = false, insertable = false)
    @JsonProperty
    private SoortBetrokkenheid rol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Pers")
    private PersoonModel       persoon;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected AbstractBetrokkenheidModel() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param relatie relatie van Betrokkenheid.
     * @param rol rol van Betrokkenheid.
     * @param persoon persoon van Betrokkenheid.
     */
    public AbstractBetrokkenheidModel(final RelatieModel relatie, final SoortBetrokkenheid rol,
            final PersoonModel persoon)
    {
        this();
        this.relatie = relatie;
        this.rol = rol;
        this.persoon = persoon;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param betrokkenheid Te kopieren object type.
     * @param relatie Bijbehorende Relatie.
     * @param persoon Bijbehorende Persoon.
     */
    public AbstractBetrokkenheidModel(final Betrokkenheid betrokkenheid, final RelatieModel relatie,
            final PersoonModel persoon)
    {
        this();
        this.relatie = relatie;
        this.rol = betrokkenheid.getRol();
        this.persoon = persoon;

    }

    /**
     * Retourneert ID van Betrokkenheid.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Relatie van Betrokkenheid.
     *
     * @return Relatie.
     */
    public RelatieModel getRelatie() {
        return relatie;
    }

    /**
     * Retourneert Rol van Betrokkenheid.
     *
     * @return Rol.
     */
    public SoortBetrokkenheid getRol() {
        return rol;
    }

    /**
     * Retourneert Persoon van Betrokkenheid.
     *
     * @return Persoon.
     */
    public PersoonModel getPersoon() {
        return persoon;
    }

}

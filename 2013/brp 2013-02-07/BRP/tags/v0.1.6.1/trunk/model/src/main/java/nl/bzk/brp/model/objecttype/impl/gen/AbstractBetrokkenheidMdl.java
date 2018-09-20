/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.impl.gen;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

import nl.bzk.brp.model.basis.AbstractDynamischObjectType;
import nl.bzk.brp.model.groep.impl.usr.BetrokkenheidOuderlijkGezagGroepMdl;
import nl.bzk.brp.model.groep.impl.usr.BetrokkenheidOuderschapGroepMdl;
import nl.bzk.brp.model.objecttype.impl.usr.PersoonMdl;
import nl.bzk.brp.model.objecttype.impl.usr.RelatieMdl;
import nl.bzk.brp.model.objecttype.interfaces.gen.BetrokkenheidBasis;
import nl.bzk.brp.model.objecttype.statisch.SoortBetrokkenheid;

/**
 * Implementatie voor objecttype betrokkenheid.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractBetrokkenheidMdl extends AbstractDynamischObjectType implements BetrokkenheidBasis {

    @Id
    @SequenceGenerator(name = "BETROKKENHEID", sequenceName = "Kern.seq_Betr")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BETROKKENHEID")
    private Long id;

    @Column(name = "rol")
    @NotNull
    private SoortBetrokkenheid rol;

    @ManyToOne
    @JoinColumn(name = "relatie")
    @NotNull
    private RelatieMdl relatie;

    @OneToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH })
    @JoinColumn(name = "betrokkene")
    @NotNull
    private PersoonMdl betrokkene;

    @Embedded
    @NotNull
    private BetrokkenheidOuderlijkGezagGroepMdl betrokkenheidOuderlijkGezag;

    @Embedded
    @NotNull
    private BetrokkenheidOuderschapGroepMdl betrokkenheidOuderschap;

    public Long getId() {
        return id;
    }

    @Override
    public SoortBetrokkenheid getRol() {
        return rol;
    }

    @Override
    public RelatieMdl getRelatie() {
        return relatie;
    }

    @Override
    public PersoonMdl getBetrokkene() {
        return betrokkene;
    }

    @Override
    public BetrokkenheidOuderlijkGezagGroepMdl getBetrokkenheidOuderlijkGezag() {
        return betrokkenheidOuderlijkGezag;
    }

    @Override
    public BetrokkenheidOuderschapGroepMdl getBetrokkenheidOuderschap() {
        return betrokkenheidOuderschap;
    }

    protected void setRol(final SoortBetrokkenheid rol) {
        this.rol = rol;
    }

    protected void setRelatie(final RelatieMdl relatie) {
        this.relatie = relatie;
    }

    protected void setBetrokkene(final PersoonMdl betrokkene) {
        this.betrokkene = betrokkene;
    }

    protected void setBetrokkenheidOuderlijkGezag(final BetrokkenheidOuderlijkGezagGroepMdl betrokkenheidOuderlijkGezag) {
        this.betrokkenheidOuderlijkGezag = betrokkenheidOuderlijkGezag;
    }

    protected void setBetrokkenheidOuderschap(final BetrokkenheidOuderschapGroepMdl betrokkenheidOuderschap) {
        this.betrokkenheidOuderschap = betrokkenheidOuderschap;
    }
}

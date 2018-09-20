/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.impl.gen;

import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

import nl.bzk.brp.model.basis.AbstractDynamischObjectType;
import nl.bzk.brp.model.groep.impl.usr.RelatieStandaardGroepMdl;
import nl.bzk.brp.model.objecttype.impl.usr.BetrokkenheidMdl;
import nl.bzk.brp.model.objecttype.interfaces.gen.RelatieBasis;
import nl.bzk.brp.model.objecttype.statisch.SoortRelatie;


/**
 * Implementatie voor objecttyp relatie.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractRelatieMdl extends AbstractDynamischObjectType implements RelatieBasis {

    @Id
    @SequenceGenerator(name = "RELATIE", sequenceName = "Kern.seq_Relatie")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RELATIE")
    private Long                  id;

    @Column(name = "Srt")
    @Enumerated
    @NotNull
    private SoortRelatie          soort;

    @Embedded
    @NotNull
    private RelatieStandaardGroepMdl gegevens;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "relatie")
    private Set<BetrokkenheidMdl> betrokkenheden;

    public Long getId() {
        return id;
    }

    @Override
    public SoortRelatie getSoort() {
        return soort;
    }

    @Override
    public RelatieStandaardGroepMdl getGegevens() {
        return gegevens;
    }

    @Override
    public Set<BetrokkenheidMdl> getBetrokkenheden() {
        return betrokkenheden;
    }

    protected void setSoort(final SoortRelatie soort) {
        this.soort = soort;
    }

    protected void setGegevens(final RelatieStandaardGroepMdl gegevens) {
        this.gegevens = gegevens;
    }

    protected void setBetrokkenheden(final Set<BetrokkenheidMdl> betrokkenheden) {
        this.betrokkenheden = betrokkenheden;
    }
}

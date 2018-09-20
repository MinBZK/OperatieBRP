/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel.basis;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EnumType;
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
import nl.bzk.brp.model.groep.operationeel.actueel.RelatieStandaardGroepModel;
import nl.bzk.brp.model.objecttype.logisch.basis.RelatieBasis;
import nl.bzk.brp.model.objecttype.operationeel.BetrokkenheidModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortRelatie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.StatusHistorie;


/**
 * Implementatie voor objecttyp relatie.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractRelatieModel extends AbstractDynamischObjectType implements RelatieBasis {

    @Id
    @SequenceGenerator(name = "RELATIE", sequenceName = "Kern.seq_Relatie")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RELATIE")
    private Integer                    id;

    @Column(name = "Srt")
    @Enumerated
    @NotNull
    private SoortRelatie               soort;

    @Embedded
    private RelatieStandaardGroepModel gegevens;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "relatie")
    private Set<BetrokkenheidModel>    betrokkenheden;

    @Column(name = "RelatieStatusHis")
    @Enumerated(value = EnumType.STRING)
    @NotNull
    private StatusHistorie             statusHistorie;

    /**
     * Copy constructor. Om een model object te construeren uit een web object.
     *
     * @param relatie Object type dat gekopieerd dient te worden.
     */
    protected AbstractRelatieModel(final RelatieBasis relatie) {
        super(relatie);
        soort = relatie.getSoort();
        initLegeStatusHistorie();
        if (relatie.getGegevens() != null) {
            gegevens = new RelatieStandaardGroepModel(relatie.getGegevens());
            statusHistorie = StatusHistorie.A;
        }

        // LET OP!! GEEN Kopie actie voor betrokkenheden!!
        betrokkenheden = new HashSet<BetrokkenheidModel>();
    }

    /**
     * default cons.
     */
    protected AbstractRelatieModel() {
        initLegeStatusHistorie();
    }

    /**
     * initieer alle sttaushistories op waarde X.
     */
    private void initLegeStatusHistorie() {
        statusHistorie = StatusHistorie.X;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public SoortRelatie getSoort() {
        return soort;
    }

    @Override
    public RelatieStandaardGroepModel getGegevens() {
        return gegevens;
    }

    @Override
    public Set<BetrokkenheidModel> getBetrokkenheden() {
        return betrokkenheden;
    }

    protected void setSoort(final SoortRelatie soort) {
        this.soort = soort;
    }

    protected void setGegevens(final RelatieStandaardGroepModel gegevens) {
        this.gegevens = gegevens;
    }

    protected void setBetrokkenheden(final Set<BetrokkenheidModel> betrokkenheden) {
        this.betrokkenheden = betrokkenheden;
    }

    public StatusHistorie getStatusHistorie() {
        return statusHistorie;
    }

}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.objecttype.operationeel.basis;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import nl.bzk.copy.model.basis.AbstractDynamischObjectType;
import nl.bzk.copy.model.groep.operationeel.actueel.BetrokkenheidOuderlijkGezagGroepModel;
import nl.bzk.copy.model.groep.operationeel.actueel.BetrokkenheidOuderschapGroepModel;
import nl.bzk.copy.model.objecttype.logisch.basis.BetrokkenheidBasis;
import nl.bzk.copy.model.objecttype.operationeel.PersoonModel;
import nl.bzk.copy.model.objecttype.operationeel.RelatieModel;
import nl.bzk.copy.model.objecttype.operationeel.statisch.SoortBetrokkenheid;
import nl.bzk.copy.model.objecttype.operationeel.statisch.StatusHistorie;
import nl.bzk.copy.model.validatie.MeldingCode;
import nl.bzk.copy.model.validatie.constraint.ConditioneelVerplichtVeld;
import nl.bzk.copy.model.validatie.constraint.ConditioneelVerplichteVelden;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * Implementatie voor objecttype betrokkenheid.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
@ConditioneelVerplichteVelden({
                                      @ConditioneelVerplichtVeld(
                                              naamVerplichtVeld = "betrokkenheidOuderschap.datumAanvang",
                                              naamAfhankelijkVeld = "rol.code", waardeAfhankelijkVeld = "K",
                                              verplichtNull = true, code = MeldingCode.BRAL0203,
                                              message = "BRAL0203_datumAanvang"),
                                      @ConditioneelVerplichtVeld(
                                              naamVerplichtVeld = "betrokkenheidOuderschap.indAdresGevend",
                                              naamAfhankelijkVeld = "rol.code", waardeAfhankelijkVeld = "K",
                                              verplichtNull = true, code = MeldingCode.BRAL0203,
                                              message = "BRAL0203_indAdresGevend"),
                                      @ConditioneelVerplichtVeld(naamVerplichtVeld = "betrokkenheidOuderschap.indOuder",
                                                                 naamAfhankelijkVeld = "rol.code",
                                                                 waardeAfhankelijkVeld = "K", verplichtNull = true,
                                                                 code = MeldingCode.BRAL0203,
                                                                 message = "BRAL0203_indOuder")
                              })
public abstract class AbstractBetrokkenheidModel extends AbstractDynamischObjectType implements BetrokkenheidBasis {

    @Id
    @SequenceGenerator(name = "BETROKKENHEID", sequenceName = "Kern.seq_Betr")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BETROKKENHEID")
    private Integer id;

    @Column(name = "rol")
    @NotNull
    private SoortBetrokkenheid rol;

    @ManyToOne
    @JoinColumn(name = "relatie")
    @NotNull
    @Fetch(FetchMode.JOIN)
    private RelatieModel relatie;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "pers")
    @NotNull
    private PersoonModel betrokkene;

    @Embedded
    private BetrokkenheidOuderlijkGezagGroepModel betrokkenheidOuderlijkGezag;

    @Embedded
    private BetrokkenheidOuderschapGroepModel betrokkenheidOuderschap;

    @Column(name = "OuderlijkgezagstatusHis")
    @Enumerated(value = EnumType.STRING)
    @NotNull
    private StatusHistorie ouderlijkGezagStatusHistorie;

    @Column(name = "Ouderschapstatushis")
    @Enumerated(value = EnumType.STRING)
    @NotNull
    private StatusHistorie ouderschapStatusHistorie;


    /**
     * Copy constructor. Om een model object te construeren uit een web object.
     *
     * @param betr    Object type dat gekopieerd dient te worden.
     * @param pers    Betrokkene
     * @param relatie Relatie
     */
    protected AbstractBetrokkenheidModel(final BetrokkenheidBasis betr, final PersoonModel pers,
                                         final RelatieModel relatie)
    {
        super(betr);
        rol = betr.getRol();
        initLegeStatusHistorie();
        if (betr.getBetrokkenheidOuderlijkGezag() != null) {
            betrokkenheidOuderlijkGezag =
                    new BetrokkenheidOuderlijkGezagGroepModel(betr.getBetrokkenheidOuderlijkGezag());
            ouderlijkGezagStatusHistorie = StatusHistorie.A;
        }
        if (betr.getBetrokkenheidOuderschap() != null) {
            betrokkenheidOuderschap = new BetrokkenheidOuderschapGroepModel(betr.getBetrokkenheidOuderschap());
            ouderschapStatusHistorie = StatusHistorie.A;
        }
        betrokkene = pers;
        this.relatie = relatie;
    }

    /**
     * default cons.
     */
    protected AbstractBetrokkenheidModel() {
        initLegeStatusHistorie();
    }

    /**
     * initieer alle sttaushistories op waarde X.
     */
    private void initLegeStatusHistorie() {
        ouderlijkGezagStatusHistorie = StatusHistorie.X;
        ouderschapStatusHistorie = StatusHistorie.X;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public SoortBetrokkenheid getRol() {
        return rol;
    }

    @Override
    public RelatieModel getRelatie() {
        return relatie;
    }

    @Override
    public PersoonModel getBetrokkene() {
        return betrokkene;
    }

    @Override
    public BetrokkenheidOuderlijkGezagGroepModel getBetrokkenheidOuderlijkGezag() {
        return betrokkenheidOuderlijkGezag;
    }

    @Override
    public BetrokkenheidOuderschapGroepModel getBetrokkenheidOuderschap() {
        return betrokkenheidOuderschap;
    }

    public void setRol(final SoortBetrokkenheid rol) {
        this.rol = rol;
    }

    public void setRelatie(final RelatieModel relatie) {
        this.relatie = relatie;
    }

    public void setBetrokkene(final PersoonModel betrokkene) {
        this.betrokkene = betrokkene;
    }

    public void setBetrokkenheidOuderlijkGezag(final BetrokkenheidOuderlijkGezagGroepModel betrokkenheidOuderlijkGezag) {
        this.betrokkenheidOuderlijkGezag = betrokkenheidOuderlijkGezag;
    }

    public void setBetrokkenheidOuderschap(final BetrokkenheidOuderschapGroepModel betrokkenheidOuderschap) {
        this.betrokkenheidOuderschap = betrokkenheidOuderschap;
    }

    public StatusHistorie getOuderlijkGezagStatusHistorie() {
        return ouderlijkGezagStatusHistorie;
    }

    public StatusHistorie getOuderschapStatusHistorie() {
        return ouderschapStatusHistorie;
    }


    public void setId(final Integer id) {
        this.id = id;
    }


    public void setOuderlijkGezagStatusHistorie(final StatusHistorie ouderlijkGezagStatusHistorie) {
        this.ouderlijkGezagStatusHistorie = ouderlijkGezagStatusHistorie;
    }


    public void setOuderschapStatusHistorie(final StatusHistorie ouderschapStatusHistorie) {
        this.ouderschapStatusHistorie = ouderschapStatusHistorie;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final AbstractBetrokkenheidModel that = (AbstractBetrokkenheidModel) o;

        if (!id.equals(that.id)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

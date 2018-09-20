/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel.basis;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import nl.bzk.brp.model.basis.AbstractDynamischObjectType;
import nl.bzk.brp.model.basis.AbstractGroep;
import nl.bzk.brp.model.groep.operationeel.actueel.RelatieStandaardGroepModel;
import nl.bzk.brp.model.objecttype.logisch.basis.RelatieBasis;
import nl.bzk.brp.model.objecttype.operationeel.BetrokkenheidModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortRelatie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.StatusHistorie;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.constraint.ConditioneelVerplichtVeld;
import nl.bzk.brp.model.validatie.constraint.ConditioneelVerplichteVelden;


/**
 * Implementatie voor objecttyp relatie.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
@ConditioneelVerplichteVelden({
                                      @ConditioneelVerplichtVeld(naamVerplichtVeld = "gegevens.datumAanvang",
                                                                 naamAfhankelijkVeld = "soort.code",
                                                                 waardeAfhankelijkVeld = "F", verplichtNull = true,
                                                                 code = MeldingCode.BRAL0201,
                                                                 message = "BRAL0201_datumAanvang"),
                                      @ConditioneelVerplichtVeld(naamVerplichtVeld = "gegevens.datumEinde",
                                                                 naamAfhankelijkVeld = "soort.code",
                                                                 waardeAfhankelijkVeld = "F", verplichtNull = true,
                                                                 code = MeldingCode.BRAL0201,
                                                                 message = "BRAL0201_datumEinde"),
                                      @ConditioneelVerplichtVeld(naamVerplichtVeld = "gegevens.landAanvang",
                                                                 naamAfhankelijkVeld = "soort.code",
                                                                 waardeAfhankelijkVeld = "F", verplichtNull = true,
                                                                 code = MeldingCode.BRAL0201,
                                                                 message = "BRAL0201_landAanvang"),
                                      @ConditioneelVerplichtVeld(naamVerplichtVeld = "gegevens.landEinde",
                                                                 naamAfhankelijkVeld = "soort.code",
                                                                 waardeAfhankelijkVeld = "F", verplichtNull = true,
                                                                 code = MeldingCode.BRAL0201,
                                                                 message = "BRAL0201_landEinde"),
                                      @ConditioneelVerplichtVeld(naamVerplichtVeld = "gegevens.gemeenteAanvang",
                                                                 naamAfhankelijkVeld = "soort.code",
                                                                 waardeAfhankelijkVeld = "F", verplichtNull = true,
                                                                 code = MeldingCode.BRAL0201,
                                                                 message = "BRAL0201_gemeenteAanvang"),
                                      @ConditioneelVerplichtVeld(naamVerplichtVeld = "gegevens.gemeenteEinde",
                                                                 naamAfhankelijkVeld = "soort.code",
                                                                 waardeAfhankelijkVeld = "F", verplichtNull = true,
                                                                 code = MeldingCode.BRAL0201,
                                                                 message = "BRAL0201_gemeenteEinde"),
                                      @ConditioneelVerplichtVeld(naamVerplichtVeld = "gegevens.woonPlaatsAanvang",
                                                                 naamAfhankelijkVeld = "soort.code",
                                                                 waardeAfhankelijkVeld = "F", verplichtNull = true,
                                                                 code = MeldingCode.BRAL0201,
                                                                 message = "BRAL0201_woonPlaatsAanvang"),
                                      @ConditioneelVerplichtVeld(naamVerplichtVeld = "gegevens.woonPlaatsEinde",
                                                                 naamAfhankelijkVeld = "soort.code",
                                                                 waardeAfhankelijkVeld = "F", verplichtNull = true,
                                                                 code = MeldingCode.BRAL0201,
                                                                 message = "BRAL0201_woonPlaatsEinde"),
                                      @ConditioneelVerplichtVeld(
                                              naamVerplichtVeld = "gegevens.omschrijvingLocatieAanvang",
                                              naamAfhankelijkVeld = "soort.code", waardeAfhankelijkVeld = "F",
                                              verplichtNull = true, code = MeldingCode.BRAL0201,
                                              message = "BRAL0201_omschrijvingLocatieAanvang"),
                                      @ConditioneelVerplichtVeld(
                                              naamVerplichtVeld = "gegevens.omschrijvingLocatieEinde",
                                              naamAfhankelijkVeld = "soort.code", waardeAfhankelijkVeld = "F",
                                              verplichtNull = true, code = MeldingCode.BRAL0201,
                                              message = "BRAL0201_omschrijvingLocatieEinde"),
                                      @ConditioneelVerplichtVeld(
                                              naamVerplichtVeld = "gegevens.buitenlandsePlaatsAanvang",
                                              naamAfhankelijkVeld = "soort.code", waardeAfhankelijkVeld = "F",
                                              verplichtNull = true, code = MeldingCode.BRAL0201,
                                              message = "BRAL0201_buitenlandsePlaatsAanvang"),
                                      @ConditioneelVerplichtVeld(naamVerplichtVeld = "gegevens.buitenlandsePlaatsEinde",
                                                                 naamAfhankelijkVeld = "soort.code",
                                                                 waardeAfhankelijkVeld = "F", verplichtNull = true,
                                                                 code = MeldingCode.BRAL0201,
                                                                 message = "BRAL0201_buitenlandsePlaatsEinde"),
                                      @ConditioneelVerplichtVeld(
                                              naamVerplichtVeld = "gegevens.buitenlandseRegioAanvang",
                                              naamAfhankelijkVeld = "soort.code", waardeAfhankelijkVeld = "F",
                                              verplichtNull = true, code = MeldingCode.BRAL0201,
                                              message = "BRAL0201_buitenlandseRegioAanvang"),
                                      @ConditioneelVerplichtVeld(naamVerplichtVeld = "gegevens.buitenlandseRegioEinde",
                                                                 naamAfhankelijkVeld = "soort.code",
                                                                 waardeAfhankelijkVeld = "F", verplichtNull = true,
                                                                 code = MeldingCode.BRAL0201,
                                                                 message = "BRAL0201_buitenlandseRegioEinde"),
                                      @ConditioneelVerplichtVeld(naamVerplichtVeld = "gegevens.redenBeeindigingRelatie",
                                                                 naamAfhankelijkVeld = "soort.code",
                                                                 waardeAfhankelijkVeld = "F", verplichtNull = true,
                                                                 code = MeldingCode.BRAL0201,
                                                                 message = "BRAL0201_redenBeeindigingRelatie")

                              })
public abstract class AbstractRelatieModel extends AbstractDynamischObjectType implements RelatieBasis {

    @Id
    @SequenceGenerator(name = "RELATIE", sequenceName = "Kern.seq_Relatie")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "RELATIE")
    @JsonProperty
    private Integer id;

    @Column(name = "Srt")
    @Enumerated
    @NotNull
    @JsonProperty
    private SoortRelatie soort;

    @Embedded
    @JsonProperty
    private RelatieStandaardGroepModel gegevens;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "relatie")
    @OrderBy("id")
    private Set<BetrokkenheidModel> betrokkenheden;

    @Column(name = "RelatieStatusHis")
    @Enumerated(value = EnumType.STRING)
    @NotNull
    @JsonProperty
    private StatusHistorie statusHistorie;

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

    public void vervangGroepen(final AbstractGroep... groepen) {
        if (groepen != null) {
            for (AbstractGroep groep : groepen) {
                if (groep instanceof RelatieStandaardGroepModel) {
                    gegevens = new RelatieStandaardGroepModel((RelatieStandaardGroepModel) groep);
                    statusHistorie = StatusHistorie.A;
                } else {
                    // adres heeft alleen maar 1 groep. Dus eigenlijk hoeven we geen list mee te geven.
                    throw new IllegalArgumentException(
                            "Groep van type " + groep.getClass().getName() + " wordt hier niet ondersteund");
                }
            }
        }
    }
}

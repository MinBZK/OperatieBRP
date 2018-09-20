/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.kern;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Generated;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import nl.bzk.brp.model.FormeleHistorieSet;
import nl.bzk.brp.model.FormeleHistorieSetImpl;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatieAttribuut;
import nl.bzk.brp.model.basis.ALaagAfleidbaar;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.RelatieHisVolledigBasis;
import nl.bzk.brp.model.operationeel.kern.HisRelatieModel;
import nl.bzk.brp.model.operationeel.kern.RelatieStandaardGroepModel;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * HisVolledig klasse voor Relatie.
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigModelGenerator")
@MappedSuperclass
public abstract class AbstractRelatieHisVolledigImpl implements HisVolledigImpl, RelatieHisVolledigBasis, ALaagAfleidbaar, ElementIdentificeerbaar {

    @Transient
    @JsonProperty
    private Integer iD;

    @Embedded
    @AttributeOverride(name = SoortRelatieAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Srt", updatable = false, insertable = false))
    @JsonProperty
    private SoortRelatieAttribuut soort;

    @Embedded
    private RelatieStandaardGroepModel standaard;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "relatie", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    @JsonProperty
    @JsonManagedReference
    private Set<HisRelatieModel> hisRelatieLijst;

    @Transient
    private FormeleHistorieSet<HisRelatieModel> relatieHistorie;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "relatie", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    private Set<BetrokkenheidHisVolledigImpl> betrokkenheden;

    /**
     * Default contructor voor JPA.
     *
     */
    protected AbstractRelatieHisVolledigImpl() {
        betrokkenheden = new HashSet<BetrokkenheidHisVolledigImpl>();

    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param soort soort van Relatie.
     */
    public AbstractRelatieHisVolledigImpl(final SoortRelatieAttribuut soort) {
        this();
        this.soort = soort;

    }

    /**
     * Retourneert ID van Relatie.
     *
     * @return ID.
     */
    @Id
    @SequenceGenerator(name = "RELATIE", sequenceName = "Kern.seq_Relatie")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "RELATIE")
    @Access(value = AccessType.PROPERTY)
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Soort van Relatie.
     *
     * @return Soort.
     */
    public SoortRelatieAttribuut getSoort() {
        return soort;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<BetrokkenheidHisVolledigImpl> getBetrokkenheden() {
        return betrokkenheden;
    }

    /**
     * Setter is verplicht voor JPA, omdat de Id annotatie op de getter zit. We maken de functie private voor de
     * zekerheid.
     *
     * @param id Id
     */
    private void setID(final Integer id) {
        this.iD = id;
    }

    /**
     * Zet lijst van Betrokkenheid.
     *
     * @param betrokkenheden lijst van Betrokkenheid
     */
    public void setBetrokkenheden(final Set<BetrokkenheidHisVolledigImpl> betrokkenheden) {
        this.betrokkenheden = betrokkenheden;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void leidALaagAf() {
        final HisRelatieModel actueelStandaard = getRelatieHistorie().getActueleRecord();
        if (actueelStandaard != null) {
            this.standaard =
                    new RelatieStandaardGroepModel(
                        actueelStandaard.getDatumAanvang(),
                        actueelStandaard.getGemeenteAanvang(),
                        actueelStandaard.getWoonplaatsnaamAanvang(),
                        actueelStandaard.getBuitenlandsePlaatsAanvang(),
                        actueelStandaard.getBuitenlandseRegioAanvang(),
                        actueelStandaard.getOmschrijvingLocatieAanvang(),
                        actueelStandaard.getLandGebiedAanvang(),
                        actueelStandaard.getRedenEinde(),
                        actueelStandaard.getDatumEinde(),
                        actueelStandaard.getGemeenteEinde(),
                        actueelStandaard.getWoonplaatsnaamEinde(),
                        actueelStandaard.getBuitenlandsePlaatsEinde(),
                        actueelStandaard.getBuitenlandseRegioEinde(),
                        actueelStandaard.getOmschrijvingLocatieEinde(),
                        actueelStandaard.getLandGebiedEinde());
        } else {
            this.standaard = null;
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FormeleHistorieSet<HisRelatieModel> getRelatieHistorie() {
        if (hisRelatieLijst == null) {
            hisRelatieLijst = new HashSet<>();
        }
        if (relatieHistorie == null) {
            relatieHistorie = new FormeleHistorieSetImpl<HisRelatieModel>(hisRelatieLijst);
        }
        return relatieHistorie;
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public ElementEnum getElementIdentificatie() {
        return ElementEnum.RELATIE;
    }

}

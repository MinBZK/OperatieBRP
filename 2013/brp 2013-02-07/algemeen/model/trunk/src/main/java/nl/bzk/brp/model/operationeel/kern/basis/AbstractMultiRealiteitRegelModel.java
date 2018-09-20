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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.StatusHistorie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortMultiRealiteitRegel;
import nl.bzk.brp.model.basis.AbstractDynamischObjectType;
import nl.bzk.brp.model.logisch.kern.basis.MultiRealiteitRegelBasis;
import nl.bzk.brp.model.operationeel.kern.BetrokkenheidModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.operationeel.kern.RelatieModel;
import org.hibernate.annotations.Type;


/**
 * De regel waarmee de Multirealiteit wordt vastgelegd.
 *
 * De BRP bevat normaal gesproken op elk moment in de tijd ��n consistent beeld van heden en verleden van de
 * werkelijkheid. In uitzonderlijke gevallen dient de BRP in staat te zijn verschillende, onderling conflicterende,
 * werkelijkheden vast te leggen.
 * Dit gebeurt door de constructie van Multirealiteit.
 * Zie voor verdere toelichting het LO BRP.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@MappedSuperclass
public abstract class AbstractMultiRealiteitRegelModel extends AbstractDynamischObjectType implements
        MultiRealiteitRegelBasis
{

    @Id
    @SequenceGenerator(name = "MULTIREALITEITREGEL", sequenceName = "Kern.seq_MultiRealiteitRegel")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "MULTIREALITEITREGEL")
    @JsonProperty
    private Integer                  iD;

    @ManyToOne
    @JoinColumn(name = "GeldigVoor")
    @JsonProperty
    private PersoonModel             geldigVoor;

    @Enumerated
    @Column(name = "Srt")
    @JsonProperty
    private SoortMultiRealiteitRegel soort;

    @ManyToOne
    @JoinColumn(name = "Pers")
    @JsonProperty
    private PersoonModel             persoon;

    @ManyToOne
    @JoinColumn(name = "MultiRealiteitPers")
    @JsonProperty
    private PersoonModel             multiRealiteitPersoon;

    @ManyToOne
    @JoinColumn(name = "Relatie")
    @JsonProperty
    private RelatieModel             relatie;

    @ManyToOne
    @JoinColumn(name = "Betr")
    @JsonProperty
    private BetrokkenheidModel       betrokkenheid;

    @Type(type = "StatusHistorie")
    @Column(name = "MultiRealiteitRegelStatusHis")
    @JsonProperty
    private StatusHistorie           multiRealiteitRegelStatusHis;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected AbstractMultiRealiteitRegelModel() {
        this.multiRealiteitRegelStatusHis = StatusHistorie.X;

    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param geldigVoor geldigVoor van Multi-realiteit regel.
     * @param soort soort van Multi-realiteit regel.
     * @param persoon persoon van Multi-realiteit regel.
     * @param multiRealiteitPersoon multiRealiteitPersoon van Multi-realiteit regel.
     * @param relatie relatie van Multi-realiteit regel.
     * @param betrokkenheid betrokkenheid van Multi-realiteit regel.
     */
    public AbstractMultiRealiteitRegelModel(final PersoonModel geldigVoor, final SoortMultiRealiteitRegel soort,
            final PersoonModel persoon, final PersoonModel multiRealiteitPersoon, final RelatieModel relatie,
            final BetrokkenheidModel betrokkenheid)
    {
        this();
        this.geldigVoor = geldigVoor;
        this.soort = soort;
        this.persoon = persoon;
        this.multiRealiteitPersoon = multiRealiteitPersoon;
        this.relatie = relatie;
        this.betrokkenheid = betrokkenheid;

    }

    /**
     * Retourneert ID van Multi-realiteit regel.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Geldig voor van Multi-realiteit regel.
     *
     * @return Geldig voor.
     */
    public PersoonModel getGeldigVoor() {
        return geldigVoor;
    }

    /**
     * Retourneert Soort van Multi-realiteit regel.
     *
     * @return Soort.
     */
    public SoortMultiRealiteitRegel getSoort() {
        return soort;
    }

    /**
     * Retourneert Persoon van Multi-realiteit regel.
     *
     * @return Persoon.
     */
    public PersoonModel getPersoon() {
        return persoon;
    }

    /**
     * Retourneert Multi-realiteit persoon van Multi-realiteit regel.
     *
     * @return Multi-realiteit persoon.
     */
    public PersoonModel getMultiRealiteitPersoon() {
        return multiRealiteitPersoon;
    }

    /**
     * Retourneert Relatie van Multi-realiteit regel.
     *
     * @return Relatie.
     */
    public RelatieModel getRelatie() {
        return relatie;
    }

    /**
     * Retourneert Betrokkenheid van Multi-realiteit regel.
     *
     * @return Betrokkenheid.
     */
    public BetrokkenheidModel getBetrokkenheid() {
        return betrokkenheid;
    }

    /**
     * Retourneert Multi-realiteit regel StatusHis van Multi-realiteit regel.
     *
     * @return Multi-realiteit regel StatusHis.
     */
    public StatusHistorie getMultiRealiteitRegelStatusHis() {
        return multiRealiteitRegelStatusHis;
    }

    /**
     * Zet Multi-realiteit regel StatusHis van Multi-realiteit regel.
     *
     * @param multiRealiteitRegelStatusHis Multi-realiteit regel StatusHis.
     */
    public void setMultiRealiteitRegelStatusHis(final StatusHistorie multiRealiteitRegelStatusHis) {
        this.multiRealiteitRegelStatusHis = multiRealiteitRegelStatusHis;
    }

}

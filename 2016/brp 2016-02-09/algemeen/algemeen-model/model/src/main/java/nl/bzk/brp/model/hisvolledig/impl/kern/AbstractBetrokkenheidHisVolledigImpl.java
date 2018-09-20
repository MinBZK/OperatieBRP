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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import nl.bzk.brp.model.FormeleHistorieSet;
import nl.bzk.brp.model.FormeleHistorieSetImpl;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheidAttribuut;
import nl.bzk.brp.model.basis.ALaagAfleidbaar;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.BetrokkenheidHisVolledigBasis;
import nl.bzk.brp.model.operationeel.kern.HisBetrokkenheidModel;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * HisVolledig klasse voor Betrokkenheid.
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigModelGenerator")
@MappedSuperclass
public abstract class AbstractBetrokkenheidHisVolledigImpl implements HisVolledigImpl, BetrokkenheidHisVolledigBasis, ALaagAfleidbaar,
        ElementIdentificeerbaar
{

    @Transient
    @JsonProperty
    private Integer iD;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "Relatie")
    @JsonProperty
    private RelatieHisVolledigImpl relatie;

    @Embedded
    @AttributeOverride(name = SoortBetrokkenheidAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Rol", updatable = false, insertable = false))
    @JsonProperty
    private SoortBetrokkenheidAttribuut rol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Pers")
//    Handmatige aanpassing
//    @JsonBackReference
    private PersoonHisVolledigImpl persoon;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "betrokkenheid", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    @JsonProperty
    @JsonManagedReference
    private Set<HisBetrokkenheidModel> hisBetrokkenheidLijst;

    @Transient
    private FormeleHistorieSet<HisBetrokkenheidModel> betrokkenheidHistorie;

    /**
     * Default contructor voor JPA.
     *
     */
    protected AbstractBetrokkenheidHisVolledigImpl() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param relatie relatie van Betrokkenheid.
     * @param rol rol van Betrokkenheid.
     * @param persoon persoon van Betrokkenheid.
     */
    public AbstractBetrokkenheidHisVolledigImpl(
        final RelatieHisVolledigImpl relatie,
        final SoortBetrokkenheidAttribuut rol,
        final PersoonHisVolledigImpl persoon)
    {
        this();
        this.relatie = relatie;
        this.rol = rol;
        this.persoon = persoon;

    }

    /**
     * Retourneert ID van Betrokkenheid.
     *
     * @return ID.
     */
    @Id
    @SequenceGenerator(name = "BETROKKENHEID", sequenceName = "Kern.seq_Betr")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "BETROKKENHEID")
    @Access(value = AccessType.PROPERTY)
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Relatie van Betrokkenheid.
     *
     * @return Relatie.
     */
    public RelatieHisVolledigImpl getRelatie() {
        return relatie;
    }

    /**
     * Retourneert Rol van Betrokkenheid.
     *
     * @return Rol.
     */
    public SoortBetrokkenheidAttribuut getRol() {
        return rol;
    }

    /**
     * Retourneert Persoon van Betrokkenheid.
     *
     * @return Persoon.
     */
    public PersoonHisVolledigImpl getPersoon() {
        return persoon;
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
     * Zet Relatie van Betrokkenheid.
     *
     * @param relatie Relatie.
     */
    public void setRelatie(final RelatieHisVolledigImpl relatie) {
        this.relatie = relatie;
    }

    /**
     * Zet Persoon van Betrokkenheid.
     *
     * @param persoon Persoon.
     */
    public void setPersoon(final PersoonHisVolledigImpl persoon) {
        this.persoon = persoon;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void leidALaagAf() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FormeleHistorieSet<HisBetrokkenheidModel> getBetrokkenheidHistorie() {
        if (hisBetrokkenheidLijst == null) {
            hisBetrokkenheidLijst = new HashSet<>();
        }
        if (betrokkenheidHistorie == null) {
            betrokkenheidHistorie = new FormeleHistorieSetImpl<HisBetrokkenheidModel>(hisBetrokkenheidLijst);
        }
        return betrokkenheidHistorie;
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public ElementEnum getElementIdentificatie() {
        return ElementEnum.BETROKKENHEID;
    }

}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.autaut;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Generated;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AssociationOverride;
import javax.persistence.CascadeType;
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
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.LeveringsautorisatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.basis.ALaagAfleidbaar;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.autaut.PersoonAfnemerindicatieHisVolledigBasis;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.operationeel.autaut.HisPersoonAfnemerindicatieModel;
import nl.bzk.brp.model.operationeel.autaut.PersoonAfnemerindicatieStandaardGroepModel;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * HisVolledig klasse voor Persoon \ Afnemerindicatie.
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigModelGenerator")
@MappedSuperclass
public abstract class AbstractPersoonAfnemerindicatieHisVolledigImpl implements HisVolledigImpl, PersoonAfnemerindicatieHisVolledigBasis, ALaagAfleidbaar {

    @Transient
    @JsonProperty
    private Long iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Pers")
    @JsonBackReference
    private PersoonHisVolledigImpl persoon;

    @Embedded
    @AssociationOverride(name = PartijAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "Afnemer"))
    @JsonProperty
    private PartijAttribuut afnemer;

    @Embedded
    @AssociationOverride(name = LeveringsautorisatieAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "Levsautorisatie"))
    @JsonProperty
    private LeveringsautorisatieAttribuut leveringsautorisatie;

    @Embedded
    private PersoonAfnemerindicatieStandaardGroepModel standaard;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persoonAfnemerindicatie", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    @JsonProperty
    @JsonManagedReference
    private Set<HisPersoonAfnemerindicatieModel> hisPersoonAfnemerindicatieLijst;

    @Transient
    private FormeleHistorieSet<HisPersoonAfnemerindicatieModel> persoonAfnemerindicatieHistorie;

    /**
     * Default contructor voor JPA.
     *
     */
    protected AbstractPersoonAfnemerindicatieHisVolledigImpl() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param persoon persoon van Persoon \ Afnemerindicatie.
     * @param afnemer afnemer van Persoon \ Afnemerindicatie.
     * @param leveringsautorisatie leveringsautorisatie van Persoon \ Afnemerindicatie.
     */
    public AbstractPersoonAfnemerindicatieHisVolledigImpl(
        final PersoonHisVolledigImpl persoon,
        final PartijAttribuut afnemer,
        final LeveringsautorisatieAttribuut leveringsautorisatie)
    {
        this();
        this.persoon = persoon;
        this.afnemer = afnemer;
        this.leveringsautorisatie = leveringsautorisatie;

    }

    /**
     * Retourneert ID van Persoon \ Afnemerindicatie.
     *
     * @return ID.
     */
    @Id
    @SequenceGenerator(name = "PERSOONAFNEMERINDICATIE", sequenceName = "AutAut.seq_PersAfnemerindicatie")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PERSOONAFNEMERINDICATIE")
    @Access(value = AccessType.PROPERTY)
    public Long getID() {
        return iD;
    }

    /**
     * Retourneert Persoon van Persoon \ Afnemerindicatie.
     *
     * @return Persoon.
     */
    public PersoonHisVolledigImpl getPersoon() {
        return persoon;
    }

    /**
     * Retourneert Afnemer van Persoon \ Afnemerindicatie.
     *
     * @return Afnemer.
     */
    public PartijAttribuut getAfnemer() {
        return afnemer;
    }

    /**
     * Retourneert Leveringsautorisatie van Persoon \ Afnemerindicatie.
     *
     * @return Leveringsautorisatie.
     */
    public LeveringsautorisatieAttribuut getLeveringsautorisatie() {
        return leveringsautorisatie;
    }

    /**
     * Setter is verplicht voor JPA, omdat de Id annotatie op de getter zit. We maken de functie private voor de
     * zekerheid.
     *
     * @param id Id
     */
    private void setID(final Long id) {
        this.iD = id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void leidALaagAf() {
        final HisPersoonAfnemerindicatieModel actueelStandaard = getPersoonAfnemerindicatieHistorie().getActueleRecord();
        if (actueelStandaard != null) {
            this.standaard =
                    new PersoonAfnemerindicatieStandaardGroepModel(
                        actueelStandaard.getDatumAanvangMaterielePeriode(),
                        actueelStandaard.getDatumEindeVolgen());
        } else {
            this.standaard = null;
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FormeleHistorieSet<HisPersoonAfnemerindicatieModel> getPersoonAfnemerindicatieHistorie() {
        if (hisPersoonAfnemerindicatieLijst == null) {
            hisPersoonAfnemerindicatieLijst = new HashSet<>();
        }
        if (persoonAfnemerindicatieHistorie == null) {
            persoonAfnemerindicatieHistorie = new FormeleHistorieSetImpl<HisPersoonAfnemerindicatieModel>(hisPersoonAfnemerindicatieLijst);
        }
        return persoonAfnemerindicatieHistorie;
    }

}

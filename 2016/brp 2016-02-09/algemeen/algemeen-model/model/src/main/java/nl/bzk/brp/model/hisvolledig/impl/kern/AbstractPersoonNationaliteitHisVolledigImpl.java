/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.kern;

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
import nl.bzk.brp.model.MaterieleHistorieSet;
import nl.bzk.brp.model.MaterieleHistorieSetImpl;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NationaliteitAttribuut;
import nl.bzk.brp.model.basis.ALaagAfleidbaar;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonNationaliteitHisVolledigBasis;
import nl.bzk.brp.model.operationeel.kern.HisPersoonNationaliteitModel;
import nl.bzk.brp.model.operationeel.kern.PersoonNationaliteitStandaardGroepModel;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * HisVolledig klasse voor Persoon \ Nationaliteit.
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigModelGenerator")
@MappedSuperclass
public abstract class AbstractPersoonNationaliteitHisVolledigImpl implements HisVolledigImpl, PersoonNationaliteitHisVolledigBasis, ALaagAfleidbaar,
        ElementIdentificeerbaar
{

    @Transient
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Pers")
    @JsonBackReference
    private PersoonHisVolledigImpl persoon;

    @Embedded
    @AssociationOverride(name = NationaliteitAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "Nation"))
    @JsonProperty
    private NationaliteitAttribuut nationaliteit;

    @Embedded
    private PersoonNationaliteitStandaardGroepModel standaard;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persoonNationaliteit", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    @JsonProperty
    @JsonManagedReference
    private Set<HisPersoonNationaliteitModel> hisPersoonNationaliteitLijst;

    @Transient
    private MaterieleHistorieSet<HisPersoonNationaliteitModel> persoonNationaliteitHistorie;

    /**
     * Default contructor voor JPA.
     *
     */
    protected AbstractPersoonNationaliteitHisVolledigImpl() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param persoon persoon van Persoon \ Nationaliteit.
     * @param nationaliteit nationaliteit van Persoon \ Nationaliteit.
     */
    public AbstractPersoonNationaliteitHisVolledigImpl(final PersoonHisVolledigImpl persoon, final NationaliteitAttribuut nationaliteit) {
        this();
        this.persoon = persoon;
        this.nationaliteit = nationaliteit;

    }

    /**
     * Retourneert ID van Persoon \ Nationaliteit.
     *
     * @return ID.
     */
    @Id
    @SequenceGenerator(name = "PERSOONNATIONALITEIT", sequenceName = "Kern.seq_PersNation")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PERSOONNATIONALITEIT")
    @Access(value = AccessType.PROPERTY)
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Persoon van Persoon \ Nationaliteit.
     *
     * @return Persoon.
     */
    public PersoonHisVolledigImpl getPersoon() {
        return persoon;
    }

    /**
     * Retourneert Nationaliteit van Persoon \ Nationaliteit.
     *
     * @return Nationaliteit.
     */
    public NationaliteitAttribuut getNationaliteit() {
        return nationaliteit;
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
     * {@inheritDoc}
     */
    @Override
    public void leidALaagAf() {
        final HisPersoonNationaliteitModel actueelStandaard = getPersoonNationaliteitHistorie().getActueleRecord();
        if (actueelStandaard != null) {
            this.standaard =
                    new PersoonNationaliteitStandaardGroepModel(
                        actueelStandaard.getRedenVerkrijging(),
                        actueelStandaard.getRedenVerlies(),
                        actueelStandaard.getIndicatieBijhoudingBeeindigd(),
                        actueelStandaard.getMigratieRedenOpnameNationaliteit(),
                        actueelStandaard.getMigratieRedenBeeindigenNationaliteit(),
                        actueelStandaard.getMigratieDatumEindeBijhouding());
        } else {
            this.standaard = null;
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MaterieleHistorieSet<HisPersoonNationaliteitModel> getPersoonNationaliteitHistorie() {
        if (hisPersoonNationaliteitLijst == null) {
            hisPersoonNationaliteitLijst = new HashSet<>();
        }
        if (persoonNationaliteitHistorie == null) {
            persoonNationaliteitHistorie = new MaterieleHistorieSetImpl<HisPersoonNationaliteitModel>(hisPersoonNationaliteitLijst);
        }
        return persoonNationaliteitHistorie;
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.PERSOON_NATIONALITEIT;
    }

}

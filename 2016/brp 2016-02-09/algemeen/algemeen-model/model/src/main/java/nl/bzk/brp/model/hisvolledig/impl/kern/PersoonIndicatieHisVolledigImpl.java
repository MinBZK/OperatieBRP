/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.kern;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import nl.bzk.brp.model.HistorieSet;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatieAttribuut;
import nl.bzk.brp.model.basis.ALaagAfleidbaar;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonIndicatieHisVolledig;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieModel;
import nl.bzk.brp.model.operationeel.kern.PersoonIndicatieStandaardGroepModel;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 * HisVolledig klasse voor Persoon \ Indicatie.
 */
@Entity
@Table(schema = "Kern", name = "PersIndicatie")
@Access(value = AccessType.FIELD)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Srt", discriminatorType = DiscriminatorType.INTEGER)
public abstract class PersoonIndicatieHisVolledigImpl<T extends HisPersoonIndicatieModel> implements HisVolledigImpl,
    PersoonIndicatieHisVolledig<T>, ALaagAfleidbaar, ElementIdentificeerbaar
{

    @Transient
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Pers")
    @JsonBackReference
    private PersoonHisVolledigImpl persoon;

    @Embedded
    @AttributeOverride(name = SoortIndicatieAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Srt", updatable = false, insertable = false))
    @JsonProperty
    private SoortIndicatieAttribuut soort;

    @Embedded
    private PersoonIndicatieStandaardGroepModel standaard;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persoonIndicatie", cascade = CascadeType.ALL, orphanRemoval = true,
        targetEntity = HisPersoonIndicatieModel.class)
    @Fetch(value = FetchMode.SELECT)
    @JsonProperty
    @JsonManagedReference
    protected Set<T> hisPersoonIndicatieLijst;

    @Transient
    protected HistorieSet<T> persoonIndicatieHistorie;

    /**
     * Default contructor voor JPA.
     */
    protected PersoonIndicatieHisVolledigImpl() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param persoon persoon van Persoon \ Indicatie.
     * @param soort   soort van Persoon \ Indicatie.
     */
    public PersoonIndicatieHisVolledigImpl(final PersoonHisVolledigImpl persoon, final SoortIndicatieAttribuut soort) {
        this();
        this.persoon = persoon;
        this.soort = soort;

    }

    /**
     * Retourneert ID van Persoon \ Indicatie.
     *
     * @return ID.
     */
    @Override
    @Id
    @SequenceGenerator(name = "PERSOONINDICATIE", sequenceName = "Kern.seq_PersIndicatie")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PERSOONINDICATIE")
    @Access(value = AccessType.PROPERTY)
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Persoon van Persoon \ Indicatie.
     *
     * @return Persoon.
     */
    @Override
    public PersoonHisVolledigImpl getPersoon() {
        return persoon;
    }

    /**
     * Retourneert Soort van Persoon \ Indicatie.
     *
     * @return Soort.
     */
    @Override
    public SoortIndicatieAttribuut getSoort() {
        return soort;
    }

    /**
     * Setter is verplicht voor JPA, omdat de Id annotatie op de getter zit. We maken de functie private voor de zekerheid.
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
        final HisPersoonIndicatieModel actueelStandaard = getPersoonIndicatieHistorie().getActueleRecord();
        if (actueelStandaard != null) {
            this.standaard = new PersoonIndicatieStandaardGroepModel(actueelStandaard.getWaarde(),
                actueelStandaard.getMigratieRedenOpnameNationaliteit(), actueelStandaard.getMigratieRedenBeeindigenNationaliteit());
        } else {
            this.standaard = null;
        }

    }

    @Override
    public abstract HistorieSet<T> getPersoonIndicatieHistorie();

    @Override
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.PERSOON_INDICATIE;
    }
}

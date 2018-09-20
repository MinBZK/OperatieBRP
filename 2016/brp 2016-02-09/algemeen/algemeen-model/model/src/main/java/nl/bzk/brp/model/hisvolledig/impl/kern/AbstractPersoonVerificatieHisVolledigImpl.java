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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.basis.ALaagAfleidbaar;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonVerificatieHisVolledigBasis;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVerificatieModel;
import nl.bzk.brp.model.operationeel.kern.PersoonVerificatieStandaardGroepModel;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * HisVolledig klasse voor Persoon \ Verificatie.
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigModelGenerator")
@MappedSuperclass
public abstract class AbstractPersoonVerificatieHisVolledigImpl implements HisVolledigImpl, PersoonVerificatieHisVolledigBasis, ALaagAfleidbaar,
        ElementIdentificeerbaar
{

    @Transient
    @JsonProperty
    private Long iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Geverifieerde")
    @JsonBackReference
    private PersoonHisVolledigImpl geverifieerde;

    @Embedded
    @AssociationOverride(name = PartijAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "Partij"))
    @JsonProperty
    private PartijAttribuut partij;

    @Embedded
    @AttributeOverride(name = NaamEnumeratiewaardeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Srt"))
    @JsonProperty
    private NaamEnumeratiewaardeAttribuut soort;

    @Embedded
    private PersoonVerificatieStandaardGroepModel standaard;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persoonVerificatie", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    @JsonProperty
    @JsonManagedReference
    private Set<HisPersoonVerificatieModel> hisPersoonVerificatieLijst;

    @Transient
    private FormeleHistorieSet<HisPersoonVerificatieModel> persoonVerificatieHistorie;

    /**
     * Default contructor voor JPA.
     *
     */
    protected AbstractPersoonVerificatieHisVolledigImpl() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param geverifieerde geverifieerde van Persoon \ Verificatie.
     * @param partij partij van Persoon \ Verificatie.
     * @param soort soort van Persoon \ Verificatie.
     */
    public AbstractPersoonVerificatieHisVolledigImpl(
        final PersoonHisVolledigImpl geverifieerde,
        final PartijAttribuut partij,
        final NaamEnumeratiewaardeAttribuut soort)
    {
        this();
        this.geverifieerde = geverifieerde;
        this.partij = partij;
        this.soort = soort;

    }

    /**
     * Retourneert ID van Persoon \ Verificatie.
     *
     * @return ID.
     */
    @Id
    @SequenceGenerator(name = "PERSOONVERIFICATIE", sequenceName = "Kern.seq_PersVerificatie")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PERSOONVERIFICATIE")
    @Access(value = AccessType.PROPERTY)
    public Long getID() {
        return iD;
    }

    /**
     * Retourneert Geverifieerde van Persoon \ Verificatie.
     *
     * @return Geverifieerde.
     */
    public PersoonHisVolledigImpl getGeverifieerde() {
        return geverifieerde;
    }

    /**
     * Retourneert Partij van Persoon \ Verificatie.
     *
     * @return Partij.
     */
    public PartijAttribuut getPartij() {
        return partij;
    }

    /**
     * Retourneert Soort van Persoon \ Verificatie.
     *
     * @return Soort.
     */
    public NaamEnumeratiewaardeAttribuut getSoort() {
        return soort;
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
        final HisPersoonVerificatieModel actueelStandaard = getPersoonVerificatieHistorie().getActueleRecord();
        if (actueelStandaard != null) {
            this.standaard = new PersoonVerificatieStandaardGroepModel(actueelStandaard.getDatum());
        } else {
            this.standaard = null;
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FormeleHistorieSet<HisPersoonVerificatieModel> getPersoonVerificatieHistorie() {
        if (hisPersoonVerificatieLijst == null) {
            hisPersoonVerificatieLijst = new HashSet<>();
        }
        if (persoonVerificatieHistorie == null) {
            persoonVerificatieHistorie = new FormeleHistorieSetImpl<HisPersoonVerificatieModel>(hisPersoonVerificatieLijst);
        }
        return persoonVerificatieHistorie;
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.PERSOON_VERIFICATIE;
    }

}

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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.basis.ALaagAfleidbaar;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonVerstrekkingsbeperkingHisVolledigBasis;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVerstrekkingsbeperkingModel;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * HisVolledig klasse voor Persoon \ Verstrekkingsbeperking.
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigModelGenerator")
@MappedSuperclass
public abstract class AbstractPersoonVerstrekkingsbeperkingHisVolledigImpl implements HisVolledigImpl, PersoonVerstrekkingsbeperkingHisVolledigBasis,
        ALaagAfleidbaar, ElementIdentificeerbaar
{

    @Transient
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Pers")
    @JsonBackReference
    private PersoonHisVolledigImpl persoon;

    @Embedded
    @AssociationOverride(name = PartijAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "Partij"))
    @JsonProperty
    private PartijAttribuut partij;

    @Embedded
    @AttributeOverride(name = OmschrijvingEnumeratiewaardeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "OmsDerde"))
    @JsonProperty
    private OmschrijvingEnumeratiewaardeAttribuut omschrijvingDerde;

    @Embedded
    @AssociationOverride(name = PartijAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "GemVerordening"))
    @JsonProperty
    private PartijAttribuut gemeenteVerordening;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persoonVerstrekkingsbeperking", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    @JsonProperty
    @JsonManagedReference
    private Set<HisPersoonVerstrekkingsbeperkingModel> hisPersoonVerstrekkingsbeperkingLijst;

    @Transient
    private FormeleHistorieSet<HisPersoonVerstrekkingsbeperkingModel> persoonVerstrekkingsbeperkingHistorie;

    /**
     * Default contructor voor JPA.
     *
     */
    protected AbstractPersoonVerstrekkingsbeperkingHisVolledigImpl() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param persoon persoon van Persoon \ Verstrekkingsbeperking.
     * @param partij partij van Persoon \ Verstrekkingsbeperking.
     * @param omschrijvingDerde omschrijvingDerde van Persoon \ Verstrekkingsbeperking.
     * @param gemeenteVerordening gemeenteVerordening van Persoon \ Verstrekkingsbeperking.
     */
    public AbstractPersoonVerstrekkingsbeperkingHisVolledigImpl(
        final PersoonHisVolledigImpl persoon,
        final PartijAttribuut partij,
        final OmschrijvingEnumeratiewaardeAttribuut omschrijvingDerde,
        final PartijAttribuut gemeenteVerordening)
    {
        this();
        this.persoon = persoon;
        this.partij = partij;
        this.omschrijvingDerde = omschrijvingDerde;
        this.gemeenteVerordening = gemeenteVerordening;

    }

    /**
     * Retourneert ID van Persoon \ Verstrekkingsbeperking.
     *
     * @return ID.
     */
    @Id
    @SequenceGenerator(name = "PERSOONVERSTREKKINGSBEPERKING", sequenceName = "Kern.seq_PersVerstrbeperking")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PERSOONVERSTREKKINGSBEPERKING")
    @Access(value = AccessType.PROPERTY)
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Persoon van Persoon \ Verstrekkingsbeperking.
     *
     * @return Persoon.
     */
    public PersoonHisVolledigImpl getPersoon() {
        return persoon;
    }

    /**
     * Retourneert Partij van Persoon \ Verstrekkingsbeperking.
     *
     * @return Partij.
     */
    public PartijAttribuut getPartij() {
        return partij;
    }

    /**
     * Retourneert Omschrijving derde van Persoon \ Verstrekkingsbeperking.
     *
     * @return Omschrijving derde.
     */
    public OmschrijvingEnumeratiewaardeAttribuut getOmschrijvingDerde() {
        return omschrijvingDerde;
    }

    /**
     * Retourneert Gemeente verordening van Persoon \ Verstrekkingsbeperking.
     *
     * @return Gemeente verordening.
     */
    public PartijAttribuut getGemeenteVerordening() {
        return gemeenteVerordening;
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
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FormeleHistorieSet<HisPersoonVerstrekkingsbeperkingModel> getPersoonVerstrekkingsbeperkingHistorie() {
        if (hisPersoonVerstrekkingsbeperkingLijst == null) {
            hisPersoonVerstrekkingsbeperkingLijst = new HashSet<>();
        }
        if (persoonVerstrekkingsbeperkingHistorie == null) {
            persoonVerstrekkingsbeperkingHistorie =
                    new FormeleHistorieSetImpl<HisPersoonVerstrekkingsbeperkingModel>(hisPersoonVerstrekkingsbeperkingLijst);
        }
        return persoonVerstrekkingsbeperkingHistorie;
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.PERSOON_VERSTREKKINGSBEPERKING;
    }

}

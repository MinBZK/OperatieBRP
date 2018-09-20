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
import nl.bzk.brp.model.MaterieleHistorieSet;
import nl.bzk.brp.model.MaterieleHistorieSetImpl;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.basis.ALaagAfleidbaar;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonGeslachtsnaamcomponentHisVolledigBasis;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeslachtsnaamcomponentModel;
import nl.bzk.brp.model.operationeel.kern.PersoonGeslachtsnaamcomponentStandaardGroepModel;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * HisVolledig klasse voor Persoon \ Geslachtsnaamcomponent.
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigModelGenerator")
@MappedSuperclass
public abstract class AbstractPersoonGeslachtsnaamcomponentHisVolledigImpl implements HisVolledigImpl, PersoonGeslachtsnaamcomponentHisVolledigBasis,
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
    @AttributeOverride(name = VolgnummerAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Volgnr"))
    @JsonProperty
    private VolgnummerAttribuut volgnummer;

    @Embedded
    private PersoonGeslachtsnaamcomponentStandaardGroepModel standaard;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persoonGeslachtsnaamcomponent", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    @JsonProperty
    @JsonManagedReference
    private Set<HisPersoonGeslachtsnaamcomponentModel> hisPersoonGeslachtsnaamcomponentLijst;

    @Transient
    private MaterieleHistorieSet<HisPersoonGeslachtsnaamcomponentModel> persoonGeslachtsnaamcomponentHistorie;

    /**
     * Default contructor voor JPA.
     *
     */
    protected AbstractPersoonGeslachtsnaamcomponentHisVolledigImpl() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param persoon persoon van Persoon \ Geslachtsnaamcomponent.
     * @param volgnummer volgnummer van Persoon \ Geslachtsnaamcomponent.
     */
    public AbstractPersoonGeslachtsnaamcomponentHisVolledigImpl(final PersoonHisVolledigImpl persoon, final VolgnummerAttribuut volgnummer) {
        this();
        this.persoon = persoon;
        this.volgnummer = volgnummer;

    }

    /**
     * Retourneert ID van Persoon \ Geslachtsnaamcomponent.
     *
     * @return ID.
     */
    @Id
    @SequenceGenerator(name = "PERSOONGESLACHTSNAAMCOMPONENT", sequenceName = "Kern.seq_PersGeslnaamcomp")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PERSOONGESLACHTSNAAMCOMPONENT")
    @Access(value = AccessType.PROPERTY)
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Persoon van Persoon \ Geslachtsnaamcomponent.
     *
     * @return Persoon.
     */
    public PersoonHisVolledigImpl getPersoon() {
        return persoon;
    }

    /**
     * Retourneert Volgnummer van Persoon \ Geslachtsnaamcomponent.
     *
     * @return Volgnummer.
     */
    public VolgnummerAttribuut getVolgnummer() {
        return volgnummer;
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
        final HisPersoonGeslachtsnaamcomponentModel actueelStandaard = getPersoonGeslachtsnaamcomponentHistorie().getActueleRecord();
        if (actueelStandaard != null) {
            this.standaard =
                    new PersoonGeslachtsnaamcomponentStandaardGroepModel(
                        actueelStandaard.getPredicaat(),
                        actueelStandaard.getAdellijkeTitel(),
                        actueelStandaard.getVoorvoegsel(),
                        actueelStandaard.getScheidingsteken(),
                        actueelStandaard.getStam());
        } else {
            this.standaard = null;
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MaterieleHistorieSet<HisPersoonGeslachtsnaamcomponentModel> getPersoonGeslachtsnaamcomponentHistorie() {
        if (hisPersoonGeslachtsnaamcomponentLijst == null) {
            hisPersoonGeslachtsnaamcomponentLijst = new HashSet<>();
        }
        if (persoonGeslachtsnaamcomponentHistorie == null) {
            persoonGeslachtsnaamcomponentHistorie =
                    new MaterieleHistorieSetImpl<HisPersoonGeslachtsnaamcomponentModel>(hisPersoonGeslachtsnaamcomponentLijst);
        }
        return persoonGeslachtsnaamcomponentHistorie;
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.PERSOON_GESLACHTSNAAMCOMPONENT;
    }

}

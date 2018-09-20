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
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.basis.ALaagAfleidbaar;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonOnderzoekHisVolledigBasis;
import nl.bzk.brp.model.operationeel.kern.HisPersoonOnderzoekModel;
import nl.bzk.brp.model.operationeel.kern.PersoonOnderzoekStandaardGroepModel;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * HisVolledig klasse voor Persoon \ Onderzoek.
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigModelGenerator")
@MappedSuperclass
public abstract class AbstractPersoonOnderzoekHisVolledigImpl implements HisVolledigImpl, PersoonOnderzoekHisVolledigBasis, ALaagAfleidbaar,
        ElementIdentificeerbaar
{

    @Transient
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Pers")
    // Handmatige wijziging
//    @JsonBackReference
    private PersoonHisVolledigImpl persoon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Onderzoek")
    // Handmatige wijziging
//    @JsonBackReference
    private OnderzoekHisVolledigImpl onderzoek;

    @Embedded
    private PersoonOnderzoekStandaardGroepModel standaard;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persoonOnderzoek", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    @JsonProperty
    @JsonManagedReference
    private Set<HisPersoonOnderzoekModel> hisPersoonOnderzoekLijst;

    @Transient
    private FormeleHistorieSet<HisPersoonOnderzoekModel> persoonOnderzoekHistorie;

    /**
     * Default contructor voor JPA.
     *
     */
    protected AbstractPersoonOnderzoekHisVolledigImpl() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param persoon persoon van Persoon \ Onderzoek.
     * @param onderzoek onderzoek van Persoon \ Onderzoek.
     */
    public AbstractPersoonOnderzoekHisVolledigImpl(final PersoonHisVolledigImpl persoon, final OnderzoekHisVolledigImpl onderzoek) {
        this();
        this.persoon = persoon;
        this.onderzoek = onderzoek;

    }

    /**
     * Retourneert ID van Persoon \ Onderzoek.
     *
     * @return ID.
     */
    @Id
    @SequenceGenerator(name = "PERSOONONDERZOEK", sequenceName = "Kern.seq_PersOnderzoek")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PERSOONONDERZOEK")
    @Access(value = AccessType.PROPERTY)
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Persoon van Persoon \ Onderzoek.
     *
     * @return Persoon.
     */
    public PersoonHisVolledigImpl getPersoon() {
        return persoon;
    }

    /**
     * Retourneert Onderzoek van Persoon \ Onderzoek.
     *
     * @return Onderzoek.
     */
    public OnderzoekHisVolledigImpl getOnderzoek() {
        return onderzoek;
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
        final HisPersoonOnderzoekModel actueelStandaard = getPersoonOnderzoekHistorie().getActueleRecord();
        if (actueelStandaard != null) {
            this.standaard = new PersoonOnderzoekStandaardGroepModel(actueelStandaard.getRol());
        } else {
            this.standaard = null;
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FormeleHistorieSet<HisPersoonOnderzoekModel> getPersoonOnderzoekHistorie() {
        if (hisPersoonOnderzoekLijst == null) {
            hisPersoonOnderzoekLijst = new HashSet<>();
        }
        if (persoonOnderzoekHistorie == null) {
            persoonOnderzoekHistorie = new FormeleHistorieSetImpl<HisPersoonOnderzoekModel>(hisPersoonOnderzoekLijst);
        }
        return persoonOnderzoekHistorie;
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.PERSOON_ONDERZOEK;
    }

    /**
     * Zet de persoon referentie.
     *
     * @param persoon een nieuwe persoon instantie
     *
     * Handmatige wijziging.
     */
    public void setPersoon(final PersoonHisVolledigImpl persoon) {
        this.persoon = persoon;
    }
}

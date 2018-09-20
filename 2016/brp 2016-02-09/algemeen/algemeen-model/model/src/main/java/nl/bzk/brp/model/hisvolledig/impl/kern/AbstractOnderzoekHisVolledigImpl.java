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
import javax.persistence.CascadeType;
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
import nl.bzk.brp.model.basis.ALaagAfleidbaar;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.OnderzoekHisVolledigBasis;
import nl.bzk.brp.model.operationeel.kern.HisOnderzoekAfgeleidAdministratiefModel;
import nl.bzk.brp.model.operationeel.kern.HisOnderzoekModel;
import nl.bzk.brp.model.operationeel.kern.OnderzoekAfgeleidAdministratiefGroepModel;
import nl.bzk.brp.model.operationeel.kern.OnderzoekStandaardGroepModel;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * HisVolledig klasse voor Onderzoek.
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigModelGenerator")
@MappedSuperclass
public abstract class AbstractOnderzoekHisVolledigImpl implements HisVolledigImpl, OnderzoekHisVolledigBasis, ALaagAfleidbaar, ElementIdentificeerbaar {

    @Transient
    @JsonProperty
    private Integer iD;

    @Embedded
    private OnderzoekStandaardGroepModel standaard;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "onderzoek", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    @JsonProperty
    @JsonManagedReference
    private Set<HisOnderzoekModel> hisOnderzoekLijst;

    @Transient
    private FormeleHistorieSet<HisOnderzoekModel> onderzoekHistorie;

    @Embedded
    private OnderzoekAfgeleidAdministratiefGroepModel afgeleidAdministratief;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "onderzoek", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    @JsonProperty
    @JsonManagedReference
    private Set<HisOnderzoekAfgeleidAdministratiefModel> hisOnderzoekAfgeleidAdministratiefLijst;

    @Transient
    private FormeleHistorieSet<HisOnderzoekAfgeleidAdministratiefModel> onderzoekAfgeleidAdministratiefHistorie;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "onderzoek", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    @JsonProperty
    @JsonManagedReference
    private Set<GegevenInOnderzoekHisVolledigImpl> gegevensInOnderzoek;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "onderzoek", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    private Set<PersoonOnderzoekHisVolledigImpl> personenInOnderzoek;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "onderzoek", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    @JsonProperty
    @JsonManagedReference
    private Set<PartijOnderzoekHisVolledigImpl> partijenInOnderzoek;

    /**
     * Default contructor voor JPA.
     *
     */
    public AbstractOnderzoekHisVolledigImpl() {
        gegevensInOnderzoek = new HashSet<GegevenInOnderzoekHisVolledigImpl>();
        personenInOnderzoek = new HashSet<PersoonOnderzoekHisVolledigImpl>();
        partijenInOnderzoek = new HashSet<PartijOnderzoekHisVolledigImpl>();

    }

    /**
     * Retourneert ID van Onderzoek.
     *
     * @return ID.
     */
    @Id
    @SequenceGenerator(name = "ONDERZOEK", sequenceName = "Kern.seq_Onderzoek")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ONDERZOEK")
    @Access(value = AccessType.PROPERTY)
    public Integer getID() {
        return iD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<GegevenInOnderzoekHisVolledigImpl> getGegevensInOnderzoek() {
        return gegevensInOnderzoek;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<PersoonOnderzoekHisVolledigImpl> getPersonenInOnderzoek() {
        return personenInOnderzoek;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<PartijOnderzoekHisVolledigImpl> getPartijenInOnderzoek() {
        return partijenInOnderzoek;
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
     * Zet lijst van Gegeven in onderzoek.
     *
     * @param gegevensInOnderzoek lijst van Gegeven in onderzoek
     */
    public void setGegevensInOnderzoek(final Set<GegevenInOnderzoekHisVolledigImpl> gegevensInOnderzoek) {
        this.gegevensInOnderzoek = gegevensInOnderzoek;
    }

    /**
     * Zet lijst van Persoon \ Onderzoek.
     *
     * @param personenInOnderzoek lijst van Persoon \ Onderzoek
     */
    public void setPersonenInOnderzoek(final Set<PersoonOnderzoekHisVolledigImpl> personenInOnderzoek) {
        this.personenInOnderzoek = personenInOnderzoek;
    }

    /**
     * Zet lijst van Partij \ Onderzoek.
     *
     * @param partijenInOnderzoek lijst van Partij \ Onderzoek
     */
    public void setPartijenInOnderzoek(final Set<PartijOnderzoekHisVolledigImpl> partijenInOnderzoek) {
        this.partijenInOnderzoek = partijenInOnderzoek;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void leidALaagAf() {
        final HisOnderzoekModel actueelStandaard = getOnderzoekHistorie().getActueleRecord();
        if (actueelStandaard != null) {
            this.standaard =
                    new OnderzoekStandaardGroepModel(
                        actueelStandaard.getDatumAanvang(),
                        actueelStandaard.getVerwachteAfhandeldatum(),
                        actueelStandaard.getDatumEinde(),
                        actueelStandaard.getOmschrijving(),
                        actueelStandaard.getStatus());
        } else {
            this.standaard = null;
        }
        final HisOnderzoekAfgeleidAdministratiefModel actueelAfgeleidAdministratief = getOnderzoekAfgeleidAdministratiefHistorie().getActueleRecord();
        if (actueelAfgeleidAdministratief != null) {
            this.afgeleidAdministratief = new OnderzoekAfgeleidAdministratiefGroepModel(actueelAfgeleidAdministratief.getAdministratieveHandeling());
        } else {
            this.afgeleidAdministratief = null;
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FormeleHistorieSet<HisOnderzoekModel> getOnderzoekHistorie() {
        if (hisOnderzoekLijst == null) {
            hisOnderzoekLijst = new HashSet<>();
        }
        if (onderzoekHistorie == null) {
            onderzoekHistorie = new FormeleHistorieSetImpl<HisOnderzoekModel>(hisOnderzoekLijst);
        }
        return onderzoekHistorie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FormeleHistorieSet<HisOnderzoekAfgeleidAdministratiefModel> getOnderzoekAfgeleidAdministratiefHistorie() {
        if (hisOnderzoekAfgeleidAdministratiefLijst == null) {
            hisOnderzoekAfgeleidAdministratiefLijst = new HashSet<>();
        }
        if (onderzoekAfgeleidAdministratiefHistorie == null) {
            onderzoekAfgeleidAdministratiefHistorie =
                    new FormeleHistorieSetImpl<HisOnderzoekAfgeleidAdministratiefModel>(hisOnderzoekAfgeleidAdministratiefLijst);
        }
        return onderzoekAfgeleidAdministratiefHistorie;
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.ONDERZOEK;
    }

}

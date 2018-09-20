/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import nl.bzk.brp.model.basis.AbstractDynamischObject;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.Onderzoek;
import nl.bzk.brp.model.logisch.kern.OnderzoekBasis;

/**
 * Onderzoek naar gegevens in de BRP.
 *
 * Normaliter is er geen reden om te twijfelen aan de in de BRP geregistreerde gegevens. Soms echter is dat wel aan de
 * orde. Vanuit verschillende hoeken kan een signaal komen dat bepaalde gegevens niet correct zijn. Dit kan om zowel
 * actuele gegevens als om (materieel) historische gegevens gaan. Met het objecttype Onderzoek wordt vastgelegd dat
 * gegevens in onderzoek zijn, en welke gegevens het betreft.
 *
 * Nog onderzoeken: Relatie met Terugmeld voorziening (TMV)/TMF.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractOnderzoekModel extends AbstractDynamischObject implements OnderzoekBasis, ModelIdentificeerbaar<Integer> {

    @Transient
    @JsonProperty
    private Integer iD;

    @Embedded
    @JsonProperty
    private OnderzoekStandaardGroepModel standaard;

    @Embedded
    @JsonProperty
    private OnderzoekAfgeleidAdministratiefGroepModel afgeleidAdministratief;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "Onderzoek")
    @JsonProperty
    private Set<GegevenInOnderzoekModel> gegevensInOnderzoek;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "Onderzoek")
    @JsonProperty
    private Set<PersoonOnderzoekModel> personenInOnderzoek;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "Onderzoek")
    @JsonProperty
    private Set<PartijOnderzoekModel> partijenInOnderzoek;

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     */
    public AbstractOnderzoekModel() {
        gegevensInOnderzoek = new HashSet<GegevenInOnderzoekModel>();
        personenInOnderzoek = new HashSet<PersoonOnderzoekModel>();
        partijenInOnderzoek = new HashSet<PartijOnderzoekModel>();

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param onderzoek Te kopieren object type.
     */
    public AbstractOnderzoekModel(final Onderzoek onderzoek) {
        this();
        if (onderzoek.getStandaard() != null) {
            this.standaard = new OnderzoekStandaardGroepModel(onderzoek.getStandaard());
        }
        if (onderzoek.getAfgeleidAdministratief() != null) {
            this.afgeleidAdministratief = new OnderzoekAfgeleidAdministratiefGroepModel(onderzoek.getAfgeleidAdministratief());
        }

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
    public OnderzoekStandaardGroepModel getStandaard() {
        return standaard;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OnderzoekAfgeleidAdministratiefGroepModel getAfgeleidAdministratief() {
        return afgeleidAdministratief;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<GegevenInOnderzoekModel> getGegevensInOnderzoek() {
        return gegevensInOnderzoek;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<PersoonOnderzoekModel> getPersonenInOnderzoek() {
        return personenInOnderzoek;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<PartijOnderzoekModel> getPartijenInOnderzoek() {
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
     * Zet Standaard van Onderzoek.
     *
     * @param standaard Standaard.
     */
    public void setStandaard(final OnderzoekStandaardGroepModel standaard) {
        this.standaard = standaard;
    }

    /**
     * Zet Afgeleid administratief van Onderzoek.
     *
     * @param afgeleidAdministratief Afgeleid administratief.
     */
    public void setAfgeleidAdministratief(final OnderzoekAfgeleidAdministratiefGroepModel afgeleidAdministratief) {
        this.afgeleidAdministratief = afgeleidAdministratief;
    }

    /**
     * Geeft alle groepen van de object met uitzondering van groepen die null zijn.
     *
     * @return Lijst met groepen.
     */
    public List<Groep> getGroepen() {
        final List<Groep> groepen = new ArrayList<>();
        if (standaard != null) {
            groepen.add(standaard);
        }
        if (afgeleidAdministratief != null) {
            groepen.add(afgeleidAdministratief);
        }
        return groepen;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van het object.
     */
    public List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        return attributen;
    }

}

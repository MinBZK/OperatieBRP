/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AssociationOverride;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.basis.AbstractDynamischObject;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.PartijOnderzoek;
import nl.bzk.brp.model.logisch.kern.PartijOnderzoekBasis;

/**
 * De wijze waarop een Partij betrokken is bij een Onderzoek.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractPartijOnderzoekModel extends AbstractDynamischObject implements PartijOnderzoekBasis, ModelIdentificeerbaar<Integer> {

    @Transient
    @JsonProperty
    private Integer iD;

    @Embedded
    @AssociationOverride(name = PartijAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "Partij"))
    @JsonProperty
    private PartijAttribuut partij;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Onderzoek")
    private OnderzoekModel onderzoek;

    @Embedded
    @JsonProperty
    private PartijOnderzoekStandaardGroepModel standaard;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractPartijOnderzoekModel() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param partij partij van Partij \ Onderzoek.
     * @param onderzoek onderzoek van Partij \ Onderzoek.
     */
    public AbstractPartijOnderzoekModel(final PartijAttribuut partij, final OnderzoekModel onderzoek) {
        this();
        this.partij = partij;
        this.onderzoek = onderzoek;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param partijOnderzoek Te kopieren object type.
     * @param onderzoek Bijbehorende Onderzoek.
     */
    public AbstractPartijOnderzoekModel(final PartijOnderzoek partijOnderzoek, final OnderzoekModel onderzoek) {
        this();
        this.partij = partijOnderzoek.getPartij();
        this.onderzoek = onderzoek;
        if (partijOnderzoek.getStandaard() != null) {
            this.standaard = new PartijOnderzoekStandaardGroepModel(partijOnderzoek.getStandaard());
        }

    }

    /**
     * Retourneert ID van Partij \ Onderzoek.
     *
     * @return ID.
     */
    @Id
    @SequenceGenerator(name = "PARTIJONDERZOEK", sequenceName = "Kern.seq_PartijOnderzoek")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PARTIJONDERZOEK")
    @Access(value = AccessType.PROPERTY)
    public Integer getID() {
        return iD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PartijAttribuut getPartij() {
        return partij;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OnderzoekModel getOnderzoek() {
        return onderzoek;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PartijOnderzoekStandaardGroepModel getStandaard() {
        return standaard;
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
     * Zet Standaard van Partij \ Onderzoek.
     *
     * @param standaard Standaard.
     */
    public void setStandaard(final PartijOnderzoekStandaardGroepModel standaard) {
        this.standaard = standaard;
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
        return groepen;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van het object.
     */
    public List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (partij != null) {
            attributen.add(partij);
        }
        return attributen;
    }

}

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
import nl.bzk.brp.model.basis.AbstractDynamischObject;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.PersoonOnderzoek;
import nl.bzk.brp.model.logisch.kern.PersoonOnderzoekBasis;

/**
 * De constatering dat een Onderzoek een Persoon raakt.
 *
 * Als er gegevens van een Persoon in Onderzoek staan, dan wordt (redundant) vastgelegd dat de Persoon onderwerp is van
 * een Onderzoek. Er wordt een koppeling tussen een Persoon en een Onderzoek gelegd indien er een gegeven in onderzoek
 * is dat behoort tot het objecttype Persoon, of tot de naar de Persoon verwijzende objecttypen. Een speciaal geval is
 * de Relatie: is de Relatie zelf in onderzoek, dan zijn alle Personen die betrokken zijn in die Relatie ook in
 * onderzoek.
 *
 * Het objecttype 'Persoon/Onderzoek' had ook de naam "Persoon in Onderzoek" kunnen heten. We sluiten echter aan bij de
 * naamgeving van andere koppeltabellen.
 *
 * De exemplaren van Persoon/Onderzoek zijn volledig afleidbaar uit de exemplaren van Gegevens-in-onderzoek. We leggen
 * dit gegeven echter redundant vast om snel de vraag te kunnen beantwoorden of 'de gegevens over de Persoon' in
 * onderzoek zijn.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractPersoonOnderzoekModel extends AbstractDynamischObject implements PersoonOnderzoekBasis, ModelIdentificeerbaar<Integer> {

    @Transient
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Pers")
    private PersoonModel persoon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Onderzoek")
    private OnderzoekModel onderzoek;

    @Embedded
    @JsonProperty
    private PersoonOnderzoekStandaardGroepModel standaard;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractPersoonOnderzoekModel() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param persoon persoon van Persoon \ Onderzoek.
     * @param onderzoek onderzoek van Persoon \ Onderzoek.
     */
    public AbstractPersoonOnderzoekModel(final PersoonModel persoon, final OnderzoekModel onderzoek) {
        this();
        this.persoon = persoon;
        this.onderzoek = onderzoek;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonOnderzoek Te kopieren object type.
     * @param persoon Bijbehorende Persoon.
     * @param onderzoek Bijbehorende Onderzoek.
     */
    public AbstractPersoonOnderzoekModel(final PersoonOnderzoek persoonOnderzoek, final PersoonModel persoon, final OnderzoekModel onderzoek) {
        this();
        this.persoon = persoon;
        this.onderzoek = onderzoek;
        if (persoonOnderzoek.getStandaard() != null) {
            this.standaard = new PersoonOnderzoekStandaardGroepModel(persoonOnderzoek.getStandaard());
        }

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
     * {@inheritDoc}
     */
    @Override
    public PersoonModel getPersoon() {
        return persoon;
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
    public PersoonOnderzoekStandaardGroepModel getStandaard() {
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
     * Zet Standaard van Persoon \ Onderzoek.
     *
     * @param standaard Standaard.
     */
    public void setStandaard(final PersoonOnderzoekStandaardGroepModel standaard) {
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
        return attributen;
    }

}

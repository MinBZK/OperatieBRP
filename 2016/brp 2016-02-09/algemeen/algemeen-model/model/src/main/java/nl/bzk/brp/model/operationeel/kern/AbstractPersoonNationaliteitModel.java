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
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NationaliteitAttribuut;
import nl.bzk.brp.model.basis.AbstractDynamischObject;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.PersoonNationaliteit;
import nl.bzk.brp.model.logisch.kern.PersoonNationaliteitBasis;

/**
 * Een aanduiding van de nationaliteit die de persoon bezit.
 *
 * 1. Waarden 'Vastgesteld niet-Nederlander', 'Behandelen als Nederlander' en 'Statenloos' worden niet geregistreerd als
 * Nationaliteit, maar onder een aparte groep. Motivatie voor het apart behandelen van 'Vastgesteld niet-Nederlander',
 * is dat het een expliciete uitspraak is (van een rechter), dat iemand geen Nederlander (meer) is. Deze waarde kan best
 * náást bijvoorbeeld een Belgische Nationaliteit gelden, en moet niet worden gezien als 'deelinformatie' over de
 * Nationaliteit. De 'Behandelen als Nederlander' gaat over de wijze van behandeling, en past dientengevolge minder goed
 * als waarde voor 'Nationaliteit'. Als er (vermoedelijk) wel een Nationaliteit is, alleen die is onbekend, dán wordt
 * "Onbekend" ingevuld.
 *
 * 2.Nationaliteit is niet sterk gedefinieerd. Het wijkt af van de Wikipedia definitie (althans, op 23 februari 2011).
 * Beste bron lijkt een Europees verdrag (Europees Verdrag inzake nationaliteit, Straatsburg, 06-11-1997), en dan de
 * Nederlandse vertaling ervan:
 * "de juridische band tussen een persoon en een Staat; deze term verwijst niet naar de etnische afkomst van de persoon"
 * . Deze zin loop niet heel lekker en is ook niet éénduidig (er is ook een juridische band tussen een president van een
 * Staat en die Staat ten gevolge van het presidentschap, en die wordt niet bedoeld). Daarom gekozen voor een nadere
 * toelichting met verwijzing. Verder goed om in de toelichting te beschrijven hoe wordt omgegaan met meerdere
 * Nationaliteiten (ook wel dubbele Nationaliteiten genoemd), dan wel statenloosheid en vastgesteld niet-nederlander.
 *
 * 3. Dit Objecttype wijkt sterk af van de wijze van registeren zoals geintroduceerd is bij LO 3.9. Zie voor meer
 * informatie de A:"Persoon \ Nationaliteit.Bijhouding beeindigd?" en _Migratie:_ attributen.
 *
 * Het betreft de juridische band tussen een persoon en een staat zoals bedoeld in het Europees verdrag inzake
 * nationaliteit, Straatsburg 06-11-1997.
 *
 * Indien iemand tegelijk meerdere nationaliteiten heeft, zijn dit ook aparte exemplaren van Nationaliteit. Indien
 * aannemelijk is dat iemand een Nationaliteit heeft, maar deze is onbekend, dat wordt dit vastgelegd als 'Onbekend'.
 * Situaties als 'behandeld als Nederlander', 'Vastgesteld niet-Nederlander' en 'Staatloos' worden geregistreerd onder
 * 'overige indicaties', en niet als Nationaliteit.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractPersoonNationaliteitModel extends AbstractDynamischObject implements PersoonNationaliteitBasis,
        ModelIdentificeerbaar<Integer>
{

    @Transient
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Pers")
    private PersoonModel persoon;

    @Embedded
    @AssociationOverride(name = NationaliteitAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "Nation"))
    @JsonProperty
    private NationaliteitAttribuut nationaliteit;

    @Embedded
    @JsonProperty
    private PersoonNationaliteitStandaardGroepModel standaard;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractPersoonNationaliteitModel() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param persoon persoon van Persoon \ Nationaliteit.
     * @param nationaliteit nationaliteit van Persoon \ Nationaliteit.
     */
    public AbstractPersoonNationaliteitModel(final PersoonModel persoon, final NationaliteitAttribuut nationaliteit) {
        this();
        this.persoon = persoon;
        this.nationaliteit = nationaliteit;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonNationaliteit Te kopieren object type.
     * @param persoon Bijbehorende Persoon.
     */
    public AbstractPersoonNationaliteitModel(final PersoonNationaliteit persoonNationaliteit, final PersoonModel persoon) {
        this();
        this.persoon = persoon;
        this.nationaliteit = persoonNationaliteit.getNationaliteit();
        if (persoonNationaliteit.getStandaard() != null) {
            this.standaard = new PersoonNationaliteitStandaardGroepModel(persoonNationaliteit.getStandaard());
        }

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
    public NationaliteitAttribuut getNationaliteit() {
        return nationaliteit;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonNationaliteitStandaardGroepModel getStandaard() {
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
     * Zet Standaard van Persoon \ Nationaliteit.
     *
     * @param standaard Standaard.
     */
    public void setStandaard(final PersoonNationaliteitStandaardGroepModel standaard) {
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
        if (nationaliteit != null) {
            attributen.add(nationaliteit);
        }
        return attributen;
    }

}

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
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.basis.AbstractDynamischObject;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.basis.VolgnummerBevattend;
import nl.bzk.brp.model.logisch.kern.PersoonGeslachtsnaamcomponent;
import nl.bzk.brp.model.logisch.kern.PersoonGeslachtsnaamcomponentBasis;

/**
 * Component van de geslachtsnaam van een persoon
 *
 * De geslachtsnaam van een persoon kan uit meerdere delen bestaan, bijvoorbeeld ten gevolge van een namenreeks. Ook kan
 * er sprake zijn van het voorkomen van meerdere geslachten, die in de geslachtsnaam terugkomen. In dat geval valt de
 * geslachtsnaam uiteen in meerdere geslachtsnaamcomponenten. Een geslachtsnaamcomponent bestaat vervolgens
 * mogelijkerwijs uit meerdere onderdelen, waaronder voorvoegsel en naamdeel. De structuur van de gegevens in de
 * centrale voorzieningen en in de berichtuitwisseling met de centrale voorzieningen is voorbereid op het nauwkeuriger
 * vastleggen van naamgegevens. Dit is bijvoorbeeld te zien aan het feit dat de berichtopbouw van berichten met
 * naamgegevens ruimte geeft voor meerdere geslachtsnaamcomponenten en het feit dat de groep geslachtsnaamcomponent een
 * gegeven volgnummer kent.
 *
 * Indien in de toekomst, bijvoorbeeld als gevolg van liberalisering van het naamrecht, het zinvol wordt om
 * afzonderlijke geslachtsnaamcomponenten te onderkennen, dan kunnen ook die afzonderlijk worden vastgelegd. Daarbij
 * kunnen voorvoegsels, scheidingstekens en adellijke titels gekoppeld worden aan de specifieke geslachtsnaamcomponent
 * waarop deze van toepassing zijn. Predikaten kunnen worden opgenomen als onderdeel van de gegevens over de specifieke
 * geslachtsnaamcomponent die het gebruik van het predikaat rechtvaardigt.
 *
 * Vooralsnog kunnen deze mogelijkheden niet worden gebruikt. De GBA kent een beperkte vastlegging van naamgegevens.
 * Zolang de BRP en de GBA naast elkaar bestaan – en/of zolang akten nauwkeuriger registratie nog niet ondersteunen –
 * controleren de centrale voorzieningen dat het gebruik van de mogelijkheden van de BRP beperkt blijft tot een enkel
 * voorkomen van geslachtsnaamcomponent.
 *
 * 1. Vooruitlopend op liberalisering namenwet, waarbij het waarschijnlijk mogelijk wordt om de (volledige)
 * geslachtsnaam van een kind te vormen door delen van de geslachtsnaam van beide ouders samen te voegen, is het alvast
 * mogelijk gemaakt om deze delen apart te 'kennen'. Deze beslissing is genomen na raadpleging van ministerie van
 * Justitie, in de persoon van Jet Lenters. 2. De verplichting van de groep 'standaard' is niet geheel zuiver: in een
 * toekomstige situatie, waarin we meerdere geslachtsnaamcomponenten gaan krijgen, is het theoretisch denkbaar dat een
 * tweede, derde of hogere geslachtsnaamcomponent "geen actuele waarde" kent, c.q. alleen voor 'vervallen' records
 * staat. In deze zin zou de groep standaard dus optioneel moeten zijn: er kunnen gaten komen. Alleen komen deze alleen
 * voor ná dat we splitsingen in twee of meer componenten gaan krijgen; in de tussentijd dus niet. De modellering als
 * verplicht is dus nu juist, in de toekomst niet. Besloten is echter deze voorlopig zo te laten, en pas bij de
 * liberalisering de optionaliteit aan te passen, en ook dan pas na te denken over de bedrijfsregel
 * "elke geslachtsnaamcomponent met volgnummer 1 is verplicht, met volgnummer hoger dan 1 is deze WEL optioneel".
 *
 * Voorbeeld: Geslachtsnaam is samengesteld door: Voorvoegsel Scheidingsteken Geslachtsnaamstam van der <spatie> Meer
 * van <spatie> Jansen de Groot d ’ Ancona
 *
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractPersoonGeslachtsnaamcomponentModel extends AbstractDynamischObject implements PersoonGeslachtsnaamcomponentBasis,
        ModelIdentificeerbaar<Integer>, VolgnummerBevattend
{

    @Transient
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Pers")
    private PersoonModel persoon;

    @Embedded
    @AttributeOverride(name = VolgnummerAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Volgnr"))
    @JsonProperty
    private VolgnummerAttribuut volgnummer;

    @Embedded
    @JsonProperty
    private PersoonGeslachtsnaamcomponentStandaardGroepModel standaard;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractPersoonGeslachtsnaamcomponentModel() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param persoon persoon van Persoon \ Geslachtsnaamcomponent.
     * @param volgnummer volgnummer van Persoon \ Geslachtsnaamcomponent.
     */
    public AbstractPersoonGeslachtsnaamcomponentModel(final PersoonModel persoon, final VolgnummerAttribuut volgnummer) {
        this();
        this.persoon = persoon;
        this.volgnummer = volgnummer;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonGeslachtsnaamcomponent Te kopieren object type.
     * @param persoon Bijbehorende Persoon.
     */
    public AbstractPersoonGeslachtsnaamcomponentModel(final PersoonGeslachtsnaamcomponent persoonGeslachtsnaamcomponent, final PersoonModel persoon) {
        this();
        this.persoon = persoon;
        this.volgnummer = persoonGeslachtsnaamcomponent.getVolgnummer();
        if (persoonGeslachtsnaamcomponent.getStandaard() != null) {
            this.standaard = new PersoonGeslachtsnaamcomponentStandaardGroepModel(persoonGeslachtsnaamcomponent.getStandaard());
        }

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
    public VolgnummerAttribuut getVolgnummer() {
        return volgnummer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonGeslachtsnaamcomponentStandaardGroepModel getStandaard() {
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
     * Zet Standaard van Persoon \ Geslachtsnaamcomponent.
     *
     * @param standaard Standaard.
     */
    public void setStandaard(final PersoonGeslachtsnaamcomponentStandaardGroepModel standaard) {
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
        if (volgnummer != null) {
            attributen.add(volgnummer);
        }
        return attributen;
    }

}

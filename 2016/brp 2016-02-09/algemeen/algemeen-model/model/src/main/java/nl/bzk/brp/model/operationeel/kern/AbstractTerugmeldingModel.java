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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.basis.AbstractDynamischObject;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.TerugmeldingBasis;

/**
 * De melding van twijfel bij de juistheid van in de BRP geregistreerde gegevens.
 *
 * Een ontvanger van BRP-gegevens die ‘gerede twijfel’ heeft over de juistheid van een gegeven, is gerechtigd om dat
 * gegeven niet te gebruiken, maar moet de bronhouder diens twijfel melden. Dat laatste is de zogenaamde terugmelding.
 *
 * 1. Een definitie van terugmelding is niet snel gevonden. Een redelijke beschrijving staat in 'Handreiking Gerede
 * Twijfel' van 25 mei 2012(http://www.bprbzk.nl/dsresource?objectid=39545&type=org), waarin de volgende zinsnede staat:
 * " Een ontvanger van GBA-gegevens die ‘gerede twijfel’ heeft over de juistheid van een gegeven, is gerechtigd om dat
 * gegeven niet te gebruiken, maar moet de bronhouder diens twijfel melden. Dat laatste is de zogenaamde terugmelding."
 * In de definitie is aangesloten bij de lossere 'diens twijfel' uit de ene laatste zin, in plaats van de strictere
 * 'gerede twijfel' die eerder in de alinea voorkomt. Dit ook, omdat een terugmelding kan plaatsvinden zonder dat er
 * gerede twijfel wás.
 *
 * 2. Keuze bij contactpersoongegevens is hoe de naam uit te modelleren. Enerzijds is "gewoon een string" wel voldoende.
 * Anderzijds scheidt bijv. KING bij e-formulieren dit altijd uit. Uiteindelijk gekozen om deze uit te schrijven zoals
 * samengestelde naam, maar dan de adelijke titel en predicaat weg te laten. NB: deze (of een andere) keuze is altijd
 * arbitrair.
 *
 * Modellering van Persoon binnen dit OT is nog niet 100%. Als de Terugmelding echt gebruikt gaat worden, dan een
 * constructie identiek aan Persoon \ Onderzoek maken waarbij er theoretisch meerdere Personen binnen de Terugmelding
 * kunnen vallen (maar dit mogelijk functioneel beperkt wordt tot 1). Dan houd je de concepten identiek.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractTerugmeldingModel extends AbstractDynamischObject implements TerugmeldingBasis, ModelIdentificeerbaar<Integer> {

    @Transient
    @JsonProperty
    private Integer iD;

    @Embedded
    @AssociationOverride(name = PartijAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "TerugmeldendePartij"))
    @JsonProperty
    private PartijAttribuut terugmeldendePartij;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Pers")
    @JsonProperty
    private PersoonModel persoon;

    @Embedded
    @AssociationOverride(name = PartijAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "Bijhgem"))
    @JsonProperty
    private PartijAttribuut bijhoudingsgemeente;

    @Embedded
    @AttributeOverride(name = DatumTijdAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "TsReg"))
    @JsonProperty
    private DatumTijdAttribuut tijdstipRegistratie;

    @Embedded
    @JsonProperty
    private TerugmeldingStandaardGroepModel standaard;

    @Embedded
    @JsonProperty
    private TerugmeldingContactpersoonGroepModel contactpersoon;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "Terugmelding")
    @JsonProperty
    private Set<GegevenInTerugmeldingModel> gegevens;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractTerugmeldingModel() {
        gegevens = new HashSet<GegevenInTerugmeldingModel>();

    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param terugmeldendePartij terugmeldendePartij van Terugmelding.
     * @param persoon persoon van Terugmelding.
     * @param bijhoudingsgemeente bijhoudingsgemeente van Terugmelding.
     * @param tijdstipRegistratie tijdstipRegistratie van Terugmelding.
     */
    public AbstractTerugmeldingModel(
        final PartijAttribuut terugmeldendePartij,
        final PersoonModel persoon,
        final PartijAttribuut bijhoudingsgemeente,
        final DatumTijdAttribuut tijdstipRegistratie)
    {
        this();
        this.terugmeldendePartij = terugmeldendePartij;
        this.persoon = persoon;
        this.bijhoudingsgemeente = bijhoudingsgemeente;
        this.tijdstipRegistratie = tijdstipRegistratie;

    }

    /**
     * Retourneert ID van Terugmelding.
     *
     * @return ID.
     */
    @Id
    @SequenceGenerator(name = "TERUGMELDING", sequenceName = "Kern.seq_Terugmelding")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "TERUGMELDING")
    @Access(value = AccessType.PROPERTY)
    public Integer getID() {
        return iD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PartijAttribuut getTerugmeldendePartij() {
        return terugmeldendePartij;
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
    public PartijAttribuut getBijhoudingsgemeente() {
        return bijhoudingsgemeente;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumTijdAttribuut getTijdstipRegistratie() {
        return tijdstipRegistratie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TerugmeldingStandaardGroepModel getStandaard() {
        return standaard;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TerugmeldingContactpersoonGroepModel getContactpersoon() {
        return contactpersoon;
    }

    /**
     * Retourneert Gegevens in terugmelding van Terugmelding.
     *
     * @return Gegevens in terugmelding van Terugmelding.
     */
    public Set<GegevenInTerugmeldingModel> getGegevens() {
        return gegevens;
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
     * Zet Standaard van Terugmelding.
     *
     * @param standaard Standaard.
     */
    public void setStandaard(final TerugmeldingStandaardGroepModel standaard) {
        this.standaard = standaard;
    }

    /**
     * Zet Contactpersoon van Terugmelding.
     *
     * @param contactpersoon Contactpersoon.
     */
    public void setContactpersoon(final TerugmeldingContactpersoonGroepModel contactpersoon) {
        this.contactpersoon = contactpersoon;
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
        if (contactpersoon != null) {
            groepen.add(contactpersoon);
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
        if (terugmeldendePartij != null) {
            attributen.add(terugmeldendePartij);
        }
        if (bijhoudingsgemeente != null) {
            attributen.add(bijhoudingsgemeente);
        }
        if (tijdstipRegistratie != null) {
            attributen.add(tijdstipRegistratie);
        }
        return attributen;
    }

}

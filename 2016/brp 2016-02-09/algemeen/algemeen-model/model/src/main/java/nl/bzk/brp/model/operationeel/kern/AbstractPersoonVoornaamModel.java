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
import nl.bzk.brp.model.logisch.kern.PersoonVoornaam;
import nl.bzk.brp.model.logisch.kern.PersoonVoornaamBasis;

/**
 * Voornaam van een persoon
 *
 * Voornamen worden in de BRP los van elkaar geregistreerd. Het LO BRP is voorbereid op het kunnen vastleggen van
 * voornamen zoals 'Jan Peter', 'Aberto di Maria' of 'Wonder op aarde' als één enkele voornaam. In de BRP is het
 * namelijk niet noodzakelijk (conform LO 3.x) om de verschillende woorden aan elkaar te plakken met een koppelteken.
 *
 * Het gebruik van de spatie als koppelteken is echter (nog) niet toegestaan.
 *
 * Indien er sprake is van een namenreeks wordt dit opgenomen als geslachtsnaam; er is dan geen sprake van een voornaam.
 *
 * Een voornaam mag voorlopig nog geen spatie bevatten. Hiertoe dient eerst de akten van burgerlijke stand aangepast te
 * worden (zodat voornamen individueel kunnen worden vastgelegd, en er geen interpretatie meer nodig is van de ambtenaar
 * over waar de ene voornaam eindigt en een tweede begint). Daarnaast is er ook nog geen duidelijkheid over de wijze
 * waarop bestaande namen aangepast kunnen worden: kan de burger hier simpelweg om verzoeken en wordt het dan aangepast?
 *
 * De BRP is wel al voorbereid op het kunnen bevatten van spaties.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractPersoonVoornaamModel extends AbstractDynamischObject implements PersoonVoornaamBasis, ModelIdentificeerbaar<Integer>,
        VolgnummerBevattend
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
    private PersoonVoornaamStandaardGroepModel standaard;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractPersoonVoornaamModel() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param persoon persoon van Persoon \ Voornaam.
     * @param volgnummer volgnummer van Persoon \ Voornaam.
     */
    public AbstractPersoonVoornaamModel(final PersoonModel persoon, final VolgnummerAttribuut volgnummer) {
        this();
        this.persoon = persoon;
        this.volgnummer = volgnummer;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonVoornaam Te kopieren object type.
     * @param persoon Bijbehorende Persoon.
     */
    public AbstractPersoonVoornaamModel(final PersoonVoornaam persoonVoornaam, final PersoonModel persoon) {
        this();
        this.persoon = persoon;
        this.volgnummer = persoonVoornaam.getVolgnummer();
        if (persoonVoornaam.getStandaard() != null) {
            this.standaard = new PersoonVoornaamStandaardGroepModel(persoonVoornaam.getStandaard());
        }

    }

    /**
     * Retourneert ID van Persoon \ Voornaam.
     *
     * @return ID.
     */
    @Id
    @SequenceGenerator(name = "PERSOONVOORNAAM", sequenceName = "Kern.seq_PersVoornaam")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PERSOONVOORNAAM")
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
    public PersoonVoornaamStandaardGroepModel getStandaard() {
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
     * Zet Standaard van Persoon \ Voornaam.
     *
     * @param standaard Standaard.
     */
    public void setStandaard(final PersoonVoornaamStandaardGroepModel standaard) {
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

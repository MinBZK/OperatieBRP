/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.ist;

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
import nl.bzk.brp.model.logisch.ist.StapelVoorkomenBasis;

/**
 * Een voorkomen binnen een stapel.
 *
 * Een stapel bestaat uit één of meer "categorieën", zijnde de actuele categorie, en de daaraan voorafgaande/nu
 * historisch geworden waarden. Een "stapel voorkomen" kan worden beschouwd als een "rij" binnen de stapel.
 *
 * De IST (InterStelselTabellen) zijn noodzakelijkerwijs een 'brug' tussen enerzijds de modellering binnen de BRP, en
 * anderzijds de modellering binnen de GBA c.q. de LO3.x wereld. Hierbij is voor de STRUCTUUR vooral gekeken naar de
 * LO3.x wereld: deze kent stapels van categorieën, en 'voorkomens' daarbinnen (in terminologie van LO3.x zijn dit
 * overigens categorieën). Voor individuele gegevenselementen is - daar waar mogelijk - juist gekozen voor de BRP
 * modellering. De reden hiervoor is dat de IST gegevens ook jaren ná uitfasering van de huidige GBA gebruikt zullen
 * worden (c.q.: geraadpleegd zullen worden); het is dan fijn om aan te sluiten bij de overige BRP gegevens qua
 * definitie en toelichting. Een voorbeeld waarbij dit speelt: LO3.x kent een enkel attribuut 72.10
 * "omschrijving van de aangifte adreshouding", waar de BRP er twéé kent: een "reden wijziging" (van een adres) en een
 * "aangever adreshouding". De vertaling tussen het ene LO3.x attribuut en de twee BRP attributen is éénduidig; het
 * voordeel van een modellering conform BRP formaat is dat de raadplegende ambtenaar van de toekomst alleen nog de BRP
 * attributen hoeft te kennen, en niet (opnieuw) hoeft te worden ingewijd in de terminologie van LO3.x. Op een paar
 * plaatsen gaat dit niet, omdat de LO3.x attributen geen directe BRP tegenhanger hebben. In deze situatie is gekozen
 * voor een herkenbare naamgeving (bijv. "Rubriek 8220 Datum Document") opdat deze attributen duidelijk herkenbaar zijn.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractStapelVoorkomenModel extends AbstractDynamischObject implements StapelVoorkomenBasis, ModelIdentificeerbaar<Integer>,
        VolgnummerBevattend
{

    @Transient
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Stapel")
    private StapelModel stapel;

    @Embedded
    @AttributeOverride(name = VolgnummerAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Volgnr"))
    @JsonProperty
    private VolgnummerAttribuut volgnummer;

    @Embedded
    @JsonProperty
    private StapelVoorkomenStandaardGroepModel standaard;

    @Embedded
    @JsonProperty
    private StapelVoorkomenCategorieOudersGroepModel categorieOuders;

    @Embedded
    @JsonProperty
    private StapelVoorkomenCategorieGerelateerdenGroepModel categorieGerelateerden;

    @Embedded
    @JsonProperty
    private StapelVoorkomenCategorieHuwelijkGeregistreerdPartnerschapGroepModel categorieHuwelijkGeregistreerdPartnerschap;

    @Embedded
    @JsonProperty
    private StapelVoorkomenCategorieGezagsverhoudingGroepModel categorieGezagsverhouding;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractStapelVoorkomenModel() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param stapel stapel van Stapel voorkomen.
     * @param volgnummer volgnummer van Stapel voorkomen.
     */
    public AbstractStapelVoorkomenModel(final StapelModel stapel, final VolgnummerAttribuut volgnummer) {
        this();
        this.stapel = stapel;
        this.volgnummer = volgnummer;

    }

    /**
     * Retourneert ID van Stapel voorkomen.
     *
     * @return ID.
     */
    @Id
    @SequenceGenerator(name = "STAPELVOORKOMEN", sequenceName = "IST.seq_StapelVoorkomen")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "STAPELVOORKOMEN")
    @Access(value = AccessType.PROPERTY)
    public Integer getID() {
        return iD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StapelModel getStapel() {
        return stapel;
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
    public StapelVoorkomenStandaardGroepModel getStandaard() {
        return standaard;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StapelVoorkomenCategorieOudersGroepModel getCategorieOuders() {
        return categorieOuders;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StapelVoorkomenCategorieGerelateerdenGroepModel getCategorieGerelateerden() {
        return categorieGerelateerden;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StapelVoorkomenCategorieHuwelijkGeregistreerdPartnerschapGroepModel getCategorieHuwelijkGeregistreerdPartnerschap() {
        return categorieHuwelijkGeregistreerdPartnerschap;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StapelVoorkomenCategorieGezagsverhoudingGroepModel getCategorieGezagsverhouding() {
        return categorieGezagsverhouding;
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
     * Zet Standaard van Stapel voorkomen.
     *
     * @param standaard Standaard.
     */
    public void setStandaard(final StapelVoorkomenStandaardGroepModel standaard) {
        this.standaard = standaard;
    }

    /**
     * Zet Categorie ouders van Stapel voorkomen.
     *
     * @param categorieOuders Categorie ouders.
     */
    public void setCategorieOuders(final StapelVoorkomenCategorieOudersGroepModel categorieOuders) {
        this.categorieOuders = categorieOuders;
    }

    /**
     * Zet Categorie gerelateerden van Stapel voorkomen.
     *
     * @param categorieGerelateerden Categorie gerelateerden.
     */
    public void setCategorieGerelateerden(final StapelVoorkomenCategorieGerelateerdenGroepModel categorieGerelateerden) {
        this.categorieGerelateerden = categorieGerelateerden;
    }

    /**
     * Zet Categorie Huwelijk/Geregistreerd partnerschap van Stapel voorkomen.
     *
     * @param categorieHuwelijkGeregistreerdPartnerschap Categorie Huwelijk/Geregistreerd partnerschap.
     */
    public void setCategorieHuwelijkGeregistreerdPartnerschap(
        final StapelVoorkomenCategorieHuwelijkGeregistreerdPartnerschapGroepModel categorieHuwelijkGeregistreerdPartnerschap)
    {
        this.categorieHuwelijkGeregistreerdPartnerschap = categorieHuwelijkGeregistreerdPartnerschap;
    }

    /**
     * Zet Categorie gezagsverhouding van Stapel voorkomen.
     *
     * @param categorieGezagsverhouding Categorie gezagsverhouding.
     */
    public void setCategorieGezagsverhouding(final StapelVoorkomenCategorieGezagsverhoudingGroepModel categorieGezagsverhouding) {
        this.categorieGezagsverhouding = categorieGezagsverhouding;
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
        if (categorieOuders != null) {
            groepen.add(categorieOuders);
        }
        if (categorieGerelateerden != null) {
            groepen.add(categorieGerelateerden);
        }
        if (categorieHuwelijkGeregistreerdPartnerschap != null) {
            groepen.add(categorieHuwelijkGeregistreerdPartnerschap);
        }
        if (categorieGezagsverhouding != null) {
            groepen.add(categorieGezagsverhouding);
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

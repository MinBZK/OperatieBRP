/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.ist;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.annotation.Generated;
import javax.persistence.Access;
import javax.persistence.AccessType;
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
import nl.bzk.brp.model.algemeen.attribuuttype.ist.LO3CategorieAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.basis.AbstractDynamischObject;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.ComparatorFactory;
import nl.bzk.brp.model.basis.GesorteerdeSetOpVolgnummer;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.basis.VolgnummerBevattend;
import nl.bzk.brp.model.basis.VolgnummerComparator;
import nl.bzk.brp.model.logisch.ist.StapelBasis;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

/**
 * De categoriestapel van een LO3 persoon
 *
 * In LO3.x wordt niet over objecten gesproken, maar over categorieën. Van sommige categorieën (maar niet alle) kunnen
 * meerdere stapels aanwezig zijn.
 *
 * Overwegingen:
 *
 * 1) stapel in één structuur. Vier structuren: - categorie 5, een huwelijk met stapels vanuit beide partners. In
 * essentie nodig: verwijzing naar relatie(s), en verwijzing naar partners. Verder essentiële naamgegevens, en gegevens
 * over relatie inclusief document en opneming etc. Verder een volgnummer binnen de categorie, omdat er meer 'categorie
 * 5' stapels kunnen zijn. - categorie 2 en 2, informatie over de ouders. In essentie nodig: verwijzing naar de relatie.
 * Geen verwijzing naar de specifieke ouder(!), dezelfde stapel kan namelijk meer verschillende ouders bevatten. Relatie
 * naar het kind is mogelijk, edoch in essentie overbodig als er al een relatie naar zijn FRB is. Verder essentiële
 * naamgegevens, en gegevens over document en opneming etc. Volgnummer eigenlijk niet nodig, omdat er voor categorie 2
 * en 3 elk precies één stapel is. - categorie 9, een verwijzing naar een kind. In essentie nodig: verwijzing naar de
 * relatie (van het kind), en een verwijzing naar de ouder voor wie deze stapel van toepassing is. Volgnummer, omdat er
 * meerdere categorie 9 stapels kunnen zijn. - categorie 11, een uitspraak over gezag, een verwijzing naar OF het kind,
 * OF de FRB: heb je de één dan heb je de ander. Volgnummer overbodig, want je hebt maar één stapel voor categorie 11.
 * NB: categorie 11 hoeft niet synchroon te lopen met categorie 2 of 3 ==> eigen stapel!
 *
 * MB: als relatie=FRB, en persoon is het kind daarin, dan TOCH persoon vullen, en wel met kind. Dus: categorie 2, 3 en
 * 11: persoon = kind van FRB.
 *
 * --aandachtspunt: weggooien en opnieuw aanmaken & uit de sleutels lopen(!)
 *
 * Uitgangspunten: IST is géén boedelbak. Dus bijvoorbeeld: geen materiële historie op naamgebruik toevoegen aan de IST.
 *
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractStapelModel extends AbstractDynamischObject implements StapelBasis, ModelIdentificeerbaar<Integer>, VolgnummerBevattend {

    @Transient
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Pers")
    @JsonProperty
    private PersoonModel persoon;

    @Embedded
    @AttributeOverride(name = LO3CategorieAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Categorie"))
    @JsonProperty
    private LO3CategorieAttribuut categorie;

    @Embedded
    @AttributeOverride(name = VolgnummerAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Volgnr"))
    @JsonProperty
    private VolgnummerAttribuut volgnummer;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "Stapel")
    @JsonProperty
    private Set<StapelRelatieModel> stapelRelaties;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "Stapel")
    @JsonProperty
    @JsonDeserialize(as = GesorteerdeSetOpVolgnummer.class)
    @Sort(type = SortType.COMPARATOR, comparator = VolgnummerComparator.class)
    private SortedSet<StapelVoorkomenModel> stapelVoorkomens;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractStapelModel() {
        stapelRelaties = new HashSet<StapelRelatieModel>();
        stapelVoorkomens = new TreeSet<StapelVoorkomenModel>(ComparatorFactory.getComparatorVoorStapelVoorkomen());

    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param persoon persoon van Stapel.
     * @param categorie categorie van Stapel.
     * @param volgnummer volgnummer van Stapel.
     */
    public AbstractStapelModel(final PersoonModel persoon, final LO3CategorieAttribuut categorie, final VolgnummerAttribuut volgnummer) {
        this();
        this.persoon = persoon;
        this.categorie = categorie;
        this.volgnummer = volgnummer;

    }

    /**
     * Retourneert ID van Stapel.
     *
     * @return ID.
     */
    @Id
    @SequenceGenerator(name = "STAPEL", sequenceName = "IST.seq_Stapel")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "STAPEL")
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
    public LO3CategorieAttribuut getCategorie() {
        return categorie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VolgnummerAttribuut getVolgnummer() {
        return volgnummer;
    }

    /**
     * Retourneert Stapel \ Relaties van Stapel.
     *
     * @return Stapel \ Relaties van Stapel.
     */
    public Set<StapelRelatieModel> getStapelRelaties() {
        return stapelRelaties;
    }

    /**
     * Retourneert Stapel voorkomens van Stapel.
     *
     * @return Stapel voorkomens van Stapel.
     */
    public SortedSet<StapelVoorkomenModel> getStapelVoorkomens() {
        return stapelVoorkomens;
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
     * Geeft alle groepen van de object met uitzondering van groepen die null zijn.
     *
     * @return Lijst met groepen.
     */
    public List<Groep> getGroepen() {
        final List<Groep> groepen = new ArrayList<>();
        return groepen;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van het object.
     */
    public List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (categorie != null) {
            attributen.add(categorie);
        }
        if (volgnummer != null) {
            attributen.add(volgnummer);
        }
        return attributen;
    }

}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.ist;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.HashSet;
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
import nl.bzk.brp.model.basis.GesorteerdeSetOpVolgnummer;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.basis.VolgnummerComparator;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.ist.StapelHisVolledigBasis;
import nl.bzk.brp.model.hisvolledig.kern.HisVolledigComparatorFactory;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

/**
 * HisVolledig klasse voor Stapel.
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigModelGenerator")
@MappedSuperclass
public abstract class AbstractStapelHisVolledigImpl implements HisVolledigImpl, StapelHisVolledigBasis {

    @Transient
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Pers")
    @JsonProperty
    private PersoonHisVolledigImpl persoon;

    @Embedded
    @AttributeOverride(name = LO3CategorieAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Categorie"))
    @JsonProperty
    private LO3CategorieAttribuut categorie;

    @Embedded
    @AttributeOverride(name = VolgnummerAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Volgnr"))
    @JsonProperty
    private VolgnummerAttribuut volgnummer;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "stapel", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    @JsonProperty
    @JsonManagedReference
    private Set<StapelRelatieHisVolledigImpl> stapelRelaties;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "stapel", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    @JsonProperty
    @JsonManagedReference
    @JsonDeserialize(as = GesorteerdeSetOpVolgnummer.class)
    @Sort(type = SortType.COMPARATOR, comparator = VolgnummerComparator.class)
    private SortedSet<StapelVoorkomenHisVolledigImpl> stapelVoorkomens;

    /**
     * Default contructor voor JPA.
     *
     */
    protected AbstractStapelHisVolledigImpl() {
        stapelRelaties = new HashSet<StapelRelatieHisVolledigImpl>();
        stapelVoorkomens = new TreeSet<StapelVoorkomenHisVolledigImpl>(HisVolledigComparatorFactory.getComparatorVoorStapelVoorkomen());

    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param persoon persoon van Stapel.
     * @param categorie categorie van Stapel.
     * @param volgnummer volgnummer van Stapel.
     */
    public AbstractStapelHisVolledigImpl(final PersoonHisVolledigImpl persoon, final LO3CategorieAttribuut categorie, final VolgnummerAttribuut volgnummer)
    {
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
     * Retourneert Persoon van Stapel.
     *
     * @return Persoon.
     */
    public PersoonHisVolledigImpl getPersoon() {
        return persoon;
    }

    /**
     * Retourneert Categorie van Stapel.
     *
     * @return Categorie.
     */
    public LO3CategorieAttribuut getCategorie() {
        return categorie;
    }

    /**
     * Retourneert Volgnummer van Stapel.
     *
     * @return Volgnummer.
     */
    public VolgnummerAttribuut getVolgnummer() {
        return volgnummer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<StapelRelatieHisVolledigImpl> getStapelRelaties() {
        return stapelRelaties;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SortedSet<StapelVoorkomenHisVolledigImpl> getStapelVoorkomens() {
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
     * Zet lijst van Stapel \ Relatie.
     *
     * @param stapelRelaties lijst van Stapel \ Relatie
     */
    public void setStapelRelaties(final Set<StapelRelatieHisVolledigImpl> stapelRelaties) {
        this.stapelRelaties = stapelRelaties;
    }

    /**
     * Zet lijst van Stapel voorkomen.
     *
     * @param stapelVoorkomens lijst van Stapel voorkomen
     */
    public void setStapelVoorkomens(final SortedSet<StapelVoorkomenHisVolledigImpl> stapelVoorkomens) {
        this.stapelVoorkomens = stapelVoorkomens;
    }

}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import nl.bzk.algemeenbrp.dal.domein.brp.annotation.IndicatieActueelEnGeldig;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;
import org.hibernate.annotations.Any;
import org.hibernate.annotations.AnyMetaDef;
import org.hibernate.annotations.MetaValue;

/**
 * The persistent class for the gegeveninonderzoek database table.
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "gegeveninonderzoekormmapping", schema = "kern")
public class GegevenInOnderzoek extends AbstractEntiteit implements Afleidbaar, ALaagHistorieVerzameling, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "gegeveninonderzoek_id_generator", sequenceName = "kern.seq_gegeveninonderzoek", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gegeveninonderzoek_id_generator")
    @Column(nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "onderzoek", nullable = false)
    private Onderzoek onderzoek;

    @Column(name = "element", nullable = false)
    private int elementId;

    @Column(name = "indag")
    private boolean isActueelEnGeldig;

    @IndicatieActueelEnGeldig(naam = "isActueelEnGeldig")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "gegevenInOnderzoek", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true)
    private final Set<GegevenInOnderzoekHistorie> gegevenInOnderzoekHistorieSet = new LinkedHashSet<>(0);

    @Any(metaColumn = @Column(name = "tblvoorkomen"))
    @AnyMetaDef(idType = "long", metaType = "int", metaValues = {
            @MetaValue(value = "3211", targetEntity = BetrokkenheidOuderlijkGezagHistorie.class),
            @MetaValue(value = "3774", targetEntity = OnderzoekHistorie.class),
            @MetaValue(value = "4618", targetEntity = PartijHistorie.class),
            @MetaValue(value = "6063", targetEntity = PersoonAdresHistorie.class),
            @MetaValue(value = "3909", targetEntity = PersoonAfgeleidAdministratiefHistorie.class),
            @MetaValue(value = "3664", targetEntity = PersoonBijhoudingHistorie.class),
            @MetaValue(value = "3901", targetEntity = PersoonDeelnameEuVerkiezingenHistorie.class),
            @MetaValue(value = "3598", targetEntity = PersoonGeslachtsnaamcomponentHistorie.class),
            @MetaValue(value = "3654", targetEntity = PersoonIndicatieHistorie.class),
            @MetaValue(value = "3521", targetEntity = PersoonInschrijvingHistorie.class),
            @MetaValue(value = "3790", targetEntity = PersoonMigratieHistorie.class),
            @MetaValue(value = "3487", targetEntity = PersoonNaamgebruikHistorie.class),
            @MetaValue(value = "3604", targetEntity = PersoonNationaliteitHistorie.class),
            @MetaValue(value = "10900", targetEntity = PersoonNummerverwijzingHistorie.class),
            @MetaValue(value = "3515", targetEntity = PersoonOverlijdenHistorie.class),
            @MetaValue(value = "3662", targetEntity = PersoonPersoonskaartHistorie.class),
            @MetaValue(value = "3577", targetEntity = PersoonReisdocumentHistorie.class),
            @MetaValue(value = "3519", targetEntity = PersoonUitsluitingKiesrechtHistorie.class),
            @MetaValue(value = "3517", targetEntity = PersoonVerblijfsrechtHistorie.class),
            @MetaValue(value = "3783", targetEntity = PersoonVerificatieHistorie.class),
            @MetaValue(value = "9347", targetEntity = PersoonVerstrekkingsbeperkingHistorie.class),
            @MetaValue(value = "3050", targetEntity = PersoonVoornaamHistorie.class),
            @MetaValue(value = "37653", targetEntity = PersoonBuitenlandsPersoonsnummerHistorie.class),
            // GerelateerdeKind.Persoon
            @MetaValue(value = "12925", targetEntity = PersoonIDHistorie.class),
            @MetaValue(value = "12938", targetEntity = PersoonSamengesteldeNaamHistorie.class),
            @MetaValue(value = "12956", targetEntity = PersoonGeboorteHistorie.class),
            // GerelateerdeOuder.Persoon
            @MetaValue(value = "12827", targetEntity = BetrokkenheidOuderHistorie.class),
            @MetaValue(value = "12843", targetEntity = PersoonIDHistorie.class),
            @MetaValue(value = "12856", targetEntity = PersoonSamengesteldeNaamHistorie.class),
            @MetaValue(value = "12874", targetEntity = PersoonGeboorteHistorie.class),
            @MetaValue(value = "12889", targetEntity = PersoonGeslachtsaanduidingHistorie.class),
            // GerelateerdeHuwelijkspartner.Persoon
            @MetaValue(value = "12996", targetEntity = PersoonIDHistorie.class),
            @MetaValue(value = "13009", targetEntity = PersoonSamengesteldeNaamHistorie.class),
            @MetaValue(value = "13027", targetEntity = PersoonGeboorteHistorie.class),
            @MetaValue(value = "13042", targetEntity = PersoonGeslachtsaanduidingHistorie.class),
            // GerelateerdeGeregistreerdePartner.Persoon
            @MetaValue(value = "13078", targetEntity = PersoonIDHistorie.class),
            @MetaValue(value = "13091", targetEntity = PersoonSamengesteldeNaamHistorie.class),
            @MetaValue(value = "13109", targetEntity = PersoonGeboorteHistorie.class),
            @MetaValue(value = "13124", targetEntity = PersoonGeslachtsaanduidingHistorie.class),
            // Persoon
            @MetaValue(value = "3344", targetEntity = PersoonIDHistorie.class),
            @MetaValue(value = "3557", targetEntity = PersoonSamengesteldeNaamHistorie.class),
            @MetaValue(value = "3514", targetEntity = PersoonGeboorteHistorie.class),
            @MetaValue(value = "3554", targetEntity = PersoonGeslachtsaanduidingHistorie.class),
            // BetrokkenheidOuderHistorie Ouder. Deze moet eerder dan 22211 en is alleen nodig voor de ORM mapping
            @MetaValue(value = "3858", targetEntity = BetrokkenheidOuderHistorie.class),
            // BetrokkenheidOuderHistorie Persoon.Ouder
            @MetaValue(value = "22211", targetEntity = BetrokkenheidOuderHistorie.class),
            // BetrokkenheidOuderHistorie GerelateerderOuder.Ouder
            @MetaValue(value = "2071", targetEntity = BetrokkenheidHistorie.class),

            // Relatie historie -> Huwelijk.Standaard
            @MetaValue(value = "13848", targetEntity = RelatieHistorie.class),
            // Relatie historie -> GeregistreerdPartnerschap.Standaard
            @MetaValue(value = "12751", targetEntity = RelatieHistorie.class),
            // Relatie historie -> Relatie.Standaard
            @MetaValue(value = "3599", targetEntity = RelatieHistorie.class)})
    @JoinColumn(name = "voorkomensleutelgegeven")
    private FormeleHistorie voorkomen;

    @Any(metaColumn = @Column(name = "tblobject"))
    @AnyMetaDef(idType = "long", metaType = "int", metaValues = {
            @MetaValue(value = "3071", targetEntity = BRPActie.class),
            @MetaValue(value = "8118", targetEntity = ActieBron.class),
            @MetaValue(value = "9018", targetEntity = AdministratieveHandeling.class),
            @MetaValue(value = "3857", targetEntity = Betrokkenheid.class),
            @MetaValue(value = "3135", targetEntity = Document.class),
            @MetaValue(value = "3863", targetEntity = GegevenInOnderzoek.class),
            @MetaValue(value = "3167", targetEntity = Onderzoek.class),
            @MetaValue(value = "3141", targetEntity = Partij.class),
            @MetaValue(value = "3010", targetEntity = Persoon.class),
            @MetaValue(value = "3237", targetEntity = PersoonAdres.class),
            @MetaValue(value = "3020", targetEntity = PersoonGeslachtsnaamcomponent.class),
            @MetaValue(value = "3637", targetEntity = PersoonIndicatie.class),
            @MetaValue(value = "3129", targetEntity = PersoonNationaliteit.class),
            @MetaValue(value = "3576", targetEntity = PersoonReisdocument.class),
            @MetaValue(value = "3775", targetEntity = PersoonVerificatie.class),
            @MetaValue(value = "9344", targetEntity = PersoonVerstrekkingsbeperking.class),
            @MetaValue(value = "3022", targetEntity = PersoonVoornaam.class),
            @MetaValue(value = "33381", targetEntity = PersoonBuitenlandsPersoonsnummer.class),
            @MetaValue(value = "3184", targetEntity = Relatie.class)})
    @JoinColumn(name = "objectsleutelgegeven")
    private Entiteit entiteit;

    /**
     * JPA default constructor.
     */
    protected GegevenInOnderzoek() {
    }

    /**
     * Maak een nieuwe gegeven in onderzoek.
     * @param onderzoek onderzoek
     * @param soortGegeven soort gegeven
     */
    public GegevenInOnderzoek(final Onderzoek onderzoek, final Element soortGegeven) {
        setOnderzoek(onderzoek);
        setSoortGegeven(soortGegeven);

    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.algemeen.dal.domein.brp.kern.entity.DeltaEntiteit#getId()
     */
    @Override
    public Long getId() {
        return id;
    }

    /**
     * Zet de waarden voor id van GegevenInOnderzoek.
     * @param id de nieuwe waarde voor id van GegevenInOnderzoek
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van onderzoek van GegevenInOnderzoek.
     * @return de waarde van onderzoek van GegevenInOnderzoek
     */
    public Onderzoek getOnderzoek() {
        return onderzoek;
    }

    /**
     * Zet de waarden voor onderzoek van GegevenInOnderzoek.
     * @param onderzoek de nieuwe waarde voor onderzoek van GegevenInOnderzoek
     */
    public void setOnderzoek(final Onderzoek onderzoek) {
        ValidationUtils.controleerOpNullWaarden("onderzoek mag niet null zijn", onderzoek);
        this.onderzoek = onderzoek;
    }

    /**
     * Geef de waarde van soort gegeven van GegevenInOnderzoek.
     * @return de waarde van soort gegeven van GegevenInOnderzoek
     */
    public Element getSoortGegeven() {
        return Element.parseId(elementId);
    }

    /**
     * Zet de waarden voor soort gegeven van GegevenInOnderzoek.
     * @param soortGegeven de nieuwe waarde voor soort gegeven van GegevenInOnderzoek
     */
    public void setSoortGegeven(final Element soortGegeven) {
        ValidationUtils.controleerOpNullWaarden("element mag niet null zijn", soortGegeven);
        elementId = soortGegeven.getId();
    }

    /**
     * Zet de waarden voor object of voorkomen van GegevenInOnderzoek.
     * @param entiteitParam de nieuwe waarde voor object of voorkomen van GegevenInOnderzoek
     */
    public void setEntiteitOfVoorkomen(final Entiteit entiteitParam) {
        ValidationUtils.controleerOpNullWaarden("Het gegeven dat in onderzoek staat mag niet NULL zijn", entiteitParam);
        verbreekBestaandeKoppelingEntiteit();
        if (entiteitParam instanceof FormeleHistorie) {
            voorkomen = (FormeleHistorie) entiteitParam;

        } else {
            entiteit = entiteitParam;
        }

        if (entiteitParam != null) {
            entiteitParam.setGegevenInOnderzoek(this);
        }
    }

    /**
     * Verbreekt de koppeling vanuit een {@link Entiteit} of {@link FormeleHistorie} naar deze
     * instantie.
     */
    private void verbreekBestaandeKoppelingEntiteit() {
        final Entiteit teVerbrekenKoppeling = getEntiteitOfVoorkomen();
        if (teVerbrekenKoppeling != null) {
            teVerbrekenKoppeling.verwijderGegevenInOnderzoek(getSoortGegeven());
        }
    }

    /**
     * Geef de waarde van gegevens in Onderzoek historie set van {@link GegevenInOnderzoek}.
     * @return de waarde van gegevens in onderzoek historie set van {@link GegevenInOnderzoek}
     */
    public Set<GegevenInOnderzoekHistorie> getGegevenInOnderzoekHistorieSet() {
        return gegevenInOnderzoekHistorieSet;
    }

    /**
     * Toevoegen van een gegevens in onderzoek historie.
     * @param gegevenInOnderzoekHistorie gegevens in onderzoek historie
     */
    public void addGegevenInOnderzoekHistorie(final GegevenInOnderzoekHistorie gegevenInOnderzoekHistorie) {
        gegevenInOnderzoekHistorie.setGegevenInOnderzoek(this);
        gegevenInOnderzoekHistorieSet.add(gegevenInOnderzoekHistorie);
    }

    /**
     * Geef de waarde van object of voorkomen van GegevenInOnderzoek.
     * @return de waarde van object of voorkomen van GegevenInOnderzoek
     */
    public Entiteit getEntiteitOfVoorkomen() {
        return entiteit != null ? entiteit : voorkomen;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " -> " + (getEntiteitOfVoorkomen() != null ? getEntiteitOfVoorkomen().getClass().getSimpleName() : null);
    }

    @Override
    public Map<String, Collection<FormeleHistorie>> verzamelHistorie() {
        final Map<String, Collection<FormeleHistorie>> result = new HashMap<>();
        result.put("gegevensInOnderzoekHistorieSet", Collections.unmodifiableSet(gegevenInOnderzoekHistorieSet));
        return result;
    }

    /**
     * Geef de waarde van isActueelEnGeldig.
     * @return isActueelEnGeldig
     */
    public boolean isActueelEnGeldig() {
        return isActueelEnGeldig;
    }

    /**
     * Zet de waarde van isActueelEnGeldig.
     * @param actueelEnGeldig isActueelEnGeldig
     */
    public void setActueelEnGeldig(final boolean actueelEnGeldig) {
        isActueelEnGeldig = actueelEnGeldig;
    }
}

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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import nl.bzk.algemeenbrp.dal.domein.brp.annotation.IndicatieActueelEnGeldig;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;

/**
 * The persistent class for the betr database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "betr", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"relatie", "pers"}))
public class Betrokkenheid extends AbstractEntiteit implements Afleidbaar, RootEntiteit, Serializable {

    /** Veldnaam van persoon tbv verschil verwerking. */
    public static final String VELD_PERSOON = "persoon";
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "betr_id_generator", sequenceName = "kern.seq_betr", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "betr_id_generator")
    @Column(nullable = false)
    private Long id;

    @Column(name = "indouderuitwiekindisgeboren")
    private Boolean indicatieOuderUitWieKindIsGeboren;

    @Column(name = "indouderheeftgezag")
    private Boolean indicatieOuderHeeftGezag;

    /* Bij wijziging veld-naam ook de string constante daarvoor aanpassen */
    // bi-directional many-to-one association to Persoon
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "pers")
    private Persoon persoon;

    // bi-directional many-to-one association to Relatie
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "relatie", nullable = false)
    private Relatie relatie;

    @Column(name = "rol", nullable = false)
    private int soortBetrokkenheidId;

    @Column(name = "indag", nullable = false)
    private boolean isActueelEnGeldig;

    @Column(name = "indagouderschap", nullable = false)
    private boolean isActueelEnGeldigVoorOuderschap;

    @Column(name = "indagouderlijkgezag", nullable = false)
    private boolean isActueelEnGeldigVoorOuderlijkGezag;

    // bi-directional many-to-one association to BetrokkenheidOuderHistorie
    @IndicatieActueelEnGeldig(naam = "isActueelEnGeldigVoorOuderschap")
    @OneToMany(mappedBy = "betrokkenheid", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private final Set<BetrokkenheidOuderHistorie> betrokkenheidOuderHistorieSet = new LinkedHashSet<>(0);

    // bi-directional many-to-one association to BetrokkenheidOuderlijkGezagHistorie
    @IndicatieActueelEnGeldig(naam = "isActueelEnGeldigVoorOuderlijkGezag")
    @OneToMany(mappedBy = "betrokkenheid", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private final Set<BetrokkenheidOuderlijkGezagHistorie> betrokkenheidOuderlijkGezagHistorieSet = new LinkedHashSet<>(0);

    // bi-directional many-to-one association to BetrokkenheidHistorie
    @IndicatieActueelEnGeldig(naam = "isActueelEnGeldig")
    @OneToMany(mappedBy = "betrokkenheid", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private final Set<BetrokkenheidHistorie> betrokkenheidHistorieSet = new LinkedHashSet<>(0);

    // bi-directional one-to-one association met Lo3AanduidingOuder
    @OneToOne(mappedBy = "ouder", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private Lo3AanduidingOuder aanduidingOuder;

    /**
     * JPA default constructor.
     */
    protected Betrokkenheid() {}

    /**
     * Maak een nieuwe betrokkenheid.
     *
     * @param soortBetrokkenheid soort betrokkenheid
     * @param relatie relatie
     */
    public Betrokkenheid(final SoortBetrokkenheid soortBetrokkenheid, final Relatie relatie) {
        setSoortBetrokkenheid(soortBetrokkenheid);
        setRelatie(relatie);
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
     * Zet de waarden voor id van Betrokkenheid.
     *
     * @param id de nieuwe waarde voor id van Betrokkenheid
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van indicatie ouder uit wie kind is geboren van Betrokkenheid.
     *
     * @return de waarde van indicatie ouder uit wie kind is geboren van Betrokkenheid
     */
    public Boolean getIndicatieOuderUitWieKindIsGeboren() {
        return indicatieOuderUitWieKindIsGeboren;
    }

    /**
     * Zet de waarden voor indicatie ouder uit wie kind is geboren van Betrokkenheid.
     *
     * @param indicatieOuderUitWieKindIsGeboren de nieuwe waarde voor indicatie ouder uit wie kind
     *        is geboren van Betrokkenheid
     */
    public void setIndicatieOuderUitWieKindIsGeboren(final Boolean indicatieOuderUitWieKindIsGeboren) {
        this.indicatieOuderUitWieKindIsGeboren = indicatieOuderUitWieKindIsGeboren;
    }

    /**
     * Geef de waarde van indicatie ouder heeft gezag van Betrokkenheid.
     *
     * @return de waarde van indicatie ouder heeft gezag van Betrokkenheid
     */
    public Boolean getIndicatieOuderHeeftGezag() {
        return indicatieOuderHeeftGezag;
    }

    /**
     * Zet de waarden voor indicatie ouder heeft gezag van Betrokkenheid.
     *
     * @param indicatieOuderHeeftGezag de nieuwe waarde voor indicatie ouder heeft gezag van
     *        Betrokkenheid
     */
    public void setIndicatieOuderHeeftGezag(final Boolean indicatieOuderHeeftGezag) {
        this.indicatieOuderHeeftGezag = indicatieOuderHeeftGezag;
    }

    /**
     * Geef de waarde van persoon van Betrokkenheid.
     *
     * @return de waarde van persoon van Betrokkenheid
     */
    public Persoon getPersoon() {
        return persoon;
    }

    /**
     * Zet de waarden voor persoon van Betrokkenheid.
     *
     * @param persoon de nieuwe waarde voor persoon van Betrokkenheid
     */
    public void setPersoon(final Persoon persoon) {
        this.persoon = persoon;
    }

    /**
     * Geef de waarde van relatie van Betrokkenheid.
     *
     * @return de waarde van relatie van Betrokkenheid
     */
    public Relatie getRelatie() {
        return relatie;
    }

    /**
     * Zet de waarden voor relatie van Betrokkenheid.
     *
     * @param relatie de nieuwe waarde voor relatie van Betrokkenheid
     */
    public void setRelatie(final Relatie relatie) {
        ValidationUtils.controleerOpNullWaarden("relatie mag niet null zijn", relatie);
        this.relatie = relatie;
    }

    /**
     * Geef de waarde van soort betrokkenheid van Betrokkenheid.
     *
     * @return de waarde van soort betrokkenheid van Betrokkenheid
     */
    public SoortBetrokkenheid getSoortBetrokkenheid() {
        return SoortBetrokkenheid.parseId(soortBetrokkenheidId);
    }

    /**
     * Zet de waarden voor soort betrokkenheid van Betrokkenheid.
     *
     * @param soortBetrokkenheid de nieuwe waarde voor soort betrokkenheid van Betrokkenheid
     */
    public void setSoortBetrokkenheid(final SoortBetrokkenheid soortBetrokkenheid) {
        ValidationUtils.controleerOpNullWaarden("soortBetrokkenheid mag niet null zijn", soortBetrokkenheid);
        soortBetrokkenheidId = soortBetrokkenheid.getId();
    }

    /**
     * Geef de waarde van isActueelEnGeldig.
     *
     * @return isActueelEnGeldig
     */
    public boolean isActueelEnGeldig() {
        return isActueelEnGeldig;
    }

    /**
     * Zet de waarde van isActueelEnGeldig.
     *
     * @param actueelEnGeldig isActueelEnGeldig
     */
    public void setActueelEnGeldig(final boolean actueelEnGeldig) {
        isActueelEnGeldig = actueelEnGeldig;
    }

    /**
     * Geef de waarde van isActueelEnGeldigVoorOuderschap.
     *
     * @return isActueelEnGeldigVoorOuderschap
     */
    public boolean isActueelEnGeldigVoorOuderschap() {
        return isActueelEnGeldigVoorOuderschap;
    }

    /**
     * Zet de waarde van isActueelEnGeldigVoorOuderschap.
     *
     * @param actueelEnGeldigVoorOuderschap isActueelEnGeldigVoorOuderschap
     */
    public void setActueelEnGeldigVoorOuderschap(final boolean actueelEnGeldigVoorOuderschap) {
        isActueelEnGeldigVoorOuderschap = actueelEnGeldigVoorOuderschap;
    }

    /**
     * Geef de waarde van isActueelEnGeldigVoorOuderlijkGezag.
     *
     * @return isActueelEnGeldigVoorOuderlijkGezag
     */
    public boolean isActueelEnGeldigVoorOuderlijkGezag() {
        return isActueelEnGeldigVoorOuderlijkGezag;
    }

    /**
     * Zet de waarde van isActueelEnGeldigVoorOuderlijkGezag.
     *
     * @param actueelEnGeldigVoorOuderlijkGezag isActueelEnGeldigVoorOuderlijkGezag
     */
    public void setActueelEnGeldigVoorOuderlijkGezag(final boolean actueelEnGeldigVoorOuderlijkGezag) {
        isActueelEnGeldigVoorOuderlijkGezag = actueelEnGeldigVoorOuderlijkGezag;
    }

    /**
     * Toevoegen van een betrokkenheid ouder historie.
     *
     * @param betrokkenheidOuderHistorie betrokkenheid ouder historie
     */
    public void addBetrokkenheidOuderHistorie(final BetrokkenheidOuderHistorie betrokkenheidOuderHistorie) {
        betrokkenheidOuderHistorie.setBetrokkenheid(this);
        betrokkenheidOuderHistorieSet.add(betrokkenheidOuderHistorie);
    }

    /**
     * Verwijderen van een betrokkenheid ouder historie.
     *
     * @param betrokkenheidOuderHistorie betrokkenheid ouder historie
     * @return true, if successful
     */
    public boolean removeBetrokkenheidOuderHistorie(final BetrokkenheidOuderHistorie betrokkenheidOuderHistorie) {
        return betrokkenheidOuderHistorieSet.remove(betrokkenheidOuderHistorie);
    }

    /**
     * Geef de waarde van betrokkenheid ouder historie set van Betrokkenheid.
     *
     * @return de waarde van betrokkenheid ouder historie set van Betrokkenheid
     */
    public Set<BetrokkenheidOuderHistorie> getBetrokkenheidOuderHistorieSet() {
        return betrokkenheidOuderHistorieSet;
    }

    /**
     * Geef de waarde van betrokkenheid ouderlijk gezag historie set van Betrokkenheid.
     *
     * @return de waarde van betrokkenheid ouderlijk gezag historie set van Betrokkenheid
     */
    public Set<BetrokkenheidOuderlijkGezagHistorie> getBetrokkenheidOuderlijkGezagHistorieSet() {
        return betrokkenheidOuderlijkGezagHistorieSet;
    }

    /**
     * Toevoegen van een betrokkenheid ouderlijk gezag historie.
     *
     * @param betrokkenheidOuderlijkGezagHistorie betrokkenheid ouderlijk gezag historie
     */
    public void addBetrokkenheidOuderlijkGezagHistorie(final BetrokkenheidOuderlijkGezagHistorie betrokkenheidOuderlijkGezagHistorie) {
        betrokkenheidOuderlijkGezagHistorie.setBetrokkenheid(this);
        betrokkenheidOuderlijkGezagHistorieSet.add(betrokkenheidOuderlijkGezagHistorie);
    }

    /**
     * Verwijderen van een betrokkenheid ouderlijk gezag historie.
     *
     * @param betrokkenheidOuderlijkGezagHistorie betrokkenheid ouderlijk gezag historie
     * @return true, if successful
     */
    public boolean removeBetrokkenheidOuderlijkGezagHistorie(final BetrokkenheidOuderlijkGezagHistorie betrokkenheidOuderlijkGezagHistorie) {
        return betrokkenheidOuderlijkGezagHistorieSet.remove(betrokkenheidOuderlijkGezagHistorie);
    }

    /**
     * Geef de waarde van betrokkenheid historie set van Betrokkenheid.
     *
     * @return de waarde van betrokkenheid historie set van Betrokkenheid
     */
    public Set<BetrokkenheidHistorie> getBetrokkenheidHistorieSet() {
        return betrokkenheidHistorieSet;
    }

    /**
     * Toevoegen van een betrokkenheid historie.
     *
     * @param betrokkenheidHistorie betrokkenheid historie
     */
    public void addBetrokkenheidHistorie(final BetrokkenheidHistorie betrokkenheidHistorie) {
        betrokkenheidHistorie.setBetrokkenheid(this);
        betrokkenheidHistorieSet.add(betrokkenheidHistorie);
    }

    /**
     * Geef de waarde van aanduiding ouder van Betrokkenheid.
     *
     * @return de waarde van aanduiding ouder van Betrokkenheid
     */
    public Lo3AanduidingOuder getAanduidingOuder() {
        return aanduidingOuder;
    }

    /**
     * Zet de waarden voor aanduiding ouder van Betrokkenheid.
     *
     * @param aanduidingOuder de nieuwe waarde voor aanduiding ouder van Betrokkenheid
     */
    public void setAanduidingOuder(final Lo3AanduidingOuder aanduidingOuder) {
        this.aanduidingOuder = aanduidingOuder;
    }

    @Override
    public Map<String, Collection<FormeleHistorie>> verzamelHistorie() {
        final Map<String, Collection<FormeleHistorie>> result = new HashMap<>();

        result.put("betrokkenheidHistorieSet", Collections.unmodifiableSet(betrokkenheidHistorieSet));
        result.put("betrokkenheidOuderHistorieSet", Collections.unmodifiableSet(betrokkenheidOuderHistorieSet));
        result.put("betrokkenheidOuderlijkGezagHistorieSet", Collections.unmodifiableSet(betrokkenheidOuderlijkGezagHistorieSet));

        if (persoon != null && !SoortPersoon.INGESCHREVENE.equals(persoon.getSoortPersoon())) {
            result.putAll(persoon.verzamelHistorie());
        }
        return result;
    }

    /**
     * Filtert uit de gegeven set aan betrokkenheden de set van actuele betrokkenheden.
     *
     * @param betrokkenheden de set van betrokkenheden
     * @return alleen de actuele betrokkenheden uit de gegeven set of een lege set als deze niet gevonden worden
     */
    static Set<Betrokkenheid> getActueleBetrokkenheden(final Set<Betrokkenheid> betrokkenheden) {
        final Set<Betrokkenheid> result = new LinkedHashSet<>();
        for (final Betrokkenheid betrokkenheid : betrokkenheden) {
            if (FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(betrokkenheid.getBetrokkenheidHistorieSet()) != null) {
                result.add(betrokkenheid);
            }
        }
        return result;
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

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

import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;

/**
 * The persistent class for the betr database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "betr", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"relatie", "pers" }))
@SuppressWarnings("checkstyle:designforextension")
public class Betrokkenheid extends AbstractDeltaEntiteit implements DeltaRootEntiteit, Serializable {

    /** Veldnaam van persoon tbv verschil verwerking. */
    public static final String PERSOON = "persoon";
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "betr_id_generator", sequenceName = "kern.seq_betr", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "betr_id_generator")
    @Column(nullable = false)
    /**
     * Het veld zou Integer moeten zijn maar is Long i.v.m. de link naar GegevenInOnderzoek.
     */
    private Long id;

    @Column(name = "indouder")
    private Boolean indicatieOuder;

    @Column(name = "indouderuitwiekindisgeboren")
    private Boolean indicatieOuderUitWieKindIsGeboren;

    @Column(name = "indouderheeftgezag")
    private Boolean indicatieOuderHeeftGezag;

    /* Bij wijziging veld-naam ook de string constante daarvoor aanpassen */
    // bi-directional many-to-one association to Persoon
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "pers")
    private Persoon persoon;

    // bi-directional many-to-one association to Relatie
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "relatie", nullable = false)
    private Relatie relatie;

    @Column(name = "rol", nullable = false)
    private short soortBetrokkenheidId;

    // bi-directional many-to-one association to BetrokkenheidOuderHistorie
    @OneToMany(mappedBy = "betrokkenheid", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE }, orphanRemoval = true)
    private Set<BetrokkenheidOuderHistorie> betrokkenheidOuderHistorieSet = new LinkedHashSet<>(0);

    // bi-directional many-to-one association to BetrokkenheidOuderlijkGezagHistorie
    @OneToMany(mappedBy = "betrokkenheid", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE }, orphanRemoval = true)
    private Set<BetrokkenheidOuderlijkGezagHistorie> betrokkenheidOuderlijkGezagHistorieSet = new LinkedHashSet<>(0);

    // bi-directional many-to-one association to BetrokkenheidHistorie
    @OneToMany(mappedBy = "betrokkenheid", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE }, orphanRemoval = true)
    private Set<BetrokkenheidHistorie> betrokkenheidHistorieSet = new LinkedHashSet<>(0);

    // bi-directional one-to-one association met Lo3AanduidingOuder
    @OneToOne(mappedBy = "ouder", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE }, orphanRemoval = true)
    private Lo3AanduidingOuder aanduidingOuder;

    /**
     * JPA default constructor.
     */
    protected Betrokkenheid() {
    }

    /**
     * Maak een nieuwe betrokkenheid.
     *
     * @param soortBetrokkenheid
     *            soort betrokkenheid
     * @param relatie
     *            relatie
     */
    public Betrokkenheid(final SoortBetrokkenheid soortBetrokkenheid, final Relatie relatie) {
        setSoortBetrokkenheid(soortBetrokkenheid);
        setRelatie(relatie);
    }

    /**
     * Geef de Id waarde voor de entiteit. Intern is de Id een Long voor integratie met GegevenInOnderzoek. De waarde
     * wordt geconverteerd naar een Integer.
     *
     * @return de Id waarde.
     */
    public Integer getId() {
        return convertLongNaarInteger(id);
    }

    /**
     * Zet de Id waarde voor de entiteit. Intern wordt de Id waarde geconverteert naar een Long voor integratie met
     * GegevenInOnderzoek.
     *
     * @param id
     *            de Id waarde.
     */
    public void setId(final Integer id) {
        this.id = convertIntegerNaarLong(id);
    }

    /**
     * Geef de waarde van indicatie ouder.
     *
     * @return indicatie ouder
     */
    public Boolean getIndicatieOuder() {
        return indicatieOuder;
    }

    /**
     * Zet de waarde van indicatie ouder.
     *
     * @param indicatieOuder
     *            indicatie ouder
     */
    public void setIndicatieOuder(final Boolean indicatieOuder) {
        this.indicatieOuder = indicatieOuder;
    }

    /**
     * Geef de waarde van indicatie adresgevende ouder.
     *
     * @return indicatie adresgevende ouder
     */
    public Boolean getIndicatieOuderUitWieKindIsGeboren() {
        return indicatieOuderUitWieKindIsGeboren;
    }

    /**
     * Zet de waarde van indicatie adresgevende ouder.
     *
     * @param indicatieOuderUitWieKindIsGeboren
     *            indicatie adresgevende ouder
     */
    public void setIndicatieOuderUitWieKindIsGeboren(final Boolean indicatieOuderUitWieKindIsGeboren) {
        this.indicatieOuderUitWieKindIsGeboren = indicatieOuderUitWieKindIsGeboren;
    }

    /**
     * Geef de waarde van indicatie ouder heeft gezag.
     *
     * @return indicatie ouder heeft gezag
     */
    public Boolean getIndicatieOuderHeeftGezag() {
        return indicatieOuderHeeftGezag;
    }

    /**
     * Zet de waarde van indicatie ouder heeft gezag.
     *
     * @param indicatieOuderHeeftGezag
     *            indicatie ouder heeft gezag
     */
    public void setIndicatieOuderHeeftGezag(final Boolean indicatieOuderHeeftGezag) {
        this.indicatieOuderHeeftGezag = indicatieOuderHeeftGezag;
    }

    /**
     * Geef de waarde van persoon.
     *
     * @return persoon
     */
    public Persoon getPersoon() {
        return persoon;
    }

    /**
     * Zet de waarde van persoon.
     *
     * @param persoon
     *            persoon
     */
    public void setPersoon(final Persoon persoon) {
        this.persoon = persoon;
    }

    /**
     * Geef de waarde van relatie.
     *
     * @return relatie
     */
    public Relatie getRelatie() {
        return relatie;
    }

    /**
     * Zet de waarde van relatie.
     *
     * @param relatie
     *            relatie
     */
    public void setRelatie(final Relatie relatie) {
        ValidationUtils.controleerOpNullWaarden("relatie mag niet null zijn", relatie);
        this.relatie = relatie;
    }

    /**
     * Geef de waarde van soort betrokkenheid.
     *
     * @return soort betrokkenheid
     */
    public SoortBetrokkenheid getSoortBetrokkenheid() {
        return SoortBetrokkenheid.parseId(soortBetrokkenheidId);
    }

    /**
     * Zet de waarde van soort betrokkenheid.
     *
     * @param soortBetrokkenheid
     *            soort betrokkenheid
     */
    public void setSoortBetrokkenheid(final SoortBetrokkenheid soortBetrokkenheid) {
        ValidationUtils.controleerOpNullWaarden("soortBetrokkenheid mag niet null zijn", soortBetrokkenheid);
        soortBetrokkenheidId = soortBetrokkenheid.getId();
    }

    /**
     * Toevoegen van een betrokkenheid ouder historie.
     *
     * @param betrokkenheidOuderHistorie
     *            betrokkenheid ouder historie
     */
    public void addBetrokkenheidOuderHistorie(final BetrokkenheidOuderHistorie betrokkenheidOuderHistorie) {
        betrokkenheidOuderHistorie.setBetrokkenheid(this);
        betrokkenheidOuderHistorieSet.add(betrokkenheidOuderHistorie);
    }

    /**
     * Verwijderen van een betrokkenheid ouder historie.
     *
     * @param betrokkenheidOuderHistorie
     *            betrokkenheid ouder historie
     * @return true, if successful
     */
    public boolean removeBetrokkenheidOuderHistorie(final BetrokkenheidOuderHistorie betrokkenheidOuderHistorie) {
        return betrokkenheidOuderHistorieSet.remove(betrokkenheidOuderHistorie);
    }

    /**
     * Geef de waarde van betrokkenheid ouder historie set.
     *
     * @return betrokkenheid ouder historie set
     */
    public Set<BetrokkenheidOuderHistorie> getBetrokkenheidOuderHistorieSet() {
        return betrokkenheidOuderHistorieSet;
    }

    /**
     * Geef de waarde van betrokkenheid ouderlijk gezag historie set.
     *
     * @return betrokkenheid ouderlijk gezag historie set
     */
    public Set<BetrokkenheidOuderlijkGezagHistorie> getBetrokkenheidOuderlijkGezagHistorieSet() {
        return betrokkenheidOuderlijkGezagHistorieSet;
    }

    /**
     * Toevoegen van een betrokkenheid ouderlijk gezag historie.
     *
     * @param betrokkenheidOuderlijkGezagHistorie
     *            betrokkenheid ouderlijk gezag historie
     */
    public void addBetrokkenheidOuderlijkGezagHistorie(final BetrokkenheidOuderlijkGezagHistorie betrokkenheidOuderlijkGezagHistorie) {
        betrokkenheidOuderlijkGezagHistorie.setBetrokkenheid(this);
        betrokkenheidOuderlijkGezagHistorieSet.add(betrokkenheidOuderlijkGezagHistorie);
    }

    /**
     * Verwijderen van een betrokkenheid ouderlijk gezag historie.
     *
     * @param betrokkenheidOuderlijkGezagHistorie
     *            betrokkenheid ouderlijk gezag historie
     * @return true, if successful
     */
    public boolean removeBetrokkenheidOuderlijkGezagHistorie(final BetrokkenheidOuderlijkGezagHistorie betrokkenheidOuderlijkGezagHistorie) {
        return betrokkenheidOuderlijkGezagHistorieSet.remove(betrokkenheidOuderlijkGezagHistorie);
    }

    /**
     * Geef de waarde van betrokkenheid historie set.
     *
     * @return betrokkenheid ouderlijk gezag historie set
     */
    public Set<BetrokkenheidHistorie> getBetrokkenheidHistorieSet() {
        return betrokkenheidHistorieSet;
    }

    /**
     * Toevoegen van een betrokkenheid historie.
     *
     * @param betrokkenheidHistorie
     *            betrokkenheid historie
     */
    public void addBetrokkenheidHistorie(final BetrokkenheidHistorie betrokkenheidHistorie) {
        betrokkenheidHistorie.setBetrokkenheid(this);
        betrokkenheidHistorieSet.add(betrokkenheidHistorie);
    }

    /**
     * aanduiding ouder (ouder 1 of ouder 2).
     * 
     * @return Lo3AanduidingOuder
     */
    public Lo3AanduidingOuder getAanduidingOuder() {
        return aanduidingOuder;
    }

    /**
     * zetten van aanduiding ouder.
     * 
     * @param aanduidingOuder
     *            Aanduiding ouder
     */
    public void setAanduidingOuder(final Lo3AanduidingOuder aanduidingOuder) {
        this.aanduidingOuder = aanduidingOuder;
    }

    @Override
    public Map<String, Collection<? extends FormeleHistorie>> verzamelHistorie() {
        final Map<String, Collection<? extends FormeleHistorie>> result = new HashMap<>();

        result.put("betrokkenheidHistorieSet", Collections.unmodifiableSet(betrokkenheidHistorieSet));
        result.put("betrokkenheidOuderHistorieSet", Collections.unmodifiableSet(betrokkenheidOuderHistorieSet));
        result.put("betrokkenheidOuderlijkGezagHistorieSet", Collections.unmodifiableSet(betrokkenheidOuderlijkGezagHistorieSet));

        if (persoon != null && !SoortPersoon.INGESCHREVENE.equals(persoon.getSoortPersoon())) {
            result.putAll(persoon.verzamelHistorie());
        }
        return result;
    }
}

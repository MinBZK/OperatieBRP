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
import javax.persistence.UniqueConstraint;
import nl.bzk.algemeenbrp.dal.domein.brp.annotation.IndicatieActueelEnGeldig;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;

/**
 * The persistent class for the persblpersnr database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "persblpersnr", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"pers", "autvanafgifte", "nr"}))
public class PersoonBuitenlandsPersoonsnummer extends AbstractEntiteit implements SubRootEntiteit, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "persblpersnr_id_generator", sequenceName = "kern.seq_persblpersnr", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "persblpersnr_id_generator")
    @Column(nullable = false)
    private Long id;

    // bi-directional many-to-one association to Persoon
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "pers", nullable = false)
    private Persoon persoon;

    // uni-directionele many-to-one assocation to AutoriteitAfgifteBuitenlandsPersoonsnummer
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autvanafgifte", nullable = false)
    private AutoriteitAfgifteBuitenlandsPersoonsnummer autoriteitAfgifteBuitenlandsPersoonsnummer;

    @Column(name = "nr", length = 40, nullable = false)
    private String nummer;

    @Column(name = "indag", nullable = false)
    private boolean isActueelEnGeldig;

    // bi-directional many-to-one association to PersoonNationaliteitHistorie
    @IndicatieActueelEnGeldig(naam = "isActueelEnGeldig")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "persoonBuitenlandsPersoonsnummer", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private final Set<PersoonBuitenlandsPersoonsnummerHistorie> persoonBuitenlandsPersoonsnummerHistorieSet = new LinkedHashSet<>(0);

    /**
     * JPA default constructor.
     */
    protected PersoonBuitenlandsPersoonsnummer() {}

    /**
     * Maak een nieuw PersoonBuitenlandsPersoonsnummer object.
     *
     * @param persoon persoon
     * @param autoriteitAfgifteBuitenlandsPersoonsnummer autoriteit afgifte buitenlands
     *        persoonsnummer
     * @param nummer buitenlands persoonsnummer
     */
    public PersoonBuitenlandsPersoonsnummer(final Persoon persoon, final AutoriteitAfgifteBuitenlandsPersoonsnummer autoriteitAfgifteBuitenlandsPersoonsnummer,
            final String nummer) {
        setPersoon(persoon);
        setAutoriteitAfgifteBuitenlandsPersoonsnummer(autoriteitAfgifteBuitenlandsPersoonsnummer);
        setNummer(nummer);
    }

    @Override
    public Long getId() {
        return id;
    }

    /**
     * Zet de waarden voor id van PersoonBuitenlandsPersoonsnummer.
     *
     * @param id de nieuwe waarde voor id van PersoonBuitenlandsPersoonsnummer
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van persoonBuitenlandsPersoonsnummerHistorieSet.
     *
     * @return de waarde van persoonBuitenlandsPersoonsnummerHistorieSet
     */
    public Set<PersoonBuitenlandsPersoonsnummerHistorie> getPersoonBuitenlandsPersoonsnummerHistorieSet() {
        return persoonBuitenlandsPersoonsnummerHistorieSet;
    }

    /**
     * Toevoegen van een persoon nationaliteit historie.
     *
     * @param persoonBuitenlandsPersoonsnummerHistorie persoon buitenlands persoonsnummer historie
     */
    public void addPersoonBuitenlandsPersoonsnummerHistorie(final PersoonBuitenlandsPersoonsnummerHistorie persoonBuitenlandsPersoonsnummerHistorie) {
        persoonBuitenlandsPersoonsnummerHistorie.setPersoonBuitenlandsPersoonsnummer(this);
        persoonBuitenlandsPersoonsnummerHistorieSet.add(persoonBuitenlandsPersoonsnummerHistorie);
    }

    @Override
    public Persoon getPersoon() {
        return persoon;
    }

    /**
     * Zet de waarden voor persoon van PersoonNationaliteit.
     *
     * @param persoon de nieuwe waarde voor persoon van PersoonNationaliteit
     */
    public void setPersoon(final Persoon persoon) {
        ValidationUtils.controleerOpNullWaarden("persoon mag niet null zijn", persoon);
        this.persoon = persoon;
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
     * Geeft de autoriteit afgifte buitenlands persoonsnummer terug.
     *
     * @return de {@link AutoriteitAfgifteBuitenlandsPersoonsnummer}
     */
    public AutoriteitAfgifteBuitenlandsPersoonsnummer getAutoriteitAfgifteBuitenlandsPersoonsnummer() {
        return autoriteitAfgifteBuitenlandsPersoonsnummer;
    }

    /**
     * Zet de autoriteit afgifte buitenlands persoonsnummer.
     *
     * @param autoriteitAfgifteBuitenlandsPersoonsnummer de
     *        {@link AutoriteitAfgifteBuitenlandsPersoonsnummer}
     */
    public void setAutoriteitAfgifteBuitenlandsPersoonsnummer(final AutoriteitAfgifteBuitenlandsPersoonsnummer autoriteitAfgifteBuitenlandsPersoonsnummer) {
        ValidationUtils.controleerOpNullWaarden("Autoriteit van afgifte mag niet null zijn", autoriteitAfgifteBuitenlandsPersoonsnummer);
        this.autoriteitAfgifteBuitenlandsPersoonsnummer = autoriteitAfgifteBuitenlandsPersoonsnummer;
    }

    /**
     * Geeft het buitenlands persoonsnummer terug.
     *
     * @return het buitenlands persoonsnummer
     */
    public String getNummer() {
        return nummer;
    }

    /**
     * Zet het buitenlands persoonsnummer.
     *
     * @param nummer het buitenlands persoonsnummer
     */
    public void setNummer(final String nummer) {
        ValidationUtils.controleerOpNullWaarden("Nummer mag niet null zijn", nummer);
        this.nummer = nummer;
    }

    @Override
    public Map<String, Collection<FormeleHistorie>> verzamelHistorie() {
        final Map<String, Collection<FormeleHistorie>> result = new HashMap<>();
        result.put("persoonBuitenlandsPersoonsnummerHistorieSet", Collections.unmodifiableSet(persoonBuitenlandsPersoonsnummerHistorieSet));
        return result;
    }
}

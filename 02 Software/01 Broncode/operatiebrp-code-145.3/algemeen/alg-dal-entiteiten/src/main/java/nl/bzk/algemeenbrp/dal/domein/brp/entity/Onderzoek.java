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
import javax.persistence.Transient;
import nl.bzk.algemeenbrp.dal.domein.brp.annotation.IndicatieActueelEnGeldig;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.StatusOnderzoek;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;

/**
 * The persistent class for the onderzoek database table.
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "onderzoek", schema = "kern")
public class Onderzoek extends AbstractEntiteit implements Afleidbaar, RootEntiteit, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "onderzoek_id_generator", sequenceName = "kern.seq_onderzoek", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "onderzoek_id_generator")
    @Column(nullable = false)
    /*
     * Het veld zou Integer moeten zijn maar is Long i.v.m. de link naar GegevenInOnderzoek.
     */
    private Long id;

    @Column(name = "dataanv")
    private Integer datumAanvang;

    @Column(name = "dateinde")
    private Integer datumEinde;

    @Column(name = "oms", length = 2_147_483_647)
    private String omschrijving;

    @Column(name = "status")
    private Integer statusOnderzoekId;

    @Column(name = "indag")
    private boolean isActueelEnGeldig;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "partij")
    private Partij partij;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "pers")
    private Persoon persoon;

    @Transient
    private boolean voortgekomenUitNietActueelVoorkomen;

    // bi-directional many-to-one association to OnderzoekHistorie
    @IndicatieActueelEnGeldig(naam = "isActueelEnGeldig")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "onderzoek", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private final Set<OnderzoekHistorie> onderzoekHistorieSet = new LinkedHashSet<>(0);

    /**
     * Onderzoeken moeten EAGER worden geladen ivm uni-directionele koppeling van
     * {@link GegevenInOnderzoek} naar {@link Entiteit} door {@link GegevenInOnderzoekListener}. Dit
     * wordt gedaan ivm @Any in de {@link GegevenInOnderzoek}.
     */
    // bi-directional one-to-many association to GegevenInOnderzoek
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "onderzoek", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private final Set<GegevenInOnderzoek> gegevenInOnderzoekSet = new LinkedHashSet<>(0);

    /**
     * JPA Default constructor.
     */
    protected Onderzoek() {
    }

    /**
     * Maakt een onderzoek aan op een {@link Persoon} voor een bepaalde {@link Partij}.
     * @param partij de partij
     * @param persoon de persoon
     */
    public Onderzoek(final Partij partij, final Persoon persoon) {
        setPersoon(persoon);
        setPartij(partij);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.algemeen.dal.domein.brp.kern.entity.DeltaEntiteit#getId()
     */
    @Override
    public Integer getId() {
        return convertLongNaarInteger(id);
    }

    /**
     * Zet de waarden voor id van Onderzoek.
     * @param id de nieuwe waarde voor id van Onderzoek
     */
    public void setId(final Integer id) {
        this.id = convertIntegerNaarLong(id);
    }

    /**
     * Geef de waarde van datum aanvang van Onderzoek.
     * @return de waarde van datum aanvang van Onderzoek
     */
    public Integer getDatumAanvang() {
        return datumAanvang;
    }

    /**
     * Zet de waarden voor datum aanvang van Onderzoek.
     * @param datumAanvang de nieuwe waarde voor datum aanvang van Onderzoek
     */
    public void setDatumAanvang(final Integer datumAanvang) {
        this.datumAanvang = datumAanvang;
    }

    /**
     * Geef de waarde van datum einde van Onderzoek.
     * @return de waarde van datum einde van Onderzoek
     */
    public Integer getDatumEinde() {
        return datumEinde;
    }

    /**
     * Zet de waarden voor datum einde van Onderzoek.
     * @param datumEinde de nieuwe waarde voor datum einde van Onderzoek
     */
    public void setDatumEinde(final Integer datumEinde) {
        this.datumEinde = datumEinde;
    }

    /**
     * Geef de waarde van omschrijving van Onderzoek.
     * @return de waarde van omschrijving van Onderzoek
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    /**
     * Zet de waarden voor omschrijving van Onderzoek.
     * @param omschrijving de nieuwe waarde voor omschrijving van Onderzoek
     */
    public void setOmschrijving(final String omschrijving) {
        this.omschrijving = omschrijving;
    }

    /**
     * Geef de waarde van status onderzoek van Onderzoek.
     * @return de waarde van status onderzoek van Onderzoek
     */
    public StatusOnderzoek getStatusOnderzoek() {
        return StatusOnderzoek.parseId(statusOnderzoekId);
    }

    /**
     * Zet de waarden voor status onderzoek van Onderzoek.
     * @param statusOnderzoek de nieuwe waarde voor status onderzoek van Onderzoek
     */
    public void setStatusOnderzoek(final StatusOnderzoek statusOnderzoek) {
        statusOnderzoekId = statusOnderzoek.getId();
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

    /**
     * Geef de waarde van onderzoek historie set van Onderzoek.
     * @return de waarde van onderzoek historie set van Onderzoek
     */
    public Set<OnderzoekHistorie> getOnderzoekHistorieSet() {
        return onderzoekHistorieSet;
    }

    /**
     * Toevoegen van een onderzoek historie.
     * @param onderzoekHistorie onderzoek historie
     */
    public void addOnderzoekHistorie(final OnderzoekHistorie onderzoekHistorie) {
        onderzoekHistorie.setOnderzoek(this);
        onderzoekHistorieSet.add(onderzoekHistorie);
    }

    /**
     * Geef de waarde van partij van Onderzoek.
     * @return de waarde van partij van Onderzoek
     */
    public Partij getPartij() {
        return partij;
    }

    /**
     * Zet de partij van het onderzoek.
     * @param partij partij
     */
    public void setPartij(final Partij partij) {
        ValidationUtils.controleerOpNullWaarden("partij mag niet null zijn", partij);
        this.partij = partij;
    }

    /**
     * Geef de persoon van Onderzoek.
     * @return de persoon van Onderzoek
     */
    public Persoon getPersoon() {
        return persoon;
    }

    /**
     * Zet de persoon van het onderzoek.
     * @param persoon persoon
     */
    public void setPersoon(final Persoon persoon) {
        ValidationUtils.controleerOpNullWaarden("persoon mag niet null zijn", persoon);
        this.persoon = persoon;
    }

    /**
     * Geef de waarde van gegeven in onderzoek set van Onderzoek.
     * @return de waarde van gegeven in onderzoek set van Onderzoek
     */
    public Set<GegevenInOnderzoek> getGegevenInOnderzoekSet() {
        return gegevenInOnderzoekSet;
    }

    /**
     * Toevoegen van een gegeven in onderzoek.
     * @param gegevenInOnderzoek gegeven in onderzoek
     */
    public void addGegevenInOnderzoek(final GegevenInOnderzoek gegevenInOnderzoek) {
        gegevenInOnderzoek.setOnderzoek(this);
        gegevenInOnderzoekSet.add(gegevenInOnderzoek);
    }

    /**
     * Checks if is voortgekomen uit niet actueel voorkomen.
     * @return true, if is voortgekomen uit niet actueel voorkomen
     */
    public final boolean isVoortgekomenUitNietActueelVoorkomen() {
        return voortgekomenUitNietActueelVoorkomen;
    }

    /**
     * Zet de waarden voor voortgekomen uit niet actueel voorkomen van Onderzoek.
     * @param waarde de nieuwe waarde voor voortgekomen uit niet actueel voorkomen van Onderzoek
     */
    public final void setVoortgekomenUitNietActueelVoorkomen(final boolean waarde) {
        voortgekomenUitNietActueelVoorkomen = waarde;
    }

    @Override
    public Map<String, Collection<FormeleHistorie>> verzamelHistorie() {
        final Map<String, Collection<FormeleHistorie>> result = new HashMap<>();
        result.put("onderzoekHistorieSet", Collections.unmodifiableSet(onderzoekHistorieSet));
        return result;
    }

    /**
     * Deze methode kopieert een eventueel gegeven in onderzoek op het oude voorkomen of entiteit zodat dit onderzoek ook geldt voor het nieuwe gegeven. Het
     * oude gegeven moet wel een database id hebben omdat dit nodig is ter identificatie van het gegeven in onderzoek. Als het oude gegeven niet in
     * onderzoek staat doet deze methode niets.
     * @param oudGegevenInOnderzoek het oude voorkomen of entiteit dat in onderzoek staat
     * @param nieuwGegevenInOnderzoek het nieuwe voorkomen of entiteit dat in onderzoek staat
     * @param actie de verantwoording voor het nieuwe gegeven in onderzoek
     */
    public void kopieerGegevenInOnderzoekVoorNieuwGegeven(final Entiteit oudGegevenInOnderzoek,
                                                          final Entiteit nieuwGegevenInOnderzoek,
                                                          final BRPActie actie) {
        ValidationUtils.controleerOpNullWaarden("oudGegevenInOnderzoek en nieuwGegevenInOnderzoek mogen niet null zijn", oudGegevenInOnderzoek,
                nieuwGegevenInOnderzoek);
        if (oudGegevenInOnderzoek.getId() == null) {
            throw new IllegalArgumentException("Het oude voorkomen moet een database ID hebben");
        }
        final GegevenInOnderzoek gegevenInOnderzoek = zoekGegevenInOnderzoekVoorEntiteitOfVoorkomen(oudGegevenInOnderzoek);
        if (gegevenInOnderzoek != null) {
            final GegevenInOnderzoek kopieGegevenInOnderzoek = new GegevenInOnderzoek(this, gegevenInOnderzoek.getSoortGegeven());
            kopieGegevenInOnderzoek.setEntiteitOfVoorkomen(nieuwGegevenInOnderzoek);
            final GegevenInOnderzoekHistorie gegevenInOnderzoekHistorie = new GegevenInOnderzoekHistorie(kopieGegevenInOnderzoek, actie);
            kopieGegevenInOnderzoek.addGegevenInOnderzoekHistorie(gegevenInOnderzoekHistorie);
            addGegevenInOnderzoek(kopieGegevenInOnderzoek);
        }
    }

    private GegevenInOnderzoek zoekGegevenInOnderzoekVoorEntiteitOfVoorkomen(final Entiteit entiteit) {
        for (final GegevenInOnderzoek gegevenInOnderzoek : gegevenInOnderzoekSet) {
            if (gegevenInOnderzoek.getEntiteitOfVoorkomen().getClass().equals(entiteit.getClass())
                    && gegevenInOnderzoek.getEntiteitOfVoorkomen().getId() != null
                    && entiteit.getId().longValue() == gegevenInOnderzoek.getEntiteitOfVoorkomen().getId().longValue()) {
                return gegevenInOnderzoek;
            }
        }
        return null;
    }

    /**
     * Deze methode kopieert een eventueel gegeven in onderzoek op het oude voorkomen of entiteit zodat dit onderzoek ook geldt voor het nieuwe gegeven. Het
     * oude gegeven moet wel een database id hebben omdat dit nodig is ter identificatie van het gegeven in onderzoek. Als het oude gegeven niet in
     * onderzoek staat doet deze methode niets.
     * @param onderzoeken de onderzoeken die gekopieerd moeten worden
     * @param oudGegevenInOnderzoek het oude voorkomen of entiteit dat in onderzoek staat
     * @param nieuwGegevenInOnderzoek het nieuwe voorkomen of entiteit dat in onderzoek staat
     * @param actie de verantwoording voor het nieuwe gegeven in onderzoek
     */
    public static void kopieerGegevenInOnderzoekVoorNieuwGegeven(final Collection<Onderzoek> onderzoeken, final Entiteit oudGegevenInOnderzoek,
                                                                 final Entiteit nieuwGegevenInOnderzoek,
                                                                 final BRPActie actie) {
        for (final Onderzoek onderzoek : onderzoeken) {
            onderzoek.kopieerGegevenInOnderzoekVoorNieuwGegeven(oudGegevenInOnderzoek, nieuwGegevenInOnderzoek, actie);
        }
    }
}

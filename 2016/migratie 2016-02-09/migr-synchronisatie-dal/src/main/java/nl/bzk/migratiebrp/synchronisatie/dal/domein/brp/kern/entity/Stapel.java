/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.Validatie;
import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.util.PersistenceUtil;

/**
 * The persistent class for the stapel database table.
 * 
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "stapel", schema = "ist", uniqueConstraints = @UniqueConstraint(columnNames = {"pers", "categorie", "volgnr" }))
@SuppressWarnings("checkstyle:designforextension")
public class Stapel extends AbstractDeltaEntiteit implements Serializable, DeltaRootEntiteit {
    /** Veldnaam van relaties tbv verschil verwerking. */
    public static final String RELATIES = "relaties";
    /** Veldnaam van volgnummer tbv verschil verwerking. */
    public static final String VOLGNUMMER = "volgnummer";
    /** Veldnaam van voorkomens tbv verschil verwerking. */
    public static final String STAPEL_VOORKOMENS = "stapelVoorkomens";
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "stapel_id_generator", sequenceName = "ist.seq_stapel", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stapel_id_generator")
    @Column(unique = true, nullable = false)
    private Integer id;

    @Column(nullable = false, length = 2)
    private String categorie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pers", nullable = false)
    private Persoon persoon;

    @Column(name = "volgnr", nullable = false)
    private int volgnummer;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "stapels", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
    private Set<Relatie> relaties = new LinkedHashSet<>(0);

    // bi-directional many-to-one association to StapelVoorkomen
    @OneToMany(mappedBy = "stapel", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE }, orphanRemoval = true)
    private Set<StapelVoorkomen> stapelVoorkomens = new LinkedHashSet<>(0);

    /**
     * JPA default constructor.
     */
    protected Stapel() {
    }

    /**
     * Maakt een Stapel object.
     * 
     * @param persoon
     *            de persoon waarvoor deze gegevens zijn vastgelegd, mag niet null zijn
     * @param categorie
     *            de LO3 categorie (alleen actueel) van de gegevens, mag niet null zijn
     * @param volgnummer
     *            het volgnummer van de stapel, beginnend bij 0
     */
    public Stapel(final Persoon persoon, final String categorie, final int volgnummer) {
        setPersoon(persoon);
        setCategorie(categorie);
        setVolgnummer(volgnummer);
    }

    /**
     * Geef de waarde van id.
     *
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Zet de waarde van id.
     *
     * @param id
     *            id
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Geef de waarde van categorie.
     *
     * @return categorie
     */
    public String getCategorie() {
        return categorie;
    }

    /**
     * Zet de waarde van categorie.
     *
     * @param categorie
     *            categorie
     */
    void setCategorie(final String categorie) {
        ValidationUtils.controleerOpNullWaarden("categorie mag niet null zijn", categorie);
        Validatie.controleerOpLegeWaarden("categorie mag geen lege string zijn", categorie);
        this.categorie = categorie;
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
        ValidationUtils.controleerOpNullWaarden("persoon mag niet null zijn", persoon);
        this.persoon = persoon;
    }

    /**
     * Geef de waarde van volgnummer.
     *
     * @return volgnummer
     */
    public int getVolgnummer() {
        return volgnummer;
    }

    /**
     * Zet de waarde van volgnummer.
     *
     * @param volgnummer
     *            volgnummer
     */
    void setVolgnummer(final int volgnummer) {
        this.volgnummer = volgnummer;
    }

    /**
     * Geef de waarde van relaties.
     *
     * @return de lijst met relaties voor deze ist stapel
     */
    public Set<Relatie> getRelaties() {
        return new LinkedHashSet<>(relaties);
    }

    /**
     * Bevat relatie.
     *
     * @param relatie
     *            de relatie
     * @return true als deze stapel hoort bij de meegegeven stapel, anders false
     */
    public boolean bevatRelatie(final Relatie relatie) {
        boolean result = false;
        for (final Relatie mijnRelatie : relaties) {
            if (PersistenceUtil.getPojoFromObject(relatie).equals(PersistenceUtil.getPojoFromObject(mijnRelatie))) {
                result = true;
                break;
            }
        }

        return result;
    }

    /**
     * Maakt een bidirectionele koppeling tussen de meegegeven relatie en deze stapel.
     * 
     * @param relatie
     *            de relatie die moet worden toegevoegd aan deze stapel
     */
    public void addRelatie(final Relatie relatie) {
        if (!bevatRelatie(relatie)) {
            relaties.add(relatie);

            if (!relatie.bevatStapel(this)) {
                relatie.addStapel(this);
            }
        }
    }

    /**
     * Verwijder de gegeven relatie van deze stapel.
     *
     * @param relatie
     *            de te verwijderen relatie
     */
    public void removeRelatie(final Relatie relatie) {
        for (final Iterator<Relatie> iterator = relaties.iterator(); iterator.hasNext();) {
            final Relatie mijnRelatie = iterator.next();
            if (PersistenceUtil.getPojoFromObject(relatie).equals(PersistenceUtil.getPojoFromObject(mijnRelatie))) {
                iterator.remove();
                relatie.removeStapel(this);
                break;
            }
        }
    }

    /**
     * Geef de waarde van stapelvoorkomens.
     *
     * @return stapelvoorkomens
     */
    public Set<StapelVoorkomen> getStapelvoorkomens() {
        return stapelVoorkomens;
    }

    /**
     * Toevoegen van een stapel voorkomen.
     *
     * @param stapelVoorkomen
     *            stapel voorkomen
     */
    public void addStapelVoorkomen(final StapelVoorkomen stapelVoorkomen) {
        stapelVoorkomen.setStapel(this);
        stapelVoorkomens.add(stapelVoorkomen);
    }

    @Override
    public Map<String, Collection<? extends FormeleHistorie>> verzamelHistorie() {
        final Map<String, Collection<? extends FormeleHistorie>> histories = new HashMap<>();
        for (final Relatie relatie : relaties) {
            histories.putAll(relatie.verzamelHistorie());
        }
        return histories;
    }
}

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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.Validatie;
import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.util.PersistenceUtil;

/**
 * The persistent class for the relatie database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "relatie", schema = "kern")
@SuppressWarnings("checkstyle:designforextension")
public class Relatie extends AbstractDeltaEntiteit implements DeltaRootEntiteit, Serializable {
    /** Veldnaam van betrokkenheidset tbv verschil verwerking. */
    public static final String BETROKKENHEID_SET = "betrokkenheidSet";
    /** Veldnaam van datumAanvang tbv verschil verwerking. */
    public static final String DATUM_AANVANG = "datumAanvang";
    /** Veldnaam van srt tbv verschil verwerking. */
    public static final String RELATIE_SOORT = "srt";

    private static final String RELATIE_ATTRIBUUT = "relatie";
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "relatie_id_generator", sequenceName = "kern.seq_relatie", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "relatie_id_generator")
    @Column(nullable = false)
    /**
     * Het veld zou Integer moeten zijn maar is Long i.v.m. de link naar GegevenInOnderzoek.
     */
    private Long id;

    @Column(name = "blplaatsaanv", length = 40)
    private String buitenlandsePlaatsAanvang;

    @Column(name = "blplaatseinde", length = 40)
    private String buitenlandsePlaatsEinde;

    @Column(name = "blregioaanv", length = 35)
    private String buitenlandseRegioAanvang;

    @Column(name = "blregioeinde", length = 35)
    private String buitenlandseRegioEinde;

    @Column(name = "dataanv")
    private Integer datumAanvang;

    @Column(name = "dateinde")
    private Integer datumEinde;

    @Column(name = "omslocaanv", length = 40)
    private String omschrijvingLocatieAanvang;

    @Column(name = "omsloceinde", length = 40)
    private String omschrijvingLocatieEinde;

    /* Bij wijziging veld-naam ook de string constante daarvoor aanpassen */
    // bi-directional many-to-one association to Betrokkenheid
    @OneToMany(fetch = FetchType.LAZY, mappedBy = RELATIE_ATTRIBUUT, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    private Set<Betrokkenheid> betrokkenheidSet = new LinkedHashSet<>(0);

    // bi-directional many-to-one association to RelatieHistorie
    @OneToMany(mappedBy = RELATIE_ATTRIBUUT, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE }, orphanRemoval = true)
    private Set<RelatieHistorie> relatieHistorieSet = new LinkedHashSet<>(0);

    // bi-directional many-to-one association to LandOfGebied
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "landgebiedeinde")
    private LandOfGebied landOfGebiedEinde;

    // bi-directional many-to-one association to LandOfGebied
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "landgebiedaanv")
    private LandOfGebied landOfGebiedAanvang;

    // bi-directional many-to-one association to Partij
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "gemeinde")
    private Gemeente gemeenteEinde;

    // bi-directional many-to-one association to Partij
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "gemaanv")
    private Gemeente gemeenteAanvang;

    @Column(name = "wplnaamaanv")
    private String woonplaatsnaamAanvang;

    @Column(name = "wplnaameinde")
    private String woonplaatsnaamEinde;

    // bi-directional many-to-one association to RedenBeeindigingRelatie
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "rdneinde")
    private RedenBeeindigingRelatie redenBeeindigingRelatie;

    @Column(name = "srt", nullable = false)
    private short soortRelatieId;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
    @JoinTable(name = "stapelrelatie", schema = "ist", joinColumns = @JoinColumn(name = "relatie", nullable = false), inverseJoinColumns = @JoinColumn(
            name = "stapel", nullable = false))
    private Set<Stapel> stapels = new LinkedHashSet<>(0);

    /**
     * JPA default constructor.
     */
    protected Relatie() {
    }

    /**
     * Maak een nieuwe relatie.
     *
     * @param soortRelatie
     *            soort relatie
     */
    public Relatie(final SoortRelatie soortRelatie) {
        setSoortRelatie(soortRelatie);
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
     * Geef de waarde van buitenlandse plaats aanvang.
     *
     * @return buitenlandse plaats aanvang
     */
    public String getBuitenlandsePlaatsAanvang() {
        return buitenlandsePlaatsAanvang;
    }

    /**
     * Zet de waarde van buitenlandse plaats aanvang.
     *
     * @param buitenlandsePlaatsAanvang
     *            buitenlandse plaats aanvang
     */
    public void setBuitenlandsePlaatsAanvang(final String buitenlandsePlaatsAanvang) {
        Validatie.controleerOpLegeWaarden("buitenlandsePlaatsAanvang mag geen lege string zijn", buitenlandsePlaatsAanvang);
        this.buitenlandsePlaatsAanvang = buitenlandsePlaatsAanvang;
    }

    /**
     * Geef de waarde van buitenlandse plaats einde.
     *
     * @return buitenlandse plaats einde
     */
    public String getBuitenlandsePlaatsEinde() {
        return buitenlandsePlaatsEinde;
    }

    /**
     * Zet de waarde van buitenlandse plaats einde.
     *
     * @param buitenlandsePlaatsEinde
     *            buitenlandse plaats einde
     */
    public void setBuitenlandsePlaatsEinde(final String buitenlandsePlaatsEinde) {
        Validatie.controleerOpLegeWaarden("buitenlandsePlaatsEinde mag geen lege string zijn", buitenlandsePlaatsEinde);
        this.buitenlandsePlaatsEinde = buitenlandsePlaatsEinde;
    }

    /**
     * Geef de waarde van buitenlandse regio aanvang.
     *
     * @return buitenlandse regio aanvang
     */
    public String getBuitenlandseRegioAanvang() {
        return buitenlandseRegioAanvang;
    }

    /**
     * Zet de waarde van buitenlandse regio aanvang.
     *
     * @param buitenlandseRegioAanvang
     *            buitenlandse regio aanvang
     */
    public void setBuitenlandseRegioAanvang(final String buitenlandseRegioAanvang) {
        Validatie.controleerOpLegeWaarden("buitenlandseRegioAanvang mag geen lege string zijn", buitenlandseRegioAanvang);
        this.buitenlandseRegioAanvang = buitenlandseRegioAanvang;
    }

    /**
     * Geef de waarde van buitenlandse regio einde.
     *
     * @return buitenlandse regio einde
     */
    public String getBuitenlandseRegioEinde() {
        return buitenlandseRegioEinde;
    }

    /**
     * Zet de waarde van buitenlandse regio einde.
     *
     * @param buitenlandseRegioEinde
     *            buitenlandse regio einde
     */
    public void setBuitenlandseRegioEinde(final String buitenlandseRegioEinde) {
        Validatie.controleerOpLegeWaarden("buitenlandseRegioEinde mag geen lege string zijn", buitenlandseRegioEinde);
        this.buitenlandseRegioEinde = buitenlandseRegioEinde;
    }

    /**
     * Geef de waarde van datum aanvang.
     *
     * @return datum aanvang
     */
    public Integer getDatumAanvang() {
        return datumAanvang;
    }

    /**
     * Zet de waarde van datum aanvang.
     *
     * @param datumAanvang
     *            datum aanvang
     */
    public void setDatumAanvang(final Integer datumAanvang) {
        this.datumAanvang = datumAanvang;
    }

    /**
     * Geef de waarde van datum einde.
     *
     * @return datum einde
     */
    public Integer getDatumEinde() {
        return datumEinde;
    }

    /**
     * Zet de waarde van datum einde.
     *
     * @param datumEinde
     *            datum einde
     */
    public void setDatumEinde(final Integer datumEinde) {
        this.datumEinde = datumEinde;
    }

    /**
     * Geef de waarde van omschrijving locatie aanvang.
     *
     * @return omschrijving locatie aanvang
     */
    public String getOmschrijvingLocatieAanvang() {
        return omschrijvingLocatieAanvang;
    }

    /**
     * Zet de waarde van omschrijving locatie aanvang.
     *
     * @param omschrijvingLocatieAanvang
     *            omschrijving locatie aanvang
     */
    public void setOmschrijvingLocatieAanvang(final String omschrijvingLocatieAanvang) {
        Validatie.controleerOpLegeWaarden("omschrijvingLocatieAanvang mag geen lege string zijn", omschrijvingLocatieAanvang);
        this.omschrijvingLocatieAanvang = omschrijvingLocatieAanvang;
    }

    /**
     * Geef de waarde van omschrijving locatie einde.
     *
     * @return omschrijving locatie einde
     */
    public String getOmschrijvingLocatieEinde() {
        return omschrijvingLocatieEinde;
    }

    /**
     * Zet de waarde van omschrijving locatie einde.
     *
     * @param omschrijvingLocatieEinde
     *            omschrijving locatie einde
     */
    public void setOmschrijvingLocatieEinde(final String omschrijvingLocatieEinde) {
        Validatie.controleerOpLegeWaarden("omschrijvingLocatieEinde mag geen lege string zijn", omschrijvingLocatieEinde);
        this.omschrijvingLocatieEinde = omschrijvingLocatieEinde;
    }

    /**
     * Geef de waarde van betrokkenheid set.
     *
     * @return betrokkenheid set
     */
    public Set<Betrokkenheid> getBetrokkenheidSet() {
        return betrokkenheidSet;
    }

    /**
     * Geef de waarde van betrokkenheid set.
     *
     * @param soortBetrokkenheid
     *            soort betrokkenheid
     * @return betrokkenheid set
     */
    public Set<Betrokkenheid> getBetrokkenheidSet(final SoortBetrokkenheid soortBetrokkenheid) {
        final Set<Betrokkenheid> result = new LinkedHashSet<>();
        for (final Betrokkenheid betrokkenheid : betrokkenheidSet) {
            if (soortBetrokkenheid.equals(betrokkenheid.getSoortBetrokkenheid())) {
                result.add(betrokkenheid);
            }
        }
        return result;
    }

    /**
     * Toevoegen van een betrokkenheid.
     *
     * @param betrokkenheid
     *            betrokkenheid
     */
    public void addBetrokkenheid(final Betrokkenheid betrokkenheid) {
        betrokkenheid.setRelatie(this);
        betrokkenheidSet.add(betrokkenheid);
    }

    /**
     * Verwijderen van een betrokkenheid.
     *
     * @param betrokkenheid
     *            betrokkenheid
     * @return true, if successful
     */
    public boolean removeBetrokkenheid(final Betrokkenheid betrokkenheid) {
        return betrokkenheidSet.remove(betrokkenheid);
    }

    /**
     * Geef de waarde van huwelijk geregistreerd partnerschap historie set.
     *
     * @return huwelijk geregistreerd partnerschap historie set
     */
    public Set<RelatieHistorie> getRelatieHistorieSet() {
        return relatieHistorieSet;
    }

    /**
     * Geef de waarde van actuele huwelijk geregistreerd partnerschap historie.
     *
     * @return de actuele (niet-vervallen) historische huwelijk of gp voorkomen van deze relatie, of null als deze niet
     *         bestaat
     */
    public RelatieHistorie getActueleRelatieHistorie() {
        return HistorieUtil.getActueelHistorieVoorkomen(relatieHistorieSet);
    }

    /**
     * Toevoegen van een huwelijk geregistreerd partnerschap historie.
     *
     * @param relatieHistorie
     *            huwelijk geregistreerd partnerschap historie
     */
    public void addRelatieHistorie(final RelatieHistorie relatieHistorie) {
        relatieHistorie.setRelatie(this);
        relatieHistorieSet.add(relatieHistorie);
    }

    /**
     * Geef de waarde van land of gebied einde.
     *
     * @return land of gebied einde
     */
    public LandOfGebied getLandOfGebiedEinde() {
        return landOfGebiedEinde;
    }

    /**
     * Zet de waarde van land of gebied einde.
     *
     * @param landOfGebiedEinde
     *            land of gebied einde
     */
    public void setLandOfGebiedEinde(final LandOfGebied landOfGebiedEinde) {
        this.landOfGebiedEinde = landOfGebiedEinde;
    }

    /**
     * Geef de waarde van land of gebied aanvang.
     *
     * @return land of gebied aanvang
     */
    public LandOfGebied getLandOfGebiedAanvang() {
        return landOfGebiedAanvang;
    }

    /**
     * Zet de waarde van land of gebied aanvang.
     *
     * @param landOfGebiedAanvang
     *            land of gebied aanvang
     */
    public void setLandOfGebiedAanvang(final LandOfGebied landOfGebiedAanvang) {
        this.landOfGebiedAanvang = landOfGebiedAanvang;
    }

    /**
     * Geef de waarde van gemeente einde.
     *
     * @return gemeente einde
     */
    public Gemeente getGemeenteEinde() {
        return gemeenteEinde;
    }

    /**
     * Zet de waarde van gemeente einde.
     *
     * @param gemeenteEinde
     *            gemeente einde
     */
    public void setGemeenteEinde(final Gemeente gemeenteEinde) {
        this.gemeenteEinde = gemeenteEinde;
    }

    /**
     * Geef de waarde van gemeente aanvang.
     *
     * @return gemeente aanvang
     */
    public Gemeente getGemeenteAanvang() {
        return gemeenteAanvang;
    }

    /**
     * Zet de waarde van gemeente aanvang.
     *
     * @param gemeenteAanvang
     *            gemeente aanvang
     */
    public void setGemeenteAanvang(final Gemeente gemeenteAanvang) {
        this.gemeenteAanvang = gemeenteAanvang;
    }

    /**
     * Geef de waarde van woonplaatsnaam aanvang.
     *
     * @return woonplaatsnaam aanvang
     */
    public String getWoonplaatsnaamAanvang() {
        return woonplaatsnaamAanvang;
    }

    /**
     * Zet de waarde van woonplaatsnaam aanvang.
     *
     * @param woonplaatsnaamAanvang
     *            woonplaatsnaam aanvang
     */
    public void setWoonplaatsnaamAanvang(final String woonplaatsnaamAanvang) {
        this.woonplaatsnaamAanvang = woonplaatsnaamAanvang;
    }

    /**
     * Geef de waarde van woonplaatsnaam einde.
     *
     * @return woonplaatsnaam einde
     */
    public String getWoonplaatsnaamEinde() {
        return woonplaatsnaamEinde;
    }

    /**
     * Zet de waarde van woonplaatsnaam einde.
     *
     * @param woonplaatsnaamEinde
     *            woonplaatsnaam einde
     */
    public void setWoonplaatsnaamEinde(final String woonplaatsnaamEinde) {
        this.woonplaatsnaamEinde = woonplaatsnaamEinde;
    }

    /**
     * Geef de waarde van reden beeindiging relatie.
     *
     * @return reden beeindiging relatie
     */
    public RedenBeeindigingRelatie getRedenBeeindigingRelatie() {
        return redenBeeindigingRelatie;
    }

    /**
     * Zet de waarde van reden beeindiging relatie.
     *
     * @param redenBeeindigingRelatie
     *            reden beeindiging relatie
     */
    public void setRedenBeeindigingRelatie(final RedenBeeindigingRelatie redenBeeindigingRelatie) {
        this.redenBeeindigingRelatie = redenBeeindigingRelatie;
    }

    /**
     * Geef de waarde van soort relatie.
     *
     * @return soort relatie
     */
    public SoortRelatie getSoortRelatie() {
        return SoortRelatie.parseId(soortRelatieId);
    }

    /**
     * Zet de waarde van soort relatie.
     *
     * @param soortRelatie
     *            soort relatie
     */
    public void setSoortRelatie(final SoortRelatie soortRelatie) {
        ValidationUtils.controleerOpNullWaarden("soortRelatie mag niet null zijn", soortRelatie);
        soortRelatieId = soortRelatie.getId();
    }

    /**
     * Geef de waarde van stapels.
     *
     * @return de ljst met stapels voor deze relatie
     */
    public Set<Stapel> getStapels() {
        return new LinkedHashSet<>(stapels);
    }

    /**
     * Bevat stapel.
     *
     * @param stapel
     *            de stapel
     * @return true als de meegegeven stapel hoort bij deze relatie, anders false
     */
    public boolean bevatStapel(final Stapel stapel) {
        boolean result = false;
        for (final Stapel mijnStapel : stapels) {
            if (PersistenceUtil.getPojoFromObject(stapel).equals(PersistenceUtil.getPojoFromObject(mijnStapel))) {
                result = true;
                break;
            }
        }

        return result;
    }

    /**
     * Maakt een bidirectionele koppeling tussen de meegegeven stapel en deze relatie.
     *
     * @param stapel
     *            de stapel
     */
    public void addStapel(final Stapel stapel) {
        if (!bevatStapel(stapel)) {
            stapels.add(stapel);

            if (!stapel.bevatRelatie(this)) {
                stapel.addRelatie(this);
            }
        }
    }

    /**
     * Verwijder de gegeven stapel van deze relatie.
     *
     * @param stapel
     *            de te verwijderen stapel
     */
    public void removeStapel(final Stapel stapel) {
        for (final Iterator<Stapel> iterator = stapels.iterator(); iterator.hasNext();) {
            final Stapel mijnStapel = iterator.next();
            if (PersistenceUtil.getPojoFromObject(stapel).equals(PersistenceUtil.getPojoFromObject(mijnStapel))) {
                iterator.remove();
                stapel.removeRelatie(this);
                break;
            }
        }
    }

    @Override
    public Map<String, Collection<? extends FormeleHistorie>> verzamelHistorie() {
        final Map<String, Collection<? extends FormeleHistorie>> result = new HashMap<>();

        result.put("relatieHistorieSet", Collections.unmodifiableSet(relatieHistorieSet));
        return result;
    }
}

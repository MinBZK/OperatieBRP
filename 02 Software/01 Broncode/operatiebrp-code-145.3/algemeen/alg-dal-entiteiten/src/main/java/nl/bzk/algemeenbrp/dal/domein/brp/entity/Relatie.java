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
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
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
import nl.bzk.algemeenbrp.dal.domein.brp.annotation.IndicatieActueelEnGeldig;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;

/**
 * The persistent class for the relatie database table.
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "relatie", schema = "kern")
public class Relatie extends AbstractEntiteit implements Afleidbaar, RootEntiteit, Serializable {
    /**
     * Veldnaam van betrokkenheidset tbv verschil verwerking.
     */
    public static final String BETROKKENHEID_SET = "betrokkenheidSet";
    /**
     * Veldnaam van datumAanvang tbv verschil verwerking.
     */
    public static final String DATUM_AANVANG = "datumAanvang";
    /**
     * Veldnaam van srt tbv verschil verwerking.
     */
    public static final String RELATIE_SOORT = "srt";

    private static final String RELATIE_ATTRIBUUT = "relatie";
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "relatie_id_generator", sequenceName = "kern.seq_relatie", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "relatie_id_generator")
    @Column(nullable = false)
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
    @OneToMany(fetch = FetchType.LAZY, mappedBy = RELATIE_ATTRIBUUT, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private final Set<Betrokkenheid> betrokkenheidSet = new LinkedHashSet<>(0);

    // bi-directional many-to-one association to RelatieHistorie
    @IndicatieActueelEnGeldig(naam = "isActueelEnGeldig")
    @OneToMany(mappedBy = RELATIE_ATTRIBUUT, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private final Set<RelatieHistorie> relatieHistorieSet = new LinkedHashSet<>(0);

    // bi-directional many-to-one association to LandOfGebied
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "landgebiedeinde")
    private LandOfGebied landOfGebiedEinde;

    // bi-directional many-to-one association to LandOfGebied
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "landgebiedaanv")
    private LandOfGebied landOfGebiedAanvang;

    // bi-directional many-to-one association to Partij
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gemeinde")
    private Gemeente gemeenteEinde;

    // bi-directional many-to-one association to Partij
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gemaanv")
    private Gemeente gemeenteAanvang;

    @Column(name = "wplnaamaanv")
    private String woonplaatsnaamAanvang;

    @Column(name = "wplnaameinde")
    private String woonplaatsnaamEinde;

    // bi-directional many-to-one association to RedenBeeindigingRelatie
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rdneinde")
    private RedenBeeindigingRelatie redenBeeindigingRelatie;

    @Column(name = "srt", nullable = false)
    private int soortRelatieId;

    @Column(name = "indag", nullable = false)
    private boolean isActueelEnGeldig;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinTable(name = "stapelrelatie", schema = "ist", joinColumns = @JoinColumn(name = "relatie", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "stapel", nullable = false))
    private final Set<Stapel> stapels = new LinkedHashSet<>(0);

    /**
     * JPA default constructor.
     */
    protected Relatie() {
    }

    /**
     * Maak een nieuwe relatie.
     * @param soortRelatie soort relatie
     */
    public Relatie(final SoortRelatie soortRelatie) {
        setSoortRelatie(soortRelatie);
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
     * Zet de waarden voor id van Relatie.
     * @param id de nieuwe waarde voor id van Relatie
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van buitenlandse plaats aanvang van Relatie.
     * @return de waarde van buitenlandse plaats aanvang van Relatie
     */
    public String getBuitenlandsePlaatsAanvang() {
        return buitenlandsePlaatsAanvang;
    }

    /**
     * Zet de waarden voor buitenlandse plaats aanvang van Relatie.
     * @param buitenlandsePlaatsAanvang de nieuwe waarde voor buitenlandse plaats aanvang van Relatie
     */
    public void setBuitenlandsePlaatsAanvang(final String buitenlandsePlaatsAanvang) {
        ValidationUtils.controleerOpLegeWaarden("buitenlandsePlaatsAanvang mag geen lege string zijn", buitenlandsePlaatsAanvang);
        this.buitenlandsePlaatsAanvang = buitenlandsePlaatsAanvang;
    }

    /**
     * Geef de waarde van buitenlandse plaats einde van Relatie.
     * @return de waarde van buitenlandse plaats einde van Relatie
     */
    public String getBuitenlandsePlaatsEinde() {
        return buitenlandsePlaatsEinde;
    }

    /**
     * Zet de waarden voor buitenlandse plaats einde van Relatie.
     * @param buitenlandsePlaatsEinde de nieuwe waarde voor buitenlandse plaats einde van Relatie
     */
    public void setBuitenlandsePlaatsEinde(final String buitenlandsePlaatsEinde) {
        ValidationUtils.controleerOpLegeWaarden("buitenlandsePlaatsEinde mag geen lege string zijn", buitenlandsePlaatsEinde);
        this.buitenlandsePlaatsEinde = buitenlandsePlaatsEinde;
    }

    /**
     * Geef de waarde van buitenlandse regio aanvang van Relatie.
     * @return de waarde van buitenlandse regio aanvang van Relatie
     */
    public String getBuitenlandseRegioAanvang() {
        return buitenlandseRegioAanvang;
    }

    /**
     * Zet de waarden voor buitenlandse regio aanvang van Relatie.
     * @param buitenlandseRegioAanvang de nieuwe waarde voor buitenlandse regio aanvang van Relatie
     */
    public void setBuitenlandseRegioAanvang(final String buitenlandseRegioAanvang) {
        ValidationUtils.controleerOpLegeWaarden("buitenlandseRegioAanvang mag geen lege string zijn", buitenlandseRegioAanvang);
        this.buitenlandseRegioAanvang = buitenlandseRegioAanvang;
    }

    /**
     * Geef de waarde van buitenlandse regio einde van Relatie.
     * @return de waarde van buitenlandse regio einde van Relatie
     */
    public String getBuitenlandseRegioEinde() {
        return buitenlandseRegioEinde;
    }

    /**
     * Zet de waarden voor buitenlandse regio einde van Relatie.
     * @param buitenlandseRegioEinde de nieuwe waarde voor buitenlandse regio einde van Relatie
     */
    public void setBuitenlandseRegioEinde(final String buitenlandseRegioEinde) {
        ValidationUtils.controleerOpLegeWaarden("buitenlandseRegioEinde mag geen lege string zijn", buitenlandseRegioEinde);
        this.buitenlandseRegioEinde = buitenlandseRegioEinde;
    }

    /**
     * Geef de waarde van datum aanvang van Relatie.
     * @return de waarde van datum aanvang van Relatie
     */
    public Integer getDatumAanvang() {
        return datumAanvang;
    }

    /**
     * Zet de waarden voor datum aanvang van Relatie.
     * @param datumAanvang de nieuwe waarde voor datum aanvang van Relatie
     */
    public void setDatumAanvang(final Integer datumAanvang) {
        this.datumAanvang = datumAanvang;
    }

    /**
     * Geef de waarde van datum einde van Relatie.
     * @return de waarde van datum einde van Relatie
     */
    public Integer getDatumEinde() {
        return datumEinde;
    }

    /**
     * Zet de waarden voor datum einde van Relatie.
     * @param datumEinde de nieuwe waarde voor datum einde van Relatie
     */
    public void setDatumEinde(final Integer datumEinde) {
        this.datumEinde = datumEinde;
    }

    /**
     * Geef de waarde van omschrijving locatie aanvang van Relatie.
     * @return de waarde van omschrijving locatie aanvang van Relatie
     */
    public String getOmschrijvingLocatieAanvang() {
        return omschrijvingLocatieAanvang;
    }

    /**
     * Zet de waarden voor omschrijving locatie aanvang van Relatie.
     * @param omschrijvingLocatieAanvang de nieuwe waarde voor omschrijving locatie aanvang van Relatie
     */
    public void setOmschrijvingLocatieAanvang(final String omschrijvingLocatieAanvang) {
        ValidationUtils.controleerOpLegeWaarden("omschrijvingLocatieAanvang mag geen lege string zijn", omschrijvingLocatieAanvang);
        this.omschrijvingLocatieAanvang = omschrijvingLocatieAanvang;
    }

    /**
     * Geef de waarde van omschrijving locatie einde van Relatie.
     * @return de waarde van omschrijving locatie einde van Relatie
     */
    public String getOmschrijvingLocatieEinde() {
        return omschrijvingLocatieEinde;
    }

    /**
     * Zet de waarden voor omschrijving locatie einde van Relatie.
     * @param omschrijvingLocatieEinde de nieuwe waarde voor omschrijving locatie einde van Relatie
     */
    public void setOmschrijvingLocatieEinde(final String omschrijvingLocatieEinde) {
        ValidationUtils.controleerOpLegeWaarden("omschrijvingLocatieEinde mag geen lege string zijn", omschrijvingLocatieEinde);
        this.omschrijvingLocatieEinde = omschrijvingLocatieEinde;
    }

    /**
     * Geef de waarde van betrokkenheid set van Relatie.
     * @return de waarde van betrokkenheid set van Relatie
     */
    public Set<Betrokkenheid> getBetrokkenheidSet() {
        return betrokkenheidSet;
    }

    /**
     * Geef de actuele betrokkenheden terug van {@link Relatie}.
     * @return de lijst van actuele betrokkenheden
     */
    public Set<Betrokkenheid> getActueleBetrokkenheidSet() {
        return Betrokkenheid.getActueleBetrokkenheden(betrokkenheidSet);
    }

    /**
     * Geef de actuele betrokkenheden van het gegeven soort.
     * @param soortBetrokkenheid soort betrokkenheid
     * @return actuele betrokkenheid set
     */
    public Set<Betrokkenheid> getActueleBetrokkenheidSet(final SoortBetrokkenheid soortBetrokkenheid) {
        return Betrokkenheid.getActueleBetrokkenheden(getBetrokkenheidSet(soortBetrokkenheid));
    }

    /**
     * Geef de waarde van betrokkenheid set.
     * @param soortBetrokkenheid soort betrokkenheid
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
     * @param betrokkenheid betrokkenheid
     */
    public void addBetrokkenheid(final Betrokkenheid betrokkenheid) {
        betrokkenheid.setRelatie(this);
        betrokkenheidSet.add(betrokkenheid);
    }

    /**
     * Verwijderen van een betrokkenheid.
     * @param betrokkenheid betrokkenheid
     * @return true, if successful
     */
    public boolean removeBetrokkenheid(final Betrokkenheid betrokkenheid) {
        return betrokkenheidSet.remove(betrokkenheid);
    }

    /**
     * Geef de waarde van relatie historie set van Relatie.
     * @return de waarde van relatie historie set van Relatie
     */
    public Set<RelatieHistorie> getRelatieHistorieSet() {
        return relatieHistorieSet;
    }

    /**
     * Geef de waarde van actuele relatie historie van Relatie.
     * @return de waarde van actuele relatie historie van Relatie
     */
    public RelatieHistorie getActueleRelatieHistorie() {
        return FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(relatieHistorieSet);
    }

    /**
     * Toevoegen van een huwelijk geregistreerd partnerschap historie.
     * @param relatieHistorie huwelijk geregistreerd partnerschap historie
     */
    public void addRelatieHistorie(final RelatieHistorie relatieHistorie) {
        relatieHistorie.setRelatie(this);
        relatieHistorieSet.add(relatieHistorie);
    }

    /**
     * Geef de waarde van land of gebied einde van Relatie.
     * @return de waarde van land of gebied einde van Relatie
     */
    public LandOfGebied getLandOfGebiedEinde() {
        return landOfGebiedEinde;
    }

    /**
     * Zet de waarden voor land of gebied einde van Relatie.
     * @param landOfGebiedEinde de nieuwe waarde voor land of gebied einde van Relatie
     */
    public void setLandOfGebiedEinde(final LandOfGebied landOfGebiedEinde) {
        this.landOfGebiedEinde = landOfGebiedEinde;
    }

    /**
     * Geef de waarde van land of gebied aanvang van Relatie.
     * @return de waarde van land of gebied aanvang van Relatie
     */
    public LandOfGebied getLandOfGebiedAanvang() {
        return landOfGebiedAanvang;
    }

    /**
     * Zet de waarden voor land of gebied aanvang van Relatie.
     * @param landOfGebiedAanvang de nieuwe waarde voor land of gebied aanvang van Relatie
     */
    public void setLandOfGebiedAanvang(final LandOfGebied landOfGebiedAanvang) {
        this.landOfGebiedAanvang = landOfGebiedAanvang;
    }

    /**
     * Geef de waarde van gemeente einde van Relatie.
     * @return de waarde van gemeente einde van Relatie
     */
    public Gemeente getGemeenteEinde() {
        return gemeenteEinde;
    }

    /**
     * Zet de waarden voor gemeente einde van Relatie.
     * @param gemeenteEinde de nieuwe waarde voor gemeente einde van Relatie
     */
    public void setGemeenteEinde(final Gemeente gemeenteEinde) {
        this.gemeenteEinde = gemeenteEinde;
    }

    /**
     * Geef de waarde van gemeente aanvang van Relatie.
     * @return de waarde van gemeente aanvang van Relatie
     */
    public Gemeente getGemeenteAanvang() {
        return gemeenteAanvang;
    }

    /**
     * Zet de waarden voor gemeente aanvang van Relatie.
     * @param gemeenteAanvang de nieuwe waarde voor gemeente aanvang van Relatie
     */
    public void setGemeenteAanvang(final Gemeente gemeenteAanvang) {
        this.gemeenteAanvang = gemeenteAanvang;
    }

    /**
     * Geef de waarde van woonplaatsnaam aanvang van Relatie.
     * @return de waarde van woonplaatsnaam aanvang van Relatie
     */
    public String getWoonplaatsnaamAanvang() {
        return woonplaatsnaamAanvang;
    }

    /**
     * Zet de waarden voor woonplaatsnaam aanvang van Relatie.
     * @param woonplaatsnaamAanvang de nieuwe waarde voor woonplaatsnaam aanvang van Relatie
     */
    public void setWoonplaatsnaamAanvang(final String woonplaatsnaamAanvang) {
        this.woonplaatsnaamAanvang = woonplaatsnaamAanvang;
    }

    /**
     * Geef de waarde van woonplaatsnaam einde van Relatie.
     * @return de waarde van woonplaatsnaam einde van Relatie
     */
    public String getWoonplaatsnaamEinde() {
        return woonplaatsnaamEinde;
    }

    /**
     * Zet de waarden voor woonplaatsnaam einde van Relatie.
     * @param woonplaatsnaamEinde de nieuwe waarde voor woonplaatsnaam einde van Relatie
     */
    public void setWoonplaatsnaamEinde(final String woonplaatsnaamEinde) {
        this.woonplaatsnaamEinde = woonplaatsnaamEinde;
    }

    /**
     * Geef de waarde van reden beeindiging relatie van Relatie.
     * @return de waarde van reden beeindiging relatie van Relatie
     */
    public RedenBeeindigingRelatie getRedenBeeindigingRelatie() {
        return redenBeeindigingRelatie;
    }

    /**
     * Zet de waarden voor reden beeindiging relatie van Relatie.
     * @param redenBeeindigingRelatie de nieuwe waarde voor reden beeindiging relatie van Relatie
     */
    public void setRedenBeeindigingRelatie(final RedenBeeindigingRelatie redenBeeindigingRelatie) {
        this.redenBeeindigingRelatie = redenBeeindigingRelatie;
    }

    /**
     * Geef de waarde van soort relatie van Relatie.
     * @return de waarde van soort relatie van Relatie
     */
    public SoortRelatie getSoortRelatie() {
        return SoortRelatie.parseId(soortRelatieId);
    }

    /**
     * Zet de waarden voor soort relatie van Relatie.
     * @param soortRelatie de nieuwe waarde voor soort relatie van Relatie
     */
    public void setSoortRelatie(final SoortRelatie soortRelatie) {
        ValidationUtils.controleerOpNullWaarden("soortRelatie mag niet null zijn", soortRelatie);
        soortRelatieId = soortRelatie.getId();
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
     * Geef de waarde van stapels van Relatie.
     * @return de waarde van stapels van Relatie
     */
    public Set<Stapel> getStapels() {
        return new LinkedHashSet<>(stapels);
    }

    /**
     * Bevat stapel.
     * @param stapel de stapel
     * @return true als de meegegeven stapel hoort bij deze relatie, anders false
     */
    boolean bevatStapel(final Stapel stapel) {
        boolean result = false;
        for (final Stapel mijnStapel : stapels) {
            if (Entiteit.convertToPojo(stapel).equals(Entiteit.convertToPojo(mijnStapel))) {
                result = true;
                break;
            }
        }

        return result;
    }

    /**
     * Maakt een bidirectionele koppeling tussen de meegegeven stapel en deze relatie.
     * @param stapel de stapel
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
     * @param stapel de te verwijderen stapel
     */
    public void removeStapel(final Stapel stapel) {
        for (final Iterator<Stapel> iterator = stapels.iterator(); iterator.hasNext(); ) {
            final Stapel mijnStapel = iterator.next();
            if (Entiteit.convertToPojo(stapel).equals(Entiteit.convertToPojo(mijnStapel))) {
                iterator.remove();
                stapel.removeRelatie(this);
                break;
            }
        }
    }

    @Override
    public Map<String, Collection<FormeleHistorie>> verzamelHistorie() {
        final Map<String, Collection<FormeleHistorie>> result = new HashMap<>();

        result.put("relatieHistorieSet", Collections.unmodifiableSet(relatieHistorieSet));
        return result;
    }

    /**
     * Geeft de andere betrokkenheid binnen de relatie.
     * @param persoon de persoon waarmee bepaalt wordt wat de andere betrokkenheid is.
     * @return de andere betrokkenheid
     * @throws IllegalStateException als de relatie niet van het soort {@link SoortRelatie#GEREGISTREERD_PARTNERSCHAP} of {@link SoortRelatie#HUWELIJK} is
     */
    public Betrokkenheid getAndereBetrokkenheid(final Persoon persoon) {
        if (SoortRelatie.HUWELIJK.equals(getSoortRelatie()) || SoortRelatie.GEREGISTREERD_PARTNERSCHAP.equals(getSoortRelatie())) {
            for (final Betrokkenheid betrokkenheid : betrokkenheidSet) {
                if (betrokkenheid.getPersoon() != persoon) {
                    return betrokkenheid;
                }
            }
        }
        throw new IllegalStateException("Methode niet mogelijk bij andere relaties dan huwelijk of geregistreerd partnerschap");
    }

    /**
     * Geeft de andere betrokkenheden van het meegegeven soort betrokkenheid binnen de relatie.
     * worden terug gegeven.
     * @param persoon de persoon waarmee bepaalt wordt wat de andere betrokkenheid is.
     * @param soortBetrokkenheid soort betrokkenheid indien null wordt niet gefilterd.
     * @return lijst met betrokkenheden van het soort ouder
     */
    public List<Persoon> getAndereBetrokkenheden(final Persoon persoon, final SoortBetrokkenheid soortBetrokkenheid) {
        return betrokkenheidSet.stream()
                .filter(Betrokkenheid::isActueelEnGeldig)
                .filter(betrokkenheid -> betrokkenheid.getPersoon() != persoon)
                .filter(betrokkenheid -> soortBetrokkenheid == null || soortBetrokkenheid.equals(betrokkenheid.getSoortBetrokkenheid()))
                .map(Betrokkenheid::getPersoon).collect(Collectors.toList());

    }


    /**
     * Geeft de actuele of de meest recent beeindigde relatie terug uit de collectie van meegegeven
     * relaties.
     * @param relaties de collectie van relaties waaruit de actuele of meest recent beeindigde relatie gezocht wordt
     * @return de actuele of de meest recent beeindigde relatie
     */
    static Relatie getActueleOfMeestRecentBeeindigdeRelatie(final Set<Relatie> relaties) {
        final List<Relatie> actueleOfMeestRecenteRelaties =
                relaties.stream().filter(relatie -> relatie.getActueleRelatieHistorie() != null).sorted((final Relatie o1, final Relatie o2) -> {
                    final RelatieHistorie o1Historie = o1.getActueleRelatieHistorie();
                    final RelatieHistorie o2Historie = o2.getActueleRelatieHistorie();
                    final Integer o1DatumAanvang = o1Historie.getDatumAanvang();
                    final Integer o2DatumAanvang = o2Historie.getDatumAanvang();
                    final int datumEindeO1 = o1Historie.getDatumEinde() == null ? Integer.MAX_VALUE : o1Historie.getDatumEinde();
                    final int datumEindeO2 = o2Historie.getDatumEinde() == null ? Integer.MAX_VALUE : o2Historie.getDatumEinde();
                    if (datumEindeO2 - datumEindeO1 == 0) {
                        return o2DatumAanvang - o1DatumAanvang;
                    }
                    return datumEindeO2 - datumEindeO1;
                }).collect(Collectors.toList());
        return actueleOfMeestRecenteRelaties.isEmpty() ? null : actueleOfMeestRecenteRelaties.get(0);
    }
}

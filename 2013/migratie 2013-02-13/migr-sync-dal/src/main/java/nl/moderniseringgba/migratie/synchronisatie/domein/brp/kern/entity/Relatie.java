/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the relatie database table.
 * 
 */
@Entity
@Table(name = "relatie", schema = "kern")
/*
 * CHECKSTYLE:OFF Deze class is gegenereerd o.b.v. het BRP datamodel en bevat daarom geen javadoc, daarnaast mogen
 * entities en de methoden van entities niet final zijn.
 */
public class Relatie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "RELATIE_ID_GENERATOR", sequenceName = "KERN.SEQ_RELATIE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RELATIE_ID_GENERATOR")
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

    @Column(name = "dataanv", precision = 8)
    private BigDecimal datumAanvang;

    @Column(name = "dateinde", precision = 8)
    private BigDecimal datumEinde;

    @Column(name = "omslocaanv", length = 40)
    private String omschrijvingLocatieAanvang;

    @Column(name = "omsloceinde", length = 40)
    private String omschrijvingLocatieEinde;

    @Enumerated(EnumType.STRING)
    @Column(name = "relatiestatushis", nullable = false, length = 1)
    private HistorieStatus relatieStatusHistorie = HistorieStatus.X;

    // bi-directional many-to-one association to Betrokkenheid
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "relatie", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private final Set<Betrokkenheid> betrokkenheidSet = new LinkedHashSet<Betrokkenheid>(0);

    // bi-directional many-to-one association to RelatieHistorie
    @OneToMany(mappedBy = "relatie", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
    private final Set<RelatieHistorie> relatieHistorieSet = new LinkedHashSet<RelatieHistorie>(0);

    // bi-directional many-to-one association to MultiRealiteitRegel
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "relatie", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private final Set<MultiRealiteitRegel> multiRealiteitRegelSet = new LinkedHashSet<MultiRealiteitRegel>(0);

    // bi-directional many-to-one association to Land
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "landeinde")
    private Land landEinde;

    // bi-directional many-to-one association to Land
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "landaanv")
    private Land landAanvang;

    // bi-directional many-to-one association to Partij
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "gemeinde")
    private Partij gemeenteEinde;

    // bi-directional many-to-one association to Partij
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "gemaanv")
    private Partij gemeenteAanvang;

    // bi-directional many-to-one association to Plaats
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "wplaanv")
    private Plaats woonplaatsAanvang;

    // bi-directional many-to-one association to Plaats
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "wpleinde")
    private Plaats woonplaatsEinde;

    // bi-directional many-to-one association to RedenBeeindigingRelatie
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "rdneinde")
    private RedenBeeindigingRelatie redenBeeindigingRelatie;

    @Column(name = "srt", nullable = false)
    private Integer soortRelatieId;

    /**
     * 
     */
    public Relatie() {
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getBuitenlandsePlaatsAanvang() {
        return buitenlandsePlaatsAanvang;
    }

    public void setBuitenlandsePlaatsAanvang(final String buitenlandsePlaatsAanvang) {
        this.buitenlandsePlaatsAanvang = buitenlandsePlaatsAanvang;
    }

    public String getBuitenlandsePlaatsEinde() {
        return buitenlandsePlaatsEinde;
    }

    public void setBuitenlandsePlaatsEinde(final String buitenlandsePlaatsEinde) {
        this.buitenlandsePlaatsEinde = buitenlandsePlaatsEinde;
    }

    public String getBuitenlandseRegioAanvang() {
        return buitenlandseRegioAanvang;
    }

    public void setBuitenlandseRegioAanvang(final String buitenlandseRegioAanvang) {
        this.buitenlandseRegioAanvang = buitenlandseRegioAanvang;
    }

    public String getBuitenlandseRegioEinde() {
        return buitenlandseRegioEinde;
    }

    public void setBuitenlandseRegioEinde(final String buitenlandseRegioEinde) {
        this.buitenlandseRegioEinde = buitenlandseRegioEinde;
    }

    public BigDecimal getDatumAanvang() {
        return datumAanvang;
    }

    public void setDatumAanvang(final BigDecimal datumAanvang) {
        this.datumAanvang = datumAanvang;
    }

    public BigDecimal getDatumEinde() {
        return datumEinde;
    }

    public void setDatumEinde(final BigDecimal datumEinde) {
        this.datumEinde = datumEinde;
    }

    public String getOmschrijvingLocatieAanvang() {
        return omschrijvingLocatieAanvang;
    }

    public void setOmschrijvingLocatieAanvang(final String omschrijvingLocatieAanvang) {
        this.omschrijvingLocatieAanvang = omschrijvingLocatieAanvang;
    }

    public String getOmschrijvingLocatieEinde() {
        return omschrijvingLocatieEinde;
    }

    public void setOmschrijvingLocatieEinde(final String omschrijvingLocatieEinde) {
        this.omschrijvingLocatieEinde = omschrijvingLocatieEinde;
    }

    public HistorieStatus getRelatieStatusHistorie() {
        return relatieStatusHistorie;
    }

    public void setRelatieStatusHistorie(final HistorieStatus relatieStatusHistorie) {
        this.relatieStatusHistorie = relatieStatusHistorie;
    }

    public Set<Betrokkenheid> getBetrokkenheidSet() {
        return betrokkenheidSet;
    }

    /**
     * @param betrokkenheid
     */
    public void addBetrokkenheid(final Betrokkenheid betrokkenheid) {
        betrokkenheid.setRelatie(this);
        betrokkenheidSet.add(betrokkenheid);
    }

    /**
     * @param betrokkenheid
     * @return
     */
    public boolean removeBetrokkenheid(final Betrokkenheid betrokkenheid) {
        return betrokkenheidSet.remove(betrokkenheid);
    }

    public Set<RelatieHistorie> getRelatieHistorieSet() {
        return relatieHistorieSet;
    }

    /**
     * @param relatieHistorie
     */
    public void addRelatieHistorie(final RelatieHistorie relatieHistorie) {
        relatieHistorie.setRelatie(this);
        relatieHistorieSet.add(relatieHistorie);
    }

    public Set<MultiRealiteitRegel> getMultiRealiteitRegelSet() {
        return multiRealiteitRegelSet;
    }

    /**
     * @param multiRealiteitRegel
     */
    public void addMultiRealiteitRegel(final MultiRealiteitRegel multiRealiteitRegel) {
        multiRealiteitRegel.setRelatie(this);
        multiRealiteitRegelSet.add(multiRealiteitRegel);
    }

    /**
     * @param multiRealiteitRegel
     * @return
     */
    public boolean removeMultiRealiteitRegel(final MultiRealiteitRegel multiRealiteitRegel) {
        return multiRealiteitRegelSet.remove(multiRealiteitRegel);
    }

    public Land getLandEinde() {
        return landEinde;
    }

    public void setLandEinde(final Land landEinde) {
        this.landEinde = landEinde;
    }

    public Land getLandAanvang() {
        return landAanvang;
    }

    public void setLandAanvang(final Land landAanvang) {
        this.landAanvang = landAanvang;
    }

    public Partij getGemeenteEinde() {
        return gemeenteEinde;
    }

    public void setGemeenteEinde(final Partij gemeenteEinde) {
        this.gemeenteEinde = gemeenteEinde;
    }

    public Partij getGemeenteAanvang() {
        return gemeenteAanvang;
    }

    public void setGemeenteAanvang(final Partij gemeenteAanvang) {
        this.gemeenteAanvang = gemeenteAanvang;
    }

    public Plaats getWoonplaatsAanvang() {
        return woonplaatsAanvang;
    }

    public void setWoonplaatsAanvang(final Plaats woonplaatsAanvang) {
        this.woonplaatsAanvang = woonplaatsAanvang;
    }

    public Plaats getWoonplaatsEinde() {
        return woonplaatsEinde;
    }

    public void setWoonplaatsEinde(final Plaats woonplaatsEinde) {
        this.woonplaatsEinde = woonplaatsEinde;
    }

    public RedenBeeindigingRelatie getRedenBeeindigingRelatie() {
        return redenBeeindigingRelatie;
    }

    public void setRedenBeeindigingRelatie(final RedenBeeindigingRelatie redenBeeindigingRelatie) {
        this.redenBeeindigingRelatie = redenBeeindigingRelatie;
    }

    /**
     * @return
     */
    public SoortRelatie getSoortRelatie() {
        return SoortRelatie.parseId(soortRelatieId);
    }

    /**
     * @param soortRelatie
     */
    public void setSoortRelatie(final SoortRelatie soortRelatie) {
        if (soortRelatie == null) {
            soortRelatieId = null;
        } else {
            soortRelatieId = soortRelatie.getId();
        }
    }

    /**
     * Verwijderd de MultiRealiteitRegel op relatie die geldig is voor de meegeven persoon als deze bestaat.
     * 
     * @param persoon
     *            de persoon waarvoor de MultiRealiteitRegel geldig is, mag niet null zijn
     * @return de MultiRealiteitRegel waarvan alle associaties zijn verwijderd en dus nu zelf verwijderd kan worden,
     *         anders null
     * @throws NullPointerException
     *             als persoon null is
     */
    public MultiRealiteitRegel removeMultiRealiteitRegelDieGeldigIsVoorPersoon(final Persoon persoon) {
        if (persoon == null) {
            throw new NullPointerException("persoon mag niet null zijn");
        }
        for (final MultiRealiteitRegel multiRealiteitRegel : multiRealiteitRegelSet) {
            if (persoon.equals(multiRealiteitRegel.getGeldigVoorPersoon())) {
                if (removeMultiRealiteitRegel(multiRealiteitRegel)) {
                    if (persoon.removeMultiRealiteitRegelGeldigVoorPersoon(multiRealiteitRegel)) {
                        return multiRealiteitRegel;
                    }
                }
            }
        }
        return null;
    }

    /**
     * @param persoon
     *            de persoon waarvoor de MultiRealiteitRegel geldig is, mag niet null zijn
     * @return de MultiRealiteitRegel die geldig is voor deze persoon, anders null
     * @throws NullPointerException
     *             als persoon null is
     */
    public MultiRealiteitRegel getMultiRealiteitRegelDieGeldigIsVoorPersoon(final Persoon persoon) {
        if (persoon == null) {
            throw new NullPointerException("persoon mag niet null zijn");
        }
        for (final MultiRealiteitRegel multiRealiteitRegel : multiRealiteitRegelSet) {
            if (persoon.equals(multiRealiteitRegel.getGeldigVoorPersoon())) {
                return multiRealiteitRegel;
            }
        }
        return null;
    }

    /**
     * @param andereRelatie
     * @param controleerBetrokkenheid
     * @return
     */
    public boolean isInhoudelijkGelijkAan(final Relatie andereRelatie, final boolean controleerBetrokkenheid) {
        if (this == andereRelatie) {
            return true;
        }
        if (andereRelatie == null) {
            return false;
        }
        if (buitenlandsePlaatsAanvang == null) {
            if (andereRelatie.buitenlandsePlaatsAanvang != null) {
                return false;
            }
        } else if (!buitenlandsePlaatsAanvang.equals(andereRelatie.buitenlandsePlaatsAanvang)) {
            return false;
        }
        if (buitenlandsePlaatsEinde == null) {
            if (andereRelatie.buitenlandsePlaatsEinde != null) {
                return false;
            }
        } else if (!buitenlandsePlaatsEinde.equals(andereRelatie.buitenlandsePlaatsEinde)) {
            return false;
        }
        if (buitenlandseRegioAanvang == null) {
            if (andereRelatie.buitenlandseRegioAanvang != null) {
                return false;
            }
        } else if (!buitenlandseRegioAanvang.equals(andereRelatie.buitenlandseRegioAanvang)) {
            return false;
        }
        if (buitenlandseRegioEinde == null) {
            if (andereRelatie.buitenlandseRegioEinde != null) {
                return false;
            }
        } else if (!buitenlandseRegioEinde.equals(andereRelatie.buitenlandseRegioEinde)) {
            return false;
        }
        if (datumAanvang == null) {
            if (andereRelatie.datumAanvang != null) {
                return false;
            }
        } else if (!datumAanvang.equals(andereRelatie.datumAanvang)) {
            return false;
        }
        if (datumEinde == null) {
            if (andereRelatie.datumEinde != null) {
                return false;
            }
        } else if (!datumEinde.equals(andereRelatie.datumEinde)) {
            return false;
        }
        if (gemeenteAanvang == null) {
            if (andereRelatie.gemeenteAanvang != null) {
                return false;
            }
        } else if (!gemeenteAanvang.isInhoudelijkGelijkAan(andereRelatie.gemeenteAanvang)) {
            return false;
        }
        if (gemeenteEinde == null) {
            if (andereRelatie.gemeenteEinde != null) {
                return false;
            }
        } else if (!gemeenteEinde.isInhoudelijkGelijkAan(andereRelatie.gemeenteEinde)) {
            return false;
        }
        if (landAanvang == null) {
            if (andereRelatie.landAanvang != null) {
                return false;
            }
        } else if (!landAanvang.isInhoudelijkGelijkAan(andereRelatie.landAanvang)) {
            return false;
        }
        if (landEinde == null) {
            if (andereRelatie.landEinde != null) {
                return false;
            }
        } else if (!landEinde.isInhoudelijkGelijkAan(andereRelatie.landEinde)) {
            return false;
        }
        if (omschrijvingLocatieAanvang == null) {
            if (andereRelatie.omschrijvingLocatieAanvang != null) {
                return false;
            }
        } else if (!omschrijvingLocatieAanvang.equals(andereRelatie.omschrijvingLocatieAanvang)) {
            return false;
        }
        if (omschrijvingLocatieEinde == null) {
            if (andereRelatie.omschrijvingLocatieEinde != null) {
                return false;
            }
        } else if (!omschrijvingLocatieEinde.equals(andereRelatie.omschrijvingLocatieEinde)) {
            return false;
        }
        if (redenBeeindigingRelatie == null) {
            if (andereRelatie.redenBeeindigingRelatie != null) {
                return false;
            }
        } else if (!redenBeeindigingRelatie.isInhoudelijkGelijkAan(andereRelatie.redenBeeindigingRelatie)) {
            return false;
        }
        if (soortRelatieId == null) {
            if (andereRelatie.soortRelatieId != null) {
                return false;
            }
        } else if (!soortRelatieId.equals(andereRelatie.soortRelatieId)) {
            return false;
        }
        if (woonplaatsAanvang == null) {
            if (andereRelatie.woonplaatsAanvang != null) {
                return false;
            }
        } else if (!woonplaatsAanvang.isInhoudelijkGelijkAan(andereRelatie.woonplaatsAanvang)) {
            return false;
        }
        if (woonplaatsEinde == null) {
            if (andereRelatie.woonplaatsEinde != null) {
                return false;
            }
        } else if (!woonplaatsEinde.isInhoudelijkGelijkAan(andereRelatie.woonplaatsEinde)) {
            return false;
        }
        if (relatieHistorieSet.size() != andereRelatie.relatieHistorieSet.size()) {
            return false;
        }
        final List<RelatieHistorie> relatieHistorieList = new ArrayList<RelatieHistorie>(relatieHistorieSet);
        final List<RelatieHistorie> andereRelatieHistorieList =
                new ArrayList<RelatieHistorie>(andereRelatie.relatieHistorieSet);
        Collections.sort(relatieHistorieList, FormeleHistorie.COMPARATOR);
        Collections.sort(andereRelatieHistorieList, FormeleHistorie.COMPARATOR);
        for (int index = 0; index < relatieHistorieList.size(); index++) {
            if (!relatieHistorieList.get(index).isInhoudelijkGelijkAan(andereRelatieHistorieList.get(index))) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return true als alle personen die betrokken zijn bij deze relatie, deze relatie ontkennen met MR, anders false
     */
    public boolean isDoorAlleBetrokkenPersonenOntkend() {
        boolean result = false;
        switch (getSoortRelatie()) {
            case GEREGISTREERD_PARTNERSCHAP:
            case HUWELIJK:
                result = isHuwelijkOfGpDoorAlleBetrokkenPersonenOntkend();
                break;
            case FAMILIERECHTELIJKE_BETREKKING:
                result = isFamilieDoorAlleBetrokkenPersonenOntkend();
                break;
            default:
                throw new IllegalStateException("Onbekende soort relatie: " + getSoortRelatie());
        }
        return result;
    }

    /**
     * @return
     */
    private boolean isHuwelijkOfGpDoorAlleBetrokkenPersonenOntkend() {
        boolean result = true;
        for (final Betrokkenheid betrokkenheid : betrokkenheidSet) {
            boolean isBetrokkenheidOntkend = false;
            if (betrokkenheid.getPersoon() == null
                    || betrokkenheid.getPersoon().getSoortPersoon() == SoortPersoon.NIET_INGESCHREVENE) {
                isBetrokkenheidOntkend = true;
            } else {
                for (final MultiRealiteitRegel multiRealiteitRegel : betrokkenheid.getPersoon()
                        .getMultiRealiteitRegelGeldigVoorPersoonSet()) {
                    if (SoortMultiRealiteitRegel.RELATIE.equals(multiRealiteitRegel.getSoortMultiRealiteitRegel())) {
                        final boolean isIdGelijk =
                                getId() != null && getId().equals(multiRealiteitRegel.getRelatie().getId());
                        if (isIdGelijk || this.equals(multiRealiteitRegel.getRelatie())) {
                            isBetrokkenheidOntkend = true;
                        }
                    }
                }
            }
            result &= isBetrokkenheidOntkend;
        }
        return result;
    }

    /**
     * @return
     */
    private boolean isFamilieDoorAlleBetrokkenPersonenOntkend() {
        boolean result = true;
        for (final Betrokkenheid betrokkenheid : betrokkenheidSet) {
            boolean isBetrokkenheidOntkend = false;
            if (betrokkenheid.getPersoon() == null
                    || betrokkenheid.getPersoon().getSoortPersoon() == SoortPersoon.NIET_INGESCHREVENE) {
                isBetrokkenheidOntkend = true;
            } else {
                for (final MultiRealiteitRegel multiRealiteitRegel : betrokkenheid.getPersoon()
                        .getMultiRealiteitRegelGeldigVoorPersoonSet()) {
                    if (SoortMultiRealiteitRegel.BETROKKENHEID.equals(multiRealiteitRegel
                            .getSoortMultiRealiteitRegel())) {
                        final boolean isIdGelijk =
                                getId() != null
                                        && getId()
                                                .equals(multiRealiteitRegel.getBetrokkenheid().getRelatie().getId());
                        if (isIdGelijk || this.equals(multiRealiteitRegel.getBetrokkenheid().getRelatie())) {
                            isBetrokkenheidOntkend = true;
                        }
                    }
                }
            }
            result &= isBetrokkenheidOntkend;
        }
        return result;
    }

    /**
     * @param teMergenBetrokkenheid
     * @return
     */
    public boolean heeftBetrokkenheid(final Betrokkenheid teMergenBetrokkenheid) {
        if (teMergenBetrokkenheid != null) {
            for (final Betrokkenheid betrokkenheid : betrokkenheidSet) {
                final boolean isRelatieGelijk =
                        betrokkenheid.getRelatie().getId() != null
                                && betrokkenheid.getRelatie().getId()
                                        .equals(teMergenBetrokkenheid.getRelatie().getId())
                                || betrokkenheid.getRelatie().equals(teMergenBetrokkenheid.getRelatie());
                final boolean isBetrokkeneGelijk =
                        betrokkenheid.getPersoon() != null
                                && betrokkenheid.getPersoon().getAdministratienummer()
                                        .equals(teMergenBetrokkenheid.getPersoon().getAdministratienummer())
                                || betrokkenheid.getPersoon() == null && teMergenBetrokkenheid.getPersoon() == null;
                if (isRelatieGelijk && isBetrokkeneGelijk) {
                    return true;
                }
            }
        }
        return false;
    }
}

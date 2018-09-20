/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity;

import java.io.Serializable;
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
 * The persistent class for the betr database table.
 * 
 */
@Entity
@Table(name = "betr", schema = "kern")
/*
 * CHECKSTYLE:OFF Deze class is gegenereerd o.b.v. het BRP datamodel en bevat daarom geen javadoc, daarnaast mogen
 * entities en de methoden van entities niet final zijn.
 */
public class Betrokkenheid implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "BETR_ID_GENERATOR", sequenceName = "KERN.SEQ_BETR", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BETR_ID_GENERATOR")
    @Column(nullable = false)
    private Integer id;

    @Column(name = "indouder")
    private Boolean indicatieOuder;

    @Column(name = "indouderheeftgezag")
    private Boolean indicatieOuderHeeftGezag;

    @Enumerated(EnumType.STRING)
    @Column(name = "ouderlijkgezagstatushis", nullable = false, length = 1)
    private HistorieStatus ouderlijkGezagStatusHistorie = HistorieStatus.X;

    @Enumerated(EnumType.STRING)
    @Column(name = "ouderschapstatushis", nullable = false, length = 1)
    private HistorieStatus ouderStatusHistorie = HistorieStatus.X;

    // bi-directional many-to-one association to Persoon
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "pers")
    private Persoon persoon;

    // bi-directional many-to-one association to Relatie
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "relatie", nullable = false)
    private Relatie relatie;

    @Column(name = "rol", nullable = false)
    private Integer soortBetrokkenheidId;

    // bi-directional many-to-one association to BetrokkenheidOuderHistorie
    @OneToMany(mappedBy = "betrokkenheid", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
    private final Set<BetrokkenheidOuderHistorie> betrokkenheidOuderHistorieSet =
            new LinkedHashSet<BetrokkenheidOuderHistorie>(0);

    // bi-directional many-to-one association to BetrokkenheidOuderlijkGezagHistorie
    @OneToMany(mappedBy = "betrokkenheid", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
    private final Set<BetrokkenheidOuderlijkGezagHistorie> betrokkenheidOuderlijkGezagHistorieSet =
            new LinkedHashSet<BetrokkenheidOuderlijkGezagHistorie>(0);

    // bi-directional many-to-one association to MultiRealiteitRegel
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "betrokkenheid",
            cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private final Set<MultiRealiteitRegel> multiRealiteitRegelSet = new LinkedHashSet<MultiRealiteitRegel>(0);

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public Boolean getIndicatieOuder() {
        return indicatieOuder;
    }

    public void setIndicatieOuder(final Boolean indicatieOuder) {
        this.indicatieOuder = indicatieOuder;
    }

    public Boolean getIndicatieOuderHeeftGezag() {
        return indicatieOuderHeeftGezag;
    }

    public void setIndicatieOuderHeeftGezag(final Boolean indicatieOuderHeeftGezag) {
        this.indicatieOuderHeeftGezag = indicatieOuderHeeftGezag;
    }

    public HistorieStatus getOuderlijkGezagStatusHistorie() {
        return ouderlijkGezagStatusHistorie;
    }

    public void setOuderlijkGezagStatusHistorie(final HistorieStatus ouderlijkGezagStatusHistorie) {
        this.ouderlijkGezagStatusHistorie = ouderlijkGezagStatusHistorie;
    }

    public HistorieStatus getOuderStatusHistorie() {
        return ouderStatusHistorie;
    }

    public void setOuderStatusHistorie(final HistorieStatus ouderStatusHistorie) {
        this.ouderStatusHistorie = ouderStatusHistorie;
    }

    public Persoon getPersoon() {
        return persoon;
    }

    public void setPersoon(final Persoon persoon) {
        this.persoon = persoon;
    }

    public Relatie getRelatie() {
        return relatie;
    }

    /**
     * 
     */
    public Set<Betrokkenheid> getAndereBetrokkenhedenVanRelatie() {
        final Set<Betrokkenheid> result = new LinkedHashSet<Betrokkenheid>();
        if (relatie != null) {
            result.addAll(relatie.getBetrokkenheidSet());
            result.remove(this);
        }
        return result;
    }

    public void setRelatie(final Relatie relatie) {
        this.relatie = relatie;
    }

    /**
     * 
     */
    public SoortBetrokkenheid getSoortBetrokkenheid() {
        return SoortBetrokkenheid.parseId(soortBetrokkenheidId);
    }

    /**
     * @param soortBetrokkenheid
     */
    public void setSoortBetrokkenheid(final SoortBetrokkenheid soortBetrokkenheid) {
        if (soortBetrokkenheid == null) {
            soortBetrokkenheidId = null;
        } else {
            soortBetrokkenheidId = soortBetrokkenheid.getId();
        }
    }

    /**
     * 
     */
    public void addBetrokkenheidOuderHistorie(final BetrokkenheidOuderHistorie betrokkenheidOuderHistorie) {
        betrokkenheidOuderHistorie.setBetrokkenheid(this);
        betrokkenheidOuderHistorieSet.add(betrokkenheidOuderHistorie);
    }

    /**
     * 
     */
    public boolean removeBetrokkenheidOuderHistorie(final BetrokkenheidOuderHistorie betrokkenheidOuderHistorie) {
        return betrokkenheidOuderHistorieSet.remove(betrokkenheidOuderHistorie);
    }

    public Set<BetrokkenheidOuderHistorie> getBetrokkenheidOuderHistorieSet() {
        return betrokkenheidOuderHistorieSet;
    }

    public Set<BetrokkenheidOuderlijkGezagHistorie> getBetrokkenheidOuderlijkGezagHistorieSet() {
        return betrokkenheidOuderlijkGezagHistorieSet;
    }

    /**
     * 
     */
    public void addBetrokkenheidOuderlijkGezagHistorie(
            final BetrokkenheidOuderlijkGezagHistorie betrokkenheidOuderlijkGezagHistorie) {
        betrokkenheidOuderlijkGezagHistorie.setBetrokkenheid(this);
        betrokkenheidOuderlijkGezagHistorieSet.add(betrokkenheidOuderlijkGezagHistorie);
    }

    /**
     * 
     */
    public boolean removeBetrokkenheidOuderlijkGezagHistorie(
            final BetrokkenheidOuderlijkGezagHistorie betrokkenheidOuderlijkGezagHistorie) {
        return betrokkenheidOuderlijkGezagHistorieSet.remove(betrokkenheidOuderlijkGezagHistorie);
    }

    public Set<MultiRealiteitRegel> getMultiRealiteitRegelSet() {
        return multiRealiteitRegelSet;
    }

    /**
     * 
     */
    public void addMultiRealiteitRegel(final MultiRealiteitRegel multiRealiteitRegel) {
        multiRealiteitRegel.setBetrokkenheid(this);
        multiRealiteitRegelSet.add(multiRealiteitRegel);
    }

    /**
     * 
     */
    public boolean removeMultiRealiteitRegel(final MultiRealiteitRegel multiRealiteitRegel) {
        return multiRealiteitRegelSet.remove(multiRealiteitRegel);
    }

    /**
     * Vergelijkt de betrokkenheid en a-nummer van de betrokkenpersoon en delegeert de vergelijking van de
     * relatiegegevens aan de relatie als het een ik-betrokkenheid betreft. Multirealiteit wordt niet meegenomen in de
     * vergelijking en de status historie velden ook niet. De inhoud van betrokkenheid en betrokkenheid historie wordt
     * OPTIONEEL in de vergelijking meegenomen en kunnen dus verschillende zijn.
     * 
     * @param andereBetrokkenheid
     *            de andere betrokkenheid waarmee wordt vergeleken, mag niet null zijn
     * @param isIkBetrokkenheid
     *            is deze betrokkenheid een ikBetrokkenheid dan worden ook de relaties vergeleken anders niet
     * @param controleerBetrokkenheid
     *            true als de betrokkenheid en betrokkenheid historie ook gecontroleerd moet worden, anders false
     * @return true als de twee betrokkenheden volgens de conversie regels als gelijke behandeld moeten worden, anders
     *         false
     * @throws NullPointerException
     *             als andereBetrokkenheid null is
     * @throws IllegalStateException
     *             als de persoon van deze betrokkenheid niet bestaat of geen a-nummer heeft
     */
    public final boolean isInhoudelijkGelijkAan(
            final Betrokkenheid andereBetrokkenheid,
            final boolean isIkBetrokkenheid,
            final boolean controleerBetrokkenheid) {
        if (andereBetrokkenheid == null) {
            return false;
        } else if (this == andereBetrokkenheid) {
            return true;
        } else if (andereBetrokkenheid.getPersoon() == null) {
            return false;
        } else if (getPersoon() == null || getPersoon().getAdministratienummer() == null) {
            return false;
        } else if (!getPersoon().getAdministratienummer().equals(
                andereBetrokkenheid.getPersoon().getAdministratienummer())) {
            return false;
        } else if (soortBetrokkenheidId == null) {
            if (andereBetrokkenheid.soortBetrokkenheidId != null) {
                return false;
            }
        } else if (!soortBetrokkenheidId.equals(andereBetrokkenheid.soortBetrokkenheidId)) {
            return false;
        } else if (controleerBetrokkenheid) {
            if (indicatieOuder == null) {
                if (andereBetrokkenheid.indicatieOuder != null) {
                    return false;
                }
            } else if (!indicatieOuder.equals(andereBetrokkenheid.indicatieOuder)) {
                return false;
            }
            if (indicatieOuderHeeftGezag == null) {
                if (andereBetrokkenheid.indicatieOuderHeeftGezag != null) {
                    return false;
                }
            } else if (!indicatieOuderHeeftGezag.equals(andereBetrokkenheid.indicatieOuderHeeftGezag)) {
                return false;
            }
            if (!isBetrokkenheidOuderHistorieSetGelijk(andereBetrokkenheid)) {
                return false;
            }
            if (!isBetrokkenheidOuderlijkGezagHistorieSetGelijk(andereBetrokkenheid)) {
                return false;
            }
        }
        return !isIkBetrokkenheid
                || getRelatie().isInhoudelijkGelijkAan(andereBetrokkenheid.getRelatie(), controleerBetrokkenheid);

    }

    /**
     * Verwijderd de MultiRealiteitRegel op betrokkenheid die geldig is voor de meegeven persoon als deze bestaat.
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
     * Neemt de indicatie en historie van de andere betrokkenheid over. Historie van de andere betrokkenheid wordt
     * verwijderd en aan deze betrokkenheid toegevoegd.
     * 
     * @param andereBetrokkenheid
     *            de andere betrokkenheid die als bron dient
     */
    public void mergeBetrokkenheid(final Betrokkenheid andereBetrokkenheid) {
        if (andereBetrokkenheid == null) {
            throw new NullPointerException("andereBetrokkenheid mag niet null zijn");
        }
        setIndicatieOuder(andereBetrokkenheid.getIndicatieOuder());
        setIndicatieOuderHeeftGezag(andereBetrokkenheid.getIndicatieOuderHeeftGezag());
        final Set<BetrokkenheidOuderHistorie> overTeNemenOuderHistorie =
                new LinkedHashSet<BetrokkenheidOuderHistorie>(andereBetrokkenheid.getBetrokkenheidOuderHistorieSet());
        final Set<BetrokkenheidOuderlijkGezagHistorie> overTeNemenOuderlijkGezagHistorie =
                new LinkedHashSet<BetrokkenheidOuderlijkGezagHistorie>(
                        andereBetrokkenheid.getBetrokkenheidOuderlijkGezagHistorieSet());

        for (final BetrokkenheidOuderHistorie ouderHistorie : overTeNemenOuderHistorie) {
            andereBetrokkenheid.removeBetrokkenheidOuderHistorie(ouderHistorie);
            addBetrokkenheidOuderHistorie(ouderHistorie);
        }
        for (final BetrokkenheidOuderlijkGezagHistorie ouderlijkGezagHistorie : overTeNemenOuderlijkGezagHistorie) {
            andereBetrokkenheid.removeBetrokkenheidOuderlijkGezagHistorie(ouderlijkGezagHistorie);
            addBetrokkenheidOuderlijkGezagHistorie(ouderlijkGezagHistorie);
        }
        setOuderStatusHistorie(HistorieStatus.bepaalHistorieStatusVoorBrp(getBetrokkenheidOuderHistorieSet()));
        setOuderlijkGezagStatusHistorie(HistorieStatus
                .bepaalHistorieStatusVoorBrp(getBetrokkenheidOuderlijkGezagHistorieSet()));
    }

    private boolean isBetrokkenheidOuderHistorieSetGelijk(final Betrokkenheid andereBetrokkenheid) {
        final List<BetrokkenheidOuderHistorie> ouderHistorieList =
                new ArrayList<BetrokkenheidOuderHistorie>(betrokkenheidOuderHistorieSet);
        final List<BetrokkenheidOuderHistorie> anderOuderHistorieList =
                new ArrayList<BetrokkenheidOuderHistorie>(andereBetrokkenheid.betrokkenheidOuderHistorieSet);

        if (ouderHistorieList.size() != anderOuderHistorieList.size()) {
            return false;
        }

        Collections.sort(ouderHistorieList, FormeleHistorie.COMPARATOR);
        Collections.sort(anderOuderHistorieList, FormeleHistorie.COMPARATOR);

        for (int index = 0; index < ouderHistorieList.size(); index++) {
            if (!ouderHistorieList.get(index).isInhoudelijkGelijkAan(anderOuderHistorieList.get(index))) {
                return false;
            }
        }
        return true;
    }

    private boolean isBetrokkenheidOuderlijkGezagHistorieSetGelijk(final Betrokkenheid andereBetrokkenheid) {
        final List<BetrokkenheidOuderlijkGezagHistorie> ouderlijkgezagHistorieList =
                new ArrayList<BetrokkenheidOuderlijkGezagHistorie>(betrokkenheidOuderlijkGezagHistorieSet);
        final List<BetrokkenheidOuderlijkGezagHistorie> anderOuderlijkgezagHistorieList =
                new ArrayList<BetrokkenheidOuderlijkGezagHistorie>(
                        andereBetrokkenheid.betrokkenheidOuderlijkGezagHistorieSet);

        if (ouderlijkgezagHistorieList.size() != anderOuderlijkgezagHistorieList.size()) {
            return false;
        }

        Collections.sort(ouderlijkgezagHistorieList, FormeleHistorie.COMPARATOR);
        Collections.sort(anderOuderlijkgezagHistorieList, FormeleHistorie.COMPARATOR);

        for (int index = 0; index < ouderlijkgezagHistorieList.size(); index++) {
            if (!ouderlijkgezagHistorieList.get(index).isInhoudelijkGelijkAan(
                    anderOuderlijkgezagHistorieList.get(index))) {
                return false;
            }
        }
        return true;
    }
}

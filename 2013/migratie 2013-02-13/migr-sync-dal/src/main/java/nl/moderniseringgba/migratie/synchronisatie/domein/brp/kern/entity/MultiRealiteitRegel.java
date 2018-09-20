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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.BinaireRelatie;

/**
 * The persistent class for the multirealiteitregel database table.
 * 
 */
@Entity
@Table(name = "multirealiteitregel", schema = "kern")
/*
 * CHECKSTYLE:OFF Deze class is gegenereerd o.b.v. het BRP datamodel en bevat daarom geen javadoc, daarnaast mogen
 * entities en de methoden van entities niet final zijn.
 */
public class MultiRealiteitRegel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "MULTIREALITEITREGEL_ID_GENERATOR", sequenceName = "KERN.SEQ_MULTIREALITEITREGEL",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MULTIREALITEITREGEL_ID_GENERATOR")
    @Column(nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "multirealiteitregelstatushis", nullable = false, length = 1)
    private HistorieStatus multiRealiteitRegelStatusHistorie;

    // bi-directional many-to-one association to MultiRealiteitRegelHistorie
    @OneToMany(mappedBy = "multirealiteitregel", cascade = { CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.REMOVE })
    private final Set<MultiRealiteitRegelHistorie> hisMultirealiteitregels =
            new LinkedHashSet<MultiRealiteitRegelHistorie>(0);

    // bi-directional many-to-one association to Betrokkenheid
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "betr")
    private Betrokkenheid betrokkenheid;

    // bi-directional many-to-one association to Persoon
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "multirealiteitpers")
    private Persoon multiRealiteitPersoon;

    // bi-directional many-to-one association to Persoon
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "pers")
    private Persoon persoon;

    // bi-directional many-to-one association to Persoon
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "geldigvoor", nullable = false)
    private Persoon geldigVoorPersoon;

    // bi-directional many-to-one association to Relatie
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "relatie")
    private Relatie relatie;

    @Column(name = "srt", nullable = false)
    private Integer soortMultiRealiteitRegelId;

    public MultiRealiteitRegel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public HistorieStatus getMultiRealiteitRegelStatusHistorie() {
        return multiRealiteitRegelStatusHistorie;
    }

    public void setMultiRealiteitRegelStatusHistorie(final HistorieStatus multiRealiteitRegelStatusHistorie) {
        this.multiRealiteitRegelStatusHistorie = multiRealiteitRegelStatusHistorie;
    }

    public Set<MultiRealiteitRegelHistorie> getHisMultirealiteitregels() {
        return hisMultirealiteitregels;
    }

    /**
     * @param hisMultirealiteitregel
     */
    public void addHisMultirealiteitregel(final MultiRealiteitRegelHistorie hisMultirealiteitregel) {
        hisMultirealiteitregel.setMultirealiteitregel(this);
        hisMultirealiteitregels.add(hisMultirealiteitregel);
    }

    public Betrokkenheid getBetrokkenheid() {
        return betrokkenheid;
    }

    public void setBetrokkenheid(final Betrokkenheid betrokkenheid) {
        this.betrokkenheid = betrokkenheid;
    }

    public Persoon getMultiRealiteitPersoon() {
        return multiRealiteitPersoon;
    }

    public void setMultiRealiteitPersoon(final Persoon multiRealiteitPersoon) {
        this.multiRealiteitPersoon = multiRealiteitPersoon;
    }

    public Persoon getPersoon() {
        return persoon;
    }

    public void setPersoon(final Persoon persoon) {
        this.persoon = persoon;
    }

    public Persoon getGeldigVoorPersoon() {
        return geldigVoorPersoon;
    }

    public void setGeldigVoorPersoon(final Persoon geldigVoorPersoon) {
        this.geldigVoorPersoon = geldigVoorPersoon;
    }

    public Relatie getRelatie() {
        return relatie;
    }

    public void setRelatie(final Relatie relatie) {
        this.relatie = relatie;
    }

    /**
     * @return
     */
    public SoortMultiRealiteitRegel getSoortMultiRealiteitRegel() {
        return SoortMultiRealiteitRegel.parseId(soortMultiRealiteitRegelId);
    }

    /**
     * @param soortMultiRealiteitRegel
     */
    public void setSoortMultiRealiteitRegel(final SoortMultiRealiteitRegel soortMultiRealiteitRegel) {
        if (soortMultiRealiteitRegel == null) {
            soortMultiRealiteitRegelId = null;
        } else {
            soortMultiRealiteitRegelId = soortMultiRealiteitRegel.getId();
        }
    }

    /**
     * Maakt een MultiRealiteitRegel die de binaire relatie ontkent in de richting van de binaire relatie, dus van
     * ikBetrokkenheid naar andereBetrokkenheid. Wanneer de persoon waarvoor de MultiRealiteitRegel geldig moet zijn een
     * Niet-Ingeschrevene is, dan wordt geen MultiRealiteitRegel aangemaakt.
     * 
     * @param binaireRelatie
     *            de binaire relatie die moet worden ontkend
     */
    public static void maakMultiRealiteitRegel(final BinaireRelatie binaireRelatie) {
        if (binaireRelatie == null) {
            throw new NullPointerException();
        }
        if (binaireRelatie.getIkBetrokkenheid().getPersoon() == null
                || binaireRelatie.getIkBetrokkenheid().getPersoon().getSoortPersoon() == SoortPersoon.NIET_INGESCHREVENE) {
            return;
        }
        final MultiRealiteitRegel multiRealiteitRegel = new MultiRealiteitRegel();
        final MultiRealiteitRegelHistorie multiRealiteitRegelHistorie = new MultiRealiteitRegelHistorie();
        multiRealiteitRegel.addHisMultirealiteitregel(multiRealiteitRegelHistorie);

        switch (binaireRelatie.getRelatie().getSoortRelatie()) {
            case HUWELIJK:
            case GEREGISTREERD_PARTNERSCHAP:
                multiRealiteitRegel.setSoortMultiRealiteitRegel(SoortMultiRealiteitRegel.RELATIE);
                binaireRelatie.getRelatie().addMultiRealiteitRegel(multiRealiteitRegel);
                binaireRelatie.getIkBetrokkenheid().getPersoon()
                        .addMultiRealiteitRegelGeldigVoorPersoon(multiRealiteitRegel);
                final List<RelatieHistorie> relatieHistorieList =
                        new ArrayList<RelatieHistorie>(binaireRelatie.getRelatie().getRelatieHistorieSet());
                Collections.sort(relatieHistorieList, FormeleHistorie.COMPARATOR);
                final RelatieHistorie oudsteRelatieHistorie = relatieHistorieList.get(0);
                final RelatieHistorie nieuwsteRelatieHistorie =
                        relatieHistorieList.get(relatieHistorieList.size() - 1);

                multiRealiteitRegelHistorie.setDatumTijdRegistratie(oudsteRelatieHistorie.getDatumTijdRegistratie());
                multiRealiteitRegelHistorie.setDatumTijdVerval(nieuwsteRelatieHistorie.getDatumTijdVerval());
                multiRealiteitRegelHistorie.setActieInhoud(oudsteRelatieHistorie.getActieInhoud());
                multiRealiteitRegelHistorie.setActieVerval(nieuwsteRelatieHistorie.getActieVerval());
                break;
            case FAMILIERECHTELIJKE_BETREKKING:
                multiRealiteitRegel.setSoortMultiRealiteitRegel(SoortMultiRealiteitRegel.BETROKKENHEID);
                final List<BetrokkenheidOuderHistorie> ouderHistorieList =
                        new ArrayList<BetrokkenheidOuderHistorie>();
                switch (binaireRelatie.getIkBetrokkenheid().getSoortBetrokkenheid()) {
                    case OUDER:
                        // ontkenning vanuit ouder (een ouder ontkent zijn eigen betrokkenheid)
                        binaireRelatie.getIkBetrokkenheid().addMultiRealiteitRegel(multiRealiteitRegel);
                        binaireRelatie.getIkBetrokkenheid().getPersoon()
                                .addMultiRealiteitRegelGeldigVoorPersoon(multiRealiteitRegel);
                        ouderHistorieList.addAll(binaireRelatie.getIkBetrokkenheid()
                                .getBetrokkenheidOuderHistorieSet());
                        break;
                    case KIND:
                        // ontkenning vanuit kind (het kind ontkent de betrokkenheid van de ouder)
                        binaireRelatie.getAndereBetrokkenheid().addMultiRealiteitRegel(multiRealiteitRegel);
                        binaireRelatie.getIkBetrokkenheid().getPersoon()
                                .addMultiRealiteitRegelGeldigVoorPersoon(multiRealiteitRegel);
                        ouderHistorieList.addAll(binaireRelatie.getAndereBetrokkenheid()
                                .getBetrokkenheidOuderHistorieSet());
                        break;
                    default:
                        throw new IllegalStateException(String.format(
                                "Niet toegestane SoortBetrokkenheid(%s) voor SoortRelatie(%s)", binaireRelatie
                                        .getIkBetrokkenheid().getSoortBetrokkenheid(), binaireRelatie.getRelatie()
                                        .getSoortRelatie()));
                }
                if (!ouderHistorieList.isEmpty()) {
                    Collections.sort(ouderHistorieList, FormeleHistorie.COMPARATOR);
                    final BetrokkenheidOuderHistorie oudsteBetrokkenheidOuderHistorie = ouderHistorieList.get(0);
                    final BetrokkenheidOuderHistorie nieuwsteBetrokkenheidOuderHistorie =
                            ouderHistorieList.get(ouderHistorieList.size() - 1);

                    multiRealiteitRegelHistorie.setDatumTijdRegistratie(oudsteBetrokkenheidOuderHistorie
                            .getDatumTijdRegistratie());
                    multiRealiteitRegelHistorie.setDatumTijdVerval(nieuwsteBetrokkenheidOuderHistorie
                            .getDatumTijdVerval());
                    multiRealiteitRegelHistorie.setActieInhoud(oudsteBetrokkenheidOuderHistorie.getActieInhoud());
                    multiRealiteitRegelHistorie.setActieVerval(nieuwsteBetrokkenheidOuderHistorie.getActieVerval());
                }
                break;
            default:
                throw new IllegalStateException("Onverwachte soort relatie: "
                        + binaireRelatie.getRelatie().getSoortRelatie());
        }
        multiRealiteitRegel.setMultiRealiteitRegelStatusHistorie(HistorieStatus
                .bepaalHistorieStatusVoorBrp(multiRealiteitRegel.getHisMultirealiteitregels()));
    }
}

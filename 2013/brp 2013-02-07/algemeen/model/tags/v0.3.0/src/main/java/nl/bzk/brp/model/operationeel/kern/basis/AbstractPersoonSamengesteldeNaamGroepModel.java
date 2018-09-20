/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern.basis;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Geslachtsnaam;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNee;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Scheidingsteken;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Voornamen;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Voorvoegsel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AdellijkeTitel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Predikaat;
import nl.bzk.brp.model.logisch.kern.PersoonSamengesteldeNaamGroep;
import nl.bzk.brp.model.logisch.kern.basis.PersoonSamengesteldeNaamGroepBasis;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 * De naam zoals die ontstaat door samenvoegen van alle exemplaren van Voornaam en van Geslachtsnaamcomponent van een
 * Persoon.
 *
 * De Samengestelde naam is vrijwel altijd via een algoritme af te leiden uit de exemplaren van Voornaam en
 * Geslachtsnaamcomponent van een Persoon. In uitzonderingssituaties is dat niet mogelijk.
 *
 * Verplicht aanwezig bij persoon
 *
 * Historie: beide vormen van historie, aangezien de samengestelde naam ook kan wijzigen ZONDER dat er sprake is van
 * terugwerkende kracht (met andere woorden: 'vanaf vandaag heet ik...' ipv 'en deze moet met terugwerkende kracht
 * gelden vanaf de geboorte').
 * RvdP 9 jan 2012
 *
 *
 *
 */
@MappedSuperclass
public abstract class AbstractPersoonSamengesteldeNaamGroepModel implements PersoonSamengesteldeNaamGroepBasis {

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "IndAlgoritmischAfgeleid"))
    @JsonProperty
    private JaNee           indicatieAlgoritmischAfgeleid;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "IndNreeks"))
    @JsonProperty
    private JaNee           indicatieNamenreeks;

    @ManyToOne
    @JoinColumn(name = "Predikaat")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Predikaat       predikaat;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Voornamen"))
    @JsonProperty
    private Voornamen       voornamen;

    @ManyToOne
    @JoinColumn(name = "AdellijkeTitel")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private AdellijkeTitel  adellijkeTitel;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Voorvoegsel"))
    @JsonProperty
    private Voorvoegsel     voorvoegsel;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Scheidingsteken"))
    @JsonProperty
    private Scheidingsteken scheidingsteken;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Geslnaam"))
    @JsonProperty
    private Geslachtsnaam   geslachtsnaam;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected AbstractPersoonSamengesteldeNaamGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     * CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param indicatieAlgoritmischAfgeleid indicatieAlgoritmischAfgeleid van Samengestelde naam.
     * @param indicatieNamenreeks indicatieNamenreeks van Samengestelde naam.
     * @param predikaat predikaat van Samengestelde naam.
     * @param voornamen voornamen van Samengestelde naam.
     * @param adellijkeTitel adellijkeTitel van Samengestelde naam.
     * @param voorvoegsel voorvoegsel van Samengestelde naam.
     * @param scheidingsteken scheidingsteken van Samengestelde naam.
     * @param geslachtsnaam geslachtsnaam van Samengestelde naam.
     */
    public AbstractPersoonSamengesteldeNaamGroepModel(final JaNee indicatieAlgoritmischAfgeleid,
            final JaNee indicatieNamenreeks, final Predikaat predikaat, final Voornamen voornamen,
            final AdellijkeTitel adellijkeTitel, final Voorvoegsel voorvoegsel, final Scheidingsteken scheidingsteken,
            final Geslachtsnaam geslachtsnaam)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        this.indicatieAlgoritmischAfgeleid = indicatieAlgoritmischAfgeleid;
        this.indicatieNamenreeks = indicatieNamenreeks;
        this.predikaat = predikaat;
        this.voornamen = voornamen;
        this.adellijkeTitel = adellijkeTitel;
        this.voorvoegsel = voorvoegsel;
        this.scheidingsteken = scheidingsteken;
        this.geslachtsnaam = geslachtsnaam;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonSamengesteldeNaamGroep te kopieren groep.
     */
    public AbstractPersoonSamengesteldeNaamGroepModel(final PersoonSamengesteldeNaamGroep persoonSamengesteldeNaamGroep)
    {
        this.indicatieAlgoritmischAfgeleid = persoonSamengesteldeNaamGroep.getIndicatieAlgoritmischAfgeleid();
        this.indicatieNamenreeks = persoonSamengesteldeNaamGroep.getIndicatieNamenreeks();
        this.predikaat = persoonSamengesteldeNaamGroep.getPredikaat();
        this.voornamen = persoonSamengesteldeNaamGroep.getVoornamen();
        this.adellijkeTitel = persoonSamengesteldeNaamGroep.getAdellijkeTitel();
        this.voorvoegsel = persoonSamengesteldeNaamGroep.getVoorvoegsel();
        this.scheidingsteken = persoonSamengesteldeNaamGroep.getScheidingsteken();
        this.geslachtsnaam = persoonSamengesteldeNaamGroep.getGeslachtsnaam();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JaNee getIndicatieAlgoritmischAfgeleid() {
        return indicatieAlgoritmischAfgeleid;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JaNee getIndicatieNamenreeks() {
        return indicatieNamenreeks;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Predikaat getPredikaat() {
        return predikaat;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Voornamen getVoornamen() {
        return voornamen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AdellijkeTitel getAdellijkeTitel() {
        return adellijkeTitel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Voorvoegsel getVoorvoegsel() {
        return voorvoegsel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Scheidingsteken getScheidingsteken() {
        return scheidingsteken;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Geslachtsnaam getGeslachtsnaam() {
        return geslachtsnaam;
    }

}

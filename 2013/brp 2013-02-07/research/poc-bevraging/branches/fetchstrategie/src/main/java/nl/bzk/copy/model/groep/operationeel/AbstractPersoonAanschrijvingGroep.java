/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.groep.operationeel;

import javax.persistence.*;

import nl.bzk.copy.model.attribuuttype.*;
import nl.bzk.copy.model.basis.AbstractGroep;
import nl.bzk.copy.model.groep.logisch.basis.PersoonAanschrijvingGroepBasis;
import nl.bzk.copy.model.objecttype.operationeel.statisch.AdellijkeTitel;
import nl.bzk.copy.model.objecttype.operationeel.statisch.Predikaat;
import nl.bzk.copy.model.objecttype.operationeel.statisch.WijzeGebruikGeslachtsnaam;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;


/**
 * .
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonAanschrijvingGroep extends AbstractGroep
        implements PersoonAanschrijvingGroepBasis
{

    @Column(name = "gebrgeslnaamegp")
    @Enumerated
    private WijzeGebruikGeslachtsnaam gebruikGeslachtsnaam;

    @Column(name = "indaanschrmetadellijketitels")
    @Type(type = "JaNee")
    private JaNee indAanschrijvenMetAdellijkeTitel;

    @Column(name = "indaanschralgoritmischafgele")
    @Type(type = "JaNee")
    private JaNee indAanschrijvingAlgorthmischAfgeleid;

    @ManyToOne
    @JoinColumn(name = "predikaataanschr")
    @Fetch(FetchMode.JOIN)
    private Predikaat predikaat;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "voornamenaanschr"))
    private Voornaam voornamen;

    @AttributeOverride(name = "waarde", column = @Column(name = "voorvoegselaanschr"))
    private Voorvoegsel voorvoegsel;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "scheidingstekenaanschr"))
    private Scheidingsteken scheidingsteken;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "geslnaamaanschr"))
    private Geslachtsnaamcomponent geslachtsnaam;

    @ManyToOne
    @JoinColumn(name = "adellijketitelaanschr")
    @Fetch(FetchMode.JOIN)
    private AdellijkeTitel adellijkeTitel;

    /**
     * Default constructor tbv hibernate.
     */
    protected AbstractPersoonAanschrijvingGroep() {
    }

    /**
     * .
     *
     * @param persoonAanschrijvingGroepBasis PersoonAanschrijvingGroepBasis
     */
    protected AbstractPersoonAanschrijvingGroep(final PersoonAanschrijvingGroepBasis persoonAanschrijvingGroepBasis) {
        gebruikGeslachtsnaam = persoonAanschrijvingGroepBasis.getGebruikGeslachtsnaam();
        indAanschrijvenMetAdellijkeTitel = persoonAanschrijvingGroepBasis.getIndAanschrijvenMetAdellijkeTitel();
        indAanschrijvingAlgorthmischAfgeleid = persoonAanschrijvingGroepBasis.getIndAanschrijvingAlgorthmischAfgeleid();
        predikaat = persoonAanschrijvingGroepBasis.getPredikaat();
        voornamen = persoonAanschrijvingGroepBasis.getVoornamen();
        voorvoegsel = persoonAanschrijvingGroepBasis.getVoorvoegsel();
        scheidingsteken = persoonAanschrijvingGroepBasis.getScheidingsteken();
        geslachtsnaam = persoonAanschrijvingGroepBasis.getGeslachtsnaam();
        adellijkeTitel = persoonAanschrijvingGroepBasis.getAdellijkeTitel();
    }

    @Override
    public WijzeGebruikGeslachtsnaam getGebruikGeslachtsnaam() {
        return gebruikGeslachtsnaam;
    }

    @Override
    public JaNee getIndAanschrijvenMetAdellijkeTitel() {
        return indAanschrijvenMetAdellijkeTitel;
    }

    @Override
    public JaNee getIndAanschrijvingAlgorthmischAfgeleid() {
        return indAanschrijvingAlgorthmischAfgeleid;
    }

    @Override
    public Predikaat getPredikaat() {
        return predikaat;
    }

    @Override
    public Voornaam getVoornamen() {
        return voornamen;
    }

    @Override
    public Voorvoegsel getVoorvoegsel() {
        return voorvoegsel;
    }

    @Override
    public Scheidingsteken getScheidingsteken() {
        return scheidingsteken;
    }

    @Override
    public Geslachtsnaamcomponent getGeslachtsnaam() {
        return geslachtsnaam;
    }

    @Override
    public AdellijkeTitel getAdellijkeTitel() {
        return adellijkeTitel;
    }

    public void setGebruikGeslachtsnaam(final WijzeGebruikGeslachtsnaam gebruikGeslachtsnaam) {
        this.gebruikGeslachtsnaam = gebruikGeslachtsnaam;
    }

    public void setIndAanschrijvenMetAdellijkeTitel(final JaNee indAanschrijvenMetAdellijkeTitel) {
        this.indAanschrijvenMetAdellijkeTitel = indAanschrijvenMetAdellijkeTitel;
    }

    public void setIndAanschrijvingAlgorthmischAfgeleid(final JaNee indAanschrijvingAlgorthmischAfgeleid) {
        this.indAanschrijvingAlgorthmischAfgeleid = indAanschrijvingAlgorthmischAfgeleid;
    }

    public void setPredikaat(final Predikaat predikaat) {
        this.predikaat = predikaat;
    }

    public void setVoornamen(final Voornaam voornamen) {
        this.voornamen = voornamen;
    }

    public void setVoorvoegsel(final Voorvoegsel voorvoegsel) {
        this.voorvoegsel = voorvoegsel;
    }

    public void setScheidingsteken(final Scheidingsteken scheidingsteken) {
        this.scheidingsteken = scheidingsteken;
    }

    public void setGeslachtsnaam(final Geslachtsnaamcomponent geslachtsnaam) {
        this.geslachtsnaam = geslachtsnaam;
    }

    public void setAdellijkeTitel(final AdellijkeTitel adellijkeTitel) {
        this.adellijkeTitel = adellijkeTitel;
    }
}

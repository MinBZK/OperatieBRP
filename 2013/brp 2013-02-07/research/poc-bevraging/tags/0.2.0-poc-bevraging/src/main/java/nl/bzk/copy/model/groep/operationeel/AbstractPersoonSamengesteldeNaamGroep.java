/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.groep.operationeel;

import javax.persistence.*;

import nl.bzk.copy.model.attribuuttype.*;
import nl.bzk.copy.model.basis.AbstractGroep;
import nl.bzk.copy.model.groep.logisch.basis.PersoonSamengesteldeNaamGroepBasis;
import nl.bzk.copy.model.objecttype.operationeel.statisch.AdellijkeTitel;
import nl.bzk.copy.model.objecttype.operationeel.statisch.Predikaat;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;


/**
 * .
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonSamengesteldeNaamGroep extends AbstractGroep implements
        PersoonSamengesteldeNaamGroepBasis
{

    @ManyToOne
    @JoinColumn(name = "predikaat")
    @Fetch(FetchMode.JOIN)
    private Predikaat predikaat;

    @ManyToOne
    @JoinColumn(name = "adellijkeTitel")
    @Fetch(FetchMode.JOIN)
    private AdellijkeTitel adellijkeTitel;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "voornamen"))
    private Voornaam voornamen;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "voorvoegsel"))
    private Voorvoegsel voorvoegsel;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "scheidingsteken"))
    private Scheidingsteken scheidingsteken;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "geslnaam"))
    private Geslachtsnaamcomponent geslachtsnaam;

    @Column(name = "indnreeksalsgeslnaam")
    @Type(type = "JaNee")
    private JaNee indNamenreeksAlsGeslachtsNaam;

    @Column(name = "indalgoritmischafgeleid")
    @Type(type = "JaNee")
    private JaNee indAlgorithmischAfgeleid;

    /**
     * Default constructor tbv hibernate.
     */
    protected AbstractPersoonSamengesteldeNaamGroep() {

    }

    /**
     * .
     *
     * @param persoonSamengesteldeNaamGroepBasis
     *         PersoonSamengesteldeNaamGroepBasis
     */
    protected AbstractPersoonSamengesteldeNaamGroep(
            final PersoonSamengesteldeNaamGroepBasis persoonSamengesteldeNaamGroepBasis)
    {
        predikaat = persoonSamengesteldeNaamGroepBasis.getPredikaat();
        adellijkeTitel = persoonSamengesteldeNaamGroepBasis.getAdellijkeTitel();
        voornamen = persoonSamengesteldeNaamGroepBasis.getVoornamen();
        voorvoegsel = persoonSamengesteldeNaamGroepBasis.getVoorvoegsel();
        scheidingsteken = persoonSamengesteldeNaamGroepBasis.getScheidingsteken();
        geslachtsnaam = persoonSamengesteldeNaamGroepBasis.getGeslachtsnaam();
        indNamenreeksAlsGeslachtsNaam = persoonSamengesteldeNaamGroepBasis.getIndNamenreeksAlsGeslachtsNaam();
        indAlgorithmischAfgeleid = persoonSamengesteldeNaamGroepBasis.getIndAlgorithmischAfgeleid();
    }

    @Override
    public Predikaat getPredikaat() {
        return predikaat;
    }

    @Override
    public AdellijkeTitel getAdellijkeTitel() {
        return adellijkeTitel;
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
    public JaNee getIndNamenreeksAlsGeslachtsNaam() {
        return indNamenreeksAlsGeslachtsNaam;
    }

    @Override
    public JaNee getIndAlgorithmischAfgeleid() {
        return indAlgorithmischAfgeleid;
    }


    public void setPredikaat(final Predikaat predikaat) {
        this.predikaat = predikaat;
    }


    public void setAdellijkeTitel(final AdellijkeTitel adellijkeTitel) {
        this.adellijkeTitel = adellijkeTitel;
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


    public void setIndNamenreeksAlsGeslachtsNaam(final JaNee indNamenreeksAlsGeslachtsNaam) {
        this.indNamenreeksAlsGeslachtsNaam = indNamenreeksAlsGeslachtsNaam;
    }


    public void setIndAlgorithmischAfgeleid(final JaNee indAlgorithmischAfgeleid) {
        this.indAlgorithmischAfgeleid = indAlgorithmischAfgeleid;
    }
}

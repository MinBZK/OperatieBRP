/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.groep.operationeel;

import javax.persistence.*;

import nl.bzk.copy.model.attribuuttype.Geslachtsnaamcomponent;
import nl.bzk.copy.model.attribuuttype.Scheidingsteken;
import nl.bzk.copy.model.attribuuttype.Voorvoegsel;
import nl.bzk.copy.model.basis.AbstractGroep;
import nl.bzk.copy.model.groep.logisch.basis.PersoonGeslachtsnaamcomponentStandaardGroepBasis;
import nl.bzk.copy.model.objecttype.operationeel.statisch.AdellijkeTitel;
import nl.bzk.copy.model.objecttype.operationeel.statisch.Predikaat;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 * Implementatie standaard groep object type Persoon Geslachtsnaam component.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonGeslachtsnaamcomponentStandaardGroep extends AbstractGroep implements
        PersoonGeslachtsnaamcomponentStandaardGroepBasis
{

    @ManyToOne
    @JoinColumn(name = "predikaat")
    @Fetch(FetchMode.JOIN)
    private Predikaat predikaat;

    @ManyToOne
    @JoinColumn(name = "adellijketitel")
    @Fetch(FetchMode.JOIN)
    private AdellijkeTitel adellijkeTitel;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "voorvoegsel"))
    private Voorvoegsel voorvoegsel;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "scheidingsteken"))
    private Scheidingsteken scheidingsteken;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "naam"))
    private Geslachtsnaamcomponent geslachtsnaamcomponent;

    /**
     * Default constructor tbv hibernate.
     */
    protected AbstractPersoonGeslachtsnaamcomponentStandaardGroep() {

    }

    /**
     * .
     *
     * @param persoonGeslachtsnaamcomponentStandaardGroepBasis
     *         PersoonGeslachtsnaamcomponentStandaardGroepBasis
     */
    protected AbstractPersoonGeslachtsnaamcomponentStandaardGroep(
            final PersoonGeslachtsnaamcomponentStandaardGroepBasis persoonGeslachtsnaamcomponentStandaardGroepBasis)
    {
        predikaat = persoonGeslachtsnaamcomponentStandaardGroepBasis.getPredikaat();
        adellijkeTitel = persoonGeslachtsnaamcomponentStandaardGroepBasis.getAdellijkeTitel();
        voorvoegsel = persoonGeslachtsnaamcomponentStandaardGroepBasis.getVoorvoegsel();
        scheidingsteken = persoonGeslachtsnaamcomponentStandaardGroepBasis.getScheidingsteken();
        geslachtsnaamcomponent = persoonGeslachtsnaamcomponentStandaardGroepBasis.getNaam();
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
    public Voorvoegsel getVoorvoegsel() {
        return voorvoegsel;
    }

    @Override
    public Scheidingsteken getScheidingsteken() {
        return scheidingsteken;
    }

    @Override
    public Geslachtsnaamcomponent getNaam() {
        return geslachtsnaamcomponent;
    }


    public Geslachtsnaamcomponent getGeslachtsnaamcomponent() {
        return geslachtsnaamcomponent;
    }


    public void setGeslachtsnaamcomponent(final Geslachtsnaamcomponent geslachtsnaamcomponent) {
        this.geslachtsnaamcomponent = geslachtsnaamcomponent;
    }


    public void setPredikaat(final Predikaat predikaat) {
        this.predikaat = predikaat;
    }


    public void setAdellijkeTitel(final AdellijkeTitel adellijkeTitel) {
        this.adellijkeTitel = adellijkeTitel;
    }


    public void setVoorvoegsel(final Voorvoegsel voorvoegsel) {
        this.voorvoegsel = voorvoegsel;
    }


    public void setScheidingsteken(final Scheidingsteken scheidingsteken) {
        this.scheidingsteken = scheidingsteken;
    }
}

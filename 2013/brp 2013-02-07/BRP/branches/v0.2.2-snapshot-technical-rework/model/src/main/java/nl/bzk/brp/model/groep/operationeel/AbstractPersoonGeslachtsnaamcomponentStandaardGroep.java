/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.operationeel;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.attribuuttype.Geslachtsnaamcomponent;
import nl.bzk.brp.model.attribuuttype.Scheidingsteken;
import nl.bzk.brp.model.attribuuttype.Voorvoegsel;
import nl.bzk.brp.model.basis.AbstractGroep;
import nl.bzk.brp.model.groep.logisch.basis.PersoonGeslachtsnaamcomponentStandaardGroepBasis;
import nl.bzk.brp.model.objecttype.operationeel.statisch.AdellijkeTitel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Predikaat;


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
    private Predikaat predikaat;

    @ManyToOne
    @JoinColumn(name = "adellijketitel")
    private AdellijkeTitel         adellijkeTitel;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "voorvoegsel"))
    private Voorvoegsel            voorvoegsel;

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
     * @param persoonGeslachtsnaamcomponentStandaardGroepBasis PersoonGeslachtsnaamcomponentStandaardGroepBasis
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
}

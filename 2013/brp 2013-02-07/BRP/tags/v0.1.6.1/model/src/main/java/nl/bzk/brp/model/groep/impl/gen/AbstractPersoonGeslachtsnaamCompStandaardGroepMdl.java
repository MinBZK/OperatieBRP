/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.impl.gen;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import nl.bzk.brp.model.attribuuttype.GeslachtsnaamComponent;
import nl.bzk.brp.model.attribuuttype.ScheidingsTeken;
import nl.bzk.brp.model.attribuuttype.Voorvoegsel;
import nl.bzk.brp.model.basis.AbstractGroep;
import nl.bzk.brp.model.groep.interfaces.gen.PersoonGeslachtsnaamCompStandaardGroepBasis;
import nl.bzk.brp.model.objecttype.statisch.AdellijkeTitel;
import nl.bzk.brp.model.objecttype.statisch.Predikaat;
import nl.bzk.brp.model.objecttype.statisch.StatusHistorie;


/**
 * Implementatie standaard groep object type Persoon Geslachtsnaam component.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonGeslachtsnaamCompStandaardGroepMdl extends AbstractGroep implements
        PersoonGeslachtsnaamCompStandaardGroepBasis
{

    @ManyToOne
    @JoinColumn(name = "predikaat")
    private Predikaat              predikaat;

    @ManyToOne
    @JoinColumn(name = "adellijketitel")
    private AdellijkeTitel         adellijkeTitel;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "voorvoegsel"))
    private Voorvoegsel            voorvoegsel;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "scheidingsteken"))
    private ScheidingsTeken        scheidingsteken;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "naam"))
    private GeslachtsnaamComponent geslachtsnaamComponent;

    @Transient
    private StatusHistorie         statusHistorie;

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
    public ScheidingsTeken getScheidingsteken() {
        return scheidingsteken;
    }

    @Override
    public GeslachtsnaamComponent getNaam() {
        return geslachtsnaamComponent;
    }

    @Override
    public StatusHistorie getStatusHistorie() {
        return statusHistorie;
    }
}

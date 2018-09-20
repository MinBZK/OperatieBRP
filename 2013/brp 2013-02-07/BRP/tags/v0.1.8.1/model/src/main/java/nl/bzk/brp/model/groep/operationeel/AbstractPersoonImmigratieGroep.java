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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.basis.AbstractGroep;
import nl.bzk.brp.model.groep.logisch.basis.PersoonImmigratieGroepBasis;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Land;


/**
 * Implementatie voor groep persoon immigratie.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonImmigratieGroep extends AbstractGroep implements
        PersoonImmigratieGroepBasis
{
    @ManyToOne
    @JoinColumn(name = "landvanwaargevestigd")
    private Land           landVanwaarGevestigd;

    @AttributeOverride(name = "waarde", column = @Column(name = "datvestiginginnederland"))
    private Datum        datumVestigingInNederland;

    /**
     * Copy constructor voor groep.
     *
     * @param groep De te kopieren groep.
     */
    protected AbstractPersoonImmigratieGroep(final PersoonImmigratieGroepBasis groep) {
        super(groep);
        landVanwaarGevestigd = groep.getLandVanwaarGevestigd();
        datumVestigingInNederland = groep.getDatumVestigingInNederland();
    }

    /**
     * Default constructor tbv hibernate.
     */
    protected AbstractPersoonImmigratieGroep() {
        super();
    }



    @Override
    public Land getLandVanwaarGevestigd() {
        return landVanwaarGevestigd;
    }

    @Override
    public Datum getDatumVestigingInNederland() {
        return datumVestigingInNederland;
    }
}

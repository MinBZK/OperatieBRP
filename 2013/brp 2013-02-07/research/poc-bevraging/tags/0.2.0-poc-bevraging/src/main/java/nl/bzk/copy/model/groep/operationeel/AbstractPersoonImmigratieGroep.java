/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.groep.operationeel;

import javax.persistence.*;

import nl.bzk.copy.model.attribuuttype.Datum;
import nl.bzk.copy.model.basis.AbstractGroep;
import nl.bzk.copy.model.groep.logisch.basis.PersoonImmigratieGroepBasis;
import nl.bzk.copy.model.objecttype.operationeel.statisch.Land;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


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
    @Fetch(FetchMode.JOIN)
    private Land landVanwaarGevestigd;

    @AttributeOverride(name = "waarde", column = @Column(name = "datvestiginginnederland"))
    private Datum datumVestigingInNederland;

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

    public void setLandVanwaarGevestigd(final Land landVanwaarGevestigd) {
        this.landVanwaarGevestigd = landVanwaarGevestigd;
    }

    public void setDatumVestigingInNederland(final Datum datumVestigingInNederland) {
        this.datumVestigingInNederland = datumVestigingInNederland;
    }
}

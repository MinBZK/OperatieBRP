/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.operationeel;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.basis.AbstractGroep;
import nl.bzk.brp.model.groep.logisch.basis.PersoonNationaliteitStandaardGroepBasis;
import nl.bzk.brp.model.objecttype.operationeel.statisch.RedenVerkrijgingNLNationaliteit;
import nl.bzk.brp.model.objecttype.operationeel.statisch.RedenVerliesNLNationaliteit;


/**
 * De standaard groep implementatie van object type persoonnationaliteit.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonNationaliteitStandaardGroep extends AbstractGroep implements
        PersoonNationaliteitStandaardGroepBasis
{

    @ManyToOne
    @JoinColumn(name = "RdnVerk")
    private RedenVerkrijgingNLNationaliteit redenVerkregenNlNationaliteit;

    @ManyToOne
    @JoinColumn(name = "RdnVerlies")
    private RedenVerliesNLNationaliteit     redenVerliesNlNationaliteit;

    /**
     * Default constructor tbv hibernate.
     */
    protected AbstractPersoonNationaliteitStandaardGroep() {

    }

    /**
     * .
     *
     * @param persoonNationaliteitStandaardGroepBasis PersoonNationaliteitStandaardGroepBasis
     */
    protected AbstractPersoonNationaliteitStandaardGroep(
            final PersoonNationaliteitStandaardGroepBasis persoonNationaliteitStandaardGroepBasis)
    {
        if (persoonNationaliteitStandaardGroepBasis != null) {
            redenVerkregenNlNationaliteit = persoonNationaliteitStandaardGroepBasis.getRedenVerkregenNlNationaliteit();
            redenVerliesNlNationaliteit = persoonNationaliteitStandaardGroepBasis.getRedenVerliesNlNationaliteit();
        }
    }

    @Override
    public RedenVerkrijgingNLNationaliteit getRedenVerkregenNlNationaliteit() {
        return redenVerkregenNlNationaliteit;
    }

    @Override
    public RedenVerliesNLNationaliteit getRedenVerliesNlNationaliteit() {
        return redenVerliesNlNationaliteit;
    }
}

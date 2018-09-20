/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.groep.operationeel;

import javax.persistence.*;

import nl.bzk.copy.model.basis.AbstractGroep;
import nl.bzk.copy.model.groep.logisch.basis.PersoonNationaliteitStandaardGroepBasis;
import nl.bzk.copy.model.objecttype.operationeel.statisch.RedenVerkrijgingNLNationaliteit;
import nl.bzk.copy.model.objecttype.operationeel.statisch.RedenVerliesNLNationaliteit;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


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
    @Fetch(FetchMode.JOIN)
    private RedenVerkrijgingNLNationaliteit redenVerkregenNlNationaliteit;

    @ManyToOne
    @JoinColumn(name = "RdnVerlies")
    @Fetch(FetchMode.JOIN)
    private RedenVerliesNLNationaliteit redenVerliesNlNationaliteit;

    /**
     * Default constructor tbv hibernate.
     */
    protected AbstractPersoonNationaliteitStandaardGroep() {

    }

    /**
     * .
     *
     * @param persoonNationaliteitStandaardGroepBasis
     *         PersoonNationaliteitStandaardGroepBasis
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

    public void setRedenVerkregenNlNationaliteit(final RedenVerkrijgingNLNationaliteit redenVerkregenNlNationaliteit) {
        this.redenVerkregenNlNationaliteit = redenVerkregenNlNationaliteit;
    }

    public void setRedenVerliesNlNationaliteit(final RedenVerliesNLNationaliteit redenVerliesNlNationaliteit) {
        this.redenVerliesNlNationaliteit = redenVerliesNlNationaliteit;
    }
}

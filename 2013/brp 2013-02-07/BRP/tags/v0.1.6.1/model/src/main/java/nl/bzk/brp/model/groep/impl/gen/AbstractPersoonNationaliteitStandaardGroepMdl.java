/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.impl.gen;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import nl.bzk.brp.model.basis.AbstractGroep;
import nl.bzk.brp.model.groep.interfaces.gen.PersoonNationaliteitStandaardGroepBasis;
import nl.bzk.brp.model.objecttype.statisch.RedenVerkrijgingNLNationaliteit;
import nl.bzk.brp.model.objecttype.statisch.RedenVerliesNLNationaliteit;
import nl.bzk.brp.model.objecttype.statisch.StatusHistorie;


/**
 * De standaard groep implementatie van object type persoonnationaliteit.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonNationaliteitStandaardGroepMdl extends AbstractGroep implements
        PersoonNationaliteitStandaardGroepBasis
{

    @ManyToOne
    @JoinColumn(name = "RdnVerk")
    private RedenVerkrijgingNLNationaliteit redenVerkregenNlNationaliteit;

    @ManyToOne
    @JoinColumn(name = "RdnVerlies")
    private RedenVerliesNLNationaliteit     redenVerliesNlNationaliteit;

    @Transient
    private StatusHistorie                  statusHistorie;

    @Override
    public RedenVerkrijgingNLNationaliteit getRedenVerkregenNlNationaliteit() {
        return redenVerkregenNlNationaliteit;
    }

    @Override
    public RedenVerliesNLNationaliteit getRedenVerliesNlNationaliteit() {
        return redenVerliesNlNationaliteit;
    }

    @Override
    public StatusHistorie getStatusHistorie() {
        return statusHistorie;
    }
}

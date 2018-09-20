/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Embeddable;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerkrijgingNLNationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerliesNLNationaliteit;
import nl.bzk.brp.model.logisch.kern.PersoonNationaliteitStandaardGroep;
import nl.bzk.brp.model.operationeel.kern.basis.AbstractPersoonNationaliteitStandaardGroepModel;


/**
 * Vorm van historie: beiden. Motivatie: een persoon kan een bepaalde Nationaliteit verkrijgen, dan wel verliezen. Naast
 * een formele historie ('wat stond geregistreerd') is dus ook materi�le historie denkbaar ('over welke periode
 * beschikte hij over de Nederlandse Nationaliteit'). We leggen beide vast, ��k omdat dit van oudsher gebeurde vanuit de
 * GBA.
 * RvdP 17 jan 2012.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.OperationeelModelGenerator.
 * Metaregister versie: 1.1.8.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2012-11-27 12:02:51.
 * Gegenereerd op: Tue Nov 27 14:55:36 CET 2012.
 */
@Embeddable
public class PersoonNationaliteitStandaardGroepModel extends AbstractPersoonNationaliteitStandaardGroepModel implements
        PersoonNationaliteitStandaardGroep
{

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected PersoonNationaliteitStandaardGroepModel() {
        super();
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param redenVerlies redenVerlies van Standaard.
     * @param redenVerkrijging redenVerkrijging van Standaard.
     */
    public PersoonNationaliteitStandaardGroepModel(final RedenVerliesNLNationaliteit redenVerlies,
            final RedenVerkrijgingNLNationaliteit redenVerkrijging)
    {
        super(redenVerkrijging, redenVerlies);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonNationaliteitStandaardGroep te kopieren groep.
     */
    public PersoonNationaliteitStandaardGroepModel(
            final PersoonNationaliteitStandaardGroep persoonNationaliteitStandaardGroep)
    {
        super(persoonNationaliteitStandaardGroep);
    }

}

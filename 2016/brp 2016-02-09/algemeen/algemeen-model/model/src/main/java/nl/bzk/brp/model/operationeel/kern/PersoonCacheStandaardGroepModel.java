/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Embeddable;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ByteaopslagAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ChecksumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VersienummerKleinAttribuut;
import nl.bzk.brp.model.logisch.kern.PersoonCacheStandaardGroep;


/**
 *
 *
 */
@Embeddable
public class PersoonCacheStandaardGroepModel extends AbstractPersoonCacheStandaardGroepModel implements
    PersoonCacheStandaardGroep
{

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     */
    public PersoonCacheStandaardGroepModel() {
        super();
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param versienummer versienummer van Standaard.
     * @param persoonHistorieVolledigChecksum
     *                     persoonHistorieVolledigChecksum van Standaard.
     * @param persoonHistorieVolledigGegevens
     *                     persoonHistorieVolledigGegevens van Standaard.
     * @param administratieveHandelingenChecksum
     *                     administratieveHandelingenChecksum van Standaard.
     * @param administratieveHandelingenGegevens
     *                     administratieveHandelingenGegevens van Standaard.
     */
    public PersoonCacheStandaardGroepModel(final VersienummerKleinAttribuut versienummer,
        final ChecksumAttribuut persoonHistorieVolledigChecksum,
        final ByteaopslagAttribuut persoonHistorieVolledigGegevens,
        final ChecksumAttribuut administratieveHandelingenChecksum,
        final ByteaopslagAttribuut administratieveHandelingenGegevens)
    {
        super(versienummer, persoonHistorieVolledigChecksum, persoonHistorieVolledigGegevens,
            administratieveHandelingenChecksum, administratieveHandelingenGegevens);
    }
}

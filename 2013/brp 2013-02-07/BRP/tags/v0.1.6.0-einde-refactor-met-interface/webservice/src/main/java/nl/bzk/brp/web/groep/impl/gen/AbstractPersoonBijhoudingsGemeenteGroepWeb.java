/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.groep.impl.gen;

import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.JaNee;
import nl.bzk.brp.model.groep.interfaces.gen.PersoonBijhoudingsGemeenteGroepBasis;
import nl.bzk.brp.model.objecttype.statisch.Partij;
import nl.bzk.brp.web.AbstractGroepWeb;

/**
 * Implementatie voor groep persoon bijhoudingsgemeente.
 */
public abstract class AbstractPersoonBijhoudingsGemeenteGroepWeb extends AbstractGroepWeb
        implements PersoonBijhoudingsGemeenteGroepBasis
{
    private Partij bijhoudingsGemeente;
    private JaNee indOnverwerktDocumentAanwezig;
    private Datum datumInschrijvingInGemeente;

    @Override
    public Partij getBijhoudingsGemeente() {
        return bijhoudingsGemeente;
    }

    @Override
    public JaNee getIndOnverwerktDocumentAanwezig() {
        return indOnverwerktDocumentAanwezig;
    }

    @Override
    public Datum getDatumInschrijvingInGemeente() {
        return datumInschrijvingInGemeente;
    }
}

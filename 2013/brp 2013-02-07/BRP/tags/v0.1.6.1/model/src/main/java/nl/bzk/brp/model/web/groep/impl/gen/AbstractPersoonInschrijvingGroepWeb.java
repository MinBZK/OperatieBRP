/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.web.groep.impl.gen;

import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.Versienummer;
import nl.bzk.brp.model.groep.interfaces.gen.PersoonInschrijvingGroepBasis;
import nl.bzk.brp.model.objecttype.interfaces.gen.PersoonBasis;
import nl.bzk.brp.model.web.AbstractGroepWeb;

/**
 * Implementatie voor groep persoon inschrijving.
 */
@SuppressWarnings("serial")
public abstract class AbstractPersoonInschrijvingGroepWeb extends AbstractGroepWeb
    implements PersoonInschrijvingGroepBasis
{

    private Versienummer versienummer;
    private PersoonBasis vorigePersoon;
    private PersoonBasis volgendePersoon;
    private Datum datumInschrijving;

    @Override
    public Versienummer getVersienummer() {
        return versienummer;
    }

    @Override
    public PersoonBasis getVorigePersoon() {
        return vorigePersoon;
    }

    @Override
    public PersoonBasis getVolgendePersoon() {
        return volgendePersoon;
    }

    @Override
    public Datum getDatumInschrijving() {
        return datumInschrijving;
    }
}

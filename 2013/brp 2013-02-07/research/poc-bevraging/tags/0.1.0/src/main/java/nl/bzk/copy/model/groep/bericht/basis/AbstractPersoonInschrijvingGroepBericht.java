/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.groep.bericht.basis;

import nl.bzk.copy.model.attribuuttype.Datum;
import nl.bzk.copy.model.attribuuttype.Versienummer;
import nl.bzk.copy.model.basis.AbstractGroepBericht;
import nl.bzk.copy.model.groep.logisch.basis.PersoonInschrijvingGroepBasis;
import nl.bzk.copy.model.objecttype.logisch.basis.PersoonBasis;

/**
 * Implementatie voor groep persoon inschrijving.
 */
@SuppressWarnings("serial")
public abstract class AbstractPersoonInschrijvingGroepBericht extends AbstractGroepBericht
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

    public void setDatumInschrijving(final Datum datumInschrijving) {
        this.datumInschrijving = datumInschrijving;
    }

    public void setVersienummer(final Versienummer versienummer) {
        this.versienummer = versienummer;
    }
}

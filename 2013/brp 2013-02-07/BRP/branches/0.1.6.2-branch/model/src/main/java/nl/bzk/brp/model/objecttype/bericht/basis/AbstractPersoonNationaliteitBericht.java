/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.bericht.basis;

import nl.bzk.brp.model.attribuuttype.NationaliteitCode;
import nl.bzk.brp.model.basis.AbstractObjectTypeBericht;
import nl.bzk.brp.model.groep.bericht.PersoonNationaliteitStandaardGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.logisch.basis.PersoonNationaliteitBasis;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Nationaliteit;

/**
 * Implementatie voor objecttype Persoon Nationaliteit.
 */
@SuppressWarnings("serial")
public abstract class AbstractPersoonNationaliteitBericht extends AbstractObjectTypeBericht
        implements PersoonNationaliteitBasis
{

    private PersoonBericht persoon;
    private Nationaliteit nationaliteit;
    private NationaliteitCode nationaliteitCode;
    private PersoonNationaliteitStandaardGroepBericht gegevens;

    @Override
    public PersoonBericht getPersoon() {
        return persoon;
    }

    @Override
    public Nationaliteit getNationaliteit() {
        return nationaliteit;
    }

    @Override
    public PersoonNationaliteitStandaardGroepBericht getGegevens() {
        return gegevens;
    }

    public void setPersoon(final PersoonBericht persoon) {
        this.persoon = persoon;
    }

    public void setNationaliteit(final Nationaliteit nationaliteit) {
        this.nationaliteit = nationaliteit;
    }

    public void setGegevens(final PersoonNationaliteitStandaardGroepBericht gegevens) {
        this.gegevens = gegevens;
    }

    public NationaliteitCode getNationaliteitCode() {
        return nationaliteitCode;
    }

    public void setNationaliteitCode(final NationaliteitCode nationaliteitCode) {
        this.nationaliteitCode = nationaliteitCode;
    }
}

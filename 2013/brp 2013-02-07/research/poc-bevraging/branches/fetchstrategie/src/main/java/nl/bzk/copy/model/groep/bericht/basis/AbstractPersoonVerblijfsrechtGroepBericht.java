/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.groep.bericht.basis;

import nl.bzk.copy.model.attribuuttype.Datum;
import nl.bzk.copy.model.basis.AbstractGroepBericht;
import nl.bzk.copy.model.groep.logisch.basis.PersoonVerblijfsrechtGroepBasis;
import nl.bzk.copy.model.objecttype.operationeel.statisch.Verblijfsrecht;


/**
 * .
 */
@SuppressWarnings("serial")
public abstract class AbstractPersoonVerblijfsrechtGroepBericht extends AbstractGroepBericht
        implements PersoonVerblijfsrechtGroepBasis
{
    private Verblijfsrecht verblijfsrecht;
    private Datum datumAanvangVerblijfsrecht;
    private Datum datumAanvangAaneensluitendVerblijfsrecht;
    private Datum datumVoorzienEindeVerblijfsrecht;

    @Override
    public Verblijfsrecht getVerblijfsrecht() {
        return verblijfsrecht;
    }

    @Override
    public Datum getDatumAanvangVerblijfsrecht() {
        return datumAanvangVerblijfsrecht;
    }

    @Override
    public Datum getDatumAanvangAaneensluitendVerblijfsrecht() {
        return datumAanvangAaneensluitendVerblijfsrecht;
    }

    @Override
    public Datum getDatumVoorzienEindeVerblijfsrecht() {
        return datumVoorzienEindeVerblijfsrecht;
    }

    public void setVerblijfsrecht(final Verblijfsrecht verblijfsrecht) {
        this.verblijfsrecht = verblijfsrecht;
    }

    public void setDatumAanvangVerblijfsrecht(final Datum datumAanvangVerblijfsrecht) {
        this.datumAanvangVerblijfsrecht = datumAanvangVerblijfsrecht;
    }

    public void setDatumAanvangAaneensluitendVerblijfsrecht(final Datum datumAanvangAaneensluitendVerblijfsrecht) {
        this.datumAanvangAaneensluitendVerblijfsrecht = datumAanvangAaneensluitendVerblijfsrecht;
    }

    public void setDatumVoorzienEindeVerblijfsrecht(final Datum datumVoorzienEindeVerblijfsrecht) {
        this.datumVoorzienEindeVerblijfsrecht = datumVoorzienEindeVerblijfsrecht;
    }
}

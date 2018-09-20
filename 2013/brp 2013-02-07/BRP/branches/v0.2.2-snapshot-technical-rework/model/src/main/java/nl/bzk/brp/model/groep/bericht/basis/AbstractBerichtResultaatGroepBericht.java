/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.bericht.basis;

import nl.bzk.brp.model.attribuuttype.DatumTijd;
import nl.bzk.brp.model.basis.AbstractGroepBericht;
import nl.bzk.brp.model.groep.logisch.basis.BerichtResultaatGroepBasis;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Bijhoudingsresultaat;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortMelding;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Verwerkingsresultaat;

/**
 *
 */
public abstract class AbstractBerichtResultaatGroepBericht extends AbstractGroepBericht
        implements BerichtResultaatGroepBasis
{
    private Verwerkingsresultaat verwerkingsresultaat;

    private Bijhoudingsresultaat bijhoudingsresultaat;

    private SoortMelding hoogsteMeldingNiveau;

    private DatumTijd tijdstipRegistratie;

    public Verwerkingsresultaat getVerwerkingsresultaat() {
        return verwerkingsresultaat;
    }

    public Bijhoudingsresultaat getBijhoudingsresultaat() {
        return bijhoudingsresultaat;
    }

    public SoortMelding getHoogsteMeldingNiveau() {
        return hoogsteMeldingNiveau;
    }

    public DatumTijd getTijdstipRegistratie() {
        return tijdstipRegistratie;
    }

    public void setTijdstipRegistratie(final DatumTijd tijdstipRegistratie) {
        this.tijdstipRegistratie = tijdstipRegistratie;
    }

    public void setVerwerkingsresultaat(final Verwerkingsresultaat verwerkingsresultaat) {
        this.verwerkingsresultaat = verwerkingsresultaat;
    }

    public void setBijhoudingsresultaat(final Bijhoudingsresultaat bijhoudingsresultaat) {
        this.bijhoudingsresultaat = bijhoudingsresultaat;
    }

    public void setHoogsteMeldingNiveau(final SoortMelding hoogsteMeldingNiveau) {
        this.hoogsteMeldingNiveau = hoogsteMeldingNiveau;
    }
}

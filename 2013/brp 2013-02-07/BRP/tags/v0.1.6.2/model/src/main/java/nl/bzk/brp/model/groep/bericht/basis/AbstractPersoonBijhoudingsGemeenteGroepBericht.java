/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.bericht.basis;

import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.GemeenteCode;
import nl.bzk.brp.model.attribuuttype.JaNee;
import nl.bzk.brp.model.basis.AbstractGroepBericht;
import nl.bzk.brp.model.groep.logisch.basis.PersoonBijhoudingsGemeenteGroepBasis;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;

/**
 * Implementatie voor groep persoon bijhoudingsgemeente.
 */
@SuppressWarnings("serial")
public abstract class AbstractPersoonBijhoudingsGemeenteGroepBericht extends AbstractGroepBericht
        implements PersoonBijhoudingsGemeenteGroepBasis
{
    private Partij bijhoudingsGemeente;
    private GemeenteCode bijhoudingsGemeenteCode;
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

    public void setBijhoudingsGemeente(final Partij bijhoudingsGemeente) {
        this.bijhoudingsGemeente = bijhoudingsGemeente;
    }

    public void setIndOnverwerktDocumentAanwezig(final JaNee indOnverwerktDocumentAanwezig) {
        this.indOnverwerktDocumentAanwezig = indOnverwerktDocumentAanwezig;
    }

    public void setDatumInschrijvingInGemeente(final Datum datumInschrijvingInGemeente) {
        this.datumInschrijvingInGemeente = datumInschrijvingInGemeente;
    }

    public GemeenteCode getBijhoudingsGemeenteCode() {
        return bijhoudingsGemeenteCode;
    }
}

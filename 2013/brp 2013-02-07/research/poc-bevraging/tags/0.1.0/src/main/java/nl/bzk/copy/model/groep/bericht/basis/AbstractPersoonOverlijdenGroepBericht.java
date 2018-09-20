/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.groep.bericht.basis;

import nl.bzk.copy.model.attribuuttype.BuitenlandsePlaats;
import nl.bzk.copy.model.attribuuttype.BuitenlandseRegio;
import nl.bzk.copy.model.attribuuttype.Datum;
import nl.bzk.copy.model.attribuuttype.LocatieOmschrijving;
import nl.bzk.copy.model.basis.AbstractGroepBericht;
import nl.bzk.copy.model.groep.logisch.basis.PersoonOverlijdenGroepBasis;
import nl.bzk.copy.model.objecttype.operationeel.statisch.Land;
import nl.bzk.copy.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.copy.model.objecttype.operationeel.statisch.Plaats;


/**
 * .
 */
@SuppressWarnings("serial")
public abstract class AbstractPersoonOverlijdenGroepBericht extends AbstractGroepBericht
        implements PersoonOverlijdenGroepBasis
{

    @nl.bzk.copy.model.validatie.constraint.Datum
    private Datum datumOverlijden;
    private Partij overlijdenGemeente;
    private Plaats woonplaatsOverlijden;
    private BuitenlandsePlaats buitenlandsePlaatsOverlijden;
    private BuitenlandseRegio buitenlandseRegioOverlijden;
    private Land landOverlijden;
    private LocatieOmschrijving omschrijvingLocatieOverlijden;

    @Override
    public Datum getDatumOverlijden() {
        return datumOverlijden;
    }

    @Override
    public Partij getOverlijdenGemeente() {
        return overlijdenGemeente;
    }

    @Override
    public Plaats getWoonplaatsOverlijden() {
        return woonplaatsOverlijden;
    }

    @Override
    public BuitenlandsePlaats getBuitenlandsePlaatsOverlijden() {
        return buitenlandsePlaatsOverlijden;
    }

    @Override
    public BuitenlandseRegio getBuitenlandseRegioOverlijden() {
        return buitenlandseRegioOverlijden;
    }

    @Override
    public Land getLandOverlijden() {
        return landOverlijden;
    }

    @Override
    public LocatieOmschrijving getOmschrijvingLocatieOverlijden() {
        return omschrijvingLocatieOverlijden;
    }

    public void setDatumOverlijden(final Datum datumOverlijden) {
        this.datumOverlijden = datumOverlijden;
    }

    public void setOverlijdenGemeente(final Partij overlijdenGemeente) {
        this.overlijdenGemeente = overlijdenGemeente;
    }

    public void setWoonplaatsOverlijden(final Plaats woonplaatsOverlijden) {
        this.woonplaatsOverlijden = woonplaatsOverlijden;
    }

    public void setBuitenlandsePlaatsOverlijden(final BuitenlandsePlaats buitenlandsePlaatsOverlijden) {
        this.buitenlandsePlaatsOverlijden = buitenlandsePlaatsOverlijden;
    }

    public void setBuitenlandseRegioOverlijden(final BuitenlandseRegio buitenlandseRegioOverlijden) {
        this.buitenlandseRegioOverlijden = buitenlandseRegioOverlijden;
    }

    public void setLandOverlijden(final Land landOverlijden) {
        this.landOverlijden = landOverlijden;
    }

    public void setOmschrijvingLocatieOverlijden(final LocatieOmschrijving omschrijvingLocatieOverlijden) {
        this.omschrijvingLocatieOverlijden = omschrijvingLocatieOverlijden;
    }


}

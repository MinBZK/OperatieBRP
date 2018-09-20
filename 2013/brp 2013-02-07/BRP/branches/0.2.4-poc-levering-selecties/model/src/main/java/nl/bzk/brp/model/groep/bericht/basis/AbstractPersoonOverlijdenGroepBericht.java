/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.bericht.basis;

import nl.bzk.brp.model.attribuuttype.BuitenlandsePlaats;
import nl.bzk.brp.model.attribuuttype.BuitenlandseRegio;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.Gemeentecode;
import nl.bzk.brp.model.attribuuttype.Landcode;
import nl.bzk.brp.model.attribuuttype.LocatieOmschrijving;
import nl.bzk.brp.model.attribuuttype.PlaatsCode;
import nl.bzk.brp.model.basis.AbstractGroepBericht;
import nl.bzk.brp.model.groep.logisch.basis.PersoonOverlijdenGroepBasis;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Land;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Plaats;


/**
 * .
 *
 */
@SuppressWarnings("serial")
public abstract class AbstractPersoonOverlijdenGroepBericht extends AbstractGroepBericht implements PersoonOverlijdenGroepBasis {

    @nl.bzk.brp.model.validatie.constraint.Datum
    private Datum               datumOverlijden;
    private Partij              overlijdenGemeente;
    private Gemeentecode        gemeenteOverlijdenCode;
    private Plaats              woonplaatsOverlijden;
    private PlaatsCode          woonplaatsOverlijdenCode;
    private BuitenlandsePlaats  buitenlandsePlaatsOverlijden;
    private BuitenlandseRegio   buitenlandseRegioOverlijden;
    private Land                landOverlijden;
    private Landcode            landOverlijdenCode;
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


    public Gemeentecode getGemeenteOverlijdenCode() {
        return gemeenteOverlijdenCode;
    }


    public void setGemeenteOverlijdenCode(final Gemeentecode gemeenteOverlijdenCode) {
        this.gemeenteOverlijdenCode = gemeenteOverlijdenCode;
    }


    public PlaatsCode getWoonplaatsOverlijdenCode() {
        return woonplaatsOverlijdenCode;
    }


    public void setWoonplaatsOverlijdenCode(final PlaatsCode woonplaatsOverlijdenCode) {
        this.woonplaatsOverlijdenCode = woonplaatsOverlijdenCode;
    }


    public Landcode getLandOverlijdenCode() {
        return landOverlijdenCode;
    }


    public void setLandOverlijdenCode(final Landcode landOverlijdenCode) {
        this.landOverlijdenCode = landOverlijdenCode;
    }
}

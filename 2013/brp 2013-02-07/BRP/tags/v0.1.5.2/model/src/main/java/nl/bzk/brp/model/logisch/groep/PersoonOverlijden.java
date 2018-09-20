/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.groep;

import nl.bzk.brp.model.gedeeld.Land;
import nl.bzk.brp.model.gedeeld.Partij;
import nl.bzk.brp.model.gedeeld.Plaats;
import nl.bzk.brp.model.logisch.AbstractIdentificerendeGroep;
import nl.bzk.brp.model.validatie.constraint.Datum;


/** Overlijden groep van persoon. */
public class PersoonOverlijden extends AbstractIdentificerendeGroep {

    @Datum
    private Integer datumOverlijden;

    private Partij gemeenteOverlijden;

    private Plaats woonplaatsOverlijden;

    private Land landOverlijden;

    private String buitenlandsePlaats;

    private String buitenlandseRegio;

    private String omschrijvingLocatie;

    public Integer getDatumOverlijden() {
        return datumOverlijden;
    }

    public void setDatumOverlijden(final Integer datumOverlijden) {
        this.datumOverlijden = datumOverlijden;
    }

    public Partij getGemeenteOverlijden() {
        return gemeenteOverlijden;
    }

    public void setGemeenteOverlijden(final Partij gemeenteOverlijden) {
        this.gemeenteOverlijden = gemeenteOverlijden;
    }

    public Plaats getWoonplaatsOverlijden() {
        return woonplaatsOverlijden;
    }

    public void setWoonplaatsOverlijden(final Plaats woonplaatsOverlijden) {
        this.woonplaatsOverlijden = woonplaatsOverlijden;
    }

    public Land getLandOverlijden() {
        return landOverlijden;
    }

    public void setLandOverlijden(final Land landOverlijden) {
        this.landOverlijden = landOverlijden;
    }

    public String getBuitenlandsePlaats() {
        return buitenlandsePlaats;
    }

    public void setBuitenlandsePlaats(final String buitenlandsePlaats) {
        this.buitenlandsePlaats = buitenlandsePlaats;
    }

    public String getBuitenlandseRegio() {
        return buitenlandseRegio;
    }

    public void setBuitenlandseRegio(final String buitenlandseRegio) {
        this.buitenlandseRegio = buitenlandseRegio;
    }

    public String getOmschrijvingLocatie() {
        return omschrijvingLocatie;
    }

    public void setOmschrijvingLocatie(final String omschrijvingLocatie) {
        this.omschrijvingLocatie = omschrijvingLocatie;
    }
}

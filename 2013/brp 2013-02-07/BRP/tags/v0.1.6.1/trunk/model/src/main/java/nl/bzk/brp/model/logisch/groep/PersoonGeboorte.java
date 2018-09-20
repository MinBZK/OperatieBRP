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


/** Geboorte groep van persoon. */
public class PersoonGeboorte extends AbstractIdentificerendeGroep {

    @Datum
    private Integer datumGeboorte;

    private Partij gemeenteGeboorte;

    private Plaats woonplaatsGeboorte;

    private Land landGeboorte;

    private String buitenlandsePlaats;

    private String buitenlandseRegio;

    private String omschrijvingLocatie;

    public Integer getDatumGeboorte() {
        return datumGeboorte;
    }

    public void setDatumGeboorte(final Integer datumGeboorte) {
        this.datumGeboorte = datumGeboorte;
    }

    public Partij getGemeenteGeboorte() {
        return gemeenteGeboorte;
    }

    public void setGemeenteGeboorte(final Partij gemeenteGeboorte) {
        this.gemeenteGeboorte = gemeenteGeboorte;
    }

    public Plaats getWoonplaatsGeboorte() {
        return woonplaatsGeboorte;
    }

    public void setWoonplaatsGeboorte(final Plaats woonplaatsGeboorte) {
        this.woonplaatsGeboorte = woonplaatsGeboorte;
    }

    public Land getLandGeboorte() {
        return landGeboorte;
    }

    public void setLandGeboorte(final Land landGeboorte) {
        this.landGeboorte = landGeboorte;
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

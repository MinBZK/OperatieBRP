/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.groep;

import java.util.Date;

import nl.bzk.brp.model.logisch.AbstractIdentificerendeGroep;
import nl.bzk.brp.model.validatie.constraint.Datum;

/** Groep voor afgeleide administratieve velden. */
public class PersoonAfgeleidAdministratief extends AbstractIdentificerendeGroep {

    @Datum(magDeelsOnbekendZijn = false)
    private Date    laatstGewijzigd;
    private Boolean indGegevensInOnderzoek;

    /**
     * Datum en tijdstip waarop de gegevens van de persoon voor het laatst zijn aangepast.
     *
     * @return datum en tijdstip waarop de gegevens van de persoon voor het laatst zijn aangepast.
     */
    public Date getLaatstGewijzigd() {
        if (laatstGewijzigd == null) {
            return null;
        } else {
            return (Date) laatstGewijzigd.clone();
        }
    }

    /**
     * Zet de datum en tijdstip waarop de gegevens van de persoon voor het laatst zijn aangepast.
     *
     * @param laatstGewijzigd datum en tijdstip waarop de gegevens van de persoon voor het laatst zijn aangepast.
     */
    public void setLaatstGewijzigd(final Date laatstGewijzigd) {
        if (laatstGewijzigd == null) {
            this.laatstGewijzigd = null;
        } else {
            this.laatstGewijzigd = (Date) laatstGewijzigd.clone();
        }
    }

    /**
     * Indicator die aangeeft dat er gegevens over de persoon in onderzoek zijn.
     *
     * @return Indicator die aangeeft dat er gegevens over de persoon in onderzoek zijn.
     */
    public Boolean getIndGegevensInOnderzoek() {
        return indGegevensInOnderzoek;
    }

    /**
     * Zet de indicator die aangeeft dat er gegevens over de persoon in onderzoek zijn.
     *
     * @param indGegevensInOnderzoek indicator die aangeeft dat er gegevens over de persoon in onderzoek zijn.
     */
    public void setIndGegevensInOnderzoek(final Boolean indGegevensInOnderzoek) {
        this.indGegevensInOnderzoek = indGegevensInOnderzoek;
    }

}

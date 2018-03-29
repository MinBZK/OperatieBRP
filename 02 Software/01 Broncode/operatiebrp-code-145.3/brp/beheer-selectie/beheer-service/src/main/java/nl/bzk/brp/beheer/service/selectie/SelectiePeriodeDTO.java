/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.service.selectie;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Request DTO object voor selectieperiode.
 */
public class SelectiePeriodeDTO {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate beginDatum;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate eindDatum;

    /**
     * Default constructor.
     */
    SelectiePeriodeDTO() {

    }

    /**
     * Constructor.
     * @param beginDatum de begindatum van de periode
     * @param eindDatum de einddatum van de periode
     */
    public SelectiePeriodeDTO(LocalDate beginDatum, LocalDate eindDatum) {
        this.beginDatum = beginDatum;
        this.eindDatum = eindDatum;
    }

    /**
     * Geef de begindatum.
     * @return de begindatum
     */
    public LocalDate getBeginDatum() {
        return beginDatum;
    }

    /**
     * Zet de begindatum.
     * @param beginDatum de begindatum
     */
    public void setBeginDatum(LocalDate beginDatum) {
        this.beginDatum = beginDatum;
    }

    /**
     * Geef de einddatum.
     * @return de einddatum
     */
    public LocalDate getEindDatum() {
        return eindDatum;
    }

    /**
     * Zet de einddatum
     * @param eindDatum de einddatum
     */
    public void setEindDatum(LocalDate eindDatum) {
        this.eindDatum = eindDatum;
    }

    /**
     * Indicatie of het object geldig is.
     * @return indicatie geldig
     */
    public boolean isValid() {
        return beginDatum != null && eindDatum != null;
    }
}

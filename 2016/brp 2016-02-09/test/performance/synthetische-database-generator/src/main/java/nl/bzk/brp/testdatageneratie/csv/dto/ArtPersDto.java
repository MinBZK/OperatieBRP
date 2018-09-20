/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.csv.dto;
/**
 *  De class is een vertaal slag maakt tussen de logische persoonId (de persoonId uit de csv bestand) en de
 *  daadwerkelijk persoonId in de database. Dit komt omdat we de set in de csv bestand herbruiken.
 *  Maar we wille wel dat de personen die een relatie hebben in de csv bestand, dat deze relatie ook blijven
 *  en wel binnne dezelfde set.
 *
 */
public class ArtPersDto {
    private final int logischPersId;
    private final int persId;

    /**
     * Instantieert Art pers dto.
     *
     * @param logischPersId logisch pers id
     * @param persId pers id
     */
    public ArtPersDto(final int logischPersId, final int persId) {
        this.logischPersId = logischPersId; this.persId = persId;
    }

    public int getLogischPersId() {
        return logischPersId;
    }

    public int getPersId() {
        return persId;
    }
}

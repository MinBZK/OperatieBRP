/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.csv.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Houder per thread om allerlei zaken bij te houden, allen te maken met de ART data die we voor testen kunnen
 * gebruiken.
 */
public class ArtHuishoudingDto {
    // templates die ingelezen wordt vanuit csv, per thread, omdat we dan niet meer the synchroniseren.
    private Map<Integer, PersDto> artPersonenTemplateDto;
    // templates die ingelezen wordt vanuit csv, per thread, omdat we dan niet meer the synchroniseren.
    private Map<Integer, RelatieDto> artRelatiesTemplateDto;

    // lijst van aantal personen die daadwerkelijk zijn aangemaakt (in setjes).
    // TODO : optimalisatie, zet om in Map ipv. List voor sneller opzoeken
    private Map<Integer, List<ArtPersDto>> mapArtPersonenDto = new HashMap<Integer, List<ArtPersDto>>();


    public Map<Integer, PersDto> getArtPersonenTemplateDto() {
        return artPersonenTemplateDto;
    }

    public Map<Integer, RelatieDto> getArtRelatiesTemplateDto() {
        return artRelatiesTemplateDto;
    }

    public Map<Integer, List<ArtPersDto>> getArtPersonenDto() {
        return mapArtPersonenDto;
    }

    public void setArtPersonenTemplateDto(final Map<Integer, PersDto> artPersonenTemplateDto) {
        this.artPersonenTemplateDto = artPersonenTemplateDto;
    }

    public void setArtRelatiesTemplateDto(final Map<Integer, RelatieDto> artRelatiesTemplateDto) {
        this.artRelatiesTemplateDto = artRelatiesTemplateDto;
    }

    public void setArtPersonenDto(final Map<Integer, List<ArtPersDto>> artPersonenDto) {
        mapArtPersonenDto = artPersonenDto;
    }

    /**
     * Geeft pers id.
     *
     * @param sesNr ses nr
     * @param logischePersId logische pers id
     * @return pers id
     */
    public Integer getPersId(final Integer sesNr, final Integer logischePersId) {
        List<ArtPersDto> list = mapArtPersonenDto.get(sesNr);
        if (null != list) {
            for (ArtPersDto brpPers : list) {
                if (brpPers.getLogischPersId() == logischePersId) {
                    return brpPers.getPersId();
                }
            }
        }
        return null;
    }
}

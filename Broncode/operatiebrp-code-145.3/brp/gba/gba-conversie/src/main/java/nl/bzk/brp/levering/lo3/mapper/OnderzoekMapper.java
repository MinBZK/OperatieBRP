/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import java.util.List;
import java.util.Set;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.GroepElement;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;

/**
 * Onderzoek mapper.
 */
public interface OnderzoekMapper {

    /**
     * Bepaal of een attribuut in een onderzoek betrokken is en lever in dat geval een Lo3Onderzoek
     * op.
     * @param voorkomenSleutel De voorkomen sleutel van het record waar het attribuut onderdeel van uitmaakt
     * @param element Het element waarvoor het onderzoek bepaalt moet worden
     * @param elementBehoortBijGroepsOnderzoek Geeft aan of dit attribuut in onderzoek moet staan als de BRP groep in zijn geheel in onderzoek staat.
     * @return Het Lo3Onderzoek als dit attribuut in onderzoek staat, of anders <code>null</code>.
     */
    Lo3Onderzoek bepaalOnderzoek(final Long voorkomenSleutel, final AttribuutElement element, final boolean elementBehoortBijGroepsOnderzoek);

    /**
     * Bepaal of een attribuut in een onderzoek betrokken is en lever in dat geval een Lo3Onderzoek
     * op.
     *
     * Deze cornercase gaat om elementen die in BRP op de A-laag worden gepositioneerd; bij een GBA
     * onderzoek wordt dan ook de standaardgroep in onderzoek gezet om aan te geven in welk record
     * de rubriek in onderzoek staat. Als het om een BRP onderzoek gaat dan zal het resultaat zijn
     * dat in GBA het onderzoek op alle rijen voorkomt.
     * @param objectSleutel De voorkomen sleutel van het record waar het attribuut onderdeel van uitmaakt
     * @param element Het element waarvoor het onderzoek bepaalt moet worden
     * @param elementBehoortBijGroepsOnderzoek Geeft aan of dit attribuut in onderzoek moet staan als de BRP groep in zijn geheel in onderzoek staat.
     * @param voorkomenSleutel De voorkomen sleutel van de groep die voor een GBA onderzoek ook in onderzoek moet staan
     * @param groepElement Het element van de groep die ook in onderzoek moet staan voor een GBA onderzoek
     * @return Het Lo3Onderzoek als dit attribuut in onderzoek staat, of anders <code>null</code>.
     */
    Lo3Onderzoek bepaalOnderzoek(final Long objectSleutel, final AttribuutElement element, final boolean elementBehoortBijGroepsOnderzoek,
                                 final Long voorkomenSleutel, final GroepElement groepElement);

    /**
     * Bepaal of een attribuut in onderzoeken betrokken is.
     * @param voorkomenSleutel De voorkomen sleutel van het record waar het attribuut onderdeel van uitmaakt
     * @param element Het element waarvoor het onderzoek bepaalt moet worden
     * @param elementBehoortBijGroepsOnderzoek Geeft aan of dit attribuut in onderzoek moet staan als de BRP groep in zijn geheel in onderzoek staat.
     * @return De lijst van Lo3Onderzoeken (is nooit null, kan wel leeg zijn).
     */
    Set<Lo3Onderzoek> bepaalOnderzoeken(Long voorkomenSleutel, AttribuutElement element, boolean elementBehoortBijGroepsOnderzoek);

    /**
     * Bepaal puur op de acties en de 'groep' element enum het onderzoek.
     * @param acties actie id's
     * @param objectSleutels object sleutels
     * @param voorkomenSleutels voorkomen sleutels
     * @param groepElement groep element
     * @return onderzoek
     */
    Lo3Onderzoek bepaalActueelOnderzoek(final List<Long> acties, final List<Long> objectSleutels, final List<Long> voorkomenSleutels,
                                        final GroepElement groepElement);

    /**
     * Bepaal historisch onderzoek.
     * @param acties acties
     * @param objectSleutels object sleutels
     * @param voorkomenSleutels voorkomensleutels
     * @param groepElement groep element
     * @return historisch onderzoek
     */
    Lo3Onderzoek bepaalHistorischOnderzoek(final List<Long> acties, final List<Long> objectSleutels, final List<Long> voorkomenSleutels,
                                           final GroepElement groepElement);

}

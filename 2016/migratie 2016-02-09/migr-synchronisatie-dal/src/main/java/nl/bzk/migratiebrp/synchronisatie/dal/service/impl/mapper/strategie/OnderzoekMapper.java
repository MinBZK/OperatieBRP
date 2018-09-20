/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie;

import java.util.Set;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAttribuutMetOnderzoek;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.DeltaEntiteit;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Element;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Onderzoek;

/**
 * Interface voor een Mapper om op algemene wijze een onderzoek op gegevens te vertalen naar BRP entiteiten.
 */
public interface OnderzoekMapper {
    /**
     * Geeft de onderzoeken terug die gemapped zijn door
     * {@link #mapOnderzoek(DeltaEntiteit, BrpAttribuutMetOnderzoek, Element)}.
     *
     * @return De set onderzoeken.
     */
    Set<Onderzoek> getOnderzoekSet();

    /**
     * Voeg dit gegeven toe aan de lijst van onderzoeken, als het gegeven in onderzoek staat.
     *
     * @param deltaEntiteit
     *            de BRP database entiteit die het gegeven in onderzoek bevat.
     * @param attribuut
     *            het gegeven wat in onderzoek staat.
     * @param soortGegeven
     *            het soort gegeven waar dit onderzoek voor geldt
     */
    void mapOnderzoek(DeltaEntiteit deltaEntiteit, BrpAttribuutMetOnderzoek attribuut, Element soortGegeven);
}

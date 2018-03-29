/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.zoekpersoongeneriek;

import java.time.ZonedDateTime;
import nl.bzk.brp.service.bevraging.algemeen.AbstractProtocolleerBerichtServiceImpl;
import nl.bzk.brp.service.bevraging.algemeen.BevragingResultaat;
import org.springframework.stereotype.Service;

/**
 * De specialisatie van {@link AbstractProtocolleerBerichtServiceImpl} voor Zoek Persoon.
 * @param <T> verzoek.
 */
@Service
final class ZoekPersoonProtocolleerBerichtServiceImpl<T extends ZoekPersoonGeneriekVerzoek>
        extends AbstractProtocolleerBerichtServiceImpl<T, BevragingResultaat> {
    @Override
    public Integer bepaalDatumAanvangMaterielePeriodeResultaat(final ZoekPersoonGeneriekVerzoek bevragingVerzoek) {
        return null;
    }

    @Override
    public Integer bepaalDatumEindeMaterielePeriodeResultaat(final ZoekPersoonGeneriekVerzoek bevragingVerzoek) {
        return null;
    }

    @Override
    public ZonedDateTime bepaalDatumTijdAanvangFormelePeriodeResultaat(final ZoekPersoonGeneriekVerzoek bevragingVerzoek) {
        return null;
    }

    @Override
    public ZonedDateTime bepaalDatumTijdEindeFormelePeriodeResultaat(final ZoekPersoonGeneriekVerzoek bevragingVerzoek,
                                                                     final ZonedDateTime datumTijdKlaarzettenBericht) {
        return datumTijdKlaarzettenBericht;
    }
}

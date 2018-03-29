/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.geefmedebewoners;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.DatumFormatterUtil;
import nl.bzk.brp.service.bevraging.algemeen.AbstractProtocolleerBerichtServiceImpl;
import nl.bzk.brp.service.bevraging.algemeen.BevragingResultaat;
import org.springframework.stereotype.Service;

/**
 * Protocollering implementatie voor Geef Medebewoners.
 */
@Service
final class GeefMedebewonersProtocolleerBerichtServiceImpl
        extends AbstractProtocolleerBerichtServiceImpl<GeefMedebewonersVerzoek, BevragingResultaat> {

    @Override
    protected Integer bepaalDatumAanvangMaterielePeriodeResultaat(final GeefMedebewonersVerzoek verzoek) {
        if (verzoek.getParameters().getPeilmomentMaterieel() != null) {
            final LocalDate peilMomentMaterieelResultaat = DatumFormatterUtil.vanXsdDatumNaarLocalDate(
                    verzoek.getParameters().getPeilmomentMaterieel());
            return DatumFormatterUtil.vanLocalDateNaarInteger(peilMomentMaterieelResultaat);
        }
        return DatumUtil.vandaag();
    }

    @Override
    protected Integer bepaalDatumEindeMaterielePeriodeResultaat(final GeefMedebewonersVerzoek verzoek) {
        if (verzoek.getParameters().getPeilmomentMaterieel() != null) {
            final LocalDate peilMomentMaterieelResultaatPlusEenDag = DatumFormatterUtil.vanXsdDatumNaarLocalDate(
                    verzoek.getParameters().getPeilmomentMaterieel()).plus(1, ChronoUnit.DAYS);
            return DatumFormatterUtil.vanLocalDateNaarInteger(peilMomentMaterieelResultaatPlusEenDag);
        }
        return DatumUtil.morgen();
    }

    @Override
    protected ZonedDateTime bepaalDatumTijdAanvangFormelePeriodeResultaat(final GeefMedebewonersVerzoek verzoek) {
        return null;
    }

    @Override
    protected ZonedDateTime bepaalDatumTijdEindeFormelePeriodeResultaat(final GeefMedebewonersVerzoek verzoek,
                                                                        final ZonedDateTime datumTijdKlaarzettenBericht) {
        return datumTijdKlaarzettenBericht;
    }

}

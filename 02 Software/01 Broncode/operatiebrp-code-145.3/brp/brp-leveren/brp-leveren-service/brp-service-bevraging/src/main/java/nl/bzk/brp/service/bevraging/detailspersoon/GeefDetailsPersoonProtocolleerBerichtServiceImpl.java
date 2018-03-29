/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.detailspersoon;

import com.google.common.collect.Sets;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.HistorieVorm;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.DatumFormatterUtil;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.protocollering.domain.algemeen.ProtocolleringOpdracht;
import nl.bzk.brp.service.bevraging.algemeen.AbstractProtocolleerBerichtServiceImpl;
import nl.bzk.brp.service.bevraging.algemeen.BevragingResultaat;
import org.springframework.stereotype.Service;

/**
 * De specialisatie van {@link AbstractProtocolleerBerichtServiceImpl} voor Geef Details Persoon.
 */
@Service
final class GeefDetailsPersoonProtocolleerBerichtServiceImpl
        extends AbstractProtocolleerBerichtServiceImpl<GeefDetailsPersoonVerzoek, BevragingResultaat> {

    @Override
    protected void vulScopingParameters(final GeefDetailsPersoonVerzoek verzoek, final ProtocolleringOpdracht berichtGegevens) {
        if (verzoek.getScopingElementen() != null
                && verzoek.getScopingElementen().getElementen() != null
                && !verzoek.getScopingElementen().getElementen().isEmpty()) {
            final Set<Integer> scopingAttributen = Sets.newHashSet();
            for (final String element : verzoek.getScopingElementen().getElementen()) {
                scopingAttributen.add(ElementHelper.getElement(element).getId());
            }
            berichtGegevens.setScopingAttributen(scopingAttributen);
        }
    }

    @Override
    protected Integer bepaalDatumAanvangMaterielePeriodeResultaat(final GeefDetailsPersoonVerzoek verzoek) {
        Integer datumAanvangMaterielePeriodeResultaat = null;
        final GeefDetailsPersoonVerzoek.HistorieFilterParameters historieFilterParameters = verzoek.getParameters()
                .getHistorieFilterParameters();
        if (historieFilterParameters != null && historieFilterParameters.getHistorieVorm() == HistorieVorm.GEEN) {
            if (historieFilterParameters.getPeilMomentMaterieelResultaat() != null) {
                final LocalDate peilMomentMaterieelResultaat = DatumFormatterUtil
                        .vanXsdDatumNaarLocalDate(historieFilterParameters.getPeilMomentMaterieelResultaat());
                datumAanvangMaterielePeriodeResultaat = DatumFormatterUtil.vanLocalDateNaarInteger(peilMomentMaterieelResultaat);
            } else {
                datumAanvangMaterielePeriodeResultaat = DatumUtil.vandaag();
            }
        }
        return datumAanvangMaterielePeriodeResultaat;
    }

    @Override
    protected Integer bepaalDatumEindeMaterielePeriodeResultaat(final GeefDetailsPersoonVerzoek verzoek) {
        final Integer datumEindeMaterielePeriodeResultaat;
        final GeefDetailsPersoonVerzoek.HistorieFilterParameters historieFilterParameters = verzoek.getParameters()
                .getHistorieFilterParameters();
        if (historieFilterParameters != null && historieFilterParameters.getPeilMomentMaterieelResultaat() != null) {
            final LocalDate peilMomentMaterieelResultaatPlusEenDag = DatumFormatterUtil.vanXsdDatumNaarLocalDate(
                    historieFilterParameters.getPeilMomentMaterieelResultaat()).plus(1, ChronoUnit.DAYS);
            datumEindeMaterielePeriodeResultaat = DatumFormatterUtil.vanLocalDateNaarInteger(peilMomentMaterieelResultaatPlusEenDag);
        } else {
            datumEindeMaterielePeriodeResultaat = DatumUtil.morgen();
        }
        return datumEindeMaterielePeriodeResultaat;
    }

    @Override
    protected ZonedDateTime bepaalDatumTijdAanvangFormelePeriodeResultaat(final GeefDetailsPersoonVerzoek verzoek) {
        ZonedDateTime datumTijdAanvangFormelePeriodeResultaat = null;
        final GeefDetailsPersoonVerzoek.HistorieFilterParameters historieFilterParameters = verzoek.getParameters()
                .getHistorieFilterParameters();
        if (historieFilterParameters != null && (historieFilterParameters.getHistorieVorm() == HistorieVorm.GEEN
                || historieFilterParameters.getHistorieVorm() == HistorieVorm.MATERIEEL)) {
            if (historieFilterParameters.getPeilMomentFormeelResultaat() != null) {
                final ZonedDateTime peilMomentFormeelResultaat = DatumFormatterUtil
                        .vanXsdDatumTijdNaarZonedDateTime(historieFilterParameters.getPeilMomentFormeelResultaat());
                datumTijdAanvangFormelePeriodeResultaat = peilMomentFormeelResultaat;
            } else {
                datumTijdAanvangFormelePeriodeResultaat = DatumUtil.nuAlsZonedDateTimeInNederland();
            }
        }
        return datumTijdAanvangFormelePeriodeResultaat;
    }

    @Override
    protected ZonedDateTime bepaalDatumTijdEindeFormelePeriodeResultaat(final GeefDetailsPersoonVerzoek verzoek,
                                                                        final ZonedDateTime datumTijdKlaarzettenBericht) {
        ZonedDateTime datumTijdEindeFormelePeriodeResultaat = null;
        final GeefDetailsPersoonVerzoek.HistorieFilterParameters historieFilterParameters = verzoek.getParameters()
                .getHistorieFilterParameters();
        if (historieFilterParameters != null && historieFilterParameters.getPeilMomentFormeelResultaat() != null) {
            final ZonedDateTime peilMomentFormeelResultaat = DatumFormatterUtil
                    .vanXsdDatumTijdNaarZonedDateTime(historieFilterParameters.getPeilMomentFormeelResultaat());
            datumTijdEindeFormelePeriodeResultaat = peilMomentFormeelResultaat;
        }
        return datumTijdEindeFormelePeriodeResultaat == null ? datumTijdKlaarzettenBericht : datumTijdEindeFormelePeriodeResultaat;
    }

}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.detailspersoon;

import java.time.ZonedDateTime;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.DatumFormatterUtil;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.StapException;
import nl.bzk.brp.service.algemeen.StapMeldingException;
import nl.bzk.brp.service.bevraging.algemeen.BevragingSelecteerPersoonService;
import org.springframework.stereotype.Service;


/**
 * Functionaliteit voor het ophalen van een persoon a.h.v. bsn, anr of objectsleutel
 */

@Bedrijfsregel(Regel.R1587)
@Bedrijfsregel(Regel.R1585)
@Bedrijfsregel(Regel.R1833)
@Bedrijfsregel(Regel.R2192)
@Bedrijfsregel(Regel.R2232)
@Bedrijfsregel(Regel.R2300)
@Service
final class OphalenGeefDetailsPersoonServiceImpl implements GeefDetailsPersoon.OphalenPersoonService {

    @Inject
    private BevragingSelecteerPersoonService selecteerPersoonService;

    private OphalenGeefDetailsPersoonServiceImpl() {

    }

    @Override
    public Persoonslijst voerStapUit(final GeefDetailsPersoonVerzoek bevragingVerzoek, final Autorisatiebundel autorisatiebundel) throws StapException {

        final Persoonslijst persoonslijst = selecteerPersoonService
                .selecteerPersoon(bevragingVerzoek.getIdentificatiecriteria().getBsn(), bevragingVerzoek.getIdentificatiecriteria().getAnr(),
                        bevragingVerzoek.getIdentificatiecriteria().getObjectSleutel(), bevragingVerzoek.getStuurgegevens().getZendendePartijCode(),
                        autorisatiebundel);
        controleerFormeelPeilmomentTovGBASystematiek(persoonslijst.bepaalTijdstipLaatsteWijzigingGBASystematiek(), bevragingVerzoek);

        return persoonslijst;
    }

    private void controleerFormeelPeilmomentTovGBASystematiek(final ZonedDateTime tijdstipLaatsteWijzigingGBASystematiek,
                                                              final GeefDetailsPersoonVerzoek bevragingVerzoek) throws StapMeldingException {
        final GeefDetailsPersoonVerzoek.HistorieFilterParameters historieParams = bevragingVerzoek.getParameters()
                .getHistorieFilterParameters();
        if (tijdstipLaatsteWijzigingGBASystematiek != null && historieParams != null && historieParams.getPeilMomentFormeelResultaat() != null) {
            final ZonedDateTime peilMomentFormeelResultaat =
                    DatumFormatterUtil.vanXsdDatumTijdNaarZonedDateTime(historieParams.getPeilMomentFormeelResultaat());
            if (peilMomentFormeelResultaat != null && peilMomentFormeelResultaat.isBefore(tijdstipLaatsteWijzigingGBASystematiek)) {
                throw new StapMeldingException(Regel.R2300);
            }
        }
    }
}

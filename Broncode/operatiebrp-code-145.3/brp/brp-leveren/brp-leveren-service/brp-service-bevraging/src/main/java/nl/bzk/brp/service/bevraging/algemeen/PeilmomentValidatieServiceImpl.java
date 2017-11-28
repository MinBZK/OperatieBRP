/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.algemeen;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMelding;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.service.algemeen.StapMeldingException;
import nl.bzk.brp.service.algemeen.request.DatumService;
import org.springframework.stereotype.Service;

/**
 * Service implementatie vh valideren van peilmoment(en) uit bevragingverzoek.
 * Het peilmoment moet het juiste datum formaat hebben (R1274) en mag niet in de toekomst liggen (R2222 en R2295).
 */
@Bedrijfsregel(Regel.R1274)
@Bedrijfsregel(Regel.R2222)
@Bedrijfsregel(Regel.R2295)
@Service
final class PeilmomentValidatieServiceImpl implements PeilmomentValidatieService {

    @Inject
    private DatumService datumService;

    private PeilmomentValidatieServiceImpl() {
    }

    @Override
    public void valideerMaterieel(String peilmomentMaterieel) throws StapMeldingException {
        valideerMaterieelPeilmomentNietInToekomst(peilmomentMaterieel);
    }

    @Override
    public void valideerFormeelEnMaterieel(String peilmomentMaterieel, String peilmomentFormeel) throws StapMeldingException {
        valideerFormeelPeilmomentNietInToekomst(peilmomentFormeel);
        valideerMaterieelPeilmomentNietInToekomst(peilmomentMaterieel);
    }

    private void valideerFormeelPeilmomentNietInToekomst(String peilmomentFormeel) throws StapMeldingException {
        final ZonedDateTime formeelPeilmoment = datumService.parseDateTime(peilmomentFormeel);
        if (formeelPeilmoment != null && formeelPeilmoment.isAfter(DatumUtil.nuAlsZonedDateTime())) {
            throw new StapMeldingException(new Melding(SoortMelding.FOUT, Regel.R2222));
        }
    }

    private void valideerMaterieelPeilmomentNietInToekomst(String peilmomentMaterieel) throws StapMeldingException {
        LocalDate materieelPeilmoment = datumService.parseDate(peilmomentMaterieel);
        if (materieelPeilmoment != null && materieelPeilmoment.isAfter(DatumUtil.vanZonedDateTimeNaarLocalDateNederland(DatumUtil
                .nuAlsZonedDateTime()))) {
            throw new StapMeldingException(new Melding(SoortMelding.FOUT, Regel.R2295));
        }
    }


}

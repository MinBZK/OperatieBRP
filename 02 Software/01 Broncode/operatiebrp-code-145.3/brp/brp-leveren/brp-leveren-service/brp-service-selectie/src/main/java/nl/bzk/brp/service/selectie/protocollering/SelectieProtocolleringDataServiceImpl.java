/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.protocollering;

import java.sql.Timestamp;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Selectietaak;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SelectietaakStatus;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.service.dalapi.SelectieRepository;
import nl.bzk.brp.service.selectie.algemeen.SelectietaakStatusHistorieUtil;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Transactionele service tbv het protocolleren van de selectie gegevens
 */
@Component
public final class SelectieProtocolleringDataServiceImpl implements SelectieProtocolleringDataService {

    @Inject
    private SelectieRepository selectieRepository;

    private SelectieProtocolleringDataServiceImpl() {
    }

    /**
     * Selecteer te protocolleren selectietaken.
     */
    @Bedrijfsregel(Regel.R2662)
    @Override
    public List<Selectietaak> selecteerTeProtocollerenSelectietaken() {
        return selectieRepository.getSelectietakenMetStatusTeProtocolleren();
    }

    @Override
    @Transactional(transactionManager = "masterTransactionManager")
    public Selectietaak updateSelectietaakStatus(Selectietaak selectietaakOud, SelectietaakStatus status) {
        final Selectietaak selectietaak = selectieRepository.haalSelectietaakOp(selectietaakOud.getId());
        final Timestamp nuTijd = DatumUtil.vanZonedDateTimeNaarSqlTimeStamp(DatumUtil.nuAlsZonedDateTime());
        selectietaak.setStatus((short) status.getId());
        SelectietaakStatusHistorieUtil.updateTaakStatus(selectietaak, nuTijd, status, null);
        selectieRepository.slaSelectietaakOp(selectietaak);
        return selectietaak;
    }

    @Override
    @Transactional(transactionManager = "masterTransactionManager")
    public void setVoortgang(final Selectietaak selectietaak, final int regelsVerwerkt, final int totaalAantalRegels) {
        //nog te doen ROOD-906
    }


}

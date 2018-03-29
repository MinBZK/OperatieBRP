/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.protocollering;

import com.google.common.collect.Lists;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Selectietaak;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SelectietaakHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SelectietaakStatusHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SelectietaakStatus;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSelectie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.service.dalapi.SelectieRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 */
@RunWith(MockitoJUnitRunner.class)
public class SelectieProtocolleringDataServiceImplTest {

    @InjectMocks
    private SelectieProtocolleringDataServiceImpl service;

    @Mock
    private SelectieRepository selectieRepository;

    @Test
    public void test() {
        final ArrayList<Selectietaak> list = Lists.newArrayList(
                maakSelectieTaak(SelectietaakStatus.TE_PROTOCOLLEREN)
        );
        Mockito.when(selectieRepository.getSelectietakenMetStatusTeProtocolleren()).thenReturn(list);
        final List<Selectietaak> serviceList = service.selecteerTeProtocollerenSelectietaken();
        Assert.assertTrue(list == serviceList);
        Mockito.verify(selectieRepository).getSelectietakenMetStatusTeProtocolleren();
    }

    private Selectietaak maakSelectieTaak(final SelectietaakStatus status) {
        final Dienst dienst = AutAutUtil.zoekDienst(TestAutorisaties.metSoortDienst(SoortDienst.SELECTIE), SoortDienst.SELECTIE);
        dienst.setSoortSelectie(SoortSelectie.STANDAARD_SELECTIE.getId());
        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(
                TestAutorisaties.maak(Rol.AFNEMER, dienst), dienst);

        final Timestamp nuTijd = DatumUtil.vanZonedDateTimeNaarSqlTimeStamp(DatumUtil.nuAlsZonedDateTime());
        final Selectietaak selectietaak = new Selectietaak(dienst, autorisatiebundel.getToegangLeveringsautorisatie(), 1);
        selectietaak.setStatus((short) SelectietaakStatus.UITVOERBAAR.getId());
        selectietaak.setDatumPlanning(DatumUtil.vandaag());
        selectietaak.setDienst(autorisatiebundel.getDienst());
        selectietaak.setStatus((short) status.getId());
        selectietaak.setId(new Random().nextInt(Integer.MAX_VALUE));

        final SelectietaakHistorie selectietaakHistorie = new SelectietaakHistorie(selectietaak);
        selectietaakHistorie.setDatumTijdRegistratie(nuTijd);
        selectietaak.addSelectietaakHistorieSet(selectietaakHistorie);

        final SelectietaakStatusHistorie selectietaakStatusHistorie = new SelectietaakStatusHistorie(selectietaak);
        selectietaakStatusHistorie.setDatumTijdRegistratie(nuTijd);
        selectietaakStatusHistorie.setStatus(selectietaak.getStatus());
        selectietaak.addSelectietaakStatusHistorieSet(selectietaakStatusHistorie);
        return selectietaak;
    }

}

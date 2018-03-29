/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.beheer;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Selectietaak;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SelectietaakHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SelectietaakStatusHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.beheer.service.selectie.SelectiePeriodeDTO;
import nl.bzk.brp.beheer.service.selectie.SelectieTaakDTO;
import nl.bzk.brp.beheer.service.selectie.SelectieTaakService;
import nl.bzk.brp.test.common.DienstSleutel;
import nl.bzk.brp.test.common.dsl.DslSectie;
import org.junit.Assert;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

/**
 */
@Component
class BeheerSelectieAPIServiceImpl implements BeheerSelectieAPIService {

    @Inject
    private SelectieTaakRepositoryStub selectieTaakRepositoryStub;
    @Inject
    private BeheerAutorisatieStub autorisatieStub;
    @Inject
    private SelectieTaakService selectieTaakService;

    private Collection<SelectieTaakDTO> berekendeTaken;
    private final Function<DienstSleutel, Dienst> dienstResolver = dienstSleutel -> autorisatieStub.getDienst(dienstSleutel);

    @Override
    public void maakSelectietaken(List<DslSectie> sectieList) {
        selectieTaakRepositoryStub.setSelectietaken(maakSelectietakenObvDsl(sectieList));
    }

    @Override
    public void geefBerekendeTaken(String beginDatum, String eindDatum) {
        LocalDate ldBeginDatum = LocalDate.parse(beginDatum);
        LocalDate ldEindDatum = LocalDate.parse(eindDatum);
        final SelectiePeriodeDTO selectiePeriodeDTO = new SelectiePeriodeDTO(ldBeginDatum, ldEindDatum);
        this.berekendeTaken = selectieTaakService.getSelectieTaken(selectiePeriodeDTO);
    }

    @Override
    public void assertBerekendeTaken(Integer verwachteAantalTaken, List<Map<String, String>> verwachteTaken) {
        Assert.assertTrue("Aantal ontvangen taken was " + berekendeTaken.size(), berekendeTaken.size() == verwachteAantalTaken);
        Assert.assertTrue("Aantal verwachte rijen klopt niet.", verwachteTaken.size() == verwachteAantalTaken);
        int index = 0;
        for (Map<String, String> verwachteTaak : verwachteTaken) {
            for (Map.Entry<String, String> kolom : verwachteTaak.entrySet()) {
                try {
                    Field field = SelectieTaakDTO.class.getDeclaredField(kolom.getKey());
                    field.setAccessible(true);
                    SelectieTaakDTO target = Iterables.get(berekendeTaken, index);
                    Object waarde = ReflectionUtils.getField(field, target);
                    Assert.assertEquals(kolom.getKey() + " komt niet overeen met verwachting.", kolom.getValue(),
                            waarde == null ? "null" : waarde.toString());
                } catch (NoSuchFieldException e) {
                    throw new RuntimeException(String.format("Veld %s niet gevonden.", kolom.getKey()));
                }
            }
            index++;
        }
    }

    private List<Selectietaak> maakSelectietakenObvDsl(List<DslSectie> sectieList) {
        final Function<DienstSleutel, ToegangLeveringsAutorisatie> tlaResolver = dienstSleutel
                -> autorisatieStub.getToegangleveringsautorisatie(dienstSleutel);
        final ArrayList<Selectietaak> takenlijst = Lists.newArrayList();
        for (DslSectie dslSectie : sectieList) {
            final Selectietaak selectietaak = SelectietaakParser.parse(dslSectie, dienstResolver, tlaResolver);

            final Timestamp nuTijd = DatumUtil.vanZonedDateTimeNaarSqlTimeStamp(DatumUtil.nuAlsZonedDateTime());
            //his records voor taak
            final SelectietaakHistorie selectietaakHistorie = new SelectietaakHistorie(selectietaak);
            selectietaakHistorie.setDatumTijdRegistratie(nuTijd);
            selectietaakHistorie.setIndicatieSelectielijstGebruiken(selectietaak.isIndicatieSelectielijstGebruiken());
            selectietaak.addSelectietaakHistorieSet(selectietaakHistorie);
            final SelectietaakStatusHistorie selectietaakStatusHistorie = new SelectietaakStatusHistorie(selectietaak);
            selectietaakStatusHistorie.setStatusGewijzigdDoor("Systeem");
            selectietaakStatusHistorie.setDatumTijdRegistratie(nuTijd);
            selectietaakStatusHistorie.setStatus(selectietaak.getStatus());
            selectietaak.addSelectietaakStatusHistorieSet(selectietaakStatusHistorie);

            takenlijst.add(selectietaak);
        }
        return takenlijst;
    }

    @Override
    public void reset() {
        this.selectieTaakRepositoryStub.reset();
        this.berekendeTaken = null;
    }
}

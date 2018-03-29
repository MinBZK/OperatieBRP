/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.service.selectie;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.google.common.collect.Iterables;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSelectie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.algemeen.TestPartijRolBuilder;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test voor {@link SelectieTaakAutorisatieFilterServiceImpl}.
 */
public class SelectieTaakAutorisatieFilterServiceImplTest {

    private final SelectieTaakAutorisatieFilterServiceImpl service = new SelectieTaakAutorisatieFilterServiceImpl();

    private Dienst dienst;
    private ToegangLeveringsAutorisatie toegangLeveringsAutorisatie;
    private Leveringsautorisatie leveringsautorisatie;
    private SelectieTaakDTO taak;

    @Before
    public void before() {
        leveringsautorisatie = TestAutorisaties.metSoortDienst(SoortDienst.SELECTIE);
        List<Dienst>
                diensten =
                leveringsautorisatie.getDienstbundelSet().stream().map(Dienstbundel::getDienstSet).flatMap(Collection::stream).collect(Collectors.toList());
        dienst = Iterables.getOnlyElement(diensten);
        dienst.setEersteSelectieDatum(DatumUtil.vandaag());
        dienst.setSoortSelectie(SoortSelectie.STANDAARD_SELECTIE.getId());
        toegangLeveringsAutorisatie = TestAutorisaties.maak(Rol.AFNEMER, dienst);
        linkAutorisatieEnMaakValide(toegangLeveringsAutorisatie, leveringsautorisatie);
        taak = new SelectieTaakDTO(dienst, toegangLeveringsAutorisatie, DatumUtil.vanIntegerNaarLocalDate(DatumUtil.vandaag()), null, null, 0);
    }

    private static void linkAutorisatieEnMaakValide(ToegangLeveringsAutorisatie toegangLeveringsAutorisatie, Leveringsautorisatie leveringsautorisatie) {
        toegangLeveringsAutorisatie.setActueelEnGeldig(true);
        toegangLeveringsAutorisatie.setDatumIngang(DatumUtil.gisteren());
        Partij
                partij =
                TestPartijBuilder.maakBuilder().metCode("123456").metActueelEnGeldig(true).metDatumAanvang(DatumUtil.gisteren()).build();
        PartijRol
                partijRol =
                TestPartijRolBuilder.maker().metRol(Rol.AFNEMER).metActueelEnGeldig(true).metDatumIngang(DatumUtil.gisteren()).metPartij(partij).maak();
        toegangLeveringsAutorisatie.setGeautoriseerde(partijRol);
        leveringsautorisatie.getToegangLeveringsautorisatieSet().add(toegangLeveringsAutorisatie);
    }

    @Test
    public void houdtRekeningMetGeldigheidDienst() {
        dienst.setDatumEinde(DatumUtil.gisteren());

        Collection<SelectieTaakDTO> taken = service.filter(Collections.singleton(taak));

        assertThat(taken.size(), is(0));
    }

    @Test
    public void houdtRekeningMetGeldigheidDienstbundel() {
        dienst.getDienstbundel().setDatumEinde(DatumUtil.gisteren());

        Collection<SelectieTaakDTO> taken = service.filter(Collections.singleton(taak));

        assertThat(taken.size(), is(0));
    }

    @Test
    public void houdtRekeningMetGeldigheidLeveringsautorisatie() {
        dienst.getDienstbundel().getLeveringsautorisatie().setDatumEinde(DatumUtil.gisteren());

        Collection<SelectieTaakDTO> taken = service.filter(Collections.singleton(taak));

        assertThat(taken.size(), is(0));
    }

    @Test
    public void houdtRekeningMetGeldigheidToegangLeveringsautorisatie() {
        toegangLeveringsAutorisatie.setDatumEinde(DatumUtil.gisteren());

        Collection<SelectieTaakDTO> taken = service.filter(Collections.singleton(taak));

        assertThat(taken.size(), is(0));
    }

    @Test
    public void houdtRekeningMetGeldigheidGeautoriseerde() {
        toegangLeveringsAutorisatie.getGeautoriseerde().setDatumEinde(DatumUtil.gisteren());

        Collection<SelectieTaakDTO> taken = service.filter(Collections.singleton(taak));

        assertThat(taken.size(), is(0));
    }

    @Test
    public void houdtRekeningMetGeldigheidGeautoriseerdePartij() {
        toegangLeveringsAutorisatie.getGeautoriseerde().getPartij().setDatumEinde(DatumUtil.gisteren());

        Collection<SelectieTaakDTO> taken = service.filter(Collections.singleton(taak));

        assertThat(taken.size(), is(0));
    }

    @Test
    public void houdtGeenRekeningMetActualiteitDienstWantInQuery() {
        dienst.setActueelEnGeldig(false);

        Collection<SelectieTaakDTO> taken = service.filter(Collections.singleton(taak));

        assertThat(taken.size(), is(1));
    }

    @Test
    public void houdtRekeningMetActualiteitDienstbundel() {
        dienst.getDienstbundel().setActueelEnGeldig(false);

        Collection<SelectieTaakDTO> taken = service.filter(Collections.singleton(taak));

        assertThat(taken.size(), is(0));
    }

    @Test
    public void houdtRekeningMetActualiteitLeveringsautorisatie() {
        dienst.getDienstbundel().getLeveringsautorisatie().setActueelEnGeldig(false);

        Collection<SelectieTaakDTO> taken = service.filter(Collections.singleton(taak));

        assertThat(taken.size(), is(0));
    }

    @Test
    public void houdtRekeningMetActualiteitToegangLeveringsautorisatie() {
        toegangLeveringsAutorisatie.setActueelEnGeldig(false);

        Collection<SelectieTaakDTO> taken = service.filter(Collections.singleton(taak));

        assertThat(taken.size(), is(0));
    }

    @Test
    public void houdtRekeningMetActualiteitGeautoriseerde() {
        toegangLeveringsAutorisatie.getGeautoriseerde().setActueelEnGeldig(false);

        Collection<SelectieTaakDTO> taken = service.filter(Collections.singleton(taak));

        assertThat(taken.size(), is(0));
    }

    @Test
    public void houdtRekeningMetActualiteitGeautoriseerdePartij() {
        toegangLeveringsAutorisatie.getGeautoriseerde().getPartij().setActueelEnGeldig(false);

        Collection<SelectieTaakDTO> taken = service.filter(Collections.singleton(taak));

        assertThat(taken.size(), is(0));
    }

    @Test
    public void houdtGeenRekeningMetBlokkeringDienstWantInQuery() {
        dienst.setIndicatieGeblokkeerd(true);

        Collection<SelectieTaakDTO> taken = service.filter(Collections.singleton(taak));

        assertThat(taken.size(), is(1));
    }

    @Test
    public void houdtRekeningMetBlokkeringDienstbundel() {
        dienst.getDienstbundel().setIndicatieGeblokkeerd(true);

        Collection<SelectieTaakDTO> taken = service.filter(Collections.singleton(taak));

        assertThat(taken.size(), is(0));
    }

    @Test
    public void houdtRekeningMetBlokkeringLeveringsautorisatie() {
        dienst.getDienstbundel().getLeveringsautorisatie().setIndicatieGeblokkeerd(true);

        Collection<SelectieTaakDTO> taken = service.filter(Collections.singleton(taak));

        assertThat(taken.size(), is(0));
    }

    @Test
    public void houdtRekeningMetBlokkeringToegangLeveringsautorisatie() {
        toegangLeveringsAutorisatie.setIndicatieGeblokkeerd(true);

        Collection<SelectieTaakDTO> taken = service.filter(Collections.singleton(taak));

        assertThat(taken.size(), is(0));
    }

}
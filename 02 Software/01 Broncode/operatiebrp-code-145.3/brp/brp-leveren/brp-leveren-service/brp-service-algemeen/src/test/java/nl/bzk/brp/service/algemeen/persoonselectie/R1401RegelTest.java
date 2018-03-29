/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.persoonselectie;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMelding;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import org.junit.Assert;
import org.junit.Test;

public class R1401RegelTest {

    @Test
    public final void testGetRegel() {
        final R1401Regel regel = new R1401Regel(null, null);
        Assert.assertEquals(Regel.R1401, regel.getRegel());
    }

    @Test
    public final void testGeenMeldingWantAfnemerindicatieMatchtMetLeveringautorisatie() {
        int leveringsautorisatieId = 10;
        final String afnemerCode = "000123";
        Autorisatiebundel autorisatiebundel = maakAutorisatiebundel(leveringsautorisatieId, afnemerCode, SoortDienst.VERWIJDERING_AFNEMERINDICATIE);
        final MetaObject persoon = TestBuilders.maakIngeschrevenPersoon()
                .metObject(TestBuilders.maakAfnemerindicatie(leveringsautorisatieId, afnemerCode))
                .build();
        final Persoonslijst persoonslijst = new Persoonslijst(persoon, 0L);
        final R1401Regel regel = new R1401Regel(persoonslijst, autorisatiebundel);
        Assert.assertNull(regel.valideer());
    }

    @Test
    public final void testMeldingWantAfnemerindicatieLeveringAutorisatieIdMatchtNietMetLeveringautorisatie() {
        int leveringsautorisatieId = 10;
        final String afnemerCode = "000123";
        Autorisatiebundel autorisatiebundel = maakAutorisatiebundel(leveringsautorisatieId, afnemerCode, SoortDienst.VERWIJDERING_AFNEMERINDICATIE);
        final MetaObject persoon = TestBuilders.maakIngeschrevenPersoon()
                .metObject(TestBuilders.maakAfnemerindicatie(101, afnemerCode))
                .build();
        final Persoonslijst persoonslijst = new Persoonslijst(persoon, 0L);
        final R1401Regel regel = new R1401Regel(persoonslijst, autorisatiebundel);
        Assert.assertEquals(Regel.R1401, regel.valideer().getRegel());
    }

    @Test
    public final void testMeldingWantAfnemerindicatieAfnemerCodeMatchtNietMetLeveringautorisatie() {
        int leveringsautorisatieId = 10;
        final String afnemerCode = "000123";
        Autorisatiebundel autorisatiebundel = maakAutorisatiebundel(leveringsautorisatieId, afnemerCode, SoortDienst.VERWIJDERING_AFNEMERINDICATIE);
        final MetaObject persoon = TestBuilders.maakIngeschrevenPersoon()
                .metObject(TestBuilders.maakAfnemerindicatie(leveringsautorisatieId, "001234"))
                .build();
        final Persoonslijst persoonslijst = new Persoonslijst(persoon, 0L);
        final R1401Regel regel = new R1401Regel(persoonslijst, autorisatiebundel);
        Assert.assertEquals(Regel.R1401, regel.valideer().getRegel());
    }


    @Test
    public final void testVoerRegelUitGeenMeldingenDienstIsNietRelevant() {
        final int leveringsautorisatieId = 10;
        final String afnemerCode = "000123";
        final SoortDienst soortDienst = SoortDienst.ATTENDERING;
        final PartijRol partijRol = new PartijRol(TestPartijBuilder.maakBuilder().metId(1).metCode(afnemerCode).build(), Rol.AFNEMER);

        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(1, soortDienst);
        leveringsautorisatie.setId(leveringsautorisatieId);
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);

        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(tla, AutAutUtil.zoekDienst(leveringsautorisatie, soortDienst));

        final Persoonslijst persoon = maakHuidigeSituatie();
        final R1401Regel regel = new R1401Regel(persoon, autorisatiebundel);
        final Melding melding = regel.valideer();
        Assert.assertNull(melding);
    }

    @Test
    public final void testVoerRegelUitGeenMeldingenAbonnementIsNull() {
        final Persoonslijst persoon = maakHuidigeSituatie();
        final R1401Regel regel = new R1401Regel(persoon, null);
        final Melding melding = regel.valideer();
        Assert.assertNull(melding);
    }

    @Test
    public final void testVoerRegelUitGeenAfnemerIndicatie() {
        Autorisatiebundel autorisatiebundel = maakAutorisatiebundel(1, "000001", SoortDienst.VERWIJDERING_AFNEMERINDICATIE);
        final Persoonslijst persoon = maakHuidigeSituatie();
        final R1401Regel regel = new R1401Regel(persoon, autorisatiebundel);
        final Melding melding = regel.valideer();
        Assert.assertEquals(Regel.R1401, melding.getRegel());
        Assert.assertEquals(SoortMelding.FOUT, melding.getSoort());
    }

    @Test
    public final void testVoerRegelUitGeenAfnemerIndicatieMaarGeenRelevanteDienst() {
        Autorisatiebundel autorisatiebundel = maakAutorisatiebundel(1, "000001", SoortDienst.ATTENDERING);
        final Persoonslijst persoon = maakHuidigeSituatie();
        final R1401Regel regel = new R1401Regel(persoon, autorisatiebundel);
        final Melding melding = regel.valideer();
        Assert.assertNull(melding);
    }

    @Test
    public final void testVoerRegelUitGeenAfnemerIndicatieMaarGeenRelevanteDienstSPZonderAfnemerindicatieDienst() {
        Autorisatiebundel autorisatiebundel = maakAutorisatiebundel(1, "000001", SoortDienst.SYNCHRONISATIE_PERSOON);
        final Persoonslijst persoon = maakHuidigeSituatie();
        final R1401Regel regel = new R1401Regel(persoon, autorisatiebundel);
        final Melding melding = regel.valideer();
        Assert.assertNull(melding);
    }

    @Test
    public final void testVoerRegelUitGeenAfnemerIndicatieMaarGeenRelevanteDienstSPMetAfnemerindicatieDienst() {
        Autorisatiebundel autorisatiebundel = maakAutorisatiebundel(1, "000001", SoortDienst.SYNCHRONISATIE_PERSOON);
        final Dienstbundel dienstbundel = autorisatiebundel.getLeveringsautorisatie().getDienstbundelSet().iterator().next();
        dienstbundel.getDienstSet().add(new Dienst(dienstbundel, SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE));
        final Persoonslijst persoon = maakHuidigeSituatie();
        final R1401Regel regel = new R1401Regel(persoon, autorisatiebundel);
        final Melding melding = regel.valideer();
        Assert.assertEquals(Regel.R1401, melding.getRegel());
        Assert.assertEquals(SoortMelding.FOUT, melding.getSoort());
    }


    /**
     * Maakt de huidige situatie.
     * @return De persoon view.
     */
    private Persoonslijst maakHuidigeSituatie() {
        return new Persoonslijst(TestBuilders.maakIngeschrevenPersoon().build(), 0L);
//        final MetaObject persoon = MetaObject
//            .maakBuilder()
//            .metObjectElement(ElementConstants.PERSOON)
//            .build();
//        return new Persoonslijst(persoon);
    }

    private Autorisatiebundel maakAutorisatiebundel(final int leveringsautorisatieId, final String afnemerCode, final SoortDienst soortDienst) {
        final PartijRol partijRol = new PartijRol(TestPartijBuilder.maakBuilder().metId(1).metCode(afnemerCode).build(), Rol.AFNEMER);

        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(1, soortDienst);
        leveringsautorisatie.setId(leveringsautorisatieId);
        leveringsautorisatie.setDatumIngang(DatumUtil.gisteren());
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);

        return new Autorisatiebundel(tla, AutAutUtil.zoekDienst(leveringsautorisatie, soortDienst));
    }
}

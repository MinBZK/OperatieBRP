/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

//package nl.bzk.brp.delivery.algemeen;
//
//import com.google.common.collect.Lists;
//import java.time.ZonedDateTime;
//import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
//import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
//import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
//import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
//import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
//import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
//import nl.bzk.algemeenbrp.util.common.DatumUtil;
//import nl.bzk.brp.delivery.algemeen.writer.BerichtTestUtil;
//import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
//import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
//import nl.bzk.brp.domain.berichtmodel.VerwerkPersoonBericht;
//import nl.bzk.brp.service.algemeen.StapException;
//import org.junit.Assert;
//import org.junit.Test;
//
///**
// * MaakPersoonBerichtServiceImplTest.
// */
//public class MaakPersoonBerichtServiceImplTest extends BerichtTestUtil {
//    private MaakPersoonBerichtServiceImpl service = new MaakPersoonBerichtServiceImpl();
//
//    @Test
//    public void testMaakPersoonBericht() throws StapException {
//
//        //@formatter:off
//        final BasisBerichtGegevens basisBerichtGegevens = BasisBerichtGegevens.builder()
//                .metTijdstipRegistratie(ZonedDateTime.of(1920, 1, 3, 0, 0, 0, 0, DatumUtil.BRP_ZONE_ID))
//                .metParameters()
//                .metSoortSynchronisatie(SoortSynchronisatie.MUTATIE_BERICHT)
//                .metDienst(new Dienst(new Dienstbundel(new Leveringsautorisatie(Stelsel.BRP, false)), SoortDienst
//                    .MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING))
//            .eindeParameters()
//            .metStuurgegevens()
//                .metTijdstipVerzending(getVasteDatum())
//                .metReferentienummer("123")
//                .metCrossReferentienummer("123")
//                .metZendendePartij(TestPartijBuilder.maakBuilder().metCode("000123").build())
//                .metOntvangendePartij(TestPartijBuilder.maakBuilder().metCode("000456").build())
//                .metZendendeSysteem("BRP")
//            .eindeStuurgegevens().build();
//        final VerwerkPersoonBericht bericht = new VerwerkPersoonBericht(basisBerichtGegevens, null, Lists.newArrayList());
//        //@formatter:on
//        final String xml = service.maakPersoonBericht(bericht);
//        Assert.assertNotNull(xml);
//
//    }
//}

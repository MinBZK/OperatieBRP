/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import org.junit.Before;
import org.junit.Test;

public class VerstrekkingsbeperkingServiceImplTest {

    private final VerstrekkingsbeperkingServiceImpl verstrekkingsbeperkingService
            = new VerstrekkingsbeperkingServiceImpl();

    private static final String PARTIJ_CODE = "000136";

    private static final Partij VERSTREKKINGSBEPERKING_MOGELIJK_JA_PARTIJ = TestPartijBuilder.maakBuilder()
            .metCode(PARTIJ_CODE)
            .metIndicatieVerstrekkingsbeperkingMogelijk(true)
            .build();

    private static final Partij VERSTREKKINGSBEPERKING_MOGELIJK_NEE_PARTIJ = TestPartijBuilder.maakBuilder()
            .metCode(PARTIJ_CODE)
            .metIndicatieVerstrekkingsbeperkingMogelijk(false)
            .build();

    @Before
    public void voorTest() {
        BrpNu.set();
    }

    @Test
    public void testGeenVerstrekkingsbeperking() {
        final Persoonslijst persoonslijst = maakPersoonMetVolledigeVerstrekkingsbeperking(false);
        final boolean verstrekkingsbeperkingAanwezig =
                verstrekkingsbeperkingService.heeftGeldigeVerstrekkingsbeperking(persoonslijst, VERSTREKKINGSBEPERKING_MOGELIJK_JA_PARTIJ);

        assertFalse(verstrekkingsbeperkingAanwezig);

        final RegelValidatie regelValidatie = verstrekkingsbeperkingService
                .maakRegelValidatie(persoonslijst, VERSTREKKINGSBEPERKING_MOGELIJK_JA_PARTIJ);
        assertNull(regelValidatie.valideer());
    }

    @Test
    public void testVolledigeVerstrekkingsbeperking() {

        Persoonslijst persoonslijst = maakPersoonMetVolledigeVerstrekkingsbeperking(true);
        final boolean verstrekkingsbeperkingAanwezig = verstrekkingsbeperkingService.heeftGeldigeVerstrekkingsbeperking(
                persoonslijst, VERSTREKKINGSBEPERKING_MOGELIJK_JA_PARTIJ);

        assertTrue(verstrekkingsbeperkingAanwezig);
        final RegelValidatie regelValidatie = verstrekkingsbeperkingService
                .maakRegelValidatie(persoonslijst, VERSTREKKINGSBEPERKING_MOGELIJK_JA_PARTIJ);
        assertNotNull(regelValidatie.valideer());
    }

    @Test
    public void testWelVolledigeVerstrekkingsBeperkingPersoonMaarNietMogelijkOpPartij() {

        final Persoonslijst persoonslijst = maakPersoonMetVolledigeVerstrekkingsbeperking(true);
        final boolean verstrekkingsbeperkingAanwezig = verstrekkingsbeperkingService
                .heeftGeldigeVerstrekkingsbeperking(persoonslijst, VERSTREKKINGSBEPERKING_MOGELIJK_NEE_PARTIJ);

        assertFalse(verstrekkingsbeperkingAanwezig);
    }

    @Test
    public void testVerstrekkingsbeperkingOpPartij() {
        final Persoonslijst persoonslijst = maakPersoonMetVerstrekkingsbeperkingOpPartij(PARTIJ_CODE);
        final boolean verstrekkingsbeperkingAanwezig = verstrekkingsbeperkingService.heeftGeldigeVerstrekkingsbeperking(
                persoonslijst, VERSTREKKINGSBEPERKING_MOGELIJK_JA_PARTIJ);

        assertTrue(verstrekkingsbeperkingAanwezig);
    }

    @Test
    public void testVerstrekkingsbeperkingOpPartijMaarNietMogelijk() {
        final Persoonslijst persoonslijst = maakPersoonMetVerstrekkingsbeperkingOpPartij(PARTIJ_CODE);
        final boolean verstrekkingsbeperkingAanwezig = verstrekkingsbeperkingService.heeftGeldigeVerstrekkingsbeperking(
                persoonslijst, VERSTREKKINGSBEPERKING_MOGELIJK_NEE_PARTIJ);

        assertFalse(verstrekkingsbeperkingAanwezig);
    }

    @Test
    public void testVerstrekkingsbeperkingOpAnderePartij() {
        final Persoonslijst persoonslijst = maakPersoonMetVerstrekkingsbeperkingOpPartij(PARTIJ_CODE + 1);
        final boolean verstrekkingsbeperkingAanwezig = verstrekkingsbeperkingService.heeftGeldigeVerstrekkingsbeperking(
                persoonslijst, VERSTREKKINGSBEPERKING_MOGELIJK_JA_PARTIJ);

        assertFalse(verstrekkingsbeperkingAanwezig);
    }


    private Persoonslijst maakPersoonMetVerstrekkingsbeperkingOpPartij(String partijCode) {
        //@formatter:off
        final MetaObject persoon = MetaObject.maakBuilder()
                .metObjectElement(Element.PERSOON)
                .metId(1)
                .metObject()
                    .metObjectElement(Element.PERSOON_VERSTREKKINGSBEPERKING)
                    .metGroep()
                        .metGroepElement(Element.PERSOON_VERSTREKKINGSBEPERKING_IDENTITEIT)
                            .metRecord()
                                .metId(2)
                                .metAttribuut()
                                    .metType(Element.PERSOON_VERSTREKKINGSBEPERKING_PARTIJCODE)
                                    .metWaarde(partijCode)
                                .eindeAttribuut()
                                .metActieInhoud(TestVerantwoording.maakActie(1, ZonedDateTime.from(BrpNu.get().getDatum().toInstant().atZone(ZoneId.of("UTC")))))
                            .eindeRecord()
                    .eindeGroep()
                .eindeObject()
                .build();
        //@formatter:on
        return new Persoonslijst(persoon, 0L);
    }

    private Persoonslijst maakPersoonMetVolledigeVerstrekkingsbeperking(boolean verstrekkingsbeperkingWaarde) {
        //@formatter:off
        final MetaObject persoon = MetaObject.maakBuilder()
            .metObjectElement(Element.PERSOON)
            .metId(1)
            .metObject()
                .metObjectElement(Element.PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING)
                .metGroep()
                    .metGroepElement(Element.PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_STANDAARD)
                        .metRecord()
                            .metId(1)
                            .metAttribuut()
                                .metType(Element.PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_WAARDE)
                                .metWaarde(verstrekkingsbeperkingWaarde)
                            .eindeAttribuut()
                            .metActieInhoud(TestVerantwoording.maakActie(1, ZonedDateTime.from(BrpNu.get().getDatum().toInstant().atZone(ZoneId.of("UTC")))))
                        .eindeRecord()
                .eindeGroep()
            .eindeObject()
            .build();
        //@formatter:on
        return new Persoonslijst(persoon, 0L);
    }

}

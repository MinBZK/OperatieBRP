/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.bepaling;

import static nl.bzk.brp.domain.element.ElementHelper.getGroepElement;

import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * StatischePersoonGegevensBepalingServiceImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class StatischePersoongegevensBepalingServiceImplTest {

    @Mock
    private MutatieleveringDeltabepalingService mutatieleveringDeltabepalingService;

    @Mock
    private MutatieleveringVerwerkingssoortService mutatieleveringVerwerkingssoortService;

    @Mock
    private BepaalPreRelatieGegevensService bepaalPreRelatieGegevensService;

    @Mock
    private BepaalVolgnummerService bepaalVolgnummerService;

    @InjectMocks
    private StatischePersoongegevensBepalingServiceImpl statischePersoonGegevensBepalingService;

    @Test
    public void testStatischeGegevensBepalingVoorMutatielevering() {
        final ZonedDateTime waarde = DatumUtil.nuAlsZonedDateTime();
        //@formatter:off
        final MetaObject handelingMeta = MetaObject.maakBuilder().metObject()
            .metObjectElement(Element.ADMINISTRATIEVEHANDELING.getId())
            .metId(1)
            .metGroep()
                .metGroepElement(getGroepElement(Element.ADMINISTRATIEVEHANDELING_IDENTITEIT))
                .metRecord()
                    .metAttribuut(Element.ADMINISTRATIEVEHANDELING_PARTIJCODE.getId(), "000001")
                .eindeRecord()
            .eindeGroep()
            .metObject()
                .metObjectElement(Element.ACTIE.getId())
                .metId(1)
                .metGroep()
                    .metGroepElement(getGroepElement(Element.ACTIE_IDENTITEIT))
                    .metRecord()
                        .metAttribuut(Element.ACTIE_PARTIJCODE.getId(), "000001")
                        .metAttribuut(Element.ACTIE_TIJDSTIPREGISTRATIE.getId(), waarde)
                    .eindeRecord()
                .eindeGroep()
            .eindeObject()
        .build();
        //@formatter:on
        final AdministratieveHandeling handeling = AdministratieveHandeling.converter().converteer(handelingMeta);

        final Persoonslijst persoonsLijst = new Persoonslijst(TestBuilders.LEEG_PERSOON, 0L);
        statischePersoonGegevensBepalingService.bepaal(persoonsLijst, true);

        Mockito.verify(mutatieleveringDeltabepalingService, Mockito.times(1)).execute(persoonsLijst);
        Mockito.verify(mutatieleveringVerwerkingssoortService, Mockito.times(1)).execute(persoonsLijst);
        Mockito.verify(bepaalPreRelatieGegevensService, Mockito.times(1)).bepaal(persoonsLijst);
        Mockito.verify(bepaalVolgnummerService, Mockito.times(1)).bepaalVolgnummers(persoonsLijst);
    }

    @Test
    public void testStatischeGegevensBepalingGeenHandeling() {

        final Persoonslijst persoonsLijst = new Persoonslijst(TestBuilders.LEEG_PERSOON, 0L);
        statischePersoonGegevensBepalingService.bepaal(persoonsLijst, false);

        Mockito.verify(mutatieleveringDeltabepalingService, Mockito.never()).execute(persoonsLijst);
        Mockito.verify(mutatieleveringVerwerkingssoortService, Mockito.never()).execute(persoonsLijst);
        Mockito.verify(bepaalPreRelatieGegevensService, Mockito.times(1)).bepaal(persoonsLijst);
        Mockito.verify(bepaalVolgnummerService, Mockito.times(1)).bepaalVolgnummers(persoonsLijst);
    }
}

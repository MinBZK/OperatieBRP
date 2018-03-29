/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.autorisatie;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorieTest;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.autorisatie.BrpProtocolleringsniveauCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.autorisatie.BrpStelselCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpLeveringsautorisatieInhoud;
import nl.bzk.migratiebrp.conversie.model.testutils.EqualsAndHashcodeTester;
import org.junit.Test;

public class BrpAutorisatieTest {

    @Test
    public void test() throws NoSuchMethodException, IllegalAccessException {
        final BrpAutorisatie subject = maak("000001");
        final BrpAutorisatie equals = maak("000001");
        final BrpAutorisatie different = maak("000002");

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(subject, equals, different);

    }

    public static BrpAutorisatie maak(final String partij) {
        return new BrpAutorisatie(new BrpPartijCode(partij), new BrpBoolean(true),
                Collections.singletonList(new BrpLeveringsautorisatie(BrpStelselCode.GBA, false, null, null)));
    }

    @Test
    public void sorteer() {

        final List<BrpLeveringsautorisatie> lijst = new LinkedList<>();
        lijst.add(new BrpLeveringsautorisatie(BrpStelselCode.GBA, false, null, null));
        lijst.add(new BrpLeveringsautorisatie(BrpStelselCode.GBA, true, null, null));

        final BrpStapel<BrpLeveringsautorisatieInhoud>
                levStapel = createLevAutStapel("een", BrpProtocolleringsniveauCode.GEEN_BEPERKINGEN, false, false, "A", 20010101, null, "toel");
        lijst.add(new BrpLeveringsautorisatie(BrpStelselCode.GBA, true, levStapel, null));

        final BrpStapel<BrpLeveringsautorisatieInhoud>
                levStapel2 = createLevAutStapel("een", BrpProtocolleringsniveauCode.GEEN_BEPERKINGEN, false, false, "A", 20010101, null, "toel");
        lijst.add(new BrpLeveringsautorisatie(BrpStelselCode.GBA, true, levStapel2, null));

        BrpAutorisatie autorisatie = new BrpAutorisatie(new BrpPartijCode("000001"), new BrpBoolean(true), lijst);
        autorisatie.sorteer();
    }

    private BrpStapel<BrpLeveringsautorisatieInhoud> createLevAutStapel(final String naam, final BrpProtocolleringsniveauCode protNiv,
                                                                        final boolean indiAliasSoort,
                                                                        final boolean iniBlok, final String popBeperk, final Integer dIngang,
                                                                        final Integer dEinde,
                                                                        final String toelichting) {
        final List<BrpGroep<BrpLeveringsautorisatieInhoud>> groepen = new LinkedList<>();
        final BrpLeveringsautorisatieInhoud inhoud = new BrpLeveringsautorisatieInhoud(
                naam,
                protNiv,
                indiAliasSoort,
                iniBlok,
                popBeperk,
                dIngang == null ? null : new BrpDatum(dIngang, null),
                dEinde == null ? null : new BrpDatum(dEinde, null),
                toelichting);
        final BrpGroep<BrpLeveringsautorisatieInhoud> groep = new BrpGroep<>(inhoud, BrpHistorieTest.createdefaultInhoud(), null, null, null);
        groepen.add(groep);
        return new BrpStapel<>(groepen);
    }
}


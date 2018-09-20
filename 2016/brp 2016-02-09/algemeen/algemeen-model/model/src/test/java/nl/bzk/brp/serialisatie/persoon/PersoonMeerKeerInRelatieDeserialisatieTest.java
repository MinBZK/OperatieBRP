/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie.persoon;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.hisvolledig.impl.kern.FamilierechtelijkeBetrekkingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.KindHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.OuderHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.serialisatie.AbstractTestPersoonBuilderDeserialisatie;
import nl.bzk.brp.util.hisvolledig.kern.FamilierechtelijkeBetrekkingHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.KindHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.OuderHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Omdat een persoon nu (na het fundamentele issue rond betrokkenheden/relaties) meer keer in een
 * relatie betrokken kan zijn, is het nodig om deze situatie in de BLOB te testen.
 *
 * Het kan voorkomen dat door erkenning door een vader zijn eerste betrokkenheid komt
 * te vervallen omdat deze niet correct blijkt te zijn en er een nieuwe wordt geregistreerd.
 */
public class PersoonMeerKeerInRelatieDeserialisatieTest extends AbstractTestPersoonBuilderDeserialisatie {

    @Override
    protected PersoonHisVolledigImpl maakPersoon() {
        PersoonHisVolledigImpl kind = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();
        PersoonHisVolledigImpl vader = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();
        PersoonHisVolledigImpl moeder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();

        ReflectionTestUtils.setField(vader, "iD", 1);
        ReflectionTestUtils.setField(moeder, "iD", 2);
        ReflectionTestUtils.setField(kind, "iD", 3);

        ActieModel familieActie = maakActie();
        FamilierechtelijkeBetrekkingHisVolledigImpl familie = new FamilierechtelijkeBetrekkingHisVolledigImplBuilder()
            .nieuwStandaardRecord(familieActie)
            .eindeRecord().build();
        ReflectionTestUtils.setField(familie, "iD", 99);

        KindHisVolledigImpl kindBetr = new KindHisVolledigImplBuilder(familie, kind)
            .metVerantwoording(familieActie)
            .build();
        ReflectionTestUtils.setField(kindBetr, "iD", 20);

        OuderHisVolledigImpl moederBetr = new OuderHisVolledigImplBuilder(familie, moeder)
            .metVerantwoording(familieActie)
            .nieuwOuderschapRecord(familieActie)
            .indicatieOuder(Ja.J)
            .eindeRecord(21)
            .build();
        ReflectionTestUtils.setField(moederBetr, "iD", 21);

        OuderHisVolledigImpl ouder1 = new OuderHisVolledigImplBuilder(familie, vader)
            .metVerantwoording(familieActie)
            .nieuwOuderschapRecord(familieActie)
            .indicatieOuder(Ja.J)
            .eindeRecord(123)
            .build();
        ReflectionTestUtils.setField(ouder1, "iD", 123);

        OuderHisVolledigImpl ouder2 = new OuderHisVolledigImplBuilder(familie, vader)
            .metVerantwoording(familieActie)
            .nieuwOuderschapRecord(familieActie)
            .indicatieOuder(Ja.J)
            .eindeRecord(125)
            .build();
        ReflectionTestUtils.setField(ouder2, "iD", 125);

        return vader;
    }
}

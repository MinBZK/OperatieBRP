/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.functie;

import static nl.bzk.brp.domain.expressie.util.TestUtils.assertExceptie;

import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMigratie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.expressie.Context;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.expressie.ExpressieRuntimeException;
import nl.bzk.brp.domain.expressie.parser.ExpressieParser;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import org.junit.Assert;
import org.junit.Test;

/**
 * FunctieGewijzigdTest.
 */
public class GewijzigdFunctieTest {

    private static final String NIEUW = "nieuw";
    private static final String OUD = "oud";

    @Test
    public void testSignaturen() {
        assertExceptie("GEWIJZIGD()", "De functie GEWIJZIGD kan niet overweg met de argumenten: []");
    }

    @Test(expected = ExpressieRuntimeException.class)
    public void testGroepWortNietOndersteund() throws ExpressieException {
        final String expressieStr = "GEWIJZIGD(oud, nieuw, [Persoon.Reisdocument.Standaard])";
        final Expressie expressie = ExpressieParser.parse(expressieStr);
        final Context context = maakContextVoorNieuwPersoon();
        expressie.evalueer(context).alsBoolean();
    }

    @Test
    public void testGewijzigdTweeArgumenten() throws ExpressieException {
        final String expressieStr = "GEWIJZIGD(oud, nieuw)";
        final Expressie expressie = ExpressieParser.parse(expressieStr);
        final Context context = maakContextMetVerschillendePersonen();
        Assert.assertTrue(expressie.evalueer(context).alsBoolean());
    }

    @Test
    public void testOnGewijzigdTweeArgumenten() throws ExpressieException {
        final String expressieStr = "GEWIJZIGD(oud, nieuw)";
        final Expressie expressie = ExpressieParser.parse(expressieStr);
        final Context context = maakContextZelfdePersoon();
        Assert.assertFalse(expressie.evalueer(context).alsBoolean());
    }

    @Test
    public void testGewijzigdMetVoorwaarde() throws ExpressieException {
        final String expressieStr = "GEWIJZIGD(ALS(oud.Migratie.SoortCode=\"I\", oud.Migratie.LandGebiedCode, NULL), ALS(nieuw.Migratie.SoortCode=\"I\","
                + "nieuw"
                + ".Migratie.LandGebiedCode,NULL))";
        final Expressie expressie = ExpressieParser.parse(expressieStr);
        final Context context = maakContextMetVerschillendePersonen();
        Assert.assertTrue(expressie.evalueer(context).alsBoolean());
    }

    @Test
    public void testOnGewijzigdMetVoorwaarde() throws ExpressieException {
        final String expressieStr = "GEWIJZIGD(ALS(oud.Migratie.SoortCode=\"I\",oud.Migratie.LandGebiedCode,NULL), "
                + "ALS(nieuw.Migratie.SoortCode=\"I\",nieuw.Migratie.LandGebiedCode,NULL))";
        final Expressie expressie = ExpressieParser.parse(expressieStr);
        final Context context = maakContextZelfdePersoon();
        Assert.assertFalse(expressie.evalueer(context).alsBoolean());
    }

    @Test
    public void testGewijzigdObject() throws ExpressieException {
        final Expressie expressie = ExpressieParser.parse("GEWIJZIGD(oud, nieuw, [Reisdocumenten])");
        final Context context = maakContextMetVerschillendePersonen();
        Assert.assertTrue(expressie.evalueer(context).alsBoolean());
    }

    @Test
    public void testGewijzigdAttribuut() throws ExpressieException {
        final Expressie expressie = ExpressieParser.parse("GEWIJZIGD(oud, nieuw, [Persoon.Reisdocument.AanduidingInhoudingVermissingCode])");
        final Context context = maakContextMetVerschillendePersonen();
        Assert.assertTrue(expressie.evalueer(context).alsBoolean());
    }

    @Test
    public void testOnGewijzigdObject() throws ExpressieException {
        final Expressie expressie = ExpressieParser.parse("GEWIJZIGD(oud, nieuw, [Reisdocumenten])");
        final Context context = maakContextZelfdePersoon();
        Assert.assertFalse(expressie.evalueer(context).alsBoolean());
    }

    @Test
    public void testOnGewijzigdAttribuut() throws ExpressieException {
        final Expressie expressie = ExpressieParser.parse("GEWIJZIGD(oud, nieuw, [Persoon.Reisdocument.AanduidingInhoudingVermissingCode])");
        final Context context = maakContextZelfdePersoon();
        Assert.assertFalse(expressie.evalueer(context).alsBoolean());
    }

    @Test
    public void testNieuwPersoonBevatAttribuut() throws ExpressieException {
        final Expressie expressie = ExpressieParser.parse("GEWIJZIGD(oud, nieuw, [Persoon.Reisdocument.AanduidingInhoudingVermissingCode])");
        final Context context = maakContextVoorNieuwPersoon();
        Assert.assertTrue(expressie.evalueer(context).alsBoolean());
    }

    @Test
    public void testNieuwPersoonBevatObject() throws ExpressieException {
        final Expressie expressie = ExpressieParser.parse("GEWIJZIGD(oud, nieuw, [Persoon.Reisdocument])");
        final Context context = maakContextVoorNieuwPersoon();
        Assert.assertTrue(expressie.evalueer(context).alsBoolean());
    }

    @Test
    public void testNieuwPersoonBevatAttribuutNiet() throws ExpressieException {
        final Expressie expressie = ExpressieParser.parse("GEWIJZIGD(oud, nieuw, [Persoon.Reisdocument.Nummer])");
        final Context context = maakContextVoorNieuwPersoon();
        Assert.assertFalse(expressie.evalueer(context).alsBoolean());
    }

    @Test
    public void testNieuwPersoonBevatObjectNiet() throws ExpressieException {
        final Expressie expressie = ExpressieParser.parse("GEWIJZIGD(oud, nieuw, [Persoon.Adres])");
        final Context context = maakContextVoorNieuwPersoon();
        Assert.assertFalse(expressie.evalueer(context).alsBoolean());
    }

    private Context maakContextMetVerschillendePersonen() {
        final Context context = new Context();
        context.addProperty(OUD, maakPersoon1());
        context.addProperty(NIEUW, maakPersoon2());
        return context;
    }

    private Context maakContextVoorNieuwPersoon() {
        final Context context = new Context();
        context.addProperty(NIEUW, maakPersoon2());
        return context;
    }

    private Context maakContextZelfdePersoon() {
        final Context context = new Context();
        final Persoonslijst persoon1 = maakPersoon1();
        context.addProperty(OUD, persoon1);
        context.addProperty(NIEUW, persoon1);
        return context;
    }

    private Persoonslijst maakPersoon1() {
        final ZonedDateTime alsZonedDateTime = DatumUtil.nuAlsZonedDateTime();
        final Actie actieInhoud = TestVerantwoording.maakActie(1, alsZonedDateTime);
        //@formatter:off
        final MetaObject persoon = MetaObject.maakBuilder()
            .metObjectElement(Element.PERSOON.getId())
            .metGroep()
                .metGroepElement(Element.PERSOON_INSCHRIJVING.getId())
                .metRecord()
                .metActieInhoud(actieInhoud)
                .metAttribuut(Element.PERSOON_INSCHRIJVING_DATUM.getId(), 20000101)
                .eindeRecord()
            .eindeGroep()
            .metGroep()
                .metGroepElement(Element.PERSOON_MIGRATIE.getId())
                .metRecord()
                .metActieInhoud(actieInhoud)
                .metAttribuut(Element.PERSOON_MIGRATIE_SOORTCODE.getId(), SoortMigratie.IMMIGRATIE.getCode())
                .metAttribuut(Element.PERSOON_MIGRATIE_LANDGEBIEDCODE.getId(), 10)
                .eindeRecord()
            .eindeGroep()
            .metObject()
                .metObjectElement(Element.PERSOON_REISDOCUMENT.getId())
                .metGroep()
                    .metGroepElement(Element.PERSOON_REISDOCUMENT_STANDAARD.getId())
                    .metRecord()
                        .metActieInhoud(actieInhoud)
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_REISDOCUMENT_AANDUIDINGINHOUDINGVERMISSINGCODE.getId()), "G")
                    .eindeRecord()
                .eindeGroep()
            .eindeObject()
        .build();
        //@formatter:on
        BrpNu.set(alsZonedDateTime);
        return new Persoonslijst(persoon, 0L);
    }

    private Persoonslijst maakPersoon2() {
        final ZonedDateTime alsZonedDateTime = DatumUtil.nuAlsZonedDateTime();
        final Actie actieInhoud = TestVerantwoording.maakActie(2, alsZonedDateTime);
        //@formatter:off
        final MetaObject persoon = MetaObject.maakBuilder()
            .metObjectElement(Element.PERSOON.getId())
            .metGroep()
                .metGroepElement(Element.PERSOON_INSCHRIJVING.getId())
                .metRecord()
                .metActieInhoud(actieInhoud)
                .metAttribuut(Element.PERSOON_INSCHRIJVING_DATUM.getId(), 20000101)
                .eindeRecord()
            .eindeGroep()
            .metGroep()
                .metGroepElement(Element.PERSOON_MIGRATIE.getId())
                .metRecord()
                .metActieInhoud(actieInhoud)
                .metAttribuut(Element.PERSOON_MIGRATIE_SOORTCODE.getId(), SoortMigratie.IMMIGRATIE.getCode())
                .metAttribuut(Element.PERSOON_MIGRATIE_LANDGEBIEDCODE.getId(), 20)
                .eindeRecord()
            .eindeGroep()
            .metObject()
                .metObjectElement(Element.PERSOON_REISDOCUMENT.getId())
                .metGroep()
                    .metGroepElement(Element.PERSOON_REISDOCUMENT_STANDAARD.getId())
                    .metRecord()
                        .metActieInhoud(actieInhoud)
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_REISDOCUMENT_AANDUIDINGINHOUDINGVERMISSINGCODE.getId()), "V")
                    .eindeRecord()
                .eindeGroep()
            .eindeObject()
        .build();
        //@formatter:on
        BrpNu.set(alsZonedDateTime);
        return new Persoonslijst(persoon, 0L);
    }
}

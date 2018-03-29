/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.MessageIdGenerator;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.bericht.model.lo3.parser.Lo3PersoonslijstParser;
import nl.bzk.migratiebrp.bericht.model.sync.factory.SyncBerichtFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.BlokkeringAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.BlokkeringInfoAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.BlokkeringInfoVerzoekType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.BlokkeringVerzoekType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.DeblokkeringAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.DeblokkeringVerzoekType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.PersoonsaanduidingType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.BlokkeringAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.BlokkeringInfoAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.BlokkeringInfoVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.BlokkeringVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.DeblokkeringAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.DeblokkeringVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesUitBrpAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesUitBrpVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpAntwoordBericht;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.model.logging.LogRegel;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import org.junit.Assert;
import org.junit.Test;

public class SyncBerichtenTest {

    private static final String
            LO3_PL_STRING =
            "01575011640110010965323958501200099577721050210005James0240004Levi03100081976103003200040599033000460300410001M6110001E8110004190181200071 "
                    +
                    "A8425851000819761030861000819761031021790110010716198531301200098057925210210008Jennifer0240009Lionheart03100081949020203200041900033000460300410001V6210008197610308110004190181200071 A8425851000819761030861000819761031031720110010107494716501200099152169050210006Walter0240004Levi03100081951010103200041901033000460300410001M6210008197610308110004190181200071 A842585100081976103086100081976103104051051000400016310003020851000819761030861000819761031052070110010805619075301200091211398390210009Elizabeth0240006Harvey03100081977101003200040599033000460300410001V06100082005033006200041901063000460301510001H8110004190181200073 A12838510008200503308610008200503310606208100082014083008200040626083000460308110004062681200072XA073407058681000819761030701000178010004000280200172005033100000000008106091000405990920008197610301010001W102000405991030008197610301110001.7210001A851000819761030861000819761031091470110010817263952101200099613112410210005Lizzy0240003Lee03100082011091403200040736033000460308110004073681200071 A8343851000820110914861000820110914100693910002003920008000000003930008000000008510008197610308610008197610311108533100011821000405998220008201401018230014Onder curatele851000820140101861000820140102121533510002PD3520009NA23010113530008201010303540006BI05993550008202010303580003185821000405998220008201010308230012Reisdocument8510008201010308610008201010311305731100011312000820100110821000419018220008201001108230001.";
    private final SyncBerichtFactory factory = SyncBerichtFactory.SINGLETON;

    private static Lo3Persoonslijst maakLo3Persoonslijst(final String plAlsString) throws BerichtSyntaxException {
        final List<Lo3CategorieWaarde> categorieen = Lo3Inhoud.parseInhoud(plAlsString);
        return new Lo3PersoonslijstParser().parse(categorieen);
    }

    @Test
    public void testBlokkering() throws ClassNotFoundException, BerichtInhoudException, IOException {
        final BlokkeringVerzoekType blokkeringVerzoekType = new BlokkeringVerzoekType();
        blokkeringVerzoekType.setANummer("1237298734");
        blokkeringVerzoekType.setProcessId("45344");
        blokkeringVerzoekType.setGemeenteNaar("190501");
        blokkeringVerzoekType.setGemeenteRegistratie("190401");
        blokkeringVerzoekType.setPersoonsaanduiding(PersoonsaanduidingType.VERHUIZEND_VAN_LO_3_NAAR_BRP);
        final BlokkeringVerzoekBericht input = new BlokkeringVerzoekBericht(blokkeringVerzoekType);
        testFormatAndParseBericht(input);
    }

    @Test
    public void testBlokkeringAntwoord() throws ClassNotFoundException, BerichtInhoudException, IOException {
        final BlokkeringAntwoordType blokkeringAntwoordType = new BlokkeringAntwoordType();
        blokkeringAntwoordType.setStatus(StatusType.OK);
        final BlokkeringAntwoordBericht input = new BlokkeringAntwoordBericht(blokkeringAntwoordType);
        testFormatAndParseBericht(input);
    }

    @Test
    public void testBlokkeringInfo() throws ClassNotFoundException, BerichtInhoudException, IOException {
        final BlokkeringInfoVerzoekType blokkeringInfoVerzoekType = new BlokkeringInfoVerzoekType();
        blokkeringInfoVerzoekType.setANummer("1237298734");
        final BlokkeringInfoVerzoekBericht input = new BlokkeringInfoVerzoekBericht(blokkeringInfoVerzoekType);
        testFormatAndParseBericht(input);
    }

    @Test
    public void testBlokkeringInfoAntwoord() throws ClassNotFoundException, BerichtInhoudException, IOException {
        final BlokkeringInfoAntwoordType blokkeringInfoAntwoordType = new BlokkeringInfoAntwoordType();
        blokkeringInfoAntwoordType.setStatus(StatusType.OK);
        blokkeringInfoAntwoordType.setProcessId("45344");
        blokkeringInfoAntwoordType.setGemeenteNaar("051801");
        blokkeringInfoAntwoordType.setPersoonsaanduiding(PersoonsaanduidingType.VERHUIZEND_VAN_LO_3_NAAR_BRP);
        final BlokkeringInfoAntwoordBericht input = new BlokkeringInfoAntwoordBericht(blokkeringInfoAntwoordType);
        testFormatAndParseBericht(input);
    }

    @Test
    public void testDeblokkering() throws ClassNotFoundException, BerichtInhoudException, IOException {
        final DeblokkeringVerzoekType deblokkeringVerzoekType = new DeblokkeringVerzoekType();
        deblokkeringVerzoekType.setANummer("1237298734");
        deblokkeringVerzoekType.setProcessId("45344");
        deblokkeringVerzoekType.setGemeenteRegistratie("190401");
        final DeblokkeringVerzoekBericht input = new DeblokkeringVerzoekBericht(deblokkeringVerzoekType);
        testFormatAndParseBericht(input);
    }

    @Test
    public void testDeblokkeringAntwoord() throws ClassNotFoundException, BerichtInhoudException, IOException {
        final DeblokkeringAntwoordType deblokkeringAntwoordType = new DeblokkeringAntwoordType();
        deblokkeringAntwoordType.setStatus(StatusType.OK);
        final DeblokkeringAntwoordBericht input = new DeblokkeringAntwoordBericht(deblokkeringAntwoordType);
        testFormatAndParseBericht(input);
    }

    @Test
    public void testLeesUitBrpVerzoekBericht() throws ClassNotFoundException, BerichtInhoudException, IOException {
        // testFormatAndParseBericht(new QueryBericht());
        testFormatAndParseBericht(new LeesUitBrpVerzoekBericht("14"));
    }

    @Test
    public void testLeesUitBrpAntwoordBericht() throws ClassNotFoundException, BerichtInhoudException, IOException, BerichtSyntaxException {
        // testFormatAndParseBericht(new QueryResponseBericht());
        testFormatAndParseBericht(new LeesUitBrpAntwoordBericht("324598734598345", SyncBerichtenTest.maakLo3Persoonslijst(LO3_PL_STRING)));
    }

    @Test
    public void testSynchroniseerNaarBrpAntwoordBericht() throws ClassNotFoundException, BerichtInhoudException, IOException {
        final SynchroniseerNaarBrpAntwoordBericht berichtMetWaarschuwing = new SynchroniseerNaarBrpAntwoordBericht();
        berichtMetWaarschuwing.setStatus(StatusType.TOEGEVOEGD);
        berichtMetWaarschuwing.setLogging(maakLogging());

        testFormatAndParseBericht(berichtMetWaarschuwing);
    }

    private Set<LogRegel> maakLogging() {
        final Set<LogRegel> result = new HashSet<>();
        result.add(
                new LogRegel(
                        new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 4, 2),
                        LogSeverity.INFO,
                        SoortMeldingCode.BIJZ_CONV_LB001,
                        Lo3ElementEnum.ELEMENT_0110));
        result.add(
                new LogRegel(new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_02, 3, 1), LogSeverity.ERROR, SoortMeldingCode.PRE001, Lo3ElementEnum.ELEMENT_0210));
        result.add(
                new LogRegel(
                        new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_03, 2, 4),
                        LogSeverity.WARNING,
                        SoortMeldingCode.STRUC_DATUM,
                        Lo3ElementEnum.ELEMENT_0310));
        result.add(
                new LogRegel(new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_04, 1, 3), LogSeverity.WARNING, SoortMeldingCode.LENGTE, Lo3ElementEnum.ELEMENT_0410));
        return result;
    }

    private void testFormatAndParseBericht(final SyncBericht bericht) throws BerichtInhoudException, IOException, ClassNotFoundException {

        bericht.setMessageId(MessageIdGenerator.generateId());

        final String formatted = bericht.format();

        Assert.assertNotNull(formatted);

        final SyncBericht parsed = factory.getBericht(formatted);

        Assert.assertNotNull(parsed);

        parsed.setMessageId(bericht.getMessageId());
        parsed.setCorrelationId(bericht.getCorrelationId());

        Assert.assertEquals(bericht, parsed);

        // Serialize
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(parsed);

        final byte[] data = baos.toByteArray();

        final ByteArrayInputStream bais = new ByteArrayInputStream(data);
        final ObjectInputStream ois = new ObjectInputStream(bais);

        final Object deserialized = ois.readObject();
        Assert.assertEquals(bericht, deserialized);

        Assert.assertEquals(bericht.getMessageId(), ((SyncBericht) deserialized).getMessageId());
    }

}

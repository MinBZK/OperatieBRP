/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.sync;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import nl.moderniseringgba.isc.esb.message.lo3.Lo3Inhoud;
import nl.moderniseringgba.isc.esb.message.lo3.parser.Lo3PersoonslijstParser;
import nl.moderniseringgba.isc.esb.message.sync.generated.BlokkeringAntwoordType;
import nl.moderniseringgba.isc.esb.message.sync.generated.BlokkeringInfoAntwoordType;
import nl.moderniseringgba.isc.esb.message.sync.generated.BlokkeringInfoVerzoekType;
import nl.moderniseringgba.isc.esb.message.sync.generated.BlokkeringVerzoekType;
import nl.moderniseringgba.isc.esb.message.sync.generated.DeblokkeringAntwoordType;
import nl.moderniseringgba.isc.esb.message.sync.generated.DeblokkeringVerzoekType;
import nl.moderniseringgba.isc.esb.message.sync.generated.PersoonsaanduidingType;
import nl.moderniseringgba.isc.esb.message.sync.generated.SearchResultaatType;
import nl.moderniseringgba.isc.esb.message.sync.generated.StatusType;
import nl.moderniseringgba.isc.esb.message.sync.impl.BlokkeringAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.BlokkeringInfoAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.BlokkeringInfoVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.BlokkeringVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.ConverteerNaarBrpAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.ConverteerNaarBrpVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.ConverteerNaarLo3AntwoordBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.ConverteerNaarLo3VerzoekBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.DeblokkeringAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.DeblokkeringVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.LeesUitBrpAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.LeesUitBrpVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.SynchronisatieStrategieAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.SynchronisatieStrategieVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.SynchroniseerNaarBrpAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpActie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroep;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpHistorie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPartijCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.moderniseringgba.migratie.conversie.model.logging.LogRegel;
import nl.moderniseringgba.migratie.conversie.model.logging.LogSeverity;
import nl.moderniseringgba.migratie.conversie.model.logging.LogType;

import org.junit.Assert;
import org.junit.Test;

public class SyncBerichtenTest {

    private static final String LO3_PL_STRING =
            "00697011640110010817238743501200092995889450210004Mart0240005Vries03100081990010103200040599033000460300410001M6110001E8110004059981200071 A9102851000819900101861000819900102021720110010192829389501200099911223340210006Jannie0240004Smit03100081969010103200041901033000460300410001M6210008199001018110004059981200071 A9102851000819900101861000819900102031750110010172625463201200093827261340210008Mitchell0240005Vries03100081970010103200041900033000460300410001M6210008199001018110004059981200071 A910285100081990010186100081990010207055681000819900101701000108010001180200170000000000000000008106091000405990920008199001011010001W102000405991030008199001011110001.7210001G851000819900101861000819900102";

    private final SyncBerichtFactory factory = SyncBerichtFactory.SINGLETON;

    @Test
    public void testBlokkering() throws Exception {
        final BlokkeringVerzoekType blokkeringVerzoekType = new BlokkeringVerzoekType();
        blokkeringVerzoekType.setANummer("1237298734");
        blokkeringVerzoekType.setProcessId("45344");
        blokkeringVerzoekType.setGemeenteNaar("1905");
        blokkeringVerzoekType.setGemeenteRegistratie("1904");
        blokkeringVerzoekType.setPersoonsaanduiding(PersoonsaanduidingType.VERHUIZEND_VAN_LO_3_NAAR_BRP);
        final BlokkeringVerzoekBericht input = new BlokkeringVerzoekBericht(blokkeringVerzoekType);
        testFormatAndParseBericht(input);
    }

    @Test
    public void testBlokkeringAntwoord() throws Exception {
        final BlokkeringAntwoordType blokkeringAntwoordType = new BlokkeringAntwoordType();
        blokkeringAntwoordType.setStatus(StatusType.OK);
        blokkeringAntwoordType.setToelichting("Super!");
        final BlokkeringAntwoordBericht input = new BlokkeringAntwoordBericht(blokkeringAntwoordType);
        testFormatAndParseBericht(input);
    }

    @Test
    public void testBlokkeringInfo() throws Exception {
        final BlokkeringInfoVerzoekType blokkeringInfoVerzoekType = new BlokkeringInfoVerzoekType();
        blokkeringInfoVerzoekType.setANummer("1237298734");
        final BlokkeringInfoVerzoekBericht input = new BlokkeringInfoVerzoekBericht(blokkeringInfoVerzoekType);
        testFormatAndParseBericht(input);
    }

    @Test
    public void testBlokkeringInfoAntwoord() throws Exception {
        final BlokkeringInfoAntwoordType blokkeringInfoAntwoordType = new BlokkeringInfoAntwoordType();
        blokkeringInfoAntwoordType.setStatus(StatusType.OK);
        blokkeringInfoAntwoordType.setProcessId("45344");
        blokkeringInfoAntwoordType.setToelichting("Super!");
        blokkeringInfoAntwoordType.setPersoonsaanduiding(PersoonsaanduidingType.VERHUIZEND_VAN_LO_3_NAAR_BRP);
        final BlokkeringInfoAntwoordBericht input = new BlokkeringInfoAntwoordBericht(blokkeringInfoAntwoordType);
        testFormatAndParseBericht(input);
    }

    @Test
    public void testDeblokkering() throws Exception {
        final DeblokkeringVerzoekType deblokkeringVerzoekType = new DeblokkeringVerzoekType();
        deblokkeringVerzoekType.setANummer("1237298734");
        deblokkeringVerzoekType.setProcessId("45344");
        deblokkeringVerzoekType.setGemeenteRegistratie("1904");
        final DeblokkeringVerzoekBericht input = new DeblokkeringVerzoekBericht(deblokkeringVerzoekType);
        testFormatAndParseBericht(input);
    }

    @Test
    public void testDeblokkeringAntwoord() throws Exception {
        final DeblokkeringAntwoordType deblokkeringAntwoordType = new DeblokkeringAntwoordType();
        deblokkeringAntwoordType.setStatus(StatusType.OK);
        deblokkeringAntwoordType.setToelichting("Super!");
        final DeblokkeringAntwoordBericht input = new DeblokkeringAntwoordBericht(deblokkeringAntwoordType);
        testFormatAndParseBericht(input);
    }

    @Test
    public void testLeesUitBrpVerzoekBericht() throws Exception {
        // testFormatAndParseBericht(new QueryBericht());
        testFormatAndParseBericht(new LeesUitBrpVerzoekBericht(14L));
    }

    @Test
    public void testLeesUitBrpAntwoordBericht() throws Exception {
        // testFormatAndParseBericht(new QueryResponseBericht());
        testFormatAndParseBericht(new LeesUitBrpAntwoordBericht("324598734598345", maakLo3Persoonslijst()));
        testFormatAndParseBericht(new LeesUitBrpAntwoordBericht("498749857439372958", "Foutmelding"));
    }

    @Test
    public void testConverteerNaarLo3VerzoekBericht() throws Exception {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();

        final BrpHistorie historie =
                new BrpHistorie(new BrpDatum(20121106), null, BrpDatumTijd.fromDatum(20121106), null);
        final BrpActie actieInhoud =
                new BrpActie(1234L, BrpSoortActieCode.CONVERSIE_GBA, new BrpPartijCode("Testgemeente",
                        Integer.valueOf(6030)), null, BrpDatumTijd.fromDatum(20121106),
                        BrpDatumTijd.fromDatum(20121106), null, 1, null);

        final List<BrpGroep<BrpIdentificatienummersInhoud>> identificatieGroepLijst =
                new ArrayList<BrpGroep<BrpIdentificatienummersInhoud>>();
        final BrpIdentificatienummersInhoud inhoud = new BrpIdentificatienummersInhoud(123456789L, 987654321L);
        final BrpGroep<BrpIdentificatienummersInhoud> identificatieGroep =
                new BrpGroep<BrpIdentificatienummersInhoud>(inhoud, historie, actieInhoud, null, null);
        identificatieGroepLijst.add(identificatieGroep);
        final BrpStapel<BrpIdentificatienummersInhoud> identificatienummersStapel =
                new BrpStapel<BrpIdentificatienummersInhoud>(identificatieGroepLijst);
        builder.identificatienummersStapel(identificatienummersStapel);

        final BrpPersoonslijst brpPersoonslijst = builder.build();
        testFormatAndParseBericht(new ConverteerNaarLo3VerzoekBericht(brpPersoonslijst));
    }

    @Test
    public void testConverteerNaarLo3AntwoordBericht() throws Exception {
        testFormatAndParseBericht(new ConverteerNaarLo3AntwoordBericht("324598734598345", maakLo3Persoonslijst()));
        testFormatAndParseBericht(new ConverteerNaarLo3AntwoordBericht("498749857439372958", "Foutmelding"));
    }

    @Test
    public void testConverteerNaarBrpVerzoekBericht() throws Exception {
        testFormatAndParseBericht(new ConverteerNaarBrpVerzoekBericht(maakLo3Persoonslijst()));
    }

    @Test
    public void testConverteerNaarBrpAntwoordBericht() throws Exception {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();

        final BrpHistorie historie =
                new BrpHistorie(new BrpDatum(20121106), null, BrpDatumTijd.fromDatum(20121106), null);
        final BrpActie actieInhoud =
                new BrpActie(1234L, BrpSoortActieCode.CONVERSIE_GBA, new BrpPartijCode("Testgemeente",
                        Integer.valueOf(6030)), null, BrpDatumTijd.fromDatum(20121106),
                        BrpDatumTijd.fromDatum(20121106), null, 1, null);

        final List<BrpGroep<BrpIdentificatienummersInhoud>> identificatieGroepLijst =
                new ArrayList<BrpGroep<BrpIdentificatienummersInhoud>>();
        final BrpIdentificatienummersInhoud inhoud = new BrpIdentificatienummersInhoud(123456789L, 987654321L);
        final BrpGroep<BrpIdentificatienummersInhoud> identificatieGroep =
                new BrpGroep<BrpIdentificatienummersInhoud>(inhoud, historie, actieInhoud, null, null);
        identificatieGroepLijst.add(identificatieGroep);
        final BrpStapel<BrpIdentificatienummersInhoud> identificatienummersStapel =
                new BrpStapel<BrpIdentificatienummersInhoud>(identificatieGroepLijst);
        builder.identificatienummersStapel(identificatienummersStapel);

        final BrpPersoonslijst brpPersoonslijst = builder.build();

        testFormatAndParseBericht(new ConverteerNaarBrpAntwoordBericht("324598734598345", brpPersoonslijst));
        testFormatAndParseBericht(new ConverteerNaarBrpAntwoordBericht("498749857439372958", "Foutmelding"));
    }

    @Test
    public void testSearchBericht() throws Exception {
        // testFormatAndParseBericht(new SearchBericht());
        testFormatAndParseBericht(new SynchronisatieStrategieVerzoekBericht(14L, 26L, 38L));
    }

    @Test
    public void testSearchResponseBericht() throws Exception {
        // testFormatAndParseBericht(new SearchResponseBericht());
        testFormatAndParseBericht(new SynchronisatieStrategieAntwoordBericht("324598734598345", "toelichting"));

        final SynchronisatieStrategieAntwoordBericht bericht = new SynchronisatieStrategieAntwoordBericht();
        bericht.setCorrelationId("22334353434");
        bericht.setStatus(StatusType.OK);
        bericht.setResultaat(SearchResultaatType.VERVANGEN);
        bericht.setToelichting("vervang gevonden pl");
        bericht.setLo3Persoonslijst(maakLo3Persoonslijst());

        testFormatAndParseBericht(bericht);
    }

    @Test
    public void testSynchroniseerNaarBrpVerzoekBericht() throws Exception {
        testFormatAndParseBericht(new SynchroniseerNaarBrpVerzoekBericht(LO3_PL_STRING));
        testFormatAndParseBericht(new SynchroniseerNaarBrpVerzoekBericht(LO3_PL_STRING, 777777L));
        testFormatAndParseBericht(new SynchroniseerNaarBrpVerzoekBericht(maakLo3Persoonslijst(), null));
        testFormatAndParseBericht(new SynchroniseerNaarBrpVerzoekBericht(maakLo3Persoonslijst(), 777777L));
    }

    @Test
    public void testSynchroniseerNaarBrpAntwoordBericht() throws Exception {
        testFormatAndParseBericht(new SynchroniseerNaarBrpAntwoordBericht("c1"));
        testFormatAndParseBericht(new SynchroniseerNaarBrpAntwoordBericht("c1", StatusType.FOUT, "Foutmelding"));
        testFormatAndParseBericht(new SynchroniseerNaarBrpAntwoordBericht("c1", StatusType.FOUT, "Foutmelding",
                maakLogging()));
    }

    private static Lo3Persoonslijst maakLo3Persoonslijst() throws Exception {
        final List<Lo3CategorieWaarde> categorieen = Lo3Inhoud.parseInhoud(LO3_PL_STRING);
        final Lo3Persoonslijst result = new Lo3PersoonslijstParser().parse(categorieen);
        return result;
    }

    private List<LogRegel> maakLogging() {
        final List<LogRegel> result = new ArrayList<LogRegel>();
        result.add(new LogRegel(new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 4, 2), LogSeverity.INFO,
                LogType.BIJZONDERE_SITUATIE, "TEST-01", "Test logregel nummer een."));
        result.add(new LogRegel(new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_02, 3, 1), LogSeverity.ERROR,
                LogType.PRECONDITIE, "TEST-02", "Test logregel nummer twee."));
        result.add(new LogRegel(new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_03, 2, 4), LogSeverity.WARNING,
                LogType.STRUCTUUR, "TEST-03", "Test logregel nummer drie."));
        result.add(new LogRegel(new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_04, 1, 3), LogSeverity.WARNING,
                LogType.VERWERKING, "TEST-04", "Test logregel nummer vier."));

        return result;
    }

    private void testFormatAndParseBericht(final SyncBericht bericht) throws Exception {
        System.out.println("Bericht: " + bericht);

        final String formatted = bericht.format();
        System.out.println("Bericht.formatted: " + formatted);
        Assert.assertNotNull(formatted);

        final SyncBericht parsed = factory.getBericht(formatted);
        System.out.println("Bericht.parsed: " + parsed);
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
        Assert.assertEquals(parsed, deserialized);

        Assert.assertEquals(parsed.getMessageId(), ((SyncBericht) deserialized).getMessageId());
    }
}

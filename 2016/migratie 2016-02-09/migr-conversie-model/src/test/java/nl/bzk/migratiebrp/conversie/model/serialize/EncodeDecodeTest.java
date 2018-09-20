/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.serialize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVoornaamInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.util.BrpVergelijker;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3Autorisatie;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3AutorisatieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieGeheimCode;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import nl.bzk.migratiebrp.conversie.model.testutils.VerplichteStapel;
import org.junit.Assert;
import org.junit.Test;

public class EncodeDecodeTest {

    public static final String KNV_07_67_20 = "KNV 07.67.20";

    @Test
    public void testLo3() {
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();

        // TODO: Vul alle stapels
        builder.persoonStapel(VerplichteStapel.createPersoonStapel());
        builder.inschrijvingStapel(VerplichteStapel.createInschrijvingStapel());

        final Lo3Persoonslijst persoonslijst = builder.build();

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PersoonslijstEncoder.encodePersoonslijst(persoonslijst, baos);

        final byte[] data = baos.toByteArray();

        final ByteArrayInputStream bais = new ByteArrayInputStream(data);
        final Lo3Persoonslijst decodedPl = PersoonslijstDecoder.decodeLo3Persoonslijst(bais);

        final StringBuilder log = new StringBuilder();
        final boolean result = Lo3StapelHelper.vergelijkPersoonslijst(log, persoonslijst, decodedPl);

        Assert.assertTrue(result);
    }

    @Test
    public void testLo3MetLegeRij() {
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();

        builder.persoonStapel(VerplichteStapel.createPersoonStapel());
        builder.inschrijvingStapel(VerplichteStapel.createInschrijvingStapel());

        // Nationaliteit met lege stapel
        builder.nationaliteitStapel(
            Lo3StapelHelper.lo3Stapel(
                Lo3StapelHelper.lo3Cat(
                    Lo3StapelHelper.lo3Nationaliteit("0050", null, null, null),
                    Lo3StapelHelper.lo3Akt(1),
                    Lo3StapelHelper.lo3His(19900101),
                    new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_54, 0, 1)),
                Lo3StapelHelper.lo3Cat(
                    Lo3StapelHelper.lo3Nationaliteit(null, null, null, null),
                    Lo3StapelHelper.lo3Akt(2),
                    Lo3StapelHelper.lo3His(20000101),
                    new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_04, 0, 0))));

        final Lo3Persoonslijst persoonslijst = builder.build();

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PersoonslijstEncoder.encodePersoonslijst(persoonslijst, baos);

        final byte[] data = baos.toByteArray();

        final ByteArrayInputStream bais = new ByteArrayInputStream(data);
        final Lo3Persoonslijst decodedPl = PersoonslijstDecoder.decodeLo3Persoonslijst(bais);

        final StringBuilder log = new StringBuilder();
        final boolean result = Lo3StapelHelper.vergelijkPersoonslijst(log, persoonslijst, decodedPl);

        Assert.assertTrue(result);
    }

    @Test
    public void testBrp() {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        final BrpGroep<BrpVoornaamInhoud> voornaam =
                BrpStapelHelper.groep(BrpStapelHelper.voornaam("Jaap", 1), BrpStapelHelper.his(20000101), BrpStapelHelper.act(1, 20000101));

        final BrpStapel<BrpVoornaamInhoud> voornaamStapel = BrpStapelHelper.stapel(voornaam);
        builder.voornaamStapel(voornaamStapel);
        // TODO: Vul alle stapels
        final BrpPersoonslijst persoonslijst = builder.build();

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PersoonslijstEncoder.encodePersoonslijst(persoonslijst, baos);

        final byte[] data = baos.toByteArray();

        final ByteArrayInputStream bais = new ByteArrayInputStream(data);
        final BrpPersoonslijst decodedPl = PersoonslijstDecoder.decodeBrpPersoonslijst(bais);

        final StringBuilder log = new StringBuilder();
        final boolean result = BrpVergelijker.vergelijkPersoonslijsten(log, persoonslijst, decodedPl, true, true);

        Assert.assertTrue(result);
    }

    @Test
    public void testLo3Autorisatie() {
        final Lo3Datum datumIngang = new Lo3Datum(20130101);
        final Lo3Datum datumIngangHis = new Lo3Datum(20120101);
        final Lo3Datum datumEindeHis = new Lo3Datum(20130101);
        final Lo3AutorisatieInhoud lo3Autorisatie = createAutorisatie("Afnemer x", datumIngang, null);
        final Lo3AutorisatieInhoud lo3AutorisatieHistorie = createAutorisatie("Afnemer y aangepast", datumIngangHis, datumEindeHis);

        // Stapel
        final List<Lo3Categorie<Lo3AutorisatieInhoud>> categorieen = new ArrayList<>();
        final Lo3Historie historieActueel = new Lo3Historie(null, datumIngang, datumIngang);
        categorieen.add(new Lo3Categorie<>(lo3Autorisatie, null, historieActueel, null));

        final Lo3Historie historieHistorie = new Lo3Historie(null, datumIngangHis, datumIngangHis);
        categorieen.add(new Lo3Categorie<>(lo3AutorisatieHistorie, null, historieHistorie, null));

        final Lo3Autorisatie lo3AutorisatieStapel = new Lo3Autorisatie(new Lo3Stapel<>(categorieen));

        // Omzetten naar XML
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XmlEncoding.encodeLo3Autorisatie(lo3AutorisatieStapel, baos);

        final byte[] data = baos.toByteArray();

        // Omzetten van XML
        final ByteArrayInputStream bais = new ByteArrayInputStream(data);
        final Lo3Autorisatie decodedLo3AutorisatieStapel = XmlEncoding.decodeLo3Autorisatie(bais);
        Assert.assertTrue(decodedLo3AutorisatieStapel.equals(lo3AutorisatieStapel));
    }

    private Lo3AutorisatieInhoud createAutorisatie(final String afnemerNaam, final Lo3Datum datumIngang, final Lo3Datum datumEinde) {
        final Lo3AutorisatieInhoud lo3Autorisatie = new Lo3AutorisatieInhoud();
        // lo3Autorisatie.setVersie(versie);
        lo3Autorisatie.setAfnemernaam(afnemerNaam);
        lo3Autorisatie.setIndicatieGeheimhouding(new Lo3IndicatieGeheimCode(0));
        lo3Autorisatie.setVerstrekkingsbeperking(0);
        lo3Autorisatie.setAdresvraagBevoegdheid(0);
        lo3Autorisatie.setAfnemernaam("afnemernaam");
        lo3Autorisatie.setAfnemersindicatie(100220);
        lo3Autorisatie.setAfnemersverstrekking("852018 852102");
        lo3Autorisatie.setBerichtaanduiding(0);
        lo3Autorisatie.setConditioneleVerstrekking(0);
        lo3Autorisatie.setEersteSelectiedatum(new Lo3Datum(20110101));
        lo3Autorisatie.setMediumAdHoc("N");
        lo3Autorisatie.setMediumAdresgeorienteerd("N");
        lo3Autorisatie.setMediumSelectie("N");
        lo3Autorisatie.setMediumSpontaan("N");
        lo3Autorisatie.setPlaatsingsbevoegdheidPersoonslijst(0);
        lo3Autorisatie.setRubrieknummerAdHoc("010110");
        lo3Autorisatie.setRubrieknummerAdresgeorienteerd("010120");
        lo3Autorisatie.setRubrieknummerSelectie("090110");
        lo3Autorisatie.setRubrieknummerSpontaan("090120");
        lo3Autorisatie.setSelectieperiode(12);
        lo3Autorisatie.setSelectiesoort(0);
        lo3Autorisatie.setSleutelrubriek("010110 010120 090110 090120");
        lo3Autorisatie.setVoorwaarderegelAdHoc(KNV_07_67_20);
        lo3Autorisatie.setVoorwaarderegelAdresgeorienteerd(KNV_07_67_20);
        lo3Autorisatie.setVoorwaarderegelSelectie(KNV_07_67_20);
        lo3Autorisatie.setVoorwaarderegelSpontaan(KNV_07_67_20);
        lo3Autorisatie.setDatumIngang(datumIngang);
        lo3Autorisatie.setDatumEinde(datumEinde);
        return lo3Autorisatie;
    }
}

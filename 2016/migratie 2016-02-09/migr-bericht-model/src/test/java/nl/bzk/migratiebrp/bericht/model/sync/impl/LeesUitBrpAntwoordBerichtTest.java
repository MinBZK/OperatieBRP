/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.MessageIdGenerator;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3PersoonslijstFormatter;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.factory.SyncBerichtFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AntwoordFormaatType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.BrpPlType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.LeesUitBrpAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.Lo3PlXmlType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLong;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

public class LeesUitBrpAntwoordBerichtTest {

    private static final Logger LOG = LoggerFactory.getLogger();
    private final SyncBerichtFactory factory = SyncBerichtFactory.SINGLETON;

    @Test
    public void testVertaal() throws IOException {
        final String berichtOrigineel = IOUtils.toString(LeesUitBrpAntwoordBerichtTest.class.getResourceAsStream("leesUitBrpAntwoord.xml"));
        final SyncBericht bericht = factory.getBericht(berichtOrigineel);

        final LeesUitBrpAntwoordBericht converteerNaarLo3AntwoordBericht = (LeesUitBrpAntwoordBericht) bericht;
        Assert.assertEquals("LeesUitBrpAntwoord", converteerNaarLo3AntwoordBericht.getBerichtType());
        Assert.assertEquals(StatusType.OK, converteerNaarLo3AntwoordBericht.getStatus());
        Assert.assertNull(converteerNaarLo3AntwoordBericht.getStartCyclus());
        Assert.assertNotNull(converteerNaarLo3AntwoordBericht.getLo3Persoonslijst());
    }

    @Test
    public void testFormat() throws BerichtInhoudException {
        final LeesUitBrpAntwoordType converteerNaarLo3AntwoordType = new LeesUitBrpAntwoordType();
        Assert.assertNull(converteerNaarLo3AntwoordType.getStatus());
        converteerNaarLo3AntwoordType.setStatus(StatusType.FOUT);
        final LeesUitBrpAntwoordBericht converteerNaarLo3AntwoordBericht = new LeesUitBrpAntwoordBericht(converteerNaarLo3AntwoordType);

        LOG.info("Geformat: {}", converteerNaarLo3AntwoordBericht.format());
        Assert.assertEquals("LeesUitBrpAntwoord", converteerNaarLo3AntwoordBericht.getBerichtType());
        Assert.assertEquals(StatusType.FOUT, converteerNaarLo3AntwoordBericht.getStatus());
    }

    @Test
    public void testLeegType() throws BerichtInhoudException {
        final LeesUitBrpAntwoordType type = new LeesUitBrpAntwoordType();
        final LeesUitBrpAntwoordBericht converteerNaarLo3AntwoordBericht = new LeesUitBrpAntwoordBericht(type);
        Assert.assertNull(converteerNaarLo3AntwoordBericht.getStatus());
        Assert.assertNull(converteerNaarLo3AntwoordBericht.getLo3Persoonslijst());
    }

    @Test
    public void testTypeZonderPl() throws BerichtInhoudException {
        final LeesUitBrpAntwoordType type = new LeesUitBrpAntwoordType();
        type.setStatus(StatusType.FOUT);
        final LeesUitBrpAntwoordBericht converteerNaarLo3AntwoordBericht = new LeesUitBrpAntwoordBericht(type);
        Assert.assertNotNull(converteerNaarLo3AntwoordBericht.getStatus());
        Assert.assertEquals(StatusType.FOUT, converteerNaarLo3AntwoordBericht.getStatus());
        Assert.assertNull(converteerNaarLo3AntwoordBericht.getLo3Persoonslijst());

        type.setLo3Pl(plAlsString(maakPersoonslijst()));
        Assert.assertNotNull(converteerNaarLo3AntwoordBericht.getLo3Persoonslijst());

    }

    @Test
    public void testBericht() throws BerichtInhoudException {
        final LeesUitBrpAntwoordBericht converteerNaarLo3AntwoordBericht = new LeesUitBrpAntwoordBericht();
        converteerNaarLo3AntwoordBericht.setStatus(StatusType.OK);

        LOG.info("Geformat: {}", converteerNaarLo3AntwoordBericht.format());
        Assert.assertEquals("LeesUitBrpAntwoord", converteerNaarLo3AntwoordBericht.getBerichtType());
        Assert.assertEquals(StatusType.OK, converteerNaarLo3AntwoordBericht.getStatus());
        Assert.assertNull(converteerNaarLo3AntwoordBericht.getLo3Persoonslijst());
    }

    @Test(expected = NullPointerException.class)
    public void testNullPointerException() throws BerichtInhoudException {
        final LeesUitBrpAntwoordBericht bericht = new LeesUitBrpAntwoordBericht();
        bericht.setStatus(null);
    }

    @Test
    public void testConvenienceBrpPl() throws BerichtInhoudException {
        final String CORRELATION_ID = "correlatie-1234";
        final LeesUitBrpAntwoordBericht bericht = new LeesUitBrpAntwoordBericht(CORRELATION_ID, maakBrpPersoonslijst());
        Assert.assertNotNull(bericht.getBrpPersoonslijst());
        Assert.assertNull(bericht.getLo3Persoonslijst());
        Assert.assertNull(bericht.getLo3PersoonslijstFromXml());

        bericht.setBrpPersoonslijst(maakBrpPersoonslijst());
        Assert.assertNotNull(bericht.getBrpPersoonslijst());
        Assert.assertNull(bericht.getLo3Persoonslijst());
        Assert.assertNull(bericht.getLo3PersoonslijstFromXml());
    }

    @Test
    public void testConvenienceLo3Pl() throws BerichtInhoudException {
        final String CORRELATION_ID = "correlatie-1234";
        final LeesUitBrpAntwoordBericht bericht = new LeesUitBrpAntwoordBericht(CORRELATION_ID, maakPersoonslijst());
        Assert.assertNull(bericht.getBrpPersoonslijst());
        Assert.assertNotNull(bericht.getLo3Persoonslijst());
        Assert.assertNull(bericht.getLo3PersoonslijstFromXml());
    }

    @Test
    public void testConvenienceLo3PlXml() throws BerichtInhoudException {
        final String CORRELATION_ID = "correlatie-1234";
        final LeesUitBrpAntwoordBericht bericht = new LeesUitBrpAntwoordBericht(CORRELATION_ID, maakPersoonslijst(), AntwoordFormaatType.LO_3_XML);
        Assert.assertNull(bericht.getBrpPersoonslijst());
        Assert.assertNull(bericht.getLo3Persoonslijst());
        Assert.assertNotNull(bericht.getLo3PersoonslijstFromXml());

        bericht.setLo3Persoonslijst(maakPersoonslijst(), AntwoordFormaatType.LO_3_XML);
        Assert.assertNull(bericht.getBrpPersoonslijst());
        Assert.assertNull(bericht.getLo3Persoonslijst());
        Assert.assertNotNull(bericht.getLo3PersoonslijstFromXml());
    }

    @Test
    public void testViaType() {
        final LeesUitBrpAntwoordType type = new LeesUitBrpAntwoordType();
        type.setBrpPl(new BrpPlType());
        type.setLo3PlXml(new Lo3PlXmlType());
        final LeesUitBrpAntwoordBericht bericht = new LeesUitBrpAntwoordBericht(type);
        Assert.assertNull(bericht.getBrpPersoonslijst());
        Assert.assertNull(bericht.getLo3PersoonslijstFromXml());
    }

    @Test
    public void testMetPl() throws BerichtInhoudException {
        final String CORRELATIE_ID = "correlatie-1234";
        final LeesUitBrpAntwoordBericht converteerNaarLo3AntwoordBericht = new LeesUitBrpAntwoordBericht(CORRELATIE_ID, maakPersoonslijst());
        converteerNaarLo3AntwoordBericht.setStatus(StatusType.OK);
        Assert.assertEquals(CORRELATIE_ID, converteerNaarLo3AntwoordBericht.getCorrelationId());
        Assert.assertNotNull(converteerNaarLo3AntwoordBericht.getLo3Persoonslijst());
        Assert.assertEquals("LeesUitBrpAntwoord", converteerNaarLo3AntwoordBericht.getBerichtType());
    }

    @Test
    public void testEquals() {
        final LeesUitBrpAntwoordType converteerNaarLo3AntwoordType = new LeesUitBrpAntwoordType();
        converteerNaarLo3AntwoordType.setStatus(StatusType.OK);
        final LeesUitBrpAntwoordBericht converteerNaarLo3AntwoordBerichtOrigineel = new LeesUitBrpAntwoordBericht(converteerNaarLo3AntwoordType);
        converteerNaarLo3AntwoordBerichtOrigineel.setMessageId(MessageIdGenerator.generateId());

        final LeesUitBrpAntwoordBericht converteerNaarLo3AntwoordBerichtKopie = new LeesUitBrpAntwoordBericht(converteerNaarLo3AntwoordType);
        final LeesUitBrpAntwoordBericht converteerNaarLo3AntwoordBerichtObjectKopie = converteerNaarLo3AntwoordBerichtOrigineel;
        final DeblokkeringAntwoordBericht deblokkeringAntwoordBericht = new DeblokkeringAntwoordBericht();

        converteerNaarLo3AntwoordBerichtKopie.setMessageId(converteerNaarLo3AntwoordBerichtOrigineel.getMessageId());
        converteerNaarLo3AntwoordBerichtKopie.setCorrelationId(converteerNaarLo3AntwoordBerichtOrigineel.getCorrelationId());

        Assert.assertTrue(converteerNaarLo3AntwoordBerichtObjectKopie.equals(converteerNaarLo3AntwoordBerichtOrigineel));
        Assert.assertFalse(converteerNaarLo3AntwoordBerichtOrigineel.equals(deblokkeringAntwoordBericht));
        Assert.assertTrue(converteerNaarLo3AntwoordBerichtKopie.equals(converteerNaarLo3AntwoordBerichtOrigineel));
        Assert.assertEquals(converteerNaarLo3AntwoordBerichtObjectKopie.hashCode(), converteerNaarLo3AntwoordBerichtOrigineel.hashCode());
        Assert.assertEquals(converteerNaarLo3AntwoordBerichtKopie.hashCode(), converteerNaarLo3AntwoordBerichtOrigineel.hashCode());
        Assert.assertEquals(converteerNaarLo3AntwoordBerichtKopie.toString(), converteerNaarLo3AntwoordBerichtOrigineel.toString());
    }

    @Test
    public void testBerichtSyntaxException() throws IOException {
        final String berichtOrigineel =
                IOUtils.toString(LeesUitBrpAntwoordBerichtTest.class.getResourceAsStream("leesUitBrpAntwoordBerichtSyntaxException.xml"));

        final SyncBericht syncBericht = factory.getBericht(berichtOrigineel);
        Assert.assertTrue(syncBericht instanceof OngeldigBericht);
    }

    protected Lo3Persoonslijst maakPersoonslijst() {

        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        // @formatter:off
        builder.persoonStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Persoon(1234L,
            null,
            "Jan",
            null,
            null,
            "Jansen",
            19700101,
            "0518",
            "6030",
            "M",
            null,
            null,
                "E"),
                Lo3StapelHelper.lo3Akt(1),
                Lo3StapelHelper.lo3His(19700101),
                new Lo3Herkomst(Lo3CategorieEnum.PERSOON, 0, 0))));

        builder.verblijfplaatsStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Verblijfplaats("0600",
            1970101,
            1970101,
            "Straat",
            15,
            "9876AA",
                "I"),
                null,
                Lo3StapelHelper.lo3His(19700101),
                new Lo3Herkomst(Lo3CategorieEnum.VERBLIJFPLAATS, 0, 0))));
        // @formatter:on

        return builder.build();
    }

    private String plAlsString(final Lo3Persoonslijst persoonslijst) {
        final Lo3PersoonslijstFormatter formatter = new Lo3PersoonslijstFormatter();
        return Lo3Inhoud.formatInhoud(formatter.format(persoonslijst));
    }

    private BrpPersoonslijst maakBrpPersoonslijst() {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();

        final BrpHistorie historie = new BrpHistorie(new BrpDatum(20121106, null), null, BrpDatumTijd.fromDatum(20121106, null), null, null);
        final BrpActie actieInhoud =
                new BrpActie(
                    1234L,
                    BrpSoortActieCode.CONVERSIE_GBA,
                    new BrpPartijCode(603001),
                    BrpDatumTijd.fromDatum(20121106, null),
                    null,
                    null,
                    1,
                    null);

        final List<BrpGroep<BrpIdentificatienummersInhoud>> identificatieGroepLijst = new ArrayList<>();
        final BrpIdentificatienummersInhoud inhoud = new BrpIdentificatienummersInhoud(new BrpLong(123456789L), new BrpInteger(987654321));
        final BrpGroep<BrpIdentificatienummersInhoud> identificatieGroep = new BrpGroep<>(inhoud, historie, actieInhoud, null, null);
        identificatieGroepLijst.add(identificatieGroep);
        final BrpStapel<BrpIdentificatienummersInhoud> identificatienummersStapel = new BrpStapel<>(identificatieGroepLijst);
        builder.identificatienummersStapel(identificatienummersStapel);

        return builder.build();
    }
}

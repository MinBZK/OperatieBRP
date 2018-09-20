/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import java.io.IOException;
import java.math.BigInteger;

import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.MessageIdGenerator;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.factory.SyncBerichtFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AkteGroepType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.GeboorteGroepType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.GeldigheidGroepType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.GeslachtGroepType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.GeslachtsaanduidingType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.IdentificatienummersGroepType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.NaamGroepType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.PersoonType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.RelatieSluitingGroepType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.RelatieSoortGroepType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.RelatieType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.RelatieType.Sluiting;
import nl.bzk.migratiebrp.bericht.model.sync.generated.SoortRelatieType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.VerwerkToevalligeGebeurtenisVerzoekType;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

public class VerwerkToevalligeGebeurtenisVerzoekBerichtTest {

    private static final String VOORNAAM_RELATIE = "Truus";
    private static final String GESLACHTSNAAM_RELATIE = "Verhulst";
    private static final String VOORNAAM = "Jan";
    private static final String GESLACHTSNAAM = "Janssen";
    private static final String LAND_SLUITING = "0630";
    private static final BigInteger DATUM_SLUITING = BigInteger.valueOf(20150101L);
    private static final String BURGER_SERVICE_NUMMER = "252523452";
    private static final String A_NUMMER = "5064118411";
    private static final String RECHTS_GEMEENTE = "0600";
    private static final String RECHTS_GEMEENTE_RELATIE = "0599";
    private static final String GEBOORTE_LAND = "0630";
    private static final String GEBOORTE_LAND_RELATIE = "0631";
    private static final String AKTE_NUMMER = "3QA1234";
    private static final String FEIT_GEMEENTE = "3333";
    private static final BigInteger GEBOORTE_DATUM = BigInteger.valueOf(19780501);
    private static final BigInteger GEBOORTE_DATUM_RELATIE = BigInteger.valueOf(19760905);
    private static final Logger LOG = LoggerFactory.getLogger();

    private final SyncBerichtFactory factory = SyncBerichtFactory.SINGLETON;

    @Test
    public void testVertaal() throws IOException {
        final String berichtOrigineel =
                IOUtils.toString(VerwerkToevalligeGebeurtenisVerzoekBerichtTest.class.getResourceAsStream("verwerkToevalligeGebeurtenisVerzoek.xml"));
        final SyncBericht bericht = factory.getBericht(berichtOrigineel);

        final VerwerkToevalligeGebeurtenisVerzoekBericht verwerkToevalligeGebeurtenisVerzoekBericht = (VerwerkToevalligeGebeurtenisVerzoekBericht) bericht;
        Assert.assertEquals(AKTE_NUMMER, verwerkToevalligeGebeurtenisVerzoekBericht.getAkte().getAktenummer());
        Assert.assertEquals("VerwerkToevalligeGebeurtenisVerzoek", verwerkToevalligeGebeurtenisVerzoekBericht.getBerichtType());
        Assert.assertEquals(null, verwerkToevalligeGebeurtenisVerzoekBericht.getStartCyclus());
    }

    @Test
    public void testFormat() throws BerichtInhoudException {
        final VerwerkToevalligeGebeurtenisVerzoekType verwerkToevalligeGebeurtenisVerzoekType = new VerwerkToevalligeGebeurtenisVerzoekType();
        verwerkToevalligeGebeurtenisVerzoekType.setAkte(maakAkteGroep());
        verwerkToevalligeGebeurtenisVerzoekType.setDoelGemeente(RECHTS_GEMEENTE);
        verwerkToevalligeGebeurtenisVerzoekType.setFamilierechtelijkeBetrekking(null);
        verwerkToevalligeGebeurtenisVerzoekType.setGeldigheid(maakGeldigheidGroep());
        verwerkToevalligeGebeurtenisVerzoekType.setNaamGeslacht(null);
        verwerkToevalligeGebeurtenisVerzoekType.setOverlijden(null);
        verwerkToevalligeGebeurtenisVerzoekType.setPersoon(maakPersoonType());
        verwerkToevalligeGebeurtenisVerzoekType.setRelatie(maakRelatieType());

        final VerwerkToevalligeGebeurtenisVerzoekBericht verwerkToevalligeGebeurtenisVerzoekBericht =
                new VerwerkToevalligeGebeurtenisVerzoekBericht(verwerkToevalligeGebeurtenisVerzoekType);
        final String geformat = verwerkToevalligeGebeurtenisVerzoekBericht.format();

        LOG.info("Geformat: {}", geformat);
        // controleer of geformat bericht weer geparsed kan worden
        final VerwerkToevalligeGebeurtenisVerzoekBericht format = (VerwerkToevalligeGebeurtenisVerzoekBericht) factory.getBericht(geformat);
        Assert.assertEquals("VerwerkToevalligeGebeurtenisVerzoek", format.getBerichtType());
        Assert.assertNotNull(format.getAkte());
        Assert.assertEquals(AKTE_NUMMER, format.getAkte().getAktenummer());
        Assert.assertNull(format.getFamilieRechtelijkeBetrekking());
        Assert.assertNotNull(format.getGeldigheid());
        Assert.assertNull(format.getOverlijden());
        Assert.assertNotNull(format.getPersoon());
        Assert.assertNotNull(format.getRelatie());
        Assert.assertNull(format.getUpdatePersoon());
    }

    private RelatieType maakRelatieType() {
        final RelatieType relatieType = new RelatieType();
        relatieType.setPersoon(maakRelatiePersoonType());
        final Sluiting sluiting = new Sluiting();
        final RelatieSluitingGroepType relatieSluiting = new RelatieSluitingGroepType();
        relatieSluiting.setDatum(DATUM_SLUITING);
        relatieSluiting.setLand(LAND_SLUITING);
        relatieSluiting.setPlaats(FEIT_GEMEENTE);
        sluiting.setSluiting(relatieSluiting);
        final RelatieSoortGroepType relatieSoort = new RelatieSoortGroepType();
        relatieSoort.setSoort(SoortRelatieType.H);
        sluiting.setSoort(relatieSoort);
        relatieType.setSluiting(sluiting);
        return relatieType;
    }

    private PersoonType maakPersoonType() {
        final PersoonType persoonType = new PersoonType();
        final GeboorteGroepType geboorte = new GeboorteGroepType();
        geboorte.setDatum(GEBOORTE_DATUM);
        geboorte.setLand(GEBOORTE_LAND);
        geboorte.setPlaats(RECHTS_GEMEENTE);
        persoonType.setGeboorte(geboorte);
        final GeslachtGroepType geslacht = new GeslachtGroepType();
        geslacht.setGeslachtsaanduiding(GeslachtsaanduidingType.M);
        persoonType.setGeslacht(geslacht);
        final IdentificatienummersGroepType identificatie = new IdentificatienummersGroepType();
        identificatie.setANummer(A_NUMMER);
        identificatie.setBurgerservicenummer(BURGER_SERVICE_NUMMER);
        persoonType.setIdentificatienummers(identificatie);
        final NaamGroepType naamGroep = new NaamGroepType();
        naamGroep.setGeslachtsnaam(GESLACHTSNAAM);
        naamGroep.setVoornamen(VOORNAAM);
        persoonType.setNaam(naamGroep);
        return persoonType;
    }

    private PersoonType maakRelatiePersoonType() {
        final PersoonType persoonType = new PersoonType();
        final GeboorteGroepType geboorte = new GeboorteGroepType();
        geboorte.setDatum(GEBOORTE_DATUM_RELATIE);
        geboorte.setLand(GEBOORTE_LAND_RELATIE);
        geboorte.setPlaats(RECHTS_GEMEENTE_RELATIE);
        persoonType.setGeboorte(geboorte);
        final GeslachtGroepType geslacht = new GeslachtGroepType();
        geslacht.setGeslachtsaanduiding(GeslachtsaanduidingType.V);
        persoonType.setGeslacht(geslacht);
        final IdentificatienummersGroepType identificatie = new IdentificatienummersGroepType();
        identificatie.setANummer(A_NUMMER);
        identificatie.setBurgerservicenummer(BURGER_SERVICE_NUMMER);
        persoonType.setIdentificatienummers(identificatie);
        final NaamGroepType naamGroep = new NaamGroepType();
        naamGroep.setGeslachtsnaam(GESLACHTSNAAM_RELATIE);
        naamGroep.setVoornamen(VOORNAAM_RELATIE);
        persoonType.setNaam(naamGroep);
        return persoonType;
    }

    private AkteGroepType maakAkteGroep() {
        final AkteGroepType akteGroep = new AkteGroepType();
        akteGroep.setAktenummer(AKTE_NUMMER);
        akteGroep.setRegistergemeente(FEIT_GEMEENTE);
        return akteGroep;
    }

    private GeldigheidGroepType maakGeldigheidGroep() {
        final GeldigheidGroepType geldigheidGroep = new GeldigheidGroepType();
        geldigheidGroep.setDatumIngang(BigInteger.valueOf(20150101));
        return geldigheidGroep;
    }

    @Test
    public void testEquals() {
        final VerwerkToevalligeGebeurtenisVerzoekType verwerkToevalligeGebeurtenisVerzoekType = new VerwerkToevalligeGebeurtenisVerzoekType();
        verwerkToevalligeGebeurtenisVerzoekType.setAkte(null);
        final VerwerkToevalligeGebeurtenisVerzoekBericht verwerkToevalligeGebeurtenisVerzoekBerichtOrigineel =
                new VerwerkToevalligeGebeurtenisVerzoekBericht(verwerkToevalligeGebeurtenisVerzoekType);
        verwerkToevalligeGebeurtenisVerzoekBerichtOrigineel.setMessageId(MessageIdGenerator.generateId());

        final VerwerkToevalligeGebeurtenisVerzoekBericht verwerkToevalligeGebeurtenisVerzoekBerichtKopie =
                new VerwerkToevalligeGebeurtenisVerzoekBericht(verwerkToevalligeGebeurtenisVerzoekType);
        final VerwerkToevalligeGebeurtenisVerzoekBericht verwerkToevalligeGebeurtenisVerzoekBerichtObjectKopie =
                verwerkToevalligeGebeurtenisVerzoekBerichtOrigineel;
        final BlokkeringInfoAntwoordBericht blokkeringInfoAntwoordBericht = new BlokkeringInfoAntwoordBericht();

        verwerkToevalligeGebeurtenisVerzoekBerichtKopie.setMessageId(verwerkToevalligeGebeurtenisVerzoekBerichtOrigineel.getMessageId());
        verwerkToevalligeGebeurtenisVerzoekBerichtKopie.setCorrelationId(verwerkToevalligeGebeurtenisVerzoekBerichtOrigineel.getCorrelationId());

        Assert.assertTrue(verwerkToevalligeGebeurtenisVerzoekBerichtObjectKopie.equals(verwerkToevalligeGebeurtenisVerzoekBerichtOrigineel));
        Assert.assertFalse(verwerkToevalligeGebeurtenisVerzoekBerichtOrigineel.equals(blokkeringInfoAntwoordBericht));
        Assert.assertTrue(verwerkToevalligeGebeurtenisVerzoekBerichtKopie.equals(verwerkToevalligeGebeurtenisVerzoekBerichtOrigineel));
        Assert.assertEquals(
            verwerkToevalligeGebeurtenisVerzoekBerichtObjectKopie.hashCode(),
            verwerkToevalligeGebeurtenisVerzoekBerichtOrigineel.hashCode());
        Assert.assertEquals(verwerkToevalligeGebeurtenisVerzoekBerichtKopie.hashCode(), verwerkToevalligeGebeurtenisVerzoekBerichtOrigineel.hashCode());
        Assert.assertEquals(verwerkToevalligeGebeurtenisVerzoekBerichtKopie.toString(), verwerkToevalligeGebeurtenisVerzoekBerichtOrigineel.toString());
    }

    @Test
    public void testSettersEnGetters() {
        final VerwerkToevalligeGebeurtenisVerzoekBericht verwerkToevalligeGebeurtenisVerzoekBericht = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        verwerkToevalligeGebeurtenisVerzoekBericht.setAkte(null);

        Assert.assertNull(verwerkToevalligeGebeurtenisVerzoekBericht.getAkte());
    }

    @Test
    public void tesBerichtSyntaxException() throws IOException {
        final String berichtOrigineel =
                IOUtils.toString(VerwerkToevalligeGebeurtenisVerzoekBerichtTest.class.getResourceAsStream("verwerkToevalligeGebeurtenisVerzoekBerichtSyntaxException.xml"));

        final SyncBericht syncBericht = factory.getBericht(berichtOrigineel);
        Assert.assertTrue(syncBericht instanceof OngeldigBericht);
    }

}

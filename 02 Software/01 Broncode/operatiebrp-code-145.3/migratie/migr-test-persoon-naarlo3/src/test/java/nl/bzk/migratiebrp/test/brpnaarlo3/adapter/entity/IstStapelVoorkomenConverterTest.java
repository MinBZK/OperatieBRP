/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.brpnaarlo3.adapter.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;

import org.junit.Test;

import org.junit.Assert;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenBeeindigingRelatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Stapel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.StapelVoorkomen;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.AdellijkeTitel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Geslachtsaanduiding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Predicaat;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.ConverterContext;

/**
 * de volgende lijst met headers kan niet worden getest, de IstStapelConvertor is final wat het onmogelijk maakt om
 * de geinjecteerde classes te mocken.
 * converter.convertInhoudelijk(ctx,IstStapelVoorkomenConverter.HEADER_SOORT_DOCUMENTATIE,soortDocument);
 * converter.convertInhoudelijk(ctx,IstStapelVoorkomenConverter.HEADER_PARTIJ,"");
 * converter.convertInhoudelijk(ctx,IstStapelVoorkomenConverter.HEADER_GEMEENTE_GEBOORTE,"");
 * converter.convertInhoudelijk(ctx,IstStapelVoorkomenConverter.HEADER_OMSCHRIJVING_LOCATIE_GEBOORTE,"");
 * onverter.convertInhoudelijk(ctx,IstStapelVoorkomenConverter.HEADER_LAND_OF_GEBIED_GEBOORTE,"");
 * converter.convertInhoudelijk(ctx,IstStapelVoorkomenConverter.HEADER_GEMEENTE_AANVANG,"");
 * converter.convertInhoudelijk(ctx,IstStapelVoorkomenConverter.HEADER_LAND_OF_GEBIED_AANVANG,"");
 * converter.convertInhoudelijk(ctx,IstStapelVoorkomenConverter.HEADER_GEMEENTE_EINDE,"");
 * converter.convertInhoudelijk(ctx,IstStapelVoorkomenConverter.HEADER_REDEN_EINDE,"");
 * converter.convertInhoudelijk(ctx,IstStapelVoorkomenConverter.HEADER_LAND_OF_GEBIED_EINDE,"");
 */
public class IstStapelVoorkomenConverterTest {
    public static final String LEEG = "leeg";
    public static final String MINIMAAL = "minimaal";

    private String volgnummer = "1";
    private AdministratieveHandeling administratieveHandeling =
            new AdministratieveHandeling(
                    new Partij("naam", "000001"),
                    SoortAdministratieveHandeling.CORRECTIE_CURATELE,
                    new Timestamp(System.currentTimeMillis()));
    private String soortDocument = "soort Document";
    private Partij partij = new Partij("naam", "000001");
    private Integer rubriek8220 = 20001111;
    private String documentOmschrijving = "soort Document";
    private Integer rubriek8310 = 010000;
    private Integer rubriek8320 = 20140101;
    private Integer rubriek8330 = 20140301;
    private Character rubriek8410 = 'O';
    private Integer rubriek8510 = 19920808;
    private Integer rubriek8610 = 19940930;
    private Integer rubriek6210 = 19660821;
    private String aktenummer = "009";
    private String anummer = "8191430945";
    private String bsn = "492475945";
    private String voornamen = "voornamen";
    private Predicaat predicaat = Predicaat.H;
    private AdellijkeTitel adellijkeTitel = AdellijkeTitel.H;
    private Geslachtsaanduiding geslachtsaanduidingBijAdellijkeTitelOfPredicaat = Geslachtsaanduiding.ONBEKEND;
    private String voorvoegsel = "voorvoegsel";
    private Character scheidingsteken = ',';
    private String geslachtsnaamstam = "geslachtsnaamstam";
    private Integer datumGeboorte = 20000101;
    private Gemeente gemeenteGeboorte = new Gemeente(null, "gemeente", "0001", new Partij("gemeenteGeboorte", "000001"));
    private String buitenlandsePlaatsGeboorte = "Marakech";
    private String omschrijvingLocatieGeboorte = "omschrijvingLocatieGeboorte";
    private LandOfGebied landOfGebiedGeboorte = new LandOfGebied("0123", "landOfGebiedGeboorte");
    private Geslachtsaanduiding geslachtsaanduiding = Geslachtsaanduiding.ONBEKEND;
    private Integer datumAanvang = 20000102;
    private Gemeente gemeenteAanvang = new Gemeente(null, "gemeente", "0001", new Partij("gemeenteAanvang", "000001"));
    private String buitenlandsePlaatsAanvang = "buitenlandsePlaatsAanvang";
    private String omschrijvingLocatieAanvang = "omschrijvingLocatieAanvang";
    private LandOfGebied landOfGebiedAanvang = new LandOfGebied("0123", "landOfGebiedAanvang");
    private RedenBeeindigingRelatie redenBeeindigingRelatie = new RedenBeeindigingRelatie('A', "redenBeeindigingRelatie");
    private Integer datumEinde = 20100101;
    private Gemeente gemeenteEinde = new Gemeente(null, "gemeente", "0001", new Partij("gemeenteEinde", "000001"));
    private String buitenlandsePlaatsEinde = "buitenlandsePlaatsEinde";
    private String omschrijvingLocatieEinde = "omschrijvingLocatieEinde";
    private LandOfGebied landOfGebiedEinde = new LandOfGebied("0123", "landOfGebiedEinde");
    private SoortRelatie soortRelatie = SoortRelatie.HUWELIJK;
    private Boolean indicatieOuder1HeeftGezag = Boolean.FALSE;
    private Boolean indicatieOuder2HeeftGezag = Boolean.FALSE;
    private Boolean indicatieDerdeHeeftGezag = Boolean.FALSE;
    private Boolean indicatieOnderCuratele = Boolean.FALSE;

    private IstStapelVoorkomenConverter converter = new IstStapelVoorkomenConverter();

    @Test(expected = NullPointerException.class)
    public void testMaakentityConvertorLeeg() throws Exception {
        ConverterContext ctx = createContext(LEEG);
        converter.maakEntity(ctx);
    }

    @Test
    public void testMaakentityConvertorMinimaal() throws Exception {
        ConverterContext ctx = createContext(MINIMAAL);
        assertEquals("context zou nog geen stapel mogen hebben", 0, ctx.getStapel(Integer.parseInt(volgnummer)).getStapelvoorkomens().size());
        converter.maakEntity(ctx);
        assertEquals("we zouden er nu 1 moeten hebben", 1, ctx.getStapel(Integer.parseInt(volgnummer)).getStapelvoorkomens().size());
    }

    @Test
    public void testMaakentityConvertorHaaderTypeMagGeenInvloedHebben() throws Exception {
        ConverterContext ctx1 = createContext(MINIMAAL);
        converter.maakEntity(ctx1);
        assertEquals("we zouden er nu 1 moeten hebben", 1, ctx1.getStapel(Integer.parseInt(volgnummer)).getStapelvoorkomens().size());
        ConverterContext ctx2 = createContext(MINIMAAL);
        converter.maakEntity(ctx2);
        converter.convertInhoudelijk(ctx2, IstStapelVoorkomenConverter.HEADER_TYPE, "niks veranderd");
        assertEquals("we zouden er nu 1 moeten hebben", 1, ctx2.getStapel(Integer.parseInt(volgnummer)).getStapelvoorkomens().size());
        assertTrue(zijnTweeStapelVoorkomensGelijk(ctx1.getStapel(Integer.parseInt(volgnummer)).getStapelvoorkomens().iterator().next(),
                ctx2.getStapel(Integer.parseInt(volgnummer)).getStapelvoorkomens().iterator().next()));
    }

    @Test
    public void testMaakentityConvertorRubriek8220() throws Exception {
        ConverterContext ctx = createContext(MINIMAAL);
        converter.convertInhoudelijk(ctx, IstStapelVoorkomenConverter.HEADER_RUBRIEK_8220, rubriek8220.toString());
        converter.maakEntity(ctx);
        StapelVoorkomen stapelVoorkomen = ctx.getStapel(Integer.parseInt(volgnummer)).getStapelvoorkomens().iterator().next();
        assertEquals(rubriek8220, stapelVoorkomen.getRubriek8220DatumDocument());
    }

    @Test
    public void testMaakentityConvertorDocumentOmschrijving() throws Exception {
        ConverterContext ctx = createContext(MINIMAAL);
        converter.convertInhoudelijk(ctx, IstStapelVoorkomenConverter.HEADER_DOCUMENTATIE_OMSCHRIJVING, documentOmschrijving);
        converter.maakEntity(ctx);
        StapelVoorkomen stapelVoorkomen = ctx.getStapel(Integer.parseInt(volgnummer)).getStapelvoorkomens().iterator().next();
        assertEquals(documentOmschrijving, stapelVoorkomen.getDocumentOmschrijving());
    }

    @Test
    public void testMaakentityConvertorRubriek_8310() throws Exception {
        ConverterContext ctx = createContext(MINIMAAL);
        converter.convertInhoudelijk(ctx, IstStapelVoorkomenConverter.HEADER_RUBRIEK_8310, rubriek8310.toString());
        converter.maakEntity(ctx);
        StapelVoorkomen stapelVoorkomen = ctx.getStapel(Integer.parseInt(volgnummer)).getStapelvoorkomens().iterator().next();
        assertEquals(rubriek8310, stapelVoorkomen.getRubriek8310AanduidingGegevensInOnderzoek());
    }

    @Test
    public void testMaakentityConvertorRubriek_8320() throws Exception {
        ConverterContext ctx = createContext(MINIMAAL);
        converter.convertInhoudelijk(ctx, IstStapelVoorkomenConverter.HEADER_RUBRIEK_8320, rubriek8320.toString());
        converter.maakEntity(ctx);
        StapelVoorkomen stapelVoorkomen = ctx.getStapel(Integer.parseInt(volgnummer)).getStapelvoorkomens().iterator().next();
        assertEquals(rubriek8320, stapelVoorkomen.getRubriek8320DatumIngangOnderzoek());
    }

    @Test
    public void testMaakentityConvertorRubriek_8330() throws Exception {
        ConverterContext ctx = createContext(MINIMAAL);
        converter.convertInhoudelijk(ctx, IstStapelVoorkomenConverter.HEADER_RUBRIEK_8330, rubriek8330.toString());
        converter.maakEntity(ctx);
        StapelVoorkomen stapelVoorkomen = ctx.getStapel(Integer.parseInt(volgnummer)).getStapelvoorkomens().iterator().next();
        assertEquals(rubriek8330, stapelVoorkomen.getRubriek8330DatumEindeOnderzoek());
    }

    @Test
    public void testMaakentityConvertorRubriek_8410() throws Exception {
        ConverterContext ctx = createContext(MINIMAAL);
        converter.convertInhoudelijk(ctx, IstStapelVoorkomenConverter.HEADER_RUBRIEK_8410, rubriek8410.toString());
        converter.maakEntity(ctx);
        StapelVoorkomen stapelVoorkomen = ctx.getStapel(Integer.parseInt(volgnummer)).getStapelvoorkomens().iterator().next();
        assertEquals(rubriek8410, stapelVoorkomen.getRubriek8410OnjuistOfStrijdigOpenbareOrde());
    }

    @Test
    public void testMaakentityConvertorRubriek_8510() throws Exception {
        ConverterContext ctx = createContext(MINIMAAL);
        converter.convertInhoudelijk(ctx, IstStapelVoorkomenConverter.HEADER_RUBRIEK_8510, rubriek8510.toString());
        converter.maakEntity(ctx);
        StapelVoorkomen stapelVoorkomen = ctx.getStapel(Integer.parseInt(volgnummer)).getStapelvoorkomens().iterator().next();
        assertEquals(rubriek8510, stapelVoorkomen.getRubriek8510IngangsdatumGeldigheid());
    }

    @Test
    public void testMaakentityConvertorRubriek_8610() throws Exception {
        ConverterContext ctx = createContext(MINIMAAL);
        converter.convertInhoudelijk(ctx, IstStapelVoorkomenConverter.HEADER_RUBRIEK_8610, rubriek8610.toString());
        converter.maakEntity(ctx);
        StapelVoorkomen stapelVoorkomen = ctx.getStapel(Integer.parseInt(volgnummer)).getStapelvoorkomens().iterator().next();
        assertEquals(rubriek8610, stapelVoorkomen.getRubriek8610DatumVanOpneming());
    }

    @Test
    public void testMaakentityConvertorRubriek_6210() throws Exception {
        ConverterContext ctx = createContext(MINIMAAL);
        converter.convertInhoudelijk(ctx, IstStapelVoorkomenConverter.HEADER_RUBRIEK_6210, rubriek6210.toString());
        converter.maakEntity(ctx);
        StapelVoorkomen stapelVoorkomen = ctx.getStapel(Integer.parseInt(volgnummer)).getStapelvoorkomens().iterator().next();
        assertEquals(rubriek6210, stapelVoorkomen.getRubriek6210DatumIngangFamilierechtelijkeBetrekking());
    }

    @Test
    public void testMaakentityConvertorAkteNummer() throws Exception {
        ConverterContext ctx = createContext(MINIMAAL);
        converter.convertInhoudelijk(ctx, IstStapelVoorkomenConverter.HEADER_AKTE_NUMMER, aktenummer);
        converter.maakEntity(ctx);
        StapelVoorkomen stapelVoorkomen = ctx.getStapel(Integer.parseInt(volgnummer)).getStapelvoorkomens().iterator().next();
        assertEquals(aktenummer, stapelVoorkomen.getAktenummer());
    }

    @Test
    public void testMaakentityConvertorAnummer() throws Exception {
        ConverterContext ctx = createContext(MINIMAAL);
        converter.convertInhoudelijk(ctx, IstStapelVoorkomenConverter.HEADER_ANUMMER, anummer);
        converter.maakEntity(ctx);
        StapelVoorkomen stapelVoorkomen = ctx.getStapel(Integer.parseInt(volgnummer)).getStapelvoorkomens().iterator().next();
        assertEquals(anummer, stapelVoorkomen.getAnummer());
    }

    @Test
    public void testMaakentityConvertorBSNNummer() throws Exception {
        ConverterContext ctx = createContext(MINIMAAL);
        converter.convertInhoudelijk(ctx, IstStapelVoorkomenConverter.HEADER_BSN, bsn.toString());
        converter.maakEntity(ctx);
        StapelVoorkomen stapelVoorkomen = ctx.getStapel(Integer.parseInt(volgnummer)).getStapelvoorkomens().iterator().next();
        assertEquals(bsn, stapelVoorkomen.getBsn());
    }

    @Test
    public void testMaakentityConvertorVoornamen() throws Exception {
        ConverterContext ctx = createContext(MINIMAAL);
        converter.convertInhoudelijk(ctx, IstStapelVoorkomenConverter.HEADER_VOORNAMEN, voornamen);
        converter.maakEntity(ctx);
        StapelVoorkomen stapelVoorkomen = ctx.getStapel(Integer.parseInt(volgnummer)).getStapelvoorkomens().iterator().next();
        assertEquals(voornamen, stapelVoorkomen.getVoornamen());
    }

    @Test
    public void testMaakentityConvertorPredicaat() throws Exception {
        ConverterContext ctx = createContext(MINIMAAL);
        converter.convertInhoudelijk(ctx, IstStapelVoorkomenConverter.HEADER_PREDICAAT, Integer.toString(predicaat.getId()));
        converter.maakEntity(ctx);
        StapelVoorkomen stapelVoorkomen = ctx.getStapel(Integer.parseInt(volgnummer)).getStapelvoorkomens().iterator().next();
        assertEquals(predicaat, stapelVoorkomen.getPredicaat());
    }

    @Test
    public void testMaakentityConvertorAdelijkeTitel() throws Exception {
        ConverterContext ctx = createContext(MINIMAAL);
        converter.convertInhoudelijk(ctx, IstStapelVoorkomenConverter.HEADER_ADELLIJKE_TITEL, Integer.toString(adellijkeTitel.getId()));
        converter.maakEntity(ctx);
        StapelVoorkomen stapelVoorkomen = ctx.getStapel(Integer.parseInt(volgnummer)).getStapelvoorkomens().iterator().next();
        assertEquals(adellijkeTitel.getNaamMannelijk(), stapelVoorkomen.getAdellijkeTitel().getNaamMannelijk());
    }

    @Test
    public void testMaakentityConvertorGesclachtBijAdelijkeTitelPredicaat() throws Exception {
        ConverterContext ctx = createContext(MINIMAAL);
        converter.convertInhoudelijk(ctx, IstStapelVoorkomenConverter.HEADER_GESLACHT_BIJ_ADELLIJKE_TITEL_PREDICAAT,
                Integer.toString(geslachtsaanduidingBijAdellijkeTitelOfPredicaat.getId()));
        converter.maakEntity(ctx);
        StapelVoorkomen stapelVoorkomen = ctx.getStapel(Integer.parseInt(volgnummer)).getStapelvoorkomens().iterator().next();
        assertEquals(geslachtsaanduidingBijAdellijkeTitelOfPredicaat.getNaam(), stapelVoorkomen.getGeslachtsaanduidingBijAdellijkeTitelOfPredikaat().getNaam());
    }

    @Test
    public void testMaakentityConvertorVoorvoegsel() throws Exception {
        ConverterContext ctx = createContext(MINIMAAL);
        converter.convertInhoudelijk(ctx, IstStapelVoorkomenConverter.HEADER_VOORVOEGSEL, voorvoegsel);
        converter.maakEntity(ctx);
        StapelVoorkomen stapelVoorkomen = ctx.getStapel(Integer.parseInt(volgnummer)).getStapelvoorkomens().iterator().next();
        assertEquals(voorvoegsel, stapelVoorkomen.getVoorvoegsel());
    }

    @Test
    public void testMaakentityConvertorScheidingsTeken() throws Exception {
        ConverterContext ctx = createContext(MINIMAAL);
        converter.convertInhoudelijk(ctx, IstStapelVoorkomenConverter.HEADER_SCHEIDINGSTEKEN, Character.toString(scheidingsteken));
        converter.maakEntity(ctx);
        StapelVoorkomen stapelVoorkomen = ctx.getStapel(Integer.parseInt(volgnummer)).getStapelvoorkomens().iterator().next();
        assertEquals(scheidingsteken, stapelVoorkomen.getScheidingsteken());
    }

    @Test
    public void testMaakentityConvertorGesclachtsNaamStam() throws Exception {
        ConverterContext ctx = createContext(MINIMAAL);
        converter.convertInhoudelijk(ctx, IstStapelVoorkomenConverter.HEADER_GESLACHTSNAAMSTAM, geslachtsnaamstam);
        converter.maakEntity(ctx);
        StapelVoorkomen stapelVoorkomen = ctx.getStapel(Integer.parseInt(volgnummer)).getStapelvoorkomens().iterator().next();
        assertEquals(geslachtsnaamstam, stapelVoorkomen.getGeslachtsnaamstam());
    }

    @Test
    public void testMaakentityConvertorDatumGeboorte() throws Exception {
        ConverterContext ctx = createContext(MINIMAAL);
        converter.convertInhoudelijk(ctx, IstStapelVoorkomenConverter.HEADER_DATUM_GEBOORTE, datumGeboorte.toString());
        converter.maakEntity(ctx);
        StapelVoorkomen stapelVoorkomen = ctx.getStapel(Integer.parseInt(volgnummer)).getStapelvoorkomens().iterator().next();
        assertEquals(datumGeboorte, stapelVoorkomen.getDatumGeboorte());
    }

    @Test
    public void testMaakentityConvertorBuitenlandsePlaatsGeboorte() throws Exception {
        ConverterContext ctx = createContext(MINIMAAL);
        converter.convertInhoudelijk(ctx, IstStapelVoorkomenConverter.HEADER_BUITENLANDSE_PLAATS_GEBOORTE, buitenlandsePlaatsGeboorte);
        converter.maakEntity(ctx);
        StapelVoorkomen stapelVoorkomen = ctx.getStapel(Integer.parseInt(volgnummer)).getStapelvoorkomens().iterator().next();
        assertEquals(buitenlandsePlaatsGeboorte, stapelVoorkomen.getBuitenlandsePlaatsGeboorte());
    }

    @Test
    public void testMaakentityConvertorOmscrijvingLokatieGeboorte() throws Exception {
        ConverterContext ctx = createContext(MINIMAAL);
        converter.convertInhoudelijk(ctx, IstStapelVoorkomenConverter.HEADER_OMSCHRIJVING_LOCATIE_GEBOORTE, omschrijvingLocatieGeboorte);
        converter.maakEntity(ctx);
        StapelVoorkomen stapelVoorkomen = ctx.getStapel(Integer.parseInt(volgnummer)).getStapelvoorkomens().iterator().next();
        assertEquals(omschrijvingLocatieGeboorte, stapelVoorkomen.getOmschrijvingLocatieGeboorte());
    }

    @Test
    public void testMaakentityConvertorGeslachtsAanduiding() throws Exception {
        ConverterContext ctx = createContext(MINIMAAL);
        converter.convertInhoudelijk(ctx, IstStapelVoorkomenConverter.HEADER_GESLACHTS_AANDUIDING, Integer.toString(geslachtsaanduiding.getId()));
        converter.maakEntity(ctx);
        StapelVoorkomen stapelVoorkomen = ctx.getStapel(Integer.parseInt(volgnummer)).getStapelvoorkomens().iterator().next();
        assertEquals(geslachtsaanduiding.getNaam(), stapelVoorkomen.getGeslachtsaanduiding().getNaam());
    }

    @Test
    public void testMaakentityConvertorDatumAanvang() throws Exception {
        ConverterContext ctx = createContext(MINIMAAL);
        converter.convertInhoudelijk(ctx, IstStapelVoorkomenConverter.HEADER_DATUM_AANVANG, datumAanvang.toString());
        converter.maakEntity(ctx);
        StapelVoorkomen stapelVoorkomen = ctx.getStapel(Integer.parseInt(volgnummer)).getStapelvoorkomens().iterator().next();
        assertEquals(datumAanvang, stapelVoorkomen.getDatumAanvang());
    }

    @Test
    public void testMaakentityConvertorBuitenlandsePlaatsAanvang() throws Exception {
        ConverterContext ctx = createContext(MINIMAAL);
        converter.convertInhoudelijk(ctx, IstStapelVoorkomenConverter.HEADER_BUITENLANDSE_PLAATS_AANVANG, buitenlandsePlaatsAanvang);
        converter.maakEntity(ctx);
        StapelVoorkomen stapelVoorkomen = ctx.getStapel(Integer.parseInt(volgnummer)).getStapelvoorkomens().iterator().next();
        assertEquals(buitenlandsePlaatsAanvang, stapelVoorkomen.getBuitenlandsePlaatsAanvang());
    }

    @Test
    public void testMaakentityConvertorOmschrijvingLocatieAanvang() throws Exception {
        ConverterContext ctx = createContext(MINIMAAL);
        converter.convertInhoudelijk(ctx, IstStapelVoorkomenConverter.HEADER_OMSCHRIJVING_LOCATIE_AANVANG, omschrijvingLocatieAanvang);
        converter.maakEntity(ctx);
        StapelVoorkomen stapelVoorkomen = ctx.getStapel(Integer.parseInt(volgnummer)).getStapelvoorkomens().iterator().next();
        assertEquals(omschrijvingLocatieAanvang, stapelVoorkomen.getOmschrijvingLocatieAanvang());
    }

    @Test
    public void testMaakentityConvertorDatumEinde() throws Exception {
        ConverterContext ctx = createContext(MINIMAAL);
        converter.convertInhoudelijk(ctx, IstStapelVoorkomenConverter.HEADER_DATUM_EINDE, datumEinde.toString());
        converter.maakEntity(ctx);
        StapelVoorkomen stapelVoorkomen = ctx.getStapel(Integer.parseInt(volgnummer)).getStapelvoorkomens().iterator().next();
        assertEquals(datumEinde, stapelVoorkomen.getDatumEinde());
    }

    @Test
    public void testMaakentityConvertorOmschrijvingLocatieEinde() throws Exception {
        ConverterContext ctx = createContext(MINIMAAL);
        converter.convertInhoudelijk(ctx, IstStapelVoorkomenConverter.HEADER_OMSCHRIJVING_LOCATIE_EINDE, omschrijvingLocatieEinde);
        converter.maakEntity(ctx);
        StapelVoorkomen stapelVoorkomen = ctx.getStapel(Integer.parseInt(volgnummer)).getStapelvoorkomens().iterator().next();
        assertEquals(omschrijvingLocatieEinde, stapelVoorkomen.getOmschrijvingLocatieEinde());
    }

    @Test
    public void testMaakentityConvertorBuitenlandsePlaatsEinde() throws Exception {
        ConverterContext ctx = createContext(MINIMAAL);
        converter.convertInhoudelijk(ctx, IstStapelVoorkomenConverter.HEADER_BUITENLANDSE_PLAATS_EINDE, buitenlandsePlaatsEinde);
        converter.maakEntity(ctx);
        StapelVoorkomen stapelVoorkomen = ctx.getStapel(Integer.parseInt(volgnummer)).getStapelvoorkomens().iterator().next();
        assertEquals(buitenlandsePlaatsEinde, stapelVoorkomen.getBuitenlandsePlaatsEinde());
    }

    @Test
    public void testMaakentityConvertorSoortRelatie() throws Exception {
        ConverterContext ctx = createContext(MINIMAAL);
        converter.convertInhoudelijk(ctx, IstStapelVoorkomenConverter.HEADER_SOORT_RELATIE, Integer.toString(soortRelatie.getId()));
        converter.maakEntity(ctx);
        StapelVoorkomen stapelVoorkomen = ctx.getStapel(Integer.parseInt(volgnummer)).getStapelvoorkomens().iterator().next();
        assertEquals(soortRelatie.getNaam(), stapelVoorkomen.getSoortRelatie().getNaam());
    }

    @Test
    public void testMaakentityConvertorIndicatieOuder1HeeftGezag() throws Exception {
        ConverterContext ctx = createContext(MINIMAAL);
        converter.convertInhoudelijk(ctx, IstStapelVoorkomenConverter.HEADER_INDICATIE_OUDER1_HEEFT_GEZAG, indicatieOuder1HeeftGezag.toString());
        converter.maakEntity(ctx);
        StapelVoorkomen stapelVoorkomen = ctx.getStapel(Integer.parseInt(volgnummer)).getStapelvoorkomens().iterator().next();
        assertFalse(stapelVoorkomen.getIndicatieOuder1HeeftGezag());
    }

    @Test
    public void testMaakentityConvertorIndicatieOuder2HeeftGezag() throws Exception {
        ConverterContext ctx = createContext(MINIMAAL);
        converter.convertInhoudelijk(ctx, IstStapelVoorkomenConverter.HEADER_INDICATIE_OUDER2_HEEFT_GEZAG, indicatieOuder2HeeftGezag.toString());
        converter.maakEntity(ctx);
        StapelVoorkomen stapelVoorkomen = ctx.getStapel(Integer.parseInt(volgnummer)).getStapelvoorkomens().iterator().next();
        assertFalse(stapelVoorkomen.getIndicatieOuder2HeeftGezag());
    }

    @Test
    public void testMaakentityConvertorIndicatieDerdeHeeftGezag() throws Exception {
        ConverterContext ctx = createContext(MINIMAAL);
        converter.convertInhoudelijk(ctx, IstStapelVoorkomenConverter.HEADER_INDICATIE_DERDE_HEEFT_GEZAG, indicatieDerdeHeeftGezag.toString());
        converter.maakEntity(ctx);
        StapelVoorkomen stapelVoorkomen = ctx.getStapel(Integer.parseInt(volgnummer)).getStapelvoorkomens().iterator().next();
        assertFalse(stapelVoorkomen.getIndicatieDerdeHeeftGezag());
    }

    @Test
    public void testMaakentityConvertorIndicatieOnderCuratele() throws Exception {
        ConverterContext ctx = createContext(MINIMAAL);
        converter.convertInhoudelijk(ctx, IstStapelVoorkomenConverter.HEADER_INDICATIE_ONDER_CURATELE, indicatieOnderCuratele.toString());
        converter.maakEntity(ctx);
        StapelVoorkomen stapelVoorkomen = ctx.getStapel(Integer.parseInt(volgnummer)).getStapelvoorkomens().iterator().next();
        assertFalse(stapelVoorkomen.getIndicatieOnderCuratele());
    }



    /*

    @Test
    public void testMaakentityConvertor() throws Exception {
        ConverterContext ctx = createContext(MINIMAAL);
        converter.convertInhoudelijk(ctx,IstStapelVoorkomenConverter.HEADER_ANUMMER,anummer.toString());
        converter.maakEntity(ctx);
        StapelVoorkomen stapelVoorkomen = ctx.getStapel(Integer.parseInt(volgnummer)).getStapelvoorkomens().iterator().next();
        assertEquals(anummer,stapelVoorkomen.getAnummer());
    }
*/


    private ConverterContext createContext(String type) {
        ConverterContext ctx = new ConverterContext();
        if (!LEEG.equals(type)) {
            Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
            Stapel stapel = new Stapel(persoon, "01", 1);
            ctx.storePersoon(1, persoon);
            ctx.storeStapel(1, stapel);
            ctx.storeAdministratieveHandeling(1, administratieveHandeling);
            converter.convertInhoudelijk(ctx, IstStapelVoorkomenConverter.HEADER_VOLGNUMMER, volgnummer);
            converter.convertInhoudelijk(ctx, IstStapelVoorkomenConverter.HEADER_STAPEL, volgnummer);
            converter.convertInhoudelijk(ctx, IstStapelVoorkomenConverter.HEADER_ADMINISTRATIEVE_HANDELING, volgnummer);
        }
        return ctx;
    }


    @Test(expected = NullPointerException.class)
    public void testResetConverter() throws Exception {
        ConverterContext ctx = createContext(MINIMAAL);
        converter.convertInhoudelijk(ctx, IstStapelVoorkomenConverter.HEADER_OMSCHRIJVING_LOCATIE_AANVANG, omschrijvingLocatieAanvang);
        converter.maakEntity(ctx);
        assertEquals("we zouden er nu 1 moeten hebben", 1, ctx.getStapel(Integer.parseInt(volgnummer)).getStapelvoorkomens().size());
        converter.resetConverter();
        converter.maakEntity(ctx);
        Assert.fail("maakEntity zou een nullpointer hebben moeten gooien");
    }


    private boolean zijnTweeStapelVoorkomensGelijk(StapelVoorkomen vk1, StapelVoorkomen vk2) {
        boolean gelijk = true;
        gelijk =
                vk1.getAdellijkeTitel() != null ? gelijk && vk1.getAdellijkeTitel().equals(vk2.getAdellijkeTitel()) : gelijk && vk2.getAdellijkeTitel() == null;
        gelijk =
                vk1.getAdministratieveHandeling() != null ? gelijk && vk1.getAdministratieveHandeling().equals(vk2.getAdministratieveHandeling())
                        : gelijk && vk2.getAdministratieveHandeling() == null;
        gelijk = vk1.getAktenummer() != null ? gelijk && vk1.getAktenummer().equals(vk2.getAktenummer()) : gelijk && vk2.getAktenummer() == null;
        gelijk = vk1.getAnummer() != null ? gelijk && vk1.getAnummer().equals(vk2.getAnummer()) : gelijk && vk2.getAnummer() == null;
        gelijk = vk1.getBsn() != null ? gelijk && vk1.getBsn().equals(vk2.getBsn()) : gelijk && vk2.getBsn() == null;
        gelijk =
                vk1.getBuitenlandsePlaatsAanvang() != null ? gelijk && vk1.getBuitenlandsePlaatsAanvang().equals(vk2.getBuitenlandsePlaatsAanvang())
                        : gelijk && vk2.getBuitenlandsePlaatsAanvang() == null;
        gelijk =
                vk1.getBuitenlandsePlaatsEinde() != null ? gelijk && vk1.getBuitenlandsePlaatsEinde().equals(vk2.getBuitenlandsePlaatsEinde())
                        : gelijk && vk2.getBuitenlandsePlaatsEinde() == null;
        gelijk =
                vk1.getBuitenlandsePlaatsGeboorte() != null ? gelijk && vk1.getBuitenlandsePlaatsGeboorte().equals(vk2.getBuitenlandsePlaatsGeboorte())
                        : gelijk && vk2.getBuitenlandsePlaatsGeboorte() == null;
        gelijk = vk1.getDatumAanvang() != null ? gelijk && vk1.getDatumAanvang().equals(vk2.getDatumAanvang()) : gelijk && vk2.getDatumAanvang() == null;
        gelijk = vk1.getDatumEinde() != null ? gelijk && vk1.getDatumEinde().equals(vk2.getDatumEinde()) : gelijk && vk2.getDatumEinde() == null;
        gelijk = vk1.getDatumGeboorte() != null ? gelijk && vk1.getDatumGeboorte().equals(vk2.getDatumGeboorte()) : gelijk && vk2.getDatumGeboorte() == null;
        gelijk =
                vk1.getDocumentOmschrijving() != null ? gelijk && vk1.getDocumentOmschrijving().equals(vk2.getDocumentOmschrijving())
                        : gelijk && vk2.getDocumentOmschrijving() == null;
        gelijk =
                vk1.getGemeenteAanvang() != null ? gelijk && vk1.getGemeenteAanvang().equals(vk2.getGemeenteAanvang())
                        : gelijk && vk2.getGemeenteAanvang() == null;
        gelijk = vk1.getGemeenteEinde() != null ? gelijk && vk1.getGemeenteEinde().equals(vk2.getGemeenteEinde()) : gelijk && vk2.getGemeenteEinde() == null;
        gelijk =
                vk1.getGemeenteGeboorte() != null ? gelijk && vk1.getGemeenteGeboorte().equals(vk2.getGemeenteGeboorte())
                        : gelijk && vk2.getGemeenteGeboorte() == null;
        gelijk =
                vk1.getGeslachtsaanduiding() != null ? gelijk && vk1.getGeslachtsaanduiding().equals(vk2.getGeslachtsaanduiding())
                        : gelijk && vk2.getGeslachtsaanduiding() == null;
        gelijk =
                vk1.getGeslachtsaanduidingBijAdellijkeTitelOfPredikaat() != null ? gelijk && vk1.getGeslachtsaanduidingBijAdellijkeTitelOfPredikaat()
                        .equals(vk2.getGeslachtsaanduidingBijAdellijkeTitelOfPredikaat())
                        : gelijk && vk2.getGeslachtsaanduidingBijAdellijkeTitelOfPredikaat() == null;
        gelijk =
                vk1.getGeslachtsnaamstam() != null ? gelijk && vk1.getGeslachtsnaamstam().equals(vk2.getGeslachtsnaamstam())
                        : gelijk && vk2.getGeslachtsnaamstam() == null;
        gelijk = vk1.getId() != null ? gelijk && vk1.getId().equals(vk2.getId()) : gelijk && vk2.getId() == null;
        gelijk =
                vk1.getIndicatieDerdeHeeftGezag() != null ? gelijk && vk1.getIndicatieDerdeHeeftGezag().equals(vk2.getIndicatieDerdeHeeftGezag())
                        : gelijk && vk2.getIndicatieDerdeHeeftGezag() == null;
        gelijk =
                vk1.getIndicatieOnderCuratele() != null ? gelijk && vk1.getIndicatieOnderCuratele().equals(vk2.getIndicatieOnderCuratele())
                        : gelijk && vk2.getIndicatieOnderCuratele() == null;
        gelijk =
                vk1.getIndicatieOuder1HeeftGezag() != null ? gelijk && vk1.getIndicatieOuder1HeeftGezag().equals(vk2.getIndicatieOuder1HeeftGezag())
                        : gelijk && vk2.getIndicatieOuder1HeeftGezag() == null;
        gelijk =
                vk1.getIndicatieOuder2HeeftGezag() != null ? gelijk && vk1.getIndicatieOuder2HeeftGezag().equals(vk2.getIndicatieOuder2HeeftGezag())
                        : gelijk && vk2.getIndicatieOuder2HeeftGezag() == null;
        gelijk =
                vk1.getLandOfGebiedAanvang() != null ? gelijk && vk1.getLandOfGebiedAanvang().equals(vk2.getLandOfGebiedAanvang())
                        : gelijk && vk2.getLandOfGebiedAanvang() == null;
        gelijk =
                vk1.getLandOfGebiedEinde() != null ? gelijk && vk1.getLandOfGebiedEinde().equals(vk2.getLandOfGebiedEinde())
                        : gelijk && vk2.getLandOfGebiedEinde() == null;
        gelijk =
                vk1.getLandOfGebiedGeboorte() != null ? gelijk && vk1.getLandOfGebiedGeboorte().equals(vk2.getLandOfGebiedGeboorte())
                        : gelijk && vk2.getLandOfGebiedGeboorte() == null;
        gelijk =
                vk1.getOmschrijvingLocatieAanvang() != null ? gelijk && vk1.getOmschrijvingLocatieAanvang().equals(vk2.getOmschrijvingLocatieAanvang())
                        : gelijk && vk2.getOmschrijvingLocatieAanvang() == null;
        gelijk =
                vk1.getOmschrijvingLocatieEinde() != null ? gelijk && vk1.getOmschrijvingLocatieEinde().equals(vk2.getOmschrijvingLocatieEinde())
                        : gelijk && vk2.getOmschrijvingLocatieEinde() == null;
        gelijk =
                vk1.getOmschrijvingLocatieGeboorte() != null ? gelijk && vk1.getOmschrijvingLocatieGeboorte().equals(vk2.getOmschrijvingLocatieGeboorte())
                        : gelijk && vk2.getOmschrijvingLocatieGeboorte() == null;
        gelijk = vk1.getPartij() != null ? gelijk && vk1.getPartij().equals(vk2.getPartij()) : gelijk && vk2.getPartij() == null;
        gelijk = vk1.getPredicaat() != null ? gelijk && vk1.getPredicaat().equals(vk2.getPredicaat()) : gelijk && vk2.getPredicaat() == null;
        gelijk =
                vk1.getRedenBeeindigingRelatie() != null ? gelijk && vk1.getRedenBeeindigingRelatie().equals(vk2.getRedenBeeindigingRelatie())
                        : gelijk && vk2.getRedenBeeindigingRelatie() == null;
        gelijk =
                vk1.getRubriek6210DatumIngangFamilierechtelijkeBetrekking() != null ? gelijk && vk1.getRubriek6210DatumIngangFamilierechtelijkeBetrekking()
                        .equals(vk2.getRubriek6210DatumIngangFamilierechtelijkeBetrekking())
                        : gelijk && vk2.getRubriek6210DatumIngangFamilierechtelijkeBetrekking() == null;
        gelijk =
                vk1.getRubriek8220DatumDocument() != null ? gelijk && vk1.getRubriek8220DatumDocument().equals(vk2.getRubriek8220DatumDocument())
                        : gelijk && vk2.getRubriek8220DatumDocument() == null;
        gelijk =
                vk1.getRubriek8310AanduidingGegevensInOnderzoek() != null ? gelijk && vk1.getRubriek8310AanduidingGegevensInOnderzoek()
                        .equals(vk2.getRubriek8310AanduidingGegevensInOnderzoek()) : gelijk && vk2.getRubriek8310AanduidingGegevensInOnderzoek() == null;
        gelijk =
                vk1.getRubriek8320DatumIngangOnderzoek() != null ? gelijk && vk1.getRubriek8320DatumIngangOnderzoek()
                        .equals(vk2.getRubriek8320DatumIngangOnderzoek()) : gelijk && vk2.getRubriek8320DatumIngangOnderzoek() == null;
        gelijk =
                vk1.getRubriek8330DatumEindeOnderzoek() != null ? gelijk && vk1.getRubriek8330DatumEindeOnderzoek()
                        .equals(vk2.getRubriek8330DatumEindeOnderzoek()) : gelijk && vk2.getRubriek8330DatumEindeOnderzoek() == null;
        gelijk =
                vk1.getRubriek8410OnjuistOfStrijdigOpenbareOrde() != null ? gelijk && vk1.getRubriek8410OnjuistOfStrijdigOpenbareOrde()
                        .equals(vk2.getRubriek8410OnjuistOfStrijdigOpenbareOrde()) : gelijk && vk2.getRubriek8410OnjuistOfStrijdigOpenbareOrde() == null;
        gelijk =
                vk1.getRubriek8510IngangsdatumGeldigheid() != null ? gelijk && vk1.getRubriek8510IngangsdatumGeldigheid()
                        .equals(vk2.getRubriek8510IngangsdatumGeldigheid()) : gelijk && vk2.getRubriek8510IngangsdatumGeldigheid() == null;
        gelijk =
                vk1.getRubriek8610DatumVanOpneming() != null ? gelijk && vk1.getRubriek8610DatumVanOpneming().equals(vk2.getRubriek8610DatumVanOpneming())
                        : gelijk && vk2.getRubriek8610DatumVanOpneming() == null;
        gelijk =
                vk1.getScheidingsteken() != null ? gelijk && vk1.getScheidingsteken().equals(vk2.getScheidingsteken())
                        : gelijk && vk2.getScheidingsteken() == null;
        gelijk = vk1.getSoortDocument() != null ? gelijk && vk1.getSoortDocument().equals(vk2.getSoortDocument()) : gelijk && vk2.getSoortDocument() == null;
        gelijk = vk1.getSoortRelatie() != null ? gelijk && vk1.getSoortRelatie().equals(vk2.getSoortRelatie()) : gelijk && vk2.getSoortRelatie() == null;
        gelijk = gelijk && vk1.getVolgnummer() == vk2.getVolgnummer();
        gelijk = vk1.getVoornamen() != null ? gelijk && vk1.getVoornamen().equals(vk2.getVoornamen()) : gelijk && vk2.getVoornamen() == null;
        gelijk = vk1.getVoorvoegsel() != null ? gelijk && vk1.getVoorvoegsel().equals(vk2.getVoorvoegsel()) : gelijk && vk2.getVoorvoegsel() == null;
        return gelijk;
    }
}

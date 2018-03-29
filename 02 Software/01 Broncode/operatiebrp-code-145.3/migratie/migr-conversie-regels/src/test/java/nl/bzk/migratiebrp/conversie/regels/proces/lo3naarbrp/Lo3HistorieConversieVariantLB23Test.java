/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerliesNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNummerverwijzingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieOnjuist;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.Lo3AttribuutConverteerder;
import nl.bzk.migratiebrp.conversie.regels.tabel.ConversietabelFactoryImpl;
import org.junit.Before;
import org.junit.Test;

public class Lo3HistorieConversieVariantLB23Test {

    private static final BrpNationaliteitCode NATIONALITEIT_CODE = new BrpNationaliteitCode("0001");
    private static final Lo3Herkomst HERKOMST_NUMMERVERWIJZING = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0);
    private static final Lo3Herkomst HERKOMST_NATIONALITEIT = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_04, 0, 0);
    private static final Lo3IndicatieOnjuist INDICATIE_ONJUIST = new Lo3IndicatieOnjuist("O");
    private static final Lo3GemeenteCode GEMEENTE_CODE = new Lo3GemeenteCode("0518");
    private static final BrpCharacter NADERE_AANDUIDING_VERVAL = new BrpCharacter('O');
    private static final long BASIS_ACTIE_ID = 1L;
    private static final int DATUM_INGANG_GELDIGHEID = 20150101;
    private static final int DATUM_OPNAME = 20150102;

    private AbstractLo3HistorieConversieVariant lb23;

    @Before
    public void setUp() {
        final Lo3AttribuutConverteerder attribuutConverteerder = new Lo3AttribuutConverteerder(new ConversietabelFactoryImpl());
        lb23 = new Lo3HistorieConversieVariantLB23(attribuutConverteerder);
    }

    @Test
    public <T extends BrpGroepInhoud> void testStap2() {
        final Lo3Documentatie lo3Documentatie = maakAkte(BASIS_ACTIE_ID);
        final Lo3Historie lo3Historie = new Lo3Historie(INDICATIE_ONJUIST, new Lo3Datum(DATUM_INGANG_GELDIGHEID), new Lo3Datum(DATUM_OPNAME));

        final BrpNationaliteitInhoud inhoud = maakNationaliteitInhoud();
        final TussenGroep<T> lo3Groep = new TussenGroep<>((T) inhoud, lo3Historie, lo3Documentatie, HERKOMST_NATIONALITEIT);

        final Map<Long, BrpActie> actieCache = new HashMap<>();
        final List<BrpGroep<T>> brpGroepen = lb23.converteer(Collections.singletonList(lo3Groep), actieCache);
        assertEquals(1, brpGroepen.size());
        assertEquals(1, actieCache.size());
        final BrpActie cacheActie = actieCache.get(BASIS_ACTIE_ID);
        assertNotNull(cacheActie);
        assertTrue(cacheActie.isGebruikt());
        final BrpGroep<T> brpGroep = brpGroepen.get(0);
        assertSame(inhoud, brpGroep.getInhoud());
        final BrpHistorie brpHistorie = brpGroep.getHistorie();
        assertEquals(BrpDatum.wrap(DATUM_INGANG_GELDIGHEID, null), brpHistorie.getDatumAanvangGeldigheid());
        assertNull(brpHistorie.getDatumEindeGeldigheid());
        assertEquals(new Lo3Datum(DATUM_OPNAME), brpHistorie.getDatumTijdRegistratie().converteerNaarLo3Datum());
        assertEquals(brpHistorie.getDatumTijdRegistratie(), brpHistorie.getDatumTijdVerval());
        assertEquals(cacheActie, brpGroep.getActieInhoud());
        assertNull(brpGroep.getActieGeldigheid());
        assertEquals(brpGroep.getActieInhoud(), brpGroep.getActieVerval());
        assertEquals(NADERE_AANDUIDING_VERVAL, brpHistorie.getNadereAanduidingVerval());
    }

    private BrpNationaliteitInhoud maakNationaliteitInhoud() {
        return new BrpNationaliteitInhoud.Builder(NATIONALITEIT_CODE).build();
    }

    @Test
    public <T extends BrpGroepInhoud> void testStap3EnkeleRij() {
        final Lo3Documentatie lo3Documentatie = maakAkte(BASIS_ACTIE_ID);
        final Lo3Historie lo3Historie = new Lo3Historie(null, new Lo3Datum(DATUM_INGANG_GELDIGHEID), new Lo3Datum(DATUM_OPNAME));

        final BrpNummerverwijzingInhoud inhoud = maakNummerverwijzingInhoud();
        final TussenGroep<T> lo3Groep = new TussenGroep<>((T) inhoud, lo3Historie, lo3Documentatie, HERKOMST_NUMMERVERWIJZING);

        final Map<Long, BrpActie> actieCache = new HashMap<>();
        final List<BrpGroep<T>> brpGroepen = lb23.converteer(Collections.singletonList(lo3Groep), actieCache);
        assertEquals(1, brpGroepen.size());
        assertEquals(1, actieCache.size());
        final BrpActie cacheActie = actieCache.get(BASIS_ACTIE_ID);
        assertNotNull(cacheActie);
        assertTrue(cacheActie.isGebruikt());
        final BrpGroep<T> brpGroep = brpGroepen.get(0);
        assertSame(inhoud, brpGroep.getInhoud());
        final BrpHistorie brpHistorie = brpGroep.getHistorie();
        assertEquals(BrpDatum.wrap(DATUM_INGANG_GELDIGHEID, null), brpHistorie.getDatumAanvangGeldigheid());
        assertNull(brpHistorie.getDatumEindeGeldigheid());
        assertEquals(new Lo3Datum(DATUM_OPNAME), brpHistorie.getDatumTijdRegistratie().converteerNaarLo3Datum());
        assertNull(brpHistorie.getDatumTijdVerval());
        assertEquals(cacheActie, brpGroep.getActieInhoud());
        assertNull(brpGroep.getActieGeldigheid());
        assertNull(brpGroep.getActieVerval());
        assertNull(brpHistorie.getNadereAanduidingVerval());
    }

    private BrpNummerverwijzingInhoud maakNummerverwijzingInhoud() {
        return new BrpNummerverwijzingInhoud(null, new BrpString("1234567890"), null, null);
    }

    @Test
    public <T extends BrpGroepInhoud> void testStap3GevuldEnLegeRij() {
        final long actieId2 = 2L;
        final int datumOpname2 = 20150801;
        final Lo3Documentatie lo3Documentatie = maakAkte(BASIS_ACTIE_ID);
        final Lo3Historie lo3Historie = new Lo3Historie(null, new Lo3Datum(DATUM_INGANG_GELDIGHEID), new Lo3Datum(DATUM_OPNAME));
        final Lo3Documentatie lo3Documentatie2 = maakAkte(actieId2);
        final Lo3Historie lo3Historie2 = new Lo3Historie(null, new Lo3Datum(DATUM_INGANG_GELDIGHEID), new Lo3Datum(datumOpname2));

        final BrpNummerverwijzingInhoud inhoud = maakNummerverwijzingInhoud();
        final BrpNummerverwijzingInhoud leegInhoud = new BrpNummerverwijzingInhoud(null, null, null, null);
        final TussenGroep<T> lo3Groep = new TussenGroep<>((T) inhoud, lo3Historie, lo3Documentatie, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_51, 0, 1));
        final TussenGroep<T> lo3Groep2 = new TussenGroep<>((T) leegInhoud, lo3Historie2, lo3Documentatie2, HERKOMST_NUMMERVERWIJZING);

        final Map<Long, BrpActie> actieCache = new HashMap<>();
        final List<BrpGroep<T>> brpGroepen = lb23.converteer(Arrays.asList(lo3Groep, lo3Groep2), actieCache);
        assertEquals(2, actieCache.size());

        final BrpActie cacheActie = actieCache.get(BASIS_ACTIE_ID);
        assertNotNull(cacheActie);
        assertTrue(cacheActie.isGebruikt());

        final BrpActie cacheActie2 = actieCache.get(actieId2);
        assertNotNull(cacheActie2);
        assertTrue(cacheActie2.isGebruikt());
        assertEquals(BrpSoortActieCode.CONVERSIE_GBA, cacheActie2.getSoortActieCode());

        assertEquals(1, brpGroepen.size());
        final BrpGroep<T> brpGroep = brpGroepen.get(0);
        assertSame(inhoud, brpGroep.getInhoud());
        final BrpHistorie brpHistorie = brpGroep.getHistorie();
        assertEquals(BrpDatum.wrap(DATUM_INGANG_GELDIGHEID, null), brpHistorie.getDatumAanvangGeldigheid());
        assertEquals(BrpDatum.wrap(DATUM_INGANG_GELDIGHEID, null), brpHistorie.getDatumEindeGeldigheid());
        assertEquals(new Lo3Datum(DATUM_OPNAME), brpHistorie.getDatumTijdRegistratie().converteerNaarLo3Datum());
        assertEquals(new Lo3Datum(DATUM_OPNAME), brpHistorie.getDatumTijdVerval().converteerNaarLo3Datum());
        assertEquals(cacheActie, brpGroep.getActieInhoud());
        assertEquals(cacheActie2, brpGroep.getActieGeldigheid());
        assertEquals(brpGroep.getActieInhoud(), brpGroep.getActieVerval());
        assertNull(brpHistorie.getNadereAanduidingVerval());
    }

    @Test
    public <T extends BrpGroepInhoud> void testStap3GevuldeEn2LegeRijen() {
        final long actieId2 = 2L;
        final int datumOpname2 = 20150801;
        final Lo3Documentatie lo3Documentatie = maakAkte(BASIS_ACTIE_ID);
        final Lo3Historie lo3Historie = new Lo3Historie(null, new Lo3Datum(DATUM_INGANG_GELDIGHEID), new Lo3Datum(DATUM_OPNAME));
        final Lo3Documentatie lo3Documentatie2 = maakAkte(actieId2);
        final Lo3Historie lo3Historie2 = new Lo3Historie(null, new Lo3Datum(DATUM_INGANG_GELDIGHEID), new Lo3Datum(datumOpname2));

        final BrpNummerverwijzingInhoud inhoud = maakNummerverwijzingInhoud();
        final BrpNummerverwijzingInhoud leegInhoud = new BrpNummerverwijzingInhoud(null, null, null, null);
        final TussenGroep<T> lo3Groep = new TussenGroep<>((T) inhoud, lo3Historie, lo3Documentatie, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_51, 0, 2));
        final TussenGroep<T>
                lo3Groep2 =
                new TussenGroep<>((T) leegInhoud, lo3Historie2, lo3Documentatie2, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_51, 0, 1));
        final TussenGroep<T> lo3Groep3 = new TussenGroep<>((T) leegInhoud, lo3Historie2, lo3Documentatie2, HERKOMST_NUMMERVERWIJZING);

        final Map<Long, BrpActie> actieCache = new HashMap<>();
        final List<BrpGroep<T>> brpGroepen = lb23.converteer(Arrays.asList(lo3Groep, lo3Groep2, lo3Groep3), actieCache);
        assertEquals(2, actieCache.size());

        final BrpActie cacheActie = actieCache.get(BASIS_ACTIE_ID);
        assertNotNull(cacheActie);
        assertTrue(cacheActie.isGebruikt());

        final BrpActie cacheActie2 = actieCache.get(actieId2);
        assertNotNull(cacheActie2);
        assertTrue(cacheActie2.isGebruikt());
        assertEquals(BrpSoortActieCode.CONVERSIE_GBA_MATERIELE_HISTORIE, cacheActie2.getSoortActieCode());

        assertEquals(1, brpGroepen.size());
        final BrpGroep<T> brpGroep = brpGroepen.get(0);
        assertSame(inhoud, brpGroep.getInhoud());
        final BrpHistorie brpHistorie = brpGroep.getHistorie();
        assertEquals(BrpDatum.wrap(DATUM_INGANG_GELDIGHEID, null), brpHistorie.getDatumAanvangGeldigheid());
        assertEquals(BrpDatum.wrap(DATUM_INGANG_GELDIGHEID, null), brpHistorie.getDatumEindeGeldigheid());
        assertEquals(new Lo3Datum(DATUM_OPNAME), brpHistorie.getDatumTijdRegistratie().converteerNaarLo3Datum());
        assertEquals(new Lo3Datum(DATUM_OPNAME), brpHistorie.getDatumTijdVerval().converteerNaarLo3Datum());
        assertEquals(cacheActie, brpGroep.getActieInhoud());
        assertEquals(cacheActie2, brpGroep.getActieGeldigheid());
        assertEquals(brpGroep.getActieInhoud(), brpGroep.getActieVerval());
        assertNull(brpHistorie.getNadereAanduidingVerval());
    }

    @Test
    public <T extends BrpGroepInhoud> void testStap32GevuldeRijen() {
        final long actieId2 = 2L;
        final int datumOpname2 = 20150801;
        final Lo3Documentatie lo3Documentatie = maakAkte(BASIS_ACTIE_ID);
        final Lo3Historie lo3Historie = new Lo3Historie(null, new Lo3Datum(DATUM_INGANG_GELDIGHEID), new Lo3Datum(DATUM_OPNAME));
        final Lo3Documentatie lo3Documentatie2 = maakAkte(actieId2);
        final Lo3Historie lo3Historie2 = new Lo3Historie(null, new Lo3Datum(DATUM_INGANG_GELDIGHEID), new Lo3Datum(datumOpname2));

        final BrpNummerverwijzingInhoud inhoud = maakNummerverwijzingInhoud();
        final TussenGroep<T> lo3Groep = new TussenGroep<>((T) inhoud, lo3Historie, lo3Documentatie, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_51, 0, 1));
        final TussenGroep<T> lo3Groep2 = new TussenGroep<>((T) inhoud, lo3Historie2, lo3Documentatie2, HERKOMST_NUMMERVERWIJZING);

        final Map<Long, BrpActie> actieCache = new HashMap<>();
        final List<BrpGroep<T>> brpGroepen = lb23.converteer(Arrays.asList(lo3Groep, lo3Groep2), actieCache);
        assertEquals(2, actieCache.size());

        final BrpActie cacheActie = actieCache.get(BASIS_ACTIE_ID);
        assertNotNull(cacheActie);
        assertTrue(cacheActie.isGebruikt());

        assertEquals(2, brpGroepen.size());
        final BrpGroep<T> brpGroep = brpGroepen.get(0);
        assertSame(inhoud, brpGroep.getInhoud());
        final BrpHistorie brpHistorie = brpGroep.getHistorie();
        assertEquals(BrpDatum.wrap(DATUM_INGANG_GELDIGHEID, null), brpHistorie.getDatumAanvangGeldigheid());
        assertNull(brpHistorie.getDatumEindeGeldigheid());
        assertEquals(new Lo3Datum(DATUM_OPNAME), brpHistorie.getDatumTijdRegistratie().converteerNaarLo3Datum());
        assertEquals(new Lo3Datum(DATUM_OPNAME), brpHistorie.getDatumTijdVerval().converteerNaarLo3Datum());
        assertEquals(cacheActie, brpGroep.getActieInhoud());
        assertNull(brpGroep.getActieGeldigheid());
        assertEquals(brpGroep.getActieInhoud(), brpGroep.getActieVerval());
        assertNull(brpHistorie.getNadereAanduidingVerval());
    }

    @Test
    public <T extends BrpGroepInhoud> void testStap3NationaliteitEnkeleRij() {
        final Lo3Documentatie lo3Documentatie = maakAkte(BASIS_ACTIE_ID);
        final Lo3Historie lo3Historie = new Lo3Historie(null, new Lo3Datum(DATUM_INGANG_GELDIGHEID), new Lo3Datum(DATUM_OPNAME));

        final BrpNationaliteitInhoud inhoud = maakNationaliteitInhoud();
        final TussenGroep<T> lo3Groep = new TussenGroep<>((T) inhoud, lo3Historie, lo3Documentatie, HERKOMST_NATIONALITEIT);

        final Map<Long, BrpActie> actieCache = new HashMap<>();
        final List<BrpGroep<T>> brpGroepen = lb23.converteer(Collections.singletonList(lo3Groep), actieCache);
        assertEquals(1, brpGroepen.size());
        assertEquals(1, actieCache.size());
        final BrpActie cacheActie = actieCache.get(BASIS_ACTIE_ID);
        assertNotNull(cacheActie);
        assertTrue(cacheActie.isGebruikt());
        final BrpGroep<T> brpGroep = brpGroepen.get(0);
        assertEquals(inhoud, brpGroep.getInhoud());
        final BrpHistorie brpHistorie = brpGroep.getHistorie();
        assertEquals(BrpDatum.wrap(DATUM_INGANG_GELDIGHEID, null), brpHistorie.getDatumAanvangGeldigheid());
        assertNull(brpHistorie.getDatumEindeGeldigheid());
        assertEquals(new Lo3Datum(DATUM_OPNAME), brpHistorie.getDatumTijdRegistratie().converteerNaarLo3Datum());
        assertNull(brpHistorie.getDatumTijdVerval());
        assertEquals(cacheActie, brpGroep.getActieInhoud());
        assertNull(brpGroep.getActieGeldigheid());
        assertNull(brpGroep.getActieVerval());
        assertNull(brpHistorie.getNadereAanduidingVerval());
    }

    @Test
    public <T extends BrpGroepInhoud> void testStap3NationaliteitGevuldEnRedenVerlies() {
        final long actieId2 = 2L;
        final int datumOpname2 = 20150801;

        final Lo3Documentatie lo3Documentatie = maakAkte(BASIS_ACTIE_ID);
        final Lo3Historie lo3Historie = new Lo3Historie(null, new Lo3Datum(DATUM_INGANG_GELDIGHEID), new Lo3Datum(DATUM_OPNAME));
        final BrpNationaliteitInhoud inhoud = maakNationaliteitInhoud();
        final TussenGroep<T> lo3Groep = new TussenGroep<>((T) inhoud, lo3Historie, lo3Documentatie, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_54, 0, 1));

        final BrpNationaliteitInhoud.Builder builder = new BrpNationaliteitInhoud.Builder(NATIONALITEIT_CODE);
        builder.redenVerliesNederlandschapCode(new BrpRedenVerliesNederlandschapCode("001"));
        builder.eindeBijhouding(new BrpBoolean(false));
        builder.migratieRedenBeeindigingNationaliteit(new BrpString("401"));
        builder.migratieDatum(new BrpDatum(20150101, null));

        final BrpNationaliteitInhoud inhoud2 = builder.build();
        final Lo3Documentatie lo3Documentatie2 = maakAkte(actieId2);
        final Lo3Historie lo3Historie2 = new Lo3Historie(null, new Lo3Datum(DATUM_INGANG_GELDIGHEID), new Lo3Datum(datumOpname2));
        final TussenGroep<T> lo3Groep2 = new TussenGroep<>((T) inhoud2, lo3Historie2, lo3Documentatie2, HERKOMST_NATIONALITEIT);

        final Map<Long, BrpActie> actieCache = new HashMap<>();
        final List<BrpGroep<T>> brpGroepen = lb23.converteer(Arrays.asList(lo3Groep, lo3Groep2), actieCache);
        assertEquals(2, actieCache.size());

        final BrpActie cacheActie = actieCache.get(BASIS_ACTIE_ID);
        assertNotNull(cacheActie);
        assertTrue(cacheActie.isGebruikt());

        final BrpActie cacheActie2 = actieCache.get(actieId2);
        assertNotNull(cacheActie2);
        assertTrue(cacheActie2.isGebruikt());
        assertEquals(BrpSoortActieCode.CONVERSIE_GBA, cacheActie2.getSoortActieCode());

        assertEquals(1, brpGroepen.size());
        final BrpGroep<T> brpGroep = brpGroepen.get(0);
        final BrpNationaliteitInhoud convInhoud = (BrpNationaliteitInhoud) brpGroep.getInhoud();
        assertEquals(inhoud2.getEindeBijhouding(), convInhoud.getEindeBijhouding());
        assertEquals(inhoud2.getMigratieDatum(), convInhoud.getMigratieDatum());
        assertEquals(inhoud2.getMigratieRedenBeeindigingNationaliteit(), convInhoud.getMigratieRedenBeeindigingNationaliteit());
        assertEquals(inhoud2.getRedenVerliesNederlandschapCode(), convInhoud.getRedenVerliesNederlandschapCode());

        final BrpHistorie brpHistorie = brpGroep.getHistorie();
        assertEquals(BrpDatum.wrap(DATUM_INGANG_GELDIGHEID, null), brpHistorie.getDatumAanvangGeldigheid());
        assertEquals(BrpDatum.wrap(DATUM_INGANG_GELDIGHEID, null), brpHistorie.getDatumEindeGeldigheid());
        assertEquals(new Lo3Datum(DATUM_OPNAME), brpHistorie.getDatumTijdRegistratie().converteerNaarLo3Datum());
        assertEquals(new Lo3Datum(DATUM_OPNAME), brpHistorie.getDatumTijdVerval().converteerNaarLo3Datum());

        assertEquals(cacheActie, brpGroep.getActieInhoud());
        assertEquals(cacheActie2, brpGroep.getActieGeldigheid());
        assertEquals(brpGroep.getActieInhoud(), brpGroep.getActieVerval());
        assertNull(brpHistorie.getNadereAanduidingVerval());
    }

    @Test
    public <T extends BrpGroepInhoud> void testStap3NationaliteitGevuldEnEindeBijhouding() {
        final long actieId2 = 2L;
        final int datumOpname2 = 20150801;

        final Lo3Documentatie lo3Documentatie = maakAkte(BASIS_ACTIE_ID);
        final Lo3Historie lo3Historie = new Lo3Historie(null, new Lo3Datum(DATUM_INGANG_GELDIGHEID), new Lo3Datum(DATUM_OPNAME));
        final BrpNationaliteitInhoud inhoud = maakNationaliteitInhoud();
        final TussenGroep<T> lo3Groep = new TussenGroep<>((T) inhoud, lo3Historie, lo3Documentatie, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_54, 0, 1));

        final BrpNationaliteitInhoud.Builder builder = new BrpNationaliteitInhoud.Builder((BrpNationaliteitCode) null);
        builder.eindeBijhouding(new BrpBoolean(true));
        builder.migratieDatum(new BrpDatum(20150101, null));

        final BrpNationaliteitInhoud inhoud2 = builder.build();
        final Lo3Documentatie lo3Documentatie2 = maakAkte(actieId2);
        final Lo3Historie lo3Historie2 = new Lo3Historie(null, new Lo3Datum(DATUM_INGANG_GELDIGHEID), new Lo3Datum(datumOpname2));
        final TussenGroep<T> lo3Groep2 = new TussenGroep<>((T) inhoud2, lo3Historie2, lo3Documentatie2, HERKOMST_NATIONALITEIT);

        final Map<Long, BrpActie> actieCache = new HashMap<>();
        final List<BrpGroep<T>> brpGroepen = lb23.converteer(Arrays.asList(lo3Groep, lo3Groep2), actieCache);
        assertEquals(2, actieCache.size());

        final BrpActie cacheActie = actieCache.get(BASIS_ACTIE_ID);
        assertNotNull(cacheActie);
        assertTrue(cacheActie.isGebruikt());

        final BrpActie cacheActie2 = actieCache.get(actieId2);
        assertNotNull(cacheActie2);
        assertTrue(cacheActie2.isGebruikt());
        assertEquals(BrpSoortActieCode.CONVERSIE_GBA, cacheActie2.getSoortActieCode());

        assertEquals(1, brpGroepen.size());
        final BrpGroep<T> brpGroep = brpGroepen.get(0);
        final BrpNationaliteitInhoud convInhoud = (BrpNationaliteitInhoud) brpGroep.getInhoud();
        assertEquals(inhoud2.getEindeBijhouding(), convInhoud.getEindeBijhouding());
        assertEquals(inhoud2.getMigratieDatum(), convInhoud.getMigratieDatum());
        assertEquals(inhoud2.getMigratieRedenBeeindigingNationaliteit(), convInhoud.getMigratieRedenBeeindigingNationaliteit());
        assertEquals(inhoud2.getRedenVerliesNederlandschapCode(), convInhoud.getRedenVerliesNederlandschapCode());

        final BrpHistorie brpHistorie = brpGroep.getHistorie();
        assertEquals(BrpDatum.wrap(DATUM_INGANG_GELDIGHEID, null), brpHistorie.getDatumAanvangGeldigheid());
        assertNull(brpHistorie.getDatumEindeGeldigheid());
        assertEquals(new Lo3Datum(DATUM_OPNAME), brpHistorie.getDatumTijdRegistratie().converteerNaarLo3Datum());
        assertEquals(new Lo3Datum(datumOpname2), brpHistorie.getDatumTijdVerval().converteerNaarLo3Datum());

        assertEquals(cacheActie, brpGroep.getActieInhoud());
        assertNull(brpGroep.getActieGeldigheid());
        assertEquals(cacheActie2, brpGroep.getActieVerval());
        assertNull(brpHistorie.getNadereAanduidingVerval());
    }

    private Lo3Documentatie maakAkte(final long actieId) {
        final String waarde = "akte";
        final Lo3String aktenummer = new Lo3String(waarde);
        return new Lo3Documentatie(actieId, GEMEENTE_CODE, aktenummer, null, null, null, null, null);
    }
}

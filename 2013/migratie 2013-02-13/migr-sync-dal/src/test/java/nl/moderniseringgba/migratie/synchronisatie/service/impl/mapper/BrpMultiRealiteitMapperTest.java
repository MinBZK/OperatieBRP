/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import junit.framework.Assert;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpActie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroep;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpHistorie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPartijCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.AbstractBrpGroepInhoud;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.BrpStapelHelper;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.BRPActie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.MultiRealiteitRegel;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.MultiRealiteitRegelHistorie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Partij;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.SoortActie;

import org.junit.Test;

public class BrpMultiRealiteitMapperTest extends BrpAbstractTest {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private BrpMultiRealiteitMapper mapper;

    /**
     * MR en groep hebben geen overlap.
     */
    @Test
    public void testLigtBuiten() {
        LOG.info("\n\n\ntestLigtBuiten()\n");
        final List<BrpGroep<DummyInhoud>> groepen = new ArrayList<BrpGroep<DummyInhoud>>();
        groepen.add(maakGroep(20000101, 20100101, 1, 2));
        groepen.add(maakGroep(20300101, 20400101, 3, 4));
        final BrpStapel<DummyInhoud> stapel = new BrpStapel<DummyInhoud>(groepen);

        final MultiRealiteitRegel multiRealiteit = maakMultiRealiteitRegel(20150101, 20250101, 5, 6);

        final BrpStapel<DummyInhoud> result = mapper.verwerk(stapel, multiRealiteit);

        Assert.assertNotNull(result);
        Assert.assertTrue(BrpStapelHelper.vergelijkStapels(stapel, result));
    }

    /**
     * MR en groep starten en eindigen op hetzelfde moment.
     */
    @Test
    public void testLigtExactOpGrens() {
        LOG.info("\n\n\ntestLigtExactOpGrens()\n");
        final List<BrpGroep<DummyInhoud>> groepen = new ArrayList<BrpGroep<DummyInhoud>>();
        groepen.add(maakGroep(20000101, 20150101, 1, 2));
        groepen.add(maakGroep(20250101, 20400101, 3, 4));
        final BrpStapel<DummyInhoud> stapel = new BrpStapel<DummyInhoud>(groepen);

        final MultiRealiteitRegel multiRealiteit = maakMultiRealiteitRegel(20150101, 20250101, 5, 6);

        final BrpStapel<DummyInhoud> result = mapper.verwerk(stapel, multiRealiteit);

        Assert.assertNotNull(result);

        final List<BrpGroep<DummyInhoud>> expected = new ArrayList<BrpGroep<DummyInhoud>>();
        expected.add(maakGroep(20000101, 20150101, 1, 5));
        expected.add(maakGroep(20250101, 20400101, 6, 4));
        Assert.assertTrue(BrpStapelHelper.vergelijkStapels(new BrpStapel<DummyInhoud>(expected), result));
    }

    @Test
    public void testVolledigOver() {
        LOG.info("\n\n\ntestVolledigOver()\n");
        final List<BrpGroep<DummyInhoud>> groepen = new ArrayList<BrpGroep<DummyInhoud>>();
        groepen.add(maakGroep(20000101, 20100101, 1, 2));
        final BrpStapel<DummyInhoud> stapel = new BrpStapel<DummyInhoud>(groepen);

        final MultiRealiteitRegel multiRealiteit = maakMultiRealiteitRegel(20000101, 20100101, 5, 6);

        final BrpStapel<DummyInhoud> result = mapper.verwerk(stapel, multiRealiteit);

        Assert.assertNull(result);
    }

    @Test
    public void testLigtOverBegin() {
        LOG.info("\n\n\ntestLigtOverBegin()\n");
        final List<BrpGroep<DummyInhoud>> groepen = new ArrayList<BrpGroep<DummyInhoud>>();
        groepen.add(maakGroep(20050101, 20150101, 1, 2));
        final BrpStapel<DummyInhoud> stapel = new BrpStapel<DummyInhoud>(groepen);

        final MultiRealiteitRegel multiRealiteit = maakMultiRealiteitRegel(20000101, 20100101, 5, 6);

        final BrpStapel<DummyInhoud> result = mapper.verwerk(stapel, multiRealiteit);

        final List<BrpGroep<DummyInhoud>> expected = new ArrayList<BrpGroep<DummyInhoud>>();
        expected.add(maakGroep(20100101, 20150101, 6, 2));
        Assert.assertTrue(BrpStapelHelper.vergelijkStapels(new BrpStapel<DummyInhoud>(expected), result));
    }

    @Test
    public void testLigtOverEinde() {
        LOG.info("\n\n\ntestLigtOverEinde()\n");
        final List<BrpGroep<DummyInhoud>> groepen = new ArrayList<BrpGroep<DummyInhoud>>();
        groepen.add(maakGroep(20050101, 20150101, 1, 2));
        final BrpStapel<DummyInhoud> stapel = new BrpStapel<DummyInhoud>(groepen);

        final MultiRealiteitRegel multiRealiteit = maakMultiRealiteitRegel(20100101, 20200101, 5, 6);

        final BrpStapel<DummyInhoud> result = mapper.verwerk(stapel, multiRealiteit);

        final List<BrpGroep<DummyInhoud>> expected = new ArrayList<BrpGroep<DummyInhoud>>();
        expected.add(maakGroep(20050101, 20100101, 1, 5));
        Assert.assertTrue(BrpStapelHelper.vergelijkStapels(new BrpStapel<DummyInhoud>(expected), result));
    }

    @Test
    public void testLigtIn() {
        LOG.info("\n\n\ntestLigtIn()\n");
        final List<BrpGroep<DummyInhoud>> groepen = new ArrayList<BrpGroep<DummyInhoud>>();
        groepen.add(maakGroep(20000101, 20200101, 1, 2));
        final BrpStapel<DummyInhoud> stapel = new BrpStapel<DummyInhoud>(groepen);

        final MultiRealiteitRegel multiRealiteit = maakMultiRealiteitRegel(20100101, 20150101, 5, 6);

        final BrpStapel<DummyInhoud> result = mapper.verwerk(stapel, multiRealiteit);

        final List<BrpGroep<DummyInhoud>> expected = new ArrayList<BrpGroep<DummyInhoud>>();
        expected.add(maakGroep(20000101, 20100101, 1, 5));
        expected.add(maakGroep(20150101, 20200101, 6, 2));
        Assert.assertTrue(BrpStapelHelper.vergelijkStapels(new BrpStapel<DummyInhoud>(expected), result));
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    //
    // private static <T extends BrpGroepInhoud> boolean vergelijkStapels(
    // final BrpStapel<T> expected,
    // final BrpStapel<T> actual) {
    // boolean equal = true;
    // LOG.info("vergelijk(expected={}, actual={})", expected, actual);
    //
    // if ((expected == null) || (actual == null)) {
    // if ((expected == null) != (actual == null)) {
    // LOG.debug("Een van de stapels is null");
    // equal = false;
    // } else {
    // LOG.debug("Beide stapels zijn null");
    // }
    // } else {
    // if (expected.size() != actual.size()) {
    // LOG.info("stapels zijn niet even groot (expected={}, actual={})", expected.size(), actual.size());
    // equal = false;
    // }
    //
    // final List<BrpGroep<T>> expectedCategorieen = getGesorteerdeGroepen(expected);
    // final List<BrpGroep<T>> actualCategorieen = getGesorteerdeGroepen(actual);
    //
    // LOG.debug("Gesorteerde expected categorieen: {}", expectedCategorieen);
    // LOG.debug("Gesorteerde actual categorieen: {}", expectedCategorieen);
    //
    // for (int index = 0; index < expected.size(); index++) {
    // if (index >= actual.size()) {
    // break;
    // }
    //
    // if (!vergelijkGroepen(expectedCategorieen.get(index), actualCategorieen.get(index))) {
    // equal = false;
    // }
    // }
    // }
    //
    // LOG.info("equal: {}", equal);
    // return equal;
    // }
    //
    // private static <T extends BrpGroepInhoud> List<BrpGroep<T>> getGesorteerdeGroepen(final BrpStapel<T> stapel) {
    // final List<BrpGroep<T>> categorieen = stapel.getGroepen();
    // Collections.sort(categorieen, new Comparator<BrpGroep<T>>() {
    //
    // /** Sorteer op datum ingang geldigheid, datum opneming. */
    // @Override
    // public int compare(final BrpGroep<T> arg0, final BrpGroep<T> arg1) {
    // return arg0.getHistorie().getDatumTijdRegistratie()
    // .compareTo(arg1.getHistorie().getDatumTijdRegistratie());
    //
    // }
    // });
    //
    // return categorieen;
    // }
    //
    // private static <T extends BrpGroepInhoud> boolean vergelijkGroepen(
    // final BrpGroep<T> expected,
    // final BrpGroep<T> actual) {
    // LOG.debug("vergelijk(expected={}, actual={})", expected, actual);
    // boolean equal = true;
    //
    // if ((expected == null) || (actual == null)) {
    // if ((expected == null) != (actual == null)) {
    // LOG.debug("Een van de categorieen is null");
    // equal = false;
    // } else {
    // LOG.debug("Beide categorieen zijn null (?)");
    // }
    // } else {
    // if (!equals(expected.getInhoud(), actual.getInhoud())) {
    // LOG.info("inhoud ongelijk\n   expected={}\n   actual={}", expected.getInhoud(), actual.getInhoud());
    // equal = false;
    // }
    // if (!equals(expected.getHistorie(), actual.getHistorie())) {
    // LOG.info("historie ongelijk\n   expected={}\n   actual={}", expected.getHistorie(),
    // actual.getHistorie());
    // equal = false;
    // }
    //
    // if (!vergelijkActies(expected.getActieInhoud(), actual.getActieInhoud())) {
    // LOG.info("actie inhoud ongelijk\n   expected={}\n   actual={}", expected.getActieInhoud(),
    // actual.getActieInhoud());
    // equal = false;
    // }
    //
    // if (!vergelijkActies(expected.getActieVerval(), actual.getActieVerval())) {
    // LOG.info("actie verval ongelijk\n   expected={}\n   actual={}", expected.getActieVerval(),
    // actual.getActieVerval());
    // equal = false;
    // }
    // if (!vergelijkActies(expected.getActieGeldigheid(), actual.getActieGeldigheid())) {
    // LOG.info("actie geldigheid ongelijk\n   expected={}\n   actual={}", expected.getActieGeldigheid(),
    // actual.getActieGeldigheid());
    // equal = false;
    // }
    // }
    //
    // return equal;
    // }
    //
    // private static boolean vergelijkActies(final BrpActie expected, final BrpActie actual) {
    // LOG.debug("vergelijkActies(expected={}, actual={})", expected, actual);
    // boolean equal = true;
    //
    // if ((expected == null) || (actual == null)) {
    // if ((expected == null) != (actual == null)) {
    // LOG.debug("Een van de acties is null");
    // equal = false;
    // }
    // } else {
    // if (!expected.getId().equals(actual.getId())) {
    // LOG.info("actie id ongelijk\n   expected={}\n   actual={}", expected.getId(), actual.getId());
    // equal = false;
    // }
    //
    // if (!expected.equals(actual)) {
    // LOG.info("actie inhoudelijk ongelijk\n   expected={}\n   actual={}", expected, actual);
    // equal = false;
    // }
    // }
    //
    // return equal;
    // }
    //
    // public static boolean equals(final Object expected, final Object actual) {
    // final boolean result;
    // if (expected == null) {
    // if (actual == null) {
    // result = true;
    // } else {
    // result = false;
    // }
    // } else {
    // if (actual == null) {
    // result = false;
    // } else {
    // result = expected.equals(actual);
    // }
    // }
    // return result;
    // }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private BrpGroep<DummyInhoud> maakGroep(
            final Integer dtRegistratie,
            final Integer dtVerval,
            final Integer idInhoud,
            final Integer idVerval) {
        final DummyInhoud inhoud = new DummyInhoud();
        final BrpHistorie historie =
                new BrpHistorie(null, null, maakDatumTijd(dtRegistratie), maakDatumTijd(dtVerval));
        final BrpActie actieInhoud = maakActie(idInhoud, dtRegistratie);
        final BrpActie actieVerval = maakActie(idVerval, dtVerval);

        return new BrpGroep<DummyInhoud>(inhoud, historie, actieInhoud, actieVerval, null);
    }

    private BrpActie maakActie(final Integer id, final Integer dt) {
        return id == null ? null : new BrpActie(id.longValue(), BrpSoortActieCode.CONVERSIE_GBA,
                BrpPartijCode.MIGRATIEVOORZIENING, null, null, maakDatumTijd(dt), null, 0, null);

    }

    private BrpDatumTijd maakDatumTijd(final Integer dt) {
        return dt == null ? null : BrpDatumTijd.fromDatumTijd(dt.longValue() * 1000000);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private MultiRealiteitRegel maakMultiRealiteitRegel(
            final Integer dtRegistratie,
            final Integer dtVerval,
            final Integer idInhoud,
            final Integer idVerval) {
        final MultiRealiteitRegelHistorie historie = new MultiRealiteitRegelHistorie();
        historie.setDatumTijdRegistratie(maakTimestamp(dtRegistratie));
        historie.setDatumTijdVerval(maakTimestamp(dtVerval));
        historie.setActieInhoud(maakBRPActie(idInhoud, dtRegistratie));
        historie.setActieVerval(maakBRPActie(idVerval, dtVerval));

        final MultiRealiteitRegel result = new MultiRealiteitRegel();
        result.addHisMultirealiteitregel(historie);

        return result;
    }

    private BRPActie maakBRPActie(final Integer id, final Integer dtRegistratie) {
        if (id == null) {
            return null;
        }

        final BRPActie result = new BRPActie();
        result.setId(id.longValue());
        result.setDatumTijdRegistratie(maakTimestamp(dtRegistratie));
        result.setPartij(new Partij());
        result.getPartij().setNaam(BrpPartijCode.MIGRATIEVOORZIENING.getNaam());
        result.getPartij().setGemeentecode(
                BrpPartijCode.MIGRATIEVOORZIENING.getGemeenteCode() == null ? null : new BigDecimal(
                        BrpPartijCode.MIGRATIEVOORZIENING.getGemeenteCode()));
        result.setSoortActie(SoortActie.CONVERSIE_GBA);

        return result;
    }

    private Timestamp maakTimestamp(final Integer dt) {
        return dt == null ? null : timestamp(dt.toString() + "000000");
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private static final class DummyInhoud extends AbstractBrpGroepInhoud {
        @Override
        public boolean isLeeg() {
            return false;
        }

        @Override
        public int hashCode() {
            return 42;
        }

        @Override
        public boolean equals(final Object other) {
            return other instanceof DummyInhoud;
        }
    }
}

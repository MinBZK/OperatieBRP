/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

//package nl.bzk.brp.dataaccess.modelverificatie;
//
//import java.math.BigInteger;
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.Query;
//
//import nl.bzk.brp.dataaccess.test.AbstractDBUnitIntegratieTest;
//import nl.bzk.brp.model.algemeen.stamgegeven.autaut.CatalogusOptie;
//import org.junit.Assert;
//import org.junit.Ignore;
//import org.junit.Test;
//
///**
// * Deze test controleert of de enum van Catalogusoptie overeenkomstig is met de waarden in de database. Deze enum wordt
// * namelijk handmatig bijgewerkt.
// */
//@Ignore
//public class CatalogusOptieTest extends AbstractDBUnitIntegratieTest {
//    private static final String CATEGORIE_DIENST = "categoriedienst";
//    private static final String EFFECT_AFNEMERINDICATIES = "effectafnemerindicaties";
//    private static final String AAND_MEDIUM = "aandmedium";
//
//    @PersistenceContext(unitName = "nl.bzk.brp.alleenlezen")
//    private EntityManager em;
//
//    @Test
//    public void testAantalCatalogusOpties() {
//        final long aantalCatalogusOptiesVolgensDatabase = geefAantalCatalogusOptiesInDatabase();
//
//        // Aantal catalogusopties van de enum minus de DUMMY-waarde.
//        final int aantalCatalogusOptiesVolgensModel = CatalogusOptie.values().length - 1;
//
//        Assert.assertEquals(aantalCatalogusOptiesVolgensDatabase, aantalCatalogusOptiesVolgensModel);
//    }
//
//    @Test
//    public void testInhoudCatalogusOpties() {
//        final long aantalCatalogusOptiesVolgensDatabase = geefAantalCatalogusOptiesInDatabase();
//
//        final Map<Integer, Map<String, Integer>> catalogusOptiesVolgensDatabase = new HashMap<>();
//        for (int i = 1; i <= aantalCatalogusOptiesVolgensDatabase; i++) {
//            final Query query =
//                    em.createNativeQuery("SELECT categoriedienst, effectafnemerindicaties, aandmedium "
//                                                 + "FROM autaut.CatalogusOptie WHERE id = :id ;");
//            query.setParameter("id", i);
//
//            final Object[] result = (Object[]) query.getSingleResult();
//            final Integer categoriedienst = ((Short) result[0]).intValue();
//            Integer effectafnemerindicaties = null;
//            if (result[1] != null) {
//                effectafnemerindicaties = ((Short) result[1]).intValue();
//            }
//            Integer aandmedium = null;
//            if (result[2] != null) {
//                aandmedium = ((Short) result[2]).intValue();
//            }
//
//            final Map<String, Integer> waardenVanCatalogusOptie = new HashMap<>();
//            waardenVanCatalogusOptie.put(CATEGORIE_DIENST, categoriedienst);
//            waardenVanCatalogusOptie.put(EFFECT_AFNEMERINDICATIES, effectafnemerindicaties);
//            waardenVanCatalogusOptie.put(AAND_MEDIUM, aandmedium);
//
//            catalogusOptiesVolgensDatabase.put(i, waardenVanCatalogusOptie);
//        }
//
//        // Controleert de inhoud van de catalogusopties. Hier worden de database-waarden vergeleken met de model-
//        // waarden.
//        for (final Integer catalogusOptieId : catalogusOptiesVolgensDatabase.keySet()) {
//
//            final Map<String, Integer> catalogusOptieMap = catalogusOptiesVolgensDatabase.get(catalogusOptieId);
//            final Integer categorieDienst = catalogusOptieMap.get(CATEGORIE_DIENST);
//            final Integer effectAfnemerindicaties = catalogusOptieMap.get(EFFECT_AFNEMERINDICATIES);
//            final Integer aandMedium = catalogusOptieMap.get(AAND_MEDIUM);
//
//            final CatalogusOptie catalogusOptieUitModel = CatalogusOptie.values()[catalogusOptieId];
//            Integer categorieDienstUitModel = null;
//            if (catalogusOptieUitModel.getCategorieDienst() != null) {
//                categorieDienstUitModel = catalogusOptieUitModel.getCategorieDienst().ordinal();
//            }
//            Assert.assertEquals(categorieDienstUitModel, categorieDienst);
//
//            Integer effectAfnemerindicatiesUitModel = null;
//            if (catalogusOptieUitModel.getEffectAfnemerindicaties() != null) {
//                effectAfnemerindicatiesUitModel = catalogusOptieUitModel.getEffectAfnemerindicaties().ordinal();
//            }
//            Assert.assertEquals(effectAfnemerindicatiesUitModel, effectAfnemerindicaties);
//
//            Integer aandMediumUitModel = null;
//            if (catalogusOptieUitModel.getAanduidingMedium() != null) {
//                aandMediumUitModel = catalogusOptieUitModel.getAanduidingMedium().ordinal();
//            }
//            Assert.assertEquals(aandMediumUitModel, aandMedium);
//        }
//    }
//
//    /**
//     * Geeft het aantal catalogus opties in de database.
//     *
//     * @return aantal catalogusopties
//     */
//    private long geefAantalCatalogusOptiesInDatabase() {
//        final Query query = em.createNativeQuery("SELECT COUNT(*) FROM autaut.CatalogusOptie;");
//        return ((BigInteger) query.getSingleResult()).longValue();
//    }
//
//}

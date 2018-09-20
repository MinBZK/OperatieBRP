/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.levering.mutatiebericht;

import java.lang.reflect.Method;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import junit.framework.Assert;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Test om te controleren dat de enumeraties die migratie en BRP gebruiken wel overeenkomen. (BRP gebruikt de ordinal,
 * Migratie gebruikt een ID veld).
 */
@Ignore("Migratie zit op BMR44, BRP (trunk) op BMR43")
public class EnumeratieKoppelvlakTest {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Test
    public void vergelijkKernBijhoudingsaard() throws ReflectiveOperationException {
        vergelijkEnumeraties(
            nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Bijhoudingsaard.class,
            "getId",
            "getCode",
            nl.bzk.brp.model.algemeen.stamgegeven.kern.Bijhoudingsaard.class,
            "getCode");
    }

    @Test
    public void vergelijkKernBurgerzakenModule() throws ReflectiveOperationException {
        vergelijkEnumeraties(
            nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BurgerzakenModule.class,
            "getId",
            "getNaam",
            nl.bzk.brp.model.algemeen.stamgegeven.kern.BurgerzakenModule.class,
            "getNaam");
    }

    @Test
    public void vergelijkKernCategorieAdministratieveHandeling() throws ReflectiveOperationException {
        vergelijkEnumeraties(
            nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.CategorieAdministratieveHandeling.class,
            "getId",
            "getNaam",
            nl.bzk.brp.model.algemeen.stamgegeven.kern.CategorieAdministratieveHandeling.class,
            "getNaam");
    }

    // @Test
    // public void vergelijkKernCategoriePersonen() throws ReflectiveOperationException {
    // vergelijkEnumeraties(
    // nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.CategoriePersonen.class,
    // "getId",
    // "getNaam",
    // nl.bzk.brp.model.algemeen.stamgegeven.kern.CategoriePersonen.class,
    // "getNaam");
    // }

    @Test
    public void vergelijkKernElement() throws ReflectiveOperationException {
        final Map<Short, nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum> brpElementenOpId = new HashMap<>();
        for (final nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum brpElement : nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum.values()) {
            brpElementenOpId.put(brpElement.getId().shortValue(), brpElement);
        }

        for (final nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Element migElement : nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Element.values()) {
            final nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum brpElement = brpElementenOpId.get(migElement.getId());

            LOGGER.info("{} ({}); {} -> {}; {}", migElement, migElement.getId(), brpElement);

            vergelijk(migElement.toString(), brpElement.toString());
        }
    }

    @Test
    public void vergelijkKernFunctieAdres() throws ReflectiveOperationException {
        vergelijkEnumeraties(
            nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.FunctieAdres.class,
            "getId",
            "getCode",
            nl.bzk.brp.model.algemeen.stamgegeven.kern.FunctieAdres.class,
            "getCode");
    }

    @Test
    public void vergelijkKernGeslachtsaanduiding() throws ReflectiveOperationException {
        vergelijkEnumeraties(
            nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Geslachtsaanduiding.class,
            "getId",
            "getCode",
            nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding.class,
            "getCode");
    }

    @Test
    public void vergelijkKernKoppelvlak() throws ReflectiveOperationException {
        vergelijkEnumeraties(
            nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Koppelvlak.class,
            "getId",
            "getNaam",
            nl.bzk.brp.model.algemeen.stamgegeven.kern.Koppelvlak.class,
            "getNaam");
    }

    @Test
    public void vergelijkKernNaamgebruik() throws ReflectiveOperationException {
        vergelijkEnumeraties(
            nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Naamgebruik.class,
            "getId",
            "getCode",
            nl.bzk.brp.model.algemeen.stamgegeven.kern.Naamgebruik.class,
            "getCode");
    }

    @Test
    public void vergelijkKernNadereBijhoudingsaard() throws ReflectiveOperationException {
        vergelijkEnumeraties(
            nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.NadereBijhoudingsaard.class,
            "getId",
            "getCode",
            nl.bzk.brp.model.algemeen.stamgegeven.kern.NadereBijhoudingsaard.class,
            "getCode");
    }

    @Test
    @Ignore("Kern.Regel bestaat niet in migratie")
    public void vergelijkKernRegel() throws ReflectiveOperationException {
        // vergelijkEnumeraties(
        // nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Regel.class,
        // "getId",
        // "getCode",
        // nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel.class,
        // "getCode");
    }

    @Test
    public void vergelijkKernRol() throws ReflectiveOperationException {
        vergelijkEnumeraties(
            nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Rol.class,
            "getId",
            "getNaam",
            nl.bzk.brp.model.algemeen.stamgegeven.kern.Rol.class,
            "getNaam");
    }

    @Test
    public void vergelijkKernSoortActie() throws ReflectiveOperationException {
        vergelijkEnumeraties(
            nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortActie.class,
            "getId",
            "getNaam",
            nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie.class,
            "getNaam");
    }

    @Test
    public void vergelijkKernSoortAdministratieveHandeling() throws ReflectiveOperationException {
        vergelijkEnumeraties(
            nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortAdministratieveHandeling.class,
            "getId",
            "getCode",
            nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling.class,
            "getCode");
    }

    @Test
    public void vergelijkKernSoortBetrokkenheid() throws ReflectiveOperationException {
        vergelijkEnumeraties(
            nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortBetrokkenheid.class,
            "getId",
            "getCode",
            nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid.class,
            "getCode");
    }

    @Test
    public void vergelijkKernSoortElement() throws ReflectiveOperationException {
        vergelijkEnumeraties(
            nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortElement.class,
            "getId",
            "getNaam",
            nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortElement.class,
            "getNaam");
    }

    @Test
    public void vergelijkKernSoortElementAutorisatie() throws ReflectiveOperationException {
        vergelijkEnumeraties(
            nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortElementAutorisatie.class,
            "getId",
            "getNaam",
            nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortElementAutorisatie.class,
            "getNaam");
    }

    @Test
    public void vergelijkKernSoortIndicatie() throws ReflectiveOperationException {
        vergelijkEnumeraties(
            nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortIndicatie.class,
            "getId",
            "getOmschrijving",
            nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie.class,
            "getNaam");
    }

    @Test
    public void vergelijkKernSoortMigratie() throws ReflectiveOperationException {
        vergelijkEnumeraties(
            nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortMigratie.class,
            "getId",
            "getCode",
            nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortMigratie.class,
            "getCode");
    }

    // @Test
    // public void vergelijkKernSoortPartij() throws ReflectiveOperationException {
    // vergelijkEnumeraties(
    // nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortPartij.class,
    // "getId",
    // "getOmschrijving",
    // nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPartij.class,
    // "getNaam");
    // }

    @Test
    public void vergelijkKernSoortPartijOnderzoek() throws ReflectiveOperationException {
        vergelijkEnumeraties(
            nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortPartijOnderzoek.class,
            "getId",
            "getNaam",
            nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPartijOnderzoek.class,
            "getNaam");
    }

    @Test
    public void vergelijkKernSoortPersoon() throws ReflectiveOperationException {
        vergelijkEnumeraties(
            nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortPersoon.class,
            "getId",
            "getCode",
            nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon.class,
            "getCode");
    }

    @Test
    public void vergelijkKernSoortPersoonOnderzoek() throws ReflectiveOperationException {
        vergelijkEnumeraties(
            nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortPersoonOnderzoek.class,
            "getId",
            "getNaam",
            nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonOnderzoek.class,
            "getNaam");
    }

    @Test
    @Ignore("SoortRechtsgrond bevat geen tuples")
    public void vergelijkKernSoortRechtsgrond() throws ReflectiveOperationException {
        vergelijkEnumeraties(
            nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortRechtsgrond.class,
            "getId",
            "getNaam",
            nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRechtsgrond.class,
            "getNaam");
    }

    @Test
    public void vergelijkKernSoortRelatie() throws ReflectiveOperationException {
        vergelijkEnumeraties(
            nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortRelatie.class,
            "getId",
            "getNaam",
            nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie.class,
            "getNaam");
    }

    @Test
    public void vergelijkKernStatusOnderzoek() throws ReflectiveOperationException {
        vergelijkEnumeraties(
            nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.StatusOnderzoek.class,
            "getId",
            "getNaam",
            nl.bzk.brp.model.algemeen.stamgegeven.kern.StatusOnderzoek.class,
            "getNaam");
    }

    @Test
    @Ignore("Kern.StatusTerugmelding bestaat niet in migratie")
    public void vergelijkKernStatusTerugmelding() throws ReflectiveOperationException {
        // vergelijkEnumeraties(
        // nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.StatusTerugmelding.class,
        // "getId",
        // "getCode",
        // nl.bzk.brp.model.algemeen.stamgegeven.kern.StatusTerugmelding.class,
        // "getCode");
    }

    @Test
    public void vergelijkKernStelsel() throws ReflectiveOperationException {
        vergelijkEnumeraties(
            nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Stelsel.class,
            "getId",
            "getNaam",
            nl.bzk.brp.model.algemeen.stamgegeven.kern.Stelsel.class,
            "getNaam");
    }

    // @Test
    // public void vergelijkAutAutAanduidingMedium() throws ReflectiveOperationException {
    // vergelijkEnumeraties(
    // nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.AanduidingMedium.class,
    // "getId",
    // "getNaam",
    // nl.bzk.brp.model.algemeen.stamgegeven.autaut.AanduidingMedium.class,
    // "getNaam");
    // }

    // @Test
    // public void vergelijkAutAutCatalogusOptie() throws ReflectiveOperationException {
    // for (final nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.CatalogusOptie migCatalogusOptie :
    // nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.CatalogusOptie.values()) {
    // final nl.bzk.brp.model.algemeen.stamgegeven.autaut.CatalogusOptie brpCatalogusOptie =
    // nl.bzk.brp.model.algemeen.stamgegeven.autaut.CatalogusOptie.values()[migCatalogusOptie.getId()];
    //
    // vergelijk(migCatalogusOptie.getCategorieDienst().getId(), brpCatalogusOptie.getCategorieDienst().ordinal());
    // if (migCatalogusOptie.getEffectAfnemerindicaties() != null) {
    // vergelijk(migCatalogusOptie.getEffectAfnemerindicaties().getId(),
    // brpCatalogusOptie.getEffectAfnemerindicaties().ordinal());
    // }
    // if (migCatalogusOptie.getAanduidingMedium() != null) {
    // vergelijk(migCatalogusOptie.getAanduidingMedium().getId(), brpCatalogusOptie.getAanduidingMedium().ordinal());
    // }
    // }
    // }

    // @Test
    // public void vergelijkAutAutCategorieDienst() throws ReflectiveOperationException {
    // vergelijkEnumeraties(
    // nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.CategorieDienst.class,
    // "getId",
    // "getNaam",
    // nl.bzk.brp.model.algemeen.stamgegeven.autaut.CategorieDienst.class,
    // "getNaam");
    // }

    @Test
    public void vergelijkAutAutEffectAfnemerindicaties() throws ReflectiveOperationException {
        vergelijkEnumeraties(
            nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.EffectAfnemerindicaties.class,
            "getId",
            "getNaam",
            nl.bzk.brp.model.algemeen.stamgegeven.autaut.EffectAfnemerindicaties.class,
            "getNaam");
    }

    // @Test
    // public void vergelijkAutAutFunctie() throws ReflectiveOperationException {
    // vergelijkEnumeraties(
    // nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.Functie.class,
    // "getId",
    // "getNaam",
    // nl.bzk.brp.model.algemeen.stamgegeven.autaut.Functie.class,
    // "getNaam");
    // }

    // @Test
    // public void vergelijkAutAutKanaal() throws ReflectiveOperationException {
    // vergelijkEnumeraties(
    // nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.Kanaal.class,
    // "getId",
    // "getNaam",
    // nl.bzk.brp.model.algemeen.stamgegeven.autaut.Kanaal.class,
    // "getNaam");
    // }

    @Test
    public void vergelijkAutAutProtocolleringsniveau() throws ReflectiveOperationException {
        vergelijkEnumeraties(
            nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.Protocolleringsniveau.class,
            "getId",
            "getNaam",
            nl.bzk.brp.model.algemeen.stamgegeven.autaut.Protocolleringsniveau.class,
            "getNaam");
    }

    // @Test
    // public void vergelijkAutAutSoortAutorisatiebesluit() throws ReflectiveOperationException {
    // vergelijkEnumeraties(
    // nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.SoortAutorisatiebesluit.class,
    // "getId",
    // "getNaam",
    // nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortAutorisatiebesluit.class,
    // "getNaam");
    // }

    // @Test
    // public void vergelijkAutAutSoortBevoegdheid() throws ReflectiveOperationException {
    // vergelijkEnumeraties(
    // nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.SoortBevoegdheid.class,
    // "getId",
    // "getNaam",
    // nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortBevoegdheid.class,
    // "getNaam");
    // }

    // @Test
    // public void vergelijkAutAutToestand() throws ReflectiveOperationException {
    // vergelijkEnumeraties(
    // nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.Toestand.class,
    // "getId",
    // "getNaam",
    // nl.bzk.brp.model.algemeen.stamgegeven.autaut.Toestand.class,
    // "getNaam");
    // }

    @Test
    @Ignore("Ber enums worden niet gebruikt in migratie software")
    public void vergelijkBerEnums() throws ReflectiveOperationException {
        // Bijhoudingsresultaat.class
        // Bijhoudingssituatie.class
        // Historievorm.class
        // Richting.class
        // SoortBericht.class
        // SoortMelding.class
        // SoortSynchronisatie.class
        // Verwerkingsresultaat.class
        // Verwerkingswijze.class
    }

    @Test
    @Ignore("BRM enums worden niet gebruikt in migratie software")
    public void vergelijkBrmEnums() throws ReflectiveOperationException {
        // RegelSoortBericht.class
        // Regeleffect.class
        // SoortRegel.class
    }

    @Test
    public void vergelijkVerConvLo3BerichtenBron() throws ReflectiveOperationException {
        vergelijkEnumeraties(
            nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3BerichtenBron.class,
            "getId",
            "getNaam",
            nl.bzk.brp.model.algemeen.stamgegeven.verconv.LO3BerichtenBron.class,
            "getNaam");
    }

    @Test
    public void vergelijkVerConvLo3CategorieMelding() throws ReflectiveOperationException {
        vergelijkEnumeraties(
            nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3CategorieMelding.class,
            "getId",
            "getNaam",
            nl.bzk.brp.model.algemeen.stamgegeven.verconv.LO3CategorieMelding.class,
            "getNaam");
    }

    @Test
    public void vergelijkVerConvLo3Severity() throws ReflectiveOperationException {

        vergelijkEnumeraties(
            nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3Severity.class,
            "getId",
            "getNaam",
            nl.bzk.brp.model.algemeen.stamgegeven.verconv.LO3Severity.class,
            "getNaam");
    }

    @Test

    public void vergelijkVerConvAanduidingOuder() throws ReflectiveOperationException {
        vergelijkEnumeraties(
            nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AanduidingOuder.class,
            "getId",
            "getId",
            nl.bzk.brp.model.algemeen.stamgegeven.verconv.LO3SoortAanduidingOuder.class,
            "ordinal");
    }

    @Test
    @Ignore("Deze is helemaal stuk; door 'sparse' ids")
    public void vergelijkVerConvLo3SoortMelding() throws ReflectiveOperationException {
        vergelijkEnumeraties(
            nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3SoortMelding.class,
            "getId",
            "getCode",
            nl.bzk.brp.model.algemeen.stamgegeven.verconv.LO3SoortMelding.class,
            "getCode");
    }

    @Test
    public void vergelijkMigBlokRedenBlokkering() throws ReflectiveOperationException {
        vergelijkEnumeraties(
            nl.bzk.migratiebrp.synchronisatie.dal.domein.blokkering.entity.RedenBlokkering.class,
            "getId",
            "getNaam",
            nl.bzk.brp.model.algemeen.stamgegeven.migblok.RedenBlokkering.class,
            "getNaam");
    }

    private <M extends Enum<?>, B extends Enum<?>> void vergelijkEnumeraties(
        final Class<M> migratieEnumClass,
        final String migratieGetIdMethodName,
        final String migratieGetCodeMethodName,
        final Class<B> brpEnumClass,
        final String brpGetCodeMethodName) throws ReflectiveOperationException
    {
        final Method migratieGetIdMethod = migratieEnumClass.getMethod(migratieGetIdMethodName);
        final Method migratieGetCodeMethod = migratieEnumClass.getMethod(migratieGetCodeMethodName);
        final Method brpGetCodeMethod = brpEnumClass.getMethod(brpGetCodeMethodName);

        final Map<Integer, B> brpEnums = new HashMap<>();
        for (final B brpEnum : getValues(brpEnumClass)) {
            brpEnums.put(brpEnum.ordinal(), brpEnum);
        }
        Assert.assertEquals("DUMMY", brpEnums.remove(0).name());

        for (final M migratieEnum : getValues(migratieEnumClass)) {
            final Number migratieId = (Number) migratieGetIdMethod.invoke(migratieEnum);

            final B brpEnum = brpEnums.remove(migratieId.intValue());

            final Object migratieCode = migratieGetCodeMethod.invoke(migratieEnum);
            final Object brpCode = brpGetCodeMethod.invoke(brpEnum);

            LOGGER.info("{} ({}); {} -> {}; {}", migratieEnum, migratieId, migratieCode, brpEnum, brpCode);

            vergelijk(migratieCode, brpCode);
        }

        final Iterator<B> vervallenIterator = brpEnums.values().iterator();
        while (vervallenIterator.hasNext()) {
            final B vervallenCheck = vervallenIterator.next();
            if (vervallenCheck.name().contains("VERVALLEN")) {
                vervallenIterator.remove();
            }
        }

        Assert.assertEquals(0, brpEnums.size());
    }

    private void vergelijk(final Object migratieCode, final Object brpCode) {
        if (migratieCode instanceof String) {

            // Assert.assertEquals(unaccent((String) migratieCode), unaccent((String) brpCode));

            if (((String) migratieCode).length() != ((String) brpCode).length()) {
                Assert.fail("expected:" + migratieCode + " but was:" + brpCode);
            }
            // Vergelijk zonder 'moeilijke' tekens
            for (int index = 0; index < ((String) migratieCode).length(); index++) {
                final String migratieDeel = ((String) migratieCode).substring(index, index + 1);
                if (migratieDeel.equals(unaccent(migratieDeel))) {
                    final int migratieCodepoint = ((String) migratieCode).codePointAt(index);
                    final int brpCodepoint = ((String) brpCode).charAt(index);
                    if (migratieCodepoint != brpCodepoint) {
                        Assert.fail("expected:" + migratieCode + " but was:" + brpCode);

                    }
                }
            }
        } else if (migratieCode instanceof Number) {
            Assert.assertEquals(((Number) migratieCode).longValue(), ((Number) brpCode).longValue());
        } else {
            Assert.assertEquals(migratieCode, brpCode);
        }
    }

    private String unaccent(final String value) {
        final String temp = Normalizer.normalize(value, Normalizer.Form.NFD);
        return temp.replaceAll("[^\\p{ASCII}]", "");
    }

    @SuppressWarnings("unchecked")
    private <M extends Enum<?>> M[] getValues(final Class<M> enumClass) throws ReflectiveOperationException {
        final Method valuesMethod = enumClass.getMethod("values");
        return (M[]) valuesMethod.invoke(null);
    }
}

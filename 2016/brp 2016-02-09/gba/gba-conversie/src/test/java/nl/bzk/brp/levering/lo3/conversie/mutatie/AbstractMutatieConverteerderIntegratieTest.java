/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.brp.levering.dataaccess.AbstractIntegratieTest;
import nl.bzk.brp.levering.lo3.conversie.ConversieCache;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.HistorieSet;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.FunctieAdres;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Naamgebruik;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonAdresHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.util.hisvolledig.kern.PersoonAdresHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.junit.Assert;
import org.junit.Before;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import support.PersoonHisVolledigUtil;

@ContextConfiguration(locations = "classpath:config/mutatieconverteerder-test-context.xml", inheritLocations = false)
public abstract class AbstractMutatieConverteerderIntegratieTest extends AbstractIntegratieTest {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    protected MutatieConverteerder subject;
    //
    // @Inject
    // protected TestVerConvRepository verConvRepository;
    // @Inject
    // protected TestLo3AanduidingOuderRepository lo3AanduidingOuderRepository;

    protected Partij partij = PersoonHisVolledigUtil.maakPartij();

    protected PersoonHisVolledigImplBuilder builder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);

    protected ActieModel actieGeboorte =
            PersoonHisVolledigUtil.maakActie(1L, SoortAdministratieveHandeling.GEBOORTE_IN_NEDERLAND, SoortActie.REGISTRATIE_GEBOORTE, 19200101, partij);

    protected void voegGeboorteToe(final PersoonHisVolledigImplBuilder builder, final ActieModel actieGeboorte) {
        // Geboorte
        final PersoonAdresHisVolledigImpl adres =
                new PersoonAdresHisVolledigImplBuilder().nieuwStandaardRecord(actieGeboorte)
                                                        .aangeverAdreshouding("I")
                                                        .datumAanvangAdreshouding(actieGeboorte.getDatumAanvangGeldigheid())
                                                        .gemeente((short) 518)
                                                        .gemeentedeel("deel vd gemeente")
                                                        .huisletter("A")
                                                        .huisnummer(10)
                                                        .huisnummertoevoeging("B")
                                                        .landGebied((short) 6030)
                                                        .naamOpenbareRuimte("naam")
                                                        .postcode("2245HJ")
                                                        .redenWijziging("P")
                                                        .soort(FunctieAdres.WOONADRES)
                                                        .woonplaatsnaam("Rotterdam")
                                                        .eindeRecord(1001)
                                                        .build();
        ReflectionTestUtils.setField(adres, "iD", 34544);
        builder.voegPersoonAdresToe(adres);

        builder.nieuwGeboorteRecord(actieGeboorte)
               .datumGeboorte(actieGeboorte.getDatumAanvangGeldigheid())
               .gemeenteGeboorte((short) 518)
               .landGebiedGeboorte((short) 6030)
               .eindeRecord(1002);
        builder.nieuwGeslachtsaanduidingRecord(actieGeboorte).geslachtsaanduiding(Geslachtsaanduiding.MAN).eindeRecord(1003);
        builder.nieuwIdentificatienummersRecord(actieGeboorte).administratienummer(1234567890L).burgerservicenummer(123456789).eindeRecord(1004);
        builder.nieuwInschrijvingRecord(actieGeboorte)
               .datumInschrijving(actieGeboorte.getDatumAanvangGeldigheid())
               .versienummer(1L)
               .datumtijdstempel(new Date(123))
               .eindeRecord(1005);
        builder.nieuwNaamgebruikRecord(actieGeboorte).indicatieNaamgebruikAfgeleid(true).naamgebruik(Naamgebruik.EIGEN).eindeRecord(1006);
        builder.nieuwAfgeleidAdministratiefRecord(actieGeboorte)
               .indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig(false)
               .tijdstipLaatsteWijziging(actieGeboorte.getTijdstipRegistratie())
               .tijdstipLaatsteWijzigingGBASystematiek(actieGeboorte.getTijdstipRegistratie())
               .eindeRecord(1007);
        builder.nieuwSamengesteldeNaamRecord(actieGeboorte)
               .geslachtsnaamstam("geslachtsnaam")
               .voorvoegsel("de")
               .voornamen("Voornaam1 Voornaam2")
               .scheidingsteken(" ")
               .eindeRecord(1008);
        builder.nieuwBijhoudingRecord(actieGeboorte).bijhoudingspartij(51801).eindeRecord(1009);
    }

    @Before
    public void setup() {
        voegGeboorteToe(builder, actieGeboorte);
        // verConvRepository.reset();
        // lo3AanduidingOuderRepository.reset();
    }

    protected List<Lo3CategorieWaarde> uitvoeren(final PersoonHisVolledig persoon, final ActieModel actie) {
        final List<Lo3CategorieWaarde> resultaat = subject.converteer(persoon, null, actie.getAdministratieveHandeling(), new ConversieCache());

        LOGGER.info("Resultaat ({}):", resultaat.size());
        for (final Lo3CategorieWaarde categorie : resultaat) {
            LOGGER.info(" - {}", categorie);
        }

        return resultaat;
    }

    protected List<Lo3CategorieWaarde> uitvoeren(final ActieModel... acties) {
        Assert.assertTrue("Geen actie meegegeven bij uitvoeren", acties.length > 0);
        final PersoonHisVolledigImpl persoon = builder.build();
        final List<ActieModel> alleActies = new ArrayList<>(Arrays.asList(acties));
        alleActies.add(actieGeboorte);
        PersoonHisVolledigUtil.maakVerantwoording(persoon, alleActies.toArray(new ActieModel[] {}));

        return uitvoeren(persoon, acties[0]);
    }

    protected void assertElementen(
        final List<Lo3CategorieWaarde> resultaat,
        final Lo3CategorieEnum categorie,
        final boolean volledig,
        final Object... elementen)
    {
        Assert.assertTrue(elementen.length % 2 == 0);
        boolean found = false;
        for (final Lo3CategorieWaarde categorieWaarde : resultaat) {
            if (categorie == categorieWaarde.getCategorie()) {
                found = true;

                for (int elementIndex = 0; elementIndex < elementen.length; elementIndex = elementIndex + 2) {
                    final Lo3ElementEnum element = (Lo3ElementEnum) elementen[elementIndex];
                    final String elementWaarde = (String) elementen[elementIndex + 1];

                    Assert.assertTrue(
                        "Element "
                                      + element.getElementNummer(true)
                                      + " ("
                                      + element.getLabel()
                                      + ") komt niet voor in categorie "
                                      + categorie
                                      + " ("
                                      + categorie.getLabel()
                                      + ").",
                        categorieWaarde.getElementen().containsKey(element));

                    Assert.assertEquals(
                        "Element "
                                        + element.getElementNummer(true)
                                        + " ("
                                        + element.getLabel()
                                        + ") in categorie "
                                        + categorie
                                        + " ("
                                        + categorie.getLabel()
                                        + ") heeft niet de verwachte waarde.",
                        elementWaarde,
                        categorieWaarde.getElement(element));
                }

                if (volledig) {
                    Assert.assertEquals("Ander aantal elementen dan verwacht in categorie.", elementen.length / 2, categorieWaarde.getElementen().size());
                }
            }
        }
        Assert.assertTrue("Categorie " + categorie + " niet gevonden in resultaat", found);
    }

    protected void debugHistorieSet(final Object builder, final String historieGetMethodeNaam) {
        final Object hisVolledigImpl = ReflectionTestUtils.getField(builder, "hisVolledigImpl");
        final HistorieSet<?> historieSet = (HistorieSet<?>) ReflectionTestUtils.invokeGetterMethod(hisVolledigImpl, historieGetMethodeNaam);
        debugHistorieSet(historieSet);
    }

    protected void debugHistorieSet(final HistorieSet<?> historieSet) {
        LOGGER.info("Historie ({})", historieSet.getAantal());
        for (final Object historieRecord : historieSet) {
            LOGGER.info(" -  {}", objectToString(historieRecord));
        }
    }

    protected String objectToString(final Object historieRecord) {
        if (historieRecord == null) {
            return "null";
        }

        Class<?> clazz = historieRecord.getClass();
        final StringBuilder result = new StringBuilder();
        result.append(clazz.getSimpleName()).append('[');

        boolean first = true;
        do {
            for (final Field field : clazz.getDeclaredFields()) {
                if (field.getName().startsWith("ajc$tjp_") || field.getName().startsWith("JiBX_")) {
                    continue;
                }

                field.setAccessible(true);
                Object value;
                try {
                    value = field.get(historieRecord);

                    if (first) {
                        first = false;
                    } else {
                        result.append(", ");
                    }
                    result.append(field.getName()).append('=').append(value);

                } catch (final ReflectiveOperationException e) {
                    LOGGER.warn("Fout bij ophalen veld waarde", e);
                }
            }

        } while ((clazz = clazz.getSuperclass()) != null);
        result.append(']');

        return result.toString();
    }

    protected void debugResultaat(final List<Lo3CategorieWaarde> resultaat) {
        LOGGER.info("Categorieen ({})", resultaat.size());
        for (final Lo3CategorieWaarde categorie : resultaat) {
            LOGGER.info(" - {}", categorie);
        }
    }

}

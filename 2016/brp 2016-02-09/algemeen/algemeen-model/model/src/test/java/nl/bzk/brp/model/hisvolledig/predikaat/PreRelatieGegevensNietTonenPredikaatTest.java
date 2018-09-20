/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortElement;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.basis.GerelateerdIdentificeerbaar;
import nl.bzk.brp.model.basis.MaterieleHistorie;
import nl.bzk.brp.model.basis.MaterieleHistorieImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.FamilierechtelijkeBetrekkingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.GeregistreerdPartnerschapHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.HuwelijkGeregistreerdPartnerschapHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.KindHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.OuderHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PartnerHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.BetrokkenheidHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.RelatieHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.BetrokkenheidHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.KindHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.OuderHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PartnerHisVolledigView;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisOuderOuderschapModel;
import nl.bzk.brp.model.operationeel.kern.HisRelatieModel;
import nl.bzk.brp.model.operationeel.kern.OuderOuderschapGroepModel;
import nl.bzk.brp.model.operationeel.kern.RelatieStandaardGroepModel;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

/**
 */
public class PreRelatieGegevensNietTonenPredikaatTest {

    private static final Logger LOGGER       = LoggerFactory.getLogger();
    private static final String VOORKOMEN_NIET_TONEN = "Voorkomen %s niet tonen";
    private static final String VOORKOMEN_WEL_TONEN = "Voorkomen %s wel tonen";
    private static final String VOORKOMEN_IS_VERVALLEN_VOOR_AANVANG_OUDERSCHAP_NIET_TONEN = "Voorkomen is vervallen vóór aanvang ouderschap, niet tonen";
    private static final String YYYYMMDD = "yyyyMMdd";
    private static final String ID = "iD";
    private static final String ELEMENT = "Element {} ";
    private static final String PERSOON = "persoon";

    private final PreRelatieGegevensNietTonenPredikaat predikaat = new PreRelatieGegevensNietTonenPredikaat();

    @Test
    public void testPreRelatieNietWegfilterCondities() {

        for (final ElementEnum elementEnum : ElementEnum.values()) {
            if (elementEnum.getSoort() == SoortElement.GROEP) {
                final FormeelGerelateerdIdentificeerbaar voorkomen = Mockito.mock(FormeelGerelateerdIdentificeerbaar.class);
                Mockito.when(voorkomen.getGerelateerdeObjectType()).thenReturn(elementEnum);
                Mockito.when(voorkomen.getBetrokkenPersoonBetrokkenheidView())
                    .thenReturn(getOuderHisVolledigView(new DatumEvtDeelsOnbekendAttribuut(20100101)));

                // Voorkomens die niet vervallen en niet beeindigd zijn dienen niet weggefilterd te worden
                Mockito.when(voorkomen.getDatumEindeGeldigheid()).thenReturn(null);
                Mockito.when(voorkomen.getDatumTijdVerval()).thenReturn(null);
                Assert.assertTrue(predikaat.evaluate(voorkomen));

                // Voorkomens die beeindigd zijn ná aanvang ouderschap dienen niet weggefilterd te worden
                Mockito.when(voorkomen.getDatumEindeGeldigheid()).thenReturn(new DatumEvtDeelsOnbekendAttribuut(new DatumAttribuut(new Date())));
                Assert.assertTrue(predikaat.evaluate(voorkomen));

                // reset
                Mockito.when(voorkomen.getDatumEindeGeldigheid()).thenReturn(null);

                // Voorkomens die vervallen zijn ná aanvang ouderschap dienen niet weggefilterd te worden.
                Mockito.when(voorkomen.getDatumTijdVerval()).thenReturn(new DatumTijdAttribuut(new Date()));
                Assert.assertTrue(predikaat.evaluate(voorkomen));
            }
        }
    }

    @Test
    public void testPreRelatieOpOuderschapOuder() {


        for (final ElementEnum elementEnum : ElementEnum.values()) {
            if (elementEnum.getSoort() == SoortElement.GROEP) {
                final FormeelGerelateerdIdentificeerbaar voorkomen = Mockito.mock(FormeelGerelateerdIdentificeerbaar.class);
                Mockito.when(voorkomen.getGerelateerdeObjectType()).thenReturn(elementEnum);

                final Calendar instance = Calendar.getInstance();
                instance.set(Calendar.YEAR, instance.get(Calendar.YEAR) - 1);
                final int datumAanvang = Integer.parseInt(new SimpleDateFormat(YYYYMMDD).format(instance.getTime()));
                instance.set(Calendar.YEAR, instance.get(Calendar.YEAR) - 1);
                final Date jaarVoorDatumAanvang = instance.getTime();

                // Voorkomens van de gerelateerde ouder dienen weggefilterd te worden indien deze zijn beeindigd vóór aanvang ouderschap
                Mockito.when(voorkomen.getBetrokkenPersoonBetrokkenheidView()).thenReturn(getOuderHisVolledigView(new DatumEvtDeelsOnbekendAttribuut(datumAanvang)));
                Mockito.when(voorkomen.getDatumEindeGeldigheid()).thenReturn(new DatumEvtDeelsOnbekendAttribuut(new DatumAttribuut(jaarVoorDatumAanvang)));

                final boolean evaluatie = predikaat.evaluate(voorkomen);
                if (elementEnum.getObjectType() == ElementEnum.GERELATEERDEOUDER_PERSOON) {
                    // alle groepen onder gerelateerde ouder niet tonen
                    Assert.assertFalse(String.format(VOORKOMEN_NIET_TONEN, elementEnum), evaluatie);
                } else {
                    // alle overige groepen wel tonen
                    Assert.assertTrue(String.format(VOORKOMEN_WEL_TONEN, elementEnum), evaluatie);
                }

                // reset
                Mockito.when(voorkomen.getDatumEindeGeldigheid()).thenReturn(null);

                // Voorkomens van de gerelateerde ouder dienen weggefilterd te worden indien deze zijn vervallen vóór aanvang ouderschap
                Mockito.when(voorkomen.getBetrokkenPersoonBetrokkenheidView()).thenReturn(getOuderHisVolledigView(new DatumEvtDeelsOnbekendAttribuut(datumAanvang)));
                Mockito.when(voorkomen.getDatumTijdVerval()).thenReturn(new DatumTijdAttribuut(jaarVoorDatumAanvang));

                final boolean evaluatie2 = predikaat.evaluate(voorkomen);
                if (elementEnum.getObjectType() == ElementEnum.GERELATEERDEOUDER_PERSOON) {
                    Assert.assertFalse(VOORKOMEN_IS_VERVALLEN_VOOR_AANVANG_OUDERSCHAP_NIET_TONEN, evaluatie2);
                } else {
                    Assert.assertTrue(evaluatie2);
                }
            }
        }
    }


    @Test
    public void testPreRelatieOpOuderschapKind() {

        for (final ElementEnum elementEnum : ElementEnum.values()) {
            if (elementEnum.getSoort() == SoortElement.GROEP) {
                final FormeelGerelateerdIdentificeerbaar voorkomen = Mockito.mock(FormeelGerelateerdIdentificeerbaar.class);
                Mockito.when(voorkomen.getGerelateerdeObjectType()).thenReturn(elementEnum);

                final Calendar instance = Calendar.getInstance();
                instance.set(Calendar.YEAR, instance.get(Calendar.YEAR) - 1);
                final int datumAanvang = Integer.parseInt(new SimpleDateFormat(YYYYMMDD).format(instance.getTime()));
                instance.set(Calendar.YEAR, instance.get(Calendar.YEAR) - 1);
                final Date jaarVoorDatumAanvang = instance.getTime();

                // Voorkomens van de gerelateerde ouder dienen weggefilterd te worden indien deze zijn beeindigd vóór aanvang ouderschap
                Mockito.when(voorkomen.getBetrokkenPersoonBetrokkenheidView()).thenReturn(getKindHisVolledigView(
                    new DatumEvtDeelsOnbekendAttribuut(datumAanvang)));
                Mockito.when(voorkomen.getDatumEindeGeldigheid()).thenReturn(new DatumEvtDeelsOnbekendAttribuut(new DatumAttribuut(jaarVoorDatumAanvang)));

                final boolean evaluatie = predikaat.evaluate(voorkomen);
                if (elementEnum.getObjectType() == ElementEnum.GERELATEERDEKIND_PERSOON) {
                    // alle groepen onder gerelateerde ouder niet tonen
                    Assert.assertFalse(String.format(VOORKOMEN_NIET_TONEN, elementEnum), evaluatie);
                } else {
                    // alle overige groepen wel tonen
                    Assert.assertTrue(String.format(VOORKOMEN_WEL_TONEN, elementEnum), evaluatie);
                }

                // reset
                Mockito.when(voorkomen.getDatumEindeGeldigheid()).thenReturn(null);

                // Voorkomens van de gerelateerde ouder dienen weggefilterd te worden indien deze zijn vervallen vóór aanvang ouderschap
                Mockito.when(voorkomen.getBetrokkenPersoonBetrokkenheidView()).thenReturn(getKindHisVolledigView(
                    new DatumEvtDeelsOnbekendAttribuut(datumAanvang)));
                Mockito.when(voorkomen.getDatumTijdVerval()).thenReturn(new DatumTijdAttribuut(jaarVoorDatumAanvang));

                final boolean evaluatie2 = predikaat.evaluate(voorkomen);
                if (elementEnum.getObjectType() == ElementEnum.GERELATEERDEKIND_PERSOON) {
                    Assert.assertFalse(VOORKOMEN_IS_VERVALLEN_VOOR_AANVANG_OUDERSCHAP_NIET_TONEN, evaluatie2);
                } else {
                    Assert.assertTrue(evaluatie2);
                }
            }
        }
    }

    @Test
    public void testPreRelatieOpPartner() {

        for (final ElementEnum elementEnum : ElementEnum.values()) {
            if (elementEnum.getSoort() == SoortElement.GROEP) {

                final FormeelGerelateerdIdentificeerbaar voorkomen = Mockito.mock(FormeelGerelateerdIdentificeerbaar.class);
                Mockito.when(voorkomen.getGerelateerdeObjectType()).thenReturn(elementEnum);

                final Calendar instance = Calendar.getInstance();
                instance.set(Calendar.YEAR, instance.get(Calendar.YEAR) - 1);
                final int datumAanvang = Integer.parseInt(new SimpleDateFormat(YYYYMMDD).format(instance.getTime()));
                instance.set(Calendar.YEAR, instance.get(Calendar.YEAR) - 1);
                final Date jaarVoorDatumAanvang = instance.getTime();

                // Voorkomens van de gerelateerde ouder dienen weggefilterd te worden indien deze zijn beeindigd vóór aanvang ouderschap
                Mockito.when(voorkomen.getBetrokkenPersoonBetrokkenheidView()).thenReturn(getPartnerHisVolledigView(
                    new DatumEvtDeelsOnbekendAttribuut(datumAanvang)));
                Mockito.when(voorkomen.getDatumEindeGeldigheid()).thenReturn(new DatumEvtDeelsOnbekendAttribuut(new DatumAttribuut(jaarVoorDatumAanvang)));

                final boolean evaluatie = predikaat.evaluate(voorkomen);
                if (elementEnum.getObjectType() == ElementEnum.GERELATEERDEHUWELIJKSPARTNER_PERSOON
                    || elementEnum.getObjectType() == ElementEnum.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON)
                {
                    // alle groepen onder gerelateerde ouder niet tonen
                    Assert.assertFalse(String.format(VOORKOMEN_NIET_TONEN, elementEnum), evaluatie);
                } else {
                    // alle overige groepen wel tonen
                    Assert.assertTrue(String.format(VOORKOMEN_WEL_TONEN, elementEnum), evaluatie);
                }

                // reset
                Mockito.when(voorkomen.getDatumEindeGeldigheid()).thenReturn(null);

                // Voorkomens van de gerelateerde ouder dienen weggefilterd te worden indien deze zijn vervallen vóór aanvang ouderschap
                Mockito.when(voorkomen.getBetrokkenPersoonBetrokkenheidView()).thenReturn(getPartnerHisVolledigView(
                    new DatumEvtDeelsOnbekendAttribuut(datumAanvang)));
                Mockito.when(voorkomen.getDatumTijdVerval()).thenReturn(new DatumTijdAttribuut(jaarVoorDatumAanvang));

                final boolean evaluatie2 = predikaat.evaluate(voorkomen);
                if (elementEnum.getObjectType() == ElementEnum.GERELATEERDEHUWELIJKSPARTNER_PERSOON
                    || elementEnum.getObjectType() == ElementEnum.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON)
                {
                    Assert.assertFalse(VOORKOMEN_IS_VERVALLEN_VOOR_AANVANG_OUDERSCHAP_NIET_TONEN, evaluatie2);
                } else {
                    Assert.assertTrue(evaluatie2);
                }
            }
        }
    }

    private BetrokkenheidHisVolledigView getKindHisVolledigView(final DatumEvtDeelsOnbekendAttribuut datumAanvang) {
        final FamilierechtelijkeBetrekkingHisVolledigImpl relatie = new FamilierechtelijkeBetrekkingHisVolledigImpl();
        relatie.getRelatieHistorie().voegToe(maakRelatieModel(null, relatie));
        final KindHisVolledigImpl kindHisVolledig = new KindHisVolledigImpl(relatie, new
            PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.ONBEKEND)));

        final OuderHisVolledigImpl ouderHisVolledig = new OuderHisVolledigImpl(relatie, new
            PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.ONBEKEND)));
        ouderHisVolledig.getOuderOuderschapHistorie().voegToe(maakOuderschapModel(datumAanvang, ouderHisVolledig));
        ReflectionTestUtils.setField(kindHisVolledig, ID, new Random().nextInt(10000));
        final PersoonHisVolledigImpl kindPersoon = TestPersoonJohnnyJordaan.maak();
        ReflectionTestUtils.setField(kindPersoon, ID, new Random().nextInt(10000));
        ReflectionTestUtils.setField(kindHisVolledig, PERSOON, kindPersoon);
        relatie.getBetrokkenheden().add(kindHisVolledig);
        ReflectionTestUtils.setField(ouderHisVolledig, ID, new Random().nextInt(10000));
        ReflectionTestUtils.setField(ouderHisVolledig, PERSOON, TestPersoonJohnnyJordaan.maak());
        relatie.getBetrokkenheden().add(ouderHisVolledig);
        return new KindHisVolledigView(kindHisVolledig, null);
    }


    private PartnerHisVolledigView getPartnerHisVolledigView(final DatumEvtDeelsOnbekendAttribuut datumAanvang) {
        final HuwelijkGeregistreerdPartnerschapHisVolledigImpl relatie = new GeregistreerdPartnerschapHisVolledigImpl();
        relatie.getRelatieHistorie().voegToe(maakRelatieModel(datumAanvang, relatie));
        final PartnerHisVolledigImpl partnerHisVolledig = new PartnerHisVolledigImpl(relatie, new
            PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.ONBEKEND)));
        return new PartnerHisVolledigView(partnerHisVolledig, null);
    }


    private OuderHisVolledigView getOuderHisVolledigView(final DatumEvtDeelsOnbekendAttribuut datumAanvang) {
        final FamilierechtelijkeBetrekkingHisVolledigImpl relatie = new FamilierechtelijkeBetrekkingHisVolledigImpl();
        relatie.getRelatieHistorie().voegToe(maakRelatieModel(datumAanvang, relatie));
        final OuderHisVolledigImpl ouderHisVolledig = new OuderHisVolledigImpl(relatie, new
            PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.ONBEKEND)));
        ouderHisVolledig.getOuderOuderschapHistorie().voegToe(maakOuderschapModel(datumAanvang, ouderHisVolledig));
        return new OuderHisVolledigView(ouderHisVolledig, null);
    }

    private HisRelatieModel maakRelatieModel(final DatumEvtDeelsOnbekendAttribuut datumAanvang, final RelatieHisVolledig relatieHisVolledig) {
        final RelatieStandaardGroepModel standaardGroepModel = new RelatieStandaardGroepModel(datumAanvang, null, null, null, null, null, null, null,
                                                                                              null, null, null, null, null, null, null);
        return new HisRelatieModel(relatieHisVolledig, standaardGroepModel, new ActieModel(null, null, null, datumAanvang,
                                                                                           null, new DatumTijdAttribuut(new Date()), null));
    }

    private HisOuderOuderschapModel maakOuderschapModel(final DatumEvtDeelsOnbekendAttribuut datumAanvang, final BetrokkenheidHisVolledig betrokkenheidHisVolledig) {

        final ActieModel actieInhoud = new ActieModel(new SoortActieAttribuut(SoortActie.DUMMY), null, null, datumAanvang,
                                                      null, new DatumTijdAttribuut(new Date()), null);

        final OuderOuderschapGroepModel ouderOuderschapGroepModel = new OuderOuderschapGroepModel(new JaAttribuut(Ja.J), new JaNeeAttribuut(true));
        final MaterieleHistorieImpl historie = new MaterieleHistorieImpl(datumAanvang, null);
        return new HisOuderOuderschapModel(betrokkenheidHisVolledig, ouderOuderschapGroepModel, historie, actieInhoud);
    }

    interface FormeelGerelateerdIdentificeerbaar extends MaterieleHistorie, GerelateerdIdentificeerbaar {
    }

}

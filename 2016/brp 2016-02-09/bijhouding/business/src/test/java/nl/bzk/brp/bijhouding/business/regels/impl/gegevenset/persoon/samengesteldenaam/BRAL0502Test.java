/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.samengesteldenaam;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.basis.ModelRootObject;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.KindBericht;
import nl.bzk.brp.model.bericht.kern.OuderBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.FamilierechtelijkeBetrekkingView;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.util.RelatieTestUtil;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder.PersoonHisVolledigImplBuilderSamengesteldeNaam;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Unit test klasse voor de regel {@link BRAL0502}.
 */
public class BRAL0502Test {

    private static final boolean INDICATIE_NAMEN_REEKS_AANWEZIG = true;
    private static final boolean INDICATIE_NAMEN_REEKS_NIET_AANWEZIG = false;

    private static final boolean SAMENGESTELDE_NAAM_AANWEZIG = true;
    private static final boolean SAMENGESTELDE_NAAM_NIET_AANWEZIG = false;

    private static final Integer BSN_OUDER = 123456789;
    private static final Integer ID_OUDER = 123456789;
    private static final Integer BSN_KIND = 987654321;
    private static final Integer ID_KIND = 987654321;

    private final BRAL0502 bral0502 = new BRAL0502();

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRAL0502, bral0502.getRegel());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNietBestaandRootObject() {
        bral0502.voerRegelUit(Mockito.mock(ModelRootObject.class), null);
    }

    @Test
    public void testPersoonMetNamenReeksEnGeenVoornamen() {
        final PersoonView persoon = bouwPersoonView(INDICATIE_NAMEN_REEKS_AANWEZIG, SAMENGESTELDE_NAAM_AANWEZIG, null, BSN_OUDER, ID_OUDER);
        final List<BerichtEntiteit> berichtEntiteits =
                bral0502.voerRegelUit(persoon, bouwPersoonBericht(BSN_OUDER));

        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    @Test
    public void testPersoonMetNamenReeksEnGeenSamengesteldeNaam() {
        final PersoonView persoon = bouwPersoonView(INDICATIE_NAMEN_REEKS_AANWEZIG, SAMENGESTELDE_NAAM_NIET_AANWEZIG, null, BSN_OUDER, ID_OUDER);
        final List<BerichtEntiteit> berichtEntiteits =
                bral0502.voerRegelUit(persoon, bouwPersoonBericht(BSN_OUDER));

        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    @Test
    public void testPersoonZonderNamenReeksEnMetVoornamen() {
        final PersoonView persoon =
                bouwPersoonView(INDICATIE_NAMEN_REEKS_NIET_AANWEZIG, SAMENGESTELDE_NAAM_AANWEZIG, "Jan", BSN_OUDER, ID_OUDER);
        final List<BerichtEntiteit> berichtEntiteits =
                bral0502.voerRegelUit(persoon, bouwPersoonBericht(BSN_OUDER));

        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    @Test
    public void testPersoonMetNamenReeksEnMetVoornamen() {
        final PersoonView persoon = bouwPersoonView(INDICATIE_NAMEN_REEKS_AANWEZIG, SAMENGESTELDE_NAAM_AANWEZIG, "Jan", BSN_OUDER, ID_OUDER);
        final List<BerichtEntiteit> berichtEntiteits =
                bral0502.voerRegelUit(persoon, bouwPersoonBericht(BSN_OUDER));

        Assert.assertEquals(1, berichtEntiteits.size());
        Assert.assertEquals(BSN_OUDER.toString(), berichtEntiteits.get(0).getObjectSleutel());
    }

    @Test
    public void testRelatieMetNamenReeksEnGeenVoornamen() {
        final FamilierechtelijkeBetrekkingView familierechtelijkeBetrekkingView =
                bouwFamilierechtelijkeBetrekking(INDICATIE_NAMEN_REEKS_AANWEZIG, null, BSN_OUDER, ID_OUDER,
                                                 INDICATIE_NAMEN_REEKS_NIET_AANWEZIG, "Jantje", BSN_KIND, ID_KIND);
        final List<BerichtEntiteit> berichtEntiteits =
                bral0502.voerRegelUit(familierechtelijkeBetrekkingView,
                                      bouwFamilierechtelijkBetrekkingBericht(BSN_OUDER, ID_OUDER, BSN_KIND, ID_KIND));

        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    @Test
    public void testRelatieZonderNamenReeksEnMetVoornamen() {
        final FamilierechtelijkeBetrekkingView familierechtelijkeBetrekkingView =
                bouwFamilierechtelijkeBetrekking(INDICATIE_NAMEN_REEKS_NIET_AANWEZIG, "Jan", BSN_OUDER, ID_OUDER,
                                                 INDICATIE_NAMEN_REEKS_NIET_AANWEZIG, "Jantje", BSN_KIND, ID_KIND);
        final List<BerichtEntiteit> berichtEntiteits =
                bral0502.voerRegelUit(familierechtelijkeBetrekkingView,
                                      bouwFamilierechtelijkBetrekkingBericht(BSN_OUDER, ID_OUDER, BSN_KIND, ID_KIND));

        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    @Test
    public void testRelatieMetNamenReeksEnMetVoornamen() {
        final FamilierechtelijkeBetrekkingView familierechtelijkeBetrekkingView =
                bouwFamilierechtelijkeBetrekking(INDICATIE_NAMEN_REEKS_AANWEZIG, "Jan", BSN_OUDER, ID_OUDER,
                                                 INDICATIE_NAMEN_REEKS_NIET_AANWEZIG, "Jantje",
                                                 BSN_KIND, ID_KIND);
        final List<BerichtEntiteit> berichtEntiteits =
                bral0502.voerRegelUit(familierechtelijkeBetrekkingView,
                                      bouwFamilierechtelijkBetrekkingBericht(BSN_OUDER, ID_OUDER, BSN_KIND, ID_KIND));

        Assert.assertEquals(1, berichtEntiteits.size());
        Assert.assertEquals(BSN_OUDER.toString(), berichtEntiteits.get(0).getObjectSleutel());
    }

    @Test
    public void testRelatieWaarbijBeidePersonenMetNamenReeksEnMetVoornamen() {
        final FamilierechtelijkeBetrekkingView familierechtelijkeBetrekkingView =
                bouwFamilierechtelijkeBetrekking(INDICATIE_NAMEN_REEKS_AANWEZIG, "Jan", BSN_OUDER, ID_OUDER,
                                                 INDICATIE_NAMEN_REEKS_AANWEZIG, "Jantje", BSN_KIND, ID_KIND);
        final List<BerichtEntiteit> berichtEntiteits =
                bral0502.voerRegelUit(familierechtelijkeBetrekkingView,
                                      bouwFamilierechtelijkBetrekkingBericht(BSN_OUDER, ID_OUDER, BSN_KIND, ID_KIND));

        final List<String> technischeSleutels = Arrays.asList(berichtEntiteits.get(0).getObjectSleutel(), berichtEntiteits.get(1).getObjectSleutel());
        Assert.assertTrue(technischeSleutels.contains(BSN_OUDER.toString()));
        Assert.assertTrue(technischeSleutels.contains(BSN_KIND.toString()));
    }

    private PersoonBericht bouwPersoonBericht(final Integer technischeSleutel) {
        final PersoonBericht persoon = new PersoonBericht();
        persoon.setSoort(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        persoon.setObjectSleutel(technischeSleutel.toString());

        return persoon;
    }

    private FamilierechtelijkeBetrekkingBericht bouwFamilierechtelijkBetrekkingBericht(
            final Integer technischeSleutelOuder, final Integer databaseIdOuder, final Integer technischeSleutelKind,
            final Integer databaseIdKind)
    {
        final PersoonBericht persoonOuder = new PersoonBericht();
        persoonOuder.setSoort(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        persoonOuder.setObjectSleutel(technischeSleutelOuder.toString());
        persoonOuder.setObjectSleutelDatabaseID(databaseIdOuder);
        final BetrokkenheidBericht betrOuder = new OuderBericht();
        betrOuder.setPersoon(persoonOuder);

        final PersoonBericht persoonKind = new PersoonBericht();
        persoonKind.setSoort(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        persoonKind.setObjectSleutel(technischeSleutelKind.toString());
        persoonKind.setObjectSleutelDatabaseID(databaseIdKind);
        final BetrokkenheidBericht betrKind = new KindBericht();
        betrKind.setPersoon(persoonKind);

        final FamilierechtelijkeBetrekkingBericht relatie = new FamilierechtelijkeBetrekkingBericht();
        relatie.setBetrokkenheden(Arrays.asList(betrOuder, betrKind));
        betrOuder.setRelatie(relatie);
        betrKind.setRelatie(relatie);

        return relatie;
    }

    private FamilierechtelijkeBetrekkingView bouwFamilierechtelijkeBetrekking(
            final boolean indNamenReeksOuder, final String voornaamOuder, final Integer burgerservicenummerOuder, final Integer databaseIdOuder,
            final boolean indNamenReeksKind, final String voornaamKind, final Integer burgerservicenummerKind, final Integer databaseIdKind)
    {
        final PersoonHisVolledigImpl ouder =
                bouwPersoonHisVolledig(indNamenReeksOuder, SAMENGESTELDE_NAAM_AANWEZIG, voornaamOuder, burgerservicenummerOuder, databaseIdOuder);
        final PersoonHisVolledigImpl kind = bouwPersoonHisVolledig(indNamenReeksKind, SAMENGESTELDE_NAAM_AANWEZIG, voornaamKind, burgerservicenummerKind, databaseIdKind);

        final ActieModel actie =
            new ActieModel(new SoortActieAttribuut(SoortActie.DUMMY), null, null,
                           new DatumEvtDeelsOnbekendAttribuut(
                               20130101), null, new DatumTijdAttribuut(new Date()), null);

        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(ouder, null, kind, 20120101, actie);

        return new FamilierechtelijkeBetrekkingView(
                RelatieTestUtil.haalFamilieRechtelijkeBetrekkingUitPersoonBetrokkenhedenWaarPersoonKindInIs(kind),
                DatumTijdAttribuut.nu(),
                DatumAttribuut.vandaag()
        );
    }

    private PersoonView bouwPersoonView(final boolean indNamenReeks, final boolean heeftSamengesteldeNaam,
            final String voornaam, final Integer burgerservicenummer, final Integer databaseId)
    {
        return new PersoonView(bouwPersoonHisVolledig(indNamenReeks, heeftSamengesteldeNaam,
                voornaam, burgerservicenummer, databaseId), DatumTijdAttribuut.nu(), DatumAttribuut.vandaag());
    }

    private PersoonHisVolledigImpl bouwPersoonHisVolledig(final boolean indNamenReeks,
            final boolean heeftSamengesteldeNaam, final String voornaam,
            final Integer burgerservicenummer, final Integer databaseId)
    {
        final PersoonHisVolledigImplBuilder persoonBuilder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);
        if (heeftSamengesteldeNaam) {
            final PersoonHisVolledigImplBuilderSamengesteldeNaam samengesteldeNaamBuilder = persoonBuilder
                    .nieuwSamengesteldeNaamRecord(20120101, null, 20120101);
            if (voornaam != null) {
                samengesteldeNaamBuilder.voornamen(voornaam);
            }
            samengesteldeNaamBuilder
                    .indicatieNamenreeks(indNamenReeks)
                    .eindeRecord();
        }
        final PersoonHisVolledigImpl persoon = persoonBuilder
                .nieuwIdentificatienummersRecord(20120101, null, 20120101)
                .burgerservicenummer(burgerservicenummer)
                .eindeRecord()
                .build();
        ReflectionTestUtils.setField(persoon, "iD", databaseId);

        return persoon;
    }
}

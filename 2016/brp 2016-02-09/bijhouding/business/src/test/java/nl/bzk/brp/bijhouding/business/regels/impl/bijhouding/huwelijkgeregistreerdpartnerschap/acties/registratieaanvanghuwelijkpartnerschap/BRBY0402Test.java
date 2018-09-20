/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.huwelijkgeregistreerdpartnerschap.acties
        .registratieaanvanghuwelijkpartnerschap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NationaliteitcodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Nationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieAanvangHuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonNationaliteitHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;

import nl.bzk.brp.util.RelatieBuilder;
import nl.bzk.brp.util.StamgegevenBuilder;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonNationaliteitHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Test;

public class BRBY0402Test {

    private static final String IDENTIFICERENDE_SLEUTEL_MAN = "id.man";

    private static final String IDENTIFICERENDE_SLEUTEL_VROUW = "id.vrouw";
    private static final DatumTijdAttribuut TIJDSTIP_REGISTRATIE = DatumTijdAttribuut.bouwDatumTijd(2013, 8, 15, 0, 0, 0);

    private static final int HUWELIJKS_DATUM = 20010404;
    private static final int GEBOORTE_DATUM_VOOR_15_JAAR = 19870404;

    private static final int GEBOORTE_DATUM_15_JAAR = 19860404;
    private static final int GEBOORTE_DATUM_NA_15_JAAR = 19800404;
    private final BRBY0402 brby0402 = new BRBY0402();

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0402, brby0402.getRegel());
    }

    @Test
    public void testGetMinimaleLeeftijd() {
        Assert.assertEquals(15, brby0402.getMinimaleLeeftijd().intValue());
    }

    @Test
    public void testOnder15JaarEn15Jaar() {
        final PersoonView man = maakPersoonView(GEBOORTE_DATUM_VOOR_15_JAAR);
        final PersoonView vrouw = maakPersoonView(GEBOORTE_DATUM_15_JAAR);

        final List<BerichtEntiteit> resultaat =
                brby0402.voerRegelUit(null, maakHuwelijkBericht(), maakActie(), maakBestaandeBetrokkenen(man, vrouw));

        Assert.assertEquals(1, resultaat.size());
        Assert.assertTrue(resultaat.get(0) instanceof PersoonBericht);
    }

    @Test
    public void testBeidePartnersOuderDan15Jaar() {
        final PersoonView man = maakPersoonView(GEBOORTE_DATUM_NA_15_JAAR);
        final PersoonView vrouw = maakPersoonView(GEBOORTE_DATUM_NA_15_JAAR);

        final List<BerichtEntiteit> resultaat =
                brby0402.voerRegelUit(null, maakHuwelijkBericht(), maakActie(), maakBestaandeBetrokkenen(man, vrouw));

        Assert.assertEquals(0, resultaat.size());
    }

    @Test
    public void testHuwelijkZonderPartners() {
        // Geen man, geen vrouw, test voor coverage tbv conditionals.
        final List<BerichtEntiteit> resultaat =
                brby0402.voerRegelUit(null, maakHuwelijkBerichtZonderPartners(), maakActie(), null);

        Assert.assertEquals(0, resultaat.size());
    }

    /**
     * Maakt een huwelijk bericht met een man, vrouw en standaard id's.
     *
     * @return huwelijk geregistreerd partnerschap bericht
     */
    private HuwelijkGeregistreerdPartnerschapBericht maakHuwelijkBericht() {
        return maakHuwelijkBericht(SoortPersoon.INGESCHREVENE);
    }

    /**
     * Maakt een huwelijk bericht met een man, vrouw en standaard id's.
     *
     * @param soortMan SoortPersoon van de man
     * @return huwelijk geregistreerd partnerschap bericht
     */
    private HuwelijkGeregistreerdPartnerschapBericht maakHuwelijkBericht(final SoortPersoon soortMan) {
        final PersoonBericht manBericht = new PersoonBericht();
        manBericht.setSoort(new SoortPersoonAttribuut(soortMan));
        manBericht.setIdentificerendeSleutel(IDENTIFICERENDE_SLEUTEL_MAN);
        final PersoonBericht vrouwBericht = new PersoonBericht();
        vrouwBericht.setSoort(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        vrouwBericht.setIdentificerendeSleutel(IDENTIFICERENDE_SLEUTEL_VROUW);
        return new RelatieBuilder<HuwelijkGeregistreerdPartnerschapBericht>().bouwHuwelijkRelatie()
                .setLandGebiedAanvang(StatischeObjecttypeBuilder.LAND_NEDERLAND.getWaarde())
                .voegPartnerToe(manBericht)
                .voegPartnerToe(vrouwBericht)
                .setDatumAanvang(HUWELIJKS_DATUM).getRelatie();
    }

    /**
     * Maakt een huwelijk bericht zonder partners. Dit is ten behoeve van de test coverage (conditionals).
     *
     * @return huwelijk geregistreerd partnerschap bericht
     */
    private HuwelijkGeregistreerdPartnerschapBericht maakHuwelijkBerichtZonderPartners() {
        return new RelatieBuilder<HuwelijkGeregistreerdPartnerschapBericht>().bouwHuwelijkRelatie()
                .setLandGebiedAanvang(StatischeObjecttypeBuilder.LAND_NEDERLAND.getWaarde())
                .setDatumAanvang(HUWELIJKS_DATUM).getRelatie();
    }

    /**
     * Maakt een standaard persoon his volledig view van een persoon.
     *
     * @param geboorteDatum the geboorte datum
     * @return the persoon his volledig view
     */
    private PersoonView maakPersoonView(final int geboorteDatum) {
        final PersoonHisVolledigImpl persoon = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwGeboorteRecord(geboorteDatum)
                .datumGeboorte(geboorteDatum)
                .eindeRecord()
                .build();

        final PersoonNationaliteitHisVolledigImpl persoonNationaliteit = new PersoonNationaliteitHisVolledigImplBuilder(
                StamgegevenBuilder
                        .bouwDynamischStamgegeven(Nationaliteit.class, NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE))
                .nieuwStandaardRecord(geboorteDatum, null, geboorteDatum)
                .eindeRecord()
                .build();
        persoon.getNationaliteiten().add(persoonNationaliteit);

        return new PersoonView(persoon);
    }

    /**
     * Maakt een map met bestaande betrokkenen.
     *
     * @param man man in huwelijk
     * @param vrouw vrouw in huwelijk
     * @return map met betrokkenen
     */
    private Map<String, PersoonView> maakBestaandeBetrokkenen(
            final PersoonView man, final PersoonView vrouw)
    {
        final Map<String, PersoonView> bestaandeBetrokkenen = new HashMap<>();
        bestaandeBetrokkenen.put(IDENTIFICERENDE_SLEUTEL_MAN, man);
        bestaandeBetrokkenen.put(IDENTIFICERENDE_SLEUTEL_VROUW, vrouw);
        return bestaandeBetrokkenen;
    }

    /**
     * Maakt een standaard actie voor ActieRegistratieAanvangHuwelijkPartnerschap.
     *
     * @return actie bericht
     */
    private ActieBericht maakActie() {
        final ActieBericht actie = new ActieRegistratieAanvangHuwelijkGeregistreerdPartnerschapBericht();
        actie.setTijdstipRegistratie(TIJDSTIP_REGISTRATIE);

        return actie;
    }
}

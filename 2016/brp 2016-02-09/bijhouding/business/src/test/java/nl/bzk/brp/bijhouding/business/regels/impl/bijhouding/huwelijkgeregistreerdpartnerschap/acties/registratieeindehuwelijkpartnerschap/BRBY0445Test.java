/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.huwelijkgeregistreerdpartnerschap.acties.registratieeindehuwelijkpartnerschap;

import static nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling.BEEINDIGING_GEREGISTREERD_PARTNERSCHAP_IN_BUITENLAND;
import static nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling.BEEINDIGING_GEREGISTREERD_PARTNERSCHAP_IN_NEDERLAND;
import static nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling.CORRECTIE_HUWELIJK;
import static nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling.NIETIGVERKLARING_GEREGISTREERD_PARTNERSCHAP;
import static nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling.NIETIGVERKLARING_HUWELIJK;
import static nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling.OMZETTING_GEREGISTREERD_PARTNERSCHAP_IN_HUWELIJK;
import static nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling.ONTBINDING_HUWELIJK_IN_BUITENLAND;
import static nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling.ONTBINDING_HUWELIJK_IN_NEDERLAND;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenEindeRelatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestRedenEindeRelatieBuilder;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieConversieGBABericht;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.bericht.kern.HandelingBeeindigingGeregistreerdPartnerschapInBuitenlandBericht;
import nl.bzk.brp.model.bericht.kern.HandelingBeeindigingGeregistreerdPartnerschapInNederlandBericht;
import nl.bzk.brp.model.bericht.kern.HandelingCorrectieHuwelijkBericht;
import nl.bzk.brp.model.bericht.kern.HandelingNietigverklaringGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bericht.kern.HandelingNietigverklaringHuwelijkBericht;
import nl.bzk.brp.model.bericht.kern.HandelingOmzettingGeregistreerdPartnerschapInHuwelijkBericht;
import nl.bzk.brp.model.bericht.kern.HandelingOntbindingHuwelijkInBuitenlandBericht;
import nl.bzk.brp.model.bericht.kern.HandelingOntbindingHuwelijkInNederlandBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bericht.kern.RelatieStandaardGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.HuwelijkHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.HuwelijkGeregistreerdPartnerschapView;
import nl.bzk.brp.model.hisvolledig.momentview.kern.HuwelijkView;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.util.hisvolledig.kern.HuwelijkHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

/**
 *
 */
@RunWith(Parameterized.class)
public class BRBY0445Test {

    private static final boolean REGEL_OVERTREDEN = false;

    private static final boolean REGEL_NIET_OVERTREDEN = true;

    @Parameter
    public SoortAdministratieveHandeling soortAdministratieveHandeling;

    @Parameter(value = 1)
    public String redenBeeindigingRelatie;

    @Parameter(value = 2)
    public boolean expectedResult;

    private final BRBY0445 brby0445 = new BRBY0445();

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(
            new Object[][] {

                { ONTBINDING_HUWELIJK_IN_NEDERLAND, "A", REGEL_NIET_OVERTREDEN },
                { ONTBINDING_HUWELIJK_IN_NEDERLAND, "O", REGEL_NIET_OVERTREDEN },
                { ONTBINDING_HUWELIJK_IN_NEDERLAND, "R", REGEL_NIET_OVERTREDEN },
                { ONTBINDING_HUWELIJK_IN_NEDERLAND, "S", REGEL_NIET_OVERTREDEN },
                { ONTBINDING_HUWELIJK_IN_NEDERLAND, "?", REGEL_OVERTREDEN },
                { ONTBINDING_HUWELIJK_IN_NEDERLAND, "V", REGEL_OVERTREDEN },
                { ONTBINDING_HUWELIJK_IN_NEDERLAND, "Z", REGEL_OVERTREDEN },
                { ONTBINDING_HUWELIJK_IN_NEDERLAND, "N", REGEL_OVERTREDEN },

                { BEEINDIGING_GEREGISTREERD_PARTNERSCHAP_IN_NEDERLAND, "A", REGEL_NIET_OVERTREDEN },
                { BEEINDIGING_GEREGISTREERD_PARTNERSCHAP_IN_NEDERLAND, "O", REGEL_NIET_OVERTREDEN },
                { BEEINDIGING_GEREGISTREERD_PARTNERSCHAP_IN_NEDERLAND, "R", REGEL_NIET_OVERTREDEN },
                { BEEINDIGING_GEREGISTREERD_PARTNERSCHAP_IN_NEDERLAND, "S", REGEL_NIET_OVERTREDEN },
                { BEEINDIGING_GEREGISTREERD_PARTNERSCHAP_IN_NEDERLAND, "?", REGEL_OVERTREDEN },
                { BEEINDIGING_GEREGISTREERD_PARTNERSCHAP_IN_NEDERLAND, "V", REGEL_OVERTREDEN },
                { BEEINDIGING_GEREGISTREERD_PARTNERSCHAP_IN_NEDERLAND, "Z", REGEL_OVERTREDEN },
                { BEEINDIGING_GEREGISTREERD_PARTNERSCHAP_IN_NEDERLAND, "N", REGEL_OVERTREDEN },

                { ONTBINDING_HUWELIJK_IN_BUITENLAND, "A", REGEL_NIET_OVERTREDEN },
                { ONTBINDING_HUWELIJK_IN_BUITENLAND, "O", REGEL_NIET_OVERTREDEN },
                { ONTBINDING_HUWELIJK_IN_BUITENLAND, "R", REGEL_NIET_OVERTREDEN },
                { ONTBINDING_HUWELIJK_IN_BUITENLAND, "S", REGEL_NIET_OVERTREDEN },
                { ONTBINDING_HUWELIJK_IN_BUITENLAND, "?", REGEL_NIET_OVERTREDEN },
                { ONTBINDING_HUWELIJK_IN_BUITENLAND, "V", REGEL_NIET_OVERTREDEN },
                { ONTBINDING_HUWELIJK_IN_BUITENLAND, "Z", REGEL_OVERTREDEN },
                { ONTBINDING_HUWELIJK_IN_BUITENLAND, "N", REGEL_OVERTREDEN },

                { BEEINDIGING_GEREGISTREERD_PARTNERSCHAP_IN_BUITENLAND, "A", REGEL_NIET_OVERTREDEN },
                { BEEINDIGING_GEREGISTREERD_PARTNERSCHAP_IN_BUITENLAND, "O", REGEL_NIET_OVERTREDEN },
                { BEEINDIGING_GEREGISTREERD_PARTNERSCHAP_IN_BUITENLAND, "R", REGEL_NIET_OVERTREDEN },
                { BEEINDIGING_GEREGISTREERD_PARTNERSCHAP_IN_BUITENLAND, "S", REGEL_NIET_OVERTREDEN },
                { BEEINDIGING_GEREGISTREERD_PARTNERSCHAP_IN_BUITENLAND, "?", REGEL_NIET_OVERTREDEN },
                { BEEINDIGING_GEREGISTREERD_PARTNERSCHAP_IN_BUITENLAND, "V", REGEL_NIET_OVERTREDEN },
                { BEEINDIGING_GEREGISTREERD_PARTNERSCHAP_IN_BUITENLAND, "Z", REGEL_OVERTREDEN },
                { BEEINDIGING_GEREGISTREERD_PARTNERSCHAP_IN_BUITENLAND, "N", REGEL_OVERTREDEN },

                { OMZETTING_GEREGISTREERD_PARTNERSCHAP_IN_HUWELIJK, "A", REGEL_OVERTREDEN },
                { OMZETTING_GEREGISTREERD_PARTNERSCHAP_IN_HUWELIJK, "O", REGEL_OVERTREDEN },
                { OMZETTING_GEREGISTREERD_PARTNERSCHAP_IN_HUWELIJK, "R", REGEL_OVERTREDEN },
                { OMZETTING_GEREGISTREERD_PARTNERSCHAP_IN_HUWELIJK, "S", REGEL_OVERTREDEN },
                { OMZETTING_GEREGISTREERD_PARTNERSCHAP_IN_HUWELIJK, "?", REGEL_OVERTREDEN },
                { OMZETTING_GEREGISTREERD_PARTNERSCHAP_IN_HUWELIJK, "V", REGEL_OVERTREDEN },
                { OMZETTING_GEREGISTREERD_PARTNERSCHAP_IN_HUWELIJK, "Z", REGEL_NIET_OVERTREDEN },
                { OMZETTING_GEREGISTREERD_PARTNERSCHAP_IN_HUWELIJK, "N", REGEL_OVERTREDEN },

                { NIETIGVERKLARING_HUWELIJK, "A", REGEL_OVERTREDEN },
                { NIETIGVERKLARING_HUWELIJK, "O", REGEL_OVERTREDEN },
                { NIETIGVERKLARING_HUWELIJK, "R", REGEL_OVERTREDEN },
                { NIETIGVERKLARING_HUWELIJK, "S", REGEL_OVERTREDEN },
                { NIETIGVERKLARING_HUWELIJK, "?", REGEL_OVERTREDEN },
                { NIETIGVERKLARING_HUWELIJK, "V", REGEL_OVERTREDEN },
                { NIETIGVERKLARING_HUWELIJK, "Z", REGEL_OVERTREDEN },
                { NIETIGVERKLARING_HUWELIJK, "N", REGEL_NIET_OVERTREDEN },

                { NIETIGVERKLARING_GEREGISTREERD_PARTNERSCHAP, "A", REGEL_OVERTREDEN },
                { NIETIGVERKLARING_GEREGISTREERD_PARTNERSCHAP, "O", REGEL_OVERTREDEN },
                { NIETIGVERKLARING_GEREGISTREERD_PARTNERSCHAP, "R", REGEL_OVERTREDEN },
                { NIETIGVERKLARING_GEREGISTREERD_PARTNERSCHAP, "S", REGEL_OVERTREDEN },
                { NIETIGVERKLARING_GEREGISTREERD_PARTNERSCHAP, "?", REGEL_OVERTREDEN },
                { NIETIGVERKLARING_GEREGISTREERD_PARTNERSCHAP, "V", REGEL_OVERTREDEN },
                { NIETIGVERKLARING_GEREGISTREERD_PARTNERSCHAP, "Z", REGEL_OVERTREDEN },
                { NIETIGVERKLARING_GEREGISTREERD_PARTNERSCHAP, "N", REGEL_NIET_OVERTREDEN },

                { CORRECTIE_HUWELIJK, "?", REGEL_NIET_OVERTREDEN }

            });
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0445, brby0445.getRegel());
    }

    @Test
    public void testRegelBRBY0445() {
        Actie actie = maakActie(soortAdministratieveHandeling);
        HuwelijkGeregistreerdPartnerschapBericht nieuweSituatie = maakNieuweSituatie(redenBeeindigingRelatie);

        final List<BerichtEntiteit> berichtEntiteits =
                brby0445.voerRegelUit(maakHuidigeSituatie(20130101), nieuweSituatie, actie, null);
        Assert.assertEquals(
                "Onverwachte uitkomst bij soort AH: '" + soortAdministratieveHandeling + "' en reden beeindiging: '" + redenBeeindigingRelatie + "'.",
                expectedResult, berichtEntiteits.isEmpty());
    }

    private HuwelijkGeregistreerdPartnerschapView maakHuidigeSituatie(final Integer datumEinde) {
        final HuwelijkHisVolledigImpl huwelijk =
                new HuwelijkHisVolledigImplBuilder().nieuwStandaardRecord(20120101).datumEinde(datumEinde).eindeRecord()
                                                    .build();
        return new HuwelijkView(huwelijk, DatumTijdAttribuut.nu());
    }

    private Actie maakActie(final SoortAdministratieveHandeling soortAdmHandeling) {
        ActieBericht actie = new ActieConversieGBABericht();
        AdministratieveHandelingBericht administratieveHandeling = null;

        switch (soortAdmHandeling) {
            case ONTBINDING_HUWELIJK_IN_NEDERLAND:
                administratieveHandeling = new HandelingOntbindingHuwelijkInNederlandBericht();
                break;
            case BEEINDIGING_GEREGISTREERD_PARTNERSCHAP_IN_NEDERLAND:
                administratieveHandeling = new HandelingBeeindigingGeregistreerdPartnerschapInNederlandBericht();
                break;
            case ONTBINDING_HUWELIJK_IN_BUITENLAND:
                administratieveHandeling = new HandelingOntbindingHuwelijkInBuitenlandBericht();
                break;
            case BEEINDIGING_GEREGISTREERD_PARTNERSCHAP_IN_BUITENLAND:
                administratieveHandeling = new HandelingBeeindigingGeregistreerdPartnerschapInBuitenlandBericht();
                break;
            case NIETIGVERKLARING_HUWELIJK:
                administratieveHandeling = new HandelingNietigverklaringHuwelijkBericht();
                break;
            case NIETIGVERKLARING_GEREGISTREERD_PARTNERSCHAP:
                administratieveHandeling = new HandelingNietigverklaringGeregistreerdPartnerschapBericht();
                break;
            case OMZETTING_GEREGISTREERD_PARTNERSCHAP_IN_HUWELIJK:
                administratieveHandeling = new HandelingOmzettingGeregistreerdPartnerschapInHuwelijkBericht();
                break;
            default:
                administratieveHandeling = new HandelingCorrectieHuwelijkBericht();
        }

        actie.setAdministratieveHandeling(administratieveHandeling);
        return actie;
    }

    private HuwelijkGeregistreerdPartnerschapBericht maakNieuweSituatie(final String redenEindeCode) {
        HuwelijkGeregistreerdPartnerschapBericht nieuweSituatie = new HuwelijkBericht();
        RelatieStandaardGroepBericht standaard =
                new RelatieStandaardGroepBericht();
        standaard.setRedenEinde(
                new RedenEindeRelatieAttribuut(
                    TestRedenEindeRelatieBuilder.maker().metCode(redenEindeCode).maak()));
        nieuweSituatie.setStandaard(standaard);

        return nieuweSituatie;
    }

}

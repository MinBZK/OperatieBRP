/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.huwelijkgeregistreerdpartnerschap.acties
    .registratieeindehuwelijkpartnerschap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.inject.Named;
import nl.bzk.brp.business.regels.VoorActieRegelMetMomentopname;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenEindeRelatieCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.HuwelijkGeregistreerdPartnerschapView;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;


/**
 * BRBY0445: Reden beëindiging moet geldig zijn in context.
 *
 * De RedenEinde in groep StandaardVerbintenis die door de Actie RegistratieEindeHuwelijkPartnerschap wordt
 * geregistreerd, moet afhankelijk van de Handeling waartoe de Actie behoort, verwijzen naar één van de volgende
 * mogelijkheden in RedenBeëindigingRelatie:
 * - OntbindingHuwelijkInNederland / OntbindingPartnerschapInNederland: ‘A’, ‘O’, ‘R’, ‘S’
 * - OntbindingHuwelijkInBuitenland / OntbindingPartnerschapInBuitenland: ‘A’, ‘O’, ‘R’, ‘S’, '?', 'V'
 * - OmzettingPartnerschapInHuwelijk: 'Z'
 * - NietigverklaringHuwelijk / NietigverklaringPartnerschap: 'N'
 *
 */
@Named("BRBY0445")
public class BRBY0445 implements
        VoorActieRegelMetMomentopname<HuwelijkGeregistreerdPartnerschapView, HuwelijkGeregistreerdPartnerschapBericht>
{

    private static final List<String> TOEGESTANE_REDEN_EINDE_RELATIE_NEDERLAND = Arrays.asList(
        RedenEindeRelatieCodeAttribuut.REDEN_EINDE_RELATIE_ANDERE_VERBINTENIS_CODE_STRING,
        RedenEindeRelatieCodeAttribuut.REDEN_EINDE_RELATIE_OVERLIJDEN_CODE_STRING,
        RedenEindeRelatieCodeAttribuut.REDEN_EINDE_RELATIE_RECHTSVERMOEDEN_OVERLIJDEN_CODE_STRING,
        RedenEindeRelatieCodeAttribuut.REDEN_EINDE_RELATIE_ECHTSCHEIDING_CODE_STRING
    );

    private static final List<String> TOEGESTANE_REDEN_EINDE_RELATIE_BUITENLAND = Arrays.asList(
        RedenEindeRelatieCodeAttribuut.REDEN_EINDE_RELATIE_ANDERE_VERBINTENIS_CODE_STRING,
        RedenEindeRelatieCodeAttribuut.REDEN_EINDE_RELATIE_OVERLIJDEN_CODE_STRING,
        RedenEindeRelatieCodeAttribuut.REDEN_EINDE_RELATIE_RECHTSVERMOEDEN_OVERLIJDEN_CODE_STRING,
        RedenEindeRelatieCodeAttribuut.REDEN_EINDE_RELATIE_ECHTSCHEIDING_CODE_STRING,
        RedenEindeRelatieCodeAttribuut.REDEN_EINDE_RELATIE_ONBEKEND_CODE_STRING,
        RedenEindeRelatieCodeAttribuut.REDEN_EINDE_RELATIE_VREEMD_RECHT_CODE_STRING
    );

    private static final List<String> TOEGESTANE_REDEN_EINDE_RELATIE_OMZETTING = Arrays.asList(
        RedenEindeRelatieCodeAttribuut.REDEN_EINDE_RELATIE_OMZETTING_SOORT_VERBINTENIS_CODE_STRING
    );

    private static final List<String> TOEGESTANE_REDEN_EINDE_RELATIE_NIETIGVERKLARING = Arrays.asList(
        RedenEindeRelatieCodeAttribuut.REDEN_EINDE_RELATIE_NIETIGVERKLARING_CODE_STRING
    );

    @Override
    public List<BerichtEntiteit> voerRegelUit(final HuwelijkGeregistreerdPartnerschapView huidigeSituatie,
        final HuwelijkGeregistreerdPartnerschapBericht nieuweSituatie, final Actie actie,
        final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();

        final SoortAdministratieveHandeling soortAdmHand = actie.getAdministratieveHandeling().getSoort().getWaarde();
        final String redenEindeCode = nieuweSituatie.getStandaard().getRedenEinde().getWaarde().getCode().getWaarde();

        final List<String> toegestaneRedenenEindeRelatie = zoekToegestaneRedenenEindeRelatie(soortAdmHand);

        if (toegestaneRedenenEindeRelatie != null && !toegestaneRedenenEindeRelatie.contains(redenEindeCode)) {
            objectenDieDeRegelOvertreden.add((ActieBericht) actie);
        }

        return objectenDieDeRegelOvertreden;
    }

    /**
     * Bepaalt aan de hand van de soort administratieve handeling welke redenen voor einde relatie zijn toegestaan.
     * @param soortAdmHand
     * @return een lijst met toegestane codes einde relatie, of <code>null</code> als alles is toegestaan
     */
    private List<String> zoekToegestaneRedenenEindeRelatie(final SoortAdministratieveHandeling soortAdmHand) {
        final List<String> result;
        switch (soortAdmHand) {
            case ONTBINDING_HUWELIJK_IN_NEDERLAND:
            case BEEINDIGING_GEREGISTREERD_PARTNERSCHAP_IN_NEDERLAND:
                result = TOEGESTANE_REDEN_EINDE_RELATIE_NEDERLAND;
                break;
            case ONTBINDING_HUWELIJK_IN_BUITENLAND:
            case BEEINDIGING_GEREGISTREERD_PARTNERSCHAP_IN_BUITENLAND:
                result = TOEGESTANE_REDEN_EINDE_RELATIE_BUITENLAND;
                break;
            case OMZETTING_GEREGISTREERD_PARTNERSCHAP_IN_HUWELIJK:
                result = TOEGESTANE_REDEN_EINDE_RELATIE_OMZETTING;
                break;
            case NIETIGVERKLARING_HUWELIJK:
            case NIETIGVERKLARING_GEREGISTREERD_PARTNERSCHAP:
                result = TOEGESTANE_REDEN_EINDE_RELATIE_NIETIGVERKLARING;
                break;
            default:
                result = null;
        }
        return result;
    }

    @Override
    public final Regel getRegel() {
        return Regel.BRBY0445;
    }
}

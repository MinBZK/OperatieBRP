/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.definitieregels;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.RelatieBericht;
import nl.bzk.brp.model.bijhouding.BijhoudingsBericht;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.FamilierechtelijkeBetrekking;
import nl.bzk.brp.model.logisch.kern.HuwelijkGeregistreerdPartnerschap;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.logisch.kern.Relatie;

/**
 * De Persoon wiens gegevens worden gewijzigd in de Hoofdactie wordt de Hoofdpersoon genoemd.
 * Opmerkingen:
 *
 * 1. In gevallen dat de Hoofdactie slechts wijzigingen op gegevens van 1 persoon bevat, is dat de Hoofdpersoon.
 * 2. In gevallen dat de Hoofdactie wijzigingen op gegevens van meerdere persoon bevat, gaat het om de volgende
 * situaties met de volgende hoofdpersonen:
 * a. een huwelijk: als beide partners Ingeschrevene zijn, zijn beide partners Hoofdpersoon. Als een van beide partners
 * NietIngeschrevene is, is de andere partner (de Ingeschrevene) de hoofdpersoon.
 * b. een familierechtelijke betrekking: hier is de Persoon met de rol Kind altijd de hoofdpersoon, tenzij er sprake is
 * van een bijschrijving van een NietIngeschreven Kind bij een Ingeschreven Ouder, dan is de Persoon met de rol Ouder
 * de hoofdpersoon.
 * 3. Een NietIngeschreven Persoon kan dus nooit een Hoofdpersoon zijn!
 * 4. Een persoon waarvan de gewijzigde gegevens slechts voorkomen als gegeven van een gerelateerde persoon, kan nooit
 * een hoofdpersoon zijn.
 *
 * @brp.bedrijfsregel BRBY0009
 */
public class BRBY0009 {

    /**
     * Bepaalt de hoofdpersonen binnen een administratieve handeling in een bericht en zoekt de daarbij behorende
     * personen op in de context.
     *
     * @param bericht het bericht met de administratieve handeling met acties
     * @return lijst van hoofdpersonen voor de administratieve handeling.
     */
    public final List<PersoonBericht> bepaalHoofdPersonen(final BijhoudingsBericht bericht) {
        final AdministratieveHandelingBericht administratieveHandelingBericht = bericht.getAdministratieveHandeling();
        final List<PersoonBericht> hoofdPersonenInBericht = new ArrayList<>();
        final Actie hoofdActie = administratieveHandelingBericht.getHoofdActie();
        if (hoofdActie == null) {
            throw new IllegalArgumentException("BRBY0009: Administratieve handeling kent geen hoofdactie,"
                                                       + " waardoor de hoofdpersonen niet bepaald kunnen worden.");
        } else {
            final RootObject rootObject = hoofdActie.getRootObject();
            if (rootObject instanceof Persoon) {
                hoofdPersonenInBericht.add((PersoonBericht) rootObject);
            } else if (rootObject instanceof Relatie) {
                hoofdPersonenInBericht.addAll(bepaalHoofdPersonenVoorRelatie((RelatieBericht) rootObject));
            }
        }

        return hoofdPersonenInBericht;
    }

    /**
     * Bepaalt de hoofdpersonen voor een relatie, hierbij speelt de soort relatie een rol.
     *
     * @param relatie de relatie waarvoor de hoofdpersonen moeten worden bepaald
     * @return lijst van hoofdpersonen binnen de relatie
     */
    private Collection<PersoonBericht> bepaalHoofdPersonenVoorRelatie(final RelatieBericht relatie) {
        Collection<PersoonBericht> result;
        if (relatie instanceof HuwelijkGeregistreerdPartnerschap) {
            result = bepaalHoofdPersonenVoorHuwelijkGeregistreerdPartnerschap(relatie);
        } else if (relatie instanceof FamilierechtelijkeBetrekking) {
            result = bepaalHoofdPersonenVoorFamilierechtelijkeBetrekking(relatie);
        } else {
            result = new ArrayList<>();
        }
        return result;
    }

    /**
     * Geval 2a.
     * @param relatie
     * @return
     */
    private List<PersoonBericht> bepaalHoofdPersonenVoorHuwelijkGeregistreerdPartnerschap(final RelatieBericht relatie) {
        final List<PersoonBericht> result = new ArrayList<>();
        for (final BetrokkenheidBericht betrokkenheid : relatie.getBetrokkenheden()) {
            if (SoortPersoon.INGESCHREVENE == betrokkenheid.getPersoon().getSoort().getWaarde()) {
                result.add(betrokkenheid.getPersoon());
            }
        }
        return result;
    }

    /**
     * Geval 2b.
     * @param relatie
     * @return
     */
    private List<PersoonBericht> bepaalHoofdPersonenVoorFamilierechtelijkeBetrekking(final RelatieBericht relatie) {
        final List<PersoonBericht> result = new ArrayList<>();
        for (final BetrokkenheidBericht betrokkenheid : relatie.getBetrokkenheden()) {
            if (SoortBetrokkenheid.KIND == betrokkenheid.getRol().getWaarde()
                    && SoortPersoon.INGESCHREVENE == betrokkenheid.getPersoon().getSoort().getWaarde())
            {
                result.add(betrokkenheid.getPersoon());
            }
        }

        if (result.isEmpty()) {
             // Dit is het geval van het bijschrijven van een niet ingeschreven kind bij een ingeschreven ouder.
            for (final BetrokkenheidBericht betrokkenheid : relatie.getBetrokkenheden()) {
                if (SoortBetrokkenheid.OUDER == betrokkenheid.getRol().getWaarde()
                    && SoortPersoon.INGESCHREVENE == betrokkenheid.getPersoon().getSoort().getWaarde())
                {
                    result.add(betrokkenheid.getPersoon());
                }
            }
        }
        return result;
    }

}

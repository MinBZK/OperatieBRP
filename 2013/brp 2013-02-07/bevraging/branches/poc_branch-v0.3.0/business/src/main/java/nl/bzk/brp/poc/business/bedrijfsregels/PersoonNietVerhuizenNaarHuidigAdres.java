/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.business.bedrijfsregels;

import nl.bzk.brp.poc.business.dto.antwoord.BijhoudingWaarschuwing;
import nl.bzk.brp.poc.business.dto.antwoord.BijhoudingWaarschuwingNiveau;
import nl.bzk.brp.poc.domein.PocPersoon;
import nl.bzk.brp.poc.domein.PocPersoonAdres;

/**
 * Bedrijfsregel die stelt dat een persoon niet mag verhuizen naar het adres waar deze persoon al woont.
 */
public class PersoonNietVerhuizenNaarHuidigAdres implements BedrijfsRegel<PocPersoon> {

    /**
     * {@inheritDoc}
     *
     * @param nieuweSituatie de nieuwe situatie; een persoon waarvan het adres gecontroleerd zal worden als nieuw
     * adres.
     * @param huidigeSituatie de huidige situatie; een persoon waarvan het adres gecontroleerd zal worden als huidig
     * adres.
     */
    @Override
    public BijhoudingWaarschuwing voerUit(final PocPersoon nieuweSituatie, final PocPersoon huidigeSituatie) {
        BijhoudingWaarschuwing resultaat = null;

        PocPersoonAdres nieuwAdres = nieuweSituatie.getAdressen().iterator().next();
        PocPersoonAdres oudAdres = huidigeSituatie.getAdressen().iterator().next();

        if (!zijnAdressenGelijk(nieuwAdres, oudAdres)) {
            resultaat = new BijhoudingWaarschuwing(BijhoudingWaarschuwingNiveau.ZACHTE_FOUT, "bdvh002.01",
                    "Persoon mag niet verhuizen naar hetzelfde huidige adres.");
        }
        return resultaat;
    }

    /**
     * Geeft aan of de twee opgegeven adressen gelijk zijn of niet.
     * @param nieuwAdres het nieuwe adres.
     * @param oudAdres het oude adres.
     * @return of de twee adressen gelijk zijn of niet.
     */
    private boolean zijnAdressenGelijk(final PocPersoonAdres nieuwAdres, final PocPersoonAdres oudAdres) {
        boolean resultaat = nieuwAdres != null && oudAdres != null;
        resultaat &= equalsMetVeiligeNull(nieuwAdres.getSoort(), oudAdres.getSoort());
        resultaat &= equalsMetVeiligeNull(nieuwAdres.getLand(), oudAdres.getLand());
        resultaat &= equalsMetVeiligeNull(nieuwAdres.getGemeente(), oudAdres.getGemeente());
        resultaat &= equalsMetVeiligeNull(nieuwAdres.getWoonplaats(), oudAdres.getWoonplaats());
        resultaat &= equalsMetVeiligeNull(nieuwAdres.getNaamOpenbareRuimte(), nieuwAdres.getNaamOpenbareRuimte());
        resultaat &= equalsMetVeiligeNull(nieuwAdres.getHuisNummer(), oudAdres.getHuisNummer());
        resultaat &= equalsMetVeiligeNull(nieuwAdres.getHuisnummertoevoeging(), oudAdres.getHuisnummertoevoeging());
        resultaat &= equalsMetVeiligeNull(nieuwAdres.getHuisletter(), oudAdres.getHuisletter());
        return resultaat;
    }

    /**
     * Geeft aan of de twee opgegeven waardes gelijk zijn of niet, rekening houdend met de mogelijkheid dat beide of
     * slechts één van de twee <code>null</code> zijn.
     *
     * @param nieuw de nieuwe waarde.
     * @param oud de oude waarde.
     * @return of de twee waardes gelijk/identiek zijn of niet.
     */
    private boolean equalsMetVeiligeNull(final Object nieuw, final Object oud) {
        return (nieuw == null && oud == null) || (nieuw != null && nieuw.equals(oud));
    }
}

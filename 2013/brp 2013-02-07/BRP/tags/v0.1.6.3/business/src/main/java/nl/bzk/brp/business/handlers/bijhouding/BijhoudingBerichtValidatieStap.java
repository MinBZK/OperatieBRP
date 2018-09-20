/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.handlers.bijhouding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.dto.BerichtResultaat;
import nl.bzk.brp.business.dto.ResultaatCode;
import nl.bzk.brp.business.dto.bijhouding.AbstractBijhoudingsBericht;
import nl.bzk.brp.business.dto.bijhouding.BijhoudingResultaat;
import nl.bzk.brp.business.handlers.AbstractBerichtVerwerkingsStap;
import nl.bzk.brp.model.objecttype.logisch.Persoon;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.OverruleMelding;
import nl.bzk.brp.model.validatie.SoortMelding;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * In deze stap wordt het bericht gevalideerd .
 */
public class BijhoudingBerichtValidatieStap extends AbstractBerichtVerwerkingsStap<AbstractBijhoudingsBericht,
        BerichtResultaat>
{

    private static final Logger LOGGER = LoggerFactory.getLogger(BijhoudingBerichtValidatieStap.class);

    // voorlopig is dit hier gedefinieerd, uiteindelijk moet dit naar de xml configuratie.
    private static final List<String> WHITELIST = Arrays.asList(
           MeldingCode.BRBY0106.getNaam(),
           MeldingCode.BRAL9003.getNaam()
    );

    @Override
    public boolean voerVerwerkingsStapUitVoorBericht(final AbstractBijhoudingsBericht bericht,
        final BerichtContext context, final BerichtResultaat resultaat)
    {
        testVoorOverruleMeldingen(bericht, resultaat);
        return DOORGAAN_MET_VERWERKING;
    }


    /**
     * Doe een generieke validatie dat de overrule meldingen in de white list zitten.
     * @param bericht het bericht
     * @param resultaat het uiteindelijk resultaat berichten.
     */
    private void testVoorOverruleMeldingen(final AbstractBijhoudingsBericht bericht,
        final BerichtResultaat resultaat)
    {
        if (bericht.getOverruledMeldingen() != null && !bericht.getOverruledMeldingen().isEmpty()) {
            for (OverruleMelding m : bericht.getOverruledMeldingen()) {
                if (StringUtils.isBlank(m.getCode())) {
                    resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.ALG0001,
                            "Code is verplicht voor overrule."));
                } else if (!WHITELIST.contains(m.getCode())) {
                    // test in de whitelist.
                    String msg = String.format("code %s mag niet overruled worden.", m.getCode());
                    resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.ALG0001, msg
                            , m, "regelCode"));
                    LOGGER.warn(msg);
                }
            }
        }
    }


    /**
     * Test of een persoon in de lijst van personen voorkoment MET dezelfde BSN. Dit houdt in dat alleen personen
     * met BSN in de lijst kan voorkomen.
     * @param lijst de lijst met personen.
     * @param persoon de op te zoeken persoon.
     * @return true als hij al voorkomt, false ander.
     */
    private boolean persoonIndeLijst(final List<Persoon> lijst, final Persoon persoon) {
        for (Persoon pers : lijst) {
            if (pers.getIdentificatieNummers().getBurgerServiceNummer().equals(
                    persoon.getIdentificatieNummers().getBurgerServiceNummer()))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public void naVerwerkingsStapVoorBericht(final AbstractBijhoudingsBericht bericht, final BerichtContext context,
        final BerichtResultaat resultaat)
    {
        // TODO Deze methode herzien en mogelijk op andere plek beter implementeren.

        // deze method wordt misbruikt, omdat deze stap als eerste in de stappen keten wordt doorlopen
        // en dus als laatste in de keten de naVerwerkingsStapVoorBericht wordt aangeroepen.

        // Wat we hier gaan doen in admistratieve taken uitvoeren.
        // Haal alle hoofd en bijpersonen die door deze actie zijn geraakt en maakt een grote lijst van.
        // bij de actie zitten deze in het berichtencontext omdat de acteis geen resultaten kennen
        // en hier moeten we copieren naar het bijhoudingResultaat
        if (resultaat.getResultaatCode() == ResultaatCode.GOED) {
            List<Persoon> copy = new ArrayList<Persoon>(context.getHoofdPersonen());
            for (Persoon pers : context.getBijPersonen()) {
                if (!persoonIndeLijst(copy, pers)) {
                    copy.add(pers);
                }
            }
            // niet blij met deze oplossing, moet nog in de template verwerken.
            if (resultaat instanceof BijhoudingResultaat) {
                BijhoudingResultaat bijhoudingResultaat = (BijhoudingResultaat) resultaat;
                for (Persoon pers : copy) {
                    bijhoudingResultaat.voegPersoonToe(pers);
                }
            }
        }

    }

}

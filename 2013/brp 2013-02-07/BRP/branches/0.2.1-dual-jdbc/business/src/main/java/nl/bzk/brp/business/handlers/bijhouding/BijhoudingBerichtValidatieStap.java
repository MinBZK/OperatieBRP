/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.handlers.bijhouding;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.business.bedrijfsregels.BedrijfsRegelManager;
import nl.bzk.brp.business.bedrijfsregels.BerichtBedrijfsRegel;
import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.dto.BerichtVerwerkingsResultaat;
import nl.bzk.brp.business.dto.bijhouding.AbstractBijhoudingsBericht;
import nl.bzk.brp.business.dto.bijhouding.BijhoudingResultaat;
import nl.bzk.brp.business.handlers.AbstractBerichtVerwerkingsStap;
import nl.bzk.brp.model.objecttype.logisch.Persoon;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Soortmelding;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.OverruleMelding;
import nl.bzk.brp.model.validatie.SoortOverruleMelding;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * In deze stap wordt het bericht gevalideerd. Dit gebeurd door eventuele bericht specifieke bedrijfsregels
 * te controleren.
 */
public class BijhoudingBerichtValidatieStap extends AbstractBerichtVerwerkingsStap<AbstractBijhoudingsBericht,
        BerichtVerwerkingsResultaat>
{

    private static final Logger LOGGER = LoggerFactory.getLogger(BijhoudingBerichtValidatieStap.class);

    @Inject
    private BedrijfsRegelManager bedrijfsRegelManager;

    /** lijst van toegestane overrule meldingen. */
    private List<String> whitelistOverrulbareMeldingen    = null;
    /** lijst van overrule meldingen die pas gebeuren NA de uitvoer stap, moet ook onderdeel zijn van de whitelist. */
    private List<String> naverwerkingOverrulbareMeldingen = null;

    @Override
    public boolean voerVerwerkingsStapUitVoorBericht(final AbstractBijhoudingsBericht bericht,
        final BerichtContext context, final BerichtVerwerkingsResultaat resultaat)
    {
        testVoorOverruleMeldingen(bericht, resultaat);
        controleerBedrijfsregelsOpBericht(bericht, resultaat);

        return DOORGAAN_MET_VERWERKING;
    }

    /**
     * Controleert of er bericht specifieke bedrijfsregels zijn en zo ja, controleert deze tegen het bericht. Eventuele
     * gevonden fouten/waarschuwingen worden aan het resultaat toegevoegd als meldingen.
     *
     * @param bericht het bericht dat gecontroleerd dient te worden.
     * @param resultaat het resultaat van de bericht verwerking.
     */
    private void controleerBedrijfsregelsOpBericht(final AbstractBijhoudingsBericht bericht,
                                                   final BerichtVerwerkingsResultaat resultaat)
    {
        if (bedrijfsRegelManager == null) {
            LOGGER.error("Geen bedrijfsregelmanager aanwezig; bericht wordt niet gevalideerd op bericht regels!");
        } else if (null != bedrijfsRegelManager.getUitTeVoerenBerichtBedrijfsRegels(bericht.getClass())) {
            for (BerichtBedrijfsRegel regel : bedrijfsRegelManager.getUitTeVoerenBerichtBedrijfsRegels(
                bericht.getClass()))
            {
                final List<Melding> meldingen = regel.executeer(bericht);
                if (meldingen.size() > 0) {
                    resultaat.voegMeldingenToe(meldingen);
                }
            }
        }
    }

    /**
     * Doe een generieke validatie dat de overrule meldingen in de white list zitten.
     *
     * @param bericht het bericht
     * @param resultaat het uiteindelijk resultaat berichten.
     */
    private void testVoorOverruleMeldingen(final AbstractBijhoudingsBericht bericht,
                                           final BerichtVerwerkingsResultaat resultaat)
    {
        if (bericht.getOverruledMeldingen() != null && !bericht.getOverruledMeldingen().isEmpty()) {
            for (OverruleMelding m : bericht.getOverruledMeldingen()) {
                if (StringUtils.isBlank(m.getCode())) {
                    resultaat.voegMeldingToe(new Melding(Soortmelding.FOUT, MeldingCode.ALG0001,
                        "Code is verplicht voor overrule."));
                } else if (!whitelistOverrulbareMeldingen.contains(m.getCode())) {
                    // test in de whitelist.
                    String msg = String.format("code %s mag niet overruled worden.", m.getCode());
                    resultaat.voegMeldingToe(new Melding(Soortmelding.FOUT, MeldingCode.ALG0001, msg
                        , m, "regelCode"));
                    LOGGER.warn(msg);
                } else if (naverwerkingOverrulbareMeldingen != null
                    && naverwerkingOverrulbareMeldingen.contains(m.getCode()))
                {
                    // Dit om een speciale type overrule meldingen, kan pas gebeuren bij validatie NA verwerking.
                    m.setSoort(SoortOverruleMelding.NABEWERKING_VALIDATIE_MELDING);
                }
            }
        }
    }


    /**
     * Test of een persoon in de lijst van personen voorkoment MET dezelfde BSN. Dit houdt in dat alleen personen
     * met BSN in de lijst kan voorkomen.
     *
     * @param lijst de lijst met personen.
     * @param persoon de op te zoeken persoon.
     * @return true als hij al voorkomt, false ander.
     */
    private boolean persoonIndeLijst(final List<Persoon> lijst, final Persoon persoon) {
        for (Persoon pers : lijst) {
            if (pers.getIdentificatienummers().getBurgerservicenummer().equals(
                persoon.getIdentificatienummers().getBurgerservicenummer()))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public void naVerwerkingsStapVoorBericht(final AbstractBijhoudingsBericht bericht, final BerichtContext context,
        final BerichtVerwerkingsResultaat resultaat)
    {
        // TODO Deze methode herzien en mogelijk op andere plek beter implementeren.

        // deze method wordt misbruikt, omdat deze stap als eerste in de stappen keten wordt doorlopen
        // en dus als laatste in de keten de naVerwerkingsStapVoorBericht wordt aangeroepen.

        // Wat we hier gaan doen in admistratieve taken uitvoeren.
        // Haal alle hoofd en bijpersonen die door deze actie zijn geraakt en maakt een grote lijst van.
        // bij de actie zitten deze in het berichtencontext omdat de acteis geen resultaten kennen
        // en hier moeten we copieren naar het bijhoudingResultaat
        if (resultaat.getVerwerkingsResultaat()) {
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


    public List<String> getWhitelistOverrulbareMeldingen() {
        return whitelistOverrulbareMeldingen;
    }


    public void setWhitelistOverrulbareMeldingen(final List<String> whitelistOverrulbareMeldingen) {
        this.whitelistOverrulbareMeldingen = whitelistOverrulbareMeldingen;
    }

    public List<String> getNaverwerkingOverrulbareMeldingen() {
        return naverwerkingOverrulbareMeldingen;
    }

    public void setNaverwerkingOverrulbareMeldingen(final List<String> overrulbareMeldingen) {
        naverwerkingOverrulbareMeldingen = overrulbareMeldingen;
    }
}

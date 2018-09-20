/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.handlers.bijhouding;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.business.bedrijfsregels.BedrijfsRegel;
import nl.bzk.brp.business.bedrijfsregels.BedrijfsRegelManager;
import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.dto.BerichtResultaat;
import nl.bzk.brp.business.dto.bijhouding.AbstractBijhoudingsBericht;
import nl.bzk.brp.business.handlers.AbstractBerichtVerwerkingsStap;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.OverruleMelding;
import nl.bzk.brp.model.validatie.SoortMelding;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * De stap in de uitvoering van een bericht waarin wordt gecontroleerd of het binnenkomende bericht valide is volgens
 * een bedrijfsregel. Geconstateerde fouten waardes worden, inclusief bericht melding en zwaarte, toegevoegd aan de
 * lijst van fouten binnen het antwoord.
 */
public class BedrijfsregelValidatieStap extends
        AbstractBerichtVerwerkingsStap<AbstractBijhoudingsBericht, BerichtResultaat>
{

    private static final Logger  LOGGER = LoggerFactory.getLogger(BerichtUitvoerStap.class);

    @Inject
    private BedrijfsRegelManager bedrijfsRegelManager;

    @Inject
    private PersoonRepository    persoonRepository;

    /** {@inheritDoc} */
    @Override
    public boolean voerVerwerkingsStapUitVoorBericht(final AbstractBijhoudingsBericht bericht,
            final BerichtContext context, final BerichtResultaat resultaat)
    {
        for (Actie actie : bericht.getBrpActies()) {
            RootObject huidigeSituatie = haalHuidigeSituatieOp(actie);
            RootObject nieuweSituatie = actie.getRootObjecten().get(0);

            if (null != bedrijfsRegelManager.getUitTeVoerenBedrijfsRegels(actie.getSoort())) {
                for (BedrijfsRegel<RootObject> bedrijfsRegel : bedrijfsRegelManager.getUitTeVoerenBedrijfsRegels(actie
                        .getSoort()))
                {
                    final List<Melding> meldingen =
                        bedrijfsRegel.executeer(huidigeSituatie, nieuweSituatie, actie.getDatumAanvangGeldigheid(),
                                actie.getDatumEindeGeldigheid());
                    if (meldingen.size() > 0) {
                        resultaat.voegMeldingenToe(meldingen);
                    }
                }
            }
        }

        // we moeten hier corrigeren, omdat als we deze methode verlaten en er is een Fout geconstateerd,
        // de trein wordt gestopt.
        corrigeerVoorOverrulebareFouten(bericht, context, resultaat);
        return DOORGAAN_MET_VERWERKING;
    }

    /**
     * Omdat bedrijfsregels gebruik maken van de huidige situatie in de BRP database, moet de huidige situatie ook
     * via de DAL laag opgehaald worden. Dat gebeurt in deze functie.
     *
     * @param actie De actie die uitegevoerd dient te worden uit het inkomende bericht.
     * @return Een PersistentRootObject wat in feite een instantie is van een Persoon of Relatie sinds deze 2 objecten
     *         als RootObject worden gezien.
     */
    private RootObject haalHuidigeSituatieOp(final Actie actie) {
        final RootObject huidigeSituatie;
        switch (actie.getSoort()) {
            case VERHUIZING:
            case CORRECTIE_ADRES_BINNEN_NL:
            case WIJZIGING_NAAMGEBRUIK:
                huidigeSituatie =
                    persoonRepository.findByBurgerservicenummer(((PersoonBericht) actie.getRootObjecten().get(0))
                            .getIdentificatienummers().getBurgerservicenummer());
                break;
            case REGISTRATIE_NATIONALITEIT:
            case INSCHRIJVING_GEBOORTE:
            case REGISTRATIE_HUWELIJK:
                huidigeSituatie = null;
                break;
            default:
                throw new IllegalArgumentException(actie.getSoort()
                    + " wordt nog niet ondersteund door de berichtverwerker");
        }
        return huidigeSituatie;
    }

    /**
     * Corrigeer alle fouten van het type OVERRULEBAAR, met deze voorwaarden:
     * - alle overrulebare fouten zijn gespecificeerd in de lijst met overrulemeldingen.
     * - het aantal is identiek (maw. alle fouten moeten ook daadwerkelijk geconstateerd zijn).
     * - er mogen geen andere overrulebare fouten overblijven. (maw. alle fouten zijn oevrruled).
     * Alleen dan worden alle overrulebare gedowngrade naar WAARSCHUWING en de trein kan doorgaan.
     *
     * @param bericht het volledig bericht
     * @param context de context
     * @param resultaat de lijst met meldingen.
     */
    public void corrigeerVoorOverrulebareFouten(final AbstractBijhoudingsBericht bericht, final BerichtContext context,
            final BerichtResultaat resultaat)
    {
        if (null != bericht.getOverruledMeldingen() && !bericht.getOverruledMeldingen().isEmpty()) {
            List<OverruleMelding> lokOverrMeldingen = new ArrayList<OverruleMelding>(bericht.getOverruledMeldingen());

            if (resultaat.bevatVerwerkingStoppendeFouten()) {
                List<Melding> resultaatMeldingen = resultaat.getMeldingen();
                List<Melding> copy = new ArrayList<Melding>(resultaatMeldingen);

                for (Melding melding : resultaatMeldingen) {
                    if (melding.getSoort() == SoortMelding.FOUT_OVERRULEBAAR) {
                        // gevondenMelding is het object in de overrulebareMeldingen lijst.
                        OverruleMelding gevondenMelding = isMeldingOverruled(bericht.getOverruledMeldingen(), melding);
                        if (gevondenMelding != null) {
                            copy.remove(melding);
                            lokOverrMeldingen.remove(gevondenMelding);
                            gevondenMelding.setOmschrijving(melding.getOmschrijving());
                            // wat doen we met de attribuutnaam? de foutmelding copieren we met die van de
                            // geconstateerde fout
                        }
                    }
                }

                // OK, test nu of overrulebare fouten in beide lijsten leeg zijn. zo ja, dan doen we in het echt
                if (lokOverrMeldingen.size() == 0 && (!lijstHeeftOverRulebareMeldingen(copy))) {
                    resultaat.overruleAlleOverrulebareFouten();
                    resultaat.bevatVerwerkingStoppendeFouten();
                    resultaat.setOverruleMeldingen(bericht.getOverruledMeldingen());

                    // schrijf nu als 'administratief' in de logfile, dat deze meldingen zijn overschreven.
                    List<String> codes = new ArrayList<String>();
                    for (OverruleMelding m : bericht.getOverruledMeldingen()) {
                        codes.add(m.getCode());
                    }
                    String msg =
                        String.format("%s: Voor het bericht %d zijn de volgende regelcodes overrruled %s",
                                "[OVERRULED]", context.getIngaandBerichtId(), codes);
                    LOGGER.warn(msg);
                }
            }

            // test of nog in de lijst meldingen staan die we niet gevonden hebben.
            if (lokOverrMeldingen.size() != 0) {
                // oeps, probeert te overrulen wat er niet was. foei !!
                List<String> codes = new ArrayList<String>();
                for (OverruleMelding m : lokOverrMeldingen) {
                    codes.add(m.getCode());
                }
                // pak de eerste verzendend id als id
                resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.ALG0001,
                        "Er zijn overrulebare meldingen opgegeven die niet van toepassing zijn: " + codes + ".",
                        lokOverrMeldingen.get(0), "regelCode"));
            }
        }
    }

    /**
     * Test of een lijst Fout-meldingen bevat.
     *
     * @param meldingen de lijst
     * @return true of false
     */
    private boolean lijstHeeftOverRulebareMeldingen(final List<Melding> meldingen) {
        for (Melding m : meldingen) {
            if (m.getSoort() == SoortMelding.FOUT_OVERRULEBAAR || m.getSoort() == SoortMelding.FOUT_ONOVERRULEBAAR) {
                return true;
            }
        }
        return false;
    }

    /**
     * Test of 2 strings gelijk zijn aan elkaar. waarbij null gelijk is aan string (met evt. aleen maar spaties)
     *
     * @param s1 string 1
     * @param s2 string 2
     * @return true als gelijk, false anders.
     */
    boolean isEquals(final String s1, final String s2) {
        boolean retval = false;
        if (s1 == null && s2 == null) {
            retval = true;
        } else if (s1 == null || s2 == null) {
            retval = StringUtils.isBlank(s1) && StringUtils.isBlank(s2);
        } else {
            retval = s1.equals(s2);
        }
        return retval;
    }

    /**
     * Melding is gevonden als de meldingscode gelijk is en het verzendendId gelijk is (beide leeg/null) of
     * beide dezelfde waarde.
     *
     * @param overrulebareMeldingen lijst van overrulbare meldingen.
     * @param melding de geconstateerde melding.
     * @return de gevonden overrulebare melding uit de lijst (of null als niet gevonden).
     */
    private OverruleMelding isMeldingOverruled(final List<OverruleMelding> overrulebareMeldingen,
                                               final Melding melding)
    {

        LOGGER.debug("Gevonden Melding: verz=[%s], code=[%s], attr=[%s]", new Object[] { melding.getVerzendendId(),
            melding.getCode().getNaam(), melding.getAttribuutNaam() });
        for (OverruleMelding m : overrulebareMeldingen) {
            LOGGER.debug("Given overrulMelding: verz=[%s], code=[%s], attr=[%s]",
                    new Object[] { m.getVerzendendId(), m.getCode(), melding.getAttribuutNaam() });
            if (m.getCode().equals(melding.getCode().getNaam())
                && isEquals(m.getVerzendendId(), melding.getVerzendendId()))
            {
                LOGGER.debug("Wordt overruled");
                return m;
            }
        }
        LOGGER.debug("Is NIET gevonden");
        return null;
    }

}

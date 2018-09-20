/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.business.dto.BerichtVerwerkingsResultaat;
import nl.bzk.brp.business.dto.bevraging.AbstractBevragingsBericht;
import nl.bzk.brp.business.dto.bevraging.OpvragenPersoonResultaat;
import nl.bzk.brp.business.dto.bijhouding.AbstractBijhoudingsBericht;
import nl.bzk.brp.business.dto.bijhouding.BijhoudingResultaat;
import nl.bzk.brp.model.attribuuttype.Applicatienaam;
import nl.bzk.brp.model.attribuuttype.DatumTijd;
import nl.bzk.brp.model.attribuuttype.Ja;
import nl.bzk.brp.model.attribuuttype.Organisatienaam;
import nl.bzk.brp.model.attribuuttype.Sleutelwaardetekst;
import nl.bzk.brp.model.groep.bericht.BerichtResultaatGroepBericht;
import nl.bzk.brp.model.groep.bericht.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.groep.logisch.BerichtResultaatGroep;
import nl.bzk.brp.model.groep.logisch.BerichtStuurgegevensGroep;
import nl.bzk.brp.model.objecttype.bericht.BerichtBericht;
import nl.bzk.brp.model.objecttype.operationeel.BetrokkenheidModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.RelatieModel;
import nl.bzk.brp.model.objecttype.operationeel.basis.AbstractBetrokkenheidModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Bijhoudingsresultaat;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortMelding;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Verwerkingsresultaat;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.OverruleMelding;
import nl.bzk.brp.util.AttribuutTypeUtil;
import nl.bzk.brp.web.bevraging.AbstractBevragingAntwoordBericht;
import nl.bzk.brp.web.bevraging.VraagDetailsPersoonAntwoord;
import nl.bzk.brp.web.bevraging.VraagKandidaatVaderAntwoord;
import nl.bzk.brp.web.bevraging.VraagPersonenOpAdresInclusiefBetrokkenhedenAntwoord;
import nl.bzk.brp.web.bijhouding.AbstractBijhoudingAntwoordBericht;
import nl.bzk.brp.web.bijhouding.CorrectieAdresNLAntwoordBericht;
import nl.bzk.brp.web.bijhouding.HuwelijkEnGeregistreerdPartnerschapAntwoordBericht;
import nl.bzk.brp.web.bijhouding.InschrijvingGeboorteAntwoordBericht;
import nl.bzk.brp.web.bijhouding.VerhuizingAntwoordBericht;
import nl.bzk.brp.web.interceptor.ArchiveringBericht;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class AntwoordBerichtFactoryImpl implements AntwoordBerichtFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(AntwoordBerichtFactoryImpl.class);

    /** {@inheritDoc} **/
    @Override
    public AbstractAntwoordBericht bouwAntwoordBericht(final BerichtBericht ingaandBericht,
                                                       final BerichtVerwerkingsResultaat resultaat)
    {
        final AbstractAntwoordBericht antwoordBericht = maakAntwoordBerichtVoorIngaanBericht(ingaandBericht);
        antwoordBericht.setBerichtStuurgegevensGroep(maakStuurgegevensGroepVoorUitgaandBericht(ingaandBericht));
        antwoordBericht.setBerichtResultaatGroep(bepaalResultaatVoorBerichtVerwerking(ingaandBericht, resultaat));
        antwoordBericht.setMeldingen(bepaalMeldingen(resultaat));
        antwoordBericht.setOverruledMeldingen(bepaalOverruledMeldingen(resultaat));

        if (ingaandBericht instanceof AbstractBijhoudingsBericht) {
            BijhoudingResultaat bijhResultaat = (BijhoudingResultaat) resultaat;
            AbstractBijhoudingAntwoordBericht bijhAntwoord = (AbstractBijhoudingAntwoordBericht) antwoordBericht;
            if (bijhResultaat.getBijgehoudenPersonen() == null || bijhResultaat.getBijgehoudenPersonen().isEmpty()) {
                bijhAntwoord.setBijgehoudenPersonen(null);
            } else {
                bijhAntwoord.setBijgehoudenPersonen(bijhResultaat.getBijgehoudenPersonen());
            }
        } else if (ingaandBericht instanceof AbstractBevragingsBericht) {
            final AbstractBevragingAntwoordBericht bevragingAntwoordBericht =
                    (AbstractBevragingAntwoordBericht) antwoordBericht;
            final OpvragenPersoonResultaat opvragenPersoonResultaat = (OpvragenPersoonResultaat) resultaat;
            if (opvragenPersoonResultaat.getGevondenPersonen() != null
                && !opvragenPersoonResultaat.getGevondenPersonen().isEmpty())
            {
                List<PersoonModel> personen = new ArrayList<PersoonModel>();
                for (final PersoonModel gevondenPersoon : opvragenPersoonResultaat.getGevondenPersonen()) {
                    personen.add(gevondenPersoon);
                }

                for (PersoonModel persoon : personen) {
                    bewerkViewOpPersoonVoorBinding(persoon);
                }

                bevragingAntwoordBericht.setPersonen(personen);

            }

        }
        return antwoordBericht;
    }

    /**
     * Bepaalt de resultaat groep voor het uitgaande bericht op basis van het resultaat uit de business laag.
     *
     * @param ingaandBericht Het ingaande bericht.
     * @param resultaat Het resultaat van de verwerking uit de business laag.
     * @return Bericht resultaat groep.
     */
    private BerichtResultaatGroep bepaalResultaatVoorBerichtVerwerking(final BerichtBericht ingaandBericht,
                                                                       final BerichtVerwerkingsResultaat resultaat)
    {
        final BerichtResultaatGroepBericht resultaatGroep = new BerichtResultaatGroepBericht();
        if (ingaandBericht instanceof AbstractBijhoudingsBericht) {
            resultaatGroep.setBijhoudingsresultaat(bepaalResultaatVoorBijhouding(resultaat,
                    ingaandBericht.getBerichtStuurgegevensGroep().getIndPrevalidatie()));
            if (((BijhoudingResultaat) resultaat).getTijdstipRegistratie() != null) {
                resultaatGroep.setTijdstipRegistratie(new DatumTijd(
                        ((BijhoudingResultaat) resultaat).getTijdstipRegistratie()));
            }
        }
        resultaatGroep.setHoogsteMeldingNiveau(bepaalHoogsteMeldingNiveau(resultaat.getMeldingen()));

        if (resultaat.getVerwerkingsResultaat()) {
            resultaatGroep.setVerwerkingsresultaat(Verwerkingsresultaat.GOED);
        } else {
            resultaatGroep.setVerwerkingsresultaat(Verwerkingsresultaat.FOUT);
        }
        return resultaatGroep;
    }

    /**
     * Bepaalt het hoogste melding niveau aan de hand van de lijst met meldingen.
     * @param meldingen Lijst van meldingen.
     * @return Soortmelding.
     */
    private SoortMelding bepaalHoogsteMeldingNiveau(final List<Melding> meldingen) {
        SoortMelding hoogste;
        if (meldingen == null || meldingen.isEmpty()) {
            hoogste = null;
        } else {
            int hoogsteOrdinal = -1;
            for (Melding melding : meldingen) {
                hoogsteOrdinal = Math.max(hoogsteOrdinal, melding.getSoort().ordinal());
            }
            hoogste = SoortMelding.values()[hoogsteOrdinal];
        }
        return hoogste;
    }

    /**
     * Bepaalt het resultaat van de bijhouding. Dit wordt gedaan op basis van het verwerkingsresultaat uit de business
     * laag.
     *
     * @param resultaat Het resultaat uit de business laag.
     * @param indPrevalidatie Het indicatie prevalidatie vlag.
     * @return Het bijhoudingsresultaat.
     */
    private Bijhoudingsresultaat bepaalResultaatVoorBijhouding(final BerichtVerwerkingsResultaat resultaat,
                                                               final Ja indPrevalidatie)
    {
        final Bijhoudingsresultaat bijhResultaat;

        if (resultaat.getMeldingen() != null
            && indPrevalidatie == Ja.Ja)
        {
            int hoogsteOrdinal = -1;
            for (Melding melding : resultaat.getMeldingen()) {
                hoogsteOrdinal = Math.max(hoogsteOrdinal, melding.getSoort().ordinal());
            }
            if (hoogsteOrdinal != -1 && SoortMelding.values()[hoogsteOrdinal] != SoortMelding.FOUT) {
                bijhResultaat = Bijhoudingsresultaat.VERWERKT;
            } else {
                bijhResultaat = null;
            }
        } else if (resultaat.getVerwerkingsResultaat()) {
            bijhResultaat = Bijhoudingsresultaat.VERWERKT;
        } else {
            bijhResultaat = null;
        }
        return bijhResultaat;
    }

    /**
     * Maakt de stuurgegevens aan voor het uitgaande bericht.
     *
     * @param ingaandBericht Het ingaande bericht.
     * @return Bericht stuurgegevens.
     */
    private BerichtStuurgegevensGroep maakStuurgegevensGroepVoorUitgaandBericht(final BerichtBericht ingaandBericht) {
        final BerichtStuurgegevensGroepBericht stuurgegevensGroep = new BerichtStuurgegevensGroepBericht();
        stuurgegevensGroep.setIndPrevalidatie(ingaandBericht.getBerichtStuurgegevensGroep().getIndPrevalidatie());
        stuurgegevensGroep.setApplicatie(new Applicatienaam("BRP"));
        stuurgegevensGroep.setOrganisatie(new Organisatienaam("Ministerie BZK"));
        stuurgegevensGroep.setReferentienummer(new Sleutelwaardetekst(bepaalReferentieNr()));
        if (ingaandBericht.getBerichtStuurgegevensGroep() != null
            && AttribuutTypeUtil.isNotBlank(ingaandBericht.getBerichtStuurgegevensGroep().getReferentienummer()))
        {
            stuurgegevensGroep.setCrossReferentienummer(
                    ingaandBericht.getBerichtStuurgegevensGroep().getReferentienummer());
        } else {
            stuurgegevensGroep.setCrossReferentienummer(new Sleutelwaardetekst("onbekend"));
        }

        return stuurgegevensGroep;
    }

    /**
     * Haal de unieke id op dat correspondeert met de inkomende bericht.
     *
     * @return refentieId
     */
    private String bepaalReferentieNr() {
        // haal de (unieke) id dat toevallig correspondeert met de inkomend bericht id.
        Long berichtInId;
        String referentieNr = "OnbekendeID";
        try {
            berichtInId =
                (Long) PhaseInterceptorChain.getCurrentMessage().getExchange()
                        .get(ArchiveringBericht.BERICHT_ARCHIVERING_IN_ID);
        } catch (NullPointerException e) {
            berichtInId = null;
            // in geval dat PhaseInterceptorChain.getCurrentMessage().getExchange() niet bestaat
        }
        if (berichtInId != null) {
            referentieNr = berichtInId.toString();
        }

        return referentieNr;
    }

    /**
     * Bepaalt op basis van het ingaande bericht het antwoord bericht dat geretourneerd moet worden.
     *
     * @param ingaandBericht Het ingaande bericht.
     * @return Het antwoord bericht behorende bij het ingaande bericht.
     */
    private AbstractAntwoordBericht maakAntwoordBerichtVoorIngaanBericht(final BerichtBericht ingaandBericht) {
        final AbstractAntwoordBericht antwoordBericht;
        switch (ingaandBericht.getSoortBericht()) {
            case AFSTAMMING_INSCHRIJVINGAANGIFTEGEBOORTE_BIJHOUDING:
                antwoordBericht = new InschrijvingGeboorteAntwoordBericht();
                break;
            case HUWELIJKPARTNERSCHAP_REGISTRATIEHUWELIJK_BIJHOUDING:
                antwoordBericht = new HuwelijkEnGeregistreerdPartnerschapAntwoordBericht();
                break;
            case HUWELIJKPARTNERSCHAP_REGISTRATIEPARTNERSCHAP_BIJHOUDING:
                throw new UnsupportedOperationException("Registratie partnerschap wordt nog niet ondersteund.");
            case MIGRATIE_CORRECTIEADRESBINNENNL_BIJHOUDING:
                antwoordBericht = new CorrectieAdresNLAntwoordBericht();
                break;
            case MIGRATIE_VERHUIZING_BIJHOUDING:
                antwoordBericht = new VerhuizingAntwoordBericht();
                break;
            case VRAAG_DETAILSPERSOON_BEVRAGEN:
                antwoordBericht = new VraagDetailsPersoonAntwoord();
                break;
            case VRAAG_KANDIDAATVADER_BEVRAGEN:
                antwoordBericht = new VraagKandidaatVaderAntwoord();
                break;
            case VRAAG_PERSONENOPADRESINCLUSIEFBETROKKENHEDEN_BEVRAGEN:
                antwoordBericht = new VraagPersonenOpAdresInclusiefBetrokkenhedenAntwoord();
                break;
            default:
                throw new IllegalStateException("Mapping van ingaande en uitgaande bericht soorten is niet compleet.");
        }
        return antwoordBericht;
    }

    /**
     * Zet de berichtMeldingen op null als er geen meldingen zijn en anders maak een nieuwe lijst van meldingen.
     *
     * @param berichtResultaat het resultaat van een bericht
     * @return lijst van meldingen
     */
    private List<Melding> bepaalMeldingen(final BerichtVerwerkingsResultaat berichtResultaat) {
        List<Melding> berichtMeldingen;

        if (berichtResultaat == null || berichtResultaat.getMeldingen() == null
            || berichtResultaat.getMeldingen().isEmpty())
        {
            berichtMeldingen = null;
        } else {
            berichtMeldingen = new ArrayList<Melding>(berichtResultaat.getMeldingen());
        }

        return berichtMeldingen;
    }

    /**
     * Zet de berichtMeldingen op null als er geen overruledMeldingen zijn en anders maak een nieuwe lijst
     * van overruledMeldingen.
     *
     * @param berichtResultaat het resultaat van een bericht
     * @return lijst van overruledMeldingen
     */
    private List<OverruleMelding> bepaalOverruledMeldingen(final BerichtVerwerkingsResultaat berichtResultaat) {
        List<OverruleMelding> berichtMeldingen = null;

        if (berichtResultaat != null && berichtResultaat.getOverruleMeldingen() != null
            && !berichtResultaat.getMeldingen().isEmpty())
        {
            berichtMeldingen = new ArrayList<OverruleMelding>(berichtResultaat.getOverruleMeldingen());
        }
        return berichtMeldingen;
    }

    /**
     * Let op, de Persoon die in dit antwoord bericht zit bevat dubbele informatie die ervoor kan zorgen dat de
     * databinding niet goed werkt. De persoon kent mogelijk een partner betrokkenheid, deze partner betrokkenheid kent
     * een relatie met weer 2 betrokkenheden. Van deze 2 betrokkenheden is er een hetzelfde als de directe
     * betrokkenheid op de persoon in dit antwoord bericht. Hier kan de databinding niet tegen. Wat we in deze functie
     * doen is de betrokkenheid die aan de relatie hangt verwijderen. We passen dus de View op de persoon aan voor
     * correcte presentatie in het xml bericht.
     *
     * @param persoon De te bewerken persoon.
     */
    private void bewerkViewOpPersoonVoorBinding(final PersoonModel persoon) {
        if (persoon.getBetrokkenheden() != null) {
            BetrokkenheidModel teVerwijderenBetr = null;
            for (BetrokkenheidModel directeBetrokkenheid : persoon.getPartnerBetrokkenHeden()) {
                RelatieModel relatie = directeBetrokkenheid.getRelatie();
                for (BetrokkenheidModel indirecteBetrokkenheid : relatie.getBetrokkenheden()) {
                    if (indirecteBetrokkenheid.getId() != null
                        && indirecteBetrokkenheid.getId().equals(directeBetrokkenheid.getId()))
                    {
                        teVerwijderenBetr = indirecteBetrokkenheid;
                        continue;
                    }
                }
                if (teVerwijderenBetr != null) {
                    RelatieModel relatieKopie = new RelatieModel(relatie);
                    for (BetrokkenheidModel betrokkenheid : relatie.getBetrokkenheden()) {
                        if (betrokkenheid != teVerwijderenBetr) {
                            relatieKopie.getBetrokkenheden().add(betrokkenheid);
                        }
                    }
                    teVerwijderenBetr = null;

                    // De relatie wordt vervangen door een copy met daarin de aangepaste betrokkenheden (en dus
                    // eventueel een betrokkenheid verwijderd. We kunnen niet de betrokkenheid uit de originele
                    // relatie verwijderen, daar mogelijk naar dezelfde relatie wordt verwezen vanuit een andere
                    // persoon die is gevonden. Bijvoorbeeld als er wordt gezocht op personen op hetzelfde adres,
                    // dan zullen twee getrouwde mensen die op hetzelfde adres wonen beide naar de zelfde relatie
                    // verwijzen. Als we dan voor persoon 1 zijn eigen betrokkenheid uit de relatie halen en dan
                    // voor persoon 2 ook zijn eigen betrokkenheid uit de relatie verwijderen, houden we een lege
                    // relatie over en niet zoals bedoeld twee relaties met in de een de betrokkenheid naar de ander
                    // en in de ander de betrokkenheid naar de een. Vandaar de copy, zodat beide personen naar een
                    // andere relatie verwijzen met ieder een andere betrokkenheid.
                    try {
                        // TODO: Onderstaande zonder reflectie uitvoeren!
                        Field fld = AbstractBetrokkenheidModel.class.getDeclaredField("relatie");
                        fld.setAccessible(true);
                        fld.set(directeBetrokkenheid, relatieKopie);
                    } catch (IllegalAccessException e) {
                        LOGGER.error("Systeem fout bij het zetten van een relatie via reflectie.", e);
                        e.printStackTrace();
                    } catch (NoSuchFieldException e) {
                        LOGGER.error("Systeem fout bij het zetten van een relatie via reflectie.", e);
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

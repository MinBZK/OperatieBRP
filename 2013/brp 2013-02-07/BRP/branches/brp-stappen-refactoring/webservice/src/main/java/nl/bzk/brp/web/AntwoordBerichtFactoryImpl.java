/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import nl.bzk.brp.business.dto.BerichtVerwerkingsResultaat;
import nl.bzk.brp.business.dto.bevraging.AbstractBevragingsBericht;
import nl.bzk.brp.business.dto.bevraging.OpvragenPersoonResultaat;
import nl.bzk.brp.business.dto.bijhouding.AbstractBijhoudingsBericht;
import nl.bzk.brp.business.dto.bijhouding.BijhoudingResultaat;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.Applicatienaam;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.Meldingtekst;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.Organisatienaam;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.Verwerkingswijze;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijd;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Sleutelwaardetekst;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Bijhoudingsresultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Verwerkingsresultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Element;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.bericht.ber.AdministratieveHandelingBijgehoudenPersoonBericht;
import nl.bzk.brp.model.bericht.ber.AdministratieveHandelingGedeblokkeerdeMeldingBericht;
import nl.bzk.brp.model.bericht.ber.BerichtMeldingBericht;
import nl.bzk.brp.model.bericht.ber.BerichtParametersGroepBericht;
import nl.bzk.brp.model.bericht.ber.BerichtResultaatGroepBericht;
import nl.bzk.brp.model.bericht.ber.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.bericht.ber.MeldingBericht;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.bericht.kern.HandelingErkenningOngeborenVruchtBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.logisch.ber.Bericht;
import nl.bzk.brp.model.logisch.kern.Betrokkenheid;
import nl.bzk.brp.model.logisch.kern.GeregistreerdPartnerschap;
import nl.bzk.brp.model.logisch.kern.Huwelijk;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.logisch.kern.Relatie;
import nl.bzk.brp.model.operationeel.kern.BetrokkenheidModel;
import nl.bzk.brp.model.operationeel.kern.GeregistreerdPartnerschapModel;
import nl.bzk.brp.model.operationeel.kern.HuwelijkModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.operationeel.kern.RelatieModel;
import nl.bzk.brp.model.operationeel.kern.basis.AbstractBetrokkenheidModel;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.util.AttribuutTypeUtil;
import nl.bzk.brp.util.MeldingUtil;
import nl.bzk.brp.util.RelatieUtils;
import nl.bzk.brp.web.bevraging.AbstractBevragingAntwoordBericht;
import nl.bzk.brp.web.bevraging.VraagDetailsPersoonAntwoordBericht;
import nl.bzk.brp.web.bevraging.VraagKandidaatVaderAntwoordBericht;
import nl.bzk.brp.web.bevraging.VraagPersonenOpAdresInclusiefBetrokkenhedenAntwoordBericht;
import nl.bzk.brp.web.bijhouding.AbstractBijhoudingAntwoordBericht;
import nl.bzk.brp.web.bijhouding.CorrectieAdresNLAntwoordBericht;
import nl.bzk.brp.web.bijhouding.HuwelijkAntwoordBericht;
import nl.bzk.brp.web.bijhouding.InschrijvingGeboorteAntwoordBericht;
import nl.bzk.brp.web.bijhouding.RegistratieOverlijdenAntwoordBericht;
import nl.bzk.brp.web.bijhouding.VerhuizingAntwoordBericht;
import nl.bzk.brp.web.interceptor.ArchiveringBericht;

import org.apache.commons.collections.CollectionUtils;
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

    /** {@inheritDoc} * */
    @Override
    public AbstractAntwoordBericht bouwAntwoordBericht(final Bericht ingaandBericht,
        final BerichtVerwerkingsResultaat resultaat)
    {
        final AbstractAntwoordBericht antwoordBericht = maakAntwoordBerichtVoorIngaanBericht(ingaandBericht);
        antwoordBericht.setParameters(maakParametersVoorAntwoordBericht(ingaandBericht));
        antwoordBericht.setStuurgegevens(maakStuurgegevensVoorUitgaandBericht(ingaandBericht));
        antwoordBericht.setResultaat(bepaalResultaatVoorBerichtVerwerking(ingaandBericht, resultaat));
        antwoordBericht.setMeldingen(bepaalMeldingen(resultaat));
        // copieer de uitgaande administratieveHandeling van de origineel inkomend administratieve handeling.
        
        // het doet er niet toe welk soort AdministratieveHandelingBericht het is, we tonen deze niet meer naar buiten.
        AdministratieveHandelingBericht administratieveHandelingBericht =
                new HandelingErkenningOngeborenVruchtBericht();


        if (ingaandBericht instanceof AbstractBijhoudingsBericht) {
            AdministratieveHandelingBericht admHIngaand = (AdministratieveHandelingBericht)ingaandBericht.getAdministratieveHandeling();
            antwoordBericht.setAdministratieveHandeling(administratieveHandelingBericht);
            administratieveHandelingBericht.setPartijCode(admHIngaand.getPartijCode());
            administratieveHandelingBericht.setTijdstipOntlening(admHIngaand.getTijdstipOntlening());
            BijhoudingResultaat bijhResultaat = (BijhoudingResultaat) resultaat;
            administratieveHandelingBericht.setTijdstipRegistratie(
                    new DatumTijd((bijhResultaat.getTijdstipRegistratie())));

            administratieveHandelingBericht.setGedeblokkeerdeMeldingen(bepaalOverruledMeldingen(resultaat));

            // als het goed is verwerkt en de status is NIET prevalidatie, dan pas de bijgehouden personen
            // en bijgehouden documenten meeleveren.
            if (antwoordBericht.getResultaat() != null
                && antwoordBericht.getResultaat().getVerwerking() == Verwerkingsresultaat.VERWERKING_GESLAAGD)
            {
                administratieveHandelingBericht.setBijgehoudenPersonen(
                        bepaalBijgehoudenPersonen(bijhResultaat.getBijgehoudenPersonen()));
                // TODO, nog uit te zoeken hoe dit bijhouden.
//                administratieveHandelingBericht.setBijgehoudenDocumenten(
//                        bepaalBijgehoudenDocumenten(bijhResultaat.getBijgehoudenDocumenten()));

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

    private List<AdministratieveHandelingBijgehoudenPersoonBericht> bepaalBijgehoudenPersonen(
            final Set<Persoon> bijgehoudenPersonen)
    {
        List<AdministratieveHandelingBijgehoudenPersoonBericht> berichtBijgehoudenPersonen = null;
        if (bijgehoudenPersonen != null && !bijgehoudenPersonen.isEmpty()) {
            berichtBijgehoudenPersonen = new ArrayList<AdministratieveHandelingBijgehoudenPersoonBericht>();
            for (Persoon persoon : bijgehoudenPersonen) {
                final PersoonBericht persoonBericht = new PersoonBericht();
                final AdministratieveHandelingBijgehoudenPersoonBericht bijgehoudenPersoonBericht =
                        new AdministratieveHandelingBijgehoudenPersoonBericht();
                bijgehoudenPersoonBericht.setPersoon(persoonBericht);
                berichtBijgehoudenPersonen.add(bijgehoudenPersoonBericht);

                //Bijgehouden persoon bevat enkel de BSN.
                persoonBericht.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
                persoonBericht.getIdentificatienummers().setBurgerservicenummer(
                        persoon.getIdentificatienummers().getBurgerservicenummer());
            }
        }
        return berichtBijgehoudenPersonen;
    }

    private BerichtParametersGroepBericht maakParametersVoorAntwoordBericht(final Bericht ingaandBericht) {
        if (ingaandBericht.getParameters() != null) {
            final BerichtParametersGroepBericht parameters = new BerichtParametersGroepBericht();
            parameters.setVerwerkingswijze(ingaandBericht.getParameters().getVerwerkingswijze());
            return parameters;
        }
        return null;
    }

    /**
     * Bepaalt de resultaat groep voor het uitgaande bericht op basis van het resultaat uit de business laag.
     *
     * @param ingaandBericht Het ingaande bericht.
     * @param resultaat Het resultaat van de verwerking uit de business laag.
     * @return Bericht resultaat groep.
     */
    private BerichtResultaatGroepBericht bepaalResultaatVoorBerichtVerwerking(final Bericht ingaandBericht,
        final BerichtVerwerkingsResultaat resultaat)
    {
        final BerichtResultaatGroepBericht resultaatGroep = new BerichtResultaatGroepBericht();
        if (ingaandBericht instanceof AbstractBijhoudingsBericht) {
            resultaatGroep.setBijhouding(bepaalResultaatVoorBijhouding(resultaat, ingaandBericht
                    .getParameters().getVerwerkingswijze()));
        }
        resultaatGroep.setHoogsteMeldingsniveau(bepaalHoogsteMeldingNiveau(resultaat.getMeldingen()));

        if (resultaat.getVerwerkingsResultaat()) {
            resultaatGroep.setVerwerking(Verwerkingsresultaat.VERWERKING_GESLAAGD);
        } else {
            resultaatGroep.setVerwerking(Verwerkingsresultaat.VERWERKING_FOUTIEF);
        }
        return resultaatGroep;
    }

    /**
     * Bepaalt het hoogste melding niveau aan de hand van de lijst met meldingen.
     *
     * @param meldingen Lijst van meldingen.
     * @return SoortMelding.
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
     * @param verwerkingswijze Het indicatie prevalidatie vlag.
     * @return Het bijhoudingsresultaat.
     */
    private Bijhoudingsresultaat bepaalResultaatVoorBijhouding(final BerichtVerwerkingsResultaat resultaat,
        final Verwerkingswijze verwerkingswijze)
    {
        final Bijhoudingsresultaat bijhResultaat;

        if (!CollectionUtils.isEmpty(resultaat.getMeldingen()) && verwerkingswijze == Verwerkingswijze.P) {
            int hoogsteOrdinal = -1;
            for (Melding melding : resultaat.getMeldingen()) {
                hoogsteOrdinal = Math.max(hoogsteOrdinal, melding.getSoort().ordinal());
            }
            // BOLIE: sinds kort ook Deblokeerbare fout als soortMelding bijgekomen.
            if (hoogsteOrdinal != -1
                && SoortMelding.values()[hoogsteOrdinal] != SoortMelding.FOUT
                && SoortMelding.values()[hoogsteOrdinal] != SoortMelding.DEBLOKKEERBAAR)
            {
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
    private BerichtStuurgegevensGroepBericht maakStuurgegevensVoorUitgaandBericht(final Bericht ingaandBericht) {
        final BerichtStuurgegevensGroepBericht stuurgegevensGroep = new BerichtStuurgegevensGroepBericht();
        stuurgegevensGroep.setApplicatie(new Applicatienaam("BRP"));
        stuurgegevensGroep.setOrganisatie(new Organisatienaam("Ministerie BZK"));
        stuurgegevensGroep.setReferentienummer(new Sleutelwaardetekst(bepaalReferentieNr()));
        if (ingaandBericht.getStuurgegevens() != null
            && AttribuutTypeUtil.isNotBlank(ingaandBericht.getStuurgegevens().getReferentienummer()))
        {
            stuurgegevensGroep.setCrossReferentienummer(ingaandBericht.getStuurgegevens().getReferentienummer());
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
    private AbstractAntwoordBericht maakAntwoordBerichtVoorIngaanBericht(final Bericht ingaandBericht) {
        final AbstractAntwoordBericht antwoordBericht;
        switch (ingaandBericht.getSoort()) {
            case A_F_S_REGISTREER_GEBOORTE_B:
                antwoordBericht = new InschrijvingGeboorteAntwoordBericht();
                break;
            case H_G_P_REGISTREER_HUWELIJK_B:
                antwoordBericht = new HuwelijkAntwoordBericht();
                break;
            case M_I_G_CORRIGEER_ADRES_B:
                antwoordBericht = new CorrectieAdresNLAntwoordBericht();
                break;
            case M_I_G_REGISTREER_VERHUIZING_B:
                antwoordBericht = new VerhuizingAntwoordBericht();
                break;
            case A_L_G_GEEF_DETAILS_PERSOON_V:
                antwoordBericht = new VraagDetailsPersoonAntwoordBericht();
                break;
            case A_L_G_BEPAAL_KANDIDAAT_VADER_VI:
                antwoordBericht = new VraagKandidaatVaderAntwoordBericht();
                break;
            case A_L_G_GEEF_BEWONERS_OP_ADRES_INCLUSIEF_BETROKKENHEDEN_VI:
                antwoordBericht = new VraagPersonenOpAdresInclusiefBetrokkenhedenAntwoordBericht();
                break;
            case O_V_L_REGISTREER_OVERLIJDEN_B:
                antwoordBericht = new RegistratieOverlijdenAntwoordBericht();
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
    private List<BerichtMeldingBericht> bepaalMeldingen(final BerichtVerwerkingsResultaat berichtResultaat) {
        List<BerichtMeldingBericht> berichtMeldingen;

        if (berichtResultaat == null || berichtResultaat.getMeldingen() == null
            || berichtResultaat.getMeldingen().isEmpty())
        {
            berichtMeldingen = null;
        } else {
            berichtMeldingen = new ArrayList<BerichtMeldingBericht>();
            for (Melding melding : berichtResultaat.getMeldingen()) {
                final MeldingBericht meldingBericht = new MeldingBericht();
                meldingBericht.setSoort(melding.getSoort());
                // TODO: bolie, is dit de goede waarde? anders krijgen we een missing requiredobject
                meldingBericht.setSoort(melding.getSoort());

                //TODO Voorlopig zetten we de regelcode omdat we nog geen Regel enumeraties hebben.
                meldingBericht.setRegel(MeldingUtil.zoekRegeOpviaMeldingCode(melding.getCode()));
                meldingBericht.setMelding(new Meldingtekst(melding.getOmschrijving()));
                meldingBericht.setCommunicatieID(melding.getCommunicatieID());
                //TODO Wat doen we met Attribuut, Element enum is leeg. Moet afgeleid worden uit BMR.
                // staat terdiscussie welk nut dit element heeft (ivm. de hoeveelheid effort om dit correct te vullen).
                meldingBericht.setAttribuut(Element.DUMMY);

                BerichtMeldingBericht berichtMeldingBericht = new BerichtMeldingBericht();
                berichtMeldingBericht.setMelding(meldingBericht);
                berichtMeldingen.add(berichtMeldingBericht);
            }
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
    private List<AdministratieveHandelingGedeblokkeerdeMeldingBericht> bepaalOverruledMeldingen(
            final BerichtVerwerkingsResultaat berichtResultaat)
    {
        return berichtResultaat.getOverruleMeldingen();
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
            for (Betrokkenheid directeBetrokkenheid : RelatieUtils.haalAllePartnerBetrokkenhedenUitPersoon(persoon)) {
                Relatie relatie = directeBetrokkenheid.getRelatie();
                for (Betrokkenheid indirecteBetrokkenheid : relatie.getBetrokkenheden()) {
                    BetrokkenheidModel indirecteBetrokkenheidModel = (BetrokkenheidModel) indirecteBetrokkenheid;
                    if (indirecteBetrokkenheidModel.getID() != null
                        && indirecteBetrokkenheidModel.getID().equals(((BetrokkenheidModel) directeBetrokkenheid).getID()))
                    {
                        teVerwijderenBetr = indirecteBetrokkenheidModel;
                        break;
                    }
                }

                if (teVerwijderenBetr != null) {
                    RelatieModel relatieKopie;
                    if (relatie instanceof Huwelijk) {
                        relatieKopie = new HuwelijkModel((Huwelijk) relatie);
                    } else if (relatie instanceof GeregistreerdPartnerschap) {
                        relatieKopie = new GeregistreerdPartnerschapModel((GeregistreerdPartnerschap) relatie);
                    } else {
                        throw new IllegalArgumentException("Vreemd type relatie gevonden, verwacht worden alleen "
                                                 + "relaties van het type 'Huwelijk' of 'Geregistreerd Partnerschap'.");
                    }

                    for (Betrokkenheid betrokkenheid : relatie.getBetrokkenheden()) {
                        if (betrokkenheid != teVerwijderenBetr) {
                            relatieKopie.getBetrokkenheden().add((BetrokkenheidModel) betrokkenheid);
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

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.handlers.bevraging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.dto.bevraging.AbstractBevragingsBericht;
import nl.bzk.brp.business.dto.bevraging.OpvragenPersoonResultaat;
import nl.bzk.brp.business.dto.bevraging.VraagDetailsPersoonBericht;
import nl.bzk.brp.business.dto.bevraging.VraagKandidaatVaderBericht;
import nl.bzk.brp.business.dto.bevraging.VraagPersonenOpAdresInclusiefBetrokkenhedenBericht;
import nl.bzk.brp.business.dto.bevraging.zoekcriteria.ZoekCriteriaPersoonOpAdres;
import nl.bzk.brp.business.handlers.AbstractBerichtVerwerkingsStap;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.dataaccess.repository.RelatieRepository;
import nl.bzk.brp.dataaccess.selectie.RelatieSelectieFilter;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.Huisletter;
import nl.bzk.brp.model.attribuuttype.Huisnummer;
import nl.bzk.brp.model.attribuuttype.Huisnummertoevoeging;
import nl.bzk.brp.model.attribuuttype.Ja;
import nl.bzk.brp.model.attribuuttype.Postcode;
import nl.bzk.brp.model.objecttype.operationeel.BetrokkenheidModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonAdresModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.RelatieModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Geslachtsaanduiding;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortBetrokkenheid;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Soortmelding;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.util.AttribuutTypeUtil;
import nl.bzk.brp.util.ObjectUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;


/** Uitvoer stap die het opvragen van een persoon afhandelt. De persoon wordt via de DAL laag opgehaald. */
public class OpvragenPersoonBerichtUitvoerStap extends
    AbstractBerichtVerwerkingsStap<AbstractBevragingsBericht, OpvragenPersoonResultaat>
{

    @Inject
    private PersoonRepository persoonRepository;

    @Inject
    private RelatieRepository relatieRepository;

    @Override
    public boolean voerVerwerkingsStapUitVoorBericht(final AbstractBevragingsBericht bericht,
                                                     final BerichtContext context,
                                                     final OpvragenPersoonResultaat resultaat)
    {
        boolean verwerkingsResultaat;

        if (bericht instanceof VraagDetailsPersoonBericht) {
            verwerkingsResultaat = vraagOpDetailPersoon((VraagDetailsPersoonBericht) bericht, resultaat);
        } else if (bericht instanceof VraagPersonenOpAdresInclusiefBetrokkenhedenBericht) {
            verwerkingsResultaat =
                vraagOpPersonenOpAdresInclusiefBetrokkenheden(
                    (VraagPersonenOpAdresInclusiefBetrokkenhedenBericht) bericht, resultaat);
        } else if (bericht instanceof VraagKandidaatVaderBericht) {
            verwerkingsResultaat = vraagOpKandidaatVader((VraagKandidaatVaderBericht) bericht, resultaat);
        } else {
            verwerkingsResultaat = AbstractBerichtVerwerkingsStap.STOP_VERWERKING;
        }

        return verwerkingsResultaat;
    }

    /**
     * Methode om persoon details op te halen.
     *
     * @param bericht het VraagDetailsPersoonBericht.
     * @param resultaat een set met gevonden personen.
     * @return een boolean die aangeeft of de stap succesvol is uitgevoerd (true) of niet (false).
     */
    private boolean vraagOpDetailPersoon(final VraagDetailsPersoonBericht bericht,
        final OpvragenPersoonResultaat resultaat)
    {
        boolean metHistorie = false;
        boolean inclFormeleHistorie = false;

        if (bericht.getVraag().getVraagOpties() != null) {
            if (Ja.Ja == bericht.getVraag().getVraagOpties().getHistorieFormeel()) {
                metHistorie = true;
                inclFormeleHistorie = true;
            } else if (Ja.Ja == bericht.getVraag().getVraagOpties().getHistorieMaterieel()) {
                metHistorie = true;
            }
        }

        final PersoonModel gevondenPersoon =
            persoonRepository.haalPersoonOpMetBurgerservicenummer(bericht.getVraag()
                                                                         .getZoekCriteria().getBurgerservicenummer());

        if (gevondenPersoon != null) {
            if (metHistorie) {
                persoonRepository.vulaanAdresMetHistorie(gevondenPersoon, inclFormeleHistorie);
            }
            resultaat.setGevondenPersonen(new HashSet<PersoonModel>());
            resultaat.getGevondenPersonen().add(gevondenPersoon);
        } else {
            resultaat.voegMeldingToe(new Melding(Soortmelding.INFORMATIE, MeldingCode.ALG0001,
                "Er zijn geen personen gevonden die voldoen aan de opgegeven criteria.",
                bericht.getVraag().getZoekCriteria(), "burgerservicenummer"));
        }
        return AbstractBerichtVerwerkingsStap.DOORGAAN_MET_VERWERKING;
    }



    /**
     * Methode om alle personen op te halen die op het adres zijn ingeschreven op basis van de vraag in het bericht.
     *
     * @param bericht het VraagPersonenOpAdresInclusiefBetrokkenhedenBericht.
     * @param resultaat een set met gevonden personen.
     * @return een boolean die aangeeft of de stap succesvol is uitgevoerd (true) of niet (false).
     */
    private boolean vraagOpPersonenOpAdresInclusiefBetrokkenheden(
        final VraagPersonenOpAdresInclusiefBetrokkenhedenBericht bericht, final OpvragenPersoonResultaat resultaat)
    {
        List<PersoonModel> gevondenPersonen = new ArrayList<PersoonModel>();

        if (isBsnCriteria(bericht)) {
            gevondenPersonen =
                persoonRepository.haalPersonenMetWoonAdresOpViaBurgerservicenummer(bericht
                    .getVraag().getZoekCriteria().getBurgerservicenummer());

            if (CollectionUtils.isNotEmpty(gevondenPersonen)
                && CollectionUtils.isNotEmpty(gevondenPersonen.get(0).getAdressen()))
            {
                // Uitgaande van dat er maar 1 persoon terugkomt met bsn zoek vraag.
                // Uitgaande dat er er maar 1 woon adres aanwezig kan zijn.
                PersoonAdresModel persoonAdres = gevondenPersonen.get(0).getAdressen().iterator().next();
                gevondenPersonen = haalAllePersonenOpMetAdres(persoonAdres);
            }
        } else if (isPostcodeCriteria(bericht)) {
            ZoekCriteriaPersoonOpAdres zoekCriteria = bericht.getVraag().getZoekCriteria();
            gevondenPersonen =
                persoonRepository.haalPersonenOpMetAdresViaPostcodeHuisnummer(
                    new Postcode(zoekCriteria.getPostcode()), new Huisnummer(zoekCriteria.getHuisnummer()),
                    new Huisletter(zoekCriteria.getHuisletter()),
                    new Huisnummertoevoeging(zoekCriteria.getHuisnummertoevoeging()));
        } else if (isGemCodeCriteria(bericht)) {
            // TODO implementeer aanroep naar juiste methode om te zoeken met gemeente adres

            // dummy statement ! voor sonar/findbugs/checktyle
            gevondenPersonen.isEmpty();
        } else {
            // dummy statement ! voor sonar/findbugs/checktyle
            gevondenPersonen.isEmpty();
        }

        // Alle null waardes er uit halen (kunnen voorkomen)
        gevondenPersonen.removeAll(Collections.singletonList(null));

        if (CollectionUtils.isNotEmpty(gevondenPersonen)) {
            verwijderAlleBetrokkeneNietWondendOpZelfdeAdres(gevondenPersonen);
            resultaat.setGevondenPersonen(new HashSet<PersoonModel>(gevondenPersonen));
        } else {
            resultaat.voegMeldingToe(new Melding(Soortmelding.INFORMATIE, MeldingCode.ALG0001,
                "Er zijn geen personen gevonden die voldoen aan de opgegeven criteria.",
                bericht.getVraag().getZoekCriteria(), ""));
        }

        return AbstractBerichtVerwerkingsStap.DOORGAAN_MET_VERWERKING;
    }

    /**
     * test of een persoon (betrokkene) in een list van personen bevindt. Er wordt alleen gekeken naar de persoon.id.
     * We kunnen niet zo maar .contains(object) gebruiker, omdat de lijst is een 'simpel persoon' en de betrokkene
     * een 'referentie persoon' is.
     *
     * @param gevondenPersonen de lijst van personen
     * @param betrokkene de betrokkene
     * @return true als deze in de lijst zit, false anders.
     */
    private boolean isBetrokkeneInGevondenPersonen(final List<PersoonModel> gevondenPersonen,
        final PersoonModel betrokkene)
    {
        boolean resultaat = false;
        for (PersoonModel persoon : gevondenPersonen) {
            if (persoon.getId().equals(betrokkene.getId())) {
                resultaat = true;
                break;
            }
        }
        return resultaat;
    }

    /**
     * Deze methode schoont alle betrokkene van de gevonden personen die niet op dit adres wonen.
     *
     * @param gevondenPersonen de lijst van gevonden personen.
     */
    private void verwijderAlleBetrokkeneNietWondendOpZelfdeAdres(final List<PersoonModel> gevondenPersonen) {
        for (PersoonModel persoon : gevondenPersonen) {
            if (persoon.getBetrokkenheden() != null) {
                for (BetrokkenheidModel betrokkenheid : persoon.getBetrokkenheden()) {
                    RelatieModel relatie = betrokkenheid.getRelatie();
                    // loop door een 'copy' omdat we anders een concurrent probleem hebben tijdens het verwijderen.
                    List<BetrokkenheidModel> copy = new ArrayList<BetrokkenheidModel>(relatie.getBetrokkenheden());
                    for (BetrokkenheidModel bPartner : copy) {
                        if (!isBetrokkeneInGevondenPersonen(gevondenPersonen, bPartner.getBetrokkene())) {
                            relatie.getBetrokkenheden().remove(bPartner);
                        }
                    }
                }
            }
        }

        for (PersoonModel persoon : gevondenPersonen) {
            if (persoon.getBetrokkenheden() != null) {
                // We moeten nu opschonen, elk relatie dat slechts 1 'member' heeft gaat niet goed; want dat is
                // altijd de persoon himself. Verwijder de realtie EN daarmee de betrokkenheid.
                // for some reason, binding gaat fout met alleen 1-member link
                List<BetrokkenheidModel> copy = new ArrayList<BetrokkenheidModel>(persoon.getBetrokkenheden());
                for (BetrokkenheidModel betrokkenheid : copy) {
                    if (betrokkenheid.getRelatie().getBetrokkenheden().size() <= 1) {
                        // relatie met 1 of minder betrkkenheden is geen relatie. verwijder deze (dus ook de
                        // betrokkenheid.
                        persoon.getBetrokkenheden().remove(betrokkenheid);
                    }
                }
            }
        }
    }

    /**
     * Methode om alle personen op te halen die mogelijk de vader zou kunnen zijn .
     *
     * @param bericht het bericht
     * @param resultaat de lijst van personen die potentieel vader kunnen zijn.
     * @return een boolean die aangeeft of de stap succesvol is uitgevoerd (true) of niet (false).
     */
    private boolean vraagOpKandidaatVader(final VraagKandidaatVaderBericht bericht,
        final OpvragenPersoonResultaat resultaat)
    {
        boolean retval = AbstractBerichtVerwerkingsStap.STOP_VERWERKING;

        PersoonModel moeder =
            persoonRepository.findByBurgerservicenummer(bericht.getVraag().getZoekCriteria()
                                                               .getBurgerservicenummer());
        // deze validatie(s) zou eerder moeten gebeuren.
        if (moeder == null) {
            // TODO Foutmelding 'BSN onbekend', moet aangemaakt worden, voorlopg een generieke
            resultaat.voegMeldingToe(new Melding(Soortmelding.FOUT, MeldingCode.ALG0001,
                "BSN is onbekend.", bericht.getVraag().getZoekCriteria(), "burgerservicenummer"));
        } else if (moeder.getGeslachtsaanduiding().getGeslachtsaanduiding() != Geslachtsaanduiding.VROUW) {
            // TODO Foutmelding 'BSN is geen Vrouw', moet aangemaakt worden, voorlopg een generieke
            resultaat.voegMeldingToe(new Melding(Soortmelding.FOUT, MeldingCode.ALG0001,
                "De persoon is niet van het vrouwelijk geslacht.", bericht.getVraag().getZoekCriteria(),
                "burgerservicenummer"));
        } else {
            List<PersoonModel> kandidaatVaders = new ArrayList<PersoonModel>();
            // zoek in de relaties, welke persone hebben een 'huwelijk' relatie met de moeder, rekening houdend
            // met het geslacht van de partner (== man) en dat de relatie geldig is op het moment van de
            // peilDatum (aka. geboorteDatumKind).
            //
            RelatieSelectieFilter filter = new RelatieSelectieFilter();
            filter.setSoortRelaties();
            filter.setSoortBetrokkenheden(SoortBetrokkenheid.PARTNER);
            filter.setUitGeslachtsaanduidingen(Geslachtsaanduiding.MAN);
            filter.setPeilDatum(new Datum(bericht.getVraag().getZoekCriteria().getGeboortedatumKind()));

            List<Integer> persoonIds = relatieRepository.haalopPersoonIdsVanRelatiesVanPersoon(moeder.getId(), filter);
            for (Integer id : persoonIds) {
                kandidaatVaders.add(persoonRepository.haalPersoonMetAdres(id));
            }
            if (!kandidaatVaders.isEmpty()) {
                resultaat.setGevondenPersonen(new HashSet<PersoonModel>(kandidaatVaders));
            } else {
                resultaat.voegMeldingToe(new Melding(Soortmelding.INFORMATIE, MeldingCode.ALG0001,
                    "Kandidaat-vader kan niet worden bepaald.", bericht.getVraag().getZoekCriteria(),
                    "burgerservicenummer"));
            }
            retval = AbstractBerichtVerwerkingsStap.DOORGAAN_MET_VERWERKING;
        }
        return retval;
    }

    /**
     * Algoritme om alle personen op te halen die wonen op een adres.
     * <p/>
     * Zoekmethoden:
     * 1. identificatiecodeNummeraanduiding
     * 2. combinatie NaamOpenbareRuimte, Huisnummer, Huisletter, HuisnummerToevoeging, LocatieOmschrijving,
     * LocatieTOVAdres en Woonplaats gelijk zijn, mits NaamOpenbareRuimte, Huisnummer en Woonplaats gevuld zijn
     * 3. De combinatie Postcode, huisnummer, Huisletter en HuisnummerToevoeging gelijk zijn, mits postcode en huis
     * gevuld zijn.
     * <p/>
     * Wanneer met de ene methode niks gevonden wordt dan wordt de volgende methode uitgeprobeerd.
     *
     * @param persoonAdres PersistentPersoonAdres waarmee gezocht moet worden.
     * @return alle personen die op het adres wonen.
     * @brp.bedrijfsregel BRPUC50121
     */
    private List<PersoonModel> haalAllePersonenOpMetAdres(final PersoonAdresModel persoonAdres) {
        List<PersoonModel> resultaat = new ArrayList<PersoonModel>();
        if (persoonAdres != null) {
            // Zoek verder met de PersistentPersoonAdres
            if (AttribuutTypeUtil.isNotBlank(persoonAdres.getGegevens().getIdentificatiecodeNummeraanduiding())) {
                // Zoeken met IdentificatiecodeNummeraanduiding
                resultaat =
                    persoonRepository.haalPersonenMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(persoonAdres
                        .getGegevens().getIdentificatiecodeNummeraanduiding());

                // Resultaat moet op zijn minst de persoon zelf teruggeven.
                if (resultaat.size() < 2) {
                    if (isZoekbaarMetVolledigAdres(persoonAdres)) {
                        // Zoeken met volledige adres
                        resultaat = persoonRepository.haalPersonenMetWoonAdresOpViaVolledigAdres(
                            persoonAdres.getGegevens().getNaamOpenbareRuimte(),
                            persoonAdres.getGegevens().getHuisnummer(),
                            persoonAdres.getGegevens().getHuisletter(),
                            persoonAdres.getGegevens().getHuisnummertoevoeging(),
                            persoonAdres.getGegevens().getWoonplaats(),
                            persoonAdres.getGegevens().getGemeente());

                        if (resultaat.size() < 2 && isZoekbaarMetOpPostcodeHuisnummer(persoonAdres)) {
                            // Zoeken met postcode huisnummer
                            resultaat = persoonRepository.haalPersonenOpMetAdresViaPostcodeHuisnummer(
                                persoonAdres.getGegevens().getPostcode(),
                                persoonAdres.getGegevens().getHuisnummer(),
                                persoonAdres.getGegevens().getHuisletter(),
                                persoonAdres.getGegevens().getHuisnummertoevoeging());
                        }
                    } else if (isZoekbaarMetOpPostcodeHuisnummer(persoonAdres)) {
                        // Zoeken met postcode huisnummer
                        resultaat = persoonRepository.haalPersonenOpMetAdresViaPostcodeHuisnummer(
                            persoonAdres.getGegevens().getPostcode(),
                            persoonAdres.getGegevens().getHuisnummer(),
                            persoonAdres.getGegevens().getHuisletter(),
                            persoonAdres.getGegevens().getHuisnummertoevoeging());
                    }
                }
            } else if (isZoekbaarMetVolledigAdres(persoonAdres)) {
                // Zoeken met volledige adres
                resultaat = persoonRepository.haalPersonenMetWoonAdresOpViaVolledigAdres(
                    persoonAdres.getGegevens().getNaamOpenbareRuimte(),
                    persoonAdres.getGegevens().getHuisnummer(),
                    persoonAdres.getGegevens().getHuisletter(),
                    persoonAdres.getGegevens().getHuisnummertoevoeging(),
                    persoonAdres.getGegevens().getWoonplaats(),
                    persoonAdres.getGegevens().getGemeente());

                if (resultaat.size() < 2 && isZoekbaarMetOpPostcodeHuisnummer(persoonAdres)) {
                    // Zoeken met postcode huisnummer
                    resultaat = persoonRepository.haalPersonenOpMetAdresViaPostcodeHuisnummer(
                        persoonAdres.getGegevens().getPostcode(),
                        persoonAdres.getGegevens().getHuisnummer(),
                        persoonAdres.getGegevens().getHuisletter(),
                        persoonAdres.getGegevens().getHuisnummertoevoeging());
                }
            } else if (isZoekbaarMetOpPostcodeHuisnummer(persoonAdres)) {
                // Zoeken met postcode huisnummer
                resultaat = persoonRepository.haalPersonenOpMetAdresViaPostcodeHuisnummer(
                    persoonAdres.getGegevens().getPostcode(),
                    persoonAdres.getGegevens().getHuisnummer(),
                    persoonAdres.getGegevens().getHuisletter(),
                    persoonAdres.getGegevens().getHuisnummertoevoeging());
            } else {
                resultaat.add(persoonAdres.getPersoon());
            }
        }

        return resultaat;
    }

    /**
     * Controlleer of het om een bsn zoek criteria gaat.
     *
     * @param bericht het VraagPersonenOpAdresInclusiefBetrokkenhedenBericht
     * @return true als alleen de BSN is ingevuld in het zoek bericht
     */
    private boolean isBsnCriteria(final VraagPersonenOpAdresInclusiefBetrokkenhedenBericht bericht) {
        ZoekCriteriaPersoonOpAdres zoekCriteria = bericht.getVraag().getZoekCriteria();
        return AttribuutTypeUtil.isNotBlank(zoekCriteria.getBurgerservicenummer())
            && ObjectUtil.isAllEmpty(zoekCriteria.getPostcode(), zoekCriteria.getHuisnummer(),
                zoekCriteria.getHuisletter(), zoekCriteria.getHuisnummertoevoeging(),
                zoekCriteria.getNaamOpenbareRuimte(), zoekCriteria.getGemeentecode());
    }

    /**
     * Controlleer of het om een postcode zoek criteria gaat.
     *
     * @param bericht het VraagPersonenOpAdresInclusiefBetrokkenhedenBericht
     * @return true als het gaat om postcode huisnummer zoek criteria gaat
     */
    private boolean isPostcodeCriteria(final VraagPersonenOpAdresInclusiefBetrokkenhedenBericht bericht) {
        ZoekCriteriaPersoonOpAdres zoekCriteria = bericht.getVraag().getZoekCriteria();
        return StringUtils.isNotBlank(zoekCriteria.getPostcode())
            && zoekCriteria.getHuisnummer() != null
            && ObjectUtil.isAllEmpty(zoekCriteria.getBurgerservicenummer(), zoekCriteria.getNaamOpenbareRuimte(),
                zoekCriteria.getGemeentecode());
    }

    /**
     * Controlleer of het om een gemeente code zoek criteria gaat.
     *
     * @param bericht het VraagPersonenOpAdresInclusiefBetrokkenhedenBericht
     * @return true als het gaat om plaats adres huisnummer zoek criteria gaat
     */
    private boolean isGemCodeCriteria(final VraagPersonenOpAdresInclusiefBetrokkenhedenBericht bericht) {
        ZoekCriteriaPersoonOpAdres zoekCriteria = bericht.getVraag().getZoekCriteria();
        return zoekCriteria.getHuisnummer() != null
            && StringUtils.isNotBlank(zoekCriteria.getNaamOpenbareRuimte())
            && StringUtils.isNotBlank(zoekCriteria.getGemeentecode())
            && ObjectUtil.isAllEmpty(zoekCriteria.getBurgerservicenummer(), zoekCriteria.getPostcode());
    }

    /**
     * Bepaalt of zoek opdracht met volledige adres uitgevoerd mag worden. Hier wordt gekeken of NaamOpenbareRuimte,
     * huisnummer en woonplaats is ingevuld.
     *
     * @param persoonAdres adres dat gecontrolleerd moet worden.
     * @return true als de genoemde velden gevuld zijn.
     */
    private boolean isZoekbaarMetVolledigAdres(final PersoonAdresModel persoonAdres) {
        return AttribuutTypeUtil.isNotBlank(persoonAdres.getGegevens().getNaamOpenbareRuimte())
            && persoonAdres.getGegevens().getHuisnummer() != null
            && persoonAdres.getGegevens().getHuisnummer().getWaarde() != null
            && persoonAdres.getGegevens().getWoonplaats() != null;
    }

    /**
     * Bepaalt of zoek opdracht met postcode en huisnummer uitgevoerd mag worden. Hier wordt gekeken naar postcode en
     * huisnummer.
     *
     * @param persoonAdres adres dat gecontrolleerd moet worden.
     * @return true als postcode en huisnummer zijn ingevuld.
     */
    private boolean isZoekbaarMetOpPostcodeHuisnummer(final PersoonAdresModel persoonAdres) {
        return AttribuutTypeUtil.isNotBlank(persoonAdres.getGegevens().getPostcode())
            && AttribuutTypeUtil.isNotBlank(persoonAdres.getGegevens().getHuisnummer());
    }

}

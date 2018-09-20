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

import nl.bzk.brp.business.dto.BRPBericht;
import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.dto.bevraging.OpvragenPersoonResultaat;
import nl.bzk.brp.business.dto.bevraging.VraagDetailsPersoonBericht;
import nl.bzk.brp.business.dto.bevraging.VraagKandidaatVaderBericht;
import nl.bzk.brp.business.dto.bevraging.VraagPersonenOpAdresInclusiefBetrokkenhedenBericht;
import nl.bzk.brp.business.handlers.AbstractBerichtVerwerkingsStap;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.dataaccess.repository.RelatieRepository;
import nl.bzk.brp.dataaccess.selectie.RelatieSelectieFilter;
import nl.bzk.brp.model.gedeeld.GeslachtsAanduiding;
import nl.bzk.brp.model.gedeeld.SoortBetrokkenheid;
import nl.bzk.brp.model.gedeeld.SoortRelatie;
import nl.bzk.brp.model.logisch.Betrokkenheid;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.PersoonAdres;
import nl.bzk.brp.model.logisch.Relatie;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoon;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;
import nl.bzk.brp.util.ObjectUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;


/** Uitvoer stap die het opvragen van een persoon afhandelt. De persoon wordt via de DAL laag opgehaald. */
public class OpvragenPersoonBerichtUitvoerStap extends
        AbstractBerichtVerwerkingsStap<BRPBericht, OpvragenPersoonResultaat>
{

    @Inject
    private PersoonRepository persoonRepository;

    @Inject
    private RelatieRepository relatieRepository;

    @Override
    public boolean voerVerwerkingsStapUitVoorBericht(final BRPBericht bericht, final BerichtContext context,
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
        final Persoon gevondenPersoon =
            persoonRepository.haalPersoonOpMetBurgerservicenummer(bericht.getVraag().getBurgerservicenummer());

        if (gevondenPersoon != null) {
            // TODO: bolie: eigenlijk moet nog getest/gevalideerd worden of deze data voldoet aan de xsd
            // (bv. not null etc.) De reden is dat JIBx dadelijk over zijn nek gaat als het verplicht is
            // deze gevondenPersoon deze waarden niet hebben.

            resultaat.setGevondenPersonen(new HashSet<Persoon>());
            resultaat.getGevondenPersonen().add(gevondenPersoon);
        } else {
            resultaat.voegMeldingToe(new Melding(SoortMelding.INFO, MeldingCode.ALG0001,
                    "Er zijn geen personen gevonden die voldoen aan de opgegeven criteria."));
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
        List<Persoon> gevondenPersonen;

        if (isBsnCriteria(bericht)) {
            gevondenPersonen =
                persoonRepository.haalPersonenMetWoonAdresOpViaBurgerservicenummer(bericht.getVraag()
                        .getBurgerservicenummer());

            if (CollectionUtils.isNotEmpty(gevondenPersonen)
                && CollectionUtils.isNotEmpty(gevondenPersonen.get(0).getAdressen()))
            {
                // Uitgaande van dat er maar 1 persoon terugkomt met bsn zoek vraag.
                // Uitgaande dat er er maar 1 woon adres aanwezig kan zijn.
                PersoonAdres persoonAdres = gevondenPersonen.get(0).getAdressen().iterator().next();
                gevondenPersonen = haalAllePersonenOpMetAdres(persoonAdres);
            }
        } else if (isPostcodeCriteria(bericht)) {
            gevondenPersonen =
                persoonRepository.haalPersonenOpMetAdresViaPostcodeHuisnummer(bericht.getVraag().getPostcode(), bericht
                        .getVraag().getHuisnummer(), bericht.getVraag().getHuisletter(), bericht.getVraag()
                        .getHuisnummertoevoeging());
        } else if (isGemCodeCriteria(bericht)) {
            // TODO implementeer aanroep naar juiste methode om te zoeken met gemeente adres
            gevondenPersonen = new ArrayList<Persoon>();
        } else {
            gevondenPersonen = new ArrayList<Persoon>();
        }

        // Alle null waardes er uit halen (kunnen voorkomen)
        gevondenPersonen.removeAll(Collections.singletonList(null));

        if (CollectionUtils.isNotEmpty(gevondenPersonen)) {
            verwijderAlleBetrokkeneNietWondendOpZelfdeAdres(gevondenPersonen);
            resultaat.setGevondenPersonen(new HashSet<Persoon>(gevondenPersonen));
        } else {
            resultaat.voegMeldingToe(new Melding(SoortMelding.INFO, MeldingCode.ALG0001,
                    "Er zijn geen personen gevonden die voldoen aan de opgegeven criteria."));
        }

        return AbstractBerichtVerwerkingsStap.DOORGAAN_MET_VERWERKING;
    }

    /**
     * test of een persoon (betrokkene) in een list van personen bevindt. Er wordt alleen gekeken naar de persoon.id.
     * We kunnen niet zo maar .contains(object) gebruiker, omdat de lijst is een 'simpel persoon' en de betrokkene
     * een 'referentie persoon' is.
     * @param gevondenPersonen de lijst van personen
     * @param betrokkene de betrokkene
     * @return true als deze in de lijst zit, false anders.
     */
    private boolean isBetrokkeneInGevondenPersonen(final List<Persoon> gevondenPersonen, final Persoon betrokkene) {
        for (Persoon persoon : gevondenPersonen) {
            if (persoon.getId().equals(betrokkene.getId())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Deze methode schoont alle betrokkene van de gevonden personen die niet op dit adres wonen.
     * @param gevondenPersonen de lijst van gevonden personen.
     */
    private void verwijderAlleBetrokkeneNietWondendOpZelfdeAdres(final List<Persoon> gevondenPersonen) {
        for (Persoon persoon : gevondenPersonen) {
            if (persoon.getBetrokkenheden() != null) {
                for (Betrokkenheid betrokkenheid : persoon.getBetrokkenheden()) {
                    Relatie relatie = betrokkenheid.getRelatie();
                    // loop door een 'copy' omdat we anders een concurrent probleem hebben tijdens het verwijderen.
                    List<Betrokkenheid> copy = new ArrayList<Betrokkenheid>(relatie.getBetrokkenheden());
                    for (Betrokkenheid bPartner : copy) {
                        if (!isBetrokkeneInGevondenPersonen(gevondenPersonen, bPartner  .getBetrokkene())) {
                            relatie.getBetrokkenheden().remove(bPartner);
                        }
                    }
                }
            }
        }

        for (Persoon persoon : gevondenPersonen) {
            // We moeten nu opschonen, elk relatie dat slechts 1 'member' heeft gaat niet goed; want dat is
            // altijd de persoon himself. Verwijder de realtie EN daarmee de betrokkenheid.
            // for some reason, binding gaat fout met alleen 1-member link
            List<Betrokkenheid> copy = new ArrayList<Betrokkenheid>(persoon.getBetrokkenheden());
            for (Betrokkenheid betrokkenheid : copy) {
                if (betrokkenheid.getRelatie().getBetrokkenheden().size() <= 1) {
                    // relatie met 1 of minder betrkkenheden is geen relatie. verwijder deze (dus ook de betrokkenheid.
                    persoon.getBetrokkenheden().remove(betrokkenheid);
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

        PersistentPersoon moeder =
            persoonRepository.findByBurgerservicenummer(bericht.getVraag().getBurgerservicenummer());
        // deze validatie(s) zou eerder moeten gebeuren.
        if (moeder == null) {
            // TODO Foutmelding 'BSN onbekend', moet aangemaakt worden, voorlopg een generieke
            resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.ALG0001,
                    "Bsn is onbekend."));
        } else if (moeder.getGeslachtsAanduiding() != GeslachtsAanduiding.VROUW) {
            // TODO Foutmelding 'BSN is geen Vrouw', moet aangemaakt worden, voorlopg een generieke
            resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.ALG0001,
                    "De persoon is niet van het vrouwelijk geslacht."));
        } else {
            List<Persoon> kandidaatVaders = new ArrayList<Persoon>();
            // zoek in de relaties, welke persone hebben een 'huwelijk' relatie met de moeder, rekening houdend
            // met het geslacht van de partner (== man) en dat de relatie geldig is op het moment van de
            // peilDatum (aka. geboorteDatumKind).
            //
            RelatieSelectieFilter filter = new RelatieSelectieFilter();
            filter.setSoortRelaties(SoortRelatie.HUWELIJK);
            filter.setSoortBetrokkenheden(SoortBetrokkenheid.PARTNER);
            filter.setUitGeslachtsAanduidingen(GeslachtsAanduiding.MAN);
            filter.setPeilDatum(bericht.getVraag().getGeboortedatumKind());

            List<Long> persoonIds = relatieRepository.haalopRelatiesVanPersoon(moeder.getId(), filter);
            for (Long id : persoonIds) {
                kandidaatVaders.add(persoonRepository.haalPersoonMetAdres(id));
            }
            if (!kandidaatVaders.isEmpty()) {
                resultaat.setGevondenPersonen(new HashSet<Persoon>(kandidaatVaders));
            } else {
                resultaat.voegMeldingToe(new Melding(SoortMelding.INFO, MeldingCode.ALG0001,
                        "Kandidaat vader kan niet worden bepaald."));
            }
            retval = AbstractBerichtVerwerkingsStap.DOORGAAN_MET_VERWERKING;
        }
        return retval;
    }

    /**
     * Algoritme om alle personen op te halen die wonen op een adres.
     *
     * Zoekmethoden:
     * 1. identificatiecodeNummeraanduiding
     * 2. combinatie NaamOpenbareRuimte, Huisnummer, Huisletter, HuisnummerToevoeging, LocatieOmschrijving,
     * LocatieTOVAdres en Woonplaats gelijk zijn, mits NaamOpenbareRuimte, Huisnummer en Woonplaats gevuld zijn
     * 3. De combinatie Postcode, huisnummer, Huisletter en HuisnummerToevoeging gelijk zijn, mits postcode en huis
     * gevuld zijn.
     *
     * Wanneer met de ene methode niks gevonden wordt dan wordt de volgende methode uitgeprobeerd.
     *
     * @param persoonAdres PersistentPersoonAdres waarmee gezocht moet worden.
     * @return alle personen die op het adres wonen.
     *
     * @brp.bedrijfsregel BRPUC50121
     */
    private List<Persoon> haalAllePersonenOpMetAdres(final PersoonAdres persoonAdres) {
        List<Persoon> resultaat = new ArrayList<Persoon>();
        if (persoonAdres != null) {
            // Zoek verder met de PersistentPersoonAdres
            if (StringUtils.isNotBlank(persoonAdres.getIdentificatiecodeNummeraanduiding())) {
                // Zoeken met IdentificatiecodeNummeraanduiding
                resultaat =
                    persoonRepository.haalPersonenMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(persoonAdres
                            .getIdentificatiecodeNummeraanduiding());

                // Resultaat moet op zijn minst de persoon zelf teruggeven.
                if (resultaat.size() < 2) {
                    if (isZoekbaarMetVolledigAdres(persoonAdres)) {
                        // Zoeken met volledige adres
                        resultaat =
                            persoonRepository.haalPersonenMetWoonAdresOpViaVolledigAdres(
                                    persoonAdres.getNaamOpenbareRuimte(), persoonAdres.getHuisnummer(),
                                    persoonAdres.getHuisletter(), persoonAdres.getHuisnummertoevoeging(),
                                    persoonAdres.getLocatieOmschrijving(), persoonAdres.getLocatieTovAdres(),
                                    persoonAdres.getWoonplaats());

                        if (resultaat.size() < 2 && isZoekbaarMetOpPostcodeHuisnummer(persoonAdres)) {
                            // Zoeken met postcode huisnummer
                            resultaat =
                                persoonRepository.haalPersonenOpMetAdresViaPostcodeHuisnummer(
                                        persoonAdres.getPostcode(), persoonAdres.getHuisnummer(),
                                        persoonAdres.getHuisletter(), persoonAdres.getHuisnummertoevoeging());
                        }
                    } else if (isZoekbaarMetOpPostcodeHuisnummer(persoonAdres)) {
                        // Zoeken met postcode huisnummer
                        resultaat =
                            persoonRepository.haalPersonenOpMetAdresViaPostcodeHuisnummer(persoonAdres.getPostcode(),
                                    persoonAdres.getHuisnummer(), persoonAdres.getHuisletter(),
                                    persoonAdres.getHuisnummertoevoeging());
                    }

                }
            } else if (isZoekbaarMetVolledigAdres(persoonAdres)) {
                // Zoeken met volledige adres
                resultaat =
                    persoonRepository.haalPersonenMetWoonAdresOpViaVolledigAdres(persoonAdres.getNaamOpenbareRuimte(),
                            persoonAdres.getHuisnummer(), persoonAdres.getHuisletter(),
                            persoonAdres.getHuisnummertoevoeging(), persoonAdres.getLocatieOmschrijving(),
                            persoonAdres.getLocatieTovAdres(), persoonAdres.getWoonplaats());

                if (resultaat.size() < 2 && isZoekbaarMetOpPostcodeHuisnummer(persoonAdres)) {
                    // Zoeken met postcode huisnummer
                    resultaat =
                        persoonRepository.haalPersonenOpMetAdresViaPostcodeHuisnummer(persoonAdres.getPostcode(),
                                persoonAdres.getHuisnummer(), persoonAdres.getHuisletter(),
                                persoonAdres.getHuisnummertoevoeging());
                }
            } else if (isZoekbaarMetOpPostcodeHuisnummer(persoonAdres)) {
                // Zoeken met postcode huisnummer
                resultaat =
                    persoonRepository.haalPersonenOpMetAdresViaPostcodeHuisnummer(persoonAdres.getPostcode(),
                            persoonAdres.getHuisnummer(), persoonAdres.getHuisletter(),
                            persoonAdres.getHuisnummertoevoeging());
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
        return StringUtils.isNotBlank(bericht.getVraag().getBurgerservicenummer())
            && ObjectUtil.isAllEmpty(bericht.getVraag().getPostcode(), bericht.getVraag().getHuisnummer(), bericht
                    .getVraag().getHuisletter(), bericht.getVraag().getHuisnummertoevoeging(), bericht.getVraag()
                    .getNaamOpenbareRuimte(), bericht.getVraag().getGemeenteCode());
    }

    /**
     * Controlleer of het om een postcode zoek criteria gaat.
     *
     * @param bericht het VraagPersonenOpAdresInclusiefBetrokkenhedenBericht
     * @return true als het gaat om postcode huisnummer zoek criteria gaat
     */
    private boolean isPostcodeCriteria(final VraagPersonenOpAdresInclusiefBetrokkenhedenBericht bericht) {
        return StringUtils.isNotBlank(bericht.getVraag().getPostcode())
            && StringUtils.isNotBlank(bericht.getVraag().getHuisnummer())
            && ObjectUtil.isAllEmpty(bericht.getVraag().getBurgerservicenummer(), bericht.getVraag()
                    .getNaamOpenbareRuimte(), bericht.getVraag().getGemeenteCode());
    }

    /**
     * Controlleer of het om een gemeente code zoek criteria gaat.
     *
     * @param bericht het VraagPersonenOpAdresInclusiefBetrokkenhedenBericht
     * @return true als het gaat om plaats adres huisnummer zoek criteria gaat
     */
    private boolean isGemCodeCriteria(final VraagPersonenOpAdresInclusiefBetrokkenhedenBericht bericht) {
        return StringUtils.isNotBlank(bericht.getVraag().getHuisnummer())
            && StringUtils.isNotBlank(bericht.getVraag().getNaamOpenbareRuimte())
            && StringUtils.isNotBlank(bericht.getVraag().getGemeenteCode())
            && ObjectUtil.isAllEmpty(bericht.getVraag().getBurgerservicenummer(), bericht.getVraag().getPostcode());
    }

    /**
     * Bepaalt of zoek opdracht met volledige adres uitgevoerd mag worden. Hier wordt gekeken of NaamOpenbareRuimte,
     * huisnummer en woonplaats is ingevuld.
     *
     * @param persoonAdres adres dat gecontrolleerd moet worden.
     * @return true als de genoemde velden gevuld zijn.
     */
    private boolean isZoekbaarMetVolledigAdres(final PersoonAdres persoonAdres) {
        return StringUtils.isNotBlank(persoonAdres.getNaamOpenbareRuimte())
            && StringUtils.isNotBlank(persoonAdres.getHuisnummer()) && persoonAdres.getWoonplaats() != null;
    }

    /**
     * Bepaalt of zoek opdracht met postcode en huisnummer uitgevoerd mag worden. Hier wordt gekeken naar postcode en
     * huisnummer.
     *
     * @param persoonAdres adres dat gecontrolleerd moet worden.
     * @return true als postcode en huisnummer zijn ingevuld.
     */
    private boolean isZoekbaarMetOpPostcodeHuisnummer(final PersoonAdres persoonAdres) {
        return StringUtils.isNotBlank(persoonAdres.getPostcode())
            && StringUtils.isNotBlank(persoonAdres.getHuisnummer());
    }

}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen.bevraging.bijhouding;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.brp.blobifier.exceptie.NietUniekeBsnExceptie;
import nl.bzk.brp.blobifier.exceptie.PersoonNietAanwezigExceptie;
import nl.bzk.brp.blobifier.service.BlobifierService;
import nl.bzk.brp.business.dto.bevraging.BevragingResultaat;
import nl.bzk.brp.business.regels.impl.gegevenset.definitieregels.BRBY0002;
import nl.bzk.brp.business.stappen.Stap;
import nl.bzk.brp.business.stappen.bevraging.AbstractBevragingUitvoerStap;
import nl.bzk.brp.business.stappen.bevraging.BevragingBerichtContextBasis;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.bericht.ber.BerichtZoekcriteriaPersoonGroepBericht;
import nl.bzk.brp.model.bevraging.BevragingsBericht;
import nl.bzk.brp.model.bevraging.bijhouding.BepaalKandidaatVaderBericht;
import nl.bzk.brp.model.bevraging.bijhouding.GeefDetailsPersoonBericht;
import nl.bzk.brp.model.bevraging.bijhouding.GeefPersonenOpAdresMetBetrokkenhedenBericht;
import nl.bzk.brp.model.bevraging.bijhouding.ZoekPersoonBericht;
import nl.bzk.brp.model.hisvolledig.kern.BetrokkenheidHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.BetrokkenheidHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.RelatieHisVolledigView;
import nl.bzk.brp.model.logisch.kern.PersoonAdresStandaardGroep;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAdresModel;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.util.AttribuutUtil;
import nl.bzk.brp.util.ObjectUtil;
import org.apache.commons.collections.CollectionUtils;


/**
 * Uitvoer stap die het opvragen van een persoon afhandelt.
 */
public class BijhoudingBevragingUitvoerStap extends AbstractBevragingUitvoerStap {

    private static final Logger LOGGER                             = LoggerFactory.getLogger();
    private static final String BURGERSERVICENUMMER                = "burgerservicenummer";
    private static final String MEERDERE_PERSONEN_MET_BSN_GEVONDEN = "Meerdere personen met bsn {} gevonden.";
    private static final String PERSOON_MET_BSN_NIET_GEVONDEN      = "Persoon met bsn {} niet gevonden.";

    @Inject
    private PersoonRepository   persoonRepository;
    @Inject
    private BlobifierService    blobifierService;

    /**
     * Methode om alle personen op te halen die op het adres zijn ingeschreven op basis van de vraag in het bericht.
     *
     * @param bericht het VraagPersonenOpAdresInclusiefBetrokkenhedenBericht.
     * @param context de bericht context.
     * @param resultaat een set met gevonden personen. @return een boolean die aangeeft of de stap succesvol is
     *            uitgevoerd (true) of niet (false).
     * @brp.bedrijfsregel VR00054
     * @brp.bedrijfsregel VR00055
     */
    @Regels({ Regel.VR00054, Regel.VR00055 })
    private boolean geefPersonenOpAdresMetBetrokkenheden(final GeefPersonenOpAdresMetBetrokkenhedenBericht bericht,
        final BevragingBerichtContextBasis context, final BevragingResultaat resultaat)
    {
        List<PersoonHisVolledig> gevondenPersonen = new ArrayList<>();

        if (isBsnCriteria(bericht)) {
            PersoonHisVolledig hoofdPersoon = null;
            final BurgerservicenummerAttribuut burgerservicenummer =
                bericht.getZoekcriteriaPersoon().getBurgerservicenummer();
            try {
                hoofdPersoon = blobifierService.leesBlob(burgerservicenummer);
            } catch (final PersoonNietAanwezigExceptie exceptie) {
                LOGGER.debug(PERSOON_MET_BSN_NIET_GEVONDEN, burgerservicenummer);
            } catch (final NietUniekeBsnExceptie nietUniekeBsnExceptie) {
                LOGGER.debug(MEERDERE_PERSONEN_MET_BSN_GEVONDEN, burgerservicenummer);
            }

            if (hoofdPersoon != null && !hoofdPersoon.getAdressen().isEmpty()) {
                // We gaan er van uit dat een persoon slechts een adres heeft.
                final HisPersoonAdresModel adresGroep =
                    hoofdPersoon.getAdressen().iterator().next().getPersoonAdresHistorie().getActueleRecord();
                gevondenPersonen = haalPersonenOpMetId(haalAllePersoonIdsOpMetAdres(adresGroep));

                // Als er niets gevonden is, dan moet in ieder geval
                // de hoofdpersoon zelf (indien aanwezig) terug komen als resultaat.
                if (gevondenPersonen.isEmpty()) {
                    gevondenPersonen.add(hoofdPersoon);
                }

                // Voegt personen toe aan resultaat tbv archivering
                resultaat.getTeArchiverenPersonenIngaandBericht().add(hoofdPersoon.getID());
            }

        } else if (isPostcodeCriteria(bericht)) {
            LOGGER.debug("Zoeken op postcode.");
            final BerichtZoekcriteriaPersoonGroepBericht zoekCriteria = bericht.getZoekcriteriaPersoon();
            gevondenPersonen =
                haalPersonenOpMetId(persoonRepository.haalPersoonIdsOpMetAdresViaPostcodeHuisnummer(
                    zoekCriteria.getPostcode(), zoekCriteria.getHuisnummer(), zoekCriteria.getHuisletter(),
                    zoekCriteria.getHuisnummertoevoeging()));
        } else if (isGemCodeCriteria(bericht)) {
            LOGGER.debug("Zoeken op gemeentecode.");
            // TODO implementeer aanroep naar juiste methode om te zoeken met gemeente adres
        }

        if (CollectionUtils.isNotEmpty(gevondenPersonen)) {
            final List<PersoonHisVolledigView> persoonHisVolledigViews = maakHuidigePersonen(gevondenPersonen);
            bewerkBetrokkenhedenInViews(persoonHisVolledigViews);
            pasViewsAanVoorBetrokkenenNietWonendeOpHetzelfdeAdres(persoonHisVolledigViews);
            bepaalEnZetObjectSleutels(persoonHisVolledigViews, context);
            resultaat.voegGevondenPersonenToe(persoonHisVolledigViews);

            // Voegt personen toe aan resultaat tbv archivering
            resultaat.getTeArchiverenPersonenUitgaandBericht().addAll(
                geefIdentifiersVanPersoonHisVolledigs(gevondenPersonen));
        } else {
            resultaat.voegMeldingToe(new Melding(SoortMelding.INFORMATIE, Regel.ALG0001,
                "Er zijn geen personen gevonden die voldoen aan de opgegeven criteria.", bericht
                .getZoekcriteriaPersoon(), ""));
        }

        return Stap.DOORGAAN;
    }

    /**
     * Geeft de identifiers van persoon his volledigs.
     *
     * @param persoonHisVolledigs the persoon his volledigs
     * @return een lijst met identifiers van personen
     */
    private List<Integer> geefIdentifiersVanPersoonHisVolledigs(final List<PersoonHisVolledig> persoonHisVolledigs) {
        final List<Integer> persoonIds = new ArrayList<>();
        for (final PersoonHisVolledig persoonHisVolledig : persoonHisVolledigs) {
            persoonIds.add(persoonHisVolledig.getID());
        }
        return persoonIds;
    }

    /**
     * Deze methode checkt voor alle betrokkenen van de gevonden personen of die op hetzelfde adres wonen. Indien dit
     * niet zo is dan dient die
     * betrokkenheid niet ge-marshalled te worden en wordt het vlaggetje op false gezet.
     *
     * @param gevondenPersonen de lijst van gevonden personen.
     */
    private void pasViewsAanVoorBetrokkenenNietWonendeOpHetzelfdeAdres(
        final List<PersoonHisVolledigView> gevondenPersonen)
    {
        for (final PersoonHisVolledigView persoon : gevondenPersonen) {
            for (final BetrokkenheidHisVolledigView betrokkenheid : persoon.getBetrokkenheden()) {
                final RelatieHisVolledigView relatie = (RelatieHisVolledigView) betrokkenheid.getRelatie();
                for (final BetrokkenheidHisVolledig betrokkenheidHisVolledig : relatie.getBetrokkenheden()) {
                    final BetrokkenheidHisVolledigView betrView =
                        (BetrokkenheidHisVolledigView) betrokkenheidHisVolledig;
                    if (!isBetrokkeneInGevondenPersonen(gevondenPersonen, betrokkenheidHisVolledig.getPersoon())) {
                        betrView.setZichtbaar(false);
                    }
                }

                // Heeft de relatie nog wel betrokkenheden die zichtbaar zijn? Zo niet schrap ook de relatie en de
                // betrokkenheid waar we vandaan komen. Anders krijg je lege <betrokkenheden> tags.
                boolean relatieHeeftMinstensEenZichtbareBetrokkenheid = false;
                for (final BetrokkenheidHisVolledig betrokkenheidHisVolledig : relatie.getBetrokkenheden()) {
                    final BetrokkenheidHisVolledigView betrView =
                        (BetrokkenheidHisVolledigView) betrokkenheidHisVolledig;
                    if (betrView.isZichtbaar()) {
                        relatieHeeftMinstensEenZichtbareBetrokkenheid = true;
                        break;
                    }
                }

                if (!relatieHeeftMinstensEenZichtbareBetrokkenheid) {
                    relatie.setZichtbaar(false);
                    betrokkenheid.setZichtbaar(false);
                }
            }
        }
    }

    /**
     * test of een persoon (betrokkene) in een list van personen bevindt. Er wordt alleen gekeken naar de persoon.id. We
     * kunnen niet zo maar
     * .contains(object) gebruiker, omdat de lijst is een 'simpel persoon' en de betrokkene een 'referentie persoon' is.
     *
     * @param gevondenPersonen de lijst van personen
     * @param betrokkene de betrokkene
     * @return true als deze in de lijst zit, false anders.
     */
    private boolean isBetrokkeneInGevondenPersonen(final List<PersoonHisVolledigView> gevondenPersonen,
        final PersoonHisVolledig betrokkene)
    {
        boolean resultaat = false;
        for (final PersoonHisVolledig persoon : gevondenPersonen) {
            if (persoon.getID().equals(betrokkene.getID())) {
                resultaat = true;
                break;
            }
        }
        return resultaat;
    }

    /**
     * Methode om alle personen op te halen die mogelijk de vader zou kunnen zijn .
     *
     * @param bericht het bericht
     * @param context de bericht context.
     * @param resultaat de lijst van personen die potentieel vader kunnen zijn. @return een boolean die aangeeft of de
     *            stap succesvol is uitgevoerd (true)
     *            of niet (false).
     */
    private boolean vraagOpKandidaatVader(final BepaalKandidaatVaderBericht bericht,
        final BevragingBerichtContextBasis context, final BevragingResultaat resultaat)
    {
        boolean retval = Stap.STOPPEN;

        PersoonHisVolledig moederVolledig = null;
        final PersoonView moederView;

        final BurgerservicenummerAttribuut bsn = bericht.getZoekcriteriaPersoon().getBurgerservicenummer();

        if (bsn != null) {
            try {
                moederVolledig = blobifierService.leesBlob(bsn);
            } catch (final PersoonNietAanwezigExceptie e) {
                LOGGER.debug(PERSOON_MET_BSN_NIET_GEVONDEN, bsn);
            } catch (final NietUniekeBsnExceptie nietUniekeBsnExceptie) {
                LOGGER.debug(MEERDERE_PERSONEN_MET_BSN_GEVONDEN, bsn);
            }

            if (moederVolledig != null) {
                moederView = new PersoonView(moederVolledig);

                // Voegt personen toe aan resultaat tbv archivering
                resultaat.getTeArchiverenPersonenIngaandBericht().add(moederView.getID());

                if (moederView.getGeslachtsaanduiding().getGeslachtsaanduiding().getWaarde() != Geslachtsaanduiding.VROUW)
                {
                    // TODO Foutmelding 'BSN is geen Vrouw', moet aangemaakt worden, voorlopig een generieke
                    resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT, Regel.ALG0001,
                        "De persoon is niet van het vrouwelijk geslacht.", bericht.getZoekcriteriaPersoon(),
                        BURGERSERVICENUMMER));
                } else {
                    final List<Integer> kandidatenVaderIds =
                        new BRBY0002().bepaalKandidatenVader(moederView, bericht.getZoekcriteriaPersoon()
                            .getGeboortedatumKind());

                    if (kandidatenVaderIds.size() > 0) {
                        final List<PersoonHisVolledigView> persoonHisVolledigViews =
                            maakHuidigePersonen(haalPersonenOpMetId(kandidatenVaderIds));
                        bepaalEnZetObjectSleutels(persoonHisVolledigViews, context);
                        resultaat.voegGevondenPersonenToe(persoonHisVolledigViews);

                        // Voegt personen toe aan resultaat tbv archivering
                        resultaat.getTeArchiverenPersonenUitgaandBericht().addAll(kandidatenVaderIds);
                    } else {
                        resultaat.voegMeldingToe(new Melding(SoortMelding.INFORMATIE, Regel.BRPUC50110,
                            Regel.BRPUC50110.getOmschrijving(), bericht.getZoekcriteriaPersoon(),
                            BURGERSERVICENUMMER));
                    }
                    retval = Stap.DOORGAAN;
                }
            } else {
                // TODO Foutmelding 'BSN onbekend oid', moet aangemaakt worden, voorlopig zelfde als geen resultaat
                resultaat.voegMeldingToe(new Melding(SoortMelding.INFORMATIE, Regel.BRPUC50110, Regel.BRPUC50110
                    .getOmschrijving(), bericht.getZoekcriteriaPersoon(), BURGERSERVICENUMMER));
            }
        }
        return retval;
    }

    /**
     * Algoritme om alle personen op te halen die wonen op een adres.
     * <p/>
     * Zoekmethoden: 1. identificatiecodeNummeraanduiding 2. combinatie NaamOpenbareRuimte, Huisnummer, Huisletter,
     * HuisnummerToevoeging, LocatieOmschrijving, LocatieTOVAdres en Woonplaats gelijk zijn, mits NaamOpenbareRuimte,
     * Huisnummer en Woonplaats gevuld zijn 3. De combinatie Postcode, huisnummer, Huisletter en HuisnummerToevoeging
     * gelijk zijn, mits postcode en huis gevuld zijn.
     * <p/>
     * Wanneer met de ene methode niks gevonden wordt dan wordt de volgende methode uitgeprobeerd.
     *
     * @param adresGroep PersoonAdres waarmee gezocht moet worden.
     * @return alle personen die op het adres wonen.
     * @brp.bedrijfsregel BRPUC50121
     */
    private List<Integer> haalAllePersoonIdsOpMetAdres(final PersoonAdresStandaardGroep adresGroep) {
        List<Integer> resultaat = new ArrayList<>();
        if (AttribuutUtil.isNotBlank(adresGroep.getIdentificatiecodeNummeraanduiding())) {
            // Zoeken met IdentificatiecodeNummeraanduiding
            resultaat =
                persoonRepository.haalPersoonIdsMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(adresGroep
                    .getIdentificatiecodeNummeraanduiding());

            // Resultaat moet op zijn minst de persoon zelf teruggeven.
            if (resultaat.size() < 2) {
                if (isZoekbaarMetVolledigAdres(adresGroep)) {
                    // Zoeken met volledige adres
                    resultaat =
                        persoonRepository.haalPersoonIdsMetWoonAdresOpViaVolledigAdres(
                            adresGroep.getNaamOpenbareRuimte(), adresGroep.getHuisnummer(),
                            adresGroep.getHuisletter(), adresGroep.getHuisnummertoevoeging(),
                            adresGroep.getWoonplaatsnaam(), adresGroep.getGemeente().getWaarde());

                    if (resultaat.size() < 2 && isZoekbaarMetOpPostcodeHuisnummer(adresGroep)) {
                        // Zoeken met postcode huisnummer
                        resultaat =
                            persoonRepository.haalPersoonIdsOpMetAdresViaPostcodeHuisnummer(adresGroep.getPostcode(),
                                adresGroep.getHuisnummer(), adresGroep.getHuisletter(),
                                adresGroep.getHuisnummertoevoeging());
                    }
                } else if (isZoekbaarMetOpPostcodeHuisnummer(adresGroep)) {
                    // Zoeken met postcode huisnummer
                    resultaat =
                        persoonRepository.haalPersoonIdsOpMetAdresViaPostcodeHuisnummer(adresGroep.getPostcode(),
                            adresGroep.getHuisnummer(), adresGroep.getHuisletter(),
                            adresGroep.getHuisnummertoevoeging());
                }
            }
        } else if (isZoekbaarMetVolledigAdres(adresGroep)) {
            // Zoeken met volledige adres
            resultaat =
                persoonRepository.haalPersoonIdsMetWoonAdresOpViaVolledigAdres(adresGroep.getNaamOpenbareRuimte(),
                    adresGroep.getHuisnummer(), adresGroep.getHuisletter(), adresGroep.getHuisnummertoevoeging(),
                    adresGroep.getWoonplaatsnaam(), adresGroep.getGemeente().getWaarde());

            if (resultaat.size() < 2 && isZoekbaarMetOpPostcodeHuisnummer(adresGroep)) {
                // Zoeken met postcode huisnummer
                resultaat =
                    persoonRepository.haalPersoonIdsOpMetAdresViaPostcodeHuisnummer(adresGroep.getPostcode(),
                        adresGroep.getHuisnummer(), adresGroep.getHuisletter(),
                        adresGroep.getHuisnummertoevoeging());
            }
        } else if (isZoekbaarMetOpPostcodeHuisnummer(adresGroep)) {
            // Zoeken met postcode huisnummer
            resultaat =
                persoonRepository.haalPersoonIdsOpMetAdresViaPostcodeHuisnummer(adresGroep.getPostcode(),
                    adresGroep.getHuisnummer(), adresGroep.getHuisletter(), adresGroep.getHuisnummertoevoeging());
        }
        return resultaat;
    }

    /**
     * Controleer of het om een bsn zoek criteria gaat.
     *
     * @param bericht het bericht
     * @return true als alleen de BSN is ingevuld in het zoek bericht
     */
    protected final boolean isBsnCriteria(final BevragingsBericht bericht) {
        boolean isBsnCriteria = false;

        if (bericht instanceof GeefPersonenOpAdresMetBetrokkenhedenBericht) {
            final BerichtZoekcriteriaPersoonGroepBericht zoekCriteria = bericht.getZoekcriteriaPersoon();
            isBsnCriteria =
                AttribuutUtil.isNotBlank(zoekCriteria.getBurgerservicenummer())
                    && ObjectUtil.isAllEmpty(zoekCriteria.getPostcode(), zoekCriteria.getHuisnummer(),
                    zoekCriteria.getHuisletter(), zoekCriteria.getHuisnummertoevoeging(),
                    zoekCriteria.getNaamOpenbareRuimte(), zoekCriteria.getGemeenteCode());
        } else if (bericht instanceof ZoekPersoonBericht) {
            final BerichtZoekcriteriaPersoonGroepBericht zoekCriteria = bericht.getZoekcriteriaPersoon();
            isBsnCriteria =
                AttribuutUtil.isNotBlank(zoekCriteria.getBurgerservicenummer())
                    && ObjectUtil.isAllEmpty(zoekCriteria.getPostcode(), zoekCriteria.getHuisnummer(),
                    zoekCriteria.getHuisletter(), zoekCriteria.getHuisnummertoevoeging(),
                    zoekCriteria.getAdministratienummer(), zoekCriteria.getGeboortedatum(),
                    zoekCriteria.getGeslachtsnaamstam());
        }

        return isBsnCriteria;
    }

    /**
     * Controleer of het om een postcode zoek criteria gaat.
     *
     * @param bericht het VraagPersonenOpAdresInclusiefBetrokkenhedenBericht
     * @return true als het gaat om postcode huisnummer zoek criteria gaat
     */
    protected final boolean isPostcodeCriteria(final GeefPersonenOpAdresMetBetrokkenhedenBericht bericht) {
        final BerichtZoekcriteriaPersoonGroepBericht zoekCriteria = bericht.getZoekcriteriaPersoon();
        return AttribuutUtil.isNotBlank(zoekCriteria.getPostcode())
            && zoekCriteria.getHuisnummer() != null
            && ObjectUtil.isAllEmpty(zoekCriteria.getBurgerservicenummer(), zoekCriteria.getNaamOpenbareRuimte(),
            zoekCriteria.getGemeenteCode());
    }

    /**
     * Controleer of het om een gemeente code zoek criteria gaat.
     *
     * @param bericht het VraagPersonenOpAdresInclusiefBetrokkenhedenBericht
     * @return true als het gaat om plaats adres huisnummer zoek criteria gaat
     */
    protected final boolean isGemCodeCriteria(final GeefPersonenOpAdresMetBetrokkenhedenBericht bericht) {
        final BerichtZoekcriteriaPersoonGroepBericht zoekCriteria = bericht.getZoekcriteriaPersoon();
        return zoekCriteria.getHuisnummer() != null && AttribuutUtil.isNotBlank(zoekCriteria.getNaamOpenbareRuimte())
            && AttribuutUtil.isNotBlank(zoekCriteria.getGemeenteCode())
            && ObjectUtil.isAllEmpty(zoekCriteria.getBurgerservicenummer(), zoekCriteria.getPostcode());
    }

    /**
     * Bepaalt of zoek opdracht met volledige adres uitgevoerd mag worden. Hier wordt gekeken of NaamOpenbareRuimte,
     * huisnummer en woonplaats is ingevuld.
     *
     * @param adresGroep adres dat gecontrolleerd moet worden.
     * @return true als de genoemde velden gevuld zijn.
     */
    protected final boolean isZoekbaarMetVolledigAdres(final PersoonAdresStandaardGroep adresGroep) {
        return AttribuutUtil.isNotBlank(adresGroep.getNaamOpenbareRuimte()) && adresGroep.getHuisnummer() != null
            && adresGroep.getHuisnummer().getWaarde() != null && adresGroep.getWoonplaatsnaam() != null;
    }

    /**
     * Bepaalt of zoek opdracht met postcode en huisnummer uitgevoerd mag worden. Hier wordt gekeken naar postcode en
     * huisnummer.
     *
     * @param adresGroep adres dat gecontrolleerd moet worden.
     * @return true als postcode en huisnummer zijn ingevuld.
     */
    protected final boolean isZoekbaarMetOpPostcodeHuisnummer(final PersoonAdresStandaardGroep adresGroep) {
        return AttribuutUtil.isNotBlank(adresGroep.getPostcode())
            && AttribuutUtil.isNotBlank(adresGroep.getHuisnummer());
    }

    @Override
    public final boolean voerStapUit(final BevragingsBericht bericht, final BevragingBerichtContextBasis context,
        final BevragingResultaat resultaat)
    {
        boolean verwerkingsResultaat;

        if (bericht instanceof GeefDetailsPersoonBericht) {
            verwerkingsResultaat = geefDetailPersoon(bericht, context, resultaat);
        } else if (bericht instanceof GeefPersonenOpAdresMetBetrokkenhedenBericht) {
            verwerkingsResultaat =
                geefPersonenOpAdresMetBetrokkenheden((GeefPersonenOpAdresMetBetrokkenhedenBericht) bericht, context,
                    resultaat);
        } else if (bericht instanceof BepaalKandidaatVaderBericht) {
            verwerkingsResultaat = vraagOpKandidaatVader((BepaalKandidaatVaderBericht) bericht, context, resultaat);
        } else {
            resultaat
                .voegMeldingToe(new Melding(SoortMelding.INFORMATIE, Regel.ALG0001,
                    "Er is geen verwerker gevonden voor het soort bericht: "
                        + bericht.getClass().getSimpleName() + "."));
            verwerkingsResultaat = Stap.STOPPEN;
        }
        return verwerkingsResultaat;
    }

    /**
     * Filtert een lijst van his volledig personen, zodat alleen de huidige informatie overblijft.
     *
     * @param persoonHisVolledigLijst de persoon his volledig lijst
     * @return een view met alleen huidige data
     */
    private List<PersoonHisVolledigView> maakHuidigePersonen(final Iterable<PersoonHisVolledig> persoonHisVolledigLijst)
    {
        final List<PersoonHisVolledigView> huidigePersonen = new ArrayList<>();
        for (final PersoonHisVolledig persoonHisVolledig : persoonHisVolledigLijst) {
            huidigePersonen.add(maakHuidigePersoon(persoonHisVolledig));
        }
        return huidigePersonen;
    }

}

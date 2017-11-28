/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.bericht;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Stapel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.CategorieAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.EffectAfnemerindicaties;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Populatie;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.gba.dataaccess.IstTabelRepository;
import nl.bzk.brp.levering.lo3.conversie.IdentificatienummerMutatie;
import nl.bzk.brp.levering.lo3.conversie.mutatie.MutatieConverteerder;
import nl.bzk.brp.levering.lo3.conversie.persoon.PersoonConverteerder;
import nl.bzk.brp.levering.lo3.conversie.wa11.Wa11Converteerder;
import nl.bzk.brp.levering.lo3.filter.MutatieBerichtFilter;
import nl.bzk.brp.levering.lo3.filter.Ng01BerichtFilter;
import nl.bzk.brp.levering.lo3.filter.ResyncBerichtFilter;
import nl.bzk.brp.levering.lo3.filter.VulBerichtFilter;
import nl.bzk.brp.levering.lo3.filter.Wa11BerichtFilter;
import nl.bzk.brp.levering.lo3.format.Ag01Formatter;
import nl.bzk.brp.levering.lo3.format.Ag11Formatter;
import nl.bzk.brp.levering.lo3.format.Ag21Formatter;
import nl.bzk.brp.levering.lo3.format.Ag31Formatter;
import nl.bzk.brp.levering.lo3.format.Gv01Formatter;
import nl.bzk.brp.levering.lo3.format.Gv02Formatter;
import nl.bzk.brp.levering.lo3.format.Ha01Formatter;
import nl.bzk.brp.levering.lo3.format.Ng01Formatter;
import nl.bzk.brp.levering.lo3.format.Sf01Formatter;
import nl.bzk.brp.levering.lo3.format.Sv01Formatter;
import nl.bzk.brp.levering.lo3.format.Sv11Formatter;
import nl.bzk.brp.levering.lo3.format.Wa11DubbeleInschrijvingMetVerschillendAnummerFormatter;
import nl.bzk.brp.levering.lo3.format.Wa11Formatter;
import nl.bzk.brp.levering.lo3.util.PersoonUtil;
import org.springframework.stereotype.Component;

/**
 * De bericht factory bepaalt welk LO3 bericht verstuurd moet worden voor welk persoon obv van de dienst en soort administratieve handeling.
 */
@Component("lo3BerichtFactory")
@Bedrijfsregel(Regel.R1994)
public final class BerichtFactoryImpl implements BerichtFactory {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger();


    private IstTabelRepository istTabelRepository;

    /* *** Doet conversie naar lo3 (generiek) *** */
    private PersoonConverteerder persoonConverteerder;
    private Wa11Converteerder wa11Converteerder;
    private MutatieConverteerder mutatieConverteerder;

    /* *** Doet filtering (specifiek voor abonnement) *** */
    private ResyncBerichtFilter resyncFilter;
    private VulBerichtFilter vulFilter;
    private MutatieBerichtFilter mutatieFilter;
    private Ng01BerichtFilter ng01Filter;
    private Wa11BerichtFilter wa11Filter;

    /* *** Doet formatting (generiek) *** */
    private Ag01Formatter ag01Formatter;
    private Ag11Formatter ag11Formatter;
    private Ag21Formatter ag21Formatter;
    private Ag31Formatter ag31Formatter;
    private Gv01Formatter gv01Formatter;
    private Gv02Formatter gv02Formatter;
    private Ha01Formatter ha01Formatter;
    private Ng01Formatter ng01Formatter;
    private Sf01Formatter sf01Formatter;
    private Sv01Formatter sv01Formatter;
    private Sv11Formatter sv11Formatter;
    private Wa11Formatter wa11Formatter;
    private Wa11DubbeleInschrijvingMetVerschillendAnummerFormatter wa11DubbeleInschrijvingMetVerschillendAnummerFormatter;

    @Inject
    public void setIstTabelRepository(final IstTabelRepository istTabelRepository) {
        this.istTabelRepository = istTabelRepository;
    }

    @Inject
    public void setPersoonConverteerder(final PersoonConverteerder persoonConverteerder) {
        this.persoonConverteerder = persoonConverteerder;
    }

    @Inject
    public void setWa11Converteerder(final Wa11Converteerder wa11Converteerder) {
        this.wa11Converteerder = wa11Converteerder;
    }

    @Inject
    public void setMutatieConverteerder(final MutatieConverteerder mutatieConverteerder) {
        this.mutatieConverteerder = mutatieConverteerder;
    }

    @Inject
    public void setResyncFilter(final ResyncBerichtFilter resyncFilter) {
        this.resyncFilter = resyncFilter;
    }

    @Inject
    public void setVulFilter(final VulBerichtFilter vulFilter) {
        this.vulFilter = vulFilter;
    }

    @Inject
    public void setMutatieFilter(final MutatieBerichtFilter mutatieFilter) {
        this.mutatieFilter = mutatieFilter;
    }

    @Inject
    public void setNg01Filter(final Ng01BerichtFilter ng01Filter) {
        this.ng01Filter = ng01Filter;
    }

    @Inject
    public void setWa11Filter(final Wa11BerichtFilter wa11Filter) {
        this.wa11Filter = wa11Filter;
    }

    @Inject
    public void setAg01Formatter(final Ag01Formatter ag01Formatter) {
        this.ag01Formatter = ag01Formatter;
    }

    @Inject
    public void setAg11Formatter(final Ag11Formatter ag11Formatter) {
        this.ag11Formatter = ag11Formatter;
    }

    @Inject
    public void setAg21Formatter(final Ag21Formatter ag21Formatter) {
        this.ag21Formatter = ag21Formatter;
    }

    @Inject
    public void setAg31Formatter(final Ag31Formatter ag31Formatter) {
        this.ag31Formatter = ag31Formatter;
    }

    @Inject
    public void setGv01Formatter(final Gv01Formatter gv01Formatter) {
        this.gv01Formatter = gv01Formatter;
    }

    @Inject
    public void setGv02Formatter(final Gv02Formatter gv02Formatter) {
        this.gv02Formatter = gv02Formatter;
    }

    @Inject
    public void setHa01Formatter(final Ha01Formatter ha01Formatter) {
        this.ha01Formatter = ha01Formatter;
    }

    @Inject
    public void setNg01Formatter(final Ng01Formatter ng01Formatter) {
        this.ng01Formatter = ng01Formatter;
    }

    @Inject
    public void setSf01Formatter(final Sf01Formatter sf01Formatter) {
        this.sf01Formatter = sf01Formatter;
    }

    @Inject
    public void setSv01Formatter(final Sv01Formatter sv01Formatter) {
        this.sv01Formatter = sv01Formatter;
    }

    @Inject
    public void setSv11Formatter(final Sv11Formatter sv11Formatter) {
        this.sv11Formatter = sv11Formatter;
    }

    @Inject
    public void setWa11Formatter(final Wa11Formatter wa11Formatter) {
        this.wa11Formatter = wa11Formatter;
    }

    @Inject
    public void setWa11DubbeleInschrijvingMetVerschillendAnummerFormatter(
            final Wa11DubbeleInschrijvingMetVerschillendAnummerFormatter wa11DubbeleInschrijvingMetVerschillendAnummerFormatter) {
        this.wa11DubbeleInschrijvingMetVerschillendAnummerFormatter = wa11DubbeleInschrijvingMetVerschillendAnummerFormatter;
    }

    @Override
    public List<Bericht> maakBerichten(
            final Autorisatiebundel leveringAutorisatie,
            final Map<Persoonslijst, Populatie> populatieMap,
            final AdministratieveHandeling administratieveHandeling,
            final IdentificatienummerMutatie identificatienummerMutatieResultaat) {
        final List<Bericht> resultaat = new ArrayList<>();
        for (final Map.Entry<Persoonslijst, Populatie> populatieEntry : populatieMap.entrySet()) {
            final Persoonslijst persoon = populatieEntry.getKey();

            // IST stapels voor persoon bepalen
            final List<Stapel> istStapels = istTabelRepository.leesIstStapels(persoon.getMetaObject().getObjectsleutel());

            // Per persoon het type bericht bepalen
            final List<Bericht> berichten =
                    bepaalBerichten(persoon, istStapels, leveringAutorisatie, administratieveHandeling, identificatienummerMutatieResultaat);
            if (berichten != null) {
                resultaat.addAll(berichten);
            }
        }

        return resultaat;
    }

    /**
     * Bepaal bericht(en).
     * @param persoon persoon
     * @param istStapels ist stapels
     * @param leveringAutorisatie abonnement
     * @param administratieveHandeling administratieve handeling
     * @param identificatienummerMutatieResultaat identificatienummer mutatie resultaat
     * @return leveringsbericht
     */
    private List<Bericht> bepaalBerichten(
            final Persoonslijst persoon,
            final List<Stapel> istStapels,
            final Autorisatiebundel leveringAutorisatie,
            final AdministratieveHandeling administratieveHandeling,
            final IdentificatienummerMutatie identificatienummerMutatieResultaat) {
        final SoortDienst soortDienst = leveringAutorisatie.getSoortDienst();
        LOGGER.debug("Bepaal berichten voor soort dienst: {}.", soortDienst);

        final List<Bericht> resultaat;

        switch (soortDienst) {
            case ATTENDERING:
                resultaat = bepaalAttenderingsBericht(persoon, istStapels, administratieveHandeling,
                        leveringAutorisatie.getDienst().getEffectAfnemerindicaties());
                break;

            case MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE:
                resultaat = bepaalMutatieBericht(persoon, istStapels, administratieveHandeling, identificatienummerMutatieResultaat);
                break;
            case SELECTIE:
                resultaat = bepaalSelectieBericht(persoon, istStapels);
                break;
            default:
                resultaat = null;
                break;
        }

        return resultaat;
    }

    private List<Bericht> bepaalAttenderingsBericht(
            final Persoonslijst persoon,
            final List<Stapel> istStapels,
            final AdministratieveHandeling administratieveHandeling,
            final EffectAfnemerindicaties effectAfnemerindicaties) {
        final List<Bericht> resultaat;
        if (effectAfnemerindicaties == null) {
            // Ag21 (conditioneel)
            LOGGER.debug("Attendering zonder plaatsen afnemersindicatie -> vul bericht Ag21 (conditioneel)");
            resultaat =
                    Collections.<Bericht>singletonList(
                            new BerichtImpl(
                                    SoortBericht.AG21,
                                    persoonConverteerder,
                                    vulFilter,
                                    ag21Formatter,
                                    persoon,
                                    istStapels,
                                    administratieveHandeling,
                                    null));
        } else {
            // Ag11 (vulbericht nav spontaan plaatsen afnemersindicatie)
            LOGGER.debug("Attendering met plaatsen afnemersindicatie -> vul bericht Ag11");
            resultaat =
                    Collections.<Bericht>singletonList(
                            new BerichtImpl(
                                    SoortBericht.AG11,
                                    persoonConverteerder,
                                    vulFilter,
                                    ag11Formatter,
                                    persoon,
                                    istStapels,
                                    administratieveHandeling,
                                    null));
        }
        return resultaat;
    }

    @Bedrijfsregel(Regel.R1994)
    private List<Bericht> bepaalMutatieBericht(
            final Persoonslijst persoon,
            final List<Stapel> istStapels,
            final AdministratieveHandeling administratieveHandeling,
            final IdentificatienummerMutatie identificatienummerMutatie) {
        final List<Bericht> resultaat = new ArrayList<>();
        if (identificatienummerMutatie != null && identificatienummerMutatie.isAnummerWijziging()) {
            resultaat.add(wa11(persoon, istStapels, administratieveHandeling, identificatienummerMutatie));
        }

        final SoortAdministratieveHandeling soort = administratieveHandeling.getSoort();
        final CategorieAdministratieveHandeling categorie = soort.getCategorie();

        switch (categorie) {
            case CORRECTIE:
                // Ag31 (foutherstel; correcties kunnen in Lo3 niet als mutatie worden geleverd)
                LOGGER.debug("Foutherstel obv afnemersindicatie; categorie correctie");
                resultaat.add(ag31(persoon, istStapels, administratieveHandeling));
                break;
            case ACTUALISERING:
            case GBA_SYNCHRONISATIE:
                final Bericht bericht = bepaalGbaSynchronisatieBericht(soort, persoon, istStapels, administratieveHandeling, identificatienummerMutatie);
                if (bericht != null) {
                    resultaat.add(bericht);
                }
                break;
            default:
        }

        return resultaat;
    }

    private List<Bericht> bepaalSelectieBericht(
            final Persoonslijst persoon,
            final List<Stapel> istStapels) {
        final List<Bericht> resultaat = new ArrayList<>();

        // Sv01 (selectie bericht)
        LOGGER.debug("Selectie bericht Sv01");
        resultaat.add(sv01(persoon, istStapels));

        return resultaat;
    }

    private Bericht bepaalGbaSynchronisatieBericht(
            final SoortAdministratieveHandeling soort,
            final Persoonslijst persoon,
            final List<Stapel> istStapels,
            final AdministratieveHandeling administratieveHandeling,
            final IdentificatienummerMutatie identificatienummerMutatie) {
        Bericht resultaat = null;

        switch (soort) {
            case WIJZIGING_ADRES_INFRASTRUCTUREEL:
            case GBA_INFRASTRUCTURELE_WIJZIGING:
                // Gv02 (infrastructurele wijziging)
                LOGGER.debug("Mutatielevering obv afnemersindicatie; wijziging adres infrastructureel -> mutatie bericht Gv02");
                resultaat = gv02(persoon, istStapels, administratieveHandeling);
                break;

            case GBA_AFVOEREN_PL:
                LOGGER.debug("Mutatielevering obv afnemersindicatie; afvoeren PL");
                resultaat = bepaalAfvoerenBericht(persoon, istStapels, administratieveHandeling, identificatienummerMutatie);
                break;

            case GBA_BIJHOUDING_OVERIG:
                // Ag31 (foutherstel)
                LOGGER.debug("Foutherstel obv afnemersindicatie; gba bijhouding overig");
                resultaat = ag31(persoon, istStapels, administratieveHandeling);
                break;
            default:
                // Gv01 (spontane mutatie)
                LOGGER.debug("Mutatielevering obv afnemersindicatie; overig -> mutatie bericht Gv01");
                resultaat = gv01(persoon, istStapels, administratieveHandeling, identificatienummerMutatie);
                break;
        }

        return resultaat;
    }

    private Bericht bepaalAfvoerenBericht(Persoonslijst persoon, List<Stapel> istStapels, AdministratieveHandeling administratieveHandeling,
                                          IdentificatienummerMutatie identificatienummerMutatie) {
        final Bericht resultaat;
        final String volgendeAnummer = PersoonUtil.getVolgendeAnummer(persoon);
        if (volgendeAnummer != null) {
            final String aNummer = PersoonUtil.getAnummer(persoon);
            if (volgendeAnummer.equals(aNummer)) {
                // Geen bericht
                resultaat = null;
            } else {
                // Wa11 (Afnemers op het 'oude' a-nummer wat wordt afgevoerd, moeten een
                // a-nummerwijzigings bericht krijgen ipv een afvoeren bericht)
                LOGGER.debug("Mutatielevering obv afnemersindicatie; afvoeren PL -> wijziging bericht Wa11");
                resultaat = new BerichtImpl(
                        SoortBericht.WA11,
                        persoonConverteerder,
                        wa11Filter,
                        wa11DubbeleInschrijvingMetVerschillendAnummerFormatter,
                        persoon,
                        istStapels,
                        administratieveHandeling,
                        identificatienummerMutatie);
            }
        } else {
            // Ng01 (Afvoeren PL)
            LOGGER.debug("Mutatielevering obv afnemersindicatie; afvoeren PL -> afvoeren bericht Ng01");
            resultaat = ng01(persoon, istStapels, administratieveHandeling);
        }
        return resultaat;
    }

    private Bericht ag31(final Persoonslijst persoon, final List<Stapel> istStapels, final AdministratieveHandeling administratieveHandeling) {
        return new BerichtImpl(SoortBericht.AG31, persoonConverteerder, resyncFilter, ag31Formatter, persoon, istStapels, administratieveHandeling, null);
    }

    private Bericht gv01(
            final Persoonslijst persoon,
            final List<Stapel> istStapels,
            final AdministratieveHandeling administratieveHandeling,
            final IdentificatienummerMutatie identificatienummerMutatie) {
        return new BerichtImpl(
                SoortBericht.GV01,
                mutatieConverteerder,
                mutatieFilter,
                gv01Formatter,
                persoon,
                istStapels,
                administratieveHandeling,
                identificatienummerMutatie);
    }

    private Bericht gv02(final Persoonslijst persoon, final List<Stapel> istStapels, final AdministratieveHandeling administratieveHandeling) {
        return new BerichtImpl(SoortBericht.GV02, mutatieConverteerder, mutatieFilter, gv02Formatter, persoon, istStapels, administratieveHandeling, null);
    }

    private Bericht ng01(final Persoonslijst persoon, final List<Stapel> istStapels, final AdministratieveHandeling administratieveHandeling) {
        return new BerichtImpl(SoortBericht.NG01, persoonConverteerder, ng01Filter, ng01Formatter, persoon, istStapels, administratieveHandeling, null);
    }

    private Bericht sv01(final Persoonslijst persoon, final List<Stapel> istStapels) {
        return new BerichtImpl(SoortBericht.SV01, persoonConverteerder, vulFilter, sv01Formatter, persoon, istStapels, null, null);
    }

    private Bericht wa11(
            final Persoonslijst persoon,
            final List<Stapel> istStapels,
            final AdministratieveHandeling administratieveHandeling,
            final IdentificatienummerMutatie identificatienummerMutatieResultaat) {
        return new BerichtImpl(
                SoortBericht.WA11,
                wa11Converteerder,
                wa11Filter,
                wa11Formatter,
                persoon,
                istStapels,
                administratieveHandeling,
                identificatienummerMutatieResultaat);
    }

    @Override
    public Bericht maakAg01Bericht(final Persoonslijst persoon) {
        final List<Stapel> istStapels = istTabelRepository.leesIstStapels(persoon.getMetaObject().getObjectsleutel());
        return new BerichtImpl(SoortBericht.AG01, persoonConverteerder, vulFilter, ag01Formatter, persoon, istStapels, null, null);
    }

    @Override
    public Bericht maakAg11Bericht(final Persoonslijst persoon) {
        final List<Stapel> istStapels = istTabelRepository.leesIstStapels(persoon.getMetaObject().getObjectsleutel());
        return new BerichtImpl(SoortBericht.AG11, persoonConverteerder, vulFilter, ag11Formatter, persoon, istStapels, null, null);
    }

    @Override
    public Bericht maakHa01Bericht(final Persoonslijst persoon) {
        final List<Stapel> istStapels = istTabelRepository.leesIstStapels(persoon.getMetaObject().getObjectsleutel());
        return new BerichtImpl(SoortBericht.HA01, persoonConverteerder, vulFilter, ha01Formatter, persoon, istStapels, null, null);
    }

    @Override
    public Bericht maakSf01Bericht(final Persoonslijst persoon) {
        return new BerichtImpl(SoortBericht.SF01, persoonConverteerder, vulFilter, sf01Formatter, persoon, null, null, null);
    }

    @Override
    public Bericht maakSv01Bericht(final Persoonslijst persoon) {
        final List<Stapel> istStapels = istTabelRepository.leesIstStapels(persoon.getMetaObject().getObjectsleutel());
        return new BerichtImpl(SoortBericht.SV01, persoonConverteerder, vulFilter, sv01Formatter, persoon, istStapels, null, null);
    }

    @Override
    public Bericht maakSv11Bericht() {
        return new BerichtImpl(SoortBericht.SV11, persoonConverteerder, vulFilter, sv11Formatter, null, null, null, null);
    }

    @Override
    public Xa01Bericht maakXa01Bericht(final List<Persoonslijst> personen) {
        return new Xa01Bericht(istTabelRepository, persoonConverteerder, vulFilter, personen);
    }

    @Override
    public AdhocWebserviceAntwoord maakAdhocAntwoord(final List<Persoonslijst> personen) {
        return new AdhocWebserviceAntwoord(istTabelRepository, persoonConverteerder, vulFilter, personen);
    }

    @Override
    public OpvragenPLWebserviceAntwoord maakVraagPLAntwoord(final List<Persoonslijst> personen) {
        return new OpvragenPLWebserviceAntwoord(istTabelRepository, persoonConverteerder, personen);
    }
}

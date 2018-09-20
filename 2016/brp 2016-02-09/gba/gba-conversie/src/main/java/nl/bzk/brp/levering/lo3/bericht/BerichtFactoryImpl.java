/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.bericht;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.brp.gba.dataaccess.IstTabelRepository;
import nl.bzk.brp.levering.lo3.conversie.mutatie.MutatieConverteerder;
import nl.bzk.brp.levering.lo3.conversie.persoon.PersoonConverteerder;
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
import nl.bzk.brp.levering.lo3.format.Ng01Formatter;
import nl.bzk.brp.levering.lo3.format.Wa11DubbeleInschrijvingMetVerschillendAnummerFormatter;
import nl.bzk.brp.levering.lo3.format.Wa11Formatter;
import nl.bzk.brp.levering.lo3.util.PersoonUtil;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.levering.model.Populatie;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.EffectAfnemerindicaties;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.CategorieAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.levering.SynchronisatieBericht;
import nl.bzk.brp.model.logisch.ist.Stapel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import org.springframework.stereotype.Component;

/**
 * De bericht factory bepaalt welk LO3 bericht verstuurd moet worden voor welk persoon obv van de dienst en soort
 * administratieve handeling.
 *
 * @brp.bedrijfsregel R1994
 */
@Component("lo3BerichtFactory")
@Regels(Regel.R1994)
public final class BerichtFactoryImpl implements BerichtFactory {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private IstTabelRepository istTabelRepository;

    /* *** Doet conversie naar lo3 (generiek) *** */

    @Inject
    private PersoonConverteerder persoonConverteerder;
    @Inject
    private MutatieConverteerder mutatieConverteerder;

    /* *** Doet filtering (specifiek voor abonnement) *** */

    @Inject
    private ResyncBerichtFilter resyncFilter;
    @Inject
    private VulBerichtFilter vulFilter;
    @Inject
    private MutatieBerichtFilter mutatieFilter;
    @Inject
    private Ng01BerichtFilter ng01Filter;
    @Inject
    private Wa11BerichtFilter wa11Filter;

    /* *** Doet formatting (generiek) *** */

    @Inject
    private Ag01Formatter ag01Formatter;
    @Inject
    private Ag11Formatter ag11Formatter;
    @Inject
    private Ag21Formatter ag21Formatter;
    @Inject
    private Ag31Formatter ag31Formatter;
    @Inject
    private Gv01Formatter gv01Formatter;
    @Inject
    private Gv02Formatter gv02Formatter;
    @Inject
    private Ng01Formatter ng01Formatter;
    @Inject
    private Wa11Formatter wa11Formatter;
    @Inject
    private Wa11DubbeleInschrijvingMetVerschillendAnummerFormatter wa11DubbeleInschrijvingMetVerschillendAnummerFormatter;

    @Override
    public List<SynchronisatieBericht> maakBerichten(
        final List<PersoonHisVolledig> personen,
        final Leveringinformatie leveringAutorisatie,
        final Map<Integer, Populatie> populatieMap,
        final AdministratieveHandelingModel administratieveHandeling)
    {

        final List<SynchronisatieBericht> resultaat = new ArrayList<>();
        for (final PersoonHisVolledig persoon : personen) {
            // IST stapels voor persoon bepalen
            final List<Stapel> istStapels = istTabelRepository.leesIstStapels(persoon);

            // Per persoon het type bericht bepalen
            final List<Bericht> berichten = bepaalBerichten(persoon, istStapels, leveringAutorisatie, administratieveHandeling);
            if (berichten != null) {
                resultaat.addAll(berichten);
            }
        }

        return resultaat;
    }

    /**
     * Bepaal bericht(en).
     *
     * @param persoon
     *            persoon
     * @param istStapels
     *            ist stapels
     * @param leveringAutorisatie
     *            abonnement
     * @param administratieveHandeling
     *            administratieve handeling
     * @return leveringsbericht
     */
    private List<Bericht> bepaalBerichten(
        final PersoonHisVolledig persoon,
        final List<Stapel> istStapels,
        final Leveringinformatie leveringAutorisatie,
        final AdministratieveHandelingModel administratieveHandeling)
    {
        final SoortDienst soortDienst = leveringAutorisatie.getSoortDienst();

        final List<Bericht> resultaat;
        switch (soortDienst) {
            case ATTENDERING:
                final EffectAfnemerindicaties effectAfnemerindicaties = leveringAutorisatie.getDienst().getEffectAfnemerindicaties();
                resultaat = bepaalAttenderingsBericht(persoon, istStapels, administratieveHandeling, effectAfnemerindicaties);
                break;

            case MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE:
                resultaat = bepaalMutatieBericht(persoon, istStapels, administratieveHandeling);
                break;

            default:
                resultaat = null;
                break;
        }

        return resultaat;
    }

    private List<Bericht> bepaalAttenderingsBericht(
        final PersoonHisVolledig persoon,
        final List<Stapel> istStapels,
        final AdministratieveHandelingModel administratieveHandeling,
        final EffectAfnemerindicaties effectAfnemerindicaties)
    {
        final List<Bericht> resultaat;
        if (effectAfnemerindicaties == null) {
            // Ag21 (conditioneel)
            LOGGER.debug("Attendering zonder plaatsen afnemersindicatie -> vul bericht Ag21 (conditioneel)");
            resultaat =
                    Collections.<Bericht>singletonList(
                        new BerichtImpl(SoortBericht.AG21, persoonConverteerder, vulFilter, ag21Formatter, persoon, istStapels, administratieveHandeling));
        } else {
            // Ag11 (vulbericht nav spontaan plaatsen afnemersindicatie)
            LOGGER.debug("Attendering met plaatsen afnemersindicatie -> vul bericht Ag11");
            resultaat =
                    Collections.<Bericht>singletonList(
                        new BerichtImpl(SoortBericht.AG11, persoonConverteerder, vulFilter, ag11Formatter, persoon, istStapels, administratieveHandeling));
        }
        return resultaat;
    }

    @Regels(Regel.R1994)
    private List<Bericht> bepaalMutatieBericht(
        final PersoonHisVolledig persoon,
        final List<Stapel> istStapels,
        final AdministratieveHandelingModel administratieveHandeling)
    {
        final List<Bericht> resultaat;
        final SoortAdministratieveHandeling soort = administratieveHandeling.getSoort().getWaarde();
        final CategorieAdministratieveHandeling categorie = soort.getCategorieAdministratieveHandeling();

        switch (categorie) {
            case CORRECTIE:
                // Ag31 (foutherstel; correcties kunnen in Lo3 niet als mutatie worden geleverd)
                LOGGER.debug("Foutherstel obv afnemersindicatie; categorie correctie");
                resultaat = ag31(persoon, istStapels, administratieveHandeling);
                break;

            case ACTUALISERING:
            case G_B_A_SYNCHRONISATIE:
                switch (soort) {
                    case WIJZIGING_ADRES_INFRASTRUCTUREEL:
                    case G_B_A_INFRASTRUCTURELE_WIJZIGING:
                        // Gv02 (infrastructurele wijziging)
                        LOGGER.debug("Mutatielevering obv afnemersindicatie; wijziging adres infrastructureel -> mutatie bericht Gv02");
                        resultaat = gv02(persoon, istStapels, administratieveHandeling);
                        break;

                    case G_B_A_AFVOEREN_P_L:
                        final Long aNummer = PersoonUtil.getAnummer(persoon);
                        final Long volgendeAnummer = PersoonUtil.getVolgendeAnummer(persoon);
                        if (volgendeAnummer != null) {
                            if (volgendeAnummer.equals(aNummer)) {
                                // Geen bericht
                                resultaat = null;
                                break;
                            } else {
                                // Wa11 (Afnemers op het 'oude' a-nummer wat wordt afgevoerd, moeten een
                                // a-nummerwijzigings
                                // bericht krijgen ipv een afvoeren bericht)
                                resultaat =
                                        Collections.<Bericht>singletonList(
                                            new BerichtImpl(
                                                SoortBericht.WA11,
                                                persoonConverteerder,
                                                wa11Filter,
                                                wa11DubbeleInschrijvingMetVerschillendAnummerFormatter,
                                                persoon,
                                                istStapels,
                                                administratieveHandeling));
                                break;
                            }
                        } else {
                            // Ng01 (Afvoeren PL)
                            resultaat = ng01(persoon, istStapels, administratieveHandeling);

                            break;
                        }

                    case G_B_A_A_NUMMER_WIJZIGING:
                        // Wa11 (Wijzigen A-nummer)
                        resultaat = wa11(persoon, istStapels, administratieveHandeling);
                        break;

                    case G_B_A_BIJHOUDING_OVERIG:
                        // Ag31 (foutherstel)
                        LOGGER.debug("Foutherstel obv afnemersindicatie; gba bijhouding overig");
                        resultaat = ag31(persoon, istStapels, administratieveHandeling);
                        break;
                    default:
                        // Gv01 (spontane mutatie)
                        LOGGER.debug("Mutatielevering obv afnemersindicatie; overig -> mutatie bericht Gv01");
                        resultaat = gv01(persoon, istStapels, administratieveHandeling);
                        break;
                }
                break;

            default:
                resultaat = null;
                break;
        }
        return resultaat;
    }

    private List<Bericht> ag31(
        final PersoonHisVolledig persoon,
        final List<Stapel> istStapels,
        final AdministratieveHandelingModel administratieveHandeling)
    {
        return Collections.<Bericht>singletonList(
            new BerichtImpl(SoortBericht.AG31, persoonConverteerder, resyncFilter, ag31Formatter, persoon, istStapels, administratieveHandeling));
    }

    private List<Bericht> gv01(
        final PersoonHisVolledig persoon,
        final List<Stapel> istStapels,
        final AdministratieveHandelingModel administratieveHandeling)
    {
        return Collections.<Bericht>singletonList(
            new BerichtImpl(SoortBericht.GV01, mutatieConverteerder, mutatieFilter, gv01Formatter, persoon, istStapels, administratieveHandeling));
    }

    private List<Bericht> gv02(
        final PersoonHisVolledig persoon,
        final List<Stapel> istStapels,
        final AdministratieveHandelingModel administratieveHandeling)
    {
        return Collections.<Bericht>singletonList(
            new BerichtImpl(SoortBericht.GV02, mutatieConverteerder, mutatieFilter, gv02Formatter, persoon, istStapels, administratieveHandeling));
    }

    private List<Bericht> ng01(
        final PersoonHisVolledig persoon,
        final List<Stapel> istStapels,
        final AdministratieveHandelingModel administratieveHandeling)
    {
        return Collections.<Bericht>singletonList(
            new BerichtImpl(SoortBericht.NG01, persoonConverteerder, ng01Filter, ng01Formatter, persoon, istStapels, administratieveHandeling));
    }

    private List<Bericht> wa11(
        final PersoonHisVolledig persoon,
        final List<Stapel> istStapels,
        final AdministratieveHandelingModel administratieveHandeling)
    {
        return Collections.<Bericht>singletonList(
            new BerichtImpl(SoortBericht.WA11, persoonConverteerder, wa11Filter, wa11Formatter, persoon, istStapels, administratieveHandeling));
    }

    @Override
    public Bericht maakAg01Bericht(final PersoonHisVolledig persoon) {
        final List<Stapel> istStapels = istTabelRepository.leesIstStapels(persoon);
        return new BerichtImpl(
            SoortBericht.AG01,
            persoonConverteerder,
            vulFilter,
            ag01Formatter,
            persoon,
            istStapels,
            new AdministratieveHandelingModel(new SoortAdministratieveHandelingAttribuut(null), new PartijAttribuut(null), null, null));
    }
}

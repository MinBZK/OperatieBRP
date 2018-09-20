/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.algemeen.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.bzk.brp.levering.algemeen.service.ToekomstigeActieService;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAfgeleidAdministratiefModel;
import org.springframework.stereotype.Service;

/**
 * De implementatie van de Niet Relevante Actie Service bepaalt de relevante acties voor de verschillende situaties bij het opstellen van
 * leveringberichten.
 */
@Service
public class ToekomstigeActieServiceImpl implements ToekomstigeActieService {

    @Override
    public final Set<Long> geefToekomstigeActieIds(final AdministratieveHandelingModel admhnd,
        final PersoonHisVolledig persoonHisVolledig)
    {
        final DatumTijdAttribuut tijdstipLaatsteWijziging = geefTijdstipLaatsteWijzigingVoorAdministratieveHandeling(admhnd, persoonHisVolledig);

        final List<HisPersoonAfgeleidAdministratiefModel> hisPersoonAfgeleidAdministratiefLijst =
            geefGesorteerdeHisPersoonAfgeleidAdministratiefLijst(persoonHisVolledig);

        final List<AdministratieveHandelingModel> toekomstigeHandelingen =
            geefToekomstigeHandelingenNa(hisPersoonAfgeleidAdministratiefLijst, tijdstipLaatsteWijziging);

        return geefActieIdsVanHandelingen(toekomstigeHandelingen);
    }

    @Override
    public final Set<Long> geefToekomstigeActieIdsPlusHuidigeHandeling(final AdministratieveHandelingModel admhnd,
        final PersoonHisVolledig persoonHisVolledig)
    {
        final List<HisPersoonAfgeleidAdministratiefModel> hisPersoonAfgeleidAdministratiefLijst =
            geefGesorteerdeHisPersoonAfgeleidAdministratiefLijst(persoonHisVolledig);

        final AdministratieveHandelingModel admhndEerder = geefAdministratieveHandelingEerderDan(hisPersoonAfgeleidAdministratiefLijst, admhnd);

        if (admhndEerder != null) {
            final DatumTijdAttribuut tijdstipLaatsteWijziging = geefTijdstipLaatsteWijzigingVoorAdministratieveHandeling(admhndEerder, persoonHisVolledig);

            final List<AdministratieveHandelingModel> toekomstigeHandelingen =
                geefToekomstigeHandelingenNa(hisPersoonAfgeleidAdministratiefLijst, tijdstipLaatsteWijziging);

            return geefActieIdsVanHandelingen(toekomstigeHandelingen);
        } else {
            // eerste handeling, dus alle ID;s terug geven

            final List<AdministratieveHandelingModel> alleHandelingen = new ArrayList<>();
            for (final HisPersoonAfgeleidAdministratiefModel afgeleidAdministratiefModel : hisPersoonAfgeleidAdministratiefLijst) {
                alleHandelingen.add(afgeleidAdministratiefModel.getAdministratieveHandeling());
            }

            return geefActieIdsVanHandelingen(alleHandelingen);
        }
    }

    /**
     * Geeft de administratieve handeling die voor de meegegeven administratieve handeling ligt.
     *
     * @param hisPersoonAfgeleidAdministratiefLijst de his persoon afgeleid administratief lijst
     * @param admhnd                                de administratieve handeling waarmee vergeleken wordt.
     * @return de administratieve handeling model die voor de meegegeven administratieve handeling ligt
     */
    private AdministratieveHandelingModel geefAdministratieveHandelingEerderDan(
        final List<HisPersoonAfgeleidAdministratiefModel> hisPersoonAfgeleidAdministratiefLijst,
        final AdministratieveHandelingModel admhnd)
    {
        int indexHuidigeHandeling = 0;
        for (final HisPersoonAfgeleidAdministratiefModel hisPersoonAfgeleidAdministratiefModel : hisPersoonAfgeleidAdministratiefLijst) {
            if (hisPersoonAfgeleidAdministratiefModel.getAdministratieveHandeling().getID().equals(admhnd.getID())) {
                indexHuidigeHandeling = hisPersoonAfgeleidAdministratiefLijst.indexOf(hisPersoonAfgeleidAdministratiefModel);
                break;
            }
        }

        if (indexHuidigeHandeling == 0) {
            return null;
//            throw new IllegalArgumentException("Er is geen eerdere administratieve handeling voor de persoon.");
        }

        final HisPersoonAfgeleidAdministratiefModel eerderHisPersoonAfgeleidAdministratiefModel =
            hisPersoonAfgeleidAdministratiefLijst.get(indexHuidigeHandeling - 1);

        return eerderHisPersoonAfgeleidAdministratiefModel.getAdministratieveHandeling();
    }

    /**
     * Geeft het tijdstip laatste wijziging voor een specifieke administratieve handeling.
     *
     * @param admhnd             de administratieve handeling
     * @param persoonHisVolledig de persoon his volledig
     * @return het tijdstip laatste wijziging als datum tijd attribuut
     */
    private DatumTijdAttribuut geefTijdstipLaatsteWijzigingVoorAdministratieveHandeling(final AdministratieveHandelingModel admhnd,
        final PersoonHisVolledig persoonHisVolledig)
    {
        DatumTijdAttribuut tijdstipLaatsteWijzigingTot = null;
        for (final HisPersoonAfgeleidAdministratiefModel hisPersoonAfgeleidAdministratiefModel
            : persoonHisVolledig.getPersoonAfgeleidAdministratiefHistorie())
        {
            if (hisPersoonAfgeleidAdministratiefModel.getAdministratieveHandeling().getID().equals(admhnd.getID())) {
                tijdstipLaatsteWijzigingTot = hisPersoonAfgeleidAdministratiefModel.getTijdstipLaatsteWijziging();
                break;
            }
        }

        if (tijdstipLaatsteWijzigingTot == null) {
            throw new IllegalStateException("Administratieve handeling komt niet voor in de afgeleid administratief historie van de persoon.\n"
                + "Persoon id: " + persoonHisVolledig.getID() + ", Administratieve handeling id: " + admhnd.getID());
        }

        return tijdstipLaatsteWijzigingTot;
    }

    /**
     * Geeft een gesorteerde his persoon afgeleid administratief lijst.
     *
     * @param persoonHisVolledig the persoon his volledig
     * @return the list
     */
    private List<HisPersoonAfgeleidAdministratiefModel> geefGesorteerdeHisPersoonAfgeleidAdministratiefLijst(final PersoonHisVolledig persoonHisVolledig) {
        final List<HisPersoonAfgeleidAdministratiefModel> hisPersoonAfgeleidAdministratiefLijst = new ArrayList<>();
        for (final HisPersoonAfgeleidAdministratiefModel hisPersoonAfgeleidAdministratief
            : persoonHisVolledig.getPersoonAfgeleidAdministratiefHistorie())
        {
            hisPersoonAfgeleidAdministratiefLijst.add(hisPersoonAfgeleidAdministratief);
        }

        Collections.sort(hisPersoonAfgeleidAdministratiefLijst, new HisPersoonAfgeleidAdministratiefModelComparator());
        return hisPersoonAfgeleidAdministratiefLijst;
    }

    /**
     * Geeft de niet relevante handelingen tot en met een tijdstip laatste wijziging van een bepaalde handeling.
     *
     * @param hisPersoonAfgeleidAdministratiefLijst de gesorteerde lijst van his persoon afgeleid administratief
     * @param tijdstipLaatsteWijzigingTot           de tijdstip laatste wijziging van de laatste relevante administratieve handeling
     * @return de lijst met niet relevante administratievehandelingen
     */
    private List<AdministratieveHandelingModel> geefToekomstigeHandelingenNa(
        final List<HisPersoonAfgeleidAdministratiefModel> hisPersoonAfgeleidAdministratiefLijst,
        final DatumTijdAttribuut tijdstipLaatsteWijzigingTot)
    {
        final List<AdministratieveHandelingModel> toekomstigeHandelingen = new ArrayList<>();
        for (final HisPersoonAfgeleidAdministratiefModel hisPersoonAfgeleidAdministratiefModel : hisPersoonAfgeleidAdministratiefLijst) {
            if (hisPersoonAfgeleidAdministratiefModel.getTijdstipLaatsteWijziging().na(tijdstipLaatsteWijzigingTot)) {
                toekomstigeHandelingen.add(hisPersoonAfgeleidAdministratiefModel.getAdministratieveHandeling());
            }
        }
        return toekomstigeHandelingen;
    }

    /**
     * Geeft de actie ids van alle acties die binnen administratieve handelingen voorkomen.
     *
     * @param administratieveHandelingen de administratieve handelingenhandelingen
     * @return de set met actieIds
     */
    private Set<Long> geefActieIdsVanHandelingen(final List<AdministratieveHandelingModel> administratieveHandelingen) {
        final Set<Long> actieIds = new HashSet<>();
        for (final AdministratieveHandelingModel administratieveHandelingModel : administratieveHandelingen) {
            for (final ActieModel actieModel : administratieveHandelingModel.getActies()) {
                actieIds.add(actieModel.getID());
            }
        }
        return actieIds;
    }
}

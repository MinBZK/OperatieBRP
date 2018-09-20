/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.toegang.gegevensfilter;

import java.util.List;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.basis.FormeelHistorisch;
import nl.bzk.brp.model.basis.FormeelVerantwoordbaar;
import nl.bzk.brp.model.basis.HistorieEntiteit;
import nl.bzk.brp.model.basis.MaterieelHistorisch;
import nl.bzk.brp.model.basis.MaterieelVerantwoordbaar;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import org.springframework.stereotype.Component;


/**
 * De implementatie van het filter om de verantwoordingsinformatie te filteren op relevante verantwoordingsinformatie in mutatie leveringen.
 * <p/>
 * VR00086: In het resultaat van een levering mogen geen verantwoordingsgroepen ‘Actie’ worden opgenomen waarnaar geen enkele verwijzing bestaat vanuit een
 * inhoudelijke groep in hetzelfde resultaat. Dit betekent dat wanneer een Actie alleen groepen heeft geraakt die door autorisatie of een ander
 * filtermechanisme niet in het bericht komen, de Actie dus niets meer 'verantwoordt' en zelf ook uit het bericht verwijderd moet worden.
 * <p/>
 * VR00087: In het resultaat van een levering mogen geen verantwoordingsgroepen ‘Administratieve handeling’ en onderliggende groepen ‘Bron’ en ‘Document’
 * worden opgenomen als er binnen die Administratieve handeling geen enkele Actie voorkomt waarvoor een verwijzing bestaat vanuit een inhoudelijke groep
 * uit hetzelfde resultaat.
 * <p/>
 * VR00095: In een Inhoudelijke groep van een mutatiebericht worden alleen actieverwijzingen opgenomen die samenhangen met de onderhanden Administratieve
 * handeling.
 * <p/>
 * R2015: Alleen bronnen waarnaar daadwerkelijk wordt verwezen meeleveren.
 */
@Regels({ Regel.VR00086, Regel.VR00087, Regel.VR00095 })
@Component
public class MutatieLeveringVerantwoordingsinformatieFilterImpl extends AbstractVerantwoordingsinformatieFilter
    implements MutatieLeveringVerantwoordingsinformatieFilter
{

    @Override
    public final void filter(final PersoonHisVolledigView persoonHisVolledigView, final Long administratieveHandelingId,
        final Leveringinformatie leveringAutorisatie)
    {
        resetPersoonHisVolledigViews(persoonHisVolledigView, leveringAutorisatie);
        zetMagGeleverdWordenVoorActiesInGroepenVoorMutatieLevering(persoonHisVolledigView, administratieveHandelingId);
        final ActieInformatie actieInformatie = bepaalRelevanteActiesInGroepen(persoonHisVolledigView);
        filterActiesInVerantwoording(persoonHisVolledigView, actieInformatie, leveringAutorisatie);
        filterActiesInGroepen(persoonHisVolledigView);
    }

    /**
     * Zet mag geleverd worden-vlaggen voor acties in groepen voor mutatie levering.
     *
     * @param persoonHisVolledigView     persoon his volledig view
     * @param administratieveHandelingId administratieve handeling id
     */
    @SuppressWarnings("unchecked")
    private void zetMagGeleverdWordenVoorActiesInGroepenVoorMutatieLevering(final PersoonHisVolledigView persoonHisVolledigView,
        final Long administratieveHandelingId)
    {
        final List<HistorieEntiteit> totaleLijstVanHisElementenOpPersoonsLijst = persoonHisVolledigView.getTotaleLijstVanHisElementenOpPersoonsLijst();

        for (final HistorieEntiteit historieEntiteit : totaleLijstVanHisElementenOpPersoonsLijst) {
            if (historieEntiteit instanceof MaterieelHistorisch) {
                final MaterieelHistorisch materieleHistorie = (MaterieelHistorisch) historieEntiteit;
                final MaterieelVerantwoordbaar<ActieModel> materieelVerantwoordbaar = (MaterieelVerantwoordbaar<ActieModel>) materieleHistorie;
                zetMagGeleverdWordenVoorVerantwoordingsVeld(materieelVerantwoordbaar.getVerantwoordingInhoud(), administratieveHandelingId);
                zetMagGeleverdWordenVoorVerantwoordingsVeld(materieelVerantwoordbaar.getVerantwoordingAanpassingGeldigheid(), administratieveHandelingId);
                zetMagGeleverdWordenVoorVerantwoordingsVeld(materieelVerantwoordbaar.getVerantwoordingVerval(), administratieveHandelingId);
            } else {
                final FormeelHistorisch formeleHistorie = (FormeelHistorisch) historieEntiteit;
                final FormeelVerantwoordbaar<ActieModel> formeelVerantwoordbaar = (FormeelVerantwoordbaar<ActieModel>) formeleHistorie;
                zetMagGeleverdWordenVoorVerantwoordingsVeld(formeelVerantwoordbaar.getVerantwoordingInhoud(), administratieveHandelingId);
                zetMagGeleverdWordenVoorVerantwoordingsVeld(formeelVerantwoordbaar.getVerantwoordingVerval(), administratieveHandelingId);
            }
        }
    }

    /**
     * Zet mag geleverd worden-vlag voor verantwoordingsveld.
     *
     * @param actie                      actie
     * @param administratieveHandelingId administratieve handeling id
     */
    private void zetMagGeleverdWordenVoorVerantwoordingsVeld(final ActieModel actie, final Long administratieveHandelingId) {
        if (actie != null) {
            if (isOnderdeelVanHandeling(actie, administratieveHandelingId)) {
                zetActieMagGeleverdWorden(actie);
            } else {
                actie.setMagGeleverdWorden(false);
            }
        }
    }

    /**
     * Is onderdeel van handeling.
     *
     * @param actie                      actie
     * @param administratieveHandelingId administratieve handeling id
     * @return the boolean
     */
    private boolean isOnderdeelVanHandeling(final ActieModel actie, final Long administratieveHandelingId) {
        return administratieveHandelingId.equals(actie.getAdministratieveHandeling().getID());
    }
}

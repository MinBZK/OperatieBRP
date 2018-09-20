/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaat;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.FormeelVerantwoordbaar;
import nl.bzk.brp.model.basis.MaterieelVerantwoordbaar;
import nl.bzk.brp.model.basis.VerantwoordingTbvLeveringMutaties;
import nl.bzk.brp.model.operationeel.autaut.HisPersoonAfnemerindicatieModel;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import org.apache.commons.collections.Predicate;

/**
 * Predikaat voor het bepalen welke historie records van een groep geraakt zijn door een bepaalde administratieve
 * handeling.
 *
 * VR00079 Een mutatielevering bevat slechts groepen die in de administratieve handeling geraakt zijn plus de
 * identificerende groepen.
 *
 * Bij een Mutatielevering worden in het resultaat alleen groepen opgenomen die voldoen aan minstens één van de volgende
 * voorwaarden:
 * - ActieInhoud hoort bij de Administratieve handelingen en (ActieAanpassingGeldigheid is leeg )
 * - ActieAanpassingGeldigheid hoort bij de Administratieve handeling
 * - ActieVerval hoort bij de Admninistratieve handeling
 * - Het betreft een Identificerende groep
 *
 *(Toelichting op het eerste streepje: dit is om te voorkomen dat eventuele groepen uit latere handelingen waarbij
 * ActieInhoud slecht is doorgekopieerd ook worden geselecteerd)
 *
 * @brp.bedrijfsregel VR00079
 */
@Regels(Regel.VR00079)
public class AdministratieveHandelingDeltaPredikaat implements Predicate {

    private final Long administratieveHandelingId;

    /**
     * Instantieert een nieuwe Tijdstip registratie delta predikaat.
     *
     * @param administratieveHandelingId de id van de handeling op basis waarvan je de delta bepaling wil doen.
     */
    public AdministratieveHandelingDeltaPredikaat(final Long administratieveHandelingId) {
        this.administratieveHandelingId = administratieveHandelingId;
    }

    @Override
    public final boolean evaluate(final Object object) {
        boolean resultaat = false;
        if (object instanceof FormeelVerantwoordbaar) {
            if (verantwoordingLooptViaActies((FormeelVerantwoordbaar) object)) {
                final FormeelVerantwoordbaar<ActieModel> formeelVerantwoordbaar = (FormeelVerantwoordbaar) object;
                resultaat = actieInhoudIsOnderdeelVanHandelingEnActieGeldigheidIsLeeg(formeelVerantwoordbaar)
                        || actieGeldigheidIsOnderdeelVanHandeling(formeelVerantwoordbaar)
                        || actieVervalIsOnderdeelVanHandeling(formeelVerantwoordbaar);
            } else if (object instanceof HisPersoonAfnemerindicatieModel) {
                // Afnemerindicaties zijn een uitzondering want deze worden verantwoord door diensten en niet door acties.
                // Aangezien we deze nooit leveren, retourneren we false.
                resultaat = false;
            } else {
                throw new IllegalArgumentException("Delta predikaat kan alleen ge-evalueerd worden voor groep "
                        + "voorkomens die verantwoord worden door acties of diensten.");
            }
        }
        return resultaat;
    }

    /**
     * Controleert of de verantwoording via acties loopt.
     *
     * @param object Het object.
     * @return True als het om actie-verantwoording gaat, anders false.
     */
    private boolean verantwoordingLooptViaActies(final FormeelVerantwoordbaar object) {
        return object.getVerantwoordingInhoud() instanceof ActieModel;
    }

    /**
     * Controleert of de actieverval onderdeel is van de handeling.
     *
     * @param formeelVerantwoordbaar Het record.
     * @return True als het record gekoppeld is via de actie verval aan de handeling.
     */
    private boolean actieVervalIsOnderdeelVanHandeling(final FormeelVerantwoordbaar<ActieModel> formeelVerantwoordbaar) {
        final ActieModel actieModel;
        if (formeelVerantwoordbaar instanceof VerantwoordingTbvLeveringMutaties) {
            actieModel = geefMutLevActieVervalOfVerantwoordingVerval((VerantwoordingTbvLeveringMutaties) formeelVerantwoordbaar);
        } else {
            actieModel = formeelVerantwoordbaar.getVerantwoordingVerval();
        }
        return actieIsGekoppeldAanAdministratieHandeling(actieModel);
    }

    private ActieModel geefMutLevActieVervalOfVerantwoordingVerval(final VerantwoordingTbvLeveringMutaties verantwoordingTbvLeveringMutaties) {
        final ActieModel actieModel;
        if (verantwoordingTbvLeveringMutaties.getVerantwoordingVervalTbvLeveringMutaties() != null)
        {
            actieModel = verantwoordingTbvLeveringMutaties.getVerantwoordingVervalTbvLeveringMutaties();
        } else {
            actieModel = verantwoordingTbvLeveringMutaties.getVerantwoordingVerval();
        }
        return actieModel;
    }

    /**
     * Controleert of de actie geldigheid onderdeel is van de handeling.
     *
     * @param formeelVerantwoordbaar Het record.
     * @return True als het record gekoppeld is via de actie geldigheid aan de handeling.
     */
    private boolean actieGeldigheidIsOnderdeelVanHandeling(final FormeelVerantwoordbaar<ActieModel> formeelVerantwoordbaar) {
        final boolean isOnderdeelVanHandeling;
        if (formeelVerantwoordbaar instanceof MaterieelVerantwoordbaar) {
            final MaterieelVerantwoordbaar<ActieModel> materieelVerantwoordbaar = (MaterieelVerantwoordbaar<ActieModel>) formeelVerantwoordbaar;
            isOnderdeelVanHandeling = actieIsGekoppeldAanAdministratieHandeling(materieelVerantwoordbaar.getVerantwoordingAanpassingGeldigheid());
        } else {
            isOnderdeelVanHandeling = false;
        }
        return isOnderdeelVanHandeling;
    }

    /**
     * Controleert of de actie inhoud onderdeel is van de handeling.
     *
     * @param formeelVerantwoordbaar Het record.
     * @return True als het record gekoppeld is via de actie inhoud aan de handeling.
     */
    private boolean actieInhoudIsOnderdeelVanHandelingEnActieGeldigheidIsLeeg(final FormeelVerantwoordbaar<ActieModel> formeelVerantwoordbaar) {
        final boolean actieInhoudIsOnderdeelVanHandeling = actieIsGekoppeldAanAdministratieHandeling(formeelVerantwoordbaar.getVerantwoordingInhoud());

        final boolean actieAanpassingGeldigheidIsLeeg;
        if (formeelVerantwoordbaar instanceof MaterieelVerantwoordbaar) {
            final MaterieelVerantwoordbaar<ActieModel> materieelVerantwoordbaar = (MaterieelVerantwoordbaar<ActieModel>) formeelVerantwoordbaar;
            actieAanpassingGeldigheidIsLeeg = materieelVerantwoordbaar.getVerantwoordingAanpassingGeldigheid() == null;
        } else {
            actieAanpassingGeldigheidIsLeeg = true;
        }

        return actieInhoudIsOnderdeelVanHandeling && actieAanpassingGeldigheidIsLeeg;
    }

    /**
     * Controleert of de actie gekoppeld is aan de administratieve handeling waarmee dit predikaat is geinstantieerd.
     *
     * @param actie De actie.
     * @return True als de actie gekoppeld is aan de administratieve handeling, anders false.
     */
    private boolean actieIsGekoppeldAanAdministratieHandeling(final ActieModel actie) {
        return actie != null && actie.getAdministratieveHandeling().getID().equals(administratieveHandelingId);
    }

}

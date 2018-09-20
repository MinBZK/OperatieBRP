/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.stappen.bijhouding;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.bijhouding.business.stappen.context.BijhoudingBerichtContext;
import nl.bzk.brp.model.HisVolledigRootObject;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Verwerkingswijze;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.logisch.ber.BerichtBasis;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.Betrokkenheid;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.logisch.kern.Relatie;
import nl.bzk.brp.model.operationeel.kern.HisPersoonBijhoudingModel;

/**
 * Utility/Helper klasse met helper methodes voor de bericht verwerking.
 */
public final class BerichtUtils {

    /**
     * private constructor.
     */
    private BerichtUtils() {
    }

    /**
     * Stel samen de lijst van alle personen die gerefereerd wordt binnen een actie. Deze methode gaat vanuit dat de actie een Relatie-georienteerde actie
     * gaat, waarin meerdere personen benoemd zijn.
     *
     * @param actie de actie
     * @return de gevonden personen.
     */
    public static List<Persoon> bepaalPersonenUitActie(final Actie actie) {
        final List<Persoon> personen = new ArrayList<>();
        // Het root object kan een persoon of relatie zijn.
        // in geval van persoon, hebben we de hoofdpersoon.
        // in geval van relatie, haal alle personen uit die relatie en
        final RootObject hoofdRootObject = actie.getRootObject();
        if (hoofdRootObject instanceof Persoon) {
            personen.add((Persoon) hoofdRootObject);
        } else if (hoofdRootObject instanceof Relatie) {
            final Relatie relatie = (Relatie) hoofdRootObject;
            if (relatie.getSoort().getWaarde() == SoortRelatie.FAMILIERECHTELIJKE_BETREKKING) {
                final Betrokkenheid kind = ((FamilierechtelijkeBetrekkingBericht) relatie).getKindBetrokkenheid();
                if (kind != null && kind.getPersoon() != null && !personen.contains(kind.getPersoon())) {
                    personen.add(kind.getPersoon());
                }
            }
        }
        return personen;
    }

    /**
     * Test of in het bericht de prevalidatie aan staat.
     *
     * @param bericht het bericht
     * @return true als deze op 'J' staat, false anders
     */
    public static boolean isBerichtPrevalidatieAan(final BerichtBasis bericht) {
        if (bericht != null && bericht.getParameters() != null
            && bericht.getParameters().getVerwerkingswijze() != null)
        {
            final Verwerkingswijze verwerkingsWijze = bericht.getParameters().getVerwerkingswijze().getWaarde();

            if (verwerkingsWijze == Verwerkingswijze.PREVALIDATIE) {
                return true;
            }
        }
        return false;
    }

    /**
     * TODO POC Specifiek
     *
     * Test of in het bericht een BRP of ISC registreer huwelijk geregistreerd partnerschap is en de hoofdpersoon een GBA bijhouder heeft of een bijhouder
     * met autofiat uit.
     *
     * @param bericht        het bericht
     * @param berichtContext de berichtContext
     * @return true als het een registreer huwwelijk geregistreerd partnerschap bericht is en er een persoon waarvoor autofiat niet aan staat.
     */
    public static boolean isHuwelijksberichtMetHandmatigFiatterendePartners(final BerichtBasis bericht, final BijhoudingBerichtContext
        berichtContext)
    {
        if (bericht.getSoort().getWaarde().equals(SoortBericht.BHG_HGP_REGISTREER_HUWELIJK_GEREGISTREERD_PARTNERSCHAP)
            || bericht.getSoort().getWaarde().equals(SoortBericht.ISC_MIG_REGISTREER_HUWELIJK_GEREGISTREERD_PARTNERSCHAP))
        {
            for (final HisVolledigRootObject hisVolledigRootObject : berichtContext.getBestaandeBijhoudingsRootObjecten().values()) {
                if (hisVolledigRootObject instanceof PersoonHisVolledigImpl
                    && heeftHandmatigFiatterendeBijhouder((PersoonHisVolledigImpl) hisVolledigRootObject))
                {
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean heeftHandmatigFiatterendeBijhouder(final PersoonHisVolledigImpl persoonHisVolledigImpl) {
        final HisPersoonBijhoudingModel actueleBijhouding = persoonHisVolledigImpl.getPersoonBijhoudingHistorie().getActueleRecord();
        if (actueleBijhouding != null) {
            final JaNeeAttribuut indicatieAutomatischFiatteren = actueleBijhouding.getBijhoudingspartij().getWaarde().getIndicatieAutomatischFiatteren();
            return indicatieAutomatischFiatteren == null || !indicatieAutomatischFiatteren.getWaarde();
        }
        return false;
    }
}

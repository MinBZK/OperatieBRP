/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.service;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.Verwerkingswijze;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.bericht.ber.basis.AbstractBerichtBericht;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.Betrokkenheid;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.logisch.kern.Relatie;
import org.apache.commons.collections.CollectionUtils;

public final class BerichtUtils {

    /**
     * private constructor.
     */
    private BerichtUtils() {
    }

    public static void haalHoofpersoonUitBericht() {

    }

    /**
     * Stel samen de lijst van alle personen die gerefereerd wordt binnen een actie. Deze methode gaat vanuit dat de
     * actie een Relatie-georienteerde actie gaat, waarin meerdere personen benoemd zijn.
     *
     * @param actie de actie
     * @return de gevonden personen.
     */
    public static List<Persoon> bepaalPersonenUitActie(final Actie actie) {
        List<Persoon> personen = new ArrayList<Persoon>();
        if (CollectionUtils.isNotEmpty(actie.getRootObjecten())) {
            // gaat vanuit dat slechts 1 rootObject heeft. Deze kan een persoon of relatie zijn.
            // in geval van persoon, hebben we de hoofdpersoon.
            // in geval van relatie, haal alle personen uit die relatie en
            RootObject hoofdRootObject = actie.getRootObjecten().get(0);
            if (hoofdRootObject instanceof Persoon) {
                personen.add((Persoon) hoofdRootObject);
            } else if (hoofdRootObject instanceof Relatie) {
                Relatie relatie = (Relatie) hoofdRootObject;
                if (relatie.getSoort() == SoortRelatie.FAMILIERECHTELIJKE_BETREKKING) {
                    Betrokkenheid kind = ((FamilierechtelijkeBetrekkingBericht) relatie).getKindBetrokkenheid();
                    if (null != kind && null != kind.getPersoon() && !personen.contains(kind.getPersoon())) {
                        personen.add(kind.getPersoon());
                    }
                }
            }
        } else {
            // TODO: weer aanzetten met goede fout logging.
//            LOGGER.error("Geen rootobjecten gevonden in actie. Bedrijfsregel {} daarom niet verder uitgevoerd en geen "
//                + "meldingen toegevoegd.", getOmschrijving());
        }
        return personen;

    }

    /**
     * Test of in het bericht de prevalidatie aan staat.
     * @param bericht het bericht
     * @return true als deze op 'J' staat, false anders
     */
    public static boolean isBerichtPrevalidatieAan(final AbstractBerichtBericht bericht) {
        if (null != bericht && null != bericht.getParameters() && null != bericht.getParameters().getVerwerkingswijze())
        {
            Verwerkingswijze verwerkingsWijze = bericht.getParameters().getVerwerkingswijze();

            if (verwerkingsWijze == Verwerkingswijze.P) {
                return true;
            }
        }
        return false;
    }
}

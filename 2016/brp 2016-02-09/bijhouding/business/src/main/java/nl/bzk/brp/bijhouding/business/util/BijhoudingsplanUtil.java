/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.util;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.bijhouding.business.stappen.context.BijhoudingBerichtContext;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.BerichtdataLaxXSDAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Bijhoudingssituatie;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.BijhoudingssituatieAttribuut;
import nl.bzk.brp.model.bericht.ber.BijhoudingsplanBericht;
import nl.bzk.brp.model.bericht.ber.BijhoudingsplanPersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAfgeleidAdministratiefGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBijhoudingGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;

/**
 * Utiliteitsklasse voor bijhoudingsplan.
 */
public class BijhoudingsplanUtil {

    private BijhoudingsplanUtil() {}

    /**
     * Maakt een {@link BijhoudingsplanBericht} op basis van een {@link BijhoudingBerichtContext}.
     * @param context de {@link BijhoudingBerichtContext}
     * @return het {@link BijhoudingsplanPersoonBericht}
     */
    public static BijhoudingsplanBericht maakBijhoudingsplan(final BijhoudingBerichtContext context) {
        final BijhoudingsplanBericht bijhoudingsplan = new BijhoudingsplanBericht();
        bijhoudingsplan.setPartijBijhoudingsvoorstelCode(context.getPartij().getWaarde().getCode().toString());
        bijhoudingsplan.setBericht(new BerichtdataLaxXSDAttribuut("<bijhoudingsvoorstelBerichtInhoud/>"));
        bijhoudingsplan.setBerichtResultaat(new BerichtdataLaxXSDAttribuut("<bijhoudingsvoorstelBerichtResultaatInhoud/>"));
        final List<BijhoudingsplanPersoonBericht> bijhoudingsplanPersonen = new ArrayList<>();
        final BijhoudingsplanPersoonBericht bijhoudingsplanPersoonBericht = new BijhoudingsplanPersoonBericht();

        bijhoudingsplanPersoonBericht.setPersoon(maakPersoon(context));
        bijhoudingsplanPersoonBericht.setSituatie(new BijhoudingssituatieAttribuut(Bijhoudingssituatie.AUTOMATISCHE_FIAT));
        bijhoudingsplanPersonen.add(bijhoudingsplanPersoonBericht);
        bijhoudingsplan.setBijhoudingsplanPersonen(bijhoudingsplanPersonen);

        return bijhoudingsplan;
    }

    private static PersoonBericht maakPersoon(final BijhoudingBerichtContext context) {
        final PersoonBericht persoon = new PersoonBericht();
        persoon.setObjecttype("Persoon");
        final PersoonAfgeleidAdministratiefGroepBericht persoonAfgeleidAdministratiefGroepBericht = new PersoonAfgeleidAdministratiefGroepBericht();
        persoonAfgeleidAdministratiefGroepBericht.setTijdstipLaatsteWijziging(DatumTijdAttribuut.nu());
        persoon.setAfgeleidAdministratief(persoonAfgeleidAdministratiefGroepBericht);
        final PersoonIdentificatienummersGroepBericht persoonIdentificatienummersGroepBericht = new PersoonIdentificatienummersGroepBericht();
        persoonIdentificatienummersGroepBericht.setBurgerservicenummer(new BurgerservicenummerAttribuut(1));
        persoon.setIdentificatienummers(persoonIdentificatienummersGroepBericht);
        final PersoonBijhoudingGroepBericht persoonBijhoudingGroepBericht = new PersoonBijhoudingGroepBericht();

        final PersoonHisVolledigImpl persoonHisVolledig = context.getBijgehoudenPersonen().get(0);
        persoonBijhoudingGroepBericht.setBijhoudingspartijCode(
            persoonHisVolledig.getPersoonBijhoudingHistorie().getActueleRecord().getBijhoudingspartij().getWaarde().getCode().toString());
        persoon.setBijhouding(persoonBijhoudingGroepBericht);

        return persoon;
    }
}

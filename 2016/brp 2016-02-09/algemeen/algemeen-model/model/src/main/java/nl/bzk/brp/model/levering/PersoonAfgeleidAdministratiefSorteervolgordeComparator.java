/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.levering;

import java.util.Comparator;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.SorteervolgordeAttribuut;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import org.apache.commons.lang.builder.CompareToBuilder;


/**
 * Comparator die er voor zorgt de de personen binnen de kennisgevingberichten gesorteerd zijn volgens de sorteer- volgorgde op afgeleid administratief.
 */
public class PersoonAfgeleidAdministratiefSorteervolgordeComparator implements Comparator<PersoonHisVolledig> {

    private DatumTijdAttribuut tijdstipRegistratie;

    /**
     * Constructor voor deze comparator.
     *
     * @param tsReg tijdstip dat wordt gebruikt voor het sorteren
     */
    public PersoonAfgeleidAdministratiefSorteervolgordeComparator(final DatumTijdAttribuut tsReg) {
        this.tijdstipRegistratie = tsReg;
    }

    @Override
    public int compare(final PersoonHisVolledig persoonLinks, final PersoonHisVolledig persoonRechts) {
        SorteervolgordeAttribuut volgordeLinks = null;
        SorteervolgordeAttribuut volgordeRechts = null;

        if (!persoonLinks.getPersoonAfgeleidAdministratiefHistorie().isLeeg()) {
            final PersoonView view = new PersoonView(persoonLinks, tijdstipRegistratie);
            if (view.getAfgeleidAdministratief() != null) {
                volgordeLinks = view.getAfgeleidAdministratief().getSorteervolgorde();
            }

        }
        if (!persoonRechts.getPersoonAfgeleidAdministratiefHistorie().isLeeg()) {
            final PersoonView view = new PersoonView(persoonRechts, tijdstipRegistratie);
            if (view.getAfgeleidAdministratief() != null) {
                volgordeRechts = view.getAfgeleidAdministratief().getSorteervolgorde();
            }
        }

        return new CompareToBuilder().append(volgordeLinks, volgordeRechts)
            .append(persoonLinks.getID(), persoonRechts.getID()).toComparison();
    }
}

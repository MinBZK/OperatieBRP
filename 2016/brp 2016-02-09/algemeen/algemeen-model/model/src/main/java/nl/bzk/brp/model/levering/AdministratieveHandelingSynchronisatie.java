/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.levering;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.Verwerkingssoort;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;


/**
 * Deze klasse is bevat de gegevens van de administratieve handeling die gebruikt worden in de levering berichten.
 */
public class AdministratieveHandelingSynchronisatie {

    private final Long iD;

    private final Partij partij;

    private final DatumTijdAttribuut tijdstipRegistratie;

    private final SoortAdministratieveHandeling soort;

    private List<PersoonHisVolledigView> bijgehoudenPersonen;

    private Predicate bijgehoudenPersonenPredikaat;

    private Verwerkingssoort verwerkingssoort;

    /**
     * Constructor die een deel van de gegevens van een administratieveHandelingModel kopieert.
     *
     * @param administratieveHandelingModel De administratieve handeling waarop het deze administratieve handeling op gebaseerd moet worden.
     */
    public AdministratieveHandelingSynchronisatie(final AdministratieveHandelingModel administratieveHandelingModel) {
        this.iD = administratieveHandelingModel.getID();
        this.soort = administratieveHandelingModel.getSoort().getWaarde();
        this.partij = administratieveHandelingModel.getPartij().getWaarde();
        this.tijdstipRegistratie = administratieveHandelingModel.getTijdstipRegistratie();
    }

    public Partij getPartij() {
        return partij;
    }

    public DatumTijdAttribuut getTijdstipRegistratie() {
        return tijdstipRegistratie;
    }

    /**
     * Geeft de bijgehouden personen die voldoen aan het opgegeven predikaat.
     *
     * @return De bijgehouden personen die voldoen aan het predikaat.
     */
    public List<PersoonHisVolledigView> getBijgehoudenPersonen() {
        final List<PersoonHisVolledigView> resultaat = new ArrayList<>();

        if (this.bijgehoudenPersonenPredikaat != null) {
            CollectionUtils.select(this.bijgehoudenPersonen, this.bijgehoudenPersonenPredikaat, resultaat);
        } else if (this.bijgehoudenPersonen != null) {
            resultaat.addAll(this.bijgehoudenPersonen);
        }

        final Comparator<PersoonHisVolledig> comparator =
            new PersoonAfgeleidAdministratiefSorteervolgordeComparator(tijdstipRegistratie);
        Collections.sort(resultaat, comparator);
        return resultaat;
    }

    public void setBijgehoudenPersonen(final List<PersoonHisVolledigView> bijgehoudenPersonen) {
        this.bijgehoudenPersonen = bijgehoudenPersonen;
    }

    public SoortAdministratieveHandeling getSoort() {
        return soort;
    }

    public Long getID() {
        return iD;
    }

    public void setBijgehoudenPersonenPredikaat(final Predicate bijgehoudenPersonenPredikaat) {
        this.bijgehoudenPersonenPredikaat = bijgehoudenPersonenPredikaat;
    }

    public Verwerkingssoort getVerwerkingssoort() {
        return verwerkingssoort;
    }

    public void setVerwerkingssoort(final Verwerkingssoort verwerkingssoort) {
        this.verwerkingssoort = verwerkingssoort;
    }

    /**
     * Controleert of het aantal bijgehouden personen groter is dan 0.
     *
     * @return true als er bijgehouden personen zijn, anders false.
     */
    public final boolean heeftBijgehoudenPersonen() {
        return !getBijgehoudenPersonen().isEmpty();
    }
}

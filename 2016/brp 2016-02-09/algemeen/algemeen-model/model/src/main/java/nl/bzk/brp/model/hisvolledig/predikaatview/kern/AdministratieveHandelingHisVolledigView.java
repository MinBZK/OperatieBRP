/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.kern;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.IdComparator;
import nl.bzk.brp.model.hisvolledig.kern.ActieBronHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.ActieHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.AdministratieveHandelingGedeblokkeerdeMeldingHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.AdministratieveHandelingHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaatview.ActieComparator;
import nl.bzk.brp.model.hisvolledig.predikaatview.AdministratieveHandelingBronComparator;
import org.apache.commons.collections.Predicate;

/**
 * Predikaat view voor Administratieve handeling.
 */
public final class AdministratieveHandelingHisVolledigView extends AbstractAdministratieveHandelingHisVolledigView implements
    AdministratieveHandelingHisVolledig, ElementIdentificeerbaar
{

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param administratieveHandelingHisVolledig administratieveHandeling
     * @param predikaat                           predikaat
     */
    public AdministratieveHandelingHisVolledigView(final AdministratieveHandelingHisVolledig administratieveHandelingHisVolledig,
        final Predicate predikaat)
    {
        super(administratieveHandelingHisVolledig, predikaat);
    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param administratieveHandelingHisVolledig administratieveHandeling
     * @param predikaat                           predikaat
     * @param peilmomentVoorAltijdTonenGroepen    peilmomentVoorAltijdTonenGroepen
     */
    public AdministratieveHandelingHisVolledigView(
        final AdministratieveHandelingHisVolledig administratieveHandelingHisVolledig,
        final Predicate predikaat,
        final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(administratieveHandelingHisVolledig, predikaat, peilmomentVoorAltijdTonenGroepen);
    }

    @Override
    public void setMagGeleverdWorden(final boolean vlag) {
        administratieveHandeling.setMagGeleverdWorden(vlag);
    }

    @Override
    public boolean isMagGeleverdWorden() {
        return administratieveHandeling.isMagGeleverdWorden();
    }

    /**
     * Functie gebruikt door Jibx om de bronnen onder een handeling in een lever/bevraging bericht te marshallen. Retourneert de bij de handeling gebruikte
     * bronnen. Let op, een bron dat bij meerdere acties is gebruikt moet maar één keer geretourneerd worden. Het gaat hier dus om een lijst met unieke
     * bronnen. (Document, rechtsgrond, rechtsgrondomschrijving)
     *
     * @return lijst met alle gebruikte bronnen bij deze handeling.
     */
    public Set<ActieBronHisVolledig> getHandelingBronnen() {
        final Set<ActieBronHisVolledig> handelingBronnen = new TreeSet<>(new AdministratieveHandelingBronComparator());

        final List<Short> idsVanRechtsgronden = new ArrayList<>();
        final List<Long> idsVanDocumenten = new ArrayList<>();
        final List<String> rechtsgrondOmschrijvingen = new ArrayList<>();

        for (final ActieHisVolledig actie : getActies()) {
            for (final ActieBronHisVolledigView actieBronModel : (Set<ActieBronHisVolledigView>) actie.getBronnen()) {
                if (!actieBronModel.isZichtbaar()) {
                    continue;
                }
                if (actieBronModel.getDocument() != null) {
                    if (!idsVanDocumenten.contains(actieBronModel.getDocument().getID())) {
                        handelingBronnen.add(actieBronModel);
                        idsVanDocumenten.add(actieBronModel.getDocument().getID());
                    }
                }
                if (actieBronModel.getRechtsgrond() != null) {
                    if (!idsVanRechtsgronden.contains(actieBronModel.getRechtsgrond().getWaarde().getID())) {
                        handelingBronnen.add(actieBronModel);
                        idsVanRechtsgronden.add(actieBronModel.getRechtsgrond().getWaarde().getID());
                    }
                }
                if (actieBronModel.getRechtsgrondomschrijving() != null) {
                    if (!rechtsgrondOmschrijvingen.contains(actieBronModel.getRechtsgrondomschrijving().getWaarde())) {
                        handelingBronnen.add(actieBronModel);
                        rechtsgrondOmschrijvingen.add(actieBronModel.getRechtsgrondomschrijving().getWaarde());
                    }
                }
            }
        }
        return handelingBronnen;
    }

    /**
     * Test functie voor Jibx om te bepalen of er gedeblokkeerde meldingen aanwezig zijn.
     *
     * @return true indien er gedeblokkeerde meldingen zijn, anders false.
     */
    public boolean heeftGedeblokkeerdeMeldingen() {
        return !getGedeblokkeerdeMeldingen().isEmpty();
    }

    /**
     * Test functie voor Jibx om te bepalen of er actie bronnen aanwezig zijn.
     *
     * @return true indien er actie bronnen zijn, anders false.
     */
    public boolean heeftBronnen() {
        return !getHandelingBronnen().isEmpty();
    }

    /**
     * Controleert of er acties zijn die geleverd mogen worden. Gebruikt om vanuit de bindings te bepalen of de <bijgehoudenActies> tag getoond moet
     * worden.
     *
     * @return True als er minimaal 1 actie is die geleverd mag worden, anders false.
     */
    public boolean heeftActiesDieGeleverdMogenWorden() {
        if (getActies() != null) {
            for (final ActieHisVolledig actie : getActies()) {
                if (actie.isMagGeleverdWorden()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Geeft een gesorteerde lijst (volgens de ActieComparator) van acties.
     *
     * @return De gesorteerde lijst van acties.
     */
    public SortedSet<ActieHisVolledig> getActiesVoorLeveren() {
        final SortedSet<ActieHisVolledig> resultaat = new TreeSet<>(new ActieComparator());
        resultaat.addAll(super.getActies());
        return Collections.unmodifiableSortedSet(resultaat);
    }

    /**
     * Geeft een gesorteerde lijst (volgens de IdComparator) van gedeblokkeerde meldingen. Dit wordt door JiBX gebruikt om altijd een vaste volgorde te
     * hebben voor meldingen.
     *
     * @return De gesorteerde lijst van gedeblokkeerde meldingen.
     */
    public SortedSet<AdministratieveHandelingGedeblokkeerdeMeldingHisVolledig> getGedeblokkeerdeMeldingenVoorLeveren() {
        final SortedSet<AdministratieveHandelingGedeblokkeerdeMeldingHisVolledig> resultaat = new TreeSet<>(new IdComparator());
        resultaat.addAll(super.getGedeblokkeerdeMeldingen());
        return Collections.unmodifiableSortedSet(resultaat);
    }
}

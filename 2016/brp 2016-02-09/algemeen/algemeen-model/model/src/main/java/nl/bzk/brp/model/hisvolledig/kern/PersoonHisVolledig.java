/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.kern;

import java.util.List;
import java.util.Set;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.Verwerkingssoort;
import nl.bzk.brp.model.basis.HisVolledigModelRootObject;


/**
 * Interface voor Persoon.
 */
public interface PersoonHisVolledig extends PersoonHisVolledigBasis, HisVolledigModelRootObject {

    /**
     * Geeft de gehele verantwoording terug in een lijst.
     *
     * @return de complete lijst met verantwoording voor deze persoon.
     */
    List<? extends AdministratieveHandelingHisVolledig> getAdministratieveHandelingen();

    /**
     * Geeft de partner betrokkenheden van de persoon.
     *
     * @return de set van partner betrokkenheden, of een lege set
     */
    Set<? extends PartnerHisVolledig> getPartnerBetrokkenheden();

    /**
     * Geeft de ouder betrokkenheden van de persoon.
     *
     * @return de set van ouder betrokkenheden, of een lege set
     */
    Set<? extends OuderHisVolledig> getOuderBetrokkenheden();

    /**
     * Geeft de kind betrokkenheid van de persoon.
     *
     * @return de kindbetrokkenheid of {@code null}
     */
    KindHisVolledig getKindBetrokkenheid();

    /**
     * Geeft de huwelijkGeregistreerdPartnerschappen van de persoon.
     *
     * @return de set van huwelijkgeregistreerdpartnerschappen, of een lege set
     */
    Set<? extends HuwelijkGeregistreerdPartnerschapHisVolledig> getHuwelijkGeregistreerdPartnerschappen();

    /**
     * Geef de verwerkingssoort terug.
     *
     * @return de verwerkingssoort
     */
    Verwerkingssoort getVerwerkingssoort();

    /**
     * Zet de verwerkingssoort.
     *
     * @param verwerkingssoort de verwerkingssoort
     */
    void setVerwerkingssoort(Verwerkingssoort verwerkingssoort);
}

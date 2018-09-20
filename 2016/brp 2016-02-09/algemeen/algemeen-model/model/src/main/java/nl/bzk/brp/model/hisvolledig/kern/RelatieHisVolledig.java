/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.kern;

import java.util.Set;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.Verwerkingssoort;
import nl.bzk.brp.model.basis.HisVolledigModelRootObject;


/**
 * Interface voor Relatie.
 */
public interface RelatieHisVolledig extends RelatieHisVolledigBasis, HisVolledigModelRootObject {

    /**
     * Geeft de kind betrokkenheid uit deze relatie.
     * @return de kind betrokkenheid indien aanwezig, anders {@code null}
     */
    KindHisVolledig getKindBetrokkenheid();

    /**
     * Geeft de ouder betrokkenheden uit deze relatie.
     * @return de set van ouder betrokkenheden, anders een lege set
     */
    Set<? extends OuderHisVolledig> getOuderBetrokkenheden();

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

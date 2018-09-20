/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.logisch.kern.AdministratieveHandeling;


/**
 * Een door het bijhoudingsorgaan ge�nitieerde activiteit in de BRP, waarmee persoonsgegevens worden bijgehouden.
 * <p/>
 * De binnen de BRP geadministreerde persoonsgegevens worden bijgehouden doordat wijzigingen worden doorgevoerd vanuit de gemeentelijke of ministeri�le
 * verantwoordelijkheid. Het initiatief gegevens te wijzigen komt vanuit het betreffende bijhoudingsorgaan; deze stuurt daartoe een bericht aan de BRP die
 * de daadwerkelijke bijhouding doet plaatsvinden. Voor de verwerking binnen de BRP wordt dit bericht uiteen gerafeld in ��n of meer Acties. Het geheel aan
 * acties wordt de administratieve handeling genoemd; dit is in de BRP de weerslag van wat in termen van de burgerzakenmodule 'de zaak' zal zijn geweest.
 * Qua niveau staat het op hetzelfde niveau als het bericht; het verschil bestaat eruit dat het bericht het vehikel is waarmee de administratieve handeling
 * wordt bewerkstelligt.
 */
public abstract class AdministratieveHandelingBericht extends AbstractAdministratieveHandelingBericht implements
    AdministratieveHandeling
{

    /**
     * @return de hoofd actie
     * Retourneert de hoofdactie van deze administratieve handeling. De eerste Actie die voorkomt binnen een AdmHandeling wordt
     * de Hoofdactie genoemd.
     * @brp.bedrijfsregel BRAL2116
     */
    public ActieBericht getHoofdActie() {
        ActieBericht hoofdActie = null;
        if (getActies() != null && !getActies().isEmpty()) {
            hoofdActie = getActies().get(0);
        }
        return hoofdActie;
    }

    /**
     * @return lijst van nevenacties
     * Retourneert de neven acties van deze administratieve handeling. Een Actie die binnen een AdmHandeling niet de Hoofdactie
     * is, wordt een "Nevenactie" genoemd.
     * @brp.bedrijfsregel BRAL2117
     */
    public List<ActieBericht> getNevenActies() {
        final List<ActieBericht> nevenActies = new ArrayList<ActieBericht>();
        if (getActies() != null && !getActies().isEmpty()) {
            nevenActies.addAll(getActies());
            nevenActies.remove(0);
        }
        return nevenActies;
    }

    /**
     * Constructor die het discriminator attribuut zet of doorgeeft.
     *
     * @param soort de waarde van het discriminator attribuut
     */
    public AdministratieveHandelingBericht(final SoortAdministratieveHandelingAttribuut soort) {
        super(soort);
    }

    /**
     * Het is belangrijk dat voor antwoord berichten de code is ingevuld. Want in de xml communiceren we met codes. Dit om "missing required object"
     * meldingen te voorkomen.
     *
     * @param partij x.
     */
    @Override
    public void setPartij(final PartijAttribuut partij) {
        super.setPartij(partij);
        if (partij != null && null != partij.getWaarde() && null != partij.getWaarde().getCode()) {
            setPartijCode(partij.getWaarde().getCode().toString());
        }
    }
}

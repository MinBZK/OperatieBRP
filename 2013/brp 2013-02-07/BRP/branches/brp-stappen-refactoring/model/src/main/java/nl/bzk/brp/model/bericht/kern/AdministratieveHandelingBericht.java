/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import java.util.List;

import javax.validation.Valid;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.bericht.kern.basis.AbstractAdministratieveHandelingBericht;
import nl.bzk.brp.model.logisch.kern.AdministratieveHandeling;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.constraint.NietGroterDan;


/**
 * Een door het bijhoudingsorgaan ge�nitieerde activiteit in de BRP, waarmee persoonsgegevens worden bijgehouden.
 *
 * De binnen de BRP geadministreerde persoonsgegevens worden bijgehouden doordat wijzigingen worden doorgevoerd vanuit
 * de gemeentelijke of ministeri�le verantwoordelijkheid. Het initiatief gegevens te wijzigen komt vanuit het
 * betreffende bijhoudingsorgaan; deze stuurt daartoe een bericht aan de BRP die de daadwerkelijke bijhouding doet
 * plaatsvinden. Voor de verwerking binnen de BRP wordt dit bericht uiteen gerafeld in ��n of meer Acties. Het geheel
 * aan acties wordt de administratieve handeling genoemd; dit is in de BRP de weerslag van wat in termen van de
 * burgerzakenmodule 'de zaak' zal zijn geweest. Qua niveau staat het op hetzelfde niveau als het bericht; het verschil
 * bestaat eruit dat het bericht het vehikel is waarmee de administratieve handeling wordt bewerkstelligt.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.BerichtModelGenerator.
 * Metaregister versie: 1.1.8.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2012-11-27 12:02:51.
 * Gegenereerd op: Tue Nov 27 13:50:43 CET 2012.
 */
@NietGroterDan(veld = "tijdstipOntlening",
               nietGroterDanVeld = "tijdstipRegistratie", code = MeldingCode.BRAL9010, message = "BRAL9010")
public abstract class AdministratieveHandelingBericht extends AbstractAdministratieveHandelingBericht implements
        AdministratieveHandeling
{
    /**
     * Constructor die het discriminator attribuut zet of doorgeeft.
     *
     * @param soort de waarde van het discriminator attribuut
     */
    protected AdministratieveHandelingBericht(final SoortAdministratieveHandeling soort) {
        super(soort);
    }

    @Override
    @Valid
    public List<ActieBericht> getActies() {
        return super.getActies();
    }

    /**
     * @TODO Kijken of we dit kunnen genereren of op een andere manier oplossen.
     * Het is belangrijk dat voor antwoord berichten de code is ingevuld. Want in de xml communiceren we met codes.
     * Dit om "missing required object" meldingen te voorkomen. (Meer info: Oussama)
     * @param partij x.
     */
    @Override
    public void setPartij(final Partij partij) {
        super.setPartij(partij);
        if (partij != null && null != partij.getCode()) {
            setPartijCode(partij.getCode().toString());
        }
    }

}

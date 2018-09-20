/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern.basis;

import java.util.Collection;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijd;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ontleningstoelichting;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.basis.ObjectType;
import nl.bzk.brp.model.logisch.ber.AdministratieveHandelingBijgehoudenPersoon;
import nl.bzk.brp.model.logisch.ber.AdministratieveHandelingDocument;
import nl.bzk.brp.model.logisch.ber.AdministratieveHandelingGedeblokkeerdeMelding;
import nl.bzk.brp.model.logisch.kern.Actie;


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
 * Generator: nl.bzk.brp.generatoren.java.LogischModelGenerator.
 * Generator versie: 1.0-SNAPSHOT.
 * Metaregister versie: 1.6.0.
 * Gegenereerd op: Tue Jan 15 12:53:51 CET 2013.
 */
public interface AdministratieveHandelingBasis extends ObjectType {

    /**
     * Retourneert Soort van Administratieve handeling.
     *
     * @return Soort.
     */
    SoortAdministratieveHandeling getSoort();

    /**
     * Retourneert Partij van Administratieve handeling.
     *
     * @return Partij.
     */
    Partij getPartij();

    /**
     * Retourneert Tijdstip ontlening van Administratieve handeling.
     *
     * @return Tijdstip ontlening.
     */
    DatumTijd getTijdstipOntlening();

    /**
     * Retourneert Toelichting ontlening van Administratieve handeling.
     *
     * @return Toelichting ontlening.
     */
    Ontleningstoelichting getToelichtingOntlening();

    /**
     * Retourneert Tijdstip registratie van Administratieve handeling.
     *
     * @return Tijdstip registratie.
     */
    DatumTijd getTijdstipRegistratie();

    /**
     * Retourneert Administratieve handeling \ Gedeblokkeerde Meldingen van Administratieve handeling.
     *
     * @return Administratieve handeling \ Gedeblokkeerde Meldingen van Administratieve handeling.
     */
    Collection<? extends AdministratieveHandelingGedeblokkeerdeMelding> getGedeblokkeerdeMeldingen();

    /**
     * Retourneert Administratieve handeling \ Bijgehouden personen van Administratieve handeling.
     *
     * @return Administratieve handeling \ Bijgehouden personen van Administratieve handeling.
     */
    Collection<? extends AdministratieveHandelingBijgehoudenPersoon> getBijgehoudenPersonen();

    /**
     * Retourneert Acties van Administratieve handeling.
     *
     * @return Acties van Administratieve handeling.
     */
    Collection<? extends Actie> getActies();

    /**
     * Retourneert Administratieve handeling \ Documenten van Administratieve handeling.
     *
     * @return Administratieve handeling \ Documenten van Administratieve handeling.
     */
    Collection<? extends AdministratieveHandelingDocument> getBijgehoudenDocumenten();

}

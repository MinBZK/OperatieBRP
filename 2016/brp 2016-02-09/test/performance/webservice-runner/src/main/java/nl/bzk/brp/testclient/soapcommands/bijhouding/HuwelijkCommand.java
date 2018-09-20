/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.soapcommands.bijhouding;

import nl.bzk.brp.bijhouding.service.BhgHuwelijkGeregisteerdPartnerschap;
import nl.bzk.brp.brp0200.HuwelijkPartnerschapRegistreerHuwelijkPartnerschapBijhouding;
import nl.bzk.brp.brp0200.HuwelijkPartnerschapRegistreerHuwelijkPartnerschapBijhoudingResultaat;
import nl.bzk.brp.brp0200.ObjecttypeAdministratieveHandeling;
import nl.bzk.brp.testclient.soapcommands.AbstractSoapCommand;


/**
 * Het 'command' dat uitgevoerd dient te worden voor het versturen van een huwelijk SOAP bericht.
 */
public class HuwelijkCommand extends
    AbstractSoapCommand<BhgHuwelijkGeregisteerdPartnerschap,
            HuwelijkPartnerschapRegistreerHuwelijkPartnerschapBijhouding,
            HuwelijkPartnerschapRegistreerHuwelijkPartnerschapBijhoudingResultaat>
{

    /**
     * Instantieert een nieuwe huwelijk command.
     *
     * @param vraag de vraag
     */
    public HuwelijkCommand(final HuwelijkPartnerschapRegistreerHuwelijkPartnerschapBijhouding vraag) {
        super(vraag);
    }

    /* (non-Javadoc)
     * @see nl.bzk.brp.testclient.soapcommands.AbstractSoapCommand#bepaalEnSetAntwoord(java.lang.Object)
     */
    @Override
    public HuwelijkPartnerschapRegistreerHuwelijkPartnerschapBijhoudingResultaat bepaalEnSetAntwoord(final BhgHuwelijkGeregisteerdPartnerschap portType) {
        return portType.registreerHuwelijkPartnerschap(getVraag());
    }


    @Override
    protected ObjecttypeAdministratieveHandeling getAdministratieveHandelingUitAntwoord(
            final HuwelijkPartnerschapRegistreerHuwelijkPartnerschapBijhoudingResultaat antwoord)
    {
        ObjecttypeAdministratieveHandeling objecttypeAdministratieveHandeling = null;

        if (null != antwoord.getVoltrekkingHuwelijkInNederland()) {
            objecttypeAdministratieveHandeling = antwoord.getVoltrekkingHuwelijkInNederland().getValue();
        } else if (null != antwoord.getAangaanGeregistreerdPartnerschapInNederland()) {
            objecttypeAdministratieveHandeling = antwoord.getAangaanGeregistreerdPartnerschapInNederland().getValue();
        } else if (null != antwoord.getVoltrekkingHuwelijkInBuitenland()) {
            objecttypeAdministratieveHandeling = antwoord.getVoltrekkingHuwelijkInBuitenland().getValue();
        } else if (null != antwoord.getAangaanGeregistreerdPartnerschapInBuitenland()) {
            objecttypeAdministratieveHandeling = antwoord.getAangaanGeregistreerdPartnerschapInBuitenland().getValue();
        }
        return objecttypeAdministratieveHandeling;
    }
}

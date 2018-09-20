/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.ber;

import nl.bzk.brp.model.logisch.ber.basis.AdministratieveHandelingGedeblokkeerdeMeldingBasis;


/**
 * Het door middel van een bericht deblokkeren of gedeblokkkerd hebben van een (deblokkeerbare) fout.
 * 
 * Een bijhoudingsbericht kan aanleiding zijn tot ��n of meer deblokkeerbare fouten. Een deblokkeerbare fout kan worden
 * gedeblokkeerd door in een bijhoudingsbericht expliciet de (deblokkeerbare) fout te de-blokkeren. Een gedeblokkeerde
 * fout wordt twee keer gekoppeld aan een bericht: enerzijds door een koppeling tussen het inkomende bijhoudingsbericht
 * en de deblokkage; anderzijds door het uitgaand bericht waarin wordt medegedeeld welke deblokkeringen zijn verwerkt.
 * 
 * 
 * 
 * Generator: nl.bzk.brp.generatoren.java.LogischModelGenerator.
 * Generator versie: 1.0-SNAPSHOT.
 * Metaregister versie: 1.4.6.
 * Gegenereerd op: Tue Dec 18 10:54:31 CET 2012.
 */
public interface AdministratieveHandelingGedeblokkeerdeMelding extends
        AdministratieveHandelingGedeblokkeerdeMeldingBasis
{

}

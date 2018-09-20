/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/**
 * Package voor de CXF/JAX-WS interceptors die in de 'chain' van de CXF bericht afhandeling worden opgenomen. Deze
 * interceptors bevatten code die voordat of nadat de BRP service het bericht verwerkt nog zaken aan het bericht kunnen
 * wijzigen en/of toevoegen of bepaalde functionaliteit kunnen uitvoeren.
 *
 * De interceptors worden via de Spring configuratie toegevoegd aan de CXF bericht afhandeling 'chain' en dienen
 * daar dan ook eventueel verder geconfigureerd te worden.
 */
package nl.bzk.brp.bevraging.ws.service.interceptor;

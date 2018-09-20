/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/**
 * Tests voor het serializeren van {@link nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig} instanties van/naar JSON representatie.
 *
 * Verschillende personen in de testdata hebben verschillende interessante gegevens:
 *
 * 1 : 3 adressen, 3 bijhoudingsgemeentes, partner
 * 2 : 1 indicatie
 * 5 : ouder
 * 8 : 3 voornamen
 * 11: 2x ouder
 * 13: 4 voornamen, 2 indicaties
 * 14: opschorting, overlijden
 *
 */
package nl.bzk.brp.dataaccess.serializatie;

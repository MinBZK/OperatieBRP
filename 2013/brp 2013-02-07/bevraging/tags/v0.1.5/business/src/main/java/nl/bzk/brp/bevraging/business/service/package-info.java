/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/**
 * Package bevat de interfaces van de door de business laag aangeboden service(s). De centrale service is de
 * {@link nl.bzk.brp.bevraging.business.service.BerichtUitvoerderService}, die een binnenkomend {@link
 * nl.bzk.brp.bevraging.business.berichtcmds.BrpBerichtCommand} verwerkt door de benodigde handlers aan te
 * roepen en het bericht command uiteindelijk te executeren.
 *
 * De overige services zijn ter ondersteuning van het gehele proces, zoals het leveren van een uniek bericht id of
 * het archiveren van een bericht.
 */
package nl.bzk.brp.bevraging.business.service;

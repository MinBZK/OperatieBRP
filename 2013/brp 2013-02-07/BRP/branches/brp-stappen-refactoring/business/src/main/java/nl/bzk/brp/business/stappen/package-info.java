/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/**
 * Package bevat de verschillende stappen, waarbij elke stap een eigen bericht verwerkingsstap representeert. Deze
 * stappen dienen alle de {@link nl.bzk.brp.business.stappen.AbstractBerichtVerwerkingsStap} te implementeren, die
 * de methode {@link nl.bzk.brp.business.stappen.AbstractBerichtVerwerkingsStap#voerVerwerkingsStapUitVoorBericht(
 * nl.bzk.brp.business.dto.BRPBericht, nl.bzk.brp.business.dto.BerichtContext,
 * nl.bzk.brp.business.dto.BerichtVerwerkingsResultaat)} definieert, waarin de verschillende stappen hun specifieke
 * functionaliteit dienen te implementeren.
 */
package nl.bzk.brp.business.stappen;

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/**
 * Package bevat alle bericht specifieke command objecten. Voor elke door de service geboden functionaliteit (en dus
 * Use Case), is er een specifieke class die de betreffende functionaliteit implementeert.
 *
 * De package bevat tevens de interface {@link nl.bzk.brp.bevraging.business.berichtcmds.BerichtCommand} voor de
 * command objecten en een abstracte implementatie
 * ({@link nl.bzk.brp.bevraging.business.berichtcmds.AbstractBerichtCommand}) welke gebruikt kunnen worden als
 * basis voor de werkelijke, specifieke implementaties.
 */
package nl.bzk.brp.bevraging.business.berichtcmds;

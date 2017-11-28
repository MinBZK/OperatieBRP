/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.domain.bevraging;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Persoonsantwoord (Hf01 indien foutreden is gevuld, anders Ha01).
 */
@JsonInclude(Include.NON_NULL)
public class Persoonsantwoord extends Basisantwoord {
}

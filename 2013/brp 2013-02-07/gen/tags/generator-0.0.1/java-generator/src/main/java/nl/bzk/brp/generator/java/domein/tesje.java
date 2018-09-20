/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generator.java.domein;

import java.util.Date;

import javax.persistence.MappedSuperclass;

import nl.bzk.brp.generator.JavaGeneratorUtils;
import org.slf4j.Logger;


@MappedSuperclass
public class tesje {

    Date               d;

    JavaGeneratorUtils u;

    Logger l;
}

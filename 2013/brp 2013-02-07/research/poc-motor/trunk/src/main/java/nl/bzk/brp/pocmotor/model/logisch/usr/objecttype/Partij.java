/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.model.logisch.usr.objecttype;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import nl.bzk.brp.pocmotor.model.logisch.gen.objecttype.AbstractPartij;

/**
 * Partij
  */
@Entity
@Table(schema = "Kern", name = "Partij")
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
    name="Srt",
    discriminatorType= DiscriminatorType.INTEGER
)
public class Partij extends AbstractPartij {


}

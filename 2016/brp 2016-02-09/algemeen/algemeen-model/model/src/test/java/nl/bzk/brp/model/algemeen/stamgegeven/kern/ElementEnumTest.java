/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import org.junit.Test;

public class ElementEnumTest {

	@Test
	public void test() {
		ElementEnum element = ElementEnum.AANDUIDINGINHOUDINGVERMISSINGREISDOCUMENT;
		element.getGroep();
		element.getGroepId();
		element.getHisDbObjectId();
		element.getId();
		element.getNaam();
		element.getSoort();
	}
}

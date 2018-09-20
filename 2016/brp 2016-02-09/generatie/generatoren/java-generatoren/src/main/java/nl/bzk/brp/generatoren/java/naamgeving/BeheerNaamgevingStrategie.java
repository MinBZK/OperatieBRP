/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.naamgeving;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.bzk.brp.generatoren.algemeen.common.BmrSoortInhoud;
import nl.bzk.brp.generatoren.java.BeheerGenerator;
import nl.bzk.brp.generatoren.java.util.GeneratiePackage;
import nl.bzk.brp.generatoren.java.util.JavaGeneratieUtil;
import nl.bzk.brp.metaregister.model.GeneriekElement;

public class BeheerNaamgevingStrategie extends AbstractNaamgevingStrategie {
    private static final Logger LOGGER = LoggerFactory.getLogger(BeheerGenerator.class);

	@Override
	protected String bepaalPackageNaamVoorElement(GeneriekElement element) {

		if(valtOnderBeheerGenerator(element)) {
			return concatPackages(GeneratiePackage.BEHEER_PACKAGE, getSchemaVoorElement(element));
		} else {
			if(isStatischStamgegeven(element)) {
				return concatPackages(GeneratiePackage.STAMGEGEVEN_STATISCH_PACKAGE, getSchemaVoorElement(element));
			} else if(isDynamischStamgegeven(element)) {
				return concatPackages(GeneratiePackage.STAMGEGEVEN_DYNAMISCH_PACKAGE, getSchemaVoorElement(element));
			} else {
				return concatPackages(GeneratiePackage.OBJECTTYPE_OPERATIONEEL_PACKAGE, getSchemaVoorElement(element));
			}
		}
	}

	public boolean isStatischStamgegeven(GeneriekElement element) {
		return isVanSoortInhoud(element, 'X');
	}

	public boolean isDynamischStamgegeven(GeneriekElement element) {
		return isVanSoortInhoud(element, 'S');

	}

	private boolean isVanSoortInhoud(GeneriekElement element, char soortInhoud) {
		return element.getSoortInhoud()  != null && element.getSoortInhoud() == soortInhoud;
	}

	public boolean valtOnderBeheerGenerator(GeneriekElement element) {
	    final boolean result;
	    if( element.getSoortInhoud() != null && BmrSoortInhoud.DYNAMISCH_STAMGEGEVEN.getCode() == element.getSoortInhoud().charValue()) {
		    result = true;
	    } else if (element.getSoortInhoud() != null && BmrSoortInhoud.DYNAMISCH_OBJECT_TYPE.getCode() == element.getSoortInhoud().charValue() && "AutAut".equalsIgnoreCase(element.getSchema().getNaam()) ) {
		    result = true;
	    } else {
		    result = false;
	    }
	    return result;
	}

	@Override
	protected String bepaalJavaTypeNaamVoorElement(GeneriekElement element) {
		if(!valtOnderBeheerGenerator(element) && !isStatischStamgegeven(element) && !isDynamischStamgegeven(element)) {
			LOGGER.debug("Genereer link naar operationeel model voor element {}", element.getNaam());
			return JavaGeneratieUtil.cleanUpInvalidJavaCharacters(element.getIdentCode()+"Model");
		} else {
			return JavaGeneratieUtil.cleanUpInvalidJavaCharacters(element.getIdentCode());
		}
	}

}

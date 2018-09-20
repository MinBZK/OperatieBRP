/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generator;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.metaregister.model.Attribuut;
import nl.bzk.brp.metaregister.model.ElementSoort;
import nl.bzk.brp.metaregister.model.GeneriekElement;
import nl.bzk.brp.metaregister.model.Groep;
import nl.bzk.brp.metaregister.model.SoortInhoud;


public class JavaGeneratorUtils {

    public static String createImportStatement(final GeneriekElement element) {
        return JavaGeneratorUtils.getImportPackageForGroup(element).createImportStatement(getJavaType(element));
    }

    public static GenerationPackageNames getImportPackageForGroup(final GeneriekElement element) {
        switch (ElementSoort.getSoort(element)) {
            case ATTRIBUUTTYPE:
                return GenerationPackageNames.ATTRIBUUTTYPE_PACKAGE;
            case OBJECTTYPE:
                switch (SoortInhoud.getSoort(element)) {
                    case DYNAMISCH:
                        return GenerationPackageNames.OBJECTTYPE_OPERATIONEEL_PACKAGE;
                    case STATISCH:
                    case ENUMERATIE:
                        return GenerationPackageNames.OBJECTTYPE_OPERATIONEEL_STATISCH_PACKAGE;
                    default:
                        throw new RuntimeException("onbekende inhoud: " + element.getSoortInhoud());
                }
            default:
                throw new RuntimeException("onbekend package voor element soort code: "
                    + element.getSoortElement().getCode());
        }
    }
    
    public static GenerationPackageNames getImportPackageForObjectType(final GeneriekElement element) {
        switch (ElementSoort.getSoort(element)) {
            case ATTRIBUUTTYPE:
                return GenerationPackageNames.ATTRIBUUTTYPE_PACKAGE;
            case OBJECTTYPE:
                switch (SoortInhoud.getSoort(element)) {
                    case DYNAMISCH:
                        return GenerationPackageNames.OBJECTTYPE_LOGISCH_PACKAGE;
                    case STATISCH:
                    case ENUMERATIE:
                        return GenerationPackageNames.OBJECTTYPE_OPERATIONEEL_STATISCH_PACKAGE;
                    default:
                        throw new RuntimeException("onbekende inhoud: " + element.getSoortInhoud());
                }
            default:
                throw new RuntimeException("onbekend package voor element soort code: "
                    + element.getSoortElement().getCode());
        }
    }

    public static String getJavaType(final GeneriekElement type) {
        String resultaat = type.getIdentCode();
        if (ElementSoort.OBJECTTYPE.isSoort(type) && SoortInhoud.DYNAMISCH.isSoort(type)) {
            resultaat += "Model";
        }
        return resultaat;
    }

    public static List<String> getImportLijst(final Groep groep, final List<Attribuut> attributen) {
        List<String> resultaat = new ArrayList<String>();
        for (Attribuut attribuut : attributen) {
            resultaat.add(JavaGeneratorUtils.createImportStatement(attribuut.getType()));
        }
        return resultaat;
    }

}

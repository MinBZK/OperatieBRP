/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.naamgeving;

import nl.bzk.brp.generatoren.algemeen.basis.GeneratorExceptie;
import nl.bzk.brp.generatoren.algemeen.common.BmrElementSoort;
import nl.bzk.brp.generatoren.algemeen.common.BmrSoortInhoud;
import nl.bzk.brp.generatoren.java.util.GeneratiePackage;
import nl.bzk.brp.generatoren.java.util.JavaGeneratieUtil;
import nl.bzk.brp.metaregister.model.GeneriekElement;

/**
 * Implementatie van de {@link NaamgevingStrategie} interface voor alle algemene element; alle elementen/classes die
 * niet tot een specifiek model behoren. Het gaat hier in het bijzonder om attribuuttypes en statische stamgegevens,
 * daar deze niet tot een specifiek model horen (geen model specifieke representatie hebben), maar algemeen gebruikt
 * worden over de modellen heen.
 */
public class AlgemeneNaamgevingStrategie extends AbstractNaamgevingStrategie {

    /** {@inheritDoc} */
    @Override
    protected final String bepaalPackageNaamVoorElement(final GeneriekElement element) {
        final String soortCode = element.getSoortElement().getCode();
        final BmrElementSoort soort = BmrElementSoort.getBmrElementSoortVoorCode(soortCode);

        final String packageNaam;
        switch (soort) {
            case OBJECTTYPE:
                packageNaam = bouwPackageNaamVoorStamgegevenObjectType(element);
                break;
            case ATTRIBUUTTYPE:
                packageNaam =
                    concatPackages(GeneratiePackage.ATTRIBUUTTYPE_PACKAGE, getSchemaVoorElement(element));
                break;
            default:
                throw new IllegalArgumentException(
                    "Onbekend/Onverwachte element soort bij packagenaam generatie: " + soortCode);
        }
        return packageNaam;
    }

    /**
     * Retourneert de packagenaam voor een objecttype dat een stamgegeven is. Het package verschilt dan ook of het
     * een (vanuit BMR oogpunt gezien) statisch of een dynamisch stamgegeven is.
     *
     * @param objectType het stamgegeven objecttype.
     * @return de packagenaam voor het stamgegeven objecttype.
     */
    private String bouwPackageNaamVoorStamgegevenObjectType(final GeneriekElement objectType) {
        final GeneratiePackage generatiePackage;
        if (objectType.getSoortInhoud() == BmrSoortInhoud.STATISCH_STAMGEGEVEN.getCode()) {
            generatiePackage = GeneratiePackage.STAMGEGEVEN_STATISCH_PACKAGE;
        } else if (objectType.getSoortInhoud() == BmrSoortInhoud.DYNAMISCH_STAMGEGEVEN.getCode()) {
            generatiePackage = GeneratiePackage.STAMGEGEVEN_DYNAMISCH_PACKAGE;
        } else {
            throw new GeneratorExceptie(
                "Onbekend/Onverwachte element soort inhoud bij packagenaam generatie: "
                    + objectType.getSoortInhoud());
        }

        return concatPackages(generatiePackage, getSchemaVoorElement(objectType));
    }

    /** {@inheritDoc} */
    @Override
    protected final String bepaalJavaTypeNaamVoorElement(final GeneriekElement element) {
        final String soortCode = element.getSoortElement().getCode();
        final BmrElementSoort soort = BmrElementSoort.getBmrElementSoortVoorCode(soortCode);

        final String prefix;
        final String suffix;
        switch (soort) {
            case OBJECTTYPE:
                final BmrSoortInhoud soortInhoud = BmrSoortInhoud.getBmrSoortInhoudVoorCode(element.getSoortInhoud());
                if (soortInhoud == BmrSoortInhoud.STATISCH_STAMGEGEVEN
                        || soortInhoud == BmrSoortInhoud.DYNAMISCH_STAMGEGEVEN)
                {
                    prefix = "";
                    suffix = "";
                } else {
                    throw new IllegalArgumentException(String.format(
                        "Onbekend/Onverwachte element soort inhoud ('%s') voor javatype generatie van '%s'.",
                        soortInhoud, element.getNaam()));
                }
                break;
            case ATTRIBUUTTYPE:
                prefix = "";
                suffix = "";
                break;
            case TUPLE:
                prefix = "";
                suffix = "";
                break;
            default:
                throw new IllegalArgumentException(
                        "Onbekend/Onverwacht element soort bij javatype generatie: " + soortCode);
        }
        return String.format("%s%s%s", prefix,
                             JavaGeneratieUtil.cleanUpInvalidJavaCharacters(element.getIdentCode()), suffix);
    }

    /**
     * Methode die controleert of een opgegeven element een algemene naam/package structuur volgt; indien dit zo is,
     * dan kan deze naamgeving strategie gebruikt worden, zo niet, dan zal een model specifieke naamgeving strategie
     * gebruikt dienen te worden.
     *
     * @param element het element dat gecontroleerd dient te worden.
     * @return een boolean die aangeeft of het element een algemene naamgeving volgt of niet.
     */
    public final boolean heeftElementEenAlgemeneNaam(final GeneriekElement element) {
        final BmrElementSoort soort = BmrElementSoort.getBmrElementSoortVoorCode(element.getSoortElement().getCode());

        boolean resultaat = false;
        resultaat |= soort == BmrElementSoort.ATTRIBUUTTYPE;
        resultaat |= (soort == BmrElementSoort.OBJECTTYPE
            && (element.getSoortInhoud().equals('X') || element.getSoortInhoud().equals('S')));
        return resultaat;
    }

}

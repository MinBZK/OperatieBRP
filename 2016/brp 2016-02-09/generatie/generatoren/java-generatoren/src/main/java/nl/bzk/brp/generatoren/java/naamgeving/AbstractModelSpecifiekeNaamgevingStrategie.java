/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.naamgeving;

import nl.bzk.brp.generatoren.algemeen.common.BmrElementSoort;
import nl.bzk.brp.generatoren.java.util.GeneratiePackage;
import nl.bzk.brp.generatoren.java.util.JavaGeneratieUtil;
import nl.bzk.brp.metaregister.model.GeneriekElement;
import nl.bzk.brp.metaregister.model.Groep;
import nl.bzk.brp.metaregister.model.ObjectType;
import nl.bzk.brp.metaregister.model.Tuple;

/**
 * Standaard implementatie van de {@link NaamgevingStrategie} interface voor alle model specifieke naamgeving
 * strategien. Deze basis class implementeert de methodes van de {@link NaamgevingStrategie} interface middels
 * gebruik van enkele abstracte methodes voor de model specifieke delen. Elke model specifieke subclass van deze
 * basis class hoeft alleen deze methodes te implementeren en definieert daarmee dan de voor dat model specifieke
 * naamgeneratie strategie.
 */
public abstract class AbstractModelSpecifiekeNaamgevingStrategie extends AbstractNaamgevingStrategie {

    // Deze naamgeving strategie is voor 'algemene' objecten: stamgegevens en scalaire attributen.
    // Die moeten met de wrapper naamgeving strategie hun java type bepalen, aangezien in alle
    // model specifieke klassen de attributen een wrapper moeten bevatten.
    private final AttribuutWrapperNaamgevingStrategie attribuutWrapperNaamgevingStrategie =
            new AttribuutWrapperNaamgevingStrategie();

    /**
     * Retourneert de package waartoe objecttypes behoren conform het specifieke model.
     *
     * @return de package waartoe objecttypes behoren conform het specifieke model.
     */
    protected abstract GeneratiePackage getModelSpecifiekePackageVoorObjectTypes();

    /**
     * Retourneert de package waartoe groepen behoren conform het specifieke model.
     *
     * @return de package waartoe groepen behoren conform het specifieke model.
     */
    protected abstract GeneratiePackage getModelSpecifiekePackageVoorGroepen();

    /**
     * Retourneert de package waartoe tuples behoren conform het specifieke model.
     *
     * @return de package waartoe groepen behoren conform het specifieke model.
     */
    protected abstract GeneratiePackage getModelSpecifiekePackageVoorTuples();

    /**
     * Retourneert de model specifieke combinatie van een 'prefix' en een 'suffix' welke aan de naam van een element
     * toegevoegd dienen te worden om te komen tot de model specifieke naam van het opgegeven object type.
     *
     * @param element het objecttype waarvoor de model specifieke pre- en suffix moeten worden gegenereerd.
     * @return een combinatie van een prefix string en suffix string.
     */
    protected abstract PrefixSuffix getModelSpecifiekePrefixSuffixVoorObjectType(final ObjectType element);

    /**
     * Retourneert de model specifieke combinatie van een 'prefix' en een 'suffix' welke aan de naam van een element
     * toegevoegd dienen te worden om te komen tot de model specifieke naam van de opgegeven groep.
     *
     * @param element de groep waarvoor de model specifieke pre- en suffix moeten worden gegenereerd.
     * @return een combinatie van een prefix string en suffix string.
     */
    protected abstract PrefixSuffix getModelSpecifiekePrefixSuffixVoorGroep(final Groep element);

    /**
     * Retourneert de model specifieke combinatie van een 'prefix' en een 'suffix' welke aan de naam van een element
     * toegevoegd dienen te worden om te komen tot de model specifieke naam van de opgegeven tuple.
     *
     * @param element de tuple waarvoor de model specifieke pre- en suffix moeten worden gegenereerd.
     * @return een combinatie van een prefix string en suffix string.
     */
    protected abstract PrefixSuffix getModelSpecifiekePrefixSuffixVoorTuple(final Tuple element);

    /**
     * {@inheritDoc}
     */
    @Override
    protected final String bepaalPackageNaamVoorElement(final GeneriekElement element) {
        final String soortCode = element.getSoortElement().getCode();
        final BmrElementSoort soort = BmrElementSoort.getBmrElementSoortBijCode(soortCode);

        final String packageNaam;
        if (new AlgemeneNaamgevingStrategie().heeftElementEenAlgemeneNaam(element)) {
            packageNaam = attribuutWrapperNaamgevingStrategie.bepaalPackageNaamVoorElement(element);
        } else {
            switch (soort) {
                case OBJECTTYPE:
                    packageNaam =
                            concatPackages(getModelSpecifiekePackageVoorObjectTypes(), getSchemaVoorElement(element));
                    break;
                case GROEP:
                    packageNaam =
                            concatPackages(getModelSpecifiekePackageVoorGroepen(), getSchemaVoorElement(element));
                    break;
                case TUPLE:
                    packageNaam =
                            concatPackages(getModelSpecifiekePackageVoorTuples(), getSchemaVoorElement(element));
                    break;
                default:
                    throw new IllegalArgumentException(
                            "Onbekend element soort bij packagenaam generatie: " + soortCode);
            }
        }
        return packageNaam;
    }

    /** {@inheritDoc} */
    @SuppressWarnings("BC_UNCONFIRMED_CAST")
    // Casts worden gedaan na checken van 'soort' in het element en deze soort geeft juist het type aan.
    @Override
    protected final String bepaalJavaTypeNaamVoorElement(final GeneriekElement element) {
        final String soortCode = element.getSoortElement().getCode();
        final BmrElementSoort soort = BmrElementSoort.getBmrElementSoortBijCode(soortCode);

        final String javaTypeNaam;
        if (new AlgemeneNaamgevingStrategie().heeftElementEenAlgemeneNaam(element)) {
            javaTypeNaam = attribuutWrapperNaamgevingStrategie.bepaalJavaTypeNaamVoorElement(element);
        } else {
            final PrefixSuffix prefixSuffix;
            // Note: Deze casts worden door Findbugs als 'unconfirmed cast' gezien. Dat klopt op zich,
            // maar altijd als ie hier komt zal het meegegeven generiek element in werkelijkheid
            // een object type of groep object zijn. Enigszins discutabel, omdat je vertrouwd op de aanroeper.
            switch (soort) {
                case OBJECTTYPE:
                    prefixSuffix = getModelSpecifiekePrefixSuffixVoorObjectType((ObjectType) element);
                    break;
                case GROEP:
                    prefixSuffix = getModelSpecifiekePrefixSuffixVoorGroep((Groep) element);
                    break;
                case TUPLE:
                    prefixSuffix = getModelSpecifiekePrefixSuffixVoorTuple((Tuple) element);
                    break;
                default:
                    throw new IllegalArgumentException(
                        "Onbekend/Onverwacht element soort bij javatype generatie: " + soortCode);
            }
            javaTypeNaam = String.format("%s%s%s", prefixSuffix.prefix,
                           JavaGeneratieUtil.cleanUpInvalidJavaCharacters(element.getIdentCode()), prefixSuffix.suffix);
        }
        return javaTypeNaam;
    }

    /**
     * Inner class die een prefix string en een suffix string combineert. Deze prefix en suffix worden aan een naam
     * toegevoegd om zo tot de model specifieke naam te komen.
     */
    protected static class PrefixSuffix {

        private final String prefix;
        private final String suffix;

        /**
         * Constructor die direct de prefix en suffix zet.
         *
         * @param prefix de prefix string.
         * @param suffix de suffix string.
         */
        protected PrefixSuffix(final String prefix, final String suffix) {
            this.prefix = prefix;
            this.suffix = suffix;
        }

        public String getPrefix() {
            return prefix;
        }

        public String getSuffix() {
            return suffix;
        }
    }

}

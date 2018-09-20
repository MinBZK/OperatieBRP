/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator.model.dal;

import nl.bzk.brp.ecore.bmr.Attribuut;
import nl.bzk.brp.ecore.bmr.AttribuutType;
import nl.bzk.brp.ecore.bmr.Domein;
import nl.bzk.brp.ecore.bmr.ObjectType;
import nl.bzk.brp.ecore.bmr.Schema;


public class JavaPackageModelBuilder {

    private static final int OPERATIONELE_LAAG = 1751;
    private Domein           domein;

    public JavaPackageModelBuilder(final Domein domein) {
        this.domein = domein;
    }

    public JavaPackage buildJavaPackage(final String packagePrefix) {
        /*
         * Het root-package waar alle overige packages onder komen.
         */
        JavaDomeinModel root = new JavaDomeinModel(packagePrefix);
        verzamelTypes(root);
        relateerTypes(root);
        voegPropertiesToe(root);
        return root;
    }

    /**
     * Doe een eerste scan op atribuut- en object typen om een volledige lijst van typen aan te leggen.
     *
     * @param root Het beginpunt van het model.
     */
    private void verzamelTypes(final JavaDomeinModel root) {
        /*
         * Interface en persistent object implementatie voor de Factory voor domein objecten.
         */
        FactoryInterface factoryInterface = (FactoryInterface) root.addType(new FactoryInterface());
        factoryInterface.setPackage(root);
        root.addType(new PersistentObjectFactory(factoryInterface));

        for (Schema schema : domein.getSchemas()) {
            /*
             * Maak vier packages voor de vier soorten domein artifacts voor het dubbele Generation Gap Pattern.
             */
            JavaPackage interfacePackage = root.addSubPackage(new JavaPackage(schema.getNaam()));
            JavaPackage validationPackage = interfacePackage.addSubPackage(new JavaPackage("validatie"));
            interfacePackage.addSubPackage(new JavaPackage("basis"));
            JavaPackage persistentPackage = interfacePackage.addSubPackage(new JavaPackage("persistent"));
            persistentPackage.addSubPackage(new JavaPackage("basis"));
            /*
             * DatabaseSchema package bewaren voor straks.
             */
            root.putSchema(schema.getNaam(), interfacePackage);
            /*
             * Bewaar attribuuttypen in het rootpackage, waar ze beschikbaar blijven voor lookup.
             */
            for (AttribuutType attribuutType : schema.getWerkVersie().getAttribuutTypes()) {
                if (attribuutType.getLaag() != OPERATIONELE_LAAG) {
                    continue;
                }
                StandardJavaType standardJavaType = new StandardJavaType(attribuutType);
                root.putType(attribuutType, standardJavaType);
                ConstraintDefinitie constraintDefinitie = standardJavaType.getConstraintDefinitie();
                if (constraintDefinitie != null) {
                    validationPackage.addType(constraintDefinitie);
                }
            }
            /*
             * Voeg interfaces en enumeraties toe aan het interface package en bewaar ze in het rootpackage, zodat de
             * typen van attributen daar later in kunnen worden opgezocht.
             */
            for (ObjectType objectType : schema.getWerkVersie().getLogischeObjectTypes()) {
                if (objectType.getLaag() != OPERATIONELE_LAAG) {
                    continue;
                }
                AbstractType type;
                if (objectType.isEnumeratie()) {
                    type = interfacePackage.addType(new Enumeratie(objectType));
                    if (objectType.hasCode()) {
                        type.addProperty(new BeanProperty("Code", new StandardJavaType("java.lang.String")));
                    }
                    type.addProperty(new BeanProperty("Naam", new StandardJavaType("java.lang.String")));
                } else {
                    type = interfacePackage.addType(new Interface(objectType));
                }
                root.putType(objectType, type);
            }
        }
    }

    /**
     * Relateer de verschillende artifacts op de juiste manier aan elkaar.
     *
     * @param root Het beginpunt van het model.
     */
    private void relateerTypes(final JavaDomeinModel root) {
        for (Schema schema : domein.getSchemas()) {
            /*
             * Haal de packages voor dit schema terug, zodat we de artifacts aan het juiste package kunnen toevoegen.
             */
            JavaPackage interfacePackage = root.getSchema(schema.getNaam());
            /*
             * Nu de overige soorten Java artifacts maken en toevoegen aan de juiste packages.
             */
            for (ObjectType objectType : schema.getWerkVersie().getLogischeObjectTypes()) {
                if (objectType.getLaag() != OPERATIONELE_LAAG) {
                    continue;
                }
                if (objectType.isEnumeratie()) {
                    /*
                     * Voor enumeraties hoeven we nu niets meer te doen.
                     */
                } else {
                    AbstractDomeinObject basisInterfaceType =
                        (AbstractDomeinObject) interfacePackage.getSubPackage("basis").addType(
                                new BasisInterface(objectType));
                    AbstractDomeinObject basisPersistentClass =
                        (AbstractDomeinObject) interfacePackage.getSubPackage("persistent").getSubPackage("basis")
                                .addType(new BasisPersistentClass(objectType));
                    AbstractDomeinObject persistentClass =
                        (AbstractDomeinObject) interfacePackage.getSubPackage("persistent").addType(
                                new PersistentClass(objectType));
                    /*
                     * Het basisinterface type opzoeken dat we eerder hadden toegevoegd aan het rootpackage. Hebben we
                     * nodig omdat het interface type daarvan extend en de persistent class het implementeert.
                     */
                    Interface interfaceType = (Interface) root.getType(objectType);
                    interfaceType.setImplementation((PersistentClass) persistentClass);
                    interfaceType.setSuperType(basisInterfaceType);
                    basisPersistentClass.setInterface(basisInterfaceType);
                    persistentClass.setSuperType(basisPersistentClass);
                    persistentClass.setInterface(interfaceType);
                    /*
                     * Set het (logische) supertype, als dat er is.
                     */
                    if (objectType.getSuperType() != null) {
                        Interface superInterface = (Interface) root.getType(objectType.getSuperType());
                        basisInterfaceType.setSuperType(superInterface);
                        basisPersistentClass.setSuperType(superInterface.getImplementation());
                    }
                }
            }
        }
    }

    /**
     * Voeg properties toe aan alle verzamelde objecttypes.
     *
     * @param root Het beginpunt van het model.
     */
    private void voegPropertiesToe(final JavaDomeinModel root) {
        for (Schema schema : domein.getSchemas()) {
            for (ObjectType objectType : schema.getWerkVersie().getLogischeObjectTypes()) {
                if (objectType.getLaag() != OPERATIONELE_LAAG) {
                    continue;
                }
                if (objectType.isEnumeratie()) {
                    /*
                     * Voor enumeraties hoeven we nu niets meer te doen.
                     */
                } else {
                    /*
                     * Nu attributen toevoegen aan het basisinterface. De andere artifacts kunnen ze daar
                     * vinden.
                     */
                    Interface interfaceType = (Interface) root.getType(objectType);
                    BasisInterface basisInterfaceType = (BasisInterface) interfaceType.getSuperType();
                    for (Attribuut attribuut : objectType.getAttributen()) {
                        /*
                         * Attributen kunnen object type of attribuuttype zijn. Die hebben we in de eerste loop
                         * verzameld in de type map.
                         */
                        AbstractType type = root.getType(attribuut.getType());
                        BeanProperty property = new BeanProperty(attribuut, type);
                        if (type.isEntiteit()) {
                            /*
                             * Deze property wordt een OneToMany attribuut in het entiteittype van deze property.
                             */
                            ((Interface) type).getSuperType().addProperty(new BeanProperty(interfaceType, property));
                        }
                        /*
                         * Aan het basisInterfaceType toevoegen.
                         */
                        basisInterfaceType.addProperty(property);
                    }
                }
            }
        }
    }
}

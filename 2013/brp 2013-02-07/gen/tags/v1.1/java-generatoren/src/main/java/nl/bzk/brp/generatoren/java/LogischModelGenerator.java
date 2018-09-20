/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java;

import java.util.ArrayList;
import java.util.List;

import nl.bprbzk.brp.generatoren.rapportage.GeneratorNaam;
import nl.bzk.brp.generatoren.algemeen.basis.GeneratorConfiguratie;
import nl.bzk.brp.generatoren.algemeen.common.BmrLaag;
import nl.bzk.brp.generatoren.algemeen.common.GeneratieUtil;
import nl.bzk.brp.generatoren.algemeen.dataaccess.BmrElementFilterObject;
import nl.bzk.brp.generatoren.java.model.JavaAccessorFunctie;
import nl.bzk.brp.generatoren.java.model.JavaInterface;
import nl.bzk.brp.generatoren.java.model.JavaType;
import nl.bzk.brp.generatoren.java.naamgeving.LogischModelNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.NaamgevingStrategie;
import nl.bzk.brp.generatoren.java.writer.JavaWriter;
import nl.bzk.brp.metaregister.model.Attribuut;
import nl.bzk.brp.metaregister.model.GeneriekElement;
import nl.bzk.brp.metaregister.model.Groep;
import nl.bzk.brp.metaregister.model.ObjectType;
import org.springframework.stereotype.Component;

/**
 * Generator die de Java Interfaces genereert voor het logisch model.
 * Tot het logisch java model behoren alle objecttypen en groepen waarvan het vlaggetje inSet in het BMR op 'Ja' staat.
 */
@Component("logischModelJavaGenerator")
public class LogischModelGenerator extends AbstractJavaGenerator {

    private NaamgevingStrategie naamgevingStrategie = new LogischModelNaamgevingStrategie();

    /** {@inheritDoc} */
    @Override
    public void genereer(final GeneratorConfiguratie generatorConfiguratie) {
        JavaWriter<JavaInterface> writer = javaWriterFactory(generatorConfiguratie, JavaInterface.class);

        final List<JavaInterface> gegenereerdeInterfaces = new ArrayList<JavaInterface>();
        gegenereerdeInterfaces.addAll(writer.genereerEnSchrijfWeg(genereerLogischeObjectTypes(), this));
        gegenereerdeInterfaces.addAll(writer.genereerEnSchrijfWeg(genereerLogischeGroepen(), this));
        rapporteerOverGegenereerdeJavaTypen(gegenereerdeInterfaces);
    }

    @Override
    public GeneratorNaam getGeneratorNaamVoorRapportage() {
        return GeneratorNaam.LOGISCH_MODEL_JAVA_GENERATOR;
    }

    /**
     * Genereert de Logische Object interfaces.
     * @return Lijst van gegenereerde java interfaces.
     */
    private List<JavaInterface> genereerLogischeObjectTypes() {
        BmrElementFilterObject filter = new BmrElementFilterObject(BmrLaag.LOGISCH);
        filter.setSoortInhoud('D');

        // Haal alle objecttypen op
        final List<ObjectType> objectTypen = getBmrDao().getObjectTypen(filter);

        // Genereer voor de 'logische' objecttypen de benodigde Logische Model interfaces.
        List<JavaInterface> objectInterfaces = new ArrayList<JavaInterface>();
        for (ObjectType objectType : objectTypen) {
            if (behoortTotJavaLogischModel(objectType)) {
                objectInterfaces.add(bouwObjectInterface(objectType));
            }
        }
        return objectInterfaces;
    }

    /**
     * Bouwt een nieuwe Java {@link nl.bzk.brp.generatoren.java.model.JavaInterface}, welke de voor het opgegeven
     * {@link nl.bzk.brp.metaregister.model.ObjectType} benodigde informatie aangaande methodes, javadoc etc. bevat.
     *
     * @param objectType het objectType waarvoor een Java Interface gegenereerd moet worden.
     * @return een {@link nl.bzk.brp.generatoren.java.model.JavaInterface} met daarin de voor het objectType geldende
     *         informatie m.b.t. methodes, javadoc etc.
     */
    private JavaInterface bouwObjectInterface(final ObjectType objectType) {
        final JavaInterface javaInterface =
            new JavaInterface(naamgevingStrategie.getJavaTypeVoorElement(objectType),
                bouwJavadocVoorElement(objectType));

        //Is dit een sub type? Zorg dan voor een uitbreiding op het bovenliggende super type.
        if (objectType.getSuperType() != null) {
            javaInterface.voegSuperInterfaceToe(
                    naamgevingStrategie.getJavaTypeVoorElement(objectType.getSuperType()));
        }

        javaInterface.voegSuperInterfaceToe(JavaType.OBJECT_TYPE);

        voegGettersToeVoorObjectType(objectType, javaInterface);
        voegGettersToeVoorInverseAssociaties(objectType, javaInterface);
        return javaInterface;
    }

    /**
     * Voegt interface functies toe voor inverse assocaties van dit object type.
     *
     * @param objectType Het object type dat inverse associaties kan hebben.
     * @param javaInterface De java interface waar functies aan toegevoegd worden.
     */
    private void voegGettersToeVoorInverseAssociaties(final ObjectType objectType,
        final JavaInterface javaInterface)
    {
        final List<Attribuut> inverseAttrVoorObjectType = getBmrDao().getInverseAttributenVoorObjectType(objectType);

        for (Attribuut inverseAttr : inverseAttrVoorObjectType) {
            if (this.behoortTotJavaLogischModel(inverseAttr.getObjectType())) {
                final JavaType inverseType = naamgevingStrategie.getJavaTypeVoorElement(inverseAttr.getObjectType());
                final JavaType javaType = new JavaType(JavaType.COLLECTION, inverseType, true);

                final JavaAccessorFunctie getter = new JavaAccessorFunctie(
                        inverseAttr.getInverseAssociatieIdentCode(), javaType);
                genereerInverseAssociatieGetterJavaDoc(getter, objectType, inverseAttr);
                javaInterface.voegFunctieToe(getter);
            }
        }
    }

    /**
     * Voegt methodes toe aan de logische interface; te weten getter methodes voor alle attributen/groepen
     * in het objecttype waar de logische interface voor wordt gegenereerd.
     *
     * @param objectType Het objecttype waar methodes (getters) aan moeten worden toegevoegd.
     * @param javaInterface De Java interface waar deze getters aan toegevoegd moeten worden.
     */
    private void voegGettersToeVoorObjectType(final ObjectType objectType, final JavaInterface javaInterface) {
        //Itereer over de groepen en maak java members aan
        final List<Groep> groepenVoorObjectType = getBmrDao().getGroepenVoorObjectType(objectType);

        for (Groep groep : groepenVoorObjectType) {
            if (behoortTotJavaLogischModel(groep)) {
                //Identiteit groep slaan we plat.
                if (IDENTITEIT.equals(groep.getIdentCode())) {
                    //Er kunnen attributen in de identiteit groep zitten die in het gegevens set zitten. (inSet = 'Ja')
                    final List<Attribuut> attributen = getBmrDao().getAttributenVanGroep(groep);
                    for (Attribuut attribuut : attributen) {
                        if (behoortTotJavaLogischModel(attribuut)) {
                            final GeneriekElement attribuutType = attribuut.getType();
                            final JavaType javaTypeVoorVeld =
                                naamgevingStrategie.getJavaTypeVoorElement(attribuutType);

                            //Genereer getter voor veld.
                            final JavaAccessorFunctie getter =
                                new JavaAccessorFunctie(attribuut.getIdentCode(), javaTypeVoorVeld);
                            genereerGetterJavaDoc(getter, objectType, attribuut);
                            javaInterface.voegFunctieToe(getter);
                        }
                    }
                } else {
                    final JavaType javaTypeVoorVeld = naamgevingStrategie.getJavaTypeVoorElement(groep);

                    //Getter voor attribuut van object type
                    final JavaAccessorFunctie getterVoorVeld =
                        new JavaAccessorFunctie(groep.getIdentCode(),
                            javaTypeVoorVeld);
                    genereerGetterJavaDoc(getterVoorVeld, objectType, groep);
                    javaInterface.voegFunctieToe(getterVoorVeld);
                }
            }
        }
    }

    /**
     * Genereert de Logische Groep interfaces.
     *
     * @return Lijst van gegenereerde java interfaces.
     */
    private List<JavaInterface> genereerLogischeGroepen() {
        // Haal alle groepen op
        final List<Groep> groepen = getBmrDao().getGroepen();
        // Genereer voor de 'logische' groepen de benodigde Logische Model interfaces.
        final List<JavaInterface> groepInterfaces = new ArrayList<JavaInterface>();
        for (Groep groep : groepen) {
            //Let op: Identiteit groepen worden uitgeschreven, dus hebben geen aparte logische interface.
            //We genereren geen groep interfaces voor groepen die behoren tot een stamgegeven.
            if (behoortTotJavaLogischModel(groep)
                && !IDENTITEIT.equals(groep.getNaam())
                && 'D' == groep.getObjectType().getSoortInhoud())
            {
                groepInterfaces.add(bouwGroepInterface(groep));
            }
        }
        return groepInterfaces;
    }

    /**
     * Bouwt de interface voor de opgegeven groep, inclusief imports en super interfaces. Daarnaast worden ook de
     * methodes toegevoegd voor alle attributen in de groep.
     *
     * @param groep de groep waarvoor de interface gebouwd dient te worden.
     * @return de gegenereerde interface.
     */
    private JavaInterface bouwGroepInterface(final Groep groep) {
        final JavaInterface javaInterface = new JavaInterface(naamgevingStrategie.getJavaTypeVoorElement(groep),
                bouwJavadocVoorElement(groep));

        // Zet super interface, inclusief import
        javaInterface.voegSuperInterfaceToe(JavaType.GROEP);

        voegGettersToeVoorGroep(groep, javaInterface);
        return javaInterface;
    }

    /**
     * Voegt methodes toe aan de logische groepsinterface; te weten getter en setter methodes voor alle attributen
     * in de groep waar de logische interface voor wordt gegenereerd.
     *
     * @param groep De groep waar methodes (getters en setters) aan moeten worden toegevoegd.
     * @param javaInterface De Java interface waar deze getters en setters aan toegevoegd moeten worden.
     */
    private void voegGettersToeVoorGroep(final Groep groep, final JavaInterface javaInterface) {
        //Haal attributen van groep op.
        final List<Attribuut> attributen = getBmrDao().getAttributenVanGroep(groep);

        for (Attribuut attribuut : attributen) {
            if (behoortTotJavaLogischModel(attribuut)) {
                final JavaType javaTypeVoorVeld = naamgevingStrategie.getJavaTypeVoorElement(attribuut.getType());
                final String veldNaam = GeneratieUtil.lowerTheFirstCharacter(attribuut.getIdentCode());

                //Maak een accessor aan oftewel een getter.
                final JavaAccessorFunctie getter = new JavaAccessorFunctie(veldNaam, javaTypeVoorVeld);
                genereerGetterJavaDoc(getter, groep, attribuut);
                javaInterface.voegFunctieToe(getter);
            }
        }
    }

}


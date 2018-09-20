/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.generatoren.algemeen.basis.GeneratorConfiguratie;
import nl.bzk.brp.generatoren.algemeen.common.BmrLaag;
import nl.bzk.brp.generatoren.algemeen.dataaccess.BmrElementFilterObject;
import nl.bzk.brp.generatoren.java.model.JavaAccessorFunctie;
import nl.bzk.brp.generatoren.java.model.JavaInterface;
import nl.bzk.brp.generatoren.java.model.JavaType;
import nl.bzk.brp.generatoren.java.naamgeving.HisMomentNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.LogischModelNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.NaamgevingStrategie;
import nl.bzk.brp.generatoren.java.writer.JavaWriter;
import nl.bzk.brp.generatoren.rapportage.GeneratorNaam;
import nl.bzk.brp.metaregister.model.Groep;
import nl.bzk.brp.metaregister.model.ObjectType;
import org.springframework.stereotype.Component;

/**
 * Generator die de Java Interfaces genereert voor HisMoment objecten binnen het logisch model.
 * HisMoment interfaces worden gegenereerd als er minimaal één groep met materiele of formele
 * historie bestaat binnen het object. Deze groepen worden als methods in de HisMoment interfaces
 * toegevoegd.
 *
 */
@Component("hisMomentJavaGenerator")
public class HisMomentGenerator extends AbstractJavaGenerator {

    private NaamgevingStrategie naamgevingStrategie             = new HisMomentNaamgevingStrategie();
    private NaamgevingStrategie logischModelNaamgevingStrategie = new LogischModelNaamgevingStrategie();

    /**
     * {@inheritDoc}
     */
    @Override
    public final void genereer(final GeneratorConfiguratie generatorConfiguratie) {
        final JavaWriter<JavaInterface> writer = javaWriterFactory(generatorConfiguratie, JavaInterface.class);

        final List<JavaInterface> gegenereerdeInterfaces = new ArrayList<>();
        final List<JavaInterface> logischeObjectTypes = genereerLogischeObjectTypes();
        voegGeneratedAnnotatiesToe(logischeObjectTypes, generatorConfiguratie);
        gegenereerdeInterfaces.addAll(writer.genereerEnSchrijfWeg(logischeObjectTypes, this));

        rapporteerOverGegenereerdeJavaTypen(gegenereerdeInterfaces);
    }

    @Override
    public final GeneratorNaam getGeneratorNaamVoorRapportage() {
        return GeneratorNaam.HIS_MOMENT_JAVA_GENERATOR;
    }

    /**
     * Genereert de Logische Object interfaces.
     *
     * @return Lijst van gegenereerde java interfaces.
     */
    private List<JavaInterface> genereerLogischeObjectTypes() {
        final BmrElementFilterObject filter = new BmrElementFilterObject(BmrLaag.LOGISCH);
        filter.setSoortInhoud('D');

        // Haal alle objecttypen op
        final List<ObjectType> objectTypen = getBmrDao().getObjectTypen(filter);

        // Genereer voor de 'logische' objecttypen de benodigde Logische Model interfaces.
        final List<JavaInterface> objectInterfaces = new ArrayList<>();
        for (ObjectType objectType : objectTypen) {
            if (behoortTotJavaLogischModel(objectType) && heeftGroepMetHistorieExclusiefIdentiteitsgroepen(objectType)
                && !isPersoonIndicatieObject(objectType) && isKernObject(objectType))
            {
                objectInterfaces.add(bouwObjectInterface(objectType));
            }
        }
        return objectInterfaces;
    }

    private boolean isKernObject(final ObjectType objectType) {
        boolean isKernObject = "kern".equalsIgnoreCase(objectType.getSchema().getNaam());
        // Specifieke uitzondering voor Persoon \ Afnemerindicatie.
        isKernObject |= "Persoon \\ Afnemerindicatie".equals(objectType.getNaam());
        return isKernObject;
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

        javaInterface.voegSuperInterfaceToe(bepaalSuperInterface(objectType));

        voegGettersToeVoorObjectType(objectType, javaInterface);
        return javaInterface;
    }

    private JavaType bepaalSuperInterface(final ObjectType objectType) {
        return logischModelNaamgevingStrategie.getJavaTypeVoorElement(objectType);
    }

    /**
     * Voegt methodes toe aan de logische interface; te weten getter methodes voor alle attributen/groepen
     * in het objecttype waar de logische interface voor wordt gegenereerd.
     *
     * @param objectType    Het objecttype waar methodes (getters) aan moeten worden toegevoegd.
     * @param javaInterface De Java interface waar deze getters aan toegevoegd moeten worden.
     */
    private void voegGettersToeVoorObjectType(final ObjectType objectType, final JavaInterface javaInterface) {
        //Itereer over de groepen en maak java members aan
        final List<Groep> groepenVoorObjectType = getBmrDao().getGroepenVoorObjectType(objectType);

        for (Groep groep : groepenVoorObjectType) {
            if (behoortTotJavaLogischModel(groep) && isGroepMetHisMomentRelevanteHistorie(groep)) {
                final JavaType javaTypeVoorVeld = naamgevingStrategie.getJavaTypeVoorElement(groep);

                final JavaAccessorFunctie getterVoorVeld =
                    new JavaAccessorFunctie(groep.getIdentCode(),
                        javaTypeVoorVeld);
                genereerGetterJavaDoc(getterVoorVeld, objectType, groep);
                javaInterface.voegFunctieToe(getterVoorVeld);
            }
        }
    }
}


/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java;

import java.util.List;
import nl.bzk.brp.generatoren.algemeen.basis.AbstractGenerator;
import nl.bzk.brp.generatoren.java.model.Constructor;
import nl.bzk.brp.generatoren.java.model.JavaAccessModifier;
import nl.bzk.brp.generatoren.java.model.JavaAccessorFunctie;
import nl.bzk.brp.generatoren.java.model.JavaKlasse;
import nl.bzk.brp.generatoren.java.model.JavaType;
import nl.bzk.brp.generatoren.java.model.JavaVeld;
import nl.bzk.brp.generatoren.java.model.annotatie.AnnotatieWaardeParameter;
import nl.bzk.brp.generatoren.java.model.annotatie.JavaAnnotatie;
import nl.bzk.brp.generatoren.java.naamgeving.AlgemeneNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.AttribuutWrapperNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.NaamgevingStrategie;
import nl.bzk.brp.generatoren.java.util.GeneratiePackage;
import nl.bzk.brp.metaregister.model.Attribuut;
import nl.bzk.brp.metaregister.model.AttribuutType;
import nl.bzk.brp.metaregister.model.ObjectType;
import org.springframework.stereotype.Component;

/**
 * Genereert klassen voor de stamgegevens, waarbij vanuit Java oogpunt alleen de (conform BMR benaming) dynamische stamgegevens worden beschouwd als
 * werkelijke stamgegevens. Hierbij valt te denken aan objecten zoals Partij en Land; objecten die dus wel nog runtime aangepast, toegevoegd en verwijderd
 * kunnen worden. In dit geval zijn deze klassen aanpasbaar, zij hebben dus geen {@link org.hibernate.annotations.Immutable}-annotatie. In deze generator
 * worden niet-aanpasbare klassen gegenereerd, zij hebben dus een {@link org.hibernate.annotations.Immutable}-annotatie. <br/> Deze klassen zijn hibernate
 * entities, dus de ORM annotaties worden ook gegenereerd. <p> Merk op dat de, vanuit het BMR gezien, statische stamgegevens binnen de Java generatoren
 * worden gegenereerd als enumeraties. Zie hiervoor de {@link nl.bzk.brp.generatoren.java.StatischeStamgegevensGenerator}. </p>
 */
@Component("dynamischeStamgegevensJavaGenerator")
public class DynamischeStamgegevensGenerator extends AbstractDynamischeStamgegevensGenerator {

    private final NaamgevingStrategie naamgevingStrategie        = new AlgemeneNaamgevingStrategie();
    private final NaamgevingStrategie wrapperNaamgevingStrategie = new AttribuutWrapperNaamgevingStrategie();

    @Override
    protected List<ObjectType> haalDynamischeStamgegevensObjectTypenOp() {
        final List<ObjectType> stamgegevensObjectTypen = getBmrDao().getDynamischeStamgegevensObjectTypen();
        final ObjectType objectTypeVoorElement = getBmrDao().getElement(AbstractGenerator.ID_ELEMENT, ObjectType.class);
        stamgegevensObjectTypen.add(objectTypeVoorElement);
        return stamgegevensObjectTypen;
    }

    @Override
    public final NaamgevingStrategie getNaamgevingStrategie() {
        return naamgevingStrategie;
    }

    @Override
    public final NaamgevingStrategie getWrapperNaamgevingStrategie() {
        return wrapperNaamgevingStrategie;
    }

    @Override
    protected final void genereerExtraKlasseAnnotatiesVoorStamgegeven(final ObjectType objectType, final JavaKlasse clazz,
        final List<JavaAnnotatie> klasseAnnotaties)
    {
        klasseAnnotaties.add(new JavaAnnotatie(JavaType.ENTITY));

        // Een entity listener die voorkomt dat stamgegevens door de BRP worden aangemaakt, aangepast of verwijderd:
        klasseAnnotaties.add(new JavaAnnotatie(JavaType.ENTITY_LISTENERS, new AnnotatieWaardeParameter("value", "StamgegevenEntityListener.class", true)));
        clazz.voegExtraImportsToe(new JavaType("StamgegevenEntityListener", GeneratiePackage.BRP_MODEL_BASIS_PACKAGE.getPackage()));
        klasseAnnotaties.add(new JavaAnnotatie(JavaType.IMMUTABLE));
        //Cache annotatie
        klasseAnnotaties.add(new JavaAnnotatie(JavaType.CACHE, new AnnotatieWaardeParameter("usage", JavaType.CACHE_CONCURRENCY_STRATEGIE, "READ_ONLY")));
    }

    @Override
    protected final JavaAccessModifier bepaalDeAccessModifierVoorIdVeld() {
        return JavaAccessModifier.PROTECTED;
    }

    @Override
    protected final void voegStandaardLegeConstructorToeAanKlasse(final JavaKlasse clazz) {
        //Een stamgegeven komt uit de database dus het instantieren vanuit de code heeft geen zin,
        //dus we genereren een default protected constructor.
        final Constructor constructor = new Constructor(JavaAccessModifier.PROTECTED, clazz);
        constructor.setJavaDoc("Constructor is protected, want de BRP zal geen stamgegevens beheren.");
        clazz.voegConstructorToe(constructor);
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.SortedSet;

import nl.bprbzk.brp.generatoren.rapportage.GeneratorNaam;
import nl.bzk.brp.generatoren.algemeen.basis.GeneratorConfiguratie;
import nl.bzk.brp.generatoren.algemeen.common.BmrElementSoort;
import nl.bzk.brp.generatoren.algemeen.common.GeneratieUtil;
import nl.bzk.brp.generatoren.java.model.EnumeratieWaarde;
import nl.bzk.brp.generatoren.java.model.JavaAccessorFunctie;
import nl.bzk.brp.generatoren.java.model.JavaEnumeratie;
import nl.bzk.brp.generatoren.java.model.JavaFunctieParameter;
import nl.bzk.brp.generatoren.java.model.JavaType;
import nl.bzk.brp.generatoren.java.model.JavaVeld;
import nl.bzk.brp.generatoren.java.naamgeving.AlgemeneNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.NaamgevingStrategie;
import nl.bzk.brp.generatoren.java.util.JavaGeneratieUtil;
import nl.bzk.brp.generatoren.java.writer.JavaWriter;
import nl.bzk.brp.metaregister.model.Attribuut;
import nl.bzk.brp.metaregister.model.ExtraWaarde;
import nl.bzk.brp.metaregister.model.ObjectType;
import nl.bzk.brp.metaregister.model.Tuple;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Genereert Java enumeraties voor de (vanuit het BMR gezien) statische stamgegevens. Hier bij valt te denken aan
 * objecten/stamgegevens zoals Geslachtsaanduiding welke niet runtime nog gewijzigd, verwijderd of toegevoegd kunnen
 * worden.
 */
@Component("statischeStamgegevensJavaGenerator")
public class StatischeStamgegevensGenerator extends AbstractJavaGenerator {

    private final NaamgevingStrategie naamgevingStrategie = new AlgemeneNaamgevingStrategie();

    @Override
    public void genereer(final GeneratorConfiguratie generatorConfiguratie) {
        if (generatorConfiguratie.isGenerationGapPatroon()) {
            throw new IllegalArgumentException("Generation gap patroon wordt niet ondersteund voor "
                + "enumeratie generatie.");
        }

        final List<JavaEnumeratie> enumeraties = new ArrayList<JavaEnumeratie>();
        enumeraties.addAll(bouwJavaEnumeratiesVoorStatischeStamgegevens());

        final JavaWriter<JavaEnumeratie> enumWriter = javaWriterFactory(generatorConfiguratie, JavaEnumeratie.class);
        final List<JavaEnumeratie> gegenereerdeEnums = enumWriter.genereerEnSchrijfWeg(enumeraties, this);
        rapporteerOverGegenereerdeJavaTypen(gegenereerdeEnums);
    }

    @Override
    public GeneratorNaam getGeneratorNaamVoorRapportage() {
        return GeneratorNaam.STATISCHE_STAMGEGEVENS_JAVA_GENERATOR;
    }

    /**
     * Bouwt enumeraties voor (vanuit het BMR gezien) statische stamgegevens, waarbij eerst de betreffende objecttypes
     * worden opgehaald en dan de enumeraties hiervoor worden gegenereerd.
     *
     * @return een collectie van enumeraties.
     */
    private Collection<? extends JavaEnumeratie> bouwJavaEnumeratiesVoorStatischeStamgegevens() {
        final List<JavaEnumeratie> enumeraties = new ArrayList<JavaEnumeratie>();

        //Haal alle statische stamgegevens object typen op.
        final List<ObjectType> stamgegevensObjectTypen = getBmrDao().getStatischeStamgegevensObjectTypen();

        //Itereer over alle objecttypen en maak java- enumeraties aan.
        for (ObjectType objectType : stamgegevensObjectTypen) {
            enumeraties.add(genereerEnumeratieVoorObjectType(objectType));
        }
        return enumeraties;
    }

    /**
     * Genereert een Java Enumeratie voor een statisch stamgegeven object type.
     *
     * @param objectType Het object type waarvoor een enumeratie wordt gegenereerd.
     * @return Een java enumeratie.
     */
    private JavaEnumeratie genereerEnumeratieVoorObjectType(final ObjectType objectType) {
        final JavaEnumeratie javaEnum = new JavaEnumeratie(naamgevingStrategie.getJavaTypeVoorElement(objectType),
            bouwJavadocVoorElement(objectType));

        //De attributen moeten in de enum opgenomen worden als velden
        final List<Attribuut> attributen = getBmrDao().getAttributenVanObjectType(objectType);
        for (Attribuut attribuut : attributen) {
            //Met id's doen we niets omdat we in Java Enum ordinals gebruiken.
            if (!isIdAttribuut(attribuut)) {
                final JavaVeld javaVeld;

                //Het type attribuut kan een attribuut type zijn of weer een objecttype.
                final BmrElementSoort soort =
                    BmrElementSoort.getBmrElementSoortVoorCode(attribuut.getType().getSoortElement().getCode());
                switch (soort) {
                    case OBJECTTYPE:
                        javaVeld = genereerJavaVeldVoorObjectType(attribuut);
                        break;
                    case ATTRIBUUTTYPE:
                        javaVeld = genereerJavaVeldVoorAttribuutType(attribuut);
                        break;
                    default:
                        throw new UnsupportedOperationException("Kan het type attribuut niet verwerken: "
                            + attribuut.getType().getSoortElement().getCode());
                }
                javaVeld.setFinal(true);
                javaEnum.voegVeldToe(javaVeld);

                //Accessor functies voor elk veld
                final JavaAccessorFunctie getter = new JavaAccessorFunctie(javaVeld);
                genereerGetterJavaDoc(getter, objectType, attribuut);
                javaEnum.voegGetterToe(getter);
            }
        }

        //Constructor die alle velden initialiseert
        javaEnum.voegConstructorToe(genereerConstructorVoorEnum(javaEnum));

        //De enum waarden die we kennen, oftewel de Tuples
        genereerEnumWaardenVoorTuples(javaEnum, objectType);
        return javaEnum;
    }

    /**
     * Voor elke tuple dat bij een stamgegeven hoort wordt een enumeratie waarde gegenereerd, met de bijbehorende
     * parameters.
     *
     * @param javaEnum De java enumeratie waar waarden in moeten komen.
     * @param objectType Het object type.
     */
    private void genereerEnumWaardenVoorTuples(final JavaEnumeratie javaEnum, final ObjectType objectType) {
        SortedSet<Tuple> tuples = objectType.getTuples();
        //We beginnen met de Dummy waarde
        javaEnum.voegEnumeratieWaardeToe(genereerDummyEnumWaarde(javaEnum));

        for (Tuple tuple : tuples) {
            String javaDoc = tuple.getBeschrijving();
            if (StringUtils.isBlank(javaDoc)) {
                javaDoc = tuple.getNaam();
            }
            final EnumeratieWaarde enumWaarde = new EnumeratieWaarde(
                JavaGeneratieUtil.genereerNaamVoorEnumWaarde(tuple.getIdentCode()),
                javaDoc + ".");

            for (JavaFunctieParameter param : javaEnum.getConstructoren().get(0).getParameters()) {
                if (param.getNaam().equalsIgnoreCase("code")) {
                    //De code hoeft niet altijd een String te zijn, zou eventueel een ander data type kunnen zijn zoals
                    //een short of een byte.
                    JavaType typeVoorCode = param.getJavaType();
                    String parameterWaarde = tuple.getCode();

                    //JAVA ziet een decimaal literal standaard als een Integer, dus er zijn wat casts nodig voor typen
                    //die kleiner zijn dan een Integer; de Short en de Byte.
                    if (typeVoorCode.getNaam().equalsIgnoreCase("Short")) {
                        //Getver..
                        parameterWaarde = "((short) " + parameterWaarde + ")";
                    } else if (typeVoorCode.getNaam().equalsIgnoreCase("Byte")) {
                        //Getver..
                        parameterWaarde = "((byte) " + parameterWaarde + ")";
                    }
                    enumWaarde.voegConstructorParameterToe(parameterWaarde, typeVoorCode.getNaam().equals("String"));
                } else if (param.getNaam().equalsIgnoreCase("naam")) {
                    enumWaarde.voegConstructorParameterToe(tuple.getNaam(), true);
                } else if (param.getNaam().equalsIgnoreCase("omschrijving")) {
                    enumWaarde.voegConstructorParameterToe(tuple.getBeschrijving(), true);
                } else {
                    //Tuple kent dus een extra waarde, de constructie parameter die we hier nodig hebben heeft als type
                    //een ander statisch stamgegeven. Via de extra waarde van de tuple moeten we erachter komen welke
                    //enumeratie dit is.
                    for (ExtraWaarde extraWaarde : tuple.getExtraWaarden()) {
                        //TODO Jeanot: Waarom zitten er underscores in de naam kolom van naam_extra_waarde tabel.
                        final String naam = extraWaarde.getNaamExtraWaarde().getNaam().replaceAll("_", "");
                        if (naam.equalsIgnoreCase(param.getNaam())) {
                            //De extra waarde gaat dus over deze parameter. Zoek de Tuple van de extra waarde op.

                            //TODO Jeanot: Deze lelijke oplossing vraagt naar wijzigingen in het BMR.
                            List<Attribuut> attributen = this.getBmrDao().getAttributenVanObjectType(objectType);
                            for (Attribuut attribuut : attributen) {
                                // Match attribuut op naam.
                                if (attribuut.getIdentCode().equals(naam)) {
                                    //Haal eerst het object type op, dit kan op basis van id van het type van attribuut.
                                    int objectTypeWaarnaarVerwezenWordtId = attribuut.getType().getId();
                                    ObjectType statischObjectTypeWaarnaarVerwijstWordt = this.getBmrDao().
                                            getElement(objectTypeWaarnaarVerwezenWordtId, ObjectType.class);
                                    SortedSet<Tuple> tupleSortedSet =
                                            statischObjectTypeWaarnaarVerwijstWordt.getTuples();
                                    for (Tuple tuple1 : tupleSortedSet) {
                                        if (String.valueOf(tuple1.getVolgnummerT()).equals(extraWaarde.getWaarde())) {
                                            final JavaType javaType =
                                                    naamgevingStrategie.getJavaTypeVoorElement(
                                                            statischObjectTypeWaarnaarVerwijstWordt);
                                            enumWaarde.voegConstructorParameterToe(javaType.getNaam() + "."
                                                + JavaGeneratieUtil.genereerNaamVoorEnumWaarde(
                                                        tuple1.getIdentCode()), false);
                                        }
                                    }
                                }
                            }
                        } else if (naam.equalsIgnoreCase("HeeftMaterieleHistorie")) {
                            final Boolean waarde = GeneratieUtil.bmrJaNeeNaarBoolean(
                                tuple.getExtraWaarden().iterator().next().getWaarde()
                            );
                            enumWaarde.voegConstructorParameterToe(String.valueOf(waarde), false);
                        }
                    }
                }
            }
            javaEnum.voegEnumeratieWaardeToe(enumWaarde);
        }
    }

    /**
     * Genereert een {@link JavaVeld} instantie op basis van het opgegeven attribuut, wat een objecttype zou moeten
     * zijn.
     *
     * @param attribuut het attribuut waarvoor een JavaVeld instantie moet worden gegenereerd.
     * @return het gegenereerde JavaVeld.
     */
    private JavaVeld genereerJavaVeldVoorObjectType(final Attribuut attribuut) {
        final String veldNaam = GeneratieUtil.lowerTheFirstCharacter(attribuut.getIdentCode());

        //Haal het objecttype op
        final ObjectType objectTypeAttribuut = getBmrDao().getElement(attribuut.getType().getId(), ObjectType.class);
        return new JavaVeld(naamgevingStrategie.getJavaTypeVoorElement(objectTypeAttribuut), veldNaam);
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java;

import com.google.common.collect.Iterables;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import nl.bzk.brp.generatoren.algemeen.basis.GeneratorConfiguratie;
import nl.bzk.brp.generatoren.algemeen.common.BmrElementSoort;
import nl.bzk.brp.generatoren.algemeen.common.GeneratieUtil;
import nl.bzk.brp.generatoren.java.model.EnumeratieWaarde;
import nl.bzk.brp.generatoren.java.model.JavaAccessorFunctie;
import nl.bzk.brp.generatoren.java.model.JavaEnumeratie;
import nl.bzk.brp.generatoren.java.model.JavaFunctieParameter;
import nl.bzk.brp.generatoren.java.model.JavaKlasse;
import nl.bzk.brp.generatoren.java.model.JavaType;
import nl.bzk.brp.generatoren.java.model.JavaVeld;
import nl.bzk.brp.generatoren.java.naamgeving.AlgemeneNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.AttribuutWrapperNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.NaamgevingStrategie;
import nl.bzk.brp.generatoren.java.util.JavaGeneratieUtil;
import nl.bzk.brp.generatoren.java.writer.JavaWriter;
import nl.bzk.brp.generatoren.rapportage.GeneratorNaam;
import nl.bzk.brp.metaregister.model.Attribuut;
import nl.bzk.brp.metaregister.model.ExtraWaarde;
import nl.bzk.brp.metaregister.model.ObjectType;
import nl.bzk.brp.metaregister.model.Tuple;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Genereert Java enumeraties voor de (vanuit het BMR gezien) statische stamgegevens. Hier bij valt te denken aan objecten/stamgegevens zoals
 * Geslachtsaanduiding welke niet runtime nog gewijzigd, verwijderd of toegevoegd kunnen worden.
 */
@Component("statischeStamgegevensJavaGenerator")
public class StatischeStamgegevensGenerator extends AbstractJavaGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatischeStamgegevensGenerator.class);

    private final NaamgevingStrategie algemeneNaamgevingStrategie = new AlgemeneNaamgevingStrategie();
    private       NaamgevingStrategie wrapperNaamgevingStrategie  = new AttribuutWrapperNaamgevingStrategie();

    @Override
    public void genereer(final GeneratorConfiguratie generatorConfiguratie) {
        if (generatorConfiguratie.isGenerationGapPatroon()) {
            throw new IllegalArgumentException("Generation gap patroon wordt niet ondersteund voor "
                + "enumeratie generatie.");
        }

        // Maak de enumeraties aan voor de statische stamgegevens.
        final List<JavaEnumeratie> enumeraties = new ArrayList<>();
        Map<JavaEnumeratie, ObjectType> enumeratieMap = bouwJavaEnumeratiesVoorStatischeStamgegevens();
        // Neem hier alle gemaakte enums over in de enumeraties list, exclusief de expliciete skips.
        // Skip hier en niet al bij het bouwen, aangezien we wel een attribuut variant willen.
        for (Map.Entry<JavaEnumeratie, ObjectType> enumeratieEntry : enumeratieMap.entrySet()) {
            // Skip expliciet het database object stamgegeven. Omdat we die geen tuples kan hebben in het BMR,
            // hebben we hier een aparte generator voor: zie DatabaseObjectGenerator.
            // Specifieke uitzondering voor Regel, aangezien de tuples daarvan (nog) niet in het BMR staan.
            // Specifieke uitzondering voor CatalogusOptie, aangezien de tuples daarvan (nog) niet in het BMR staan.
            if (enumeratieEntry.getValue().getId() != ID_DATABASE_OBJECT
                && enumeratieEntry.getValue().getId() != ID_REGEL
                && enumeratieEntry.getValue().getId() != ID_CATALOGUS_OPTIE
                && enumeratieEntry.getValue().getId() != ID_ELEMENT)
            {
                enumeraties.add(enumeratieEntry.getKey());
            }
        }

        // Maak de klassen aan voor de attributen (wrappers).
        final List<JavaKlasse> klassen = new ArrayList<>();
        klassen.addAll(bouwJavaAttribuutKlassenVoorStatischeStamgegevens(enumeratieMap));

        // Schrijf de enums weg.
        final JavaWriter<JavaEnumeratie> enumWriter = javaWriterFactory(generatorConfiguratie, JavaEnumeratie.class);
        voegGeneratedAnnotatiesToe(enumeraties, generatorConfiguratie);
        final List<JavaEnumeratie> geschrevenEnumeraties = enumWriter.genereerEnSchrijfWeg(enumeraties, this);

        // Schrijf de klassen weg.
        final JavaWriter<JavaKlasse> klasseWriter = javaWriterFactory(generatorConfiguratie, JavaKlasse.class);
        voegGeneratedAnnotatiesToe(klassen, generatorConfiguratie);
        final List<JavaKlasse> geschrevenKlassen = klasseWriter.genereerEnSchrijfWeg(klassen, this);

        // Rapporteer over de resultaten.
        rapporteerOverGegenereerdeJavaTypen(Iterables.concat(geschrevenEnumeraties, geschrevenKlassen));
    }

    @Override
    public GeneratorNaam getGeneratorNaamVoorRapportage() {
        return GeneratorNaam.STATISCHE_STAMGEGEVENS_JAVA_GENERATOR;
    }

    /**
     * Bouwt enumeraties voor (vanuit het BMR gezien) statische stamgegevens, waarbij eerst de betreffende objecttypes worden opgehaald en dan de
     * enumeraties hiervoor worden gegenereerd.
     *
     * @return een map van enumeraties met het achterliggende object type.
     */
    private Map<JavaEnumeratie, ObjectType> bouwJavaEnumeratiesVoorStatischeStamgegevens() {
        final Map<JavaEnumeratie, ObjectType> enumeratieMap = new HashMap<>();

        //Haal alle statische stamgegevens object typen op.
        final List<ObjectType> stamgegevensObjectTypen = getBmrDao().getStatischeStamgegevensObjectTypen();

        //Itereer over alle objecttypen en maak java- enumeraties aan.
        for (ObjectType objectType : stamgegevensObjectTypen) {
            enumeratieMap.put(genereerEnumeratieVoorObjectType(objectType), objectType);
        }
        return enumeratieMap;
    }

    /**
     * Bouwt klassen voor (vanuit het BMR gezien) statische stamgegevens, waarbij attribuut (wrapper) klassen worden gemaakt met als waarde de
     * enumeraties.
     *
     * @param enumeratieMap de gegenereerde enumeraties met bijbehorende object type
     * @return een collectie van klassen.
     */
    private Collection<? extends JavaKlasse> bouwJavaAttribuutKlassenVoorStatischeStamgegevens(
        final Map<JavaEnumeratie, ObjectType> enumeratieMap)
    {
        List<JavaKlasse> klassen = new ArrayList<>();
        for (Map.Entry<JavaEnumeratie, ObjectType> enumeratieEntry : enumeratieMap.entrySet()) {
            klassen.add(genereerAttribuutWrapperKlasse(enumeratieEntry.getValue(), enumeratieEntry.getKey().getType()));
        }
        return klassen;
    }

    /**
     * Genereert een Java Enumeratie voor een statisch stamgegeven object type.
     *
     * @param objectType Het object type waarvoor een enumeratie wordt gegenereerd.
     * @return Een java enumeratie.
     */
    private JavaEnumeratie genereerEnumeratieVoorObjectType(final ObjectType objectType) {
        final JavaEnumeratie javaEnum = new JavaEnumeratie(
            algemeneNaamgevingStrategie.getJavaTypeVoorElement(objectType),
            bouwJavadocVoorElement(objectType));

        //Kan het stamgegeven gesynchroniseerd worden?
        if (objectType.getInBericht() != null && objectType.getInBericht() == 'J') {
            javaEnum.voegSuperInterfaceToe(JavaType.SYNCHRONISEERBAAR_STAMGEGEVEN);
        }

        //De attributen moeten in de enum opgenomen worden als velden
        final List<Attribuut> attributen = getBmrDao().getAttributenVanObjectType(objectType);
        for (Attribuut attribuut : attributen) {
            //Met id's doen we niets omdat we in Java Enum ordinals gebruiken.
            if (!isIdAttribuut(attribuut)) {
                final JavaVeld javaVeld;

                //Het type attribuut kan een attribuut type zijn of weer een objecttype.
                final BmrElementSoort soort =
                    BmrElementSoort.getBmrElementSoortBijCode(attribuut.getType().getSoortElement().getCode());
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

        if (isBestaansPeriodeStamgegeven(objectType)) {
            javaEnum.voegSuperInterfaceToe(JavaType.BESTAANS_PERIODE_STAMGEGEVEN);
            voegBestaansPeriodeVeldenEnAccessorsToe(javaEnum, objectType, wrapperNaamgevingStrategie);
        }

        //Constructor die alle velden initialiseert
        javaEnum.voegConstructorToe(genereerConstructorVoorEnum(javaEnum));

        //De enum waarden die we kennen, oftewel de Tuples
        genereerEnumWaardenVoorTuples(javaEnum, objectType);

        // Voeg de 'Element getElement()' functie toe, alleen voor objecttypen van het KERN schema.
        if (objectType.getSchema().getId() == ID_KERN_SCHEMA) {
            voegGetElementFunctieToe(javaEnum, objectType);
        }

        return javaEnum;
    }

    /**
     * Voor elke tuple dat bij een stamgegeven hoort wordt een enumeratie waarde gegenereerd, met de bijbehorende parameters.
     *
     * @param javaEnum   De java enumeratie waar waarden in moeten komen.
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

            for (JavaFunctieParameter constructorParameter : javaEnum.getConstructoren().get(0).getParameters()) {
                if (constructorParameter.getNaam().equalsIgnoreCase("code")) {
                    //De code hoeft niet altijd een String te zijn, zou eventueel een ander data type kunnen zijn zoals
                    //een short of een byte.
                    JavaType typeVoorCode = constructorParameter.getJavaType();
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
                } else if (constructorParameter.getNaam().equalsIgnoreCase("naam")) {
                    enumWaarde.voegConstructorParameterToe(tuple.getNaam(), true);
                } else if (constructorParameter.getNaam().equalsIgnoreCase("omschrijving")) {
                    if (StringUtils.isNotBlank(tuple.getBeschrijving())) {
                        enumWaarde.voegConstructorParameterToe(
                            StringEscapeUtils.escapeJava(tuple.getBeschrijving()), true);
                    } else {
                        enumWaarde.voegConstructorParameterToe("null", false);
                    }
                } else if (constructorParameter.getNaam().equalsIgnoreCase("datumAanvangGeldigheid")
                    || constructorParameter.getNaam().equalsIgnoreCase("datumEindeGeldigheid"))
                {
                    String extraDatumWaarde = null;
                    for (ExtraWaarde extraWaarde : tuple.getExtraWaarden()) {
                        if (constructorParameter.getNaam().equalsIgnoreCase("datumAanvangGeldigheid")
                            && ID_EXTRA_WAARDE_DATUM_AANVANG_GELDIGHEID == extraWaarde.getNaamExtraWaarde().getId())
                        {
                            extraDatumWaarde = extraWaarde.getWaarde();
                        } else if (constructorParameter.getNaam().equalsIgnoreCase("datumEindeGeldigheid")
                            && ID_EXTRA_WAARDE_DATUM_EINDE_GELDIGHEID == extraWaarde.getNaamExtraWaarde().getId())
                        {
                            extraDatumWaarde = extraWaarde.getWaarde();
                        }
                    }

                    if (StringUtils.isNotBlank(extraDatumWaarde)) {
                        enumWaarde.voegConstructorParameterToe(
                            String.format("new DatumEvtDeelsOnbekendAttribuut(%s)", extraDatumWaarde), false);
                    } else {
                        LOGGER.error("Let op: geen extra waarde gevonden voor " + constructorParameter.getNaam() + " voor Tuple: " + tuple.getNaam()
                            + " van objecttype: " + objectType.getNaam() + ". Dit moet in het BMR ingeregeld worden!");
                        enumWaarde.voegConstructorParameterToe("null", false);
                    }

                } else {
                    boolean extraWaardeToegevoegd = false;
                    //Tuple kent dus een extra waarde. Match de constructor parameter naam op een extra waarde naam.
                    for (ExtraWaarde extraWaarde : tuple.getExtraWaarden()) {
                        //TODO Jeanot: Waarom zitten er underscores in de naam kolom van naam_extra_waarde tabel.
                        final String extraWaardeNaam = extraWaarde.getNaamExtraWaarde().getNaam().replaceAll("_", "");
                        if (extraWaardeNaam.equalsIgnoreCase(constructorParameter.getNaam())) {
                            //De extra waarde gaat dus over deze constructor parameter. Zoek de Tuple van de extra waarde op.
                            //TODO Jeanot: Deze lelijke oplossing vraagt naar wijzigingen in het BMR.
                            List<Attribuut> attributen = this.getBmrDao().getAttributenVanObjectType(objectType);
                            for (Attribuut attribuut : attributen) {
                                // Match attribuut op naam.
                                if (attribuut.getIdentCode().equalsIgnoreCase(extraWaardeNaam)) {
                                    if (isAttribuutTypeAttribuut(attribuut)) {
                                        enumWaarde.voegConstructorParameterToe(extraWaarde.getWaarde(), true);
                                        extraWaardeToegevoegd = true;
                                    } else if (isStamgegevenAttribuut(attribuut)) {
                                        int stamgegevenWaarnaarVerwezenWordtId = attribuut.getType().getId();
                                        ObjectType stamgegevenWaarnaarVerwijstWordt = this.getBmrDao().
                                            getElement(stamgegevenWaarnaarVerwezenWordtId, ObjectType.class);
                                        SortedSet<Tuple> tupleSortedSet =
                                            stamgegevenWaarnaarVerwijstWordt.getTuples();
                                        for (Tuple tuple1 : tupleSortedSet) {
                                            if (String.valueOf(tuple1.getVolgnummerT())
                                                .equals(extraWaarde.getWaarde()))
                                            {
                                                final JavaType javaType =
                                                    algemeneNaamgevingStrategie.getJavaTypeVoorElement(
                                                        stamgegevenWaarnaarVerwijstWordt);
                                                enumWaarde.voegConstructorParameterToe(javaType.getNaam() + "."
                                                    + JavaGeneratieUtil
                                                    .genereerNaamVoorEnumWaarde(
                                                        tuple1.getIdentCode()), false);
                                                extraWaardeToegevoegd = true;
                                            }
                                        }
                                    }
                                }
                            }
                        } else if (extraWaardeNaam.equalsIgnoreCase("HeeftMaterieleHistorie")) {
                            final Boolean waarde = GeneratieUtil.bmrJaNeeNaarBoolean(
                                tuple.getExtraWaarden().iterator().next().getWaarde()
                            );
                            enumWaarde.voegConstructorParameterToe(String.valueOf(waarde), false);
                            extraWaardeToegevoegd = true;
                        }
                    }
                    if (!extraWaardeToegevoegd) {
                        enumWaarde.voegConstructorParameterToe("null", false);
                    }
                }
            }
            javaEnum.voegEnumeratieWaardeToe(enumWaarde);
        }
    }

    /**
     * Genereert een {@link JavaVeld} instantie op basis van het opgegeven attribuut, wat een objecttype zou moeten zijn.
     *
     * @param attribuut het attribuut waarvoor een JavaVeld instantie moet worden gegenereerd.
     * @return het gegenereerde JavaVeld.
     */
    private JavaVeld genereerJavaVeldVoorObjectType(final Attribuut attribuut) {
        final String veldNaam = GeneratieUtil.lowerTheFirstCharacter(attribuut.getIdentCode());

        //Haal het objecttype op
        final ObjectType objectTypeAttribuut = getBmrDao().getElement(attribuut.getType().getId(), ObjectType.class);
        return new JavaVeld(algemeneNaamgevingStrategie.getJavaTypeVoorElement(objectTypeAttribuut), veldNaam);
    }
}

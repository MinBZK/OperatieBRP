/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.jibx;

import nl.bzk.brp.generatoren.algemeen.basis.AbstractGenerator;
import nl.bzk.brp.generatoren.algemeen.basis.GeneratorConfiguratie;
import nl.bzk.brp.generatoren.jibx.common.JibxBinding;
import nl.bzk.brp.generatoren.jibx.model.Binding;
import nl.bzk.brp.generatoren.jibx.model.Collection;
import nl.bzk.brp.generatoren.jibx.model.Include;
import nl.bzk.brp.generatoren.jibx.model.Mapping.Choice;
import nl.bzk.brp.generatoren.jibx.model.Name;
import nl.bzk.brp.generatoren.jibx.model.Property;
import nl.bzk.brp.generatoren.jibx.model.Structure;
import nl.bzk.brp.generatoren.jibx.model.Value;
import nl.bzk.brp.generatoren.jibx.model._String;
import nl.bzk.brp.generatoren.jibx.util.JibxGeneratieUtil;
import nl.bzk.brp.generatoren.jibx.writer.EnkelBestandJibxWriter;
import nl.bzk.brp.generatoren.jibx.writer.JibxWriter;
import nl.bzk.brp.metaregister.model.Groep;
import nl.bzk.brp.metaregister.model.ObjectType;


/**
 * Abstracte super klasse voor alle JiBX generatoren. Deze klasse bevat functionaliteit die
 * algemeen is voor alle generatoren, zoals een referentie naar de JiBX writer en enkele
 * utility methodes.
 */
public abstract class AbstractJibxGenerator extends AbstractGenerator {

    protected static final String ATTR_OBJECTTYPE = "objecttype";

    /**
     * Voeg de meegegeven JiBX include toe aan de meegegeven binding.
     *
     * @param binding de binding waar de include aan moet worden toegevoegd
     * @param jibxBinding de binding die ge-include moet worden
     */
    protected void voegJibxBindingIncludeToe(final Binding binding, final JibxBinding jibxBinding) {
        this.voegClasspathIncludeToe(binding, JibxGeneratieUtil.getBestandsLocatieVoorBinding(jibxBinding));
    }

    /**
     * Voeg een classpath include toe aan de binding.
     *
     * @param binding     de binding
     * @param includePath het include pad
     */
    private void voegClasspathIncludeToe(final Binding binding, final String includePath) {
        Include include = new Include();
        include.setPrecompiled(false);
        include.setPath("classpath:/" + includePath);
        binding.getIncludeList().add(include);
    }

    /**
     * Geef een jibx writer implementatie terug op basis van de configuratie.
     *
     * @param configuratie de configuratie
     * @return een van toepassing zijnde jibx writer implementatie
     */
    protected JibxWriter jibxWriterFactory(final GeneratorConfiguratie configuratie) {
        final JibxWriter jibxWriter;
        if (configuratie.isGenerationGapPatroon()) {
            // Hier was ooit een generation gap jibx writer, maar die blijkt niet levensvatbaar...
            throw new IllegalStateException("Generation gap wordt niet ondersteund door jibx generatoren.");
        } else {
            jibxWriter = new EnkelBestandJibxWriter(configuratie.getPad(), configuratie.isOverschrijf());
        }
        return jibxWriter;
    }

    /**
     * Generieke methode om een jibx binding structure tag te maken.
     *
     * @param field    het java field, mag null zijn
     * @param getMethod de getmethod, mag null zijn
     * @param name     de xml tag name, mag null zijn
     * @param mapAs    op welk jibx type gemapt moet worden
     * @param optional optional attribuut van structure.
     * @return de choice voor de structure tag
     */
    protected Structure maakStructure(final String field, final String getMethod, final String name, final String mapAs,
                                      final boolean optional)
    {
        Structure structure = new Structure();
        structure.setMapAs(mapAs);

        Property property = new Property();
        structure.setProperty(property);

        if (field != null) {
            property.setField(field);
        }

        if (getMethod != null) {
            property.setGetMethod(getMethod);
        }

        if (optional) {
            property.setUsage("optional");
        }

        if (name != null) {
            Name nameElement = new Name();
            nameElement.setName(name);
            structure.setName(nameElement);
        }

        return structure;
    }

    /**
     * Maak een structure aan voor direct onder een jibx mapping.
     *
     * @param field field voor de structure.
     * @param getMethod de getmethod, mag null zijn
     * @param name name voor de structure.
     * @param mapAs mapAs voor de structure.
     * @param optional optional voor de structure.
     * @return Een choice die direct onder een mapping gehangen kan worden.
     */
    protected Choice maakStructureMappingChoice(final String field, final String getMethod, final String name,
                                                final String mapAs, final boolean optional)
    {
        Choice choice = new Choice();
        choice.setStructure(maakStructure(
                field, getMethod, name, mapAs, optional
        ));
        return choice;
    }

    /**
     * Maak een structure aan voor direct onder een collection.
     *
     * @param name name voor de structure.
     * @param mapAs mapAs voor de structure.
     * @param optional optional voor de structure.
     * @return Collection choice voor direct onder een collection.
     */
    protected Collection.Choice maakStructureCollectionChoice(final String name, final String mapAs,
                                                              final boolean optional)
    {
        final Collection.Choice choice = new Collection.Choice();
        choice.setStructure(
                maakStructure(null, null, name, mapAs, optional)
        );
        return choice;
    }

    /**
     * Maak een structure aan voor direct onder een structure.
     *
     * @param field field voor de structure.
     * @param getMethod de getmethod, mag null zijn
     * @param name name voor de structure.
     * @param mapAs mapAs voor de structure.
     * @param optional optional voor de structure.
     * @return Structure choice voor direct onder een structure.
     */
    protected Structure.Choice maakStructureStructureChoice(final String field,  final String getMethod,
                                                            final String name, final String mapAs,
                                                            final boolean optional)
    {
        final Structure.Choice choice = new Structure.Choice();
        choice.setStructure(
                maakStructure(field, getMethod, name, mapAs, optional)
        );
        return choice;
    }

    /**
     * Maak een value aan voor direct onder een structure.
     * @param name name voor de value.
     * @param field field voor de value.
     * @param optional optional voor de value.
     * @return Structure choice met een value daaronder voor direct onder eens structure.
     */
    protected Structure.Choice maakStructureValueChoice(final String name, final String field,
                                                         final boolean optional)
    {
        final Structure.Choice choice = new Structure.Choice();
        Value value = new Value();
        value.setName(new Name());
        value.getName().setName(name);
        value.setProperty(new Property());
        value.getProperty().setField(field);
        if (optional) {
            value.getProperty().setUsage("optional");
        }
        choice.setValue(
                value
        );
        return choice;
    }

    /**
     * Maakt een value aan.
     * @param style style.
     * @param name name.
     * @param field field.
     * @param type type.
     * @param enumValueMethod enumValueMethod.
     * @param format format.
     * @param getter getter.
     * @param setter setter.
     * @param optional optional.
     * @return een value.
     *
     * CHECKSTYLE-BRP:OFF - MAX PARAMS
     * Niet mooi, maar een keuze om het zo als util methode te houden.
     */
    private Value maakValue(final String style, final String name,
                            final String field, final String type,
                            final String enumValueMethod,
                            final String format, final String getter,
                            final String setter, final boolean optional, final String constant)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        Value value = new Value();
        if (style != null) {
            value.setStyle(style);
        }
        if (format != null) {
            value.setFormat(format);
        }
        if (constant != null) {
            value.setConstant(constant);
        }

        Property property = new Property();
        if (optional) {
            property.setUsage("optional");
        }

        if (getter != null) {
            property.setGetMethod(getter);
        }
        if (setter != null) {
            property.setSetMethod(setter);
        }

        if (field != null) {
            property.setField(field);
        }
        if (type != null) {
            property.setType(type);
        }
        value.setProperty(property);

        if (name != null) {
            Name nameElement = new Name();
            nameElement.setName(name);
            value.setName(nameElement);
        }

        if (enumValueMethod != null) {
            _String string = new _String();
            string.setEnumValueMethod(enumValueMethod);
            value.setString(string);
        }
        return value;
    }

    /**
     * Maak een structure choice met daarin een value.
     *
     * @param style style.
     * @param name name.
     * @param field fiel.
     * @param type type.
     * @param enumValueMethod enumValueMethod.
     * @param format format.
     * @param getter getter.
     * @param setter setter.
     * @param optional optional.
     * @return Structure choice met een value.
     *
     * CHECKSTYLE-BRP:OFF - MAX PARAMS
     * Niet mooi, maar een keuze om het zo als util methode te houden.
     */
    protected Structure.Choice maakValueStructureChoice(final String style, final String name,
                                                        final String field, final String type,
                                                        final String enumValueMethod,
                                                        final String format, final String getter,
                                                        final String setter, final boolean optional,
                                                        final String constante)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        Value value = maakValue(style, name, field, type, enumValueMethod, format, getter, setter, optional, constante);
        Structure.Choice choice = new Structure.Choice();
        choice.setValue(value);
        return choice;
    }

    /**
     * Generieke methode om een jibx binding value tag te maken.
     *
     * @param style           de style, mag null zijn.
     * @param name            de name, mag null zijn.
     * @param field           het field, mag null zijn.
     * @param type            het type, mag null zijn.
     * @param enumValueMethod enumeratie waarde methode, mag null zijn.
     * @param format          het format, mag null zijn.
     * @param getter          de getter methode (inclusief 'get...'), mag null zijn.
     * @param setter          de setter methode (inclusief 'set...'), mag null zijn.
     * @param optional        Optional attribute van value.
     * @return de choice voor de value tag
     *
     * CHECKSTYLE-BRP:OFF - MAX PARAMS
     * Niet mooi, maar een keuze om het zo als util methode te houden.
     */
    protected Choice maakValueMappingChoice(final String style, final String name,
                                            final String field, final String type,
                                            final String enumValueMethod,
                                            final String format, final String getter,
                                            final String setter, final boolean optional, final String constante)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        Value value = maakValue(style, name, field, type, enumValueMethod, format, getter, setter, optional, constante);
        Choice choice = new Choice();
        choice.setValue(value);
        return choice;
    }

    /**
     * Overload van maakValueMappingChoice waarbij je ook serializer en deserializer attributen kan opgeven.
     * @param style           de style, mag null zijn.
     * @param name            de name, mag null zijn.
     * @param field           het field, mag null zijn.
     * @param type            het type, mag null zijn.
     * @param enumValueMethod enumeratie waarde methode, mag null zijn.
     * @param format          het format, mag null zijn.
     * @param getter          de getter methode (inclusief 'get...'), mag null zijn.
     * @param setter          de setter methode (inclusief 'set...'), mag null zijn.
     * @param optional        Optional attribute van value.
     * @param serializer      De te gebruiker serializer method.
     * @param deserializer    De te gebruiken deserializer method.
     * @return de choice voor de value tag
     *
     * CHECKSTYLE-BRP:OFF - MAX PARAMS
     * Niet mooi, maar een keuze om het zo als util methode te houden.
     */
    protected Choice maakValueMappingChoice(final String style, final String name,
                                            final String field, final String type,
                                            final String enumValueMethod,
                                            final String format,
                                            final String getter,
                                            final String setter,
                                            final boolean optional,
                                            final String serializer,
                                            final String deserializer)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        Choice choice = maakValueMappingChoice(style, name, field, type, enumValueMethod,
                format, getter, setter, optional, null);
        if (choice.getValue().getString() == null) {
            choice.getValue().setString(new _String());
        }
        choice.getValue().getString().setSerializer(serializer);
        choice.getValue().getString().setDeserializer(deserializer);
        return choice;
    }

    /**
     * Maak een structure choice met daarin een value.
     *
     * @param style style.
     * @param name name.
     * @param field fiel.
     * @param type type.
     * @param enumValueMethod enumValueMethod.
     * @param format format.
     * @param getter getter.
     * @param setter setter.
     * @param optional optional.
     * @param serializer      De te gebruiker serializer method.
     * @param deserializer    De te gebruiken deserializer method.
     * @return Structure choice met een value.
     *
     * CHECKSTYLE-BRP:OFF - MAX PARAMS
     * Niet mooi, maar een keuze om het zo als util methode te houden.
     */
    protected Structure.Choice maakValueStructureChoice(final String style, final String name,
                                            final String field, final String type,
                                            final String enumValueMethod,
                                            final String format, final String getter,
                                            final String setter, final boolean optional,
                                            final String serializer, final String deserializer)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        Structure.Choice choice = maakValueStructureChoice(
                style, name, field, type, enumValueMethod, format, getter, setter, optional, null);
        if (choice.getValue().getString() == null) {
            choice.getValue().setString(new _String());
        }
        choice.getValue().getString().setSerializer(serializer);
        choice.getValue().getString().setDeserializer(deserializer);
        return choice;
    }

    /**
     * Retourneert jibx mapping type-name voor object type.
     *
     * @param objectType Het object type,
     * @return Type-name voor object type.
     */
    protected String getTypeNameVoorObjectType(final ObjectType objectType) {
        return "Objecttype_" + objectType.getIdentCode();
    }

    /**
     * Retourneert jibx mapping type-name voor groep.
     *
     * @param groep De groep.
     * @return type-name voor groep.
     */
    protected String getTypeNameVoorGroep(final Groep groep) {
        return "Groep_" + groep.getObjectType().getIdentCode() + groep.getIdentCode();
    }

}

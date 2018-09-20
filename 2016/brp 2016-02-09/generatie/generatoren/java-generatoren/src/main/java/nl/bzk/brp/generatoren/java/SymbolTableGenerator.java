/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java;

import com.google.common.collect.Iterables;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.generatoren.algemeen.basis.GeneratorConfiguratie;
import nl.bzk.brp.generatoren.algemeen.common.BmrLaag;
import nl.bzk.brp.generatoren.algemeen.common.BmrSoortInhoud;
import nl.bzk.brp.generatoren.algemeen.dataaccess.BmrElementFilterObject;
import nl.bzk.brp.generatoren.java.model.JavaKlasse;
import nl.bzk.brp.generatoren.java.model.JavaSymbolEnum;
import nl.bzk.brp.generatoren.java.naamgeving.SymbolTableNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.symbols.ActieAttribuutSymbol;
import nl.bzk.brp.generatoren.java.symbols.CollectieSymbol;
import nl.bzk.brp.generatoren.java.symbols.EnumeratieBuilder;
import nl.bzk.brp.generatoren.java.symbols.ExpressieGroepMapBuilder;
import nl.bzk.brp.generatoren.java.symbols.ExpressieMapBuilder;
import nl.bzk.brp.generatoren.java.symbols.GrammaticaBuilder;
import nl.bzk.brp.generatoren.java.symbols.GroepAttribuutSymbol;
import nl.bzk.brp.generatoren.java.symbols.IndicatieActieAttribuutSymbol;
import nl.bzk.brp.generatoren.java.symbols.IndicatieAttribuutSymbol;
import nl.bzk.brp.generatoren.java.symbols.StandaardAttribuutSymbol;
import nl.bzk.brp.generatoren.java.symbols.Symbol;
import nl.bzk.brp.generatoren.java.symbols.SymbolTableConstants;
import nl.bzk.brp.generatoren.java.writer.JavaWriter;
import nl.bzk.brp.generatoren.rapportage.GeneratorNaam;
import nl.bzk.brp.metaregister.model.Attribuut;
import nl.bzk.brp.metaregister.model.Groep;
import nl.bzk.brp.metaregister.model.ObjectType;
import nl.bzk.brp.metaregister.model.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Generator die de symbol table en getters voor attributen in BRP-expressies genereert.
 */
@Component("symbolTableJavaGenerator")
public class SymbolTableGenerator extends AbstractJavaGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(SymbolTableGenerator.class);

    /**
     * Selectie van object types uit het BMR die in de expressietaal bestaan met een afbeelding naar het expressietype.
     */
    private static final Map<String, String> BMR_OBJECTTYPES_VOOR_GENERATIE =
        new HashMap<String, String>() {
            {
                put("Persoon", SymbolTableConstants.PERSOON_TYPE);
                put("Huwelijk", SymbolTableConstants.HUWELIJK_TYPE);
                put("GeregistreerdPartnerschap", SymbolTableConstants.GEREGISTREERDPARTNERSCHAP_TYPE);
                put("FamilierechtelijkeBetrekking", SymbolTableConstants.FAMILIERECHTELIJKEBETREKKING_TYPE);
                put("Onderzoek", SymbolTableConstants.ONDERZOEK_TYPE);
                put("PersoonOnderzoek", SymbolTableConstants.PERSOON_ONDERZOEK_TYPE);
                put("GegevenInOnderzoek", SymbolTableConstants.GEGEVEN_IN_ONDERZOEK_TYPE);
            }
        };

    private static final SymbolTableNaamgevingStrategie SYMBOL_TABLE_NAAMGEVING_STRATEGIE =
        new SymbolTableNaamgevingStrategie();

    private List<Symbol> symbols;

    @Override
    public void genereer(final GeneratorConfiguratie generatorConfiguratie) {
        // Haal alle objecttypen op.
        final BmrElementFilterObject filter = new BmrElementFilterObject(BmrLaag.LOGISCH);
        filter.setSoortInhoud('D');
        final List<ObjectType> objectTypen = getBmrDao().getObjectTypen(filter);

        this.symbols = new ArrayList<>();

        // Verzamel alle symbolen voor de relevante objecttypen.
        for (final ObjectType objectType : objectTypen) {
            if (behoortTotJavaLogischModel(objectType)
                && (heeftMinstensEenGroepMetHistorie(objectType) || isHierarchischType(objectType)))
            {
                final String expressieType = BMR_OBJECTTYPES_VOOR_GENERATIE.get(objectType.getIdentCode());
                if (expressieType != null) {
                    voegSymbolsVanObjectTypeToe(objectType, null, expressieType);
                } else if (objectType.getSuperType() != null
                    && "Betrokkenheid".equalsIgnoreCase(objectType.getSuperType().getIdentCode()))
                {
                    // Voor subklassen van Betrokkenheid is speciale behandeling nodig. Deze attributen zijn beschikbaar
                    // in de expressietaal, maar er zijn geen typen in de taal voor deze subklassen (er is bijvoorbeeld
                    // geen type 'OUDER' voor het objecttype Ouder).
                    voegSymbolsVanObjectTypeToe(objectType, null, objectType.getIdentCode());
                }
            }
        }
        // Genereer de code voor de gecreeerde symbolen.
        genereerSymbols(generatorConfiguratie);
    }

    @Override
    public GeneratorNaam getGeneratorNaamVoorRapportage() {
        return GeneratorNaam.SYMBOL_TABLE_JAVA_GENERATOR;
    }

    /**
     * Genereert de enum voor symbols en voor alle symbols een getterklasse om de betreffende waarde uit een
     * (root)object te halen.
     *
     * @param generatorConfiguratie Configuratie voor de generator.
     */
    private void genereerSymbols(final GeneratorConfiguratie generatorConfiguratie) {
        // Genereer de enumeratie.
        final List<JavaSymbolEnum> gegenereerdeEnums = genereerSymbolEnumeratie(generatorConfiguratie);
        // Genereer de Map van id's naar expressietaalattributen.
        final List<JavaKlasse> gegenereerdeMap = genereerSymbolMap(generatorConfiguratie);
        // Genereer de getterklassen.
        final List<JavaKlasse> gegenereerdeKlassen = genereerGetterKlassen(generatorConfiguratie);

        // Rapporteer over de generatie.
        if (gegenereerdeEnums.size() > 0) {
            final JavaSymbolEnum e = gegenereerdeEnums.get(0);
            genereerGrammatica(generatorConfiguratie, e.getPackagePad());
        }

        rapporteerOverGegenereerdeJavaTypen(Iterables.concat(gegenereerdeEnums, gegenereerdeMap, gegenereerdeKlassen));
    }

    /**
     * Genereert de enumeratie voor symbols.
     *
     * @param generatorConfiguratie Configuratie voor de generator.
     * @return Enumeratie voor de symbols.
     */
    private List<JavaSymbolEnum> genereerSymbolEnumeratie(final GeneratorConfiguratie generatorConfiguratie) {
        // Bepaal de enumeratie voor symbolen.
        final List<JavaSymbolEnum> enumeraties = new ArrayList<>();
        enumeraties.add(genereerEnumeratieVoorSymbols());
        // Genereer de code voor de enumeratie.
        final JavaWriter<JavaSymbolEnum> enumeratieWriter =
                javaWriterFactory(generatorConfiguratie, JavaSymbolEnum.class);

        voegGeneratedAnnotatiesToe(enumeraties, generatorConfiguratie);

        return enumeratieWriter.genereerEnSchrijfWeg(enumeraties, this);
    }

    /**
     * Genereert een Map voor id's naar expressietaalattributen.
     *
     * @param generatorConfiguratie Configuratie voor de generator.
     * @return JavaKlasse met Map.
     */
    private List<JavaKlasse> genereerSymbolMap(final GeneratorConfiguratie generatorConfiguratie) {
        final GeneratorConfiguratie symbolMapGeneratorConfiguratie = maakConfiguratieVoorSymbolMap(generatorConfiguratie);
        final JavaWriter<JavaKlasse> klasseWriter = javaWriterFactory(symbolMapGeneratorConfiguratie, JavaKlasse.class);
        final List<JavaKlasse> klassen = new LinkedList<>();
        klassen.add(maakExpressieMap());
        klassen.add(maakExpressieGroepMap());

        voegGeneratedAnnotatiesToe(klassen, generatorConfiguratie);

        return klasseWriter.genereerEnSchrijfWeg(klassen, this);
    }

    /**
     * Genereert de getterklassen voor symbols.
     *
     * @param generatorConfiguratie Configuratie voor de generator.
     * @return Lijst met alle getterklassen voor de symbols.
     */
    private List<JavaKlasse> genereerGetterKlassen(final GeneratorConfiguratie generatorConfiguratie) {
        // Genereer de getter-klassen voor symbolen.
        final GeneratorConfiguratie solversGeneratorConfiguratie = maakConfiguratieVoorGetters(generatorConfiguratie);
        final JavaWriter<JavaKlasse> klasseWriter = javaWriterFactory(solversGeneratorConfiguratie, JavaKlasse.class);
        final List<JavaKlasse> klassen = new ArrayList<>();
        for (final Symbol symbol : symbols) {
            final JavaKlasse klasse = symbol.maakGetterKlasse();
            if (klasse != null) {
                klassen.add(klasse);
            }
        }

        voegGeneratedAnnotatiesToe(klassen, generatorConfiguratie);

        return klasseWriter.genereerEnSchrijfWeg(klassen, this);
    }

    /**
     * Definieert een generatorconfiguratie voor de generatie van de mapping van id's op attributen (ExpressieMap).
     *
     * @param enumeratieGeneratorConfiguratie
     *         Configuratie voor de generator van de enumeratie.
     * @return Generatorconfiguratie voor de generatie van ExpressieMap.
     */
    private GeneratorConfiguratie maakConfiguratieVoorSymbolMap(
            final GeneratorConfiguratie enumeratieGeneratorConfiguratie)
    {
        final GeneratorConfiguratie symbolMapGeneratorConfiguratie = new GeneratorConfiguratie();
        symbolMapGeneratorConfiguratie.setNaam(enumeratieGeneratorConfiguratie.getNaam() + " SymbolMap");
        symbolMapGeneratorConfiguratie.setAlleenRapportage(enumeratieGeneratorConfiguratie.isAlleenRapportage());
        symbolMapGeneratorConfiguratie.setGenerationGapPatroon(false);
        symbolMapGeneratorConfiguratie.setOverschrijf(true);
        symbolMapGeneratorConfiguratie.setPad(enumeratieGeneratorConfiguratie.getPad());
        return symbolMapGeneratorConfiguratie;
    }

    /**
     * Definieert een generatorconfiguratie voor de generatie van getterklassen gebaseerd op de generatorconfiguratie
     * voor de enumeratie.
     *
     * @param enumeratieGeneratorConfiguratie
     *         Configuratie voor de generator van de enumeratie.
     * @return Generatorconfiguratie voor de generatie van getterklassen.
     */
    private GeneratorConfiguratie maakConfiguratieVoorGetters(
            final GeneratorConfiguratie enumeratieGeneratorConfiguratie)
    {
        final GeneratorConfiguratie solversGeneratorConfiguratie = new GeneratorConfiguratie();
        solversGeneratorConfiguratie.setNaam(enumeratieGeneratorConfiguratie.getNaam() + " Solvers");
        solversGeneratorConfiguratie.setAlleenRapportage(enumeratieGeneratorConfiguratie.isAlleenRapportage());
        solversGeneratorConfiguratie.setGenerationGapPatroon(false);
        solversGeneratorConfiguratie.setOverschrijf(true);
        solversGeneratorConfiguratie.setPad(enumeratieGeneratorConfiguratie.getPad());
        return solversGeneratorConfiguratie;
    }

    /**
     * Maakt een collectie met symbolen die overeenstemmen met de attributen uit het gegeven objecttype.
     *
     * @param objectType        Type waartoe de attributen behoren.
     * @param parentIndex       Symbool waartoe de attributen behoren (in geval van inverse associaties) of NULL.
     * @param expressieTypeNaam Naam van het type dat overeenkomt met het objecttype (bijv. PERSOON).
     */
    private void voegSymbolsVanObjectTypeToe(final ObjectType objectType, final Symbol parentIndex,
                                             final String expressieTypeNaam)
    {
        final List<Groep> groepenVanObjectType = getBmrDao().getGroepenVoorObjectType(objectType, true);
        for (final Groep groep : groepenVanObjectType) {
            if (behoortTotJavaLogischModel(groep)) {
                final String groepNaam = SYMBOL_TABLE_NAAMGEVING_STRATEGIE.maakGroepNaam(groep, objectType, false);
                voegSymbolsVanGroepToe(groepNaam, groep, parentIndex, expressieTypeNaam);
            }
        }

        if ("Persoon \\ Indicatie".equals(objectType.getNaam())) {
            // Indicaties, een speciaal geval. Zeker omdat het logisch en operationeel model hierin onderling
            // verschillen.
            final List<Groep> groepen = getBmrDao().getGroepenWaarvanHistorieWordtVastgelegd();
            for (final Groep groep : groepen) {
                final ObjectType operationeelModelObjectType =
                        getBmrDao().getOperationeelModelObjectTypeVoorGroep(groep);
                if (operationeelModelObjectType != null
                        && operationeelModelObjectType.getSyncid() == SYNC_ID_HIS_PERSOON_INDICATIE)
                {
                    final ObjectType soortInidcatieObjectType =
                            this.getBmrDao().getElement(ID_SOORT_INDICATIE, ObjectType.class);

                    for (final Tuple tuple : soortInidcatieObjectType.getTuples()) {
                        symbols.add(new IndicatieAttribuutSymbol("indicatie", tuple,
                                IndicatieAttribuutSymbol.IndicatieAttribuutWaarde.WAARDE, null));
                        if (heeftPersoonIndicatieTupleMaterieleHistorie(tuple)) {
                            symbols.add(new IndicatieAttribuutSymbol("indicatie", tuple,
                                    IndicatieAttribuutSymbol.IndicatieAttribuutWaarde.DATUM_AANVANG, null));
                            symbols.add(new IndicatieAttribuutSymbol("indicatie", tuple,
                                    IndicatieAttribuutSymbol.IndicatieAttribuutWaarde.DATUM_EIND, null));

                            symbols.add(new IndicatieActieAttribuutSymbol("indicatie", tuple,
                                    IndicatieActieAttribuutSymbol.IndicatieVerantwoordingType.VERANTWOORDING_AANPASSING_GELDIGHEID,
                                    IndicatieActieAttribuutSymbol.IndicatieVerantwoordingAttribuutWaarde.SOORT, null));
                            symbols.add(new IndicatieActieAttribuutSymbol("indicatie", tuple,
                                    IndicatieActieAttribuutSymbol.IndicatieVerantwoordingType.VERANTWOORDING_AANPASSING_GELDIGHEID,
                                    IndicatieActieAttribuutSymbol.IndicatieVerantwoordingAttribuutWaarde.PARTIJ, null));
                            symbols.add(new IndicatieActieAttribuutSymbol("indicatie", tuple,
                                    IndicatieActieAttribuutSymbol.IndicatieVerantwoordingType.VERANTWOORDING_AANPASSING_GELDIGHEID,
                                    IndicatieActieAttribuutSymbol.IndicatieVerantwoordingAttribuutWaarde.TIJDSTIP_REGISTRATIE,
                                    null));
                            symbols.add(new IndicatieActieAttribuutSymbol("indicatie", tuple,
                                    IndicatieActieAttribuutSymbol.IndicatieVerantwoordingType.VERANTWOORDING_AANPASSING_GELDIGHEID,
                                    IndicatieActieAttribuutSymbol.IndicatieVerantwoordingAttribuutWaarde.DATUM_ONTLENING,
                                    null));
                        }
                        symbols.add(new IndicatieAttribuutSymbol("indicatie", tuple,
                                IndicatieAttribuutSymbol.IndicatieAttribuutWaarde.TIJDSTIP_REGISTRATIE, null));
                        symbols.add(new IndicatieAttribuutSymbol("indicatie", tuple,
                                IndicatieAttribuutSymbol.IndicatieAttribuutWaarde.TIJDSTIP_VERVAL, null));

                        symbols.add(new IndicatieActieAttribuutSymbol("indicatie", tuple,
                                IndicatieActieAttribuutSymbol.IndicatieVerantwoordingType.VERANTWOORDING_INHOUD,
                                IndicatieActieAttribuutSymbol.IndicatieVerantwoordingAttribuutWaarde.SOORT, null));
                        symbols.add(new IndicatieActieAttribuutSymbol("indicatie", tuple,
                                IndicatieActieAttribuutSymbol.IndicatieVerantwoordingType.VERANTWOORDING_INHOUD,
                                IndicatieActieAttribuutSymbol.IndicatieVerantwoordingAttribuutWaarde.PARTIJ, null));
                        symbols.add(new IndicatieActieAttribuutSymbol("indicatie", tuple,
                                IndicatieActieAttribuutSymbol.IndicatieVerantwoordingType.VERANTWOORDING_INHOUD,
                                IndicatieActieAttribuutSymbol.IndicatieVerantwoordingAttribuutWaarde.TIJDSTIP_REGISTRATIE,
                                null));
                        symbols.add(new IndicatieActieAttribuutSymbol("indicatie", tuple,
                                IndicatieActieAttribuutSymbol.IndicatieVerantwoordingType.VERANTWOORDING_INHOUD,
                                IndicatieActieAttribuutSymbol.IndicatieVerantwoordingAttribuutWaarde.DATUM_ONTLENING,
                                null));

                        symbols.add(new IndicatieActieAttribuutSymbol("indicatie", tuple,
                                IndicatieActieAttribuutSymbol.IndicatieVerantwoordingType.VERANTWOORDING_VERVAL,
                                IndicatieActieAttribuutSymbol.IndicatieVerantwoordingAttribuutWaarde.SOORT, null));
                        symbols.add(new IndicatieActieAttribuutSymbol("indicatie", tuple,
                                IndicatieActieAttribuutSymbol.IndicatieVerantwoordingType.VERANTWOORDING_VERVAL,
                                IndicatieActieAttribuutSymbol.IndicatieVerantwoordingAttribuutWaarde.PARTIJ, null));
                        symbols.add(new IndicatieActieAttribuutSymbol("indicatie", tuple,
                                IndicatieActieAttribuutSymbol.IndicatieVerantwoordingType.VERANTWOORDING_VERVAL,
                                IndicatieActieAttribuutSymbol.IndicatieVerantwoordingAttribuutWaarde.TIJDSTIP_REGISTRATIE,
                                null));
                        symbols.add(new IndicatieActieAttribuutSymbol("indicatie", tuple,
                                IndicatieActieAttribuutSymbol.IndicatieVerantwoordingType.VERANTWOORDING_VERVAL,
                                IndicatieActieAttribuutSymbol.IndicatieVerantwoordingAttribuutWaarde.DATUM_ONTLENING,
                                null));
                    }
                }
            }
        }

        // Maak symbolen voor de 'inverse' attributen uit het objecttype.
        final List<Attribuut> inverseAttributen = getBmrDao().getInverseAttributenVoorObjectType(objectType);
        for (final Attribuut attr : inverseAttributen) {

            if ("PersoonAdministratieveHandeling".equals(attr.getObjectType().getIdentCode())
                || "MultirealiteitRegelVerborgenPersoon".equals(attr.getObjectType().getIdentCode())
                || "PersoonOnderzoek".equals(attr.getObjectType().getIdentCode())
                || "PartijOnderzoek".equals(attr.getObjectType().getIdentCode())
                || "GegevenInOnderzoek".equals(attr.getObjectType().getIdentCode()))
            {
                continue;
            }

            final Symbol parentSymbol =
                    maakSymbolVanAttribuut(attr, SYMBOL_TABLE_NAAMGEVING_STRATEGIE.maakGroepNaam(null, objectType, false),
                            null, expressieTypeNaam, true);
            this.symbols.add(parentSymbol);
            // Elk inverse attribuut hoort bij een objecttype, waarvoor ook symbolen gedefinieerd moeten worden.
            // Roep dus deze methode recursief aan voor het type van het inverse attribuut.
            voegSymbolsVanObjectTypeToe(attr.getObjectType(), parentSymbol, expressieTypeNaam);
        }
    }


    /**
     * Maakt een collectie met symbolen die overeenstemmen met de attributen uit de gegeven groep.
     *
     * @param groepNaam         Symbolische naam van de groep.
     * @param groep             Groep waartoe de attributen behoren.
     * @param parentSymbol      Symbool waartoe het attribuut behoort of NULL, indien dat niet bestaat.
     * @param expressieTypeNaam Naam van het objecttype waartoe de groep behoort.
     */
    private void voegSymbolsVanGroepToe(final String groepNaam, final Groep groep,
                                        final Symbol parentSymbol,
                                        final String expressieTypeNaam)
    {
        if ("onderzoek.afgeleid_administratief".equals(groepNaam)) {
            return;
        }

        final List<Attribuut> attributen = getAttributenVanGroep(groep);
        for (final Attribuut attribuut : attributen) {
            // Attributen die zelf (dynamische) objecttypen zijn (zoals Persoon in het objecttype PersoonAdres), worden
            // niet als attributen in de expressietaal gegenereerd.
            if (!isAttribuutTypeAttribuut(attribuut)) {
                final ObjectType objectType = getBmrDao().getElement(attribuut.getType().getId(), ObjectType.class);
                if (objectType.getSoortInhoud() == BmrSoortInhoud.DYNAMISCH_OBJECT_TYPE.getCode()) {
                    continue;
                }
            }

            // Er worden ook attributen gegenereerd voor de subklassen van Betrokkenheid (Kind, Ouder, ...). In die
            // objecttypen zit ook een attribuut 'rol' dat correspondeert met de betreffende klasse. Niet alleen is
            // het attribuut KIND.rol nutteloos (want altijd dezelfde waarde), het is ook foutgevoelig om hetzelfde
            // attribuut meermalen laten voorkomen voor hetzelfde type. Daarom wordt dat geval hier expliciet
            // uitgefilterd. (Kan vast mooier op basis van het attribuut zelf.)
            if ("Rol".equalsIgnoreCase(attribuut.getNaam())
                    && !"betrokkenheid".equals(groepNaam)
                    && "Betrokkenheid".equals(attribuut.getObjectType().getNaam()))
            {
                continue;
            }

            symbols.add(maakSymbolVanAttribuut(attribuut, groepNaam, parentSymbol, expressieTypeNaam, false));
        }

        final ObjectType objectType = getBmrDao().getOperationeelModelObjectTypeVoorGroep(groep);
        if (objectType != null && !"His Persoon \\ Indicatie".equals(objectType.getNaam())) {
            if (groep.getHistorieVastleggen() == 'B') {
                for (final Attribuut attribuut : getBmrDao().getAttributenVanObjectType(objectType)) {

                    if ("DatumAanvangGeldigheid".equalsIgnoreCase(attribuut.getIdentCode())
                            || "DatumEindeGeldigheid".equalsIgnoreCase(attribuut.getIdentCode()))
                    {
                        symbols.add(new GroepAttribuutSymbol(groepNaam, expressieTypeNaam, attribuut, parentSymbol, this, groep));
                    }
                }
            }

            if (groep.getHistorieVastleggen() == 'B' || groep.getHistorieVastleggen() == 'F')
            {
                for (final Attribuut attribuut : getBmrDao().getAttributenVanObjectType(objectType)) {
                    if (uitsluitenVoorToevoegenSymbool(objectType, groepNaam)) {
                        continue;
                    }

                    if ("DatumTijdRegistratie".equalsIgnoreCase(attribuut.getIdentCode())
                        || "TijdstipRegistratie".equalsIgnoreCase(attribuut.getIdentCode())
                        || "DatumTijdVerval".equalsIgnoreCase(attribuut.getIdentCode())
                        || "TijdstipVerval".equalsIgnoreCase(attribuut.getIdentCode()))
                    {
                        symbols.add(new GroepAttribuutSymbol(groepNaam, expressieTypeNaam, attribuut,
                                parentSymbol, this, groep));
                    }

                    if ("BRPActieInhoud".equalsIgnoreCase(attribuut.getIdentCode())) {
                        maakSymbolsVoorActie(groepNaam, groep, parentSymbol, expressieTypeNaam, attribuut,
                                "verantwoordingInhoud");
                    } else if ("BRPActieVerval".equalsIgnoreCase(attribuut.getIdentCode())) {
                        maakSymbolsVoorActie(groepNaam, groep, parentSymbol, expressieTypeNaam, attribuut,
                                "verantwoordingVerval");
                    } else if ("BRPActieAanpassingGeldigheid".equalsIgnoreCase(attribuut.getIdentCode())) {
                        maakSymbolsVoorActie(groepNaam, groep, parentSymbol, expressieTypeNaam, attribuut,
                                "verantwoordingAanpassingGeldigheid");
                    }

                }
            }
        }
    }

    /**
     * Bepaalt bij welke objectType/groepnaam-combinatie we geen symbool moeten gaan toevoegen.
     * @param objectType
     * @param groepNaam
     * @return
     */
    private boolean uitsluitenVoorToevoegenSymbool(final ObjectType objectType, final String groepNaam) {
        return "His_Betrokkenheid".equals(objectType.getIdentCode()) && !"betrokkenheid".equals(groepNaam);
    }

    private void maakSymbolsVoorActie(final String groepNaam, final Groep groep, final Symbol parentSymbol,
                                      final String expressieTypeNaam, final Attribuut attribuut,
                                      final String verantwoordingExpressie)
    {
        final ObjectType objectType = getBmrDao().getElement(attribuut.getType().getId(), ObjectType.class);
        final List<Attribuut> attributenVanObjectType = getBmrDao().getAttributenVanObjectType(objectType);

        for (final Attribuut attribuutVanObjectType : attributenVanObjectType) {
            if ("id".equalsIgnoreCase(attribuutVanObjectType.getIdentCode())
                    || "AdministratieveHandeling".equalsIgnoreCase(attribuutVanObjectType.getIdentCode()))
            {
                continue;
            }

            symbols.add(new ActieAttribuutSymbol(groepNaam, expressieTypeNaam,
                    attribuutVanObjectType,
                    parentSymbol, this, groep, verantwoordingExpressie));
        }
    }

    /**
     * Zet een attribuut uit het BMR om in een symbool, dat gebruikt kan worden in expressies.
     *
     * @param attribuut          Attribuut uit het BMR.
     * @param groepNaam          Symbolische naam van de groep waartoe het attribuut behoort.
     * @param parentSymbol       Het geïndiceerde symbool waartoe het attribuut behoort.
     * @param objectTypeNaam     Naam van het objecttype waartoe het attribuut behoort.
     * @param isInverseAttribuut TRUE als het een inverse attribuut betreft, anders FALSE.
     * @return Het symbool voor het attribuut.
     */
    private Symbol maakSymbolVanAttribuut(final Attribuut attribuut, final String groepNaam, final Symbol parentSymbol,
                                          final String objectTypeNaam, final boolean isInverseAttribuut)
    {
        if (!isInverseAttribuut) {
            return new StandaardAttribuutSymbol(groepNaam, objectTypeNaam, attribuut, parentSymbol, this);
        } else {
            return new CollectieSymbol(groepNaam, objectTypeNaam, attribuut, this);
        }
    }

    /**
     * Geeft een lijst met attributen die tot een groep behoren.
     *
     * @param groep Groep waarvan de attributen bepaald moeten worden.
     * @return Lijst met attributen uit de groep.
     */
    private List<Attribuut> getAttributenVanGroep(final Groep groep) {
        final List<Attribuut> result = new ArrayList<>();
        final List<Attribuut> attributen = getBmrDao().getAttributenVanGroep(groep);
        for (final Attribuut attribuut : attributen) {
            if (behoortTotJavaLogischModel(attribuut)) {
                result.add(attribuut);
            }
        }
        return result;
    }

    /**
     * Genereert een java-enumeratie voor de attributen uit het BMR die gebruikt worden als symbolen in de
     * expressietaal.
     *
     * @return Representatie van de java-enumeratie.
     */
    private JavaSymbolEnum genereerEnumeratieVoorSymbols() {
        return EnumeratieBuilder.genereerEnumeratieVoorSymbols(symbols);
    }

    /**
     * Maakt een Java utility-klasse die een afbeelding van id's (syncid) van gegevenselementen op attributen uit de
     * expressietaal oplevert.
     *
     * @return Java-klasse.
     */
    private JavaKlasse maakExpressieMap() {
        return ExpressieMapBuilder.maakExpressieMap(symbols);
    }

    /**
     * Maakt een Java utility-klasse die een mapping oplevert tussen de groepen in Element en de expressietaal.
     *
     * @return Java-klasse.
     */
    private JavaKlasse maakExpressieGroepMap() {
        return ExpressieGroepMapBuilder.maakExpressieGroepMap(symbols);
    }

    /**
     * Genereert een deel van de ANTLR-grammatica van de expressietaal.
     *
     * @param generatorConfiguratie Configuratie voor de generator.
     * @param packagePad            Het package-pad waarbinnen het grammaticabestand geschreven moet worden.
     * @return TRUE als generatie succesvol is afgerond, anders FALSE.
     */
    private boolean genereerGrammatica(final GeneratorConfiguratie generatorConfiguratie,
                                       final String packagePad)
    {
        try {
            GrammaticaBuilder.genereerGrammatica(symbols, generatorConfiguratie, packagePad);
            return true;
        } catch (final FileNotFoundException e) {
            return false;
        }
    }

}

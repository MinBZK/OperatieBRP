/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;

import nl.bzk.brp.bmr.metamodel.Attribuut;
import nl.bzk.brp.bmr.metamodel.AttribuutType;
import nl.bzk.brp.bmr.metamodel.BasisType;
import nl.bzk.brp.bmr.metamodel.BedrijfsRegel;
import nl.bzk.brp.bmr.metamodel.Domein;
import nl.bzk.brp.bmr.metamodel.Element;
import nl.bzk.brp.bmr.metamodel.GelaagdElement;
import nl.bzk.brp.bmr.metamodel.Groep;
import nl.bzk.brp.bmr.metamodel.Laag;
import nl.bzk.brp.bmr.metamodel.ObjectType;
import nl.bzk.brp.bmr.metamodel.Schema;
import nl.bzk.brp.bmr.metamodel.Tekst;
import nl.bzk.brp.bmr.metamodel.Tuple;
import nl.bzk.brp.bmr.metamodel.Versie;
import nl.bzk.brp.bmr.metamodel.WaarderegelWaarde;
import nl.bzk.brp.bmr.metamodel.repository.ApplicatieRepository;
import nl.bzk.brp.bmr.metamodel.repository.BasisTypeRepository;
import nl.bzk.brp.bmr.metamodel.repository.DomeinRepository;
import nl.bzk.brp.bmr.metamodel.ui.Applicatie;
import nl.bzk.brp.bmr.metamodel.ui.Bron;
import nl.bzk.brp.bmr.metamodel.ui.BronAttribuut;
import nl.bzk.brp.bmr.metamodel.ui.Formulier;
import nl.bzk.brp.bmr.metamodel.ui.Frame;
import nl.bzk.brp.bmr.metamodel.ui.FrameVeld;
import nl.bzk.brp.ecore.bmr.BmrFactory;
import nl.bzk.brp.ecore.bmr.BmrPackage;
import nl.bzk.brp.ecore.bmr.InSetOfModel;
import nl.bzk.brp.ecore.bmr.util.BmrResourceFactoryImpl;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Level;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


/**
 * Genereert een XMI bestand uit het model in de database.
 */
@Component
public class XmiExport {

    private static final String  COMMAND_LINE_PREFIX = "bmr-export domein";

    @Inject
    private DomeinRepository     domeinRepository;

    @Inject
    private BasisTypeRepository  basisTypeRepository;

    @Inject
    private ApplicatieRepository applicatieRepository;

    private static final Logger  LOGGER              = LoggerFactory.getLogger(XmiExport.class);

    /**
     * Entrypoint voor de applicatie voor invokatie vanaf een command-line.
     *
     * @param args Dit programma accepteert de volgende argumenten:
     *            <ul>
     *            <li>outputFile: de padnaam van XML output file.
     *            </ul>
     */
    @SuppressWarnings("static-access")
    public static void main(final String[] args) throws Exception {
        /*
         * Definieer command-line opties:
         */
        Options options = new Options();
        options.addOption(OptionBuilder.withLongOpt("help").withType(boolean.class)
                .withDescription("Toont deze help informatie").create("h"));
        options.addOption(OptionBuilder.withLongOpt("debug").withType(boolean.class)
                .withDescription("Geef meer debugging informatie").create("d"));

        options.addOption(OptionBuilder.withLongOpt("file").withArgName("file").hasArg().withType(File.class)
                .isRequired().withDescription("de output file.").create("f"));
        options.addOption(OptionBuilder.withLongOpt("url").withArgName("url").hasArg()
                .withDescription("de JDBC url van de database met het BRP metaregister").create("u"));
        options.addOption(OptionBuilder.withLongOpt("username").withArgName("username").hasArg()
                .withDescription("de JDBC username voor de database met het BRP metaregister").create("n"));
        options.addOption(OptionBuilder.withLongOpt("password").withArgName("password").hasArg()
                .withDescription("het JDBC password voor de database met het BRP metaregister").create("p"));
        HelpFormatter formatter = new HelpFormatter();
        formatter.setWidth(120);

        CommandLineParser parser = new GnuParser();
        CommandLine cl = null;
        File file = null;
        try {
            cl = parser.parse(options, args);
        } catch (MissingOptionException e) {
            LOGGER.error("Er ontbreken één of meer verplichte opties: '{}'", e.getMissingOptions());
            formatter.printHelp(COMMAND_LINE_PREFIX, options, true);
            return;
        } catch (ParseException e) {
            LOGGER.error("De commandline is niet goed. Oorzaak: '{}'", e.getMessage());
            formatter.printHelp(COMMAND_LINE_PREFIX, options, true);
            return;
        }
        // help
        if (cl.hasOption("h")) {
            formatter.printHelp(COMMAND_LINE_PREFIX, options, true);
            return;
        }
        // debug
        if (cl.hasOption("d")) {
            org.apache.log4j.Logger root = org.apache.log4j.Logger.getLogger(LOGGER.getName());
            root.setLevel(Level.DEBUG);
        }
        // output
        if (cl.hasOption("f")) {
            file = (File) cl.getParsedOptionValue("f");
        }
        // url
        if (cl.hasOption("u")) {
            URI uri = new URI(cl.getOptionValue("u"));
            System.setProperty("jdbc.url", uri.toASCIIString());
        }
        // username
        if (cl.hasOption("n")) {
            System.setProperty("jdbc.username", cl.getOptionValue("n"));
        }
        // password
        if (cl.hasOption("p")) {
            System.setProperty("jdbc.password", cl.getOptionValue("p"));
        }
        Laag.setHuidigeLaag(null);
        if (args.length < 1) {
            System.err.println("Gebruik: export(java -jar jarfile) outputfile");
            return;
        }
        if (file.exists() && !file.isFile()) {
            throw new IllegalArgumentException("Als de output file al bestaat moet het wel een gewone file zijn");
        }
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }
        LOGGER.info("bmr-export: {}", file.getAbsoluteFile());
        try {
            ApplicationContext context = new ClassPathXmlApplicationContext("classpath:generator-beans.xml");
            XmiExport generator = context.getBean(XmiExport.class);
            generator.genereer(file);
        } catch (Throwable ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        LOGGER.info("Klaar", file.getAbsoluteFile());
    }

    private final Map<Integer, nl.bzk.brp.ecore.bmr.BasisType> basisTypen =
                                                                              new TreeMap<Integer, nl.bzk.brp.ecore.bmr.BasisType>();

    private final Map<Integer, nl.bzk.brp.ecore.bmr.Type>      typen      =
                                                                              new TreeMap<Integer, nl.bzk.brp.ecore.bmr.Type>();

    private final Map<Integer, nl.bzk.brp.ecore.bmr.Element>   elementen  =
                                                                              new TreeMap<Integer, nl.bzk.brp.ecore.bmr.Element>();

    @Transactional
    public void genereer(final File file) {
        ResourceSet resourceSet = new ResourceSetImpl();
        resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
                .put(Resource.Factory.Registry.DEFAULT_EXTENSION, new BmrResourceFactoryImpl());

        resourceSet.getPackageRegistry().put(BmrPackage.eNS_URI, BmrFactory.eINSTANCE);
        Resource resource = resourceSet.createResource(org.eclipse.emf.common.util.URI.createURI(file.getPath()));

        resource.getContents().add(transformeer());

        try {
            Map<String, String> options = new HashMap<String, String>();
            options.put(XMLResource.OPTION_ENCODING, "UTF-8");
            resource.save(options);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param root
     */
    private nl.bzk.brp.ecore.bmr.MetaRegister transformeer() {
        nl.bzk.brp.ecore.bmr.MetaRegister metaRegister = BmrFactory.eINSTANCE.createMetaRegister();
        for (BasisType basisType : basisTypeRepository.findAll()) {
            metaRegister.getBasisTypen().add(transformeer(basisType));
        }
        for (Domein domein : domeinRepository.findAll()) {
            metaRegister.getDomeinen().add(transformeer(domein));
        }
        for (Applicatie applicatie : applicatieRepository.findAll()) {
            metaRegister.getApplicaties().add(transformeer(applicatie));
        }
        return metaRegister;
    }

    /**
     * @param root
     * @param domein
     */
    private nl.bzk.brp.ecore.bmr.Domein transformeer(final Domein domein) {
        LOGGER.debug("Domein {}", domein.getNaam());
        nl.bzk.brp.ecore.bmr.Domein eDomein = BmrFactory.eINSTANCE.createDomein();
        transformeerElement(domein, eDomein);

        /*
         * Eerst het schema met alleen de object typen, omdat de attributen daarnaar kunnen verwijzen.
         */
        for (Schema schema : domein.getSchemas()) {
            eDomein.getSchemas().add(transformeer(schema));
        }

        /*
         * Nu de attributen erbij.
         */
        for (Schema schema : domein.getSchemas()) {
            for (ObjectType objectType : schema.getWerkVersie().getObjectTypes()) {
                LOGGER.debug("ObjectType {}", objectType.getNaam());
                nl.bzk.brp.ecore.bmr.ObjectType eObjectType =
                    (nl.bzk.brp.ecore.bmr.ObjectType) typen.get(objectType.getId());
                for (Attribuut attribuut : objectType.getAttributen()) {
                    eObjectType.getAttributen().add(transformeer(attribuut));
                }
                for (Groep groep : objectType.getGroepen()) {
                    nl.bzk.brp.ecore.bmr.Groep eGroep = transformeer(groep);
                    eObjectType.getGroepen().add(eGroep);
                    for (Attribuut attribuut : groep.getAttributen()) {
                        eGroep.getAttributen().add(eObjectType.getAttribuut(attribuut.getId()));
                    }
                }
            }
        }

        /*
         * Nu pas bedrijfsregels van attribuuttypen, omdat die weer aan de attributen refereren.
         */
        for (Schema schema : domein.getSchemas()) {
            LOGGER.debug("Schema {}", schema.getNaam());
            for (AttribuutType attribuutType : schema.getWerkVersie().getAttribuutTypes()) {
                LOGGER.debug("AttribuutType {}", attribuutType.getNaam());
                nl.bzk.brp.ecore.bmr.Type eType = typen.get(attribuutType.getId());
                for (BedrijfsRegel regel : attribuutType.getBedrijfsRegels()) {
                    nl.bzk.brp.ecore.bmr.BedrijfsRegel eRegel = transformeer(regel);
                    eType.getBedrijfsRegels().add(eRegel);
                    if (eType instanceof nl.bzk.brp.ecore.bmr.ObjectType) {
                        nl.bzk.brp.ecore.bmr.ObjectType eObjectType = (nl.bzk.brp.ecore.bmr.ObjectType) eType;
                        for (Attribuut attribuut : regel.getAttributen()) {
                            if (attribuut != null) {
                                eRegel.getAttributen().add(eObjectType.getAttribuut(attribuut.getId()));
                            }
                        }
                    }
                }
            }
        }

        /*
         * En Nu bedrijfsregels van object typen.
         */
        for (Schema schema : domein.getSchemas()) {
            for (Element objectType : schema.getWerkVersie().getObjectTypes()) {
                nl.bzk.brp.ecore.bmr.Type eType = typen.get(objectType.getId());
                for (BedrijfsRegel regel : objectType.getBedrijfsRegels()) {
                    nl.bzk.brp.ecore.bmr.BedrijfsRegel eRegel = transformeer(regel);
                    eType.getBedrijfsRegels().add(eRegel);
                    if (eType instanceof nl.bzk.brp.ecore.bmr.ObjectType) {
                        nl.bzk.brp.ecore.bmr.ObjectType eObjectType = (nl.bzk.brp.ecore.bmr.ObjectType) eType;
                        for (Attribuut attribuut : regel.getAttributen()) {
                            if (attribuut != null) {
                                try {
                                    eRegel.getAttributen().add(eObjectType.getAttribuut(attribuut.getId()));
                                } catch (Exception e) {
                                    LOGGER.debug("Attribuut {} van Objecttype {}", attribuut, objectType);
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        }
        return eDomein;
    }

    private nl.bzk.brp.ecore.bmr.Groep transformeer(final Groep groep) {
        LOGGER.debug("Groep {}", groep.getNaam());
        nl.bzk.brp.ecore.bmr.Groep eGroep = BmrFactory.eINSTANCE.createGroep();
        transformeerGelaagdElement(groep, eGroep);
        eGroep.setVolgnummer(groep.getVolgnummer());
        if (groep.getHistorieVastleggen() != null) {
            eGroep.setHistorieVastleggen(nl.bzk.brp.ecore.bmr.Historie.get(groep.getHistorieVastleggen().name()));
        }
        eGroep.setVerplicht(groep.isVerplicht());
        return eGroep;
    }

    /**
     * @param element
     * @param eElement
     */
    private void transformeerElement(final Element element, final nl.bzk.brp.ecore.bmr.Element eElement) {
        eElement.setId(element.getId());
        eElement.setSoort(element.getSoort().name());
        eElement.setSyncId(element.getSyncId());
        eElement.setNaam(element.getNaam());
        eElement.setLaag(element.getLaag());
        eElement.setBeschrijving(element.getBeschrijving());
        for (Tekst tekst : element.getTeksten().values()) {
            eElement.getTeksten().add(transformeer(tekst));
        }
        elementen.put(eElement.getId(), eElement);
    }

    /**
     * @param element
     * @param eElement
     */
    private void transformeerGelaagdElement(final GelaagdElement element,
        final nl.bzk.brp.ecore.bmr.GelaagdElement eElement)
    {
        transformeerElement(element, eElement);
        eElement.setIdentifierCode(element.getIdentifierCode());
        eElement.setIdentifierDB(element.getIdentifierDB());
        if (element.getInSetOfModel() != null) {
            eElement.setInSetOfModel(InSetOfModel.get(element.getInSetOfModel().name()));
        }
    }

    /**
     * @param tekst
     * @return
     */
    private nl.bzk.brp.ecore.bmr.Tekst transformeer(final Tekst tekst) {
        LOGGER.debug("Tekst {}", tekst.getSoort());
        nl.bzk.brp.ecore.bmr.Tekst eTekst = BmrFactory.eINSTANCE.createTekst();
        eTekst.setSoort(nl.bzk.brp.ecore.bmr.SoortTekst.get(tekst.getSoort().name()));
        eTekst.setId(tekst.getId());
        eTekst.setHtmlTekst(tekst.getHtmlTekst());
        eTekst.setTekst(tekst.getTekst());
        return eTekst;
    }

    /**
     * @param regel
     * @return
     */
    private nl.bzk.brp.ecore.bmr.BedrijfsRegel transformeer(final BedrijfsRegel regel) {
        LOGGER.debug("BedrijfsRegel {}", regel.getNaam());
        nl.bzk.brp.ecore.bmr.BedrijfsRegel eBedrijfsRegel = BmrFactory.eINSTANCE.createBedrijfsRegel();
        transformeerGelaagdElement(regel, eBedrijfsRegel);
        eBedrijfsRegel.setSoortBedrijfsRegel(nl.bzk.brp.ecore.bmr.SoortBedrijfsRegel.getByName(regel
                .getSoortBedrijfsRegel().name()));
        eBedrijfsRegel.setVolgnummer(regel.getVolgnummer());
        for (WaarderegelWaarde waarde : regel.getWaarden()) {
            eBedrijfsRegel.getWaarden().add(transformeer(waarde));
        }
        return eBedrijfsRegel;
    }

    /**
     * @param waarde
     * @return
     */
    private nl.bzk.brp.ecore.bmr.WaarderegelWaarde transformeer(final WaarderegelWaarde waarde) {
        nl.bzk.brp.ecore.bmr.WaarderegelWaarde eWaarde = BmrFactory.eINSTANCE.createWaarderegelWaarde();
        transformeerGelaagdElement(waarde, eWaarde);
        eWaarde.setVolgnummer(waarde.getVolgnummer());
        eWaarde.setWaarde(waarde.getWaarde());
        eWaarde.setWeergave(waarde.getWeergave());
        return eWaarde;
    }

    /**
     * @param attribuut
     * @return
     */
    private nl.bzk.brp.ecore.bmr.Attribuut transformeer(final Attribuut attribuut) {
        LOGGER.debug("Attribuut {}", attribuut.getNaam());
        nl.bzk.brp.ecore.bmr.Attribuut eAttribuut = BmrFactory.eINSTANCE.createAttribuut();
        transformeerGelaagdElement(attribuut, eAttribuut);
        eAttribuut.setInSetOfModel(nl.bzk.brp.ecore.bmr.InSetOfModel.get(attribuut.getInSetOfModel().name()));
        eAttribuut.setType(typen.get(attribuut.getType().getId()));
        eAttribuut.setVolgnummer(attribuut.getVolgnummer());
        eAttribuut.setAfleidbaar(attribuut.isAfleidbaar());
        eAttribuut.setVerplicht(attribuut.isVerplicht());
        eAttribuut.setInverseAssociatieNaam(attribuut.getInverseAssociatieNaam());
        if (StringUtils.hasText(attribuut.getInverseAssociatie())) {
            eAttribuut.setInverseAssociatie((attribuut.getInverseAssociatie()));
        }
        if (attribuut.getHistorieVastleggen() != null) {
            eAttribuut.setHistorieVastleggen(nl.bzk.brp.ecore.bmr.Historie
                    .get(attribuut.getHistorieVastleggen().name()));
        }
        return eAttribuut;
    }

    /**
     * @param schema
     */
    private nl.bzk.brp.ecore.bmr.Schema transformeer(final Schema schema) {
        LOGGER.debug("Schema {}", schema.getNaam());
        nl.bzk.brp.ecore.bmr.Schema eSchema = BmrFactory.eINSTANCE.createSchema();
        transformeerElement(schema, eSchema);
        eSchema.getVersies().add(transformeer(schema.getWerkVersie()));
        return eSchema;
    }

    /**
     * @param versie
     * @return
     */
    private nl.bzk.brp.ecore.bmr.Versie transformeer(final Versie versie) {
        LOGGER.debug("Versie {}", versie.getNaam());
        nl.bzk.brp.ecore.bmr.Versie eVersie = BmrFactory.eINSTANCE.createVersie();
        transformeerElement(versie, eVersie);
        eVersie.setVersieTag(nl.bzk.brp.ecore.bmr.VersieTag.get(versie.getVersieTag()));

        for (AttribuutType attribuutType : versie.getAttribuutTypes()) {
            eVersie.getAttribuutTypes().add(transformeer(attribuutType));
        }
        for (ObjectType objectType : versie.getObjectTypes()) {
            eVersie.getObjectTypes().add(transformeer(objectType));
        }
        return eVersie;
    }

    /**
     * @param type
     * @return
     */
    private nl.bzk.brp.ecore.bmr.AttribuutType transformeer(final AttribuutType type) {
        LOGGER.debug("AttribuutType {}", type.getNaam());
        nl.bzk.brp.ecore.bmr.AttribuutType eAttribuutType = BmrFactory.eINSTANCE.createAttribuutType();
        typen.put(type.getId(), eAttribuutType);
        transformeerGelaagdElement(type, eAttribuutType);
        eAttribuutType.setMinimumLengte(type.getMinimumLengte());
        eAttribuutType.setMaximumLengte(type.getMaximumLengte());
        eAttribuutType.setAantalDecimalen(type.getAantalDecimalen());
        eAttribuutType.setType(basisTypen.get(type.getType().getId()));
        eAttribuutType.setVolgnummer(type.getVolgnummer());
        return eAttribuutType;
    }

    /**
     * @param type
     * @return
     */
    private nl.bzk.brp.ecore.bmr.ObjectType transformeer(final ObjectType type) {
        LOGGER.debug("ObjectType {}", type.getNaam());
        nl.bzk.brp.ecore.bmr.ObjectType eObjectType = BmrFactory.eINSTANCE.createObjectType();
        typen.put(type.getId(), eObjectType);
        transformeerGelaagdElement(type, eObjectType);
        eObjectType.setInSetOfModel(nl.bzk.brp.ecore.bmr.InSetOfModel.get(type.getInSetOfModel().name()));
        eObjectType.setMeervoudsNaam(type.getMeervoudsNaam());
        eObjectType.setSoortInhoud(nl.bzk.brp.ecore.bmr.SoortInhoud.get(type.getSoortInhoud().ordinal()));
        eObjectType.setSuperType(eObjectType.getSuperType());
        eObjectType.setVolgnummer(type.getVolgnummer());
        eObjectType.setKunstmatigeSleutel(type.isKunstmatigeSleutel());
        if (type.getHistorieVastleggen() != null) {
            eObjectType.setHistorieVastleggen(nl.bzk.brp.ecore.bmr.Historie.get(type.getHistorieVastleggen().name()));
        }
        for (Tuple tuple : type.getTuples()) {
            eObjectType.getTuples().add(transformeer(tuple));
        }
        return eObjectType;
    }

    /**
     * @param tuple
     * @return
     */
    private nl.bzk.brp.ecore.bmr.Tuple transformeer(final Tuple tuple) {
        LOGGER.debug("Tuple {}", tuple.getCode());
        nl.bzk.brp.ecore.bmr.Tuple eTuple = BmrFactory.eINSTANCE.createTuple();
        eTuple.setId(tuple.getId());
        eTuple.setCode(tuple.getCode());
        eTuple.setNaam(tuple.getNaam());
        eTuple.setNaamMannelijk(tuple.getNaamMannelijk());
        eTuple.setNaamVrouwelijk(tuple.getNaamVrouwelijk());
        eTuple.setOmschrijving(tuple.getOmschrijving());
        eTuple.setRelatiefId(tuple.getRelatiefId());
        eTuple.setHeeftMaterieleHistorie(tuple.getHeeftMaterieleHistorie());
        eTuple.setDatumAanvangGeldigheid(tuple.getDatumAanvang());
        eTuple.setDatumEindeGeldigheid(tuple.getDatumEinde());
        eTuple.setCategorieSoortActie(tuple.getCategorieSoortActie());
        eTuple.setCategorieSoortDocument(tuple.getCategorieSoortDocument());
        return eTuple;
    }

    /**
     * @param root
     * @param type
     */
    private final nl.bzk.brp.ecore.bmr.BasisType transformeer(final BasisType type) {
        LOGGER.debug("BasisType {}", type.getNaam());
        nl.bzk.brp.ecore.bmr.BasisType eBasisType = BmrFactory.eINSTANCE.createBasisType();
        transformeerElement(type, eBasisType);
        eBasisType.setBeschrijving(type.getBeschrijving());
        this.basisTypen.put(eBasisType.getId(), eBasisType);
        return eBasisType;
    }

    private nl.bzk.brp.ecore.bmr.Applicatie transformeer(final Applicatie applicatie) {
        LOGGER.debug("Applicatie {}", applicatie.getNaam());
        nl.bzk.brp.ecore.bmr.Applicatie eApplicatie = BmrFactory.eINSTANCE.createApplicatie();
        eApplicatie.setId(applicatie.getId());
        eApplicatie.setNaam(applicatie.getNaam());
        for (Formulier formulier : applicatie.getFormulieren()) {
            LOGGER.debug("Formulier {}", formulier.getNaam());
            nl.bzk.brp.ecore.bmr.Formulier eFormulier = BmrFactory.eINSTANCE.createFormulier();
            eApplicatie.getFormulieren().add(eFormulier);
            eFormulier.setId(formulier.getId());
            eFormulier.setNaam(formulier.getNaam());
            for (Bron bron : formulier.getBronnen()) {
                nl.bzk.brp.ecore.bmr.Bron eBron = BmrFactory.eINSTANCE.createBron();
                eFormulier.getBronnen().add(eBron);
                eBron.setId(bron.getId());
                eBron.setNaam(bron.getNaam());
                eBron.setIdentifier(bron.getIdentifier());
                eBron.setMeervoudsvorm(bron.getMeervoudsNaam());
                nl.bzk.brp.ecore.bmr.ObjectType objectType =
                    (nl.bzk.brp.ecore.bmr.ObjectType) typen.get(bron.getObjectType().getId());
                eBron.setObjectType(objectType);
                if (bron.getLink() != null) {
                    eBron.setLink(objectType.getAttribuut(bron.getLink().getId()));
                }
                eBron.setVolgnummer(bron.getVolgnummer());
                for (BronAttribuut bronAttribuut : bron.getBronAttibuten()) {
                    nl.bzk.brp.ecore.bmr.BronAttribuut eVeld = BmrFactory.eINSTANCE.createBronAttribuut();
                    eBron.getBronAttributen().add(eVeld);
                    eVeld.setId(bronAttribuut.getId());
                    eVeld.setNaam(bronAttribuut.getNaam());
                    eVeld.setVolgnummer(bronAttribuut.getVolgnummer());
                    eVeld.setAttribuut(eVeld.getBron().getObjectType()
                            .getAttribuut(bronAttribuut.getAttribuut().getId()));
                }
            }
            for (Frame frame : formulier.getFrames()) {
                nl.bzk.brp.ecore.bmr.Frame eFrame = BmrFactory.eINSTANCE.createFrame();
                eFormulier.getFrames().add(eFrame);
                eFrame.setId(frame.getId());
                eFrame.setNaam(frame.getNaam());
                eFrame.setVolgnummer(frame.getVolgnummer().intValue());
                eFrame.setBron(eFormulier.getBron(frame.getBron().getId()));
                for (FrameVeld veld : frame.getVelden()) {
                    nl.bzk.brp.ecore.bmr.FrameVeld eVeld = BmrFactory.eINSTANCE.createFrameVeld();
                    eFrame.getVelden().add(eVeld);
                    eVeld.setId(veld.getId());
                    eVeld.setNaam(veld.getNaam());
                    eVeld.setVolgnummer(veld.getVolgnummer());
                    eVeld.setAttribuut(eFrame.getBron().getObjectType().getAttribuut(veld.getAttribuut().getId()));
                }
            }
        }
        return eApplicatie;
    }
}

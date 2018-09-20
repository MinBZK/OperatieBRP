/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator;

import java.io.File;
import java.io.IOException;
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
import nl.bzk.brp.bmr.metamodel.Laag;
import nl.bzk.brp.bmr.metamodel.ObjectType;
import nl.bzk.brp.bmr.metamodel.Schema;
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
import nl.bzk.brp.ecore.bmr.util.BmrResourceFactoryImpl;

import org.eclipse.emf.common.util.URI;
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

    @Inject
    private DomeinRepository     domeinRepository;

    @Inject
    private BasisTypeRepository  basisTypeRepository;

    @Inject
    private ApplicatieRepository applicatieRepository;

    private static final Logger  LOGGER = LoggerFactory.getLogger(XmiExport.class);

    /**
     * Entrypoint voor de applicatie voor invokatie vanaf een command-line.
     *
     * @param args Dit programma accepteert de volgende argumenten:
     *            <ul>
     *            <li>outputFile: de padnaam van XML output file.
     *            </ul>
     */
    public static void main(final String[] args) {
        Laag.setHuidigeLaag(null);
        if (args.length < 1) {
            System.err.println("Gebruik: export(java -jar jarfile) outputfile");
            return;
        }
        File outputFile = new File(args[0]);
        if (outputFile.exists() && !outputFile.isFile()) {
            throw new IllegalArgumentException("Als de output file al bestaat moet het wel een gewone file zijn");
        }
        outputFile.getParentFile().mkdirs();
        LOGGER.info("XmiExport: {}", outputFile.getAbsoluteFile());
        try {
            ApplicationContext context = new ClassPathXmlApplicationContext("classpath:generator-beans.xml");
            XmiExport generator = context.getBean(XmiExport.class);
            generator.genereer(outputFile);
        } catch (Throwable ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        LOGGER.info("Klaar", outputFile.getAbsoluteFile());
    }

    private final Map<Integer, nl.bzk.brp.ecore.bmr.BasisType> basisTypen =
                                                                              new TreeMap<Integer, nl.bzk.brp.ecore.bmr.BasisType>();

    private final Map<Integer, nl.bzk.brp.ecore.bmr.Type>      typen      =
                                                                              new TreeMap<Integer, nl.bzk.brp.ecore.bmr.Type>();

    @Transactional
    public void genereer(final File file) {
        ResourceSet resourceSet = new ResourceSetImpl();
        resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
                .put(Resource.Factory.Registry.DEFAULT_EXTENSION, new BmrResourceFactoryImpl());

        resourceSet.getPackageRegistry().put(BmrPackage.eNS_URI, BmrFactory.eINSTANCE);
        Resource resource = resourceSet.createResource(URI.createURI(file.getPath()));
        nl.bzk.brp.ecore.bmr.MetaRegister root = BmrFactory.eINSTANCE.createMetaRegister();
        resource.getContents().add(root);
        for (BasisType basisType : basisTypeRepository.findAll()) {
            LOGGER.debug("BasisType {}", basisType.getNaam());
            root.getBasisTypen().add(transformeer(basisType));
        }
        for (Domein domein : domeinRepository.findAll()) {
            LOGGER.debug("Domein {}", domein.getNaam());
            root.getDomeinen().add(transformeer(domein));
        }
        for (Applicatie applicatie : applicatieRepository.findAll()) {
            LOGGER.debug("Applicatie {}", applicatie.getNaam());
            root.getApplicaties().add(transformeer(applicatie));
        }
        try {
            Map<String, String> options = new HashMap<String, String>();
            options.put(XMLResource.OPTION_ENCODING, "UTF-8");
            resource.save(options);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private nl.bzk.brp.ecore.bmr.Applicatie transformeer(final Applicatie applicatie) {
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
                    eBron.setLink(objectType.getAttribuut(bron.getLink().getNaam()));
                }
                eBron.setVolgnummer(bron.getVolgnummer());
                for (BronAttribuut bronAttribuut : bron.getBronAttibuten()) {
                    nl.bzk.brp.ecore.bmr.BronAttribuut eVeld = BmrFactory.eINSTANCE.createBronAttribuut();
                    eBron.getBronAttributen().add(eVeld);
                    eVeld.setId(bronAttribuut.getId());
                    eVeld.setNaam(bronAttribuut.getNaam());
                    eVeld.setVolgnummer(bronAttribuut.getVolgnummer());
                    eVeld.setAttribuut(eVeld.getBron().getObjectType()
                            .getAttribuut(bronAttribuut.getAttribuut().getNaam()));
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
                    eVeld.setAttribuut(eFrame.getBron().getObjectType().getAttribuut(veld.getAttribuut().getNaam()));
                }
            }
        }
        return eApplicatie;
    }

    /**
     * @param root
     * @param domein
     */
    private nl.bzk.brp.ecore.bmr.Domein transformeer(final Domein domein) {
        nl.bzk.brp.ecore.bmr.Domein eDomein = BmrFactory.eINSTANCE.createDomein();
        eDomein.setNaam(domein.getNaam());
        eDomein.setId(domein.getId());
        eDomein.setLaag(domein.getLaag());
        eDomein.setBeschrijving(domein.getBeschrijving());
        for (Schema schema : domein.getSchemas()) {
            LOGGER.debug("Schema {}", schema.getNaam());
            nl.bzk.brp.ecore.bmr.Schema eSchema = BmrFactory.eINSTANCE.createSchema();
            eSchema.setId(schema.getId());
            eSchema.setLaag(schema.getLaag());
            eSchema.setNaam(schema.getNaam());
            eSchema.setBeschrijving(schema.getBeschrijving());
            eDomein.getSchemas().add(eSchema);
            Versie werkVersie = schema.getWerkVersie();
            LOGGER.debug("Versie {}", werkVersie.getNaam());
            nl.bzk.brp.ecore.bmr.Versie eVersie = BmrFactory.eINSTANCE.createVersie();
            eVersie.setBeschrijving(werkVersie.getBeschrijving());
            eVersie.setId(werkVersie.getId());
            eVersie.setSyncId(werkVersie.getSyncId());
            eVersie.setLaag(werkVersie.getLaag());
            eVersie.setNaam(werkVersie.getNaam());
            eVersie.setVersieTag(nl.bzk.brp.ecore.bmr.VersieTag.get(werkVersie.getVersieTag()));
            eSchema.getVersies().add(eVersie);
            addAttribuutTypen(werkVersie, eVersie);
            for (ObjectType objectType : werkVersie.getObjectTypes()) {
                LOGGER.debug("ObjectType {}", objectType.getNaam());
                nl.bzk.brp.ecore.bmr.ObjectType eObjectType = BmrFactory.eINSTANCE.createObjectType();
                eVersie.getObjectTypes().add(eObjectType);
                typen.put(objectType.getId(), eObjectType);
                eObjectType.setBeschrijving(objectType.getBeschrijving());
                eObjectType.setId(objectType.getId());
                eObjectType.setSyncId(objectType.getSyncId());
                eObjectType.setLaag(objectType.getLaag());
                eObjectType.setIdentifierCode(objectType.getIdentifierCode());
                eObjectType.setIdentifierDB(objectType.getIdentifierDB());
                eObjectType.setInSetOfModel(nl.bzk.brp.ecore.bmr.InSetOfModel.get(objectType.getInSetOfModel().name()));
                eObjectType.setMeervoudsNaam(objectType.getMeervoudsNaam());
                eObjectType.setNaam(objectType.getNaam());
                eObjectType.setSoortInhoud(nl.bzk.brp.ecore.bmr.SoortInhoud.get(objectType.getSoortInhoud().ordinal()));
                eObjectType.setSuperType(eObjectType.getSuperType());
                eObjectType.setVolgnummer(objectType.getVolgnummer());
                eObjectType.setKunstmatigeSleutel(objectType.isKunstmatigeSleutel());
                for (Tuple tuple : objectType.getTuples()) {
                    LOGGER.debug("Tuple {}", tuple.getCode());
                    nl.bzk.brp.ecore.bmr.Tuple eTuple = BmrFactory.eINSTANCE.createTuple();
                    eObjectType.getTuples().add(eTuple);
                    eTuple.setId(tuple.getId());
                    eTuple.setCode(tuple.getCode());
                    eTuple.setNaam(tuple.getNaam());
                    eTuple.setNaamMannelijk(tuple.getNaamMannelijk());
                    eTuple.setNaamVrouwelijk(tuple.getNaamVrouwelijk());
                    eTuple.setObjectType(eObjectType);
                    eTuple.setOmschrijving(tuple.getOmschrijving());
                    eTuple.setRelatiefId(tuple.getRelatiefId());
                    eTuple.setHeeftMaterieleHistorie(tuple.getHeeftMaterieleHistorie());
                    eTuple.setDatumAanvangGeldigheid(tuple.getDatumAanvang());
                    eTuple.setDatumEindeGeldigheid(tuple.getDatumEinde());
                }
            }
        }
        for (Schema schema : domein.getSchemas()) {
            LOGGER.debug("Schema {}", schema.getNaam());
            for (ObjectType objectType : schema.getWerkVersie().getObjectTypes()) {
                LOGGER.debug("ObjectType {}", objectType.getNaam());
                nl.bzk.brp.ecore.bmr.ObjectType eObjectType =
                    (nl.bzk.brp.ecore.bmr.ObjectType) typen.get(objectType.getId());
                for (Attribuut attribuut : objectType.getAttributen()) {
                    LOGGER.debug("Attribuut {}", attribuut.getNaam());
                    nl.bzk.brp.ecore.bmr.Attribuut eAttribuut = BmrFactory.eINSTANCE.createAttribuut();
                    eObjectType.getAttributen().add(eAttribuut);
                    eAttribuut.setBeschrijving(attribuut.getBeschrijving());
                    eAttribuut.setId(attribuut.getId());
                    eAttribuut.setSyncId(attribuut.getSyncId());
                    eAttribuut.setLaag(attribuut.getLaag());
                    eAttribuut.setIdentifierCode(attribuut.getIdentifierCode());
                    eAttribuut.setIdentifierDB(attribuut.getIdentifierDB());
                    eAttribuut.setInSetOfModel(nl.bzk.brp.ecore.bmr.InSetOfModel
                            .get(attribuut.getInSetOfModel().name()));
                    eAttribuut.setNaam(attribuut.getNaam());
                    eAttribuut.setType(typen.get(attribuut.getType().getId()));
                    eAttribuut.setVolgnummer(attribuut.getVolgnummer());
                    eAttribuut.setAfleidbaar(attribuut.isAfleidbaar());
                    eAttribuut.setVerplicht(attribuut.isVerplicht());
                    if (StringUtils.hasText(attribuut.getInverseAssociatie())) {
                        eAttribuut.setInverseAssociatie((attribuut.getInverseAssociatie()));
                    }
                    eAttribuut.setHistorieVastleggen(attribuut.isHistorieVastleggen());
                }
            }
        }
        for (Schema schema : domein.getSchemas()) {
            LOGGER.debug("Schema {}", schema.getNaam());
            for (AttribuutType attribuutType : schema.getWerkVersie().getAttribuutTypes()) {
                LOGGER.debug("AttribuutType {}", attribuutType.getNaam());
                nl.bzk.brp.ecore.bmr.AttribuutType eAttribuutType =
                    (nl.bzk.brp.ecore.bmr.AttribuutType) typen.get(attribuutType.getId());
                addBedrijfsRegels(eAttribuutType, attribuutType);
            }
        }
        for (Schema schema : domein.getSchemas()) {
            for (Element objectType : schema.getWerkVersie().getObjectTypes()) {
                nl.bzk.brp.ecore.bmr.ObjectType eObjectType =
                    (nl.bzk.brp.ecore.bmr.ObjectType) typen.get(objectType.getId());
                addBedrijfsRegels(eObjectType, objectType);
            }
        }
        return eDomein;
    }

    /**
     * @param root
     * @param basisType
     */
    private final nl.bzk.brp.ecore.bmr.BasisType transformeer(final BasisType basisType) {
        nl.bzk.brp.ecore.bmr.BasisType eBasisType = BmrFactory.eINSTANCE.createBasisType();
        eBasisType.setId(basisType.getId());
        eBasisType.setBeschrijving(basisType.getBeschrijving());
        eBasisType.setNaam(basisType.getNaam());
        this.basisTypen.put(eBasisType.getId(), eBasisType);
        return eBasisType;
    }

    /**
     * @param eElement
     * @param bedrijfsRegel
     * @param eBedrijfsRegel
     */
    private void addAttributen(final nl.bzk.brp.ecore.bmr.Element eElement, final BedrijfsRegel bedrijfsRegel,
            final nl.bzk.brp.ecore.bmr.BedrijfsRegel eBedrijfsRegel)
    {
        nl.bzk.brp.ecore.bmr.ObjectType eObjectType = (nl.bzk.brp.ecore.bmr.ObjectType) eElement;
        for (Attribuut attribuut : bedrijfsRegel.getAttributen()) {
            if (attribuut != null) {
                nl.bzk.brp.ecore.bmr.Attribuut eAttribuut = eObjectType.getAttribuut(attribuut.getNaam());
                eBedrijfsRegel.getAttributen().add(eAttribuut);
            }
        }
    }

    /**
     * @param werkVersie
     * @param eVersie
     */
    private void addAttribuutTypen(final Versie werkVersie, final nl.bzk.brp.ecore.bmr.Versie eVersie) {
        for (AttribuutType attribuutType : werkVersie.getAttribuutTypes()) {
            LOGGER.debug("AttribuutType {}", attribuutType.getNaam());
            nl.bzk.brp.ecore.bmr.AttribuutType eAttribuutType = BmrFactory.eINSTANCE.createAttribuutType();
            eVersie.getAttribuutTypes().add(eAttribuutType);
            typen.put(attribuutType.getId(), eAttribuutType);
            eAttribuutType.setId(attribuutType.getId());
            eAttribuutType.setSyncId(attribuutType.getSyncId());
            eAttribuutType.setLaag(attribuutType.getLaag());
            eAttribuutType.setBeschrijving(attribuutType.getBeschrijving());
            eAttribuutType.setIdentifierCode(attribuutType.getIdentifierCode());
            eAttribuutType.setIdentifierDB(attribuutType.getIdentifierDB());
            eAttribuutType.setNaam(attribuutType.getNaam());
            eAttribuutType.setMinimumLengte(attribuutType.getMinimumLengte());
            eAttribuutType.setMaximumLengte(attribuutType.getMaximumLengte());
            eAttribuutType.setAantalDecimalen(attribuutType.getAantalDecimalen());
            BasisType type = attribuutType.getType();
            eAttribuutType.setType(this.basisTypen.get(type.getId()));
            eAttribuutType.setVolgnummer(attribuutType.getVolgnummer());
        }
    }

    /**
     * @param bmrFactory
     * @param eElement
     * @param bedrijfsRegel
     */
    private void addBedrijfsRegels(final nl.bzk.brp.ecore.bmr.Element eElement, final Element element) {
        for (BedrijfsRegel bedrijfsRegel : element.getBedrijfsRegels()) {
            LOGGER.debug("BedrijfsRegel {}", bedrijfsRegel.getNaam());
            nl.bzk.brp.ecore.bmr.BedrijfsRegel eBedrijfsRegel = BmrFactory.eINSTANCE.createBedrijfsRegel();
            eElement.getBedrijfsRegels().add(eBedrijfsRegel);
            eBedrijfsRegel.setBeschrijving(bedrijfsRegel.getBeschrijving());
            eBedrijfsRegel.setId(bedrijfsRegel.getId());
            eBedrijfsRegel.setSyncId(bedrijfsRegel.getSyncId());
            eBedrijfsRegel.setLaag(bedrijfsRegel.getLaag());
            eBedrijfsRegel.setIdentifierCode(bedrijfsRegel.getIdentifierCode());
            eBedrijfsRegel.setIdentifierDB(bedrijfsRegel.getIdentifierDB());
            eBedrijfsRegel.setNaam(bedrijfsRegel.getNaam());
            eBedrijfsRegel.setSoortBedrijfsRegel(nl.bzk.brp.ecore.bmr.SoortBedrijfsRegel.getByName(bedrijfsRegel
                    .getSoortBedrijfsRegel().name()));
            eBedrijfsRegel.setVolgnummer(bedrijfsRegel.getVolgnummer());
            if (eElement instanceof nl.bzk.brp.ecore.bmr.ObjectType) {
                addAttributen(eElement, bedrijfsRegel, eBedrijfsRegel);
            }
            addBedrijfsRegelWaarden(bedrijfsRegel, eBedrijfsRegel);
        }
    }

    /**
     * @param bedrijfsRegel
     * @param eBedrijfsRegel
     */
    private void addBedrijfsRegelWaarden(final BedrijfsRegel bedrijfsRegel,
            final nl.bzk.brp.ecore.bmr.BedrijfsRegel eBedrijfsRegel)
    {
        for (WaarderegelWaarde waarde : bedrijfsRegel.getWaarden()) {
            nl.bzk.brp.ecore.bmr.WaarderegelWaarde eWaarde = BmrFactory.eINSTANCE.createWaarderegelWaarde();
            eBedrijfsRegel.getWaarden().add(eWaarde);
            eWaarde.setBeschrijving(waarde.getBeschrijving());
            eWaarde.setId(waarde.getId());
            eWaarde.setSyncId(waarde.getSyncId());
            eWaarde.setLaag(waarde.getLaag());
            eWaarde.setIdentifierCode(waarde.getIdentifierCode());
            eWaarde.setIdentifierDB(waarde.getIdentifierDB());
            eWaarde.setNaam(waarde.getNaam());
            eWaarde.setVolgnummer(waarde.getVolgnummer());
            eWaarde.setWaarde(waarde.getWaarde());
            eWaarde.setWeergave(waarde.getWeergave());
        }
    }
}

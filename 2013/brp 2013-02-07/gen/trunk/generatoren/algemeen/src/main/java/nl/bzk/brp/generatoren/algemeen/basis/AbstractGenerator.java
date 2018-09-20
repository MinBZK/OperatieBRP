/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.algemeen.basis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.generatoren.algemeen.common.BmrLaag;
import nl.bzk.brp.generatoren.algemeen.common.BmrSoortInhoud;
import nl.bzk.brp.generatoren.algemeen.common.GeneratieUtil;
import nl.bzk.brp.generatoren.algemeen.dataaccess.BmrDao;
import nl.bzk.brp.metaregister.model.Attribuut;
import nl.bzk.brp.metaregister.model.GeneriekElement;
import nl.bzk.brp.metaregister.model.Groep;
import nl.bzk.brp.metaregister.model.ObjectType;
import nl.bzk.brp.metaregister.model.Tuple;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;

/**
 * Basis class voor alle generatoren. Algemene attributen en methodes worden in deze basis class gedefinieerd en
 * eventueel, indien generiek, geimplementeerd.
 */
public abstract class AbstractGenerator implements Generator {

    /** De naam van de identiteit groep. */
    public static final String       IDENTITEIT                  = "Identiteit";
    /** De naam van de standaard groep. */
    public static final String       STANDAARD                   = "Standaard";
    /** De lijst met namen van de groepen die direct uitgeschreven moeten worden. */
    public static final List<String> NIET_ZELFSTANDIGE_GROEPEN   = Arrays.asList(IDENTITEIT, STANDAARD);
    /** Id in het BMR van het basis type ID. */
    public static final int          ID_BASISTYPE_ID             = 7727;
    /** Id in het BMR van het basis type Numerieke code. */
    public static final int          ID_BASISTYPE_NUMERIEKE_CODE = 4423;
    /** Id van persoon in logische laag van het BMR. * */
    public static final int          ID_PERSOON_LGM              = 3718;
    /** Id van bericht in logische laag van het BMR. * */
    public static final int          ID_BERICHT_LGM              = 3510;
    /** Id van actie in logische laag van het BMR. * */
    public static final int          ID_ACTIE_LGM                = 3764;
    /** Id van relatie in logische laag van het BMR. * */
    public static final int          ID_RELATIE_LGM              = 3852;
    /** Id van betrokkenheid in logische laag van het BMR. * */
    public static final int          ID_BETROKKENHEID_LGM        = 4242;

    @Value("${generatoren.metaregister.versie}")
    private String metaRegisterVersie;

    @Value("${generatoren.versie}")
    private String versie;

    @Value("${build.timestamp}")
    private String buildTimestamp;

    @Inject
    private BmrDao bmrDao;

    /**
     * Retourneert de algemeen gebruikte BMR DAO. Middels deze DAO kan het BMR worden geraadpleegd.
     *
     * @return de algemeen gebruikte BMR DAO.
     */
    public final BmrDao getBmrDao() {
        return bmrDao;
    }

    /** {@inheritDoc} */
    @Override
    public String getNaam() {
        return this.getClass().getName();
    }

    /** {@inheritDoc} */
    @Override
    public String getMetaRegisterVersie() {
        return metaRegisterVersie;
    }

    /** {@inheritDoc} */
    @Override
    public String getVersie() {
        return versie;
    }

    /** {@inheritDoc} */
    @Override
    public String getBuildTimestamp() {
        return this.buildTimestamp;
    }

    /**
     * Bepaalt voor een attribuut of dit tot het logisch java model behoort of niet.
     *
     * @param attribuut Het attrbiuut.
     * @return true indien attribuut bij het logische model hoort.
     */
    protected boolean behoortTotJavaLogischModel(final Attribuut attribuut) {
        return attribuut.getInLgm() != null
            && GeneratieUtil.bmrJaNeeNaarBoolean(attribuut.getInLgm())
            && !"id".equalsIgnoreCase(attribuut.getNaam());
    }

    /**
     * Bepaalt voor een groep of dit tot het logisch java model behoort of niet.
     *
     * @param groep De groep.
     * @return true indien groep bij het logische model hoort.
     */
    protected boolean behoortTotJavaLogischModel(final Groep groep) {
        boolean inLgm = GeneratieUtil.bmrJaNeeNaarBoolean(groep.getInLgm());
        if (!inLgm) {
            // Het kan toch zo zijn dat een attribuut van de groep wel in het LGM zit. (inLgm = 'Ja')
            for (Attribuut attr : getBmrDao().getAttributenVanGroep(groep)) {
                if (GeneratieUtil.bmrJaNeeNaarBoolean(attr.getInLgm())) {
                    inLgm = true;
                    break;
                }
            }
        }
        return inLgm;
    }

    /**
     * Bepaalt voor een object type of dit tot het java logische Model behoort of niet.
     *
     * @param objectType Het object type.
     * @return true indien object type bij het logische model hoort.
     */
    protected boolean behoortTotJavaLogischModel(final ObjectType objectType) {
        return BmrSoortInhoud.DYNAMISCH_OBJECT_TYPE.getCode() == objectType.getSoortInhoud()
            && BmrLaag.LOGISCH.getWaardeInBmr() == objectType.getElementByLaag().getId()
            && GeneratieUtil.bmrJaNeeNaarBoolean(objectType.getInLgm());
    }

    /**
     * Bepaalt voor een object type of dit tot het operationeel model behoort of niet.
     *
     * @param objectType het object type.
     * @return true indien object type bij het operationeel model hoort.
     */
    protected boolean behoortTotJavaOperationeelModel(final ObjectType objectType) {
        return BmrLaag.LOGISCH.getWaardeInBmr() == objectType.getElementByLaag().getId()
            && BmrSoortInhoud.DYNAMISCH_OBJECT_TYPE.getCode() == objectType.getSoortInhoud()
            && GeneratieUtil.bmrJaNeeNaarBoolean(objectType.getInLgm());
    }

    /**
     * Bepaalt voor een groep of dit tot het operationeel model behoort of niet.
     *
     * @param groep de groep.
     * @return true indien groep bij het operationeel model hoort.
     */
    protected boolean behoortTotJavaOperationeelModel(final Groep groep) {
        return GeneratieUtil.bmrJaNeeNaarBoolean(groep.getInLgm())
            && 'D' == groep.getObjectType().getSoortInhoud();
    }

    /**
     * Bepaalt voor een attribuut of dit tot het operationeel model behoort of niet.
     *
     * @param attribuut het attribuut.
     * @return true indien groep bij het operationeel model hoort.
     */
    protected boolean behoortTotJavaOperationeelModel(final Attribuut attribuut) {
        return GeneratieUtil.bmrJaNeeNaarBoolean(attribuut.getInLgm());
    }


    /**
     * Bepaalt of een object type opgenomen moet worden in het berichten model.
     *
     * @param objectType Het object type.
     * @return true indien object type behoort tot het berichten model, anders false.
     */
    public boolean behoortTotJavaBerichtModel(final ObjectType objectType) {
        return BmrSoortInhoud.DYNAMISCH_OBJECT_TYPE.getCode() == objectType.getSoortInhoud()
            && BmrLaag.LOGISCH.getWaardeInBmr() == objectType.getElementByLaag().getId()
            && ((objectType.getInBericht() != null && 'J' == objectType.getInBericht()));
    }

    /**
     * Bepaalt of een groep opgenomen moet worden in het berichten model.
     * De groep zit in de XSD of in het logisch model. Het logisch model bestaat uit interfaces die ons dus
     * verplichten een implementatie op te nemen.
     * Identiteit groepen slaan we plat in het berichten model, dus die groepen horen er niet in.
     *
     * @param groep De groep.
     * @return true indien groep behoort tot het berichten model, anders false.
     */
    public boolean behoortTotJavaBerichtModel(final Groep groep) {
        return !IDENTITEIT.equals(groep.getNaam())
            && (behoortInXsdAlsLosStaandType(groep) || behoortInXsdOnderEenObjectType(groep)
            || behoortTotJavaLogischModel(groep));
    }

    /**
     * Bepaalt of een attribuut in het Java Berichten model moet worden opgenomen.
     * Dit is het geval als het attribuut in de XSD voorkomt en als het attribuut in het logische model staat
     * gedefinieerd, omdat het berichten model moet voldoen aan de interfaces in het logische model.
     *
     * @param attr Het attribuut.
     * @return true of false.
     */
    public boolean behoortTotJavaBerichtModel(final Attribuut attr) {
        return behoortInXsd(attr) || behoortTotJavaLogischModel(attr);
    }

    /**
     * Bepaalt of een object type in de XSD hoort.
     *
     * @param ot Het object type.
     * @return true of false.
     */
    public boolean behoortInXsd(final ObjectType ot) {
        return 'D' == ot.getSoortInhoud()
            && BmrLaag.LOGISCH.getWaardeInBmr() == ot.getElementByLaag().getId()
            && (ot.getInLgm() != null && 'J' == ot.getInLgm()
            && ot.getInBericht() != null && 'J' == ot.getInBericht());
    }

    /**
     * Bepaalt of een groep in de XSD hoort onder de complex type van een object type.
     *
     * @param groep De groep.
     * @return true of false.
     */
    public boolean behoortInXsdOnderEenObjectType(final Groep groep) {
        return NIET_ZELFSTANDIGE_GROEPEN.contains(groep.getNaam())
            && behoortInXsd(groep.getObjectType())
            && (groep.getInBericht() == null || 'J' == groep.getInBericht())
            && (groep.getInLgm() != null && 'J' == groep.getInLgm());
    }

    /**
     * Bepaalt of een groep in de XSD moet worden opgenomen als los staande type. (Complex type)
     *
     * @param groep De groep.
     * @return true of false.
     */
    public boolean behoortInXsdAlsLosStaandType(final Groep groep) {
        return behoortInXsd(groep.getObjectType())
            && !NIET_ZELFSTANDIGE_GROEPEN.contains(groep.getNaam())
            && (groep.getInBericht() == null || 'J' == groep.getInBericht())
            && (groep.getInLgm() != null && 'J' == groep.getInLgm());
    }

    /**
     * Bepaalt of een attribuut moet opgenomen worden in de XSD. Dit wordt mede bepaald of het attribuut direct onder
     * een object type zit (Standaard of Identiteit groepen) of onder een andere groep.
     *
     * @param attr Het attribuut.
     * @return true of false.
     */
    public boolean behoortInXsd(final Attribuut attr) {
        final boolean attrZitDirectOnderObjectType =
            NIET_ZELFSTANDIGE_GROEPEN.contains(attr.getGroep().getNaam());
        boolean hoortInXsd = (attr.getInLgm() != null && 'J' == attr.getInLgm());
        boolean opnemen;
        if (attrZitDirectOnderObjectType) {
            opnemen = (STANDAARD.equals(attr.getGroep().getNaam())
                && (attr.getInBericht() == null || 'J' == attr.getInBericht()))
                || (attr.getInBericht() != null && 'J' == attr.getInBericht());
        } else {
            opnemen = attr.getInBericht() == null || 'J' == attr.getInBericht();
        }
        return hoortInXsd && opnemen;
    }

    /**
     * Bepaal logische identiteit, door het ophalen van de attributen die als zodanig gedefinieerd zijn.
     * Aangezien het hier om een stamgegeven gaat, moet er altijd precies 1 logische identiteit attribuut zijn.
     *
     * @param objectType het stamgegeven
     * @return het logische identiteit attribuut
     */
    public Attribuut bepaalLogischeIdentiteitVoorStamgegeven(final ObjectType objectType) {
        if (objectType.getSoortInhoud() == BmrSoortInhoud.DYNAMISCH_OBJECT_TYPE.getCode()) {
            throw new GeneratorExceptie("Meegegeven object type is geen stamgegeven.");
        }

        final List<Attribuut> logischeIdentiteitAttributen =
            this.getBmrDao().getLogischeIdentiteitAttributenVoorObjectType(objectType);
        if (logischeIdentiteitAttributen.size() != 1) {
            throw new GeneratorExceptie("Aantal logische identiteit attributen van stamgegeven moet "
                + "precies 1 zijn. Aantal attributen gevonden: "
                + logischeIdentiteitAttributen.size()
                + " Objecttype: " + objectType.getNaam());
        }
        return logischeIdentiteitAttributen.get(0);
    }

    /**
     * Retourneert of een attribuut een id attribuut is of niet.
     *
     * @param attribuut het attribuut dat gecontroleerd dient te worden.
     * @return of het attribuut een id attribuut is of niet.
     */
    protected boolean isIdAttribuut(final Attribuut attribuut) {
        return "id".equalsIgnoreCase(attribuut.getIdentCode());
    }

    /**
     * Retourneert of een groep een identiteitsgroep is of niet.
     *
     * @param groep de groep die gecontroleerd dient te worden.
     * @return of de groep een identiteit groep is of niet.
     */
    protected boolean isIdentiteitGroep(final Groep groep) {
        return IDENTITEIT.equalsIgnoreCase(groep.getIdentCode());
    }

    /**
     * Of een object type een super type heeft.
     *
     * @param objectType het object type
     * @return true of false
     */
    protected boolean heeftSupertype(final ObjectType objectType) {
        return objectType.getSuperType() != null;
    }

    /**
     * Of een object type een sub type is.
     *
     * @param objectType het object type
     * @return true of false
     */
    protected boolean isSubtype(final ObjectType objectType) {
        return heeftSupertype(objectType);
    }

    /**
     * Of een object type subtypes heeft.
     *
     * @param objectType het object type
     * @return true of false
     */
    protected boolean heeftSubtypen(final ObjectType objectType) {
        return !objectType.getSubtypen().isEmpty();
    }

    /**
     * Of een object type een super type is.
     *
     * @param objectType het object type
     * @return true of false
     */
    protected boolean isSupertype(final ObjectType objectType) {
        return heeftSubtypen(objectType);
    }

    /**
     * Of een object type zowel een super type als subtypes heeft.
     *
     * @param objectType het object type
     * @return true of false
     */
    protected boolean isTussenliggendType(final ObjectType objectType) {
        return heeftSupertype(objectType) && heeftSubtypen(objectType);
    }

    /**
     * Of een object type wel een super type heeft maar geen subtypes.
     *
     * @param objectType het object type
     * @return true of false
     */
    protected boolean isFinaalSubtype(final ObjectType objectType) {
        return heeftSupertype(objectType) && !heeftSubtypen(objectType);
    }

    /**
     * Of een object type geen super type heeft, maar wel subtypes.
     *
     * @param objectType het object type
     * @return true of false
     */
    protected boolean isFinaalSupertype(final ObjectType objectType) {
        return !heeftSupertype(objectType) && heeftSubtypen(objectType);
    }

    /**
     * Of een object type geen super type heeft en ook geen subtypes.
     *
     * @param objectType het object type
     * @return true of false
     */
    protected boolean isNietHierarchischType(final ObjectType objectType) {
        return !heeftSupertype(objectType) && !heeftSubtypen(objectType);
    }

    /**
     * Of een object type een super type heeft of subtypes.
     *
     * @param objectType het object type
     * @return true of false
     */
    protected boolean isHierarchischType(final ObjectType objectType) {
        return heeftSupertype(objectType) || heeftSubtypen(objectType);
    }

    /**
     * Bepaal of een element verplicht is aan de hand van enkele velden.
     * Is zowel van toepassing op attributen als groepen.
     *
     * @param generiekElement het element
     * @return verplicht (true) of niet (false)
     */
    protected boolean isElementVerplichtInXsd(final GeneriekElement generiekElement) {
        return ((generiekElement.getVerplicht() != null && 'J' == generiekElement.getVerplicht())
            && (generiekElement.getAfleidbaar() == null || !('J' == generiekElement.getAfleidbaar()))
            && (generiekElement.getXsdVerplicht() == null || !('N' == generiekElement.getXsdVerplicht())));
    }

    /**
     * Bepaalt het daadwerkelijke discriminator attribuut van dit object type.
     * Alleen van toepassing op hierarchische typen.
     *
     * @param objectType het object type
     * @return het discriminator attribuut of null
     */
    protected Attribuut bepaalDiscriminatorAttribuut(final ObjectType objectType) {
        Attribuut discriminatorAttribuut = null;
        // Het finale super type is het object type zelf.
        ObjectType finaalSuperType = objectType;
        // Tenzij het hier om een hierarchisch type gaat.
        if (isHierarchischType(objectType)) {
            // Dan halen we het finale supertype op.
            finaalSuperType = objectType.getFinaalSupertype();
            // Tenzij we zelf het finale supertype zijn (dan staat finaal supertype namelijk niet ingevuld in het BMR).
            if (isFinaalSupertype(objectType)) {
                finaalSuperType = objectType;
            }
        }
        if (StringUtils.isNotBlank(finaalSuperType.getDiscriminatorAttribuut())) {
            final String discriminatorAttribuutString = finaalSuperType.getDiscriminatorAttribuut();
            for (Groep groep : getBmrDao().getGroepenVoorObjectType(finaalSuperType)) {
                if (IDENTITEIT.equals(groep.getNaam())) {
                    final List<Attribuut> attributen = getBmrDao().getAttributenVanGroep(groep);
                    for (Attribuut attribuut : attributen) {
                        if (discriminatorAttribuutString.equals(attribuut.getNaam())) {
                            discriminatorAttribuut = attribuut;
                            break;
                        }
                    }
                }
            }
            // Check BMR consistentie.
            if (discriminatorAttribuut == null) {
                throw new GeneratorExceptie("Kan geen (discriminator) attribuut vinden met "
                    + "naam: " + finaalSuperType.getDiscriminatorAttribuut() + " in "
                    + "objecttype " + finaalSuperType.getNaam() + ".");
            }
        }
        return discriminatorAttribuut;
    }

    /**
     * Bepaalt het discriminator tuple van dit object type.
     *
     * @param objectType het object type
     * @return het discriminator tuple of null
     */
    protected Tuple bepaalDiscriminatorTuple(final ObjectType objectType) {
        Tuple discriminatorTuple = null;
        // Alleen van toepassing op niet hierarchische typen en finale subtypen.
        if (isNietHierarchischType(objectType) || isFinaalSubtype(objectType)) {
            Attribuut discriminatorAttribuut = this.bepaalDiscriminatorAttribuut(objectType);
            final ObjectType discriminatorObjectType =
                getBmrDao().getElement(discriminatorAttribuut.getType().getId(), ObjectType.class);
            if (discriminatorObjectType.getTuples().size() == 0) {
                throw new GeneratorExceptie("Object type dat als discriminator wordt gebruikt kent geen Tuples: "
                    + discriminatorObjectType.getNaam() + ".");
            }
            for (Tuple tuple : discriminatorObjectType.getTuples()) {
                if (tuple.getIdentCode().equalsIgnoreCase(objectType.getIdentCode())) {
                    discriminatorTuple = tuple;
                }
            }
        }
        return discriminatorTuple;
    }

    /**
     * Haal alle 'bladeren' van de subtypen op van het meegegeven object type.
     *
     * @param objectType het supertype
     * @return de lijst met 'bladeren' subtypen
     */
    protected List<ObjectType> verzamelFinaleSubtypen(final ObjectType objectType) {
        return this.verzamelSubtypen(objectType, true);
    }

    /**
     * Haal alle subtypen op van het meegegeven object type.
     * NB: Indien alleenBladeren = false, wordt ook het object type zelf opgenomen!
     *
     * @param objectType het supertype
     * @param alleenBladeren of alleen de bladeren (finale subtypen) moeten worden verzameld
     * @return de lijst met subtypen
     */
    protected List<ObjectType> verzamelSubtypen(final ObjectType objectType, final boolean alleenBladeren) {
        // Gebruik een accumulator om het telkens opnieuw aanmaken en 'optellen' van lijsten te voorkomen.
        List<ObjectType> accumulator = new ArrayList<ObjectType>();
        this.verzamelSubtypenMetAccumulator(objectType, alleenBladeren, accumulator);
        return accumulator;
    }

    /**
     * Haal alle subtypen op van het meegegeven object type,
     * met behulp van een accumulator lijst.
     * Roept zichzelf recursief aan voor subtypen die zelf ook nog een supertype zijn.
     * NB: Indien alleenBladeren = false, wordt ook het object type zelf opgenomen!
     *
     * @param objectType het supertype
     * @param alleenBladeren of alleen de bladeren (finale subtypen) moeten worden verzameld of alle typen in de boom
     * @param accumulator de accumulator
     */
    protected void verzamelSubtypenMetAccumulator(
        final ObjectType objectType, final boolean alleenBladeren, final List<ObjectType> accumulator)
    {
        if (isFinaalSubtype(objectType)) {
            accumulator.add(objectType);
        } else {
            if (!alleenBladeren) {
                accumulator.add(objectType);
            }
            for (ObjectType subtype : objectType.getSubtypen()) {
                this.verzamelSubtypenMetAccumulator(subtype, alleenBladeren, accumulator);
            }
        }
    }

    /**
     * Controleert of het opgegeven objecttype een koppeling is of niet.
     *
     * @param ot het objecttype dat gecontroleerd dient te worden.
     * @return of het opgegeven objecttype een koppeling is of niet.
     */
    protected boolean isKoppelingObjectType(final ObjectType ot) {
        return ot.getKoppeling() != null
            && 'J' == ot.getKoppeling();
    }

    /**
     * Bepaald wat het gekoppelde object type is. Zie commentaar in body voor details.
     *
     * @param inverseAssociatieAttribuut het attribuut van de inverse associatie
     * @return het gekoppelde object type
     */
    protected List<ObjectType> bepaalGekoppeldeObjectTypen(final Attribuut inverseAssociatieAttribuut) {
        List<ObjectType> gekoppeldeObjectTypen = new ArrayList<ObjectType>();
        List<Attribuut> koppelObjectTypeAttributen = this.getBmrDao().
            getAttributenVanObjectType(inverseAssociatieAttribuut.getObjectType());
        for (Attribuut attribuut : koppelObjectTypeAttributen) {
            // We zoeken de gekoppelde typen, dus niet het id en niet het inverse associatie type.
            if (!isIdAttribuut(attribuut) && attribuut.getId() != inverseAssociatieAttribuut.getId()) {
                GeneriekElement gekoppeldElement = attribuut.getType();
                if (!gekoppeldElement.getSoortElement().getCode().equals("OT")) {
                    throw new GeneratorExceptie("Een koppel object type moet altijd object typen koppelen, "
                        + "gevonden gekoppeld soort: '" + gekoppeldElement.getSoortElement().getCode() + "'.");
                }
                gekoppeldeObjectTypen.add(this.getBmrDao().getElement(gekoppeldElement.getId(), ObjectType.class));
            }
        }
        return gekoppeldeObjectTypen;
    }
}

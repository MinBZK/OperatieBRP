/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.algemeen.basis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.brp.generatoren.algemeen.common.BmrElementSoort;
import nl.bzk.brp.generatoren.algemeen.common.BmrLaag;
import nl.bzk.brp.generatoren.algemeen.common.BmrSoortInhoud;
import nl.bzk.brp.generatoren.algemeen.common.GeneratieUtil;
import nl.bzk.brp.generatoren.algemeen.common.HistoriePatroon;
import nl.bzk.brp.generatoren.algemeen.common.SoortHistorie;
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

    /**
     * De naam van de identiteit groep.
     */
    public static final String       IDENTITEIT                         = "Identiteit";
    /**
     * De naam van de standaard groep.
     */
    public static final String       STANDAARD                          = "Standaard";
    /**
     * De naam van alle 'volgnummer' attributen.
     */
    public static final String       VOLGNUMMER                         = "Volgnummer";
    /**
     * De naam van alle 'id' attributen.
     */
    public static final String       ID_ATTRIBUUT_NAAM                  = "ID";
    /**
     * De lijst met namen van de groepen die direct uitgeschreven moeten worden.
     */
    public static final List<String> NIET_ZELFSTANDIGE_GROEPEN          = Arrays.asList(IDENTITEIT, STANDAARD);
    /**
     * Id in het BMR van het basis type ID.
     */
    public static final int          ID_BASISTYPE_ID                    = 7727;
    /**
     * Id in het BMR van het basis type Numerieke code.
     */
    public static final int          ID_BASISTYPE_NUMERIEKE_CODE        = 4423;
    /**
     * ID in het BMR van het basistype datum met onzekerheid.
     */
    public final static int          ID_BASISTYPE_DATUM_MET_ONZEKERHEID = 4424;
    /**
     * ID in het BMR van het basistype datum.
     */
    public final static int          ID_BASISTYPE_DATUM                 = 9207;
    /**
     * Id van persoon in logische laag van het BMR. *
     */
    public static final int          ID_PERSOON_LGM                     = 3718;
    /**
     * Id van onderzoek in logische laag van het BMR. *
     */
    public static final int          ID_ONDERZOEK_LGM                   = 3838;
    /**
     * Id van persoon / onderzoek in logische laag van het BMR. *
     */
    public static final int          ID_PERSOON_ONDERZOEK_LGM           = 4607;
    /**
     * Id van bericht in logische laag van het BMR. *
     */
    public static final int          ID_BERICHT_LGM                     = 3510;
    /**
     * Id van actie in logische laag van het BMR. *
     */
    public static final int          ID_ACTIE_LGM                       = 3764;
    /**
     * Id van dienst in logische laag van het BMR. *
     */
    public static final int          ID_DIENST_LGM                      = 8378;
    /**
     * Id van relatie in logische laag van het BMR. *
     */
    public static final int          ID_RELATIE_LGM                     = 3852;
    /**
     * Id van betrokkenheid in logische laag van het BMR. *
     */
    public static final int          ID_BETROKKENHEID_LGM               = 4242;
    /**
     * Id van administratieve handeling in logische laag van het BMR. *
     */
    public static final int          ID_ADMHND_LGM                      = 7523;
    /**
     * Id van het Persoon indicatie objecttype in de logische laag van het BMR.
     */
    public static final int          ID_PERSOON_INDICATIE               = 4067;
    /**
     * Sync id van het His persoon indicatie objecttype.
     */
    public static final int          SYNC_ID_HIS_PERSOON_INDICATIE      = 3654;
    /**
     * Id van het Soort indicatie objecttype.
     */
    public static final int          ID_SOORT_INDICATIE                 = 4026;
    /**
     * Id van het kern schema.
     */
    public static final int          ID_KERN_SCHEMA                     = 1755;
    /**
     * Id van het ber schema.
     */
    public static final int          ID_BER_SCHEMA                      = 3508;

    /**
     * Het id van het object type 'Database object' in de logische laag van het BMR.
     */
    public static final int ID_DATABASE_OBJECT                        = 4394;
    /**
     * Het id van het object type 'Regel' in de logische laag van het BMR.
     */
    public static final int ID_REGEL                                  = 4488;
    /**
     * Het id van het object type 'Catalogus optie' in de logische laag van het BMR.
     */
    public static final int ID_CATALOGUS_OPTIE                        = 3613;
    /**
     * Het id van het object type persoon cache (in de logische laag).
     */
    public static final int ID_OT_PERSOON_CACHE                       = 8498;
    /**
     * Het id van het attribuut persoon van het object type persoon cache (in de logische laag).
     */
    public static final int ID_OT_PERSOON_CACHE_ATTRIBUUT_PERSOON     = 8518;
    /**
     * Attribuut type datum met onzekerheid id.
     */
    public static final int DATUM_EVT_DEELS_ONBEKEND_ATTRIBUUTTYPE_ID = 3743;
    /**
     * Attribuut type datum id.
     */
    public static final int DATUM_ATTRIBUUTTYPE_ID                    = 9208;
    /**
     * Ja Attribuuttype
     */
    public static final int ID_JA_ATTRIBUUTTYPE                       = 4584;

    /**
     * Element objecttype id.
     */
    public static final int ID_ELEMENT = 3842;

    /**
     * id van groep Stuurgegevens.
     */
    public static final int ID_STUURGEGEVENS = 4670;

    /**
     * id van groep standaard van Persoon / Cache
     */
    public static final int ID_STANDAARD_PERSCACHE = 8520;

    /**
     * id van groep standaard van Bericht
     */
    public static final int ID_STANDAARD_BERICHT = 9226;

    public static final int ID_EXTRA_WAARDE_DATUM_AANVANG_GELDIGHEID = 50;
    public static final int ID_EXTRA_WAARDE_DATUM_EINDE_GELDIGHEID   = 51;

    /**
     * De schema's waarvoor we niet moeten genereren.
     * Schema 'ist' is interstelsel communicatie.
     * Schema 'conv' is conversie.
     */
    public static final List<String> EXCLUDE_SCHEMAS = Arrays.asList("ist", "conv");

    /**
     * Naam van de functie voor attributen en (historie-)groepen t.b.v. het wel of niet mogen leveren van die
     * groepen/attributen aan afnemers.
     */
    protected static final String LEVEREN_MAG_GELEVERD_WORDEN_FUNCTIE = "isMagGeleverdWorden";

    private static final HashSet<String> HISTORIEPATROON_HIS_MOMENT = new HashSet<String>() {
        {
            add(HistoriePatroon.FORMEEL_MATERIEEL.getPatroon());
            add(HistoriePatroon.FORMEEL.getPatroon());
            add(HistoriePatroon.BESTAANSPERIODE_FORMEEL.getPatroon());
            add(HistoriePatroon.BESTAANSPERIODE_FORMEEL_IMPLICIETMATERIEEL.getPatroon());
        }
    };

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

    /**
     * {@inheritDoc}
     */
    @Override
    public String getNaam() {
        return this.getClass().getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMetaRegisterVersie() {
        return metaRegisterVersie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getVersie() {
        return versie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBuildTimestamp() {
        return this.buildTimestamp;
    }

    /**
     * Checkt of een element in een schema zit waarvoor we moeten genereren.
     * Oftewel of hij niet in een schema zit waarvoor we niet moeten genereren.
     *
     * @param element het element
     * @return wel in schema voor generatie (true) of niet (false)
     */
    protected boolean zitInSchemaVoorGeneratie(final GeneriekElement element) {
        return !EXCLUDE_SCHEMAS.contains(element.getSchema().getNaam().toLowerCase());
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
                && GeneratieUtil.bmrJaNeeNaarBoolean(objectType.getInLgm())
                && (objectType.getInOgm() == null || objectType.getInOgm() == 'J');
    }

    /**
     * Bepaalt voor een groep of dit tot het operationeel model behoort of niet.
     *
     * @param groep de groep.
     * @return true indien groep bij het operationeel model hoort.
     */
    protected boolean behoortTotJavaOperationeelModel(final Groep groep) {
        return GeneratieUtil.bmrJaNeeNaarBoolean(groep.getInLgm())
                && 'D' == groep.getObjectType().getSoortInhoud()
                && (groep.getInOgm() == null || groep.getInOgm() == 'J');
    }

    /**
     * Bepaalt voor een attribuut of dit tot het operationeel model behoort of niet.
     * <p/>
     * Sluit attributen uit die inOgm op 'N' hebben staan en verwijzen naar een object type.
     * De attribuut types kunnen nog nodig zijn in de bedrijfsregels (worden transient in operationeel model).
     *
     * @param attribuut het attribuut.
     * @return true indien groep bij het operationeel model hoort.
     */
    protected boolean behoortTotJavaOperationeelModel(final Attribuut attribuut) {
        return GeneratieUtil.bmrJaNeeNaarBoolean(attribuut.getInLgm())
                && !(attribuut.getInOgm() != null
                && attribuut.getInOgm() == 'N'
                && BmrElementSoort.OBJECTTYPE.hoortBijCode(attribuut.getType().getSoortElement().getCode()));
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
                && objectType.getInBericht() != null
                && 'J' == objectType.getInBericht();
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
                && ot.getInLgm() != null && 'J' == ot.getInLgm()
                && ot.getInBericht() != null && 'J' == ot.getInBericht();
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
                && groep.getInLgm() != null && 'J' == groep.getInLgm()
                && !isStandaardGroepMetHistorieVanNietLeafNode(groep);
    }

    /**
     * Bepaalt of een groep in de XSD moet worden opgenomen als los staande type. (Complex type)
     *
     * @param groep De groep.
     * @return true of false.
     */
    public boolean behoortInXsdAlsLosStaandType(final Groep groep) {
        return behoortInXsd(groep.getObjectType())
                && (groep.getInBericht() == null || 'J' == groep.getInBericht())
                && groep.getInLgm() != null && 'J' == groep.getInLgm()
                &&
                (!NIET_ZELFSTANDIGE_GROEPEN.contains(groep.getNaam())
                        || isStandaardGroepMetHistorieVanNietLeafNode(groep));
    }

    /**
     * Bouw de groepsnaam op die in de xsd tags gebruikt wordt.
     * Dit is meestal gewoon de ident code van de groep,
     * maar kan (in het geval van een standaard groep) afwijken.
     *
     * @param groep de groep
     * @return de groepsnaam
     */
    protected String bepaalGroepsNaamVoorXsd(final Groep groep) {
        String groepsNaam = groep.getIdentCode();
        if (isStandaardGroep(groep)) {
            // Bij een standaard groep (kan voorkomen ivm historie patroon), gebruiken we de naam
            // van het object type of het finale supertype.
            if (heeftSupertype(groep.getObjectType())) {
                groepsNaam = groep.getObjectType().getFinaalSupertype().getIdentCode();
            } else {
                groepsNaam = groep.getObjectType().getIdentCode();
            }
        }
        return groepsNaam;
    }

    /**
     * Kijkt of aan het speciale geval wordt voldaan: is een standaard groep
     * die historie heeft. En het bovenliggende object type is geen 'leaf node'.
     * De 'BMR gebaseerde' definitie van een leaf node is hier: geen attributen
     * van een dynamisch object type en geen inverse associaties (voor het typ
     * en alle super types).
     *
     * @param groep de groep
     * @return of de groep aan dit speciale geval voldoet of niet
     */
    public boolean isStandaardGroepMetHistorieVanNietLeafNode(final Groep groep) {
        boolean isSpeciaalGeval = false;
        if (isStandaardGroep(groep) && minstensEenGroepKentHistorie(Arrays.asList(groep))) {
            // Check de regels voor het type en alle super types.
            for (ObjectType objectType : this.verzamelSupertypen(groep.getObjectType())) {
                // De groep valt in deze speciale categorie als het object type een attribuut heeft dat
                // verwijst naar een dynamisch object type, die geen backreference is (dwz: geen inv.ass. is).
                for (Attribuut attribuut : this.getBmrDao().getAttributenVanObjectType(objectType)) {
                    if (BmrElementSoort.OBJECTTYPE.hoortBijCode(attribuut.getType().getSoortElement().getCode())
                        && attribuut.getType().getSoortInhoud() == BmrSoortInhoud.DYNAMISCH_OBJECT_TYPE.getCode()
                        && StringUtils.isBlank(attribuut.getInverseAssociatieNaam()))
                    {
                        isSpeciaalGeval = true;
                        break;
                    }
                }
                // Een andere mogelijkheid om tot een speciaal geval benoemd te worden is als er een inverse associatie
                // bestaat naar het object type van de groep.
                isSpeciaalGeval = isSpeciaalGeval
                        || !this.getBmrDao().getInverseAttributenVoorObjectType(objectType).isEmpty();
            }
        }
        return isSpeciaalGeval;
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
        boolean hoortInXsd = attr.getInLgm() != null && 'J' == attr.getInLgm();
        boolean opnemen;
        if (attrZitDirectOnderObjectType) {
            // Voor de standaard groep geldt dat in_bericht null is of ja (dus niet nee).
            // Anders (= identiteit groep) geldt dat in_bericht J moet zijn (dus niet null en niet nee).
            opnemen = STANDAARD.equals(attr.getGroep().getNaam())
                    && (attr.getInBericht() == null || 'J' == attr.getInBericht())
                    || attr.getInBericht() != null && 'J' == attr.getInBericht();
        } else {
            opnemen = attr.getInBericht() == null || 'J' == attr.getInBericht();
        }
        return hoortInXsd && opnemen;
    }

    /**
     * Checkt of een groep in de XSD thuis hoort.
     *
     * @param groep de te checken groep.
     * @return true indien de groep in de XSD hoort, anders false.
     */
    protected boolean behoortInXsd(final Groep groep) {
        return behoortInXsd(groep.getObjectType())
                && (behoortInXsdAlsLosStaandType(groep) || behoortInXsdOnderEenObjectType(groep));
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
     * Retourneert of een groep een identiteitsgroep zonder historie is of niet.
     *
     * @param groep de groep die gecontroleerd dient te worden.
     * @return of de groep een identiteit groep zonder historie is of niet.
     */
    protected boolean isIdentiteitGroepZonderHistorie(final Groep groep) {
        return isIdentiteitGroep(groep) && !kentHistorie(groep);
    }

    /**
     * Retourneert of een groep een standaardgroep is of niet.
     *
     * @param groep de groep die gecontroleerd dient te worden.
     * @return of de groep een standaard groep is of niet.
     */
    protected boolean isStandaardGroep(final Groep groep) {
        return STANDAARD.equalsIgnoreCase(groep.getIdentCode());
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
     * Geeft de standaard groep terug van een object type.
     *
     * @param objectType object type
     * @return de standaard groep
     */
    protected Groep getStandaardGroep(final ObjectType objectType) {
        Groep standaardGroep = null;
        for (Groep groep : getBmrDao().getGroepenVoorObjectType(objectType)) {
            if (isStandaardGroep(groep)) {
                standaardGroep = groep;
                break;
            }
        }
        return standaardGroep;
    }

    /**
     * Geeft de identiteit groep terug van een object type.
     *
     * @param objectType object type
     * @return de identiteit groep
     */
    protected Groep getIdentiteitGroep(final ObjectType objectType) {
        Groep identiteitGroep = null;
        for (Groep groep : getBmrDao().getGroepenVoorObjectType(objectType)) {
            if (isIdentiteitGroep(groep)) {
                identiteitGroep = groep;
                break;
            }
        }
        return identiteitGroep;
    }


    /**
     * Bepaal of een element verplicht is aan de hand van enkele velden.
     * Is zowel van toepassing op attributen als groepen.
     *
     * @param generiekElement het element
     * @return verplicht (true) of niet (false)
     */
    protected boolean isElementVerplichtInXsd(final GeneriekElement generiekElement) {
        // 2013-03-28: Alle attributen in de XSD zijn niet meer verplicht!
        // Dit, in verband met het kunnen opgeven van 'lege' groepen, bij het verwijderen.
        // De custom restrictions zetten de juiste verplichtingen in de juiste context.
        if (BmrElementSoort.ATTRIBUUT.hoortBijCode(generiekElement.getSoortElement().getCode())) {
            return false;
        }

        // Standaard afleiding voor verplichtheid uit het BMR.
        return generiekElement.getVerplicht() != null && 'J' == generiekElement.getVerplicht()
                && (generiekElement.getAfleidbaar() == null || !('J' == generiekElement.getAfleidbaar()))
                && (generiekElement.getXsdVerplicht() == null || !('N' == generiekElement.getXsdVerplicht()));
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
     * Haal alle bovenliggende typen op van het meegegeven object type (inclusief het object type zelf!).
     *
     * @param objectType het subtype
     * @return de lijst met supertypen (inclusief het object type zelf!)
     */
    protected List<ObjectType> verzamelSupertypen(final ObjectType objectType) {
        List<ObjectType> superTypen = new ArrayList<>();
        superTypen.add(objectType);
        if (heeftSupertype(objectType)) {
            superTypen.addAll(verzamelSupertypen(objectType.getSuperType()));
        }
        return superTypen;
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
     * @param objectType     het supertype
     * @param alleenBladeren of alleen de bladeren (finale subtypen) moeten worden verzameld
     * @return de lijst met subtypen
     */
    protected List<ObjectType> verzamelSubtypen(final ObjectType objectType, final boolean alleenBladeren) {
        // Gebruik een accumulator om het telkens opnieuw aanmaken en 'optellen' van lijsten te voorkomen.
        List<ObjectType> accumulator = new ArrayList<>();
        this.verzamelSubtypenMetAccumulator(objectType, alleenBladeren, accumulator);
        return accumulator;
    }

    /**
     * Haal alle subtypen op van het meegegeven object type,
     * met behulp van een accumulator lijst.
     * Roept zichzelf recursief aan voor subtypen die zelf ook nog een supertype zijn.
     * NB: Indien alleenBladeren = false, wordt ook het object type zelf opgenomen!
     *
     * @param objectType     het supertype
     * @param alleenBladeren of alleen de bladeren (finale subtypen) moeten worden verzameld of alle typen in de boom
     * @param accumulator    de accumulator
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
     * Verzamel alle groep die dit objecttype kent inclusief alle groepen die horen bij eventuele subtypen.
     *
     * @param objectType het objecttype waar alle groepen van nodig zijn.
     * @return lijst met alle groepen die in de boom zitten van dit objecttype.
     */
    protected List<Groep> verzamelAlleGroepenVanObjecttypeHierarchie(final ObjectType objectType) {
        final List<Groep> groepen = new ArrayList<>();
        groepen.addAll(getBmrDao().getGroepenVoorObjectType(objectType));
        for (ObjectType subtype : objectType.getSubtypen()) {
            groepen.addAll(verzamelAlleGroepenVanObjecttypeHierarchie(subtype));
        }
        return groepen;
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
     * Bepaalt wat het gekoppelde object type is of de gekoppelde object typen zijn.
     * Meestal maar 1, maar het kunnen er meer zijn. In dat geval is het een keuze voor een van de gekoppelde typen.
     *
     * @param inverseAssociatieAttribuut het attribuut van de inverse associatie
     * @return de gekoppelde object typen
     */
    protected List<ObjectType> bepaalGekoppeldeObjectTypen(final Attribuut inverseAssociatieAttribuut) {
        List<ObjectType> gekoppeldeObjectTypen = new ArrayList<>();
        List<Attribuut> koppelObjectTypeAttributen = this.getBmrDao().
                getAttributenVanObjectType(inverseAssociatieAttribuut.getObjectType());
        for (Attribuut attribuut : koppelObjectTypeAttributen) {
            // We zoeken de gekoppelde typen, dus niet het id en niet het inverse associatie type.
            if (!isIdAttribuut(attribuut) && attribuut.getId() != inverseAssociatieAttribuut.getId()) {
                GeneriekElement gekoppeldElement = attribuut.getType();
                if (!gekoppeldElement.getSoortElement().getCode().equals("OT")) {
                    if (this.behoortInXsd(attribuut)) {
                        throw new GeneratorExceptie("Een koppel object type mag geen niet object typen bevatten die "
                                + "in de XSD moeten komen. Gevonden attribuut: '" + attribuut.getNaam() + "' "
                                + "op object type: '" + inverseAssociatieAttribuut.getObjectType().getNaam() + "'.");
                    }
                    // Als het attribuut niet in de XSD hoort, negeren we het, aangezien het de koppeling verder
                    // niet verstoort.
                } else {
                    gekoppeldeObjectTypen.add(this.getBmrDao().getElement(gekoppeldElement.getId(), ObjectType.class));
                }
            }
        }
        return gekoppeldeObjectTypen;
    }

    /**
     * Checkt of één van de groepen historie kent.
     *
     * @param groepen de groepen.
     * @return true indien één van de groepen historie kent.
     */
    protected boolean minstensEenGroepKentHistorie(final List<Groep> groepen) {
        return minstensEenGroepKentFormeleHistorie(groepen)
                || minstensEenGroepKentMaterieleHistorie(groepen);
    }

    /**
     * Checkt of één van de groepen formele historie kent.
     *
     * @param groepen de groepen.
     * @return true indien één van de groepen formele historie kent.
     */
    protected boolean minstensEenGroepKentFormeleHistorie(final List<Groep> groepen) {
        return minstensEenGroepKentHistorie(groepen, SoortHistorie.FORMEEL);
    }

    /**
     * Checkt of één van de groepen materiele historie kent.
     *
     * @param groepen de groepen.
     * @return true indien één van de groepen materiele historie kent.
     */
    protected boolean minstensEenGroepKentMaterieleHistorie(final List<Groep> groepen) {
        return minstensEenGroepKentHistorie(groepen, SoortHistorie.MATERIEEL);
    }

    /**
     * Checkt of één van de groepen historie van het meegegeven type kent.
     *
     * @param groepen       de groepen.
     * @param soortHistorie de soort historie
     * @return true indien één van de groepen die historie kent.
     */
    protected boolean minstensEenGroepKentHistorie(final List<Groep> groepen, final SoortHistorie soortHistorie) {
        boolean resultaat = false;
        for (Groep groep : groepen) {
            if (groep.getHistorieVastleggen() != null
                    && soortHistorie.getHistorieVastleggen() == groep.getHistorieVastleggen())
            {
                resultaat = true;
                break;
            }
        }
        return resultaat;
    }

    /**
     * Retourneert true indien het objecttype alleen een identiteit groep heeft.
     *
     * @param objectType het te controleren objecttype
     * @return true indien het objecttype enkel een identiteit groep heeft, anders false
     */
    public boolean heeftEnkelIdentiteitGroep(final ObjectType objectType) {
        boolean resultaat = true;
        for (Groep groep : getBmrDao().getGroepenVoorObjectType(objectType)) {
            if (!isIdentiteitGroep(groep)) {
                resultaat = false;
                break;
            }
        }
        return resultaat;
    }

    /**
     * Retourneert true indien de groep materiele of formele historie kent.
     *
     * @param groep de groep.
     * @return true indien formele of materiele historie van toepassing.
     */
    protected boolean kentHistorie(final Groep groep) {
        return kentMaterieleHistorie(groep) || kentFormeleHistorie(groep);
    }

    /**
     * Retourneert true indien de groep materiele historie kent.
     *
     * @param groep de groep.
     * @return true indien materiele historie van toepassing.
     */
    protected boolean kentMaterieleHistorie(final Groep groep) {
        return groep.getHistorieVastleggen() != null
                && SoortHistorie.MATERIEEL.getHistorieVastleggen() == groep.getHistorieVastleggen();
    }

    /**
     * Retourneert true indien de groep formele historie kent.
     *
     * @param groep de groep.
     * @return true indien formele historie van toepassing.
     */
    protected boolean kentFormeleHistorie(final Groep groep) {
        return groep.getHistorieVastleggen() != null
                && SoortHistorie.FORMEEL.getHistorieVastleggen() == groep.getHistorieVastleggen();
    }

    /**
     * Checkt of minstens één groep van het objecttype materiele historie kent.
     *
     * @param objectType het te checken objecttype.
     * @return true indien er één groep is die materiele historie kent.
     */
    protected boolean minstensEenGroepKentMaterieleHistorie(final ObjectType objectType) {
        return minstensEenGroepKentMaterieleHistorie(getBmrDao().getGroepenVoorObjectType(objectType));
    }

    /**
     * Checkt of minstens één groep van het objecttype formele historie kent.
     *
     * @param objectType het te checken objecttype.
     * @return true indien er één groep is die formele historie kent.
     */
    protected boolean minstensEenGroepKentFormeleHistorie(final ObjectType objectType) {
        return minstensEenGroepKentFormeleHistorie(getBmrDao().getGroepenVoorObjectType(objectType));
    }

    /**
     * Checkt of minstens een groep van objecttype een vorm van historie kent.
     *
     * @param objectType het te checken objecttype.
     * @return true indien een groep historie kent.
     */
    protected boolean heeftMinstensEenGroepMetHistorie(final ObjectType objectType) {
        return minstensEenGroepKentFormeleHistorie(objectType)
                || minstensEenGroepKentMaterieleHistorie(objectType);
    }

    /**
     * Checkt of een groep historie kent.
     *
     * @param groep de te checken groep.
     * @return true indien de groep formele dan wel materiele historie kent, in alle andere gevallen false.
     */
    protected boolean groepKentHistorie(final Groep groep) {
        return groep.getHistorieVastleggen() == 'F' || groep.getHistorieVastleggen() == 'B';
    }

    /**
     * Checkt voor een objecttype of een van de groepen een vorm van historie kent, Hierbij wordt ook gekeken naar
     * eventueel aanwezige subtypen.
     *
     * @param finaalSuperObjectType het te checken objecttype.
     * @return true indien een groep van finaalSuperObjectType of een groep van een van de subtypen historie kent.
     */
    protected boolean objectTypeHierarchieHeeftMinstensEenGroepMetHistorie(final ObjectType finaalSuperObjectType) {
        boolean resultaat = false;
        if (heeftMinstensEenGroepMetHistorie(finaalSuperObjectType)) {
            resultaat = true;
        } else {
            for (ObjectType subType : finaalSuperObjectType.getSubtypen()) {
                if (heeftMinstensEenGroepMetHistorie(subType)) {
                    resultaat = true;
                    break;
                } else if (!subType.getSubtypen().isEmpty()) {
                    resultaat = objectTypeHierarchieHeeftMinstensEenGroepMetHistorie(subType);
                }
            }
        }
        return resultaat;
    }

    /**
     * Bepaalt of een attribuut een attribuut is wat gerelateerd is aan een historie patroon in het BMR.
     *
     * @param attribuut Een attribuut.
     * @return True indien het attribuut bij een historie patroon hoort, anders false.
     */
    protected boolean isHistorieGerelateerdAttribuut(final Attribuut attribuut) {
        return "TsReg".equals(attribuut.getIdentDb())
                || "TsVerval".equals(attribuut.getIdentDb())
                || "ActieInh".equals(attribuut.getIdentDb())
                || "ActieVerval".equals(attribuut.getIdentDb())
                || "DatAanvGel".equals(attribuut.getIdentDb())
                || "ActieAanpGel".equals(attribuut.getIdentDb())
                || "DatEindeGel".equals(attribuut.getIdentDb())
                || "NadereAandVerval".equals(attribuut.getIdentDb())
                || "DienstInh".equals(attribuut.getIdentDb())
                || "DienstVerval".equals(attribuut.getIdentDb())
                || "DienstAanpGel".equals(attribuut.getIdentDb())
                || "ActieVervalTbvLevMuts".equals(attribuut.getIdentDb())
                || "IndVoorkomenTbvLevMuts".equals(attribuut.getIdentDb());
    }

    /**
     * Geeft TRUE als het een attribuut van een statisch stamgegeven is.
     *
     * @param attribuut Te controleren attribuut.
     * @return True als het een attribuut van een statisch stamgegeven is.
     */
    public boolean isStatischStamgegevenAttribuut(final Attribuut attribuut) {
        boolean isStatischStamgegevenAttribuut = false;
        if (attribuut.getType().getSoortElement().getCode().equals("OT")) {
            isStatischStamgegevenAttribuut = isStatischStamgegeven(
                    getBmrDao().getElement(attribuut.getType().getId(), ObjectType.class));
        }
        return isStatischStamgegevenAttribuut;
    }

    /**
     * Geeft TRUE als het object type een stamgegeven is.
     *
     * @param objectType Te controleren object type.
     * @return True als het object type een stamgegeven is.
     */
    public boolean isStamgegeven(final ObjectType objectType) {
        return isStatischStamgegeven(objectType) || isDynamischStamgegeven(objectType);
    }

    /**
     * Geeft TRUE als het object type een statisch stamgegeven is.
     *
     * @param objectType Te controleren object type.
     * @return True als het object type een statisch stamgegeven is.
     */
    public boolean isStatischStamgegeven(final ObjectType objectType) {
        String typeCode = objectType.getSoortElement().getCode();
        Character inhoud = objectType.getSoortInhoud();
        return "OT".equals(typeCode) && Character.valueOf('X').equals(inhoud);
    }

    /**
     * Geeft TRUE als het object type een dynamisch stamgegeven is.
     *
     * @param objectType Te controleren object type.
     * @return True als het object type een statisch stamgegeven is.
     */
    public boolean isDynamischStamgegeven(final ObjectType objectType) {
        String typeCode = objectType.getSoortElement().getCode();
        Character inhoud = objectType.getSoortInhoud();
        return "OT".equals(typeCode) && Character.valueOf('S').equals(inhoud);
    }

    /**
     * Geeft TRUE als het attribuut een dynamisch stamgegeven is.
     *
     * @param attribuut Te controleren attribuut.
     * @return True als het attribuut een dynamisch stamgegeven is.
     */
    public boolean isDynamischStamgegevenAttribuut(final Attribuut attribuut) {
        String typeCode = attribuut.getType().getSoortElement().getCode();
        Character inhoud = attribuut.getType().getSoortInhoud();
        return "OT".equals(typeCode) && inhoud.equals('S');
    }

    /**
     * Geeft TRUE als het attribuut een dynamisch stamgegeven is.
     *
     * @param attribuut Te controleren attribuut.
     * @return True als het attribuut een dynamisch stamgegeven is.
     */
    public boolean isDynamischObjecttypeAttribuut(final Attribuut attribuut) {
        String typeCode = attribuut.getType().getSoortElement().getCode();
        Character inhoud = attribuut.getType().getSoortInhoud();
        return "OT".equals(typeCode) && inhoud.equals('D');
    }

    /**
     * Geeft TRUE als het attribuut een stamgegeven is.
     *
     * @param attribuut Te controleren attribuut.
     * @return True als het attribuut een stamgegeven  is.
     */
    public boolean isStamgegevenAttribuut(final Attribuut attribuut) {
        return isDynamischStamgegevenAttribuut(attribuut) || isStatischStamgegevenAttribuut(attribuut);
    }

    /**
     * Checkt of het type van het attribuut een AttribuutType is.
     *
     * @param attribuut het te checken attribuut.
     * @return true indien het type van attribuut een AtribuutType is.
     */
    public boolean isAttribuutTypeAttribuut(final Attribuut attribuut) {
        return BmrElementSoort.ATTRIBUUTTYPE.hoortBijCode(attribuut.getType().getSoortElement().getCode());
    }

    /**
     * Bepaalt voor een LGM objecttype of dit in de BRP service een root object is of niet.
     *
     * @param lgmObjectType objecttype uit het BMR LGM
     * @return true indien het een BRP root object is en anders false
     */
    protected boolean isBRPRootObject(final ObjectType lgmObjectType) {
        return ID_PERSOON_LGM == lgmObjectType.getId() || ID_RELATIE_LGM == lgmObjectType.getId()
                || ID_ONDERZOEK_LGM == lgmObjectType.getId();
    }

    /**
     * Bepaalt voor een objecttype of deze een 'Volgnummer' bevat en dus eventueel daarop gesorteerd dient te worden.
     *
     * @param objectType het objecttype dat gecontroleerd dient te worden.
     * @return true indien het object een volgnummer bevat.
     */
    protected boolean bevatVolgnummer(final ObjectType objectType) {
        boolean bevatVolgnummer = false;

        final List<Attribuut> attributen = getBmrDao().getAttributenVanObjectType(objectType);
        for (Attribuut attribuut : attributen) {
            if (attribuut.getNaam().equalsIgnoreCase(VOLGNUMMER)) {
                bevatVolgnummer = true;
                break;
            }
        }
        return bevatVolgnummer;
    }

    /**
     * Verzamelt alle object typen die een his volledig variant moeten hebben.
     *
     * @return de lijst met his volledig object typen
     */
    protected List<ObjectType> getHisVolledigObjectTypen() {
        final List<ObjectType> hisVolledigObjectTypen = new ArrayList<>();
        final List<ObjectType> alleObjectTypen = getBmrDao().getObjectTypen();
        for (ObjectType objectType : alleObjectTypen) {
            if (behoortTotJavaOperationeelModel(objectType) || isHierarchischType(objectType)) {
                hisVolledigObjectTypen.add(objectType);
            }
        }
        return hisVolledigObjectTypen;
    }

    /**
     * /**
     * Bepaalt of een objecttype een hisvolledig type is.
     *
     * @param objectType objecttype
     * @return true indien het een hisvolledig type is.
     */
    protected boolean isHisVolledigType(final GeneriekElement objectType) {
        boolean resultaat = false;
        for (ObjectType type : getHisVolledigObjectTypen()) {
            if (type.getId() == objectType.getId()) {
                resultaat = true;
                break;
            }
        }
        return resultaat;
    }

    /**
     * Checkt of het stamgegeven een bestaansperiode heeft.
     *
     * @param objectType het te checken stamgegeven.
     * @return true indien het objecttype een bestaansperiode heeft.
     */
    protected boolean isBestaansPeriodeStamgegeven(final ObjectType objectType) {
        final ObjectType operationeelModelObjectTypeVanStamgegeven =
                getBmrDao().getElementVoorSyncIdVanLaag(objectType.getSyncid(), BmrLaag.OPERATIONEEL, ObjectType.class);
        return operationeelModelObjectTypeVanStamgegeven.getHistorieVastleggen() != null
                && operationeelModelObjectTypeVanStamgegeven.getHistorieVastleggen()
                == SoortHistorie.BESTAANSPERIODE.getHistorieVastleggen();
    }

    protected final boolean heeftGroepMetHistorieExclusiefIdentiteitsgroepen(final ObjectType objectType) {
        for (Groep groep : getBmrDao().getGroepenVoorObjectType(objectType)) {
            if (isGroepMetHisMomentRelevanteHistorie(groep)) {
                return true;
            }
        }
        return false;
    }

    protected final boolean isGroepMetHisMomentRelevanteHistorie(final Groep groep) {
        return HISTORIEPATROON_HIS_MOMENT.contains(groep.getHistoriePatroon());
    }

}

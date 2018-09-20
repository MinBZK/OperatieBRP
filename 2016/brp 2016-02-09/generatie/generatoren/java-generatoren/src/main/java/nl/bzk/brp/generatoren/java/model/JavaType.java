/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nl.bzk.brp.generatoren.java.util.GeneratiePackage;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Een java type dat gebruikt wordt als veld type, functie parameter type, etc.
 */
public class JavaType implements Cloneable {

    public static final String DUMMY_PACKAGE = "dummy-package";

    private static final int HASHCODE_GETAL1 = 97;
    private static final int HASHCODE_GETAL2 = 73;

    /*
     * SPECIALE GEVALLEN.
     */
    /**
     * Speciaal geval: void.
     */
    public static final JavaType VOID              = new JavaType("void", DUMMY_PACKAGE);
    /**
     * Speciaal geval: boolean (als primitieve i.p.v. als object).
     */
    public static final JavaType BOOLEAN_PRIMITIVE = new JavaType("boolean", DUMMY_PACKAGE);

    /*
     * STANDAARD JAVA TYPES.
     */
    /**
     * java.lang.String.
     */
    public static final JavaType STRING            = new JavaType("String", "java.lang");
    /**
     * java.lang.Boolean.
     */
    public static final JavaType BOOLEAN           = new JavaType("Boolean", "java.lang");
    /**
     * java.lang.Byte.
     */
    public static final JavaType BYTE              = new JavaType("Byte", "java.lang");
    /**
     * java.lang.Short.
     */
    public static final JavaType SHORT             = new JavaType("Short", "java.lang");
    /**
     * java.lang.Long.
     */
    public static final JavaType LONG              = new JavaType("Long", "java.lang");
    /**
     * java.math.BigDecimal.
     */
    public static final JavaType BIGDECIMAL        = new JavaType("BigDecimal", "java.math");
    /**
     * java.lang.Integer.
     */
    public static final JavaType INTEGER           = new JavaType("Integer", "java.lang");
    /**
     * java.lang.Override.
     */
    public static final JavaType OVERRIDE          = new JavaType("Override", "java.lang");
    /**
     * java.lang.Override.
     */
    public static final JavaType SUPPRESS_WARNINGS = new JavaType("SuppressWarnings", "java.lang");
    /**
     * java.util.Collection.
     */
    public static final JavaType COLLECTION        = new JavaType("Collection", "java.util");
    /**
     * java.util.Collections.
     */
    public static final JavaType COLLECTIONS       = new JavaType("Collections", "java.util");
    /**
     * java.util.Iterator.
     */
    public static final JavaType ITERATOR          = new JavaType("Iterator", "java.util");
    /**
     * java.util.List.
     */
    public static final JavaType LIST              = new JavaType("List", "java.util");
    /**
     * java.util.ArrayList.
     */
    public static final JavaType ARRAY_LIST        = new JavaType("ArrayList", "java.util");
    /**
     * java.util.Arrays.
     */
    public static final JavaType ARRAYS            = new JavaType("Arrays", "java.util");
    /**
     * java.util.Set.
     */
    public static final JavaType SET               = new JavaType("Set", "java.util");
    /**
     * java.util.HashSet.
     */
    public static final JavaType HASH_SET          = new JavaType("HashSet", "java.util");
    /**
     * java.util.TreeSet.
     */
    public static final JavaType TREE_SET          = new JavaType("TreeSet", "java.util");
    /**
     * java.util.SortedSet.
     */
    public static final JavaType SORTED_SET        = new JavaType("SortedSet", "java.util");
    /**
     * java.util.Map.
     */
    public static final JavaType MAP               = new JavaType("Map", "java.util");
    /**
     * java.util.HashMap.
     */
    public static final JavaType HASH_MAP          = new JavaType("HashMap", "java.util");
    /**
     * java.util.Properties.
     */
    public static final JavaType PROPERTIES        = new JavaType("Properties", "java.util");
    /**
     * java.io.IOException.
     */
    public static final JavaType INPUTSTREAM       = new JavaType("InputStream", "java.io");
    /**
     * java.io.IOException.
     */
    public static final JavaType IO_EXCEPTION      = new JavaType("IOException", "java.io");
    /**
     * nl.bzk.brp.logging.Logger.
     */
    public static final JavaType LOGGER            = new JavaType("Logger", "nl.bzk.brp.logging");
    /**
     * nl.bzk.brp.logging.LoggerFactory.
     */
    public static final JavaType LOGGER_FACTORY    = new JavaType("LoggerFactory", "nl.bzk.brp.logging");

    /*
     * SPECIFIEKE JAVA TYPES VOOR GENERATIE.
     */
    /**
     * HisMoment interface.
     */
    public static final JavaType HISMOMENT_INTERFACE                       =
        new JavaType("HisMoment", GeneratiePackage.OBJECTTYPE_LOGISCH_PACKAGE.getPackage());
    /**
     * HisVolledigRootObject interface.
     */
    public static final JavaType HISVOLLEDIG_ROOT_OBJECT                   =
        new JavaType("HisVolledigRootObject", GeneratiePackage.BRP_MODEL_BASEPACKAGE.getPackage());
    /**
     * HisVolledig (tagging) interface, voor boven alle XxxHisVolledig interfaces.
     */
    public static final JavaType MODEL_PERIODE_INTERFACE                   =
        new JavaType("ModelPeriode", GeneratiePackage.BRP_MODEL_BASIS_PACKAGE.getPackage());
    /**
     * View (tagging) interface, voor boven alle XxxView klasses.
     */
    public static final JavaType MODEL_MOMENT_INTERFACE                    =
        new JavaType("ModelMoment", GeneratiePackage.BRP_MODEL_BASIS_PACKAGE.getPackage());
    /**
     * Super interface van alle vaste attribuut waardes.
     */
    public static final JavaType VASTE_ATTRIBUUT_WAARDE                    =
        new JavaType("VasteAttribuutWaarde", GeneratiePackage.BRP_MODEL_BASIS_PACKAGE.getPackage());
    /**
     * Super klasse van alle attributen.
     */
    public static final JavaType ABSTRACT_ATTRIBUUT                        =
        new JavaType("AbstractAttribuut", GeneratiePackage.BRP_MODEL_BASIS_PACKAGE.getPackage());
    /**
     * Super klasse van alle bericht entiteiten (specialisatie van bericht object types).
     */
    public static final JavaType ABSTRACT_BERICHT_ENTITEIT                 =
        new JavaType("AbstractBerichtEntiteit", GeneratiePackage.BRP_MODEL_BASIS_PACKAGE.getPackage());
    /**
     * Super klasse van alle bericht elementen (objecttypes en groepen) die identificeerbaar zijn.
     */
    public static final JavaType ABSTRACT_BERICHT_IDENTIFICEERBAAR         =
        new JavaType("AbstractBerichtIdentificeerbaar", GeneratiePackage.BRP_MODEL_BASIS_PACKAGE.getPackage());
    /**
     * Super klasse van alle groep bericht types met formele historie.
     */
    public static final JavaType ABSTRACT_FORMELE_HISTORIE_GROEP_BERICHT   =
        new JavaType("AbstractFormeleHistorieGroepBericht",
            GeneratiePackage.BRP_MODEL_BASIS_PACKAGE.getPackage());
    /**
     * Super klasse van alle groep bericht types met materiele historie.
     */
    public static final JavaType ABSTRACT_MATERIELE_HISTORIE_GROEP_BERICHT =
        new JavaType("AbstractMaterieleHistorieGroepBericht",
            GeneratiePackage.BRP_MODEL_BASIS_PACKAGE.getPackage());
    /**
     * Super klasse van alle dynamische objecten.
     */
    public static final JavaType ABSTRACT_DYNAMISCH_OBJECT                 =
        new JavaType("AbstractDynamischObject", GeneratiePackage.BRP_MODEL_BASIS_PACKAGE.getPackage());
    /**
     * Super interface van alle objecten.
     */
    public static final JavaType BRP_OBJECT                                =
        new JavaType("BrpObject", GeneratiePackage.BRP_MODEL_BASIS_PACKAGE.getPackage());
    /**
     * Super interface van alle bericht entiteiten (specialisatie van bericht object types).
     */
    public static final JavaType BERICHT_ENTITEIT                          =
        new JavaType("BerichtEntiteit", GeneratiePackage.BRP_MODEL_BASIS_PACKAGE.getPackage());
    /**
     * Super interface van alle groepen.
     */
    public static final JavaType GROEP                                     =
        new JavaType("Groep", GeneratiePackage.BRP_MODEL_BASIS_PACKAGE.getPackage());
    /**
     * Super interface van alle elementen (objecttypes en groepen) die op Meta ID identificeerbaar zijn.
     */
    public static final JavaType META_IDENTIFICEERBAAR                     =
        new JavaType("MetaIdentificeerbaar", GeneratiePackage.BRP_MODEL_BASIS_PACKAGE.getPackage());
    /**
     * Super interface voor formele historie.
     */
    public static final JavaType FORMELE_HISTORIE                          =
        new JavaType("FormeleHistorie", GeneratiePackage.BRP_MODEL_BASIS_PACKAGE.getPackage());
    /**
     * Super interface voor materiele historie.
     */
    public static final JavaType MATERIELE_HISTORIE                        =
        new JavaType("MaterieleHistorie", GeneratiePackage.BRP_MODEL_BASIS_PACKAGE.getPackage());

    /**
     * Interface voor de formele bestaansperiode
     */
    public static final JavaType BESTAANSPERIODE_FORMEEL =
        new JavaType("BestaansperiodeFormeel", GeneratiePackage.BRP_MODEL_BASIS_PACKAGE.getPackage());

    /**
     * Interface voor de formele materiele bestaansperiode
     */
    public static final JavaType BESTAANSPERIODE_FORMEEL_MATERIEEL =
        new JavaType("BestaansperiodeFormeelMaterieel", GeneratiePackage.BRP_MODEL_BASIS_PACKAGE.getPackage());

    /**
     * Interface voor de formele materiele bestaansperiode
     */
    public static final JavaType BESTAANSPERIODE_FORMEEL_IMPLICIETMATERIEEL =
        new JavaType("BestaansperiodeFormeelImplicietMaterieel", GeneratiePackage.BRP_MODEL_BASIS_PACKAGE.getPackage());

    /**
     * Interface voor de formele materiele bestaansperiode
     */
    public static final JavaType BESTAANSPERIODE_FORMEEL_IMPLICIETMATERIEEL_AUTAUT =
            new JavaType("BestaansperiodeFormeelImplicietMaterieelAutaut", GeneratiePackage.BRP_MODEL_BASIS_PACKAGE.getPackage());

    /**
     * Super interface voor materiele historie.
     */
    public static final JavaType VERANTWOORDINGS_ENTITEIT                                =
        new JavaType("VerantwoordingsEntiteit", GeneratiePackage.BRP_MODEL_BASIS_PACKAGE.getPackage());
    /**
     * Super klasse van formele historie entiteiten met actie verantwoording.
     */
    public static final JavaType ABSTRACT_FORMEEL_HISTORISCH_MET_ACTIE_VERANTWOORDING    =
        new JavaType("AbstractFormeelHistorischMetActieVerantwoording",
            GeneratiePackage.BRP_MODEL_BASIS_PACKAGE.getPackage());
    /**
     * Super klasse van materiele historie entiteiten met actie verantwoording..
     */
    public static final JavaType ABSTRACT_MATERIEEL_HISTORISCH_MET_ACTIE_VERANTWOORDING  =
        new JavaType("AbstractMaterieelHistorischMetActieVerantwoording",
            GeneratiePackage.BRP_MODEL_BASIS_PACKAGE.getPackage());
    /**
     * Super klasse van formele historie entiteiten met dienst verantwoording..
     */
    public static final JavaType ABSTRACT_FORMEEL_HISTORISCH_MET_DIENST_VERANTWOORDING   =
        new JavaType("AbstractFormeelHistorischMetDienstVerantwoording",
            GeneratiePackage.BRP_MODEL_BASIS_PACKAGE.getPackage());
    /**
     * Super klasse van materiele historie entiteiten met dienst verantwoording..
     */
    public static final JavaType ABSTRACT_MATERIEEL_HISTORISCH_MET_DIENST_VERANTWOORDING =
        new JavaType("AbstractMaterieelHistorischMetDienstVerantwoording",
            GeneratiePackage.BRP_MODEL_BASIS_PACKAGE.getPackage());
    /**
     * Super interface voor historie entiteiten.
     */
    public static final JavaType HISTORIE_ENTITEIT                                       =
        new JavaType("HistorieEntiteit", GeneratiePackage.BRP_MODEL_BASIS_PACKAGE.getPackage());
    /**
     * Interface voor stamgegevens met een bestaansperiode.
     */
    public static final JavaType BESTAANS_PERIODE_STAMGEGEVEN                            =
        new JavaType("BestaansPeriodeStamgegeven", GeneratiePackage.BRP_MODEL_BASIS_PACKAGE.getPackage());
    /**
     * Altijd tonen groep predikaat.
     */
    public static final JavaType ALTIJD_TONEN_GROEP_PREDIKAAT                            =
        new JavaType("AltijdTonenGroepPredikaat",
            GeneratiePackage.OBJECTTYPE_HISVOLLEDIG_PREDIKAAT_PACKAGE.getPackage());
    /**
     * Is in onderzoek predikaat.
     */
    public static final JavaType IS_IN_ONDERZOEK_PREDIKAAT                              =
        new JavaType("IsInOnderzoekPredikaat",
            GeneratiePackage.OBJECTTYPE_HISVOLLEDIG_PREDIKAAT_PACKAGE.getPackage());
    /**
     * Abstract super klasse voor alle his volledig predikaat views.
     */
    public static final JavaType ABSTRACT_HIS_VOLLEDIG_PREDIKAAT_VIEW                    =
        new JavaType("AbstractHisVolledigPredikaatView",
            GeneratiePackage.OBJECTTYPE_HISVOLLEDIG_PREDIKAAT_VIEW_PACKAGE.getPackage());
    /**
     * Utility klasse voor de predikaat views.
     */
    public static final JavaType HIS_VOLLEDIG_PREDIKAAT_VIEW_UTIL                        =
        new JavaType("HisVolledigPredikaatViewUtil",
            GeneratiePackage.OBJECTTYPE_HISVOLLEDIG_PREDIKAAT_VIEW_PACKAGE.getPackage());
    /**
     * Comparator voor formele historie entiteit in een lever bericht.
     */
    public static final JavaType FORMELE_HISTORIE_ENTITEIT_COMPARATOR                    =
        new JavaType("FormeleHistorieEntiteitComparator",
            GeneratiePackage.OBJECTTYPE_HISVOLLEDIG_PREDIKAAT_VIEW_PACKAGE.getPackage());
    /**
     * Comparator factory.
     */
    public static final JavaType COMPARATOR_FACTORY                                      =
        new JavaType("ComparatorFactory", GeneratiePackage.BRP_MODEL_BASIS_PACKAGE.getPackage());

    /**
     * His Volledig Comparator factory.
     */
    public static final JavaType HIS_VOLLEDIG_COMPARATOR_FACTORY =
        new JavaType("HisVolledigComparatorFactory", GeneratiePackage.OBJECTTYPE_HISVOLLEDIG_INTERFACES_PACKAGE.getPackage() + ".kern");

    /**
     * Comparator voor materiele historie entiteit in een lever bericht.
     */
    public static final JavaType MATERIELE_HISTORIE_ENTITEIT_COMPARATOR =
        new JavaType("MaterieleHistorieEntiteitComparator",
            GeneratiePackage.OBJECTTYPE_HISVOLLEDIG_PREDIKAAT_VIEW_PACKAGE.getPackage());
    /**
     * Comparator voor materiele historie entiteit in een lever bericht.
     */
    public static final JavaType ID_COMPARATOR                          =
        new JavaType("IdComparator",
            GeneratiePackage.BRP_MODEL_BASIS_PACKAGE.getPackage());
    /**
     * DatumTijd type, wordt gebruikt in de views.
     */
    public static final JavaType DATUMTIJD_ATTRIBUUT                    =
        new JavaType("DatumTijdAttribuut", GeneratiePackage.ATTRIBUUTTYPE_PACKAGE.getPackage() + ".kern");
    /**
     * Datum type, wordt gebruikt in de views.
     */
    public static final JavaType DATUM_ATTRIBUUT                        =
        new JavaType("DatumAttribuut", GeneratiePackage.ATTRIBUUTTYPE_PACKAGE.getPackage() + ".kern");
    /**
     * Datum basis type.
     */
    public static final JavaType DATUM_BASIS_ATTRIBUUT                  =
        new JavaType("AbstractDatumBasisAttribuut", GeneratiePackage.BRP_MODEL_BASIS_PACKAGE.getPackage());
    /**
     * MaterieleHistorieSet interface.
     */
    public static final JavaType MATERIELE_HISTORIE_SET                 =
        new JavaType("MaterieleHistorieSet",
            GeneratiePackage.BRP_MODEL_BASEPACKAGE.getPackage());
    /**
     * MaterieleHistorieSet impl.
     */
    public static final JavaType MATERIELE_HISTORIE_SET_IMPL            =
        new JavaType("MaterieleHistorieSetImpl",
            GeneratiePackage.BRP_MODEL_BASEPACKAGE.getPackage());
    /**
     * FormeleHistorieSet interface.
     */
    public static final JavaType FORMELE_HISTORIE_SET                   =
        new JavaType("FormeleHistorieSet",
            GeneratiePackage.BRP_MODEL_BASEPACKAGE.getPackage());
    /**
     * FormeleHistorieSet impl.
     */
    public static final JavaType FORMELE_HISTORIE_SET_IMPL              =
        new JavaType("FormeleHistorieSetImpl",
            GeneratiePackage.BRP_MODEL_BASEPACKAGE.getPackage());
    /**
     * ALaagAfleidbaar interface.
     */
    public static final JavaType A_LAAG_AFLEIDBAAR                      =
        new JavaType("ALaagAfleidbaar",
            GeneratiePackage.BRP_MODEL_BASIS_PACKAGE.getPackage());
    /**
     * DatabaseObject interface.
     */
    public static final JavaType DATABASE_OBJECT                        =
        new JavaType("DatabaseObject",
            GeneratiePackage.STAMGEGEVEN_STATISCH_PACKAGE.getPackage() + ".kern");

    /**
     * Element enum.
     */
    public static final JavaType ELEMENT_ENUM =
        new JavaType("ElementEnum",
            GeneratiePackage.STAMGEGEVEN_STATISCH_PACKAGE.getPackage() + ".kern");

    /**
     * Element enum.
     */
    public static final JavaType ELEMENT_IDENTIFICEERBAAR =
        new JavaType("ElementIdentificeerbaar",
            GeneratiePackage.BRP_MODEL_BASIS_PACKAGE.getPackage());

    /**
     * VolgnummerBevattend interface.
     */
    public static final JavaType SYNCHRONISEERBAAR_STAMGEGEVEN =
        new JavaType("SynchroniseerbaarStamgegeven",
            GeneratiePackage.BRP_MODEL_BASIS_PACKAGE.getPackage());

    /**
     * Synchronisatie stamgegeven interface.
     */
    public static final JavaType VOLGNUMMER_BEVATTEND = new JavaType("VolgnummerBevattend",
        GeneratiePackage.BRP_MODEL_BASIS_PACKAGE.getPackage());

    /**
     * HisVolledigModel/Model identificeerbaar interface.
     */
    public static final JavaType MODEL_IDENTIFICEERBAAR                         = new JavaType("ModelIdentificeerbaar",
        GeneratiePackage.BRP_MODEL_BASIS_PACKAGE.getPackage());
    /**
     * Volgnummer Serialisatie Set Implementatie.
     */
    public static final JavaType VOLGNUMMER_SET_IMPLEMENTATIE                   =
        new JavaType("GesorteerdeSetOpVolgnummer",
            GeneratiePackage.BRP_MODEL_BASIS_PACKAGE.getPackage());
    /**
     * Volgnummer Comparator.
     */
    public static final JavaType VOLGNUMMER_COMPARATOR                          = new JavaType("VolgnummerComparator",
        GeneratiePackage.BRP_MODEL_BASIS_PACKAGE.getPackage());
    /**
     * AttribuutAccessor annotatie.
     */
    public static final JavaType ATTRIBUUT_ACCESSOR                             =
        new JavaType("AttribuutAccessor", "nl.bzk.brp.model.annotaties");
    /**
     * GroepAccessor annotatie.
     */
    public static final JavaType GROEP_ACCESSOR                                 =
        new JavaType("GroepAccessor", "nl.bzk.brp.model.annotaties");
    /**
     * Persistent enum interface voor gebruik in hibernate user types.
     */
    public static final JavaType PERSISTENT_ENUM                                =
        new JavaType("PersistentEnum", "nl.bzk.brp.model.jpa.usertypes");
    /**
     * Tagging interface HisVolledigImpl.
     */
    public static final JavaType HISVOLLEDIGIMPL                                =
        new JavaType("HisVolledigImpl", GeneratiePackage.BRP_MODEL_BASIS_PACKAGE.getPackage());
    /**
     * Stamgegeven builder util.
     */
    public static final JavaType STAMGEGEVEN_BUILDER                            =
        new JavaType("StamgegevenBuilder", GeneratiePackage.BRP_BASEPACKAGE.getPackage() + ".util");
    /**
     * His persoon indicatie materiele historie model.
     */
    public static final JavaType HIS_PERSOON_INDICATIE_MATERIELE_HISTORIE_MODEL =
        new JavaType("AbstractHisPersoonIndicatieMaterieleHistorieModel",
            GeneratiePackage.OBJECTTYPE_OPERATIONEEL_PACKAGE.getPackage() + ".kern");
    /**
     * His persoon indicatie formele historie model.
     */
    public static final JavaType HIS_PERSOON_INDICATIE_FORMELE_HISTORIE_MODEL   =
        new JavaType("AbstractHisPersoonIndicatieFormeleHistorieModel",
            GeneratiePackage.OBJECTTYPE_OPERATIONEEL_PACKAGE.getPackage() + ".kern");

    /*
     * MODEL SPECIFIEKE JAVA TYPES.
     * Eigenlijk een beetje cheating, aangezien deze zelf ook gegenereerd worden, maar ze zijn nodig
     * voor de his volledig impl builder. (en indicaties)
     */
    /**
     * Actie bericht.
     */
    public static final JavaType ACTIE_BERICHT                     = new JavaType("ActieBericht",
        GeneratiePackage.OBJECTTYPE_BERICHT_PACKAGE.getPackage() + ".kern");
    /**
     * Actie model.
     */
    public static final JavaType ACTIE_MODEL                       = new JavaType("ActieModel",
        GeneratiePackage.OBJECTTYPE_OPERATIONEEL_PACKAGE.getPackage() + ".kern");
    /**
     * Dienst model.
     */
    public static final JavaType DIENST = new JavaType("Dienst",
        GeneratiePackage.OBJECTTYPE_OPERATIONEEL_PACKAGE.getPackage() + ".autaut");
    /**
     * HisBetrokkenheid Model.
     */
    public static final JavaType HIS_BETROKKENHEID_MODEL           = new JavaType("HisBetrokkenheidModel",
        GeneratiePackage.OBJECTTYPE_OPERATIONEEL_PACKAGE.getPackage() + ".kern");
    /**
     * Soort actie.
     */
    public static final JavaType SOORT_ACTIE                       = new JavaType("SoortActie",
        GeneratiePackage.STAMGEGEVEN_STATISCH_PACKAGE.getPackage() + ".kern");
    /**
     * Soort actie attribuut.
     */
    public static final JavaType SOORT_ACTIE_ATTRIBUUT             = new JavaType("SoortActieAttribuut",
        GeneratiePackage.STAMGEGEVEN_STATISCH_PACKAGE.getPackage() + ".kern");
    /**
     * Persoon indicatie standaard groep.
     */
    public static final JavaType PERSOON_INDICATIE_STANDAARD_GROEP = new JavaType(
        "PersoonIndicatieStandaardGroep", GeneratiePackage.GROEP_LOGISCH_PACKAGE.getPackage() + ".kern");
    /**
     * Ja attribuut.
     */
    public static final JavaType JA_ATTRIBUUT                      = new JavaType(
        "JaAttribuut", GeneratiePackage.ATTRIBUUTTYPE_PACKAGE.getPackage() + ".kern");

    /**
     * Ja.
     */
    public static final JavaType JA = new JavaType(
        "Ja", GeneratiePackage.ATTRIBUUTTYPE_PACKAGE.getPackage() + ".kern");

    /*
     * SPECIFIEKE JAVA TYPES VOOR OVERIGE LIBRARIES.
     */
    /**
     * Apache collection utils.
     */
    public static final JavaType COLLECTION_UTILS = new JavaType("CollectionUtils",
        "org.apache.commons.collections");
    /**
     * Apache collections - Functors.
     */
    public static final JavaType COLLECTION_UTILS_ANDPREDICATE = new JavaType("AndPredicate",
        "org.apache.commons.collections.functors");
    /**
     * Apache predicate utils.
     */
    public static final JavaType PREDICATE_UTILS  = new JavaType("PredicateUtils",
        "org.apache.commons.collections");
    /**
     * Apache collections - predikaat interface.
     */
    public static final JavaType PREDIKAAT_APACHE = new JavaType("Predicate",
        "org.apache.commons.collections");

    /*
     * SPECIFIEKE JAVA TYPES VOOR JPA / HIBERNATE / SPRING.
     */
    /**
     * JPA annotatie.
     */
    public static final JavaType EMBEDDABLE           = new JavaType("Embeddable", "javax.persistence");
    /**
     * JPA annotatie.
     */
    public static final JavaType EMBEDDED             = new JavaType("Embedded", "javax.persistence");
    /**
     * JPA annotatie.
     */
    public static final JavaType ATTRIBUTE_OVERRIDE   = new JavaType("AttributeOverride", "javax.persistence");
    /**
     * JPA annotatie.
     */
    public static final JavaType ASSOCIATION_OVERRIDE = new JavaType("AssociationOverride", "javax.persistence");
    /**
     * JPA annotatie.
     */
    public static final JavaType COLUMN               = new JavaType("Column", "javax.persistence");
    /**
     * JPA annotatie.
     */
    public static final JavaType TABLE                = new JavaType("Table", "javax.persistence");
    /**
     * JPA annotatie.
     */
    public static final JavaType ENTITY               = new JavaType("Entity", "javax.persistence");
    /**
     * JPA annotatie.
     */
    public static final JavaType ACCESS               = new JavaType("Access", "javax.persistence");
    /**
     * JPA annotatie.
     */
    public static final JavaType ACCESS_TYPE          = new JavaType("AccessType", "javax.persistence");
    /**
     * JPA annotatie.
     */
    public static final JavaType ENTITY_LISTENERS     = new JavaType("EntityListeners", "javax.persistence");
    /**
     * JPA annotatie.
     */
    public static final JavaType MANY_TO_ONE          = new JavaType("ManyToOne", "javax.persistence");
    /**
     * JPA annotatie.
     */
    public static final JavaType ONE_TO_MANY          = new JavaType("OneToMany", "javax.persistence");
    /**
     * JPA annotatie.
     */
    public static final JavaType ONE_TO_ONE           = new JavaType("OneToOne", "javax.persistence");
    /**
     * JPA annotatie.
     */
    public static final JavaType JOIN_COLUMN          = new JavaType("JoinColumn", "javax.persistence");
    /**
     * JPA annotatie.
     */
    public static final JavaType ID                   = new JavaType("Id", "javax.persistence");
    /**
     * JPA annotatie.
     */
    public static final JavaType TRANSIENT            = new JavaType("Transient", "javax.persistence");
    /**
     * JPA annotatie.
     */
    public static final JavaType ENUMERATED           = new JavaType("Enumerated", "javax.persistence");
    /**
     * JPA annotatie.
     */
    public static final JavaType SEQUENCE_GENERATOR   = new JavaType("SequenceGenerator", "javax.persistence");
    /**
     * JPA annotatie.
     */
    public static final JavaType GENERATED_VALUE      = new JavaType("GeneratedValue", "javax.persistence");
    /**
     * JPA annotatie.
     */
    public static final JavaType GENERATION_TYPE      = new JavaType("GenerationType", "javax.persistence");
    /**
     * JPA annotatie.
     */
    public static final JavaType MAPPED_SUPER_CLASS   = new JavaType("MappedSuperclass", "javax.persistence");
    /**
     * JPA annotatie.
     */
    public static final JavaType DISCRIMINATOR_VALUE  = new JavaType("DiscriminatorValue", "javax.persistence");
    /**
     * JPA annotatie.
     */
    public static final JavaType INHERITANCE          = new JavaType("Inheritance", "javax.persistence");
    /**
     * JPA annotatie.
     */
    public static final JavaType DISCRIMINATOR_COLUMN = new JavaType("DiscriminatorColumn", "javax.persistence");
    /**
     * JPA annotatie.
     */
    public static final JavaType FETCH_TYPE           = new JavaType("FetchType", "javax.persistence");
    /**
     * JPA annotatie.
     */
    public static final JavaType CASCADE_TYPE         = new JavaType("CascadeType", "javax.persistence");
    /**
     * JPA annotatie.
     */
    public static final JavaType INHERITANCE_TYPE     = new JavaType("InheritanceType", "javax.persistence");
    /**
     * JPA annotatie.
     */
    public static final JavaType DISCRIMINATOR_TYPE   = new JavaType("DiscriminatorType", "javax.persistence");

    /**
     * Hibernate annotatie.
     */
    public static final JavaType FETCH                       = new JavaType("Fetch", "org.hibernate.annotations");
    /**
     * Hibernate annotatie.
     */
    public static final JavaType FETCH_MODE                  = new JavaType("FetchMode", "org.hibernate.annotations");
    /**
     * Hibernate annotatie.
     */
    public static final JavaType TYPE                        = new JavaType("Type", "org.hibernate.annotations");
    /**
     * Hibernate annotatie.
     */
    public static final JavaType SORT                        = new JavaType("Sort", "org.hibernate.annotations");
    /**
     * Hibernate annotatie.
     */
    public static final JavaType SORT_TYPE                   = new JavaType("SortType", "org.hibernate.annotations");
    /**
     * Hibernate annotatie voor 'immutable' entiteiten.
     */
    public static final JavaType IMMUTABLE                   = new JavaType("Immutable", "org.hibernate.annotations");
    /**
     * Hibernate annotatie voor 'Cache'.
     */
    public static final JavaType CACHE                       = new JavaType("Cache", "org.hibernate.annotations");
    /**
     * Hibernate annotatie voor 'CacheConcurrencyStrategy'.
     */
    public static final JavaType CACHE_CONCURRENCY_STRATEGIE =
        new JavaType("CacheConcurrencyStrategy", "org.hibernate.annotations");

    /**
     * Spring Reflection Test Utils.
     */
    public static final JavaType REFLECTION_TEST_UTILS =
        new JavaType("ReflectionTestUtils", "org.springframework.test.util");

    /*
     * SPECIFIEKE JAVA TYPES VOOR JSON.
     */
    /**
     * JsonProperty annotatie.
     */
    public static final JavaType JSON_PROPERTY         =
        new JavaType("JsonProperty", "com.fasterxml.jackson.annotation");
    /**
     * JsonValue annotatie.
     */
    public static final JavaType JSON_VALUE            =
        new JavaType("JsonValue", "com.fasterxml.jackson.annotation");
    /**
     * JsonBackReference annotatie.
     */
    public static final JavaType JSON_BACKREFERENCE    =
        new JavaType("JsonBackReference", "com.fasterxml.jackson.annotation");
    /**
     * JsonBackReference annotatie.
     */
    public static final JavaType JSON_MANAGEDREFERENCE =
        new JavaType("JsonManagedReference", "com.fasterxml.jackson.annotation");
    /**
     * JsonCreator annotatie.
     */
    public static final JavaType JSON_CREATOR          =
        new JavaType("JsonCreator", "com.fasterxml.jackson.annotation");
    /**
     * JsonDeserialize annotatie.
     */
    public static final JavaType JSON_DESERIALIZE      =
        new JavaType("JsonDeserialize", "com.fasterxml.jackson.databind.annotation");

    /**
     * Javax annotatie die gegenereerde klassen markeert.
     */
    public static final JavaType GENERATED =
        new JavaType("Generated", "javax.annotation");

    /**
     * Lijst van numerieke JavaTypes; java types die java.lang.Number extenden.
     */
    private static final List<JavaType> NUMERIEKE_JAVATYPES = Arrays.asList(SHORT, INTEGER, LONG, BYTE, BIGDECIMAL);

    private String                     naam;
    private String                     packagePad;
    private List<JavaGenericParameter> genericParameters;

    /**
     * Maak een nieuwe instantie van een JavaType.
     *
     * @param naam       de klasse naam
     * @param packagePad het package pad
     */
    public JavaType(final String naam, final String packagePad)
    {
        this(naam, packagePad, null);
    }

    /**
     * Maak een nieuwe instantie van een JavaType.
     *
     * @param javaType         een java type
     * @param genericParameter het generic parameter type
     */
    public JavaType(final JavaType javaType, final JavaGenericParameter genericParameter)
    {
        this(javaType.getNaam(), javaType.getPackagePad(), genericParameter);
    }

    /**
     * Maak een nieuwe instantie van een JavaType.
     *
     * @param javaType         een java type
     * @param genericParameter het generic parameter type
     */
    public JavaType(final JavaType javaType, final JavaType genericParameter)
    {
        this(javaType, genericParameter, false);
    }

    /**
     * Maak een nieuwe instantie van een JavaType.
     *
     * @param javaType                           een java type
     * @param genericParameter                   het generic parameter type
     * @param genericParameterSubtypesToegestaan of er generic parameter subtypes zijn toegestaan
     */
    public JavaType(final JavaType javaType, final JavaType genericParameter,
        final boolean genericParameterSubtypesToegestaan)
    {
        this(javaType.getNaam(), javaType.getPackagePad(),
            new JavaGenericParameter(genericParameter, genericParameterSubtypesToegestaan));
    }

    /**
     * Maak een nieuwe instantie van een JavaType.
     *
     * @param naam             de klasse naam
     * @param packagePad       het package pad
     * @param genericParameter het generic parameter type
     */
    public JavaType(final String naam, final String packagePad, final JavaGenericParameter genericParameter)
    {
        this.naam = naam;
        this.packagePad = packagePad;
        this.genericParameters = new ArrayList<>();
        if (genericParameter != null) {
            this.genericParameters.add(genericParameter);
        }
    }

    public String getNaam() {
        return naam;
    }

    public String getPackagePad() {
        return packagePad;
    }

    /**
     * Retourneert de 'Fully Qualified Class Name' van het JavaType. Dit is dus de naam, voorafgegaan door het pad naar de Java klasse.
     *
     * @return de 'Fully Qualified Class Name' van het JavaType.
     */
    public String getFullyQualifiedClassName() {
        return this.packagePad + "." + this.naam;
    }

    /**
     * Geeft aan of dit JavaType generic parameters heeft (bijvoorbeeld bij collecties).
     *
     * @return boolean die aangeeft of dit JavaType een generic parameter heeft.
     */
    public boolean isGeparametriseerd() {
        return aantalGenericParameters() > 0;
    }

    /**
     * Geeft het aantal generic parameters van dit JavaType.
     *
     * @return aantal generic parameters van dit JavaType.
     */
    public int aantalGenericParameters() {
        return genericParameters.size();
    }

    /**
     * Geeft de generic parameter op de opgegeven positie. De eerste generic parameter heeft index 0.
     *
     * @param index generic parameter index.
     * @return Generic parameter op gegeven positie.
     */
    public JavaGenericParameter getGenericParameter(final int index) {
        return genericParameters.get(index);
    }

    public List<JavaGenericParameter> getGenericParameters() {
        return genericParameters;
    }

    /**
     * Voegt een generic parameter toe aan het type.
     *
     * @param genericParameter toe te voegen generic parameter.
     */
    public void voegGenericParameterToe(final JavaGenericParameter genericParameter) {
        genericParameters.add(genericParameter);
    }

    /**
     * Voegt een generic parameter toe aan het type.
     *
     * @param genericParameter toe te voegen generic parameter.
     */
    public void voegGenericParameterToe(final JavaType genericParameter) {
        genericParameters.add(new JavaGenericParameter(genericParameter, false));
    }

    /**
     * Maak een nieuwe instantie van een JavaType vanuit een fully qualified class name.
     *
     * @param fullyQualifiedClassName de volledige klasse naam, inclusief pad
     * @return het java type
     */
    public static JavaType bouwVoorFullyQualifiedClassName(final String fullyQualifiedClassName) {
        int lastDot = fullyQualifiedClassName.lastIndexOf('.');
        String naam = fullyQualifiedClassName.substring(lastDot + 1);
        String packagePad = fullyQualifiedClassName.substring(0, lastDot);
        return new JavaType(naam, packagePad);
    }

    @Override
    public String toString() {
        StringBuilder toString = new StringBuilder();
        toString.append("JavaType: " + this.packagePad + "." + this.naam);

        if (isGeparametriseerd()) {
            toString.append("<");
            boolean first = true;
            for (JavaGenericParameter generic : genericParameters) {
                if (!first) {
                    toString.append(", ");
                }
                toString.append(generic.toString());
                first = false;
            }
            toString.append(">");
        }
        return toString.toString();
    }

    /**
     * Haal de gebruikte types voor het JavaType op. Dat wil zeggen: het type zelf en alle eventueel geneste generic parameter types.
     *
     * @return de gebruikte types.
     */
    public List<JavaType> getGebruikteTypes() {
        List<JavaType> gebruikteTypes = new ArrayList<>();
        for (JavaGenericParameter generic : genericParameters) {
            gebruikteTypes.addAll(generic.getGebruikteTypes());
        }
        gebruikteTypes.add(this);
        return gebruikteTypes;
    }

    /**
     * Geeft aan of dit javatype een numeriek java type is of niet.
     *
     * @return indicatie of dit javatype een numeriek java type is of niet.
     */
    public boolean isNumeriek() {
        return NUMERIEKE_JAVATYPES.contains(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        final JavaType clone = (JavaType) super.clone();
        clone.naam = this.naam;
        clone.packagePad = this.packagePad;
        clone.genericParameters = new ArrayList<>();
        for (JavaGenericParameter generic : genericParameters) {
            clone.voegGenericParameterToe((JavaGenericParameter) generic.clone());
        }
        return clone;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(HASHCODE_GETAL1, HASHCODE_GETAL2)
            .append(genericParameters).append(naam).append(packagePad)
            .toHashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        JavaType other = (JavaType) obj;
        return new EqualsBuilder()
            .append(this.genericParameters, other.genericParameters)
            .append(this.naam, other.naam)
            .append(this.packagePad, other.packagePad)
            .isEquals();
    }

}

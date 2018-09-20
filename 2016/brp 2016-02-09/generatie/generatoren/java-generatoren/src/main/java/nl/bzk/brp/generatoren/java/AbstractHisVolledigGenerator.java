/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nl.bzk.brp.generatoren.algemeen.common.BmrLaag;
import nl.bzk.brp.generatoren.java.model.GenerationGapJavaType;
import nl.bzk.brp.generatoren.java.model.JavaAccessModifier;
import nl.bzk.brp.generatoren.java.model.JavaFunctie;
import nl.bzk.brp.generatoren.java.model.JavaGenericParameter;
import nl.bzk.brp.generatoren.java.model.JavaKlasse;
import nl.bzk.brp.generatoren.java.model.JavaType;
import nl.bzk.brp.generatoren.java.model.annotatie.AnnotatieWaardeParameter;
import nl.bzk.brp.generatoren.java.model.annotatie.JavaAnnotatie;
import nl.bzk.brp.generatoren.java.naamgeving.HisMomentNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.HisVolledigInterfaceModelNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.LogischModelNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.NaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.OperationeelModelNaamgevingStrategie;
import nl.bzk.brp.metaregister.model.Attribuut;
import nl.bzk.brp.metaregister.model.Groep;
import nl.bzk.brp.metaregister.model.ObjectType;
import nl.bzk.brp.metaregister.model.Tuple;

/**
 * Abstracte tussenliggende generator klasse, die alle gemeenschappelijke functionaliteit
 * verzamelt die de his volledig generatoren delen.
 */
public abstract class AbstractHisVolledigGenerator extends AbstractJavaGenerator {

    public static final String PERSOON_VERANTWOORDING_GETTER_NAAM        = "getAdministratieveHandelingen";
    public static final String PERSOON_HEEFT_VERANTWOORDING_FUNCTIE_NAAM = "heeftAdministratieveHandelingenVoorLeveren";

    protected final NaamgevingStrategie logischModelNaamgevingStrategie  = new LogischModelNaamgevingStrategie();
    private final NaamgevingStrategie hisMomentNaamgevingStrategie       = new HisMomentNaamgevingStrategie();

    /**
     * Haal alle groepen uit het object type op die van toepassing zijn op de his volledig.
     *
     * @param objectType het object type
     * @return de his volledig groepen
     */
    protected List<Groep> getHisVolledigGroepen(final ObjectType objectType) {
        List<Groep> alleGroepen = getBmrDao().getGroepenVoorObjectType(objectType);
        List<Groep> hisVolledigGroepen = new ArrayList<>();
        for (Groep groep : alleGroepen) {
            if (behoortTotJavaOperationeelModel(groep) && !isIdentiteitGroep(groep)) {
                hisVolledigGroepen.add(groep);
            }
        }
        return hisVolledigGroepen;
    }

    /**
     * Haal de indicaties weg uit de inverse associaties (alleen van toepassing bij persoon).
     * Dit, zodat we de indicaties op een custom manier kunnen behandelen.
     *
     * @param inverseAttrVoorObjectType de lijst met inverse associaties.
     */
    protected void filterIndicatiesUitInverseAssociaties(final List<Attribuut> inverseAttrVoorObjectType) {
        Iterator<Attribuut> iter = inverseAttrVoorObjectType.iterator();
        while (iter.hasNext()) {
            if (iter.next().getObjectType().getId() == ID_PERSOON_INDICATIE) {
                iter.remove();
            }
        }
    }

    /**
     * Genereer de standaard onderdelen van een persoon indicatie subtype klasse.
     *
     * @param persoonIndicatieObjectType persoon indicatie object type
     * @param tuple de subtype tuple
     * @param naamgevingStrategie de naamgeving strategie voor het subtype
     * @return een eerste opzet van de klasse voor het subtype
     */
    protected JavaKlasse genereerPersoonIndicatieSubtypeSetup(final ObjectType persoonIndicatieObjectType,
            final Tuple tuple, final NaamgevingStrategie naamgevingStrategie)
    {
        // Bijbehorend his subtype en interface subtype
        JavaType hisIndicatieJavaType = bepaalHisIndicatieJavaType(tuple);
        JavaType defaultInterfaceIndicatieJavaType = new HisVolledigInterfaceModelNaamgevingStrategie()
                .getJavaTypeVoorElement(persoonIndicatieObjectType);
        JavaType interfaceIndicatieJavaType =
                maakPersoonIndicatieSubtypeJavaType(tuple, defaultInterfaceIndicatieJavaType);

        // Algemene zaken en mappings
        JavaType defaultKlasseType = naamgevingStrategie.getJavaTypeVoorElement(persoonIndicatieObjectType);
        JavaType indicatieJavaType = maakPersoonIndicatieSubtypeJavaType(tuple, defaultKlasseType);
        JavaKlasse klasse = new JavaKlasse(indicatieJavaType, "Subtype klasse voor indicatie "
                + tuple.getNaam());
        JavaType superKlasse = new JavaType(defaultKlasseType,
                new JavaGenericParameter(hisIndicatieJavaType));
        klasse.setExtendsFrom(superKlasse);
        klasse.voegSuperInterfaceToe(interfaceIndicatieJavaType);

        return klasse;
    }

    /**
     * Bepaal het his indicatie subtype voor deze tuple.
     *
     * @param tuple de tuple
     * @return het java type van het his indicatie subtype
     */
    protected JavaType bepaalHisIndicatieJavaType(final Tuple tuple) {
        final ObjectType hisIndicatieObjectType = this.getBmrDao().getElementVoorSyncIdVanLaag(
                SYNC_ID_HIS_PERSOON_INDICATIE, BmrLaag.OPERATIONEEL, ObjectType.class);
        JavaType defaultHisIndicatieJavaType = new OperationeelModelNaamgevingStrategie()
                .getJavaTypeVoorElement(hisIndicatieObjectType);
        return maakPersoonIndicatieSubtypeJavaType(tuple, defaultHisIndicatieJavaType);
    }

    /**
     * Custom getIndicaties, die alle getters in een lijstje bij elkaar zet (indien niet null).
     *
     * @param klasse de klasse
     * @param superType het super type
     * @param lijstVanIndicatieGetters string met java code: de lijst van getters
     */
    protected void voegCustomGetIndicatiesToe(final JavaKlasse klasse,
            final JavaType superType, final String lijstVanIndicatieGetters)
    {
        JavaType returnType = new JavaType(JavaType.SET, new JavaGenericParameter(superType, true));
        JavaFunctie functie = new JavaFunctie(JavaAccessModifier.PUBLIC,
                returnType, "getIndicaties", null);
        functie.voegAnnotatieToe(new JavaAnnotatie(JavaType.OVERRIDE));
        functie.voegAnnotatieToe(new JavaAnnotatie(JavaType.SUPPRESS_WARNINGS,
                new AnnotatieWaardeParameter("value", "unchecked")));
        functie.setBody(String.format("return Collections.unmodifiableSet(new HashSet<%1$s>("
                + "CollectionUtils.select(Arrays.asList(%2$s), PredicateUtils.notNullPredicate())));",
                superType.getNaam(), lijstVanIndicatieGetters));
        klasse.voegFunctieToe(functie);
        klasse.voegExtraImportsToe(JavaType.COLLECTIONS, JavaType.ARRAYS,
                JavaType.COLLECTION_UTILS, JavaType.PREDICATE_UTILS);
    }

    protected final boolean isBetrokkenheid(final ObjectType objectType) {
        return objectType.getFinaalSupertype() != null && objectType.getFinaalSupertype().getIdentCode().equals("Betrokkenheid");
    }

    protected final JavaType bepaalLogischModelSuperInterface(final ObjectType objectType) {
        final JavaType basisType = logischModelNaamgevingStrategie.getJavaTypeVoorElement(objectType);
        if (heeftGroepMetHistorieExclusiefIdentiteitsgroepen(objectType) && !isPersoonIndicatieObject(objectType)) {
            final JavaType javaType = hisMomentNaamgevingStrategie.getJavaTypeVoorElement(objectType);
            return new GenerationGapJavaType(javaType, basisType);
        }
        return basisType;
    }
}

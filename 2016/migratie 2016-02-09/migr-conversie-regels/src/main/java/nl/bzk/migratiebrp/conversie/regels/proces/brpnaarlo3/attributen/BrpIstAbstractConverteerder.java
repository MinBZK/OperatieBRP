/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.AbstractBrpIstGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstRelatieGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstStandaardGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3CategorieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3GezagsverhoudingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3KindInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Element;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieOnjuist;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RNIDeelnemerCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Elementnummer;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpAttribuutConverteerder.Lo3GemeenteLand;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

/**
 * Abstracte klasse voor de conversie van de IST-tabellen.
 *
 * @param <T>het specifieke type converteerder
 */
public abstract class BrpIstAbstractConverteerder<T extends AbstractBrpIstGroepInhoud> {
    /**
     * Comparator om Lo3 categorieen op herkomst te sorteren.
     */
    protected static final Comparator<Lo3Categorie<?>> LO3_HERKOMST_COMPARATOR = new Lo3HerkomstComparator();
    private static final Map<Lo3CategorieEnum, Class<? extends Lo3CategorieInhoud>> IST_CATEGORIEEN;

    static {
        IST_CATEGORIEEN = new HashMap<>();
        IST_CATEGORIEEN.put(Lo3CategorieEnum.CATEGORIE_02, Lo3OuderInhoud.class);
        IST_CATEGORIEEN.put(Lo3CategorieEnum.CATEGORIE_03, Lo3OuderInhoud.class);
        IST_CATEGORIEEN.put(Lo3CategorieEnum.CATEGORIE_05, Lo3HuwelijkOfGpInhoud.class);
        IST_CATEGORIEEN.put(Lo3CategorieEnum.CATEGORIE_09, Lo3KindInhoud.class);
        IST_CATEGORIEEN.put(Lo3CategorieEnum.CATEGORIE_11, Lo3GezagsverhoudingInhoud.class);
    }

    @Inject
    private BrpAttribuutConverteerder converteerder;

    /**
     * Converteert een BRP-Ist stapel naar een LO3 stapel.
     *
     * @param brpStapel
     *            de IST-stapel die geconverteerd moet worden
     * @return een Lo3 stapel met daarin de gegevens uit de IST-stapel
     */
    public abstract Lo3Stapel<? extends Lo3CategorieInhoud> converteer(final BrpStapel<T> brpStapel);

    /**
     * Vult de Lo3 historie.
     *
     * @param inhoud
     *            inhoud waaruit de Lo3 historie wordt gevuld
     * @return Lo3 historie
     */
    final Lo3Historie maakHistorie(final BrpIstStandaardGroepInhoud inhoud) {
        final BrpCharacter onjuist = inhoud.getRubriek8410OnjuistOfStrijdigOpenbareOrde();
        Lo3IndicatieOnjuist indicatieOnjuist = null;
        if (onjuist != null) {
            final Character onjuistWaarde = onjuist.getWaarde();
            indicatieOnjuist = new Lo3IndicatieOnjuist(onjuistWaarde == null ? null : "" + onjuistWaarde, onjuist.getOnderzoek());
        }
        final Lo3Datum ingangsdatumGeldigheid = getConverteerder().converteerDatum(inhoud.getRubriek8510IngangsdatumGeldigheid());
        final Lo3Datum datumVanOpneming = getConverteerder().converteerDatum(inhoud.getRubriek8610DatumVanOpneming());

        return Lo3Historie.build(indicatieOnjuist, ingangsdatumGeldigheid, datumVanOpneming);
    }

    /**
     * Vult de Lo3 documentatie met of akte of document gegevens of null als er geen documentatie is.
     *
     * @param inhoud
     *            inhoud waaruit de Lo3 documentatie wordt gevuld
     * @return Lo3 documentatie
     */
    final Lo3Documentatie maakDocumentatie(final BrpIstStandaardGroepInhoud inhoud) {
        Lo3Documentatie documentatie = null;
        final Lo3GemeenteCode gemeenteCode = getConverteerder().converteerGemeenteCode(inhoud.getPartij());
        if (inhoud.getAktenummer() != null) {
            // Akte
            documentatie = new Lo3Documentatie(0, gemeenteCode, getConverteerder().converteerString(inhoud.getAktenummer()), null, null, null, null, null);
        } else if (inhoud.getDocumentOmschrijving() != null) {
            // Document
            final Lo3Datum lo3Datum = getConverteerder().converteerDatum(inhoud.getRubriek8220DatumDocument());
            documentatie =
                    new Lo3Documentatie(
                        0,
                        null,
                        null,
                        gemeenteCode,
                        lo3Datum,
                        getConverteerder().converteerString(inhoud.getDocumentOmschrijving()),
                        null,
                        null);
        }

        return documentatie;
    }

    /**
     * Vult de Lo3 onderzoek.
     *
     * @param inhoud
     *            inhoud waaruit de Lo3 onderzoek wordt gevuld
     * @return Lo3 onderzoek
     */
    final Lo3Onderzoek maakOnderzoek(final BrpIstStandaardGroepInhoud inhoud) {
        final Integer gegevensInOnderzoek = BrpInteger.unwrap(inhoud.getRubriek8310AanduidingGegevensInOnderzoek());
        final String gegevensInOnderzoekString = gegevensInOnderzoek == null ? null : String.format("%06d", gegevensInOnderzoek);
        final Integer datumIngangOnderzoek = BrpInteger.unwrap(inhoud.getRubriek8320DatumIngangOnderzoek());
        final Integer datumEindeOnderzoek = BrpInteger.unwrap(inhoud.getRubriek8330DatumEindeOnderzoek());
        return Lo3Onderzoek.build(
            new Lo3Integer(gegevensInOnderzoekString, null),
            datumIngangOnderzoek == null ? null : new Lo3Datum(datumIngangOnderzoek),
            datumEindeOnderzoek == null ? null : new Lo3Datum(datumEindeOnderzoek),
            maakHerkomst(inhoud));
    }

    /**
     * Vult de Lo3 herkomst. Lo3Herkomst is geen onderdeel van het Lo3 Conversie model, maar is tracing. Wordt nu alleen
     * voor IST onderdeel gemaakt van het Lo3-model. Dit blijft goed gaan totdat BRP gaat bijhouden.
     *
     * @param inhoud
     *            inhoud waaruit de Lo3 herkomst wordt gevuld
     * @return Lo3 herkomst
     */
    final Lo3Herkomst maakHerkomst(final BrpIstStandaardGroepInhoud inhoud) {
        return new Lo3Herkomst(inhoud.getCategorie(), inhoud.getStapel(), inhoud.getVoorkomen());
    }

    /**
     * Converteert de BRP land en plaats naar Lo3 waarden.
     *
     * @param gemeenteCode
     *            BRP gemeentecode
     * @param buitenlandsePlaats
     *            buitenlandse plaats
     * @param landCode
     *            BRP landcode
     * @param omschrijvingLocatie
     *            omschrijving locaie
     * @return Lo3 gemeente en land code in lo3
     */
    final Lo3GemeenteLand converteerPlaatsLand(
        final BrpGemeenteCode gemeenteCode,
        final BrpString buitenlandsePlaats,
        final BrpLandOfGebiedCode landCode,
        final BrpString omschrijvingLocatie)
    {
        return getConverteerder().converteerLocatie(gemeenteCode, buitenlandsePlaats, null, landCode, omschrijvingLocatie);
    }

    /**
     * Converteert de geboorte land/gebied en plaats naar lo3 waarden.
     *
     * @param inhoud
     *            brp ist inhoud
     * @return gemeente en land code in lo3
     */
    final Lo3GemeenteLand converteerGeboortePlaatsLand(final BrpIstRelatieGroepInhoud inhoud) {
        return converteerPlaatsLand(
            inhoud.getGemeenteCodeGeboorte(),
            inhoud.getBuitenlandsePlaatsGeboorte(),
            inhoud.getLandOfGebiedCodeGeboorte(),
            inhoud.getOmschrijvingLocatieGeboorte());
    }

    /**
     * Geeft de brp attribuut converteerder terug.
     *
     * @return de {@link BrpAttribuutConverteerder}
     */
    public final BrpAttribuutConverteerder getConverteerder() {
        return converteerder;
    }

    /**
     * Geeft een kopie van element terug waar aan onderzoek is toegevoegd. Onderzoek wordt alleen toegevoegd als het
     * element in onderzoek is.
     *
     * Als onderzoek null is, dan wordt het onderzoek dat aan het element is gekoppeld, toegevoegd. Als element null is,
     * dan wordt er via het elementnummer het juiste type element gezocht en deze terug gegeven met onderzoek.
     *
     * @param element
     *            LO3 element waar aan het onderzoek moet worden toegevoegd.
     * @param onderzoek
     *            LO3 onderzoek dat aan het element gekoppeld wordt.
     * @param herkomst
     *            LO3 herkomst om te bepalen of het onderzoek aan het element gekoppeld moet worden.
     * @param elementNummer
     *            het element nummer waaraan onderzoek moet worden toegevoegd.
     * @param <V>
     *            type van het LO3 element.
     * @return kopie van element met onderzoek
     */
    protected static <V extends Lo3Element> V toevoegenOnderzoek(
        final V element,
        final Lo3Onderzoek onderzoek,
        final Lo3Herkomst herkomst,
        final Lo3ElementEnum elementNummer)
    {
        final Lo3CategorieEnum actueleCategorie = Lo3CategorieEnum.bepaalActueleCategorie(herkomst.getCategorie());
        return toevoegenOnderzoekAanElement(element, onderzoek, herkomst, elementNummer, IST_CATEGORIEEN.get(actueleCategorie));
    }

    private static <V extends Lo3Element> V toevoegenOnderzoekAanElement(
        final V element,
        final Lo3Onderzoek onderzoek,
        final Lo3Herkomst herkomst,
        final Lo3ElementEnum elementNummer,
        final Class<?> lo3Class)
    {

        final Class<? extends Lo3Element> classType;
        final String waarde;
        final Lo3Onderzoek toegevoegdOnderzoek;

        if (element == null) {
            classType = bepaalLo3Element(lo3Class, elementNummer);
            waarde = null;
        } else {
            classType = element.getClass();
            waarde = element.getWaarde();
        }

        if (onderzoek != null) {
            toegevoegdOnderzoek = onderzoek.omvatElementInCategorie(elementNummer, herkomst.getCategorie()) ? onderzoek : null;
        } else {
            toegevoegdOnderzoek = element == null ? null : element.getOnderzoek();
        }

        V result = null;
        if (waarde != null || toegevoegdOnderzoek != null) {
            try {

                final Class[] constructoArguments = new Class[] {String.class, Lo3Onderzoek.class, };
                final Constructor<? extends Lo3Element> constructor = classType.getConstructor(constructoArguments);

                // noinspection unchecked
                result = (V) constructor.newInstance(waarde, toegevoegdOnderzoek);
            } catch (final
                InstantiationException
                | IllegalAccessException
                | InvocationTargetException
                | NoSuchMethodException e)
            {
                LoggerFactory.getLogger().error("Kan onderzoek niet toevoegen", e);
                throw new IllegalStateException("Kan onderzoek niet toevoegen bij element", e);
            }
        }
        return result;
    }

    /**
     * Geeft een kopie van historie terug waar onderzoek aan zijn elementen is toegevoegd. Onderzoek wordt alleen
     * toegevoegd als het element in onderzoek is.
     *
     * Als onderzoek null is, dan wordt het onderzoek dat aan het element is gekoppeld, toegevoegd. Als element null is,
     * dan wordt er via het elementnummer het juiste type element gezocht en deze terug gegeven met onderzoek.
     *
     * @param historie
     *            LO3 Historie waar het onderzoek aan zijn elementen moet worden toegevoegd.
     * @param onderzoek
     *            LO3 onderzoek dat aan het element gekoppeld wordt.
     * @param herkomst
     *            LO3 herkomst om te bepalen of het onderzoek aan het element gekoppeld moet worden.
     * @return kopie van historie met onderzoek
     */
    protected static Lo3Historie toevoegenOnderzoekHistorie(final Lo3Historie historie, final Lo3Onderzoek onderzoek, final Lo3Herkomst herkomst) {
        final Lo3IndicatieOnjuist onjuist =
                toevoegenOnderzoekAanElement(historie.getIndicatieOnjuist(), onderzoek, herkomst, Lo3ElementEnum.ELEMENT_8410, Lo3Historie.class);
        final Lo3Datum ingangsDatum =
                toevoegenOnderzoekAanElement(historie.getIngangsdatumGeldigheid(), onderzoek, herkomst, Lo3ElementEnum.ELEMENT_8510, Lo3Historie.class);
        final Lo3Datum opnemingsDatum =
                toevoegenOnderzoekAanElement(historie.getDatumVanOpneming(), onderzoek, herkomst, Lo3ElementEnum.ELEMENT_8610, Lo3Historie.class);

        return new Lo3Historie(onjuist, ingangsDatum, opnemingsDatum);
    }

    /**
     * Geeft een kopie van documentatie terug waar onderzoek aan zijn elementen is toegevoegd. Onderzoek wordt alleen
     * toegevoegd als het element in onderzoek is.
     *
     * Als onderzoek null is, dan wordt het onderzoek dat aan het element is gekoppeld, toegevoegd. Als element null is,
     * dan wordt er via het elementnummer het juiste type element gezocht en deze terug gegeven met onderzoek.
     *
     * @param documentatie
     *            LO3 Documentatie waar het onderzoek aan zijn elementen moet worden toegevoegd.
     * @param onderzoek
     *            LO3 onderzoek dat aan het element gekoppeld wordt.
     * @param herkomst
     *            LO3 herkomst om te bepalen of het onderzoek aan het element gekoppeld moet worden.
     * @return kopie van documentatie met onderzoek
     */
    protected static Lo3Documentatie toevoegenOnderzoekDocumentatie(
        final Lo3Documentatie documentatie,
        final Lo3Onderzoek onderzoek,
        final Lo3Herkomst herkomst)
    {
        // Akte
        final Lo3GemeenteCode gemeenteAkte =
                toevoegenOnderzoekAanElement(documentatie.getGemeenteAkte(), onderzoek, herkomst, Lo3ElementEnum.ELEMENT_8110, Lo3Documentatie.class);
        final Lo3String nummerAkte =
                toevoegenOnderzoekAanElement(documentatie.getNummerAkte(), onderzoek, herkomst, Lo3ElementEnum.ELEMENT_8120, Lo3Documentatie.class);
        // Documentatie
        final Lo3GemeenteCode gemeenteDocument =
                toevoegenOnderzoekAanElement(documentatie.getGemeenteDocument(), onderzoek, herkomst, Lo3ElementEnum.ELEMENT_8210, Lo3Documentatie.class);
        final Lo3Datum datumDocument =
                toevoegenOnderzoekAanElement(documentatie.getDatumDocument(), onderzoek, herkomst, Lo3ElementEnum.ELEMENT_8220, Lo3Documentatie.class);
        final Lo3String beschrijvingDocument =
                toevoegenOnderzoekAanElement(
                    documentatie.getBeschrijvingDocument(),
                    onderzoek,
                    herkomst,
                    Lo3ElementEnum.ELEMENT_8230,
                    Lo3Documentatie.class);
        // RNI
        final Lo3RNIDeelnemerCode rniDeelnemer =
                toevoegenOnderzoekAanElement(documentatie.getRniDeelnemerCode(), onderzoek, herkomst, Lo3ElementEnum.ELEMENT_8810, Lo3Documentatie.class);
        final Lo3String omschrijvingVerdrag =
                toevoegenOnderzoekAanElement(
                    documentatie.getOmschrijvingVerdrag(),
                    onderzoek,
                    herkomst,
                    Lo3ElementEnum.ELEMENT_8820,
                    Lo3Documentatie.class);

        return new Lo3Documentatie(
            documentatie.getId(),
            gemeenteAkte,
            nummerAkte,
            gemeenteDocument,
            datumDocument,
            beschrijvingDocument,
            rniDeelnemer,
            omschrijvingVerdrag);
    }

    private static <E extends Lo3Element> Class<E> bepaalLo3Element(final Class<?> inhoudClass, final Lo3ElementEnum elementNummer) {
        final Field[] fields = inhoudClass.getDeclaredFields();
        for (final Field field : fields) {
            final Lo3Elementnummer elementAnnotation = field.getAnnotation(Lo3Elementnummer.class);
            if (elementAnnotation != null && elementAnnotation.value().equals(elementNummer)) {
                field.setAccessible(true);
                final Class<E> elementType;
                try {
                    // noinspection unchecked
                    elementType = (Class<E>) Class.forName(field.getType().getName());
                } catch (final ClassNotFoundException e) {
                    final String mesg = "Kan LO3 element niet bepalen";
                    LoggerFactory.getLogger().error(mesg, e);
                    throw new IllegalStateException(mesg, e);
                }
                return elementType;
            }
        }
        return null;
    }

    /**
     * Comparator om Lo3 categorieen op herkomst te sorteren.
     */
    protected static final class Lo3HerkomstComparator implements Comparator<Lo3Categorie<?>>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(final Lo3Categorie<?> o1, final Lo3Categorie<?> o2) {
            final Lo3Herkomst herkomst1 = o1.getLo3Herkomst();
            final Lo3Herkomst herkomst2 = o2.getLo3Herkomst();

            int result = 0;

            if (herkomst1 != null && herkomst2 != null) {
                result = herkomst1.getStapel() - herkomst2.getStapel();

                if (result == 0) {
                    result = herkomst2.getVoorkomen() - herkomst1.getVoorkomen();
                }
            }

            return result;
        }
    }
}

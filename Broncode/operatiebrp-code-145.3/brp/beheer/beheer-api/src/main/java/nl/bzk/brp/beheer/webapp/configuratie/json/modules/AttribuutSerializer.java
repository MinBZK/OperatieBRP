/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import com.fasterxml.jackson.core.JsonGenerator;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EnumMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AanduidingInhoudingOfVermissingReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Aangever;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Nationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenBeeindigingRelatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenVerkrijgingNLNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenVerliesNLNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenWijzigingVerblijf;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortNederlandsReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Verblijfsrecht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.AdellijkeTitel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Bijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.ElementBasisType;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Enumeratie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Geslachtsaanduiding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Naamgebruik;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Predicaat;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMigratie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.brp.beheer.webapp.configuratie.RepositoryConfiguratie;
import org.springframework.stereotype.Component;

/**
 * Attribuut serializer.
 */
@Component
public final class AttribuutSerializer {

    private static final String SCHEIDINGSTEKEN_DATUM = "-";
    private static final int EIND_POSITIE_JAAR = 4;
    private static final int START_POSITIE_JAAR = 0;
    private static final int START_POSITIE_MAAND = 4;
    private static final int EIND_POSITIE_MAAND = 6;
    private static final int START_POSITIE_DAG = 6;
    private static final int EIND_POSITIE_DAG = 8;
    private static final String SLUIT_HAAK = ")";
    private static final String SPATIE_OPEN_HAAK = " (";
    private static final String CODE = "code";
    private static final String SLASH = "/";

    private static final ThreadLocal<SimpleDateFormat> DATE_DATUM_FORMAT =
            ThreadLocal.withInitial(() -> new SimpleDateFormat(AdministratieveHandelingModule.DATUM_TIJD_FORMAAT));

    private static final ThreadLocal<DecimalFormat> INT_DATE_FORMAT = ThreadLocal.withInitial(() -> new DecimalFormat("00000000"));

    private final Map<Element, AttribuutGetter> writers = new EnumMap<>(Element.class);

    @PersistenceContext(unitName = RepositoryConfiguratie.PERSISTENCE_UNIT_MASTER)
    private EntityManager entityManager;

    /**
     * Constructor.
     */
    public AttribuutSerializer() {
        writers.put(Element.AANDUIDINGINHOUDINGVERMISSINGREISDOCUMENT, this::getAanduidingInhoudingVermissingReisdocument);
        writers.put(Element.AANDUIDINGVERBLIJFSRECHT, this::getAanduidingVerblijfsrecht);
        writers.put(Element.AANGEVER, this::getAangever);
        writers.put(Element.ADELLIJKETITEL, this::getAdellijkTitel);
        writers.put(Element.ADMINISTRATIEVEHANDELING, this::getAdministratieveHandeling);
        writers.put(Element.AUTORITEITVANAFGIFTEBUITENLANDSPERSOONSNUMMER, this::getAutoriteitAfgifteBuitenlandsPersoonnummer);
        writers.put(Element.BIJHOUDINGSAARD, this::getBijhoudingsaard);
        writers.put(Element.ELEMENT, this::getElement);
        writers.put(Element.SOORTADRES, this::getSoortAdres);
        writers.put(Element.GEMEENTE, this::getGemeente);
        writers.put(Element.GESLACHTSAANDUIDING, this::getGeslachtsaanduiding);
        writers.put(Element.LANDGEBIED, this::getLandGebied);
        writers.put(Element.NAAMGEBRUIK, this::getNaamgebruik);
        writers.put(Element.NADEREBIJHOUDINGSAARD, this::getNadereBijhoudingsaard);
        writers.put(Element.NATIONALITEIT, this::getNationaliteit);
        writers.put(Element.PARTIJ, this::getPartij);
        writers.put(Element.PERSOON, this::getPersoon);
        writers.put(Element.PREDICAAT, this::getPredicaat);
        writers.put(Element.REDENEINDERELATIE, this::getRedenEindeRelatie);
        writers.put(Element.REDENVERKRIJGINGNLNATIONALITEIT, this::getRedenVerkrijgingNlNationaliteit);
        writers.put(Element.REDENVERLIESNLNATIONALITEIT, this::getRedenVerliesNlNationaliteit);
        writers.put(Element.REDENWIJZIGINGVERBLIJF, this::getRedenWijzigingVerblijf);
        writers.put(Element.SOORTACTIE, this::getSoortActie);
        writers.put(Element.SOORTADMINISTRATIEVEHANDELING, this::getSoortAdministratieveHandeling);
        writers.put(Element.SOORTBETROKKENHEID, this::getSoortBetrokkenheid);
        writers.put(Element.SOORTINDICATIE, this::getSoortIndicatie);
        writers.put(Element.SOORTMIGRATIE, this::getSoortMigratie);
        writers.put(Element.SOORTNEDERLANDSREISDOCUMENT, this::getSoortNederlandsReisdocument);
        writers.put(Element.SOORTPERSOON, this::getSoortPersoon);
        writers.put(Element.SOORTRELATIE, this::getSoortRelatie);
        writers.put(Element.STATUSONDERZOEK, this::getStatusOnderzoek);
        writers.put(Element.ROL, this::getSoortRelatie);
    }

    /**
     * Formateer datum.
     * @param datum datum
     * @return formatted string
     */
    public static String formatDatum(final Integer datum) {
        if (datum == null) {
            return null;
        } else {
            final String deelresultaat = INT_DATE_FORMAT.get().format(datum);
            return deelresultaat.subSequence(START_POSITIE_JAAR, EIND_POSITIE_JAAR)
                    + SCHEIDINGSTEKEN_DATUM
                    + deelresultaat.subSequence(START_POSITIE_MAAND, EIND_POSITIE_MAAND)
                    + SCHEIDINGSTEKEN_DATUM
                    + deelresultaat.subSequence(START_POSITIE_DAG, EIND_POSITIE_DAG);
        }
    }

    /**
     * Formateer datum/tijd.
     * @param tijdstip tijdstip
     * @return formatted string
     */
    public static String formatDatum(final Date tijdstip) {
        if (tijdstip == null) {
            return null;
        } else {
            return DATE_DATUM_FORMAT.get().format(tijdstip);
        }
    }

    /**
     * Schrijf attribuut.
     * @param element element
     * @param value waarde
     * @param jgen json generator
     * @throws IOException bij fouten
     */
    public void writeAttribuut(final Element element, final Object value, final JsonGenerator jgen) throws IOException {
        if (value == null) {
            return;
        }

        final String result;
        if (element.getType() != null) {
            result = verwerkElementMetType(element, value);
        } else {
            result = verwerkTypeloosElement(element, value);
        }
        jgen.writeStringField(element.getElementNaam(), result);

    }

    private String verwerkElementMetType(final Element element, final Object value) {
        final String result;
        final AttribuutGetter attribuutGetter = writers.get(element.getType());
        if (attribuutGetter == null) {
            throw new IllegalArgumentException("Onbekend type: " + element + "; type = " + element.getType());
        }

        result = attribuutGetter.getVeldWaarde(value);
        return result;
    }

    private String verwerkTypeloosElement(final Element element, final Object value) {
        final String result;
        if (value instanceof Boolean) {
            result = (Boolean) value ? "Ja" : "Nee";
        } else if (value instanceof Number && ElementBasisType.DATUM == element.getBasisType()) {
            result = formatDatum(((Number) value).intValue());
        } else if (value instanceof Date) {
            result = formatDatum((Date) value);
        } else {
            result = value.toString();
        }
        return result;
    }

    private String getAanduidingInhoudingVermissingReisdocument(final Object value) {
        final AanduidingInhoudingOfVermissingReisdocument entiteit =
                entityManager.createQuery(
                        "from AanduidingInhoudingOfVermissingReisdocument where code = :code",
                        AanduidingInhoudingOfVermissingReisdocument.class).setParameter(CODE, ((String) value).charAt(0)).getSingleResult();
        return entiteit.getCode() + SPATIE_OPEN_HAAK + entiteit.getNaam() + SLUIT_HAAK;
    }

    private String getAanduidingVerblijfsrecht(final Object value) {
        final Verblijfsrecht entiteit =
                entityManager.createQuery("from Verblijfsrecht where code = :code", Verblijfsrecht.class)
                        .setParameter(CODE, value.toString())
                        .getSingleResult();
        return entiteit.getCode() + SPATIE_OPEN_HAAK + entiteit.getOmschrijving() + SLUIT_HAAK;
    }

    private String getAangever(final Object value) {
        final Aangever entiteit =
                entityManager.createQuery("from Aangever where code = :code", Aangever.class)
                        .setParameter(CODE, ((String) value).charAt(0))
                        .getSingleResult();
        return entiteit.getCode() + SPATIE_OPEN_HAAK + entiteit.getNaam() + SLUIT_HAAK;
    }

    private String getAdellijkTitel(final Object value) {
        final AdellijkeTitel adellijkeTitel = AdellijkeTitel.parseCode((String) value);
        return adellijkeTitel.getCode() + SPATIE_OPEN_HAAK + adellijkeTitel.getNaamMannelijk() + SLASH + adellijkeTitel.getNaamVrouwelijk() + SLUIT_HAAK;
    }

    private String getAdministratieveHandeling(final Object value) {
        return value.toString();
    }

    private String getAutoriteitAfgifteBuitenlandsPersoonnummer(final Object value) {
        return value.toString();
    }

    private String getBijhoudingsaard(final Object value) {
        return getElementCodeNaam(Bijhoudingsaard.parseCode((String) value));
    }

    private String getSoortAdres(final Object value) {
        return getElementCodeNaam(SoortAdres.parseCode((String) value));
    }

    private String getGemeente(final Object value) {
        final Gemeente entiteit =
                entityManager.createQuery("from Gemeente where code = :code", Gemeente.class)
                        .setParameter(CODE, value.toString())
                        .getSingleResult();
        return entiteit.getCode() + SPATIE_OPEN_HAAK + entiteit.getNaam() + SLUIT_HAAK;
    }

    private String getGeslachtsaanduiding(final Object value) {
        return getElementCodeNaam(Geslachtsaanduiding.parseCode((String) value));
    }

    private String getLandGebied(final Object value) {
        final LandOfGebied entiteit =
                entityManager.createQuery("from LandOfGebied where code = :code", LandOfGebied.class)
                        .setParameter(CODE, value.toString())
                        .getSingleResult();
        return entiteit.getCode() + SPATIE_OPEN_HAAK + entiteit.getNaam() + SLUIT_HAAK;
    }

    private String getNaamgebruik(final Object value) {
        return getElementCodeNaam(Naamgebruik.parseCode((String) value));
    }

    private String getNadereBijhoudingsaard(final Object value) {
        return getElementCodeNaam(NadereBijhoudingsaard.parseCode((String) value));
    }

    private String getElement(final Object value) {
        return value.toString();
    }

    private String getNationaliteit(final Object value) {
        final Nationaliteit entiteit =
                entityManager.createQuery("from Nationaliteit where code = :code", Nationaliteit.class)
                        .setParameter(CODE, value.toString())
                        .getSingleResult();
        return entiteit.getCode() + SPATIE_OPEN_HAAK + entiteit.getNaam() + SLUIT_HAAK;
    }

    private String getPartij(final Object value) {
        final Partij entiteit =
                entityManager.createQuery("from Partij where code = :code", Partij.class)
                        .setParameter(CODE, value.toString())
                        .getSingleResult();
        return entiteit.getCode() + SPATIE_OPEN_HAAK + entiteit.getNaam() + SLUIT_HAAK;
    }

    //

    /**
     * methode krijgt default een object mee
     */
    private String getPersoon(final Object value) {
        return null;
    }

    private String getPredicaat(final Object value) {
        final Predicaat predicaat = Predicaat.parseCode((String) value);
        return predicaat.getCode() + SPATIE_OPEN_HAAK + predicaat.getNaamMannelijk() + SLASH + predicaat.getNaamVrouwelijk() + SLUIT_HAAK;
    }

    private String getRedenEindeRelatie(final Object value) {
        final RedenBeeindigingRelatie entiteit =
                entityManager.createQuery("from RedenBeeindigingRelatie where code = :code", RedenBeeindigingRelatie.class)
                        .setParameter(CODE, ((String) value).charAt(0))
                        .getSingleResult();
        return entiteit.getCode() + SPATIE_OPEN_HAAK + entiteit.getOmschrijving() + SLUIT_HAAK;
    }

    private String getRedenVerkrijgingNlNationaliteit(final Object value) {
        final RedenVerkrijgingNLNationaliteit entiteit =
                entityManager.createQuery("from RedenVerkrijgingNLNationaliteit where code = :code", RedenVerkrijgingNLNationaliteit.class)
                        .setParameter(CODE, value.toString())
                        .getSingleResult();
        return entiteit.getCode() + SPATIE_OPEN_HAAK + entiteit.getOmschrijving() + SLUIT_HAAK;
    }

    private String getRedenVerliesNlNationaliteit(final Object value) {
        final RedenVerliesNLNationaliteit entiteit =
                entityManager.createQuery("from RedenVerliesNLNationaliteit where code = :code", RedenVerliesNLNationaliteit.class)
                        .setParameter(CODE, value.toString())
                        .getSingleResult();
        return entiteit.getCode() + SPATIE_OPEN_HAAK + entiteit.getOmschrijving() + SLUIT_HAAK;
    }

    private String getRedenWijzigingVerblijf(final Object value) {
        final RedenWijzigingVerblijf entiteit =
                entityManager.createQuery("from RedenWijzigingVerblijf where code = :code", RedenWijzigingVerblijf.class)
                        .setParameter(CODE, ((String) value).charAt(0))
                        .getSingleResult();
        return entiteit.getCode() + SPATIE_OPEN_HAAK + entiteit.getNaam() + SLUIT_HAAK;
    }

    private String getSoortActie(final Object value) {
        return SoortActie.parseId(((Number) value).intValue()).getNaam();
    }

    private String getSoortAdministratieveHandeling(final Object value) {
        return getElementIdNaam(SoortAdministratieveHandeling.parseId((Integer) value));
    }

    private String getSoortBetrokkenheid(final Object value) {
        return getElementCodeNaam(SoortBetrokkenheid.parseCode((String) value));
    }

    private String getSoortIndicatie(final Object value) {
        return SoortIndicatie.parseId(((Number) value).intValue()).getOmschrijving();
    }

    private String getSoortMigratie(final Object value) {
        return getElementCodeNaam(SoortMigratie.parseCode((String) value));
    }

    private String getSoortNederlandsReisdocument(final Object value) {
        final SoortNederlandsReisdocument entiteit =
                entityManager.createQuery("from SoortNederlandsReisdocument where code = :code", SoortNederlandsReisdocument.class)
                        .setParameter(CODE, value)
                        .getSingleResult();
        return entiteit.getCode() + SPATIE_OPEN_HAAK + entiteit.getOmschrijving() + SLUIT_HAAK;
    }

    private String getSoortPersoon(final Object value) {
        return getElementCodeNaam(SoortPersoon.parseCode((String) value));
    }

    private String getSoortRelatie(final Object value) {
        return getElementCodeNaam(SoortRelatie.parseCode((String) value));
    }

    private String getStatusOnderzoek(final Object value) {
        return (String) value;
    }

    private String getElementCodeNaam(final Enumeratie enumeratie) {
        return enumeratie.getCode() + SPATIE_OPEN_HAAK + enumeratie.getNaam() + SLUIT_HAAK;
    }

    private String getElementIdNaam(final Enumeratie enumeratie) {
        return enumeratie.getId() + SPATIE_OPEN_HAAK + enumeratie.getNaam() + SLUIT_HAAK;
    }

    /**
     * Attribuut getter.
     */
    @FunctionalInterface
    private interface AttribuutGetter {
        /**
         * Geef de string representatie voor de gegeven waarde.
         * @param attribuut attribuut waarde
         * @return string representatie
         */
        String getVeldWaarde(Object attribuut);
    }

}

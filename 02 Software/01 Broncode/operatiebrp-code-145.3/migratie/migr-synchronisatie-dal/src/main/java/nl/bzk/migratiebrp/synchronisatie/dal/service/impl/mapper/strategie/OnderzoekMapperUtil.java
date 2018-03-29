/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Entiteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.GegevenInOnderzoek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.MaterieleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.AnyMetaDef;
import org.hibernate.annotations.MetaValue;

/**
 * Ondersteuning voor het mappen van onderzoek gegevens.
 */
public final class OnderzoekMapperUtil {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final Map<EntiteitDbObjectSleutel, Element> ENTITEIT_DBOBJECT_MAP = vulEntiteitDbobjectMap();
    private static final String STANDAARD = "_STANDAARD";
    private static final String IDENTITEIT = "_IDENTITEIT";

    private OnderzoekMapperUtil() {
        throw new AssertionError("Er mag geen instantie van een OnderzoekMapperUtil class gemaakt worden.");
    }

    /**
     * Bepaalt welke {@link Element} hoort bij een {@link Historieveldnaam} voor een willekeurige entiteit. Deze methode
     * zal alleen die {@link Element} objecten terug geven die tot de 'eigen' persoon hoort.
     * @param entiteit De entiteit waar het historie veld in zit.
     * @param historieveldnaam De historie veldnaam.
     * @return het bijbehorende {@link Element}
     */
    public static Element bepaalElement(final Object entiteit, final Historieveldnaam historieveldnaam) {
        return bepaalElement(entiteit, historieveldnaam, null);
    }

    /**
     * Bepaalt welke {@link Element} hoort bij een {@link Historieveldnaam} in een willekeurige entiteit. Het objecttype
     * maakt het mogelijk om onderscheid te kunnen maken tussen de 'eigen' persoon en de gerelateerde personen.
     * @param entiteit De entiteit waar het historie veld in zit.
     * @param veldNaam De historie veldnaam.
     * @param objecttype het objecttype voor de entiteit, mag null zijn.
     * @return het bijbehorende {@link Element}.
     */
    public static Element bepaalElement(final Object entiteit, final Historieveldnaam veldNaam, final Element objecttype) {
        final Class entiteitClass = Entiteit.bepaalEntiteitClass(entiteit.getClass());
        final EntiteitDbObjectSleutel classNaamPaar = new EntiteitDbObjectSleutel(entiteitClass, veldNaam, objecttype);
        return ENTITEIT_DBOBJECT_MAP.get(classNaamPaar);
    }

    private static Map<EntiteitDbObjectSleutel, Element> vulEntiteitDbobjectMap() {
        final Map<EntiteitDbObjectSleutel, Element> entiteitDbobjectMap = new HashMap<>();
        try {
            final AnyMetaDef metadef = GegevenInOnderzoek.class.getDeclaredField("voorkomen").getAnnotation(AnyMetaDef.class);

            for (final MetaValue metaValue : metadef.metaValues()) {
                final Class tableClass = metaValue.targetEntity();
                final Historieveldnaam[] historieVeldnamen = Historieveldnaam.getVeldnamen(tableClass);

                final Element element = Element.parseId(Integer.parseInt(metaValue.value()));
                final String elementNaam = bepaalGenormaliseerdeElementNaam(element);

                final Element objectType = bepaalObjecttype(elementNaam);

                for (final Historieveldnaam historieVeldnaam : historieVeldnamen) {
                    final String attribuutNaam = elementNaam + historieVeldnaam.getNaam();
                    final Element attribuut = Element.valueOf(attribuutNaam);

                    entiteitDbobjectMap.put(new EntiteitDbObjectSleutel(tableClass, historieVeldnaam, objectType), attribuut);
                }
            }
        } catch (final
        NoSuchFieldException
                | NumberFormatException e) {
            LOG.info("Fout bij vullen Entiteit-DbObject map.", e);
        }

        return entiteitDbobjectMap;
    }

    private static Element bepaalObjecttype(final String elementNaam) {
        final Element gerelateerdeKindPersoon = Element.GERELATEERDEKIND_PERSOON;
        final Element gerelateerdeHuwelijksPartnerPersoon = Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON;
        final Element gerelateerdeGeregistreerdePartnerPersoon = Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON;
        final Element gerelateerdeOuderPersoon = Element.GERELATEERDEOUDER_PERSOON;
        final String gerelateerdeOuder = gerelateerdeOuderPersoon.toString().substring(0, gerelateerdeOuderPersoon.toString().indexOf('_'));

        final Element result;
        if (elementNaam.startsWith(gerelateerdeKindPersoon.toString())) {
            result = gerelateerdeKindPersoon;
        } else if (elementNaam.startsWith(gerelateerdeOuder)) {
            result = gerelateerdeOuderPersoon;
        } else if (elementNaam.startsWith(gerelateerdeHuwelijksPartnerPersoon.toString())) {
            result = gerelateerdeHuwelijksPartnerPersoon;
        } else if (elementNaam.startsWith(gerelateerdeGeregistreerdePartnerPersoon.toString())) {
            result = gerelateerdeGeregistreerdePartnerPersoon;
        } else {
            result = null;
        }

        return result;
    }

    private static String bepaalGenormaliseerdeElementNaam(final Element element) {
        final String elementNaam = element.toString();
        final String elementNaamGenormaliseerd;
        if (elementNaam.endsWith(STANDAARD)) {
            elementNaamGenormaliseerd = elementNaam.substring(0, elementNaam.length() - STANDAARD.length());
        } else if (elementNaam.endsWith(IDENTITEIT)) {
            elementNaamGenormaliseerd = elementNaam.substring(0, elementNaam.length() - IDENTITEIT.length());
        } else {
            elementNaamGenormaliseerd = elementNaam;
        }
        return elementNaamGenormaliseerd;
    }

    /**
     * Interne enum voor de historie veldnamen van entiteiten.
     */
    public enum Historieveldnaam {
        /** */
        REGISTRATIE("_TIJDSTIPREGISTRATIE"),
        /** */
        VERVAL("_TIJDSTIPVERVAL"),
        /** */
        AANVANG("_DATUMAANVANGGELDIGHEID"),
        /** */
        EINDE("_DATUMEINDEGELDIGHEID"),
        /** */
        N_A_VERVAL("_NADEREAANDUIDINGVERVAL");

        private final String naam;

        /**
         * Constructor om de enumeratie te maken.
         * @param naam naam van het historie veld
         */
        Historieveldnaam(final String naam) {
            this.naam = naam;
        }

        /**
         * Geef de waarde van naam.
         * @return naam
         */
        public String getNaam() {
            return naam;
        }

        /**
         * Geeft de veldnamen terug voor de relevante input.
         * @param tableClass de tabel waarvoor de veldnamen terug gegeven moet worden
         * @return de veldnamen
         */
        public static Historieveldnaam[] getVeldnamen(final Class<?> tableClass) {
            final Historieveldnaam[] result;
            if (MaterieleHistorie.class.isAssignableFrom(tableClass)) {
                result = values();
            } else if (FormeleHistorie.class.isAssignableFrom(tableClass)) {
                result = new Historieveldnaam[]{REGISTRATIE, VERVAL, N_A_VERVAL};
            } else {
                result = new Historieveldnaam[]{REGISTRATIE, VERVAL};
            }
            return result;
        }
    }

    /**
     * Sleutel class voor DB Object/onderzoek mapping.
     */
    private static class EntiteitDbObjectSleutel {

        private Class entiteit;
        private Historieveldnaam historieveldnaam;
        private Element objecttype;

        /**
         * Maakt een sleutel aan.
         * @param entiteit de entiteit
         * @param historieveldnaam de historieveldnaam
         * @param objecttype het objecttype
         */
        EntiteitDbObjectSleutel(final Class entiteit, final Historieveldnaam historieveldnaam, final Element objecttype) {
            this.entiteit = entiteit;
            this.historieveldnaam = historieveldnaam;
            this.objecttype = objecttype;
        }

        @Override
        public boolean equals(final Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || (this.getClass() != other.getClass())) {
                return false;
            }
            final EntiteitDbObjectSleutel castOther = (EntiteitDbObjectSleutel) other;
            return new EqualsBuilder().append(this.entiteit, castOther.entiteit)
                    .append(this.historieveldnaam, castOther.historieveldnaam)
                    .append(this.objecttype, castOther.objecttype)
                    .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(entiteit).append(historieveldnaam).append(objecttype).toHashCode();
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("entiteit", entiteit)
                    .append("historieveldnaam", historieveldnaam)
                    .append("objecttype", objecttype)
                    .toString();
        }
    }
}

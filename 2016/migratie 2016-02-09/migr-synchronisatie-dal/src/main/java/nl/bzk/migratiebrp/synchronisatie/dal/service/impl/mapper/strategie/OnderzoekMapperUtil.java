/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Element;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.GegevenInOnderzoek;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.MaterieleHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.util.PersistenceUtil;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.apache.commons.lang3.tuple.Pair;
import org.hibernate.annotations.AnyMetaDef;
import org.hibernate.annotations.MetaValue;

/**
 * Ondersteuning voor het mappen van onderzoek gegevens.
 */
public final class OnderzoekMapperUtil {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final Map<Pair<Class, Historieveldnaam>, Element> ENTITEIT_DBOBJECT_MAP = vulEntiteitDbobjectMap();
    private static final String STANDAARD = "_STANDAARD";
    private static final String IDENTITEIT = "_IDENTITEIT";

    private OnderzoekMapperUtil() {
        throw new AssertionError("Er mag geen instantie van een OnderzoekMapperUtil class gemaakt worden.");
    }

    /**
     * Bepaal welke dbobject waarde hoort bij een Historieveldnaam in een willekeurige entiteit.
     * 
     * @param entiteit
     *            De entiteit instantie waar het historie veld in zit.
     * @param veldNaam
     *            De historie veldnaam.
     * @return de bijbehorende Dbobject instantie.
     */
    public static Element bepaalDbobject(final Object entiteit, final Historieveldnaam veldNaam) {
        final Class entiteitClass = PersistenceUtil.bepaalEntiteitClass(entiteit.getClass());
        final Pair<Class, Historieveldnaam> classNaamPaar = Pair.of(entiteitClass, veldNaam);
        return ENTITEIT_DBOBJECT_MAP.get(classNaamPaar);
    }

    private static Map<Pair<Class, Historieveldnaam>, Element> vulEntiteitDbobjectMap() {
        final Map<Pair<Class, Historieveldnaam>, Element> entiteitDbobjectMap = new HashMap<>();

        try {
            final AnyMetaDef metadef = GegevenInOnderzoek.class.getDeclaredField("voorkomen").getAnnotation(AnyMetaDef.class);

            for (final MetaValue metaValue : metadef.metaValues()) {
                final short tableId = Short.parseShort(metaValue.value());
                final Class tableClass = metaValue.targetEntity();

                final Historieveldnaam[] historieVelden;

                if (MaterieleHistorie.class.isAssignableFrom(tableClass)) {
                    historieVelden = Historieveldnaam.materieleVelden();
                } else {
                    historieVelden = Historieveldnaam.formeleVelden();
                }

                for (final Historieveldnaam veldNaam : historieVelden) {
                    final Element tabelElement = Element.parseId(tableId);
                    final String tabelElementNaam = bepaalGenormaliseerdeTabelElementNaam(tabelElement);

                    final String veldElementNaam = tabelElementNaam + veldNaam.getNaam();
                    final Element veldElement = Element.valueOf(veldElementNaam);

                    if (veldElement != null) {
                        entiteitDbobjectMap.put(Pair.of(metaValue.targetEntity(), veldNaam), veldElement);
                    }
                }
            }
        } catch (final
            NoSuchFieldException
            | NumberFormatException e)
        {
            LOG.info("Fout bij vullen Entiteit-DbObject map.", e);
        }

        return entiteitDbobjectMap;
    }

    private static String bepaalGenormaliseerdeTabelElementNaam(final Element tabelElement) {
        final String tabelElementNaam = tabelElement.toString();
        final String tabelElementNaamGenormaliseerd;
        if (tabelElementNaam.endsWith(STANDAARD)) {
            tabelElementNaamGenormaliseerd = tabelElementNaam.substring(0, tabelElementNaam.length() - STANDAARD.length());
        } else if (tabelElementNaam.endsWith(IDENTITEIT)) {
            tabelElementNaamGenormaliseerd = tabelElementNaam.substring(0, tabelElementNaam.length() - IDENTITEIT.length());
        } else {
            tabelElementNaamGenormaliseerd = tabelElementNaam;
        }
        return tabelElementNaamGenormaliseerd;
    }

    /**
     * Interne enum voor de historie veldnamen van entiteiten.
     */
    public static enum Historieveldnaam {
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

        private Historieveldnaam(final String naam) {
            this.naam = naam;
        }

        /**
         * Geef de waarde van naam.
         *
         * @return naam
         */
        public String getNaam() {
            return naam;
        }

        /**
         * @return Velden voor groepen met materiele historie
         */
        public static Historieveldnaam[] materieleVelden() {
            return values();
        }

        /**
         * @return Velden voor groepen met alleen formele historie
         */
        public static Historieveldnaam[] formeleVelden() {
            return new Historieveldnaam[] {REGISTRATIE, VERVAL, N_A_VERVAL };
        }
    }

}

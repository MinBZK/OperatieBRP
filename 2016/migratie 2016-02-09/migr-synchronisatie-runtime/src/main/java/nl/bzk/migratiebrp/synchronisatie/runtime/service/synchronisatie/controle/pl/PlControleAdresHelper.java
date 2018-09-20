/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.AbstractBrpAttribuutMetOnderzoek;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpAdresInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Helper voor het vergelijken van de historie van adressen.
 */
public final class PlControleAdresHelper {

    private static final Comparator<BrpGroep<? extends BrpGroepInhoud>> GROEPEN_COMPARATOR = new BrpGroepenComparator();

    private PlControleAdresHelper() {
        // / Niet instantieerbaar
    }

    /**
     * Maakt op basis van de brp persoonslijst een map aan met daarin de voorkomens van de historische adressen. Per
     * voorkomen wordt in de map bijgehouden hoe vaak deze voorkomt op de persoonslijst.
     *
     * @param persoonslijst
     *            De brp persoonslijst.
     * @return De map met daarin de adressen en hoe vaak deze voorkomen op de persoonslijst.
     */
    public static Map<AdresData, Long> getAdresHistorie(final BrpPersoonslijst persoonslijst) {
        final BrpStapel<BrpAdresInhoud> stapel = persoonslijst.getAdresStapel();
        if (stapel == null) {
            return new LinkedHashMap<AdresData, Long>();
        }

        final Map<AdresData, Long> adressen = new LinkedHashMap<>();
        final List<BrpGroep<BrpAdresInhoud>> groepen = stapel.getGroepen();
        Collections.sort(groepen, GROEPEN_COMPARATOR);

        final Iterator<BrpGroep<BrpAdresInhoud>> iterator = groepen.iterator();

        while (iterator.hasNext()) {
            final BrpGroep<BrpAdresInhoud> groep = iterator.next();
            if (groep.getHistorie().getDatumEindeGeldigheid() == null && groep.getHistorie().getDatumTijdVerval() == null) {
                continue;
            }
            voegAdresToeAanMap(adressen, new AdresData(groep.getInhoud(), true));
        }
        return adressen;
    }

    /**
     * Voegt een adres toe aan de map van adressen.
     *
     * @param adressenMap
     *            De huidige map van adressen.
     * @param adres
     *            Het toe te voegen adres.
     */
    public static void voegAdresToeAanMap(final Map<AdresData, Long> adressenMap, final AdresData adres) {

        if (adressenMap.containsKey(adres)) {
            Long aantal = adressenMap.get(adres);
            adressenMap.put(adres, ++aantal);
        } else {
            adressenMap.put(adres, 1L);
        }

    }

    /**
     * Vergelijkt of de gegeven volledige set van adressen de gegeven subset van adressen bevat.
     *
     * @param volledigeSet
     *            volledige set van adressen
     * @param subSet
     *            Map met de adressen van de gevonden PL.
     * @return True indien bovenstaande vergelijking slaagt, false in andere gevallen.
     */
    public static boolean volledigSetBevatSubset(final Map<AdresData, Long> volledigeSet, final Map<AdresData, Long> subSet) {

        boolean resultaat = true;

        for (final Iterator<Map.Entry<AdresData, Long>> adresIterator = subSet.entrySet().iterator(); adresIterator.hasNext();) {

            final Map.Entry<AdresData, Long> adres = adresIterator.next();

            if (volledigeSet.get(adres.getKey()) == null) {
                resultaat = false;
                break;
            }

            if (!(volledigeSet.get(adres.getKey()) >= subSet.get(adres.getKey()))) {
                resultaat = false;
                break;
            }
        }

        return resultaat;
    }

    /**
     * Comparator voor mogelijke null waarden.
     *
     * @param o1
     *            Waarde 1
     * @param o2
     *            Waarde 2
     * @return Resultaat van de vergelijking.
     */
    private static <T extends Comparable<T>> int compareNulls(final T o1, final T o2) {
        if (o1 == null) {
            return o2 == null ? 0 : -1;
        } else {
            return o2 == null ? 1 : o1.compareTo(o2);
        }
    }

    /**
     * Comparator voor de BrpGroepInhoud.
     */
    private static class BrpGroepenComparator implements Comparator<BrpGroep<? extends BrpGroepInhoud>>, Serializable {

        private static final long serialVersionUID = 1L;

        @Override
        public final int compare(final BrpGroep<? extends BrpGroepInhoud> o1, final BrpGroep<? extends BrpGroepInhoud> o2) {
            int result = 0;

            if (o1 == null || o1.getHistorie() == null || o2 == null || o2.getHistorie() == null) {
                return result;
            }
            result = -compareNulls(o1.getHistorie().getDatumAanvangGeldigheid(), o2.getHistorie().getDatumAanvangGeldigheid());

            if (result == 0) {
                result = -compareNulls(o1.getHistorie().getDatumTijdRegistratie(), o2.getHistorie().getDatumTijdRegistratie());
            }

            if (result == 0) {
                result = compareNulls(o1.getHistorie().getDatumTijdVerval(), o2.getHistorie().getDatumTijdVerval());
            }

            if (result == 0) {
                result = compareNulls(o1.getHistorie().getDatumEindeGeldigheid(), o2.getHistorie().getDatumEindeGeldigheid());
            }
            return result;
        }

    }

    /**
     * Immutable klasse om gegevens waarop wordt vergeleken bij adressen in een object samen te vatten.
     */
    public static final class AdresData {

        private final BrpGemeenteCode gemeenteCode;
        private final BrpInteger huisnummer;
        private final BrpCharacter huisletter;
        private final BrpString huisnummertoevoeging;
        private final BrpString postcode;
        private final BrpString woonplaatsnaam;
        private final BrpString buitenlandsAdresRegel1;
        private final BrpString buitenlandsAdresRegel2;
        private final BrpString buitenlandsAdresRegel3;
        private final BrpString buitenlandsAdresRegel4;
        private final BrpString buitenlandsAdresRegel5;
        private final BrpString buitenlandsAdresRegel6;
        private final boolean gemeenteCodeInControle;

        /**
         * Constructor waarbij gegevens vanuit het BRP-adres worden overgenomen.
         *
         * @param adres
         *            Het BRP adres waaruit we de gegevens over nemen.
         * @param gemeenteCodeInControle
         *            True indien de gemeentecode meegenomen dient te worden in de vergelijking.
         *
         */
        public AdresData(final BrpAdresInhoud adres, final boolean gemeenteCodeInControle) {
            gemeenteCode = adres.getGemeenteCode();
            huisnummer = adres.getHuisnummer();
            huisletter = adres.getHuisletter();
            huisnummertoevoeging = adres.getHuisnummertoevoeging();
            postcode = adres.getPostcode();
            woonplaatsnaam = adres.getWoonplaatsnaam();
            buitenlandsAdresRegel1 = adres.getBuitenlandsAdresRegel1();
            buitenlandsAdresRegel2 = adres.getBuitenlandsAdresRegel2();
            buitenlandsAdresRegel3 = adres.getBuitenlandsAdresRegel3();
            buitenlandsAdresRegel4 = adres.getBuitenlandsAdresRegel4();
            buitenlandsAdresRegel5 = adres.getBuitenlandsAdresRegel5();
            buitenlandsAdresRegel6 = adres.getBuitenlandsAdresRegel6();
            this.gemeenteCodeInControle = gemeenteCodeInControle;
        }

        @Override
        public int hashCode() {
            if (gemeenteCodeInControle) {
                return new HashCodeBuilder().append(PlControleHelper.geefAttribuutWaarde(gemeenteCode))
                                            .append(PlControleHelper.geefAttribuutWaarde(huisnummer))
                                            .append(PlControleHelper.geefAttribuutWaarde(huisletter))
                                            .append(PlControleHelper.geefAttribuutWaarde(huisnummertoevoeging))
                                            .append(PlControleHelper.geefAttribuutWaarde(postcode))
                                            .append(PlControleHelper.geefAttribuutWaarde(woonplaatsnaam))
                                            .append(PlControleHelper.geefAttribuutWaarde(buitenlandsAdresRegel1))
                                            .append(PlControleHelper.geefAttribuutWaarde(buitenlandsAdresRegel2))
                                            .append(PlControleHelper.geefAttribuutWaarde(buitenlandsAdresRegel3))
                                            .append(PlControleHelper.geefAttribuutWaarde(buitenlandsAdresRegel4))
                                            .append(PlControleHelper.geefAttribuutWaarde(buitenlandsAdresRegel5))
                                            .append(PlControleHelper.geefAttribuutWaarde(buitenlandsAdresRegel6))
                                            .toHashCode();
            } else {
                return new HashCodeBuilder().append(PlControleHelper.geefAttribuutWaarde(huisnummer))
                                            .append(PlControleHelper.geefAttribuutWaarde(huisletter))
                                            .append(PlControleHelper.geefAttribuutWaarde(huisnummertoevoeging))
                                            .append(PlControleHelper.geefAttribuutWaarde(postcode))
                                            .append(PlControleHelper.geefAttribuutWaarde(woonplaatsnaam))
                                            .append(PlControleHelper.geefAttribuutWaarde(buitenlandsAdresRegel1))
                                            .append(PlControleHelper.geefAttribuutWaarde(buitenlandsAdresRegel2))
                                            .append(PlControleHelper.geefAttribuutWaarde(buitenlandsAdresRegel3))
                                            .append(PlControleHelper.geefAttribuutWaarde(buitenlandsAdresRegel4))
                                            .append(PlControleHelper.geefAttribuutWaarde(buitenlandsAdresRegel5))
                                            .append(PlControleHelper.geefAttribuutWaarde(buitenlandsAdresRegel6))
                                            .toHashCode();
            }
        }

        @Override
        public boolean equals(final Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof AdresData)) {
                return false;
            }
            final AdresData castOther = (AdresData) other;

            if (gemeenteCodeInControle) {
                return AbstractBrpAttribuutMetOnderzoek.equalsWaarde(gemeenteCode, castOther.gemeenteCode)
                       && bepaalHuisnummerGegevensGelijk(castOther)
                       && AbstractBrpAttribuutMetOnderzoek.equalsWaarde(postcode, castOther.postcode)
                       && AbstractBrpAttribuutMetOnderzoek.equalsWaarde(woonplaatsnaam, castOther.woonplaatsnaam)
                       && bepaalBuitenlandseAdresRegels1Tot3Gelijk(castOther)
                       && bepaalBuitenlandseAdresRegels4Tot6Gelijk(castOther);
            } else {
                return bepaalHuisnummerGegevensGelijk(castOther)
                        && AbstractBrpAttribuutMetOnderzoek.equalsWaarde(postcode, castOther.postcode)
                        && AbstractBrpAttribuutMetOnderzoek.equalsWaarde(woonplaatsnaam, castOther.woonplaatsnaam)
                        && bepaalBuitenlandseAdresRegels1Tot3Gelijk(castOther)
                        && bepaalBuitenlandseAdresRegels4Tot6Gelijk(castOther);
            }
        }

        private boolean bepaalHuisnummerGegevensGelijk(final AdresData castOther) {
            return AbstractBrpAttribuutMetOnderzoek.equalsWaarde(huisnummer, castOther.huisnummer)
                   && AbstractBrpAttribuutMetOnderzoek.equalsWaarde(huisnummer, castOther.huisnummer)
                   && AbstractBrpAttribuutMetOnderzoek.equalsWaarde(huisletter, castOther.huisletter)
                   && AbstractBrpAttribuutMetOnderzoek.equalsWaarde(huisnummertoevoeging, castOther.huisnummertoevoeging);
        }

        private boolean bepaalBuitenlandseAdresRegels1Tot3Gelijk(final AdresData castOther) {
            return AbstractBrpAttribuutMetOnderzoek.equalsWaarde(buitenlandsAdresRegel1, castOther.buitenlandsAdresRegel1)
                   && AbstractBrpAttribuutMetOnderzoek.equalsWaarde(buitenlandsAdresRegel2, castOther.buitenlandsAdresRegel2)
                   && AbstractBrpAttribuutMetOnderzoek.equalsWaarde(buitenlandsAdresRegel3, castOther.buitenlandsAdresRegel3);
        }

        private boolean bepaalBuitenlandseAdresRegels4Tot6Gelijk(final AdresData castOther) {
            return AbstractBrpAttribuutMetOnderzoek.equalsWaarde(buitenlandsAdresRegel4, castOther.buitenlandsAdresRegel4)
                   && AbstractBrpAttribuutMetOnderzoek.equalsWaarde(buitenlandsAdresRegel5, castOther.buitenlandsAdresRegel5)
                   && AbstractBrpAttribuutMetOnderzoek.equalsWaarde(buitenlandsAdresRegel6, castOther.buitenlandsAdresRegel6);
        }

        @Override
        public String toString() {
            final String toString =
                    new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("gemeenteCode", PlControleHelper.geefAttribuutWaarde(gemeenteCode))
                        .append("huisnummer", PlControleHelper.geefAttribuutWaarde(huisnummer))
                        .append("huisletter", PlControleHelper.geefAttribuutWaarde(huisletter))
                        .append("huisnummertoevoeging", PlControleHelper.geefAttribuutWaarde(huisnummertoevoeging))
                        .append("postcode", PlControleHelper.geefAttribuutWaarde(postcode))
                        .append("woonplaatsnaam", PlControleHelper.geefAttribuutWaarde(woonplaatsnaam))
                        .append("buitenlandsAdresRegel1", PlControleHelper.geefAttribuutWaarde(buitenlandsAdresRegel1))
                        .append("buitenlandsAdresRegel2", PlControleHelper.geefAttribuutWaarde(buitenlandsAdresRegel2))
                        .append("buitenlandsAdresRegel3", PlControleHelper.geefAttribuutWaarde(buitenlandsAdresRegel3))
                        .append("buitenlandsAdresRegel4", PlControleHelper.geefAttribuutWaarde(buitenlandsAdresRegel4))
                        .append("buitenlandsAdresRegel5", PlControleHelper.geefAttribuutWaarde(buitenlandsAdresRegel5))
                        .append("buitenlandsAdresRegel6", PlControleHelper.geefAttribuutWaarde(buitenlandsAdresRegel6))
                        .toString()
                        .replaceAll("PlControleAdresHelper.AdresData", "Adres")
                        .replaceAll("<null>", "<leeg>");
            return toString;
        }
    }

}

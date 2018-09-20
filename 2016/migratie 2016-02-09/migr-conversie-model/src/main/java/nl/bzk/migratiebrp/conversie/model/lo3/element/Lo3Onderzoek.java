/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.element;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Elementnummer;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3GroepEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Deze class representeert de LO3 categorieen onderzoek.
 * 
 * Deze class is immutable en threadsafe.
 * 
 */
public final class Lo3Onderzoek {

    private static final int TWEE_CIJFERS = 100;

    private static final String LO3_ONDERZOEK_GEGEVENS_FORMAT = "000000";

    /*
     * Groep 31 en 38 zijn niet opgenomen in deze lijst, aangezien cat13 (waar deze groepen onder vallen) niet in
     * onderzoek gezet kan worden.
     */
    private static final Set<Lo3GroepEnum> ALGEMENE_GROEPEN =
            new HashSet<>(
                Arrays.asList(
                    Lo3GroepEnum.GROEP01,
                    Lo3GroepEnum.GROEP02,
                    Lo3GroepEnum.GROEP03,
                    Lo3GroepEnum.GROEP04,
                    Lo3GroepEnum.GROEP05,
                    Lo3GroepEnum.GROEP06,
                    Lo3GroepEnum.GROEP07,
                    Lo3GroepEnum.GROEP08,
                    Lo3GroepEnum.GROEP09,
                    Lo3GroepEnum.GROEP10,
                    Lo3GroepEnum.GROEP11,
                    Lo3GroepEnum.GROEP12,
                    Lo3GroepEnum.GROEP13,
                    Lo3GroepEnum.GROEP14,
                    Lo3GroepEnum.GROEP15,
                    Lo3GroepEnum.GROEP32,
                    Lo3GroepEnum.GROEP33,
                    Lo3GroepEnum.GROEP35,
                    Lo3GroepEnum.GROEP36,
                    Lo3GroepEnum.GROEP39,
                    Lo3GroepEnum.GROEP61,
                    Lo3GroepEnum.GROEP62,
                    Lo3GroepEnum.GROEP63,
                    Lo3GroepEnum.GROEP64,
                    Lo3GroepEnum.GROEP65,
                    Lo3GroepEnum.GROEP85));

    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_8310)
    @Element(name = "aanduidingGegevensInOnderzoek", required = false)
    private final Lo3Integer aanduidingGegevensInOnderzoek;
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_8320)
    @Element(name = "datumIngangOnderzoek", required = false)
    private final Lo3Datum datumIngangOnderzoek;
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_8330)
    @Element(name = "datumEindeOnderzoek", required = false)
    private final Lo3Datum datumEindeOnderzoek;
    // Herkomst wordt alleen gebruikt tbv Bijzondere situatie LB039.
    private final Lo3Herkomst lo3Herkomst;

    /**
     * Default constructor (alles null).
     */
    public Lo3Onderzoek() {
        this(null, null, null);
    }

    /**
     * Maakt een Lo3Onderzoek object.
     * 
     * @param gegevensInOnderzoek
     *            83.10 aanduiding gegevens in onderzoek
     * @param datumIngang
     *            83.20 datum ingang onderzoek
     * @param datumEinde
     *            83.10 datum einde onderzoek
     */
    public Lo3Onderzoek(
        @Element(name = "aanduidingGegevensInOnderzoek", required = false) final Lo3Integer gegevensInOnderzoek,
        @Element(name = "datumIngangOnderzoek", required = false) final Lo3Datum datumIngang,
        @Element(name = "datumEindeOnderzoek", required = false) final Lo3Datum datumEinde)
    {
        this(gegevensInOnderzoek, datumIngang, datumEinde, null);
    }

    /**
     * Maakt een Lo3Onderzoek object.
     * 
     * @param gegevensInOnderzoek
     *            83.10 aanduiding gegevens in onderzoek
     * @param datumIngang
     *            83.20 datum ingang onderzoek
     * @param datumEinde
     *            83.10 datum einde onderzoek
     * @param herkomst
     *            herkomst van dit onderzoek
     */
    public Lo3Onderzoek(final Lo3Integer gegevensInOnderzoek, final Lo3Datum datumIngang, final Lo3Datum datumEinde, final Lo3Herkomst herkomst) {
        this.aanduidingGegevensInOnderzoek = gegevensInOnderzoek;
        this.datumIngangOnderzoek = datumIngang;
        this.datumEindeOnderzoek = datumEinde;
        this.lo3Herkomst = herkomst;
    }

    /**
     * Maak een Lo3Onderzoek, indien nodig.
     * 
     * @param aanduidingGegevensInOnderzoek
     *            83.10 aanduiding gegevens in onderzoek
     * @param datumIngangOnderzoek
     *            83.20 datum ingang onderzoek
     * @param datumEindeOnderzoek
     *            83.10 datum einde onderzoek
     * @param lo3Herkomst
     *            herkomst van dit onderzoek
     * 
     * @return een nieuwe Lo3Onderzoek, null als alle velden leeg zijn
     */
    public static Lo3Onderzoek build(
        final Lo3Integer aanduidingGegevensInOnderzoek,
        final Lo3Datum datumIngangOnderzoek,
        final Lo3Datum datumEindeOnderzoek,
        final Lo3Herkomst lo3Herkomst)
    {
        if (Validatie.isEenParameterGevuld(aanduidingGegevensInOnderzoek, datumIngangOnderzoek, datumEindeOnderzoek)) {
            return new Lo3Onderzoek(aanduidingGegevensInOnderzoek, datumIngangOnderzoek, datumEindeOnderzoek, lo3Herkomst);
        } else {
            return null;
        }
    }

    /**
     * Bepaal welk onderzoek uit een verzameling met onderzoeken met meest relevant is. Het resultaat is altijd één van
     * de onderzoeken in de invoer. Er vindt geen aanpassing plaats aan de GegevensInOnderzoek waarde van het resultaat.
     *
     * @param onderzoeken
     *            de verzameling onderzoeken.
     * @return het meest relevante onderzoek uit de verzameling.
     */
    public static Lo3Onderzoek bepaalRelevantOnderzoek(final Collection<Lo3Onderzoek> onderzoeken) {
        final Collection<Lo3Onderzoek> nietNullOnderzoeken = verwijderNullOnderzoeken(onderzoeken);

        if (nietNullOnderzoeken.isEmpty()) {
            return null;
        }

        final boolean bevatLopendOnderzoek = Lo3Onderzoek.bevatLopendOnderzoek(nietNullOnderzoeken);

        Lo3Onderzoek resultaat = null;

        for (final Lo3Onderzoek onderzoek : nietNullOnderzoeken) {
            if (bevatLopendOnderzoek && Validatie.isElementGevuld(onderzoek.getDatumEindeOnderzoek())) {
                // Als er een lopend onderzoek is dan worden afgesloten onderzoeken niet meegenomen
                continue;
            }

            if (resultaat == null) {
                resultaat = onderzoek;
                continue;
            }
            if (bevatLopendOnderzoek) {
                if (onderzoek.getDatumIngangOnderzoek().maximaliseerOnbekendeDatum().getIntegerWaarde() > resultaat.getDatumIngangOnderzoek()
                                                                                                                   .getIntegerWaarde())
                {
                    resultaat = onderzoek;
                }
            } else {
                // Er zijn geen lopende onderzoeken, dus datum einde is altijd gevuld
                if (onderzoek.getDatumEindeOnderzoek().maximaliseerOnbekendeDatum().getIntegerWaarde() > resultaat.getDatumEindeOnderzoek()
                                                                                                                  .getIntegerWaarde())
                {
                    resultaat = onderzoek;
                }
            }
        }

        return resultaat;
    }

    private static Collection<Lo3Onderzoek> verwijderNullOnderzoeken(final Collection<Lo3Onderzoek> onderzoeken) {
        final Collection<Lo3Onderzoek> resultaat = new LinkedList<>();

        for (final Lo3Onderzoek lo3Onderzoek : onderzoeken) {
            if (lo3Onderzoek != null) {
                resultaat.add(lo3Onderzoek);
            }
        }

        return resultaat;
    }

    /**
     * Consolideer verschillende onderzoeken tot 1 onderzoek. De GegevensInOnderzoek aanduidingen worden samengevoegd
     * tot 1 aanduiding in het resultaat. Voor het bepalen van de begin- end einddatum van het resultaat wordt gebruik
     * gemaakt van <code>bepaalRelevantOnderzoek()</code>.
     *
     * @param onderzoeken
     *            de verzameling onderzoeken.
     * @param categorie
     *            de categorie code die voor het resultaat wordt gebruikt. Wordt genegeerd als er maar 1 onderzoek in de
     *            verzameling onderzoeken zit.
     * @return het meest relevante onderzoek uit de verzameling.
     */
    public static Lo3Onderzoek consolideerOnderzoeken(final Collection<Lo3Onderzoek> onderzoeken, final Lo3CategorieEnum categorie) {
        if (onderzoeken == null || onderzoeken.isEmpty()) {
            return null;
        }

        Lo3Onderzoek resultaat = null;

        if (onderzoeken.size() == 1) {
            resultaat = onderzoeken.iterator().next();
        } else {
            resultaat = doConsolidatieOnderzoeken(onderzoeken, categorie);
        }

        return resultaat;
    }

    private static Lo3Onderzoek doConsolidatieOnderzoeken(final Collection<Lo3Onderzoek> onderzoeken, final Lo3CategorieEnum categorie) {
        Lo3Onderzoek resultaat = null;
        final boolean bevatLopendOnderzoek = Lo3Onderzoek.bevatLopendOnderzoek(onderzoeken);

        boolean verwerkEersteOnderzoek = true;
        int groepnummer = 0;
        int elementnummer = 0;

        for (final Lo3Onderzoek onderzoek : onderzoeken) {
            if (bevatLopendOnderzoek && onderzoek.getDatumEindeOnderzoek() != null) {
                // Als er een lopend onderzoek is dan worden afgesloten onderzoeken niet meegenomen
                continue;
            }

            if (verwerkEersteOnderzoek) {
                verwerkEersteOnderzoek = false;
                groepnummer = onderzoek.getOnderzoekGroepnummer();
                elementnummer = onderzoek.getOnderzoekElementnummer();
            } else {
                if (groepnummer != onderzoek.getOnderzoekGroepnummer()) {
                    groepnummer = 0;
                    elementnummer = 0;
                }

                if (elementnummer != onderzoek.getOnderzoekElementnummer()) {
                    elementnummer = 0;
                }
            }
        }

        final Lo3Onderzoek onderzoekMetadata = bepaalRelevantOnderzoek(onderzoeken);
        if (onderzoekMetadata != null) {
            final String elementCode = bepaalElementCode(categorie.getCategorieAsInt(), groepnummer, elementnummer);

            resultaat =
                    new Lo3Onderzoek(
                        new Lo3Integer(elementCode, null),
                        onderzoekMetadata.getDatumIngangOnderzoek(),
                        onderzoekMetadata.getDatumEindeOnderzoek());
        }
        return resultaat;
    }

    private static boolean bevatLopendOnderzoek(final Collection<Lo3Onderzoek> onderzoeken) {
        boolean bevatLopendOnderzoek = false;

        final Iterator<Lo3Onderzoek> iterator = onderzoeken.iterator();
        while (!bevatLopendOnderzoek && iterator.hasNext()) {
            final Lo3Onderzoek onderzoek = iterator.next();
            bevatLopendOnderzoek = onderzoek.getDatumEindeOnderzoek() == null;
        }

        return bevatLopendOnderzoek;
    }

    private static String bepaalElementCode(final int categorienummer, final int groepnummer, final int rubrieknummer) {
        return String.valueOf(categorienummer * TWEE_CIJFERS * TWEE_CIJFERS + groepnummer * TWEE_CIJFERS + rubrieknummer);
    }

    /**
     * Geef de waarde van aanduiding gegevens in onderzoek.
     *
     * @return aanduiding gegevens in onderzoek
     */
    public Lo3Integer getAanduidingGegevensInOnderzoek() {
        return aanduidingGegevensInOnderzoek;
    }

    /**
     * Geef de waarde van aanduiding gegevens in onderzoek code.
     *
     * @return aanduiding gegevens in onderzoek in code vorm zoals opgeslagen in de LO3 PL.
     */
    public String getAanduidingGegevensInOnderzoekCode() {
        if (getAanduidingGegevensInOnderzoek() != null) {
            return new DecimalFormat(LO3_ONDERZOEK_GEGEVENS_FORMAT).format(Lo3Integer.unwrap(getAanduidingGegevensInOnderzoek()));
        }
        return null;
    }

    /**
     * Geef de waarde van datum ingang onderzoek.
     *
     * @return datum ingang onderzoek
     */
    public Lo3Datum getDatumIngangOnderzoek() {
        return datumIngangOnderzoek;
    }

    /**
     * Geef de waarde van datum einde onderzoek.
     *
     * @return datum einde onderzoek
     */
    public Lo3Datum getDatumEindeOnderzoek() {
        return datumEindeOnderzoek;
    }

    /**
     * Geef de waarde van lo3 herkomst.
     *
     * @return de LO3 herkomst waar dit onderzoek toe behoord.
     */
    public Lo3Herkomst getLo3Herkomst() {
        return lo3Herkomst;
    }

    /**
     * Bepaal of dit onderzoek betrekking heeft op het gegeven element in de gegeven categorie.
     *
     * @param element
     *            Het element
     * @param categorie
     *            De categorie
     * @return Heeft het onderzoek betrekking op het gegeven element in de gegeven categorie.
     */
    public boolean omvatElementInCategorie(final Lo3ElementEnum element, final Lo3CategorieEnum categorie) {
        final boolean resultaat;

        if (aanduidingGegevensInOnderzoek == null || !aanduidingGegevensInOnderzoek.isInhoudelijkGevuld()) {
            resultaat = false;
        } else if (magAnummerNietInOnderzoek(element, categorie)) {
            resultaat = false;
        } else {
            final int onderzoekCategorie = getOnderzoekCategorienummer();
            final int onderzoekGroep = getOnderzoekGroepnummer();
            final int onderzoekElement = getOnderzoekElementnummer();

            if (!categorieKomtOvereen(categorie, onderzoekCategorie)) {
                resultaat = false;
            } else if (onderzoekGroep != 0) {
                // Onderzoek naar specifieke groep of element
                final boolean groepKomtOvereen = onderzoekGroep == element.getGroep().getGroepAsInt();
                final boolean elementKomtOvereen = onderzoekElement == 0 || onderzoekElement == element.getRubriek().getRubriekAsInt();

                resultaat = groepKomtOvereen && elementKomtOvereen;
            } else {
                // Onderzoek naar hele categorie
                resultaat = ALGEMENE_GROEPEN.contains(element.getGroep());
            }
        }

        return resultaat;
    }

    private boolean categorieKomtOvereen(final Lo3CategorieEnum categorie, final int onderzoekCategorie) {
        return onderzoekCategorie == categorie.getCategorieAsInt()
               || onderzoekCategorie == Lo3CategorieEnum.bepaalActueleCategorie(categorie).getCategorieAsInt();
    }

    private boolean magAnummerNietInOnderzoek(final Lo3ElementEnum element, final Lo3CategorieEnum categorie) {
        final boolean persoonOfVerwijsCat =
                categorie.equals(Lo3CategorieEnum.CATEGORIE_01)
                                            || categorie.equals(Lo3CategorieEnum.CATEGORIE_51)
                                            || categorie.equals(Lo3CategorieEnum.CATEGORIE_21)
                                            || categorie.equals(Lo3CategorieEnum.CATEGORIE_71);
        return element.equals(Lo3ElementEnum.ANUMMER) && persoonOfVerwijsCat;
    }

    /**
     * Geef het elementnummer van de Aanduiding Gegevens In Onderzoek.
     *
     * @return het elementnummer van de Aanduiding Gegevens In Onderzoek.
     */
    public int getOnderzoekElementnummer() {
        return Lo3Integer.unwrap(aanduidingGegevensInOnderzoek) % TWEE_CIJFERS;
    }

    /**
     * Geef het groepnummer van de Aanduiding Gegevens In Onderzoek.
     *
     * @return het groepnummer van de Aanduiding Gegevens In Onderzoek.
     */
    public int getOnderzoekGroepnummer() {
        return Lo3Integer.unwrap(aanduidingGegevensInOnderzoek) / TWEE_CIJFERS % TWEE_CIJFERS;
    }

    /**
     * Geef het categorienummer van de Aanduiding Gegevens In Onderzoek.
     *
     * @return het categorienummer van de Aanduiding Gegevens In Onderzoek.
     */
    public int getOnderzoekCategorienummer() {
        return Lo3Integer.unwrap(aanduidingGegevensInOnderzoek) / (TWEE_CIJFERS * TWEE_CIJFERS);
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Lo3Onderzoek)) {
            return false;
        }
        final Lo3Onderzoek castOther = (Lo3Onderzoek) other;
        return new EqualsBuilder().append(aanduidingGegevensInOnderzoek, castOther.aanduidingGegevensInOnderzoek)
                                  .append(datumIngangOnderzoek, castOther.datumIngangOnderzoek)
                                  .append(datumEindeOnderzoek, castOther.datumEindeOnderzoek)
                                  .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(aanduidingGegevensInOnderzoek).append(datumIngangOnderzoek).append(datumEindeOnderzoek).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("aanduidingGegevensInOnderzoek", aanduidingGegevensInOnderzoek)
                                                                          .append("datumIngangOnderzoek", datumIngangOnderzoek)
                                                                          .append("datumEindeOnderzoek", datumEindeOnderzoek)
                                                                          .toString();
    }
}

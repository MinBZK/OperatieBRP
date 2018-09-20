/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Comparator;

import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3CategorieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.AbstractLo3Element;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Element;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Validatie;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Elementnummer;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Deze abstracte class is de super class voor alle LO3 categorien. Een LO3 categorie bestaat mogelijk uit:
 * <ul>
 * <li>Lo3CategorieInhoud</li>
 * <li>Lo3Historie</li>
 * <li>Lo3Documentatie</li>
 * <li>Herkomst</li>
 * </ul>
 * Deze klasse staat geen wijzigingen toe op de referenties die deze klasse vasthoudt. Dit betekend dat als de instantie
 * voor inhoud immutable is dat de gehele categorie immutable is (aangezien ook lo3historie en lo3documentatie immutable
 * zijn).
 * 
 * @param <T>
 *            de specifieke LO3 categorie inhoud
 * 
 */
public final class Lo3Categorie<T extends Lo3CategorieInhoud> {

    /**
     * @see DatumGeldigheidComparator
     */
    public static final Comparator<Lo3Categorie<? extends Lo3CategorieInhoud>> DATUM_GELDIGHEID = new DatumGeldigheidComparator();
    private final T inhoud;
    private final Lo3Historie historie;
    private final Lo3Documentatie documentatie;
    private final Lo3Onderzoek onderzoek;
    private final Lo3Herkomst lo3Herkomst;
    private final boolean afsluitendVoorkomen;

    /**
     * Maakt een Lo3Categorie object zonder onderzoek.
     * 
     * @param inhoud
     *            de LO3 categorie inhoud, mag niet null zijn
     * @param documentatie
     *            de LO3 documentatie, mag null zijn
     * @param historie
     *            de LO3 historie, mag niet null zijn
     * @param lo3Herkomst
     *            de herkomst van de categorie, mag null zijn
     * @throws NullPointerException
     *             als inhoud of historie null is
     * @see #Lo3Categorie(Lo3CategorieInhoud, Lo3Documentatie, Lo3Onderzoek, Lo3Historie, Lo3Herkomst)
     */
    public Lo3Categorie(final T inhoud, final Lo3Documentatie documentatie, final Lo3Historie historie, final Lo3Herkomst lo3Herkomst) {
        this(inhoud, documentatie, null, historie, lo3Herkomst);
    }

    /**
     * Maakt een Lo3Categorie object zonder onderzoek.
     * 
     * @param inhoud
     *            de LO3 categorie inhoud, mag niet null zijn
     * @param documentatie
     *            de LO3 documentatie, mag null zijn
     * @param historie
     *            de LO3 historie, mag niet null zijn
     * @param lo3Herkomst
     *            de herkomst van de categorie, mag null zijn
     * @param afsluitendVoorkomen
     *            als dit voorkomen alleen gebruikt wordt om een eerder voorkomen af te sluiten (bv bij het splitsen van
     *            relaties)
     * @throws NullPointerException
     *             als inhoud of historie null is
     * @see #Lo3Categorie(Lo3CategorieInhoud, Lo3Documentatie, Lo3Onderzoek, Lo3Historie, Lo3Herkomst)
     */
    public Lo3Categorie(
        final T inhoud,
        final Lo3Documentatie documentatie,
        final Lo3Historie historie,
        final Lo3Herkomst lo3Herkomst,
        final boolean afsluitendVoorkomen)
    {
        this(inhoud, documentatie, null, historie, lo3Herkomst, afsluitendVoorkomen);
    }

    /**
     * Maakt een Lo3Categorie object.
     * 
     * @param inhoud
     *            de LO3 categorie inhoud, mag niet null zijn
     * @param documentatie
     *            de LO3 documentatie, mag null zijn
     * @param historie
     *            de LO3 historie, mag niet null zijn
     * @param onderzoek
     *            de LO3 onderzoek, mag null zijn
     * @param lo3Herkomst
     *            de herkomst van de categorie, mag null zijn
     * @throws NullPointerException
     *             als inhoud of historie null is
     */
    public Lo3Categorie(
        @Element(name = "inhoud") final T inhoud,
        @Element(name = "documentatie", required = false) final Lo3Documentatie documentatie,
        @Element(name = "onderzoek", required = false) final Lo3Onderzoek onderzoek,
        @Element(name = "historie") final Lo3Historie historie,
        @Element(name = "lo3Herkomst", required = false) final Lo3Herkomst lo3Herkomst)
    {
        this(inhoud, documentatie, onderzoek, historie, lo3Herkomst, false);
    }

    /**
     * Maakt een Lo3Categorie object.
     * 
     * @param inhoud
     *            de LO3 categorie inhoud, mag niet null zijn
     * @param documentatie
     *            de LO3 documentatie, mag null zijn
     * @param historie
     *            de LO3 historie, mag niet null zijn
     * @param onderzoek
     *            de LO3 onderzoek, mag null zijn
     * @param lo3Herkomst
     *            de herkomst van de categorie, mag null zijn
     * @param afsluitendVoorkomen
     *            als dit voorkomen alleen gebruikt wordt om een eerder voorkomen af te sluiten (bv bij het splitsen van
     *            relaties)
     * @throws NullPointerException
     *             als inhoud of historie null is
     */
    public Lo3Categorie(
        final T inhoud,
        final Lo3Documentatie documentatie,
        final Lo3Onderzoek onderzoek,
        final Lo3Historie historie,
        final Lo3Herkomst lo3Herkomst,
        final boolean afsluitendVoorkomen)
    {
        if (inhoud == null) {
            throw new NullPointerException("inhoud mag niet null zijn");
        }
        if (historie == null) {
            throw new NullPointerException("historie mag niet null zijn");
        }
        this.inhoud = inhoud;

        if (documentatie != null) {
            this.documentatie = documentatie;
        } else {
            this.documentatie = new Lo3Documentatie(UniqueSequence.next(), null, null, null, null, null, null, null);
        }

        this.onderzoek = onderzoek;
        this.historie = historie;
        this.lo3Herkomst = lo3Herkomst;
        this.afsluitendVoorkomen = afsluitendVoorkomen;
    }

    /**
     * Geef de waarde van inhoud.
     *
     * @return de LO3 inhoud van deze LO3 categorie
     */
    @Element(name = "inhoud")
    public T getInhoud() {
        return inhoud;
    }

    /**
     * Geef de waarde van historie.
     *
     * @return de LO3 historie van deze LO3 categorie
     */
    @Element(name = "historie")
    public Lo3Historie getHistorie() {
        return historie;
    }

    /**
     * Geef de waarde van onderzoek.
     *
     * @return de LO3 onderzoek van deze LO3 categorie
     */
    @Element(name = "onderzoek", required = false)
    public Lo3Onderzoek getOnderzoek() {
        return onderzoek;
    }

    /**
     * Geef de waarde van documentatie.
     *
     * @return de LO3 documentatie van deze LO3 categorie
     */
    @Element(name = "documentatie", required = false)
    public Lo3Documentatie getDocumentatie() {
        return documentatie;
    }

    /**
     * Geef de waarde van lo3 herkomst.
     *
     * @return de herkomst van deze LO3 categorie
     */
    @Element(name = "lo3Herkomst", required = false)
    public Lo3Herkomst getLo3Herkomst() {
        return lo3Herkomst;
    }

    /**
     * Geef de afsluitend voorkomen.
     *
     * @return true als dit voorkomen gemaakt is om een ander voorkomen af te sluiten (bv bij splitsen van relaties)
     */
    public boolean isAfsluitendVoorkomen() {
        return afsluitendVoorkomen;
    }

    /**
     * Bepaalt de verschillen tussen dit voorkomen en het meegegeven voorkomen.
     * 
     * @param voorkomen
     *            het voorkomen waarmee vergeleken gaat worden
     * @return {@link Lo3VerschillenLog}
     */
    public Lo3VerschillenLog bepaalVerschillen(final Lo3Categorie<T> voorkomen) {
        final Lo3VerschillenLog verschillenLog = new Lo3VerschillenLog();
        // Inhoud en historie zijn verplichte velden. Zijn dus altijd aanwezig. Documentatie wordt ingevuld met minimaal
        // een ID.
        bepaalVerschillen(inhoud, voorkomen.inhoud, verschillenLog);
        bepaalVerschillen(historie, voorkomen.historie, verschillenLog);
        bepaalVerschillen(documentatie, voorkomen.documentatie, verschillenLog);
        // Onderzoek is optioneel.
        bepaalVerschillenOnderzoek(onderzoek, voorkomen.onderzoek, verschillenLog);

        return verschillenLog;
    }

    private void bepaalVerschillenOnderzoek(final Lo3Onderzoek left, final Lo3Onderzoek right, final Lo3VerschillenLog verschillenLog) {
        if (left == null && right == null) {
            return;
        }
        if (left == null) {
            verschillenLog.addNieuwElement(Lo3ElementEnum.ELEMENT_8310);
            verschillenLog.addNieuwElement(Lo3ElementEnum.ELEMENT_8320);
            verschillenLog.addNieuwElement(Lo3ElementEnum.ELEMENT_8330);
        } else if (right == null) {
            verschillenLog.addVerwijderdElement(Lo3ElementEnum.ELEMENT_8310);
            verschillenLog.addVerwijderdElement(Lo3ElementEnum.ELEMENT_8320);
            verschillenLog.addVerwijderdElement(Lo3ElementEnum.ELEMENT_8330);
        } else {
            bepaalVerschillen(left, right, verschillenLog);
        }
    }

    private void bepaalVerschillen(final Object left, final Object right, final Lo3VerschillenLog verschillenLog) {
        if (left == null || right == null) {
            return;
        }
        final Field[] fields = left.getClass().getDeclaredFields();
        for (final Field field : fields) {
            final Lo3Elementnummer elementAnnotation = field.getAnnotation(Lo3Elementnummer.class);
            if (elementAnnotation != null) {
                final Lo3ElementEnum elementNummer = elementAnnotation.value();
                field.setAccessible(true);
                try {
                    final Lo3Element leftValue = (Lo3Element) field.get(left);
                    final Lo3Element rightValue = (Lo3Element) field.get(right);

                    if (!Validatie.isElementGevuld(leftValue)) {
                        if (Validatie.isElementGevuld(rightValue)) {
                            verschillenLog.addNieuwElement(elementNummer);
                        }
                    } else {
                        if (!Validatie.isElementGevuld(rightValue)) {
                            verschillenLog.addVerwijderdElement(elementNummer);
                        } else {
                            if (!AbstractLo3Element.equalsWaarde(leftValue, rightValue)) {
                                verschillenLog.addGewijzigdElement(elementNummer);
                            }
                        }
                    }
                } catch (final IllegalAccessException iae) {
                    throw new IllegalArgumentException(iae);
                }
            }
        }
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Lo3Categorie)) {
            return false;
        }
        final Lo3Categorie<?> castOther = (Lo3Categorie<?>) other;
        return new EqualsBuilder().append(inhoud, castOther.inhoud)
                                  .append(historie, castOther.historie)
                                  .append(onderzoek, castOther.onderzoek)
                                  .append(documentatie, castOther.documentatie)
                                  .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(inhoud).append(historie).append(onderzoek).append(documentatie).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                                                                          .append("inhoud", inhoud)
                                                                          .append("historie", historie)
                                                                          .append("onderzoek", onderzoek)
                                                                          .append("documentatie", documentatie)
                                                                          .append("herkomst", lo3Herkomst)
                                                                          .toString();
    }

    /**
     * Sorteert de Lo3Categorie op 85.10 (datum ingang geldigheid) van nieuw naar oud.
     */
    private static class DatumGeldigheidComparator implements Comparator<Lo3Categorie<? extends Lo3CategorieInhoud>>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(final Lo3Categorie<? extends Lo3CategorieInhoud> o1, final Lo3Categorie<? extends Lo3CategorieInhoud> o2) {
            int resultaat;
            final Lo3Historie o1Historie = o1.getHistorie();
            final Lo3Historie o2Historie = o2.getHistorie();

            resultaat = o2Historie.getIngangsdatumGeldigheid().compareTo(o1Historie.getIngangsdatumGeldigheid());

            if (resultaat == 0) {
                // 85.10 is gelijk, nu controleren op 86.10
                resultaat = o2Historie.getDatumVanOpneming().compareTo(o1Historie.getDatumVanOpneming());
            }

            // sorteer op lo3 volgorde, hoger voorkomen nummer betekend ouder, van nieuw naar oud
            if (resultaat == 0) {
                resultaat = o1.getLo3Herkomst().getVoorkomen() - o2.getLo3Herkomst().getVoorkomen();
            }

            return resultaat;

        }

    }
}

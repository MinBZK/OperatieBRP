/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.Preconditie;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3GezagsverhoudingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3KiesrechtInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3KindInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OverlijdenInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3ReisdocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerblijfstitelInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Long;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * This class represents an LO3 persoonslijst.
 *
 * Deze class is immutable en threadsafe.
 *
 */
@Root
@Preconditie({SoortMeldingCode.PRE032, SoortMeldingCode.PRE033, SoortMeldingCode.PRE047 })
public final class Lo3Persoonslijst implements Persoonslijst {

    private final Lo3Stapel<Lo3PersoonInhoud> persoonStapel;
    private final Lo3Stapel<Lo3OuderInhoud> ouder1Stapel;
    private final Lo3Stapel<Lo3OuderInhoud> ouder2Stapel;
    private final List<Lo3Stapel<Lo3NationaliteitInhoud>> nationaliteitStapels;
    private final List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> huwelijkOfGpStapels;
    private final Lo3Stapel<Lo3VerblijfplaatsInhoud> verblijfplaatsStapel;
    private final Lo3Stapel<Lo3OverlijdenInhoud> overlijdenStapel;
    private final List<Lo3Stapel<Lo3KindInhoud>> kindStapels;
    private final Lo3Stapel<Lo3VerblijfstitelInhoud> verblijfstitelStapel;
    private final Lo3Stapel<Lo3GezagsverhoudingInhoud> gezagsverhoudingStapel;
    private final List<Lo3Stapel<Lo3ReisdocumentInhoud>> reisdocumentStapels;
    private final Lo3Stapel<Lo3InschrijvingInhoud> inschrijvingStapel;
    private final Lo3Stapel<Lo3KiesrechtInhoud> kiesrechtStapel;

    private boolean isDummyPL;

    /**
     * Maak een Lo3 persoonslijst.
     *
     * @param persoonStapel
     *            de persoon stapel, mag niet null zijn
     * @param ouder1Stapel
     *            de ouder 1 stapel, mag null zijn
     * @param ouder2Stapel
     *            de ouder 2 stapel, mag null zijn
     * @param nationaliteitStapels
     *            de lijst met nationaliteit stapels, mag null of leeg zijn
     * @param huwelijkOfGpStapels
     *            de lijst met huwelijk of geregistreerd partnerschap stapels, mag null of leeg zijn
     * @param overlijdenStapel
     *            de overlijden stapel, mag null zijn
     * @param inschrijvingStapel
     *            de inschrijving stapel, mag niet null zijn
     * @param verblijfplaatsStapel
     *            de verblijfplaats stapel, mag null zijn
     * @param kindStapels
     *            de lijst met kind stapels, mag null of leeg zijn
     * @param verblijfstitelStapel
     *            de verblijfstitel stapel, mag null zijn
     * @param gezagsverhoudingStapel
     *            de gezagsverhouding stapel, mag null zijn
     * @param reisdocumentStapels
     *            de lijst met reisdocument stapels, mag null of leeg zijn
     * @param kiesrechtStapel
     *            de kiesrecht stapel, mag null zijn
     *
     * @throws NullPointerException
     *             als persoonStapel of inschrijvingStapel null zijn
     * @throws IllegalArgumentException
     *             als een inschrijving, reisdocument of kiesrecht historie heeft, of als een actuele categorie de
     *             indicatie onjuist heeft, of als de datum registratie in enige categorie gedeeltelijk onbekend is
     *
     */
    Lo3Persoonslijst(
        /* Meer dan 7 parameters is in constructors van immutable model klassen getolereerd. */
        @Element(name = "persoonStapel", required = false) final Lo3Stapel<Lo3PersoonInhoud> persoonStapel,
        @Element(name = "ouder1Stapel", required = false) final Lo3Stapel<Lo3OuderInhoud> ouder1Stapel,
        @Element(name = "ouder2Stapel", required = false) final Lo3Stapel<Lo3OuderInhoud> ouder2Stapel,
        @ElementList(name = "nationaliteitStapels", entry = "nationaliteitStapel", type = Lo3Stapel.class, required = false) final List<Lo3Stapel<Lo3NationaliteitInhoud>> nationaliteitStapels,
        @ElementList(name = "huwelijkOfGpStapels", entry = "huwelijkOfGpStapel", type = Lo3Stapel.class, required = false) final List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> huwelijkOfGpStapels,
        @Element(name = "overlijdenStapel", required = false) final Lo3Stapel<Lo3OverlijdenInhoud> overlijdenStapel,
        @Element(name = "inschrijvingStapel", required = false) final Lo3Stapel<Lo3InschrijvingInhoud> inschrijvingStapel,
        @Element(name = "verblijfplaatsStapel", required = false) final Lo3Stapel<Lo3VerblijfplaatsInhoud> verblijfplaatsStapel,
        @ElementList(name = "kindStapels", entry = "kindStapel", type = Lo3Stapel.class, required = false) final List<Lo3Stapel<Lo3KindInhoud>> kindStapels,
        @Element(name = "verblijfstitelStapel", required = false) final Lo3Stapel<Lo3VerblijfstitelInhoud> verblijfstitelStapel,
        @Element(name = "gezagsverhoudingStapel", required = false) final Lo3Stapel<Lo3GezagsverhoudingInhoud> gezagsverhoudingStapel,
        @ElementList(name = "reisdocumentStapels", entry = "reisdocumentStapel", type = Lo3Stapel.class, required = false) final List<Lo3Stapel<Lo3ReisdocumentInhoud>> reisdocumentStapels,
        @Element(name = "kiesrechtStapel", required = false) final Lo3Stapel<Lo3KiesrechtInhoud> kiesrechtStapel)
    {
        this.persoonStapel = persoonStapel;
        this.ouder1Stapel = ouder1Stapel;
        this.ouder2Stapel = ouder2Stapel;
        this.nationaliteitStapels = nationaliteitStapels;
        this.huwelijkOfGpStapels = huwelijkOfGpStapels;
        this.overlijdenStapel = overlijdenStapel;
        this.inschrijvingStapel = inschrijvingStapel;
        this.verblijfplaatsStapel = verblijfplaatsStapel;
        this.kindStapels = kindStapels;
        this.verblijfstitelStapel = verblijfstitelStapel;
        this.gezagsverhoudingStapel = gezagsverhoudingStapel;
        this.reisdocumentStapels = reisdocumentStapels;
        this.kiesrechtStapel = kiesrechtStapel;
    }

    /**
     * Geef de waarde van actueel administratienummer.
     *
     * @return het actuele a-nummer van deze persoonslijst
     */
    @Override
    public Long getActueelAdministratienummer() {
        return persoonStapel == null ? null : Lo3Long.unwrap(persoonStapel.getLaatsteElement().getInhoud().getANummer());
    }

    /**
     * Geef de waarde van persoon stapel.
     *
     * @return de LO3 Persoon categorie stapel van deze persoonslijst.
     */
    @Element(name = "persoonStapel", required = false)
    public Lo3Stapel<Lo3PersoonInhoud> getPersoonStapel() {
        return persoonStapel;
    }

    /**
     * Geef de waarde van ouder1 stapel.
     *
     * @return de LO3 Ouder1 categorie stapel van deze persoonslijst.
     */
    @Element(name = "ouder1Stapel", required = false)
    public Lo3Stapel<Lo3OuderInhoud> getOuder1Stapel() {
        return ouder1Stapel;
    }

    /**
     * Geef de waarde van ouder2 stapel.
     *
     * @return de LO3 Ouder2 categorie stapel van deze persoonslijst.
     */
    @Element(name = "ouder2Stapel", required = false)
    public Lo3Stapel<Lo3OuderInhoud> getOuder2Stapel() {
        return ouder2Stapel;
    }

    /**
     * Geef de waarde van nationaliteit stapels.
     *
     * @return de lijst met nationaliteit stapels voor deze persoonslijst.
     */
    @ElementList(name = "nationaliteitStapels", entry = "nationaliteitStapel", type = Lo3Stapel.class, required = false)
    public List<Lo3Stapel<Lo3NationaliteitInhoud>> getNationaliteitStapels() {
        return nationaliteitStapels;
    }

    /**
     * Geef de waarde van huwelijk of gp stapels.
     *
     * @return de lijst met nationaliteit stapels voor deze persoonslijst.
     */
    @ElementList(name = "huwelijkOfGpStapels", entry = "huwelijkOfGpStapel", type = Lo3Stapel.class, required = false)
    public List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> getHuwelijkOfGpStapels() {
        return huwelijkOfGpStapels;
    }

    /**
     * Geef de waarde van overlijden stapel.
     *
     * @return de LO3 Overlijden categorie stapel van deze persoonslijst, of null.
     */
    @Element(name = "overlijdenStapel", required = false)
    public Lo3Stapel<Lo3OverlijdenInhoud> getOverlijdenStapel() {
        return overlijdenStapel;
    }

    /**
     * Geef de waarde van inschrijving stapel.
     *
     * @return de LO3 Inschrijving categorie stapel van deze persoonslijst
     */
    @Element(name = "inschrijvingStapel", required = false)
    public Lo3Stapel<Lo3InschrijvingInhoud> getInschrijvingStapel() {
        return inschrijvingStapel;
    }

    /**
     * Geef de waarde van verblijfplaats stapel.
     *
     * @return de LO3 Overlijden categorie stapel van deze persoonslijst, of null.
     */
    @Element(name = "verblijfplaatsStapel", required = false)
    public Lo3Stapel<Lo3VerblijfplaatsInhoud> getVerblijfplaatsStapel() {
        return verblijfplaatsStapel;
    }

    /**
     * Geef de waarde van kind stapels.
     *
     * @return de lijst met kind stapels voor deze persoonslijst.
     */
    @ElementList(name = "kindStapels", entry = "kindStapel", type = Lo3Stapel.class, required = false)
    public List<Lo3Stapel<Lo3KindInhoud>> getKindStapels() {
        return kindStapels;
    }

    /**
     * Geef de waarde van verblijfstitel stapel.
     *
     * @return de LO3 Verblijfstitel categorie stapel van deze persoonslijst, of null.
     */
    @Element(name = "verblijfstitelStapel", required = false)
    public Lo3Stapel<Lo3VerblijfstitelInhoud> getVerblijfstitelStapel() {
        return verblijfstitelStapel;
    }

    /**
     * Geef de waarde van gezagsverhouding stapel.
     *
     * @return de LO3 Gezagsverhouding categorie stapel van deze persoonslijst, of null.
     */
    @Element(name = "gezagsverhoudingStapel", required = false)
    public Lo3Stapel<Lo3GezagsverhoudingInhoud> getGezagsverhoudingStapel() {
        return gezagsverhoudingStapel;
    }

    /**
     * Geef de waarde van reisdocument stapels.
     *
     * @return de lijst met reisdocument stapels voor deze persoonslijst.
     */
    @ElementList(name = "reisdocumentStapels", entry = "reisdocumentStapel", type = Lo3Stapel.class, required = false)
    public List<Lo3Stapel<Lo3ReisdocumentInhoud>> getReisdocumentStapels() {
        return reisdocumentStapels;
    }

    /**
     * Geef de waarde van kiesrecht stapel.
     *
     * @return de LO3 Kiesrecht categorie stapel van deze persoonslijst
     */
    @Element(name = "kiesrechtStapel", required = false)
    public Lo3Stapel<Lo3KiesrechtInhoud> getKiesrechtStapel() {
        return kiesrechtStapel;
    }

    /**
     * Geef de dummy pl.
     *
     * @return dummy pl
     */
    public boolean isDummyPl() {
        return isDummyPL;
    }

    /**
     * Zet de waarde van checks if is dummy pl.
     *
     * @param isDummy
     *            checks if is dummy pl
     */
    public void setIsDummyPl(final boolean isDummy) {
        isDummyPL = isDummy;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Lo3Persoonslijst)) {
            return false;
        }
        final Lo3Persoonslijst castOther = (Lo3Persoonslijst) other;
        return new EqualsBuilder().append(persoonStapel, castOther.persoonStapel)
                                  .append(ouder1Stapel, castOther.ouder1Stapel)
                                  .append(ouder2Stapel, castOther.ouder2Stapel)
                                  .append(nationaliteitStapels, castOther.nationaliteitStapels)
                                  .append(huwelijkOfGpStapels, castOther.huwelijkOfGpStapels)
                                  .append(verblijfplaatsStapel, castOther.verblijfplaatsStapel)
                                  .append(overlijdenStapel, castOther.overlijdenStapel)
                                  .append(kindStapels, castOther.kindStapels)
                                  .append(verblijfstitelStapel, castOther.verblijfstitelStapel)
                                  .append(gezagsverhoudingStapel, castOther.gezagsverhoudingStapel)
                                  .append(reisdocumentStapels, castOther.reisdocumentStapels)
                                  .append(inschrijvingStapel, castOther.inschrijvingStapel)
                                  .append(kiesrechtStapel, castOther.kiesrechtStapel)
                                  .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(persoonStapel)
                                    .append(ouder1Stapel)
                                    .append(ouder2Stapel)
                                    .append(nationaliteitStapels)
                                    .append(huwelijkOfGpStapels)
                                    .append(verblijfplaatsStapel)
                                    .append(overlijdenStapel)
                                    .append(kindStapels)
                                    .append(verblijfstitelStapel)
                                    .append(gezagsverhoudingStapel)
                                    .append(reisdocumentStapels)
                                    .append(inschrijvingStapel)
                                    .append(kiesrechtStapel)
                                    .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("persoonStapel", persoonStapel)
                                                                          .append("ouder1Stapel", ouder1Stapel)
                                                                          .append("ouder2Stapel", ouder2Stapel)
                                                                          .append("nationaliteitStapels", nationaliteitStapels)
                                                                          .append("huwelijkOfGpStapels", huwelijkOfGpStapels)
                                                                          .append("verblijfplaatsStapel", verblijfplaatsStapel)
                                                                          .append("overlijdenStapel", overlijdenStapel)
                                                                          .append("kindStapels", kindStapels)
                                                                          .append("verblijfstitelStapel", verblijfstitelStapel)
                                                                          .append("gezagsverhoudingStapel", gezagsverhoudingStapel)
                                                                          .append("reisdocumentStapels", reisdocumentStapels)
                                                                          .append("inschrijvingStapel", inschrijvingStapel)
                                                                          .append("kiesrechtStapel", kiesrechtStapel)
                                                                          .toString();
    }

    /**
     * Geef de groep80 van inschrijving stapel leeg.
     *
     * @return true als groep 80 voor alle voorkomens van de inschrijving stapel leeg zijn
     * @see Lo3InschrijvingInhoud#isGroep80Leeg()
     */
    public boolean isGroep80VanInschrijvingStapelLeeg() {
        for (final Lo3Categorie<Lo3InschrijvingInhoud> inschrijving : getInschrijvingStapel()) {
            if (!inschrijving.getInhoud().isGroep80Leeg()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Maakt een kopie van deze persoonslijst waarbij groep 80 van de inschrijving stapel is gevuld met default waarden.
     *
     * @return een kopie van deze lo3persoonslijst met een default groep 80 voor de inschrijving stapel
     * @throws NullPointerException
     *             als {@link #getInschrijvingStapel()} null is
     * @throws IllegalStateException
     *             groep 80 van de inschrijving
     */
    public Lo3Persoonslijst maakKopieMetDefaultGroep80VoorInschrijvingStapel() {
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder(this);
        final List<Lo3Categorie<Lo3InschrijvingInhoud>> inschrijvingVoorkomens = new ArrayList<>();
        for (final Iterator<Lo3Categorie<Lo3InschrijvingInhoud>> inschrijvingIter = getInschrijvingStapel().iterator(); inschrijvingIter.hasNext();) {
            final Lo3Categorie<Lo3InschrijvingInhoud> voorkomen = inschrijvingIter.next();
            inschrijvingVoorkomens.add(new Lo3Categorie<>(
                voorkomen.getInhoud().maakKopieMetDefaultGroep80(),
                voorkomen.getDocumentatie(),
                voorkomen.getOnderzoek(),
                voorkomen.getHistorie(),
                voorkomen.getLo3Herkomst()));
        }
        builder.inschrijvingStapel(new Lo3Stapel<>(inschrijvingVoorkomens));
        return builder.build();
    }

    /**
     * Geeft aan of de gemeente van inschrijving van de Lo3Persoonslijst gevuld is of niet.
     *
     * @return boolean, wel of niet gevuld.
     */
    public boolean isGemeenteVanInschrijvingGevuld() {
        boolean gevuld = false;

        if (getVerblijfplaatsStapel() != null
            && getVerblijfplaatsStapel().getLaatsteElement() != null
            && getVerblijfplaatsStapel().getLaatsteElement().getInhoud() != null
            && getVerblijfplaatsStapel().getLaatsteElement().getInhoud().getGemeenteInschrijving() != null)
        {
            gevuld = true;
        }
        return gevuld;
    }

}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.lo3;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nl.moderniseringgba.migratie.Preconditie;
import nl.moderniseringgba.migratie.Precondities;
import nl.moderniseringgba.migratie.conversie.model.Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3GezagsverhoudingInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3KiesrechtInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3KindInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3OverlijdenInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3ReisdocumentInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3VerblijfstitelInhoud;

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
@Preconditie({ Precondities.PRE032, Precondities.PRE033, Precondities.PRE047 })
public final class Lo3Persoonslijst implements Persoonslijst {

    private final Lo3Stapel<Lo3PersoonInhoud> persoonStapel;
    private final List<Lo3Stapel<Lo3OuderInhoud>> ouder1Stapels;
    private final List<Lo3Stapel<Lo3OuderInhoud>> ouder2Stapels;
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

    /**
     * Maak een Lo3 persoonslijst.
     * 
     * @param persoonStapel
     *            de persoon stapel, mag niet null zijn
     * @param ouder1Stapels
     *            de ouder 1 stapels, mag null zijn
     * @param ouder2Stapels
     *            de ouder 2 stapels, mag null zijn
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
    // CHECKSTYLE:OFF - Meer dan 7 parameters is in constructors van immutable model klassen getolereerd.
    Lo3Persoonslijst(
            @Element(name = "persoonStapel", required = false) final Lo3Stapel<Lo3PersoonInhoud> persoonStapel,
            @ElementList(name = "ouder1Stapels", entry = "ouder1Stapels", type = Lo3Stapel.class, required = false) final List<Lo3Stapel<Lo3OuderInhoud>> ouder1Stapels,
            @ElementList(name = "ouder2Stapels", entry = "ouder2Stapels", type = Lo3Stapel.class, required = false) final List<Lo3Stapel<Lo3OuderInhoud>> ouder2Stapels,
            @ElementList(name = "nationaliteitStapels", entry = "nationaliteitStapel", type = Lo3Stapel.class,
                    required = false) final List<Lo3Stapel<Lo3NationaliteitInhoud>> nationaliteitStapels,
            @ElementList(name = "huwelijkOfGpStapels", entry = "huwelijkOfGpStapel", type = Lo3Stapel.class,
                    required = false) final List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> huwelijkOfGpStapels,
            @Element(name = "overlijdenStapel", required = false) final Lo3Stapel<Lo3OverlijdenInhoud> overlijdenStapel,
            @Element(name = "inschrijvingStapel", required = false) final Lo3Stapel<Lo3InschrijvingInhoud> inschrijvingStapel,
            @Element(name = "verblijfplaatsStapel", required = false) final Lo3Stapel<Lo3VerblijfplaatsInhoud> verblijfplaatsStapel,
            @ElementList(name = "kindStapels", entry = "kindStapel", type = Lo3Stapel.class, required = false) final List<Lo3Stapel<Lo3KindInhoud>> kindStapels,
            @Element(name = "verblijfstitelStapel", required = false) final Lo3Stapel<Lo3VerblijfstitelInhoud> verblijfstitelStapel,
            @Element(name = "gezagsverhoudingStapel", required = false) final Lo3Stapel<Lo3GezagsverhoudingInhoud> gezagsverhoudingStapel,
            @ElementList(name = "reisdocumentStapels", entry = "reisdocumentStapel", type = Lo3Stapel.class,
                    required = false) final List<Lo3Stapel<Lo3ReisdocumentInhoud>> reisdocumentStapels,
            @Element(name = "kiesrechtStapel", required = false) final Lo3Stapel<Lo3KiesrechtInhoud> kiesrechtStapel) {
        // CHECKSTYLE:ON
        this.persoonStapel = persoonStapel;
        this.ouder1Stapels = ouder1Stapels;
        this.ouder2Stapels = ouder2Stapels;
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

        // if (this.persoonStapel == null) {
        // FoutmeldingUtil.gooiVerplichtMaarNietGevuldFoutmelding("persoon", Precondities.PRE047);
        // }
    }

    // /**
    // * Valideer het model.
    // */
    // // CHECKSTYLE:OFF - Executable statement count - niet complex, gewoon een lijst
    // @Preconditie({ Precondities.PRE065, Precondities.PRE066 })
    // public void valideer() {
    // // CHECKSTYLE:ON
    //
    // if (inschrijvingStapel == null) {
    // FoutmeldingUtil.gooiVerplichtMaarNietGevuldFoutmelding("Categorie 07: Inschrijving", Precondities.PRE032);
    // }
    //
    // if (ouder1Stapels.size() > 1) {
    // throw new IllegalArgumentException("Ouder 1 stapel lijst mag maximaal 1 stapel bevatten.");
    // }
    //
    // if (ouder2Stapels.size() > 1) {
    // throw new IllegalArgumentException("Ouder 2 stapel lijst mag maximaal 1 stapel bevatten.");
    // }
    //
    // Lo3PersoonslijstValidator.checkGeenHistorie(inschrijvingStapel);
    // Lo3PersoonslijstValidator.checkGeenHistorie(reisdocumentStapels);
    // Lo3PersoonslijstValidator.checkGeenHistorie(kiesrechtStapel);
    //
    // // PRE051 en PRE052
    // Lo3PersoonslijstValidator.valideerNationaliteitStapels(nationaliteitStapels);
    //
    // // PRE065
    // Lo3PersoonslijstValidator.checkNietLeeg(ouder1Stapels, "ouder1", Precondities.PRE065);
    //
    // // PRE066
    // Lo3PersoonslijstValidator.checkNietLeeg(ouder2Stapels, "ouder2", Precondities.PRE066);
    //
    // // PRE033
    // if (verblijfplaatsStapel == null) {
    // FoutmeldingUtil
    // .gooiVerplichtMaarNietGevuldFoutmelding("Categorie 8 Verblijfsplaats", Precondities.PRE033);
    // }
    //
    // Lo3PersoonslijstValidator.checkHistorie(persoonStapel);
    // Lo3PersoonslijstValidator.checkHistorie(ouder1Stapels);
    // Lo3PersoonslijstValidator.checkHistorie(ouder2Stapels);
    // Lo3PersoonslijstValidator.checkHistorie(nationaliteitStapels);
    // Lo3PersoonslijstValidator.checkHistorie(huwelijkOfGpStapels);
    // Lo3PersoonslijstValidator.checkHistorie(overlijdenStapel);
    // Lo3PersoonslijstValidator.checkHistorie(inschrijvingStapel);
    // Lo3PersoonslijstValidator.checkHistorie(verblijfplaatsStapel);
    // Lo3PersoonslijstValidator.checkHistorie(kindStapels);
    // Lo3PersoonslijstValidator.checkHistorie(verblijfstitelStapel);
    // Lo3PersoonslijstValidator.checkHistorie(gezagsverhoudingStapel);
    // Lo3PersoonslijstValidator.checkHistorie(reisdocumentStapels);
    // Lo3PersoonslijstValidator.checkHistorie(kiesrechtStapel);
    //
    // // PRE056
    // Lo3PersoonslijstValidator.valideerLegeRijenMetEerdereOnjuisteRij(huwelijkOfGpStapels);
    // Lo3PersoonslijstValidator.valideerLegeRijenMetEerdereOnjuisteRij(overlijdenStapel);
    //
    // Lo3PersoonslijstValidator.valideerInhoud(persoonStapel);
    // Lo3PersoonslijstValidator.valideerInhoud(ouder1Stapels);
    // Lo3PersoonslijstValidator.valideerInhoud(ouder2Stapels);
    // Lo3PersoonslijstValidator.valideerInhoud(nationaliteitStapels);
    // Lo3PersoonslijstValidator.valideerInhoud(huwelijkOfGpStapels);
    // Lo3PersoonslijstValidator.valideerInhoud(overlijdenStapel);
    // Lo3PersoonslijstValidator.valideerInhoud(inschrijvingStapel);
    // Lo3PersoonslijstValidator.valideerInhoud(verblijfplaatsStapel);
    // Lo3PersoonslijstValidator.valideerInhoud(kindStapels);
    // Lo3PersoonslijstValidator.valideerInhoud(verblijfstitelStapel);
    // Lo3PersoonslijstValidator.valideerInhoud(gezagsverhoudingStapel);
    // Lo3PersoonslijstValidator.valideerInhoud(reisdocumentStapels);
    // Lo3PersoonslijstValidator.valideerInhoud(kiesrechtStapel);
    //
    // }

    /**
     * @return het actuele a-nummer van deze persoonslijst
     */
    @Override
    public Long getActueelAdministratienummer() {
        return persoonStapel == null ? null : persoonStapel.getMeestRecenteElement().getInhoud().getaNummer();
    }

    /**
     * @return het actuele a-nummer van deze persoonslijst
     */
    @Override
    public BigDecimal getActueelAdministratienummerAsBigDecimal() {
        return getActueelAdministratienummer() == null ? null : BigDecimal.valueOf(getActueelAdministratienummer());
    }

    /**
     * @return de LO3 Persoon categorie stapel van deze persoonslijst.
     */
    @Element(name = "persoonStapel", required = false)
    public Lo3Stapel<Lo3PersoonInhoud> getPersoonStapel() {
        return persoonStapel;
    }

    /**
     * @return de LO3 Ouder1 categorie stapel van deze persoonslijst.
     */
    @ElementList(name = "ouder1Stapels", entry = "ouder1Stapel", type = Lo3Stapel.class, required = false)
    public List<Lo3Stapel<Lo3OuderInhoud>> getOuder1Stapels() {
        return ouder1Stapels;
    }

    /**
     * @return de LO3 Ouder2 categorie stapel van deze persoonslijst.
     */
    @ElementList(name = "ouder2Stapels", entry = "ouder2Stapel", type = Lo3Stapel.class, required = false)
    public List<Lo3Stapel<Lo3OuderInhoud>> getOuder2Stapels() {
        return ouder2Stapels;
    }

    /**
     * @return de lijst met nationaliteit stapels voor deze persoonslijst.
     */
    @ElementList(name = "nationaliteitStapels", entry = "nationaliteitStapel", type = Lo3Stapel.class,
            required = false)
    public List<Lo3Stapel<Lo3NationaliteitInhoud>> getNationaliteitStapels() {
        return nationaliteitStapels;
    }

    /**
     * @return de lijst met nationaliteit stapels voor deze persoonslijst.
     */
    @ElementList(name = "huwelijkOfGpStapels", entry = "huwelijkOfGpStapel", type = Lo3Stapel.class, required = false)
    public
            List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> getHuwelijkOfGpStapels() {
        return huwelijkOfGpStapels;
    }

    /**
     * @return de LO3 Overlijden categorie stapel van deze persoonslijst, of null.
     */
    @Element(name = "overlijdenStapel", required = false)
    public Lo3Stapel<Lo3OverlijdenInhoud> getOverlijdenStapel() {
        return overlijdenStapel;
    }

    /**
     * @return de LO3 Inschrijving categorie stapel van deze persoonslijst
     */
    @Element(name = "inschrijvingStapel", required = false)
    public Lo3Stapel<Lo3InschrijvingInhoud> getInschrijvingStapel() {
        return inschrijvingStapel;
    }

    /**
     * @return de LO3 Overlijden categorie stapel van deze persoonslijst, of null.
     */
    @Element(name = "verblijfplaatsStapel", required = false)
    public Lo3Stapel<Lo3VerblijfplaatsInhoud> getVerblijfplaatsStapel() {
        return verblijfplaatsStapel;
    }

    /**
     * @return de lijst met kind stapels voor deze persoonslijst.
     */
    @ElementList(name = "kindStapels", entry = "kindStapel", type = Lo3Stapel.class, required = false)
    public List<Lo3Stapel<Lo3KindInhoud>> getKindStapels() {
        return kindStapels;
    }

    /**
     * @return de LO3 Verblijfstitel categorie stapel van deze persoonslijst, of null.
     */
    @Element(name = "verblijfstitelStapel", required = false)
    public Lo3Stapel<Lo3VerblijfstitelInhoud> getVerblijfstitelStapel() {
        return verblijfstitelStapel;
    }

    /**
     * @return de LO3 Gezagsverhouding categorie stapel van deze persoonslijst, of null.
     */
    @Element(name = "gezagsverhoudingStapel", required = false)
    public Lo3Stapel<Lo3GezagsverhoudingInhoud> getGezagsverhoudingStapel() {
        return gezagsverhoudingStapel;
    }

    /**
     * @return de lijst met reisdocument stapels voor deze persoonslijst.
     */
    @ElementList(name = "reisdocumentStapels", entry = "reisdocumentStapel", type = Lo3Stapel.class, required = false)
    public
            List<Lo3Stapel<Lo3ReisdocumentInhoud>> getReisdocumentStapels() {
        return reisdocumentStapels;
    }

    /**
     * @return de LO3 Kiesrecht categorie stapel van deze persoonslijst
     */
    @Element(name = "kiesrechtStapel", required = false)
    public Lo3Stapel<Lo3KiesrechtInhoud> getKiesrechtStapel() {
        return kiesrechtStapel;
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
                .append(ouder1Stapels, castOther.ouder1Stapels).append(ouder2Stapels, castOther.ouder2Stapels)
                .append(nationaliteitStapels, castOther.nationaliteitStapels)
                .append(huwelijkOfGpStapels, castOther.huwelijkOfGpStapels)
                .append(verblijfplaatsStapel, castOther.verblijfplaatsStapel)
                .append(overlijdenStapel, castOther.overlijdenStapel).append(kindStapels, castOther.kindStapels)
                .append(verblijfstitelStapel, castOther.verblijfstitelStapel)
                .append(gezagsverhoudingStapel, castOther.gezagsverhoudingStapel)
                .append(reisdocumentStapels, castOther.reisdocumentStapels)
                .append(inschrijvingStapel, castOther.inschrijvingStapel)
                .append(kiesrechtStapel, castOther.kiesrechtStapel).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(persoonStapel).append(ouder1Stapels).append(ouder2Stapels)
                .append(nationaliteitStapels).append(huwelijkOfGpStapels).append(verblijfplaatsStapel)
                .append(overlijdenStapel).append(kindStapels).append(verblijfstitelStapel)
                .append(gezagsverhoudingStapel).append(reisdocumentStapels).append(inschrijvingStapel)
                .append(kiesrechtStapel).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("persoonStapel", persoonStapel)
                .append("ouder1Stapel", ouder1Stapels).append("ouder2Stapel", ouder2Stapels)
                .append("nationaliteitStapels", nationaliteitStapels)
                .append("huwelijkOfGpStapels", huwelijkOfGpStapels)
                .append("verblijfplaatsStapel", verblijfplaatsStapel).append("overlijdenStapel", overlijdenStapel)
                .append("kindStapels", kindStapels).append("verblijfstitelStapel", verblijfstitelStapel)
                .append("gezagsverhoudingStapel", gezagsverhoudingStapel)
                .append("reisdocumentStapels", reisdocumentStapels).append("inschrijvingStapel", inschrijvingStapel)
                .append("kiesrechtStapel", kiesrechtStapel).toString();
    }

    /**
     * @return true als groep 80 voor alle voorkomens van de inschrijving stapel leeg zijn
     * @throws NullPointerException
     *             als {@link #getInschrijvingStapel()} null is
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
        final List<Lo3Categorie<Lo3InschrijvingInhoud>> inschrijvingVoorkomens =
                new ArrayList<Lo3Categorie<Lo3InschrijvingInhoud>>();
        for (final Iterator<Lo3Categorie<Lo3InschrijvingInhoud>> inschrijvingIter =
                getInschrijvingStapel().iterator(); inschrijvingIter.hasNext();) {
            final Lo3Categorie<Lo3InschrijvingInhoud> voorkomen = inschrijvingIter.next();
            inschrijvingVoorkomens.add(new Lo3Categorie<Lo3InschrijvingInhoud>(voorkomen.getInhoud()
                    .maakKopieMetDefaultGroep80(), voorkomen.getDocumentatie(), voorkomen.getHistorie(), voorkomen
                    .getLo3Herkomst()));
        }
        builder.inschrijvingStapel(new Lo3Stapel<Lo3InschrijvingInhoud>(inschrijvingVoorkomens));
        return builder.build();
    }

}

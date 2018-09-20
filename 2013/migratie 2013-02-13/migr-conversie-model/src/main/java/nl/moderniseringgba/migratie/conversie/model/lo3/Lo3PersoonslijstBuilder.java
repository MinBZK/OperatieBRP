/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.lo3;

import java.util.ArrayList;
import java.util.List;

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

/**
 * Deze class helpt bij het maken van een Lo3Persoonslijst. Argumenten kunnen via method-chaining worden toegevoegd.
 * 
 */
public final class Lo3PersoonslijstBuilder {

    private Lo3Stapel<Lo3PersoonInhoud> persoonStapel;
    private final List<Lo3Stapel<Lo3OuderInhoud>> ouder1Stapels = new ArrayList<Lo3Stapel<Lo3OuderInhoud>>();
    private final List<Lo3Stapel<Lo3OuderInhoud>> ouder2Stapels = new ArrayList<Lo3Stapel<Lo3OuderInhoud>>();
    private final List<Lo3Stapel<Lo3NationaliteitInhoud>> nationaliteitStapels =
            new ArrayList<Lo3Stapel<Lo3NationaliteitInhoud>>();
    private final List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> huwelijkOfGpStapels =
            new ArrayList<Lo3Stapel<Lo3HuwelijkOfGpInhoud>>();
    private Lo3Stapel<Lo3OverlijdenInhoud> overlijdenStapel;
    private Lo3Stapel<Lo3InschrijvingInhoud> inschrijvingStapel;
    private Lo3Stapel<Lo3VerblijfplaatsInhoud> verblijfplaatsStapel;
    private final List<Lo3Stapel<Lo3KindInhoud>> kindStapels = new ArrayList<Lo3Stapel<Lo3KindInhoud>>();
    private Lo3Stapel<Lo3VerblijfstitelInhoud> verblijfstitelStapel;
    private Lo3Stapel<Lo3GezagsverhoudingInhoud> gezagsverhoudingStapel;
    private final List<Lo3Stapel<Lo3ReisdocumentInhoud>> reisdocumentStapels =
            new ArrayList<Lo3Stapel<Lo3ReisdocumentInhoud>>();
    private Lo3Stapel<Lo3KiesrechtInhoud> kiesrechtStapel;

    /** Maak een lege builder. */
    public Lo3PersoonslijstBuilder() {
    }

    /**
     * Maak een initieel gevulde builder.
     * 
     * @param persoonslijst
     *            initiele inhoud
     */
    public Lo3PersoonslijstBuilder(final Lo3Persoonslijst persoonslijst) {
        this.persoonStapel = persoonslijst.getPersoonStapel();
        this.ouder1Stapels.addAll(persoonslijst.getOuder1Stapels());
        this.ouder2Stapels.addAll(persoonslijst.getOuder2Stapels());
        this.nationaliteitStapels.addAll(persoonslijst.getNationaliteitStapels());
        this.huwelijkOfGpStapels.addAll(persoonslijst.getHuwelijkOfGpStapels());
        this.overlijdenStapel = persoonslijst.getOverlijdenStapel();
        this.inschrijvingStapel = persoonslijst.getInschrijvingStapel();
        this.verblijfplaatsStapel = persoonslijst.getVerblijfplaatsStapel();
        this.kindStapels.addAll(persoonslijst.getKindStapels());
        this.verblijfstitelStapel = persoonslijst.getVerblijfstitelStapel();
        this.gezagsverhoudingStapel = persoonslijst.getGezagsverhoudingStapel();
        this.reisdocumentStapels.addAll(persoonslijst.getReisdocumentStapels());
        this.kiesrechtStapel = persoonslijst.getKiesrechtStapel();
    }

    /**
     * Zet de persoon stapel.
     * 
     * @param persoonStapel
     *            de persoon stapel
     * @return het Lo3PersoonslijstBuilder object
     */
    public Lo3PersoonslijstBuilder persoonStapel(final Lo3Stapel<Lo3PersoonInhoud> persoonStapel) {
        this.persoonStapel = persoonStapel;
        return this;
    }

    /**
     * Zet de ouder1 stapels.
     * 
     * @param ouder1Stapels
     *            lijst van de ouder1 stapel
     * @return het Lo3PersoonslijstBuilder object
     */
    public Lo3PersoonslijstBuilder ouder1Stapels(final List<Lo3Stapel<Lo3OuderInhoud>> ouder1Stapels) {
        this.ouder1Stapels.clear();
        if (ouder1Stapels != null) {
            this.ouder1Stapels.addAll(ouder1Stapels);
        }
        return this;
    }

    /**
     * Voeg een ouder1 stapel toe.
     * 
     * @param ouder1Stapel
     *            de ouder1 stapel
     * @return het Lo3PersoonslijstBuilder object
     */
    public Lo3PersoonslijstBuilder ouder1Stapel(final Lo3Stapel<Lo3OuderInhoud> ouder1Stapel) {
        if (ouder1Stapel != null) {
            this.ouder1Stapels.add(ouder1Stapel);
        }
        return this;
    }

    /**
     * Zet de ouder2 stapels.
     * 
     * @param ouder2Stapels
     *            lijst van de ouder2 stapel
     * @return het Lo3PersoonslijstBuilder object
     */
    public Lo3PersoonslijstBuilder ouder2Stapels(final List<Lo3Stapel<Lo3OuderInhoud>> ouder2Stapels) {
        this.ouder2Stapels.clear();
        if (ouder2Stapels != null) {
            this.ouder2Stapels.addAll(ouder2Stapels);
        }
        return this;
    }

    /**
     * Voeg een ouder2 stapel toe.
     * 
     * @param ouder2Stapel
     *            de ouder2 stapel
     * @return het Lo3PersoonslijstBuilder object
     */
    public Lo3PersoonslijstBuilder ouder2Stapel(final Lo3Stapel<Lo3OuderInhoud> ouder2Stapel) {
        if (ouder2Stapel != null) {
            this.ouder2Stapels.add(ouder2Stapel);
        }
        return this;
    }

    /**
     * Zet de lijst met nationaliteit stapels.
     * 
     * @param nationaliteitStapels
     *            de nationaliteit stapels, mag null zijn
     * @return het Lo3PersoonslijstBuilder object
     * 
     */
    public Lo3PersoonslijstBuilder nationaliteitStapels(
            final List<Lo3Stapel<Lo3NationaliteitInhoud>> nationaliteitStapels) {
        this.nationaliteitStapels.clear();
        if (nationaliteitStapels != null) {
            for (final Lo3Stapel<Lo3NationaliteitInhoud> nationaliteitStapel : nationaliteitStapels) {
                if (nationaliteitStapel != null) {
                    this.nationaliteitStapels.add(nationaliteitStapel);
                }
            }
        }
        return this;
    }

    /**
     * Voegt een nationaliteit stapel toe.
     * 
     * @param nationaliteitStapel
     *            de nationaliteit stapel
     * @return het Lo3PersoonslijstBuilder object
     */
    public Lo3PersoonslijstBuilder nationaliteitStapel(final Lo3Stapel<Lo3NationaliteitInhoud> nationaliteitStapel) {
        if (nationaliteitStapel != null) {
            this.nationaliteitStapels.add(nationaliteitStapel);
        }
        return this;
    }

    /**
     * Ze de lijst met huwelijk of gp stapels.
     * 
     * @param huwelijkOfGpStapels
     *            de huwelijk of gp stapels, mag null zijn
     * @return het Lo3PersoonslijstBuilder object
     * 
     */
    public Lo3PersoonslijstBuilder huwelijkOfGpStapels(
            final List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> huwelijkOfGpStapels) {
        this.huwelijkOfGpStapels.clear();
        if (huwelijkOfGpStapels != null) {
            for (final Lo3Stapel<Lo3HuwelijkOfGpInhoud> huwelijkOfGpStapel : huwelijkOfGpStapels) {
                if (huwelijkOfGpStapel != null) {
                    this.huwelijkOfGpStapels.add(huwelijkOfGpStapel);
                }
            }
        }
        return this;
    }

    /**
     * Voegt een huwelijk of gp stapel toe.
     * 
     * @param huwelijkOfGpStapel
     *            de huwelijk of gp stapel
     * @return het Lo3PersoonslijstBuilder object
     */
    public Lo3PersoonslijstBuilder huwelijkOfGpStapel(final Lo3Stapel<Lo3HuwelijkOfGpInhoud> huwelijkOfGpStapel) {
        if (huwelijkOfGpStapel != null) {
            this.huwelijkOfGpStapels.add(huwelijkOfGpStapel);
        }
        return this;
    }

    /**
     * Zet de overlijden stapel.
     * 
     * @param overlijdenStapel
     *            de overlijden stapel
     * @return het Lo3PersoonslijstBuilder object
     */
    public Lo3PersoonslijstBuilder overlijdenStapel(final Lo3Stapel<Lo3OverlijdenInhoud> overlijdenStapel) {
        this.overlijdenStapel = overlijdenStapel;
        return this;
    }

    /**
     * Zet de inschrijving stapel.
     * 
     * @param inschrijvingStapel
     *            de inschrijving stapel
     * @return het Lo3PersoonslijstBuilder object
     */
    public Lo3PersoonslijstBuilder inschrijvingStapel(final Lo3Stapel<Lo3InschrijvingInhoud> inschrijvingStapel) {
        this.inschrijvingStapel = inschrijvingStapel;
        return this;
    }

    /**
     * Zet de verblijfplaats stapel.
     * 
     * @param verblijfplaatsStapel
     *            de verblijfplaats stapel
     * @return het Lo3PersoonslijstBuilder object
     */
    public Lo3PersoonslijstBuilder
            verblijfplaatsStapel(final Lo3Stapel<Lo3VerblijfplaatsInhoud> verblijfplaatsStapel) {
        this.verblijfplaatsStapel = verblijfplaatsStapel;
        return this;
    }

    /**
     * Zet de lijst met kind stapels.
     * 
     * @param kindStapels
     *            de kind stapels, mag null zijn
     * @return het Lo3PersoonslijstBuilder object
     * 
     */
    public Lo3PersoonslijstBuilder kindStapels(final List<Lo3Stapel<Lo3KindInhoud>> kindStapels) {
        this.kindStapels.clear();
        if (kindStapels != null) {
            for (final Lo3Stapel<Lo3KindInhoud> kindStapel : kindStapels) {
                if (kindStapel != null) {
                    this.kindStapels.add(kindStapel);
                }
            }
        }
        return this;
    }

    /**
     * Voegt een kind stapel toe.
     * 
     * @param kindStapel
     *            de kind stapel
     * @return het Lo3PersoonslijstBuilder object
     */
    public Lo3PersoonslijstBuilder kindStapel(final Lo3Stapel<Lo3KindInhoud> kindStapel) {
        if (kindStapel != null) {
            this.kindStapels.add(kindStapel);
        }
        return this;
    }

    /**
     * Zet de verblijfstitel stapel.
     * 
     * @param verblijfstitelStapel
     *            de verblijfstitel stapel
     * @return het Lo3PersoonslijstBuilder object
     */
    public Lo3PersoonslijstBuilder
            verblijfstitelStapel(final Lo3Stapel<Lo3VerblijfstitelInhoud> verblijfstitelStapel) {
        this.verblijfstitelStapel = verblijfstitelStapel;
        return this;
    }

    /**
     * Zet de gezagsverhouding stapel.
     * 
     * @param gezagsverhoudingStapel
     *            de gezagsverhouding stapel
     * @return het Lo3PersoonslijstBuilder object
     */
    public Lo3PersoonslijstBuilder gezagsverhoudingStapel(
            final Lo3Stapel<Lo3GezagsverhoudingInhoud> gezagsverhoudingStapel) {
        this.gezagsverhoudingStapel = gezagsverhoudingStapel;
        return this;
    }

    /**
     * Zet de lijst met reisdocument stapels.
     * 
     * @param reisdocumentStapels
     *            de lijst met reisdocument stapel, mag null zijn
     * @return het Lo3PersoonslijstBuilder object
     * 
     */
    public Lo3PersoonslijstBuilder reisdocumentStapels(
            final List<Lo3Stapel<Lo3ReisdocumentInhoud>> reisdocumentStapels) {
        this.reisdocumentStapels.clear();
        if (reisdocumentStapels != null) {
            for (final Lo3Stapel<Lo3ReisdocumentInhoud> reisdocumentStapel : reisdocumentStapels) {
                if (reisdocumentStapel != null) {
                    this.reisdocumentStapels.add(reisdocumentStapel);
                }
            }
        }
        return this;
    }

    /**
     * Voegt een reisdocument stapel toe.
     * 
     * @param reisdocumentStapel
     *            de reisdocument stapel, mag null zijn
     * @return het Lo3PersoonslijstBuilder object
     * 
     */
    public Lo3PersoonslijstBuilder reisdocumentStapel(final Lo3Stapel<Lo3ReisdocumentInhoud> reisdocumentStapel) {
        if (reisdocumentStapel != null) {
            this.reisdocumentStapels.add(reisdocumentStapel);
        }
        return this;
    }

    /**
     * Zet de kiesrecht stapel.
     * 
     * @param kiesrechtStapel
     *            de kiesrecht stapel
     * @return het Lo3PersoonslijstBuilder object
     */
    public Lo3PersoonslijstBuilder kiesrechtStapel(final Lo3Stapel<Lo3KiesrechtInhoud> kiesrechtStapel) {
        this.kiesrechtStapel = kiesrechtStapel;
        return this;
    }

    /**
     * @return een nieuw Lo3Persoonslijst object.
     */
    public Lo3Persoonslijst build() {
        return new Lo3Persoonslijst(persoonStapel, ouder1Stapels, ouder2Stapels, nationaliteitStapels,
                huwelijkOfGpStapels, overlijdenStapel, inschrijvingStapel, verblijfplaatsStapel, kindStapels,
                verblijfstitelStapel, gezagsverhoudingStapel, reisdocumentStapels, kiesrechtStapel);
    }

}

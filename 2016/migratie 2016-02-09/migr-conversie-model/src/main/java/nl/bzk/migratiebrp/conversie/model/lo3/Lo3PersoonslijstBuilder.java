/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3;

import java.util.ArrayList;
import java.util.List;
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

/**
 * Deze class helpt bij het maken van een Lo3Persoonslijst. Argumenten kunnen via method-chaining worden toegevoegd.
 * 
 */
public final class Lo3PersoonslijstBuilder {

    private Lo3Stapel<Lo3PersoonInhoud> persoonStapel;
    private Lo3Stapel<Lo3OuderInhoud> ouder1Stapel;
    private Lo3Stapel<Lo3OuderInhoud> ouder2Stapel;
    private final List<Lo3Stapel<Lo3NationaliteitInhoud>> nationaliteitStapels = new ArrayList<>();
    private final List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> huwelijkOfGpStapels = new ArrayList<>();
    private Lo3Stapel<Lo3OverlijdenInhoud> overlijdenStapel;
    private Lo3Stapel<Lo3InschrijvingInhoud> inschrijvingStapel;
    private Lo3Stapel<Lo3VerblijfplaatsInhoud> verblijfplaatsStapel;
    private final List<Lo3Stapel<Lo3KindInhoud>> kindStapels = new ArrayList<>();
    private Lo3Stapel<Lo3VerblijfstitelInhoud> verblijfstitelStapel;
    private Lo3Stapel<Lo3GezagsverhoudingInhoud> gezagsverhoudingStapel;
    private final List<Lo3Stapel<Lo3ReisdocumentInhoud>> reisdocumentStapels = new ArrayList<>();
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
        persoonStapel = persoonslijst.getPersoonStapel();
        ouder1Stapel = persoonslijst.getOuder1Stapel();
        ouder2Stapel = persoonslijst.getOuder2Stapel();
        nationaliteitStapels.addAll(persoonslijst.getNationaliteitStapels());
        huwelijkOfGpStapels.addAll(persoonslijst.getHuwelijkOfGpStapels());
        overlijdenStapel = persoonslijst.getOverlijdenStapel();
        inschrijvingStapel = persoonslijst.getInschrijvingStapel();
        verblijfplaatsStapel = persoonslijst.getVerblijfplaatsStapel();
        kindStapels.addAll(persoonslijst.getKindStapels());
        verblijfstitelStapel = persoonslijst.getVerblijfstitelStapel();
        gezagsverhoudingStapel = persoonslijst.getGezagsverhoudingStapel();
        reisdocumentStapels.addAll(persoonslijst.getReisdocumentStapels());
        kiesrechtStapel = persoonslijst.getKiesrechtStapel();
    }

    /**
     * Zet de persoon stapel.
     * 
     * @param param
     *            de persoon stapel
     * @return het Lo3PersoonslijstBuilder object
     */
    public Lo3PersoonslijstBuilder persoonStapel(final Lo3Stapel<Lo3PersoonInhoud> param) {
        persoonStapel = param;
        return this;
    }

    /**
     * Zet de ouder1 stapels.
     * 
     * @param param
     *            de ouder1 stapel
     * @return het Lo3PersoonslijstBuilder object
     */
    public Lo3PersoonslijstBuilder ouder1Stapel(final Lo3Stapel<Lo3OuderInhoud> param) {
        ouder1Stapel = param;
        return this;
    }

    /**
     * Zet de ouder2 stapels.
     * 
     * @param param
     *            de ouder2 stapel
     * @return het Lo3PersoonslijstBuilder object
     */
    public Lo3PersoonslijstBuilder ouder2Stapel(final Lo3Stapel<Lo3OuderInhoud> param) {
        ouder2Stapel = param;
        return this;
    }

    /**
     * Zet de lijst met nationaliteit stapels.
     * 
     * @param param
     *            de nationaliteit stapels, mag null zijn
     * @return het Lo3PersoonslijstBuilder object
     * 
     */
    public Lo3PersoonslijstBuilder nationaliteitStapels(final List<Lo3Stapel<Lo3NationaliteitInhoud>> param) {
        nationaliteitStapels.clear();
        if (param != null) {
            for (final Lo3Stapel<Lo3NationaliteitInhoud> nationaliteitStapel : param) {
                if (nationaliteitStapel != null) {
                    nationaliteitStapels.add(nationaliteitStapel);
                }
            }
        }
        return this;
    }

    /**
     * Voegt een nationaliteit stapel toe.
     * 
     * @param param
     *            de nationaliteit stapel
     * @return het Lo3PersoonslijstBuilder object
     */
    public Lo3PersoonslijstBuilder nationaliteitStapel(final Lo3Stapel<Lo3NationaliteitInhoud> param) {
        if (param != null) {
            nationaliteitStapels.add(param);
        }
        return this;
    }

    /**
     * Ze de lijst met huwelijk of gp stapels.
     * 
     * @param param
     *            de huwelijk of gp stapels, mag null zijn
     * @return het Lo3PersoonslijstBuilder object
     * 
     */
    public Lo3PersoonslijstBuilder huwelijkOfGpStapels(final List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> param) {
        huwelijkOfGpStapels.clear();
        if (param != null) {
            for (final Lo3Stapel<Lo3HuwelijkOfGpInhoud> huwelijkOfGpStapel : param) {
                if (huwelijkOfGpStapel != null) {
                    huwelijkOfGpStapels.add(huwelijkOfGpStapel);
                }
            }
        }
        return this;
    }

    /**
     * Voegt een huwelijk of gp stapel toe.
     * 
     * @param param
     *            de huwelijk of gp stapel
     * @return het Lo3PersoonslijstBuilder object
     */
    public Lo3PersoonslijstBuilder huwelijkOfGpStapel(final Lo3Stapel<Lo3HuwelijkOfGpInhoud> param) {
        if (param != null) {
            huwelijkOfGpStapels.add(param);
        }
        return this;
    }

    /**
     * Zet de overlijden stapel.
     * 
     * @param param
     *            de overlijden stapel
     * @return het Lo3PersoonslijstBuilder object
     */
    public Lo3PersoonslijstBuilder overlijdenStapel(final Lo3Stapel<Lo3OverlijdenInhoud> param) {
        overlijdenStapel = param;
        return this;
    }

    /**
     * Zet de inschrijving stapel.
     * 
     * @param param
     *            de inschrijving stapel
     * @return het Lo3PersoonslijstBuilder object
     */
    public Lo3PersoonslijstBuilder inschrijvingStapel(final Lo3Stapel<Lo3InschrijvingInhoud> param) {
        inschrijvingStapel = param;
        return this;
    }

    /**
     * Zet de verblijfplaats stapel.
     * 
     * @param param
     *            de verblijfplaats stapel
     * @return het Lo3PersoonslijstBuilder object
     */
    public Lo3PersoonslijstBuilder verblijfplaatsStapel(final Lo3Stapel<Lo3VerblijfplaatsInhoud> param) {
        verblijfplaatsStapel = param;
        return this;
    }

    /**
     * Zet de lijst met kind stapels.
     * 
     * @param param
     *            de kind stapels, mag null zijn
     * @return het Lo3PersoonslijstBuilder object
     * 
     */
    public Lo3PersoonslijstBuilder kindStapels(final List<Lo3Stapel<Lo3KindInhoud>> param) {
        kindStapels.clear();
        if (param != null) {
            for (final Lo3Stapel<Lo3KindInhoud> kindStapel : param) {
                if (kindStapel != null) {
                    kindStapels.add(kindStapel);
                }
            }
        }
        return this;
    }

    /**
     * Voegt een kind stapel toe.
     * 
     * @param param
     *            de kind stapel
     * @return het Lo3PersoonslijstBuilder object
     */
    public Lo3PersoonslijstBuilder kindStapel(final Lo3Stapel<Lo3KindInhoud> param) {
        if (param != null) {
            kindStapels.add(param);
        }
        return this;
    }

    /**
     * Zet de verblijfstitel stapel.
     * 
     * @param param
     *            de verblijfstitel stapel
     * @return het Lo3PersoonslijstBuilder object
     */
    public Lo3PersoonslijstBuilder verblijfstitelStapel(final Lo3Stapel<Lo3VerblijfstitelInhoud> param) {
        verblijfstitelStapel = param;
        return this;
    }

    /**
     * Zet de gezagsverhouding stapel.
     * 
     * @param param
     *            de gezagsverhouding stapel
     * @return het Lo3PersoonslijstBuilder object
     */
    public Lo3PersoonslijstBuilder gezagsverhoudingStapel(final Lo3Stapel<Lo3GezagsverhoudingInhoud> param) {
        gezagsverhoudingStapel = param;
        return this;
    }

    /**
     * Zet de lijst met reisdocument stapels.
     * 
     * @param param
     *            de lijst met reisdocument stapel, mag null zijn
     * @return het Lo3PersoonslijstBuilder object
     * 
     */
    public Lo3PersoonslijstBuilder reisdocumentStapels(final List<Lo3Stapel<Lo3ReisdocumentInhoud>> param) {
        reisdocumentStapels.clear();
        if (param != null) {
            for (final Lo3Stapel<Lo3ReisdocumentInhoud> reisdocumentStapel : param) {
                if (reisdocumentStapel != null) {
                    reisdocumentStapels.add(reisdocumentStapel);
                }
            }
        }
        return this;
    }

    /**
     * Voegt een reisdocument stapel toe.
     * 
     * @param param
     *            de reisdocument stapel, mag null zijn
     * @return het Lo3PersoonslijstBuilder object
     * 
     */
    public Lo3PersoonslijstBuilder reisdocumentStapel(final Lo3Stapel<Lo3ReisdocumentInhoud> param) {
        if (param != null) {
            reisdocumentStapels.add(param);
        }
        return this;
    }

    /**
     * Zet de kiesrecht stapel.
     * 
     * @param param
     *            de kiesrecht stapel
     * @return het Lo3PersoonslijstBuilder object
     */
    public Lo3PersoonslijstBuilder kiesrechtStapel(final Lo3Stapel<Lo3KiesrechtInhoud> param) {
        kiesrechtStapel = param;
        return this;
    }

    /**
     * @return een nieuw Lo3Persoonslijst object.
     */
    public Lo3Persoonslijst build() {
        return new Lo3Persoonslijst(
            persoonStapel,
            ouder1Stapel,
            ouder2Stapel,
            nationaliteitStapels,
            huwelijkOfGpStapels,
            overlijdenStapel,
            inschrijvingStapel,
            verblijfplaatsStapel,
            kindStapels,
            verblijfstitelStapel,
            gezagsverhoudingStapel,
            reisdocumentStapels,
            kiesrechtStapel);
    }

}

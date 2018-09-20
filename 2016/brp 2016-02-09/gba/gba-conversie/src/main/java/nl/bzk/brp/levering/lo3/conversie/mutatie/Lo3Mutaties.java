/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3GezagsverhoudingFormatter;
import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3KindFormatter;
import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3OuderFormatter;
import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3OverlijdenFormatter;
import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3PersoonFormatter;
import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3VerblijfstitelFormatter;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3CategorieInhoud;
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
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * LO3 Mutaties.
 */
public final class Lo3Mutaties {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final Lo3PersoonFormatter PERSOON_FORMATTER = new Lo3PersoonFormatter();
    private static final Lo3OuderFormatter OUDER_FORMATTER = new Lo3OuderFormatter();
    private static final Lo3OverlijdenFormatter OVERLIJDEN_FORMATTER = new Lo3OverlijdenFormatter();
    private static final Lo3KindFormatter KIND_FORMATTER = new Lo3KindFormatter();
    private static final Lo3VerblijfstitelFormatter VERBLIJFSTITEL_FORMATTER = new Lo3VerblijfstitelFormatter();
    private static final Lo3GezagsverhoudingFormatter GEZAGSVERHOUDING_FORMATTER = new Lo3GezagsverhoudingFormatter();

    private final Lo3Wijzigingen<Lo3PersoonInhoud> persoon = new Lo3Wijzigingen<>(Lo3CategorieEnum.CATEGORIE_01, PERSOON_FORMATTER);
    private final Lo3Wijzigingen<Lo3OuderInhoud> ouder1 = new Lo3Wijzigingen<>(Lo3CategorieEnum.CATEGORIE_02, OUDER_FORMATTER);
    private final Lo3Wijzigingen<Lo3OuderInhoud> ouder2 = new Lo3Wijzigingen<>(Lo3CategorieEnum.CATEGORIE_03, OUDER_FORMATTER);

    private final List<Lo3Wijzigingen<Lo3NationaliteitInhoud>> nationaliteiten = new ArrayList<>();
    private final Map<String, Lo3WijzigingenCategorie05> huwelijken = new HashMap<>();

    private final Lo3Wijzigingen<Lo3OverlijdenInhoud> overlijden = new Lo3Wijzigingen<>(Lo3CategorieEnum.CATEGORIE_06, OVERLIJDEN_FORMATTER);
    private final Lo3Wijzigingen<Lo3InschrijvingInhoud> inschrijving = new Lo3WijzigingenCategorie07();
    private final Lo3Wijzigingen<Lo3VerblijfplaatsInhoud> adres = new Lo3WijzigingenCategorie08();
    private final List<Lo3Wijzigingen<Lo3KindInhoud>> kinderen = new ArrayList<>();
    private final Lo3Wijzigingen<Lo3VerblijfstitelInhoud> verblijfstitel = new Lo3Wijzigingen<>(Lo3CategorieEnum.CATEGORIE_10, VERBLIJFSTITEL_FORMATTER);
    private final Lo3Wijzigingen<Lo3GezagsverhoudingInhoud> gezagsverhouding =
            new Lo3Wijzigingen<>(Lo3CategorieEnum.CATEGORIE_11, GEZAGSVERHOUDING_FORMATTER);
    private final List<Lo3Wijzigingen<Lo3ReisdocumentInhoud>> reisdocumenten = new ArrayList<>();
    private final Lo3Wijzigingen<Lo3KiesrechtInhoud> kiesrecht = new Lo3WijzigingenCategorie13();

    /**
     * Geef wijzigingen voor categorie 01 (persoon).
     *
     * @return wijzigingen.
     */
    public Lo3Wijzigingen<Lo3PersoonInhoud> geefPersoonWijziging() {
        return persoon;
    }

    /**
     * Geef wijzigingen voor categorie 02 (ouder 1).
     *
     * @return wijzigingen.
     */
    public Lo3Wijzigingen<Lo3OuderInhoud> geefOuder1Wijziging() {
        return ouder1;
    }

    /**
     * Geef wijzigingen voor categorie 03 (ouder 2).
     *
     * @return wijzigingen.
     */
    public Lo3Wijzigingen<Lo3OuderInhoud> geefOuder2Wijziging() {
        return ouder2;
    }

    /**
     * Geef nieuwe wijzigingen voor categorie 04 (nationaliteit).
     *
     * @return wijzigingen.
     */

    public Lo3Wijzigingen<Lo3NationaliteitInhoud> geefNieuweNationaliteitWijziging() {
        final Lo3Wijzigingen<Lo3NationaliteitInhoud> resultaat = new Lo3WijzigingenCategorie04();
        nationaliteiten.add(resultaat);
        return resultaat;
    }

    /**
     * Geef wijzigingen voor categorie 05 (huwelijk).
     *
     * @param relatieIdentificatie
     *            relatie identificatie (om meerdere relatie samen te voegen in 1 categorie 05)
     * @return wijzigingen.
     */

    public Lo3Wijzigingen<Lo3HuwelijkOfGpInhoud> geefNieuweOfBestaandeHuwelijkWijziging(final String relatieIdentificatie) {
        if (huwelijken.containsKey(relatieIdentificatie)) {
            return huwelijken.get(relatieIdentificatie);
        } else {
            final Lo3WijzigingenCategorie05 resultaat = new Lo3WijzigingenCategorie05(relatieIdentificatie);
            huwelijken.put(relatieIdentificatie, resultaat);
            return resultaat;
        }
    }

    /**
     * Geef wijzigingen voor categorie 06 (overlijden).
     *
     * @return wijzigingen.
     */
    public Lo3Wijzigingen<Lo3OverlijdenInhoud> geefOverlijdenWijziging() {
        return overlijden;
    }

    /**
     * Geef wijzigingen voor categorie 07 (inschrijving).
     *
     * @return wijzigingen.
     */
    public Lo3Wijzigingen<Lo3InschrijvingInhoud> geefInschrijvingWijziging() {
        return inschrijving;
    }

    /**
     * Geef wijzigingen voor categorie 08 (verblijfsplaats).
     *
     * @return wijzigingen.
     */
    public Lo3Wijzigingen<Lo3VerblijfplaatsInhoud> geefVerblijfsplaatsWijziging() {
        return adres;
    }

    /**
     * Geef nieuwe wijzigingen voor categorie 09 (kind).
     *
     * @return wijzigingen.
     */
    public Lo3Wijzigingen<Lo3KindInhoud> geefNieuweKindWijziging() {
        final Lo3Wijzigingen<Lo3KindInhoud> resultaat = new Lo3Wijzigingen<>(Lo3CategorieEnum.CATEGORIE_09, KIND_FORMATTER);
        kinderen.add(resultaat);
        return resultaat;
    }

    /**
     * Geef wijzigingen voor categorie 10 (verblijfstitel).
     *
     * @return wijzigingen.
     */
    public Lo3Wijzigingen<Lo3VerblijfstitelInhoud> geefVerblijfstitelWijziging() {
        return verblijfstitel;
    }

    /**
     * Geef wijzigingen voor categorie 11 (gezagsverhouding).
     *
     * @return wijzigingen.
     */
    public Lo3Wijzigingen<Lo3GezagsverhoudingInhoud> geefGezagsverhoudingWijziging() {
        return gezagsverhouding;
    }

    /**
     * Geef nieuwe wijzigingen voor categorie 12 (reisdocument).
     *
     * @return wijzigingen.
     */
    public Lo3Wijzigingen<Lo3ReisdocumentInhoud> geefNieuweReisdocumentWijziging() {
        final Lo3Wijzigingen<Lo3ReisdocumentInhoud> resultaat = new Lo3WijzigingenCategorie12();
        reisdocumenten.add(resultaat);
        return resultaat;
    }

    /**
     * Geef wijzigingen voor categorie 13 (kiesrecht).
     *
     * @return wijzigingen.
     */
    public Lo3Wijzigingen<Lo3KiesrechtInhoud> geefKiesrechtWijziging() {
        return kiesrecht;
    }

    /**
     * Geef alle categorieen.
     *
     * @return categorien
     */
    public List<Lo3CategorieWaarde> geefCategorieen() {
        LOGGER.debug("Huwelijken/Geregistreerd partnerschappen:");
        for (final Lo3WijzigingenCategorie05 huwelijk : huwelijken.values()) {
            LOGGER.debug("Relatie: {}", huwelijk.getRelatieIdentificatie());
            LOGGER.debug("Categorie 05: {}", huwelijk.getActueleInhoud());
            LOGGER.debug("Categoie 55: {}", huwelijk.getHistorischeInhoud());
        }

        final List<Lo3CategorieWaarde> result = new ArrayList<>();
        voegToe(result, persoon);
        voegToe(result, ouder1);
        voegToe(result, ouder2);
        voegToe(result, nationaliteiten);
        voegToe(result, huwelijken.values());
        voegToe(result, overlijden);
        voegToe(result, inschrijving);
        voegToe(result, adres);
        voegToe(result, kinderen);
        voegToe(result, verblijfstitel);
        voegToe(result, gezagsverhouding);
        voegToe(result, reisdocumenten);
        voegToe(result, kiesrecht);

        return result;
    }

    private void voegToe(final List<Lo3CategorieWaarde> result, final Collection<? extends Lo3Wijzigingen<? extends Lo3CategorieInhoud>> wijzigingen) {
        if (wijzigingen != null) {
            for (final Lo3Wijzigingen<?> wijziging : wijzigingen) {
                voegToe(result, wijziging);
            }
        }
    }

    private void voegToe(final List<Lo3CategorieWaarde> result, final Lo3Wijzigingen<? extends Lo3CategorieInhoud> wijzigingen) {
        if (wijzigingen != null) {
            wijzigingen.format();
            if (wijzigingen.bevatWijzigingen()) {
                result.add(wijzigingen.geefActueleCategorie());
                result.add(wijzigingen.geefHistorischeCategorie());
            }
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                                                                          .append("persoon", persoon)
                                                                          .append("ouder1", ouder1)
                                                                          .append("ouder2", ouder2)
                                                                          .append("nationaliteiten", nationaliteiten)
                                                                          .append("huwelijken", huwelijken)
                                                                          .append("overlijden", overlijden)
                                                                          .append("inschrijving", inschrijving)
                                                                          .append("adres", adres)
                                                                          .append("kinderen", kinderen)
                                                                          .append("verblijfstitel", verblijfstitel)
                                                                          .append("gezagsverhouding", gezagsverhouding)
                                                                          .append("reisdocumenten", reisdocumenten)
                                                                          .append("kiesrecht", kiesrecht)
                                                                          .toString();
    }
}

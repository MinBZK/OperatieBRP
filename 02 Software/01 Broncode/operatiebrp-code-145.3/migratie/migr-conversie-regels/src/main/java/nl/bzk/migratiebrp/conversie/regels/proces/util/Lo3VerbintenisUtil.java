/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.conversie.model.BijzondereSituatie;
import nl.bzk.migratiebrp.conversie.model.Preconditie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.AbstractLo3Element;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Element;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOntbindingHuwelijkOfGpCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortVerbintenis;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Validatie;
import nl.bzk.migratiebrp.conversie.model.lo3.groep.Lo3GroepUtil;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3GroepEnum;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.regels.proces.foutmelding.Foutmelding;
import org.springframework.stereotype.Component;

/**
 * Splitst verbintenissen (categorie 5) op naar aparte stapels per soort verbintenis en kan per sluiting/ontbinding de
 * actuele rij bepalen.
 */
@Component
public final class Lo3VerbintenisUtil {

    private static final Logger LOG = LoggerFactory.getLogger();
    public static final int INT_2 = 2;

    private Lo3VerbintenisUtil() {
    }

    /**
     * Splits de verbintenissen die zich in één HuwelijkOfGp stapel bevinden en beeindigt de verbintenissen waar nodig
     * met reden Z (omzetting). Onjuiste rijen worden niet meegenomen.
     * @param stapel de HuwelijkOfGp stapel
     * @return de gesplitste verbintenissen
     */
    public static List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> splitsEnZetVerbintenissenOm(final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel) {
        LOG.debug("splitsEnZetVerbintenissenOm(stapel={})", stapel);
        return splits(stapel, false);
    }

    /**
     * Splits de verbintenissen en controleert de ontstane verbintenissen op preconditie overtredingen en bijzondere
     * situaties.
     * @param stapel de huwelijk/GP stapel
     */
    public static void splitsEnControleerVerbintenissen(final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel) {
        LOG.debug("splitsEnControleerVerbintenissen(stapel={})", stapel);
        splits(stapel, true);
    }

    private static List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> splits(final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel, final boolean isControleRun) {
        // We nemen eerst de juiste gevulde rijen uit de stapel
        final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> juisteRijen = bepaalJuisteRijen(stapel);

        final List<Verbintenis> verbintenissen = splitsVerbintenissen(juisteRijen);

        if (isControleRun) {
            for (final Verbintenis verbintenis : verbintenissen) {
                final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> records = verbintenis.getRecords();

                controleerPre74en75(records);
            }
        }

        beeindigVerbintenissen(verbintenissen, isControleRun);

        final List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> result = new ArrayList<>();
        if (!isControleRun) {
            for (final Verbintenis verbintenis : verbintenissen) {
                result.add(verbintenis.getStapel());
            }
        }
        return result;
    }

    private static void controleerPre74en75(final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> records) {
        Lo3Categorie<Lo3HuwelijkOfGpInhoud> vorigRecord = null;
        for (final Lo3Categorie<Lo3HuwelijkOfGpInhoud> record : records) {
            if (vorigRecord != null) {
                controleerPreconditie074(vorigRecord, record);
                controleerPreconditie075(vorigRecord, record);
            }
            vorigRecord = record;
        }
    }

    /**
     * NIET alle elementen in groep 6 gelijk aan vorige voorkomen? en 15.10 gelijk aan vorige voorkomen en Groep 6
     * aanwezig??
     * @param vorigeCategorie Vorige categorie.
     * @param categorie Categorie.
     */
    private static void controleerPreconditie074(
            final Lo3Categorie<Lo3HuwelijkOfGpInhoud> vorigeCategorie,
            final Lo3Categorie<Lo3HuwelijkOfGpInhoud> categorie) {
        final boolean groep06AanwezigHuidige = Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP06, categorie.getInhoud());
        final boolean groep06AanwezigVorige = Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP06, vorigeCategorie.getInhoud());

        if (groep06AanwezigHuidige && groep06AanwezigVorige && !isGroep6Gelijk(vorigeCategorie, categorie)) {
            // Inhoud van groep 6 is niet gelijk, preconditie fout 074
            Foutmelding.logMeldingFout(categorie.getLo3Herkomst(), LogSeverity.INFO, SoortMeldingCode.PRE074, null);
        }
    }

    /**
     * NIET alle elementen in groep 7 gelijk aan vorige voorkomen? en 15.10 gelijk aan vorige voorkomen en Groep 7
     * aanwezig??
     * @param vorigeCategorie Vorige categorie.
     * @param categorie Categorie.
     */
    private static void controleerPreconditie075(
            final Lo3Categorie<Lo3HuwelijkOfGpInhoud> vorigeCategorie,
            final Lo3Categorie<Lo3HuwelijkOfGpInhoud> categorie) {
        final boolean groep07AanwezigHuidige = Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP07, categorie.getInhoud());
        final boolean groep07AanwezigVorige = Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP07, vorigeCategorie.getInhoud());

        if (groep07AanwezigHuidige && groep07AanwezigVorige && !isGroep7Gelijk(vorigeCategorie, categorie)) {
            // Inhoud van groep 7 is niet gelijk, preconditie fout 075
            Foutmelding.logMeldingFout(categorie.getLo3Herkomst(), LogSeverity.INFO, SoortMeldingCode.PRE075, null);
        }
    }

    /**
     * Controleer of groep6 in de meegegeven categorieen inhoudelijk gelijk is.
     * @param categorie1 Eerste categorie.
     * @param categorie2 Tweede categorie
     * @return True als groep 6 inhoudelijk gelijk is.
     */
    private static boolean isGroep6Gelijk(final Lo3Categorie<Lo3HuwelijkOfGpInhoud> categorie1, final Lo3Categorie<Lo3HuwelijkOfGpInhoud> categorie2) {
        return AbstractLo3Element.equalsWaarde(
                categorie2.getInhoud().getDatumSluitingHuwelijkOfAangaanGp(),
                categorie1.getInhoud().getDatumSluitingHuwelijkOfAangaanGp())
                && AbstractLo3Element.equalsWaarde(
                categorie2.getInhoud().getGemeenteCodeSluitingHuwelijkOfAangaanGp(),
                categorie1.getInhoud().getGemeenteCodeSluitingHuwelijkOfAangaanGp())
                && AbstractLo3Element.equalsWaarde(
                categorie2.getInhoud().getLandCodeSluitingHuwelijkOfAangaanGp(),
                categorie1.getInhoud().getLandCodeSluitingHuwelijkOfAangaanGp());
    }

    /**
     * Controleer of groep7 in de meegegeven categorieen inhoudelijk gelijk is.
     * @param categorie1 Eerste categorie.
     * @param categorie2 Tweede categorie
     * @return True als groep 7 inhoudelijk gelijk is.
     */
    private static boolean isGroep7Gelijk(final Lo3Categorie<Lo3HuwelijkOfGpInhoud> categorie1, final Lo3Categorie<Lo3HuwelijkOfGpInhoud> categorie2) {
        return AbstractLo3Element.equalsWaarde(categorie2.getInhoud().getDatumOntbindingHuwelijkOfGp(), categorie1.getInhoud().getDatumOntbindingHuwelijkOfGp())
                && AbstractLo3Element.equalsWaarde(
                categorie2.getInhoud().getGemeenteCodeOntbindingHuwelijkOfGp(),
                categorie1.getInhoud().getGemeenteCodeOntbindingHuwelijkOfGp())
                && AbstractLo3Element.equalsWaarde(
                categorie2.getInhoud().getLandCodeOntbindingHuwelijkOfGp(),
                categorie1.getInhoud().getLandCodeOntbindingHuwelijkOfGp())
                && AbstractLo3Element.equalsWaarde(
                categorie2.getInhoud().getRedenOntbindingHuwelijkOfGpCode(),
                categorie1.getInhoud().getRedenOntbindingHuwelijkOfGpCode());
    }

    /**
     * Deze methode beeindigd de verbintenissen die omgezet zijn. De beeindiging wordt alleen doorgevoerd als
     * isControleRun false is.
     * @param juisteVerbintenissen de lijst met verbintenissen
     * @param isControleRun true als er alleen gecontroleerd moet worden op precondities en bijzondere situaties. false als de beeindiging ook echt moet
     * plaatsvinden
     */
    @Preconditie(SoortMeldingCode.PRE114)
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB041)
    private static void beeindigVerbintenissen(final List<Verbintenis> juisteVerbintenissen, final boolean isControleRun) {
        for (int index = juisteVerbintenissen.size() - INT_2; index >= 0; index--) {
            final Verbintenis dezeVerbintenis = juisteVerbintenissen.get(index);

            if (isControleRun && dezeVerbintenis.bevatOntbinding()) {
                Lo3Categorie<Lo3HuwelijkOfGpInhoud> teConverterenRij = zoekTeConverterenRij(dezeVerbintenis.getRecords(), false);
                if (teConverterenRij != null) {
                    Foutmelding.logMeldingFoutInfo(teConverterenRij
                                    .getLo3Herkomst(),
                            SoortMeldingCode.BIJZ_CONV_LB041,
                            null);
                }
            }

            final Verbintenis volgendeVerbintenis = juisteVerbintenissen.get(index + 1);
            final Lo3Categorie<Lo3HuwelijkOfGpInhoud> sluitingsRecord = zoekTeConverterenRij(volgendeVerbintenis.getRecords(), true);

            if (sluitingsRecord == null) {
                Foutmelding.logMeldingFoutError(volgendeVerbintenis.getRecords().get(0).getLo3Herkomst(), SoortMeldingCode.PRE114, null);
            } else if (!isControleRun) {
                dezeVerbintenis.beeindig(sluitingsRecord);
            }
        }
    }

    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB015)
    private static List<Verbintenis> splitsVerbintenissen(final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> juisteRijen) {
        final List<Verbintenis> verbintenissen = new LinkedList<>();

        // Nu bepalen we de verschillende periodes en omzettingen
        Lo3SoortVerbintenis laatsteSoortVerbintenis = null;
        Verbintenis laatsteVerbintenis = null;

        for (final Lo3Categorie<Lo3HuwelijkOfGpInhoud> juisteRij : juisteRijen) {
            final Lo3SoortVerbintenis soortVerbintenis = juisteRij.getInhoud().getSoortVerbintenis();

            if (laatsteVerbintenis == null) {
                laatsteVerbintenis = new Verbintenis(juisteRij);
                verbintenissen.add(laatsteVerbintenis);
                laatsteSoortVerbintenis = soortVerbintenis;
                continue;
            }

            // voeg lege rij of rij van dezelfde soort verbintenis toe
            if (!Lo3Validatie.isElementGevuld(soortVerbintenis)
                    || !Lo3Validatie.isElementGevuld(laatsteSoortVerbintenis)
                    || AbstractLo3Element.equalsWaarde(soortVerbintenis, laatsteSoortVerbintenis)) {
                laatsteVerbintenis.toevoegen(juisteRij);
            } else if (laatsteSoortVerbintenis.bevatGeldigeWaarde() && soortVerbintenis.bevatGeldigeWaarde()) {
                Foutmelding.logMeldingFoutInfo(juisteRij.getLo3Herkomst(), SoortMeldingCode.BIJZ_CONV_LB015, null);
                laatsteVerbintenis = new Verbintenis(juisteRij);
                verbintenissen.add(laatsteVerbintenis);
                laatsteSoortVerbintenis = soortVerbintenis;
            }
        }

        return verbintenissen;
    }

    private static List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> bepaalJuisteRijen(final Lo3Stapel<Lo3HuwelijkOfGpInhoud> verbintenis) {
        final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> result = new ArrayList<>();

        for (final Lo3Categorie<Lo3HuwelijkOfGpInhoud> rij : verbintenis) {
            if (!Lo3Validatie.isElementGevuld(rij.getHistorie().getIndicatieOnjuist())) {
                result.add(rij);
            }
        }
        result.sort(new IngangsdatumGeldigheidComparator());

        return result;
    }

    /**
     * Zoek de rij op die geconverteerd gaat worden. Ook voor een omzetting kan deze methode gebruikt worden
     * @param huwelijkOfGpStapel de stapel
     * @param zoekSluiting true als het om een sluiting gaat, false voor een ontbinding
     * @return de te converteren rij of rij die gebruikt wordt om een omzetting te doen
     */
    public static Lo3Categorie<Lo3HuwelijkOfGpInhoud> zoekTeConverterenRij(
            final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> huwelijkOfGpStapel,
            final boolean zoekSluiting) {
        // Maak en nieuwe lijst aan en loop door de relatiestapel en voeg alleen de juiste rijen aan de nieuwe lijst
        // toe
        final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> huwelijkOfGpRijen = new ArrayList<>();
        for (final Lo3Categorie<Lo3HuwelijkOfGpInhoud> huwelijkOfGpRij : huwelijkOfGpStapel) {
            // Als de rij onjuist is, dan wordt deze niet geconverteerd
            if (huwelijkOfGpRij.getHistorie().isOnjuist()) {
                continue;
            }

            if (zoekSluiting && huwelijkOfGpRij.getInhoud().isSluiting() || !zoekSluiting && huwelijkOfGpRij.getInhoud().isOntbinding()) {
                huwelijkOfGpRijen.add(huwelijkOfGpRij);
            }
        }

        return Lo3VerbintenisUtil.bepaalTeGebruikenRij(zoekSluiting, huwelijkOfGpRijen);
    }

    private static Lo3Categorie<Lo3HuwelijkOfGpInhoud> bepaalTeGebruikenRij(
            final boolean zoekSluiting,
            final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> huwelijkOfGpCategorieen) {
        Lo3Categorie<Lo3HuwelijkOfGpInhoud> teGebruikenVoorkomen = null;

        // Zoek de te gebruiken rij voor de conversie
        if (huwelijkOfGpCategorieen.isEmpty()) {
            teGebruikenVoorkomen = null;
        } else if (huwelijkOfGpCategorieen.size() == 1) {
            // 1. Er is maar 1 juiste rij. Gebruik die.
            teGebruikenVoorkomen = huwelijkOfGpCategorieen.get(0);
        } else {
            final Lo3Stapel<Lo3HuwelijkOfGpInhoud> huwelijkOfGpStapel = new Lo3Stapel<>(huwelijkOfGpCategorieen);

            final Lo3Categorie<Lo3HuwelijkOfGpInhoud> actueelVoorkomen =
                    huwelijkOfGpStapel.bevatLo3ActueelVoorkomen() ? huwelijkOfGpStapel.getLo3ActueelVoorkomen() : null;
            if (zoekSluiting && isSluitingsGegevensIdentiek(huwelijkOfGpStapel) || !zoekSluiting && isOntbindingsGegevensIdentiek(huwelijkOfGpStapel)) {
                teGebruikenVoorkomen = zoekMeestRecenteVoorkomenMetOnderzoek(huwelijkOfGpStapel, actueelVoorkomen);

                // 2. Bij consistente gegevens, zoek naar een voorkomen met geldigheid==sluitingsdatum of
                // geldigheid==ontbindingsdatum
                teGebruikenVoorkomen = bepaalTeGebruikenGegevensSluitingOntbinding(zoekSluiting, teGebruikenVoorkomen, huwelijkOfGpStapel);

                // 3. Bij consistente gegevens en geen geen rij met geldigheid==sluitingsdatum of
                // geldigheid==ontbindingsdatum, zoek
                // een unieke rij met geldigheid==Standaardwaarde==00000000
                if (teGebruikenVoorkomen == null) {
                    teGebruikenVoorkomen = zoekUniekeGeldigheidsWaarde(new Lo3Datum(0), huwelijkOfGpStapel);
                }
            }

            // 4. In andere gevallen, zoek rij met meest recente geldigheid.
            if (teGebruikenVoorkomen == null) {
                teGebruikenVoorkomen = zoekMeestRecentVoorkomen(huwelijkOfGpStapel, actueelVoorkomen);
            }
        }
        return teGebruikenVoorkomen;
    }

    private static Lo3Categorie<Lo3HuwelijkOfGpInhoud> bepaalTeGebruikenGegevensSluitingOntbinding(final boolean zoekSluiting,
                                                                                                   final Lo3Categorie<Lo3HuwelijkOfGpInhoud>
                                                                                                           teGebruikenVoorkomen,
                                                                                                   final Lo3Stapel<Lo3HuwelijkOfGpInhoud> huwelijkOfGpStapel) {
        if (teGebruikenVoorkomen == null) {
            // 2. Bij consistente gegevens, zoek naar een voorkomen met geldigheid==sluitingsdatum of
            // geldigheid==ontbindingsdatum
            final Lo3Datum zoekDatum =
                    zoekSluiting ? huwelijkOfGpStapel.get(0).getInhoud().getDatumSluitingHuwelijkOfAangaanGp()
                            : huwelijkOfGpStapel.get(0).getInhoud().getDatumOntbindingHuwelijkOfGp();
            return zoekUniekeGeldigheidsWaarde(zoekDatum, huwelijkOfGpStapel);
        }
        return teGebruikenVoorkomen;
    }

    private static Lo3Categorie<Lo3HuwelijkOfGpInhoud> zoekMeestRecentVoorkomen(final Lo3Stapel<Lo3HuwelijkOfGpInhoud> huwelijkOfGpStapel,
                                                                                final Lo3Categorie<Lo3HuwelijkOfGpInhoud> actueelVoorkomen) {
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> teGebruikenVoorkomen;
        if (actueelVoorkomen != null) {
            teGebruikenVoorkomen = actueelVoorkomen;
        } else {
            // Anders de rij met de meeste recente geldigheid zoeken
            teGebruikenVoorkomen = huwelijkOfGpStapel.zoekMeestRecentVoorkomen().orElse(null);
        }
        return teGebruikenVoorkomen;
    }

    private static Lo3Categorie<Lo3HuwelijkOfGpInhoud> zoekMeestRecenteVoorkomenMetOnderzoek(
            final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel,
            final Lo3Categorie<Lo3HuwelijkOfGpInhoud> actueelVoorkomen) {
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> result;
        if (actueelVoorkomen != null) {
            result = actueelVoorkomen.isInOnderzoek() ? actueelVoorkomen : null;
        } else {
            final Optional<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> meestRecenteCategorie = stapel.zoekMeestRecentVoorkomen();
            result = meestRecenteCategorie.isPresent() && meestRecenteCategorie.get().isInOnderzoek() ? meestRecenteCategorie.get() : null;
        }

        return result;
    }

    private static Lo3Categorie<Lo3HuwelijkOfGpInhoud> zoekUniekeGeldigheidsWaarde(
            final Lo3Datum zoekDatum,
            final Lo3Stapel<Lo3HuwelijkOfGpInhoud> huwelijkOfGpStapel) {
        Lo3Categorie<Lo3HuwelijkOfGpInhoud> result = null;

        for (final Lo3Categorie<Lo3HuwelijkOfGpInhoud> categorie : huwelijkOfGpStapel) {
            if (zoekDatum.equalsWaarde(categorie.getHistorie().getIngangsdatumGeldigheid())) {
                if (result == null) {
                    result = categorie;
                } else {
                    // Meerdere rijen met zoekdatum gevonden. Resultaat wordt null omdat de rij niet uniek is.
                    result = null;
                    break;
                }
            }
        }

        return result;
    }

    private static boolean isSluitingsGegevensIdentiek(final Lo3Stapel<Lo3HuwelijkOfGpInhoud> sluitingHuwelijkOfGpRijen) {
        boolean result = true;

        if (sluitingHuwelijkOfGpRijen.size() > 1) {
            final Iterator<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> iterator = sluitingHuwelijkOfGpRijen.iterator();
            final Lo3HuwelijkOfGpInhoud eersteRij = iterator.next().getInhoud();
            final Lo3Datum eersteSluitingsDatum = eersteRij.getDatumSluitingHuwelijkOfAangaanGp();
            final Lo3GemeenteCode eersteSluitingsGemeente = eersteRij.getGemeenteCodeSluitingHuwelijkOfAangaanGp();
            final Lo3LandCode eersteSluitingsLand = eersteRij.getLandCodeSluitingHuwelijkOfAangaanGp();

            while (iterator.hasNext()) {
                final Lo3HuwelijkOfGpInhoud volgendeRij = iterator.next().getInhoud();
                if (!isEqualNullable(eersteSluitingsDatum, volgendeRij.getDatumSluitingHuwelijkOfAangaanGp())
                        || !isEqualNullable(eersteSluitingsGemeente, volgendeRij.getGemeenteCodeSluitingHuwelijkOfAangaanGp())
                        || !isEqualNullable(eersteSluitingsLand, volgendeRij.getLandCodeSluitingHuwelijkOfAangaanGp())) {
                    result = false;
                    break;
                }
            }
        }

        return result;
    }

    private static boolean isOntbindingsGegevensIdentiek(final Lo3Stapel<Lo3HuwelijkOfGpInhoud> ontbindingHuwelijkOfGpRijen) {
        boolean result = true;

        if (ontbindingHuwelijkOfGpRijen.size() > 1) {
            final Iterator<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> iterator = ontbindingHuwelijkOfGpRijen.iterator();
            final Lo3HuwelijkOfGpInhoud eersteRij = iterator.next().getInhoud();
            final Lo3Datum eersteOntbindingsDatum = eersteRij.getDatumOntbindingHuwelijkOfGp();
            final Lo3GemeenteCode eersteOntbindingsGemeente = eersteRij.getGemeenteCodeOntbindingHuwelijkOfGp();
            final Lo3LandCode eersteOntbindingsLand = eersteRij.getLandCodeOntbindingHuwelijkOfGp();
            final Lo3RedenOntbindingHuwelijkOfGpCode eersteOntbindingsReden = eersteRij.getRedenOntbindingHuwelijkOfGpCode();

            while (iterator.hasNext()) {
                final Lo3HuwelijkOfGpInhoud volgendeRij = iterator.next().getInhoud();
                if (!isEqualNullable(eersteOntbindingsDatum, volgendeRij.getDatumOntbindingHuwelijkOfGp())
                        || !isEqualNullable(eersteOntbindingsGemeente, volgendeRij.getGemeenteCodeOntbindingHuwelijkOfGp())
                        || !isEqualNullable(eersteOntbindingsLand, volgendeRij.getLandCodeOntbindingHuwelijkOfGp())
                        || !isEqualNullable(eersteOntbindingsReden, volgendeRij.getRedenOntbindingHuwelijkOfGpCode())) {
                    result = false;
                    break;
                }
            }
        }

        return result;
    }

    private static boolean isEqualNullable(final Lo3Element eersteSluitingsDatum, final Lo3Element datumSluitingHuwelijkOfAangaanGp) {
        return eersteSluitingsDatum == null && datumSluitingHuwelijkOfAangaanGp == null
                || eersteSluitingsDatum != null && eersteSluitingsDatum.equalsWaarde(datumSluitingHuwelijkOfAangaanGp);
    }

    /**
     * Sorteer categorieen obv ingangsdatum geldigheid (en daarbinnen op datum opneming).
     */
    private static final class IngangsdatumGeldigheidComparator implements Comparator<Lo3Categorie<?>>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(final Lo3Categorie<?> o1, final Lo3Categorie<?> o2) {
            int result = o1.getHistorie().getIngangsdatumGeldigheid().compareTo(o2.getHistorie().getIngangsdatumGeldigheid());

            if (result == 0) {
                result = o1.getHistorie().getDatumVanOpneming().compareTo(o2.getHistorie().getDatumVanOpneming());
            }

            return result;
        }

    }

    /**
     * Inner class om een verbintenis weer te geven en handelingen op uit te voeren.
     */
    private static final class Verbintenis {
        private final Lo3SoortVerbintenis soortVerbintenis;
        private final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> records = new ArrayList<>();

        private Verbintenis(final Lo3Categorie<Lo3HuwelijkOfGpInhoud> record) {
            records.add(record);
            this.soortVerbintenis = record.getInhoud().getSoortVerbintenis();

        }

        private void beeindig(final Lo3Categorie<Lo3HuwelijkOfGpInhoud> record) {
            // maak eindrecord
            final Lo3HuwelijkOfGpInhoud sluiting = record.getInhoud();
            final Lo3HuwelijkOfGpInhoud.Builder omzetting = new Lo3HuwelijkOfGpInhoud.Builder();
            omzetting.datumSluitingHuwelijkOfAangaanGp(null);
            omzetting.gemeenteCodeSluitingHuwelijkOfAangaanGp(null);
            omzetting.landCodeSluitingHuwelijkOfAangaanGp(null);
            omzetting.datumOntbindingHuwelijkOfGp(sluiting.getDatumSluitingHuwelijkOfAangaanGp());
            omzetting.gemeenteCodeOntbindingHuwelijkOfGp(sluiting.getGemeenteCodeSluitingHuwelijkOfAangaanGp());
            omzetting.landCodeOntbindingHuwelijkOfGp(sluiting.getLandCodeSluitingHuwelijkOfAangaanGp());
            omzetting.redenOntbindingHuwelijkOfGpCode(Lo3RedenOntbindingHuwelijkOfGpCode.OMZETTING_VERBINTENIS);
            omzetting.soortVerbintenis(records.get(0).getInhoud().getSoortVerbintenis());

            records.add(new Lo3Categorie<>(omzetting.build(), record.getDocumentatie(), record.getHistorie(), record.getLo3Herkomst()));
        }

        private void toevoegen(final Lo3Categorie<Lo3HuwelijkOfGpInhoud> record) {
            records.add(record);
        }

        /**
         * Geef de waarde van records.
         * @return records
         */
        private List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> getRecords() {
            return records;
        }

        /**
         * Geef de waarde van stapel.
         * @return stapel
         */
        private Lo3Stapel<Lo3HuwelijkOfGpInhoud> getStapel() {
            return new Lo3Stapel<>(records);
        }

        /**
         * Geeft aan of de {@link Verbintenis} een ontbindings record bevat.
         * @return beeindigd
         */
        private boolean bevatOntbinding() {
            boolean bevatOntbinding = false;
            for (final Lo3Categorie<Lo3HuwelijkOfGpInhoud> record : records) {
                bevatOntbinding = false;
                if (record.getInhoud().isOntbinding()) {
                    bevatOntbinding = true;
                    break;
                }
                if (record.getInhoud().getSoortVerbintenis() == null) {
                    bevatOntbinding = true;
                }
            }
            return bevatOntbinding;
        }

        @Override
        public String toString() {
            return "soortVerbintenis: " + soortVerbintenis.getWaarde();
        }
    }
}

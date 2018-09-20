/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.autorisatie;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAutorisatie;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpDienst;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpDienstbundel;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpDienstbundelLo3Rubriek;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpLeveringsautorisatie;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpPartij;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstbundelInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpLeveringsautorisatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.util.BrpStapelSorter;

/**
 * Autorisaties sorteren.
 */
public final class BrpAutorisatieSorter {

    private BrpAutorisatieSorter() {
    }

    /**
     * Sorteer autorisatie.
     *
     * @param teSorterenAutorisatie
     *            te sorteren autorisatie
     * @return gesorteerde autorisatie
     */
    public static BrpAutorisatie sorteerAutorisatie(final BrpAutorisatie teSorterenAutorisatie) {
        if (teSorterenAutorisatie == null) {
            return null;
        }

        // Sorteer stapels.
        final BrpPartij gesorteeerdePartij = BrpAutorisatieSorter.sorteerPartij(teSorterenAutorisatie.getPartij());
        final List<BrpLeveringsautorisatie> gesorteerdLeveringsautorisaties =
                BrpAutorisatieSorter.sorteerLeveringsautorisatieLijst(teSorterenAutorisatie.getLeveringsAutorisatieLijst());

        return new BrpAutorisatie(gesorteeerdePartij, gesorteerdLeveringsautorisaties);
    }

    private static BrpPartij sorteerPartij(final BrpPartij partij) {
        if (partij == null) {
            return null;
        }
        return new BrpPartij(partij.getId(), partij.getNaam(), partij.getPartijCode(), BrpStapelSorter.sorteerStapel(partij.getPartijStapel()));
    }

    private static List<BrpLeveringsautorisatie> sorteerLeveringsautorisatieLijst(final List<BrpLeveringsautorisatie> leveringsautorisatieLijst) {
        final List<BrpLeveringsautorisatie> brpLeveringsautorisaties = new ArrayList<>();
        if (leveringsautorisatieLijst == null) {
            return null;
        }

        for (final BrpLeveringsautorisatie leveringsautorisatie : leveringsautorisatieLijst) {

            final List<BrpDienstbundel> gesorteerdeDienstbundelLijst =
                    BrpAutorisatieSorter.sorteerDienstbundelsLijst(leveringsautorisatie.getDienstbundels());

            brpLeveringsautorisaties.add(new BrpLeveringsautorisatie(
                leveringsautorisatie.getStelsel(),
                leveringsautorisatie.getIndicatieModelautorisatie(),
                BrpStapelSorter.sorteerStapel(leveringsautorisatie.getLeveringsautorisatieStapel()),
                gesorteerdeDienstbundelLijst));
        }

        Collections.sort(brpLeveringsautorisaties, new LeveringsautorisatieComparator());
        return brpLeveringsautorisaties;
    }

    private static List<BrpDienstbundel> sorteerDienstbundelsLijst(final List<BrpDienstbundel> dienstbundelLijst) {
        if (dienstbundelLijst == null) {
            return null;
        }

        final List<BrpDienstbundel> result = new ArrayList<>();
        for (final BrpDienstbundel dienstbundel : dienstbundelLijst) {
            result.add(BrpAutorisatieSorter.sorteerDienstbundel(dienstbundel));
        }

        Collections.sort(result, new DienstbundelComparator());

        return result;
    }

    private static BrpDienstbundel sorteerDienstbundel(final BrpDienstbundel dienstbundel) {
        if (dienstbundel == null) {
            return null;
        }
        if (dienstbundel.getLo3Rubrieken() != null) {
            Collections.sort(dienstbundel.getLo3Rubrieken(), new DienstbundelLo3RubriekenLijstComparator());
        }
        return new BrpDienstbundel(
            BrpAutorisatieSorter.sorteerDienstenLijst(dienstbundel.getDiensten()),
            dienstbundel.getLo3Rubrieken(),
            BrpStapelSorter.sorteerStapel(dienstbundel.getDienstbundelStapel()));
    }

    private static List<BrpDienst> sorteerDienstenLijst(final List<BrpDienst> dienstLijst) {
        if (dienstLijst == null) {
            return null;
        }

        final List<BrpDienst> result = new ArrayList<>();
        for (final BrpDienst dienst : dienstLijst) {
            result.add(BrpAutorisatieSorter.sorteerDienst(dienst));
        }
        Collections.sort(result, new DienstComparator());

        return result;
    }

    private static BrpDienst sorteerDienst(final BrpDienst dienst) {
        if (dienst == null) {
            return null;
        }
        return new BrpDienst(
            dienst.getEffectAfnemersindicatie(),
            dienst.getSoortDienstCode(),
            BrpStapelSorter.sorteerStapel(dienst.getDienstStapel()),
            BrpStapelSorter.sorteerStapel(dienst.getDienstAttenderingStapel()),
            BrpStapelSorter.sorteerStapel(dienst.getDienstSelectieStapel()));
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Volgorde voor leveringsautorisaties.
     */
    private static class LeveringsautorisatieComparator implements Comparator<BrpLeveringsautorisatie>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(final BrpLeveringsautorisatie o1, final BrpLeveringsautorisatie o2) {
            int resultaat;

            // Controle op bestaan stapel.
            if (o1.getLeveringsautorisatieStapel() == null) {
                if (o2.getLeveringsautorisatieStapel() == null) {
                    resultaat = 0;
                } else {
                    resultaat = 1;
                }
            } else {

                // Stapel bestaat, controleer inhoudelijk. Sorteer de stapel eerst.
                final BrpGroep<BrpLeveringsautorisatieInhoud> inhoudO1 = BrpStapelSorter.sorteerStapel(o1.getLeveringsautorisatieStapel()).get(0);
                final BrpGroep<BrpLeveringsautorisatieInhoud> inhoudO2 = BrpStapelSorter.sorteerStapel(o2.getLeveringsautorisatieStapel()).get(0);

                // Controleer of de bovenste van
                if (inhoudO1 == null) {
                    if (inhoudO2 == null) {
                        resultaat = 0;
                    } else {
                        resultaat = 1;
                    }
                } else {

                    int index = 0;
                    resultaat = 0;
                    while (resultaat == 0 && index < o1.getLeveringsautorisatieStapel().size()) {
                        resultaat =
                                new LeveringsautorisatieInhoudComparator().compare(
                                    BrpStapelSorter.sorteerStapel(o1.getLeveringsautorisatieStapel()).get(index),
                                    BrpStapelSorter.sorteerStapel(o2.getLeveringsautorisatieStapel()).get(index));
                        index++;
                    }
                }
            }
            return resultaat;
        }
    }

    /**
     * Volgorde voor leveringsautorisatie inhoud.
     */
    private static class LeveringsautorisatieInhoudComparator implements Comparator<BrpGroep<BrpLeveringsautorisatieInhoud>>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(final BrpGroep<BrpLeveringsautorisatieInhoud> o1, final BrpGroep<BrpLeveringsautorisatieInhoud> o2) {

            int resultaat;

            if (o1 == null) {
                if (o2 == null) {
                    resultaat = 0;
                } else {
                    resultaat = 1;
                }
            } else {
                // Sortering op ingangsdatum aflopend (jongste bovenaan).
                resultaat = new DatumComparator().compare(o1.getInhoud().getDatumIngang(), o2.getInhoud().getDatumIngang());

                // Sortering op einddatum aflopend indien sortering op ingangsdatum gelijk geeft.
                if (resultaat == 0) {
                    resultaat = new DatumComparator().compare(o1.getInhoud().getDatumEinde(), o2.getInhoud().getDatumEinde());
                }

                // Sortering op naam indien datums geen effect hebben.
                if (resultaat == 0) {
                    resultaat = o1.getInhoud().getNaam().compareTo(o2.getInhoud().getNaam());
                }
            }

            return resultaat;
        }

    }

    /**
     * Volgorde voor dienstbundels.
     */
    private static class DienstbundelComparator implements Comparator<BrpDienstbundel>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(final BrpDienstbundel o1, final BrpDienstbundel o2) {
            int resultaat;

            // Controle op bestaan stapel.
            if (o1.getDienstbundelStapel() == null) {
                if (o2.getDienstbundelStapel() == null) {
                    resultaat = 0;
                } else {
                    resultaat = 1;
                }
            } else {

                // Stapel bestaat, controleer inhoudelijk. Sorteer de stapel eerst.
                final BrpGroep<BrpDienstbundelInhoud> inhoudO1 = BrpStapelSorter.sorteerStapel(o1.getDienstbundelStapel()).get(0);
                final BrpGroep<BrpDienstbundelInhoud> inhoudO2 = BrpStapelSorter.sorteerStapel(o2.getDienstbundelStapel()).get(0);

                // Controleer of de bovenste van
                if (inhoudO1 == null) {
                    if (inhoudO2 == null) {
                        resultaat = 0;
                    } else {
                        resultaat = 1;
                    }
                } else {

                    int index = 0;
                    resultaat = 0;
                    while (resultaat == 0 && index < o1.getDienstbundelStapel().size()) {
                        resultaat =
                                new DienstbundelInhoudComparator().compare(
                                    BrpStapelSorter.sorteerStapel(o1.getDienstbundelStapel()).get(index),
                                    BrpStapelSorter.sorteerStapel(o2.getDienstbundelStapel()).get(index));
                        index++;
                    }
                }
            }
            return resultaat;
        }
    }

    /**
     * Volgorde voor dienstbundel inhoud.
     */
    private static class DienstbundelInhoudComparator implements Comparator<BrpGroep<BrpDienstbundelInhoud>>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(final BrpGroep<BrpDienstbundelInhoud> o1, final BrpGroep<BrpDienstbundelInhoud> o2) {

            int resultaat;

            if (o1 == null) {
                if (o2 == null) {
                    resultaat = 0;
                } else {
                    resultaat = 1;
                }
            } else {
                // Sortering op ingangsdatum aflopend (jongste bovenaan).
                resultaat = new DatumComparator().compare(o1.getInhoud().getDatumIngang(), o2.getInhoud().getDatumIngang());

                // Sortering op einddatum aflopend indien sortering op ingangsdatum gelijk geeft.
                if (resultaat == 0) {
                    resultaat = new DatumComparator().compare(o1.getInhoud().getDatumEinde(), o2.getInhoud().getDatumEinde());
                }

                // Sortering op naam indien datums geen effect hebben.
                if (resultaat == 0) {
                    resultaat = o1.getInhoud().getNaam().compareTo(o2.getInhoud().getNaam());
                }
            }

            return resultaat;
        }

    }

    /**
     * Volgorde voor diensten.
     */
    private static class DienstComparator implements Comparator<BrpDienst>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(final BrpDienst o1, final BrpDienst o2) {
            int resultaat;

            // Controle op bestaan stapel.
            if (o1.getDienstStapel() == null) {
                if (o2.getDienstStapel() == null) {
                    resultaat = 0;
                } else {
                    resultaat = 1;
                }
            } else {

                // Stapel bestaat, controleer inhoudelijk. Sorteer de stapel eerst.
                final BrpGroep<BrpDienstInhoud> inhoudO1 = BrpStapelSorter.sorteerStapel(o1.getDienstStapel()).get(0);
                final BrpGroep<BrpDienstInhoud> inhoudO2 = BrpStapelSorter.sorteerStapel(o2.getDienstStapel()).get(0);

                // Controleer of de bovenste van
                if (inhoudO1 == null) {
                    if (inhoudO2 == null) {
                        resultaat = 0;
                    } else {
                        resultaat = 1;
                    }
                } else {

                    // Sortering op dienstcode.
                    resultaat = o1.getSoortDienstCode().compareTo(o2.getSoortDienstCode());

                    int index = 0;
                    while (resultaat == 0 && index < o1.getDienstStapel().size()) {
                        // Sortering op
                        resultaat =
                                new DienstInhoudComparator().compare(
                                    BrpStapelSorter.sorteerStapel(o1.getDienstStapel()).get(index),
                                    BrpStapelSorter.sorteerStapel(o2.getDienstStapel()).get(index));
                        index++;
                    }

                }
            }
            return resultaat;
        }
    }

    /**
     * Volgorde voor dienstbundel inhoud.
     */
    private static class DienstInhoudComparator implements Comparator<BrpGroep<BrpDienstInhoud>>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(final BrpGroep<BrpDienstInhoud> o1, final BrpGroep<BrpDienstInhoud> o2) {

            int resultaat;

            if (o1 == null) {
                if (o2 == null) {
                    resultaat = 0;
                } else {
                    resultaat = 1;
                }
            } else {
                // Sortering op ingangsdatum aflopend (jongste bovenaan).
                resultaat = new DatumComparator().compare(o1.getInhoud().getDatumIngang(), o2.getInhoud().getDatumIngang());

                // Sortering op einddatum aflopend indien sortering op ingangsdatum gelijk geeft.
                if (resultaat == 0) {
                    resultaat = new DatumComparator().compare(o1.getInhoud().getDatumEinde(), o2.getInhoud().getDatumEinde());
                }
            }

            return resultaat;
        }

    }

    /**
     * Volgorde voor expressies.
     */
    private static class DienstbundelLo3RubriekenLijstComparator implements Comparator<BrpDienstbundelLo3Rubriek>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(final BrpDienstbundelLo3Rubriek o1, final BrpDienstbundelLo3Rubriek o2) {

            final int resultaat;

            if (o1.getConversieRubriek() == null) {
                if (o2.getConversieRubriek() == null) {
                    resultaat = 0;
                } else {
                    resultaat = 1;
                }
            } else {
                resultaat = o1.getConversieRubriek().compareTo(o2.getConversieRubriek());
            }

            return resultaat;
        }
    }

    /**
     * Volgorde voor datums.
     */
    private static class DatumComparator implements Comparator<BrpDatum>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(final BrpDatum o1, final BrpDatum o2) {
            final int resultaat;

            if (o1 == null) {
                if (o2 == null) {
                    resultaat = 0;
                } else {
                    resultaat = 1;
                }
            } else {
                resultaat = o1.getWaarde().compareTo(o2.getWaarde());
            }

            return resultaat;
        }
    }

}

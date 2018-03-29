/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.regels.proces.foutmelding.Foutmelding;
import org.springframework.stereotype.Component;

/**
 * Splitsen ouders.
 */
@Component
public final class Lo3SplitsenGerelateerdeOuders {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final int STANDAARD_WAARDE = 0;

    private Lo3SplitsenGerelateerdeOuders() {
    }

    /**
     * Splits ouders.
     * @param stapel de Lo3 ouder stapel.
     * @return de in afzonderlijke ouders gesplitste stapels
     */
    public static List<OuderRelatie> splitsOuders(final Lo3Stapel<Lo3OuderInhoud> stapel) {
        LOG.debug("splitsOuders(stapel={})", stapel);
        if (stapel == null) {
            return new LinkedList<>();
        }

        final List<Lo3Categorie<Lo3OuderInhoud>> juisteRijen = bepaalJuisteRijen(stapel);
        final List<OuderRelatie> ouderRelaties = splitsStapel(juisteRijen);
        bepaalTeGebruikenVoorkomens(ouderRelaties);
        beeindigOuderRelaties(ouderRelaties);
        return ouderRelaties;
    }

    private static void bepaalTeGebruikenVoorkomens(final List<OuderRelatie> ouderRelaties) {
        for (OuderRelatie relatie : ouderRelaties) {
            relatie.bepaalTeGebruikenRij();
        }
    }

    private static void beeindigOuderRelaties(final List<OuderRelatie> ouderRelaties) {
        OuderRelatie teBeeindigenOuderRelatie = null;
        for (OuderRelatie relatie : ouderRelaties) {
            if (teBeeindigenOuderRelatie != null) {
                teBeeindigenOuderRelatie.beeindig(relatie.getTeGebruikenRecord());
            }
            teBeeindigenOuderRelatie = relatie;
        }
    }

    /**
     * het eigenlijke splitsen. We groeperen alle rijen op basis van 6210 waarbij we voor elke punt ouder (geslachtsnaam
     * "." en 6210 = standaard waarde) als seperate relatie aanmerken. De groep met een standaardwaarde in 6210 wordt
     * vervolgens gesorteerd op 8510. Nu splitsen we deze groep op basis van Anummer waarbij er alleen een splitsing
     * plaatsvind indien het volgende Anummer verschilt maar het huidige Anummer niet NULL is.
     * @param orgineleRijen List<Lo3Categorie<Lo3OuderInhoud>> juisteRijen
     * @return List<OuderRelatie>.r
     */
    private static List<OuderRelatie> splitsStapel(final List<Lo3Categorie<Lo3OuderInhoud>> orgineleRijen) {
        final List<OuderRelatie> ouderRelaties = new LinkedList<>();
        final List<Lo3Categorie<Lo3OuderInhoud>> rijen = new LinkedList<>(orgineleRijen);
        // Orginele rijen sorteren op herkomst
        orgineleRijen.sort(new HerkomstComparator());
        // Juiste rijen sorteren op 85.10
        rijen.sort(new Comparator8510());

        Integer vorige6210 = null;
        Lo3String laatsteANummer = null;
        OuderRelatie ouderRelatie = null;

        for (final Lo3Categorie<Lo3OuderInhoud> rij : rijen) {
            final Lo3OuderInhoud ouderInhoud = rij.getInhoud();
            final Integer huidige6210 =
                    ouderInhoud.getFamilierechtelijkeBetrekking() != null ? ouderInhoud.getFamilierechtelijkeBetrekking().getIntegerWaarde() : null;
            final Lo3String aNummer = ouderInhoud.getaNummer();

            if (huidige6210 == null) {
                if (ouderRelatie != null) {
                    if (!ouderRelatie.isJuridischGeenOuder()) {
                        ouderRelatie = slaHuidigeOuderRelatieOpEnMaakNieuwe(ouderRelaties, ouderRelatie, rij);
                        ouderRelatie.setIsJuridischGeenOuder();
                    } else {
                        ouderRelatie.toevoegen(rij);
                    }
                }
            } else if (huidige6210 == STANDAARD_WAARDE) {
                if (ouderInhoud.getGeslachtsnaam() != null && ".".equals(ouderInhoud.getGeslachtsnaam().getWaarde())) {
                    ouderRelatie = slaHuidigeOuderRelatieOpEnMaakNieuwe(ouderRelaties, ouderRelatie, rij);
                    // Expliciet toevoegen van punt ouder aan de lijst van ouder relaties.
                    ouderRelaties.add(ouderRelatie);
                    // Null maken van ouderRelatie zorgt ervoor dat deze niet nog een keer wordt toegevoegd aan de
                    // lijst.
                    ouderRelatie = null;
                    laatsteANummer = null;
                } else if (!Objects.equals(vorige6210, STANDAARD_WAARDE)) {
                    ouderRelatie = slaHuidigeOuderRelatieOpEnMaakNieuwe(ouderRelaties, ouderRelatie, rij);
                    laatsteANummer = aNummer;
                } else {
                    // Vorige rij was ook standaardwaarde
                    if (wijzigOpBasisVanAnummer(aNummer, laatsteANummer)) {
                        ouderRelatie = slaHuidigeOuderRelatieOpEnMaakNieuwe(ouderRelaties, ouderRelatie, rij);
                    } else {
                        if (ouderRelatie == null) {
                            ouderRelatie = new OuderRelatie(rij);
                        } else {
                            ouderRelatie.toevoegen(rij);
                        }
                    }
                    laatsteANummer = aNummer;
                }
            } else if (!huidige6210.equals(vorige6210)) {
                ouderRelatie = slaHuidigeOuderRelatieOpEnMaakNieuwe(ouderRelaties, ouderRelatie, rij);
            } else {
                ouderRelatie.toevoegen(rij);
            }
            vorige6210 = huidige6210;
        }

        if (ouderRelatie != null) {
            ouderRelaties.add(ouderRelatie);
        }

        if (ouderRelaties.size() > 1 && ouderRelaties.stream().filter(rel -> !rel.isJuridischGeenOuder()).count() > 1) {
            Foutmelding.logMeldingFoutInfo(rijen.get(rijen.size() - 1).getLo3Herkomst(), SoortMeldingCode.BIJZ_CONV_LB016, null);
        }

        ouderRelaties.sort(new Comparator8510OuderRelaties());
        return ouderRelaties;
    }

    private static OuderRelatie slaHuidigeOuderRelatieOpEnMaakNieuwe(
            final List<OuderRelatie> ouderRelaties,
            final OuderRelatie ouderRelatie,
            final Lo3Categorie<Lo3OuderInhoud> rij) {
        if (ouderRelatie != null) {
            ouderRelaties.add(ouderRelatie);
        }
        return new OuderRelatie(rij);
    }

    /**
     * wijzigen als:. laatste != null en huidige == null laatste != null en huidige != null en laatste != huidige
     * @param aNummer huidig A nummer
     * @param laatsteANummer vorig A nummer
     * @return true als deze niet gelijk zijn.
     */
    private static boolean wijzigOpBasisVanAnummer(final Lo3String aNummer, final Lo3String laatsteANummer) {
        final boolean vorigAnummerGevuldHuidigeNiet = isInhoudelijkGevuld(laatsteANummer) && !isInhoudelijkGevuld(aNummer);
        final boolean aNummersInhoudelijkOngelijk =
                isInhoudelijkGevuld(laatsteANummer) && isInhoudelijkGevuld(aNummer) && !laatsteANummer.equalsWaarde(aNummer);

        return vorigAnummerGevuldHuidigeNiet || aNummersInhoudelijkOngelijk;
    }

    private static boolean isInhoudelijkGevuld(final Lo3String nummer) {
        return nummer != null && nummer.isInhoudelijkGevuld();
    }

    private static List<Lo3Categorie<Lo3OuderInhoud>> bepaalJuisteRijen(final Lo3Stapel<Lo3OuderInhoud> verbintenis) {
        final List<Lo3Categorie<Lo3OuderInhoud>> result = new ArrayList<>();
        for (final Lo3Categorie<Lo3OuderInhoud> rij : verbintenis) {
            if (!rij.getHistorie().isOnjuist()) {
                result.add(rij);
            }
        }
        return result;
    }

    /**
     * Sorteer categorieen op herkomst, van oud naar nieuw.
     */
    private static final class HerkomstComparator implements Comparator<Lo3Categorie<?>>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(final Lo3Categorie<?> o1, final Lo3Categorie<?> o2) {
            return o2.getLo3Herkomst().getVoorkomen() - o1.getLo3Herkomst().getVoorkomen();
        }
    }
}

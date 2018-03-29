/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Validatie;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;

/**
 * Inner class om een ouder relatie weer te geven en handelingen op uit te voeren.
 */
public final class OuderRelatie {
    private final List<Lo3Categorie<Lo3OuderInhoud>> records = new ArrayList<>();
    private Lo3Categorie<Lo3OuderInhoud> actueelRecord;
    private Lo3Categorie<Lo3OuderInhoud> teGebruikenRecord;
    private Lo3Categorie<Lo3OuderInhoud> beeindigingsRecord;
    private boolean juridischGeenOuder;

    /**
     * constructor.
     * @param record Lo3Categorie<Lo3OuderInhoud>
     */
    OuderRelatie(final Lo3Categorie<Lo3OuderInhoud> record) {
        if (record.getLo3Herkomst().isLo3ActueelVoorkomen()) {
            actueelRecord = record;
        }
        toevoegen(record);
    }

    /**
     * bepaal in de stapel de rij welke gebruikt wordt.
     *
     * 1) als 1 record 6210 gelijk aan 8510. 2) als ingangsdatum is standaardwaarde. 3) de meest actuele
     */
    void bepaalTeGebruikenRij() {
        if (records.size() == 1) {
            teGebruikenRecord = records.get(0);
        } else {
            if (bevatActueel() && actueelRecord.getOnderzoek() != null) {
                teGebruikenRecord = actueelRecord;
            } else {
                bepaalTeGebruikenRijOpBasisVanRegels();
            }
        }
    }

    private void bepaalTeGebruikenRijOpBasisVanRegels() {
        boolean regel1 = false;
        boolean regel2 = false;
        boolean gebruikRegel3 = false;
        for (final Lo3Categorie<Lo3OuderInhoud> record : records) {
            final Lo3Datum familierechtelijkeBetrekking = record.getInhoud().getFamilierechtelijkeBetrekking();
            final Lo3Datum ingangsdatumGeldigheid = record.getHistorie().getIngangsdatumGeldigheid();
            if (familierechtelijkeBetrekking != null
                    && Objects.equals(familierechtelijkeBetrekking.getIntegerWaarde(), ingangsdatumGeldigheid.getIntegerWaarde())) {
                if (!regel1) {
                    teGebruikenRecord = record;
                    regel1 = true;
                    gebruikRegel3 = false;
                } else {
                    gebruikRegel3 = true;
                }
            } else if (!regel1 && Objects.equals(0, ingangsdatumGeldigheid.getIntegerWaarde())) {
                if (!regel2) {
                    teGebruikenRecord = record;
                    regel2 = true;
                    gebruikRegel3 = false;
                } else {
                    gebruikRegel3 = true;
                }
            }
        }
        if (gebruikRegel3 || (!regel1 && !regel2)) {
            teGebruikenRecord = records.get(records.size() - 1);
        }
    }

    /**
     * Beeindig deze ouderRelatie met een volgende ouderRelatie.
     * @param ouderRelatie ouderRelatie welke deze beeindigd
     */
    void beeindig(final Lo3Categorie<Lo3OuderInhoud> ouderRelatie) {
        // maak eindrecord
        if (actueelRecord == null && !juridischGeenOuder) {
            final Lo3OuderInhoud inhoud = new Lo3OuderInhoud.Builder().build();
            Lo3Datum ingangsdatumBeindiging = ouderRelatie.getInhoud().getFamilierechtelijkeBetrekking();
            if (!Lo3Validatie.isElementGevuld(ingangsdatumBeindiging)) {
                ingangsdatumBeindiging = ouderRelatie.getHistorie().getIngangsdatumGeldigheid();
            }

            final Lo3Historie historie = new Lo3Historie(null, ingangsdatumBeindiging, ouderRelatie.getHistorie().getDatumVanOpneming());
            final Lo3Documentatie documentatie = ouderRelatie.getDocumentatie();

            final Lo3Herkomst herkomst = ouderRelatie.getLo3Herkomst();
            beeindigingsRecord = new Lo3Categorie<>(inhoud, documentatie, historie, herkomst, true);
            records.add(beeindigingsRecord);
        }
    }

    /**
     * Geeft het record terug dat gebruikt gaat worden voor de persoonsgegevens.
     * @return de categorie die voor persoongegevens gebruikt gaat worden
     */
    Lo3Categorie<Lo3OuderInhoud> getGerelateerdeGegevens() {
        // Als actueel in de stapel voorkomt, dan is actueelVoorkomen niet null
        Lo3Categorie<Lo3OuderInhoud> gerelateerde = actueelRecord;
        if (gerelateerde == null) {
            final List<Lo3Categorie<Lo3OuderInhoud>> gevuldeRijen = new ArrayList<>();
            for (final Lo3Categorie<Lo3OuderInhoud> ouderRij : records) {
                // Als de rij onjuist of leeg is, dan wordt deze niet gebruikt voor gerelateerde gegevens
                if (ouderRij.getInhoud().isLeeg()) {
                    continue;
                }

                gevuldeRijen.add(ouderRij);
            }

            if (!gevuldeRijen.isEmpty()) {
                gevuldeRijen.sort(Lo3Categorie.DATUM_GELDIGHEID);
                gerelateerde = gevuldeRijen.get(0);
            }
        }
        return gerelateerde;
    }

    /**
     * Voegt een record toe en sorteerd de lijst net records op basis van 8510.
     * @param record final Lo3Categorie<Lo3OuderInhoud>
     */
    public void toevoegen(final Lo3Categorie<Lo3OuderInhoud> record) {
        if (actueelRecord == null && record.getLo3Herkomst().isLo3ActueelVoorkomen()) {
            actueelRecord = record;
        }
        records.add(record);
        records.sort(new Comparator8510());
    }

    /**
     * Geef de waarde van records.
     * @return records
     */
    public List<Lo3Categorie<Lo3OuderInhoud>> getRecords() {
        return records;
    }

    /**
     * Geef de waarde van stapel.
     * @return stapel
     */
    public Lo3Stapel<Lo3OuderInhoud> getStapel() {
        return new Lo3Stapel<>(records);
    }

    /**
     * geeft aan of ouderRelatie de actueel bevat.
     * @return true als actueel
     */
    boolean bevatActueel() {
        return actueelRecord != null;
    }

    /**
     * geeft aan of stapel juridish geen Ouder is.
     * @return boolean
     */
    boolean isJuridischGeenOuder() {
        return juridischGeenOuder;
    }

    /**
     * set juridisch geen ouder.
     */
    void setIsJuridischGeenOuder() {
        this.juridischGeenOuder = true;
    }

    /**
     * Geeft de categorie terug welke gebruikt moet worden om de
     * {@link nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOuderInhoud} te vullen.
     * @return een {@link Lo3Categorie}
     */
    Lo3Categorie<Lo3OuderInhoud> getTeGebruikenRecord() {
        return teGebruikenRecord;
    }

    /**
     * Geeft de categorie terug welke gebruikt moet worden om de
     * {@link nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOuderInhoud} te beeindigen.
     * @return een {@link Lo3Categorie}
     */
    Lo3Categorie<Lo3OuderInhoud> getBeeindigingsRecord() {
        return beeindigingsRecord;
    }

    /**
     * Geeft aan of deze {@link OuderRelatie} opgenomen moet worden in de BRP.
     * @return true als deze {@link OuderRelatie} opgenomen moet worden
     */
    boolean moetOpgenomenWorden() {
        return teGebruikenRecord != null && getGerelateerdeGegevens() != null;
    }
}

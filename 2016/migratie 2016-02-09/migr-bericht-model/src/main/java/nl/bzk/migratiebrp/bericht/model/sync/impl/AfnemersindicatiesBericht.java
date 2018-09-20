/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3Format;
import nl.bzk.migratiebrp.bericht.model.lo3.parser.SimpleParser;
import nl.bzk.migratiebrp.bericht.model.sync.AbstractSyncBerichtZonderGerelateerdeInformatie;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AfnemersindicatieRecordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AfnemersindicatiesType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ObjectFactory;
import nl.bzk.migratiebrp.bericht.model.sync.xml.SyncXml;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3Afnemersindicatie;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3AfnemersindicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;

/**
 * Initiele vulling van afnemers indicaties.
 */
public final class AfnemersindicatiesBericht extends AbstractSyncBerichtZonderGerelateerdeInformatie {
    private static final long serialVersionUID = 1L;

    private final AfnemersindicatiesType afnemersindicatiesType;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public AfnemersindicatiesBericht() {
        this(new AfnemersindicatiesType());
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een bericht te maken.
     *
     * @param afnemersindicatiesType
     *            het afnemersindicatiesType type
     */
    public AfnemersindicatiesBericht(final AfnemersindicatiesType afnemersindicatiesType) {
        super("Afnemersindicaties");
        this.afnemersindicatiesType = afnemersindicatiesType;
    }

    /* ************************************************************************************************************* */

    /**
     * Geef de inhoud van het bericht als een Lo3Afnemersindicatie object.
     *
     * @return Lo3Afnemersindicatie object
     */
    public Lo3Afnemersindicatie getAfnemersindicaties() {
        final Long aNummer = Long.valueOf(afnemersindicatiesType.getANummer());
        final List<Lo3Stapel<Lo3AfnemersindicatieInhoud>> afnemersindicatieStapels = new ArrayList<>();

        final List<AfnemersindicatieRecordType> afnemersindicatieRecords = afnemersindicatiesType.getAfnemersindicatie();
        Collections.sort(afnemersindicatieRecords, new AfnemersindicatieComparator());

        List<Lo3Categorie<Lo3AfnemersindicatieInhoud>> huidigeStapel = null;
        String huidigeStapelNummer = null;
        for (final AfnemersindicatieRecordType record : afnemersindicatieRecords) {
            if (!record.getStapelNummer().equals(huidigeStapelNummer)) {
                if (huidigeStapel != null) {
                    afnemersindicatieStapels.add(new Lo3Stapel<>(huidigeStapel));
                }

                huidigeStapelNummer = record.getStapelNummer();
                huidigeStapel = new ArrayList<>();
            }

            huidigeStapel.add(maakAfnemersindicatie(record));
        }

        if (huidigeStapel != null) {
            afnemersindicatieStapels.add(new Lo3Stapel<>(huidigeStapel));
        }

        return new Lo3Afnemersindicatie(aNummer, afnemersindicatieStapels);
    }

    private Lo3Categorie<Lo3AfnemersindicatieInhoud> maakAfnemersindicatie(final AfnemersindicatieRecordType record) {
        final Lo3AfnemersindicatieInhoud inhoud = new Lo3AfnemersindicatieInhoud(SimpleParser.parseInteger(record.getAfnemerCode()));

        final int stapel = SimpleParser.parseInteger(record.getStapelNummer());
        final int voorkomen = SimpleParser.parseInteger(record.getVolgNummer());
        final Lo3Datum datumIngang = SimpleParser.parseLo3Datum(record.getGeldigheidStartDatum().toString());

        final Lo3Historie historie = new Lo3Historie(null, datumIngang, datumIngang);

        final Lo3Herkomst herkomst = new Lo3Herkomst(voorkomen == 0 ? Lo3CategorieEnum.CATEGORIE_14 : Lo3CategorieEnum.CATEGORIE_64, stapel, voorkomen);

        return new Lo3Categorie<>(inhoud, null, historie, herkomst);
    }

    /**
     * Zet de inhoud van het bericht als een Lo3Afnemersindicatie object.
     *
     * @param afnemersindicaties
     *            Lo3Afnemersindicatie object
     */
    public void setAfnemersindicaties(final Lo3Afnemersindicatie afnemersindicaties) {
        afnemersindicatiesType.setANummer(Lo3Format.format(afnemersindicaties.getANummer()));
        afnemersindicatiesType.getAfnemersindicatie().clear();

        for (final Lo3Stapel<Lo3AfnemersindicatieInhoud> afnemersindicatieStapel : afnemersindicaties.getAfnemersindicatieStapels()) {
            afnemersindicatiesType.getAfnemersindicatie().addAll(maakAfnemersindicatieRecords(afnemersindicatieStapel));
        }
    }

    private List<AfnemersindicatieRecordType> maakAfnemersindicatieRecords(final Lo3Stapel<Lo3AfnemersindicatieInhoud> stapel) {
        final List<AfnemersindicatieRecordType> result = new ArrayList<>();

        for (final Lo3Categorie<Lo3AfnemersindicatieInhoud> categorie : stapel) {
            result.add(maakAfnemersindicatieRecord(categorie));
        }

        return result;
    }

    private AfnemersindicatieRecordType maakAfnemersindicatieRecord(final Lo3Categorie<Lo3AfnemersindicatieInhoud> categorie) {
        final AfnemersindicatieRecordType result = new AfnemersindicatieRecordType();
        result.setAfnemerCode(Lo3Format.format(categorie.getInhoud().getAfnemersindicatie()));
        result.setGeldigheidStartDatum(new BigInteger(Lo3Format.format(categorie.getHistorie().getDatumVanOpneming())));
        result.setStapelNummer(Lo3Format.format(categorie.getLo3Herkomst().getStapel()));
        result.setVolgNummer(Lo3Format.format(categorie.getLo3Herkomst().getVoorkomen()));

        return result;
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() {
        return SyncXml.SINGLETON.elementToString(new ObjectFactory().createAfnemersindicaties(afnemersindicatiesType));
    }

    /**
     * Sorteert de afnemerindicaties op stapels en voorkomens.
     */
    private static class AfnemersindicatieComparator implements Comparator<AfnemersindicatieRecordType>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(final AfnemersindicatieRecordType o1, final AfnemersindicatieRecordType o2) {
            int result = o1.getStapelNummer().compareTo(o2.getStapelNummer());
            if (result == 0) {
                result = o1.getVolgNummer().compareTo(o2.getVolgNummer());
            }
            return result;
        }
    }

}

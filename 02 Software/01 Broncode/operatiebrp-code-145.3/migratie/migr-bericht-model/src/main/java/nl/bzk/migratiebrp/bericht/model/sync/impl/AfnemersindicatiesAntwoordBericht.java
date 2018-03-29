/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.sync.AbstractSyncBerichtZonderGerelateerdeInformatie;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AfnemersindicatieAntwoordRecordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AfnemersindicatiesAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ObjectFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.xml.SyncXml;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3Afnemersindicatie;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3AfnemersindicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.logging.LogRegel;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;

/**
 * Afnemersindicaties antwoord bericht.
 */
public final class AfnemersindicatiesAntwoordBericht extends AbstractSyncBerichtZonderGerelateerdeInformatie {
    private static final long serialVersionUID = 1L;

    private final AfnemersindicatiesAntwoordType afnemersindicatiesAntwoordType;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public AfnemersindicatiesAntwoordBericht() {
        this(new AfnemersindicatiesAntwoordType());
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een bericht te maken.
     * @param afnemersindicatiesType het afnemersindicatiesType type
     */
    public AfnemersindicatiesAntwoordBericht(final AfnemersindicatiesAntwoordType afnemersindicatiesType) {
        super("AfnemersindicatiesAntwoord");
        afnemersindicatiesAntwoordType = afnemersindicatiesType;
    }

    /* ************************************************************************************************************* */

    public List<AfnemersindicatieAntwoordRecordType> getAfnemersindicaties() {
        return afnemersindicatiesAntwoordType.getAfnemersindicatie();
    }


    /**
     * Maak voor elke stapel in het vraag bericht een antwoord record en bepaal de logging die daarbij hoort
     * om de foutmelding te vullen.
     * @param afnemersindicatiesBericht het vraag bericht
     * @param logging de logging
     */
    public void verwerkLogging(AfnemersindicatiesBericht afnemersindicatiesBericht, Set<LogRegel> logging) {
        Lo3Afnemersindicatie lo3Afnemersindicatie = afnemersindicatiesBericht.getAfnemersindicaties();
        for (Lo3Stapel<Lo3AfnemersindicatieInhoud> afnemerindicatie : lo3Afnemersindicatie.getAfnemersindicatieStapels()) {
            AfnemersindicatieAntwoordRecordType antwoordRecord = new AfnemersindicatieAntwoordRecordType();
            antwoordRecord.setStapelNummer(afnemerindicatie.get(0).getLo3Herkomst().getStapel());

            verwerkLogging(antwoordRecord, logging);

            getAfnemersindicaties().add(antwoordRecord);
        }
    }


    private void verwerkLogging(AfnemersindicatieAntwoordRecordType antwoordRecord, Set<LogRegel> logging) {
        int maxSeverity = 0;
        final SortedSet<String> codes = new TreeSet<>();

        for (final LogRegel logRegel : logging) {
            if (logRegel.getLo3Herkomst().getStapel() == antwoordRecord.getStapelNummer()) {
                codes.add(logRegel.getSoortMeldingCode().toString());
                if(maxSeverity < logRegel.getSeverity().getSeverity()) {
                    maxSeverity = logRegel.getSeverity().getSeverity();
                }
            }
        }

        if(!codes.isEmpty()) {
            antwoordRecord.setFoutmelding(codes.stream().collect(Collectors.joining(", ")));
        }

        if(maxSeverity >= LogSeverity.ERROR.getSeverity()) {
            antwoordRecord.setStatus(StatusType.FOUT);
        } else {
            antwoordRecord.setStatus(StatusType.TOEGEVOEGD);
        }
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() throws BerichtInhoudException {
        return SyncXml.SINGLETON.elementToString(new ObjectFactory().createAfnemersindicatiesAntwoord(afnemersindicatiesAntwoordType));
    }
}

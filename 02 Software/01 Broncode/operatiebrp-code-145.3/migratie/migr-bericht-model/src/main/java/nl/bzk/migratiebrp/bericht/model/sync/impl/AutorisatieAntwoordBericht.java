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
import nl.bzk.migratiebrp.bericht.model.sync.generated.AutorisatieAntwoordRecordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AutorisatieAntwoordRecordsType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AutorisatieAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ObjectFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.xml.SyncXml;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3Autorisatie;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3AutorisatieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogRegel;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;

/**
 * Afnemersindicaties antwoord bericht.
 */
public final class AutorisatieAntwoordBericht extends AbstractSyncBerichtZonderGerelateerdeInformatie {
    private static final long serialVersionUID = 1L;

    private final AutorisatieAntwoordType autorisatieAntwoordType;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public AutorisatieAntwoordBericht() {
        this(new AutorisatieAntwoordType());
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een bericht te maken.
     * @param autorisatieAntwoordType het autorisatieAntwoordType type
     */
    public AutorisatieAntwoordBericht(final AutorisatieAntwoordType autorisatieAntwoordType) {
        super("AutorisatieAntwoord");
        this.autorisatieAntwoordType = autorisatieAntwoordType;
    }

    /* ************************************************************************************************************* */


    /**
     * Geeft de autorisatie tabel regels {@link AutorisatieAntwoordRecordType} van het bericht terug.
     * @return De autorisatie tabel regels van het bericht.
     */
    public List<AutorisatieAntwoordRecordType> getAutorisatieTabelRegels() {
        if(autorisatieAntwoordType.getAutorisatieTabelRegels() == null) {
            autorisatieAntwoordType.setAutorisatieTabelRegels(new AutorisatieAntwoordRecordsType());
        }
        return autorisatieAntwoordType.getAutorisatieTabelRegels().getAutorisatieTabelRegel();
    }

    /**
     * Maak voor elke autorisatie tabel regel in het vraag bericht een antwoord record en bepaal de logging die daarbij hoort
     * om de status en de melding te vullen.
     * @param autorisatieBericht het vraag bericht
     * @param logging de loggings
     */
    public void verwerkLogging(AutorisatieBericht autorisatieBericht, Set<LogRegel> logging) {
        Lo3Autorisatie lo3Autorisatie = autorisatieBericht.getAutorisatie();
        for (Lo3Categorie<Lo3AutorisatieInhoud> autorisatieRecord : lo3Autorisatie.getAutorisatieStapel()) {
            AutorisatieAntwoordRecordType antwoordRecord = new AutorisatieAntwoordRecordType();
            antwoordRecord.setAutorisatieId(autorisatieRecord.getInhoud().getAutorisatieId());

            verwerkLogging(antwoordRecord, autorisatieRecord.getLo3Herkomst(), logging);

            antwoordRecord.setFoutmelding(maakFoutmelding(autorisatieRecord.getLo3Herkomst(), logging));
            getAutorisatieTabelRegels().add(antwoordRecord);
        }
    }

    private void verwerkLogging(AutorisatieAntwoordRecordType antwoordRecord, Lo3Herkomst lo3Herkomst, Set<LogRegel> logging) {
        int maxSeverity = 0;
        final SortedSet<String> codes = new TreeSet<>();

        for (final LogRegel logRegel : logging) {
            if (matches(lo3Herkomst, logRegel.getLo3Herkomst())) {
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

    private String maakFoutmelding(final Lo3Herkomst lo3Herkomst, final Set<LogRegel> logging) {
        final SortedSet<String> codes = new TreeSet<>();

        for (final LogRegel logRegel : logging) {
            if (matches(lo3Herkomst, logRegel.getLo3Herkomst())) {
                codes.add(logRegel.getSoortMeldingCode().toString());
            }
        }

        return codes.isEmpty() ? null : codes.stream().collect(Collectors.joining(", "));
    }

    private boolean matches(Lo3Herkomst lookingFor, Lo3Herkomst fromLoop) {
        if (fromLoop.getVoorkomen() == -1) {
            return true;
        } else {
            return lookingFor.getVoorkomen() == fromLoop.getVoorkomen();
        }
    }


    /* ************************************************************************************************************* */

    @Override
    public String format() throws BerichtInhoudException {
        return SyncXml.SINGLETON.elementToString(new ObjectFactory().createAutorisatieAntwoord(autorisatieAntwoordType));
    }

}

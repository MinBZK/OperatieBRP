/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.archivering.service.algemeen;

import java.sql.Timestamp;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Bericht;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BerichtPersoon;
import nl.bzk.brp.archivering.domain.algemeen.ArchiveringOpdracht;

/**
 * Converteert ArchiveringOpdracht objecten naar Bericht entities.
 */
public interface ArchiveringOpdrachtConverteerder {

    /**
     * Converteert een {@link ArchiveringOpdracht} naar een {@link Bericht}
     * @param opdracht de te converteren archivering opdracht
     * @return een bericht
     */
    static Bericht converteer(final ArchiveringOpdracht opdracht) {
        final Bericht archiefBericht = new Bericht(opdracht.getRichting(), Timestamp.valueOf(opdracht.getTijdstipRegistratie().toLocalDateTime()));
        archiefBericht.setAdministratieveHandeling(opdracht.getAdministratieveHandelingId());
        archiefBericht.setBijhoudingResultaat(opdracht.getBijhoudingResultaat());
        archiefBericht.setCrossReferentieNummer(opdracht.getCrossReferentienummer());
        archiefBericht.setData(opdracht.getData());
        archiefBericht.setDienst(opdracht.getDienstId());
        if (opdracht.getHoogsteMeldingsNiveau() != null) {
            archiefBericht.setHoogsteMeldingsNiveau(opdracht.getHoogsteMeldingsNiveau());
        }
        archiefBericht.setLeveringsAutorisatie(opdracht.getLeveringsAutorisatieId());
        archiefBericht.setOntvangendePartij(opdracht.getOntvangendePartijId());
        archiefBericht.setReferentieNummer(opdracht.getReferentienummer());
        archiefBericht.setRol(opdracht.getRolId());
        if (opdracht.getSoortBericht() != null) {
            archiefBericht.setSoortBericht(opdracht.getSoortBericht());
        }
        if (opdracht.getSoortSynchronisatie() != null) {
            archiefBericht.setSoortSynchronisatie(opdracht.getSoortSynchronisatie());
        }

        opdracht.getTeArchiverenPersonen()
                .forEach(p -> archiefBericht.addPersoon(new BerichtPersoon(archiefBericht, p,
                        Timestamp.valueOf(archiefBericht.getDatumTijdRegistratie().toLocalDateTime()))));

        if (opdracht.getTijdstipOntvangst() != null) {
            archiefBericht.setDatumTijdOntvangst(Timestamp.valueOf(opdracht.getTijdstipOntvangst().toLocalDateTime()));
        }
        if (opdracht.getTijdstipVerzending() != null) {
            archiefBericht.setDatumTijdVerzending(Timestamp.valueOf(opdracht.getTijdstipVerzending().toLocalDateTime()));
        }
        if (opdracht.getVerwerkingsresultaat() != null) {
            archiefBericht.setVerwerkingsResultaat(opdracht.getVerwerkingsresultaat());
        }
        if (opdracht.getVerwerkingswijze() != null) {
            archiefBericht.setVerwerkingswijze(opdracht.getVerwerkingswijze());
        }
        archiefBericht.setZendendePartij(opdracht.getZendendePartijId());
        archiefBericht.setZendendeSysteem(opdracht.getZendendeSysteem());
        return archiefBericht;
    }
}

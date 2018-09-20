package nl.bzk.brp.funqmachine.processors.relateren

import groovy.sql.GroovyRowResult
import nl.bzk.brp.funqmachine.configuratie.Database
import nl.bzk.brp.funqmachine.processors.SqlProcessor
import nl.bzk.brp.relateren.business.RelateerPersoon
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Biedt toegang tot het relateren component.
 */
@Service
class RelateringProcessor {

    @Autowired
    private RelateerPersoon relateerPersoon

    void relateerPersoon(final int bsn) {
        // Opvragen van persoon uit DB, aangezien relateren met technisch ID werkt
        List<GroovyRowResult> results = new SqlProcessor(Database.KERN).voerUit("SELECT id FROM kern.pers WHERE bsn = $bsn AND srt = 1") as List<GroovyRowResult>

        // Relateren van de persoon adhv het DB-id.
        if (results.size() > 1) {
            def mesg = "Meer dan 1 persoon ingeschreven gevonden voor BSN $bsn"
            LOGGER.error(mesg)
            throw new Exception(mesg)
        }

        if (results.size() == 1) {
            relateerPersoon.relateerOpBasisVanID(results.get(0).get("id") as long);
        }
    }
}

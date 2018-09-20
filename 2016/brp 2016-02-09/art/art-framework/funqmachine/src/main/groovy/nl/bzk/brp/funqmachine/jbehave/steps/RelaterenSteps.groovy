package nl.bzk.brp.funqmachine.jbehave.steps

import static org.junit.Assert.assertTrue

import groovy.sql.GroovyRowResult
import nl.bzk.brp.funqmachine.configuratie.Database
import nl.bzk.brp.funqmachine.processors.SqlProcessor
import nl.bzk.brp.funqmachine.processors.relateren.RelateringProcessor
import org.jbehave.core.annotations.Then
import org.jbehave.core.annotations.When
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired

/**
 * Stappen om persoonslijsten te relateren met elkaar.
 */
@Steps
class RelaterenSteps {
    private static final Logger LOGGER = LoggerFactory.getLogger(RelaterenSteps.class)

    @Autowired
    private RelateringProcessor relateringProcessor;

    @When("persoon \$bsn wordt gerelateerd")
    void relateerPersoon(int bsn) {
        relateringProcessor.relateerPersoon(bsn)
    }

    @Then("persoon \$bsn is niet veranderd muv de volgende groepen \$groepen")
    void controleerPersoonNietVeranderd(int bsn, List<String> groepen) {

    }

    @Then("persoon \$bsn heeft een huwelijk met datum aanvang \$datumAanvang en ingeschreven persoon \$gerelateerdeBsn")
    void persoonHeeftGeldigHuwelijk(int bsn, int datumAanvang, int gerelateerdeBsn) {
        def query = """select count(1) from kern.pers p
            join kern.betr b on b.pers = p.id
            join kern.relatie r on r.id = b.relatie
            join kern.his_relatie rh on rh.relatie = r.id
            where p.bsn = $gerelateerdeBsn
            and p.srt = 1
            and r.id in (
                    select r.id from kern.relatie r
                    join kern.betr b on b.relatie = r.id
                    join kern.pers p on p.id = b.pers
                    where p.bsn = $bsn
                    and p.srt = 1
                    and r.dataanv = $datumAanvang
                )
            and rh.tsverval is null and rh.actieverval is null and rh.indvoorkomentbvlevmuts is null"""

        List<GroovyRowResult> results = voerQueryUit(query)
        assertTrue("Geen relatie met ingeschreven persoon gevonden voor de gegeven parameters", results.size() == 1 && results.get(0).get("count") == 1);
    }

    @Then("persoon \$bsn heeft een vervallen huwelijk met datum aanvang \$datumAanvang en onbekende persoon \$gerelateerdeBsn")
    void persoonHeeftVervallenHuwelijk(int bsn, int datumAanvang, int gerelateerdeBsn) {
        def query = """select count(1) from kern.pers p
            join kern.betr b on b.pers = p.id
            join kern.relatie r on r.id = b.relatie
            join kern.his_relatie rh on rh.relatie = r.id
            where p.bsn = $gerelateerdeBsn
            and p.srt = 3
            and r.id in (
                    select r.id from kern.relatie r
                    join kern.betr b on b.relatie = r.id
                    join kern.pers p on p.id = b.pers
                    where p.bsn = $bsn
                    and p.srt = 1
                    and r.dataanv = $datumAanvang
                )
            and rh.tsverval is null and rh.actieverval is null"""

        List<GroovyRowResult> results = voerQueryUit(query)
        assertTrue("Geen vervallen relatie met onbekende persoon gevonden voor de gegeven parameters", results.size() == 1 && results.get(0).get
            ("count") == 1);
    }

    private List<GroovyRowResult> voerQueryUit(GString query) {
        List<GroovyRowResult> results = new SqlProcessor(Database.KERN).voerUit(query) as List<GroovyRowResult>
        results
    }

    @Then("onbekende persoon \$bsn is vervallen")
    void onbekendePersoonIsVervallen(int bsn) {
        def query = """
            select distinct
                case when pg.tsverval is not null and pg.actieverval is not null then true else false end as pg,
                case when ps.tsverval is not null and ps.actieverval is not null then true else false end as ps,
                case when pga.tsverval is not null and pga.actieverval is not null then true else false end as pga,
                case when pi.tsverval is not null and pi.actieverval is not null then true else false end as pi
            from kern.pers p
            join kern.his_persgeboorte pg on pg.pers = p.id
            join kern.his_perssamengesteldenaam  ps on ps.pers = p.id
            join kern.his_persgeslachtsaand pga on pga.pers = p.id
            join kern.his_persids pi on pi.pers = p.id
            where p.bsn =$bsn
            and p.srt = 3"""
        List<GroovyRowResult> results = voerQueryUit(query)
        assert results.size() == 1
        results.each {
            assertTrue("Onbekende persoon in niet vervallen", it.get("pg") && it.get("pi") && it.get("ps") && it.get("pga"))
        }
    }
}

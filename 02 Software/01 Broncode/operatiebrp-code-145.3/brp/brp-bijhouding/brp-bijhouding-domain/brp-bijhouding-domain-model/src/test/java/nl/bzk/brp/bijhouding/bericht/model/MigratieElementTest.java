/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Aangever;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenWijzigingVerblijf;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import org.junit.Before;
import org.junit.Test;

public class MigratieElementTest extends AbstractElementTest {
    ElementBuilder builder = new ElementBuilder();

    @Before
    public void setup() {
        setupDynamischeStamTabellen();
    }

    @Test
    public void correct() throws Exception {
        final MigratieElement migElement = maakMigratieElement("0001", null, null, 'P', 'G');
        final List<MeldingElement> meldingen = migElement.valideerInhoud();
        assertEquals(0, meldingen.size());
    }

    @Test
    public void R1664() throws Exception {
        final MigratieElement migElement = maakMigratieElement("0002", null, null, 'P', 'G');
        final List<MeldingElement> meldingen = migElement.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1664, meldingen.get(0).getRegel());
    }

    @Test
    public void R1666() throws Exception {
        final MigratieElement migElement = maakMigratieElement("6030", null, null, 'P', 'G');
        final List<MeldingElement> meldingen = migElement.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1666, meldingen.get(0).getRegel());
    }

    @Test
    public void R1669_adresregel1() throws Exception {
        final MigratieElement migElement = maakMigratieElement("0000", "regel1", null, 'P', 'G');
        final List<MeldingElement> meldingen = migElement.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1669, meldingen.get(0).getRegel());
    }

    @Test
    public void R2361_RedenWijzigingOnbekendStamgegeven() throws Exception {
        final MigratieElement migElement = maakMigratieElement("0001", "regel1", null, 'Q', null);
        final List<MeldingElement> meldingen = migElement.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2361, meldingen.get(0).getRegel());
    }

    @Test
    public void R2362_RedenWijzigingOnbekend() throws Exception {
        final MigratieElement migElement = maakMigratieElement("0001", "regel1", null, '?', null);
        final List<MeldingElement> meldingen = migElement.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2362, meldingen.get(0).getRegel());
    }

    @Test
    public void R2362_RedenWijzigingTechnischeWijzigingBAG() throws Exception {
        final MigratieElement migElement = maakMigratieElement("0001", "regel1", null, 'B', null);
        final List<MeldingElement> meldingen = migElement.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2362, meldingen.get(0).getRegel());
    }

    @Test
    public void R2362_RedenWijzigingInfra() throws Exception {
        final MigratieElement migElement = maakMigratieElement("0001", "regel1", null, 'I', null);
        final List<MeldingElement> meldingen = migElement.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2362, meldingen.get(0).getRegel());
    }
    @Test
    public void R2362_RedenWijzigingNull() throws Exception {
        final MigratieElement migElement = maakMigratieElement("0001", "regel1", null, null, null);
        final List<MeldingElement> meldingen = migElement.valideerInhoud();
        assertEquals(0, meldingen.size());
    }
    @Test
    public void R2363_RedenWijzigingOnbekend() throws Exception {
        final MigratieElement migElement = maakMigratieElement("0001", "regel1", null, 'P', null);
        final List<MeldingElement> meldingen = migElement.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2363, meldingen.get(0).getRegel());
    }

    @Test
    public void R2366_AangeverOnbekendStamgegeven() throws Exception {
        final MigratieElement migElement = maakMigratieElement("0001", "regel1", null, 'P', 'Q');
        final List<MeldingElement> meldingen = migElement.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2366, meldingen.get(0).getRegel());
    }

    @Test
    public void R2427_AangeverBijWijzigingsCodeAmbtenaar() throws Exception {
        final MigratieElement migElement = maakMigratieElement("0001", "regel1", null, 'A', 'G');
        final List<MeldingElement> meldingen = migElement.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2427, meldingen.get(0).getRegel());
    }

    @Test
    public void R2427_AangeverBijWijzigingsCodePersoon() throws Exception {
        final MigratieElement migElement = maakMigratieElement("0001", "regel1", null, 'P', 'G');
        final List<MeldingElement> meldingen = migElement.valideerInhoud();
        assertEquals(0, meldingen.size());
    }

    @Test
    public void R2393_BuitenlandseAdresRegel4tm6Gevuld() throws Exception {
        final ElementBuilder.MigratieParameters migParams = new ElementBuilder.MigratieParameters('P', 'G', "0001");
        migParams.adresRegel1 = new StringElement("goed");
        migParams.adresRegel2 = new StringElement("goed");
        migParams.adresRegel3 = new StringElement("goed");
        migParams.adresRegel4 = new StringElement("fout");
        migParams.adresRegel5 = new StringElement("fout");
        migParams.adresRegel6 = new StringElement("fout");
        MigratieElement migratieElement = builder.maakMigratie("mig", migParams);
        List<MeldingElement> meldingen = migratieElement.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2393, meldingen.get(0).getRegel());
    }

    @Test
    public void R2394_BuitenlandseAdresRegel2Overgeslagen() throws Exception {
        final ElementBuilder.MigratieParameters migParams = new ElementBuilder.MigratieParameters('P', 'G', "0001");
        migParams.adresRegel1 = new StringElement("Regel1");
        migParams.adresRegel2 = null;
        migParams.adresRegel3 = new StringElement("Regel3");
        MigratieElement migratieElement = builder.maakMigratie("mig", migParams);
        List<MeldingElement> meldingen = migratieElement.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2394, meldingen.get(0).getRegel());
    }

    @Test
    public void R2394_BuitenlandseAdresRegel1tm5Overgeslagen() throws Exception {
        final ElementBuilder.MigratieParameters migParams = new ElementBuilder.MigratieParameters('P', 'G', "0001");
        migParams.adresRegel6 = new StringElement("Regel3");
        MigratieElement migratieElement = builder.maakMigratie("mig", migParams);
        List<MeldingElement> meldingen = migratieElement.valideerInhoud();
        assertEquals(2, meldingen.size());
        assertEquals(Regel.R2393, meldingen.get(0).getRegel());
        assertEquals(Regel.R2394, meldingen.get(1).getRegel());
    }

    @Test
    public void R2394_BuitenlandseAdresRegelsNull() throws Exception {
        final ElementBuilder.MigratieParameters migParams = new ElementBuilder.MigratieParameters('P', 'G', "0001");
        migParams.adresRegel1 = null;
        migParams.adresRegel2 = null;
        migParams.adresRegel3 = null;
        MigratieElement migratieElement = builder.maakMigratie("mig", migParams);
        List<MeldingElement> meldingen = migratieElement.valideerInhoud();
        assertEquals(0, meldingen.size());
    }

    @Bedrijfsregel(Regel.R1377)
    @Test
    public void R1377_regels_te_lang() {
        final StringElement maxAantalKarakters = new StringElement("abcdefghijklmnopqrstuvwxyzabcdefghi");
        final StringElement teveelKarakters = new StringElement("abcdefghijklmnopqrstuvwxyzabcdefghij");
        final ElementBuilder.MigratieParameters migParams = new ElementBuilder.MigratieParameters('P', 'G', "0001");
        migParams.adresRegel1 = teveelKarakters;
        controleerRegel1377(migParams, 1);

        migParams.adresRegel1 = maxAantalKarakters;
        migParams.adresRegel2 = teveelKarakters;
        controleerRegel1377(migParams, 2);

        migParams.adresRegel1 = maxAantalKarakters;
        migParams.adresRegel2 = maxAantalKarakters;
        migParams.adresRegel3 = teveelKarakters;
        controleerRegel1377(migParams, 3);
    }

    private void controleerRegel1377(final ElementBuilder.MigratieParameters migParams, final int index) {
        final MigratieElement element = builder.maakMigratie("migratie"+index, migParams);
        final List<MeldingElement> meldingen = element.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1377, meldingen.get(0).getRegel());
    }


    public MigratieElement maakMigratieElement(final String landcode, final String regel1, final String regel6, final Character reden,
                                               final Character aangever) {
        final ElementBuilder.MigratieParameters migParams = new ElementBuilder.MigratieParameters(reden, aangever, landcode);
        if (regel1 != null) {
            migParams.adresRegel1 = new StringElement(regel1);
        }
        if (regel6 != null) {
            migParams.adresRegel2 = new StringElement(regel6);
        }
        return builder.maakMigratie("mig", migParams);
    }

    private void setupDynamischeStamTabellen() {
        when(getDynamischeStamtabelRepository().getLandOfGebiedByCode("0001")).thenReturn(new LandOfGebied("0001", "Duitsland"));
        when(getDynamischeStamtabelRepository().getLandOfGebiedByCode("0000")).thenReturn(new LandOfGebied("0001", "Duitsland"));
        when(getDynamischeStamtabelRepository().getRedenWijzigingVerblijf('A')).thenReturn(new RedenWijzigingVerblijf('A', "Ambsthalve"));
        when(getDynamischeStamtabelRepository().getRedenWijzigingVerblijf('P')).thenReturn(new RedenWijzigingVerblijf('P', "aangifte door persoon"));
        when(getDynamischeStamtabelRepository().getRedenWijzigingVerblijf('?')).thenReturn(new RedenWijzigingVerblijf('?', "onbekend"));
        when(getDynamischeStamtabelRepository().getRedenWijzigingVerblijf('B')).thenReturn(new RedenWijzigingVerblijf('?', "technisch BAG"));
        when(getDynamischeStamtabelRepository().getRedenWijzigingVerblijf('I')).thenReturn(new RedenWijzigingVerblijf('?', "Infra"));
        when(getDynamischeStamtabelRepository().getAangeverByCode('G')).thenReturn(new Aangever('G', "Gezag Hebbende", "Persoon met gezag"));
        when(getDynamischeStamtabelRepository().getAangeverByCode('P')).thenReturn(new Aangever('P', "Persoon", "Persoon"));
    }

}

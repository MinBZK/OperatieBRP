/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.services.blobber;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ActieBron;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandelingGedeblokkeerdeRegel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Document;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.services.blobber.json.BlobRecord;
import nl.bzk.algemeenbrp.services.blobber.json.BlobRoot;

/**
 * Map Actie/Actiebron entiteit op Blob-records.
 */
final class VerantwoordingBlobber {

    private final List<BlobRoot> blobRootList;

    /**
     * Constructor.
     *
     * @param actieMap map met acties behorende bij het de administratievehandeling
     */
    VerantwoordingBlobber(final Collection<BRPActie> actieMap) {
        final Multimap<AdministratieveHandeling, BRPActie> handelingActieMap = HashMultimap.create();
        for (final BRPActie brpActie : actieMap) {
            handelingActieMap.put(brpActie.getAdministratieveHandeling(), brpActie);
        }
        blobRootList = Lists.newArrayListWithExpectedSize(handelingActieMap.size());
        for (final AdministratieveHandeling ah : handelingActieMap.keySet()) {
            final BlobRoot blobRoot = new BlobRoot();
            blobRoot.setRecordList(Lists.newLinkedList());
            mapAdministratieveHandeling(ah, blobRoot.getRecordList());
            for (final BRPActie brpActie : handelingActieMap.get(ah)) {
                mapActie(brpActie, blobRoot.getRecordList());
            }
            blobRootList.add(blobRoot);
        }
    }

    List<BlobRoot> getBlobRootList() {
        return blobRootList;
    }

    private void mapAdministratieveHandeling(final AdministratieveHandeling administratieveHandeling, final List<BlobRecord> records) {
        final BlobRecord record = new BlobRecord();
        record.setObjectSleutel(administratieveHandeling.getId());
        record.setObjectElement(Element.ADMINISTRATIEVEHANDELING);
        record.setVoorkomenSleutel(administratieveHandeling.getId() /* dummy */);
        record.setGroepElement(Element.ADMINISTRATIEVEHANDELING_IDENTITEIT);
        record.addAttribuut(Element.ADMINISTRATIEVEHANDELING_SOORTNAAM, administratieveHandeling.getSoort().getId());
        record.addAttribuut(Element.ADMINISTRATIEVEHANDELING_PARTIJCODE, administratieveHandeling.getPartij().getCode());
        record.addAttribuut(Element.ADMINISTRATIEVEHANDELING_TOELICHTINGONTLENING, administratieveHandeling.getToelichtingOntlening());
        record.addAttribuut(Element.ADMINISTRATIEVEHANDELING_TIJDSTIPREGISTRATIE, administratieveHandeling.getDatumTijdRegistratie());
        records.add(record);

        administratieveHandeling.getAdministratieveHandelingGedeblokkeerdeRegelSet()
                .forEach(administratieveHandelingGedeblokkeerdeRegel -> mapGedeblokkeerdeRegels(administratieveHandelingGedeblokkeerdeRegel, records));
    }

    private void mapGedeblokkeerdeRegels(final AdministratieveHandelingGedeblokkeerdeRegel administratieveHandelingGedeblokkeerdeRegel,
                                         final List<BlobRecord> records) {

        final BlobRecord record = new BlobRecord();
        record.setParentObjectSleutel(administratieveHandelingGedeblokkeerdeRegel.getAdministratieveHandeling().getId());
        record.setParentObjectElement(Element.ADMINISTRATIEVEHANDELING);

        final Regel regel = administratieveHandelingGedeblokkeerdeRegel.getRegel();
        record.setObjectSleutel((long) regel.getId());
        record.setObjectElement(Element.REGEL);
        record.setGroepElement(Element.REGEL_IDENTITEIT);
        record.setVoorkomenSleutel((long) regel.getId() /* dummy */);
        record.addAttribuut(Element.REGEL_CODE, regel.getCode());
        record.addAttribuut(Element.REGEL_MELDING, regel.getMelding());
        records.add(record);
    }


    private void mapActie(final BRPActie actie, final List<BlobRecord> records) {
        final BlobRecord record = new BlobRecord();
        record.setParentObjectSleutel(actie.getAdministratieveHandeling().getId());
        record.setParentObjectElement(Element.ADMINISTRATIEVEHANDELING);
        record.setObjectSleutel(actie.getId());
        record.setObjectElement(Element.ACTIE);
        record.setGroepElement(Element.ACTIE_IDENTITEIT);
        record.setVoorkomenSleutel(actie.getId() /* dummy */);
        record.addAttribuut(Element.ACTIE_SOORTNAAM, actie.getSoortActie().getId());
        record.addAttribuut(Element.ACTIE_PARTIJCODE, actie.getPartij().getCode());
        record.addAttribuut(Element.ACTIE_TIJDSTIPREGISTRATIE, actie.getDatumTijdRegistratie());
        record.addAttribuut(Element.ACTIE_DATUMONTLENING, actie.getDatumOntlening());
        records.add(record);

        actie.getActieBronSet().forEach(bron -> mapActieBron(bron, records));
    }

    private void mapActieBron(final ActieBron bron, final List<BlobRecord> records) {
        final BlobRecord record = new BlobRecord();
        record.setParentObjectSleutel(bron.getActie().getId());
        record.setParentObjectElement(Element.ACTIE);
        record.setObjectSleutel(bron.getId());
        record.setObjectElement(Element.ACTIEBRON);
        record.setVoorkomenSleutel(bron.getId() /* dummy */);
        record.setGroepElement(Element.ACTIEBRON_IDENTITEIT);
        if (bron.getRechtsgrond() != null) {
            record.addAttribuut(Element.ACTIEBRON_RECHTSGRONDCODE, bron.getRechtsgrond().getCode());
        }
        record.addAttribuut(Element.ACTIEBRON_RECHTSGRONDOMSCHRIJVING, bron.getRechtsgrondOmschrijving());
        records.add(record);

        if (bron.getDocument() != null) {
            mapDocument(bron.getDocument(), bron, records);
        }
    }

    private void mapDocument(final Document document, final ActieBron bron, final List<BlobRecord> records) {
        final BlobRecord idRecord = new BlobRecord();
        idRecord.setParentObjectSleutel(bron.getId());
        idRecord.setParentObjectElement(Element.ACTIEBRON);
        idRecord.setObjectSleutel(document.getId());
        idRecord.setObjectElement(Element.DOCUMENT);
        idRecord.setVoorkomenSleutel(document.getId()/* dummy */);
        idRecord.setGroepElement(Element.DOCUMENT_IDENTITEIT);
        idRecord.addAttribuut(Element.DOCUMENT_SOORTNAAM, document.getSoortDocument().getNaam());
        idRecord.addAttribuut(Element.DOCUMENT_AKTENUMMER, document.getAktenummer());
        idRecord.addAttribuut(Element.DOCUMENT_OMSCHRIJVING, document.getOmschrijving());
        if (document.getPartij() != null) {
            idRecord.addAttribuut(Element.DOCUMENT_PARTIJCODE, document.getPartij().getCode());
        }
        records.add(idRecord);
    }
}

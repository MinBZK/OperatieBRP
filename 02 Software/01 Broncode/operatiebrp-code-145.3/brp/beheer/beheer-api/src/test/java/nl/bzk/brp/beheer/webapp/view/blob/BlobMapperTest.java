/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.view.blob;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.services.blobber.BlobException;
import nl.bzk.algemeenbrp.services.blobber.Blobber;
import nl.bzk.algemeenbrp.services.blobber.json.AfnemerindicatiesBlob;
import nl.bzk.algemeenbrp.services.blobber.json.BlobRoot;
import nl.bzk.algemeenbrp.services.blobber.json.PersoonBlob;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class BlobMapperTest {

    private BlobMapper setupSubject() throws BlobException, IOException {
        final PersoonBlob persoonBlob = Blobber.deserializeNaarPersoonBlob(IOUtils.toByteArray(BlobMapperTest.class.getResource("blob.persoon.data")));
        final AfnemerindicatiesBlob afnemerindicatiesBlob =
                Blobber.deserializeNaarAfnemerindicatiesBlob(IOUtils.toByteArray(BlobMapperTest.class.getResource("blob.afnemerindicaties.data")));

        final BlobMapper subject = new BlobMapper();
        for (final BlobRoot verantwoordingBlob : persoonBlob.getVerantwoording()) {
            subject.map(verantwoordingBlob.getRecordList());
        }
        subject.map(persoonBlob.getPersoonsgegevens().getRecordList());
        for (final BlobRoot afnemerindicatieBlob : afnemerindicatiesBlob.getAfnemerindicaties()) {
            subject.map(afnemerindicatieBlob.getRecordList());
        }
        return subject;
    }

    @Test
    public void testInzienPersoon() throws IOException, BlobException {
        final BlobMapper subject = setupSubject();
        subject.maakHierarchieVoorInzienPersoon();

        printStructuur(subject);
    }

    @Test
    public void testInzienActie() throws IOException, BlobException {
        final BlobMapper subject = setupSubject();
        subject.maakHierarchieVoorInzienActie(101);

        printStructuur(subject);
    }

    private void printStructuur(final BlobMapper subject) {
        System.out.println("Personen");
        for (final BlobViewObject object : subject.getObjecten(Element.PERSOON)) {
            printBlobViewObject(object, 0);
        }
        System.out.println("Relaties");
        for (final BlobViewObject object : subject.getObjecten(Element.FAMILIERECHTELIJKEBETREKKING)) {
            printBlobViewObject(object, 0);
        }
        for (final BlobViewObject object : subject.getObjecten(Element.HUWELIJK)) {
            printBlobViewObject(object, 0);
        }
        for (final BlobViewObject object : subject.getObjecten(Element.GEREGISTREERDPARTNERSCHAP)) {
            printBlobViewObject(object, 0);
        }
        System.out.println("Onderzoeken");
        for (final BlobViewObject object : subject.getObjecten(Element.ONDERZOEK)) {
            printBlobViewObject(object, 0);
        }
        System.out.println("Administratieve handelingen");
        for (final BlobViewObject object : subject.getObjecten(Element.ADMINISTRATIEVEHANDELING)) {
            printBlobViewObject(object, 0);
        }
        System.out.println("Afnemerindicaties");
        for (final BlobViewObject object : subject.getObjecten(Element.PERSOON_AFNEMERINDICATIE)) {
            printBlobViewObject(object, 0);
        }
    }

    private void printBlobViewObject(final BlobViewObject object, final int indent) {
        System.out.println(indent(indent) + object.getElement());
        // for (final Element groep : object.getGroepen().keySet()) {
        // System.out.println(indent(indent) + " " + groep);
        // }
        for (final Map.Entry<Element, Collection<BlobViewObject>> children : object.getObjecten().entrySet()) {
            System.out.println(indent(indent) + " - " + children.getKey());
            for (final BlobViewObject child : children.getValue()) {
                printBlobViewObject(child, indent + 4);
            }
        }
    }

    private String indent(final int indent) {
        final StringBuilder result = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            result.append(' ');
        }
        return result.toString();
    }

}

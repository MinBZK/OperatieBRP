/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa.serialization;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import nl.bzk.brp.model.blob.PlBlob;
import nl.bzk.brp.model.objecttype.pojo.PersoonHisModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * TODO: Add documentation
 */
@Component
public class ExternalizableBlobSerializer implements BlobSerializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExternalizableBlobSerializer.class);


    @Override
    public byte[] serializeObject(final PersoonHisModel model) throws IOException {
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            model.writeExternal(oos);
            oos.flush();
            return baos.toByteArray();
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
            } catch (Exception e) {
                LOGGER.error("Kon geen blob maken van persoonmodel", e);
            }
        }
    }

    @Override
    public PersoonHisModel deserializeObject(final PlBlob plBlob) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new ByteArrayInputStream(plBlob.getPl()));
            PersoonHisModel persoonHisModel = new PersoonHisModel(plBlob.getId());
            persoonHisModel.readExternal(ois);
            return persoonHisModel;
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (Exception e) {
                LOGGER.error("Kon de blob niet lezen", e);
            }
        }
    }
}

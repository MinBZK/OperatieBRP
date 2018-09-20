/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.webservice.business.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Wrapper class voor objectsleutels, een objectsleutel herbergt een persoonId, een tijdstip van uitgifte en een partij
 * waarvoor de objectsleutel is uitgegeven. Oftewel de partij die de persoon heeft bevraagd.
 */
public final class ObjectSleutel {

    private final int persoonId;
    private final long tijdstipVanUitgifte;
    private final int partijCode;

    /**
     * Constructor met de waardes voor alle velden.
     *
     * @param persoonId persoon id
     * @param tijdstipVanUitgifte tijdstip van uitgifte
     * @param partijCode de partij voor wie de sleutel is uitgegeven
     */
    public ObjectSleutel(final int persoonId, final long tijdstipVanUitgifte, final int partijCode) {
        this.persoonId = persoonId;
        this.tijdstipVanUitgifte = tijdstipVanUitgifte;
        this.partijCode = partijCode;
    }

    public int getPersoonId() {
        return persoonId;
    }

    public long getTijdstipVanUitgifte() {
        return tijdstipVanUitgifte;
    }

    public int getPartijCode() {
        return partijCode;
    }

    /**
     * Serialiseer dit object naar een byte array,
     * waarbij alle velden in een byte array output stream worden geschreven.
     * Om weer een object te verkrijgen uit deze byte[], gebruik de
     * methode 'deserialize'.
     *
     * @return een byte[] met alle informatie van dit object
     * @throws IOException als er iets mis gaat met het serialiseren
     */
    public byte[] serialize() throws IOException {
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        final ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeInt(persoonId);
        oos.writeLong(tijdstipVanUitgifte);
        oos.writeInt(partijCode);
        oos.close();
        return bos.toByteArray();
    }

    /**
     * Deserialiseer deze byte array naar een object,
     * waarbij alle velden uit een byte array input stream worden gelezen.
     * Dit zal alleen werken met een byte[], verkregen uit de
     * methode 'serialize'.
     *
     * @param bytes bytes om te deserialiseren
     * @return een object sleutel DTO
     * @throws IOException als er iets mis gaat met het deserialiseren
     */
    public static ObjectSleutel deserialize(final byte [] bytes) throws IOException {
        final ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        final ObjectInputStream ois = new ObjectInputStream(bis);
        final ObjectSleutel objectSleutel = new ObjectSleutel(
                ois.readInt(),
                ois.readLong(),
                ois.readInt()
        );
        ois.close();
        return objectSleutel;
    }
}

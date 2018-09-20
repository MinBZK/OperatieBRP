/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity;

import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.EnumParser;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Enumeratie;

/**
 * Enumeratie van protocolleringsniveaus.
 */
public enum Protocolleringsniveau implements Enumeratie {
    /** Geen beperkingen. */
    GEEN_BEPERKINGEN((short) 1, "0", "Geen beperkingen", "Aan een burger desgevraagd inzage geven in de bij zijn "
                                                         + "persoonsgegevens gelegde afnemersindicaties.", 0, 99991231),
    /** Geheim. */
    GEHEIM((short) 2, "2", "Geheim", "Aan een burger wordt GEEN inzage verstrekt over de in kader van deze "
                                     + "Autorisatie geleverde gegevens. Toegang tot deze gegevens is beperkt tot "
                                     + "gemeenteambtenaren die daartoe expliciet zijn gautoriseerd.", 0, 99991231);

    private static final EnumParser<Protocolleringsniveau> PARSER = new EnumParser<>(Protocolleringsniveau.class);

    private final short id;
    private final String code;
    private final String naam;
    private final String omschrijving;
    private final Integer datumAanvangGeldigheid;
    private final Integer datumEindeGeldigheid;

    /**
     * Maak een nieuw Protocolleringsniveau object.
     *
     * @param id
     *            id
     * @param code
     *            code
     * @param naam
     *            naam
     * @param omschrijving
     *            omschrijving
     * @param datumAanvangGeldigheid
     *            datum aanvang geldigheid
     * @param datumEindeGeldigheid
     *            datum einde geldigheid
     */
    Protocolleringsniveau(
        final short id,
        final String code,
        final String naam,
        final String omschrijving,
        final Integer datumAanvangGeldigheid,
        final Integer datumEindeGeldigheid)
    {
        this.id = id;
        this.code = code;
        this.naam = naam;
        this.omschrijving = omschrijving;
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
        this.datumEindeGeldigheid = datumEindeGeldigheid;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Enumeratie#getId()
     */
    @Override
    public short getId() {
        return id;
    }

    /**
     * Bepaal een voorkomen op basis van id.
     *
     * @param id
     *            id
     * @return functie adres
     */
    public static Protocolleringsniveau parseId(final Short id) {
        return PARSER.parseId(id);
    }

    /**
     * Bepaal een voorkomen op basis van code.
     *
     * @param code
     *            code
     * @return functie adres
     */
    public static Protocolleringsniveau parseCode(final String code) {
        return PARSER.parseCode(code);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Enumeratie#getCode()
     */
    @Override
    public String getCode() {
        return code;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean heeftCode() {
        return true;
    }

    /**
     * Geef de waarde van naam.
     *
     * @return naam
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Geef de waarde van omschrijving.
     *
     * @return omschrijving
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    /**
     * Geef de waarde van datumAanvangGeldigheid.
     *
     * @return datumAanvangGeldigheid
     */
    public Integer getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    /**
     * Geef de waarde van datumEindeGeldigheid.
     *
     * @return datumEindeGeldigheid
     */
    public Integer getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }
}

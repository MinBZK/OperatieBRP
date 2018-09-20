/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

/**
 *
 */
@SuppressWarnings("checkstyle:multiplestringliterals")
public enum SoortElementAutorisatie implements Enumeratie {

    /**
     * Het attribuut kan niet direct geautoriseerd worden maar wordt geleverd als de groepsautorisatie aanwezig is. B.v.
     * tijdstip verval van een groep wordt alleen geleverd als de partij geautoriseerd is om formele historie te zien
     * voor de groep.
     */
    VIA_GROEPSAUTORISATIE((short) 1, "Via groepsautorisatie",
            "Het attribuut kan niet direct geautoriseerd worden maar wordt geleverd als de groepsautorisatie aanwezig is. B.v. tijdstip verval van een groep "
                                                              + "wordt alleen geleverd als de partij geautoriseerd is om formele historie te zien voor de groep."),
    /**
     * Een afnemer mag dit attribuut op grond van H3 wet BRP niet verstrekt krijgen. Het attribuut kan mogelijk wel op
     * grond van andere wetgeving geleverd worden.
     */
    NIET_VERSTREKKEN((short) 2, "Niet verstrekken",
            "Een afnemer mag dit attribuut op grond van H3 wet BRP niet verstrekt krijgen. Het attribuut kan mogelijk wel op grond van andere wetgeving "
                                                    + "geleverd worden."),
    /** Een afnemer mag voor dit attribuut geautoriseerd worden. */
    OPTIONEEL((short) 3, "Optioneel", "Een afnemer mag voor dit attribuut geautoriseerd worden."),
    /**
     * Expliciete autorisatie niet mogelijk. Het attribuut is echter wel aanwezig in de berichten. Bijvoorbeeld als
     * objectsleutel of als hiërarchische relatie.
     */
    STRUCTUUR((short) 4, "Structuur",
            "Expliciete autorisatie niet mogelijk. Het attribuut is echter wel aanwezig in de berichten. Bijvoorbeeld als objectsleutel of als hiërarchische "
                                      + "relatie."),
    /** Een afnemer moet voor dit attribuut geautoriseerd worden volgens de wet BRP (o.a. artikel 3.10). */
    VERPLICHT((short) 5, "Verplicht", "Een afnemer moet voor dit attribuut geautoriseerd worden volgens de wet BRP (o.a. artikel 3.10)."),
    /**
     * Een afnemer zou bij voorkeur een autorisatie voor dit attribuut moeten krijgen. Er is echter geen wettelijke
     * grondslag.
     */
    AANBEVOLEN((short) 6, "Aanbevolen",
            "Een afnemer zou bij voorkeur een autorisatie voor dit attribuut moeten krijgen. Er is echter geen wettelijke grondslag."),
    /** Het attribuut wordt alleen verstrekt aan bijhouders. */
    BIJHOUDINGSGEGEVENS((short) 7, "Bijhoudingsgegevens", "Het attribuut wordt alleen verstrekt aan bijhouders."),
    /**
     * Er geldt een niet-standaard autorisatie voor dit attribuut. Deze uitzondering wordt afgedwongen middels
     * bedrijfsregels in de beheerapplicatie.
     */
    UITZONDERING((short) 8, "Uitzondering", "Er geldt een niet-standaard autorisatie voor dit attribuut. Deze uitzondering wordt afgedwongen "
                                            + "middels bedrijfsregels in de beheerapplicatie.");

    /**
     *
     */
    private static final EnumParser<SoortElementAutorisatie> PARSER = new EnumParser<>(SoortElementAutorisatie.class);

    /**
     *
     */
    private final short id;
    /**
     *
     */
    private final String naam;
    /**
     *
     */
    private final String omschrijving;

    /**
     * Maak een nieuwe soort element autorisatie.
     *
     * @param id
     *            id
     * @param naam
     *            naam
     * @param omschrijving
     *            omschrijving
     */
    SoortElementAutorisatie(final short id, final String naam, final String omschrijving) {
        this.id = id;
        this.naam = naam;
        this.omschrijving = omschrijving;
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
     * @return soort element autorisatie of null
     */
    public static SoortElementAutorisatie parseId(final Short id) {
        return PARSER.parseId(id);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Enumeratie#getCode()
     */
    @Override
    public String getCode() {
        throw new UnsupportedOperationException("De enumeratie SoortIndicatie heeft geen code");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean heeftCode() {
        return false;
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
     * Geef de waarde van naam.
     *
     * @return naam
     */
    public String getNaam() {
        return naam;
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import nl.bzk.brp.BindingUtil;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.gedeeld.SoortActie;
import nl.bzk.brp.model.validatie.constraint.Datum;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;


/** BRP Actie. */
public class BRPActie {

    @Valid
    private List<RootObject> rootObjecten;

    private Long id;

    private SoortActie soort;

    @Datum
    private Integer datumAanvangGeldigheid;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public SoortActie getSoort() {
        return soort;
    }

    public void setSoort(final SoortActie soort) {
        this.soort = soort;
    }

    public Integer getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    public void setDatumAanvangGeldigheid(final Integer datumAanvangGeldigheid) {
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
    }

    public List<RootObject> getRootObjecten() {
        return rootObjecten;
    }

    public void setRootObjecten(final List<RootObject> rootObjecten) {
        this.rootObjecten = rootObjecten;
    }

    /**
     * Voegt een RootObject toe aan de actie.
     *
     * @param rootObj Het toe te voegen rootobject.
     */
    public void voegRootObjectToe(final RootObject rootObj) {
        if (rootObj != null) {
            if (rootObjecten == null) {
                rootObjecten = new ArrayList<RootObject>();
            }
            rootObjecten.add(rootObj);
        }
    }

    /**
     * Voegt een persoon toe aan de lijst van root objecten.
     * @param persoon De toe te voegen persoon.
     */
    public void voegPersoonToe(final Persoon persoon) {
        voegRootObjectToe(persoon);
    }

    /**
     * Voegt een relatie toe aan de lijst van root objecten.
     * @param relatie De toe te voegen relatie.
     */
    public void voegRelatieToe(final Relatie relatie) {
        voegRootObjectToe(relatie);
    }

    /**
     * Methode puur voor input binding. Zal dus nooit aangeroepen worden.
     * Wordt helaas vereist door Jibx.
     *
     * @return null
     */
    private Persoon getPersoonRootObject() {
        return null;
    }

    /**
     * Methode puur voor input binding. Zal dus nooit aangeroepen worden.
     * Wordt helaas vereist door Jibx.
     *
     * @return null
     */
    private Relatie getRelatieRootObject() {
        return null;
    }

    /**
     * Functie puur gemaakt voor de data binding, daarom private!
     * SoortActie wordt namelijk nergens aangegeven in de XML berichten. Deze functie wordt door de binding
     * aangeroepen om de soort te initialiseren.
     *
     * @param ctx De context waar de binding zich in bevindt op het moment dat deze functie wordt aangeroepen.
     * @throws org.jibx.runtime.JiBXException Indien een functie wordt aangeroepen op de context die niet past bij de
     * huidige state van de context.
     */
    private void initSoort(final IUnmarshallingContext ctx) throws JiBXException {
        setSoort(BindingUtil.initSoort(ctx));
    }
}

package com.evansitzes.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.evansitzes.game.buildings.Building;

/**
 * Created by evan on 12/29/16.
 */
public class BasicInformationPopup extends Dialog {

    public BasicInformationPopup(final Building building, final Skin skin) {
        super(building.prettyName, skin);

        this.setName(building.prettyName);
        this.setSkin(skin);
        this.setPosition(400, 400);
        this.setWidth(500);
        this.setHeight(300);
        this.setVisible(true);
        this.setMovable(false);
        setResizable(false);
        this.setDebug(true);

        this.text(building.prettyName);
        this.getContentTable().row();

        final String buildingDescription = building.description;

        this.text(buildingDescription);
        this.getContentTable().row();

        if (!building.isConnectedToRoad) {
            this.text("Warning! Building is not connected to a road.");
        }

        this.button("okay", true);

        key(Input.Keys.ENTER, true);
        key(Input.Keys.ANY_KEY, true);
        key(Input.Buttons.LEFT, true);
    }

    @Override
    public void result(final Object object) {
//        seeWares = Boolean.parseBoolean(object.toString());
//
//        if (seeWares) {
//            gameflowController.setShopScreen();
//        }
    }

}

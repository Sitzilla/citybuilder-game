package com.evansitzes.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by evan on 12/29/16.
 */
public class BasicInformationPopup extends Dialog {

    public BasicInformationPopup(final String title, final Skin skin) {
        super(title, skin);

        this.setName(title);
        this.setSkin(skin);
        this.setPosition(400, 400);
        this.setWidth(500);
        this.setHeight(300);
        this.setVisible(true);
        this.setMovable(false);
        setResizable(false);
        this.setDebug(true);

        this.text("A modest home");
        this.getContentTable().row();
        this.text("People living here: 0 / 8");
        this.button("okay", true);

        key(Input.Keys.ENTER, true);

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

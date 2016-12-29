package com.evansitzes.game.helpers;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 * Created by evan on 12/29/16.
 */
public class TextHelper {

    public static Label createText(String message, int x, int y) {
        Label text;
        Label.LabelStyle textStyle;

        textStyle = new Label.LabelStyle();
        textStyle.font = new BitmapFont();

        text = new Label(message,textStyle);
        text.setPosition(x, y);

        return text;

    }
}

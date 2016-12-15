package com.evansitzes.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.evansitzes.game.CityBuildingGame;
import com.evansitzes.game.Configuration;

public class DesktopLauncher {
    public static void main (final String[] arg) {
        final Configuration configuration = new Configuration();
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.width = configuration.WIDTH;
        cfg.height = configuration.HEIGHT;

        new LwjglApplication(new CityBuildingGame(configuration), cfg);
    }
}

package com.evansitzes.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by evan on 12/14/16.
 */
public class Textures {

    public static class Sidebar {
        public static final TextureRegion SIDEBAR = loadSidebar();
        public static final TextureRegion HOUSE = loadHouse()[0][0];

    }

    public static class Road {
        public static final TextureRegion VERTICLE_ROAD = loadRoad()[3][5];
        public static final TextureRegion HORIZONTAL_ROAD = loadRoad()[0][0];

    }


    public static class Colors {
        public static final TextureRegion RED = loadRed()[0][0];

        private static TextureRegion[][] loadRed() {
            final int frameColumns = 1;
            final int frameRows = 1;

            return splitTextureRegion(frameColumns, frameRows, new Texture(Gdx.files.internal("colors/red.png")));
        }

    }

    private static TextureRegion loadSidebar() {
        final int frameColumns = 1;
        final int frameRows = 1;

        return splitTextureRegion(frameColumns, frameRows, new Texture(Gdx.files.internal("sidebar/demo_sidebar.png")))[0][0];
    }

    private static TextureRegion[][] loadHouse() {
        final int frameColumns = 24;
        final int frameRows = 30;

        return splitTextureRegion(frameColumns, frameRows, new Texture(Gdx.files.internal("buildings/build.png")));
    }

    private static TextureRegion[][] loadRoad() {
        final int frameColumns = 9;
        final int frameRows = 9;

        return splitTextureRegion(frameColumns, frameRows, new Texture(Gdx.files.internal("sidebar/road_and_grass.png")));
    }

    private static TextureRegion[][] splitTextureRegion(final int columns, final int rows, final Texture sheet) {
        TextureRegion[] walkFrames;

        TextureRegion[][] tmp = TextureRegion.split(sheet, sheet.getWidth() /
                columns, sheet.getHeight() / rows);
        walkFrames = new TextureRegion[columns * rows];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }

        return tmp;
    }

}

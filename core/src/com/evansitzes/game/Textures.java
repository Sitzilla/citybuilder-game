package com.evansitzes.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by evan on 12/14/16.
 */
public class Textures {

    public static class Sidebar {
        public static final TextureRegion BACKGROUND = loadBackground();
        public static final TextureRegion HOUSE = loadHouse();
        public static final TextureRegion GUARD_HOUSE = loadGuardhouse();
        public static final TextureRegion FARMHOUSE = loadFarmhouse();
        public static final TextureRegion BULLDOZER = loadBulldozer();

    }

    public static class People {
//        public static final TextureRegion STANDING = loadSprite()[0][1];

//        public static final TextureRegion[] WALKING_RIGHT = loadWalkingRight();
//        public static final TextureRegion[] WALKING_LEFT = loadWalkingLeft();
//        public static final TextureRegion[] WALKING_UP = loadWalkingUp();
//        public static final TextureRegion[] WALKING_DOWN = loadWalkingDown();

        public static TextureRegion loadStandingSprite(final String spriteFile) {
            return loadSprite(spriteFile)[0][1];
        }

        public static TextureRegion[] loadWalkingLeft(final String spriteFile) {
            return loadWalking(1, loadSprite(spriteFile));
        }

        public static TextureRegion[] loadWalkingRight(final String spriteFile) {
            return loadWalking(2, loadSprite(spriteFile));
        }

        public static TextureRegion[] loadWalkingUp(final String spriteFile) {
            return loadWalking(3, loadSprite(spriteFile));
        }

        public static TextureRegion[] loadWalkingDown(final String spriteFile) {
            return loadWalking(0, loadSprite(spriteFile));
        }

        private static TextureRegion[] loadWalking(final int index, final TextureRegion[][] walkingTextureRegion) {
//            final TextureRegion[] walkingFrames = new TextureRegion[3];
            final TextureRegion[] walkingFrames = new TextureRegion[4];
//            final TextureRegion[][] tmp = loadSprite();

//            for (int i = 0; i < 3; i++) {
//                walkingFrames[i] = tmp[index][i];
//            }

            walkingFrames[0] = walkingTextureRegion[index][0];
            walkingFrames[1] = walkingTextureRegion[index][1];
            walkingFrames[2] = walkingTextureRegion[index][2];
            walkingFrames[3] = walkingTextureRegion[index][1];

            return walkingFrames;
        }

        private static TextureRegion[][] loadSprite(final String spriteFile) {
            final int frameColumns = 3;
            final int frameRows = 4;

            return splitTextureRegion(frameColumns, frameRows, new Texture(Gdx.files.internal("people/" + spriteFile + ".png")));
        }


    }

    public static class Crops {
        public static final TextureRegion GROUND = loadGround()[0][0];

        public static final TextureRegion CARROTS_IN_GROUND = loadCarrot()[0][0];
        public static final TextureRegion CARROTS_RIPE = loadCarrot()[1][0];
        public static final TextureRegion CARROTS = loadCarrot()[2][0];

        private static TextureRegion[][] loadCarrot() {
            final int frameColumns = 1;
            final int frameRows = 3;

            return splitTextureRegion(frameColumns, frameRows, new Texture(Gdx.files.internal("buildings/crops/carrots.png")));
        }

        private static TextureRegion[][] loadGround() {
            final int frameColumns = 1;
            final int frameRows = 1;

            return splitTextureRegion(frameColumns, frameRows, new Texture(Gdx.files.internal("buildings/crops/ground.png")));
        }
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

    private static TextureRegion loadBackground() {
        final int frameColumns = 1;
        final int frameRows = 1;

        return splitTextureRegion(frameColumns, frameRows, new Texture(Gdx.files.internal("sidebar/test_background.png")))[0][0];
    }

    private static TextureRegion loadTopbar() {
        final int frameColumns = 1;
        final int frameRows = 1;

        return splitTextureRegion(frameColumns, frameRows, new Texture(Gdx.files.internal("sidebar/top_bar.png")))[0][0];
    }

    private static TextureRegion loadHouse() {
        final int frameColumns = 1;
        final int frameRows = 1;

        return splitTextureRegion(frameColumns, frameRows, new Texture(Gdx.files.internal("buildings/house.png")))[0][0];
    }

    private static TextureRegion loadFarmhouse() {
        final int frameColumns = 1;
        final int frameRows = 1;

        return splitTextureRegion(frameColumns, frameRows, new Texture(Gdx.files.internal("buildings/farmhouse.png")))[0][0];
    }

    private static TextureRegion loadGuardhouse() {
        final int frameColumns = 1;
        final int frameRows = 1;

        return splitTextureRegion(frameColumns, frameRows, new Texture(Gdx.files.internal("buildings/guard_house.png")))[0][0];
    }

    private static TextureRegion loadBulldozer() {
        final int frameColumns = 1;
        final int frameRows = 1;

        return splitTextureRegion(frameColumns, frameRows, new Texture(Gdx.files.internal("sidebar/bulldozer.png")))[0][0];
    }

    private static TextureRegion[][] loadRoad() {
        final int frameColumns = 9;
        final int frameRows = 9;

        return splitTextureRegion(frameColumns, frameRows, new Texture(Gdx.files.internal("sidebar/road_and_grass.png")));
    }

    private static TextureRegion[][] splitTextureRegion(final int columns, final int rows, final Texture sheet) {
        final TextureRegion[] walkFrames;

        final TextureRegion[][] tmp = TextureRegion.split(sheet, sheet.getWidth() /
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

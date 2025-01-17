package com.skyteam.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class Zone extends Actor {
    private Rectangle bounds;
    private Color color;
    private String name;
    private Array<TextureRegion> droppedDice;

    public Zone(float x, float y, float width, float height, Color color, String name) {
        this.bounds = new Rectangle(x, y, width, height);
        this.color = color;
        this.name = name;
        this.droppedDice = new Array<>();
        setBounds(x, y, width, height);
    }

    public String getName() {
        return name;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void drawZone(ShapeRenderer shapeRenderer, SpriteBatch spriteBatch) {
        shapeRenderer.setColor(color);
        shapeRenderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);

        spriteBatch.begin();

        for (int i = 0; i < droppedDice.size; i++) {
            TextureRegion diceFace = droppedDice.get(i);
            float diceX = bounds.x + (i * 35);
            float diceY = bounds.y + bounds.height / 2 - 15;
            spriteBatch.draw(diceFace, diceX, diceY, 30, 30);
        }

        spriteBatch.end();
    }



    @Override
    public Actor hit(float x, float y, boolean touchable) {
        return bounds.contains(x, y) ? this : null;
    }

    public void addDroppedDice(TextureRegion diceFace) {
        if (diceFace != null) {
            droppedDice.add(diceFace);
        }
    }



}

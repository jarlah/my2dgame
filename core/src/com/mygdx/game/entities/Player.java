package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;

public class Player extends Sprite {
	private Vector2 velocity = new Vector2();
	private float speed = 60;
	private TiledMapTileLayer collisionLayer;
	
	public Player(Sprite sprite, TiledMapTileLayer collisionLayer) {
		super(sprite);
		this.collisionLayer = collisionLayer;
	}
	
	public void draw(Batch batch) {
		super.draw(batch);
	}

	public void update(float deltaTime) {
		velocity.x = 0;
		velocity.y = 0;
		
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			velocity.x = speed;
		}
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			velocity.x = -speed;
		}
		if (Gdx.input.isKeyPressed(Keys.UP)) {
			velocity.y = speed;
		}
		if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			velocity.y = -speed;
		}
		
		float oldX = getX();
		float oldY = getY();
		boolean collisionX = false;
		boolean collisionY = false;
		
		float tileWidth = collisionLayer.getTileWidth();
		float tileHeight = collisionLayer.getTileHeight();
		final float playerWidth = getWidth();
		final float playerHeight = getHeight();
		
		setX(getX() + velocity.x * deltaTime);
		
		if (velocity.x < 0) {
			// top left
			Cell topLeft = collisionLayer.getCell((int) (getX() / tileWidth), (int) ((getY() + playerHeight) / tileHeight));
			collisionX = topLeft == null || topLeft.getTile().getProperties().containsKey("blocked");
			// middle left
			Cell middleLeft = collisionLayer.getCell((int) (getX() / tileWidth), (int) ((getY() + playerHeight / 2) / tileHeight));
			collisionX |= middleLeft == null || middleLeft.getTile().getProperties().containsKey("blocked");
			// bottom left
			Cell bottomLeft = collisionLayer.getCell((int) (getX() / tileWidth), (int) (getY() / tileHeight));
			collisionX |= bottomLeft == null || bottomLeft.getTile().getProperties().containsKey("blocked");
		} else if (velocity.x > 0) {
			// top right
			Cell topRight = collisionLayer.getCell((int) ((getX() + playerWidth) / tileWidth), (int) ((getY() + playerHeight) / tileHeight));
			collisionX = topRight == null || topRight.getTile().getProperties().containsKey("blocked");
			// middle right
			Cell middleRight = collisionLayer.getCell((int) ((getX() + playerWidth) / tileWidth), (int) ((getY() + playerHeight / 2) / tileHeight));
			collisionX |= middleRight == null || middleRight.getTile().getProperties().containsKey("blocked");
			// bottom right
			Cell bottomRight = collisionLayer.getCell((int) ((getX() + playerWidth) / tileWidth), (int) (getY() / tileHeight));
			collisionX |= bottomRight == null || bottomRight.getTile().getProperties().containsKey("blocked");
		}
		
		if (collisionX || getX() < 0 || getX() > collisionLayer.getWidth() * tileWidth) {
			setX(oldX);
			velocity.x = 0;
		}
		
 		setY(getY() + velocity.y * deltaTime);
 		
 		if (velocity.y < 0) {
			// bottom left
 			Cell bottomLeft = collisionLayer.getCell((int) (getX() / tileWidth), (int) (getY() / tileHeight));
			collisionY = bottomLeft == null || bottomLeft.getTile().getProperties().containsKey("blocked");
 			// bottom middle
 			Cell bottomMiddle = collisionLayer.getCell((int) ((getX() + playerWidth / 2) / tileWidth), (int) (getY() / tileHeight));
			collisionY |= bottomMiddle == null || bottomMiddle.getTile().getProperties().containsKey("blocked");
 			// bottom right
 			Cell bottomRight = collisionLayer.getCell((int) ((getX() + playerWidth) / tileWidth), (int) (getY() / tileHeight));
			collisionY |= bottomRight == null || bottomRight.getTile().getProperties().containsKey("blocked");
		} else if (velocity.y > 0) {
			// top left
 			Cell topLeft = collisionLayer.getCell((int) (getX() / tileWidth), (int) ((getY() + playerHeight) / tileHeight));
			collisionY = topLeft == null || topLeft != null && topLeft.getTile().getProperties().containsKey("blocked");
 			// top middle
 			Cell topMiddle = collisionLayer.getCell((int) ((getX() + playerWidth / 2) / tileWidth), (int) ((getY() + playerHeight) / tileHeight));
			collisionY |= topMiddle == null || topMiddle != null && topMiddle.getTile().getProperties().containsKey("blocked");
 			// top right
 			Cell topRight = collisionLayer.getCell((int) ((getX() + playerWidth) / tileWidth), (int) ((getY() + playerHeight) / tileHeight));
			collisionY |= topRight == null || topRight != null && topRight.getTile().getProperties().containsKey("blocked");
		}
 		
 		if (collisionY || getY() < 0 || getY() > collisionLayer.getHeight() * tileHeight) {
			setY(oldY);
			velocity.y = 0;
		}
	}
}

package com.ozgurkrkrt.survivorbird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

public class SurvivorBird extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture bird;
	Texture enemy1;
	Texture enemy2;
	Texture enemy3;
	float birdX;
	float birdY;
	int gameState = 0;
	float velocity = 0;
	float gravity = 0.35f;
	int numberOfEnemies = 4;
	Random random;
	int score = 0;
	int scoreEnemy = 0;
	Circle birdCircle;
	float [] enemyX = new float[numberOfEnemies];
	float distance = 0;
	float enemyVelocity = 4;
	float [] enemyOffset = new float[numberOfEnemies];
	float [] enemyOffset2 = new float[numberOfEnemies];
	float [] enemyOffset3 = new float[numberOfEnemies];
	Circle[] enemyCircles;
	Circle[] enemyCircles2;
	Circle[] enemyCircles3;
	BitmapFont font;
	BitmapFont font2;




	
	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("background.png");
		bird = new Texture("bird.png");
		enemy1 = new Texture("enemy.png");
		enemy2 = new Texture("enemy.png");
		enemy3 = new Texture("enemy.png");
		birdX = Gdx.graphics.getWidth() / 2 - bird.getHeight() / 2;
		birdY = Gdx.graphics.getHeight() / 3;

		birdCircle = new Circle();
		enemyCircles = new Circle[numberOfEnemies];
		enemyCircles2 = new Circle[numberOfEnemies];
		enemyCircles3 = new Circle[numberOfEnemies];

		distance = Gdx.graphics.getWidth() / 2;
		random = new Random();
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(4);

		font2 = new BitmapFont();
		font2.setColor(Color.WHITE);
		font2.getData().setScale(6);

		for(int i = 0 ; i<numberOfEnemies;i++){

			enemyOffset[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
			enemyOffset2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
			enemyOffset3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

			enemyCircles[i] = new Circle();
			enemyCircles2[i] = new Circle();
			enemyCircles3[i] = new Circle();

			enemyX[i] = Gdx.graphics.getWidth() - enemy1.getWidth() / 2 + i * distance;

		}




	}

	@Override
	public void render () {

		batch.begin();
		batch.draw(background,0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());



		if(gameState == 1){


			if(enemyX[scoreEnemy] < (Gdx.graphics.getWidth() / 2 - bird.getHeight() / 2)){
				score++;

				if(scoreEnemy < (numberOfEnemies - 1)){
					scoreEnemy++;
				}else{
					scoreEnemy = 0;
				}
			}

			if(Gdx.input.justTouched()){
				velocity = -7;
			}

			for(int i = 0; i < numberOfEnemies ; i++) {

				if(enemyX[i] < -enemy1.getWidth()){
					enemyX[i] = enemyX[i] + numberOfEnemies * distance;
					enemyOffset[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffset2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffset3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

				}else{
					enemyX[i] = enemyX[i] - enemyVelocity;
				}

				enemyX[i] = enemyX[i] - enemyVelocity;

				batch.draw(enemy1, enemyX[i],Gdx.graphics.getHeight() / 2 + enemyOffset[i], Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);
				batch.draw(enemy1, enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOffset2[i], Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);
				batch.draw(enemy1, enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOffset3[i], Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);

				enemyCircles[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight() / 2 + enemyOffset[i] + Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 30);
				enemyCircles2[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight() / 2 + enemyOffset2[i] + Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 30);
				enemyCircles3[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight() / 2 + enemyOffset3[i] + Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 30);

				if(Intersector.overlaps(birdCircle,enemyCircles[i]) || Intersector.overlaps(birdCircle, enemyCircles2[i]) || Intersector.overlaps(birdCircle, enemyCircles3[i])){
					gameState = 2;
				}

			}



			if(birdY > 0 ) {
				velocity = velocity + gravity;
				birdY = birdY - velocity;
			}else{
				gameState = 2;
			}
		}else if (gameState == 0){
			if(Gdx.input.justTouched()){
				gameState = 1;
			}
		}else if(gameState == 2){
			font2.draw(batch, "Game Over! Tap To Play Again!",Gdx.graphics.getWidth()/5,Gdx.graphics.getHeight()/2);
			if (Gdx.input.justTouched()){
				gameState = 1;

				birdY = Gdx.graphics.getHeight() / 3;
				for(int i = 0 ; i<numberOfEnemies;i++){

					enemyOffset[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffset2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffset3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

					enemyCircles[i] = new Circle();
					enemyCircles2[i] = new Circle();
					enemyCircles3[i] = new Circle();

					enemyX[i] = Gdx.graphics.getWidth() - enemy1.getWidth() / 2 + i * distance;

				}
				velocity = 0;
				score = 0;
				scoreEnemy = 0;
			}
		}

		batch.draw(bird,birdX,birdY,Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10);
		font.draw(batch, String.valueOf(score),100,200);
		batch.end();

		birdCircle.set(birdX + Gdx.graphics.getWidth()/30,birdY + Gdx.graphics.getWidth()/20,Gdx.graphics.getWidth()/30);

	}
	
	@Override
	public void dispose () {

	}
}

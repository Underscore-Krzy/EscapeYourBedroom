package com.example.escapeyourbedroom;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static com.example.escapeyourbedroom.EscapeRoomGame.*;

class ClickableSprite extends ImageView {
    boolean isZoomed = false;
    String name;
    double x;
    double y;
    Image normalImage;
    Image zoomedImage;
    ClickableSprite(String imagePath, String name, double spriteX, double spriteY) {
        // Set the displayed image
        normalImage = new Image(imagePath);
        setImage(normalImage);
        this.name = name;
        x = spriteX;
        y = spriteY;
        // setTranslate moves the image from [0,0], which is the middle of the screen. Apparently it's the only way to move the image because the other two don't work? Classic javaFX shenanigans
        setTranslateX(x);
        setTranslateY(y);
        root.getChildren().add(this);
    }
    void hide() {
        setVisible(false);
    }
    void show() {
        setVisible(true);
        toFront();
    }
    void setHighlightOnHover() {

        // For something funny, try to put your mouse on the edge of a sprite (try different edges, only works on some)

        // TODO: Make highlighting add a white outline to the sprite instead of resizing it (might be difficult)
        setOnMouseEntered(event -> {
            if (!isZoomed) {
                // When mouse enters a sprite, show its name on the name tag and highlight it
                nameTag.setText(name);
                setScaleX(this.getScaleX() * 1.1);
                setScaleY(this.getScaleY() * 1.1);
                nameTag.show();
            }
        });
        setOnMouseExited(event -> {
            if (!isZoomed) {
                // When the mouse exits the sprite, hide the name tag and stop highlighting it
                nameTag.hide();
                setScaleX(this.getScaleX() * (1 / 1.1));
                setScaleY(this.getScaleY() * (1 / 1.1));
            }
        });
        setOnMouseMoved(mouseEvent -> {
            // If the sprite its zoomed in, make the name tag follow the cursor; otherwise hide it
            if (!isZoomed) nameTag.setPosToCursor(mouseEvent.getSceneX(),  mouseEvent.getSceneY());
            else nameTag.hide();
        });
    }
    void setParentScene(int sceneIndex) {
       sceneSprites.get(sceneIndex).add(this);
       if (sceneIndex != currentScene) hide();
    }
    // The name needs some refinement
    void zoomInto() {
        // Hide all sprites on the current scene
        updateSpritesVisibility(currentScene, false);

        // TEMP: move the clicked sprite to the middle, increase its size and show it
        setImage(zoomedImage);
        setTranslateX(0);
        setTranslateY(0);
        show();
        isZoomed = true;
    }
    void zoomOut() {
        // Show all sprites on the current scene
        updateSpritesVisibility(currentScene, true);
        setImage(normalImage);
        setTranslateX(x);
        setTranslateY(y);
        isZoomed = false;
    }
    void zoomHandler() {
        if (isZoomed) {
            zoomOut();
        }
        else zoomInto();
    }
    void setZoomedImage(String imagePath) {
        zoomedImage = new Image(imagePath);
    }
    void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
        setTranslateX(x);
        setTranslateY(y);
    }
}
package model;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageObject implements GraphicObject {
    private int x, y; // 이미지의 위치
    private int width, height; // 이미지의 크기
    private String imagePath; // 이미지 파일 경로
    private Image image; // 실제 이미지 객체

    public ImageObject(int x, int y, int width, int height, String imagePath) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.imagePath = imagePath;

        // 이미지 로드 시도
        try {
            this.image = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            System.out.println("Error loading image: " + imagePath);
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics g) {
        if (image != null) {
            System.out.println("Drawing an image at (" + x + ", " + y + ") with width " + width + " and height " + height + " from path: " + imagePath);
            g.drawImage(image, x, y, width, height, null); // 이미지를 그리기
        } else {
            System.out.println("Failed to load image: " + imagePath);
        }
    }

    @Override
    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
}

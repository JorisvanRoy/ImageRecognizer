package org.example.ImageRecognizer;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.Stack;

public class FloodFill {
    private static FloodFill instance = new FloodFill();

    public static FloodFill getInstance() {
        return instance;
    }

    public Image floodFill(WritableImage img, Location start, Color replacementColor) {
        PixelReader reader = img.getPixelReader();
        Stack<Location> points = new Stack<>();

        PixelWriter writer = img.getPixelWriter();

        Color targetColor = reader.getColor(start.getX(), start.getY());

        points.push(start);

        while (points.size() > 0) {
            Location loc = points.pop();
            if (loc.getX() < img.getWidth() && loc.getX() > 0 && loc.getY() < img.getHeight() && loc.getY() > 0) {
                double diff = compareColors(reader.getColor(loc.getX(),loc.getY()), targetColor);
                System.out.println(diff);
                if (diff < 0.09) {
                    writer.setColor(loc.getX(),loc.getY(),replacementColor);
                    points.push(new Location(loc.getX()+1, loc.getY()));
                    points.push(new Location(loc.getX()-1, loc.getY()));
                    points.push(new Location(loc.getX(), loc.getY()-1));
                    points.push(new Location(loc.getX(), loc.getY()+1));
                }
            }
        }

        return img;

    }

    public double compareColors(Color color1, Color color2) {
        double redMean = (color1.getRed() + color2.getRed())/2;
        double red = color1.getRed() - color2.getRed();
        double green = color1.getGreen() - color2.getGreen();
        double blue = color1.getBlue() - color2.getBlue();
        return (color1.getRed() - color2.getRed() )*(color1.getRed() - color2.getRed()) + (color1.getGreen() - color2.getGreen())*
                (color1.getGreen() - color2.getGreen()) + (color1.getBlue() - color2.getBlue())*(color1.getBlue() - color2.getBlue());

    }
}

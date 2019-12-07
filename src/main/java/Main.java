import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.InputStream;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Image recognizer");

        /*Color c = Color.rgb(101,65,16);

        System.out.println(FloodFill.getInstance().compareColors(c, Color.rgb(56,56,56)));*/

        File file = getImage(stage);

        WritableImage img = SwingFXUtils.toFXImage(parseImage(file), null);

        ImageView imageView = new ImageView();
        imageView.setImage(img);

        GridPane gp = new GridPane();

        imageView.setImage(FloodFill.getInstance().floodFill(img,new Location(1,1),new Color(1,1,1,0)));


        gp.add(imageView,0,0);

        stage.setScene(new Scene(gp,img.getWidth(),img.getHeight()));
        stage.show();
    }

    public BufferedImage parseImage(File file) {
        try {
            return ImageIO.read(file);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public File getImage(Stage stage) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select an image");

        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );

        File file = chooser.showOpenDialog(stage);

        if (file != null) {
            if (FilenameUtils.getExtension(file.getName()).equals("jpg") || FilenameUtils.getExtension(file.getName()).equals("png"))
                return file;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Select an image");
        alert.setHeaderText("You have to select an image");
        alert.setContentText("You can choose between an jpeg and png file");
        alert.showAndWait();
        return getImage(stage);
    }
}

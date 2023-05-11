package com.example.baitaplonoop.controller;

import com.example.baitaplonoop.sql.DBConnect;
import com.example.baitaplonoop.util.showTreeViewCategory;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.AnchorPane;

import java.io.File;

import javafx.scene.image.PixelFormat;


import java.io.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.baitaplonoop.util.addValueComboBox;
import javafx.stage.FileChooser;

public class GUI32Controller implements Initializable {
    public TreeView<String> showCategory_tv;
    public TextArea questionText_tf;
    public TextField questionName_tf;
    public Label categoryName_lb;
    public Button addQuestion_btn;
    public TextField choice1_tf;
    public ComboBox<String> gradeChoice1_cb;
    public ComboBox<String> gradeChoice2_cb;
    public TextField choice2_tf;
    public TextField choice3_tf;
    public ComboBox<String> gradeChoice3_cb;
    public TextField choice4_tf;
    public ComboBox<String> gradeChoice4_cb;
    public TextField choice5_tf;
    public ComboBox<String> gradeChoice5_cb;
    public TextField choice6_tf;
    public ComboBox<String> gradeChoice6_cb;
    public Button createChoice_btn;
    public AnchorPane paneChoice2_ap;
    public AnchorPane buttonPane_ap;
    public AnchorPane paneInScrollPane_ap;
    public Button addPictureQuestion_btn;
    public ImageView imageQuestion_iv;

    public Button imageQuestion_btn;

    boolean checkAddCategoryQuestion;
    String nameCategoryQuestion;
    Double gradeChoice1, gradeChoice2, gradeChoice3, gradeChoice4, gradeChoice5, gradeChoice6;

    DBConnect db = new DBConnect();
    private FileChooser fileChooser;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        addValueComboBox.addValue(gradeChoice1_cb);
        addValueComboBox.addValue(gradeChoice2_cb);
        addValueComboBox.addValue(gradeChoice3_cb);
        addValueComboBox.addValue(gradeChoice4_cb);
        addValueComboBox.addValue(gradeChoice5_cb);
        addValueComboBox.addValue(gradeChoice6_cb);

        gradeChoice1_cb.setOnAction(gradeChoice1Event -> gradeChoice1 = addValueComboBox.convertStringToDouble(gradeChoice1_cb));
        gradeChoice2_cb.setOnAction(gradeChoice2Event -> gradeChoice2 = addValueComboBox.convertStringToDouble(gradeChoice2_cb));
        gradeChoice3_cb.setOnAction(gradeChoice3Event -> gradeChoice3 = addValueComboBox.convertStringToDouble(gradeChoice3_cb));
        gradeChoice4_cb.setOnAction(gradeChoice4Event -> gradeChoice4 = addValueComboBox.convertStringToDouble(gradeChoice4_cb));
        gradeChoice5_cb.setOnAction(gradeChoice5Event -> gradeChoice5 = addValueComboBox.convertStringToDouble(gradeChoice5_cb));
        gradeChoice6_cb.setOnAction(gradeChoice6Event -> gradeChoice6 = addValueComboBox.convertStringToDouble(gradeChoice6_cb));

        // Event to show treeView
        categoryName_lb.setOnMouseClicked(mouseEvent -> {
            showCategory_tv.setVisible(true);
            categoryName_lb.setVisible(false);
            checkAddCategoryQuestion = true;
            TreeItem<String> root = new TreeItem<>("Course IT:");
            showTreeViewCategory.setTreeViewImport("Select * from Category where parentID IS NULL", root);
            showCategory_tv.setRoot(root);
            showCategory_tv.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
                categoryName_lb.setText(newValue.getValue());
                nameCategoryQuestion = newValue.getValue();
            });
        });
        // Event to add Question into Database
        addQuestion_btn.setOnMouseClicked(saveChangeEvent ->{
            Alert alertAddCategory = new Alert(Alert.AlertType.INFORMATION);
            alertAddCategory.setTitle("Add Category Information");
            ButtonType btnContinue = new ButtonType("Oke", ButtonBar.ButtonData.YES);
            ButtonType btnBack = new ButtonType("Home page", ButtonBar.ButtonData.NO);
            alertAddCategory.getButtonTypes().setAll(btnContinue, btnBack);

            if(!checkAddCategoryQuestion || questionName_tf.getText() == null || questionText_tf.getText() == null) {
                alertAddCategory.setContentText("Please fill the blank field");
                alertAddCategory.setHeaderText("You must fill all field!");
                alertAddCategory.showAndWait();
            } else {
                String IDCategoryQuestion;
                try {
                    IDCategoryQuestion = String.valueOf(db.FindCategoryID(nameCategoryQuestion));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                // To add Question into database
                Image image = imageQuestion_iv.getImage();
                int image_width = (int) image.getWidth();
                int image_height = (int) image.getHeight();
                byte[] buffer = new byte[image_height*image_width*4];
                image.getPixelReader().getPixels(0, 0, image_width, image_height, PixelFormat.getByteBgraInstance(), buffer, 0, image_width*4);
                String[] addQuestion = {IDCategoryQuestion, questionName_tf.getText(), questionText_tf.getText(), "1"};
                db.InsertQuestion(addQuestion, buffer);
                // To add Choice into Database
                if(!choice1_tf.getText().equals("")){
                    String[] addChoice1 = {choice1_tf.getText(), String.valueOf(gradeChoice1), questionName_tf.getText() + "1", questionName_tf.getText(), null};
                    db.InsertChoice(addChoice1);
                }
                if(!choice2_tf.getText().equals("")) {
                    String[] addChoice2 = {choice2_tf.getText(), String.valueOf(gradeChoice2), questionName_tf.getText() + "2", questionName_tf.getText(), null};
                    db.InsertChoice(addChoice2);
                }
                if(!choice3_tf.getText().equals("")){
                    String[] addChoice3 = {choice3_tf.getText(), String.valueOf(gradeChoice3), questionName_tf.getText() + "3", questionName_tf.getText(), null};
                    db.InsertChoice(addChoice3);
                }
                if(!choice4_tf.getText().equals("")) {
                    String[] addChoice4 = {choice4_tf.getText(), String.valueOf(gradeChoice4), questionName_tf.getText() + "4", questionName_tf.getText(), null};
                    db.InsertChoice(addChoice4);
                }
                if(!choice5_tf.getText().equals("")) {
                    String[] addChoice5 = {choice5_tf.getText(), String.valueOf(gradeChoice5), questionName_tf.getText() + "5", questionName_tf.getText(), null};
                    db.InsertChoice(addChoice5);
                }
                if(!choice6_tf.getText().equals("")) {
                    String[] addChoice6 = {choice1_tf.getText(), String.valueOf(gradeChoice1), questionName_tf.getText() + "6", questionName_tf.getText(), null};
                    db.InsertChoice(addChoice6);
                }


                alertAddCategory.setContentText("Add Question Done!");
                alertAddCategory.setHeaderText(null);
                alertAddCategory.showAndWait();
            }
        });
        // Event to creat new 3 choice
        createChoice_btn.setOnMouseClicked(createChoiceEvent ->{
            paneChoice2_ap.setTranslateY(239);
            buttonPane_ap.setTranslateY(239);
        });


        imageQuestion_iv = new ImageView();
        fileChooser = new FileChooser();
        addPictureQuestion_btn.setOnMouseClicked(addPictureQuestionEvent ->{

            File file = fileChooser.showOpenDialog(null);

            if(file != null){
                Image image = new Image(file.toURI().toString());
                imageQuestion_iv.setImage(image);
            }
        });



//        imageQuestion_btn.setOnMouseClicked(addImageQuestionEvent ->{
//
//        });
    }

    public void loadImageQuestion(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Chọn ảnh");

        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

        // Show open file dialog
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try {
                // Tạo đường dẫn tới thư mục "anh"
                String imagePath = "F:/Project/OOP/src/main/resources/com/example/baitaplonoop/Image/Question/" + file.getName();

                // Đọc dữ liệu ảnh vào File
                FileInputStream fis = new FileInputStream(file);
                FileOutputStream fos = new FileOutputStream(imagePath);

                // Đọc và ghi dữ liệu ảnh vào file ảnh trong thư mục "anh"
                byte[] buffer = new byte[1024];
                int length;
                while ((length = fis.read(buffer)) > 0) {
                    fos.write(buffer, 0, length);
                }

                // Đóng input/output stream
                fis.close();
                fos.close();

                // Hiển thị ảnh trong ImageView
                Image image = new Image(new File(imagePath).toURI().toString());
                imageQuestion_iv.setImage(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadImageChoice(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Chọn ảnh");

        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

        // Show open file dialog
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try {
                // Tạo đường dẫn tới thư mục "anh"
                String imagePath = "F:/Project/OOP/src/main/resources/com/example/baitaplonoop/Image/Choice/" + file.getName();

                // Đọc dữ liệu ảnh vào File
                FileInputStream fis = new FileInputStream(file);
                FileOutputStream fos = new FileOutputStream(imagePath);

                // Đọc và ghi dữ liệu ảnh vào file ảnh trong thư mục "anh"
                byte[] buffer = new byte[1024];
                int length;
                while ((length = fis.read(buffer)) > 0) {
                    fos.write(buffer, 0, length);
                }

                // Đóng input/output stream
                fis.close();
                fos.close();

                // Hiển thị ảnh trong ImageView
                Image image = new Image(new File(imagePath).toURI().toString());
                imageQuestion_iv.setImage(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}

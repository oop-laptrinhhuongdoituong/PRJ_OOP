package com.example.baitaplonoop.controller;

import com.example.baitaplonoop.sql.DBConnect;
import com.example.baitaplonoop.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Random;
import java.util.ResourceBundle;

public class GUI65Controller implements Initializable {
    @FXML
    private ToggleButton add;
    final int numberQuestionsInAPage = 9;
    @FXML
    private CheckBox gui6_5CheckBox;
    @FXML
    private AnchorPane gui6_5;
    @FXML
    private Label Default;
    @FXML
    private Pagination pagination;
    @FXML
    private TreeView<String> category;
    @FXML
    private ComboBox<String> numberOfQuestions;
    private ListView<String> questionsListView = new ListView<>();
    private ObservableList<String> numberOfComboBox = FXCollections.observableArrayList();
    private ObservableList<String> questionsList = FXCollections.observableArrayList();
    private ObservableList<Pair<String, String>> questionRandom = FXCollections.observableArrayList();
    private ObservableList<Pair<String, String>> subQuestionRandom = FXCollections.observableArrayList();
    DBConnect db = new DBConnect();

    private void showQuestion() {
        category.setOnKeyPressed(keyEvent -> {

            if (keyEvent.getCode() == KeyCode.ENTER) {
                questionsList.clear();
                numberOfComboBox.clear();
                numberOfQuestions.getSelectionModel().clearSelection();
                questionsListView.setItems(questionsList);
                TreeItem<String> item = category.getSelectionModel().getSelectedItem();
                category.setVisible(false);
                Default.setText(FindCategoryInfo.findCategoryName(item.getValue()));
                int page = 0;
                if (gui6_5CheckBox.isSelected()) {
                    insertQuestionInto.insertQuestionIntoQuestionListWithSubcategory(item, questionsList, questionRandom);
                    if (Integer.parseInt(FindCategoryInfo.findNumberQuestionOfCategoryAndSubCategories(item)) % numberQuestionsInAPage == 0)
                        page = Integer.parseInt(FindCategoryInfo.findNumberQuestionOfCategoryAndSubCategories(item)) / numberQuestionsInAPage;
                    else
                        page = Integer.parseInt(FindCategoryInfo.findNumberQuestionOfCategoryAndSubCategories(item)) / numberQuestionsInAPage + 1;
                    for (int i = 1; i <= Integer.parseInt(FindCategoryInfo.findNumberQuestionOfCategoryAndSubCategories(item)); i++) {
                        numberOfComboBox.add(Integer.toString(i));
                    }
                } else {
                    insertQuestionInto.insertQuestionIntoQuestionListWithoutSubcategory(item, questionsList, questionRandom);
                    if (Integer.parseInt(FindCategoryInfo.findNumberQuestionsOfACategory(item)) % numberQuestionsInAPage == 0)
                        page = Integer.parseInt(FindCategoryInfo.findNumberQuestionsOfACategory(item)) / numberQuestionsInAPage;
                    else
                        page = Integer.parseInt(FindCategoryInfo.findNumberQuestionsOfACategory(item)) / numberQuestionsInAPage + 1;
                    for (int i = 1; i <= Integer.parseInt(FindCategoryInfo.findNumberQuestionsOfACategory(item)); i++) {
                        numberOfComboBox.add(Integer.toString(i));
                    }
                }

                pagination.setPageCount(page);
                pagination.setPageFactory(pageIndex -> {
                    int fromIndex = pageIndex * numberQuestionsInAPage;
                    int toIndex = Math.min(fromIndex + numberQuestionsInAPage, questionsList.size());
                    questionsListView.setItems(FXCollections.observableArrayList(questionsList.subList(fromIndex, toIndex)));
                    return questionsListView;
                });
                pagination.setVisible(true);
                numberOfQuestions.setItems(numberOfComboBox);
                questionsListView.setVisible(true);
            }
        });
    }

    private void randomQuestionFromCategory() {
        String numberofQuestions = numberOfQuestions.getValue();
        Random random=new Random();
        while (subQuestionRandom.size()<Integer.parseInt(numberofQuestions)){
            int randomIndex=random.nextInt(questionRandom.size());
            if(!subQuestionRandom.contains(questionRandom.get(randomIndex))){
                subQuestionRandom.add(questionRandom.get(randomIndex));
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        pagination.setVisible(false);
        category.setVisible(false);
        Default.setText("Default");
        Default.setOnMouseClicked(mouseEvent -> {


            TreeItem<String> root = new TreeItem<>("For IT");
            try {
                insertCategoryIntoTreeView.insertCategory("select * from dbo.Category where parentID is NULL", root);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            category.setRoot(root);
            category.toFront();
            category.setVisible(true);
        });
        gui6_5CheckBox.setSelected(false);
        showQuestion();
        gui6_5CheckBox.setOnAction(event -> {
            showQuestion();
        });
        add.setOnAction(event -> {
            if (numberOfQuestions.getValue() != null) {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/com/example/baitaplonoop/GUI62.fxml"));
                try {
                    Parent gui62 = loader.load();
                    Scene scene = new Scene(gui62);
                    GUI62Controller gui62Controller=loader.getController();
                    randomQuestionFromCategory();
                    gui62Controller.setChosenQuestions(subQuestionRandom);
                    stage.setScene(scene);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        gui6_5.setOnMouseClicked(mouseEvent -> {
            if (category.isVisible() && IsMouseOnLabel.isMouseOnLabel(Default, mouseEvent) == false)
                category.setVisible(false);
        });
    }
}
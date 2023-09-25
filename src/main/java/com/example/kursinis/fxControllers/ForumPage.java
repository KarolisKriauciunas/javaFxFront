package com.example.kursinis.fxControllers;

import com.example.kursinis.ParcelApplication;
import com.example.kursinis.model.Forumas.Comment;
import com.example.kursinis.model.Forumas.Forum;
import com.example.kursinis.utilities.CallEndpoints;
import com.example.kursinis.utilities.FxUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.json.JSONObject;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class ForumPage implements Initializable {

    public TableColumn<Forum, Long> id;
    public TableColumn<Forum, String> forumName;
    public TableColumn<Forum, Boolean> access;
    public TableView<Forum> forumTable;
    @FXML
    public TreeView<Comment> treeView;
    public TextField idField;
    public TextField commentField;
    public TextField idComment;
    public TextField nameField;
    public ObjectMapper mapper = new ObjectMapper();
    public List<Forum> forums;
    public List<Comment> comments;
    String response = CallEndpoints.Get("http://localhost:8080/forums");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillTable();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(ParcelApplication.class.getResource("forum-page-managers.fxml"));
    }
    void getInitialForums() {
        try {
            forums = Arrays.asList(mapper.readValue(response, Forum[].class));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
    void getInitialComments(Long id)
    {
        String response = CallEndpoints.Get("http://localhost:8080/comments?forumID=" + id);
        try {
            comments = Arrays.asList(mapper.readValue(response, Comment[].class));
        }
        catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }
    }
    public void fillTable() {
        getInitialForums();
        id.setCellValueFactory(new PropertyValueFactory<>("forumID"));
        forumName.setCellValueFactory(new PropertyValueFactory<>("forumName"));
        forumTable.getItems().setAll(forums);
    }

    public void fillComments(Long id) {
        getInitialComments(id);
        TreeItem root = new TreeItem("Comments");
        treeView.setRoot(root);
        List<Comment> parentComments = comments.stream().filter(comment -> comment.getReplyid() == null || comment.getReplyid() == 0).toList();

        for (Comment comment : parentComments) {
            TreeItem<Comment> parent = new TreeItem(comment);
            if(checkChildren(comments, comment.getCommentid())) {
                createBranch(comments, parent,comment.getCommentid());
            }
            root.getChildren().add(parent);
        }
        }

    public void createBranch(List<Comment> comments, TreeItem item, Long id)
    {
        List<Comment> filteredComments = comments.stream().filter(comment -> comment.getReplyid() == id).toList();
        for(Comment current : filteredComments)
        {
            TreeItem<Comment> child = new TreeItem(current);
            item.getChildren().add(child);
            if(checkChildren(comments, current.getCommentid()))
            {
                createBranch(comments, child, current.getCommentid());
            }
        }
    }

    public Boolean checkChildren(List<Comment> comments, Long id)
    {
        return !comments.stream().filter(comment -> comment.getReplyid() == id).toList().isEmpty();
    }

    public void create(ActionEvent actionEvent) {
        if(nameField.getText() != null){
            JSONObject json = new JSONObject();
            json.put("forumName", nameField.getText());
            json.put("forumAccess", "true");

            CallEndpoints.Post("http://localhost:8080/create/forum", String.valueOf(json));

            FxUtils.alert(Alert.AlertType.INFORMATION, "Success", "Creation complete", "");

            fillTable();
        }
    }
    public void createComment(ActionEvent actionEvent) {
        if(nameField.getText() != null){
            JSONObject json = new JSONObject();
            json.put("content", commentField.getText());
            json.put("forumId", idField.getText());
            json.put("replyId", "0");
            System.out.println((json));
            System.out.println(CallEndpoints.Post("http://localhost:8080/create/comment", String.valueOf(json)));

            FxUtils.alert(Alert.AlertType.INFORMATION, "Success", "Creation complete", "");

            fillComments(Long.parseLong(idField.getText()));
        }
    }
    public void replyComment(ActionEvent actionEvent) {
        if(nameField.getText() != null){
            JSONObject json = new JSONObject();
            json.put("content", commentField.getText());
            json.put("forumId", idField.getText());
            if(idComment == null)
                json.put("replyId", "0");
            else
            json.put("replyId", idComment.getText());
            System.out.println((json));
            System.out.println(CallEndpoints.Post("http://localhost:8080/create/comment", String.valueOf(json)));

            FxUtils.alert(Alert.AlertType.INFORMATION, "Success", "Creation complete", "");

            fillComments(Long.parseLong(idField.getText()));
        }
    }

    public void getCommentInfo(MouseEvent actionEvent) {
        Comment comment =  (Comment)treeView.getSelectionModel().getSelectedItem().getValue();
    }
    public void goBack(ActionEvent actionEvent) {
        FxUtils.openFxPage("manager-main-page.fxml", nameField);
    }

    @FXML
    public void rowClicked(MouseEvent event){
        Forum clickedForum = forumTable.getSelectionModel().getSelectedItem();
        if(clickedForum != null){
            idField.setText(clickedForum.getForumID().toString());
            fillComments(clickedForum.getForumID());
        }
    }

}

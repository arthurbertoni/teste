package view;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Item;
import service.ItemService;

public class ListaView extends Application {
    private ItemService itemService;
    private ListView<Item> listView;
    private ObservableList<Item> observableList;
    private TextField campoNome;
    private TextField campoDescricao;
    
    @Override
    public void start(Stage primaryStage) {
        itemService = new ItemService();
        
        // Configurar componentes
        configurarComponentes();
        
        // Layout principal
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_CENTER);
        
        // Título
        Label titulo = new Label("Lista de Itens");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        
        // Formulário de entrada
        VBox formulario = criarFormulario();
        
        // Lista
        VBox containerLista = criarContainerLista();
        
        root.getChildren().addAll(titulo, formulario, containerLista);
        
        // Cena e palco
        Scene scene = new Scene(root, 500, 600);
        primaryStage.setTitle("Lista JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Carregar dados iniciais
        atualizarLista();
    }
    
    private void configurarComponentes() {
        // ListView
        listView = new ListView<>();
        observableList = FXCollections.observableArrayList();
        listView.setItems(observableList);
        listView.setPrefHeight(300);
        
        // Campos de entrada
        campoNome = new TextField();
        campoNome.setPromptText("Nome do item");
        
        campoDescricao = new TextField();
        campoDescricao.setPromptText("Descrição do item");
    }
    
    private VBox criarFormulario() {
        VBox formulario = new VBox(10);
        formulario.setAlignment(Pos.CENTER);
        
        Label labelForm = new Label("Adicionar Novo Item:");
        labelForm.setStyle("-fx-font-weight: bold;");
        
        HBox botoes = new HBox(10);
        botoes.setAlignment(Pos.CENTER);
        
        Button btnAdicionar = new Button("Adicionar");
        btnAdicionar.setOnAction(e -> adicionarItem());
        
        Button btnLimpar = new Button("Limpar");
        btnLimpar.setOnAction(e -> limparCampos());
        
        botoes.getChildren().addAll(btnAdicionar, btnLimpar);
        
        formulario.getChildren().addAll(
            labelForm,
            campoNome,
            campoDescricao,
            botoes
        );
        
        return formulario;
    }
    
    private VBox criarContainerLista() {
        VBox container = new VBox(10);
        container.setAlignment(Pos.CENTER);
        
        Label labelLista = new Label("Itens na Lista:");
        labelLista.setStyle("-fx-font-weight: bold;");
        
        HBox botoesLista = new HBox(10);
        botoesLista.setAlignment(Pos.CENTER);
        
        Button btnRemover = new Button("Remover Selecionado");
        btnRemover.setOnAction(e -> removerItemSelecionado());
        
        Button btnAtualizar = new Button("Atualizar Lista");
        btnAtualizar.setOnAction(e -> atualizarLista());
        
        botoesLista.getChildren().addAll(btnRemover, btnAtualizar);
        
        container.getChildren().addAll(labelLista, listView, botoesLista);
        
        return container;
    }
    
    private void adicionarItem() {
        try {
            String nome = campoNome.getText();
            String descricao = campoDescricao.getText();
            
            itemService.adicionarItem(nome, descricao);
            limparCampos();
            atualizarLista();
            
            mostrarMensagem("Item adicionado com sucesso!", Alert.AlertType.INFORMATION);
            
        } catch (IllegalArgumentException e) {
            mostrarMensagem("Erro: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    private void removerItemSelecionado() {
        Item itemSelecionado = listView.getSelectionModel().getSelectedItem();
        
        if (itemSelecionado != null) {
            itemService.removerItem(itemSelecionado);
            atualizarLista();
            mostrarMensagem("Item removido com sucesso!", Alert.AlertType.INFORMATION);
        } else {
            mostrarMensagem("Selecione um item para remover!", Alert.AlertType.WARNING);
        }
    }
    
    private void atualizarLista() {
        observableList.clear();
        observableList.addAll(itemService.obterTodosItens());
    }
    
    private void limparCampos() {
        campoNome.clear();
        campoDescricao.clear();
    }
    
    private void mostrarMensagem(String mensagem, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
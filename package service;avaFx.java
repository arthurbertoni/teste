package service;

import model.Item;
import repository.ItemRepository;
import java.util.List;

public class ItemService {
    private ItemRepository repository;
    
    public ItemService() {
        this.repository = new ItemRepository();
    }
    
    public List<Item> obterTodosItens() {
        return repository.listarTodos();
    }
    
    public void adicionarItem(String nome, String descricao) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        
        Item novoItem = new Item(0, nome.trim(), descricao != null ? descricao.trim() : "");
        repository.adicionarItem(novoItem);
    }
    
    public void removerItem(Item item) {
        if (item != null) {
            repository.removerItem(item);
        }
    }
    
    public void atualizarItem(Item item, String novoNome, String novaDescricao) {
        if (item != null && novoNome != null && !novoNome.trim().isEmpty()) {
            item.setNome(novoNome.trim());
            item.setDescricao(novaDescricao != null ? novaDescricao.trim() : "");
            repository.atualizarItem(item);
        }
    }
}
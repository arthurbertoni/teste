package repository;

import model.Item;
import java.util.ArrayList;
import java.util.List;

public class ItemRepository {
    private List<Item> itens;
    private int proximoId;
    
    public ItemRepository() {
        this.itens = new ArrayList<>();
        this.proximoId = 1;
        // Dados iniciais para teste
        adicionarItem(new Item(proximoId++, "Item 1", "Primeira descrição"));
        adicionarItem(new Item(proximoId++, "Item 2", "Segunda descrição"));
    }
    
    public List<Item> listarTodos() {
        return new ArrayList<>(itens);
    }
    
    public void adicionarItem(Item item) {
        if (item.getId() == 0) {
            item.setId(proximoId++);
        }
        itens.add(item);
    }
    
    public void removerItem(Item item) {
        itens.remove(item);
    }
    
    public Item buscarPorId(int id) {
        return itens.stream()
                .filter(item -> item.getId() == id)
                .findFirst()
                .orElse(null);
    }
    
    public void atualizarItem(Item item) {
        for (int i = 0; i < itens.size(); i++) {
            if (itens.get(i).getId() == item.getId()) {
                itens.set(i, item);
                break;
            }
        }
    }
}
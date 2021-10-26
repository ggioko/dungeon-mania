package dungeonmania.items;

import dungeonmania.response.models.ItemResponse;

public class Item {
    String id;
    String type;

    public Item(String id, String type) {
        this.id = id;
        this.type = type;
    }

    public ItemResponse creatResponse() {
        return new ItemResponse(this.id, this.type);
    }

    //getters

    public String getType() {
        return this.type;
    }

}

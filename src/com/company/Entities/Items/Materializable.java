package com.company.Entities.Items;

public interface Materializable {
    Material material = null;

    public default Material getMaterial(){
        return material;
    };
}

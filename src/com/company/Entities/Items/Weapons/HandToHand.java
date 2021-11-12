package com.company.Entities.Items.Weapons;

import com.company.Entities.Entity;

import java.util.List;

public interface HandToHand {
    public boolean hit(Entity sender, List<? extends Entity>... entities);
}

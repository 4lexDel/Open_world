package com.company.Entities.Items.Weapons;

import com.company.Entities.Items.Bullets.Bullet;
import com.company.Entities.Entity;

import java.util.List;

public interface Range {
    public void shot(Entity sender, List<Bullet> bullets);
}

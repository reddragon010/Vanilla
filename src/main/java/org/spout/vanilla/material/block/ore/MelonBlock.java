/*
 * This file is part of Vanilla.
 *
 * Copyright (c) 2011-2012, SpoutDev <http://www.spout.org/>
 * Vanilla is licensed under the SpoutDev License Version 1.
 *
 * Vanilla is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * In addition, 180 days after any changes are published, you can use the
 * software, incorporating those changes, under the terms of the MIT license,
 * as described in the SpoutDev License Version 1.
 *
 * Vanilla is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License,
 * the MIT license and the SpoutDev License Version 1 along with this program.
 * If not, see <http://www.gnu.org/licenses/> for the GNU Lesser General Public
 * License and see <http://www.spout.org/SpoutDevLicenseV1.txt> for the full license,
 * including the MIT license.
 */
package org.spout.vanilla.material.block.ore;

import java.util.ArrayList;
import java.util.Random;

import org.spout.api.entity.Entity;
import org.spout.api.geo.cuboid.Block;

import org.spout.vanilla.enchantment.Enchantments;
import org.spout.vanilla.inventory.VanillaItemStack;
import org.spout.vanilla.material.VanillaMaterials;
import org.spout.vanilla.material.block.solid.Solid;
import org.spout.vanilla.material.item.tool.MiningTool;

public class MelonBlock extends Solid {
	public MelonBlock(String name, int id) {
		super(name, id);
	}

	@Override
	public void initialize() {
		super.initialize();
		this.setHardness(1.0F).setResistance(1.7F);
	}

	@Override
	public ArrayList<VanillaItemStack> getDrops(Block block) {
		ArrayList<VanillaItemStack> drops = new ArrayList<VanillaItemStack>();
		if (block.getSource() instanceof Entity) {
			VanillaItemStack held = (VanillaItemStack) ((Entity) block.getSource()).getInventory().getCurrentItem();
			if (held != null && held.getMaterial() instanceof MiningTool && held.hasEnchantment(Enchantments.SILK_TOUCH)) {
				drops.add(new VanillaItemStack(this, 1));
			} else {
				drops.add(new VanillaItemStack(VanillaMaterials.MELON_SLICE, new Random().nextInt(3 - 7 + 1) + 3));
			}
		}
		return drops;
	}
}
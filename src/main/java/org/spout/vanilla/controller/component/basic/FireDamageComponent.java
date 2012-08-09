/*
 * This file is part of Vanilla.
 *
 * Copyright (c) 2011-2012, VanillaDev <http://www.spout.org/>
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
package org.spout.vanilla.controller.component.basic;

import org.spout.api.Spout;
import org.spout.api.tickable.LogicPriority;
import org.spout.api.tickable.LogicRunnable;
import org.spout.vanilla.controller.VanillaEntityController;
import org.spout.vanilla.controller.source.DamageCause;
import org.spout.vanilla.data.VanillaData;
import org.spout.vanilla.event.entity.EntityCombustEvent;

public class FireDamageComponent extends LogicRunnable<VanillaEntityController> {
	private int fireTicks = 0;
	private boolean isFlammable = true;

	public FireDamageComponent(VanillaEntityController parent, LogicPriority priority) {
		super(parent, priority);
	}

	@Override
	public void onRegistration() {
		fireTicks = getParent().data().get(VanillaData.FIRE_TICKS);
		isFlammable = getParent().data().get(VanillaData.FLAMMABLE);
	}

	@Override
	public void onUnregistration() {
		getParent().data().put(VanillaData.FIRE_TICKS, fireTicks);
		getParent().data().put(VanillaData.FLAMMABLE, isFlammable);
	}

	/**
	 * Checks to see if the controller is combustible.
	 * 
	 * @return true is combustible, false if not
	 */
	public boolean isFlammable() {
		return isFlammable;
	}

	/**
	 * Sets if the controller is combustible or not.
	 * 
	 * @param isFlammable flag representing combustible status.
	 */
	public void setFlammable(boolean isFlammable) {
		this.isFlammable = isFlammable;
	}

	/**
	 * Gets the amount of ticks the controller has been on fire.
	 * 
	 * @return amount of ticks
	 */
	public int getFireTicks() {
		return fireTicks;
	}

	/**
	 * Sets the amount of ticks the controller has been on fire.
	 * 
	 * @param fireTicks the new amount of ticks the controller has been on fire for.
	 */
	public void setFireTicks(int fireTicks) {
		if (fireTicks > 0) {
			EntityCombustEvent event = Spout.getEventManager().callEvent(new EntityCombustEvent(getParent().getParent(), fireTicks));
			fireTicks = event.getDuration();
		}
		this.fireTicks = fireTicks;
	}

	@Override
	public void run() {
		if (!isFlammable) {
			fireTicks -= 4;
			if (fireTicks < 0) {
				fireTicks = 0;
			}
			return;
		}

		if (fireTicks % 20 == 0) {
			getParent().getHealth().damage(1, DamageCause.FIRE_CONTACT);
		}

		--fireTicks;
		if (fireTicks < 0) {
			fireTicks = 0;
		}
	}

	@Override
	public boolean shouldRun(float dt) {
		return fireTicks > 0;
	}
}

/*
 * This file is part of Vanilla.
 *
 * Copyright (c) 2011 Spout LLC <http://www.spout.org/>
 * Vanilla is licensed under the Spout License Version 1.
 *
 * Vanilla is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * In addition, 180 days after any changes are published, you can use the
 * software, incorporating those changes, under the terms of the MIT license,
 * as described in the Spout License Version 1.
 *
 * Vanilla is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for
 * more details.
 *
 * You should have received a copy of the GNU Lesser General Public License,
 * the MIT license and the Spout License Version 1 along with this program.
 * If not, see <http://www.gnu.org/licenses/> for the GNU Lesser General Public
 * License and see <http://spout.in/licensev1> for the full license, including
 * the MIT license.
 */
package org.spout.vanilla.protocol.codec.window;

import java.io.IOException;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import org.spout.api.protocol.MessageCodec;

import org.spout.vanilla.protocol.msg.window.WindowEnchantItemMessage;

public class WindowEnchantItemCodec extends MessageCodec<WindowEnchantItemMessage> {
	public WindowEnchantItemCodec() {
		super(WindowEnchantItemMessage.class, 0x6C);
	}

	@Override
	public WindowEnchantItemMessage decode(ByteBuf buffer) throws IOException {
		int transaction = buffer.readByte();
		int enchantment = buffer.readByte();
		return new WindowEnchantItemMessage(transaction, enchantment);
	}

	@Override
	public ByteBuf encode(WindowEnchantItemMessage message) throws IOException {
		ByteBuf buffer = Unpooled.buffer(2);
		buffer.writeByte(message.getTransaction());
		buffer.writeByte(message.getEnchantment());
		return buffer;
	}
}

/**
 * Copyright (c) Lambda Innovation, 2013-2015
 * 本作品版权由Lambda Innovation所有。
 * http://www.li-dev.cn/
 *
 * This project is open-source, and it is distributed under  
 * the terms of GNU General Public License. You can modify
 * and distribute freely as long as you follow the license.
 * 本项目是一个开源项目，且遵循GNU通用公共授权协议。
 * 在遵照该协议的情况下，您可以自由传播和修改。
 * http://www.gnu.org/licenses/gpl.html
 */
package cn.academy.energy.block;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import cn.academy.core.tile.TileInventory;
import cn.academy.energy.api.block.IWirelessMatrix;
import cn.academy.energy.client.render.block.RenderMatrix;
import cn.annoreg.core.Registrant;
import cn.annoreg.mc.RegTileEntity;
import cn.liutils.template.block.IMultiTile;
import cn.liutils.template.block.InfoBlockMulti;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author WeAthFolD
 */
@Registrant
@RegTileEntity
@RegTileEntity.HasRender
public class TileMatrix extends TileInventory implements IWirelessMatrix, IMultiTile {
	
	public static final double 
		MAX_CAPACITY = getCapacity(3, 3), 
		MAX_LATENCY = getLatency(3, 3), 
		MAX_RANGE = getRange(3, 3);

	@RegTileEntity.Render
	@SideOnly(Side.CLIENT)
	public static RenderMatrix renderer;
	
	public TileMatrix() {
		super("wireless_matrix", 4);
	}
	
	//InfoBlockMulti delegation
	InfoBlockMulti info = new InfoBlockMulti(this);
	
	@Override
	public void updateEntity() {
		if(info != null)
			info.update();
	}

	@Override
	public InfoBlockMulti getBlockInfo() {
		return info;
	}

	@Override
	public void setBlockInfo(InfoBlockMulti i) {
		info = i;
	}

	@Override
    public void readFromNBT(NBTTagCompound nbt) {
    	super.readFromNBT(nbt);
    	info = new InfoBlockMulti(this, nbt);
    }
    
	@Override
    public void writeToNBT(NBTTagCompound nbt) {
    	super.writeToNBT(nbt);
    	info.save(nbt);
    }
	
	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	//WEN TODO
	public int getPlateCount() {
		if(true) return 2;
		int count = 0;
		for(int i = 0; i < 3; ++i) {
			if(this.getStackInSlot(i) != null)
				count++;
		}
		return count;
	}
	
	private static int getCapacity(int N, int L) {
		return (int) Math.round(Math.sqrt(N) * L * 6);
	}
	
	private static double getLatency(int N, int L) {
		if(true) return 30;
		return N * L * L * 20;
	}
	
	private static double getRange(int N, int L) {
		if(true) return 15;
		return N * Math.sqrt(L) * 8;
	}
	
	public int getCoreLevel() {
		ItemStack stack = getStackInSlot(3);
		if(true) return 2;
		return stack == null ? 0 : stack.getItemDamage() + 1;
	}
	
	@Override
	public int getCapacity() {
		int N = getPlateCount(), L = getCoreLevel();
		return getCapacity(N, L);
	}

	@Override
	public double getLatency() {
		int N = getPlateCount(), L = getCoreLevel();
		return getLatency(N, L);
	}

	@Override
	public double getRange() {
		int N = getPlateCount(), L = getCoreLevel();
		return getRange(N, L);
	}
	
	//AABB
	@SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
		return INFINITE_EXTENT_AABB;
	}
}

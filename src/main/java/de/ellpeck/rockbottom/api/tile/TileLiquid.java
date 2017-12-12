/*
 * This file ("TileLiquid.java") is part of the RockBottomAPI by Ellpeck.
 * View the source code at <https://github.com/RockBottomGame/>.
 * View information on the project at <https://rockbottom.ellpeck.de/>.
 *
 * The RockBottomAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The RockBottomAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with the RockBottomAPI. If not, see <http://www.gnu.org/licenses/>.
 *
 * © 2017 Ellpeck
 */

package de.ellpeck.rockbottom.api.tile;

import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.render.tile.ITileRenderer;
import de.ellpeck.rockbottom.api.render.tile.TileLiquidRenderer;
import de.ellpeck.rockbottom.api.tile.state.IntProp;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public abstract class TileLiquid extends TileBasic{

    public final IntProp level = new IntProp("level", 0, this.getLevels());

    public TileLiquid(IResourceName name){
        super(name);
        this.addProps(this.level);
    }

    @Override
    protected ITileRenderer createRenderer(IResourceName name){
        return new TileLiquidRenderer(name, this);
    }

    @Override
    public boolean isLiquid(){
        return true;
    }

    public abstract int getLevels();

    public abstract boolean doesFlow();

    public abstract int getFlowSpeed();

    @Override
    public void onChangeAround(IWorld world, int x, int y, TileLayer layer, int changedX, int changedY, TileLayer changedLayer){
        if(changedLayer == TileLayer.MAIN && changedX == x && changedY == y){
            TileState state = world.getState(x, y);
            if(state.getTile().isFullTile()){
                world.setState(layer, x, y, GameContent.TILE_AIR.getDefState());
            }
        }
    }

    @Override
    public void onAdded(IWorld world, int x, int y, TileLayer layer){

    }

    @Override
    public boolean canBreak(IWorld world, int x, int y, TileLayer layer){
        return false;
    }

    @Override
    public boolean canPlaceInLayer(TileLayer layer){
        return layer == TileLayer.LIQUIDS;
    }

    @Override
    public boolean isFullTile(){
        return false;
    }
}
/*
 * NetherEx
 * Copyright (c) 2016-2019 by LogicTechCorp
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package logictechcorp.netherex.world.biome;

import logictechcorp.libraryex.api.world.biome.BiomeBlockType;
import logictechcorp.libraryex.api.world.biome.data.IBiomeData;
import logictechcorp.libraryex.api.world.generation.GenerationStage;
import logictechcorp.libraryex.world.biome.data.BiomeData;
import logictechcorp.libraryex.world.generation.trait.BiomeTraitCluster;
import logictechcorp.libraryex.world.generation.trait.BiomeTraitOre;
import logictechcorp.libraryex.world.generation.trait.BiomeTraitPool;
import logictechcorp.libraryex.world.generation.trait.BiomeTraitScatter;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.entity.monster.EntityBrute;
import logictechcorp.netherex.entity.monster.EntityCoolmarSpider;
import logictechcorp.netherex.entity.monster.EntityFrost;
import logictechcorp.netherex.entity.monster.EntityWight;
import logictechcorp.netherex.init.NetherExBiomes;
import logictechcorp.netherex.init.NetherExBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.biome.Biome;

public class BiomeArcticAbyss extends BiomeNetherEx
{
    private static final IBlockState FROSTBURN_ICE = NetherExBlocks.FROSTBURN_ICE.getDefaultState();
    private static final IBlockState ICY_NETHERRACK = NetherExBlocks.ICY_NETHERRACK.getDefaultState();

    public BiomeArcticAbyss()
    {
        super(NetherEx.instance, new BiomeProperties("Arctic Abyss").setTemperature(0.0F).setRainfall(0.0F).setRainDisabled(), "arctic_abyss");
        this.topBlock = FROSTBURN_ICE;
        this.fillerBlock = ICY_NETHERRACK;
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityFrost.class, 10, 1, 3));
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityCoolmarSpider.class, 35, 1, 4));
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityWight.class, 100, 1, 4));
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityBrute.class, 15, 1, 1));
    }

    @Override
    public IBiomeData getBiomeData()
    {
        IBiomeData biomeData = new BiomeData(NetherExBiomes.ARCTIC_ABYSS, 2, true, false, true);
        biomeData.addBiomeBlock(BiomeBlockType.SURFACE_BLOCK, FROSTBURN_ICE);
        biomeData.addBiomeBlock(BiomeBlockType.SUBSURFACE_BLOCK, ICY_NETHERRACK);
        biomeData.addBiomeBlock(BiomeBlockType.CAVE_CEILING_BLOCK, ICY_NETHERRACK);
        biomeData.addBiomeBlock(BiomeBlockType.CAVE_WALL_BLOCK, ICY_NETHERRACK);
        biomeData.addBiomeBlock(BiomeBlockType.CAVE_FLOOR_BLOCK, ICY_NETHERRACK);
        biomeData.addBiomeBlock(BiomeBlockType.FLUID_BLOCK, MAGMA);
        biomeData.addBiomeTrait(GenerationStage.TERRAIN_ALTERATION, BiomeTraitPool.create(trait ->
        {
            trait.generationAttempts(2);
            trait.generationProbability(0.125);
            trait.minimumGenerationHeight(36);
            trait.maximumGenerationHeight(108);
            trait.blockToSpawn(NetherExBlocks.ICHOR.getDefaultState());
            trait.blockToSurround(FROSTBURN_ICE);
        }));
        biomeData.addBiomeTrait(GenerationStage.DECORATION, BiomeTraitScatter.create(trait ->
        {
            trait.generationAttempts(5);
            trait.randomizeGenerationAttempts(true);
            trait.minimumGenerationHeight(4);
            trait.maximumGenerationHeight(124);
            trait.blockToSpawn(NetherExBlocks.BLUE_FIRE.getDefaultState());
            trait.blockToTarget(FROSTBURN_ICE);
            trait.placement(BiomeTraitScatter.Placement.ON_GROUND);
        }));
        biomeData.addBiomeTrait(GenerationStage.DECORATION, BiomeTraitCluster.create(trait ->
        {
            trait.generationAttempts(10);
            trait.randomizeGenerationAttempts(true);
            trait.minimumGenerationHeight(4);
            trait.maximumGenerationHeight(124);
            trait.blockToAttachTo(ICY_NETHERRACK);
            trait.direction(EnumFacing.DOWN);
        }));
        biomeData.addBiomeTrait(GenerationStage.DECORATION, BiomeTraitCluster.create(trait ->
        {
            trait.generationAttempts(10);
            trait.minimumGenerationHeight(1);
            trait.maximumGenerationHeight(128);
            trait.blockToAttachTo(ICY_NETHERRACK);
            trait.direction(EnumFacing.DOWN);
        }));
        biomeData.addBiomeTrait(GenerationStage.ORE, BiomeTraitOre.create(trait ->
        {
            trait.generationAttempts(16);
            trait.minimumGenerationHeight(10);
            trait.maximumGenerationHeight(108);
            trait.blockToSpawn(NetherExBlocks.QUARTZ_ORE.getDefaultState());
            trait.blockToReplace(ICY_NETHERRACK);
            trait.veinSize(14);
        }));
        biomeData.addBiomeTrait(GenerationStage.ORE, BiomeTraitOre.create(trait ->
        {
            trait.generationAttempts(16);
            trait.minimumGenerationHeight(10);
            trait.maximumGenerationHeight(108);
            trait.blockToSpawn(NetherExBlocks.RIME_ORE.getDefaultState());
            trait.blockToReplace(ICY_NETHERRACK);
            trait.veinSize(7);
        }));
        biomeData.writeToDefaultConfig();
        return biomeData;
    }
}

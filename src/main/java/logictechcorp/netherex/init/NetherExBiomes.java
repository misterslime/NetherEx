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

package logictechcorp.netherex.init;

import logictechcorp.libraryex.api.LibraryExAPI;
import logictechcorp.libraryex.api.world.biome.data.IBiomeDataRegistry;
import logictechcorp.libraryex.utility.InjectionHelper;
import logictechcorp.libraryex.api.world.generation.GenerationStage;
import logictechcorp.libraryex.world.generation.trait.BiomeTraitOre;
import logictechcorp.libraryex.world.generation.trait.BiomeTraitStructure;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.api.NetherExAPI;
import logictechcorp.netherex.handler.ConfigHandler;
import logictechcorp.netherex.world.biome.BiomeArcticAbyss;
import logictechcorp.netherex.world.biome.BiomeFungiForest;
import logictechcorp.netherex.world.biome.BiomeRuthlessSands;
import logictechcorp.netherex.world.biome.BiomeTorridWasteland;
import logictechcorp.netherex.world.biome.data.BiomeDataBOP;
import logictechcorp.netherex.world.biome.data.BiomeDataHell;
import logictechcorp.netherex.world.generation.trait.BiomeTraitEnoki;
import logictechcorp.netherex.world.generation.trait.BiomeTraitThornstalk;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Collections;

import static net.minecraftforge.common.BiomeDictionary.Type.*;

@GameRegistry.ObjectHolder(NetherEx.MOD_ID)
public class NetherExBiomes
{
    //public static final BiomeSoulWastes SOUL_WASTES = InjectionHelper.nullValue();
    public static final BiomeRuthlessSands RUTHLESS_SANDS = InjectionHelper.nullValue();
    public static final BiomeFungiForest FUNGI_FOREST = InjectionHelper.nullValue();
    public static final BiomeTorridWasteland TORRID_WASTELAND = InjectionHelper.nullValue();
    public static final BiomeArcticAbyss ARCTIC_ABYSS = InjectionHelper.nullValue();

    @Mod.EventBusSubscriber(modid = NetherEx.MOD_ID)
    public static class EventHandler
    {
        @SubscribeEvent
        public static void onRegisterBiomes(RegistryEvent.Register<Biome> event)
        {
            event.getRegistry().registerAll(
                    //new BiomeSoulWastes(),
                    new BiomeRuthlessSands(),
                    new BiomeFungiForest(),
                    new BiomeTorridWasteland(),
                    new BiomeArcticAbyss()
            );
        }
    }

    public static void registerBiomes()
    {
        //BiomeDictionary.addTypes(SOUL_WASTES, NETHER, HOT, DRY, SANDY);
        BiomeDictionary.addTypes(RUTHLESS_SANDS, NETHER, HOT, DRY, SANDY);
        BiomeDictionary.addTypes(FUNGI_FOREST, NETHER, COLD, WET, MUSHROOM);
        BiomeDictionary.addTypes(TORRID_WASTELAND, NETHER, HOT, DRY, WASTELAND);
        BiomeDictionary.addTypes(ARCTIC_ABYSS, NETHER, COLD, WET);

        LibraryExAPI.getInstance().getBiomeTraitRegistry().registerBiomeTrait(NetherEx.getResource("thornstalk"), new BiomeTraitThornstalk.Builder(), BiomeTraitThornstalk.class);
        LibraryExAPI.getInstance().getBiomeTraitRegistry().registerBiomeTrait(NetherEx.getResource("enoki"), new BiomeTraitEnoki.Builder(), BiomeTraitEnoki.class);

        IBiomeDataRegistry biomeDataRegistry = NetherExAPI.getInstance().getBiomeDataRegistry();
        biomeDataRegistry.registerBiomeData(BiomeDataHell.INSTANCE);
        //biomeDataRegistry.registerBiomeData(SOUL_WASTES.getBiomeData());
        biomeDataRegistry.registerBiomeData(RUTHLESS_SANDS.getBiomeData());
        biomeDataRegistry.registerBiomeData(FUNGI_FOREST.getBiomeData());
        biomeDataRegistry.registerBiomeData(TORRID_WASTELAND.getBiomeData());
        biomeDataRegistry.registerBiomeData(ARCTIC_ABYSS.getBiomeData());

        BiomeDataHell.INSTANCE.getBiomeTraits(GenerationStage.STRUCTURE).add(
                BiomeTraitStructure.create(trait ->
                {
                    trait.generationAttempts(1);
                    trait.generationProbability(0.125D);
                    trait.minimumGenerationHeight(32);
                    trait.maximumGenerationHeight(118);
                    trait.structures(Collections.singletonList(NetherEx.getResource("pigtificate_village/tiny_pigtificate_village")));
                    trait.structureType(BiomeTraitStructure.StructureType.GROUND);
                }));
        BiomeDataHell.INSTANCE.getBiomeTraits(GenerationStage.ORE).add(
                BiomeTraitOre.create(trait ->
                {
                    trait.generationAttempts(8);
                    trait.minimumGenerationHeight(10);
                    trait.maximumGenerationHeight(108);
                    trait.blockToSpawn(NetherExBlocks.AMETHYST_ORE.getDefaultState());
                    trait.blockToReplace(Blocks.NETHERRACK.getDefaultState());
                    trait.veinSize(7);
                })
        );
        BiomeDataHell.INSTANCE.writeToDefaultConfig();

        if(NetherEx.BIOMES_O_PLENTY_LOADED && ConfigHandler.compatibilityConfig.biomesOPlenty.enableCompatibility)
        {
            biomeDataRegistry.registerBiomeData(new BiomeDataBOP(new ResourceLocation("biomesoplenty:corrupted_sands"), 8, true, false, true));
            biomeDataRegistry.registerBiomeData(new BiomeDataBOP(new ResourceLocation("biomesoplenty:fungi_forest"), 4, true, false, true));
            biomeDataRegistry.registerBiomeData(new BiomeDataBOP(new ResourceLocation("biomesoplenty:phantasmagoric_inferno"), 6, true, false, true));
            biomeDataRegistry.registerBiomeData(new BiomeDataBOP(new ResourceLocation("biomesoplenty:undergarden"), 4, true, false, true));
            biomeDataRegistry.registerBiomeData(new BiomeDataBOP(new ResourceLocation("biomesoplenty:visceral_heap"), 4, true, false, true));
        }
    }
}
